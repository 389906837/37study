package com.cloud.cang.bzc.hy.service;

import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.generic.GenericService;

public interface FreeDataService extends GenericService<FreeData, String> {


    /**
     * 获取支付宝免密数据
     * @param memberId 会员Id
     * @param merchantCode 商户编号
     * @return
     */
    FreeData selectByMemberId(String memberId, String merchantCode);
}