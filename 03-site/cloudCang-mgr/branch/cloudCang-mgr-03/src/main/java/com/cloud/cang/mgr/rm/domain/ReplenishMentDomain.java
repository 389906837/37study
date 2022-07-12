package com.cloud.cang.mgr.rm.domain;


import com.cloud.cang.model.rm.ReplenishmentRecord;

/**
 * @description: 商品补货 Domain
 * @author:ChangTanchang
 * @time:2018-02-28 10:23:05
 * @version 1.0
 */
public class ReplenishMentDomain extends ReplenishmentRecord {
    private static final long serialVersionUID = 1L;

    //排序字段
    private String orderStr;

    //商户名
    private String merchantName;

    //商户编号
    private String shcode;

    //商品名称
    private String spsname;

    //设备名称
    private String sbsname;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getShcode() {
        return shcode;
    }

    public void setShcode(String shcode) {
        this.shcode = shcode;
    }

    public String getSpsname() {
        return spsname;
    }

    public void setSpsname(String spsname) {
        this.spsname = spsname;
    }

    public String getSbsname() {
        return sbsname;
    }

    public void setSbsname(String sbsname) {
        this.sbsname = sbsname;
    }
}
