package com.cloud.cang.mgr.wz.domain;

import com.cloud.cang.model.wz.Advertis;

/**
 * @version 1.0
 * @Description: 广告区域列表返商户名称字段
 * @Author: ChangTanchang
 * @Date: 2018/4/12 9:24
 */
public class AdvertisDomain extends Advertis {

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
        return "AdvertisDomain{" +
                "merchantName='" + merchantName + '\'' +
                '}';
    }
}
