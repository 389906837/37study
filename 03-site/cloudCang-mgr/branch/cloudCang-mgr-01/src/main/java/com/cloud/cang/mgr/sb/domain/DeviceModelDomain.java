package com.cloud.cang.mgr.sb.domain;

import com.cloud.cang.model.sb.DeviceModel;

/**
 * Created by Alex on 2018/2/10.
 */
public class DeviceModelDomain extends DeviceModel {
    private String scode;                   /* 设备编号 */
    private String sname;                   /* 设备名称 */
    private String merchantName;            /* 商户名称 */
    private String merchantCode;           /* 商户编号 */

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

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "DeviceModelDomain{" +
                "scode='" + scode + '\'' +
                ", sname='" + sname + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
