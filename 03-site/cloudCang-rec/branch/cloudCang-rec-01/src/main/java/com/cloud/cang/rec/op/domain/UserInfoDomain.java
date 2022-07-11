package com.cloud.cang.rec.op.domain;

import com.cloud.cang.model.op.UserInfo;

/**
 * UserInfo 查询扩展类
 * Created by yan on 2018/9/28.
 */
public class UserInfoDomain extends UserInfo {
    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    private String orderStr; //排序

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
