package com.cloud.cang.dispatcher.server.manager.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import com.cloud.cang.dispatcher.server.Server;
import com.cloud.cang.dispatcher.server.ServiceServer;
import com.cloud.cang.dispatcher.server.manager.ServerCache;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ServerCacheImpl implements ServerCache, PathChildrenCacheListener {

	private ServerManagerImpl serverManager;

	private final PathChildrenCache cache;

	private final AtomicReference<State> state = new AtomicReference<State>(
			State.LATENT);

	private final ConcurrentMap<String, Server> serversMap = Maps
			.newConcurrentMap();

	public ServerCacheImpl(ServerManagerImpl serverManager) {
		this.serverManager = serverManager;
		cache = new PathChildrenCache(serverManager.client,
				getServiceServerZKPath(), true);
		cache.getListenable().addListener(this);
	}

	private String getServiceServerZKPath() {
		return serverManager.basePath + "/"
				+ ServiceServer.class.getSimpleName();
	}

	private enum State {
		LATENT, STARTED, STOPPED
	}

	@Override
	public void start() throws Exception {
		Preconditions.checkState(
				state.compareAndSet(State.LATENT, State.STARTED),
				"ServerCache Cannot be started more than once");

		cache.start(true);
		for (ChildData childData : cache.getCurrentData()) {
			addServer(childData, true);
		}
		// serverManager.cacheOpened(this);
	}

	@Override
	public void close() throws IOException {
		Preconditions.checkState(
				state.compareAndSet(State.STARTED, State.STOPPED),
				"ServerCache Already closed or has not been started");

		CloseableUtils.closeQuietly(cache);

		// serverManager.cacheClosed(this);
	}

	@Override
	public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
			throws Exception {
		switch (event.getType()) {
		case CHILD_ADDED:
		case CHILD_UPDATED: {
			addServer(event.getData(), false);
			break;
		}

		case CHILD_REMOVED: {
			serversMap.remove(serverNameFromData(event.getData()));
			break;
		}
		default:
			break;
		}
	}

	private void addServer(ChildData childData, boolean onlyIfAbsent)
			throws Exception {
		Server server = serverManager.serializer.deserialize(
				childData.getData());
		if (onlyIfAbsent) {
			serversMap.putIfAbsent(server.getName(), server);
		} else {
			serversMap.put(server.getName(), server);
		}
		cache.clearDataBytes(childData.getPath(), childData.getStat()
				.getVersion());
	}

	private String serverNameFromData(ChildData childData) {
		return ZKPaths.getNodeFromPath(childData.getPath());
	}

	@Override
	public List<Server> getAllServers() {
		return Lists.newArrayList(serversMap.values());
	}

	@Override
	public Map<String, Server> getServerCache() {
		return this.serversMap;
	}

}
