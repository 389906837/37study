package com.cloud.cang.mgr.sp.vo;

import com.cloud.cang.model.sp.CommodityBatch;

/**
 * Created by Alex on 2018/2/22.
 */
public class CommodityBatchVo extends CommodityBatch {
    private String merchantName;            /* 商户名称 */
    private String commodityName;            /* 商品名称 */
    private String queryCondition;          /* 查询条件 */
    private String orderStr;//排序字段

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    @Override
    public String toString() {
        return "CommodityBatchVo{" +
                "merchantName='" + merchantName + '\'' +
                "commodityName='" + commodityName + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                ", orderStr='" + orderStr + '\'' +
                '}';
    }
}
