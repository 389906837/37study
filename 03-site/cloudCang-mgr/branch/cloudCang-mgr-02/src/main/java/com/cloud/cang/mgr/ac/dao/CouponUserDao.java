package com.cloud.cang.mgr.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.ac.domain.CouponUserDomain;
import com.cloud.cang.mgr.ac.vo.CouponUserVo;
import com.cloud.cang.model.ac.CouponUser;
import com.github.pagehelper.Page;

import java.util.HashMap;

/**
 * 用户持有劵信息表(AC_COUPON_USER)
 **/
public interface CouponUserDao extends GenericDao<CouponUser, String> {

    Page<CouponUserDomain> queryDataCouponUser(CouponUserVo couponUserVo);

    /**
     * 用户持有券列表页脚总统计
     *
     * @param couponUserVo
     * @return ResponseVo
     */
    HashMap queryTotalData(CouponUserVo couponUserVo);
}