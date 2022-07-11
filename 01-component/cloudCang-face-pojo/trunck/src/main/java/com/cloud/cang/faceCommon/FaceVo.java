package com.cloud.cang.faceCommon;

/**
 * @version 1.0
 * @Description: 基础VO
 * @Author: zengzexiong
 * @Date: 2018年7月25日10:16:01
 */
public class FaceVo extends SuperDto {

    private String token;//设备与服务之间的信用桥梁
    private String userId;//操作用户ID
    private String deviceId;//操作设备ID
    private String methodType;//方法类型
    private String data;//方法之间的参数 JSON String
    private Integer openSource = 0;//开门来源 10：手机扫码，20：人脸识别设备

    public Integer getOpenSource() {
        return openSource;
    }

    public void setOpenSource(Integer openSource) {
        this.openSource = openSource;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FaceVo{" +
                "token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", methodType='" + methodType + '\'' +
                ", data='" + data + '\'' +
                ", openSource='" + openSource + '\'' +
                '}';
    }
}
