package com.cloud.cang.act.ac.service;

import com.cloud.cang.model.ac.IntegralRule;
import com.cloud.cang.generic.GenericService;

public interface IntegralRuleService extends GenericService<IntegralRule, String> {

    /**
     * 根据活动配置ID查找积分规则
     * @param activityId
     * @return
     */
    IntegralRule selectIntegralRuleByActivityId(String activityId);
}