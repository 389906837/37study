package com.cloud.cang.dispatcher.services.manager.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cloud.cang.dispatcher.constants.Constants;
import com.cloud.cang.dispatcher.serializer.InstanceSerializer;
import com.cloud.cang.dispatcher.server.manager.ServerManager;
import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.manager.ServiceCache;
import com.cloud.cang.dispatcher.services.manager.ServiceDiscovery;
import com.cloud.cang.dispatcher.services.manager.ServiceProvider;
import com.cloud.cang.dispatcher.serializer.impl.JsonInstanceSerializer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 通过zookeeper来注册查询服务实例
 */
public class ServiceDiscoveryImpl implements ServiceDiscovery {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final CuratorFramework client;
	private final String basePath;
	private final InstanceSerializer serializer;
	private final Map<String, ServiceInstance> services = Maps
			.newConcurrentMap();// 本server注册的所有服务实例
	private final Map<String, NodeCache> watchedServices;// 本server注册且监听的所有服务实例

	private final ServiceProviderFactory serviceProviderFactory;

	private ServerManager serverManager;

	private final ConnectionStateListener connectionStateListener = new ConnectionStateListener() {
		@Override
		public void stateChanged(CuratorFramework client,
				ConnectionState newState) {
			if ((newState == ConnectionState.RECONNECTED)
					|| (newState == ConnectionState.CONNECTED)) {
				try {
					log.debug("Re-registering due to reconnection");
					reRegisterServices();
				} catch (Exception e) {
					log.error(
							"Could not re-register instances after reconnection",
							e);
				}
			}
		}
	};

	public ServiceDiscoveryImpl(CuratorFramework client,
			ServerManager serverManager) {
		this(client, Constants.SERVICES_BASE_PATH, serverManager);
	}

	public ServiceDiscoveryImpl(CuratorFramework client, String basePath,
			ServerManager serverManager) {
		this(client, basePath, true, serverManager);
	}

	public ServiceDiscoveryImpl(CuratorFramework client, String basePath,
			InstanceSerializer serializer, ServerManager serverManager) {
		this(client, basePath, serializer, true, serverManager);
	}

	public ServiceDiscoveryImpl(CuratorFramework client, String basePath,
			boolean watchInstances, ServerManager serverManager) {
		this(client, basePath, new JsonInstanceSerializer(), watchInstances,
				serverManager);
	}

	/**
	 * @param client
	 *            the client
	 * @param basePath
	 *            base path to store data
	 * @param serializer
	 *            serializer for instances (e.g. {@link JsonInstanceSerializer})
	 * @param thisInstance
	 *            instance that represents the service that is running. The
	 *            instance will get auto-registered
	 * @param watchInstances
	 *            if true, watches for changes to locally registered instances
	 */
	public ServiceDiscoveryImpl(CuratorFramework client, String basePath,
			InstanceSerializer serializer, boolean watchInstances,
			ServerManager serverManager) {
		this.client = Preconditions.checkNotNull(client,
				"client cannot be null");
		this.basePath = Preconditions.checkNotNull(basePath,
				"basePath cannot be null");
		this.serializer = Preconditions.checkNotNull(serializer,
				"serializer cannot be null");
		watchedServices = watchInstances ? Maps
				.<String, NodeCache> newConcurrentMap() : null;
		this.serviceProviderFactory = new ServiceProviderFactory(this);
		this.serverManager = serverManager;
	}

	/**
	 * The discovery must be started before use
	 * 
	 * @throws Exception
	 *             errors
	 */
	@Override
	public void start() throws Exception {
		try {
			reRegisterServices();
		} catch (KeeperException e) {
			log.error("Could not register instances - will try again later", e);
		}
		client.getConnectionStateListenable().addListener(
				connectionStateListener);
	}

	@Override
	public void close() throws IOException {
		this.serviceProviderFactory.close();

		if (watchedServices != null) {
			for (NodeCache nodeCache : watchedServices.values()) {
				CloseableUtils.closeQuietly(nodeCache);
			}
		}

		Iterator<ServiceInstance> it = services.values().iterator();
		while (it.hasNext()) {
			// Should not use unregisterService because of potential
			// ConcurrentModificationException
			// so we in-line the bulk of the method here
			ServiceInstance service = it.next();
			String path = pathForInstance(service);
			boolean doRemove = true;

			try {
				client.delete().forPath(path);
			} catch (KeeperException.NoNodeException ignore) {
				// ignore
			} catch (Exception e) {
				doRemove = false;
				log.error(
						"Could not unregister instance: " + service.getName(),
						e);
			}

			if (doRemove) {
				it.remove();
			}
		}

		client.getConnectionStateListenable().removeListener(
				connectionStateListener);
	}

	public ServiceProviderFactory getServiceProviderFactory() {
		return serviceProviderFactory;
	}

	/**
	 * Register/re-register/update a service instance
	 * 
	 * @param service
	 *            service to add
	 * @throws Exception
	 *             errors
	 */
	@Override
	public void registerService(ServiceInstance service) throws Exception {
		setService(service);
		internalRegisterService(service);
	}

	@Override
	public void updateService(ServiceInstance service) throws Exception {
		byte[] bytes = serializer.serialize(service);
		String path = pathForInstance(service);
		client.setData().forPath(path, bytes);
		services.put(service.getId(), service);
	}

	@VisibleForTesting
	protected void internalRegisterService(ServiceInstance service)
			throws Exception {
		byte[] bytes = serializer.serialize(service);
		String path = pathForInstance(service);

		final int MAX_TRIES = 2;
		boolean isDone = false;
		for (int i = 0; !isDone && (i < MAX_TRIES); ++i) {
			try {
				client.create().creatingParentsIfNeeded()
						.withMode(CreateMode.EPHEMERAL).forPath(path, bytes);
				isDone = true;
			} catch (KeeperException.NodeExistsException e) {
				client.delete().forPath(path); // 删除重新创建，以触发监听通知
			}
		}
	}

	/**
	 * Unregister/remove a service instance
	 * 
	 * @param service
	 *            the service
	 * @throws Exception
	 *             errors
	 */
	@Override
	public void unregisterService(ServiceInstance service) throws Exception {
		String path = pathForInstance(service);
		try {
			client.delete().forPath(path);
		} catch (KeeperException.NoNodeException ignore) {
			// ignore
		}
		services.remove(service.getId());
	}

	/**
	 * Return the names of all known services
	 * 
	 * @return list of service names
	 * @throws Exception
	 *             errors
	 */
	@Override
	public Collection<String> queryForNames() throws Exception {
		List<String> names = client.getChildren().forPath(basePath);
		return ImmutableList.copyOf(names);
	}

	/**
	 * Return all known instances for the given service
	 * 
	 * @param name
	 *            name of the service
	 * @return list of instances (or an empty list)
	 * @throws Exception
	 *             errors
	 */
	@Override
	public Collection<ServiceInstance> queryForInstances(String name)
			throws Exception {
		return queryForInstances(name, null);
	}

	/**
	 * Return a service instance POJO
	 * 
	 * @param name
	 *            name of the service
	 * @param id
	 *            ID of the instance
	 * @return the instance or <code>null</code> if not found
	 * @throws Exception
	 *             errors
	 */
	@Override
	public ServiceInstance queryForInstance(String name, String serverName)
			throws Exception {
		String path = pathForInstance(name, serverName);
		try {
			byte[] bytes = client.getData().forPath(path);
			return serializer.deserialize(bytes);
		} catch (KeeperException.NoNodeException ignore) {
			// ignore
		}
		return null;
	}

	void cacheOpened(ServiceCache cache) {
		this.serviceProviderFactory.cacheOpened(cache);
	}

	void cacheClosed(ServiceCache cache) {
		this.serviceProviderFactory.cacheClosed(cache);
	}

	void providerOpened(ServiceProvider provider) {
		this.serviceProviderFactory.providerOpened(provider);
	}

	void providerClosed(ServiceProvider provider) {
		this.serviceProviderFactory.providerOpened(provider);
	}

	CuratorFramework getClient() {
		return client;
	}

	String pathForName(String name) {
		return ZKPaths.makePath(basePath, name);
	}

	InstanceSerializer getSerializer() {
		return serializer;
	}

	List<ServiceInstance> queryForInstances(String name, Watcher watcher)
			throws Exception {
		ImmutableList.Builder<ServiceInstance> builder = ImmutableList
				.builder();
		String path = pathForName(name);
		List<String> instanceIds;

		if (watcher != null) {
			instanceIds = getChildrenWatched(path, watcher, true);
		} else {
			try {
				instanceIds = client.getChildren().forPath(path);
			} catch (KeeperException.NoNodeException e) {
				instanceIds = Lists.newArrayList();
			}
		}

		for (String id : instanceIds) {
			ServiceInstance instance = queryForInstance(name, id);
			if (instance != null) {
				builder.add(instance);
			}
		}
		return builder.build();
	}

	private List<String> getChildrenWatched(String path, Watcher watcher,
			boolean recurse) throws Exception {
		List<String> instanceIds;
		try {
			instanceIds = client.getChildren().usingWatcher(watcher)
					.forPath(path);
		} catch (KeeperException.NoNodeException e) {
			if (recurse) {
				try {
					client.create().creatingParentsIfNeeded().forPath(path);
				} catch (KeeperException.NodeExistsException ignore) {
					// ignore
				}
				instanceIds = getChildrenWatched(path, watcher, false);
			} else {
				throw e;
			}
		}
		return instanceIds;
	}

	@VisibleForTesting
	String pathForInstance(ServiceInstance service) {
		return pathForInstance(service.getName(), service.getServerName());
	}

	String pathForInstance(String serviceName, String serverName) {
		return ZKPaths.makePath(pathForName(serviceName),serverName);
	}

	@VisibleForTesting
	ServiceInstance getRegisteredService(String id) {
		return services.get(id);
	}

	private void reRegisterServices() throws Exception {
		for (ServiceInstance service : services.values()) {
			internalRegisterService(service);
		}
	}

	private void setService(final ServiceInstance instance) {
		services.put(instance.getId(), instance);
		if (watchedServices != null) {
			final NodeCache nodeCache = new NodeCache(client,
					pathForInstance(instance));
			try {
				nodeCache.start(true);
			} catch (Exception e) {
				log.error("Could not start node cache for: " + instance, e);
			}
			NodeCacheListener listener = new NodeCacheListener() {
				@Override
				public void nodeChanged() throws Exception {
					if (nodeCache.getCurrentData() != null) {
						ServiceInstance newInstance = serializer.deserialize(
								nodeCache.getCurrentData().getData());
						services.put(newInstance.getId(), newInstance);
					} else {
						log.warn("Instance data has been deleted for: "
								+ instance);
					}
				}
			};
			nodeCache.getListenable().addListener(listener);
			watchedServices.put(instance.getId(), nodeCache);
		}
	}

	public ServerManager getServerManager() {
		return this.serverManager;
	}
}
