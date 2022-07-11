package com.cloud.cang.act.ac.service;

import com.cloud.cang.model.ac.CouponUserSend;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface CouponUserSendService extends GenericService<CouponUserSend, String> {

    /**
     * 根据批次ID 查找下发用户
     * @param sbatchId
     * @return
     */
    List<CouponUserSend> selectCouponUserSendByBatchId(String sbatchId);

}