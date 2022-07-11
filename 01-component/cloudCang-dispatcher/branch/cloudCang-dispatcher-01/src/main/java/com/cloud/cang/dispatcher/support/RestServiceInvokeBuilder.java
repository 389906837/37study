package com.cloud.cang.dispatcher.support;

import com.cloud.cang.dispatcher.RegisterServiceCenter;
import com.cloud.cang.dispatcher.ServiceCenterContextUtils;
import com.cloud.cang.dispatcher.constants.Constants;
import com.cloud.cang.dispatcher.services.manager.ServiceProvider;

public class RestServiceInvokeBuilder {

	private RegisterServiceCenter serviceCenter;

	public static RestServiceInvokeBuilder newBuilder() {
		return new RestServiceInvokeBuilder(
				ServiceCenterContextUtils.getDefaultServerCenter());
	}

	public RestServiceInvokeBuilder(RegisterServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

	public RestServiceInvoker newInvoker(String serviceName) throws Exception {
		
		if (!serviceName.startsWith(Constants.REST_SERVICE_NAME_PREFIX)) {
			serviceName = Constants.REST_SERVICE_NAME_PREFIX + serviceName;
		}

		ServiceProvider serviceProvider = serviceCenter.getServiceDiscovery()
				.getServiceProviderFactory().getInstance(serviceName);
		
		return new RestServiceInvoker(serviceCenter.getRestClientHttpRequest().getRestTemplate(), serviceProvider,serviceCenter.getServerManager());
	}

	public RegisterServiceCenter getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(RegisterServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

}
