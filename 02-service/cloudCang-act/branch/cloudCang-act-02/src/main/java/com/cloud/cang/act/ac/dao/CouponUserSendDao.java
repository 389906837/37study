package com.cloud.cang.act.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.CouponUserSend;

import java.util.List;

/** 优惠券批量下发（用户信息）(AC_COUPON_USER_SEND) **/
public interface CouponUserSendDao extends GenericDao<CouponUserSend, String> {


    /**
     * 根据批次ID 查找下发用户
     * @param sbatchId
     * @return
     */
    List<CouponUserSend> selectCouponUserSendByBatchId(String sbatchId);
}