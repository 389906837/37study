package com.cloud.cang.mgr.xx.domain;


import com.cloud.cang.model.xx.SalesMsgMain;

public class SalesMsgMainDomain extends SalesMsgMain {

    // 生成序列号
    private static final long serialVersionUID = 1L;

    // 返回消息供应商名称字段
    private String supplierInfoSname;

    // 商户名称
    private String merchantName;

    public String getSupplierInfoSname() {
        return supplierInfoSname;
    }

    public void setSupplierInfoSname(String supplierInfoSname) {
        this.supplierInfoSname = supplierInfoSname;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "SalesMsgMainDomain{" +
                "supplierInfoSname='" + supplierInfoSname + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
