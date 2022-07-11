package com.cloud.cang.wap.rm.service;

import com.cloud.cang.model.rm.ReplenishmentPlan;
import com.cloud.cang.generic.GenericService;

public interface ReplenishmentPlanService extends GenericService<ReplenishmentPlan, String> {

    /***
     * 更新设备的计划补货单信息
     * @param deviceCode
     */
    void updateByLastTime(String deviceCode);
}