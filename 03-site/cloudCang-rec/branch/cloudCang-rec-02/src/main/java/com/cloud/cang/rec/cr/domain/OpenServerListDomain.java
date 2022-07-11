package com.cloud.cang.rec.cr.domain;

import com.cloud.cang.model.cr.OpenServerList;

/**
 * OpenServerList 查询扩展类
 * Created by yan on 2018/9/18.
 */
public class OpenServerListDomain extends OpenServerList {
    private String orderStr;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
