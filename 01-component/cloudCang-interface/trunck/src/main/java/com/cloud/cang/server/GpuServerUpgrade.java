package com.cloud.cang.server;

/**
 * 模型升级对象
 * Created by yan on 2018/11/8.
 */
public class GpuServerUpgrade {
    private String deviceIds;//选择部分时，需要设备ID，全部时不需要
    private String deviceCodes;//选择部分时，需要设备编号，全部时不需要
    private String url;//升级地址
    private String smodelCode;//模型编号
    private String version;// 版本
    private String irangeDevice;// 10全部，20部分
    private String dproduceDate;//定时时间
    private String timeType;// 10立即，20定时

    public String getSmodelCode() {
        return smodelCode;
    }

    public void setSmodelCode(String smodelCode) {
        this.smodelCode = smodelCode;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public String getDeviceCodes() {
        return deviceCodes;
    }

    public void setDeviceCodes(String deviceCodes) {
        this.deviceCodes = deviceCodes;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIrangeDevice() {
        return irangeDevice;
    }

    public void setIrangeDevice(String irangeDevice) {
        this.irangeDevice = irangeDevice;
    }

    public String getDproduceDate() {
        return dproduceDate;
    }

    public void setDproduceDate(String dproduceDate) {
        this.dproduceDate = dproduceDate;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }
}
