package com.cloud.cang.mgr.om.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.inventory.CommodityDiffVo;
import com.cloud.cang.inventory.InventoryCommodityDiffVo;
import com.cloud.cang.jy.*;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.om.dao.OrderAuditDao;
import com.cloud.cang.mgr.om.domain.OrderAuditDomain;
import com.cloud.cang.mgr.om.service.OrderAuditCommodityService;
import com.cloud.cang.mgr.om.service.OrderAuditService;
import com.cloud.cang.mgr.om.vo.DownloadOrderAuditVo;
import com.cloud.cang.mgr.om.vo.OrderAuditVo;
import com.cloud.cang.mgr.om.vo.OrderCommodityEditVo;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderAudit;
import com.cloud.cang.model.om.OrderAuditCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.pay.FreePaymentDto;
import com.cloud.cang.pay.FreePaymentResult;
import com.cloud.cang.pay.FreeServicesDefine;
import com.cloud.cang.rm.StockOperDto;
import com.cloud.cang.rm.StockServicesDefine;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderAuditServiceImpl extends GenericServiceImpl<OrderAudit, String> implements
        OrderAuditService {

    private static final Logger logger = LoggerFactory.getLogger(OrderAuditServiceImpl.class);

    @Autowired
    OrderAuditDao orderAuditDao;
    @Autowired
    OrderAuditCommodityService orderAuditCommodityService;
    @Autowired
    CommodityInfoService commodityInfoService;
    @Autowired
    MerchantInfoService merchantInfoService;
    @Autowired
    private ICached iCached;
    @Autowired
    DeviceInfoService deviceInfoService;

    @Override
    public GenericDao<OrderAudit, String> getDao() {
        return orderAuditDao;
    }


    @Override
    public Page<OrderAuditVo> selectPageByDomainWhere(Page<OrderAuditVo> page, OrderAuditDomain orderAuditDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return orderAuditDao.selectPageByDomainWhere(orderAuditDomain);
    }

    /**
     * ????????????
     *
     * @param checkboxId ????????????ID
     * @return ResponseVo
     */
    @Override
    public ResponseVo payOrder(String checkboxId) {

        OrderAudit orderAudit = this.selectByPrimaryKey(checkboxId);
        OrderCommDto orderCommDto = new OrderCommDto();
        OrderDto orderDto = new OrderDto();
        orderDto.setSmerchantId(orderAudit.getSmerchantId());//??????ID
        orderDto.setSmerchantCode(orderAudit.getSmerchantCode());//????????????
        orderDto.setSmemberId(orderAudit.getSmemberId());//??????ID
        orderDto.setSmemberCode(orderAudit.getSmemberCode());//????????????
        orderDto.setSmemberName(orderAudit.getSmemberName());//?????????
        int type = orderAudit.getIsourceClientType();// 10=??????  20=?????????   30=APP
        orderDto.setIsourceClientType(type);//????????????
        orderDto.setSreaderSerialNumber(orderAudit.getSreaderSerialNumber());//????????????????????????
        orderDto.setSdeviceId(orderAudit.getSdeviceId());//??????ID
        orderDto.setSdeviceCode(orderAudit.getSdeviceCode());//??????Code
        orderDto.setSdeviceAddress(orderAudit.getSdeviceAddress());//????????????
        orderDto.setSdeviceName(orderAudit.getSdeviceName());//?????????
        OrderAuditCommodity orderAuditCommodity = new OrderAuditCommodity();
        orderAuditCommodity.setSorderId(checkboxId);
        List<OrderAuditCommodity> orderAuditCommodities = orderAuditCommodityService.selectByEntityWhere(orderAuditCommodity);
        List<OrderCommodityDto> orderCommodityDtoList = new ArrayList<>();
        for (OrderAuditCommodity orderAuditCommodity1 : orderAuditCommodities) {
            OrderCommodityDto orderCommodityDto = new OrderCommodityDto();
            CommodityInfo commodityInfo = commodityInfoService.selectByPrimaryKey(orderAuditCommodity1.getScommodityId());
            orderCommodityDto.setScommodityId(commodityInfo.getId());//??????ID
            orderCommodityDto.setScommodityCode(commodityInfo.getScode());//????????????
            orderCommodityDto.setScommodityName(commodityInfo.getSname());//?????????
            orderCommodityDto.setFcommodityPrice(orderAuditCommodity1.getFcommodityPrice());//????????????
            orderCommodityDto.setForderCount(orderAuditCommodity1.getForderCount());//????????????
            orderCommodityDto.setFtaxPoint(orderAuditCommodity1.getFtaxPoint());//????????????
            orderCommodityDto.setSsmallCategoryId(commodityInfo.getSsmallCategoryId()); /* ????????????ID */
            orderCommodityDto.setSbrandId(commodityInfo.getSbrandId());/* ????????????ID */
            orderCommodityDto.setFcostAmount(commodityInfo.getFcostPrice());   /* ?????????????????? */
            orderCommodityDtoList.add(orderCommodityDto);
        }
        orderCommDto.setComms(orderCommodityDtoList);
        orderCommDto.setOrderDto(orderDto);
        ResponseVo<GeneratingOrderResults> responseVo = null;
        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OrderDiscountDefine.CREATE_ORDER);
            invoke.setRequestObj(orderCommDto); // post ??????
            // ??????????????????????????????????????????????????????????????????????????????
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GeneratingOrderResults>>() {
            });
            responseVo = (ResponseVo<GeneratingOrderResults>) invoke.invoke();
            if (null != responseVo && responseVo.isSuccess() && null != responseVo.getData()) {
                logger.info("????????????????????????????????????");
                //???????????????40????????????????????????
                if (40 == orderAudit.getItype()) {
                    DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(orderAudit.getSdeviceId());
                    // ??????????????????
                    GeneratingOrderResults orderResults = responseVo.getData();
                    List<CreatOrderResult> orderRecords = orderResults.getCreatOrderResultList();
                    //1??????????????????
                    Map<String, Object> map = calcIncrementsAndDecrements(orderCommodityDtoList);
                    List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");
                    //????????????
                    StringBuffer sb = new StringBuffer();
                    for (CreatOrderResult orderRecord : orderRecords) {
                        sb.append(orderRecord.getOrderRecord().getId() + ",");
                    }
                    //??????????????????
                    stockChange(20, null, subVoList, deviceInfo, sb.toString().substring(0, sb.toString().length() - 1));
                }
            } else {
                logger.error("????????????????????????????????????");
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????");
            }
        } catch (Exception e) {
            logger.error("????????????????????????", e);
           /* return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????");*/
            throw new ServiceException("???????????????????????????");
        }
        //????????????????????????
        orderAudit.setIstatus(20);
        orderAudit.setShandlerUserid(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        orderAudit.setShandlerUsername(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        orderAudit.setThandlerTime(DateUtils.getCurrentDateTime());
        orderAudit.setTupdateTime(DateUtils.getCurrentDateTime());
        this.updateBySelective(orderAudit);
        //????????????
        String logText = MessageFormat.format("????????????????????????,??????????????????{0}", orderAudit.getSorderCode());
        LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
        //????????????????????????
        payErrorOrder(responseVo, type);

        return ResponseVo.getSuccessResponse("???????????????????????????");
    }

    /**
     * ??????????????????
     *
     * @param
     */
    private void payErrorOrder(final ResponseVo<GeneratingOrderResults> responseVo, final Integer type) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                //2.????????????
                List<CreatOrderResult> creatOrderResultList = responseVo.getData().getCreatOrderResultList();
                String isSendMessage = BizParaUtil.get("order_audit_notify");
                for (CreatOrderResult creatOrderResult : creatOrderResultList) {
                    OrderRecord orderRecord = creatOrderResult.getOrderRecord();
                    //???????????????
                    try {
                        if (20 == type && 10 == orderRecord.getIchargebackWay()) {
                            FreePaymentDto freePaymentDto = new FreePaymentDto();
                            freePaymentDto.setSmerchantCode(orderRecord.getSmerchantCode());
                            freePaymentDto.setSmemberId(orderRecord.getSmemberId());
                            freePaymentDto.setSmemberCode(orderRecord.getSmemberCode());
                            freePaymentDto.setSmemberName(orderRecord.getSmemberName());
                            freePaymentDto.setSorderId(orderRecord.getId());
                            String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.MERCHANT_INFO_DETAILS + orderRecord.getSmerchantCode());
                            MerchantInfo merchantInfo = null;
                            if (StringUtil.isNotBlank(json)) {
                                merchantInfo = JSONObject.parseObject(json, MerchantInfo.class);
                            }
                            freePaymentDto.setSsubject(merchantInfo.getSname() + "-" + orderRecord.getId());
                           /* freePaymentDto.setIpayWay(orderRecord.getIpayWay());*/
                            freePaymentDto.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());
                            freePaymentDto.setIisIgnoreStatus(0);

                            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_PAYMENT);
                            invoke.setRequestObj(freePaymentDto); // post ??????
                            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {
                            });
                            ResponseVo<FreePaymentResult> payResponseVo = (ResponseVo<FreePaymentResult>) invoke.invoke();
                            if (null != payResponseVo && payResponseVo.isSuccess()) {
                                logger.info("??????????????????????????????????????????????????????????????????");
                            } else {
                                logger.error("??????????????????????????????????????????????????????????????????");
                            }
                            //????????????????????????????????????????????????
                            if ("1".equals(isSendMessage)) {
                                Map<String, Object> contentParam = new HashMap<String, Object>();
                                contentParam.put("address", orderRecord.getSdeviceAddress());
                                contentParam.put("time", DateUtils.dateParseString(orderRecord.getTorderTime()));
                                contentParam.put("money", orderRecord.getFactualPayAmount());
                                ResponseVo res = sendMessage(contentParam, orderRecord.getSmemberName(), "order_audit_pay_notify");
                                if (null != res && res.isSuccess()) {
                                    logger.info("????????????????????????????????????????????????,??????ID???{}", orderRecord.getId());
                                }
                            }
                        } else if (10 == type && 10 == orderRecord.getIchargebackWay()) {
                            //????????????
                            FreePaymentDto freePaymentDto = new FreePaymentDto();
                            freePaymentDto.setSmerchantCode(orderRecord.getSmerchantCode());
                            freePaymentDto.setSmemberId(orderRecord.getSmemberId());
                            freePaymentDto.setSmemberCode(orderRecord.getSmemberCode());
                            freePaymentDto.setSmemberName(orderRecord.getSmemberName());
                            freePaymentDto.setSorderId(orderRecord.getId());
                            String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.MERCHANT_INFO_DETAILS + orderRecord.getSmerchantCode());
                            MerchantInfo merchantInfo = null;
                            if (StringUtil.isNotBlank(json)) {
                                merchantInfo = JSONObject.parseObject(json, MerchantInfo.class);
                            }
                            freePaymentDto.setSsubject(merchantInfo.getSname() + "-" + orderRecord.getId());
                         /*   freePaymentDto.setIpayWay(orderRecord.getIpayWay());*/
                            freePaymentDto.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());
                            freePaymentDto.setIisIgnoreStatus(0);
                            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_PAYMENT);
                            invoke.setRequestObj(freePaymentDto); // post ??????
                            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {
                            });
                            ResponseVo<FreePaymentResult> payResponseVo = (ResponseVo<FreePaymentResult>) invoke.invoke();
                            if (null != payResponseVo && payResponseVo.isSuccess()) {
                                logger.info("???????????????????????????????????????????????????????????????");
                            } else {
                                logger.error("???????????????????????????????????????????????????????????????");
                            }
                            //
                            //????????????????????????????????????????????????
                            if ("1".equals(isSendMessage)) {
                                Map<String, Object> contentParam = new HashMap<String, Object>();
                                contentParam.put("address", orderRecord.getSdeviceAddress());
                                contentParam.put("time", DateUtils.dateParseString(orderRecord.getTorderTime()));
                                contentParam.put("money", orderRecord.getFactualPayAmount());
                                ResponseVo res = sendMessage(contentParam, orderRecord.getSmemberName(), "order_audit_pay_notify");
                                if (null != res && res.isSuccess()) {
                                    logger.info("????????????????????????????????????????????????,??????ID???{}", orderRecord.getId());
                                }
                            }
                        } else {
                            //????????????????????????????????????????????????
                            if ("1".equals(isSendMessage)) {
                                Map<String, Object> contentParam = new HashMap<String, Object>();
                                contentParam.put("address", orderRecord.getSdeviceAddress());
                                contentParam.put("time", DateUtils.dateParseString(orderRecord.getTorderTime()));
                                contentParam.put("money", orderRecord.getFactualPayAmount());
                                ResponseVo res = sendMessage(contentParam, orderRecord.getSmemberName(), "order_audit_notify");
                                if (null != res && res.isSuccess()) {
                                    logger.info("??????????????????????????????????????????,??????ID???{}", orderRecord.getId());
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("??????????????????????????????", e);
                        throw new ServiceException("?????????????????????????????????");
                    }
                }
            }
        });
    }

    /**
     * ????????????????????????
     *
     * @param contentParam
     * @param mobile
     * @param parm
     * @return
     * @throws Exception
     */
    private ResponseVo sendMessage(Map<String, Object> contentParam, String mobile, String parm) {
        try {
            MessageDto messageDto = new MessageDto();
            messageDto.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            messageDto.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
            // ????????????
            messageDto.setTemplateType(parm);
            //????????????
            //??????
            messageDto.setContentParam(contentParam);
            messageDto.setMobile(mobile);
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);//????????????
            invoke.setRequestObj(messageDto); //post ??????
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            return responseVo;
        } catch (Exception e) {
            logger.error("?????????????????????????????????????????????");
        }
        return null;
    }

    /**
     * ????????????ID????????????
     *
     * @param orderAuditDomain
     * @return List<OrderAuditVo>
     */
    @Override
    public List<DownloadOrderAuditVo> selectByDomain(OrderAuditDomain orderAuditDomain) {
        return orderAuditDao.selectByDomain(orderAuditDomain);
    }

    /**
     * ????????????????????????
     *
     * @param orderAuditDomain
     * @return BigDecimal
     */
    @Override
    public BigDecimal queryTotalData(OrderAuditDomain orderAuditDomain) {
        return orderAuditDao.queryTotalData(orderAuditDomain);
    }

    /**
     * ????????????????????????
     *
     * @param orderAudit
     * @param request
     * @param commodityIds
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    @Transactional
    public ResponseVo saveOrderAudit(OrderAudit orderAudit, HttpServletRequest request, String commodityIds) throws Exception {
        //???????????????????????????
        orderAuditCommodityService.delectByOrderId(orderAudit.getId());
        //????????????????????????
        String[] arr = commodityIds.substring(0, commodityIds.length() - 1).split(",");
        for (int i = 0; i < arr.length; i++) {
            OrderCommodityEditVo orderCommodityEditVo = commodityInfoService.selectExisCommodity(arr[i], orderAudit.getSdeviceId());
            if (null == orderCommodityEditVo) {
                logger.error("????????????????????????????????????????????????");
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????????????????????????????????????????");
              /*  throw new ServiceException("????????????????????????????????????????????????");*/
            }
            OrderAuditCommodity orderAuditCommodity = new OrderAuditCommodity();
            orderAuditCommodity.setSorderId(orderAudit.getId());
            orderAuditCommodity.setSorderCode(orderAudit.getSorderCode());
            orderAuditCommodity.setScommodityId(request.getParameter("scommodityId_" + arr[i]));
            orderAuditCommodity.setScommodityCode(request.getParameter("scommodityCode_" + arr[i]));
            orderAuditCommodity.setScommodityName(orderCommodityEditVo.getSname());
            orderAuditCommodity.setFcommodityPrice(orderCommodityEditVo.getFsalePrice());
            orderAuditCommodity.setFcostAmount(orderCommodityEditVo.getFcostAmount());
            orderAuditCommodity.setFtaxPoint(orderCommodityEditVo.getFtaxPoint());
            orderAuditCommodity.setForderCount(Integer.valueOf(request.getParameter("forderCount_" + arr[i])));
            orderAuditCommodity.setFcommodityAmount(new BigDecimal(request.getParameter("fcommodityAmount_" + arr[i])));
            orderAuditCommodity.setTaddTime(DateUtils.getCurrentDateTime());
            orderAuditCommodity.setTupdateTime(DateUtils.getCurrentDateTime());
            orderAuditCommodityService.insert(orderAuditCommodity);
        }
        this.updateBySelective(orderAudit);
        //????????????
        ResponseVo responseVo = this.payOrder(orderAudit.getId());


        return ResponseVo.getSuccessResponse();
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param orderCommodityDtoList
     * @return
     */
    private Map<String, Object> calcIncrementsAndDecrements(List<OrderCommodityDto> orderCommodityDtoList) {
        // ??????????????????
        Map<String, Object> map = new HashMap<>();
        List<CommodityDiffVo> subVoList = new ArrayList<CommodityDiffVo>(); // ?????????????????????
        if (CollectionUtils.isEmpty(orderCommodityDtoList)) {
            map.put("subVoList", subVoList);
            return map;
        }
        CommodityDiffVo commodityDiffVo = null;
        for (OrderCommodityDto orderCommodityDto : orderCommodityDtoList) {
            commodityDiffVo = new CommodityDiffVo();
            CommodityInfo commodityInfo = commodityInfoService.selectByPrimaryKey(orderCommodityDto.getScommodityId());
            commodityDiffVo.setScommodityId(orderCommodityDto.getScommodityId());
            commodityDiffVo.setScommodityCode(orderCommodityDto.getScommodityCode());
            commodityDiffVo.setScommodityImg(commodityInfo.getScommodityImg());
            commodityDiffVo.setScommodityName(commodityInfo.getSname());
            commodityDiffVo.setScommodityFullName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
            commodityDiffVo.setFcommodityPrice(orderCommodityDto.getFcommodityPrice());
            commodityDiffVo.setFcostPrice(orderCommodityDto.getFcostAmount());
            commodityDiffVo.setFtaxPoint(orderCommodityDto.getFtaxPoint());
            commodityDiffVo.setIstatus(commodityInfo.getIstatus());
            commodityDiffVo.setItype(20);
            commodityDiffVo.setSbrandId(commodityInfo.getSbrandId());
            commodityDiffVo.setSpackageUnit(commodityInfo.getSpackageUnit());
            commodityDiffVo.setSsmallCategoryId(commodityInfo.getSsmallCategoryId());
            commodityDiffVo.setSvrCode(commodityInfo.getSvrCode());
            commodityDiffVo.setNumber(orderCommodityDto.getForderCount());
            commodityDiffVo.setSshelfLife(String.valueOf(commodityInfo.getIshelfLife()));
            subVoList.add(commodityDiffVo);
        }
        map.put("subVoList", subVoList);
        return map;
    }

    /**
     * ????????????
     *
     * @param itype      ????????????
     * @param addVoList  ??????????????????
     * @param subVoList  ??????????????????
     * @param deviceInfo ????????????
     */
    private void stockChange(final Integer itype, final List<CommodityDiffVo> addVoList, final List<CommodityDiffVo> subVoList, final DeviceInfo deviceInfo, final String orderId) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("????????????????????????????????????????????????{}", itype);
                logger.info("????????????????????????????????????????????????{}", addVoList);
                logger.info("????????????????????????????????????????????????{}", subVoList);
                logger.info("????????????????????????????????????????????????{}", deviceInfo);
                try {
                    StockOperDto operDto = new StockOperDto();
                    operDto.setItype(itype);
                    if (itype == 20) {
                        operDto.setOrderIds(orderId);//????????????
                    } else if (itype == 30) {
                        operDto.setAuditOrderId(orderId);//????????????
                    }
                    operDto.setAddVoList(addVoList);
                    operDto.setSubVoList(subVoList);
                    operDto.setSdeviceId(deviceInfo.getId());
                    operDto.setSdeviceCode(deviceInfo.getScode());
                    operDto.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
                    operDto.setSreaderSerialNumber(deviceInfo.getSreaderSerialNumber());
                    operDto.setSmerchantId(deviceInfo.getSmerchantId());
                    operDto.setSmerchantCode(deviceInfo.getSmerchantCode());
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(StockServicesDefine.STOCK_OPERATE_SERVICE);// ????????????
                    // ??????????????????????????????????????????????????????????????????????????????
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                    });
                    invoke.setRequestObj(operDto); // post ??????
                    ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
                    logger.info("???????????????????????????{}", resVO);
                    if (resVO.isSuccess()) {
                        logger.info("?????????????????????{}", resVO);
                    }
                } catch (Exception e) {
                    logger.error("?????????????????????????????????{}", e);
                }
            }
        });
    }

}