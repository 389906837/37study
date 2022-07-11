package com.cloud.cang.model.sq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

import com.cloud.cang.generic.GenericEntity;

/**
 * 付款申请(SQ_PAY_APPLY)
 **/
public class PayApply extends GenericEntity {

    private static final long serialVersionUID = 1L;

    /*ID*/
    private String id;

    /*ID*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /* 订单实付总额 */
    private BigDecimal factualPayAmount;

    public BigDecimal getFactualPayAmount() {
        return factualPayAmount;
    }

    public void setFactualPayAmount(BigDecimal factualPayAmount) {
        this.factualPayAmount = factualPayAmount;
    }

    /* 实收金额 */
    private BigDecimal factualReceiveAmount;

    public BigDecimal getFactualReceiveAmount() {
        return factualReceiveAmount;
    }

    public void setFactualReceiveAmount(BigDecimal factualReceiveAmount) {
        this.factualReceiveAmount = factualReceiveAmount;
    }

    /* 用户支付金额 */
    private BigDecimal fbuyerPayAmount;

    public BigDecimal getFbuyerPayAmount() {
        return fbuyerPayAmount;
    }

    public void setFbuyerPayAmount(BigDecimal fbuyerPayAmount) {
        this.fbuyerPayAmount = fbuyerPayAmount;
    }

    /* 订单优惠券抵扣金额 */
    private BigDecimal fcouponDeductionAmount;

    public BigDecimal getFcouponDeductionAmount() {
        return fcouponDeductionAmount;
    }

    public void setFcouponDeductionAmount(BigDecimal fcouponDeductionAmount) {
        this.fcouponDeductionAmount = fcouponDeductionAmount;
    }

    /* 订单优惠总额 */
    private BigDecimal fdiscountAmount;

    public BigDecimal getFdiscountAmount() {
        return fdiscountAmount;
    }

    public void setFdiscountAmount(BigDecimal fdiscountAmount) {
        this.fdiscountAmount = fdiscountAmount;
    }

    /* 订单首单优惠金额 */
    private BigDecimal ffirstDiscountAmount;

    public BigDecimal getFfirstDiscountAmount() {
        return ffirstDiscountAmount;
    }

    public void setFfirstDiscountAmount(BigDecimal ffirstDiscountAmount) {
        this.ffirstDiscountAmount = ffirstDiscountAmount;
    }

    /* 可开发票的金额 */
    private BigDecimal finvoiceAmount;

    public BigDecimal getFinvoiceAmount() {
        return finvoiceAmount;
    }

    public void setFinvoiceAmount(BigDecimal finvoiceAmount) {
        this.finvoiceAmount = finvoiceAmount;
    }

    /* 订单其他优惠金额 */
    private BigDecimal fotherDiscountAmount;

    public BigDecimal getFotherDiscountAmount() {
        return fotherDiscountAmount;
    }

    public void setFotherDiscountAmount(BigDecimal fotherDiscountAmount) {
        this.fotherDiscountAmount = fotherDiscountAmount;
    }

    /* 其他付款金额 */
    private BigDecimal fotherPayAmount;

    public BigDecimal getFotherPayAmount() {
        return fotherPayAmount;
    }

    public void setFotherPayAmount(BigDecimal fotherPayAmount) {
        this.fotherPayAmount = fotherPayAmount;
    }

    /* 集分宝付款金额 */
    private BigDecimal fpointAmount;

    public BigDecimal getFpointAmount() {
        return fpointAmount;
    }

    public void setFpointAmount(BigDecimal fpointAmount) {
        this.fpointAmount = fpointAmount;
    }

    /* 订单积分优惠金额 */
    private BigDecimal fpointDiscountAmount;

    public BigDecimal getFpointDiscountAmount() {
        return fpointDiscountAmount;
    }

    public void setFpointDiscountAmount(BigDecimal fpointDiscountAmount) {
        this.fpointDiscountAmount = fpointDiscountAmount;
    }

    /* 订单促销优惠金额 */
    private BigDecimal fpromotionDiscountAmount;

    public BigDecimal getFpromotionDiscountAmount() {
        return fpromotionDiscountAmount;
    }

    public void setFpromotionDiscountAmount(BigDecimal fpromotionDiscountAmount) {
        this.fpromotionDiscountAmount = fpromotionDiscountAmount;
    }

    /* 订单总额 */
    private BigDecimal ftotalAmount;

    public BigDecimal getFtotalAmount() {
        return ftotalAmount;
    }

    public void setFtotalAmount(BigDecimal ftotalAmount) {
        this.ftotalAmount = ftotalAmount;
    }

    /* 支付关闭状态0 否 1是 */
    private Integer icloseStatus;

    public Integer getIcloseStatus() {
        return icloseStatus;
    }

    public void setIcloseStatus(Integer icloseStatus) {
        this.icloseStatus = icloseStatus;
    }

    /* 来源请求编号/付款订单编号 */
    private String iorignTradeCode;

    public String getIorignTradeCode() {
        return iorignTradeCode;
    }

    public void setIorignTradeCode(String iorignTradeCode) {
        this.iorignTradeCode = iorignTradeCode;
    }

    /* 来源请求ID/付款订单ID */
    private String iorignTradeId;

    public String getIorignTradeId() {
        return iorignTradeId;
    }

    public void setIorignTradeId(String iorignTradeId) {
        this.iorignTradeId = iorignTradeId;
    }

    /* 支付类型
            10=账户余额
            20=银行卡
            30=微信支付
            40=支付宝
            50=银联支付
            60=第三方支付
            70=其他 */
    private Integer ipayType;

    public Integer getIpayType() {
        return ipayType;
    }

    public void setIpayType(Integer ipayType) {
        this.ipayType = ipayType;
    }

    /* 支付类型中支付方式
            10：公众号支付
            20：H5支付
            30：扫码支付
            40：APP支付
            50：刷卡支付
            60：小程序支付
            70：代扣
             80： 微信支付分代扣*/
    private Integer ipayWay;

    public Integer getIpayWay() {
        return ipayWay;
    }

    public void setIpayWay(Integer ipayWay) {
        this.ipayWay = ipayWay;
    }

    /* 抵扣积分 */
    private BigDecimal ipoint;

    public BigDecimal getIpoint() {
        return ipoint;
    }

    public void setIpoint(BigDecimal ipoint) {
        this.ipoint = ipoint;
    }

    /* 申请状态
            10=未支付
            20=交易成功
            30=交易异常
            40=支付等待
            50=交易撤销 */
    private Integer istatus;

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    /* 版本号 */
    private Integer iversion;

    public Integer getIversion() {
        return iversion;
    }

    public void setIversion(Integer iversion) {
        this.iversion = iversion;
    }

    /* 操作人(用户名) */
    private String sadduserId;

    public String getSadduserId() {
        return sadduserId;
    }

    public void setSadduserId(String sadduserId) {
        this.sadduserId = sadduserId;
    }

    /* 关闭支付宝交易号 */
    private String scloseTradeNo;

    public String getScloseTradeNo() {
        return scloseTradeNo;
    }

    public void setScloseTradeNo(String scloseTradeNo) {
        this.scloseTradeNo = scloseTradeNo;
    }

    /* 币种 */
    private String scurrency;

    public String getScurrency() {
        return scurrency;
    }

    public void setScurrency(String scurrency) {
        this.scurrency = scurrency;
    }

    /* 会员ID */
    private String smemberId;

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    /* 会员用户名（手机号） */
    private String smemberName;

    public String getSmemberName() {
        return smemberName;
    }

    public void setSmemberName(String smemberName) {
        this.smemberName = smemberName;
    }

    /* 会员编号 */
    private String smemberNo;

    public String getSmemberNo() {
        return smemberNo;
    }

    public void setSmemberNo(String smemberNo) {
        this.smemberNo = smemberNo;
    }

    /* 交易流水号 */
    private String spaySerialNumber;

    public String getSpaySerialNumber() {
        return spaySerialNumber;
    }

    public void setSpaySerialNumber(String spaySerialNumber) {
        this.spaySerialNumber = spaySerialNumber;
    }

    /* 备注 */
    private String sremark;

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    /* 创建时间 */
    private Date taddTime;

    public Date getTaddTime() {
        return taddTime;
    }

    public void setTaddTime(Date taddTime) {
        this.taddTime = taddTime;
    }

    /* 订单关闭时间 */
    private Date tcloseTime;

    public Date getTcloseTime() {
        return tcloseTime;
    }

    public void setTcloseTime(Date tcloseTime) {
        this.tcloseTime = tcloseTime;
    }

    /* 交易完成时间 */
    private Date tfinishDatetime;

    public Date getTfinishDatetime() {
        return tfinishDatetime;
    }

    public void setTfinishDatetime(Date tfinishDatetime) {
        this.tfinishDatetime = tfinishDatetime;
    }

    /* 订单时间 */
    private Date tordertime;

    public Date getTordertime() {
        return tordertime;
    }

    public void setTordertime(Date tordertime) {
        this.tordertime = tordertime;
    }


}