package com.cloud.cang.tec.om.vo;

import java.math.BigDecimal;

/**
 * Created by yan on 2018/6/6.
 */
public class OfBillAlipayVo {
    private String alipayTradeNum;//支付宝交易号
    private String merchantOrderNum;//商户订单号
    private String businessType;//业务类型
    private String commodityName;//商品名称
    private String creatTime;//创建时间
    private String completeTime;//完成时间
    private String storeCode;//门店编号
    private String storeName;//门店名称
    private String operator;//操作员
    private String terminalNum;//终端号
    private String otherAccount;//对方账号
    private BigDecimal orderMoney;//订单金额
    private BigDecimal merchantCollection;//商家实收
    private BigDecimal alipayRedEnvelopes;//支付宝红包
    private BigDecimal collectionTreasure;//集分宝
    private BigDecimal alipayDiscount;//支付宝优惠
    private BigDecimal merchantDiscount;//商家优惠
    private BigDecimal couponWriteOffMoney;//券核销金额
    private String couponName;//券名称
    private BigDecimal merchantRedPacketConsumption;//商家红包消费金额
    private BigDecimal cardConsumptionMoney;//卡消费金额
    private String refundBatchNum;//退款批次号/请求号
    private BigDecimal serviceCharge;//服务费
    private BigDecimal splitProfit;//分润
    private String remark;//备注


    @Override
    public String toString() {
        return "OfBillAlipayVo{" +
                "alipayTradeNum='" + alipayTradeNum + '\'' +
                ", merchantOrderNum='" + merchantOrderNum + '\'' +
                ", businessType='" + businessType + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", creatTime='" + creatTime + '\'' +
                ", completeTime='" + completeTime + '\'' +
                ", storeCode='" + storeCode + '\'' +
                ", storeName='" + storeName + '\'' +
                ", operator='" + operator + '\'' +
                ", terminalNum='" + terminalNum + '\'' +
                ", otherAccount='" + otherAccount + '\'' +
                ", orderMoney=" + orderMoney +
                ", merchantCollection=" + merchantCollection +
                ", alipayRedEnvelopes=" + alipayRedEnvelopes +
                ", collectionTreasure=" + collectionTreasure +
                ", alipayDiscount=" + alipayDiscount +
                ", merchantDiscount=" + merchantDiscount +
                ", couponWriteOffMoney=" + couponWriteOffMoney +
                ", couponName='" + couponName + '\'' +
                ", merchantRedPacketConsumption=" + merchantRedPacketConsumption +
                ", cardConsumptionMoney=" + cardConsumptionMoney +
                ", refundBatchNum='" + refundBatchNum + '\'' +
                ", serviceCharge=" + serviceCharge +
                ", splitProfit=" + splitProfit +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getAlipayTradeNum() {
        return alipayTradeNum;
    }

    public void setAlipayTradeNum(String alipayTradeNum) {
        this.alipayTradeNum = alipayTradeNum;
    }

    public String getMerchantOrderNum() {
        return merchantOrderNum;
    }

    public void setMerchantOrderNum(String merchantOrderNum) {
        this.merchantOrderNum = merchantOrderNum;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTerminalNum() {
        return terminalNum;
    }

    public void setTerminalNum(String terminalNum) {
        this.terminalNum = terminalNum;
    }

    public String getOtherAccount() {
        return otherAccount;
    }

    public void setOtherAccount(String otherAccount) {
        this.otherAccount = otherAccount;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public BigDecimal getMerchantCollection() {
        return merchantCollection;
    }

    public void setMerchantCollection(BigDecimal merchantCollection) {
        this.merchantCollection = merchantCollection;
    }

    public BigDecimal getAlipayRedEnvelopes() {
        return alipayRedEnvelopes;
    }

    public void setAlipayRedEnvelopes(BigDecimal alipayRedEnvelopes) {
        this.alipayRedEnvelopes = alipayRedEnvelopes;
    }

    public BigDecimal getCollectionTreasure() {
        return collectionTreasure;
    }

    public void setCollectionTreasure(BigDecimal collectionTreasure) {
        this.collectionTreasure = collectionTreasure;
    }

    public BigDecimal getAlipayDiscount() {
        return alipayDiscount;
    }

    public void setAlipayDiscount(BigDecimal alipayDiscount) {
        this.alipayDiscount = alipayDiscount;
    }

    public BigDecimal getMerchantDiscount() {
        return merchantDiscount;
    }

    public void setMerchantDiscount(BigDecimal merchantDiscount) {
        this.merchantDiscount = merchantDiscount;
    }

    public BigDecimal getCouponWriteOffMoney() {
        return couponWriteOffMoney;
    }

    public void setCouponWriteOffMoney(BigDecimal couponWriteOffMoney) {
        this.couponWriteOffMoney = couponWriteOffMoney;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public BigDecimal getMerchantRedPacketConsumption() {
        return merchantRedPacketConsumption;
    }

    public void setMerchantRedPacketConsumption(BigDecimal merchantRedPacketConsumption) {
        this.merchantRedPacketConsumption = merchantRedPacketConsumption;
    }

    public BigDecimal getCardConsumptionMoney() {
        return cardConsumptionMoney;
    }

    public void setCardConsumptionMoney(BigDecimal cardConsumptionMoney) {
        this.cardConsumptionMoney = cardConsumptionMoney;
    }

    public String getRefundBatchNum() {
        return refundBatchNum;
    }

    public void setRefundBatchNum(String refundBatchNum) {
        this.refundBatchNum = refundBatchNum;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getSplitProfit() {
        return splitProfit;
    }

    public void setSplitProfit(BigDecimal splitProfit) {
        this.splitProfit = splitProfit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
