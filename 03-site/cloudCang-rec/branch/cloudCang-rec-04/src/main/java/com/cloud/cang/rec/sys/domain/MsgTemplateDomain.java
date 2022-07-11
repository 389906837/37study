package com.cloud.cang.rec.sys.domain;


import com.cloud.cang.model.xx.MsgTemplate;

public class  MsgTemplateDomain extends MsgTemplate {

    private static final long serialVersionUID = 1L;
    private String orderStr;//排序字段

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
