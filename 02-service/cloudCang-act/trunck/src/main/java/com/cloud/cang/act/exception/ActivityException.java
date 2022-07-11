package com.cloud.cang.act.exception;

/**
 * 活动更级别异常
 * @author Hunter
 * @version 1.0
 */
public abstract class ActivityException extends RuntimeException {

	private static final long serialVersionUID = 1741784780766500872L;
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ActivityException(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

}
