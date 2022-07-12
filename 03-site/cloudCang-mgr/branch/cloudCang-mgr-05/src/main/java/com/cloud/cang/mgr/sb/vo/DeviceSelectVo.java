package com.cloud.cang.mgr.sb.vo;


import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @ClassName DeviceSelectVo
 * @Description 选择设备
 * @Author zhouhong
 * @Date 2018年1月24日21:10:20
 */
public class DeviceSelectVo extends SuperDto {

    private String deviceIds;//设备ID
    private String deviceCodes;//设备编号
    private String sregionCode;//运营位编号
    private Integer irangeDevice;//选择设备类型 10 全部 20 部分
    private Integer newsType;//资讯类型 10 广告 20 系统公告
    private String advIds;//选择广告ID

    public String getSregionCode() {
        return sregionCode;
    }

    public void setSregionCode(String sregionCode) {
        this.sregionCode = sregionCode;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String getDeviceCodes() {
        return deviceCodes;
    }

    public void setDeviceCodes(String deviceCodes) {
        this.deviceCodes = deviceCodes;
    }

    public Integer getIrangeDevice() {
        return irangeDevice;
    }

    public void setIrangeDevice(Integer irangeDevice) {
        this.irangeDevice = irangeDevice;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    public String getAdvIds() {
        return advIds;
    }

    public void setAdvIds(String advIds) {
        this.advIds = advIds;
    }

    @Override
    public String toString() {
        return "DeviceSelectVo{" +
                "deviceIds='" + deviceIds + '\'' +
                ", deviceCodes='" + deviceCodes + '\'' +
                ", sregionCode='" + sregionCode + '\'' +
                ", irangeDevice=" + irangeDevice +
                ", newsType=" + newsType +
                ", advIds='" + advIds + '\'' +
                '}';
    }
}
