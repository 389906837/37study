package com.cloud.cang.api.antbox.constant;

/**
 * 220v电源状态
 *
 * @Author chipun.cheng
 * @Date 2017年4月8日 下午1:50:24
 */
public enum AcStatus {

    ON(0, "正常"), OFF(1, "断开");

    private int code;

    private String desc;

    private AcStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AcStatus getByCode(int code) {
        for (AcStatus type : AcStatus.values()) {
            if (type.code == code) return type;
        }

        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
