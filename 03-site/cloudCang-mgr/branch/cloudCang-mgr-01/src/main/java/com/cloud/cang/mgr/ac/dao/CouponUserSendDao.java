package com.cloud.cang.mgr.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.ac.domain.CouponUserSendDomain;
import com.cloud.cang.mgr.ac.vo.CouponUserSendVo;
import com.cloud.cang.model.ac.CouponUserSend;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/** 优惠券批量下发（用户信息）(AC_COUPON_USER_SEND) **/
public interface CouponUserSendDao extends GenericDao<CouponUserSend, String> {

    CouponUserSendDomain selectByCouponBatchSendId(String sid);

    void deleteNotInIds(String batchId);

    CouponUserSend selectByBatchId(String sid);


}