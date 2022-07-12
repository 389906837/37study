package com.cloud.cang.mgr.ac.domain;

import com.cloud.cang.model.ac.CouponUserSend;


public class CouponUserSendDomain extends CouponUserSend {

    // 返回的是会员表里的是否实名认证字段
    private Integer iisVerified;

    private String orderStr;//排序字段

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Integer getIisVerified() {
        return iisVerified;
    }

    public void setIisVerified(Integer iisVerified) {
        this.iisVerified = iisVerified;
    }
}
