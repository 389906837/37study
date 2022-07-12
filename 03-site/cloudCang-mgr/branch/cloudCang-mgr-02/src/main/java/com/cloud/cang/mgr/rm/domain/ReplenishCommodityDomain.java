package com.cloud.cang.mgr.rm.domain;


import com.cloud.cang.model.rm.ReplenishmentCommodity;

/**
 * @description: 商品补货明细 Domain
 * @author:ChangTanchang
 * @time:2018-02-28 10:23:05
 * @version 1.0
 */
public class ReplenishCommodityDomain extends ReplenishmentCommodity {

    //商品名称
    private String spsname;

    public String getSpsname() {
        return spsname;
    }

    public void setSpsname(String spsname) {
        this.spsname = spsname;
    }

    @Override
    public String toString() {
        return "ReplenishCommodityDomain{" +
                "spsname='" + spsname + '\'' +
                '}';
    }
}
