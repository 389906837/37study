package com.cloud.cang.dispatcher.server;

public class ServiceServer extends Server{

	private boolean enabled = false;//是否启用

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
