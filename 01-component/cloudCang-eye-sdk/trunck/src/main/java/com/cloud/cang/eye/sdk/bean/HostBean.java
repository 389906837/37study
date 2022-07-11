package com.cloud.cang.eye.sdk.bean;

public class HostBean {
	
	
	private String serverCode;//服务器编号
	
	private String sip;//服务器IP
	
	private String port;//服务器端口
	
	private String extendAttr;//扩展参数

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

	public String getExtendAttr() {
		return extendAttr;
	}

	public void setExtendAttr(String extendAttr) {
		this.extendAttr = extendAttr;
	}

	@Override
	public String toString() {
		return "HostBean [serverCode=" + serverCode + ", sip=" + sip
				+ ", port=" + port + ", extendAttr=" + extendAttr + "]";
	}
	
	
}
