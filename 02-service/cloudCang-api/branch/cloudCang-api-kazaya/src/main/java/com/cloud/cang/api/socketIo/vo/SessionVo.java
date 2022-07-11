package com.cloud.cang.api.socketIo.vo;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 用户信息
 * @Author: zhouhong
 * @Date: 2018/3/30 11:23
 */
public class SessionVo implements Serializable {
    private String deviceId;//设备ID
    private String deviceCode;//设备编号
    private String userId;//用户Id
    private String userCode;//用户编号
    private String userName;//用户名
    private Integer isourceClientType;//来源客户端类型 10 微信 20 支付宝 30 APP
    private Integer types;//10 开门 20 补货开门
    private String sip;//客户IP
    private String clientId;//客户端连接Id

    private String phoneNumber;//app用户手机号
    private String app_apiKey;//调用vendStop server需要
    private String app_userAuthToken;//app用户token
    private String app_sessionId;//
    private String thirdUserId;//第三方用户id

    public SessionVo() {
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public Integer getIsourceClientType() {
        return isourceClientType;
    }

    public void setIsourceClientType(Integer isourceClientType) {
        this.isourceClientType = isourceClientType;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getApp_apiKey() {
        return app_apiKey;
    }

    public void setApp_apiKey(String app_apiKey) {
        this.app_apiKey = app_apiKey;
    }

    public String getApp_userAuthToken() {
        return app_userAuthToken;
    }

    public void setApp_userAuthToken(String app_userAuthToken) {
        this.app_userAuthToken = app_userAuthToken;
    }

    public String getApp_sessionId() {
        return app_sessionId;
    }

    public void setApp_sessionId(String app_sessionId) {
        this.app_sessionId = app_sessionId;
    }

    public String getThirdUserId() {
        return thirdUserId;
    }

    public void setThirdUserId(String thirdUserId) {
        this.thirdUserId = thirdUserId;
    }

    @Override
    public String toString() {
        return "SessionVo{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", userId='" + userId + '\'' +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", isourceClientType=" + isourceClientType +
                ", types=" + types +
                ", sip='" + sip + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
