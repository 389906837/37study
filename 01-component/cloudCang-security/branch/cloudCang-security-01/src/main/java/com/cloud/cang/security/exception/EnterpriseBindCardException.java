package com.cloud.cang.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by zhouhong on 2017/5/24.
 */
public class EnterpriseBindCardException extends AuthenticationException {

    public EnterpriseBindCardException() {
        super();
    }

    public EnterpriseBindCardException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnterpriseBindCardException(String message) {
        super(message);
    }

    public EnterpriseBindCardException(Throwable cause) {
        super(cause);
    }
}
