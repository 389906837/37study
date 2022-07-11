package com.cloud.cang.rec.sh.vo;

import com.cloud.cang.generic.GenericEntity;

/**
 * @version 1.0
 * @Description: Vo
 * @Author: yanlingfeng
 * @Date: 2018/2/10 16:14
 */
public class SelectMerchantVo extends GenericEntity {
    private String merchantId;//商户ID
    private String merchantCode;//商户编号
    private String merchantName;//商户名称

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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
