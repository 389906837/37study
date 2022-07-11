package com.cloud.cang.bitmain.bean;


public class ComplexResultBean extends ResultBean {
	
	private String conns;

	public String getConns() {
		return conns;
	}

	public void setConns(String conns) {
		this.conns = conns;
	}

	@Override
	public String toString() {
		return "ComplexResultBean [conns=" + conns + "]";
	}

	


}
