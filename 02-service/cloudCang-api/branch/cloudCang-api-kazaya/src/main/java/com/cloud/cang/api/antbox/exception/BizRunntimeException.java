package com.cloud.cang.api.antbox.exception;

public class BizRunntimeException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private StatusDescribe desc;

    public BizRunntimeException(StatusDescribe desc) {
        super(desc.getTips());
        this.desc = desc;
    }

    public BizRunntimeException(StatusDescribe desc, String detail) {
        super(desc.getTips() + detail);
        this.desc = desc;
    }

    public BizRunntimeException(StatusDescribe desc, Throwable cause) {
        super(desc.getTips(), cause);
        this.desc = desc;
    }

    public BizRunntimeException(StatusDescribe desc, String detail, Throwable cause) {
        super(desc.getTips() + detail, cause);
        this.desc = desc;
    }

    public StatusDescribe getErrorDescribe() {
        return desc;
    }
}
