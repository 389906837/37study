package com.cloud.cang.pay;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @version 1.0
 * @Description: 免密支付返回参数
 * @Author: zhouhong
 * @Date: 2018/3/22 14:57
 */
public class FreePaymentResult implements Serializable {

    private Integer istatus;//订单状态
    private String tradeNo;//支付宝交易号
    private String buyerLogonId;//买家支付宝账号
    private BigDecimal totalAmount;//交易金额
    private BigDecimal receiptAmount;//实收金额
    private BigDecimal buyerPayAmount;//买家付款的金额
    private BigDecimal pointAmount;//使用集分宝付款的金额
    private BigDecimal invoiceAmount;//可给用户开具发票的金额
    private BigDecimal otherPayAmount;//第三方支付其他付款金额
    private Date gmtPayment;//交易完成时间

    private Map<String,Object> pushOrderParam;//vendstop 推送订单请求参数

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getBuyerLogonId() {
        return buyerLogonId;
    }

    public void setBuyerLogonId(String buyerLogonId) {
        this.buyerLogonId = buyerLogonId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(BigDecimal receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public BigDecimal getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(BigDecimal buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public BigDecimal getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(BigDecimal pointAmount) {
        this.pointAmount = pointAmount;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getOtherPayAmount() {
        return otherPayAmount;
    }

    public void setOtherPayAmount(BigDecimal otherPayAmount) {
        this.otherPayAmount = otherPayAmount;
    }

    public Date getGmtPayment() {
        return gmtPayment;
    }

    public void setGmtPayment(Date gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

    public Map<String, Object> getPushOrderParam() {
        return pushOrderParam;
    }

    public void setPushOrderParam(Map<String, Object> pushOrderParam) {
        this.pushOrderParam = pushOrderParam;
    }

    @Override
    public String toString() {
        return "FreePaymentResult{" +
                "istatus=" + istatus +
                ", tradeNo='" + tradeNo + '\'' +
                ", buyerLogonId='" + buyerLogonId + '\'' +
                ", totalAmount=" + totalAmount +
                ", receiptAmount=" + receiptAmount +
                ", buyerPayAmount=" + buyerPayAmount +
                ", pointAmount=" + pointAmount +
                ", invoiceAmount=" + invoiceAmount +
                ", otherPayAmount=" + otherPayAmount +
                ", gmtPayment=" + gmtPayment +
                '}';
    }
}
