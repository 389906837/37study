package com.cloud.cang.mgr.om.domain;

import com.cloud.cang.model.om.OrderAuditCommodity;

/**
 * @version 1.0
 * @description: 异常订单商品 Domain
 * @author:Yanlingfeng
 * @time:2018-04-08 14:07:05
 */
public class OrderAuditCommodityDomain extends OrderAuditCommodity {
    private String orderStr;//排序字段

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
