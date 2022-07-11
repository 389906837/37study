package com.cloud.cang.pay.sh.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.AlipayConfigure;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.pay.sh.dao.MerchantInfoDao;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.pay.sh.service.MerchantInfoService;

@Service
public class MerchantInfoServiceImpl extends GenericServiceImpl<MerchantInfo, String> implements
		MerchantInfoService {

	@Autowired
	MerchantInfoDao merchantInfoDao;
	@Autowired
	private ICached iCached;
	
	@Override
	public GenericDao<MerchantInfo, String> getDao() {
		return merchantInfoDao;
	}

	/**
	 * 获取商户信息
	 * @param merchantCode 商户编号
	 * @return
	 */
	@Override
	public MerchantInfo selectByCode(String merchantCode) {
		return merchantInfoDao.selectByCode(merchantCode);
	}
	/**
	 * 获取AlipayClient 客户端 创建支付宝的sdk client
	 * @param merchantCode 商户编号
	 * @throws Exception
	 */
	public AlipayClient getAlipayClient(String merchantCode) throws Exception {
		//获取商户配置
		MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
		if (null == clientConf) {//异常情况返回空
			return null;
		}
		if (clientConf.getIisConfAlipay().intValue() == 0) {//没有配置支付宝支付 使用默认商户
			merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
		}
		String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_ALIPAY_CONFIG + merchantCode);
		if (StringUtil.isBlank(json)) {//异常情况返回空
			return null;
		}
		MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
		if (null == conf) {//异常情况返回空
			return null;
		}
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigure.UNIFIED_ORDER_API, conf.getSappId(),
				conf.getSprivateKey(), AlipayConfigure.format, AlipayConfigure.charset, conf.getSpublicKey(), AlipayConfigure.sign_type);
		return alipayClient;
	}

	/**
	 * 获取支付宝商户配置数据
	 * @param merchantCode 商户编号
	 * @param type 类型 1 生活号 2 支付宝支付
	 * @return 支付宝商户配置数据
	 * @throws Exception
	 */
	@Override
	public MerchantConf getAlipayMerchantConf(String merchantCode, Integer type) throws Exception {
		MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO,RedisConst.SH_CLIENT_CONFIG+merchantCode);
		if (clientConf == null) {
			return null;
		}
		int typeVal = -1;
		if (type.intValue() == 1) {
			typeVal = clientConf.getIisConfAlipayShh().intValue();//没有配置支付宝生活号
		} else if (type.intValue() == 2) {
			typeVal = clientConf.getIisConfAlipay().intValue();//没有配置支付宝支付
		}
		if (typeVal == 0) {//没有配置获取默认商户编号
			merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
		}
		String json = (String) iCached.hget(RedisConst.MERCHANT_INFO,RedisConst.SH_ALIPAY_CONFIG+merchantCode);
		if (StringUtil.isBlank(json)) {
			return null;
		}
		MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
		return conf;
	}

	/**
	 * 获取支付宝商户配置数据
	 * @param merchantCode 商户编号
	 * @return 支付宝商户配置数据
	 * @throws Exception
	 */
	@Override
	public MerchantConf getAlipayMerchantConf(String merchantCode) throws Exception {
		String json = (String) iCached.hget(RedisConst.MERCHANT_INFO,RedisConst.SH_ALIPAY_CONFIG+merchantCode);
		if (StringUtil.isBlank(json)) {
			return null;
		}
		MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
		return conf;
	}
	/**
	 * 获取微信商户配置数据
	 * @param merchantCode 商户编号
	 * @return 微信商户配置数据
	 * @throws Exception
	 */
	@Override
	public MerchantConf getWechatMerchantConf(String merchantCode) throws Exception {
		String json = (String) iCached.hget(RedisConst.MERCHANT_INFO,RedisConst.SH_WECHAT_CONFIG+merchantCode);
		if (StringUtil.isBlank(json)) {
			return null;
		}
		MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
		return conf;
	}

	/**
	 * 获取微信支付商户配置数据
	 * @param merchantCode 商户编号
	 * @param type 类型 1 公众号 2 微信支付
	 * @return 微信支付商户配置数据
	 * @throws Exception
	 */
	@Override
	public MerchantConf getWechatMerchantConf(String merchantCode, Integer type) throws Exception {
		MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO,RedisConst.SH_CLIENT_CONFIG+merchantCode);
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
			merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
		}
		String json = (String) iCached.hget(RedisConst.MERCHANT_INFO,RedisConst.SH_WECHAT_CONFIG+merchantCode);
		if (StringUtil.isBlank(json)) {
			return null;
		}
		MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
		return conf;
	}
	/**
	 * 获取默认商户支付宝配置数据
	 * @return 商户支付宝配置数据
	 * @throws Exception
	 */
	@Override
	public MerchantConf getDefaultAlipayMerchantConf() throws Exception {
		String merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
		String json = (String) iCached.hget(RedisConst.MERCHANT_INFO,RedisConst.SH_ALIPAY_CONFIG+merchantCode);
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