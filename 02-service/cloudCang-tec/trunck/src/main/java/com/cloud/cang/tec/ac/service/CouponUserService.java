package com.cloud.cang.tec.ac.service;

import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.tec.ac.vo.CouponUserVo;

import java.util.List;

public interface CouponUserService extends GenericService<CouponUser, String> {
    /**
     * 优惠券过期短信提醒
     *
     * @param merchantId 商户ID
     */
    List<CouponUserVo> selectExpiredCouponWarn(String merchantId,String expireDate);

    /**
     * 优惠券过期状态变更
     *
     * @param merchantId 商户ID
     */
    List<CouponUser> selectExpiredCoupon(String merchantId);

}