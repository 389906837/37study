package com.cloud.cang.pay.wechat.notify;

/**
 * Created by YLF on 2019/8/14.
 */
public class WechatPointResponseData {
    private String code;
    private String message;


    @Override
    public String toString() {
        return "WechatPointResponseData{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
