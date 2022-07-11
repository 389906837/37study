package com.cloud.cang.dispatcher.exception;

public class NoFoundServiceInstanceException extends BaseException{
	private static final long serialVersionUID = 1L;

	/**
	 * 默认构造函数
	 */
	public NoFoundServiceInstanceException() {
	}

	/**
	 * @param code 错误编码
	 * @param message 错误消息
	 * @param cause 错误原因
	 */
	public NoFoundServiceInstanceException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code 错误编码
	 * @param message 错误消息
	 */
	public NoFoundServiceInstanceException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param code 错误编码
	 * @param cause 错误消息
	 */
	public NoFoundServiceInstanceException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param message 错误消息
	 * @param cause 错误原因
	 */
	public NoFoundServiceInstanceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message 错误消息
	 */
	public NoFoundServiceInstanceException(String message) {
		super(message);
	}

	/**
	 * @param cause 错误原因
	 */
	public NoFoundServiceInstanceException(Throwable cause) {
		super(cause);
	}
}
