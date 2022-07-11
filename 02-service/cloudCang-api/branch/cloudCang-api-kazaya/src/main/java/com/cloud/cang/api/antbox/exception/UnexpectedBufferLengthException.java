package com.cloud.cang.api.antbox.exception;

/**
 * UnexpectedBufferLengthException
 *
 * @author yong.ma
 * @see 1.3.0
 */
public class UnexpectedBufferLengthException extends AntboxProtocolException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UnexpectedBufferLengthException(int real, int expected) {
        super("real:" + real + " expected:" + expected);

    }

}
