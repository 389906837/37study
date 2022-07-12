package com.cloud.cang.wap.index.vo;

import java.util.Map;

/**
 * 补货成功开门返回类
 * Created by yan on 2018/12/27.
 */
public class ReOpenSuccessData {
    private String merchantCode;
    private String merchantName;
    private String deviceCode;
    private String deviceName;
    private String deviceAddress;
    private String srealName;
    private String openTime;
    private Map<String, Object> open;
    private String openHint;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getSrealName() {
        return srealName;
    }

    public void setSrealName(String srealName) {
        this.srealName = srealName;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Map<String, Object> getOpen() {
        return open;
    }

    public void setOpen(Map<String, Object> open) {
        this.open = open;
    }

    public String getOpenHint() {
        return openHint;
    }

    public void setOpenHint(String openHint) {
        this.openHint = openHint;
    }
}
