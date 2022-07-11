package com.cloud.cang.wap.index.service;

import com.alipay.api.AlipayClient;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;



public interface IndexService extends GenericService<Object, String> {

	/**
	 * 首页数据
	 * @param deviceVo 请求参数
	 */
	Map<String, Object> getMainData(DeviceVo deviceVo) throws Exception;

	/**
	 * 获取跳转首页路径
	 * @param request
	 * @throws Exception
	 */
	ModelAndView getIndexUrl(HttpServletRequest request) throws Exception;
	/**
	 * 获取微信授权地址
	 * @param httpRequest 请求
	 * @return 返回地址
	 * @throws Exception
	 */
	String getWxUrlForXW(HttpServletRequest httpRequest) throws Exception;
	/***
	 * 获取支付宝授权地址
	 * @param httpRequest 请求
	 * @return 授权地址
	 * @throws Exception
	 */
	String getAlipayUrlForXW(HttpServletRequest httpRequest) throws Exception;
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
	 * 获取微信支付商户配置数据
	 * @param merchantCode 商户编号
	 * @param type 类型 1 公众号 2 微信支付
	 * @return 微信支付商户配置数据
	 * @throws Exception
	 */
	MerchantConf getWechatMerchantConf(String merchantCode, Integer type) throws Exception;

	/**
	 * 获取request 参数集合
	 * @param request http 请求
	 * @return
	 */
	Map<String,String> getAlipayRequestParams(HttpServletRequest request);
	/**
	 * 获取默认商户支付宝配置数据
	 * @return 支付宝商户配置数据
	 * @throws Exception
	 */
	MerchantConf getDefaultAlipayMerchantConf() throws Exception;
	/**
	 * 获取商户客户端配置
	 * @param merchantCode 商户编号
	 * @throws Exception
	 */
	MerchantClientConf getMerchantClientConfByCode(String merchantCode) throws Exception;

	/**
	 * 组装开门参数
	 * @param types 开门类型
	 * @param deviceVo  设备数据
	 * @throws Exception
	 */
	Map<String, Object> assemblyOpenParam(DeviceVo deviceVo, Integer types) throws Exception;

	/***
	 * 获取商户数据存到map
	 * @param map 存储数据的map
	 * @param merchantCode 商户编号
	 * @throws Exception
	 */
	Map<String, Object> getMerchantClientConfByMap(Map<String, Object> map, String merchantCode) throws Exception;
}
