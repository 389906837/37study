package com.cloud.cang.pay;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 签约参数
 * @Author: zhouhong
 * @Date: 2018/3/22 14:57
 */
public class QuerySignDto implements Serializable {

    //========必填========
    private String smerchantCode;//商户编号
    private String smemberId;//会员Id

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

    @Override
    public String toString() {
        return "QuerySignDto{" +
                "smerchantCode='" + smerchantCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                '}';
    }
}
