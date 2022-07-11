package com.cloud.cang.rec.op.domain;

import com.cloud.cang.model.op.UserInfo;

/**
 * UserInfo 查询扩展类
 * Created by yan on 2018/9/28.
 */
public class UserInfoDomain extends UserInfo {
    private String condition;
    private String merchantName;//用户名
    private String orderStr; //排序

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }



    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
