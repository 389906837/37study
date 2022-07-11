package com.cloud.cang.pay;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 免密支付请求参数
 * @Author: zhouhong
 * @Date: 2018/3/22 14:57
 */
public class FreePaymentDto implements Serializable {

    //========必填========
    private String smerchantCode;//收款商户
    private String smemberId;//会员Id
    private String smemberCode;//会员编号
    private String smemberName;//会员名称
    private String sorderId;//订单ID
    private String ssubject;//订单标题
    private Integer ipayWay;//支付类型中支付方式
    private Integer iisIgnoreStatus;//是否忽略状态 0否1是

    //=====可选========
    private String body;//订单描述
    private String sremark;//付款备注
    private String spayScene;//支付场景
    private String sip;//客户端IP
    private String userAuthToken;//vendstop 调用push order接口使用
    private String sessionID;//vendstop 调用push order接口使用
    private String apiKey;//vendstop 调用push order接口使用
    private String appDeviceId;//app手机设备ID, vendstop
    private String deviceCode;//售货机设备CODE, vendstop
    private String deviceId;//售货机设备ID, vendstop

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    public String getSmemberCode() {
        return smemberCode;
    }

    public void setSmemberCode(String smemberCode) {
        this.smemberCode = smemberCode;
    }

    public String getSmemberName() {
        return smemberName;
    }

    public void setSmemberName(String smemberName) {
        this.smemberName = smemberName;
    }

    public String getSorderId() {
        return sorderId;
    }

    public void setSorderId(String sorderId) {
        this.sorderId = sorderId;
    }

    public String getSpayScene() {
        return spayScene;
    }

    public void setSpayScene(String spayScene) {
        this.spayScene = spayScene;
    }

    public String getSsubject() {
        return ssubject;
    }

    public void setSsubject(String ssubject) {
        this.ssubject = ssubject;
    }

    public Integer getIpayWay() {
        return ipayWay;
    }

    public void setIpayWay(Integer ipayWay) {
        this.ipayWay = ipayWay;
    }

    public Integer getIisIgnoreStatus() {
        return iisIgnoreStatus;
    }

    public void setIisIgnoreStatus(Integer iisIgnoreStatus) {
        this.iisIgnoreStatus = iisIgnoreStatus;
    }

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public String getUserAuthToken() {
        return userAuthToken;
    }

    public void setUserAuthToken(String userAuthToken) {
        this.userAuthToken = userAuthToken;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAppDeviceId() {
        return appDeviceId;
    }

    public void setAppDeviceId(String appDeviceId) {
        this.appDeviceId = appDeviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "FreePaymentDto{" +
                "smerchantCode='" + smerchantCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                ", smemberCode='" + smemberCode + '\'' +
                ", smemberName='" + smemberName + '\'' +
                ", sorderId='" + sorderId + '\'' +
                ", ssubject='" + ssubject + '\'' +
                ", ipayWay=" + ipayWay +
                ", iisIgnoreStatus=" + iisIgnoreStatus +
                ", body='" + body + '\'' +
                ", sremark='" + sremark + '\'' +
                ", spayScene='" + spayScene + '\'' +
                ", sip='" + sip + '\'' +
                '}';
    }
}
