package com.cloud.cang.api.antbox.constant;

import com.cloud.cang.api.antbox.exception.StatusDescribe;

public enum BaseStatusDescribe implements StatusDescribe {

    /**
     * 成功处理请求
     */
    OK(0, "ok"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR(10001, "System error"),

    /**
     * IP受限
     */
    IP_LIMITED(10002, "IP limited"),
    /**
     * 非法请求
     */
    ILLEGAL_REQUEST(10003, "Illegal request"),

    /**
     * 缺少appkey
     */
    APPKEY_MISSING(10004, "appkey missing"),

    /**
     * 缺少必填参数
     */
    PARAM_MISSING(10005, "Param missing"),

    /**
     * 参数值非法
     */
    ILLEGAL_PARAM_VALUE(10006, "Illegal param value"),

    /**
     * 请求解析失败
     */
    REQUEST_PARSE_FAIL(10007, "Request parse fail"),

    /**
     * 无效的appkey
     */
    INVALID_APPKEY(10008, "invalid appkey");

    private int code;
    private String tips;

    private BaseStatusDescribe(int code, String tips) {
        this.code = code;
        this.tips = tips;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getTips() {
        return tips;
    }
}
