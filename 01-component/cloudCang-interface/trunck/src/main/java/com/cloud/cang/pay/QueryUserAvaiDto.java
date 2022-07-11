package com.cloud.cang.pay;

/**
 * Created by YLF on 2019/10/30.
 */
public class QueryUserAvaiDto {
    private String smemberId;//会员Id
    private String smerchantCode;//商户编号

    @Override
    public String toString() {
        return "QueryUserAvaiDto{" +
                "smemberId='" + smemberId + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                '}';
    }

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
}
