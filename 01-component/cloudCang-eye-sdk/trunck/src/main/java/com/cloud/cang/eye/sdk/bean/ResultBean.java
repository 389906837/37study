package com.cloud.cang.eye.sdk.bean;


import com.cloud.cang.eye.sdk.EnumError;

public class ResultBean {
	
	private String code;//业务处理code
	private String serverCode;//服务器编号
	private String message;
	//通信占用时间
	private String commTime;
	
	public void setCodeMsg(EnumError error) {
		this.code = error.getCode();
		this.message = error.getMsg();
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCommTime() {
		return commTime;
	}
	public void setCommTime(String commTime) {
		this.commTime = commTime;
	}
	
	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	@Override
	public String toString() {
		return "ResultBean [code=" + code + ", serverCode=" + serverCode
				+ ", message=" + message + ", commTime=" + commTime + "]";
	}
	
	
}
