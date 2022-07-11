package com.cloud.cang.tec.sq.service;

import com.cloud.cang.model.sq.PayApply;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface PayApplyService extends GenericService<PayApply, String> {

    /**
     * 更新付款申请信息
     * @param pmap
     */
    void updateStatusById(Map<String, Object> pmap);
}