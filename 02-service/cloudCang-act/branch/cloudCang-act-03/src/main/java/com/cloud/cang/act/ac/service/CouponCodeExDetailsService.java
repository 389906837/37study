package com.cloud.cang.act.ac.service;

import com.cloud.cang.model.ac.CouponCodeExDetails;
import com.cloud.cang.generic.GenericService;

public interface CouponCodeExDetailsService extends GenericService<CouponCodeExDetails, String> {

    /**
     * 根据兑换券码查询券
     * @param couponCode
     * @return
     */
    CouponCodeExDetails selectCouponCodeExDetailsByCouponCode(String couponCode);
}