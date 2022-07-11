package com.cloud.cang.core.sys.domain;

import com.cloud.cang.model.sys.RemindMessage;

/**
 * Created by yan on 2018/1/23.
 */
public class RemindMessageDomain extends RemindMessage {
    private  String orderStr;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
