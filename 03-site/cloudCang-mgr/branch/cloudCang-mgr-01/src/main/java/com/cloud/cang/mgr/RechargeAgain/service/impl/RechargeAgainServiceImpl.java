package com.cloud.cang.mgr.RechargeAgain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.mgr.RechargeAgain.service.RechargeAgainService;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Service
public class RechargeAgainServiceImpl implements RechargeAgainService {
    @Autowired
    private ICached iCached;

    /**
     * 获取默认商户支付宝配置数据
     *
     * @return 支付宝商户配置数据
     * @throws Exception
     */
    @Override
    public MerchantConf getDefaultAlipayMerchantConf() throws Exception {
        String merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_ALIPAY_CONFIG + merchantCode);
        if (StringUtil.isBlank(json)) {
            return null;
        }
        return JSONObject.parseObject(json, MerchantConf.class);
    }
    /**
     * 获取默认商户微信配置数据
     * @return 商户微信配置数据
     * @throws Exception
     */
    @Override
    public MerchantConf getDefaultWechatMerchantConf() throws Exception {
        String merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO,RedisConst.SH_WECHAT_CONFIG+merchantCode);
        if (StringUtil.isBlank(json)) {
            return null;
        }
        return JSONObject.parseObject(json, MerchantConf.class);
    }
}
