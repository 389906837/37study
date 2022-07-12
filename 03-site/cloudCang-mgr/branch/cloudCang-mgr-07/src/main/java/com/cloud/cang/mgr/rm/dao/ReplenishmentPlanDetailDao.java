package com.cloud.cang.mgr.rm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.rm.domain.ReplenishMentPlanDetailDomain;
import com.cloud.cang.model.rm.ReplenishmentPlanDetail;

import java.util.List;

/** 计划补货商品明细表(RM_REPLENISHMENT_PLAN_DETAIL) **/
public interface ReplenishmentPlanDetailDao extends GenericDao<ReplenishmentPlanDetail, String> {

    List<ReplenishMentPlanDetailDomain> selectInfoId(String sid);
}