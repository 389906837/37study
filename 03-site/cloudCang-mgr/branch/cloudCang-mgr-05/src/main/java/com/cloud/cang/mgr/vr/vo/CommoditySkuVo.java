package com.cloud.cang.mgr.vr.vo;

import com.cloud.cang.model.vr.CommoditySku;

/**
 * Created by Alex on 2018/3/7.
 */
public class CommoditySkuVo extends CommoditySku {

    private String smerchantId;//商户ID
    private String orderStr;//排序字段
    private String queryCondition;//查询条件

    public String getSmerchantId() {
        return smerchantId;
    }

    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    @Override
    public String toString() {
        return "CommoditySkuVo{" +
                "smerchantId='" + smerchantId + '\'' +
                "orderStr='" + orderStr + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                '}';
    }
}
