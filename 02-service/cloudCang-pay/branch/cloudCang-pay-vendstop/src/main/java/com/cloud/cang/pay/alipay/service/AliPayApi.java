package com.cloud.cang.pay.alipay.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.cloud.cang.core.common.AlipayConfigure;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.alipay.vo.AliBaseData;
import com.cloud.cang.pay.alipay.vo.AliRefundData;
import com.cloud.cang.pay.alipay.vo.AliReqData;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * 支付宝支付创建预付款API
 * Created by Administrator on 2017/11/8.
 */
public class AliPayApi {

	private static final Logger logger = LoggerFactory.getLogger(AliPayApi.class);

	/**
	 * 订单支付申请
	 * @param reqData
	 * @param returnUrl
	 * @return
	 */
	public static String unifiedOrder(AliReqData reqData, MerchantConf conf, String returnUrl) {
		// 获得初始化的AlipayClient
		AlipayClient alipayClient = alipayClient(AlipayConfigure.UNIFIED_ORDER_API, conf);
		// 创建API对应的request
		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
		// 在公共参数中设置回跳和通知地址
		alipayRequest.setReturnUrl(returnUrl);
		//String notifyUrl = conf.getSpayCallBackUrl();
		String notifyUrl = GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "manually_pay_callback_url").getSvalue();
		if (StringUtil.isNotBlank(conf.getSauthToken())) {
			alipayRequest.putOtherTextParam("app_auth_token", conf.getSauthToken());
		}
		alipayRequest.setNotifyUrl(notifyUrl);
		// 填充业务参数
		alipayRequest.setBizContent(JSONObject.toJSONString(reqData));
		try {
			// 调用SDK生成表单
			String form = alipayClient.pageExecute(alipayRequest).getBody();
			return form;
		} catch (AlipayApiException e) {
			logger.error("调用SDK生成表单success",e);
		}
		return null;
	}


	/**
	 * 支付退款请求
	 * @param refundData
	 * @return
	 */
	public static AlipayTradeRefundResponse refundOrder(AliRefundData refundData, MerchantConf conf) {
		// 获得初始化的AlipayClient
		AlipayClient alipayClient = alipayClient(AlipayConfigure.UNIFIED_ORDER_API, conf);
		// 创建API对应的request
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		if (StringUtil.isNotBlank(conf.getSauthToken())) {
			request.putOtherTextParam("app_auth_token", conf.getSauthToken());
		}
		// 填充业务参数
		request.setBizContent(JSONObject.toJSONString(refundData));
		try {
			// 调用SDK生成表单
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			return response;
		} catch (AlipayApiException e) {
			logger.error("调用支付宝退款请求错误：{}",e);
		}
		return null;
	}

	/**
	 * 支付查询交易请求
	 * @param baseData 请求参数
	 * @return
	 */
	public static AlipayTradeQueryResponse queryOrder(AliBaseData baseData, MerchantConf conf) {
		// 获得初始化的AlipayClient
		AlipayClient alipayClient = alipayClient(AlipayConfigure.UNIFIED_ORDER_API, conf);
		// 创建API对应的request
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		if (StringUtil.isNotBlank(conf.getSauthToken())) {
			request.putOtherTextParam("app_auth_token", conf.getSauthToken());
		}
		// 填充业务参数
		request.setBizContent(JSONObject.toJSONString(baseData));
		try {
			// 调用SDK生成表单
			AlipayTradeQueryResponse response = alipayClient.execute(request);
			return response;
		} catch (AlipayApiException e) {
			logger.error("调用支付宝付款查询请求错误：{}",e);
		}
		return null;
	}
	/**
	 * 退款查询交易请求
	 * @param baseData 请求参数
	 * @return
	 */
	public static AlipayTradeFastpayRefundQueryResponse refuncQueryOrder(AliBaseData baseData, MerchantConf conf) {
		// 获得初始化的AlipayClient
		AlipayClient alipayClient = alipayClient(AlipayConfigure.UNIFIED_ORDER_API, conf);
		// 创建API对应的request
		AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
		if (StringUtil.isNotBlank(conf.getSauthToken())) {
			request.putOtherTextParam("app_auth_token", conf.getSauthToken());
		}
		// 填充业务参数
		request.setBizContent(JSONObject.toJSONString(baseData));
		try {
			// 调用SDK生成表单
			AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
			return response;
		} catch (AlipayApiException e) {
			logger.error("调用支付宝退款查询请求错误：{}",e);
		}
		return null;
	}

	/**
	 * 创建私有方法,url是需要动态传参
	 * @param url
	 * @return
	 */
	private static AlipayClient alipayClient(String url,MerchantConf conf){
		//获取商户配置信息
		AlipayClient alipayClient = new DefaultAlipayClient(url, conf.getSappId(),
				conf.getSprivateKey(), AlipayConfigure.format, AlipayConfigure.charset, conf.getSpublicKey(), AlipayConfigure.sign_type);
		return alipayClient;
	}

}
