package com.cloud.cang.tec.sh.service;

import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface MerchantInfoService extends GenericService<MerchantInfo, String> {
    /**
     * 商户签约到期状态变更定时任务
     *
     * @param merchantId 商户ID
     */
    List<MerchantInfo> selectExpired(String merchantId);
    /**
     * 获取商户信息
     * @param merchantCode 商户编号
     * @return
     */
    MerchantInfo selectByCode(String merchantCode);

}