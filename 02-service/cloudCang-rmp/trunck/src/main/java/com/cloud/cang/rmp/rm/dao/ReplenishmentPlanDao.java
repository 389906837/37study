package com.cloud.cang.rmp.rm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.rm.ReplenishmentPlan;

import java.util.Map;

/** 计划商品补货记录信息表(RM_REPLENISHMENT_PLAN) **/
public interface ReplenishmentPlanDao extends GenericDao<ReplenishmentPlan, String> {

    ReplenishmentPlan selectByReplenishmentPlanId(String sdeviceId);

    ReplenishmentPlan selectBySdeviceId(Map<String, Object> map);
    /**
     * 查询补货单数据 未完成状态
     * @param sdeviceId 设备ID
     * @return
     */
    ReplenishmentPlan selectUndoneByDeviceId(String sdeviceId);
}