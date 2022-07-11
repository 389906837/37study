package com.cloud.cang.act.ac.service;

import com.cloud.cang.model.ac.CouponRule;
import com.cloud.cang.generic.GenericService;

public interface CouponRuleService extends GenericService<CouponRule, String> {

    /**
     * 根据活动ID查询送券规则
     * @param activityId
     * @return
     */
    CouponRule selectCouponRuleByActivityId(String activityId);
}