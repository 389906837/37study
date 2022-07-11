package com.cloud.cang.act.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.CouponRule;

/** 活动返券规则记录表(AC_COUPON_RULE) **/
public interface CouponRuleDao extends GenericDao<CouponRule, String> {


    /**
     * 根据活动ID查询送券规则
     * @param activityId
     * @return
     */
    CouponRule selectCouponRuleByActivityId(String activityId);
}