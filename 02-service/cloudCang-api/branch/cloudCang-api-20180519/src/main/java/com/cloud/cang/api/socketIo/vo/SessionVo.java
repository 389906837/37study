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
                '}';
    }
}
