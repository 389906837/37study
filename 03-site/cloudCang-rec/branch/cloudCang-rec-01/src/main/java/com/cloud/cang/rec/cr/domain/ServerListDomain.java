package com.cloud.cang.rec.cr.domain;

import com.cloud.cang.model.cr.ServerList;

/**
 * ServerList 查询扩展类
 * Created by yan on 2018/9/19.
 */
public class ServerListDomain extends ServerList {
    private String orderStr;
    private String statusCondition;//状态

    public String getStatusCondition() {
        return statusCondition;
    }

    public void setStatusCondition(String statusCondition) {
        this.statusCondition = statusCondition;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
