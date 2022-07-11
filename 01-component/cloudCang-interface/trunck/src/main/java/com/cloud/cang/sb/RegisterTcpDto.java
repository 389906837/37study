package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

/**
 * Created by Alex on 2018/6/2.
 */
public class RegisterTcpDto extends SuperDto {
    private String deviceId;    // 设备ID

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
