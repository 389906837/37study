package com.cloud.cang.dispatcher;

import java.util.concurrent.atomic.AtomicBoolean;

import com.cloud.cang.dispatcher.server.manager.impl.ServerManagerImpl;
import com.cloud.cang.dispatcher.services.manager.ServiceDiscovery;
import com.cloud.cang.dispatcher.services.manager.impl.ServiceDiscoveryImpl;
import com.cloud.cang.dispatcher.support.RestClientHttpRequest;
import com.cloud.cang.dispatcher.server.manager.ServerManager;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterServiceCenter {

	private String connectString;
	private int sessionTimeoutMs;
	private int connectionTimeoutMs;
	private CuratorFramework client;

	private ServerManager serverManager;
	private ServiceDiscovery serviceDiscovery;
	
	private RestClientHttpRequest restClientHttpRequest;

	public static Logger logger = LoggerFactory.getLogger(RegisterServiceCenter.class);

	private static final int DEFAULT_SESSION_TIMEOUT_MS = 60 * 1000;
	private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 15 * 1000;
	
	public volatile AtomicBoolean isServerRegistered = new AtomicBoolean();//server是否已注册
	public volatile AtomicBoolean isServiceRegistered = new AtomicBoolean();//service是否已注册
	
	public void init() {
		try {
			
			if (sessionTimeoutMs <= 0) {
				sessionTimeoutMs = DEFAULT_SESSION_TIMEOUT_MS;
			}
			if (connectionTimeoutMs <= 0) {
				connectionTimeoutMs = DEFAULT_CONNECTION_TIMEOUT_MS;
			}
			/*this.client = CuratorFrameworkFactory.newClient(connectString,
					sessionTimeoutMs, connectionTimeoutMs,
					new ExponentialBackoffRetry(100, 3));*/
			
			setConnectString(client.getZookeeperClient().getCurrentConnectionString());

			serverManager = new ServerManagerImpl(client);
			serviceDiscovery = new ServiceDiscoveryImpl(client, serverManager);
			restClientHttpRequest = new RestClientHttpRequest().init();

			//client.start();
			serverManager.start();
			serviceDiscovery.start();
			
			ServiceCenterContextUtils.addDispatcher(this);
		} catch (Exception e) {
			logger.error("Dispatcher 初始化失败！", e);
		}
	}

	public boolean destory() {
		try {
			this.serverManager.close();
			this.serviceDiscovery.close();
			CloseableUtils.closeQuietly(client);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public ServerManager getServerManager() {
		return serverManager;
	}

	public ServiceDiscovery getServiceDiscovery() {
		return serviceDiscovery;
	}

	public String getConnectString() {
		return connectString;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	public int getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public void setSessionTimeoutMs(int sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	public int getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}

	public void setConnectionTimeoutMs(int connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}

	public void setClient(CuratorFramework client) {
		this.client = client;
	}

	public RestClientHttpRequest getRestClientHttpRequest() {
		return restClientHttpRequest;
	}
	
	

}
