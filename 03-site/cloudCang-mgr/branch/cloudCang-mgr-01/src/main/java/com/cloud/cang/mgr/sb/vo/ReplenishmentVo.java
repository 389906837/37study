package com.cloud.cang.mgr.sb.vo;

import java.io.Serializable;

/**
 * 补货单VO
 */
public class ReplenishmentVo implements Serializable {
    private String[] commodityIds;//商品信息
    private String deviceId;//补货单设备ID
    private String srenewalName;//补货员
    private String srenewalMobile;//补货员联系方式
    private String sremark;//备注

    public String[] getCommodityIds() {
        return commodityIds;
    }

    public void setCommodityIds(String[] commodityIds) {
        this.commodityIds = commodityIds;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSrenewalName() {
        return srenewalName;
    }

    public void setSrenewalName(String srenewalName) {
        this.srenewalName = srenewalName;
    }

    public String getSrenewalMobile() {
        return srenewalMobile;
    }

    public void setSrenewalMobile(String srenewalMobile) {
        this.srenewalMobile = srenewalMobile;
    }

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    @Override
    public String toString() {
        return "ReplenishmentVo{" +
                "commodityIds='" + commodityIds + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", srenewalName='" + srenewalName + '\'' +
                ", srenewalMobile='" + srenewalMobile + '\'' +
                ", sremark='" + sremark + '\'' +
                '}';
    }
}
