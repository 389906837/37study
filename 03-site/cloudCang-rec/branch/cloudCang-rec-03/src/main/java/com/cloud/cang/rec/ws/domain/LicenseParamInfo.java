package com.cloud.cang.rec.ws.domain;

/**
 * Created by YLF on 2020/5/27.
 */
public class LicenseParamInfo {
    private String appName;
    private String requestAmount;
    private Integer consumptionId;
    private String licenseType;
    private String buyerID;
    private String appVersion;
    private String allocalTable;
    private String requestType;
    private String requestDays;
    private String requestContent;
    private String itemId;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(String requestAmount) {
        this.requestAmount = requestAmount;
    }

    public Integer getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(Integer consumptionId) {
        this.consumptionId = consumptionId;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAllocalTable() {
        return allocalTable;
    }

    public void setAllocalTable(String allocalTable) {
        this.allocalTable = allocalTable;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestDays() {
        return requestDays;
    }

    public void setRequestDays(String requestDays) {
        this.requestDays = requestDays;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "licenseParamInfo{" +
                "appName='" + appName + '\'' +
                ", requestAmount='" + requestAmount + '\'' +
                ", consumptionId=" + consumptionId +
                ", licenseType='" + licenseType + '\'' +
                ", buyerID='" + buyerID + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", allocalTable='" + allocalTable + '\'' +
                ", requestType='" + requestType + '\'' +
                ", requestDays='" + requestDays + '\'' +
                ", requestContent='" + requestContent + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }
}
