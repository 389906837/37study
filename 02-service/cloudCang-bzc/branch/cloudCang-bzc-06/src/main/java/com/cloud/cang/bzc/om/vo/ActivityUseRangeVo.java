package com.cloud.cang.bzc.om.vo;

/**
 * Created by Alex on 2018/1/9.
 */
public class ActivityUseRangeVo {

    private String sacId;/* 活动ID */

    private String sacCode;/* 活动编号 */

    private Integer irangeType;/* 应用范围类型 10:全部 20:部分设备 30:部分商品 40:部分设备部分商品 */

    private String sdeviceId;/* 设备ID */

    private String sdeviceCode;/* 设备编号 */

    private String scommodityId;/* 商品ID */

    private String scommodityCode;/* 商品编号 */

    public String getSacId() {
        return sacId;
    }

    public void setSacId(String sacId) {
        this.sacId = sacId;
    }

    public String getSacCode() {
        return sacCode;
    }

    public void setSacCode(String sacCode) {
        this.sacCode = sacCode;
    }

    public Integer getIrangeType() {
        return irangeType;
    }

    public void setIrangeType(Integer irangeType) {
        this.irangeType = irangeType;
    }

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    public String getScommodityId() {
        return scommodityId;
    }

    public void setScommodityId(String scommodityId) {
        this.scommodityId = scommodityId;
    }

    public String getScommodityCode() {
        return scommodityCode;
    }

    public void setScommodityCode(String scommodityCode) {
        this.scommodityCode = scommodityCode;
    }
}
