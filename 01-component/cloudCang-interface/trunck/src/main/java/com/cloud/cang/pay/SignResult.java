package com.cloud.cang.pay;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 支付宝免密协议数据
 * @Author: zhouhong
 * @Date: 2018/3/22 14:57
 */
public class SignResult implements Serializable {

    private String validTime;//协议生效时间
    private String alipayLogonId;//脱敏的支付宝账
    private String invalidTime;//协议失效时间
    private String pricipalType;//签约主体类型
    private String deviceId;//设备Id
    private String principalId;//签约主体标识
    private String signScene;//签约协议场景
    private String agreementNo;//支付宝系统签约成功后的协议号
    private String thirdPartyType;//签约第三方主体类型
    private String status;//协议当前状态1. TEMP：暂存，协议未生效过；2. NORMAL：正常；3. STOP：暂停
    private String signTime;//协议签约时间
    private String personalProductCode;//协议产品码
    private String externalAgreementNo;//系统用户的唯一签约号
    private String zmOpenId;//用户的芝麻信用 openId，
    private String externalLogonId;//外部登录Id

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getAlipayLogonId() {
        return alipayLogonId;
    }

    public void setAlipayLogonId(String alipayLogonId) {
        this.alipayLogonId = alipayLogonId;
    }

    public String getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(String invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getPricipalType() {
        return pricipalType;
    }

    public void setPricipalType(String pricipalType) {
        this.pricipalType = pricipalType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getSignScene() {
        return signScene;
    }

    public void setSignScene(String signScene) {
        this.signScene = signScene;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public String getThirdPartyType() {
        return thirdPartyType;
    }

    public void setThirdPartyType(String thirdPartyType) {
        this.thirdPartyType = thirdPartyType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getPersonalProductCode() {
        return personalProductCode;
    }

    public void setPersonalProductCode(String personalProductCode) {
        this.personalProductCode = personalProductCode;
    }

    public String getExternalAgreementNo() {
        return externalAgreementNo;
    }

    public void setExternalAgreementNo(String externalAgreementNo) {
        this.externalAgreementNo = externalAgreementNo;
    }

    public String getZmOpenId() {
        return zmOpenId;
    }

    public void setZmOpenId(String zmOpenId) {
        this.zmOpenId = zmOpenId;
    }

    public String getExternalLogonId() {
        return externalLogonId;
    }

    public void setExternalLogonId(String externalLogonId) {
        this.externalLogonId = externalLogonId;
    }

    @Override
    public String toString() {
        return "SignResult{" +
                "validTime='" + validTime + '\'' +
                ", alipayLogonId='" + alipayLogonId + '\'' +
                ", invalidTime='" + invalidTime + '\'' +
                ", pricipalType='" + pricipalType + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", principalId='" + principalId + '\'' +
                ", signScene='" + signScene + '\'' +
                ", agreementNo='" + agreementNo + '\'' +
                ", thirdPartyType='" + thirdPartyType + '\'' +
                ", status='" + status + '\'' +
                ", signTime='" + signTime + '\'' +
                ", personalProductCode='" + personalProductCode + '\'' +
                ", externalAgreementNo='" + externalAgreementNo + '\'' +
                ", zmOpenId='" + zmOpenId + '\'' +
                ", externalLogonId='" + externalLogonId + '\'' +
                '}';
    }
}
