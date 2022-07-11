package com.cloud.cang.api.antbox.exception;

public class OperationException extends BizRunntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public OperationException(StatusDescribe desc) {
        super(desc);
    }

    public OperationException(StatusDescribe desc, Throwable cause) {
        super(desc, cause);
    }

}
