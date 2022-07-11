package com.cloud.cang.eye.sdk.bean;


public class BaseCellListBean {
	
	private String serverCode;//服务器编号
	
	private String scode;//处理业务编号
	
	private String sextends;//扩展参数

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}


	public String getSextends() {
		return sextends;
	}

	public void setSextends(String sextends) {
		this.sextends = sextends;
	}

	@Override
	public String toString() {
		return "BaseCellListBean [serverCode=" + serverCode + ", scode="
				+ scode + ", sextends=" + sextends + "]";
	}
	
	
}
