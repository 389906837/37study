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
    // 运营区域名称
    private String sregionName;

    private Integer istatusTmp;//临时状态

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
                ", sregionName='" + sregionName + '\'' +
                ", istatusTmp=" + istatusTmp +
                '}';
    }

    public String getSregionName() {
        return sregionName;
    }

    public void setSregionName(String sregionName) {
        this.sregionName = sregionName;
    }

    public Integer getIstatusTmp() {
        return istatusTmp;
    }

    public void setIstatusTmp(Integer istatusTmp) {
        this.istatusTmp = istatusTmp;
    }
}
