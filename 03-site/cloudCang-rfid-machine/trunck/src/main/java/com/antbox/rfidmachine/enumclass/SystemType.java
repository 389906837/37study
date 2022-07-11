package com.antbox.rfidmachine.enumclass;

/**
 * Created by oyhk on 2017/3/26.
 */
public enum SystemType {

    OPERATION("运营中心"), MALL("平台商"), MERCHANT("商户商");
    public String text;

    SystemType(String text) {
        this.text = text;
    }
}
