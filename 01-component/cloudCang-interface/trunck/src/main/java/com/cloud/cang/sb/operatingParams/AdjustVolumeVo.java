package com.cloud.cang.sb.operatingParams;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @version 1.0
 * @ClassName AdjustVolumeVo
 * @Description mgr后台操作设备--调节音量大小
 * @Author zengzexiong
 * @Date 2019年1月17日09:41:00
 */
public class AdjustVolumeVo extends SuperDto {

    private String volumeValue;             // 音量值大小
    private String type;                    // 全部=10，部分=20
    private List<String> deviceIdList;      // 设备ID
    private List<String> deviceCodeList;    // 设备编号


    public String getVolumeValue() {
        return volumeValue;
    }

    public void setVolumeValue(String volumeValue) {
        this.volumeValue = volumeValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDeviceIdList() {
        return deviceIdList;
    }

    public void setDeviceIdList(List<String> deviceIdList) {
        this.deviceIdList = deviceIdList;
    }

    public List<String> getDeviceCodeList() {
        return deviceCodeList;
    }

    public void setDeviceCodeList(List<String> deviceCodeList) {
        this.deviceCodeList = deviceCodeList;
    }

    @Override
    public String toString() {
        return "AdjustVolumeVo{" +
                "volumeValue='" + volumeValue + '\'' +
                ", type='" + type + '\'' +
                ", deviceIdList=" + deviceIdList +
                ", deviceCodeList=" + deviceCodeList +
                '}';
    }
}
