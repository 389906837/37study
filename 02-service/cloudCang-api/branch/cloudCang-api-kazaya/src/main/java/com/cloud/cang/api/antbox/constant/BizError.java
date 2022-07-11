package com.cloud.cang.api.antbox.constant;

import com.cloud.cang.api.antbox.exception.StatusDescribe;

public enum BizError implements StatusDescribe {

    DEVICE_NOT_FOUND(20001, "售货机不存在"),
    DEVICE_LOST(20002, "售货机失联"),
    NOT_ALLOW_ON_CURRENT_STATUS(20003, "售货机当前状态不允许当前行为"),
    SEND_COMMAND_FAIL(20101, "指令发送失败"),
    TRADING_WITH_SELF(20201, "售货机正与当前用户交易中"),
    TRADING_WITH_OTHER(20202, "售货机正与其他用户交易中");

    private int code;
    private String tips;

    private BizError(int code, String tips) {
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
