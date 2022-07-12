package com.cloud.cang.mgr.xx.domain;

import com.cloud.cang.model.xx.SupplierInfo;

/**
 * 短信供应商返回商户名称
 */
public class SupplierInfoDomain extends SupplierInfo{

    // 商户名称
    private String merchantName;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "SupplierInfoDomain{" +
                "merchantName='" + merchantName + '\'' +
                '}';
    }
}
