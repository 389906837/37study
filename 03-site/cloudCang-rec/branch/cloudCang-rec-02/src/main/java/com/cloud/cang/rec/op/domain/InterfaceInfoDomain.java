package com.cloud.cang.rec.op.domain;

import com.cloud.cang.model.op.InterfaceInfo;

/**
 * Created by yan on 2018/10/9.
 */
public class InterfaceInfoDomain extends InterfaceInfo {

    private String orderStr; //排序

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
