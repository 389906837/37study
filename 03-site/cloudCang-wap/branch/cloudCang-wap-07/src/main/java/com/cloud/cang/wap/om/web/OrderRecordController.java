package com.cloud.cang.wap.om.web;

import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.act.GiveActivityResult;
import com.cloud.cang.act.GiveResultQueryDto;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.*;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.hy.ThirdAuthorise;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.pay.*;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.SiteResponseVo;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import com.cloud.cang.wap.hy.service.ThirdAuthoriseService;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.om.service.OrderCommodityService;
import com.cloud.cang.wap.om.service.OrderRecordService;
import com.cloud.cang.wap.om.service.RefundAuditService;
import com.cloud.cang.wap.om.vo.CommodityVo;
import com.cloud.cang.wap.om.vo.RefundOrderVo;
import com.cloud.cang.wap.om.vo.ShoppingCartDoamin;
import com.cloud.cang.wap.sh.service.MerchantInfoService;
import com.cloud.cang.weixin.api.TicketAPI;
import com.cloud.cang.weixin.api.TokenAPI;
import com.cloud.cang.weixin.bean.Ticket;
import com.cloud.cang.weixin.bean.Token;
import com.cloud.cang.weixin.util.JsUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Description: 订单控制
 * @Author: zhouhong
 * @Date: 2018/4/17 16:28
 */
@Controller
@RequestMapping("/order")
public class OrderRecordController {

    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private IndexService indexService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private OrderCommodityService orderCommodityService;
    @Autowired
    private RefundAuditService refundAuditService;
    @Autowired
    private ThirdAuthoriseService thirdAuthoriseService;
    @Autowired
    private ICached iCached;
    @Autowired
    private MemberInfoService memberInfoService;
    private static final Logger logger = LoggerFactory.getLogger(OrderRecordController.class);


    /**
     * 云端识别购物实时订单
     * @param modelMap
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/shoppingCloudRealTimeOrder")
    @ResponseBody
    public ShoppingCartDoamin shoppingCloudRealTimeOrder(ModelMap modelMap, HttpServletRequest request) throws Exception {
        logger.debug("云端识别购物实时订单开始");
        String deviceId_userId = request.getParameter("userIdAndDeviceId");

        // 从Redis中获取订单信息
        String json = (String) iCached.get(RedisConst.CLOUD_REALTIME_DISTINGUISH + "_" + deviceId_userId);
        ShoppingCartDoamin shoppingCartDoamin = null;
        if (!StringUtil.isBlank(json)) {
            shoppingCartDoamin = JSONObject.parseObject(json, ShoppingCartDoamin.class);
        } else {
            shoppingCartDoamin = new ShoppingCartDoamin();
            shoppingCartDoamin.setActuallyPaid("0.00");
            shoppingCartDoamin.setDiscountedPrice("0.00");
            shoppingCartDoamin.setTotalNum(0);
            shoppingCartDoamin.setShelfNum(0);
            shoppingCartDoamin.setObtainedNum(0);
        }
        return shoppingCartDoamin;
    }


    /**
     * 普通会员关门成功
     * @param modelMap
     * @return
     */
    @RequestMapping("/buySuccess")
    public String buySuccess(ModelMap modelMap, HttpServletRequest request) throws Exception {
        logger.debug("普通会员购物关门成功");
        String[] orderCodes = null;
        Integer isFirstOrder = 0;
        String orderStr = request.getParameter("orderCodes");
        if (StringUtil.isNotBlank(orderStr) && !orderStr.equals("null")) {
            orderCodes = orderStr.split(",");
            logger.info("普通会员购物关门成功，订单编号：{}", orderStr);
        }
        String firstOrderStr = request.getParameter("isFirstOrder");
        if (StringUtil.isNotBlank(firstOrderStr) && !firstOrderStr.equals("null")) {
            logger.debug("普通会员购物关门成功，是否首单：{}", firstOrderStr);
            try {
                isFirstOrder = Integer.parseInt(firstOrderStr);
            } catch (Exception e) {
                isFirstOrder = 0;
            }
        }
        if (null == orderCodes || orderCodes.length <= 0) {
            return "order/empty_success";
        }

        //广告区域 ?
        //订单数据
        String orderCode = orderCodes[0];
        OrderRecord orderRecord = orderRecordService.selectByCode(orderCode);
        modelMap.put("ichargebackWay", orderRecord.getIchargebackWay());

        if (null != orderRecord) {
            GiveResultQueryDto queryDto = new GiveResultQueryDto();
            if (null != isFirstOrder && isFirstOrder.intValue() == 1) {//首单
                queryDto.setSourceType(BizTypeDefinitionEnum.SourceBizStatus.FIRST_ORDER.getCode());
            } else {
                queryDto.setSourceType(BizTypeDefinitionEnum.SourceBizStatus.ORDER.getCode());
            }
            queryDto.setMemberId(orderRecord.getSmemberId());
            if (orderRecord.getIisDismantling().intValue() == 1) {
                //拆单
                queryDto.setSourceCode(orderRecord.getSdismantlingCode());
            } else {
                queryDto.setSourceCode(orderRecord.getSorderCode());
            }
            //活动服务
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ActivityServicesDefine.QUERYACTIVEGIVERESULT_SERVICE);
            invoke.setRequestObj(queryDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GiveActivityResult>>() {});
            ResponseVo<GiveActivityResult> responseVo = (ResponseVo<GiveActivityResult>) invoke.invoke();
            GiveActivityResult giveActivityResult = responseVo.getData();
            if (giveActivityResult != null) {
                if (giveActivityResult.getCouponGiveResultList() != null) {
                    modelMap.put("coupons", giveActivityResult.getCouponGiveResultList());
                    modelMap.put("couponNum", giveActivityResult.getCouponNum());
                }
                if (giveActivityResult.getIntegralGiveResult() != null) {
                    modelMap.put("integral", giveActivityResult.getIntegralGiveResult().getIntegralValue());
                }
            }
        }
        return "order/buy_success_new";
    }


    /**
     * 立即支付 手动
     */
    @RequestMapping("/payment")
    public @ResponseBody
    ResponseVo<PayBackDto> orderPayment(String orderCode, HttpServletRequest request) {
        ResponseVo<PayBackDto> responseVo = ResponseVo.getSuccessResponse();
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        try {
            //获取商户数据
            OrderRecord orderRecord = orderRecordService.selectByCode(orderCode);
            if (null == orderRecord || !orderRecord.getSmemberId().equals(authVo.getId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
            }
            //获取商户配置数据
            //更新订单数据
            OrderRecord updateOrder = new OrderRecord();
            updateOrder.setId(orderRecord.getId());
            MerchantClientConf clientConf = indexService.getMerchantClientConfByCode(orderRecord.getSmerchantCode());
            if (null != clientConf && clientConf.getIsupportPayWay().intValue() == 20) {
                //仅代扣
                //更新订单支付方式
                if (orderRecord.getIchargebackWay().intValue() != BizTypeDefinitionEnum.ChargebackWay.WITHHOLDING.getCode()) {
                    updateOrder.setIchargebackWay(BizTypeDefinitionEnum.ChargebackWay.WITHHOLDING.getCode());
                }
                if (orderRecord.getIsourceClientType().intValue() == BizTypeDefinitionEnum.ClientType.WECHAT.getCode()) {
                    updateOrder.setIpayType(BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode());
                } else if (orderRecord.getIsourceClientType().intValue() == BizTypeDefinitionEnum.ClientType.ALIPAY.getCode()) {
                    updateOrder.setIpayType(BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode());
                } else {
                    if (WapUtils.isWXRequest(request)) {
                        updateOrder.setIpayType(BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode());
                    } else if (WapUtils.isAlipayRequest(request)) {
                        updateOrder.setIpayType(BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode());
                    }
                }
                orderRecordService.updateBySelective(updateOrder);
                //发起代扣支付
                //查询订单是否存在待支付扣款  如存在直接返回结果  不存在重新发起
                //支付宝支付失败订单
                //查看会员是否开通免密
                MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(orderRecord.getSmemberId());
                if (BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode() == orderRecord.getIpayType()) {
                    //会员开通支付宝免密支付则代付
                    if (1 == memberInfo.getIaipayOpen()) {
                        //支付宝免密支付查询
                        PaymentDto paymentDto = new PaymentDto();
                        paymentDto.setSmerchantCode(orderRecord.getSmerchantCode());//收款商户
                        paymentDto.setOutTradeNo(orderRecord.getSorderCode());//商户订单编号 必填
                        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_QUERY_PAYMENT);
                        invoke.setRequestObj(paymentDto); // post 参数
                        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<QueryPaymentResult>>() {
                        });
                        ResponseVo<QueryPaymentResult> responseVo2 = (ResponseVo<QueryPaymentResult>) invoke.invoke();
                        if (null != responseVo2 && ((responseVo2.isSuccess() && null != responseVo2.getData() && ("WAIT_BUYER_PAY".equals(responseVo2.getData().getTradeStatus()) || "TRADE_CLOSED".equals(responseVo2.getData().getTradeStatus()))) ||
                                (!responseVo2.isSuccess() && -1000 == responseVo2.getErrorCode()))) {
                            FreePaymentDto freePaymentDto = new FreePaymentDto();
                            freePaymentDto.setSmerchantCode(orderRecord.getSmerchantCode());
                            freePaymentDto.setSmemberId(orderRecord.getSmemberId());
                            freePaymentDto.setSmemberCode(orderRecord.getSmemberCode());
                            freePaymentDto.setSmemberName(orderRecord.getSmemberName());
                            freePaymentDto.setSorderId(orderRecord.getId());
                            String merchantCode = orderRecord.getSmerchantCode();
                            MerchantInfo merchantInfo = merchantInfoService.selectByCode(merchantCode);
                            if (null == merchantInfo) {
                                logger.error("收款商户异常，商户编号：{}", merchantCode);
                                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("订单异常，支付失败");
                            }
                            freePaymentDto.setSsubject(merchantInfo.getSname() + "-" + orderRecord.getId());
                            freePaymentDto.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());
                            freePaymentDto.setIisIgnoreStatus(1);
                            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_PAYMENT);
                            invoke.setRequestObj(freePaymentDto); // post 参数
                            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {
                            });
                            ResponseVo<FreePaymentResult> responseVo3 = (ResponseVo<FreePaymentResult>) invoke.invoke();
                            if (null != responseVo3 && responseVo3.isSuccess()) {
                                logger.info("wap商户代扣订单支付成功,订单编号：{}", orderRecord.getSorderCode());
                                PayBackDto payBackDto =new PayBackDto();
                                payBackDto.setIpayType(40);
                                payBackDto.setIpayWay(70);
                                responseVo.setData(payBackDto);
                                responseVo.setMsg("支付成功！");
                                //成功返回消息
                                return responseVo;
                            }
                        }
                    }
                }
                //微信支付失败订单
                else if (BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode() == orderRecord.getIpayType()) {
                    //会员开通微信免密支付则代付
                    if (1 == memberInfo.getIwechatOpen()) {
                        //微信免密支付查询
                        PaymentDto paymentDto = new PaymentDto();
                        paymentDto.setSmerchantCode(orderRecord.getSmerchantCode());//收款商户
                        paymentDto.setOutTradeNo(orderRecord.getSorderCode());//商户订单编号 必填
                        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_QUERY_PAYMENT);
                        invoke.setRequestObj(paymentDto); // post 参数
                        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<QueryWechatFreePayResult>>() {
                        });
                        ResponseVo<QueryWechatFreePayResult> responseVo4 = (ResponseVo<QueryWechatFreePayResult>) invoke.invoke();
                        if (null != responseVo4 && ((responseVo4.isSuccess() && null != responseVo4.getData() && ("CLOSED".equals(responseVo4.getData().getTrade_state()) || "PAY_FAIL".equals(responseVo4.getData().getTrade_state()))) ||
                                (!responseVo4.isSuccess() && -1001 == responseVo4.getErrorCode()))) {
                            FreePaymentDto freePaymentDto = new FreePaymentDto();
                            freePaymentDto.setSmerchantCode(orderRecord.getSmerchantCode());
                            freePaymentDto.setSmemberId(orderRecord.getSmemberId());
                            freePaymentDto.setSmemberCode(orderRecord.getSmemberCode());
                            freePaymentDto.setSmemberName(orderRecord.getSmemberName());
                            freePaymentDto.setSorderId(orderRecord.getId());
                            String merchantCode = orderRecord.getSmerchantCode();
                            MerchantInfo merchantInfo = merchantInfoService.selectByCode(merchantCode);
                            if (null == merchantInfo) {
                                logger.error("收款商户异常，商户编号：{}", merchantCode);
                                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("订单异常，支付失败");
                            }
                            freePaymentDto.setSsubject(merchantInfo.getSname() + "-" + orderRecord.getSorderCode());//商户明细+订单编号
                            freePaymentDto.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());
                            freePaymentDto.setIisIgnoreStatus(1);
                            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_PAYMENT);
                            invoke.setRequestObj(freePaymentDto); // post 参数
                            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {
                            });
                            ResponseVo<FreePaymentResult> responseVo2 = (ResponseVo<FreePaymentResult>) invoke.invoke();
                            if (null != responseVo2 && responseVo2.isSuccess()) {
                                logger.info("wap商户代扣订单支付成功,订单编号：{}", orderRecord.getSorderCode());
                                PayBackDto payBackDto =new PayBackDto();
                                payBackDto.setIpayType(30);
                                payBackDto.setIpayWay(70);
                                responseVo.setData(payBackDto);
                                responseVo.setMsg("支付成功！");
                                return responseVo;
                            }
                        }
                    }
                }
            } else {
                //更新订单支付方式
                updateOrder.setIchargebackWay(20);//扣款方式为手动支付
                updateOrder.setIpayWay(BizTypeDefinitionEnum.PayWay.PUBLIC_NUMBER.getCode());
                if (WapUtils.isWXRequest(request)) {
                    updateOrder.setIpayType(BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode());
                } else if (WapUtils.isAlipayRequest(request)) {
                    updateOrder.setIpayType(BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode());
                }
                orderRecordService.updateBySelective(updateOrder);
                String merchantCode = orderRecord.getSmerchantCode();
                MerchantInfo merchantInfo = merchantInfoService.selectByCode(merchantCode);
                if (null == merchantInfo) {
                    logger.error("收款商户异常，商户编号：{}", merchantCode);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("订单异常，支付失败");
                }
                //调用支付服务
                PayApplyDto payApplyDto = new PayApplyDto();
                payApplyDto.setSsubject(merchantInfo.getSname() + "-" + orderRecord.getSorderCode());//商户明细+订单编号
                payApplyDto.setSorderId(orderRecord.getId());
                payApplyDto.setSmemberId(authVo.getId());
                payApplyDto.setSmemberCode(authVo.getCode());
                payApplyDto.setSmemberName(authVo.getUserName());
                payApplyDto.setIpayType(updateOrder.getIpayType());
                payApplyDto.setIpayWay(updateOrder.getIpayWay());
                if (WapUtils.isWXRequest(request)) {
                    if (null == clientConf || null == clientConf.getIisConfWechat() ||
                            clientConf.getIisConfWechat().intValue() == 0) {//没有配置获取默认商户编号
                        merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
                    }
                    ThirdAuthorise third = thirdAuthoriseService.selectThirdAuthoriseByMemberId(authVo.getId(), 10);
                    if (null == third) {
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("微信支付授权异常，请退出重新登录");
                    }
                    payApplyDto.setSmemberOpenId(third.getSopenId());
                } else if (WapUtils.isAlipayRequest(request)) {
                    if (null == clientConf || null == clientConf.getIisConfAlipay() ||
                            clientConf.getIisConfAlipay().intValue() == 0) {//没有配置获取默认商户编号
                        merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
                    }
                }
                payApplyDto.setSmerchantCode(merchantCode);
                payApplyDto.setSip(RequestUtils.getIpAddr(request));
                payApplyDto.setIisIgnoreStatus(0);
                payApplyDto.setSreturnUrl(EvnUtils.getValue("wap.http.domain") + "/order/paySuccess/" + orderRecord.getSorderCode());
                // 创建Rest服务客户端
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.PAY_APPLY);
                invoke.setRequestObj(payApplyDto);
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<PayBackDto>>() {});
                ResponseVo<PayBackDto> resVo = (ResponseVo<PayBackDto>) invoke.invoke();
                if (resVo.isSuccess()) {
                    responseVo.setData(resVo.getData());
                    return responseVo;
                } else {
                    responseVo.setSuccess(false);
                    responseVo.setMsg(resVo.getMsg());
                    return responseVo;
                }
            }
        } catch (
                Exception e)

        {
            logger.error("创建支付失败：{}", e);
        }
        responseVo.setSuccess(false);
        responseVo.setMsg("支付失败");
        return responseVo;
    }

    /**
     * 支付成功修改订单状态
     */
    @RequestMapping("/updateStatus")
    public @ResponseBody
    ResponseVo<String> updateStatus(String orderId) {
        orderRecordService.updateStatus(orderId);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 支付成功 页面
     *
     * @param orderCode 订单编号
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/paySuccess/{orderCode}")
    public String paySuccess(@PathVariable String orderCode, ModelMap modelMap) throws Exception {
        logger.info("支付成功，订单编号：{}", orderCode);
        //订单数据
        OrderRecord orderRecord = orderRecordService.selectByCode(orderCode);

        if (null != orderRecord) {
            //更新订单状态 为支付处理中
            orderRecordService.updateStatus(orderRecord.getId());
        }
        modelMap.put("orderCode", orderCode);
        return "order/pay_success_new";
    }

    /**
     * 申请退款
     *
     * @param orderCode 订单编号
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/applyRefund/{orderCode}")
    public String applyRefund(@PathVariable String orderCode, ModelMap modelMap,HttpServletRequest request) throws Exception {
        logger.info("申请退款，订单编号：{}", orderCode);
        try {
            //订单数据
            OrderRecord orderRecord = orderRecordService.selectByCode(orderCode);
            if (orderRecord == null) {
                modelMap.put("resVo", new SiteResponseVo(false, -1000, "订单异常", -1));
                return "error/error";
            }
            if (orderRecord.getFactualPayAmount().doubleValue() <= 0) {
                modelMap.put("resVo", new SiteResponseVo(false, -1001, "订单实付金额为0，无法退款", -1));
                return "error/error";
            }
            if (orderRecord.getFactualPayAmount().compareTo(orderRecord.getFactualRefundAmount()) <= 0) {
                modelMap.put("resVo", new SiteResponseVo(false, -1002, "订单已申请退款，请不要重复操作", -1));
                return "error/error";
            }
            //订单日期过了退款期限
            String expiredDayStr = SysParaUtil.getValue("refund_expired_days");
            Integer expiredDay = 15;
            if (StringUtil.isNotBlank(expiredDayStr)) {
                try {
                    expiredDay = Integer.parseInt(expiredDayStr);
                } catch (Exception e) {
                    expiredDay = 15;
                }
            }
            if (DateUtils.addDays(orderRecord.getTorderTime(), expiredDay).before(new Date())) {
                modelMap.put("resVo", new SiteResponseVo(false, -1002, "已过退款期限，不能申请退款", -1));
                return "error/error";
            }

            //订单商品明细
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("sorderId", orderRecord.getId());
            List<CommodityVo> commodities = orderCommodityService.selectByMap(params);
            modelMap.put("commodities", commodities);

            modelMap.put("orderRecord", orderRecord);//订单数据

            //默认上传图片数量
            Integer uploadNum = 3;
            String uploadStr = SysParaUtil.getValue("refund_order_pic_num");
            if (StringUtil.isNotBlank(uploadStr)) {
                try {
                    uploadNum = Integer.parseInt(uploadStr);
                } catch (Exception e) {
                    uploadNum = 3;
                }
            }
            modelMap.put("uploadNum", uploadNum);

            modelMap = indexService.getPayConfig(modelMap, request);

        } catch (Exception e) {
            logger.error("申请退款数据异常：{}", e);
            modelMap.put("resVo", new SiteResponseVo(false, -1003, "系统异常，请重新操作", -1));
            return "error/error";
        }
        return "order/apply_refund_new";
    }

    /***
     * 生成退款订单
     * @param refundOrderVo 退款参数
     * @return
     */
    @RequestMapping("/generateRefundOrder")
    public @ResponseBody
    ResponseVo<String> generateRefundOrder(RefundOrderVo refundOrderVo, HttpServletRequest request) {
        try {
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            if (StringUtil.isBlank(refundOrderVo.getOrderCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单不存在");
            }
            //获取商户数据
            OrderRecord orderRecord = orderRecordService.selectByCode(refundOrderVo.getOrderCode());
            if (null == orderRecord || !orderRecord.getSmemberId().equals(authVo.getId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单状态异常");
            }
            if (null == refundOrderVo.getCommoditieIds()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择需要退款的商品");
            }
            /*if (StringUtil.isBlank(refundOrderVo.getRefundReason())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("退款原因不能为空");
            }*/
            //订单日期过了退款期限
            String expiredDayStr = SysParaUtil.getValue("refund_expired_days");
            Integer expiredDay = 15;
            if (StringUtil.isNotBlank(expiredDayStr)) {
                try {
                    expiredDay = Integer.parseInt(expiredDayStr);
                } catch (Exception e) {
                    expiredDay = 15;
                }
            }
            if (DateUtils.addDays(orderRecord.getTorderTime(), expiredDay).before(new Date())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("已过退款期限，不能申请退款");
            }
            refundOrderVo.setRecord(orderRecord);
            return refundAuditService.generateRefundOrder(refundOrderVo, request);
        } catch (ServiceException e) {
            logger.error("退款订单商品数据异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("生成退款订单失败：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("生成退款订单失败，请重新操作");
    }
}
