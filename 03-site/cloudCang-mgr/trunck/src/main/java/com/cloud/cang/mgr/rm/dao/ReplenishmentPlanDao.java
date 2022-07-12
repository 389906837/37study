package com.cloud.cang.mgr.rm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.rm.domain.ReplenishMentPlanDomain;
import com.cloud.cang.mgr.rm.vo.ReplenishMentPlanVo;
import com.cloud.cang.model.rm.ReplenishmentPlan;
import com.github.pagehelper.Page;

/** 计划商品补货记录信息表(RM_REPLENISHMENT_PLAN) **/
public interface ReplenishmentPlanDao extends GenericDao<ReplenishmentPlan, String> {


    Page<ReplenishMentPlanDomain> selectReplenishMentPlan(ReplenishMentPlanVo replenishMentPlanVo);

    ReplenishMentPlanDomain selectByrmpId(String sid);
}