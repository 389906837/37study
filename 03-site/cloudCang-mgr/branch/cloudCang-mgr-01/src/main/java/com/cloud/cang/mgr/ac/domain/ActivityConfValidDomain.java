package com.cloud.cang.mgr.ac.domain;

import com.cloud.cang.model.ac.ActivityConf;

/**
 * @version 1.0
 * @ClassName: ActivityConfValidDomain
 * @Description: 有效活动返回Domain
 * @Author: zengzexiong
 * @Date: 2018年6月6日11:44:30
 */
public class ActivityConfValidDomain extends ActivityConf {

    private String scommodityId;        /* 商品ID多个用，隔开 */
    private String scommodityCode;      /* 商品编号多个用，隔开 */
    private String sdeviceId;           /* 设备ID多个用，隔开 */
    private String sdeviceCode;          /* 设备编号多个用，隔开 */


    public String getScommodityId() {
        return scommodityId;
    }

    public void setScommodityId(String scommodityId) {
        this.scommodityId = scommodityId;
    }

    public String getScommodityCode() {
        return scommodityCode;
    }

    public void setScommodityCode(String scommodityCode) {
        this.scommodityCode = scommodityCode;
    }

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    @Override
    public String toString() {
        return "ActivityConfValidDomain{" +
                "scommodityId='" + scommodityId + '\'' +
                ", scommodityCode='" + scommodityCode + '\'' +
                ", sdeviceId='" + sdeviceId + '\'' +
                ", sdeviceCode='" + sdeviceCode + '\'' +
                '}';
    }
}
