package com.cang.os.common.utils.id.generator;

/**
 * IDException
 * @since 2010-03-17
 * @see RuntimeException
 */
public class IDException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IDException() {
		super("ID异常!");
	}

	public IDException(String message, Throwable cause) {
		super(message, cause);
	}

	public IDException(String message) {
		super(message);
	}

	public IDException(Throwable cause) {
		super(cause);
	}

}