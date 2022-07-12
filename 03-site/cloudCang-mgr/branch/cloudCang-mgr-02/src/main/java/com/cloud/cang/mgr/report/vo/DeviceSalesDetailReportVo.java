package com.cloud.cang.mgr.report.vo;

import java.math.BigDecimal;


/**
 * 设备报表 VO
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class DeviceSalesDetailReportVo {
    private String deviceId;//设备ID
    private String deviceName;//设备名
    private BigDecimal deviceSalesAmount;//单设备销售金额(现时段)
    private BigDecimal lastYearSaleAmount;//单设备去年同时间销售金额
    private String salesAmountYearOnYear;//销售金额同比
    private String salesAmountAnnulusRatio;//销售金额环比
    private Integer payTypeMost;//支付渠道最多
    private BigDecimal lastTimeSaleAmount;//上一时间段销售额
    private String commodityName;//热销商品名
    private String maxSalesMonth;//热销月份


    public String getMaxSalesMonth() {
        return maxSalesMonth;
    }

    public void setMaxSalesMonth(String maxSalesMonth) {
        this.maxSalesMonth = maxSalesMonth;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public BigDecimal getLastTimeSaleAmount() {
        return lastTimeSaleAmount;
    }

    public void setLastTimeSaleAmount(BigDecimal lastTimeSaleAmount) {
        this.lastTimeSaleAmount = lastTimeSaleAmount;
    }

    public Integer getPayTypeMost() {
        return payTypeMost;
    }

    public void setPayTypeMost(Integer payTypeMost) {
        this.payTypeMost = payTypeMost;
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

    public BigDecimal getDeviceSalesAmount() {
        return deviceSalesAmount;
    }

    public void setDeviceSalesAmount(BigDecimal deviceSalesAmount) {
        this.deviceSalesAmount = deviceSalesAmount;
    }

    public BigDecimal getLastYearSaleAmount() {
        return lastYearSaleAmount;
    }

    public void setLastYearSaleAmount(BigDecimal lastYearSaleAmount) {
        this.lastYearSaleAmount = lastYearSaleAmount;
    }

    public String getSalesAmountYearOnYear() {
        return salesAmountYearOnYear;
    }

    public void setSalesAmountYearOnYear(String salesAmountYearOnYear) {
        this.salesAmountYearOnYear = salesAmountYearOnYear;
    }

    public String getSalesAmountAnnulusRatio() {
        return salesAmountAnnulusRatio;
    }

    public void setSalesAmountAnnulusRatio(String salesAmountAnnulusRatio) {
        this.salesAmountAnnulusRatio = salesAmountAnnulusRatio;
    }
}
