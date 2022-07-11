package com.cloud.cang.wap.index.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.AlipayConfigure;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.model.wz.Announcement;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import com.cloud.cang.wap.index.dao.IndexDao;
import com.cloud.cang.wap.index.service.IndexService;
import javax.servlet.http.HttpServletRequest;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.wap.om.service.OrderRecordService;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import com.cloud.cang.wap.sh.service.MerchantInfoService;
import com.cloud.cang.weixin.api.SnsAPI;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


/**
 * 首页 Copyright (C) 2016 37cang All rights reserved Author: zhao Date: 2016-3-18
 * Description:IndexServiceImpl.java
 */
@Service
public class IndexServiceImpl extends GenericServiceImpl<Object, String> implements IndexService {

	@Autowired
    private IndexDao indexDao;
	@Autowired
	private ICached iCached;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private OrderRecordService orderRecordService;

	@Override
	public GenericDao<Object, String> getDao() {
		return indexDao;
	}

	/**
	 * 首页数据
	 * @param deviceVo 请求参数
	 */
	@Override
	public Map<String, Object> getMainData(DeviceVo deviceVo) throws Exception {
		Map<String, Object> map = new HashMap<>();
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据

		map.put("deviceVo", deviceVo);


		/*//是否直接调用开门
		if (StringUtil.isNotBlank(deviceVo.getOpenDoorKey())) {
			String deviceIdBySession = (String) iCached.get(deviceVo.getOpenDoorKey());
			if (StringUtil.isNotBlank(deviceIdBySession) && deviceIdBySession.equals(deviceVo.getDeviceId())) {
				map.put("isAutoOpen", 1);//是否主动开门
				openParam.put("openDoorKey",deviceVo.getOpenDoorKey());
			}
		}*/
		//获取客户端配置数据
		map = this.getMerchantClientConfByMap(map, deviceVo.getMerchantCode());

		//判断是否有待支付的订单 未完成
		List<OrderRecord> orderRecords = orderRecordService.selectExceptionOrder(authVo.getId());
		if (null != orderRecords && orderRecords.size() > 0) {
			map.put("isExceptionOrder", 1);//有异常订单
		}
		Integer isupportPayWay = (Integer) map.get("isupportPayWay");
		if (isupportPayWay.intValue() == 10 || isupportPayWay.intValue() == 20) {
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(authVo.getId());
			//是否开通免密
			if (deviceVo.getOpenSource().equals("wechat")) {//微信
				map.put("isOpenFreeData", memberInfo.getIwechatOpen());
			} else if (deviceVo.getOpenSource().equals("alipay")) {//支付宝
				map.put("isOpenFreeData", memberInfo.getIaipayOpen());
			}
			if (isupportPayWay.intValue() == 20) {
				map.put("isRemindOpenAndClose", 1);//不能关闭
			} else {
				//是否提醒开通免密
				String isRemindOpen = (String) iCached.get("is_remind_open_free_data_" + authVo.getId());
				if (StringUtil.isBlank(isRemindOpen)) {
					map.put("isRemindOpen", 1);
				}
			}
		}
		map.put("userId", authVo.getId());
		map.put("userCode", authVo.getCode());
		map.put("userName", authVo.getUserName());
		if (authVo.isReplenishment()) {//是否补货员
			map.put("isReplenishment", 1);
		}
		if (StringUtil.isNotBlank(deviceVo.getDeviceId())) {
			//组装开门的参数
			Map<String, Object> openParam = this.assemblyOpenParam(deviceVo, 10);
			map.put("openParam", JSON.toJSONString(openParam));//开门参数
		}
		//首页 系统公告
		String json = (String) iCached.hget(RedisConst.WZ_REGIO_ANNOUNCEMENT+deviceVo.getMerchantCode(), RedisConst.WZ_REGIO_DETAIL_+"WZRE0053_"+deviceVo.getMerchantCode());
		if (StringUtil.isNotBlank(json)) {
			List<Announcement> temp = JSONObject.parseArray(json, Announcement.class);
			if (null != temp && temp.size() > 0) {
                Date date = new Date();
                List<Announcement> announcementList = new ArrayList<Announcement>();
                for (Announcement announcement : temp) {
                    if(date.before(announcement.getTvalidDate()) && date.after(announcement.getTpublishDate())) {
						announcementList.add(announcement);
                    }
                }
                map.put("announcementList", announcementList);
            }
		}
		//图片广告
		json = (String) iCached.hget(RedisConst.WZ_REGIO_ADVERTIS+deviceVo.getMerchantCode(), RedisConst.WZ_REGIO_DETAIL_+"WZRE0056_"+deviceVo.getMerchantCode());
		if (StringUtil.isNotBlank(json)) {
			List<Advertis> temp = JSONObject.parseArray(json, Advertis.class);
			if (null != temp && temp.size() > 0) {
				Date date = new Date();
				List<Advertis> advertisList = new ArrayList<Advertis>();
				for (Advertis advertis : temp) {
					if(date.before(advertis.getTendDate()) && date.after(advertis.getTstartDate())) {
						advertisList.add(advertis);
					}
				}
				map.put("advertisList", advertisList);
			}
		}

		return map;
	}
	/**
	 * 获取跳转首页路径
	 * @param request
	 * @throws Exception
	 */
	@Override
	public ModelAndView getIndexUrl(HttpServletRequest request) throws Exception {
		String rootPath = EvnUtils.getValue("wap.http.domain");
		Map<String, String> map = new HashMap<String, String>();
		//1、处理访问来源
		if (WapUtils.isWXRequest(request)) {
			map.put("openSource", "wechat");
		} else if (WapUtils.isAlipayRequest(request)) {
			map.put("openSource", "alipay");
		}
		//获取cookie商户编号
		String merchantCode = WapUtils.getMerchantCookie(request);
		if (StringUtil.isNotBlank(merchantCode)) {
			map.put("merchantCode", merchantCode);
		}
		//获取cookie设备信息
		String deviceStr = WapUtils.getDeviceCodeCookie(request);
		if (StringUtil.isNotBlank(deviceStr)) {
			map.put("deviceId", deviceStr.split("#//#")[1]);
			map.put("deviceCode", deviceStr.split("#//#")[0]);
			map.put("isScan", "1");
			String openDoorKey = (String) request.getSession().getAttribute("openDoorKey");
			if (StringUtil.isNotBlank(openDoorKey)) {
				map.put("openDoorKey", openDoorKey);
			}
		} else {
			map.put("isScan", "0");
		}
		return new ModelAndView("redirect:" + rootPath + "/index/main", map);
	}

	/**
	 * 获取微信授权地址
	 * @param httpRequest
	 * @return 返回地址
	 * @throws Exception
	 */
	public String getWxUrlForXW(HttpServletRequest httpRequest) throws Exception {
		//获取商户编号
		String merchantCode = WapUtils.getMerchantCookie(httpRequest);
		//获取商户微信配置数据
		MerchantConf conf = this.getWechatMerchantConf(merchantCode,1);
		if (null == conf) {//异常情况返回空
			return null;
		}
		String appId = conf.getSappId();
		String back_uri = httpRequest.getParameter("backUrl");
		if (StringUtil.isBlank(back_uri)){
			back_uri = WapUtils.getDefaultBackUrl(WebUtils.toHttp(httpRequest));
		}
		String callBackUrl =conf.getScallBackUrl() + "?back_uri="+back_uri;
		callBackUrl = callBackUrl.replaceAll("&code.*&", "&");
		String url = SnsAPI.connectOauth2Authorize(appId, callBackUrl, true,"");
		return url;
	}


	/***
	 * 获取支付宝授权地址
	 * @param httpRequest 请求
	 * @return 授权地址
	 * @throws Exception
	 */
	public String getAlipayUrlForXW(HttpServletRequest httpRequest) throws Exception {
		//获取商户编号
		String merchantCode = WapUtils.getMerchantCookie(httpRequest);
		//获取支付宝商户配置数据
		MerchantConf conf = this.getAlipayMerchantConf(merchantCode,1);
		if (null == conf) {//异常情况返回空
			return null;
		}
		String appId = conf.getSappId();
		String back_uri = httpRequest.getParameter("backUrl");
		if (StringUtil.isBlank(back_uri)){
			back_uri = WapUtils.getDefaultBackUrl(WebUtils.toHttp(httpRequest));
		}
		String callBackUrl =conf.getScallBackUrl() + "?back_uri="+back_uri;
		callBackUrl = callBackUrl.replaceAll("&code.*&", "&");
		String url = SnsAPI.connectOauth2Alipay(appId, callBackUrl,true,null);
		return url;
	}
	/**
	 * 获取AlipayClient 客户端 创建支付宝的sdk client
	 * @param merchantCode 商户编号

	 * @throws Exception
	 */
	public AlipayClient getAlipayClient(String merchantCode) throws Exception {

		MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
		if (null == clientConf) {//异常情况返回空
			return null;
		}
		if (clientConf.getIisConfAlipayShh().intValue() == 0) {//没有配置支付宝生活号
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
	 * 获取request 参数集合
	 * @param request http 请求
	 * @return
	 */
	@Override
	public Map<String, String> getAlipayRequestParams(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		// 获取组装参数
		Map paramsMap = request.getParameterMap();
		for (Iterator iter = paramsMap.keySet().iterator(); iter.hasNext(); ) {
			String name = (String) iter.next();
			String[] values = (String[]) paramsMap.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			map.put(name,valueStr);
		}
		return map;
	}
	/**
	 * 获取默认商户支付宝配置数据
	 * @return 支付宝商户配置数据
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
	 * 获取商户支付宝配置数据
	 * @param merchantCode 商户编号
	 * @throws Exception
	 */
	@Override
	public MerchantClientConf getMerchantClientConfByCode(String merchantCode) throws Exception {
		MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO,RedisConst.SH_CLIENT_CONFIG+merchantCode);
		return clientConf;
	}
	/**
	 * 组装开门参数
	 * @param types 开门类型
	 * @param deviceVo  设备数据
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> assemblyOpenParam(DeviceVo deviceVo, Integer types) throws Exception {
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据

		Map<String, Object> openParam = new HashMap<String, Object>();
		openParam.put("deviceId", deviceVo.getDeviceId());
		openParam.put("userId", authVo.getId());
		openParam.put("types", types);
		openParam.put("openDoorKey", deviceVo.getOpenDoorKey());

		return openParam;
	}

	@Override
	public Map<String, Object> getMerchantClientConfByMap(Map<String, Object> map, String merchantCode) throws Exception {
		//获取商户客户端配置信息
		MerchantClientConf clientConf = this.getMerchantClientConfByCode(merchantCode);
		if (null != clientConf.getIsupportPayWay()) {
			map.put("isupportPayWay", clientConf.getIsupportPayWay());
		} else {
			map.put("isupportPayWay", 20);
		}
		//首页标题
		if (clientConf != null && StringUtil.isNotBlank(clientConf.getStitle())) {
			map.put("indexTitle", clientConf.getStitle());
		} else {
			map.put("indexTitle", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_title").getSvalue());
		}
		//首页提示语
		if (clientConf != null && StringUtil.isNotBlank(clientConf.getSindexHint())) {
			map.put("indexHint", clientConf.getSindexHint());
		} else {
			map.put("indexHint", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_index_hint").getSvalue());
		}
		//首页图片
		if (clientConf != null && StringUtil.isNotBlank(clientConf.getSindexBgurl())) {
			map.put("indexBgUrl", clientConf.getSindexBgurl());
		} else {
			map.put("indexBgUrl", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_index_bg_url").getSvalue());
		}
		//开门成功 背景图片
		if (clientConf != null && StringUtil.isNotBlank(clientConf.getSsuccessBgurl())) {
			map.put("openBgUrl", clientConf.getSsuccessBgurl());
		} else {
			map.put("openBgUrl", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_open_success_bg_url").getSvalue());
		}
		//开门成功 提示语
		if (clientConf != null && StringUtil.isNotBlank(clientConf.getSsuccessHint())) {
			map.put("openHint", clientConf.getSsuccessHint());
		} else {
			map.put("openHint", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_open_success_hint").getSvalue());
		}
		return map;
	}
}
