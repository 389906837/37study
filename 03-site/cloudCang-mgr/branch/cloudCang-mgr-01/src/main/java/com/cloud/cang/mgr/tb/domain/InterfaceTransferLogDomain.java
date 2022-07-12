package com.cloud.cang.mgr.tb.domain;

import com.cloud.cang.model.tb.InterfaceTransferLog;

public class InterfaceTransferLogDomain extends InterfaceTransferLog {
    private String merchantName;//商户名称
    private String merchantCode;//商户编号
    private String sname;//设备名称


    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "InterfaceTransferLogDomain{" +
                "merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", sname='" + sname + '\'' +
                '}';
    }
}
