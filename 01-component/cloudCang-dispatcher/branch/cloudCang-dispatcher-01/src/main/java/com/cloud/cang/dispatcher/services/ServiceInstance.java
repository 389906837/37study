package com.cloud.cang.dispatcher.services;

import java.util.UUID;

import com.cloud.cang.dispatcher.utils.DispatcherUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;

public class ServiceInstance {
	private String name;
	private String id;
	private String ipAddress;
	private Integer port;
	private Integer sslPort;
	private long registrationTimeUTC;
	private String servicePath;
	private String serviceUrl;
	private String requestMethod;//GET POST DELETE PUT
	private Class<?> returnClass;
	private boolean enabled = true;//是否启用 默认启用
	private String appContext;//上下文

	public ServiceInstance(){}
	
	public ServiceInstance(String name, String servicePath)
			throws Exception {
		name = Preconditions.checkNotNull(name, "name cannot be null");
		
		this.name = name;
		this.id = UUID.randomUUID().toString();
		this.ipAddress = DispatcherUtils.getLocalAddress();
		this.port = DispatcherUtils.getPort();
		this.sslPort = DispatcherUtils.getSslPort();
		this.appContext = DispatcherUtils.getAppContext();
		this.registrationTimeUTC = System.currentTimeMillis();
		this.setServicePath(servicePath);
	}

	public Class<?> getReturnClass() {
		return returnClass;
	}

	public void setReturnClass(Class<?> returnClass) {
		this.returnClass = returnClass;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getPort() {
		return port;
	}

	public Integer getSslPort() {
		return sslPort;
	}

	public long getRegistrationTimeUTC() {
		return registrationTimeUTC;
	}

	public void setRegistrationTimeUTC(long registrationTimeUTC) {
		this.registrationTimeUTC = registrationTimeUTC;
	}

	public String getServicePath() {
		return servicePath;
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setSslPort(Integer sslPort) {
		this.sslPort = sslPort;
	}
	
	public String getAppContext() {
		return appContext;
	}

	public void setAppContext(String appContext) {
		this.appContext = appContext;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ServiceInstance that = (ServiceInstance) o;

		if (registrationTimeUTC != that.registrationTimeUTC) {
			return false;
		}
		if (ipAddress != null ? !ipAddress.equals(that.ipAddress)
				: that.ipAddress != null) {
			return false;
		}
		if (id != null ? !id.equals(that.id) : that.id != null) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (port != null ? !port.equals(that.port) : that.port != null) {
			return false;
		}
		if (sslPort != null ? !sslPort.equals(that.sslPort)
				: that.sslPort != null) {
			return false;
		}
        return servicePath != null ? servicePath.equals(that.servicePath) : that.servicePath == null;
    }

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (id != null ? id.hashCode() : 0);
		result = 31 * result + (ipAddress != null ? ipAddress.hashCode() : 0);
		result = 31 * result + (port != null ? port.hashCode() : 0);
		result = 31 * result + (sslPort != null ? sslPort.hashCode() : 0);
		result = 31 * result
				+ (int) (registrationTimeUTC ^ (registrationTimeUTC >>> 32));
		result = 31 * result + (servicePath != null ? servicePath.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ServiceInstance{" + "name='" + name + '\'' + ", id='" + id
				+ '\'' + ", address='" + ipAddress + '\'' + ", port=" + port
				+ ", sslPort=" + sslPort + ", registrationTimeUTC="
				+ registrationTimeUTC + ", servicePath=" + servicePath + '}';
	}
	
	@JsonIgnore
	public String getServerName(){
		return this.ipAddress + ":" + this.port;
	}
}
