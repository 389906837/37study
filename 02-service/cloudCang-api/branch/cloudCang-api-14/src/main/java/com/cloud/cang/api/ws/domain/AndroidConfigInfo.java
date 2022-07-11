package com.cloud.cang.api.ws.domain;

import com.cloud.cang.generic.GenericEntity;

/**
 * 返回信息
 */
public class AndroidConfigInfo extends GenericEntity {

    private String apiKey;
    private String secretKey;
    private String licenseID;
    private String licenseFileName;
    private String groupID;             // 用户组
    private String isOpenScreenSaver;   // 是否开启屏保 10=开启 20=关闭
    private String url;         // 屏保地址
    private String score;       // 分数


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getLicenseID() {
        return licenseID;
    }

    public void setLicenseID(String licenseID) {
        this.licenseID = licenseID;
    }

    public String getLicenseFileName() {
        return licenseFileName;
    }

    public void setLicenseFileName(String licenseFileName) {
        this.licenseFileName = licenseFileName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsOpenScreenSaver() {
        return isOpenScreenSaver;
    }

    public void setIsOpenScreenSaver(String isOpenScreenSaver) {
        this.isOpenScreenSaver = isOpenScreenSaver;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "AndroidConfigInfo{" +
                "apiKey='" + apiKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", licenseID='" + licenseID + '\'' +
                ", licenseFileName='" + licenseFileName + '\'' +
                ", groupID='" + groupID + '\'' +
                ", isOpenScreenSaver='" + isOpenScreenSaver + '\'' +
                ", url='" + url + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
