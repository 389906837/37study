package com.cloud.cang.bzc.sh.service;

import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.generic.GenericService;

public interface MerchantInfoService extends GenericService<MerchantInfo, String> {

    /**
     * 获取商户信息
     * @param merchantCode 商户编号
     * @return
     */
    MerchantInfo selectByCode(String merchantCode);
}