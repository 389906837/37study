package com.cloud.cang.pay;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 签约参数
 * @Author: zhouhong
 * @Date: 2018/3/22 14:57
 */
public class SignDto implements Serializable {

    //========必填========
    private String smerchantCode;//商户编号
    private String smemberId;//会员Id
    private String smemberCode;//会员编号
    private String smemberName;//会员名称
    private String smemberMerchantCode;//会员商户编号

    //====选填=======
    private String sip;//用户IP


    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    public String getSmemberCode() {
        return smemberCode;
    }

    public void setSmemberCode(String smemberCode) {
        this.smemberCode = smemberCode;
    }

    public String getSmemberName() {
        return smemberName;
    }

    public void setSmemberName(String smemberName) {
        this.smemberName = smemberName;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public String getSmemberMerchantCode() {
        return smemberMerchantCode;
    }

    public void setSmemberMerchantCode(String smemberMerchantCode) {
        this.smemberMerchantCode = smemberMerchantCode;
    }

    @Override
    public String toString() {
        return "SignDto{" +
                "smerchantCode='" + smerchantCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                ", smemberCode='" + smemberCode + '\'' +
                ", smemberName='" + smemberName + '\'' +
                ", smemberMerchantCode='" + smemberMerchantCode + '\'' +
                ", sip='" + sip + '\'' +
                '}';
    }
}
