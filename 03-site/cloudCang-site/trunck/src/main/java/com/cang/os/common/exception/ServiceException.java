package com.cang.os.common.exception;

/**
 * ServiceException : 封装业务层发生的异常
 *
 */
public class ServiceException extends UserException {
	public ServiceException( String msg) {
		super("-1", msg);
	} 
    public ServiceException(String code, String msg) {
		super(code, msg);
	}
    public ServiceException(Throwable cause) {
		super(cause);
}
   

	/**
     * 注释内容
     */
    private static final long serialVersionUID = -4506015276365558007L;

}