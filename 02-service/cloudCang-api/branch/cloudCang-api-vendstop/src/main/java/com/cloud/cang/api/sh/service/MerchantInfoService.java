package com.cloud.cang.api.sh.service;

import com.cloud.cang.api.om.vo.OrderDomian;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.Map;

public interface MerchantInfoService extends GenericService<MerchantInfo, String> {
    /**
     * 获取商户信息
     * @param merchantCode 商户编号
     * @return
     */
    MerchantInfo selectByCode(String merchantCode);
    /***
     * 分页查询商户列表
     * @param page 分页参数
     */
    Page<MerchantInfo> selectMerchantInfoListByPage(Page page,Map<String,Object> param);
    
    /**
     * 获取微信支付商户配置数据
     * @param merchantCode 商户编号
     * @param type 类型 1 公众号 2 微信支付
     * @return 微信支付商户配置数据
     * @throws Exception
     */
    MerchantConf getWechatMerchantConf(String merchantCode, Integer type) throws Exception;
}