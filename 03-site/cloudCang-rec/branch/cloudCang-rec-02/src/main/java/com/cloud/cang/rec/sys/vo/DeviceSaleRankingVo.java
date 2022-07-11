package com.cloud.cang.rec.sys.vo;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @version 1.0
 * @description: 设备销售Top 10
 * @author:Yanlingfeng
 * @time:2018-03-26 09:39:05
 */
public class DeviceSaleRankingVo {
    private String id;//订单Id
    private String deviceId;//设备Id
    private String deviceName;//设备名
    private BigDecimal deviceSaleAmount;//设备销售总金额
    private String deviceSaleAmountStr;//设备销售总金额
    private String deviceSaleNum;//设备销售商品数
    private String visitorNum;//设备访客数

    @Override
    public String toString() {
        return "DeviceSaleRankingVo{" +
                "id='" + id + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceSaleAmount=" + deviceSaleAmount +
                ", deviceSaleAmountStr='" + deviceSaleAmountStr + '\'' +
                ", deviceSaleNum='" + deviceSaleNum + '\'' +
                ", visitorNum='" + visitorNum + '\'' +
                '}';
    }

    public String getVisitorNum() {
        return visitorNum;
    }

    public void setVisitorNum(String visitorNum) {
        this.visitorNum = visitorNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceSaleNum() {
        return deviceSaleNum;
    }

    public void setDeviceSaleNum(String deviceSaleNum) {
        this.deviceSaleNum = deviceSaleNum;
    }

    public BigDecimal getDeviceSaleAmount() {
        return deviceSaleAmount;
    }

    public void setDeviceSaleAmount(BigDecimal deviceSaleAmount) {
        this.deviceSaleAmount = deviceSaleAmount;
    }

    public String getDeviceSaleAmountStr() {
        if (null == this.getDeviceSaleAmount()) {
            return "--";
        } else {
            return new DecimalFormat(",##0.##").format(this.getDeviceSaleAmount().doubleValue());
        }
    }

    public void setDeviceSaleAmountStr(String deviceSaleAmountStr) {
        this.deviceSaleAmountStr = deviceSaleAmountStr;
    }
}

