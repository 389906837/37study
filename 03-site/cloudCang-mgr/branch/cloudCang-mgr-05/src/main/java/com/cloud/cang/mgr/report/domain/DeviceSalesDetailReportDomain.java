package com.cloud.cang.mgr.report.domain;

/**
 * 设备报表 Domain
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class DeviceSalesDetailReportDomain {
    private String queryTimeCondition;//查询时间条件
    private String merchantId;//商户名
    private String deviceName;//设备名
    private String orderStr;//排序字段
    private String condition;//查询条件
    private String lastCondition;//查询条件(时间推前一年)
    private String lastTimeCondition;//查询条件(上一段时间)


    public String getQueryTimeCondition() {
        return queryTimeCondition;
    }

    public void setQueryTimeCondition(String queryTimeCondition) {
        this.queryTimeCondition = queryTimeCondition;
    }

    public String getLastTimeCondition() {
        return lastTimeCondition;
    }

    public void setLastTimeCondition(String lastTimeCondition) {
        this.lastTimeCondition = lastTimeCondition;
    }

    public String getLastCondition() {
        return lastCondition;
    }

    public void setLastCondition(String lastCondition) {
        this.lastCondition = lastCondition;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
