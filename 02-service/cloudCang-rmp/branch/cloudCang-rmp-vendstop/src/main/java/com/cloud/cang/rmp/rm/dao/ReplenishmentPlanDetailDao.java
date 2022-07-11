package com.cloud.cang.rmp.rm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.rm.ReplenishmentPlanDetail;

/** 计划补货商品明细表(RM_REPLENISHMENT_PLAN_DETAIL) **/
public interface ReplenishmentPlanDetailDao extends GenericDao<ReplenishmentPlanDetail, String> {

    ReplenishmentPlanDetail selectByRpdId(String pid);
}