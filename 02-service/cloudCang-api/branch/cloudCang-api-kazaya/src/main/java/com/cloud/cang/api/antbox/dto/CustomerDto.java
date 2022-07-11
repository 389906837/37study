package com.cloud.cang.api.antbox.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物用户
 */
public class CustomerDto extends Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间（登录时间）
     */
    private Date loginTime;

    /**
     * 是否补货开门
     */
    private Boolean distribution;
    /**
     * 开门时间
     */
    private Date openTime;

    /**
     * 关门时间
     */
    private Date closeDoorTime;

    /**
     * 盘点结束时间
     */
    private Date inventoriedTime;
    /**
     * 设备ID
     */
    private String deviceId;

    private BoxOpenDoorSource boxOpenDoorSource;
    private String sessionId;

    public static CustomerDto copyFrom(CustomerDto orig) {
        if (orig == null)
            return null;

        CustomerDto dest = new CustomerDto();
        dest.loginTime = orig.loginTime;
        dest.openTime = orig.openTime;
        dest.distribution = orig.distribution;
        dest.boxOpenDoorSource = orig.boxOpenDoorSource;
        dest.id = orig.id;
        dest.sessionId = orig.sessionId;
        dest.deviceId = orig.deviceId;
        return dest;
    }

    private CustomerDto() {

    }

    public CustomerDto(String id) {
        this.id = id;
        this.loginTime = new Date();
    }

    public BoxOpenDoorSource getBoxOpenDoorSource() {
        return boxOpenDoorSource;
    }

    public void setBoxOpenDoorSource(BoxOpenDoorSource boxOpenDoorSource) {
        this.boxOpenDoorSource = boxOpenDoorSource;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void openDoor(Date openTime) {
        this.openTime = openTime;
    }

    public void closeDoor(Date closeDoorTime) {
        this.closeDoorTime = closeDoorTime;
    }

    public void markInventoried() {
        this.inventoriedTime = new Date();
    }

    public Date getOpenTime() {
        return openTime;
    }

    public Date getCloseDoorTime() {
        return closeDoorTime;
    }

    public Date getInventoriedTime() {
        return inventoriedTime;
    }

    public Boolean getDistribution() {
        return distribution;
    }

    public void setDistribution(Boolean distribution) {
        this.distribution = distribution;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
