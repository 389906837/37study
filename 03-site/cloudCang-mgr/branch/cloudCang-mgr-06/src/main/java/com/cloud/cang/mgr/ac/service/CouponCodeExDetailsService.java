package com.cloud.cang.mgr.ac.service;

import com.cloud.cang.mgr.ac.domain.CouponCodeExDetailsDomain;
import com.cloud.cang.mgr.ac.vo.CouponCodeExDetailsVo;
import com.cloud.cang.model.ac.CouponCodeExDetails;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface CouponCodeExDetailsService extends GenericService<CouponCodeExDetails, String> {

    /**
     * 券码兑换明细查询
     * @param page
     * @param couponCodeExDetailsVo
     * @return
     */
    Page<CouponCodeExDetailsDomain> queryDataCouponCouponCodeExDetails(Page<CouponCodeExDetailsDomain> page, CouponCodeExDetailsVo couponCodeExDetailsVo);
}