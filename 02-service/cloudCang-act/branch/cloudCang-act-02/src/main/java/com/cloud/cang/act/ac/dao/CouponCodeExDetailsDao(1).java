package com.cloud.cang.act.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.CouponCodeExDetails;

/** 券码兑换明细表(AC_COUPON_CODE_EX_DETAILS) **/
public interface CouponCodeExDetailsDao extends GenericDao<CouponCodeExDetails, String> {


    /**
     * 根据兑换券码查询券
     * @param couponCode
     * @return
     */
    CouponCodeExDetails selectCouponCodeExDetailsByCouponCode(String couponCode);
}