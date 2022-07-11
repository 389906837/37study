package com.cloud.cang.api.antbox.exception;

public abstract class BizException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract StatusDescribe getErrorDescribe();
}
