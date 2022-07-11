package com.cloud.cang.dispatcher.services.filters;

import com.cloud.cang.dispatcher.server.Server;
import com.cloud.cang.dispatcher.server.ServiceServer;
import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.manager.impl.ServiceDiscoveryImpl;

public class EnabledServiceFilter implements InstanceFilter {

	private ServiceDiscoveryImpl discovery;

	public EnabledServiceFilter(ServiceDiscoveryImpl discovery) {
		this.discovery = discovery;
	}

	@Override
	public boolean apply(ServiceInstance instance) {
		if (!instance.isEnabled()) {
			return false;
		}
		Server server = discovery.getServerManager().getServerByName(instance.getServerName());
		if(server == null){
			return false;
		}
        return !(server instanceof ServiceServer && !((ServiceServer) server).isEnabled());
    }
}
