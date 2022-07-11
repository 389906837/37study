package com.cloud.cang.pay;

import java.io.Serializable;


/**
 * @version 1.0
 * @Description: 微信支付分撤销订单
 * @Author: yanlingfeng
 * @Date: 2019/8/1 14:57
 */
public class CancelSmartretailOrdersDto implements Serializable {
    /*必填*/
    private String reason;//用户投诉	支持50个字符，按照字符计算 超过长度报错处理
    private String smerchantCode;//商户编号
    private String orderCode;//订单编号
    private String orderId;//订单Id


    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
