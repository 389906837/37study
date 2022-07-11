package com.cloud.cang.security.vo;


/**
 * 认证结果
 * @author zhouhong
 * @version 1.0
 */
public class AuthenticationResult {
	
	private  Principal principal;
	
	private  boolean isSuccess;
	
	private  String errorCode;
	
	private  String errorMsg;
	
	public Principal getPrincipal() {
		return principal;
	}

	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	
}
