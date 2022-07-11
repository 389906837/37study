package com.cloud.cang.exception;
/**
 * DaoException : 封装Dao(数据库访问)层发生的异常
 *
 */
public class DaoException extends UserException {

	public DaoException( String msg) {
		super("-1", msg);
	} 
	
    public DaoException(String code, String msg) {
		super(code, msg);
	}

	/**
     * 注释内容
     */
    private static final long serialVersionUID = 4697417123995008715L;

}