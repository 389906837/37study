package com.cloud.cang.api.sh.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.api.sh.dao.MerchantInfoDao;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.api.sh.service.MerchantInfoService;

@Service
public class MerchantInfoServiceImpl extends GenericServiceImpl<MerchantInfo, String> implements
        MerchantInfoService {

    @Autowired
    MerchantInfoDao merchantInfoDao;
    @Autowired
    ICached iCached;

    @Override
    public GenericDao<MerchantInfo, String> getDao() {
        return merchantInfoDao;
    }

    /**
     * 获取商户信息
     *
     * @param merchantCode 商户编号
     * @return
     */
    @Override
    public MerchantInfo selectByCode(String merchantCode) {
        return merchantInfoDao.selectByCode(merchantCode);
    }

    /**
     * 获取微信支付商户配置数据
     *
     * @param merchantCode 商户编号
     * @param type         类型 1 公众号 2 微信支付
     * @return 微信支付商户配置数据
     * @throws Exception
     */
    @Override
    public MerchantConf getWechatMerchantConf(String merchantCode, Integer type) throws Exception {
        MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
        if (clientConf == null) {
            return null;
        }
        int typeVal = -1;
        if (type.intValue() == 1) {
            typeVal = clientConf.getIisConfWechatGzh().intValue();//没有配置微信公众号
        } else if (type.intValue() == 2) {
            typeVal = clientConf.getIisConfWechat().intValue();//没有配置微信支付
        }
        if (typeVal == 0) {
            merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
        }
        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + merchantCode);
        if (StringUtil.isBlank(json)) {
            return null;
        }
        MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
        return conf;
    }

}