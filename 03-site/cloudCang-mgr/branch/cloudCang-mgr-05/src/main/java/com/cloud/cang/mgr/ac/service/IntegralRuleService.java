package com.cloud.cang.mgr.ac.service;

import com.cloud.cang.model.ac.IntegralRule;
import com.cloud.cang.generic.GenericService;

public interface IntegralRuleService extends GenericService<IntegralRule, String> {


    /**
     * 保存或更新积分规则
     * @param integralRule 积分规则实体
     * @return
     */
    IntegralRule saveOrUpdate(IntegralRule integralRule);
}