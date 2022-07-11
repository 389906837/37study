package com.cloud.cang.api.antbox.exception;

@SuppressWarnings("serial")
public class UnknownPkgTypeException extends AntboxProtocolException {

    public UnknownPkgTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownPkgTypeException(String message) {
        super(message);
    }

    public UnknownPkgTypeException(Throwable cause) {
        super(cause);
    }

}
