package com.cloud.cang.bzc.ac.service;

import com.cloud.cang.model.ac.CouponRule;
import com.cloud.cang.generic.GenericService;

public interface CouponRuleService extends GenericService<CouponRule, String> {
 
    CouponRule selectBySacId(String sacId);
}