package com.cloud.cang.mgr.rm.domain;

import com.cloud.cang.model.rm.ReplenishmentPlanDetail;

/**
 * Created by Administrator on 2018/6/21.
 */
public class ReplenishMentPlanDetailDomain extends ReplenishmentPlanDetail {

    // 商品名称
    private String spsname;

    public String getSpsname() {
        return spsname;
    }

    public void setSpsname(String spsname) {
        this.spsname = spsname;
    }

    @Override
    public String toString() {
        return "ReplenishMentPlanDetailDomain{" +
                "spsname='" + spsname + '\'' +
                '}';
    }
}
