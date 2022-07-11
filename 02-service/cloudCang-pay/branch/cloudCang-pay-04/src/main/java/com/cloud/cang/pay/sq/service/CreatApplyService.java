package com.cloud.cang.pay.sq.service;

import com.cloud.cang.model.sq.CreatApply;
import com.cloud.cang.generic.GenericService;

public interface CreatApplyService extends GenericService<CreatApply, String> {

    /**
     * 根据用户和设备Id查询数据
     *
     * @param userId
     * @param deviceId
     * @return
     */
    CreatApply selectByUserIdAndDeviceId(String userId, String deviceId);
}