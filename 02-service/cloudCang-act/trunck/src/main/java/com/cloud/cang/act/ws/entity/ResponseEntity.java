package com.cloud.cang.act.ws.entity;

public class ResponseEntity {
    private String buyerID;
    private String appName;
    private String appVersion;
    private String licenseType;
    private String expiredDays;
    private String serialNumber;
    private String message;
    private String resultCode;
    private String authorizationDate;//外加字段
    private String signNumber;//签名参数 外加字段
    private String allocalTable;//设备ID信息  外加字段

    public String getAllocalTable() {
        return allocalTable;
    }

    public void setAllocalTable(String allocalTable) {
        this.allocalTable = allocalTable;
    }

    public String getSignNumber() {
        return signNumber;
    }

    public void setSignNumber(String signNumber) {
        this.signNumber = signNumber;
    }

    public String getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(String authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public String getBuyerID() {
        return this.buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getLicenseType() {
        return this.licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getExpiredDays() {
        return this.expiredDays;
    }

    public void setExpiredDays(String expiredDays) {
        this.expiredDays = expiredDays;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
