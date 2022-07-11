package com.cloud.cang.wap.ac.vo;

/**
 * Created by yan on 2018/5/30.
 */
public class DeviceVo {
    private String sname;//设备名
    private String scityName;//所属地区
    private String deviceAddress;//设备地址
    private String id;
    private String sareaName;
    private String sputAddress;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getSareaName() {
        return sareaName;
    }

    public void setSareaName(String sareaName) {
        this.sareaName = sareaName;
    }

    public String getSputAddress() {
        return sputAddress;
    }

    public void setSputAddress(String sputAddress) {
        this.sputAddress = sputAddress;
    }

    public String getScityName() {
        return scityName;
    }

    public void setScityName(String scityName) {
        this.scityName = scityName;
    }

    public String getDeviceAddress() {

        return this.scityName + this.sareaName + this.sputAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }
}
