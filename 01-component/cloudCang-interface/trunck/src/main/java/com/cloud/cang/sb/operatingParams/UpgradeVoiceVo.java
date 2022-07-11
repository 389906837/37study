package com.cloud.cang.sb.operatingParams;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @version 1.0
 * @ClassName UpgradeVoiceVo
 * @Description mgr后台操作设备--升级语音
 * @Author zengzexiong
 * @Date 2019年1月23日
 */
public class UpgradeVoiceVo extends SuperDto {

    private String Url;             //开门语音url
    private String voiceType;       //升级语音类型
    private String mainId;        // 升级记录主表ID（记录升级操作）

    private String type;    // 全部=10，部分=20
    private List<String> deviceIdList; // 设备ID
    private List<String> deviceCodeList; // 设备编号

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getVoiceType() {
        return voiceType;
    }

    public void setVoiceType(String voiceType) {
        this.voiceType = voiceType;
    }


    @Override
    public String toString() {
        return "UpgradeVoiceVo{" +
                "Url='" + Url + '\'' +
                ", voiceType='" + voiceType + '\'' +
                ", mainId='" + mainId + '\'' +
                ", type='" + type + '\'' +
                ", deviceIdList=" + deviceIdList +
                ", deviceCodeList=" + deviceCodeList +
                '}';
    }
}
