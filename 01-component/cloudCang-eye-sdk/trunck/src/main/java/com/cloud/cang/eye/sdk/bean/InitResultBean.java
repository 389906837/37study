package com.cloud.cang.eye.sdk.bean;


public class InitResultBean {
	
	private String status;
	private String cells;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCells() {
		return cells;
	}

	public void setCells(String cells) {
		this.cells = cells;
	}

	@Override
	public String toString() {
		return "InitResultBean [status=" + status + ", cells=" + cells + "]";
	}
	
	
}
