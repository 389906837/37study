package com.cloud.cang.rec.sh.domain;

import com.cloud.cang.model.sh.DomainConf;

/**
 * @version 1.0
 * @Description: 域名domain
 * @Author: yanlingfeng
 * @Date: 2018/2/10 16:14
 */
public class DomainConfDomain extends DomainConf {
    private static final long serialVersionUID = 1L;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    private String orderStr;//排序字段
    private String merchantName; //商户名
    private String merchantCode; //商户编号
    private String condition;//搜索条件

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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
}
