package com.cloud.cang.pay.ws;


import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.pay.*;
import com.cloud.cang.pay.hy.service.FreeDataService;
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
 * 支付宝代扣服务
 * @author zhouhong
 */
@RestController
@RequestMapping("/alipayFree")
@RegisterRestResource
public class AlipayFreeService {

	@Autowired
	private FreeDataService freeDataService;
	@Autowired
	private OrderRecordService orderRecordService;
	private static final Logger logger = LoggerFactory.getLogger(AlipayFreeService.class);


	/***
	 * 免密签约（代扣协议）
	 * @param signDto
	 * @return
	 */
	@RequestMapping(value = "/sign", method = RequestMethod.POST)
	public ResponseVo<String> sign(@RequestBody SignDto signDto) {
		logger.debug("支付宝免密签约服务开始...");
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
			return freeDataService.alipaySign(signDto);
		} catch (Exception e) {
			logger.error("支付宝免密签约服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("支付宝免密签约异常");
		return responseVo;
	}

	/***
	 * 免密协议查询（代扣协议）
	 * @param querySignDto
	 * @return
	 */
	@RequestMapping(value = "/querySign", method = RequestMethod.POST)
	public ResponseVo<SignResult> querySign(@RequestBody QuerySignDto querySignDto) {
		logger.debug("支付宝免密协议查询服务开始...");
		ResponseVo<SignResult> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			if (StringUtil.isBlank(querySignDto.getSmemberId())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("签约用户信息不能为空");
			}
			if (StringUtil.isBlank(querySignDto.getSmerchantCode())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("签约商户编号不能为空");
			}
			return freeDataService.alipayQuerySign(querySignDto);
		} catch (Exception e) {
			logger.error("支付宝免密协议查询服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("支付宝免密协议查询异常");
		return responseVo;
	}

	/**
	 * 支付宝解约服务
	 * @param unsignDto
	 * @return
	 */
	@RequestMapping(value = "/unsign", method = RequestMethod.POST)
	public ResponseVo<String> unsign(@RequestBody UnsignDto unsignDto) {
		logger.debug("支付宝免密解约服务开始...");
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
			return freeDataService.alipayUnsign(unsignDto);
		} catch (Exception e) {
			logger.error("支付宝免密解约服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("支付宝免密解约异常");
		return responseVo;
	}


	/**
	 * 支付宝免密支付服务
	 * @param freePaymentDto 支付服务参数
	 * @return
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public ResponseVo<FreePaymentResult> payment(@RequestBody FreePaymentDto freePaymentDto) {
		logger.debug("支付宝免密支付服务开始...");
		ResponseVo<FreePaymentResult> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			ResponseVo<String> validateResult = validatePaymentParam(freePaymentDto);
			if (!validateResult.isSuccess()) {
				logger.info("创建支付宝免密付款订单参数校验失败：{}",validateResult.getMsg());
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
			}
			return orderRecordService.createAlipayFreePay(freePaymentDto);
		} catch (Exception e) {
			logger.error("支付宝创建免密支付服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("创建支付订单异常");
		return responseVo;
	}

	/**
	 * 支付宝免密支付签约服务
	 * @param freePaymentDto 支付服务参数
	 * @return
	 */
	@RequestMapping(value = "/paymentAndSign", method = RequestMethod.POST)
	public ResponseVo<String> paymentAndSign(@RequestBody FreePaymentDto freePaymentDto) {
		logger.debug("支付宝免密支付签约服务开始...");
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			ResponseVo<String> validateResult = validatePaymentParam(freePaymentDto);
			if (!validateResult.isSuccess()) {
				logger.info("创建支付宝免密付款签约订单参数校验失败：{}",validateResult.getMsg());
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
			}
			return orderRecordService.createAlipayFreePayAndSign(freePaymentDto);
		} catch (Exception e) {
			logger.error("支付宝创建免密支付签约服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("创建支付签约订单异常");
		return responseVo;
	}


	/**
	 * 支付宝免密支付查询服务
	 * @param paymentDto 免密支付订单查询参数
	 * @return
	 */
	@RequestMapping(value = "/queryPayment", method = RequestMethod.POST)
	public ResponseVo<QueryPaymentResult> queryPayment(@RequestBody PaymentDto paymentDto) {
		logger.debug("支付宝免密支付订单查询服务开始...");
		ResponseVo<QueryPaymentResult> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			if (StringUtil.isBlank(paymentDto.getOutTradeNo()) && StringUtil.isBlank(paymentDto.getTradeNo())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("支付订单商户编号和支付宝交易号不能同时为空");
			}
			if (StringUtil.isBlank(paymentDto.getSmerchantCode())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户编号不能为空");
			}
			return orderRecordService.queryAlipayFreePay(paymentDto);
		} catch (Exception e) {
			logger.error("支付宝免密支付订单查询服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("支付订单查询异常");
		return responseVo;
	}


	/**
	 * 支付宝免密支付撤销服务
	 * @param paymentDto 免密支付订单撤销参数
	 * @return
	 */
	@RequestMapping(value = "/cancelPayment", method = RequestMethod.POST)
	public ResponseVo<String> cancelPayment(@RequestBody PaymentDto paymentDto) {
		logger.debug("支付宝免密支付订单撤销服务开始...");
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			if (StringUtil.isBlank(paymentDto.getOutTradeNo()) && StringUtil.isBlank(paymentDto.getTradeNo())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("支付订单商户编号和支付宝交易号不能同时为空");
			}
			if (StringUtil.isBlank(paymentDto.getSmerchantCode())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户编号不能为空");
			}
			return orderRecordService.cancelAlipayFreePay(paymentDto);
		} catch (Exception e) {
			logger.error("支付宝免密支付订单撤销服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("支付订单撤销异常");
		return responseVo;
	}


	/**
	 * 支付宝免密支付关闭服务
	 * @param paymentDto 免密支付订单关闭参数
	 * @return
	 */
	@RequestMapping(value = "/closePayment", method = RequestMethod.POST)
	public ResponseVo<String> closePayment(@RequestBody PaymentDto paymentDto) {
		logger.debug("支付宝免密支付订单关闭服务开始...");
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			if (StringUtil.isBlank(paymentDto.getOutTradeNo()) && StringUtil.isBlank(paymentDto.getTradeNo())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("支付订单商户编号和支付宝交易号不能同时为空");
			}
			if (StringUtil.isBlank(paymentDto.getSmerchantCode())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户编号不能为空");
			}
			return orderRecordService.closeAlipayFreePay(paymentDto);
		} catch (Exception e) {
			logger.error("支付宝免密支付订单关闭服务异常：", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("支付订单关闭异常");
		return responseVo;
	}

	private ResponseVo<String> validatePaymentParam(FreePaymentDto freePaymentDto) {
		logger.info("创建支付宝免密付款申请校验参数开始.....参数：{}", freePaymentDto);
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
		logger.debug("创建支付宝免密付款申请校验参数成功.....");
		return ResponseVo.getSuccessResponse();
	}
}
