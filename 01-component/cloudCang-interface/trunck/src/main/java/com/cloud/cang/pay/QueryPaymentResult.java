package com.cloud.cang.pay;

import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.domain.VoucherDetail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Description: 免密支付返回参数
 * @Author: zhouhong
 * @Date: 2018/3/22 14:57
 */
public class QueryPaymentResult implements Serializable {

    private String alipayStoreId;//支付宝店铺编号
    private String buyerLogonId;//买家支付宝账号
    private String buyerPayAmount;//买家实付金额
    private String buyerUserId;//买家在支付宝的用户id
    private String discountGoodsDetail;//单品券优惠的商品优惠信息
    private List<TradeFundBill> fundBillList;//交易支付使用的资金渠道
    private String industrySepcDetail;//行业特殊信息
    private String invoiceAmount;//用户支付的可开具发票的金额
    private String outTradeNo;//商家订单号
    private String pointAmount;//积分支付的金额
    private String receiptAmount;//实收金额
    private Date sendPayDate;//本次交易打款给卖家的时间
    private String storeId;//商户门店编号
    private String storeName;//请求交易支付中的商户店铺的名称
    private String terminalId;//商户机具终端编号
    private String totalAmount;//交易的订单金额
    private String tradeNo;//支付宝交易号
    private String tradeStatus;//交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
    private List<VoucherDetail> voucherDetailList;//交易所有优惠券信息

    public String getAlipayStoreId() {
        return alipayStoreId;
    }

    public void setAlipayStoreId(String alipayStoreId) {
        this.alipayStoreId = alipayStoreId;
    }

    public String getBuyerLogonId() {
        return buyerLogonId;
    }

    public void setBuyerLogonId(String buyerLogonId) {
        this.buyerLogonId = buyerLogonId;
    }

    public String getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(String buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public String getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(String buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public String getDiscountGoodsDetail() {
        return discountGoodsDetail;
    }

    public void setDiscountGoodsDetail(String discountGoodsDetail) {
        this.discountGoodsDetail = discountGoodsDetail;
    }

    public List<TradeFundBill> getFundBillList() {
        return fundBillList;
    }

    public void setFundBillList(List<TradeFundBill> fundBillList) {
        this.fundBillList = fundBillList;
    }

    public String getIndustrySepcDetail() {
        return industrySepcDetail;
    }

    public void setIndustrySepcDetail(String industrySepcDetail) {
        this.industrySepcDetail = industrySepcDetail;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(String pointAmount) {
        this.pointAmount = pointAmount;
    }

    public String getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(String receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public Date getSendPayDate() {
        return sendPayDate;
    }

    public void setSendPayDate(Date sendPayDate) {
        this.sendPayDate = sendPayDate;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public List<VoucherDetail> getVoucherDetailList() {
        return voucherDetailList;
    }

    public void setVoucherDetailList(List<VoucherDetail> voucherDetailList) {
        this.voucherDetailList = voucherDetailList;
    }

    @Override
    public String toString() {
        return "QueryPaymentResult{" +
                "alipayStoreId='" + alipayStoreId + '\'' +
                ", buyerLogonId='" + buyerLogonId + '\'' +
                ", buyerPayAmount='" + buyerPayAmount + '\'' +
                ", buyerUserId='" + buyerUserId + '\'' +
                ", discountGoodsDetail='" + discountGoodsDetail + '\'' +
                ", fundBillList=" + fundBillList +
                ", industrySepcDetail='" + industrySepcDetail + '\'' +
                ", invoiceAmount='" + invoiceAmount + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", pointAmount='" + pointAmount + '\'' +
                ", receiptAmount='" + receiptAmount + '\'' +
                ", sendPayDate=" + sendPayDate +
                ", storeId='" + storeId + '\'' +
                ", storeName='" + storeName + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", voucherDetailList=" + voucherDetailList +
                '}';
    }
}
