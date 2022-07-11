package com.cloud.cang.wap.rm.vo;

import java.util.Date;

/**
 * @version 1.0
 * @Description: 历史补货单
 * @Author: yanlingfeng
 * @Date: 2018/5/3016:37
 */
public class HistoryReplenishmentVo {
    private String id;
    private String scode;
    private Date treplenishmentTime;//补货日期
    private String deviceName;//设备名
    private String sdeviceAddress;//设备地址
    private Integer iactualShelves;//实际上架数
    private Integer iactualUnder;//实际下架数
    private String deviceId;//设备ID

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public Date getTreplenishmentTime() {
        return treplenishmentTime;
    }

    public void setTreplenishmentTime(Date treplenishmentTime) {
        this.treplenishmentTime = treplenishmentTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSdeviceAddress() {
        return sdeviceAddress;
    }

    public void setSdeviceAddress(String sdeviceAddress) {
        this.sdeviceAddress = sdeviceAddress;
    }

    public Integer getIactualShelves() {
        return iactualShelves;
    }

    public void setIactualShelves(Integer iactualShelves) {
        this.iactualShelves = iactualShelves;
    }

    public Integer getIactualUnder() {
        return iactualUnder;
    }

    public void setIactualUnder(Integer iactualUnder) {
        this.iactualUnder = iactualUnder;
    }
}