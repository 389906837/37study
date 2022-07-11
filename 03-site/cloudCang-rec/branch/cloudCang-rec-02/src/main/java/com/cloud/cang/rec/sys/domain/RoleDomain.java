package com.cloud.cang.rec.sys.domain;

import com.cloud.cang.model.sys.Role;

/**
 * @description: 用户角色 Domain
 * @author:Yanlingfeng
 * @time:2018-02-22 14:07:05
 * @version 1.0
 */
public class RoleDomain extends Role {

    private static final long serialVersionUID = 1L;

    private String orderStr;//排序字段
    private  String merchantName;//商户名

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
