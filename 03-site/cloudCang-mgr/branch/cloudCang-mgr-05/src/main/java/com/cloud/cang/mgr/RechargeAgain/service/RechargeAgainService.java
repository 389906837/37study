package com.cloud.cang.mgr.RechargeAgain.service;


import com.cloud.cang.model.sh.MerchantConf;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RechargeAgainService {
    /**
     * 获取默认商户支付宝配置数据
     *
     * @return 支付宝商户配置数据
     * @throws Exception
     */
    MerchantConf getDefaultAlipayMerchantConf() throws Exception;

    /**
     * 获取默认商户支付宝配置数据
     *
     * @return 微信商户配置数据
     * @throws Exception
     */
    MerchantConf getDefaultWechatMerchantConf()throws Exception ;
}
