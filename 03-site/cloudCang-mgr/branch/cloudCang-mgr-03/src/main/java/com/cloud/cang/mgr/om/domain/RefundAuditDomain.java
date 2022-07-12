package com.cloud.cang.mgr.om.domain;

import com.cloud.cang.model.om.RefundAudit;

/**
 * @version 1.0
 * @description: 退款Domain
 * @author:Yanlingfeng
 * @time:2018-02-22 14:07:05
 */
public class RefundAuditDomain extends RefundAudit {
    private static final long serialVersionUID = 1L;
    private String orderStr;//排序字段
    private String merchantName;//商户名
    private Integer isRefundAudit;//退款是否需要审核 0:否 1:是
    private String queryCondition;//查询条件
    private String deviceName;   //设备名
    private String deviceCode;    //设备编号
    private String deviceAddress;    //设备地址
    private Integer ipayType;//退款渠道



    public Integer getIpayType() {
        return ipayType;
    }

    public void setIpayType(Integer ipayType) {
        this.ipayType = ipayType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public Integer getIsRefundAudit() {
        return isRefundAudit;
    }

    public void setIsRefundAudit(Integer isRefundAudit) {
        this.isRefundAudit = isRefundAudit;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
