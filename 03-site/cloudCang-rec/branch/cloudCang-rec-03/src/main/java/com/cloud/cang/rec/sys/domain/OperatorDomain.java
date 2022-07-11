package com.cloud.cang.rec.sys.domain;


import com.cloud.cang.model.sys.Operator;

/**
 * @description: 系统用户 Domain
 * @author:Yanlingfeng
 * @time:2018-02-22 14:07:05
 * @version 1.0
 */
public class OperatorDomain extends Operator {

    private String srole;
    private Integer isReplenishment; //是否为补货员 0否/1是

    private String merchantName;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getIsReplenishment() {
        return isReplenishment;
    }

    public void setIsReplenishment(Integer isReplenishment) {
        this.isReplenishment = isReplenishment;
    }

    public String getSrole() {
        return srole;
    }

    public void setSrole(String srole) {
        this.srole = srole;
    }


}
