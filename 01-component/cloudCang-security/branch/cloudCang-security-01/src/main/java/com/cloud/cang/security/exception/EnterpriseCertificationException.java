package com.cloud.cang.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by zhouhong on 2017/5/24.
 */
public class EnterpriseCertificationException extends AuthenticationException {

    public EnterpriseCertificationException() {
        super();
    }

    public EnterpriseCertificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnterpriseCertificationException(String message) {
        super(message);
    }

    public EnterpriseCertificationException(Throwable cause) {
        super(cause);
    }
}
