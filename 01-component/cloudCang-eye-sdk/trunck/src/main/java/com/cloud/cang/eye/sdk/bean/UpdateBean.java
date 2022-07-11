package com.cloud.cang.eye.sdk.bean;

public class UpdateBean {
	
	private String path;

	private String addressType;//服务器地址类型 本地 10  远程地址 20

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	@Override
	public String toString() {
		return "UpdateBean{" +
				"path='" + path + '\'' +
				", addressType='" + addressType + '\'' +
				'}';
	}
}
