package com.cloud.cang.api.antbox.dto;

/**
 * Created by oyhk on 2017/5/10.
 */
public enum BoxOpenDoorSource {
    KAZAYA("kazaya"), ALIPAY("支付宝"), WECHAT("微信"), PARTNER("第三方"), ILLEGAL("非法来源"), CARD("充值卡"), MINI_APP("微信小程序");

    private String text;

    BoxOpenDoorSource(String text) {
        this.text = text;
    }
}
