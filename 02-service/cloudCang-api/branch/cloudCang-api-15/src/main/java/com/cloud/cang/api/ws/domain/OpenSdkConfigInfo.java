package com.cloud.cang.api.ws.domain;

import com.cloud.cang.generic.GenericEntity;

/**
 * openSdk配置信息
 */
public class OpenSdkConfigInfo extends GenericEntity {

    private String appId;                   // APPID
    private String appSercetKey;            // APP秘钥
    private String host;                    // 主机地址
    private String splatformPublicKey;      // 平台公钥
    private String sappPrivateKey;          // app私钥
    private String iisOpeningInventory;     // 是否开启实时盘货 购物
    private String replenstimer;     // 是否开启实时盘货 补货

    public String getIisOpeningInventory() {
        return iisOpeningInventory;
    }

    public void setIisOpeningInventory(String iisOpeningInventory) {
        this.iisOpeningInventory = iisOpeningInventory;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSercetKey() {
        return appSercetKey;
    }

    public void setAppSercetKey(String appSercetKey) {
        this.appSercetKey = appSercetKey;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSplatformPublicKey() {
        return splatformPublicKey;
    }

    public void setSplatformPublicKey(String splatformPublicKey) {
        this.splatformPublicKey = splatformPublicKey;
    }

    public String getSappPrivateKey() {
        return sappPrivateKey;
    }

    public void setSappPrivateKey(String sappPrivateKey) {
        this.sappPrivateKey = sappPrivateKey;
    }

    public String getReplenstimer() {
        return replenstimer;
    }

    public void setReplenstimer(String replenstimer) {
        this.replenstimer = replenstimer;
    }

    @Override
    public String toString() {
        return "OpenSdkConfigInfo{" +
                "appId='" + appId + '\'' +
                ", appSercetKey='" + appSercetKey + '\'' +
                ", host='" + host + '\'' +
                ", splatformPublicKey='" + splatformPublicKey + '\'' +
                ", sappPrivateKey='" + sappPrivateKey + '\'' +
                ", iisOpeningInventory='" + iisOpeningInventory + '\'' +
                ", replenstimer='" + replenstimer + '\'' +
                '}';
    }
}
