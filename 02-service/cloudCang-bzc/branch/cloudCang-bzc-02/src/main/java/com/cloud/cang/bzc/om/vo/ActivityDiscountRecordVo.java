package com.cloud.cang.bzc.om.vo;

/**
 * Created by Alex on 2018/1/9.
 */
public class ActivityDiscountRecordVo {

    private String scode;   /* 优惠记录编号 */

    private String sacCode; /* 活动编号 */

    private String smemberCode; /* 会员编号 */

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getSacCode() {
        return sacCode;
    }

    public void setSacCode(String sacCode) {
        this.sacCode = sacCode;
    }

    public String getSmemberCode() {
        return smemberCode;
    }

    public void setSmemberCode(String smemberCode) {
        this.smemberCode = smemberCode;
    }
}
