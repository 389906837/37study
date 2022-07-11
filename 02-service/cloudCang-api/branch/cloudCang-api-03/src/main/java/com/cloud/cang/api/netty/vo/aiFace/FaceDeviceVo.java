package com.cloud.cang.api.netty.vo.aiFace;

import com.cloud.cang.generic.GenericEntity;

/**
 * @version 1.0
 * @ClassName: FaceDeviceVo
 * @Description: AI设备基础信息
 * @Author: zengzexiong
 * @Date: 2018年7月30日11:55:06
 */
public class FaceDeviceVo extends GenericEntity {

    String aiId;                    // AI设备ID
    String aiCode;                  //  AI设备编号
    String deviceId;                // 对应售货机设备ID
    String deviceCode;              // 对应售货机设备编号
    String ip;                      // IP地址
    Integer openDoorPayType;        // 开门用户支付方式 10 ：微信支付 20 ：支付宝 30：其他
    String openDoorUserId;          // 开门用户ID

    public String getAiId() {
        return aiId;
    }

    public void setAiId(String aiId) {
        this.aiId = aiId;
    }

    public String getAiCode() {
        return aiCode;
    }

    public void setAiCode(String aiCode) {
        this.aiCode = aiCode;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getOpenDoorPayType() {
        return openDoorPayType;
    }

    public void setOpenDoorPayType(Integer openDoorPayType) {
        this.openDoorPayType = openDoorPayType;
    }

    public String getOpenDoorUserId() {
        return openDoorUserId;
    }

    public void setOpenDoorUserId(String openDoorUserId) {
        this.openDoorUserId = openDoorUserId;
    }

    @Override
    public String toString() {
        return "FaceDeviceVo{" +
                "aiId='" + aiId + '\'' +
                ", aiCode='" + aiCode + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", ip='" + ip + '\'' +
                ", openDoorPayType='" + openDoorPayType + '\'' +
                ", openDoorUserId='" + openDoorUserId + '\'' +
                '}';
    }
}
