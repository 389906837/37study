package com.cloud.cang.api.antbox.constant;

/**
 * Created by louis on 05/05/2017.
 */
public enum BoxOpenDoorType {

    COMMON(0, "购物"), DISTRIBUTION(1, "补货");

    public String text;
    public Integer code;

    BoxOpenDoorType(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
}
