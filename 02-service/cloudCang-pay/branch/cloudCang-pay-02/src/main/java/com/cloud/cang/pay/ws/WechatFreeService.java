package com.cloud.cang.pay.ws;


import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.pay.*;
import com.cloud.cang.pay.hy.service.WechatFreeDataService;
import com.cloud.cang.pay.om.service.OrderRecordService;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信代扣服务
 * @author zhouhong
 */
@RestController
@RequestMapping("/wechatFree")
@RegisterRestResource
public class WechatFreeService {

	@Autowired
	private WechatFreeDataService wechatFreeDataService;
	@Autowired
	private OrderRecordService orderRecordService;
	private static final Logger logger = LoggerFactory.getLogger(WechatFreeService.class);

	/***
	 * 免密签约（代扣协议）
	 * @param signDto
	 * @return
	 */
	@RequestMapping(value = "/sign", method = RequestMethod.POST)
	public ResponseVo<String> sign(@RequestBody SignDto signDto) {
		logger.debug("微信免密签约服务开始...");
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			if (StringUtil.isBlank(signDto.getSmemberId())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("签约用户信息不能为空");
			}
			if (StringUtil.isBlank(signDto.getSmerchantCode())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("签约商户编号不能为空");
			}
			if (StringUtil.isBlank(signDto.getSmemberMerchantCode())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员商户编号不能为空");
			}
			return wechatFreeDataService.wechatSign(signDto);
		} catch (Exception e) {
			logger.error("微信免密签约服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("微信免密签约异常");
		return responseVo;
	}

	/***
	 * 免密协议查询（代扣协议）
	 * @param querySignDto
	 * @return
	 */
	@RequestMapping(value = "/querySign", method = RequestMethod.POST)
	public ResponseVo<WechatSignResult> querySign(@RequestBody QuerySignDto querySignDto) {
		logger.debug("微信免密协议查询服务开始...");
		ResponseVo<WechatSignResult> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			if (StringUtil.isBlank(querySignDto.getSmemberId())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("签约用户信息不能为空");
			}
			if (StringUtil.isBlank(querySignDto.getSmerchantCode())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("签约商户编号不能为空");
			}
			return wechatFreeDataService.wechatQuerySign(querySignDto);
		} catch (Exception e) {
			logger.error("微信免密协议查询服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("微信免密协议查询异常");
		return responseVo;
	}

	/**
	 * 微信解约服务
	 * @param unsignDto
	 * @return
	 */
	@RequestMapping(value = "/unsign", method = RequestMethod.POST)
	public ResponseVo<String> unsign(@RequestBody UnsignDto unsignDto) {
		logger.debug("微信免密解约服务开始...");
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			if (StringUtil.isBlank(unsignDto.getSmemberId())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("解约用户信息不能为空");
			}
			if (StringUtil.isBlank(unsignDto.getSmerchantCode())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("解约商户编号不能为空");
			}
			if (StringUtil.isBlank(unsignDto.getSmemberMerchantCode())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员商户编号不能为空");
			}
			return wechatFreeDataService.wechatUnsign(unsignDto);
		} catch (Exception e) {
			logger.error("微信免密解约服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("微信免密解约异常");
		return responseVo;
	}


	/**
	 * 微信免密支付服务
	 * @param freePaymentDto 支付服务参数
	 * @return
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public ResponseVo<FreePaymentResult> payment(@RequestBody FreePaymentDto freePaymentDto) {
		logger.debug("微信免密支付服务开始...");
		ResponseVo<FreePaymentResult> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			ResponseVo<String> validateResult = validatePaymentParam(freePaymentDto);
			if (!validateResult.isSuccess()) {
				logger.info("创建微信免密付款订单参数校验失败：{}",validateResult.getMsg());
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
			}
			return orderRecordService.createWechatFreePay(freePaymentDto);
		} catch (Exception e) {
			logger.error("微信创建免密支付服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("创建支付订单异常");
		return responseVo;
	}

	/**
	 * 微信免密支付签约服务
	 * @param freePaymentDto 支付服务参数
	 * @return
	 *//*
	@RequestMapping(value = "/paymentAndSign", method = RequestMethod.POST)
	public ResponseVo<String> paymentAndSign(@RequestBody FreePaymentDto freePaymentDto) {
		logger.debug("微信免密支付签约服务开始...");
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			ResponseVo<String> validateResult = validatePaymentParam(freePaymentDto);
			if (!validateResult.isSuccess()) {
				logger.info("创建微信免密付款签约订单参数校验失败：{}",validateResult.getMsg());
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
			}
			return orderRecordService.createAlipayFreePayAndSign(freePaymentDto);
		} catch (Exception e) {
			logger.error("微信创建免密支付签约服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("创建支付签约订单异常");
		return responseVo;
	}


	*//**
	 * 微信免密支付查询服务
	 * @param paymentDto 免密支付订单查询参数
	 * @return
	 */
	@RequestMapping(value = "/queryPayment", method = RequestMethod.POST)
	public ResponseVo<QueryWechatFreePayResult> queryPayment(@RequestBody PaymentDto paymentDto) {
		logger.debug("微信免密支付订单查询服务开始...");
		ResponseVo<QueryWechatFreePayResult> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			if (StringUtil.isBlank(paymentDto.getOutTradeNo()) && StringUtil.isBlank(paymentDto.getTradeNo())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("支付订单商户编号和微信交易号不能同时为空");
			}
			if (StringUtil.isBlank(paymentDto.getSmerchantCode())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户编号不能为空");
			}
			return orderRecordService.queryWechatFreePay(paymentDto);
		} catch (Exception e) {
			logger.error("微信免密支付订单查询服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("支付订单查询异常");
		return responseVo;
	}

	private ResponseVo<String> validatePaymentParam(FreePaymentDto freePaymentDto) {
		logger.info("创建微信免密付款申请校验参数开始.....参数：{}", freePaymentDto);
		if (StringUtil.isBlank(freePaymentDto.getSmemberCode())) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户编号不能为空");
		} else if (StringUtil.isBlank(freePaymentDto.getSmemberId())) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员不能为空");
		} else if (StringUtil.isBlank(freePaymentDto.getSorderId())) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("付款订单不能为空");
		} else if (StringUtil.isBlank(freePaymentDto.getSsubject())) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("付款订单标题不能为空");
		}/* else if (StringUtil.isBlank(freePaymentDto.getSpayScene())) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单支付场景不能为空");
		}*/ else if (freePaymentDto.getIpayWay() == null) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("支付方式不能为空");
		}
		logger.debug("创建微信免密付款申请校验参数成功.....");
		return ResponseVo.getSuccessResponse();
	}
}
