package com.cloud.cang.model.om;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;

/**
 * 订单支付商户号管理(OM_ORDER_PAY)
 **/
public class OrderPay extends GenericEntity {

    private static final long serialVersionUID = 1L;

    /*主键*/
    private String id;

    /*主键*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /* 是否微信支付分订单 */
    private Integer iisWechatPayPoint;

    public Integer getIisWechatPayPoint() {
        return iisWechatPayPoint;
    }

    public void setIisWechatPayPoint(Integer iisWechatPayPoint) {
        this.iisWechatPayPoint = iisWechatPayPoint;
    }

    /* 付款类型 10=微信 20=支付宝  30 =积分抵扣*/
    private Integer ipayType;

    public Integer getIpayType() {
        return ipayType;
    }

    public void setIpayType(Integer ipayType) {
        this.ipayType = ipayType;
    }

    /* 状态 10=初始化 20=成功 30 失败 */
    private Integer istatus;

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    /* 付款编号 */
    private String scode;

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    /* 错误编号 */
    private String serrorCode;

    public String getSerrorCode() {
        return serrorCode;
    }

    public void setSerrorCode(String serrorCode) {
        this.serrorCode = serrorCode;
    }

    /* 错误信息 */
    private String serrorDesc;

    public String getSerrorDesc() {
        return serrorDesc;
    }

    public void setSerrorDesc(String serrorDesc) {
        this.serrorDesc = serrorDesc;
    }

    /* 订单编号 */
    private String sorderCode;

    public String getSorderCode() {
        return sorderCode;
    }

    public void setSorderCode(String sorderCode) {
        this.sorderCode = sorderCode;
    }

    /* 订单ID */
    private String sorderId;

    public String getSorderId() {
        return sorderId;
    }

    public void setSorderId(String sorderId) {
        this.sorderId = sorderId;
    }

    /* 付款时间 */
    private Date taddTime;

    public Date getTaddTime() {
        return taddTime;
    }

    public void setTaddTime(Date taddTime) {
        this.taddTime = taddTime;
    }

    /* 付款完成时间 */
    private Date tcompleteTime;

    public Date getTcompleteTime() {
        return tcompleteTime;
    }

    public void setTcompleteTime(Date tcompleteTime) {
        this.tcompleteTime = tcompleteTime;
    }

    /* 交易流水号 */
    private String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}