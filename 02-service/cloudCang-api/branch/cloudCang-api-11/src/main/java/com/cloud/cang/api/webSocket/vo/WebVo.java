package com.cloud.cang.api.webSocket.vo;

/**
 * Created by Alex on 2018/3/27.
 */
public class WebVo {
    private String userId;
    private String deviceId;
    private Integer type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "WebVo{" +
                "userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", type=" + type +
                '}';
    }
}
