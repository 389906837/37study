package com.cloud.cang.mgr.ac.domain;

import com.cloud.cang.model.ac.CouponBatch;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 批量发券列表返回Domain(商户名称字段)
 * @Author: ChangTanchang
 * @Date: 2018/5/5 15:55
 */
public class CouponBatchDomain extends CouponBatch{

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
        return "CouponBatchDomain{" +
                "merchantName='" + merchantName + '\'' +
                '}';
    }
}
