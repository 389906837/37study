package com.cloud.cang.mgr.sb.domain;

import com.cloud.cang.generic.GenericEntity;
import com.cloud.cang.model.sb.DeviceInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * 选择设备返回domain
 */
public class SelectDeviceDomain extends GenericEntity {

    private String deviceId;//设备ID
    private String deviceCode;//设备编号
    private String deviceName;//设备名称
    private String address;//地址
    private String groupName;//设备分组

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "SelectDeviceDomain{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
