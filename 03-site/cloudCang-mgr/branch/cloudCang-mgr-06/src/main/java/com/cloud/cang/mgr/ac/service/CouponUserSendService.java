package com.cloud.cang.mgr.ac.service;

import com.cloud.cang.mgr.ac.domain.CouponUserSendDomain;
import com.cloud.cang.mgr.ac.vo.CouponUserSendVo;
import com.cloud.cang.model.ac.CouponUserSend;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;

public interface CouponUserSendService extends GenericService<CouponUserSend, String> {

    /**
     * 根据优惠券下发表(用户信息)发放批次ID查询
     * @param sid
     * @return
     */
    CouponUserSendDomain selectByCouponBatchSendId(String sid);

    void deleteNotInIds(String batchId);

    /**
     * 批量添加用户
     * @param couponUserSendDomain
     * @return
     */
//    List<CouponUserSendDomain> selectCouponUserSend(CouponUserSendDomain couponUserSendDomain);

}