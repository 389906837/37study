package com.cloud.cang.act.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.IntegralRule;

/** 活动返积分规则记录表(AC_INTEGRAL_RULE) **/
public interface IntegralRuleDao extends GenericDao<IntegralRule, String> {


    /**
     * 根据活动配置ID查找积分规则
     * @param activityId
     * @return
     */
    IntegralRule selectIntegralRuleByActivityId(String activityId);
}