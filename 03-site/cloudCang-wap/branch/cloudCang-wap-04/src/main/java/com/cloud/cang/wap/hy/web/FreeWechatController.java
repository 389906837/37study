package com.cloud.cang.wap.hy.web;



import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.common.RequestUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.pay.*;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import com.cloud.cang.wap.index.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @author zhouhong
 * 微信免密支付 控制层
 */
@Controller
@RequestMapping("/personal")
public class FreeWechatController {

	private static final Logger logger = LoggerFactory.getLogger(FreeWechatController.class);

	@Autowired
	private IndexService indexService;
	@Autowired
	private ICached iCached;
	@Autowired
	private MemberInfoService memberInfoService;

	/**
	 * 微信免密签约
	 * @return
	 */
	@RequestMapping("/wechatSign")
	public ModelAndView wechatSign(HttpServletRequest request) {
		try {
			AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(authVo.getId());
			if (null != memberInfo && (memberInfo.getIwechatOpen() == null || memberInfo.getIwechatOpen().intValue() == 0)) {//免密未开通
				String merchantCode = authVo.getSmerchantCode();
				//获取商户客户端配置
				MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + authVo.getSmerchantCode());
				if (clientConf == null || clientConf.getIisConfWechat() == null || clientConf.getIisConfWechat().intValue() == 0){
					merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
				}
				SignDto signDto = new SignDto();
				signDto.setSmerchantCode(merchantCode);
				signDto.setSmemberId(authVo.getId());
				signDto.setSmemberCode(authVo.getCode());
				signDto.setSmemberName(authVo.getUserName());
				signDto.setSmemberMerchantCode(authVo.getSmerchantCode());
				signDto.setSip(RequestUtils.getIpAddr(request));
				RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_SIGN);// 服务名称
				// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
				invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() { });
				invoke.setRequestObj(signDto); // post 参数
				ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
				if (!resVO.isSuccess()) {
					logger.error("微信签约失败{}", resVO.getMsg());
					return WapUtils.errorDealWith("-1", resVO.getMsg());
				}
				return new ModelAndView("redirect:" + resVO.getData());
			} else {
				return WapUtils.errorDealWith("10010", "");
			}
		} catch (Exception e) {
			logger.error("微信免密签约异常:{}", e);
			return WapUtils.errorDealWith("10008","");
		}
	}
	/**
	 * 微信免密协议查询
	 * @return
	 */
	@RequestMapping("/wechatQuerySign")
	public ModelAndView wechatQuerySign(HttpServletRequest request) {
		try {
			AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
			String merchantCode = authVo.getSmerchantCode();
			//获取商户客户端配置
			MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + authVo.getSmerchantCode());
			if (clientConf == null || clientConf.getIisConfWechat() == null || clientConf.getIisConfWechat().intValue() == 0){
				merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
			}
			QuerySignDto querySignDto = new QuerySignDto();
			querySignDto.setSmerchantCode(merchantCode);
			querySignDto.setSmemberId(authVo.getId());
			RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_QUERY_SIGN);// 服务名称
			// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
			invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<WechatSignResult>>() { });
			invoke.setRequestObj(querySignDto); // post 参数
			ResponseVo<WechatSignResult> resVO = (ResponseVo<WechatSignResult>) invoke.invoke();
			if (!resVO.isSuccess()) {
				logger.error("用户微信免密协议查询异常：{}", resVO.getMsg());
				return WapUtils.errorDealWith("-1", resVO.getMsg());
			}
			logger.info("微信免密协议查询返回参数:{}", resVO.getData());
			return indexService.getIndexUrl(request);
		} catch (Exception e) {
			logger.error("微信免密协议查询异常:{}", e);
			return WapUtils.errorDealWith("10008","");
		}
	}

	/**
	 * 微信免密解约
	 * @return
	 */
	@RequestMapping("/wechatUnsign")
	public ModelAndView wechatUnsign(HttpServletRequest request) {
		try {
			AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
			if (authVo.getIwechatOpen() == null || authVo.getIwechatOpen().intValue() == 0) {//免密未开通
				return WapUtils.errorDealWith("10011", "");
			}
			String merchantCode = authVo.getSmerchantCode();
			//获取商户客户端配置
			MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + authVo.getSmerchantCode());
			if (clientConf == null || clientConf.getIisConfWechat() == null || clientConf.getIisConfWechat().intValue() == 0){
				merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
			}
			UnsignDto unsignDto = new UnsignDto();
			unsignDto.setSmerchantCode(merchantCode);
			unsignDto.setSmemberId(authVo.getId());
			unsignDto.setSmemberMerchantCode(authVo.getSmerchantCode());
			unsignDto.setSip(RequestUtils.getIpAddr(request));
			RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_UNSIGN);// 服务名称
			// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
			invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() { });
			invoke.setRequestObj(unsignDto); // post 参数
			ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
			if (!resVO.isSuccess()) {
				logger.error("用户微信免密解约异常：{}", resVO.getMsg());
				return WapUtils.errorDealWith("-1", resVO.getMsg());
			}
			return indexService.getIndexUrl(request);
		} catch (Exception e) {
			logger.error("微信免密解约异常:{}", e);
			return WapUtils.errorDealWith("10008","");
		}
	}


	/**
	 * 微信免密支付
	 * @return
	 *//*
	@RequestMapping("/freePayment")
	public ModelAndView freePayment(HttpServletRequest request) {
		try {
			AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
			String merchantCode = authVo.getSmerchantCode();
			//获取商户客户端配置
			MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + authVo.getSmerchantCode());
			if (clientConf == null || clientConf.getIisConfAlipay() == null || clientConf.getIisConfAlipay().intValue() == 0){
				merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
			}
			FreePaymentDto freePaymentDto = new FreePaymentDto();
			freePaymentDto.setBody("iphone 6s");
			freePaymentDto.setIisIgnoreStatus(1);
			freePaymentDto.setIpayWay(30);
			freePaymentDto.setSmemberCode(authVo.getCode());
			freePaymentDto.setSmemberId(authVo.getId());
			freePaymentDto.setSmemberName(authVo.getUserName());
			freePaymentDto.setSmerchantCode(merchantCode);
			freePaymentDto.setSorderId("f6befd4f4566e1fgdsfg000c293er32");
			//freePaymentDto.setSpayScene("bar_code");
			freePaymentDto.setSremark("iphone 6s");
			freePaymentDto.setSsubject("37cang订单");

			RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_PAYMENT);// 服务名称
			// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
			invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() { });
			invoke.setRequestObj(freePaymentDto); // post 参数
			ResponseVo<FreePaymentResult> resVO = (ResponseVo<FreePaymentResult>) invoke.invoke();
			if (!resVO.isSuccess()) {
				logger.error("用户微信免密支付异常：{}", resVO.getMsg());
				return WapUtils.errorDealWith("-1", resVO.getMsg());
			}
			logger.info("微信免密支付成功：{}", resVO.getData());
			return indexService.getIndexUrl(request);
		} catch (Exception e) {
			logger.error("微信免密支付异常:{}", e);
			return WapUtils.errorDealWith("10008","");
		}
	}

	*//**
	 * 微信退款
	 * @return
	 *//*
	@RequestMapping("/refund")
	public ModelAndView refund(HttpServletRequest request) {
		try {
			AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
			String merchantCode = authVo.getSmerchantCode();
			//获取商户客户端配置
			MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + authVo.getSmerchantCode());
			if (clientConf == null || clientConf.getIisConfAlipay() == null || clientConf.getIisConfAlipay().intValue() == 0){
				merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
			}
			RefundApplyDto refundApplyDto = new RefundApplyDto();
			refundApplyDto.setSmerchantCode(merchantCode);
			refundApplyDto.setFrefundMoney(new BigDecimal("0.01"));
			refundApplyDto.setFtotalMoney(new BigDecimal("1"));
			refundApplyDto.setOperName("张三");
			refundApplyDto.setSmemberCode(authVo.getCode());
			refundApplyDto.setSmemberId(authVo.getId());
			refundApplyDto.setSmemberName(authVo.getUserName());
			refundApplyDto.setSorderCode("OR2018032600001");
			refundApplyDto.setSorderId("f6befd4f4566e1fgdsfg000c293er32");
			refundApplyDto.setSremark("测试退款");
			refundApplyDto.setStransactionId("2018032721001004210596600527");
			refundApplyDto.setIpayType(40);
			//refundApplyDto.setOutTradeNo("OR2018032600001");

			RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.REFUND_APPLY);// 服务名称
			// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
			invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<HashMap<String, Object>>>() { });
			invoke.setRequestObj(refundApplyDto); // post 参数
			ResponseVo<HashMap<String, Object>> resVO = (ResponseVo<HashMap<String, Object>>) invoke.invoke();
			if (!resVO.isSuccess()) {
				logger.error("微信免密支付订单查询异常：{}", resVO.getMsg());
				return WapUtils.errorDealWith("-1", resVO.getMsg());
			}
			logger.info("微信免密支付订单查询成功：{}", resVO.getData());
			return indexService.getIndexUrl(request);
		} catch (Exception e) {
			logger.error("微信免密支付订单查询异常:{}", e);
			return WapUtils.errorDealWith("10008","");
		}
	}
	*//**
	 * 微信免密支付订单查询
	 * @return
	 *//*
	@RequestMapping("/queryFreePayment")
	public ModelAndView queryFreePayment(HttpServletRequest request) {
		try {
			AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
			String merchantCode = authVo.getSmerchantCode();
			//获取商户客户端配置
			MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + authVo.getSmerchantCode());
			if (clientConf == null || clientConf.getIisConfAlipay() == null || clientConf.getIisConfAlipay().intValue() == 0){
				merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
			}
			PaymentDto paymentDto = new PaymentDto();
			paymentDto.setSmerchantCode(merchantCode);
			paymentDto.setOutTradeNo("OR2018032600001");

			RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_QUERY_PAYMENT);// 服务名称
			// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
			invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<QueryPaymentResult>>() { });
			invoke.setRequestObj(paymentDto); // post 参数
			ResponseVo<QueryPaymentResult> resVO = (ResponseVo<QueryPaymentResult>) invoke.invoke();
			if (!resVO.isSuccess()) {
				logger.error("微信免密支付订单查询异常：{}", resVO.getMsg());
				return WapUtils.errorDealWith("-1", resVO.getMsg());
			}
			logger.info("微信免密支付订单查询成功：{}", resVO.getData());
			return indexService.getIndexUrl(request);
		} catch (Exception e) {
			logger.error("微信免密支付订单查询异常:{}", e);
			return WapUtils.errorDealWith("10008","");
		}
	}*/

	/**
	 * 微信签约成功返回页面
	 * @return
	 */
	@RequestMapping("/wechatSginNotify")
	public ModelAndView wechatSginNotify(HttpServletRequest request) {
		try {
			/*Map<String, String> map = indexService.getAlipayRequestParams(request);
			logger.debug("获得的参数=" + map);
			//获取商户配置信息
			MerchantConf conf = indexService.getDefaultWechatMerchantConf();
			//MerchantConf conf = indexService.getAlipayMerchantConf(merchantCode, 2);
			//验证签名
			boolean flag = AlipaySignature.rsaCheckV1(map, conf.getSpublicKey(), AlipayConfigure.charset, AlipayConfigure.sign_type);
			if (flag) {//验证签名成功
				logger.info("微信签约返回商户验证签名成功：{}", map);
				if(!map.get("code").equals("10000")) {
					return WapUtils.errorDealWith("-1", map.get("sub_msg"));
				}
				map.put("sip", RequestUtils.getIpAddr(request));
				flag = memberInfoService.dealwithAlipaySign(conf.getSmerchantCode(), map, conf);
				if (flag) {
					//更新登录用户的session中的值
					AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
					authVo.setIaipayOpen(1);
					SessionUserUtils.setSessionAttributeForUserDtl(authVo);
					return indexService.getIndexUrl(request);
				} else {
					return WapUtils.errorDealWith("-1","代扣签约异常，请联系客服");
				}
			}*/
			logger.error("微信验证签名失败");
		} catch (Exception e) {
			logger.error("微信签约返回页面异常：{}", e);
		}
		return WapUtils.errorDealWith("10008","");
	}
/*


	*/
/**
	 * 微信支付并签约成功返回页面
	 * @return
	 *//*

	@RequestMapping("/wechatFreePaymentNotify")
	public ModelAndView wechatFreePaymentNotify(HttpServletRequest request) {
		try {
			Map<String, String> map = indexService.getAlipayRequestParams(request);
			logger.debug("获得的参数=" + map);
			//获取商户配置信息
			MerchantConf conf = indexService.getDefaultAlipayMerchantConf();
			//验证签名
			boolean flag = AlipaySignature.rsaCheckV1(map, conf.getSpublicKey(), AlipayConfigure.charset, AlipayConfigure.sign_type);
			if (flag) {//验证签名成功
				logger.info("微信支付并签约返回商户验证签名成功：{}", map);
				if(!map.get("code").equals("10000")) {
					return WapUtils.errorDealWith("-1", map.get("sub_msg"));
				}
				//不处理 返回支付成功页面 和 活动信息 ？
				return indexService.getIndexUrl(request);
			}
			logger.error("微信验证签名失败");
		} catch (Exception e) {
			logger.error("微信支付并签约返回页面异常：{}", e);
		}
		return WapUtils.errorDealWith("10008","");
	}
*/

}
