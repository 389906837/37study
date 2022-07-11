package com.cloud.cang.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by zhouhong on 2017/5/24.
 */
public class EnterpriseInvestmentException extends AuthenticationException {

    public EnterpriseInvestmentException() {
        super();
    }

    public EnterpriseInvestmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnterpriseInvestmentException(String message) {
        super(message);
    }

    public EnterpriseInvestmentException(Throwable cause) {
        super(cause);
    }
}
