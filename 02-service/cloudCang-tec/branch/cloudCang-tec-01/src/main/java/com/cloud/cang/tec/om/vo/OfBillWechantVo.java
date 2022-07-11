package com.cloud.cang.tec.om.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yan on 2018/6/5.
 *  交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,
    企业红包金额,微信退款单号,商户退款单号,退款金额,企业红包退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率
 */
public class OfBillWechantVo {
    private String tradingTime;//交易时间
    private String publicAccount;//公众账号ID
    private  String merchantNumber;//商户号
    private String subMerchantNumber;//子商户号
    private String deviceNumber;//设备号
    private String wechantOrderNumber;//微信订单号
    private String merchantOrderNumber;//商户订单号
    private String userIdentity;//用户标识
    private String transactionType;// 交易类型
    private String transactionStatus;//交易状态
    private String payBank;//付款银行
    private String scurrency;//货币种类
    private BigDecimal totalMoney;//总金额
    private  Double enterpriseEnvelopesMoney;//企业红包金额
    private String wechantRefundNumber;//微信退款单号
    private String merchantRefundNumber;//商户退款单号
    private BigDecimal refundMoney;//退款金额
    private Double enterpriseEnvelopesRefundMoney;//企业红包退款金额
    private String refundType;//退款类型
    private String  refundStatus;//退款状态
    private String commodityName;//商品名称
    private String merchantDataPacket;//商户数据包
    private Double serviceCharge;//手续费
    private String taxation;//税费


    @Override
    public String toString() {
        return "OfBillWechantVo{" +
                "tradingTime=" + tradingTime +
                ", publicAccount='" + publicAccount + '\'' +
                ", merchantNumber='" + merchantNumber + '\'' +
                ", subMerchantNumber='" + subMerchantNumber + '\'' +
                ", deviceNumber='" + deviceNumber + '\'' +
                ", wechantOrderNumber='" + wechantOrderNumber + '\'' +
                ", merchantOrderNumber='" + merchantOrderNumber + '\'' +
                ", userIdentity='" + userIdentity + '\'' +
                ", transactionType=" + transactionType +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", payBank='" + payBank + '\'' +
                ", scurrency='" + scurrency + '\'' +
                ", totalMoney=" + totalMoney +
                ", enterpriseEnvelopesMoney=" + enterpriseEnvelopesMoney +
                ", wechantRefundNumber='" + wechantRefundNumber + '\'' +
                ", merchantRefundNumber='" + merchantRefundNumber + '\'' +
                ", refundMoney=" + refundMoney +
                ", enterpriseEnvelopesRefundMoney=" + enterpriseEnvelopesRefundMoney +
                ", refundType=" + refundType +
                ", refundStatus='" + refundStatus + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", merchantDataPacket='" + merchantDataPacket + '\'' +
                ", serviceCharge=" + serviceCharge +
                ", taxation='" + taxation + '\'' +
                '}';
    }

    public String getTradingTime() {
        return tradingTime;
    }

    public void setTradingTime(String tradingTime) {
        this.tradingTime = tradingTime;
    }

    public String getPublicAccount() {
        return publicAccount;
    }

    public void setPublicAccount(String publicAccount) {
        this.publicAccount = publicAccount;
    }

    public String getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public String getSubMerchantNumber() {
        return subMerchantNumber;
    }

    public void setSubMerchantNumber(String subMerchantNumber) {
        this.subMerchantNumber = subMerchantNumber;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getWechantOrderNumber() {
        return wechantOrderNumber;
    }

    public void setWechantOrderNumber(String wechantOrderNumber) {
        this.wechantOrderNumber = wechantOrderNumber;
    }

    public String getMerchantOrderNumber() {
        return merchantOrderNumber;
    }

    public void setMerchantOrderNumber(String merchantOrderNumber) {
        this.merchantOrderNumber = merchantOrderNumber;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getPayBank() {
        return payBank;
    }

    public void setPayBank(String payBank) {
        this.payBank = payBank;
    }

    public String getScurrency() {
        return scurrency;
    }

    public void setScurrency(String scurrency) {
        this.scurrency = scurrency;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Double getEnterpriseEnvelopesMoney() {
        return enterpriseEnvelopesMoney;
    }

    public void setEnterpriseEnvelopesMoney(Double enterpriseEnvelopesMoney) {
        this.enterpriseEnvelopesMoney = enterpriseEnvelopesMoney;
    }

    public String getWechantRefundNumber() {
        return wechantRefundNumber;
    }

    public void setWechantRefundNumber(String wechantRefundNumber) {
        this.wechantRefundNumber = wechantRefundNumber;
    }

    public String getMerchantRefundNumber() {
        return merchantRefundNumber;
    }

    public void setMerchantRefundNumber(String merchantRefundNumber) {
        this.merchantRefundNumber = merchantRefundNumber;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Double getEnterpriseEnvelopesRefundMoney() {
        return enterpriseEnvelopesRefundMoney;
    }

    public void setEnterpriseEnvelopesRefundMoney(Double enterpriseEnvelopesRefundMoney) {
        this.enterpriseEnvelopesRefundMoney = enterpriseEnvelopesRefundMoney;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getMerchantDataPacket() {
        return merchantDataPacket;
    }

    public void setMerchantDataPacket(String merchantDataPacket) {
        this.merchantDataPacket = merchantDataPacket;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getTaxation() {
        return taxation;
    }

    public void setTaxation(String taxation) {
        this.taxation = taxation;
    }
}
