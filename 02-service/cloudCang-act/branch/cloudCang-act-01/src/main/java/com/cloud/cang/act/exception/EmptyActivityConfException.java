package com.cloud.cang.act.exception;

/**
 * 找不到活动配置异常
 * @author Hunter
 * @version 1.0
 */
public class EmptyActivityConfException extends ActivityException {

	public EmptyActivityConfException(String code, String msg) {
		super(code, msg);
	}
	
	public EmptyActivityConfException(String msg) {
		super(EmptyActivityConfException.class.getSimpleName(), msg);
	}

	private static final long serialVersionUID = 5654832494386494840L;

}
