package com.cloud.cang.common;

/**
 * @version 1.0
 * @Description: 基础VO
 * @Author: zhouhong
 * @Date: 2018/3/31 18:12
 */
public class BaseVo extends SuperDto {

    private String token;//设备与服务之间的信用桥梁
    private String userId;//操作用户ID
    private String deviceId;//操作设备ID
    private String methodType;//方法类型
    private String data;//方法之间的参数 JSON String
    private Integer openSource;//开门来源 10：人脸识别，20：手机扫码
    private String closeDoorIden;//关门唯一标识

    public String getCloseDoorIden() {
        return closeDoorIden;
    }

    public void setCloseDoorIden(String closeDoorIden) {
        this.closeDoorIden = closeDoorIden;
    }

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
        return "BaseVo{" +
                "token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", methodType='" + methodType + '\'' +
                ", data='" + data + '\'' +
                ", openSource=" + openSource +
                ", closeDoorIden='" + closeDoorIden + '\'' +
                '}';
    }
}
