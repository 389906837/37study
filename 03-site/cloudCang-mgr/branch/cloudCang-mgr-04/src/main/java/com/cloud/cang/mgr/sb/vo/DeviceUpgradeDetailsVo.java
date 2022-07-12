package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.DeviceUpgradeDetails;

/**
 * Created by Alex on 2018/6/25.
 */
public class DeviceUpgradeDetailsVo extends DeviceUpgradeDetails {
    private String orderStr;                //排序字段
    private String sname;                   /* 设备名称 */
    private String merchantName;            /* 商户名称 */
    private String merchantCode;           /* 商户编号 */
    private String queryCondition;          /* 查询条件 */


    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    @Override
    public String toString() {
        return "DeviceUpgradeDetailsVo{" +
                "orderStr='" + orderStr + '\'' +
                ", sname='" + sname + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                '}';
    }
}
