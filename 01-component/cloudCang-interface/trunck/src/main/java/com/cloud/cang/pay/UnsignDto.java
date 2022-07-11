package com.cloud.cang.pay;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 解约参数
 * @Author: zhouhong
 * @Date: 2018/3/22 14:57
 */
public class UnsignDto implements Serializable {

    //========必填========
    private String smerchantCode;//商户编号
    private String smemberId;//会员Id
    private String smemberMerchantCode;//会员商户编号

    //====选填======
    private String sip;//操作IP
    private String remark;//解约备注

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    public String getSmerchantCode() {

        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSmemberMerchantCode() {
        return smemberMerchantCode;
    }

    public void setSmemberMerchantCode(String smemberMerchantCode) {
        this.smemberMerchantCode = smemberMerchantCode;
    }

    @Override
    public String toString() {
        return "UnsignDto{" +
                "smerchantCode='" + smerchantCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                ", smemberMerchantCode='" + smemberMerchantCode + '\'' +
                ", sip='" + sip + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
