package com.cloud.cang.vendstop.dto;

/**
 * @program: 37cang
 * @description: 推送订单返回
 * @author: qzg
 * @create: 2019-08-08 14:41
 **/
public class PushOrderResponseDto extends VendstopDto {
    private boolean paymentSuccess;
    private String transactionId;

    public boolean getPaymentSuccess() {
        return paymentSuccess;
    }

    public void setPaymentSuccess(boolean paymentSuccess) {
        this.paymentSuccess = paymentSuccess;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
