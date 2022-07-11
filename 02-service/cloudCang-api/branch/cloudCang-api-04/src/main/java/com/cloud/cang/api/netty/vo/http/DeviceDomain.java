package com.cloud.cang.api.netty.vo.http;

import com.cloud.cang.api.netty.vo.send.CellBean;

import java.util.List;

/**
 * HTTP请求返回的设备基础信息对象
 *
 * @author zengzexiong
 * @version 1.0
 * @data 2018年3月15日16:25:31
 */
public class DeviceDomain {


    String deviceId;                        //设备id
    String deviceCode;                      //设备编号
    Integer type;                           //设备类型
    Integer voiceValue;                      //音量值
    Integer inventoryNum;                   //盘货次数
    String url;                             //URL地址
    String port;                            //端口号
    String key;                             //安全密钥
    String lockType;                        //锁的型号
    String qrCodeUrl;                       //二维码图片地址
    String scontactMobile;                  //商户客服电话
    String slogo;                  //商户Logo
    String useristimer;             //用户是否开启定时盘货
    String replenstimer;            //补货员是否开启定时盘货
    String contrastMode;            //设备盘货对比方式
    List<CellBean> listcell;                //各层通道信息

    public String getScontactMobile() {
        return scontactMobile;
    }

    public void setScontactMobile(String scontactMobile) {
        this.scontactMobile = scontactMobile;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getLockType() {
        return lockType;
    }

    public void setLockType(String lockType) {
        this.lockType = lockType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVoiceValue() {
        return voiceValue;
    }

    public void setVoiceValue(Integer voiceValue) {
        this.voiceValue = voiceValue;
    }

    public Integer getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(Integer inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public List<CellBean> getListcell() {
        return listcell;
    }

    public void setListcell(List<CellBean> listcell) {
        this.listcell = listcell;
    }

    public String getUseristimer() {
        return useristimer;
    }

    public void setUseristimer(String useristimer) {
        this.useristimer = useristimer;
    }

    public String getSlogo() {
        return slogo;
    }

    public void setSlogo(String slogo) {
        this.slogo = slogo;
    }

    public String getReplenstimer() {
        return replenstimer;
    }

    public void setReplenstimer(String replenstimer) {
        this.replenstimer = replenstimer;
    }

    public String getContrastMode() {
        return contrastMode;
    }

    public void setContrastMode(String contrastMode) {
        this.contrastMode = contrastMode;
    }

    @Override
    public String toString() {
        return "DeviceDomain{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", type=" + type +
                ", voiceValue=" + voiceValue +
                ", inventoryNum=" + inventoryNum +
                ", url='" + url + '\'' +
                ", port='" + port + '\'' +
                ", key='" + key + '\'' +
                ", lockType='" + lockType + '\'' +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                ", scontactMobile='" + scontactMobile + '\'' +
                ", slogo='" + slogo + '\'' +
                ", useristimer='" + useristimer + '\'' +
                ", replenstimer='" + replenstimer + '\'' +
                ", contrastMode='" + contrastMode + '\'' +
                ", listcell=" + listcell +
                '}';
    }


}

