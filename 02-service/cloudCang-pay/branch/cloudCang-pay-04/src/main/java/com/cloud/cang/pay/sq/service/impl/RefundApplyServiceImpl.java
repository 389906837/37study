package com.cloud.cang.pay.sq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sq.RefundApply;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.pay.RefundApplyDto;
import com.cloud.cang.pay.alipay.service.AliPayApi;
import com.cloud.cang.pay.alipay.vo.AliRefundData;
import com.cloud.cang.pay.common.utils.HttpClientUtils;
import com.cloud.cang.pay.om.service.OrderPayService;
import com.cloud.cang.pay.om.service.OrderRecordService;
import com.cloud.cang.pay.sh.service.MerchantInfoService;
import com.cloud.cang.pay.sq.dao.RefundApplyDao;
import com.cloud.cang.pay.sq.service.RefundApplyService;
import com.cloud.cang.pay.wechat.notify.RefundNotifyData;
import com.cloud.cang.pay.wechat.protocol.RefundReqData;
import com.cloud.cang.pay.wechat.service.WxPayApi;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RefundApplyServiceImpl extends GenericServiceImpl<RefundApply, String> implements
        RefundApplyService {

    @Autowired
    private RefundApplyDao refundApplyDao;
    @Autowired
    private OrderRecordService recordService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private WxPayApi wxPayApi;
    private static final Logger logger = LoggerFactory.getLogger(RefundApplyServiceImpl.class);

    @Override
    public GenericDao<RefundApply, String> getDao() {
        return refundApplyDao;
    }

    /**
     * ?????????????????????
     *
     * @param refundApplyDto ??????????????????
     */
    @Override
    public ResponseVo<HashMap<String, Object>> generateAliPayRefundApply(RefundApplyDto refundApplyDto) throws Exception {
        ResponseVo<HashMap<String, Object>> responseVo = ResponseVo.getSuccessResponse();
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("?????????????????????????????????");
        OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(refundApplyDto.getSorderId());
        if (null == orderRecord) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????????????????");
        }
        if (orderRecord.getIstatus().intValue() != BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("???????????????????????????????????????");
        }
        if (null == orderRecord.getFactualPayAmount() || orderRecord.getFactualPayAmount().doubleValue() <= 0) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????");
        }
        //????????????????????????
        RefundApply temp = new RefundApply();
        temp.setSauditOrderId(refundApplyDto.getSauditOrderId());
        temp.setSmemberId(refundApplyDto.getSmemberId());
        temp.setSorderId(refundApplyDto.getSorderId());
        List<RefundApply> refundApplies = refundApplyDao.selectByEntityWhere(temp);
        if (null != refundApplies && refundApplies.size() > 0) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("?????????????????????????????????????????????");
        }
        //?????????????????????????????????
        ResponseVo<RefundApply> resVo = generateRefundApply(refundApplyDto);
        if (!resVo.isSuccess()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(resVo.getMsg());
        }
        RefundApply refundApply = resVo.getData();
        //??????????????????????????????
        String payCode = orderPayService.selectOutTradeNoByOrderCode(refundApplyDto.getSorderCode());
        //????????????
        AliRefundData refundData = new AliRefundData();
        refundData.setOut_trade_no(payCode);//????????????
        refundData.setTrade_no(refundApplyDto.getStransactionId());//????????????????????????
        refundData.setOut_request_no(refundApply.getSrefundNo());//????????????
        refundData.setRefund_amount(refundApplyDto.getFrefundMoney());
        refundData.setRefund_reason(refundApplyDto.getSremark());
        //????????????????????????
        MerchantConf conf = merchantInfoService.getAlipayMerchantConf(refundApplyDto.getSmerchantCode(), 2);
        AlipayTradeRefundResponse response = AliPayApi.refundOrder(refundData, conf);
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        if (response != null) {
            logger.info("??????????????????????????????{}", JSON.toJSONString(response));
            if (response.isSuccess()) {
                //????????????
                RefundApply refundApplyU = new RefundApply();
                refundApplyU.setId(refundApply.getId());
                refundApplyU.setSpaySerialNumber(response.getTradeNo());
                refundApplyU.setTfinishDatetime(response.getGmtRefundPay());
                refundApplyU.setIstatus(20);
                this.updateBySelective(refundApplyU);


                hashMap.put("refundTime", response.getGmtRefundPay());
                hashMap.put("tradeNo", response.getTradeNo());
                responseVo.setData(hashMap);
                responseVo.setSuccess(true);
                responseVo.setErrorCode(100);
                responseVo.setMsg("????????????");
                return responseVo;
            } else {
                responseVo.setErrorCode(Integer.parseInt(response.getCode()));
                responseVo.setMsg(response.getSubMsg());
                hashMap.put("refundTime", response.getGmtRefundPay());
                hashMap.put("tradeNo", response.getTradeNo());
                responseVo.setData(hashMap);
            }

            RefundApply refundApplyU = new RefundApply();
            refundApplyU.setId(refundApply.getId());
            refundApplyU.setSpaySerialNumber(response.getTradeNo());
            refundApplyU.setIstatus(30);
            refundApplyU.setSremark(StringUtil.isNotBlank(refundApplyU.getSremark()) ? refundApplyU.getSremark() + "##" + response.getSubCode() + ":" + response.getSubMsg() : response.getSubCode() + ":" + response.getSubMsg());
            this.updateBySelective(refundApplyU);
        }
        return responseVo;
    }

    /**
     * ??????????????????
     *
     * @param refundApplyDto ??????????????????
     */
    @Override
    public ResponseVo<HashMap<String, Object>> generatePointRefundApply(RefundApplyDto refundApplyDto) throws Exception {
        logger.info("???????????????????????????{}", JSON.toJSONString(refundApplyDto));
        ResponseVo<HashMap<String, Object>> responseVo = ResponseVo.getSuccessResponse();
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("?????????????????????????????????");
        OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(refundApplyDto.getSorderId());
        if (null == orderRecord) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????????????????");
        }
        if (orderRecord.getIstatus().intValue() != BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("???????????????????????????????????????");
        }
        if (refundApplyDto.getIpayType().intValue() != BizTypeDefinitionEnum.PayType.PAY_POINT.getCode() &&
                (null == orderRecord.getFactualPayAmount() || orderRecord.getFactualPayAmount().doubleValue() <= 0)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????");
        }
        //????????????????????????
        RefundApply temp = new RefundApply();
        temp.setSauditOrderId(refundApplyDto.getSauditOrderId());
        temp.setSmemberId(refundApplyDto.getSmemberId());
        temp.setSorderId(refundApplyDto.getSorderId());
        List<RefundApply> refundApplies = refundApplyDao.selectByEntityWhere(temp);
        if (null != refundApplies && refundApplies.size() > 0) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("?????????????????????????????????????????????");
        }
        //?????????????????????????????????
        ResponseVo<RefundApply> resVo = generateRefundApply(refundApplyDto);
        if (!resVo.isSuccess()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(resVo.getMsg());
        }

        RefundApply refundApply = resVo.getData();

        Map<String, Object> map = refundPoint(refundApplyDto, refundApplyDto.getSmemberName());
        Integer status = (Integer) map.get("status");
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        if (null != status && status == 200) {
            //????????????
            logger.info("??????????????????????????????");
            RefundApply refundApplyU = new RefundApply();
            refundApplyU.setId(refundApply.getId());
            refundApplyU.setTfinishDatetime(DateUtils.getCurrentDateTime());
            refundApplyU.setIstatus(20);
            this.updateBySelective(refundApplyU);

         /*   hashMap.put("refundTime", DateUtils.getCurrentDateTime());*/
            hashMap.put("tradeNo", "");
            responseVo.setData(hashMap);
            responseVo.setSuccess(true);
            responseVo.setErrorCode(100);
            responseVo.setMsg("????????????");
            return responseVo;
        } else {
            logger.info("??????????????????????????????");
            responseVo.setErrorCode(status);
            responseVo.setMsg(ErrorCodeEnum.getNameByCode(status));
          /*  hashMap.put("refundTime", response.getGmtRefundPay());
            hashMap.put("tradeNo", response.getTradeNo());
            responseVo.setData(hashMap);*/
        }
        RefundApply refundApplyU = new RefundApply();
        refundApplyU.setId(refundApply.getId());
        refundApplyU.setIstatus(30);
        refundApplyU.setSremark(StringUtil.isNotBlank(refundApplyU.getSremark()) ? refundApplyU.getSremark() + "##" + status + ":" + ErrorCodeEnum.getNameByCode(status) : status + ":" + ErrorCodeEnum.getNameByCode(status));
        this.updateBySelective(refundApplyU);
        return responseVo;
    }

    private Map<String, Object> refundPoint(RefundApplyDto refundApplyDto, String memberName) {
        //????????????????????????
        //?????????????????????????????????
        String url = GrpParaUtil.getValue("SP000181", "changePoint");
        ParameterGroupDetail parameterGroupDetail = GrpParaUtil.getDetailForName("SP000182", "capsuleCoffee");
        if (null == parameterGroupDetail || StringUtil.isBlank(parameterGroupDetail.getSvalue()) || StringUtil.isBlank(parameterGroupDetail.getSremark())) {
            logger.info("???????????????????????????????????????{}", JSON.toJSONString(parameterGroupDetail));
            throw new ServiceException("????????????????????????????????????");
        }
        String key = parameterGroupDetail.getSvalue();
        String pwd = parameterGroupDetail.getSremark();
        Map<String, Object> temp = new HashMap();
        temp.put("mobile", memberName);
        temp.put("appKey", key);
        Integer point = (int) refundApplyDto.getFrefundPoint().doubleValue();
        temp.put("point", point);
        String token = DigestUtils.md5Hex(key + pwd + memberName + point);
        temp.put("token", token);
        String jsonStr = HttpClientUtils.sendPost(url, temp);
        if (StringUtil.isBlank(jsonStr)) {
            logger.info("????????????????????????????????????????????????{}", refundApplyDto.getSorderCode());
        }
        Map<String, Object> map1 = JSONObject.parseObject(jsonStr, Map.class);
        logger.info("??????????????????????????????????????????{}", JSON.toJSONString(map1));
        return map1;
    }

    /**
     * ??????????????????
     *
     * @param refundApplyDto ??????????????????
     */
    @Override
    public ResponseVo<HashMap<String, Object>> generateWechatRefundApply(RefundApplyDto refundApplyDto) throws Exception {
        ResponseVo<HashMap<String, Object>> responseVo = ResponseVo.getSuccessResponse();
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("?????????????????????????????????");
        OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(refundApplyDto.getSorderId());
        if (null == orderRecord) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????????????????");
        }
        if (orderRecord.getIstatus().intValue() != BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("???????????????????????????????????????");
        }
        if (null == orderRecord.getFactualPayAmount() || orderRecord.getFactualPayAmount().doubleValue() <= 0) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????");
        }
        //????????????????????????
        RefundApply temp = new RefundApply();
        temp.setSauditOrderId(refundApplyDto.getSauditOrderId());
        temp.setSmemberId(refundApplyDto.getSmemberId());
        temp.setSorderId(refundApplyDto.getSorderId());
        List<RefundApply> refundApplies = refundApplyDao.selectByEntityWhere(temp);
        if (null != refundApplies && refundApplies.size() > 0) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("?????????????????????????????????????????????");
        }
        //???????????????????????? ??? ????????????????????????

        //?????????????????????????????????
        ResponseVo<RefundApply> resVo = generateRefundApply(refundApplyDto);
        if (!resVo.isSuccess()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(resVo.getMsg());
        }
        RefundApply refundApply = resVo.getData();

        //????????????????????????
        MerchantConf conf = merchantInfoService.getWechatMerchantConf(refundApplyDto.getSmerchantCode(), 2);
        //??????????????????????????????
        String payCode = orderPayService.selectOutTradeNoByOrderCode(refundApplyDto.getSorderCode());
        //????????????
        RefundReqData.UnifiedRefundReqDataBuilder builder = new RefundReqData.UnifiedRefundReqDataBuilder(conf, orderRecord.getFactualPayAmount().multiply(new BigDecimal(100)).intValue(),
                refundApplyDto.getFrefundMoney().multiply(new BigDecimal(100)).intValue(), refundApply.getSrefundNo());
        if (!StringUtils.isBlank(refundApplyDto.getStransactionId())) {
            builder.setTransaction_id(refundApplyDto.getStransactionId());
        } else {
            builder.setOut_trade_no(payCode);
        }
        builder.setRefund_desc(refundApplyDto.getSremark());
        RefundReqData refundData = builder.build();
        /*RefundReqData refundData = new RefundReqData.UnifiedRefundReqDataBuilder(conf, orderRecord.getFactualPayAmount().multiply(new BigDecimal(100)).intValue(),
                refundApplyDto.getFrefundMoney().multiply(new BigDecimal(100)).intValue(), refundApply.getSrefundNo())
				.setOut_trade_no(payCode)
				.setTransaction_id(refundApplyDto.getStransactionId())
				.setRefund_desc(refundApplyDto.getSremark())
				.build();*/

        RefundNotifyData notifyData = wxPayApi.unifiedRefundAmount(conf, refundData);
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        String err_code = "";
        String err_code_des = "";
        logger.info("???????????????????????????{}", JSON.toJSONString(notifyData));
        if (notifyData != null) {
            if (notifyData.getReturn_code().equals("SUCCESS")) {
                if (StringUtil.isNotBlank(notifyData.getResult_code())
                        && notifyData.getResult_code().equals("SUCCESS")) {
                    //????????????
                    RefundApply refundApplyU = new RefundApply();
                    refundApplyU.setId(refundApply.getId());
                    refundApplyU.setSpaySerialNumber(notifyData.getTransaction_id());
                    refundApplyU.setTfinishDatetime(new Date());
                    refundApplyU.setIstatus(20);
                    this.updateBySelective(refundApplyU);


                    hashMap.put("refundTime", DateUtils.dateToString(new Date()));
                    hashMap.put("tradeNo", notifyData.getTransaction_id());
                    responseVo.setData(hashMap);
                    responseVo.setSuccess(true);
                    responseVo.setErrorCode(100);
                    responseVo.setMsg("????????????");
                    return responseVo;
                } else {
                    responseVo.setMsg(notifyData.getErr_code_des());
                    hashMap.put("refundTime", DateUtils.dateToString(new Date()));
                    hashMap.put("tradeNo", notifyData.getTransaction_id());
                    responseVo.setData(hashMap);
                    err_code = notifyData.getErr_code();
                    err_code_des = notifyData.getErr_code_des();
                }
            } else {
                logger.error("?????????????????????{}", notifyData.getReturn_msg());
                responseVo.setMsg(notifyData.getReturn_msg());
                err_code = notifyData.getReturn_code();
                err_code_des = notifyData.getReturn_msg();
            }
            RefundApply refundApplyU = new RefundApply();
            refundApplyU.setId(refundApply.getId());
            refundApplyU.setSpaySerialNumber(notifyData.getTransaction_id());
            refundApplyU.setIstatus(30);
            refundApplyU.setSremark(StringUtil.isNotBlank(refundApplyU.getSremark()) ? refundApplyU.getSremark() + "##" + err_code + ":" + err_code_des : err_code + ":" + err_code_des);
            this.updateBySelective(refundApplyU);
        }
        return responseVo;

    }

    /**
     * ????????????????????????
     *
     * @param refundApplyDto ??????????????????
     * @return
     * @throws Exception
     */
    @Override
    public ResponseVo<RefundApply> generateRefundApply(RefundApplyDto refundApplyDto) throws Exception {
        ResponseVo<RefundApply> responseVo = ResponseVo.getSuccessResponse();
        RefundApply refundApply = new RefundApply();
        OrderRecord orderRecord = recordService.selectByPrimaryKey(refundApplyDto.getSorderId());
        if (orderRecord == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????????????????");
        } else if (!orderRecord.getSmemberId().equals(refundApplyDto.getSmemberId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????");
        } else if (orderRecord.getIstatus().intValue() != BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????");
        }
        BeanUtils.copyProperties(refundApply, refundApplyDto);
        //????????????
        if (StringUtil.isBlank(refundApplyDto.getSrefundNo())) {
            refundApply.setSrefundNo(CoreUtils.newCode("pay_refund_code"));
        }
        refundApply.setSmemberNo(refundApplyDto.getSmemberCode());
        refundApply.setFtotalAmount(refundApplyDto.getFtotalMoney());//????????????

        //????????????????????? ??????0
        refundApply.setFrefundFee(new BigDecimal("0")); //???????????????
        refundApply.setFrefundAmount(refundApplyDto.getFrefundMoney());//????????????
        refundApply.setFactualRefundAmout(refundApply.getFrefundAmount().subtract(refundApply.getFrefundFee()));//??????????????????
        if (StringUtil.isNotBlank(refundApplyDto.getOperName())) {
            refundApply.setSadduserId(refundApplyDto.getOperName());//??????????????????
        }
        refundApply.setIstatus(10);
        refundApply.setTaddTime(new Date());
        refundApply.setScurrency("10");
        refundApply.setTordertime(new Date());
        refundApply.setSremark(refundApplyDto.getSremark());
        refundApply.setIversion(1);
        this.insert(refundApply);

        responseVo.setData(refundApply);
        return responseVo;
    }
}