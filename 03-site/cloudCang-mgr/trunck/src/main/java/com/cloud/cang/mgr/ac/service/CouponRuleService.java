package com.cloud.cang.mgr.ac.service;

import com.cloud.cang.model.ac.CouponRule;
import com.cloud.cang.generic.GenericService;

public interface CouponRuleService extends GenericService<CouponRule, String> {

    /**
     * 新增或修改送券规则
     * @param couponRule 券规则实体
     * @param iscenesType 活动场景分类 30 下单
     * @param itype 活动分类 10 场景活动 20 促销活动
     * @return
     */
    CouponRule saveOrUpdate(CouponRule couponRule, Integer iscenesType, Integer itype);
}