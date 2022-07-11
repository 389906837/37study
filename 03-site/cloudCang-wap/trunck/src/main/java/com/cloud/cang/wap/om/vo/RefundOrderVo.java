package com.cloud.cang.wap.om.vo;

import com.cloud.cang.model.om.OrderRecord;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Description: 退款订单申请Vo
 * @Author: zhouhong
 * @Date: 2018/4/23 18:53
 */
public class RefundOrderVo implements Serializable {

    private String orderCode;//订单编号
    private String[] commoditieIds; //退款商品明细ID
    private String refundReason;//退款原因
    private OrderRecord record;//退款订单

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String[] getCommoditieIds() {
        return commoditieIds;
    }

    public void setCommoditieIds(String[] commoditieIds) {
        this.commoditieIds = commoditieIds;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public OrderRecord getRecord() {
        return record;
    }

    public void setRecord(OrderRecord record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "RefundOrderVo{" +
                "orderCode='" + orderCode + '\'' +
                ", commoditieIds=" + Arrays.toString(commoditieIds) +
                ", refundReason='" + refundReason + '\'' +
                ", record=" + record +
                '}';
    }
}
