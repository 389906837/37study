package com.cloud.cang.tec.om.vo;

import com.cloud.cang.model.om.OrderRecord;

/**
 * Created by yan on 2018/6/4.
 */
public class OrderVo extends OrderRecord {
    private String condition;//查询条件
    private String merchantName;//商户名
    private String orderTimeCondition;//订单时间查询条件
    private String orderTimeConditionMin;//订单时间查询条件(分钟)
    private String ichargebackWayCondition;//扣款方式查询条件


    @Override
    public String toString() {
        return "OrderVo{" +
                "condition='" + condition + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", orderTimeCondition='" + orderTimeCondition + '\'' +
                ", orderTimeConditionMin='" + orderTimeConditionMin + '\'' +
                ", ichargebackWayCondition='" + ichargebackWayCondition + '\'' +
                '}';
    }

    public String getIchargebackWayCondition() {
        return ichargebackWayCondition;
    }

    public void setIchargebackWayCondition(String ichargebackWayCondition) {
        this.ichargebackWayCondition = ichargebackWayCondition;
    }

    public String getOrderTimeConditionMin() {
        return orderTimeConditionMin;
    }

    public void setOrderTimeConditionMin(String orderTimeConditionMin) {
        this.orderTimeConditionMin = orderTimeConditionMin;
    }

    public String getOrderTimeCondition() {
        return orderTimeCondition;
    }

    public void setOrderTimeCondition(String orderTimeCondition) {
        this.orderTimeCondition = orderTimeCondition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
