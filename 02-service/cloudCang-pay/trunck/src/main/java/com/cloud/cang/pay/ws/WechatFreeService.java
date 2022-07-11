package com.cloud.cang.pay.ws;


import com.alibaba.fastjson.JSON;
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
 *
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
     * 微信支付分开启服务
     * @param signDto
     * @return
     */
    @RequestMapping(value = "/openService", method = RequestMethod.POST)
    public ResponseVo<String> openService(@RequestBody SignDto signDto) {
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
            return wechatFreeDataService.wechatOpenService(signDto);
        } catch (Exception e) {
            logger.error("微信支付分开启服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信支付分开启服务异常");
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
     *
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
     *
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
                logger.info("创建微信免密付款订单参数校验失败：{}", validateResult.getMsg());
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


	*/

    /**
     * 微信免密支付查询服务
     *
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


    /**
     * 微信支付分查询用户是否可使用服务
     *
     * @param queryUserAvaiDto
     * @return
     */
    @RequestMapping(value = "/queryUserAvailability", method = RequestMethod.POST)
    public ResponseVo<QueryUserAvaiResult> queryUserAvailability(@RequestBody QueryUserAvaiDto queryUserAvaiDto) {
        logger.debug("微信支付分查询用户服务开始...");
        ResponseVo<QueryUserAvaiResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
            if (StringUtil.isBlank(queryUserAvaiDto.getSmemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员Id不能为空！");
            }
            if (StringUtil.isBlank(queryUserAvaiDto.getSmerchantCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户编号不能为空");
            }
            return wechatFreeDataService.queryUserAvailability(queryUserAvaiDto);
        } catch (Exception e) {
            logger.error("微信支付分查询用户是否可使用服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信支付分查询用户是否可使用异常");
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

    /**
     * 微信支付分创建订单服务
     *
     * @param creatSmartretailOrderDto 支付服务参数
     * @return
     */
    @RequestMapping(value = "/creatSmartretailOrder", method = RequestMethod.POST)
    public ResponseVo<CreatSmartretailOrderResult> creatSmartretailOrder(@RequestBody CreatSmartretailOrderDto creatSmartretailOrderDto) {
        logger.debug("微信支付分创建订单服务开始...");
        ResponseVo<CreatSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
          /*  ResponseVo<String> validateResult = validatePaymentParam(freePaymentDto);
            if (!validateResult.isSuccess()) {
                logger.info("微信支付分创建订单参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }*/
            return orderRecordService.creatSmartretailOrder(creatSmartretailOrderDto);
        } catch (Exception e) {
            logger.error("微信支付分创建订单服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信支付分创建订单服务异常");
        return responseVo;
    }

    /**
     * 微信支付分创建订单服务
     *
     * @param creatSmartretailOrderDto 支付服务参数
     * @return
     */
    @RequestMapping(value = "/creatSmartretailOrderN", method = RequestMethod.POST)
    public ResponseVo<CreatSmartretailOrderResult> creatSmartretailOrderN(@RequestBody CreatSmartretailOrderDto creatSmartretailOrderDto) {
        logger.debug("微信支付分创建订单服务开始:{}", JSON.toJSONString(creatSmartretailOrderDto));
        ResponseVo<CreatSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
            if (StringUtil.isBlank(creatSmartretailOrderDto.getSmemberId())) {
                logger.info("微信支付分创建订单参数校验失败，会员ID为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败，会员ID为空");
            }
            if (StringUtil.isBlank(creatSmartretailOrderDto.getSmemberCode())) {
                logger.info("微信支付分创建订单参数校验失败，会员ID为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败，会员编号为空");
            }
            if (StringUtil.isBlank(creatSmartretailOrderDto.getSmerchantCode())) {
                logger.info("微信支付分创建订单参数校验失败，会员ID为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败，商户编号为空");
            }
            if (StringUtil.isBlank(creatSmartretailOrderDto.getDeviceId())) {
                logger.info("微信支付分创建订单参数校验失败，会员ID为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败，设备ID为空");
            }
            if (StringUtil.isBlank(creatSmartretailOrderDto.getDeviceCode())) {
                logger.info("微信支付分创建订单参数校验失败，会员ID为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败，设备编号为空");
            }
            return orderRecordService.creatSmartretailOrderN(creatSmartretailOrderDto);
        } catch (Exception e) {
            logger.error("微信支付分创建订单服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信支付分创建订单服务异常");
        return responseVo;
    }


    /**
     * 微信支付分查询订单服务
     *
     * @param querySmartretailOrderDto 查询订单参数
     * @return
     */
    @RequestMapping(value = "/querySmartretailOrder", method = RequestMethod.POST)
    public ResponseVo<QuerySmartretailOrderResult> querySmartretailOrder(@RequestBody QuerySmartretailOrderDto querySmartretailOrderDto) {
        logger.debug("微信支付分查询订单服务：{}", JSON.toJSONString(querySmartretailOrderDto));
        ResponseVo<QuerySmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
            if (StringUtil.isBlank(querySmartretailOrderDto.getSmerchantCode())) {
                logger.info("微信支付分查询订单服务参数校验失败,商户编号为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分查询订单服务参数校验失败,商户编号为空");
            }
            //校验基础参数
            if (StringUtil.isBlank(querySmartretailOrderDto.getOut_order_no())) {
                logger.info("微信支付分查询订单服务参数校验失败,商户系统内部服务订单号为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分查询订单服务参数校验失败,商户系统内部服务订单号为空");
            }
            return orderRecordService.querySmartretailOrder(querySmartretailOrderDto);
        } catch (Exception e) {
            logger.error("微信支付分查询订单服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信支付分查询订单服务异常");
        return responseVo;
    }

    /**
     * 微信支付分完结订单服务
     *
     * @param endSmartretailOrderDto 完结订单参数
     * @return
     */
    @RequestMapping(value = "/endSmartretailOrder", method = RequestMethod.POST)
    public ResponseVo<EndSmartretailOrderResult> endSmartretailOrder(@RequestBody EndSmartretailOrderDto endSmartretailOrderDto) {
        logger.debug("微信支付分完结订单服务开始...");
        ResponseVo<EndSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
          /*  ResponseVo<String> validateResult = validatePaymentParam(freePaymentDto);
            if (!validateResult.isSuccess()) {
                logger.info("微信支付分创建订单参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }*/
            return orderRecordService.endSmartretailOrder(endSmartretailOrderDto);
        } catch (Exception e) {
            logger.error("微信支付分完结订单服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信支付分完结订单服务异常");
        return responseVo;
    }

    /**
     * 微信支付分完结订单服务
     *
     * @param endSmartretailOrderDto 完结订单参数
     * @return
     */
    @RequestMapping(value = "/endSmartretailOrderN", method = RequestMethod.POST)
    public ResponseVo<EndSmartretailOrderResult> endSmartretailOrderN(@RequestBody EndSmartretailOrderDto endSmartretailOrderDto) {
        logger.debug("微信支付分完结订单服务开始...");
        ResponseVo<EndSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
          /*  ResponseVo<String> validateResult = validatePaymentParam(freePaymentDto);
            if (!validateResult.isSuccess()) {
                logger.info("微信支付分创建订单参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }*/
            return orderRecordService.endSmartretailOrderN(endSmartretailOrderDto);
        } catch (Exception e) {
            logger.error("微信支付分完结订单服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信支付分完结订单服务异常");
        return responseVo;
    }


    /**
     * 微信支付分生成订单  (创建订单,查询订单完结凭证,完结订单)
     *
     * @param creatSmartretailOrderDto 生成订单参数
     * @return
     */
    @RequestMapping(value = "/deneratingOrders", method = RequestMethod.POST)
    public ResponseVo<EndSmartretailOrderResult> deneratingOrders(@RequestBody CreatSmartretailOrderDto creatSmartretailOrderDto) {
        logger.debug("微信支付分生成订单服务开始...：{}", JSON.toJSONString(creatSmartretailOrderDto));
        ResponseVo<EndSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
            if (StringUtil.isBlank(creatSmartretailOrderDto.getOrderId())) {
                logger.info("微信支付分创建订单参数校验失败,订单Id为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,订单Id为空");
            }
            if (null == creatSmartretailOrderDto.getFees() || creatSmartretailOrderDto.getFees().isEmpty()) {
                logger.info("微信支付分创建订单参数校验失败,付费项目详情为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,付费项目详情为空");
            }
            if (null == creatSmartretailOrderDto.getTotal_amount()) {
                logger.info("微信支付分创建订单参数校验失败,订单总金额为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,订单总金额为空");
            }
            return orderRecordService.deneratingOrders(creatSmartretailOrderDto);
        } catch (Exception e) {
            logger.error("微信支付分生成订单服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信支付分生成订单服务异常");
        return responseVo;
    }

    /**
     * 微信支付分生成订单  (查询订单完结凭证,完结订单)
     *
     * @param queryAndEndSmartretailOrderDto 完结订单参数
     * @return
     */
    @RequestMapping(value = "/creatAndEndSmartretailOrder", method = RequestMethod.POST)
    public ResponseVo<EndSmartretailOrderResult> creatAndEndSmartretailOrder(@RequestBody QueryAndEndSmartretailOrderDto queryAndEndSmartretailOrderDto) {
        logger.debug("微信支付分生成订单服务开始...：{}", JSON.toJSONString(queryAndEndSmartretailOrderDto));
        ResponseVo<EndSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
         /*   if (StringUtil.isBlank(queryAndEndSmartretailOrderDto.getOrderId())) {
                logger.info("微信支付分创建订单参数校验失败,订单Id为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,订单Id为空");
            }*/
            if (null == queryAndEndSmartretailOrderDto.getFinish_type()) {
                logger.info("微信支付分完结订单参数校验失败,用户订单使用情况为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,用户订单使用情况为空");
            }
            if (queryAndEndSmartretailOrderDto.getFinish_type() == 2 && (null == queryAndEndSmartretailOrderDto.getFees() || queryAndEndSmartretailOrderDto.getFees().isEmpty())) {
                logger.info("微信支付分完结订单参数校验失败,付费项目详情为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,付费项目详情为空");
            }
            if (queryAndEndSmartretailOrderDto.getFinish_type() == 2 && null == queryAndEndSmartretailOrderDto.getTotal_amount()) {
                logger.info("微信支付分完结订单参数校验失败,订单总金额为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,订单总金额为空");
            }
            if (queryAndEndSmartretailOrderDto.getFinish_type() == 2 && StringUtil.isBlank(queryAndEndSmartretailOrderDto.getOrderId())) {
                logger.info("微信支付分完结订单参数校验失败,订单ID为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,订单ID为空");
            }
            if (StringUtil.isBlank(queryAndEndSmartretailOrderDto.getSmerchantCode())) {
                logger.info("微信支付分完结订单参数校验失败,商户编号为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,商户编号为空");
            }
            if (StringUtil.isBlank(queryAndEndSmartretailOrderDto.getSmemberId())) {
                logger.info("微信支付分完结订单参数校验失败,会员ID为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,会员ID为空");
            }
            if (StringUtil.isBlank(queryAndEndSmartretailOrderDto.getDeviceId())) {
                logger.info("微信支付分完结订单参数校验失败,设备ID为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,设备Id为空");
            }
            if (queryAndEndSmartretailOrderDto.getFinish_type() == 1 && StringUtil.isBlank(queryAndEndSmartretailOrderDto.getCancel_reason())) {
                logger.info("微信支付分完结订单参数校验失败,订单取消原因为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信支付分创建订单参数校验失败,订单取消原因为空");
            }
            return orderRecordService.createAndEndSmartretailOrder(queryAndEndSmartretailOrderDto);
        } catch (Exception e) {
            logger.error("微信支付分完结订单服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信支付分完结订单服务异常");
        return responseVo;
    }

    /**
     * 微信支付分同步订单
     *
     * @param syncOrdersDto 同步订单参数
     * @return
     */
    @RequestMapping(value = "/syncOrders", method = RequestMethod.POST)
    public ResponseVo<SyncOrderResult> syncOrders(@RequestBody SyncOrdersDto syncOrdersDto) {
        logger.debug("微信支付分同步订单服务开始...：{}", syncOrdersDto.getOrderCode());
        ResponseVo<SyncOrderResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
          /*  if (StringUtil.isBlank(syncOrdersDto.getType())) {
                logger.info("微信支付分同步订单参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }*/
            return orderRecordService.syncOrders(syncOrdersDto);
        } catch (Exception e) {
            logger.error("微信支付分同步订单服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信支付分同步订单服务异常");
        return responseVo;
    }

    /**
     * 微信支付分撤销订单
     *
     * @param cancelSmartretailOrdersDto 生成订单参数
     * @return
     */
    @RequestMapping(value = "/cancelOrders", method = RequestMethod.POST)
    public ResponseVo<CancelSmartretailOrderResult> cancelOrders(@RequestBody CancelSmartretailOrdersDto cancelSmartretailOrdersDto) {
        logger.debug("微信支付分撤销订单服务开始...：{}", cancelSmartretailOrdersDto.getOrderCode());
        ResponseVo<CancelSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
          /*  ResponseVo<String> validateResult = validatePaymentParam(freePaymentDto);
            if (!validateResult.isSuccess()) {
                logger.info("微信支付分创建订单参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }*/
            return orderRecordService.cancelOrders(cancelSmartretailOrdersDto);
        } catch (Exception e) {
            logger.error("微信支付分撤销订单服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("微信支付分撤销订单服务异常");
        return responseVo;
    }

}
