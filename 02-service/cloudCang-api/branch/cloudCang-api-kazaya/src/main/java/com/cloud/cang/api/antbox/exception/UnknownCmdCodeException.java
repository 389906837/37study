package com.cloud.cang.api.antbox.exception;

@SuppressWarnings("serial")
public class UnknownCmdCodeException extends AntboxProtocolException {

    public UnknownCmdCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownCmdCodeException(String message) {
        super(message);
    }

    public UnknownCmdCodeException(Throwable cause) {
        super(cause);
    }

}
