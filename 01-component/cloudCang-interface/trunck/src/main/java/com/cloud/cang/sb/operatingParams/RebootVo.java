package com.cloud.cang.sb.operatingParams;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @version 1.0
 * @ClassName RebootVo
 * @Description mgr后台操作设备--重启
 * @Author zengzexiong
 * @Date 2019年1月24日14:34:35
 */
public class RebootVo extends SuperDto {

    private String type;    // 全部=10，部分=20
    private List<String> deviceIdList; // 设备ID
    private List<String> deviceCodeList; // 设备编号

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
        return "RebootVo{" +
                "type='" + type + '\'' +
                ", deviceIdList=" + deviceIdList +
                ", deviceCodeList=" + deviceCodeList +
                '}';
    }
}
