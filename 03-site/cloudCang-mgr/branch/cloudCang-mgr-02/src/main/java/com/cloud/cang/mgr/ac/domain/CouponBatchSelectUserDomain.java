package com.cloud.cang.mgr.ac.domain;

import com.cloud.cang.generic.GenericEntity;

/**
 * Created by Alex on 2018/7/11.
 */
public class CouponBatchSelectUserDomain extends GenericEntity {
    private String smemberId;      // 用户ID
    private String smemberCode;    // 用户编号
    private String smemberName;    // 用户名
    private Integer inumber;        // 数量

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

    public Integer getInumber() {
        return inumber;
    }

    public void setInumber(Integer inumber) {
        this.inumber = inumber;
    }

    @Override
    public String toString() {
        return "CouponBatchSelectUserDomain{" +
                "smemberId='" + smemberId + '\'' +
                "smemberCode='" + smemberCode + '\'' +
                ", smemberName='" + smemberName + '\'' +
                ", inumber=" + inumber +
                '}';
    }
}
