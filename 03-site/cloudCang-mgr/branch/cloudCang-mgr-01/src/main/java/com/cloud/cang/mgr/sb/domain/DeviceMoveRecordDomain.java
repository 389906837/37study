package com.cloud.cang.mgr.sb.domain;

import com.cloud.cang.model.sb.DeviceMoveRecord;

/**
 * Created by Administrator on 2018/6/13.
 */
public class DeviceMoveRecordDomain extends DeviceMoveRecord{
    // 商户名称
    private String merchantName;

    // 设备名称
    private String sbName;

    // 设备地址
    private String address;

    // 排序字段
    private String orderStr;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "DeviceMoveRecordDomain{" +
                "merchantName='" + merchantName + '\'' +
                ", sbName='" + sbName + '\'' +
                ", address='" + address + '\'' +
                ", orderStr='" + orderStr + '\'' +
                '}';
    }
}
