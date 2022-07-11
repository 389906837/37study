package com.cloud.cang.bitmain.bean;

public class UpdateResultBean {
	
	private String serverCode;
	private String progress;
	private int type;//更新服务类型 视觉服务 10  视觉模型 20

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "UpdateResultBean{" +
				"serverCode='" + serverCode + '\'' +
				", progress='" + progress + '\'' +
				", type=" + type +
				'}';
	}
}
