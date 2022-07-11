package com.cloud.cang.dispatcher.exception;

/**
 */

public class AssertException extends SystemException {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认构造函数
	 */
	public AssertException() {
	}

	/**
	 * @param code 错误编码
	 * @param message 错误消息
	 * @param cause 错误原因
	 */
	public AssertException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code 错误编码
	 * @param message 错误消息
	 */
	public AssertException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param code 错误编码
	 * @param cause 错误消息
	 */
	public AssertException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param message 错误消息
	 * @param cause 错误原因
	 */
	public AssertException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message 错误消息
	 */
	public AssertException(String message) {
		super(message);
	}

	/**
	 * @param cause 错误原因
	 */
	public AssertException(Throwable cause) {
		super(cause);
	}
}
