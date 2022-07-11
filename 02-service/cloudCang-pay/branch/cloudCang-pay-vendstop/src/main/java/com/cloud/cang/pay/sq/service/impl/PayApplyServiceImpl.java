package com.cloud.cang.pay.sq.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.*;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.om.OrderPay;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sq.RefundApply;
import com.cloud.cang.pay.*;
import com.cloud.cang.pay.alipay.service.AliPayApi;
import com.cloud.cang.pay.alipay.vo.AliBaseData;
import com.cloud.cang.pay.alipay.vo.AliReqData;
import com.cloud.cang.pay.common.utils.HttpUtils;
import com.cloud.cang.pay.om.service.OrderPayService;
import com.cloud.cang.pay.om.service.OrderRecordService;
import com.cloud.cang.pay.sh.service.MerchantInfoService;
import com.cloud.cang.pay.sq.service.RefundApplyService;
import com.cloud.cang.pay.wechat.common.MD5;
import com.cloud.cang.pay.wechat.notify.QueryFreePayNotifyData;
import com.cloud.cang.pay.wechat.protocol.BaseReqData;
import com.cloud.cang.pay.wechat.protocol.DownloadbillReqData;
import com.cloud.cang.pay.wechat.protocol.QuerySmartretailOrderData;
import com.cloud.cang.pay.wechat.protocol.UnifiedOrderReqData;
import com.cloud.cang.pay.wechat.service.WxPayApi;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.pay.sq.dao.PayApplyDao;
import com.cloud.cang.model.sq.PayApply;
import com.cloud.cang.pay.sq.service.PayApplyService;

import javax.servlet.http.HttpServletRequest;

@Service
public class PayApplyServiceImpl extends GenericServiceImpl<PayApply, String> implements
        PayApplyService {

    @Autowired
    private PayApplyDao payApplyDao;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private RefundApplyService refundApplyService;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private WxPayApi wxPayApi;
    private static final Logger logger = LoggerFactory.getLogger(PayApplyServiceImpl.class);

    @Override
    public GenericDao<PayApply, String> getDao() {
        return payApplyDao;
    }

    /***
     * 微信手动支付
     * @param payApplyDto 支付参数
     */
    @Override
    public ResponseVo<PayBackDto> generateWechatPayApply(PayApplyDto payApplyDto) throws Exception {
        ResponseVo<PayBackDto> responseVo = ResponseVo.getSuccessResponse();

        PayApply payApply = null;
        BigDecimal actualAmount = new BigDecimal("0");
        OrderRecord orderRecord = null;
        //循环订单信息
        String[] orderIds = payApplyDto.getSorderId().split("#");
        if (orderIds != null && orderIds.length > 0) {
            //for (int i = 0; i < orderIds.length; i++) {
            orderRecord = orderRecordService.selectByPrimaryKey(orderIds[0]);
            if (orderRecord == null) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单信息不能为空");
            } else if (!orderRecord.getSmemberId().equals(payApplyDto.getSmemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单异常");
            }
            //新增付款申请
            payApply = orderRecordService.insertPayApply(orderRecord, payApplyDto.getSremark());
            logger.info("付款申请插入成功：{}", payApply);
            //计算订单实付总额
            actualAmount = actualAmount.add(orderRecord.getFactualPayAmount());//计算订单实付总额
            //获取商户编号配置
            MerchantConf conf = merchantInfoService.getWechatMerchantConf(payApplyDto.getSmerchantCode(), 2);
            String notifyUrl = GrpParaUtil.getDetailForName(CoreConstant.WECHAT_FREE_CONFIG, "manually_pay_callback_url").getSvalue();
            //生成付款信息
            UnifiedOrderReqData reqData = null;
            //新增订单付款信息
            OrderPay orderPay = orderPayService.insertOrderPay(orderRecord, 10, 0);
            if (payApplyDto.getIpayWay().intValue() == BizTypeDefinitionEnum.PayWay.PUBLIC_NUMBER.getCode()) {
                reqData = new UnifiedOrderReqData.UnifiedOrderReqDataBuilder(conf, payApplyDto.getSsubject(), orderPay.getScode(), actualAmount.multiply(new BigDecimal(100)).intValue(), payApplyDto.getSip(), notifyUrl, WechatConfigure.JSAPI_TRADE_TYPE)
                        .setOpenid(payApplyDto.getSmemberOpenId())
                        .setDetail(payApplyDto.getBody())
                        .setAttach("叁拾柒号仓智能货柜").build();
            } else if (payApplyDto.getIpayWay().intValue() == BizTypeDefinitionEnum.PayWay.H5.getCode()) {
                String wechat_pay_scene_info = GrpParaUtil.getDetailForName(CoreConstant.WECHAT_FREE_CONFIG, "wechat_pay_scene_info").getSvalue();
                reqData = new UnifiedOrderReqData.UnifiedOrderReqDataBuilder(conf, payApplyDto.getSsubject(), orderPay.getScode(), actualAmount.multiply(new BigDecimal(100)).intValue(), payApplyDto.getSip(), notifyUrl, WechatConfigure.MWEB_TRADE_TYPE)
                        .setScene_info(wechat_pay_scene_info)
                        .setDetail(payApplyDto.getBody())
                        .setAttach("叁拾柒号仓智能货柜").build();
            }
            Map<String, Object> resMap = wxPayApi.unifiedOrder(conf, reqData);
            logger.debug("预付款创建成功：{}", resMap);
            if (resMap != null && String.valueOf(resMap.get("return_code")).equals("SUCCESS")) {
                if (String.valueOf(resMap.get("result_code")).equals("SUCCESS")) {
                    //更新订单的付款申请Id
                    OrderRecord updateOrder = new OrderRecord();
                    updateOrder.setId(orderRecord.getId());
                    updateOrder.setSpayApplyId(payApply.getId());
                    updateOrder.setIchargebackWay(20);//手动支付
                    updateOrder.setIpayWay(payApplyDto.getIpayWay());
                    orderRecordService.updateBySelective(updateOrder);

                    PayBackDto payBack = new PayBackDto();
                    payBack.setIpayType(payApplyDto.getIpayType());
                    payBack.setIpayWay(payApplyDto.getIpayWay());

                    //判断订单方式 生成支付链接
                    if (payApplyDto.getIpayWay().intValue() == BizTypeDefinitionEnum.PayWay.PUBLIC_NUMBER.getCode()) {
                        //组装付款信息数据
                        JsAPIConfig jsApi = createPayConfig((String) resMap.get("prepay_id"), conf);
                        payBack.setJsApi(jsApi);
                        payBack.setOrderId(orderRecord.getId());
                        logger.debug("付款参数组装实体：{}", jsApi);
                    } else if (payApplyDto.getIpayWay().intValue() == BizTypeDefinitionEnum.PayWay.H5.getCode()) {
                        //返回跳转链接
                        payBack.setMwebUrl((String) resMap.get("mweb_url"));
                    }
                    responseVo.setData(payBack);
                    return responseVo;
                } else {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(String.valueOf(resMap.get("err_code_des")));
                }
            } else {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(String.valueOf(resMap.get("return_msg")));
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("付款申请失败，订单异常");
    }

    /**
     * 支付宝手动支付
     *
     * @param payApplyDto 支付参数
     */
    @Override
    public ResponseVo<PayBackDto> generateAliPayApply(PayApplyDto payApplyDto) throws Exception {
        ResponseVo<PayBackDto> responseVo = ResponseVo.getSuccessResponse();

        PayApply payApply = null;
        BigDecimal actualAmount = new BigDecimal("0");
        OrderRecord orderRecord = null;
        //循环订单信息
        String[] orderIds = payApplyDto.getSorderId().split("#");
        if (orderIds != null && orderIds.length > 0) {
            //for (int i = 0; i < orderIds.length; i++) {
            orderRecord = orderRecordService.selectByPrimaryKey(orderIds[0]);
            if (orderRecord == null) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单信息不能为空");
            } else if (!orderRecord.getSmemberId().equals(payApplyDto.getSmemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单异常");
            }
            //新增付款申请
            payApply = orderRecordService.insertPayApply(orderRecord, payApplyDto.getSremark());
            logger.info("付款申请插入成功：{}", payApply);
            actualAmount = actualAmount.add(orderRecord.getFactualPayAmount());//计算订单实付总额
            //新增订单付款信息
            OrderPay orderPay = orderPayService.insertOrderPay(orderRecord, 20, 0);
            //生成付款参数并组装参数
            AliReqData reqData = new AliReqData(orderPay.getScode(), payApplyDto.getSsubject(), actualAmount, AlipayConfigure.productCode);
            //获取商户编号配置
            MerchantConf conf = merchantInfoService.getAlipayMerchantConf(payApplyDto.getSmerchantCode(), 2);
            String form = AliPayApi.unifiedOrder(reqData, conf, payApplyDto.getSreturnUrl());
            if (StringUtil.isNotBlank(form)) {
                logger.debug("预付款创建成功：{}", form);
                //更新订单的付款申请Id
                OrderRecord updateOrder = new OrderRecord();
                updateOrder.setId(orderRecord.getId());
                updateOrder.setSpayApplyId(payApply.getId());
                updateOrder.setIpayWay(payApplyDto.getIpayWay());
                updateOrder.setIchargebackWay(20);//手动支付
                orderRecordService.updateBySelective(updateOrder);
                //接受返回参数 返回给前端
                PayBackDto payBack = new PayBackDto();
                payBack.setOrderId(orderRecord.getId());
                payBack.setForm(form);
                payBack.setIpayType(payApplyDto.getIpayType());
                responseVo.setData(payBack);
                return responseVo;
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("付款申请失败，订单异常");
    }

    /**
     * 支付宝补处理操作
     *
     * @param repairProcessDto
     * @throws Exception
     */
    @Override
    public ResponseVo<HashMap<String, Object>> payAliPayRepairProcess(RepairProcessDto repairProcessDto) throws Exception {
        ResponseVo<HashMap<String, Object>> responseVo = ResponseVo.getSuccessResponse();
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("未查到支付订单信息");

        OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(repairProcessDto.getSorderId());
        RefundApply refundApply = null;
        //获取商户订单支付编号
        String payCode = orderPayService.selectOutTradeNoByOrderCode(repairProcessDto.getSordercode());
        if (orderRecord == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单信息不能为空");
        } else if (!orderRecord.getSmemberId().equals(repairProcessDto.getSmemberId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单异常");
        } else {
            if (repairProcessDto.getItype().intValue() == 10) {
                if (orderRecord.getIstatus() == null || (orderRecord.getIstatus().intValue() != 100 && orderRecord.getIstatus().intValue() != 110)) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
                }
            } else if (repairProcessDto.getItype().intValue() == 20) {
                if (StringUtil.isBlank(repairProcessDto.getSrefundId())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("退款流水号不能为空");
                }
                refundApply = refundApplyService.selectByPrimaryKey(repairProcessDto.getSrefundId());
            }
        }
        //商户代扣支付补处理
        if (repairProcessDto.getItype().intValue() == 10 && orderRecord.getIchargebackWay().intValue() == 10) {
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setOutTradeNo(orderRecord.getSorderCode());
            paymentDto.setSmerchantCode(repairProcessDto.getSmerchantCode());
            ResponseVo<QueryPaymentResult> queryPaymentResult = orderRecordService.queryAlipayFreePay(paymentDto);
            if (queryPaymentResult.isSuccess()) {

                orderRecord.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());//设置支付方式为代扣
                FreePaymentResult freePaymentResult = new FreePaymentResult();
                freePaymentResult.setBuyerPayAmount(new BigDecimal(queryPaymentResult.getData().getBuyerPayAmount()));
                freePaymentResult.setGmtPayment(queryPaymentResult.getData().getSendPayDate());
                freePaymentResult.setInvoiceAmount(new BigDecimal(queryPaymentResult.getData().getInvoiceAmount()));
                freePaymentResult.setPointAmount(new BigDecimal(queryPaymentResult.getData().getPointAmount()));
                freePaymentResult.setReceiptAmount(new BigDecimal(queryPaymentResult.getData().getReceiptAmount()));
                freePaymentResult.setTotalAmount(new BigDecimal(queryPaymentResult.getData().getTotalAmount()));
                freePaymentResult.setTradeNo(queryPaymentResult.getData().getTradeNo());
                orderRecordService.freePaySuccess(payCode, orderRecord, freePaymentResult);

                responseVo.setSuccess(true);
                responseVo.setMsg("补处理成功");
            } else {
                responseVo.setErrorCode(queryPaymentResult.getErrorCode());
                responseVo.setMsg(queryPaymentResult.getMsg());
                //更新订单支付失败
                payRepairProcessFail(orderRecord, queryPaymentResult.getData().getTradeNo(),
                        queryPaymentResult.getData().getSendPayDate(), "{" + queryPaymentResult.getErrorCode() + ":" + queryPaymentResult.getMsg() + "}");
            }
            return responseVo;
        }
        //其他支付补处理
        AliBaseData baseData = new AliBaseData();
        baseData.setOut_trade_no(payCode);
        baseData.setTrade_no(repairProcessDto.getSpaySerialNumber());
        //获取商户编号配置
        MerchantConf conf = merchantInfoService.getAlipayMerchantConf(repairProcessDto.getSmerchantCode(), 2);
        if (repairProcessDto.getItype().intValue() == 10) {
            //付款补处理
            AlipayTradeQueryResponse response = AliPayApi.queryOrder(baseData, conf);
            if (response != null) {
                if (response.isSuccess()) {
                    //补处理成功
                    payRepairProcessSuccess(orderRecord, response.getTradeNo(), response.getSendPayDate());
                    responseVo.setSuccess(true);
                    responseVo.setMsg("补处理成功");
                    return responseVo;
                } else {
                    responseVo.setErrorCode(Integer.parseInt(response.getCode()));
                    responseVo.setMsg(response.getSubMsg());
                    //更新订单支付失败
                    payRepairProcessFail(orderRecord, response.getTradeNo(), response.getSendPayDate(), "{" + response.getSubCode() + ":" + response.getSubMsg() + "}");
                }
            }
        } else if (repairProcessDto.getItype().intValue() == 20) {
            //退款补处理
            baseData.setOut_request_no(refundApply.getSrefundNo());
            AlipayTradeFastpayRefundQueryResponse response = AliPayApi.refuncQueryOrder(baseData, conf);
            if (response != null) {
                if (response.isSuccess()) {
                    if (StringUtil.isNotBlank(response.getTradeNo())) {
                        if (refundApply.getIstatus() != null && refundApply.getIstatus().intValue() != 20) {
                            //退款成功
                            RefundApply refundApplyU = new RefundApply();
                            refundApplyU.setId(refundApply.getId());
                            refundApplyU.setSpaySerialNumber(response.getTradeNo());
                            refundApplyU.setIstatus(20);
                            refundApplyU.setTfinishDatetime(new Date());
                            refundApplyService.updateBySelective(refundApplyU);
                        }
                        responseVo.setSuccess(true);
                        responseVo.setMsg("补处理成功");
                        return responseVo;
                    }
                    responseVo.setMsg("未查到实际退款数据");
                } else {
                    responseVo.setErrorCode(Integer.parseInt(response.getCode()));
                    responseVo.setMsg(response.getSubMsg());
                }
            }
        }
        return responseVo;
    }

    /**
     * 微信补处理操作
     *
     * @param repairProcessDto
     * @throws Exception
     */
    @Override
    public ResponseVo<HashMap<String, Object>> payWechatRepairProcess(RepairProcessDto repairProcessDto) throws Exception {
        ResponseVo<HashMap<String, Object>> responseVo = ResponseVo.getSuccessResponse();
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("未查到支付订单信息");

        OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(repairProcessDto.getSorderId());
        RefundApply refundApply = null;

        if (orderRecord == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单信息不能为空");
        } else if (!orderRecord.getSmemberId().equals(repairProcessDto.getSmemberId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单异常");
        } else {
            if (repairProcessDto.getItype().intValue() == 10) {
                if (orderRecord.getIstatus() == null ||
                        (orderRecord.getIchargebackWay() != 40 && (orderRecord.getIstatus().intValue() != 100 && orderRecord.getIstatus().intValue() != 110))
                        || (orderRecord.getIchargebackWay() == 40 && (BizTypeDefinitionEnum.OrderStatus.IN_PAYMENT.getCode() != orderRecord.getIstatus()
                        && BizTypeDefinitionEnum.OrderStatus.PAYMENT_FAIL.getCode() != orderRecord.getIstatus()))) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
                }
            } else if (repairProcessDto.getItype().intValue() == 20) {
                if (StringUtil.isBlank(repairProcessDto.getSrefundId())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("退款流水号不能为空");
                }
                refundApply = refundApplyService.selectByPrimaryKey(repairProcessDto.getSrefundId());
            }
        }
        //获取商户编号配置
        MerchantConf conf = merchantInfoService.getWechatMerchantConf(repairProcessDto.getSmerchantCode(), 2);
        //获取商户订单支付编号
        String payCode = orderPayService.selectOutTradeNoByOrderCode(repairProcessDto.getSordercode());
        //商户代扣支付补处理
        if (repairProcessDto.getItype().intValue() == 10 && (orderRecord.getIchargebackWay().intValue() == 10 || orderRecord.getIchargebackWay().intValue() == 30 || orderRecord.getIchargebackWay() == 40)) {
            String errorMsg = "未查询到微信免密支付记录";
            String errorCode = "-1000";
            if (conf.getIwechatWithholdType() == 10) {
                BaseReqData.UnifiedBaseReqDataBuilder builder = new BaseReqData.UnifiedBaseReqDataBuilder(conf, payCode);
                if (StringUtil.isNotBlank(repairProcessDto.getSpaySerialNumber())) {
                    builder.setTransaction_id(repairProcessDto.getSpaySerialNumber());
                }
                BaseReqData reqData = builder.build();
                QueryFreePayNotifyData notifyData = wxPayApi.unifiedQueryFreePay(conf, reqData);
             /*   String errorMsg = "未查询到微信免密支付记录";
                String errorCode = "-1000";*/
                if (notifyData != null) {
                    if (notifyData.getReturn_code().equals("SUCCESS")) {
                        if (StringUtil.isNotBlank(notifyData.getResult_code())
                                && notifyData.getResult_code().equals("SUCCESS")) {

                            orderRecord.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());//设置支付方式为代扣
                            FreePaymentResult freePaymentResult = new FreePaymentResult();
                            freePaymentResult.setGmtPayment(DateUtils.parseDateByFormat(notifyData.getTime_end(), "yyyy-MM-dd HH:mm:ss"));

                            freePaymentResult.setPointAmount(BigDecimal.ZERO);
                            if (StringUtil.isNotBlank(notifyData.getCoupon_fee())) {
                                freePaymentResult.setOtherPayAmount(new BigDecimal(notifyData.getCoupon_fee()).divide(new BigDecimal("100")));
                            } else {
                                freePaymentResult.setOtherPayAmount(BigDecimal.ZERO);
                            }
                            if (StringUtil.isNotBlank(notifyData.getCash_fee())) {
                                freePaymentResult.setReceiptAmount(new BigDecimal(notifyData.getCash_fee()).divide(new BigDecimal("100")));
                            } else {
                                freePaymentResult.setReceiptAmount(BigDecimal.ZERO);
                            }
                            if (StringUtil.isNotBlank(notifyData.getCash_fee())) {
                                freePaymentResult.setTotalAmount(new BigDecimal(notifyData.getTotal_fee()).divide(new BigDecimal("100")));
                            } else {
                                freePaymentResult.setTotalAmount(BigDecimal.ZERO);
                            }
                            freePaymentResult.setInvoiceAmount(freePaymentResult.getTotalAmount());
                            freePaymentResult.setBuyerPayAmount(freePaymentResult.getTotalAmount());
                            freePaymentResult.setTradeNo(notifyData.getTransaction_id());
                            orderRecordService.freePaySuccess(payCode, orderRecord, freePaymentResult);

                            responseVo.setSuccess(true);
                            responseVo.setMsg("补处理成功");
                            return responseVo;
                        } else {
                            String payNumber = notifyData.getTransaction_id();
                            Date tpayCompleteTime = StringUtil.isNotBlank(notifyData.getTime_end()) ? DateUtils.parseDateByFormat(notifyData.getTime_end(), "yyyyMMddHHmmss") : DateUtils.getCurrentDateTime();

                            errorCode = notifyData.getErr_code();
                            errorMsg = notifyData.getErr_code_des();

                            payRepairProcessFail(orderRecord, payNumber, tpayCompleteTime, "{" + errorCode + ":" + errorMsg + "}");
                        }
                    } else {
                        errorCode = notifyData.getReturn_code();
                        errorMsg = notifyData.getReturn_msg();
                    }
                }
            } else if (conf.getIwechatWithholdType() == 20) {
                QuerySmartretailOrderDto querySmartretailOrderDto = new QuerySmartretailOrderDto();
                querySmartretailOrderDto.setSmerchantCode(repairProcessDto.getSmerchantCode());
                String code = orderPayService.selectOutTradeNoByOrderCode(repairProcessDto.getSordercode());
                querySmartretailOrderDto.setOut_order_no(code);
                QuerySmartretailOrderData querySmartretailOrderData = new QuerySmartretailOrderData.QuerySmartretailOrderDataBuilder(conf, querySmartretailOrderDto).build();
                CloseableHttpResponse response = wxPayApi.querySmartretailOrder(conf, querySmartretailOrderData);

                Integer statusCode = response.getStatusLine().getStatusCode();
                if (null != response) {
                    HttpEntity entity2 = response.getEntity();
                    String str = EntityUtils.toString(entity2, "utf-8");
                    if (null != statusCode && statusCode == 200) {
                        //更新订单信息
                        EntityUtils.consume(entity2);
                        QuerySmartretailOrderResult querySmartretailOrderResult = JSON.parseObject(str, QuerySmartretailOrderResult.class);
                        logger.info("微信支付分补处理查询数据：{}", JSON.toJSONString(querySmartretailOrderResult));
                        if (querySmartretailOrderResult.getState().equals("USER_PAID")) {
                            orderRecord.setIpayWay(BizTypeDefinitionEnum.PayWay.WECHAT_PAY_POINT_WITHHOLDING.getCode());//设置支付方式为代扣
                            FreePaymentResult freePaymentResult = new FreePaymentResult();
                            freePaymentResult.setGmtPayment(DateUtils.parseDateByFormat(querySmartretailOrderResult.getPay_succ_time(), "yyyyMMddHHmmss"));
                            freePaymentResult.setPointAmount(BigDecimal.ZERO);
                          /*  if (StringUtil.isNotBlank(notifyData.getCoupon_fee())) {
                                freePaymentResult.setOtherPayAmount(new BigDecimal(notifyData.getCoupon_fee()).divide(new BigDecimal("100")));
                            } else {*/
                            freePaymentResult.setOtherPayAmount(BigDecimal.ZERO);
                           /* }*/
                            if (null != querySmartretailOrderResult.getTotal_amount()) {
                                freePaymentResult.setReceiptAmount(new BigDecimal(querySmartretailOrderResult.getTotal_amount()).divide(new BigDecimal("100")));
                            } else {
                                freePaymentResult.setReceiptAmount(BigDecimal.ZERO);
                            }
                            if (null != querySmartretailOrderResult.getTotal_amount()) {
                                freePaymentResult.setTotalAmount(new BigDecimal(querySmartretailOrderResult.getTotal_amount()).divide(new BigDecimal("100")));
                            } else {
                                freePaymentResult.setTotalAmount(BigDecimal.ZERO);
                            }
                            freePaymentResult.setInvoiceAmount(freePaymentResult.getTotalAmount());
                            freePaymentResult.setBuyerPayAmount(freePaymentResult.getTotalAmount());
                            freePaymentResult.setTradeNo(querySmartretailOrderResult.getFinish_transaction_id());
                            orderRecordService.freePaySuccess(payCode, orderRecord, freePaymentResult);

                            responseVo.setSuccess(true);
                            responseVo.setMsg("补处理成功");
                            return responseVo;
                        } else {
                            errorMsg = String.valueOf("微信补处理订单未支付成功");
                        }
                    } else {
                        Map<String, String> map = JSON.parseObject(str, Map.class);
                        errorMsg = String.valueOf(map.get("message"));
                        errorCode = String.valueOf(map.get("code"));
                    }
                }
            }
            logger.error("微信免密支付订单补处理异常：{}", errorCode + "------------" + errorMsg);
            responseVo.setSuccess(false);
            responseVo.setMsg(errorMsg);
            responseVo.setErrorCode(-1000);
            return responseVo;
        }

        BaseReqData reqData = null;
        Map<String, Object> resMap = null;
        BaseReqData.UnifiedBaseReqDataBuilder builder = new BaseReqData.UnifiedBaseReqDataBuilder(conf, payCode);
        if (StringUtil.isNotBlank(repairProcessDto.getSpaySerialNumber())) {
            builder.setTransaction_id(repairProcessDto.getSpaySerialNumber());
        }
        if (repairProcessDto.getItype().intValue() == 10) {
            reqData = builder.build();
            resMap = wxPayApi.unifiedRepairProcess(conf, reqData, WechatConfigure.PAY_QUERY_API);
            logger.debug("支付补处理：{}", resMap);
        } else if (repairProcessDto.getItype().intValue() == 20) {
            if (StringUtil.isNotBlank(repairProcessDto.getSrefundSerialNumber())) {
                builder.setRefund_id(repairProcessDto.getSrefundSerialNumber());
            }
            if (StringUtil.isNotBlank(repairProcessDto.getSrefundNo())) {
                builder.setOut_refund_no(repairProcessDto.getSrefundNo());
            }
            reqData = builder.build();
            resMap = wxPayApi.unifiedRepairProcess(conf, reqData, WechatConfigure.REFUND_QUERY_API);
            logger.debug("补处理：{}", resMap);
        }

        if (resMap != null && resMap.get("return_code").equals("SUCCESS")) {
            if (repairProcessDto.getItype().intValue() == 10) {
                if (resMap.get("result_code").equals("SUCCESS")) {
                    String payNumber = (String) resMap.get("transaction_id");
                    Date tpayCompleteTime = StringUtil.isNotBlank((String) resMap.get("time_end")) ? DateUtils.parseDateByFormat((String) resMap.get("time_end"), "yyyyMMddHHmmss") : DateUtils.getCurrentDateTime();

                    //补处理成功
                    payRepairProcessSuccess(orderRecord, payNumber, tpayCompleteTime);
                    responseVo.setSuccess(true);
                    responseVo.setMsg("补处理成功");
                    return responseVo;
                } else {
                    String payNumber = (String) resMap.get("transaction_id");
                    Date tpayCompleteTime = StringUtil.isNotBlank((String) resMap.get("time_end")) ? DateUtils.parseDateByFormat((String) resMap.get("time_end"), "yyyyMMddHHmmss") : DateUtils.getCurrentDateTime();

                    String err_code = (String) resMap.get("err_code ");
                    String err_code_des = (String) resMap.get("err_code_des");

                    responseVo.setMsg(err_code_des);
                    payRepairProcessFail(orderRecord, payNumber, tpayCompleteTime, "{" + err_code + ":" + err_code_des + "}");

                }
            } else if (repairProcessDto.getItype().intValue() == 20) {
                if (resMap.get("result_code").equals("SUCCESS")) {
                    String payNumber = (String) resMap.get("transaction_id");
                    if (StringUtil.isNotBlank(payNumber)) {
                        if (refundApply.getIstatus() != null && refundApply.getIstatus().intValue() != 20) {
                            //退款成功
                            RefundApply refundApplyU = new RefundApply();
                            refundApplyU.setId(refundApply.getId());
                            refundApplyU.setSpaySerialNumber(payNumber);
                            refundApplyU.setIstatus(20);
                            refundApplyU.setTfinishDatetime(new Date());
                            refundApplyService.updateBySelective(refundApplyU);
                        }
                        responseVo.setSuccess(true);
                        responseVo.setMsg("补处理成功");
                        return responseVo;
                    }
                    responseVo.setMsg("未查到实际退款数据");
                } else {
                    String err_code = (String) resMap.get("err_code ");
                    String err_code_des = (String) resMap.get("err_code_des");
                    responseVo.setMsg(err_code + ":" + err_code_des);
                }
            }
        }
        return responseVo;
    }


    /***
     * 微信支付补处理 成功业务处理
     * @param orderRecord 订单编号
     * @param payNumber 支付流水号
     * @param tpayCompleteTime 支付完成时间
     */
    private void payRepairProcessSuccess(OrderRecord orderRecord, String payNumber, Date tpayCompleteTime) {
        //补处理成功
        PayApply payApply = selectByPrimaryKey(orderRecord.getSpayApplyId());
        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("orderId", orderRecord.getId());
        pmap.put("istatus", 10);
        pmap.put("spaySerialNumber", payNumber);
        pmap.put("tpayCompleteTime", tpayCompleteTime);
        Integer updateNum = orderRecordService.updateStatusByOrderId(pmap);
        if (updateNum != null && updateNum.intValue() > 0) {
            //更新付款申请状态
            pmap = new HashMap<String, Object>();
            pmap.put("applyId", payApply.getId());
            pmap.put("istatus", 20);
            pmap.put("spaySerialNumber", payNumber);
            pmap.put("tfinishDatetime", tpayCompleteTime);
            updateStatusById(pmap);
        }
    }

    /***
     * 微信支付补处理 失败业务处理
     * @param orderRecord 订单编号
     * @param payNumber 支付流水号
     * @param tpayCompleteTime 支付完成时间
     * @param failReason 失败完成
     */
    private void payRepairProcessFail(OrderRecord orderRecord, String payNumber, Date tpayCompleteTime, String failReason) {
        //更新订单支付失败
        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("orderId", orderRecord.getId());
        pmap.put("istatus", 20);
        pmap.put("spayFailureReason", failReason);
        Integer updateNum = orderRecordService.updateStatusByOrderId(pmap);
        if (updateNum != null && updateNum.intValue() > 0) {
            //更新付款申请状态
            pmap = new HashMap<String, Object>();
            pmap.put("applyId", orderRecord.getSpayApplyId());
            pmap.put("istatus", 30);
            pmap.put("spaySerialNumber", payNumber);
            pmap.put("tfinishDatetime", tpayCompleteTime);
            updateStatusById(pmap);
        }
    }

    /**
     * 更新付款申请信息
     *
     * @param pmap
     */
    @Override
    public void updateStatusById(Map<String, Object> pmap) {
        payApplyDao.updateStatusById(pmap);
    }

    /**
     * 微信下载账单
     *
     * @param downloadBillDto 查询参数
     * @param request
     * @return
     */
    @Override
    public ResponseVo<String> wechatDownloadBill(DownloadBillDto downloadBillDto, HttpServletRequest request) throws Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //获取商户编号配置
        MerchantConf conf = merchantInfoService.getWechatMerchantConf(downloadBillDto.getSmerchantCode(), 2);
        DownloadbillReqData reqData = new DownloadbillReqData.UnifiedDownloadbillReqDataBuilder(conf, downloadBillDto.getBillType(), downloadBillDto.getBillDate()).build();
        Map<String, Object> resMap = wxPayApi.unifiedDownloadBill(conf, reqData);
        logger.info("微信账单下载返回参数：{}", JSON.toJSONString(resMap));
        if (resMap != null && null != resMap.get("return_code")) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(String.valueOf(resMap.get("return_msg")));
        }
        InputStream is = (InputStream) resMap.get("result");
        if (null != is) {
            // 返回URL地址
            String serverPath = EvnUtils.getValue("bill.wechat.path");
            String fileName = conf.getSpid() + "_" + downloadBillDto.getBillDate() + ".txt";
            if (FtpUtils.uploadToFtpServer(is, serverPath, fileName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(serverPath).append("/").append(fileName);//路径为------>/commodityInfo/2018-03-07/20888213308584530156_20180530.csv
                responseVo.setData(stringBuffer.toString());
            } else {
                responseVo.setSuccess(false);
                responseVo.setMsg("微信获取账单文件异常，上传到服务失败");
            }
        } else {
            responseVo.setSuccess(false);
            responseVo.setMsg("微信获取账单文件异常，下载微信账单失败");
        }
        return responseVo;
    }

    /**
     * 支付宝下载账单
     *
     * @param downloadBillDto 查询参数
     * @param request
     * @return
     */
    @Override
    public ResponseVo<String> alipayDownloadBill(DownloadBillDto downloadBillDto, HttpServletRequest request) throws Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        AlipayClient alipayClient = merchantInfoService.getAlipayClient(downloadBillDto.getSmerchantCode());
        AlipayDataDataserviceBillDownloadurlQueryRequest queryRequest = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        queryRequest.setBizContent("{" +
                "\"bill_type\":\"" + downloadBillDto.getBillType() + "\"," +
                "\"bill_date\":\"" + downloadBillDto.getBillDate() + "\"" +
                "  }");
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(queryRequest);
        logger.info("支付宝账单下载返回参数：{}", JSON.toJSONString(response));
        if (response.isSuccess()) {
            String paramStr = response.getBillDownloadUrl().split("[?]")[1];
            String[] arr = paramStr.split("[&]");
            Map<String, String> map = new HashMap<String, String>();
            if (arr != null) {
                for (String param : arr) {
                    map.put(param.split("=")[0], param.split("=")[1]);
                }
            }
            InputStream is = HttpUtils.getInputStream(response.getBillDownloadUrl());
            // 返回URL地址
            String serverPath = EvnUtils.getValue("bill.alipay.path");
            String fileName = map.get("downloadFileName");
            if (FtpUtils.uploadToFtpServer(is, serverPath, fileName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(serverPath).append("/").append(fileName);//路径为------>/commodityInfo/2018-03-07/20888213308584530156_20180530.csv
                responseVo.setData(stringBuffer.toString());
            } else {
                responseVo.setSuccess(false);
                responseVo.setMsg("支付宝获取账单文件异常，上传到服务失败");
            }
            return responseVo;
        } else {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(response.getSubMsg());
        }
    }


    /**
     * 创建JsAPIConfig
     *
     * @param prepayId 预付款ID
     * @param conf     商户配置数据
     * @throws Exception
     */
    private JsAPIConfig createPayConfig(String prepayId, MerchantConf conf) throws Exception {
        JsAPIConfig config = new JsAPIConfig();

        String nonce = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String packageName = "prepay_id=" + prepayId;
        StringBuffer sign = new StringBuffer();
        sign.append("appId=").append(conf.getSappId());
        sign.append("&nonceStr=").append(nonce);
        sign.append("&package=").append(packageName);
        sign.append("&signType=").append(config.getSignType());
        sign.append("&timeStamp=").append(timestamp);
        sign.append("&key=").append(conf.getSpayWechatKey());
        String signature = MD5.MD5Encode(sign.toString()).toUpperCase();

        config.setAppId(conf.getSappId());
        config.setNonce(nonce);
        config.setTimestamp(timestamp);
        config.setPackageName(packageName);
        config.setSignature(signature);

        return config;
    }
}