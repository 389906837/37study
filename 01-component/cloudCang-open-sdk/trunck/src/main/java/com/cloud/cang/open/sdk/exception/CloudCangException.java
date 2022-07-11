package com.cloud.cang.open.sdk.exception;

/**
 * @version 1.0
 * @Description: 异常类
 * @Author: zengzexiong
 * @Date: 2018年8月31日13:23:12
 */
public class CloudCangException extends Exception {

    private String errCode;
    private String errMsg;

    public CloudCangException() {
    }

    public CloudCangException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudCangException(String message) {
        super(message);
    }

    public CloudCangException(Throwable cause) {
        super(cause);
    }

    public CloudCangException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }


    public String getErrMsg() {
        return errMsg;
    }

}
