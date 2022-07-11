package com.cloud.cang.dispatcher.server.manager.impl;

import java.io.IOException;
import java.util.List;

import com.cloud.cang.dispatcher.constants.Constants;
import com.cloud.cang.dispatcher.serializer.ServerSerializer;
import com.cloud.cang.dispatcher.server.Server;
import com.cloud.cang.dispatcher.server.manager.ServerManager;
import com.cloud.cang.dispatcher.server.manager.ServerCache;
import com.cloud.cang.dispatcher.serializer.impl.JsonServerSerializer;
import com.cloud.cang.dispatcher.utils.DispatcherUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class ServerManagerImpl implements ServerManager {

	private final Logger log = LoggerFactory.getLogger(getClass());

	final CuratorFramework client;
	final String basePath;
	final ServerSerializer serializer;

	private Server registerServer;

	private NodeCache nodeCache;
	
	private ServerCache serverCache;

	private final ConnectionStateListener connectionStateListener = new ConnectionStateListener() {
		@Override
		public void stateChanged(CuratorFramework client,
				ConnectionState newState) {
			if ((newState == ConnectionState.RECONNECTED)
					|| (newState == ConnectionState.CONNECTED)) {
				try {
					log.debug("Re-registering due to reconnection");
					reRegisterServer();
				} catch (Exception e) {
					log.error(
							"Could not re-register instances after reconnection",
							e);
				}
			}
		}
	};

	public ServerManagerImpl(CuratorFramework client) {
		this(client, Constants.SERVERS_BASE_PATH);
	}

	public ServerManagerImpl(CuratorFramework client, String basePath) {
		this(client, basePath, new JsonServerSerializer());
	}

	public ServerManagerImpl(CuratorFramework client, String basePath,
			ServerSerializer serializer) {
		this.client = client;
		this.basePath = basePath;
		this.serializer = serializer;
		this.serverCache = new ServerCacheImpl(this);
	}

	@Override
	public void registerServer(Server server) throws Exception {
		Preconditions.checkNotNull(server.getModule(),
				"registerServer module cannot be null");

		// 如果未指定ip则取第一个接口ip
		if (StringUtils.isEmpty(server.getIpAddress())) {
			server.setIpAddress(DispatcherUtils.getLocalAddress());
		}
		if (server.getPort() <= 0) {
			server.setPort(DispatcherUtils.getPort());
		}
		this.registerServer = server;
		internalRegisterServer();
		watcherServer();
	}

	private void internalRegisterServer() throws Exception {
		byte[] bytes = serializer.serialize(registerServer);
		String path = getPathForServer(registerServer);

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

	private String getPathForServer(Server server) {
		if (StringUtils.isEmpty(server.getZkPath())) {
		
			String zkPath = ZKPaths.makePath(ZKPaths.makePath(
					basePath, server.getClass().getSimpleName()), server.getName());
			
			server.setZkPath(zkPath);
		}
		return server.getZkPath();
	}

	private void watcherServer() {
		nodeCache = new NodeCache(client, getPathForServer(registerServer));
		try {
			nodeCache.start(true);
		} catch (Exception e) {
			log.error("Could not start node cache for: " + registerServer, e);
		}
		NodeCacheListener listener = new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				if (nodeCache.getCurrentData() != null) {
					registerServer = serializer.deserialize(nodeCache
							.getCurrentData().getData());
				} else {
					log.warn("Instance data has been deleted for: "
							+ registerServer);
				}
			}
		};
		nodeCache.getListenable().addListener(listener);
	}

	@Override
	public void updateServer(Server server) throws Exception {
		byte[] bytes = serializer.serialize(server);
		String path = getPathForServer(server);
		client.setData().forPath(path, bytes);
	}

	private void reRegisterServer() throws Exception {
		if(registerServer != null){
			registerServer.setLastRegisterTime(System.currentTimeMillis());
			this.registerServer(registerServer);
		}
	}

	@Override
	public void start() throws Exception {
		client.getConnectionStateListenable().addListener(
				connectionStateListener);
		serverCache.start();
	}

	@Override
	public void close() throws IOException {
		client.getConnectionStateListenable().removeListener(
				connectionStateListener);
		nodeCache.close();
		serverCache.close();
	}

	public Server getRegisterServer() {
		return registerServer;
	}

	@Override
	public List<Server> getAllServers() {
		return serverCache.getAllServers();
	}

	@Override
	public Server getServerByName(String name) {
		return serverCache.getServerCache().get(name);
	}
	
}
