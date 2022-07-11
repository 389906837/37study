package com.cloud.cang.exception;
/**
 * UserException : 用户自定义异常
 *
 */
public class UserException extends RuntimeException {

	public UserException(String msg) {
		this("-1", msg);
	}
	public UserException(String code,String msg){
		super(msg);
		this.errorCode=code;
	}
	 public UserException(Throwable cause) {
			super(cause);
	}
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 7928003401611048366L;
    /**
     * 异常发生时间
     */
    private long date = System.currentTimeMillis();

    private String errorCode;
    
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public long getDate() {
        return date;
    }
}