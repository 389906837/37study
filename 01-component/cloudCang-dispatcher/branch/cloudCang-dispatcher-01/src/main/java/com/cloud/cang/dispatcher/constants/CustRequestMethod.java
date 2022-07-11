package com.cloud.cang.dispatcher.constants;

public enum CustRequestMethod {
	
	POST("POST"),
	GET("GET"),
	PUT("PUT"),
	DELETE("DELETE");
    private String value;

	public String getValue() {
		return value;
	}

	CustRequestMethod(String value) {
		this.value = value;
	}
	public static void main(String[] args) {
		System.out.println(CustRequestMethod.PUT.getValue());
	}
  }
