package com.cloud.cang.mgr.om.domain;

import com.cloud.cang.model.om.OrderCommodity;

/**
 * @description: 订单商品 Domain
 * @author:Yanlingfeng
 * @time:2018-02-22 14:07:05
 * @version 1.0
 */
public class OrderCommodityDomain extends OrderCommodity {
    private  String orderStr;//排序字段

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
