package com.cloud.cang.mgr.xx.domain;

import com.cloud.cang.model.xx.MsgTemplate;

/**
 * Created by Administrator on 2018/5/30.
 */
public class MsgTemplateDomain extends MsgTemplate{
    // 短信供应商名称
    private String supplierName;

    // 商户名称
    private String merchantName;

    // 商户编号
    private String shCode;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getShCode() {
        return shCode;
    }

    public void setShCode(String shCode) {
        this.shCode = shCode;
    }

    @Override
    public String toString() {
        return "MsgTemplateDomain{" +
                "supplierName='" + supplierName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", shCode='" + shCode + '\'' +
                '}';
    }
}
