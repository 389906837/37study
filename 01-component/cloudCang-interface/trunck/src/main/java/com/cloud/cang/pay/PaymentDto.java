package com.cloud.cang.pay;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 免密支付订单查询、撤销、关闭参数 微信支付宝
 * @Author: zhouhong
 * @Date: 2018/3/22 14:57
 */
public class PaymentDto implements Serializable {

    //========必填========
    private String smerchantCode;//收款商户
    private String outTradeNo;//商户订单编号 必填


    //=====可选========
    private String tradeNo;//微信/支付宝交易号
    private String operatorId;//商户自定义的的操作员ID

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "smerchantCode='" + smerchantCode + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", operatorId='" + operatorId + '\'' +
                '}';
    }
}
