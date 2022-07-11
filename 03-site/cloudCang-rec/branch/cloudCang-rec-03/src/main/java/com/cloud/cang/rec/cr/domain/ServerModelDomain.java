package com.cloud.cang.rec.cr.domain;

import com.cloud.cang.model.cr.ServerModel;

/**
 * ServerModel 查询扩展类
 * Created by yan on 2018/9/19.
 */
public class ServerModelDomain extends ServerModel {
    private String orderStr;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
