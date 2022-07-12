package com.cloud.cang.mgr.ac.domain;

import com.cloud.cang.model.ac.RecommendRecord;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 推荐好友列表返回Domain(商户名称字段)
 * @Author: ChangTanchang
 * @Date: 2018/5/5 15:55
 */
public class RecommendRecordDomain extends RecommendRecord {

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
        return "RecommendRecordDomain{" +
                "merchantName='" + merchantName + '\'' +
                '}';
    }
}
