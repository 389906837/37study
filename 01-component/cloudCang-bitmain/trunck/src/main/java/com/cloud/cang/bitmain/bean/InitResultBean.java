package com.cloud.cang.bitmain.bean;


public class InitResultBean {
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "InitResultBean{" +
				"status='" + status + '\'' +
				'}';
	}
}
