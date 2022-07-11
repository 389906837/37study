package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * 更新广告Dto
 * Created by YLF
 * on 2019/7/31.
 */
public class UpdateDeviceAdvertisDto extends SuperDto {
    private static final long serialVersionUID = -8138082486960651840L;
    //======必填=========
    private List<String> deviceCodes;//设备集合
    private String sregionCode;//运营区域编号

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getDeviceCodes() {
        return deviceCodes;
    }

    public void setDeviceCodes(List<String> deviceCodes) {
        this.deviceCodes = deviceCodes;
    }

    public String getSregionCode() {
        return sregionCode;
    }

    public void setSregionCode(String sregionCode) {
        this.sregionCode = sregionCode;
    }

    @Override
    public String toString() {
        return "UpdateDeviceAdvertisDto{" +
                "deviceCodes=" + deviceCodes +
                ", sregionCode='" + sregionCode + '\'' +
                '}';
    }
}
