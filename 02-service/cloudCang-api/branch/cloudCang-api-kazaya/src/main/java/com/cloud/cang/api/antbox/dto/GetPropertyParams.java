package com.cloud.cang.api.antbox.dto;

import java.util.List;


public class GetPropertyParams {

    private long deviceId;

    private List<Integer> codes;

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public List<Integer> getCodes() {
        return codes;
    }

    public void setCodes(List<Integer> codes) {
        this.codes = codes;
    }

}
