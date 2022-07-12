package com.cloud.cang.mgr.ac.service;

import com.cloud.cang.mgr.ac.domain.CouponUserDomain;
import com.cloud.cang.mgr.ac.vo.CouponUserVo;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.HashMap;

public interface CouponUserService extends GenericService<CouponUser, String> {

    /**
     * 用户持有券
     *
     * @param page
     * @param couponUserVo
     * @return
     */
    Page<CouponUserDomain> queryDataCouponUser(Page<CouponUserDomain> page, CouponUserVo couponUserVo);

    /**
     * 用户持有券列表页脚总统计
     *
     * @param couponUserVo
     * @return ResponseVo
     */
    HashMap queryTotalData(CouponUserVo couponUserVo);
}