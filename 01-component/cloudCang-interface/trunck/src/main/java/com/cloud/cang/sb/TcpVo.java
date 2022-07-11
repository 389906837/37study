package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

/**
 * 查询连接返回结果
 *
 * @author zengzexiong
 * @version 1.0
 * @date 2018年6月2日16:10:17
 */
public class TcpVo extends SuperDto {
    private String deviceId;    // 设备ID
    private String deviceCode;  // 设备编号
    private String channelId;   // 通道long id
    private String ip;          // ip地址
    private String port;        // 端口号


    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "TcpVo{" +
                "deviceId='" + deviceId + '\'' +
                "deviceCode='" + deviceCode + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
