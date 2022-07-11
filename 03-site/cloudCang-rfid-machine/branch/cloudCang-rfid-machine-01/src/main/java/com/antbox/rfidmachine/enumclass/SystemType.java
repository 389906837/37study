package com.antbox.rfidmachine.enumclass;

/**
 * Created by oyhk on 2017/3/26.
 */
public enum SystemType {

    OPERATION("Operations Center"), MALL("Platform vendor"), MERCHANT("Merchant");
    public String text;

    SystemType(String text) {
        this.text = text;
    }
}
