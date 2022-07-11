package com.cloud.cang.pay;

/**
 * Created by YLF on 2019/11/22.
 */
public class DeductionPointDto {
    private String orderId;//订单Id
    private String deviceId;//设备Id
    private String userId;//用户iD
    private String mobile;//用户手机号
    private String sremark;//付款备注

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
}
