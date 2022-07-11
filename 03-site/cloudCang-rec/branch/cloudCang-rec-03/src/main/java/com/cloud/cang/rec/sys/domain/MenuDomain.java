package com.cloud.cang.rec.sys.domain;


import com.cloud.cang.model.sys.Menu;

public class MenuDomain extends Menu {

    private static final long serialVersionUID = 1L;
    private String orderStr;//排序字段

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
