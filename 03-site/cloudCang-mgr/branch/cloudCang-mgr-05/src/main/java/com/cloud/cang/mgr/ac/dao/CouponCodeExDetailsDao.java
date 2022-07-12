package com.cloud.cang.mgr.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.ac.domain.CouponCodeExDetailsDomain;
import com.cloud.cang.mgr.ac.vo.CouponCodeExDetailsVo;
import com.cloud.cang.model.ac.CouponCodeExDetails;
import com.github.pagehelper.Page;

/** 券码兑换明细表(AC_COUPON_CODE_EX_DETAILS) **/
public interface CouponCodeExDetailsDao extends GenericDao<CouponCodeExDetails, String> {

    Page<CouponCodeExDetailsDomain> queryDataCouponCouponCodeExDetails(CouponCodeExDetailsVo couponCodeExDetailsVo);
}