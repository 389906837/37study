package com.cloud.cang.api.antbox.constant;

public enum BoxBizCode {

    DEVICE_NOT_REGISTER("20001", "售货机没有注册"),
    DEVICE_NOT_FOUND("20002", "售货机不存在"),
    DEVICE_LOST("20003", "售货机失联"),
    NOT_ALLOW_ON_CURRENT_STATUS("20004", "售货机当前状态不允许当前行为"),
    DEVICE_IS_BUSY("20005", "售货机正在繁忙"),
    SEND_COMMAND_FAIL("20101", "指令发送失败"),
    TRADING_WITH_SELF("20201", "售货机正与当前用户交易中"),
    TRADING_WITH_OTHER("20202", "售货机正与其他用户交易中");

    private String code;
    private String tips;

    private BoxBizCode(String code, String tips) {
        this.code = code;
        this.tips = tips;
    }

    public String getCode() {
        return code;
    }

    public String getTips() {
        return tips;
    }

}
