package com.cloud.cang.mgr.om.domain;

import com.cloud.cang.model.om.RefundCommodity;

/**
 * @version 1.0
 * @description: 退款订单详情 Domain
 * @author:Yanlingfeng
 * @time:2018-02-22 14:07:05
 */
public class RefundCommodityDomain extends RefundCommodity {
    private String orderStr;//排序字段

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
