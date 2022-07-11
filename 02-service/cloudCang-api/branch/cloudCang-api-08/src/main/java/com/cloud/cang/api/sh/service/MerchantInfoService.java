package com.cloud.cang.api.sh.service;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.generic.GenericService;

public interface MerchantInfoService extends GenericService<MerchantInfo, String> {
    /**
     * 获取商户信息
     * @param merchantCode 商户编号
     * @return
     */
    MerchantInfo selectByCode(String merchantCode);
    /**
     * 获取微信支付商户配置数据
     * @param merchantCode 商户编号
     * @param type 类型 1 公众号 2 微信支付
     * @return 微信支付商户配置数据
     * @throws Exception
     */
    MerchantConf getWechatMerchantConf(String merchantCode, Integer type) throws Exception;
}