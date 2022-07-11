package com.cloud.cang.pay.om.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AgreementSignParams;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.*;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.hy.WechatFreeData;
import com.cloud.cang.model.om.OrderPay;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sq.CreatApply;
import com.cloud.cang.model.sq.PayApply;
import com.cloud.cang.pay.*;
import com.cloud.cang.pay.common.utils.IdGenerator;
import com.cloud.cang.pay.hy.service.FreeDataService;
import com.cloud.cang.pay.hy.service.FreeOperLogService;
import com.cloud.cang.pay.hy.service.MemberInfoService;
import com.cloud.cang.pay.hy.service.WechatFreeDataService;
import com.cloud.cang.pay.om.dao.OrderRecordDao;
import com.cloud.cang.pay.om.service.OrderPayService;
import com.cloud.cang.pay.om.service.OrderRecordService;
import com.cloud.cang.pay.om.vo.AgreementParams;
import com.cloud.cang.pay.om.vo.FreePaymentVo;
import com.cloud.cang.pay.om.vo.PaymentVo;
import com.cloud.cang.pay.sb.service.DeviceInfoService;
import com.cloud.cang.pay.sh.service.MerchantInfoService;
import com.cloud.cang.pay.sq.service.CreatApplyService;
import com.cloud.cang.pay.sq.service.PayApplyService;
import com.cloud.cang.pay.wechat.notify.*;
import com.cloud.cang.pay.wechat.protocol.*;
import com.cloud.cang.pay.wechat.service.WxPayApi;
import com.cloud.cang.pay.wechat.util.httpclient.util.AesUtil;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderRecordServiceImpl extends GenericServiceImpl<OrderRecord, String> implements
		OrderRecordService {


	@Autowired
	private OrderRecordDao orderRecordDao;
	@Autowired
	private PayApplyService payApplyService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private FreeDataService freeDataService;
	@Autowired
	private WechatFreeDataService wechatFreeDataService;
	@Autowired
	private OrderPayService orderPayService;
	@Autowired
	private WxPayApi wxPayApi;
	@Autowired
	private FreeOperLogService freeOperLogService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private DeviceInfoService deviceInfoService;
	private static final Logger logger = LoggerFactory.getLogger(OrderRecordServiceImpl.class);
	@Autowired
	ICached iCached;
	@Autowired
	private CreatApplyService creatApplyService;


	@Override

	public GenericDao<OrderRecord, String> getDao() {
		return orderRecordDao;
	}


	/***
	 * 处理支付宝手动支付回调通知
	 * @param conf 商户配置信息 操作商户
	 * @param map 支付宝回调参数集合
	 */
	@Override
	public boolean dealwithAlipayPaymentNotify(MerchantConf conf, Map<String, String> map) {
		// 获取商户订单号
		String outTradePayNo = map.get("out_trade_no");
		logger.info("获取支付商户订单号=" + outTradePayNo);
		String outTradeNo = orderPayService.selectOrderCodeByPayNo(outTradePayNo);
		logger.info("获取商户订单号=" + outTradeNo);
		// 获取支付订单信息
		OrderRecord orderRecord = this.selectByOrderCode(outTradeNo);
		logger.info("获取的订单信息=" + orderRecord);
		if (orderRecord == null) {
			logger.info("订单编号：" + outTradeNo + "的订单不存在");
			return false;
		}
		if (orderRecord.getIstatus() == null ||
				orderRecord.getIstatus().intValue() == BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
			logger.info("支付宝手动支付订单状态异常或回调已处理，订单编号：{}", orderRecord.getSorderCode());
			return true;
		}
		// 获取支付付款申请信息
		PayApply payApply = payApplyService.selectByPrimaryKey(orderRecord.getSpayApplyId());
		if (payApply != null) {
			// 订单状态在待付款和付款申请才操作
			BigDecimal totalMount = new BigDecimal(map.get("total_amount"));
			if (payApply.getFactualPayAmount().doubleValue() != totalMount.doubleValue()) {
				throw new ServiceException("实付金额不正确");
			}
			if ("TRADE_SUCCESS".equals(map.get("trade_status"))) {
				// 成功处理
				paySuccess(outTradePayNo, orderRecord, payApply, map.get("trade_no"), map.get("gmt_payment"));
				paySuccessSyncOrder(orderRecord, map.get("gmt_payment"));
				logger.info("订单支付处理成功");
				return true;
			} else {
				// 失败处理
				payFail(outTradePayNo, orderRecord, map.get("trade_no"), map.get("gmt_close"), "trade_closed", "交易超时关闭");
			}
		}
		return false;
	}

	/**
	 * 处理微信手动支付回调通知
	 *
	 * @param conf          商户配置信息 操作商户
	 * @param payNotifyData 微信回调参数集合
	 * @throws Exception
	 */
	@Override
	public boolean dealwithWechatPaymentNotify(MerchantConf conf, PayNotifyData payNotifyData) throws Exception {
		// 获取商户订单号
		String outTradePayNo = payNotifyData.getOut_trade_no();
		logger.info("获取支付商户订单号=" + outTradePayNo);
		String outTradeNo = orderPayService.selectOrderCodeByPayNo(outTradePayNo);
		logger.info("获取商户订单号=" + outTradeNo);
		// 获取支付订单信息
		OrderRecord orderRecord = this.selectByOrderCode(outTradeNo);
		logger.info("获取的订单信息=" + orderRecord);
		if (orderRecord == null) {
			logger.info("订单编号：" + outTradeNo + "的订单不存在");
			return false;
		}
		if (orderRecord.getIstatus() == null ||
				orderRecord.getIstatus().intValue() == BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
			logger.info("微信手动支付订单状态异常或回调已处理，订单编号：{}", orderRecord.getSorderCode());
			return true;
		}
		// 获取支付付款申请信息
		PayApply payApply = payApplyService.selectByPrimaryKey(orderRecord.getSpayApplyId());
		if (payApply != null) {
			BigDecimal actualAmount = payApply.getFactualPayAmount().multiply(new BigDecimal("100"));
			if (actualAmount.compareTo(new BigDecimal(payNotifyData.getTotal_fee())) != 0) {
				throw new ServiceException("实付金额不正确");
			}
			if ("SUCCESS".equals(payNotifyData.getResult_code())) {
				// 成功处理
				paySuccess(outTradePayNo, orderRecord, payApply, payNotifyData.getTransaction_id(), payNotifyData.getTime_end());
				//手动支付成功调用同步接口
				paySuccessSyncOrder(orderRecord, payNotifyData.getTime_end());
				logger.info("订单支付处理成功");
				return true;
			} else {
				// 失败处理
				payFail(outTradePayNo, orderRecord, payNotifyData.getTransaction_id(), payNotifyData.getTime_end(), payNotifyData.getErr_code(), payNotifyData.getErr_code_des());
			}
		}
		return false;
	}

	/**
	 * 微信免密订单支付查询
	 *
	 * @param paymentDto 查询参数
	 */
	@Override
	public ResponseVo<QueryWechatFreePayResult> queryWechatFreePay(PaymentDto paymentDto) throws Exception {
		ResponseVo<QueryWechatFreePayResult> responseVo = ResponseVo.getSuccessResponse();
		//获取商户编号配置
		MerchantConf conf = merchantInfoService.getWechatMerchantConf(paymentDto.getSmerchantCode(), 2);
		//获取商户订单支付编号
		String outTradeNo = orderPayService.selectOutTradeNoByOrderCode(paymentDto.getOutTradeNo());

		BaseReqData.UnifiedBaseReqDataBuilder builder = new BaseReqData.UnifiedBaseReqDataBuilder(conf, outTradeNo);
		if (StringUtil.isNotBlank(paymentDto.getTradeNo())) {
			builder.setTransaction_id(paymentDto.getTradeNo());
		}
		BaseReqData reqData = builder.build();
		QueryFreePayNotifyData notifyData = wxPayApi.unifiedQueryFreePay(conf, reqData);
		logger.info("微信免密支付查询成功，返回参数：{}", JSON.toJSONString(notifyData));
		String errorMsg = "未查询到微信免密支付记录";
		String errorCode = "-1000";
		Integer code = -1000;
		if (notifyData != null) {
			if (notifyData.getReturn_code().equals("SUCCESS")) {
				if (StringUtil.isNotBlank(notifyData.getResult_code())
						&& notifyData.getResult_code().equals("SUCCESS")) {
					QueryWechatFreePayResult result = new QueryWechatFreePayResult();
					BeanUtils.copyProperties(result, notifyData);
					responseVo.setData(result);
					return responseVo;
				} else {
					errorCode = notifyData.getErr_code();
					errorMsg = "{" + errorCode + ":" + notifyData.getErr_code_des() + "}";
					code = -1001;
				}
			} else {
				errorCode = notifyData.getReturn_code();
				errorMsg = notifyData.getReturn_msg();
			}
		}
		logger.error("微信免密支付订单查询异常：{}", errorCode + "------------" + errorMsg);
		responseVo.setSuccess(false);
		responseVo.setErrorCode(code);
		responseVo.setMsg(errorMsg);
		return responseVo;
	}

	/**
	 * 获取订单信息
	 *
	 * @param orderCode 订单编号
	 */
	@Override
	public OrderRecord selectByOrderCode(String orderCode) {
		return orderRecordDao.selectByOrderCode(orderCode);
	}

	/**
	 * 创建支付宝免密支付
	 *
	 * @param freePaymentDto 支付参数
	 */
	@Override
	public ResponseVo<FreePaymentResult> createAlipayFreePay(FreePaymentDto freePaymentDto) {
		ResponseVo<FreePaymentResult> responseVo = ResponseVo.getSuccessResponse();
		try {
			//判断订单有效性
			ResponseVo<OrderRecord> resVo = validateOrderEffective(freePaymentDto);
			if (!resVo.isSuccess()) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(resVo.getMsg());
			}
			OrderRecord orderRecord = resVo.getData();
			//新增付款申请
			orderRecord.setIpayWay(freePaymentDto.getIpayWay());//设置支付方式
			PayApply payApply = this.insertPayApply(orderRecord, freePaymentDto.getSremark());

			AlipayClient alipayClient = merchantInfoService.getAlipayClient(freePaymentDto.getSmerchantCode());
			AlipayTradePayRequest request = new AlipayTradePayRequest();
			request.setNotifyUrl(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "free_payment_notify_url").getSvalue());

			//获取商户配置信息
			MerchantConf conf = merchantInfoService.getAlipayMerchantConf(freePaymentDto.getSmerchantCode(), 2);
			if (null != conf && StringUtil.isNotBlank(conf.getSauthToken())) {
				request.putOtherTextParam("app_auth_token", conf.getSauthToken());
			}
			//新增订单付款信息
			OrderPay orderPay = orderPayService.insertOrderPay(orderRecord, 20, 0);
			//组装支付请求参数
			FreePaymentVo freePaymentVo = this.assemblyFreePaymentRequest(orderPay, orderRecord, freePaymentDto, null, 1);
			request.setBizContent(JSON.toJSONString(freePaymentVo));
			logger.info("支付宝免密支付请求参数：{}", request.getBizContent());
			AlipayTradePayResponse response = alipayClient.execute(request);
			logger.info("支付宝免密支付返回参数：{}", JSON.toJSONString(response));

			FreePaymentResult freePaymentResult = new FreePaymentResult();
			orderRecord.setSpayApplyId(payApply.getId());
			orderRecord.setIpayWay(freePaymentDto.getIpayWay());
			if (response.isSuccess()) {
				//设置返回值
				freePaymentResult.setIstatus(10);
				freePaymentResult.setBuyerLogonId(response.getBuyerLogonId());
				freePaymentResult.setBuyerPayAmount(new BigDecimal(response.getBuyerPayAmount()));
				freePaymentResult.setGmtPayment(response.getGmtPayment());
				freePaymentResult.setInvoiceAmount(new BigDecimal(response.getInvoiceAmount()));
				freePaymentResult.setPointAmount(new BigDecimal(response.getPointAmount()));
				freePaymentResult.setReceiptAmount(new BigDecimal(response.getReceiptAmount()));
				freePaymentResult.setTotalAmount(new BigDecimal(response.getTotalAmount()));
				freePaymentResult.setTradeNo(response.getTradeNo());

				//正常处理更新数据
				this.freePaySuccess(orderPay.getScode(), orderRecord, freePaymentResult);
				responseVo.setData(freePaymentResult);
				responseVo.setErrorCode(100);
				responseVo.setMsg("支付成功");
				updateMemberAlipaySign(freePaymentDto.getSip(), orderRecord.getSmemberId(), orderRecord.getSmerchantCode());
				return responseVo;
			}
			freePaymentResult = this.freePayFail(orderPay.getScode(), response.getCode(), response.getSubCode(), response.getSubMsg(), orderRecord, freePaymentResult);
			if (null != freePaymentResult.getIstatus() && freePaymentResult.getIstatus().intValue() == 110) {
				responseVo.setData(freePaymentResult);
				return responseVo;
			}
			logger.error("支付宝创建免密支付异常：{}", response.getSubCode() + "------------" + response.getSubMsg());
			responseVo.setSuccess(false);
			responseVo.setErrorCode(-1000);
			responseVo.setMsg(response.getSubMsg());
			responseVo.setData(freePaymentResult);
		} catch (Exception e) {
			logger.error("创建支付宝免密支付异常：{}", e);
			responseVo.setSuccess(false);
			responseVo.setErrorCode(-1000);
			responseVo.setMsg("创建支付异常，请重新操作");
		}
		return responseVo;
	}

	/**
	 * 创建微信免密支付
	 *
	 * @param freePaymentDto 支付参数
	 */
	@Override
	public ResponseVo<FreePaymentResult> createWechatFreePay(FreePaymentDto freePaymentDto) {
		ResponseVo<FreePaymentResult> responseVo = ResponseVo.getSuccessResponse();
		try {
			//判断订单有效性
			ResponseVo<OrderRecord> resVo = validateOrderEffective(freePaymentDto);
			if (!resVo.isSuccess()) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(resVo.getMsg());
			}
			OrderRecord orderRecord = resVo.getData();
			//获取商户配置信息
			MerchantConf conf = merchantInfoService.getWechatMerchantConf(freePaymentDto.getSmerchantCode(), 2);
			//获取本机ip
			InetAddress addr = InetAddress.getLocalHost();
			String sip = addr.getHostAddress().toString();
			String notifyUrl = GrpParaUtil.getDetailForName(CoreConstant.WECHAT_FREE_CONFIG, "free_payment_notify_url").getSvalue();
			String tradeType = GrpParaUtil.getDetailForName(CoreConstant.WECHAT_FREE_CONFIG, "free_trade_type").getSvalue();
			//代扣支付协议信息
			WechatFreeData wechatFreeData = wechatFreeDataService.selectByMemberIdAndWithholdType(orderRecord.getSmemberId(), orderRecord.getSmerchantCode(), 10);
			if (null == wechatFreeData) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员免密数据不存在");
			}
			//新增付款申请
			orderRecord.setIpayWay(freePaymentDto.getIpayWay());//设置支付方式
			PayApply payApply = this.insertPayApply(orderRecord, freePaymentDto.getSremark());
			//新增订单付款信息
			OrderPay orderPay = orderPayService.insertOrderPay(orderRecord, 10, 0);
			UnifiedFreePayReqData reqData = new UnifiedFreePayReqData
					.UnifiedFreePayReqDataBuilder(conf, freePaymentDto.getSsubject(),
					orderPay.getScode(),
					orderRecord.getFactualPayAmount().multiply(new BigDecimal(100)).intValue(),
					sip, notifyUrl, tradeType, wechatFreeData.getScontractId())
					.setDetail(freePaymentDto.getSremark())
					.setOuterid(freePaymentDto.getSmemberCode())
					.setTimestamp((int) System.currentTimeMillis() / 1000)
					.setAttach("叁拾柒号仓智能货柜").build();

			Map<String, Object> resMap = wxPayApi.unifiedFreePay(conf, reqData);
			String err_code_des = "创建微信免密支付异常";
			if (null != resMap) {
				logger.info("微信免密支付宝返回参数：{}", JSON.toJSONString(resMap));
				if ("SUCCESS".equals(resMap.get("return_code"))) {
					if ("SUCCESS".equals(resMap.get("result_code"))) {
						//更新订单信息
						OrderRecord updateOrder = new OrderRecord();
						updateOrder.setId(orderRecord.getId());
						updateOrder.setSpayApplyId(payApply.getId());
						updateOrder.setIstatus(BizTypeDefinitionEnum.OrderStatus.IN_PAYMENT.getCode());
						this.updateBySelective(updateOrder);

						responseVo.setMsg("创建免密支付成功");
						return responseVo;
					} else {
						err_code_des = String.valueOf(resMap.get("err_code_des"));
					}
				} else {
					err_code_des = String.valueOf(resMap.get("return_msg"));
				}
			}
			responseVo.setSuccess(false);
			responseVo.setErrorCode(-1000);
			responseVo.setMsg(err_code_des);
		} catch (Exception e) {
			logger.error("创建微信免密支付异常：{}", e);
			responseVo.setSuccess(false);
			responseVo.setErrorCode(-1000);
			responseVo.setMsg("创建支付异常，请重新操作");
		}
		return responseVo;
	}


	/**
	 * 新增支付宝免密支付并且签约
	 *
	 * @param freePaymentDto 支付签约参数
	 */
	@Override
	public ResponseVo<String> createAlipayFreePayAndSign(FreePaymentDto freePaymentDto) {
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		try {
			//判断订单有效性
			ResponseVo<OrderRecord> resVo = validateOrderEffective(freePaymentDto);
			if (!resVo.isSuccess()) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(resVo.getMsg());
			}
			OrderRecord orderRecord = resVo.getData();
			//新增付款申请
			orderRecord.setIpayWay(freePaymentDto.getIpayWay());//设置支付方式
			//获取用户的支付宝免密数据
			FreeData freeData = freeDataService.selectByMemberId(freePaymentDto.getSmemberId(), freePaymentDto.getSmerchantCode());
			boolean flag = false;
			if (freeData == null) {
				freeData = new FreeData();
				freeData.setSexternalAgreementNo(IdGenerator.randomUUID(32));
				freeData.setSmemberId(freePaymentDto.getSmemberId());
				freeData.setSmemberName(freePaymentDto.getSmemberName());
				freeData.setSmemberNo(freePaymentDto.getSmemberCode());
				freeData.setSmerchantCode(freePaymentDto.getSmerchantCode());
				flag = true;
			}
			AlipayClient alipayClient = merchantInfoService.getAlipayClient(freePaymentDto.getSmerchantCode());
			AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
			request.setNotifyUrl(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "free_payment_and_sign_notify_url").getSvalue());
			request.setReturnUrl(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "free_payment_return_url").getSvalue());

			//获取商户配置信息
			MerchantConf conf = merchantInfoService.getAlipayMerchantConf(freePaymentDto.getSmerchantCode(), 2);
			if (null != conf && StringUtil.isNotBlank(conf.getSauthToken())) {
				request.putOtherTextParam("app_auth_token", conf.getSauthToken());
			}
			//新增订单付款信息
			OrderPay orderPay = orderPayService.insertOrderPay(orderRecord, 20, 0);
			//组装支付并签约请求参数
			FreePaymentVo freePaymentVo = this.assemblyFreePaymentRequest(orderPay, orderRecord, freePaymentDto, freeData, 2);
			request.setBizContent(JSON.toJSONString(freePaymentVo));
			logger.debug("支付宝免密支付订单关闭请求参数:{}", request.getBizContent());
			AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "get");
			logger.info("支付宝免密支付并签约返回参数：{}", JSON.toJSONString(response));
			String url = "";
			if (response.isSuccess()) {
				if (flag) {//新增免密数据
					freeDataService.saveOrUpdate(freeData);
					//新增支付申请
					this.insertPayApply(orderRecord, freePaymentDto.getSremark());
				}
				url = "alipays://platformapi/startapp?appId=60000157&appClearTop=false&startMultApp=YES&sign_params=" + URLEncoder.encode(response.getBody().replace(AlipayConfigure.UNIFIED_ORDER_API + "?", ""), AlipayConfigure.charset);
				responseVo.setData("http://d.alipay.com/i/index.htm?iframeSrc=" + URLEncoder.encode(url, AlipayConfigure.charset));
				logger.info("支付宝免密支付并签约请求参数：{}", responseVo.getData());
				return responseVo;
			}
			logger.error("支付宝免密支付并签约异常：{}", response.getSubCode() + "------------" + response.getSubMsg());
			responseVo.setSuccess(false);
			responseVo.setErrorCode(-1000);
			responseVo.setMsg(response.getSubMsg());
		} catch (Exception e) {
			logger.error("创建支付宝免密支付异常：{}", e);
			responseVo.setSuccess(false);
			responseVo.setErrorCode(-1000);
			responseVo.setMsg("创建支付异常，请重新操作");
		}
		return responseVo;
	}

	/**
	 * 验证订单是否有效
	 *
	 * @return
	 */
	private ResponseVo<OrderRecord> validateOrderEffective(CreatSmartretailOrderDto creatSmartretailOrderDto) {
		ResponseVo<OrderRecord> responseVo = ResponseVo.getSuccessResponse();
		//判断订单有效性
		OrderRecord orderRecord = this.selectByPrimaryKey(creatSmartretailOrderDto.getOrderId());
		if (null == orderRecord) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("支付订单不存在");
		}
		if (orderRecord.getIstatus().intValue() == BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
		}
    /*    if (null == creatSmartretailOrderDto.getIisIgnoreStatus() || creatSmartretailOrderDto.getIisIgnoreStatus().intValue() == 0) {
            if (orderRecord.getIstatus().intValue() != BizTypeDefinitionEnum.OrderStatus.PENDING_PAYMENT.getCode()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
            }
        }*/
		if (null == orderRecord.getFactualPayAmount() || orderRecord.getFactualPayAmount().doubleValue() < 0.01d) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单实付金额异常");
		}
		responseVo.setData(orderRecord);
		return responseVo;
	}

	/**
	 * 验证订单是否有效
	 *
	 * @return
	 */
	private ResponseVo<OrderRecord> validateOrderEffectiveN(EndSmartretailOrderDto endSmartretailOrderDto) {
		ResponseVo<OrderRecord> responseVo = ResponseVo.getSuccessResponse();
		//判断订单有效性
		OrderRecord orderRecord = this.selectByPrimaryKey(endSmartretailOrderDto.getOrderId());
		if (null == orderRecord) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("支付订单不存在");
		}
		if (orderRecord.getIstatus().intValue() == BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
		}
    /*    if (null == creatSmartretailOrderDto.getIisIgnoreStatus() || creatSmartretailOrderDto.getIisIgnoreStatus().intValue() == 0) {
            if (orderRecord.getIstatus().intValue() != BizTypeDefinitionEnum.OrderStatus.PENDING_PAYMENT.getCode()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
            }
        }*/
		if (null == orderRecord.getFactualPayAmount() || orderRecord.getFactualPayAmount().doubleValue() < 0.01d) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单实付金额异常");
		}
		responseVo.setData(orderRecord);
		return responseVo;
	}

	/**
	 * 验证订单是否有效
	 *
	 * @return
	 */
	private ResponseVo<OrderRecord> validateOrderEffective(FreePaymentDto freePaymentDto) {
		ResponseVo<OrderRecord> responseVo = ResponseVo.getSuccessResponse();
		//判断订单有效性
		OrderRecord orderRecord = this.selectByPrimaryKey(freePaymentDto.getSorderId());
		if (null == orderRecord) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("支付订单不存在");
		}
		if (orderRecord.getIstatus().intValue() == BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
		}
		if (null == freePaymentDto.getIisIgnoreStatus() || freePaymentDto.getIisIgnoreStatus().intValue() == 0) {
			if (orderRecord.getIstatus().intValue() != BizTypeDefinitionEnum.OrderStatus.PENDING_PAYMENT.getCode()) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
			}
		}
		if (null == orderRecord.getFactualPayAmount() || orderRecord.getFactualPayAmount().doubleValue() < 0.01d) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单实付金额异常");
		}
		responseVo.setData(orderRecord);
		return responseVo;
	}

	/**
	 * 新增申请支付订单数据
	 *
	 * @param orderRecord 订单数据
	 * @param remark      付款备注
	 * @throws Exception
	 */
	@Override
	@Transactional
	public PayApply insertPayApply(OrderRecord orderRecord, String remark) throws Exception {
		PayApply payApply = new PayApply();
		BeanUtils.copyProperties(payApply, orderRecord);

		payApply.setSmemberNo(orderRecord.getSmemberCode());
		payApply.setIorignTradeCode(orderRecord.getSorderCode());
		payApply.setIorignTradeId(orderRecord.getId());

		payApply.setIcloseStatus(0);//订单关闭状态
		payApply.setIstatus(10);
		payApply.setTaddTime(new Date());
		payApply.setScurrency("10");//币种
		payApply.setSremark(remark);
		payApply.setTaddTime(new Date());
		payApply.setIversion(1);
		payApplyService.insert(payApply);
		return payApply;
	}

	/**
	 * 查询免密支付订单
	 *
	 * @param paymentDto 查询参数
	 */
	@Override
	public ResponseVo<QueryPaymentResult> queryAlipayFreePay(PaymentDto paymentDto) throws Exception {
		ResponseVo<QueryPaymentResult> responseVo = ResponseVo.getSuccessResponse();
		AlipayClient alipayClient = merchantInfoService.getAlipayClient(paymentDto.getSmerchantCode());
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		//获取商户配置信息
		MerchantConf conf = merchantInfoService.getAlipayMerchantConf(paymentDto.getSmerchantCode(), 2);
		if (null != conf && StringUtil.isNotBlank(conf.getSauthToken())) {
			request.putOtherTextParam("app_auth_token", conf.getSauthToken());
		}
		PaymentVo paymentVo = new PaymentVo();

		//获取商户订单支付编号
		String outTradeNo = orderPayService.selectOutTradeNoByOrderCode(paymentDto.getOutTradeNo());

		paymentVo.setOut_trade_no(outTradeNo);
		paymentVo.setTrade_no(paymentDto.getTradeNo());
		request.setBizContent(JSON.toJSONString(paymentVo));
		logger.debug("支付宝免密支付订单查询请求参数:{}", request.getBizContent());
		AlipayTradeQueryResponse response = alipayClient.execute(request);
		logger.info("支付宝免密支付订单查询返回数据：{}", JSON.toJSONString(response));
		if (response.isSuccess()) {
			QueryPaymentResult queryPaymentResult = new QueryPaymentResult();
			BeanUtils.copyProperties(queryPaymentResult, response);
			responseVo.setData(queryPaymentResult);
			return responseVo;
		}
		logger.error("支付宝免密支付订单查询异常：{}", response.getSubCode() + "------------" + response.getSubMsg());
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(response.getSubMsg());
		return responseVo;
	}

	/**
	 * 撤销免密支付订单
	 *
	 * @param paymentDto 查询参数
	 * @throws Exception
	 */
	@Override
	public ResponseVo<String> cancelAlipayFreePay(PaymentDto paymentDto) throws Exception {
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		//获取操作订单信息
		OrderRecord orderRecord = this.selectByOrderCode(paymentDto.getOutTradeNo());
		if (orderRecord.getIstatus().intValue() == BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
		}
		if (StringUtil.isBlank(orderRecord.getSpayApplyId())) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("无付款申请记录");
		}
		PayApply payApply = payApplyService.selectByPrimaryKey(orderRecord.getSpayApplyId());
		if (payApply.getIstatus().intValue() == 20) {//订单交易成功
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("付款申请状态异常");
		}
		AlipayClient alipayClient = merchantInfoService.getAlipayClient(paymentDto.getSmerchantCode());
		AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
		//获取商户配置信息
		MerchantConf conf = merchantInfoService.getAlipayMerchantConf(paymentDto.getSmerchantCode(), 2);
		if (null != conf && StringUtil.isNotBlank(conf.getSauthToken())) {
			request.putOtherTextParam("app_auth_token", conf.getSauthToken());
		}
		PaymentVo paymentVo = new PaymentVo();
		paymentVo.setOut_trade_no(paymentDto.getOutTradeNo());
		paymentVo.setTrade_no(paymentDto.getTradeNo());
		request.setBizContent(JSON.toJSONString(paymentVo));
		logger.debug("支付宝免密支付订单撤销请求参数:{}", request.getBizContent());
		AlipayTradeCancelResponse response = alipayClient.execute(request);
		logger.info("支付宝免密支付订单撤销返回数据：{}", JSON.toJSONString(response));
		if (response.isSuccess()) {
			//更新付款申请状态
			PayApply updateApply = new PayApply();
			updateApply.setId(payApply.getId());
			updateApply.setIstatus(50);
			updateApply.setSpaySerialNumber(response.getTradeNo());
			updateApply.setTfinishDatetime(new Date());
			payApplyService.updateBySelective(updateApply);
			//交易动作 close：关闭交易，无退款 refund：产生了退款
			responseVo.setData(response.getAction());
			return responseVo;
		}
		logger.error("支付宝免密支付订单撤销异常：{}", response.getSubCode() + "------------" + response.getSubMsg());
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(response.getSubMsg());
		return responseVo;
	}

	/**
	 * 关闭免密支付订单
	 *
	 * @param paymentDto 查询参数
	 * @throws Exception
	 */
	@Override
	public ResponseVo<String> closeAlipayFreePay(PaymentDto paymentDto) throws Exception {
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		//获取操作订单信息
		OrderRecord orderRecord = this.selectByOrderCode(paymentDto.getOutTradeNo());
		if (orderRecord.getIstatus().intValue() == BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
		}
		if (StringUtil.isBlank(orderRecord.getSpayApplyId())) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("无付款申请记录");
		}
		AlipayClient alipayClient = merchantInfoService.getAlipayClient(paymentDto.getSmerchantCode());
		AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
		request.setNotifyUrl(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "close_free_payment_notify_url").getSvalue());
		//获取商户配置信息
		MerchantConf conf = merchantInfoService.getAlipayMerchantConf(paymentDto.getSmerchantCode(), 2);
		if (null != conf && StringUtil.isNotBlank(conf.getSauthToken())) {
			request.putOtherTextParam("app_auth_token", conf.getSauthToken());
		}
		PaymentVo paymentVo = new PaymentVo();
		paymentVo.setOut_trade_no(paymentDto.getOutTradeNo());
		paymentVo.setTrade_no(paymentDto.getTradeNo());
		request.setBizContent(JSON.toJSONString(paymentVo));
		logger.debug("支付宝免密支付订单关闭请求参数:{}", request.getBizContent());
		AlipayTradeCloseResponse response = alipayClient.execute(request);
		logger.info("支付宝免密支付订单关闭返回数据：{}", JSON.toJSONString(response));
		if (response.isSuccess()) {
			//更新付款申请状态
			PayApply updateApply = new PayApply();
			updateApply.setId(orderRecord.getSpayApplyId());
			updateApply.setIcloseStatus(1);
			updateApply.setScloseTradeNo(response.getTradeNo());
			updateApply.setTcloseTime(new Date());
			payApplyService.updateBySelective(updateApply);
			responseVo.setData(response.getTradeNo());//交易流水
			return responseVo;
		}
		logger.error("支付宝免密支付订单关闭异常：{}", response.getSubCode() + "------------" + response.getSubMsg());
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(response.getSubMsg());
		return responseVo;
	}

	/**
	 * 处理支付宝免密支付 回调
	 *
	 * @param conf 商户配置信息 操作商户
	 * @param map  回调参数
	 */
	@Override
	public boolean dealwithAlipayFreePaymentNotify(MerchantConf conf, Map<String, String> map) throws Exception {
		// 获取商户订单号
		String outTradePayNo = map.get("out_trade_no");
		logger.info("获取支付商户订单号=" + outTradePayNo);
		String outTradeNo = orderPayService.selectOrderCodeByPayNo(outTradePayNo);
		logger.info("获取商户订单号=" + outTradeNo);
		// 获取支付订单信息
		OrderRecord orderRecord = this.selectByOrderCode(outTradeNo);
		logger.info("获取的订单信息=" + orderRecord);
		if (orderRecord == null) {
			logger.info("订单编号：" + outTradeNo + "的订单不存在");
			return false;
		}
		if (orderRecord.getIstatus() == null ||
				orderRecord.getIstatus().intValue() == BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
			logger.info("免密支付订单状态异常或回调已处理，订单编号：{}", orderRecord.getSorderCode());
			//更新会员签约数据
			updateMemberAlipaySign(map.get("clientIp"), orderRecord.getSmemberId(), orderRecord.getSmerchantCode());
			return true;
		}
		// 获取支付付款申请信息
		PayApply payApply = payApplyService.selectByPrimaryKey(orderRecord.getSpayApplyId());
		if (payApply != null) {
			if ("TRADE_SUCCESS".equals(map.get("trade_status"))) {
				orderRecord.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());//设置支付方式为代扣
				FreePaymentResult freePaymentResult = new FreePaymentResult();
				freePaymentResult.setBuyerPayAmount(new BigDecimal(map.get("buyer_pay_amount")));
				freePaymentResult.setGmtPayment(DateUtils.parseDateByFormat(map.get("gmt_payment"), "yyyy-MM-dd HH:mm:ss"));
				freePaymentResult.setInvoiceAmount(new BigDecimal(map.get("invoice_amount")));
				freePaymentResult.setPointAmount(new BigDecimal(map.get("point_amount")));
				freePaymentResult.setReceiptAmount(new BigDecimal(map.get("receipt_amount")));
				freePaymentResult.setTotalAmount(new BigDecimal(map.get("total_amount")));
				freePaymentResult.setTradeNo(map.get("trade_no"));
				this.freePaySuccess(outTradePayNo, orderRecord, freePaymentResult);
				//更新会员签约数据
				updateMemberAlipaySign(map.get("clientIp"), orderRecord.getSmemberId(), orderRecord.getSmerchantCode());
				return true;
			}
		}
		return false;
	}

	/**
	 * 更新会员免密数据
	 *
	 * @param merchantCode 商户编号
	 * @param memberId     会员信息
	 */
	public void updateMemberAlipaySign(final String clientIp, final String memberId, final String merchantCode) {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					//代扣支付协议信息
					MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);
					FreeData freeData = freeDataService.selectByMemberId(memberId, memberInfo.getSmerchantCode());
					if (StringUtil.isNotBlank(freeData.getSproductCode()) &&
							freeData.getSproductCode().equals("ONE_TIME_AUTH_P")) {//单次代扣

						UnsignDto unsignDto = new UnsignDto();
						unsignDto.setSmemberId(memberId);
						unsignDto.setSmerchantCode(merchantCode);
						unsignDto.setSmemberMerchantCode(memberInfo.getSmerchantCode());
						unsignDto.setSip(clientIp);
						// 创建Rest服务客户端
						RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_UNSIGN);
						invoke.setRequestObj(unsignDto);
						// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
						invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
						});
						ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
						logger.info("免密解约结果：{}", JSONObject.toJSONString(responseVo));
					}
					logger.info("不是单次免密，不需要解约");
				} catch (Exception e) {
					logger.error("支付宝免密解约结果异常：{}", e);
				}
                /*try {
                    MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);
					QuerySignDto querySignDto = new QuerySignDto();
					querySignDto.setSmemberId(memberId);
					querySignDto.setSmerchantCode(merchantCode);
					// 创建Rest服务客户端
					RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_QUERY_SIGN);
					invoke.setRequestObj(querySignDto);
					// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
					invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<SignResult>>() {});
					ResponseVo<SignResult> responseVo = (ResponseVo<SignResult>) invoke.invoke();
					if (null != responseVo && responseVo.isSuccess() && null != responseVo.getData()) {
						//协议当前状态1. TEMP：暂存，协议未生效过；2. NORMAL：正常；3. STOP：暂停
						boolean flag = dealwithAlipaySign(clientIp, responseVo.getData(), merchantCode);
						if (!flag && 1 == memberInfo.getIaipayOpen()) {
							MemberInfo updata = new MemberInfo();
							updata.setId(memberId);
							updata.setIaipayOpen(0);
							updata.setUpdateTime(DateUtils.getCurrentDateTime());
							memberInfoService.updateBySelective(updata);
						}
					} else if (null != responseVo && null == responseVo.getData() && 1 == memberInfo.getIaipayOpen()) {
						MemberInfo updata = new MemberInfo();
						updata.setId(memberId);
						updata.setIaipayOpen(0);
						updata.setUpdateTime(DateUtils.getCurrentDateTime());
						memberInfoService.updateBySelective(updata);
					}
				} catch (Exception e) {
					logger.error("支付宝免密协议查询异常：{}", e);
				}*/
			}
		});
	}
	/**
	 * 用户支付宝免密支付补处理更新数据
	 * @param
	 * @return boolean
	 */
    /*private Boolean dealwithAlipaySign(String clientIp, SignResult signResult, String merchantCode) {
        String externalAgreementNo = signResult.getExternalAgreementNo();//系统商户签约唯一标识
		FreeData freeData = freeDataService.selectByExternalAgreementNo(externalAgreementNo, merchantCode);
		if (freeData == null) {
			logger.error("支付宝签约数据异常");
			return false;
		}
		if ("TEMP".equals(signResult.getStatus()) || "STOP".equals(signResult.getStatus())) {
			if ("UNSIGN".equals(freeData.getSstatus())) {
				logger.info("支付宝免密协议解约重复处理");
				return true;
			}
			//更新数据
			FreeData updateData = new FreeData();
			updateData.setId(freeData.getId());
			updateData.setSstatus("UNSIGN");
			updateData.setTunsignTime(DateUtils.parseDateByFormat(signResult.getInvalidTime(), "yyyy-MM-dd HH:mm:ss"));
			updateData.setTupdateTime(DateUtils.getCurrentDateTime());
			freeDataService.saveOrUpdate(updateData);

			//新增免密操作记录
			FreeOperLog freeOperLog = new FreeOperLog();

			freeOperLog.setSmemberId(freeData.getSmemberId());
			freeOperLog.setSmemberName(freeData.getSmemberName());
			freeOperLog.setSmemberNo(freeData.getSmemberNo());
			freeOperLog.setSmerchantCode(freeData.getSmerchantCode());
			freeOperLog.setSmerchantId(freeData.getSmerchantId());

			freeOperLog.setIaction(20);
			freeOperLog.setItype(10);
			freeOperLog.setToperTime(updateData.getTunsignTime());
			freeOperLog.setScontractCode(freeData.getSexternalAgreementNo());
			freeOperLog.setSoperIp(clientIp);
			freeOperLogService.insert(freeOperLog);

			//修改用户状态
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setId(freeData.getSmemberId());
			memberInfo.setIaipayOpen(0);
			memberInfo.setUpdateTime(new Date());
			memberInfoService.updateBySelective(memberInfo);
		}
		return true;
	}*/

	/**
	 * 处理微信免密支付 回调
	 *
	 * @param conf              商户配置信息 操作商户
	 * @param freePayNotifyData 回调参数
	 */
	@Override
	public boolean dealwithWechatFreePaymentNotify(MerchantConf conf, FreePayNotifyData freePayNotifyData) throws Exception {
		// 获取商户订单号
		String outTradePayNo = freePayNotifyData.getOut_trade_no();
		logger.info("获取支付商户订单号=" + outTradePayNo);
		String outTradeNo = orderPayService.selectOrderCodeByPayNo(outTradePayNo);
		logger.info("获取商户订单号=" + outTradeNo);
		// 获取支付订单信息
		OrderRecord orderRecord = this.selectByOrderCode(outTradeNo);
		logger.info("获取的订单信息=" + orderRecord);
		if (orderRecord == null) {
			logger.info("订单编号：" + outTradeNo + "的订单不存在");
			return false;
		}
		if (orderRecord.getIstatus() == null ||
				orderRecord.getIstatus().intValue() == BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
			logger.info("免密支付订单状态异常或回调已处理，订单编号：{}", orderRecord.getSorderCode());
			return true;
		}
		// 获取支付付款申请信息
		PayApply payApply = payApplyService.selectByPrimaryKey(orderRecord.getSpayApplyId());
		if (payApply != null) {
			FreePaymentResult freePaymentResult = new FreePaymentResult();
			if (StringUtil.isNotBlank(freePayNotifyData.getTime_end())) {
				freePaymentResult.setGmtPayment(DateUtils.parseDateByFormat(freePayNotifyData.getTime_end(), "yyyyMMddHHmmss"));
			} else {
				freePaymentResult.setGmtPayment(DateUtils.getCurrentDateTime());
			}
			freePaymentResult.setPointAmount(BigDecimal.ZERO);
			if (StringUtil.isNotBlank(freePayNotifyData.getCoupon_fee())) {
				freePaymentResult.setOtherPayAmount(new BigDecimal(freePayNotifyData.getCoupon_fee()).divide(new BigDecimal("100")));
			} else {
				freePaymentResult.setOtherPayAmount(BigDecimal.ZERO);
			}
			if (StringUtil.isNotBlank(freePayNotifyData.getCash_fee())) {
				freePaymentResult.setReceiptAmount(new BigDecimal(freePayNotifyData.getCash_fee()).divide(new BigDecimal("100")));
			} else {
				freePaymentResult.setReceiptAmount(BigDecimal.ZERO);
			}
			if (StringUtil.isNotBlank(freePayNotifyData.getCash_fee())) {
				freePaymentResult.setTotalAmount(new BigDecimal(freePayNotifyData.getTotal_fee()).divide(new BigDecimal("100")));
			} else {
				freePaymentResult.setTotalAmount(BigDecimal.ZERO);
			}
			freePaymentResult.setInvoiceAmount(freePaymentResult.getTotalAmount());
			freePaymentResult.setBuyerPayAmount(freePaymentResult.getTotalAmount());
			freePaymentResult.setTradeNo(freePayNotifyData.getTransaction_id());
			orderRecord.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());//设置支付方式为代扣
			if ("SUCCESS".equals(freePayNotifyData.getResult_code())) {
				if ("SUCCESS".equals(freePayNotifyData.getTrade_state())) {
					this.freePaySuccess(outTradePayNo, orderRecord, freePaymentResult);
					return true;
				}
			} else {
				// 失败处理
				//payFail(orderRecord, freePayNotifyData.getTransaction_id(), freePayNotifyData.getTime_end(), freePayNotifyData.getErr_code(), freePayNotifyData.getErr_code_des());
				this.freePayFail(outTradePayNo, freePayNotifyData.getResult_code(), freePayNotifyData.getErr_code(), freePayNotifyData.getErr_code_des(), orderRecord, freePaymentResult);
				return true;
			}
		}
		return false;
	}

	/**
	 * 修改订单数据 成功处理
	 *
	 * @param outTradePayNo
	 * @param orderRecord       原始订单数据
	 * @param freePaymentResult 支付返回结果
	 * @return
	 */
	@Override
	public boolean freePaySuccess(String outTradePayNo, OrderRecord orderRecord, FreePaymentResult freePaymentResult) {
		//更新订单信息
		OrderRecord updateOrder = new OrderRecord();
		updateOrder.setId(orderRecord.getId());
		updateOrder.setSpayApplyId(orderRecord.getSpayApplyId());
		updateOrder.setIpayWay(orderRecord.getIpayWay());
		updateOrder.setIstatus(10);//付款成功
		if (orderRecord.getIchargebackWay().intValue() == BizTypeDefinitionEnum.ChargebackWay.ONE_WITHHOLDING.getCode()) {
			updateOrder.setIchargebackWay(orderRecord.getIchargebackWay());//单次代扣
		} else if (orderRecord.getIchargebackWay().intValue() == BizTypeDefinitionEnum.ChargebackWay.WECHAT_PAY_POINT_WITHHOLDING.getCode()) {
			updateOrder.setIchargebackWay(orderRecord.getIchargebackWay());//微信支付分代扣
		} else {
			updateOrder.setIchargebackWay(10);//商户代扣
		}
		updateOrder.setTpayCompleteTime(freePaymentResult.getGmtPayment());
		updateOrder.setSpaySerialNumber(freePaymentResult.getTradeNo());
		this.updateBySelective(updateOrder);

		//更新申请付款数据
		PayApply updateApply = new PayApply();
		updateApply.setId(orderRecord.getSpayApplyId());
		updateApply.setSpaySerialNumber(freePaymentResult.getTradeNo());
		updateApply.setIstatus(20);
		updateApply.setTfinishDatetime(freePaymentResult.getGmtPayment());
		if (null == freePaymentResult.getReceiptAmount()) {
			updateApply.setFactualReceiveAmount(BigDecimal.ZERO);
		}
		updateApply.setFactualReceiveAmount(freePaymentResult.getReceiptAmount());
		updateApply.setFbuyerPayAmount(freePaymentResult.getBuyerPayAmount());
		if (null == freePaymentResult.getPointAmount()) {
			updateApply.setFinvoiceAmount(BigDecimal.ZERO);
		}
		updateApply.setFinvoiceAmount(freePaymentResult.getPointAmount());
		if (null == freePaymentResult.getPointAmount()) {
			updateApply.setFpointAmount(BigDecimal.ZERO);
		}
		updateApply.setFpointAmount(freePaymentResult.getPointAmount());
		if (null == freePaymentResult.getOtherPayAmount()) {
			updateApply.setFotherPayAmount(BigDecimal.ZERO);
		}
		updateApply.setFotherPayAmount(freePaymentResult.getOtherPayAmount());
		payApplyService.updateBySelective(updateApply);

		//更新订单付款数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scode", outTradePayNo);
		map.put("sorderCode", orderRecord.getSorderCode());
		map.put("transactionId", freePaymentResult.getTradeNo());
		map.put("istatus", 20);
		map.put("tcompleteTime", updateOrder.getTpayCompleteTime());
		orderPayService.updateDataByMap(map);

		return true;
	}

	/***
	 * 免密订单支付失败处理
	 * @param outTradePayNo
	 * @param code 支付宝处理结果code
	 * @param subCode 业务code
	 * @param subMsg 业务处理信息
	 * @param orderRecord 原始订单数据
	 * @param freePaymentResult 支付返回结果   @return
	 */
	@Override
	public FreePaymentResult freePayFail(String outTradePayNo, String code, String subCode, String subMsg, OrderRecord orderRecord, FreePaymentResult freePaymentResult) {
		//更新订单信息
		OrderRecord updateOrder = new OrderRecord();
		updateOrder.setId(orderRecord.getId());
		updateOrder.setSpayApplyId(orderRecord.getSpayApplyId());
		updateOrder.setIpayWay(orderRecord.getIpayWay());

		//更新付款订单
		PayApply updateApply = new PayApply();
		updateApply.setId(orderRecord.getSpayApplyId());
		if (code.equals("10003")) {//支付处理中
			logger.info("支付处理中...订单编号:{}", orderRecord.getSorderCode());
			freePaymentResult.setIstatus(110);
			updateOrder.setIstatus(110);//付款处理中
			this.updateBySelective(updateOrder);

			//更新付款申请数据
			updateApply.setIstatus(40);
			payApplyService.updateBySelective(updateApply);

		} else {
			freePaymentResult.setIstatus(20);
			updateOrder.setIstatus(20);//付款失败
			if (StringUtil.isNotBlank(orderRecord.getSpayFailureReason())) {
				updateOrder.setSpayFailureReason(orderRecord.getSpayFailureReason() + ",{" + subCode + ":" + subMsg + "}");
			} else {
				updateOrder.setSpayFailureReason("{" + subCode + ":" + subMsg + "}");
			}
			this.updateBySelective(updateOrder);
			//更新付款申请数据
			updateApply.setIstatus(30);
			updateApply.setTfinishDatetime(new Date());
			payApplyService.updateBySelective(updateApply);
		}


		//更新订单付款数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scode", outTradePayNo);
		map.put("serrorCode", subCode);
		map.put("serrorDesc", subMsg);
		map.put("sorderCode", orderRecord.getSorderCode());
		map.put("transactionId", freePaymentResult.getTradeNo());
		map.put("istatus", 30);
		map.put("tcompleteTime", updateApply.getTfinishDatetime());
		orderPayService.updateDataByMap(map);

		return freePaymentResult;
	}


	/**
	 * 支付成功处理
	 *
	 * @param outTradePayNo
	 * @param orderRecord     订单信息
	 * @param payApply        支付信息
	 * @param paySerialNumber 第三方支付流水号
	 * @param sfinishTime     支付完成时间
	 */
	private void paySuccess(String outTradePayNo, OrderRecord orderRecord, PayApply payApply, String paySerialNumber, String sfinishTime) {
		//更新订单信息
		OrderRecord updateOrder = new OrderRecord();
		updateOrder.setId(orderRecord.getId());
		updateOrder.setIstatus(10);//付款成功
		if (orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode()) {
			updateOrder.setTpayCompleteTime(DateUtils.parseDateByFormat(sfinishTime, "yyyyMMddHHmmss"));
		} else if (orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode()) {
			updateOrder.setTpayCompleteTime(DateUtils.parseDateByFormat(sfinishTime, "yyyy-MM-dd HH:mm:ss"));
		}
		updateOrder.setSpaySerialNumber(paySerialNumber);
		this.updateBySelective(updateOrder);

		//更新申请付款数据
		PayApply updateApply = new PayApply();
		updateApply.setId(payApply.getId());
		updateApply.setSpaySerialNumber(paySerialNumber);
		updateApply.setIstatus(20);
		updateApply.setTfinishDatetime(updateOrder.getTpayCompleteTime());
		payApplyService.updateBySelective(updateApply);


		//更新订单付款数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scode", outTradePayNo);
		map.put("sorderCode", orderRecord.getSorderCode());
		map.put("transactionId", paySerialNumber);
		map.put("istatus", 20);
		map.put("tcompleteTime", updateOrder.getTpayCompleteTime());
		orderPayService.updateDataByMap(map);

	}

	/**
	 * 支付成功后查询是否有支付分订单  有的话同步订单
	 *
	 * @param orderRecord 订单信息
	 * @param sfinishTime 支付完成时间
	 */
	private void paySuccessSyncOrder(final OrderRecord orderRecord, final String sfinishTime) {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				logger.info("手动支付成功后异步同步微信支付分订单：{}", orderRecord.getSorderCode());
				try {
					PayApply payApply = new PayApply();
					payApply.setIorignTradeCode(orderRecord.getSorderCode());
					payApply.setIpayWay(80);
					List<PayApply> payApplyList = payApplyService.selectByEntityWhere(payApply);
					logger.info("手动支付成功后异步同步微信支付分订单,查询付款申请：{}", JSON.toJSONString(payApplyList));
					if (CollectionUtils.isNotEmpty(payApplyList)) {
						//同步订单
						SyncOrdersDto syncOrdersDto = new SyncOrdersDto();
						syncOrdersDto.setType("Order_Paid");
						SyncOrdersDto.detail detail = new SyncOrdersDto.detail();
						if (orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode()) {
							detail.setPaid_time(sfinishTime);
						} else if (orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode()) {
							detail.setPaid_time(DateUtils.dateToString(DateUtils.convertToDateTime(sfinishTime), "yyyyMMddHHmmss"));
						}
						syncOrdersDto.setDetail(detail);
						syncOrdersDto.setOrderCode(orderRecord.getSorderCode());
						syncOrdersDto.setOrderId(orderRecord.getId());
						syncOrdersDto.setSmerchantCode(orderRecord.getSmerchantCode());
						syncOrders(syncOrdersDto);
						logger.info("手动支付成功后异步同步微信支付分订单成功：{}", JSON.toJSONString(payApplyList));
					}
				} catch (Exception e) {
					logger.error("手动支付成功后异步同步微信支付分订单异常：{}", e);
				}
			}
		});
	}


	/**
	 * 付款失败处理
	 *
	 * @param outTradePayNo
	 * @param orderRecord     订单信息
	 * @param paySerialNumber 第三方支付流水号
	 * @param sfinishTime     支付完成时间
	 * @param errorCode       错误代码
	 * @param errorDesc       错误描叙
	 */
	private void payFail(String outTradePayNo, OrderRecord orderRecord, String paySerialNumber, String sfinishTime, String errorCode, String errorDesc) {
		//更新订单信息 //更新付款订单
		OrderRecord updateOrder = new OrderRecord();
		updateOrder.setId(orderRecord.getId());
		updateOrder.setIstatus(20);//付款失败
		if (StringUtil.isNotBlank(orderRecord.getSpayFailureReason())) {
			updateOrder.setSpayFailureReason(orderRecord.getSpayFailureReason() + ",{" + errorCode + ":" + errorDesc + "}");
		} else {
			updateOrder.setSpayFailureReason("{" + errorCode + ":" + errorDesc + "}");
		}
		this.updateBySelective(updateOrder);

		//更新付款申请数据
		PayApply updateApply = new PayApply();
		updateApply.setIcloseStatus(1);
		updateApply.setScloseTradeNo(paySerialNumber);
		updateApply.setTcloseTime(DateUtils.parseDateByFormat(sfinishTime, "yyyy-MM-dd HH:mm:ss"));
		updateApply.setId(orderRecord.getSpayApplyId());
		updateApply.setIstatus(30);
		updateApply.setTfinishDatetime(DateUtils.parseDateByFormat(sfinishTime, "yyyy-MM-dd HH:mm:ss"));
		payApplyService.updateBySelective(updateApply);


		//更新订单付款数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scode", outTradePayNo);
		map.put("serrorCode", errorCode);
		map.put("serrorDesc", errorDesc);
		map.put("sorderCode", orderRecord.getSorderCode());
		map.put("transactionId", paySerialNumber);
		map.put("istatus", 30);
		map.put("tcompleteTime", updateApply.getTfinishDatetime());
		orderPayService.updateDataByMap(map);
	}

	/***
	 * 组装免密支付请求参数
	 * @param orderPay
	 * @param orderRecord 订单数据
	 * @param freePaymentDto 请求数据
	 * @param freeData 支付宝免密数据
	 * @param type 类型 1支付 2 支付且签约
	 */
	@Override
	public FreePaymentVo assemblyFreePaymentRequest(OrderPay orderPay, OrderRecord orderRecord, FreePaymentDto freePaymentDto, FreeData freeData, int type) throws Exception {
		FreePaymentVo freePaymentVo = new FreePaymentVo();
		freePaymentVo.setOut_trade_no(orderPay.getScode());
		freePaymentVo.setSubject(freePaymentDto.getSsubject());
		freePaymentVo.setTotal_amount(orderRecord.getFactualPayAmount());
		freePaymentVo.setBody(freePaymentDto.getBody());
		if (type == 1) {
			freePaymentVo.setScene(freePaymentDto.getSpayScene());
			freePaymentVo.setProduct_code(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "product_code").getSvalue());
			//代扣支付协议信息
			freeData = freeDataService.selectByMemberId(orderRecord.getSmemberId(), orderRecord.getSmerchantCode());
			AgreementParams agreementParams = new AgreementParams();
			agreementParams.setAgreement_no(freeData.getSagreementNo());
			freePaymentVo.setAgreement_params(agreementParams);
			if (StringUtil.isNotBlank(freeData.getSproductCode()) &&
					freeData.getSproductCode().equals("ONE_TIME_AUTH_P")) {//单次代扣
				orderRecord.setIchargebackWay(BizTypeDefinitionEnum.ChargebackWay.ONE_WITHHOLDING.getCode());//单次代扣
			}
		} else if (type == 2) {
			freePaymentVo.setProduct_code(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "pay_sign_product_code").getSvalue());
			AgreementSignParams agreementSignParams = new AgreementSignParams();
			agreementSignParams.setPersonalProductCode(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "sign_personal_product_code").getSvalue());
			agreementSignParams.setSignScene(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "sign_scene").getSvalue());
			agreementSignParams.setExternalAgreementNo(freeData.getSexternalAgreementNo());
			freePaymentVo.setAgreement_sign_params(agreementSignParams);
		}

		/*MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO,RedisConst.SH_CLIENT_CONFIG+orderRecord.getSmerchantCode());
        if (null != clientConf && null != clientConf.getIisSeparateAccounts()
				&& clientConf.getIisSeparateAccounts().intValue() == 1) {
			logger.info("商户开启分润模式,商户编号：{}，订单编号：{}",orderRecord.getSmemberCode(), orderRecord.getSorderCode());
			MerchantConf orderConf = merchantInfoService.getAlipayMerchantConf(orderRecord.getSmerchantCode());
			if (null != orderConf && StringUtil.isNotBlank(orderConf.getSpid())) {
				if (!freePaymentDto.getSmerchantCode().equals(orderRecord.getSmemberCode())) {//不是收款商户
					//分账最小金额
					BigDecimal minRoyaltyAmount = new BigDecimal(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "royalty_min_amount").getSvalue());
					BigDecimal separateScale = BigDecimal.ZERO;
					if(null != clientConf.getSeparateAccountsPro()) {
						separateScale = new BigDecimal(clientConf.getSeparateAccountsPro()).divide(new BigDecimal("100"));
					}
					//支付宝实际到账金额
					BigDecimal alipayActualAmount = separateScale.multiply(freePaymentVo.getTotal_amount());
					if (alipayActualAmount.doubleValue() >= minRoyaltyAmount.doubleValue()
							&& alipayActualAmount.doubleValue() > 0) {//分账金额大于最小分账金额
						//分账信息
						RoyaltyInfo royaltyInfo = new RoyaltyInfo();
						royaltyInfo.setRoyaltyType(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "royalty_type").getSvalue());
						RoyaltyDetailInfos royaltyDetailInfos = new RoyaltyDetailInfos();
						royaltyDetailInfos.setSerialNo(1l);
						royaltyDetailInfos.setTransInType("userId");
						royaltyDetailInfos.setBatchNo(orderRecord.getId());
						royaltyDetailInfos.setOutRelationId(orderRecord.getSorderCode());
						royaltyDetailInfos.setTransOutType("userId");
						MerchantConf conf = merchantInfoService.getAlipayMerchantConf(freePaymentDto.getSmerchantCode(),2);
						royaltyDetailInfos.setTransOut(conf.getSpid());

						royaltyDetailInfos.setTransIn(orderConf.getSpid());
						royaltyDetailInfos.setAmount(alipayActualAmount.multiply(new BigDecimal("100")).longValue());
						royaltyDetailInfos.setDesc("分账到商户");
						royaltyDetailInfos.setAmountPercentage("100");
						List<RoyaltyDetailInfos> tempList = new ArrayList<RoyaltyDetailInfos>();
						tempList.add(royaltyDetailInfos);
						royaltyInfo.setRoyaltyDetailInfos(tempList);
						freePaymentVo.setRoyalty_info(royaltyInfo);
					}
				}
			} else {
				logger.error("未配置商户合作伙伴ID");
			}
		}*/

		return freePaymentVo;
	}

	/**
	 * 处理免密支付并签约回调
	 *
	 * @param conf 商户配置信息
	 * @param map  支付宝返回参数
	 * @return
	 */
	@Override
	public boolean dealwithAlipayFreePaymentAndSignNotify(MerchantConf conf, Map<String, String> map) throws Exception {

		return false;
	}

	/**
	 * 更新订单状态
	 *
	 * @param pmap 参数集合
	 * @return
	 */
	@Override
	public Integer updateStatusByOrderId(Map<String, Object> pmap) {
		return orderRecordDao.updateStatusByOrderId(pmap);
	}

	/**
	 * 微信支付分创建订单
	 *
	 * @param creatSmartretailOrderDto
	 * @return
	 */
	@Override
	public ResponseVo<CreatSmartretailOrderResult> creatSmartretailOrder(CreatSmartretailOrderDto creatSmartretailOrderDto) throws Exception {
		ResponseVo<CreatSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
		//判断订单有效性
		ResponseVo<OrderRecord> resVo = validateOrderEffective(creatSmartretailOrderDto);
		if (!resVo.isSuccess()) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(resVo.getMsg());
		}
		OrderRecord orderRecord = resVo.getData();
		//新增付款申请
		OrderPay orderPay = orderPayService.insertOrderPay(orderRecord, 10, 1);
		creatSmartretailOrderDto.setRisk_amount(20000);
		creatSmartretailOrderDto.setNeed_user_confirm(false);
		creatSmartretailOrderDto.setOut_order_no(orderPay.getScode());
		creatSmartretailOrderDto.setSmerchantCode(orderRecord.getSmerchantCode());
		creatSmartretailOrderDto.setSmemberId(orderRecord.getSmemberId());
		// creatSmartretailOrderDto.setService_start_time(DateUtils.dateToString(orderRecord.getTorderTime(),"yyyyMMddHHmmss"));
		creatSmartretailOrderDto.setService_start_time("OnAccept");
		Date date = DateUtils.getCurrentDateTime();
		date = DateUtils.addMinutes(date, 2);
		creatSmartretailOrderDto.setService_end_time(DateUtils.dateToString(date, "yyyyMMddHHmmss"));
		//creatSmartretailOrderDto.setService_start_time(DateUtils.getCurrentDTimeNumber());
		DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(orderRecord.getSdeviceId());
		creatSmartretailOrderDto.setService_start_location(deviceInfo.getSputAddress());
		creatSmartretailOrderDto.setService_introduction("智慧零售");
		//获取用户的支付宝免密数据
		WechatFreeData freeData = wechatFreeDataService.selectByMemberIdAndWithholdType(creatSmartretailOrderDto.getSmemberId(), creatSmartretailOrderDto.getSmerchantCode(), 20);
		if (null == freeData) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户签约异常");
		}
		//获取商户配置信息
		/*MerchantConf conf = merchantInfoService.getWechatMerchantConf(creatSmartretailOrderDto.getSmerchantCode(), 2);*/
		String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + creatSmartretailOrderDto.getSmerchantCode());
		if (StringUtil.isBlank(json)) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
		MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
		if (null == conf) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
		CreatSmartretailOrderData creatSmartretailOrderData = new CreatSmartretailOrderData.CreatSmartretailOrderBuilder(conf, freeData.getSopenid(), creatSmartretailOrderDto).build();
		CloseableHttpResponse response = wxPayApi.creatSmartretailOrder(conf, creatSmartretailOrderData);
		Integer statusCode = response.getStatusLine().getStatusCode();
		String err_code_des = "微信支付分创建订单异常";
		if (null != response) {
			HttpEntity entity2 = response.getEntity();
			String str = EntityUtils.toString(entity2, "utf-8");
			if (null != statusCode && statusCode == 200) {
				//更新订单信息
				PayApply payApply = this.insertPayApply(orderRecord, creatSmartretailOrderDto.getSremark());
				OrderRecord orderRecordTemp = new OrderRecord();
				orderRecordTemp.setId(orderRecord.getId());
				orderRecordTemp.setSpayApplyId(payApply.getId());
				orderRecordDao.updateByIdSelective(orderRecordTemp);
				EntityUtils.consume(entity2);
				CreatSmartretailOrderResult creatSmartretailOrderResult = JSON.parseObject(str, CreatSmartretailOrderResult.class);
				creatSmartretailOrderResult.setSmemberId(creatSmartretailOrderDto.getSmemberId());
				creatSmartretailOrderResult.setSmerchantCode(creatSmartretailOrderDto.getSmerchantCode());
				responseVo.setData(creatSmartretailOrderResult);
				return responseVo;
			} else {
				Map<String, String> map = JSON.parseObject(str, Map.class);
				err_code_des = String.valueOf(map.get("message"));
			}
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(err_code_des);
		return responseVo;
	}

	/**
	 * 微信支付分创建订单
	 *
	 * @param creatSmartretailOrderDto
	 * @return
	 */
	@Override
	public ResponseVo<CreatSmartretailOrderResult> creatSmartretailOrderN(CreatSmartretailOrderDto creatSmartretailOrderDto) throws Exception {
		ResponseVo<CreatSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
		//判断订单有效性
    /*      ResponseVo<OrderRecord> resVo = validateOrderEffective(creatSmartretailOrderDto);
        if (!resVo.isSuccess()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(resVo.getMsg());
        }
        OrderRecord orderRecord = resVo.getData();
        //新增付款申请
        OrderPay orderPay = orderPayService.insertOrderPay(orderRecord, 10, 1);*/
		CreatApply creatApply = new CreatApply();
		creatApply.setSadduserId(creatSmartretailOrderDto.getSmemberId());
		creatApply.setSdeviceCode(creatSmartretailOrderDto.getDeviceCode());
		creatApply.setSdeviceId(creatSmartretailOrderDto.getDeviceId());
		creatApply.setSmemberId(creatSmartretailOrderDto.getSmemberId());
		creatApply.setSmemberNo(creatSmartretailOrderDto.getSmemberCode());
		creatApply.setTaddTime(DateUtils.getCurrentDateTime());
		creatApply.setIstatus(10);
		creatApply.setScode(CoreUtils.newCode(EntityTables.SQ_CREAT_APPLY));
		creatApply.setSorderPayCode(CoreUtils.newCode("om_order_pay"));
		// creatApply.setScode(CoreUtils.newCode(""));
		creatApplyService.insert(creatApply);
		creatSmartretailOrderDto.setRisk_amount(20000);
		creatSmartretailOrderDto.setNeed_user_confirm(false);
		creatSmartretailOrderDto.setOut_order_no(creatApply.getSorderPayCode());
		creatSmartretailOrderDto.setSmerchantCode(creatSmartretailOrderDto.getSmerchantCode());
		creatSmartretailOrderDto.setSmemberId(creatSmartretailOrderDto.getSmemberId());
		// creatSmartretailOrderDto.setService_start_time(DateUtils.dateToString(orderRecord.getTorderTime(),"yyyyMMddHHmmss"));
		creatSmartretailOrderDto.setService_start_time("OnAccept");
		Date date = DateUtils.getCurrentDateTime();
		date = DateUtils.addMinutes(date, 2);
		creatSmartretailOrderDto.setService_end_time(DateUtils.dateToString(date, "yyyyMMddHHmmss"));
		//creatSmartretailOrderDto.setService_start_time(DateUtils.getCurrentDTimeNumber());
		DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(creatSmartretailOrderDto.getDeviceId());
		creatSmartretailOrderDto.setService_start_location(deviceInfo.getSputAddress());
		creatSmartretailOrderDto.setService_introduction("智慧零售");
		//获取用户的支付宝免密数据
		WechatFreeData freeData = wechatFreeDataService.selectByMemberIdAndWithholdType(creatSmartretailOrderDto.getSmemberId(), creatSmartretailOrderDto.getSmerchantCode(), 20);
		if (null == freeData) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户签约异常");
		}
		//获取商户配置信息
		MerchantConf conf = merchantInfoService.getWechatMerchantConf(creatSmartretailOrderDto.getSmerchantCode(), 2);
      /*  String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + creatSmartretailOrderDto.getSmerchantCode());
        if (StringUtil.isBlank(json)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
        }
        MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
        if (null == conf) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
        }*/
		CreatSmartretailOrderData creatSmartretailOrderData = new CreatSmartretailOrderData.CreatSmartretailOrderBuilder(conf, freeData.getSopenid(), creatSmartretailOrderDto).build();
		CloseableHttpResponse response = wxPayApi.creatSmartretailOrder(conf, creatSmartretailOrderData);
		Integer statusCode = response.getStatusLine().getStatusCode();
		String err_code_des = "微信支付分创建订单异常";
		if (null != response) {
			HttpEntity entity2 = response.getEntity();
			String str = EntityUtils.toString(entity2, "utf-8");
			if (null != statusCode && statusCode == 200) {
				//更新订单信息
                /*PayApply payApply = this.insertPayApply(orderRecord, creatSmartretailOrderDto.getSremark());
                OrderRecord orderRecordTemp = new OrderRecord();
                orderRecordTemp.setId(orderRecord.getId());
                orderRecordTemp.setSpayApplyId(payApply.getId());
                orderRecordDao.updateByIdSelective(orderRecordTemp);*/
				EntityUtils.consume(entity2);
				CreatSmartretailOrderResult creatSmartretailOrderResult = JSON.parseObject(str, CreatSmartretailOrderResult.class);
				creatSmartretailOrderResult.setSmemberId(creatSmartretailOrderDto.getSmemberId());
				creatSmartretailOrderResult.setSmerchantCode(creatSmartretailOrderDto.getSmerchantCode());
				responseVo.setData(creatSmartretailOrderResult);
				return responseVo;
			} else {
				logger.info("创建微信支付分失败,修改创建请求参数：{}", JSON.toJSONString(creatApply));
				CreatApply temp = new CreatApply();
				temp.setIstatus(40);
				temp.setId(creatApply.getId());
				creatApplyService.updateByPrimaryKey(temp);
				Map<String, String> map = JSON.parseObject(str, Map.class);
				err_code_des = String.valueOf(map.get("message"));
			}
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(err_code_des);
		return responseVo;
	}

	/**
	 * 微信支付分完结订单
	 *
	 * @param endSmartretailOrderDto
	 * @return
	 */
	@Override
	public ResponseVo<EndSmartretailOrderResult> endSmartretailOrder(EndSmartretailOrderDto endSmartretailOrderDto) throws Exception {
		ResponseVo<EndSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
		//获取用户的支付宝免密数据
		WechatFreeData freeData = wechatFreeDataService.selectByMemberIdAndWithholdType(endSmartretailOrderDto.getSmemberId(), endSmartretailOrderDto.getSmerchantCode(), 20);
		if (null == freeData) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户签约异常");
		}
		//获取商户配置信息
		MerchantConf conf = merchantInfoService.getWechatMerchantConf(endSmartretailOrderDto.getSmerchantCode(), 2);
		if (null == conf) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
    /*    String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + endSmartretailOrderDto.getSmerchantCode());
        if (StringUtil.isBlank(json)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
        }
        MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
        if (null == conf) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
        }*/
		EndSmartretailOrderData endSmartretailOrderData = new EndSmartretailOrderData.EndSmartretailOrderDataBuilder(conf, endSmartretailOrderDto).build();
		CloseableHttpResponse response = wxPayApi.endSmartretailOrder(conf, endSmartretailOrderData, endSmartretailOrderDto.getOut_order_no());
		Integer statusCode = response.getStatusLine().getStatusCode();
		String err_code_des = "微信支付分完结订单异常";
		if (null != response) {
			HttpEntity entity2 = response.getEntity();
			String str = EntityUtils.toString(entity2, "utf-8");
			if (null != statusCode && statusCode == 200) {
				//更新订单信息
				OrderRecord updateOrder = new OrderRecord();
				updateOrder.setId(endSmartretailOrderDto.getOrderId());
				updateOrder.setIstatus(BizTypeDefinitionEnum.OrderStatus.IN_PAYMENT.getCode());
				this.updateBySelective(updateOrder);
				EntityUtils.consume(entity2);
				EndSmartretailOrderResult endSmartretailOrderResult = JSON.parseObject(str, EndSmartretailOrderResult.class);
				responseVo.setData(endSmartretailOrderResult);
				return responseVo;
			} else {
				Map<String, String> map = JSON.parseObject(str, Map.class);
				err_code_des = String.valueOf(map.get("message"));
			}
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(err_code_des);
		return responseVo;
	}

	/**
	 * 微信支付分完结订单
	 *
	 * @param endSmartretailOrderDto
	 * @return
	 */
	@Override
	public ResponseVo<EndSmartretailOrderResult> endSmartretailOrderN(EndSmartretailOrderDto endSmartretailOrderDto) throws Exception {
		ResponseVo<EndSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
		//获取商户配置信息
		MerchantConf conf = merchantInfoService.getWechatMerchantConf(endSmartretailOrderDto.getSmerchantCode(), 2);
		if (null == conf) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
		if (endSmartretailOrderDto.getFinish_type() == 2) {
			//判断订单有效性
			ResponseVo<OrderRecord> resVo = validateOrderEffectiveN(endSmartretailOrderDto);
			if (!resVo.isSuccess()) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(resVo.getMsg());
			}
			//获取用户的支付宝免密数据
			WechatFreeData freeData = wechatFreeDataService.selectByMemberIdAndWithholdType(endSmartretailOrderDto.getSmemberId(), endSmartretailOrderDto.getSmerchantCode(), 20);
			if (null == freeData) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户签约异常");
			}
			OrderRecord orderRecord = resVo.getData();
			//新增付款申请
			orderPayService.insertOrderPayN(orderRecord, 10, 1, endSmartretailOrderDto.getOut_order_no());
			endSmartretailOrderDto.setOut_order_no(endSmartretailOrderDto.getOut_order_no());
			//更新订单信息
			PayApply payApply = this.insertPayApply(orderRecord, "微信支付分支付请求");
			OrderRecord orderRecordTemp = new OrderRecord();
			orderRecordTemp.setId(orderRecord.getId());
			orderRecordTemp.setSpayApplyId(payApply.getId());
			orderRecordDao.updateByIdSelective(orderRecordTemp);
			//完结订单
		}
		EndSmartretailOrderData endSmartretailOrderData = new EndSmartretailOrderData.EndSmartretailOrderDataBuilder(conf, endSmartretailOrderDto).build();
		CloseableHttpResponse response = wxPayApi.endSmartretailOrder(conf, endSmartretailOrderData, endSmartretailOrderDto.getOut_order_no());
		Integer statusCode = response.getStatusLine().getStatusCode();
		String err_code_des = "微信支付分完结订单异常";
		if (null != response) {
			HttpEntity entity2 = response.getEntity();
			String str = EntityUtils.toString(entity2, "utf-8");
			if (null != statusCode && statusCode == 200) {
				//更新订单信息
				if (endSmartretailOrderDto.getFinish_type() == 2) {
					OrderRecord updateOrder = new OrderRecord();
					updateOrder.setId(endSmartretailOrderDto.getOrderId());
					updateOrder.setIstatus(BizTypeDefinitionEnum.OrderStatus.IN_PAYMENT.getCode());
					this.updateBySelective(updateOrder);
				}
				EntityUtils.consume(entity2);
				EndSmartretailOrderResult endSmartretailOrderResult = JSON.parseObject(str, EndSmartretailOrderResult.class);
				responseVo.setData(endSmartretailOrderResult);
				return responseVo;
			} else {
				Map<String, String> map = JSON.parseObject(str, Map.class);
				err_code_des = String.valueOf(map.get("message"));
			}
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(err_code_des);
		return responseVo;
	}

	/**
	 * 微信支付分生成订单(创建订单,查询订单完结凭证,完结订单)
	 *
	 * @param creatSmartretailOrderDto
	 * @return
	 */
	@Override
	public ResponseVo<EndSmartretailOrderResult> deneratingOrders(CreatSmartretailOrderDto creatSmartretailOrderDto) throws Exception {
		List<FeeDto> feeDtos = creatSmartretailOrderDto.getFees();
		creatSmartretailOrderDto.setFees(null);
		ResponseVo<CreatSmartretailOrderResult> creatResponseVo = creatSmartretailOrder(creatSmartretailOrderDto);
		ResponseVo<EndSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
		responseVo.setMsg("微信支付分生成订单!");
		if (null != creatResponseVo && creatResponseVo.isSuccess()) {
			CreatSmartretailOrderResult creatSmartretailOrderResult = creatResponseVo.getData();
			logger.info("创建订单服务生成订单成功,服务订单号：{}", creatSmartretailOrderResult.getOut_order_no());
			QuerySmartretailOrderDto querySmartretailOrderDto = new QuerySmartretailOrderDto();
			querySmartretailOrderDto.setAppid(creatSmartretailOrderResult.getAppid());
			querySmartretailOrderDto.setOut_order_no(creatSmartretailOrderResult.getOut_order_no());
			querySmartretailOrderDto.setService_id(creatSmartretailOrderResult.getService_id());
			querySmartretailOrderDto.setSmerchantCode(creatSmartretailOrderResult.getSmerchantCode());
			ResponseVo<QuerySmartretailOrderResult> queryResponseVo = querySmartretailOrder(querySmartretailOrderDto);
			if (null != queryResponseVo && queryResponseVo.isSuccess()) {
				QuerySmartretailOrderResult querySmartretailOrderResult = queryResponseVo.getData();
				logger.info("查询订单服务生成订单成功,服务订单号：{}", querySmartretailOrderResult.getOut_order_no());
				if (querySmartretailOrderResult.getState().equals("USER_ACCEPTED")) {
					EndSmartretailOrderDto endSmartretailOrderDto = new EndSmartretailOrderDto();
					endSmartretailOrderDto.setFinish_type(2);
					endSmartretailOrderDto.setSmemberId(creatSmartretailOrderDto.getSmemberId());
					endSmartretailOrderDto.setSmerchantCode(creatSmartretailOrderDto.getSmerchantCode());
					endSmartretailOrderDto.setFees(feeDtos);
					endSmartretailOrderDto.setTotal_amount(creatSmartretailOrderDto.getTotal_amount());
					Date date = DateUtils.getCurrentDateTime();
					date = DateUtils.addSeconds(date, 1);
					endSmartretailOrderDto.setReal_service_end_time(DateUtils.dateToString(date, "yyyyMMddHHmmss"));
					//endSmartretailOrderDto.setReal_service_end_time(DateUtils.getCurrentDTimeNumber());
					endSmartretailOrderDto.setOut_order_no(querySmartretailOrderResult.getOut_order_no());
					endSmartretailOrderDto.setFinish_ticket(querySmartretailOrderResult.getFinish_ticket());
					endSmartretailOrderDto.setProfit_sharing(false);
					endSmartretailOrderDto.setOrderId(creatSmartretailOrderDto.getOrderId());
					ResponseVo<EndSmartretailOrderResult> endResponseVo = endSmartretailOrder(endSmartretailOrderDto);
					if (null != endResponseVo && endResponseVo.isSuccess()) {
						logger.info("完结订单成功：{}", endResponseVo.getData().getOut_order_no());
						return endResponseVo;
					} else {
						logger.info("微信支付分生成订单完结订单失败：{}", endResponseVo.getMsg());
						//0元结单 修改支付状态 修改申请状态
						zeroCloseOrder(creatSmartretailOrderResult, creatSmartretailOrderDto, feeDtos);
						responseVo.setMsg(endResponseVo.getMsg());
					}
				} else {
					logger.info("服务订单状态不正确,无法得到完结凭证：{}", querySmartretailOrderResult.getState());
					responseVo.setMsg("服务订单状态不正确");
				}
			} else {
				logger.info("微信支付分生成订单查询订单失败:{}", queryResponseVo.getMsg());
				//0元结单 修改支付状态 修改申请状态
				zeroCloseOrder(creatSmartretailOrderResult, creatSmartretailOrderDto, feeDtos);
				responseVo.setMsg(queryResponseVo.getMsg());
			}
		} else {
			logger.info("微信支付分生成订单创建订单失败:{}", creatResponseVo.getMsg());
			responseVo.setMsg(creatResponseVo.getMsg());
		}
		responseVo.setSuccess(false);
		return responseVo;
	}

	/**
	 * 微信支付查询并且完结订单
	 *
	 * @param queryAndEndSmartretailOrderDto
	 * @return
	 */
	@Override
	public ResponseVo<EndSmartretailOrderResult> createAndEndSmartretailOrder(QueryAndEndSmartretailOrderDto queryAndEndSmartretailOrderDto) throws Exception {
		ResponseVo<EndSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
		QuerySmartretailOrderDto querySmartretailOrderDto = new QuerySmartretailOrderDto();
		CreatApply creatApply = null;
		if (StringUtil.isBlank(queryAndEndSmartretailOrderDto.getCreatApplyId())) {
			creatApply = creatApplyService.selectByUserIdAndDeviceId(queryAndEndSmartretailOrderDto.getSmemberId(), queryAndEndSmartretailOrderDto.getDeviceId());
		} else {
			creatApply = creatApplyService.selectByPrimaryKey(queryAndEndSmartretailOrderDto.getCreatApplyId());
		}
		if (null == creatApply) {
			logger.info("未查询到用户在设备上有创建微信支付分记录：{}", JSON.toJSONString(queryAndEndSmartretailOrderDto));
			responseVo.setSuccess(false);
			responseVo.setMsg("未查询到用户在设备上有创建微信支付分记录!");
			return responseVo;
		}
		querySmartretailOrderDto.setOut_order_no(creatApply.getSorderPayCode());
		querySmartretailOrderDto.setSmerchantCode(queryAndEndSmartretailOrderDto.getSmerchantCode());
		ResponseVo<QuerySmartretailOrderResult> queryResponseVo = querySmartretailOrder(querySmartretailOrderDto);
		if (null != queryResponseVo && queryResponseVo.isSuccess()) {
			QuerySmartretailOrderResult querySmartretailOrderResult = queryResponseVo.getData();
			logger.info("查询订单服务生成订单：{}", JSON.toJSONString(querySmartretailOrderResult));
			String state = querySmartretailOrderResult.getState();
			if (state.equals("USER_ACCEPTED")) {
				EndSmartretailOrderDto endSmartretailOrderDto = new EndSmartretailOrderDto();
				if (queryAndEndSmartretailOrderDto.getFinish_type() == 2) {
					endSmartretailOrderDto.setFees(queryAndEndSmartretailOrderDto.getFees());
					endSmartretailOrderDto.setTotal_amount(queryAndEndSmartretailOrderDto.getTotal_amount());
					Date date = DateUtils.getCurrentDateTime();
					date = DateUtils.addSeconds(date, 1);
					endSmartretailOrderDto.setReal_service_end_time(DateUtils.dateToString(date, "yyyyMMddHHmmss"));
					endSmartretailOrderDto.setOrderId(queryAndEndSmartretailOrderDto.getOrderId());
				}
				endSmartretailOrderDto.setSmemberId(queryAndEndSmartretailOrderDto.getSmemberId());
				endSmartretailOrderDto.setSmerchantCode(queryAndEndSmartretailOrderDto.getSmerchantCode());
				endSmartretailOrderDto.setOut_order_no(querySmartretailOrderResult.getOut_order_no());
				endSmartretailOrderDto.setFinish_ticket(querySmartretailOrderResult.getFinish_ticket());
				endSmartretailOrderDto.setProfit_sharing(queryAndEndSmartretailOrderDto.isProfit_sharing());
				endSmartretailOrderDto.setFinish_type(queryAndEndSmartretailOrderDto.getFinish_type());
				endSmartretailOrderDto.setCancel_reason(queryAndEndSmartretailOrderDto.getCancel_reason());
				endSmartretailOrderDto.setOut_order_no(creatApply.getSorderPayCode());
				ResponseVo<EndSmartretailOrderResult> endResponseVo = endSmartretailOrderN(endSmartretailOrderDto);
				if (null != endResponseVo && endResponseVo.isSuccess()) {
					logger.info("完结订单成功：{}", endResponseVo.getData().getOut_order_no());
					//取消/完结订单修改创建订单请求状态
					CreatApply temp = new CreatApply();
					temp.setId(creatApply.getId());
					if (queryAndEndSmartretailOrderDto.getFinish_type() == 1) {
						temp.setIstatus(20);
					} else if (queryAndEndSmartretailOrderDto.getFinish_type() == 2) {
						temp.setIstatus(30);
					}
					creatApplyService.updateBySelective(temp);
					return endResponseVo;
				} else {
					logger.info("微信支付分生成订单完结订单失败：{}", endResponseVo.getMsg());
					//0元结单 修改支付状态 修改申请状态
					zeroCloseOrderN(creatApply.getSorderPayCode(), queryAndEndSmartretailOrderDto);
					responseVo.setMsg(endResponseVo.getMsg());
				}
			} else if (("FINISHED".equals(state) || "USER_PAID".equals(state)) && creatApply.getIstatus() == 10) {
				CreatApply temp = new CreatApply();
				temp.setIstatus(30);
				temp.setId(creatApply.getId());
				creatApplyService.updateBySelective(temp);
				logger.info("订单已完结或已经支付成功,修改申请创建记录状态：{}", state);
				return responseVo;
			} else {
				logger.info("服务订单状态不正确,无法得到完结凭证：{}", querySmartretailOrderResult.getState());
				responseVo.setMsg("服务订单状态不正确");
			}
		} else {
			logger.info("微信支付分生成订单查询订单失败:{}", queryResponseVo.getMsg());
			//0元结单 修改支付状态 修改申请状态
			/*  zeroCloseOrderN(creatApply.getSorderPayCode(), queryAndEndSmartretailOrderDto);*/
			responseVo.setMsg(queryResponseVo.getMsg());
		}
		responseVo.setSuccess(false);
		return responseVo;
	}

	/**
	 * 微信支付分0元结单
	 *
	 * @param creatSmartretailOrderResult
	 * @param creatSmartretailOrderDto
	 * @param feeDtos
	 */
	private void zeroCloseOrder(final CreatSmartretailOrderResult creatSmartretailOrderResult, final CreatSmartretailOrderDto creatSmartretailOrderDto, final List<FeeDto> feeDtos) {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					logger.info("微信支付分0元结单：{}", JSON.toJSONString(creatSmartretailOrderDto));
					ResponseVo<EndSmartretailOrderResult> endResponseVo = queryAndEndOrder(creatSmartretailOrderResult, creatSmartretailOrderDto, feeDtos);
					if (null != endResponseVo && endResponseVo.isSuccess()) {
						logger.info("微信支付分0元结单成功：{}", creatSmartretailOrderResult.getOut_order_no());
						OrderRecord orderRecord = new OrderRecord();
						orderRecord.setId(creatSmartretailOrderDto.getOrderId());
						orderRecord.setIstatus(BizTypeDefinitionEnum.OrderStatus.PAYMENT_FAIL.getCode());
						orderRecord.setTupdateTime(DateUtils.getCurrentDateTime());
						orderRecordDao.updateByIdSelective(orderRecord);
						OrderRecord orderRecord1 = orderRecordDao.selectByPrimaryKey(creatSmartretailOrderDto.getOrderId());
						PayApply payApply = payApplyService.selectByPrimaryKey(orderRecord1.getSpayApplyId());
						Map<String, Object> map = new HashMap<>();
						map.put("applyId", payApply.getId());
						map.put("istatus", 30);
						payApplyService.updateStatusById(map);
					} else {
						logger.info("微信支付分0元结单失败：{}", creatSmartretailOrderResult.getOut_order_no());
					}
				} catch (Exception e) {
					logger.info("微信支付分0元结单异常：{}", e);

				}
			}
		});
	}

	/**
	 * 微信支付分0元结单
	 *
	 * @param outOrderNo
	 * @param queryAndEndSmartretailOrderDto
	 */
	private void zeroCloseOrderN(final String outOrderNo, final QueryAndEndSmartretailOrderDto queryAndEndSmartretailOrderDto) {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					logger.info("微信支付分0元结单：{}", JSON.toJSONString(queryAndEndSmartretailOrderDto));
					ResponseVo<EndSmartretailOrderResult> endResponseVo = queryAndZeroEndOrder(outOrderNo, queryAndEndSmartretailOrderDto);
					if (null != endResponseVo && endResponseVo.isSuccess()) {
						logger.info("微信支付分0元结单成功：{}", outOrderNo);
						OrderRecord orderRecord = new OrderRecord();
						orderRecord.setId(queryAndEndSmartretailOrderDto.getOrderId());
						orderRecord.setIstatus(BizTypeDefinitionEnum.OrderStatus.PAYMENT_FAIL.getCode());
						orderRecord.setTupdateTime(DateUtils.getCurrentDateTime());
						orderRecordDao.updateByIdSelective(orderRecord);
						OrderRecord orderRecord1 = orderRecordDao.selectByPrimaryKey(queryAndEndSmartretailOrderDto.getOrderId());
						PayApply payApply = payApplyService.selectByPrimaryKey(orderRecord1.getSpayApplyId());
						Map<String, Object> map = new HashMap<>();
						map.put("applyId", payApply.getId());
						map.put("istatus", 30);
						payApplyService.updateStatusById(map);
					} else {
						logger.info("微信支付分0元结单失败：{}", outOrderNo);
					}
				} catch (Exception e) {
					logger.info("微信支付分0元结单异常：{}", e);

				}
			}
		});
	}

	/**
	 * 微信支付分0元结单
	 *
	 * @param creatSmartretailOrderResult
	 * @param creatSmartretailOrderDto
	 * @param feeDtos
	 * @return
	 * @throws Exception
	 */
	private ResponseVo queryAndEndOrder(CreatSmartretailOrderResult creatSmartretailOrderResult, CreatSmartretailOrderDto creatSmartretailOrderDto, List<FeeDto> feeDtos) throws Exception {
		logger.info("微信支付分考试0元结单,服务订单号：{}", creatSmartretailOrderDto.getOut_order_no());
		ResponseVo<EndSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
		QuerySmartretailOrderDto querySmartretailOrderDto = new QuerySmartretailOrderDto();
		querySmartretailOrderDto.setAppid(creatSmartretailOrderResult.getAppid());
		querySmartretailOrderDto.setOut_order_no(creatSmartretailOrderResult.getOut_order_no());
		querySmartretailOrderDto.setService_id(creatSmartretailOrderResult.getService_id());
		querySmartretailOrderDto.setSmerchantCode(creatSmartretailOrderResult.getSmerchantCode());
		ResponseVo<QuerySmartretailOrderResult> queryResponseVo = querySmartretailOrder(querySmartretailOrderDto);
		if (null != queryResponseVo && queryResponseVo.isSuccess()) {
			QuerySmartretailOrderResult querySmartretailOrderResult = queryResponseVo.getData();
			logger.info("查询订单服务生成订单成功,服务订单号：{}", querySmartretailOrderResult.getOut_order_no());
			if (querySmartretailOrderResult.getState().equals("USER_ACCEPTED")) {
				EndSmartretailOrderDto endSmartretailOrderDto = new EndSmartretailOrderDto();
				for (FeeDto feeDto : feeDtos) {
					feeDto.setFee_amount(0);
				}
				endSmartretailOrderDto.setFinish_type(2);
				endSmartretailOrderDto.setSmemberId(creatSmartretailOrderDto.getSmemberId());
				endSmartretailOrderDto.setSmerchantCode(creatSmartretailOrderDto.getSmerchantCode());
				endSmartretailOrderDto.setFees(feeDtos);
				endSmartretailOrderDto.setTotal_amount(0);
				Date date = DateUtils.getCurrentDateTime();
				date = DateUtils.addSeconds(date, 1);
				endSmartretailOrderDto.setReal_service_end_time(DateUtils.dateToString(date, "yyyyMMddHHmmss"));
				//endSmartretailOrderDto.setReal_service_end_time(DateUtils.getCurrentDTimeNumber());
				endSmartretailOrderDto.setOut_order_no(querySmartretailOrderResult.getOut_order_no());
				endSmartretailOrderDto.setFinish_ticket(querySmartretailOrderResult.getFinish_ticket());
				endSmartretailOrderDto.setProfit_sharing(false);
				endSmartretailOrderDto.setOrderId(creatSmartretailOrderDto.getOrderId());
				ResponseVo<EndSmartretailOrderResult> endResponseVo = endSmartretailOrder(endSmartretailOrderDto);
				return endResponseVo;
			}
		} else {
			logger.info("微信支付分生成订单查询订单失败:{}", queryResponseVo.getMsg());
			responseVo.setMsg(queryResponseVo.getMsg());
		}
		responseVo.setSuccess(false);
		return responseVo;
	}

	/**
	 * 微信支付分0元结单
	 *
	 * @param outOrderNo
	 * @param queryAndEndSmartretailOrderDto
	 * @return
	 * @throws Exception
	 */
	private ResponseVo queryAndZeroEndOrder(String outOrderNo, QueryAndEndSmartretailOrderDto queryAndEndSmartretailOrderDto) throws Exception {
		logger.info("微信支付分开始0元结单,服务订单号：{}", outOrderNo);
		ResponseVo<EndSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
		QuerySmartretailOrderDto querySmartretailOrderDto = new QuerySmartretailOrderDto();
		querySmartretailOrderDto.setOut_order_no(outOrderNo);
		querySmartretailOrderDto.setSmerchantCode(queryAndEndSmartretailOrderDto.getSmerchantCode());
		ResponseVo<QuerySmartretailOrderResult> queryResponseVo = querySmartretailOrder(querySmartretailOrderDto);
		if (null != queryResponseVo && queryResponseVo.isSuccess()) {
			QuerySmartretailOrderResult querySmartretailOrderResult = queryResponseVo.getData();
			logger.info("查询订单服务生成订单成功,服务订单号：{}", querySmartretailOrderResult.getOut_order_no());
			if (querySmartretailOrderResult.getState().equals("USER_ACCEPTED")) {
				EndSmartretailOrderDto endSmartretailOrderDto = new EndSmartretailOrderDto();
				List<FeeDto> feeDtos = queryAndEndSmartretailOrderDto.getFees();
				for (FeeDto feeDto : feeDtos) {
					feeDto.setFee_amount(0);
				}
				endSmartretailOrderDto.setFinish_type(2);
				endSmartretailOrderDto.setSmemberId(queryAndEndSmartretailOrderDto.getSmemberId());
				endSmartretailOrderDto.setSmerchantCode(queryAndEndSmartretailOrderDto.getSmerchantCode());
				endSmartretailOrderDto.setFees(feeDtos);
				endSmartretailOrderDto.setTotal_amount(0);
				Date date = DateUtils.getCurrentDateTime();
				date = DateUtils.addSeconds(date, 1);
				endSmartretailOrderDto.setReal_service_end_time(DateUtils.dateToString(date, "yyyyMMddHHmmss"));
				//endSmartretailOrderDto.setReal_service_end_time(DateUtils.getCurrentDTimeNumber());
				endSmartretailOrderDto.setOut_order_no(querySmartretailOrderResult.getOut_order_no());
				endSmartretailOrderDto.setFinish_ticket(querySmartretailOrderResult.getFinish_ticket());
				endSmartretailOrderDto.setProfit_sharing(false);
				endSmartretailOrderDto.setOrderId(queryAndEndSmartretailOrderDto.getOrderId());
				ResponseVo<EndSmartretailOrderResult> endResponseVo = endSmartretailOrder(endSmartretailOrderDto);
				return endResponseVo;
			}
		} else {
			logger.info("微信支付分生成订单查询订单失败:{}", queryResponseVo.getMsg());
			responseVo.setMsg(queryResponseVo.getMsg());
		}
		responseVo.setSuccess(false);
		return responseVo;
	}

	/**
	 * 微信支付分查询订单
	 *
	 * @param querySmartretailOrderDto
	 * @return
	 */
	@Override
	public ResponseVo<QuerySmartretailOrderResult> querySmartretailOrder(QuerySmartretailOrderDto querySmartretailOrderDto) throws Exception {
		ResponseVo<QuerySmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
		//获取用户的支付宝免密数据
       /* WechatFreeData freeData = wechatFreeDataService.selectByMemberId(querySmartretailOrderDto.getSmemberId());
        if (null == freeData) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户签约异常");
        }*/
		//获取商户配置信息
		MerchantConf conf = merchantInfoService.getWechatMerchantConf(querySmartretailOrderDto.getSmerchantCode(), 2);
		if (null == conf) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
		QuerySmartretailOrderData querySmartretailOrderData = new QuerySmartretailOrderData.QuerySmartretailOrderDataBuilder(conf, querySmartretailOrderDto).build();
		CloseableHttpResponse response = wxPayApi.querySmartretailOrder(conf, querySmartretailOrderData);
		Integer statusCode = response.getStatusLine().getStatusCode();
		String err_code_des = "微信支付分查询订单异常";
		if (null != response) {
			HttpEntity entity2 = response.getEntity();
			String str = EntityUtils.toString(entity2, "utf-8");
			if (null != statusCode && statusCode == 200) {
				//更新订单信息
				EntityUtils.consume(entity2);
				QuerySmartretailOrderResult querySmartretailOrderResult = JSON.parseObject(str, QuerySmartretailOrderResult.class);
				responseVo.setData(querySmartretailOrderResult);
				return responseVo;
			} else {
				Map<String, String> map = JSON.parseObject(str, Map.class);
				err_code_des = String.valueOf(map.get("message"));
			}
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(err_code_des);
		return responseVo;
	}

	/**
	 * 微信支付分撤销订单
	 *
	 * @param cancelSmartretailOrdersDto
	 * @return
	 */
	@Override
	public ResponseVo<CancelSmartretailOrderResult> cancelOrders(CancelSmartretailOrdersDto cancelSmartretailOrdersDto) throws Exception {
		ResponseVo<CancelSmartretailOrderResult> responseVo = ResponseVo.getSuccessResponse();
		String out_order_no = orderPayService.selectOutTradeNoByOrderCode(cancelSmartretailOrdersDto.getOrderCode());
		logger.info("商户订单号：{}", out_order_no);
		String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + cancelSmartretailOrdersDto.getSmerchantCode());
		if (StringUtil.isBlank(json)) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
		MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
		if (null == conf) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
		CancelSmartretailOrderData cancelSmartretailOrderData = new CancelSmartretailOrderData.CancelSmartretailOrderDataBuilder(conf, cancelSmartretailOrdersDto).build();
		CloseableHttpResponse response = wxPayApi.cancelSmartretailOrder(conf, cancelSmartretailOrderData, out_order_no);
		Integer statusCode = response.getStatusLine().getStatusCode();
		String err_code_des = "微信支付分撤销订单异常";
		if (null != response) {
			HttpEntity entity2 = response.getEntity();
			String str = EntityUtils.toString(entity2, "utf-8");
			if (null != statusCode && statusCode == 200) {
				//更新订单信息
				EntityUtils.consume(entity2);
				CancelSmartretailOrderResult cancelSmartretailOrderResult = JSON.parseObject(str, CancelSmartretailOrderResult.class);
				responseVo.setData(cancelSmartretailOrderResult);
				return responseVo;
			} else {
				Map<String, String> map = JSON.parseObject(str, Map.class);
				err_code_des = String.valueOf(map.get("message"));
			}
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(err_code_des);
		return responseVo;
	}

	/**
	 * 微信支付分同步订单
	 *
	 * @param syncOrdersDto
	 * @return
	 */
	@Override
	public ResponseVo<SyncOrderResult> syncOrders(SyncOrdersDto syncOrdersDto) throws Exception {
		ResponseVo<SyncOrderResult> responseVo = ResponseVo.getSuccessResponse();
		//查询订单有没有微信支付分的申请
		String out_order_no = orderPayService.selectOutTradeNoByOrderCodeAsc(syncOrdersDto.getOrderCode());
		logger.info("商户订单号：{}", out_order_no);
		String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + syncOrdersDto.getSmerchantCode());
		if (StringUtil.isBlank(json)) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
		MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
		if (null == conf) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
		SyncOrderData syncOrderData = new SyncOrderData.SyncSmartretailOrderDataBuilder(conf, syncOrdersDto).build();
		CloseableHttpResponse response = wxPayApi.syncSmartretailOrder(conf, syncOrderData, out_order_no);
		Integer statusCode = response.getStatusLine().getStatusCode();
		String err_code_des = "微信支付分同步订单异常";
		if (null != response) {
			HttpEntity entity2 = response.getEntity();
			String str = EntityUtils.toString(entity2, "utf-8");
			if (null != statusCode && statusCode == 200) {
				//更新订单信息
				EntityUtils.consume(entity2);
				SyncOrderResult syncOrderResult = JSON.parseObject(str, SyncOrderResult.class);
				responseVo.setData(syncOrderResult);
				return responseVo;
			} else {
				Map<String, String> map = JSON.parseObject(str, Map.class);
				err_code_des = String.valueOf(map.get("message"));
			}
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(err_code_des);
		return responseVo;
	}

	/**
	 * 微信支付分撤销订单
	 *
	 * @param
	 * @return
	 */
	@Override
	public void closeOrder(String outout_order_no, String merchantCode) throws Exception {
		String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + merchantCode);
		MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
		wxPayApi.closeSmartretailOrder(conf, outout_order_no);
	}

	/**
	 * 微信支付分查询证书
	 *
	 * @param
	 * @return
	 */
	@Override
	public void queryCert() throws Exception {
		MerchantConf conf = merchantInfoService.getWechatMerchantConf("OP201801220037", 2);

		wxPayApi.queryCert(conf);
	}

	@Override
	public ResponseVo<OrderRecord> createVendstopPay(FreePaymentDto freePaymentDto) {
		try {
			//判断订单有效性
			ResponseVo<OrderRecord> resVo = validateOrderEffective(freePaymentDto);
			if (!resVo.isSuccess()) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(resVo.getMsg());
			}
			OrderRecord orderRecord = resVo.getData();

			//新增付款申请
			orderRecord.setIpayWay(freePaymentDto.getIpayWay());//设置支付方式
			PayApply payApply = this.insertPayApply(orderRecord, freePaymentDto.getSremark());

			//新增订单付款信息
			OrderPay orderPay = orderPayService.insertOrderPay(orderRecord,20,0);

			//更新订单状态(110=付款处理中)
			OrderRecord updateOrder = new OrderRecord();
			updateOrder.setId(orderRecord.getId());
			updateOrder.setSpayApplyId(payApply.getId());
			updateOrder.setIstatus(BizTypeDefinitionEnum.OrderStatus.IN_PAYMENT.getCode());
			this.updateBySelective(updateOrder);
			return resVo;
		} catch (Exception e) {
			logger.error("创建微信免密支付异常：{}", e);
			ResponseVo res = new ResponseVo();
			res.setSuccess(false);
			res.setErrorCode(-1000);
			res.setMsg("创建支付异常，请重新操作");
			return res;
		}
	}

	/***
	 * 微信支付分确认成功回调处理
	 * @param sip IP
	 * @param wechatPointsNotifyData  返回参数
	 * @param conf 商户配置信息
	 * @throws Exception
	 */
  /*  @Override
    public boolean dealwithWechatConfirmOrder(String sip, WechatPointsNotifyData wechatPointsNotifyData, MerchantConf conf) throws Exception {
        String sprivateKey = conf.getSwechatApiv3Key();
        if (StringUtils.isBlank(sprivateKey)) {
        }
        AesUtil aesUtil = new AesUtil(sprivateKey.getBytes());
        WechatPointsResourceData wechatPointsResourceData = JSON.toJavaObject(wechatPointsNotifyData.getResource(), WechatPointsResourceData.class);
        String decryptData = aesUtil.decryptToString(wechatPointsResourceData.getAssociated_data().getBytes(), wechatPointsResourceData.getNonce().getBytes(), wechatPointsResourceData.getCiphertext());
        if (StringUtil.isBlank(decryptData)) {
        }
        ConfirmOrderResouceDecrypt confirmOrderResouceDecrypt = JSON.parseObject(decryptData, ConfirmOrderResouceDecrypt.class);
        // 获取商户订单号
        String outTradePayNo = confirmOrderResouceDecrypt.getOut_order_no();
        logger.info("获取支付商户订单号=" + outTradePayNo);
        String outTradeNo = orderPayService.selectOrderCodeByPayNo(outTradePayNo);
        logger.info("获取商户订单号=" + outTradeNo);
        // 获取支付订单信息
        OrderRecord orderRecord = this.selectByOrderCode(outTradeNo);
        logger.info("获取的订单信息=" + orderRecord);
        if (orderRecord == null) {
            logger.info("订单编号：" + outTradeNo + "的订单不存在");
            return false;
        }
        if (orderRecord.getIstatus() == null ||
                orderRecord.getIstatus().intValue() == BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
            logger.info("支付宝手动支付订单状态异常或回调已处理，订单编号：{}", orderRecord.getSorderCode());
            return true;
        }
        // 获取支付付款申请信息
        PayApply payApply = payApplyService.selectByPrimaryKey(orderRecord.getSpayApplyId());
        if (payApply != null) {
            // 订单状态在待付款和付款申请才操作
            BigDecimal totalMount = new BigDecimal(confirmOrderResouceDecrypt.getTotal_amount());
            if (payApply.getFactualPayAmount().doubleValue() != totalMount.doubleValue()) {
                throw new ServiceException("实付金额不正确");
            }
            if ("USER_PAID".equals(confirmOrderResouceDecrypt.getState())) {
                // 成功处理
                paySuccess(outTradePayNo, orderRecord, payApply, confirmOrderResouceDecrypt.getOut_order_no(), confirmOrderResouceDecrypt.getPay_succ_time());
                logger.info("订单支付处理成功");
                return true;
            } else {
                // 失败处理
                //payFail(outTradePayNo, orderRecord, receivablesResouceDecrypt.getOut_order_no(), map.get("gmt_close"), "trade_closed", "交易超时关闭");
            }
        }
        return false;
    }*/

	/***
	 * 微信支付分支付成功回调处理
	 * @param sip IP
	 * @param receivablesNotifyData  返回参数
	 * @param conf 商户配置信息
	 * @throws Exception
	 */
	@Override
	public boolean dealwithWechatReceivables(String sip, WechatPointsNotifyData receivablesNotifyData, MerchantConf conf) throws Exception {
		String sprivateKey = conf.getSwechatApiv3Key();
		if (StringUtils.isBlank(sprivateKey)) {
		}
		AesUtil aesUtil = new AesUtil(sprivateKey.getBytes());
		WechatPointsResourceData receivablesResourceData = JSON.toJavaObject(receivablesNotifyData.getResource(), WechatPointsResourceData.class);
		String decryptData = aesUtil.decryptToString(receivablesResourceData.getAssociated_data().getBytes(), receivablesResourceData.getNonce().getBytes(), receivablesResourceData.getCiphertext());
		if (StringUtil.isBlank(decryptData)) {
			logger.info("支付成功回调数据为空:{}", JSON.toJSONString(receivablesNotifyData));
			return false;
		}
		ReceivablesResouceDecrypt receivablesResouceDecrypt = JSON.parseObject(decryptData, ReceivablesResouceDecrypt.class);
		// 获取商户订单号
		String outTradePayNo = receivablesResouceDecrypt.getOut_order_no();
		logger.info("获取支付商户订单号:{}", outTradePayNo);
		String outTradeNo = orderPayService.selectOrderCodeByPayNo(outTradePayNo);
		logger.info("获取商户订单编号:{}", outTradeNo);
		// 获取支付订单信息
		OrderRecord orderRecord = this.selectByOrderCode(outTradeNo);
		logger.info("获取的订单信息:{}", JSON.toJSONString(orderRecord));
		if (orderRecord == null) {
			logger.info("订单编号：" + outTradeNo + "的订单不存在");
			return false;
		}
		if (orderRecord.getIstatus() == null ||
				orderRecord.getIstatus().intValue() == BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
			logger.info("支付宝手动支付订单状态异常或回调已处理，订单编号：{}", orderRecord.getSorderCode());
			return true;
		}
		// 获取支付付款申请信息
		PayApply payApply = payApplyService.selectByPrimaryKey(orderRecord.getSpayApplyId());
		if (payApply != null) {
			// 订单状态在待付款和付款申请才操作
			BigDecimal totalMount = new BigDecimal(receivablesResouceDecrypt.getTotal_amount());
			if (payApply.getFactualPayAmount().multiply(new BigDecimal(100)).doubleValue() != totalMount.doubleValue()) {
				logger.info("支付成功回调实付金额不正确：{}", totalMount);
				throw new ServiceException("实付金额不正确");
			}
			if ("USER_PAID".equals(receivablesResouceDecrypt.getState())) {
				// 成功处理
				paySuccess(outTradePayNo, orderRecord, payApply, receivablesResouceDecrypt.getFinish_transaction_id(), receivablesResouceDecrypt.getPay_succ_time());
				logger.info("订单支付处理成功");
				return true;
			} else {
				// 失败处理
				//payFail(outTradePayNo, orderRecord, receivablesResouceDecrypt.getOut_order_no(), map.get("gmt_close"), "trade_closed", "交易超时关闭");
				payFail(outTradePayNo, orderRecord, receivablesResouceDecrypt.getFinish_transaction_id(), receivablesResouceDecrypt.getPay_succ_time(), "trade_closed", "交易超时关闭");

			}
		}
		return false;
	}
}