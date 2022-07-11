package com.cloud.cang.pay.om.service.impl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AgreementSignParams;
import com.alipay.api.domain.RoyaltyDetailInfos;
import com.alipay.api.domain.RoyaltyInfo;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.*;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.model.hy.WechatFreeData;
import com.cloud.cang.model.om.OrderPay;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sq.PayApply;
import com.cloud.cang.pay.*;
import com.cloud.cang.pay.common.utils.IdGenerator;
import com.cloud.cang.pay.hy.service.FreeDataService;
import com.cloud.cang.pay.hy.service.WechatFreeDataService;
import com.cloud.cang.pay.om.service.OrderPayService;
import com.cloud.cang.pay.om.vo.AgreementParams;
import com.cloud.cang.pay.om.vo.FreePaymentVo;
import com.cloud.cang.pay.om.vo.PaymentVo;
import com.cloud.cang.pay.sh.service.MerchantInfoService;
import com.cloud.cang.pay.sq.service.PayApplyService;
import com.cloud.cang.pay.wechat.notify.FreePayNotifyData;
import com.cloud.cang.pay.wechat.notify.PayNotifyData;
import com.cloud.cang.pay.wechat.notify.QueryFreePayNotifyData;
import com.cloud.cang.pay.wechat.protocol.BaseReqData;
import com.cloud.cang.pay.wechat.protocol.UnifiedFreePayReqData;
import com.cloud.cang.pay.wechat.service.WxPayApi;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.pay.om.dao.OrderRecordDao;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.pay.om.service.OrderRecordService;

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
	private static final Logger logger = LoggerFactory.getLogger(OrderRecordServiceImpl.class);
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
		logger.info("获取支付商户订单号="+ outTradePayNo);
		String outTradeNo = orderPayService.selectOrderCodeByPayNo(outTradePayNo);
		logger.info("获取商户订单号="+ outTradeNo);
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
			if("TRADE_SUCCESS".equals(map.get("trade_status"))){
				// 成功处理
				paySuccess(outTradePayNo, orderRecord, payApply, map.get("trade_no"), map.get("gmt_payment"));
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
	 * @param conf 商户配置信息 操作商户
	 * @param payNotifyData 微信回调参数集合
	 * @throws Exception
	 */
	@Override
	public boolean dealwithWechatPaymentNotify(MerchantConf conf, PayNotifyData payNotifyData) throws Exception {
		// 获取商户订单号
		String outTradePayNo = payNotifyData.getOut_trade_no();
		logger.info("获取支付商户订单号="+ outTradePayNo);
		String outTradeNo = orderPayService.selectOrderCodeByPayNo(outTradePayNo);
		logger.info("获取商户订单号="+ outTradeNo);
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
				paySuccess(outTradePayNo,orderRecord, payApply, payNotifyData.getTransaction_id(), payNotifyData.getTime_end());
				logger.info("订单支付处理成功");
				return true;
			} else {
				// 失败处理
				payFail(outTradePayNo,orderRecord, payNotifyData.getTransaction_id(), payNotifyData.getTime_end(), payNotifyData.getErr_code(), payNotifyData.getErr_code_des());
			}
		}
		return false;
	}
	/**
	 * 微信免密订单支付查询
	 * @param paymentDto 查询参数
	 */
	@Override
	public ResponseVo<QueryWechatFreePayResult> queryWechatFreePay(PaymentDto paymentDto) throws Exception {
		ResponseVo<QueryWechatFreePayResult> responseVo = ResponseVo.getSuccessResponse();
		//获取商户编号配置
		MerchantConf conf = merchantInfoService.getWechatMerchantConf(paymentDto.getSmerchantCode(),2);
		//获取商户订单支付编号
		String outTradeNo = orderPayService.selectOutTradeNoByOrderCode(paymentDto.getOutTradeNo());

		BaseReqData.UnifiedBaseReqDataBuilder builder = new BaseReqData.UnifiedBaseReqDataBuilder(conf, outTradeNo);
		if(StringUtil.isNotBlank(paymentDto.getTradeNo())) {
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
					errorMsg = "{" + errorCode + ":" + notifyData.getErr_code_des()+"}";
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
	 * @param orderCode 订单编号
	 */
	@Override
	public OrderRecord selectByOrderCode(String orderCode) {
		return orderRecordDao.selectByOrderCode(orderCode);
	}
	/**
	 * 创建支付宝免密支付
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
			//新增订单付款信息
			OrderPay orderPay = orderPayService.insertOrderPay(orderRecord,20);
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
				return responseVo;
			}
			freePaymentResult = this.freePayFail(orderPay.getScode(), response.getCode(),response.getSubCode(),response.getSubMsg(), orderRecord,freePaymentResult);
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
			WechatFreeData wechatFreeData = wechatFreeDataService.selectByMemberId(orderRecord.getSmemberId(), orderRecord.getSmerchantCode());
			if (null == wechatFreeData) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员免密数据不存在");
			}
			//新增付款申请
			orderRecord.setIpayWay(freePaymentDto.getIpayWay());//设置支付方式
			PayApply payApply = this.insertPayApply(orderRecord, freePaymentDto.getSremark());
			//新增订单付款信息
			OrderPay orderPay = orderPayService.insertOrderPay(orderRecord,10);
			UnifiedFreePayReqData reqData = new UnifiedFreePayReqData
					.UnifiedFreePayReqDataBuilder(conf, freePaymentDto.getSsubject(),
					orderPay.getScode(),
					orderRecord.getFactualPayAmount().multiply(new BigDecimal(100)).intValue(),
					sip, notifyUrl, tradeType, wechatFreeData.getScontractId())
					.setDetail(freePaymentDto.getSremark())
					.setOuterid(freePaymentDto.getSmemberCode())
					.setTimestamp((int) System.currentTimeMillis()/1000)
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
			//新增订单付款信息
			OrderPay orderPay = orderPayService.insertOrderPay(orderRecord,20);
			//组装支付并签约请求参数
			FreePaymentVo freePaymentVo = this.assemblyFreePaymentRequest(orderPay, orderRecord, freePaymentDto, freeData, 2);
			request.setBizContent(JSON.toJSONString(freePaymentVo));
			logger.debug("支付宝免密支付订单关闭请求参数:{}", request.getBizContent());
			AlipayTradePagePayResponse response = alipayClient.pageExecute(request,"get");
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
	 * @param orderRecord 订单数据
	 * @param remark 付款备注
	 * @throws Exception
	 */
	@Override
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
	 * @param paymentDto 查询参数
	 */
	@Override
	public ResponseVo<QueryPaymentResult> queryAlipayFreePay(PaymentDto paymentDto) throws Exception {
		ResponseVo<QueryPaymentResult> responseVo = ResponseVo.getSuccessResponse();
		AlipayClient alipayClient = merchantInfoService.getAlipayClient(paymentDto.getSmerchantCode());
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
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
	 * @param conf 商户配置信息 操作商户
	 * @param map 回调参数
	 */
	@Override
	public boolean dealwithAlipayFreePaymentNotify(MerchantConf conf, Map<String, String> map) throws Exception {
		// 获取商户订单号
		String outTradePayNo = map.get("out_trade_no");
		logger.info("获取支付商户订单号="+ outTradePayNo);
		String outTradeNo = orderPayService.selectOrderCodeByPayNo(outTradePayNo);
		logger.info("获取商户订单号="+ outTradeNo);
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
			if("TRADE_SUCCESS".equals(map.get("trade_status"))) {
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
				return true;
			}
		}
		return false;
	}
	/**
	 * 处理微信免密支付 回调
	 * @param conf 商户配置信息 操作商户
	 * @param freePayNotifyData 回调参数
	 */
	@Override
	public boolean dealwithWechatFreePaymentNotify(MerchantConf conf, FreePayNotifyData freePayNotifyData) throws Exception {
		// 获取商户订单号
		String outTradePayNo = freePayNotifyData.getOut_trade_no();
		logger.info("获取支付商户订单号="+ outTradePayNo);
		String outTradeNo = orderPayService.selectOrderCodeByPayNo(outTradePayNo);
		logger.info("获取商户订单号="+ outTradeNo);
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
				if("SUCCESS".equals(freePayNotifyData.getTrade_state())) {
					this.freePaySuccess(outTradePayNo,orderRecord, freePaymentResult);
					return true;
				}
			} else {
				// 失败处理
				//payFail(orderRecord, freePayNotifyData.getTransaction_id(), freePayNotifyData.getTime_end(), freePayNotifyData.getErr_code(), freePayNotifyData.getErr_code_des());
				this.freePayFail(outTradePayNo,freePayNotifyData.getResult_code(), freePayNotifyData.getErr_code(), freePayNotifyData.getErr_code_des(), orderRecord, freePaymentResult);
                return true;
			}
		}
		return false;
	}

	/**
	 * 修改订单数据 成功处理
	 *
	 * @param outTradePayNo
	 * @param orderRecord 原始订单数据
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
		updateOrder.setIchargebackWay(10);//商户代扣
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
		map.put("scode",outTradePayNo);
		map.put("sorderCode",orderRecord.getSorderCode());
		map.put("transactionId",freePaymentResult.getTradeNo());
		map.put("istatus",20);
		map.put("tcompleteTime",updateOrder.getTpayCompleteTime());
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
				updateOrder.setSpayFailureReason(orderRecord.getSpayFailureReason() + ",{"+subCode+":"+subMsg+"}");
			} else {
				updateOrder.setSpayFailureReason("{"+subCode+":"+subMsg+"}");
			}
			this.updateBySelective(updateOrder);
			//更新付款申请数据
			updateApply.setIstatus(30);
			updateApply.setTfinishDatetime(new Date());
			payApplyService.updateBySelective(updateApply);
		}


		//更新订单付款数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scode",outTradePayNo);
		map.put("serrorCode",subCode);
		map.put("serrorDesc",subMsg);
		map.put("sorderCode",orderRecord.getSorderCode());
		map.put("transactionId", freePaymentResult.getTradeNo());
		map.put("istatus", 30);
		map.put("tcompleteTime", updateApply.getTfinishDatetime());
		orderPayService.updateDataByMap(map);

		return freePaymentResult;
	}


	/**
	 * 支付成功处理
	 * @param outTradePayNo
	 * @param orderRecord 订单信息
	 * @param payApply 支付信息
	 * @param paySerialNumber 第三方支付流水号
	 * @param sfinishTime 支付完成时间
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
		map.put("scode",outTradePayNo);
		map.put("sorderCode",orderRecord.getSorderCode());
		map.put("transactionId",paySerialNumber);
		map.put("istatus",20);
		map.put("tcompleteTime",updateOrder.getTpayCompleteTime());
		orderPayService.updateDataByMap(map);

	}


	/**
	 * 付款失败处理
	 * @param outTradePayNo
	 * @param orderRecord 订单信息
	 * @param paySerialNumber 第三方支付流水号
	 * @param sfinishTime 支付完成时间
	 * @param errorCode 错误代码
	 * @param errorDesc 错误描叙
	 */
	private void payFail(String outTradePayNo, OrderRecord orderRecord, String paySerialNumber, String sfinishTime, String errorCode, String errorDesc) {
		//更新订单信息 //更新付款订单
		OrderRecord updateOrder = new OrderRecord();
		updateOrder.setId(orderRecord.getId());
		updateOrder.setIstatus(20);//付款失败
		if (StringUtil.isNotBlank(orderRecord.getSpayFailureReason())) {
			updateOrder.setSpayFailureReason(orderRecord.getSpayFailureReason() + ",{"+errorCode+":"+errorDesc+"}");
		} else {
			updateOrder.setSpayFailureReason("{"+errorCode+":"+errorDesc+"}");
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
		map.put("scode",outTradePayNo);
		map.put("serrorCode",errorCode);
		map.put("serrorDesc",errorDesc);
		map.put("sorderCode",orderRecord.getSorderCode());
		map.put("transactionId",paySerialNumber);
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
		} else if (type == 2) {
			freePaymentVo.setProduct_code(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "pay_sign_product_code").getSvalue());
			AgreementSignParams agreementSignParams = new AgreementSignParams();
			agreementSignParams.setPersonalProductCode(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "personal_product_code").getSvalue());
			agreementSignParams.setSignScene(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "personal_product_code").getSvalue());
			agreementSignParams.setExternalAgreementNo(freeData.getSexternalAgreementNo());
			freePaymentVo.setAgreement_sign_params(agreementSignParams);
		}

		//分账最小金额
		BigDecimal minRoyaltyAmount = new BigDecimal(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "royalty_min_amount").getSvalue());
		//支付宝实际到账金额
		BigDecimal alipayActualAmount = new BigDecimal(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "royalty_min_amount").getSvalue()).multiply(freePaymentVo.getTotal_amount());
		if (!freePaymentDto.getSmerchantCode().equals(orderRecord.getSmemberCode()) && alipayActualAmount.doubleValue() >= minRoyaltyAmount.doubleValue()) {//不是收款商户
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
			conf = merchantInfoService.getAlipayMerchantConf(orderRecord.getSmerchantCode());
			royaltyDetailInfos.setTransIn(conf.getSpid());
			royaltyDetailInfos.setAmount(alipayActualAmount.multiply(new BigDecimal("100")).longValue());
			royaltyDetailInfos.setDesc("分账到商户");
			royaltyDetailInfos.setAmountPercentage("100");
			List<RoyaltyDetailInfos> tempList = new ArrayList<RoyaltyDetailInfos>();
			royaltyInfo.setRoyaltyDetailInfos(tempList);
			freePaymentVo.setRoyalty_info(royaltyInfo);
		}


		return freePaymentVo;
	}
	/**
	 * 处理免密支付并签约回调
	 * @param conf 商户配置信息
	 * @param map 支付宝返回参数
	 * @return
	 */
	@Override
	public boolean dealwithAlipayFreePaymentAndSignNotify(MerchantConf conf, Map<String, String> map) throws Exception {

		return false;
	}
	/**
	 * 更新订单状态
	 * @param pmap 参数集合
	 * @return
	 */
	@Override
	public Integer updateStatusByOrderId(Map<String, Object> pmap) {
		return orderRecordDao.updateStatusByOrderId(pmap);
	}




}