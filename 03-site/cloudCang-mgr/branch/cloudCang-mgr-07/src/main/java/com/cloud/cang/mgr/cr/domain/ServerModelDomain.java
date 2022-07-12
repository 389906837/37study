package com.cloud.cang.mgr.cr.domain;

import com.cloud.cang.model.cr.ServerModel;

/**
 * ServerModel 查询扩展类
 * Created by yan on 2018/9/19.
 */
public class ServerModelDomain extends ServerModel {
    private String orderStr;
    private String merchantCode;//商户编号
    private String merchantId;//商户Id

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
