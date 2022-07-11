package com.cloud.cang.pay.sh.service;

import com.alipay.api.AlipayClient;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.generic.GenericService;

import javax.servlet.http.HttpServletRequest;

public interface MerchantInfoService extends GenericService<MerchantInfo, String> {
    /**
     * 获取商户信息
     * @param merchantCode 商户编号
     * @return
     */
    MerchantInfo selectByCode(String merchantCode);

    /**
     * 获取AlipayClient 客户端 创建支付宝的sdk client
     * @param merchantCode 商户编号
     * @throws Exception
     */
    AlipayClient getAlipayClient(String merchantCode) throws Exception;

    /**
     * 获取支付宝商户配置数据
     * @param merchantCode 商户编号
     * @param type 类型 1 生活号 2 支付宝支付
     * @return 支付宝商户配置数据
     * @throws Exception
     */
    MerchantConf getAlipayMerchantConf(String merchantCode, Integer type) throws Exception;

    /**
     * 获取支付宝商户配置数据
     * @param merchantCode 商户编号
     * @return 支付宝商户配置数据
     * @throws Exception
     */
    MerchantConf getAlipayMerchantConf(String merchantCode) throws Exception;

    /**
     * 获取微信商户配置数据
     * @param merchantCode 商户编号
     * @return 微信商户配置数据
     * @throws Exception
     */
    MerchantConf getWechatMerchantConf(String merchantCode) throws Exception;

    /**
     * 获取微信支付商户配置数据
     * @param merchantCode 商户编号
     * @param type 类型 1 公众号 2 微信支付
     * @return 微信支付商户配置数据
     * @throws Exception
     */
    MerchantConf getWechatMerchantConf(String merchantCode, Integer type) throws Exception;
    /**
     * 获取默认商户支付宝配置数据
     * @return 商户支付宝配置数据
     * @throws Exception
     */
    MerchantConf getDefaultAlipayMerchantConf() throws Exception;
    /**
     * 获取默认商户微信配置数据
     * @return 商户微信配置数据
     * @throws Exception
     */
    MerchantConf getDefaultWechatMerchantConf() throws Exception;
}