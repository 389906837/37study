package com.cloud.cang.mgr.sb.domain;

import com.cloud.cang.model.sb.DeviceUpgradeDetails;

/**
 * @version 1.0
 * @ClassName DeviceUpgradeDetailsDomain
 * @Description 设备升级记录页面返回对象
 * @Author zengzexiong
 * @Date 2018年6月22日15:14:54
 */
public class DeviceUpgradeDetailsDomain extends DeviceUpgradeDetails {


    private String merchantName;            // 商户名称
    private String merchantCode;            // 商户编号
    private String sname;              // 设备名称


    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

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

    @Override
    public String toString() {
        return "DeviceUpgradeDetailsDomain{" +
                "merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", sname='" + sname + '\'' +
                '}';
    }
}
