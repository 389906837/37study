package com.cloud.cang.mgr.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.ac.domain.CouponBatchDomain;
import com.cloud.cang.mgr.ac.vo.CouponBatchVo;
import com.cloud.cang.model.ac.CouponBatch;
import com.github.pagehelper.Page;

import java.util.Map;

/** 优惠券批量下发表(AC_COUPON_BATCH) **/
public interface CouponBatchDao extends GenericDao<CouponBatch, String> {

    Page<CouponBatchDomain> queryDataCoupon(CouponBatchVo couponBatchVo);

    void updateIbatchNumber(CouponBatch couponBatch);

    /**
     * @Author: zhouhong
     * @Description: 获取商户活动信息
     * @param: actId 活动ID
     * @Date: 2018/2/10 20:15
     * @return com.cloud.cang.model.ac.ActivityConf
     */
    CouponBatch selectCouponBatchByIdAndMerchantId(Map<String, Object> paramMap);
}