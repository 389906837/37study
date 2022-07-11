package com.cloud.cang.dispatcher.support;

import com.cloud.cang.dispatcher.RegisterServiceCenter;
import com.cloud.cang.dispatcher.ServiceCenterContextUtils;
import com.cloud.cang.dispatcher.server.Server;
import com.cloud.cang.dispatcher.server.ServiceServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerRegisterSupportor {

	public static Logger logger = LoggerFactory
			.getLogger(ServerRegisterSupportor.class);

	private RegisterServiceCenter serviceCenter;

	private Server server;

	public void register() {
		try {
			if (serviceCenter == null) {
				serviceCenter = ServiceCenterContextUtils
						.getDefaultServerCenter();
			}
			if (server == null) {
				server = new ServiceServer();// 默认是serviceServer
			}
			serviceCenter.getServerManager().registerServer(server);
			serviceCenter.isServerRegistered.set(true);
		} catch (Exception e) {
			logger.error("server注册失败!" + server, e);
		}
	}

	public RegisterServiceCenter getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(RegisterServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

}
