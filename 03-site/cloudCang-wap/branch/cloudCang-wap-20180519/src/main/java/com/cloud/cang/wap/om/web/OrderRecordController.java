package com.cloud.cang.wap.om.web;

import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.act.GiveActivityResult;
import com.cloud.cang.act.GiveResultQueryDto;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.model.hy.ThirdAuthorise;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.pay.PayApplyDto;
import com.cloud.cang.pay.PayBackDto;
import com.cloud.cang.pay.PayServicesDefine;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.SiteResponseVo;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.service.ThirdAuthoriseService;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.om.service.OrderCommodityService;
import com.cloud.cang.wap.om.service.OrderRecordService;
import com.cloud.cang.wap.om.service.RefundAuditService;
import com.cloud.cang.wap.om.vo.RefundOrderVo;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import com.cloud.cang.wap.sh.service.MerchantInfoService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Description: ????????????
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
    private static final Logger logger = LoggerFactory.getLogger(OrderRecordController.class);
    /**
     * ????????????????????????
     * @param modelMap
     * @return
     */
    @RequestMapping("/buySuccess")
    public String buySuccess(ModelMap modelMap, HttpServletRequest request) throws Exception {
        logger.debug("??????????????????????????????");
        String[] orderCodes = null;
        Integer isFirstOrder = 0;
        String orderStr = request.getParameter("orderCodes");
        if (StringUtil.isNotBlank(orderStr) && !orderStr.equals("null")) {
            orderCodes = orderStr.split(",");
            logger.info("????????????????????????????????????????????????{}", orderStr);
        }
        String firstOrderStr = request.getParameter("isFirstOrder");
        if (StringUtil.isNotBlank(firstOrderStr) && !firstOrderStr.equals("null")) {
            logger.debug("????????????????????????????????????????????????{}", firstOrderStr);
            try{
                isFirstOrder = Integer.parseInt(firstOrderStr);
            } catch (Exception e){
                isFirstOrder = 0;
            }
        }
        if (null == orderCodes || orderCodes.length <= 0) {
            return "order/empty_success";
        }

        //???????????? ?
        //????????????
        String orderCode = orderCodes[0];
        OrderRecord orderRecord = orderRecordService.selectByCode(orderCode);
        if (null != orderRecord) {
            GiveResultQueryDto queryDto = new GiveResultQueryDto();
            if (null != isFirstOrder && isFirstOrder.intValue() == 1) {//??????
                queryDto.setSourceType(BizTypeDefinitionEnum.SourceBizStatus.FIRST_ORDER.getCode());
            } else {
                queryDto.setSourceType(BizTypeDefinitionEnum.SourceBizStatus.ORDER.getCode());
            }
            queryDto.setMemberId(orderRecord.getSmemberId());
            if (orderRecord.getIisDismantling().intValue() == 1){
                //??????
                queryDto.setSourceCode(orderRecord.getSdismantlingCode());
            } else {
                queryDto.setSourceCode(orderRecord.getSorderCode());
            }
            //????????????
            // ??????Rest???????????????
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ActivityServicesDefine.QUERYACTIVEGIVERESULT_SERVICE);
            invoke.setRequestObj(queryDto);
            // ??????????????????????????????????????????????????????????????????????????????
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GiveActivityResult>>() {});
            ResponseVo<GiveActivityResult> responseVo = (ResponseVo<GiveActivityResult>) invoke.invoke();
            GiveActivityResult giveActivityResult = responseVo.getData();
            if (giveActivityResult != null) {
                if (giveActivityResult.getCouponGiveResultList() != null) {
                    modelMap.put("coupons", giveActivityResult.getCouponGiveResultList());
                }
                if (giveActivityResult.getIntegralGiveResult() != null) {
                    modelMap.put("integral", giveActivityResult.getIntegralGiveResult().getIntegralValue());
                }
            }
        }
        return "order/buy_success";
    }


    /**
     * ???????????? ??????
     */
    @RequestMapping("/payment")
    public @ResponseBody
    ResponseVo<PayBackDto> orderPayment(String orderCode, HttpServletRequest request) {

        ResponseVo<PayBackDto> responseVo = ResponseVo.getSuccessResponse();
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        try {
            //??????????????????
            OrderRecord orderRecord = orderRecordService.selectByCode(orderCode);
            if (null == orderRecord || !orderRecord.getSmemberId().equals(authVo.getId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????");
            }
            //????????????????????????
            //??????????????????
            OrderRecord updateOrder = new OrderRecord();
            updateOrder.setId(orderRecord.getId());
            MerchantClientConf clientConf = indexService.getMerchantClientConfByCode(orderRecord.getSmerchantCode());
            if (null != clientConf && clientConf.getIsupportPayWay().intValue() == 20) {
                //?????????
                //????????????????????????
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
                //??????????????????
                //???????????????????????????????????????  ???????????????????????????  ?????????????????????

            } else {
                //????????????????????????
                updateOrder.setIchargebackWay(20);//???????????????????????????
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
                    logger.error("????????????????????????????????????{}",merchantCode);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????");
                }
                //??????????????????
                PayApplyDto payApplyDto = new PayApplyDto();
                payApplyDto.setSsubject(merchantInfo.getSname() + "-" + orderRecord.getSorderCode());//????????????+????????????
                payApplyDto.setSorderId(orderRecord.getId());
                payApplyDto.setSmemberId(authVo.getId());
                payApplyDto.setSmemberCode(authVo.getCode());
                payApplyDto.setSmemberName(authVo.getUserName());
                payApplyDto.setIpayType(updateOrder.getIpayType());
                payApplyDto.setIpayWay(updateOrder.getIpayWay());
                if (WapUtils.isWXRequest(request)) {
                    if (null == clientConf || null == clientConf.getIisConfAlipay() ||
                            clientConf.getIisConfAlipay().intValue() == 0) {//????????????????????????????????????
                        merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
                    }
                    ThirdAuthorise third = thirdAuthoriseService.selectThirdAuthoriseByMemberId(authVo.getId(),10);
                    if (null == third) {
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????????????????????????????????????????");
                    }
                    payApplyDto.setSmemberOpenId(third.getSopenId());
                } else if (WapUtils.isAlipayRequest(request)) {
                    if (null == clientConf || null == clientConf.getIisConfWechat() ||
                            clientConf.getIisConfWechat().intValue() == 0) {//????????????????????????????????????
                        merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
                    }
                }
                payApplyDto.setSmerchantCode(merchantCode);
                payApplyDto.setIisIgnoreStatus(0);
                payApplyDto.setSreturnUrl(EvnUtils.getValue("wap.http.domain")+"/order/paySuccess/"+orderRecord.getSorderCode());
                // ??????Rest???????????????
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.PAY_APPLY);
                invoke.setRequestObj(payApplyDto);
                // ??????????????????????????????????????????????????????????????????????????????
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
        } catch (Exception e) {
            logger.error("?????????????????????{}", e);
        }
        responseVo.setSuccess(false);
        responseVo.setMsg("????????????");
        return responseVo;
    }
    /**
     * ??????????????????????????????
     */
    @RequestMapping("/updateStatus")
    public @ResponseBody
    ResponseVo<String> updateStatus(String orderId) {
        orderRecordService.updateStatus(orderId);
        return ResponseVo.getSuccessResponse();
    }
    /**
     * ???????????? ??????
     * @param orderCode ????????????
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/paySuccess/{orderCode}")
    public String paySuccess(@PathVariable String orderCode, ModelMap modelMap) throws Exception {
        logger.info("??????????????????????????????{}", orderCode);
        //????????????
        OrderRecord orderRecord = orderRecordService.selectByCode(orderCode);

        if (null != orderRecord) {
            //?????????????????? ??????????????????
            orderRecordService.updateStatus(orderRecord.getId());
        }
        modelMap.put("orderCode",orderCode);
        return "order/pay_success";
    }

    /**
     * ????????????
     * @param orderCode ????????????
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/applyRefund/{orderCode}")
    public String applyRefund(@PathVariable String orderCode, ModelMap modelMap) {
        logger.info("??????????????????????????????{}", orderCode);
        try {
            //????????????
            OrderRecord orderRecord = orderRecordService.selectByCode(orderCode);
            if (orderRecord == null) {
                modelMap.put("resVo", new SiteResponseVo(false, -1000, "????????????", -1));
                return "error/error";
            }
            if (orderRecord.getFactualPayAmount().doubleValue() <= 0) {
                modelMap.put("resVo", new SiteResponseVo(false, -1001, "?????????????????????0???????????????", -1));
                return "error/error";
            }
            if (orderRecord.getFactualPayAmount().compareTo(orderRecord.getFactualRefundAmount()) <= 0) {
                modelMap.put("resVo", new SiteResponseVo(false, -1002, "?????????????????????????????????????????????", -1));
                return "error/error";
            }
            //??????????????????????????????
            String expiredDayStr = SysParaUtil.getValue("refund_expired_days");
            Integer expiredDay = 15;
            if (StringUtil.isNotBlank(expiredDayStr)) {
                try {
                    expiredDay = Integer.parseInt(expiredDayStr);
                } catch (Exception e) {
                    expiredDay = 15;
                }
            }
            if (DateUtils.addDays(orderRecord.getTorderTime(),expiredDay).before(new Date())) {
                modelMap.put("resVo", new SiteResponseVo(false, -1002, "???????????????????????????????????????", -1));
                return "error/error";
            }



            //??????????????????
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("sorderId",orderRecord.getId());
            List<OrderCommodity> commodities = orderCommodityService.selectByMapWhere(params);
            modelMap.put("commodities",commodities);

            modelMap.put("orderRecord", orderRecord);//????????????

            //????????????????????????
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

        } catch (Exception e) {
            logger.error("???????????????????????????{}", e);
            modelMap.put("resVo", new SiteResponseVo(false, -1003, "??????????????????????????????", -1));
            return "error/error";
        }
        return "order/apply_refund";
    }

    /***
     * ??????????????????
     * @param refundOrderVo ????????????
     * @return
     */
    @RequestMapping("/generateRefundOrder")
    public @ResponseBody
    ResponseVo<String> generateRefundOrder(RefundOrderVo refundOrderVo, HttpServletRequest request) {


        try {
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            if (StringUtil.isBlank(refundOrderVo.getOrderCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("???????????????");
            }
            //??????????????????
            OrderRecord orderRecord = orderRecordService.selectByCode(refundOrderVo.getOrderCode());
            if (null == orderRecord || !orderRecord.getSmemberId().equals(authVo.getId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????");
            }
            if (null == refundOrderVo.getCommoditieIds()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????????????????");
            }
            if (StringUtil.isBlank(refundOrderVo.getRefundReason())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????");
            }
            //??????????????????????????????
            String expiredDayStr = SysParaUtil.getValue("refund_expired_days");
            Integer expiredDay = 15;
            if (StringUtil.isNotBlank(expiredDayStr)) {
                try {
                    expiredDay = Integer.parseInt(expiredDayStr);
                } catch (Exception e) {
                    expiredDay = 15;
                }
            }
            if (DateUtils.addDays(orderRecord.getTorderTime(),expiredDay).before(new Date())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("???????????????????????????????????????");
            }
            refundOrderVo.setRecord(orderRecord);
            return refundAuditService.generateRefundOrder(refundOrderVo,request);

        } catch (Exception e) {
            logger.error("???????????????????????????{}", e);
        }

        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????????????????????????????????????????");
    }
}
