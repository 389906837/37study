package com.cloud.cang.bitmain.socket;

public class Connection {
	
	private String serverCode;//服务器编号
	
	private String sip;
	
	private String port;
	
	private TCPClient client;

	private boolean isConnected;
	
	private String extendAttr;//扩展参数
	
	//is updating or not
	private boolean isUpdating = false;
	
	//is busying or not
	private boolean isBusying = false;
	
	//SDK has initialized or not
	private boolean hasInit = false;

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public String getSip() {
		return sip;
	}

	public void setSip(String sip) {
		this.sip = sip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public TCPClient getClient() {
		return client;
	}

	public void setClient(TCPClient client) {
		this.client = client;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public String getExtendAttr() {
		return extendAttr;
	}

	public void setExtendAttr(String extendAttr) {
		this.extendAttr = extendAttr;
	}
	
	public boolean isBusying() {
		return isBusying;
	}

	public void setBusying(boolean isBusying) {
		this.isBusying = isBusying;
	}

	
	public boolean isHasInit() {
		return hasInit;
	}

	public void setHasInit(boolean hasInit) {
		this.hasInit = hasInit;
	}

	
	public boolean isUpdating() {
		return isUpdating;
	}

	public void setUpdating(boolean isUpdating) {
		this.isUpdating = isUpdating;
	}

	@Override
	public String toString() {
		return "Connection [serverCode=" + serverCode + ", sip=" + sip
				+ ", port=" + port + ", client=" + client + ", isConnected="
				+ isConnected + ", extendAttr=" + extendAttr + ", isUpdating="
				+ isUpdating + ", isBusying=" + isBusying + ", hasInit="
				+ hasInit + "]";
	}
	
	
}
