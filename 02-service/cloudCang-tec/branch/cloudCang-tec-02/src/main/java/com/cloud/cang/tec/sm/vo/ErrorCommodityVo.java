package com.cloud.cang.tec.sm.vo;

/**
 * 异常商品预警 Vo
 */
public class ErrorCommodityVo {
        private String deviceName;//设备名
        private String commodityName;//商品名
        private Integer errorCommodityNum;//异常商品数量


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Integer getErrorCommodityNum() {
        return errorCommodityNum;
    }

    public void setErrorCommodityNum(Integer errorCommodityNum) {
        this.errorCommodityNum = errorCommodityNum;
    }
}
