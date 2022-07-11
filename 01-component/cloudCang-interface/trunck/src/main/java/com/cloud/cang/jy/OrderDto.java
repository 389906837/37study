package com.cloud.cang.jy;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 创建订单Dto
 * @Author: yanlingfeng
 */
public class OrderDto extends SuperDto {
    //-------------------------------必填--------------------------------------
    /*  支付类型
      10=账户余额
      20=银行卡
      30=微信支付
      40=支付宝
      50=银联支付
      60=现金支付
      70=第三方支付
      80=其他*/
/*    private Integer ipayType;*/
    /* 来源客户端 10=微信
     20=支付宝
     30=APP */
    private Integer isourceClientType;
    /* 设备地址 */
    private String sdeviceAddress;
    /* 设备编号 */
    private String sdeviceCode;
    /* 设备ID */
    private String sdeviceId;
    /*设备名*/
    private String sdeviceName;
    /* 设备读写器序列号 */
    private String sreaderSerialNumber;
    /* 会员编号 */
    private String smemberCode;
    /* 会员ID */
    private String smemberId;
    /* 会员用户名（手机号） */
    private String smemberName;
    /* 商户编号 */
    private String smerchantCode;
    /* 商户ID */
    private String smerchantId;


    //-----------------------------------------------选填--------------------------------------------------
    /* 来源方式编号 */
    private Integer isourceWayCode;
    /* 订单来源方式 */
    private Integer isourceWay;
    /* 来源方式名称 */
    private String isourceWayName;
    //-------------------------------组装计算优惠值(传参不用填)--------------------------------------
    private BigDecimal ftotalCostAmount;//订单商品总成本

    private Integer ichargebackWay; //扣款方式(主动付款;商户代扣)

    private BigDecimal ftotalAmount;//订单总额

    private BigDecimal fdiscountAmount;//订单优惠总额

    private BigDecimal ffirstDiscountAmount;//订单首单优惠金额

    private BigDecimal fpromotionDiscountAmount;//订单促销优惠金额

    private BigDecimal fcouponDeductionAmount;//订单优惠券抵扣金额

    private BigDecimal factualPayAmount;//实付金额

    private BigDecimal fpointDiscountAmount;//订单积分优惠金额

    private BigDecimal fotherDiscountAmount;//订单其他优惠金额

    private String sacId;	/* 活动ID 多个，隔开  */

    private String sacCode;	/* 活动编号 多个，隔开 */

    private String susedCouponCode;	/* 券使用编号 */

    private String susedCouponId;	/* 券使用ID */

    @Override
    public String toString() {
        return "OrderDto{" +
                "isourceClientType=" + isourceClientType +
                ", sdeviceAddress='" + sdeviceAddress + '\'' +
                ", sdeviceCode='" + sdeviceCode + '\'' +
                ", sdeviceId='" + sdeviceId + '\'' +
                ", sdeviceName='" + sdeviceName + '\'' +
                ", sreaderSerialNumber='" + sreaderSerialNumber + '\'' +
                ", smemberCode='" + smemberCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                ", smemberName='" + smemberName + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", smerchantId='" + smerchantId + '\'' +
                ", isourceWayCode=" + isourceWayCode +
                ", isourceWay=" + isourceWay +
                ", isourceWayName='" + isourceWayName + '\'' +
                '}';
    }

    public String getSdeviceName() {
        return sdeviceName;
    }

    public void setSdeviceName(String sdeviceName) {
        this.sdeviceName = sdeviceName;
    }

    public BigDecimal getFtotalCostAmount() {
        return ftotalCostAmount;
    }

    public void setFtotalCostAmount(BigDecimal ftotalCostAmount) {
        this.ftotalCostAmount = ftotalCostAmount;
    }

    public Integer getIchargebackWay() {
        return ichargebackWay;
    }

    public void setIchargebackWay(Integer ichargebackWay) {
        this.ichargebackWay = ichargebackWay;
    }

    public BigDecimal getFtotalAmount() {
        return ftotalAmount;
    }

    public void setFtotalAmount(BigDecimal ftotalAmount) {
        this.ftotalAmount = ftotalAmount;
    }

    public BigDecimal getFdiscountAmount() {
        return fdiscountAmount;
    }

    public void setFdiscountAmount(BigDecimal fdiscountAmount) {
        this.fdiscountAmount = fdiscountAmount;
    }

    public BigDecimal getFfirstDiscountAmount() {
        return ffirstDiscountAmount;
    }

    public void setFfirstDiscountAmount(BigDecimal ffirstDiscountAmount) {
        this.ffirstDiscountAmount = ffirstDiscountAmount;
    }

    public String getSreaderSerialNumber() {
        return sreaderSerialNumber;
    }

    public void setSreaderSerialNumber(String sreaderSerialNumber) {
        this.sreaderSerialNumber = sreaderSerialNumber;
    }

    public BigDecimal getFpromotionDiscountAmount() {
        return fpromotionDiscountAmount;
    }

    public void setFpromotionDiscountAmount(BigDecimal fpromotionDiscountAmount) {
        this.fpromotionDiscountAmount = fpromotionDiscountAmount;
    }

    public BigDecimal getFcouponDeductionAmount() {
        return fcouponDeductionAmount;
    }

    public void setFcouponDeductionAmount(BigDecimal fcouponDeductionAmount) {
        this.fcouponDeductionAmount = fcouponDeductionAmount;
    }

    public BigDecimal getFactualPayAmount() {
        return factualPayAmount;
    }

    public void setFactualPayAmount(BigDecimal factualPayAmount) {
        this.factualPayAmount = factualPayAmount;
    }

    public BigDecimal getFpointDiscountAmount() {
        return fpointDiscountAmount;
    }

    public void setFpointDiscountAmount(BigDecimal fpointDiscountAmount) {
        this.fpointDiscountAmount = fpointDiscountAmount;
    }

    public BigDecimal getFotherDiscountAmount() {
        return fotherDiscountAmount;
    }

    public void setFotherDiscountAmount(BigDecimal fotherDiscountAmount) {
        this.fotherDiscountAmount = fotherDiscountAmount;
    }

    public String getSacId() {
        return sacId;
    }

    public void setSacId(String sacId) {
        this.sacId = sacId;
    }

    public String getSacCode() {
        return sacCode;
    }

    public void setSacCode(String sacCode) {
        this.sacCode = sacCode;
    }

    public String getSusedCouponCode() {
        return susedCouponCode;
    }

    public void setSusedCouponCode(String susedCouponCode) {
        this.susedCouponCode = susedCouponCode;
    }

    public String getSusedCouponId() {
        return susedCouponId;
    }

    public void setSusedCouponId(String susedCouponId) {
        this.susedCouponId = susedCouponId;
    }


    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }

    public Integer getIsourceClientType() {
        return isourceClientType;
    }

    public void setIsourceClientType(Integer isourceClientType) {
        this.isourceClientType = isourceClientType;
    }

    public Integer getIsourceWay() {
        return isourceWay;
    }

    public void setIsourceWay(Integer isourceWay) {
        this.isourceWay = isourceWay;
    }

    public Integer getIsourceWayCode() {
        return isourceWayCode;
    }

    public void setIsourceWayCode(Integer isourceWayCode) {
        this.isourceWayCode = isourceWayCode;
    }

    public String getIsourceWayName() {
        return isourceWayName;
    }

    public void setIsourceWayName(String isourceWayName) {
        this.isourceWayName = isourceWayName;
    }

    public String getSdeviceAddress() {
        return sdeviceAddress;
    }

    public void setSdeviceAddress(String sdeviceAddress) {
        this.sdeviceAddress = sdeviceAddress;
    }

    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    public String getSmemberCode() {
        return smemberCode;
    }

    public void setSmemberCode(String smemberCode) {
        this.smemberCode = smemberCode;
    }

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    public String getSmemberName() {
        return smemberName;
    }

    public void setSmemberName(String smemberName) {
        this.smemberName = smemberName;
    }

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getSmerchantId() {
        return smerchantId;
    }


}