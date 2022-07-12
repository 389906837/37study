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
     * 异常订单
     *
     * @param checkboxId 异常订单ID
     * @return ResponseVo
     */
    @Override
    public ResponseVo payOrder(String checkboxId) {

        OrderAudit orderAudit = this.selectByPrimaryKey(checkboxId);
        OrderCommDto orderCommDto = new OrderCommDto();
        OrderDto orderDto = new OrderDto();
        orderDto.setSmerchantId(orderAudit.getSmerchantId());//商户ID
        orderDto.setSmerchantCode(orderAudit.getSmerchantCode());//商户编号
        orderDto.setSmemberId(orderAudit.getSmemberId());//会员ID
        orderDto.setSmemberCode(orderAudit.getSmemberCode());//会员编号
        orderDto.setSmemberName(orderAudit.getSmemberName());//会员名
        int type = orderAudit.getIsourceClientType();// 10=微信  20=支付宝   30=APP
        orderDto.setIsourceClientType(type);//来源方式
        orderDto.setSreaderSerialNumber(orderAudit.getSreaderSerialNumber());//设备读写器序列号
        orderDto.setSdeviceId(orderAudit.getSdeviceId());//设备ID
        orderDto.setSdeviceCode(orderAudit.getSdeviceCode());//设备Code
        orderDto.setSdeviceAddress(orderAudit.getSdeviceAddress());//设备地址
        orderDto.setSdeviceName(orderAudit.getSdeviceName());//设备名
        OrderAuditCommodity orderAuditCommodity = new OrderAuditCommodity();
        orderAuditCommodity.setSorderId(checkboxId);
        List<OrderAuditCommodity> orderAuditCommodities = orderAuditCommodityService.selectByEntityWhere(orderAuditCommodity);
        List<OrderCommodityDto> orderCommodityDtoList = new ArrayList<>();
        for (OrderAuditCommodity orderAuditCommodity1 : orderAuditCommodities) {
            OrderCommodityDto orderCommodityDto = new OrderCommodityDto();
            CommodityInfo commodityInfo = commodityInfoService.selectByPrimaryKey(orderAuditCommodity1.getScommodityId());
            orderCommodityDto.setScommodityId(commodityInfo.getId());//商品ID
            orderCommodityDto.setScommodityCode(commodityInfo.getScode());//商品编号
            orderCommodityDto.setScommodityName(commodityInfo.getSname());//商品名
            orderCommodityDto.setFcommodityPrice(orderAuditCommodity1.getFcommodityPrice());//商品单价
            orderCommodityDto.setForderCount(orderAuditCommodity1.getForderCount());//商品数量
            orderCommodityDto.setFtaxPoint(orderAuditCommodity1.getFtaxPoint());//进价税点
            orderCommodityDto.setSsmallCategoryId(commodityInfo.getSsmallCategoryId()); /* 商品小类ID */
            orderCommodityDto.setSbrandId(commodityInfo.getSbrandId());/* 商品品牌ID */
            orderCommodityDto.setFcostAmount(commodityInfo.getFcostPrice());   /* 单商品成本价 */
            orderCommodityDtoList.add(orderCommodityDto);
        }
        orderCommDto.setComms(orderCommodityDtoList);
        orderCommDto.setOrderDto(orderDto);
        ResponseVo<GeneratingOrderResults> responseVo = null;
        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OrderDiscountDefine.CREATE_ORDER);
            invoke.setRequestObj(orderCommDto); // post 参数
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GeneratingOrderResults>>() {
            });
            responseVo = (ResponseVo<GeneratingOrderResults>) invoke.invoke();
            if (null != responseVo && responseVo.isSuccess() && null != responseVo.getData()) {
                logger.info("调用生成订单接口服务成功");
                //异常类型为40的话异步操作库存
                if (40 == orderAudit.getItype()) {
                    DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(orderAudit.getSdeviceId());
                    // 返回订单信息
                    GeneratingOrderResults orderResults = responseVo.getData();
                    List<CreatOrderResult> orderRecords = orderResults.getCreatOrderResultList();
                    //1、计算商品差
                    Map<String, Object> map = calcIncrementsAndDecrements(orderCommodityDtoList);
                    List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");
                    //操作库存
                    StringBuffer sb = new StringBuffer();
                    for (CreatOrderResult orderRecord : orderRecords) {
                        sb.append(orderRecord.getOrderRecord().getId() + ",");
                    }
                    //异步操作库存
                    stockChange(20, null, subVoList, deviceInfo, sb.toString().substring(0, sb.toString().length() - 1));
                }
            } else {
                logger.error("调用生成订单接口服务失败");
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("订单扣款失败！");
            }
        } catch (Exception e) {
            logger.error("创建订单服务失败", e);
           /* return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("订单扣款失败！");*/
            throw new ServiceException("创建订单服务失败！");
        }
        //修改异常订单状态
        orderAudit.setIstatus(20);
        orderAudit.setShandlerUserid(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        orderAudit.setShandlerUsername(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        orderAudit.setThandlerTime(DateUtils.getCurrentDateTime());
        orderAudit.setTupdateTime(DateUtils.getCurrentDateTime());
        this.updateBySelective(orderAudit);
        //操作日志
        String logText = MessageFormat.format("异常订单扣款成功,异常订单编号{0}", orderAudit.getSorderCode());
        LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
        //异步支付异常订单
        payErrorOrder(responseVo, type);

        return ResponseVo.getSuccessResponse("异常订单扣款成功！");
    }

    /**
     * 异常订单支付
     *
     * @param
     */
    private void payErrorOrder(final ResponseVo<GeneratingOrderResults> responseVo, final Integer type) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                //2.支付订单
                List<CreatOrderResult> creatOrderResultList = responseVo.getData().getCreatOrderResultList();
                String isSendMessage = BizParaUtil.get("order_audit_notify");
                for (CreatOrderResult creatOrderResult : creatOrderResultList) {
                    OrderRecord orderRecord = creatOrderResult.getOrderRecord();
                    //支付宝支付
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
                            invoke.setRequestObj(freePaymentDto); // post 参数
                            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {
                            });
                            ResponseVo<FreePaymentResult> payResponseVo = (ResponseVo<FreePaymentResult>) invoke.invoke();
                            if (null != payResponseVo && payResponseVo.isSuccess()) {
                                logger.info("异常订单扣款，调用支付宝免密支付接口服务成功");
                            } else {
                                logger.error("异常订单扣款，调用支付宝免密支付接口服务失败");
                            }
                            //未开通免密发送短信通知已生成订单
                            if ("1".equals(isSendMessage)) {
                                Map<String, Object> contentParam = new HashMap<String, Object>();
                                contentParam.put("address", orderRecord.getSdeviceAddress());
                                contentParam.put("time", DateUtils.dateParseString(orderRecord.getTorderTime()));
                                contentParam.put("money", orderRecord.getFactualPayAmount());
                                ResponseVo res = sendMessage(contentParam, orderRecord.getSmemberName(), "order_audit_pay_notify");
                                if (null != res && res.isSuccess()) {
                                    logger.info("异常订单生成订单代扣发送短信成功,订单ID：{}", orderRecord.getId());
                                }
                            }
                        } else if (10 == type && 10 == orderRecord.getIchargebackWay()) {
                            //微信支付
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
                            invoke.setRequestObj(freePaymentDto); // post 参数
                            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {
                            });
                            ResponseVo<FreePaymentResult> payResponseVo = (ResponseVo<FreePaymentResult>) invoke.invoke();
                            if (null != payResponseVo && payResponseVo.isSuccess()) {
                                logger.info("异常订单扣款，调用微信免密支付接口服务成功");
                            } else {
                                logger.error("异常订单扣款，调用微信免密支付接口服务失败");
                            }
                            //
                            //未开通免密发送短信通知已生成订单
                            if ("1".equals(isSendMessage)) {
                                Map<String, Object> contentParam = new HashMap<String, Object>();
                                contentParam.put("address", orderRecord.getSdeviceAddress());
                                contentParam.put("time", DateUtils.dateParseString(orderRecord.getTorderTime()));
                                contentParam.put("money", orderRecord.getFactualPayAmount());
                                ResponseVo res = sendMessage(contentParam, orderRecord.getSmemberName(), "order_audit_pay_notify");
                                if (null != res && res.isSuccess()) {
                                    logger.info("异常订单生成订单代扣发送短信成功,订单ID：{}", orderRecord.getId());
                                }
                            }
                        } else {
                            //未开通免密发送短信通知已生成订单
                            if ("1".equals(isSendMessage)) {
                                Map<String, Object> contentParam = new HashMap<String, Object>();
                                contentParam.put("address", orderRecord.getSdeviceAddress());
                                contentParam.put("time", DateUtils.dateParseString(orderRecord.getTorderTime()));
                                contentParam.put("money", orderRecord.getFactualPayAmount());
                                ResponseVo res = sendMessage(contentParam, orderRecord.getSmemberName(), "order_audit_notify");
                                if (null != res && res.isSuccess()) {
                                    logger.info("异常订单生成订单发送短信成功,订单ID：{}", orderRecord.getId());
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("异常订单免密支付异常", e);
                        throw new ServiceException("异常订单免密支付异常！");
                    }
                }
            }
        });
    }

    /**
     * 异常订单发送短信
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
            // 模板类型
            messageDto.setTemplateType(parm);
            //发送短信
            //内容
            messageDto.setContentParam(contentParam);
            messageDto.setMobile(mobile);
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);//服务名称
            invoke.setRequestObj(messageDto); //post 参数
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            return responseVo;
        } catch (Exception e) {
            logger.error("异常订单生成订单发送短信异常！");
        }
        return null;
    }

    /**
     * 根据订单ID查询订单
     *
     * @param orderAuditDomain
     * @return List<OrderAuditVo>
     */
    @Override
    public List<DownloadOrderAuditVo> selectByDomain(OrderAuditDomain orderAuditDomain) {
        return orderAuditDao.selectByDomain(orderAuditDomain);
    }

    /**
     * 异常订单页脚总和
     *
     * @param orderAuditDomain
     * @return BigDecimal
     */
    @Override
    public BigDecimal queryTotalData(OrderAuditDomain orderAuditDomain) {
        return orderAuditDao.queryTotalData(orderAuditDomain);
    }

    /**
     * 编辑保存审核订单
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
        //删除原有的订单从表
        orderAuditCommodityService.delectByOrderId(orderAudit.getId());
        //添加新的订单从表
        String[] arr = commodityIds.substring(0, commodityIds.length() - 1).split(",");
        for (int i = 0; i < arr.length; i++) {
            OrderCommodityEditVo orderCommodityEditVo = commodityInfoService.selectExisCommodity(arr[i], orderAudit.getSdeviceId());
            if (null == orderCommodityEditVo) {
                logger.error("设备里不存在该商品，请重新选择！");
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("设备里不存在该商品，请重新选择！");
              /*  throw new ServiceException("设备里不存在该商品，请重新选择！");*/
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
        //生成订单
        // ResponseVo responseVo = this.payOrder(orderAudit.getId());


        return ResponseVo.getSuccessResponse();
    }

    /**
     * 关门时根据商品差计算订单商品信息
     *
     * @param orderCommodityDtoList
     * @return
     */
    private Map<String, Object> calcIncrementsAndDecrements(List<OrderCommodityDto> orderCommodityDtoList) {
        // 根据差值计算
        Map<String, Object> map = new HashMap<>();
        List<CommodityDiffVo> subVoList = new ArrayList<CommodityDiffVo>(); // 增加的商品信息
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
     * 库存便跟
     *
     * @param itype      变更类型
     * @param addVoList  增加商品集合
     * @param subVoList  减少商品集合
     * @param deviceInfo 设备信息
     */
    private void stockChange(final Integer itype, final List<CommodityDiffVo> addVoList, final List<CommodityDiffVo> subVoList, final DeviceInfo deviceInfo, final String orderId) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("调用库存变更服务开始，类型参数：{}", itype);
                logger.info("调用库存变更服务开始，增加参数：{}", addVoList);
                logger.info("调用库存变更服务开始，减少参数：{}", subVoList);
                logger.info("调用库存变更服务开始，设备参数：{}", deviceInfo);
                try {
                    StockOperDto operDto = new StockOperDto();
                    operDto.setItype(itype);
                    if (itype == 20) {
                        operDto.setOrderIds(orderId);//订单信息
                    } else if (itype == 30) {
                        operDto.setAuditOrderId(orderId);//审核订单
                    }
                    operDto.setAddVoList(addVoList);
                    operDto.setSubVoList(subVoList);
                    operDto.setSdeviceId(deviceInfo.getId());
                    operDto.setSdeviceCode(deviceInfo.getScode());
                    operDto.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
                    operDto.setSreaderSerialNumber(deviceInfo.getSreaderSerialNumber());
                    operDto.setSmerchantId(deviceInfo.getSmerchantId());
                    operDto.setSmerchantCode(deviceInfo.getSmerchantCode());
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(StockServicesDefine.STOCK_OPERATE_SERVICE);// 服务名称
                    // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                    });
                    invoke.setRequestObj(operDto); // post 参数
                    ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
                    logger.info("库存变更响应参数：{}", resVO);
                    if (resVO.isSuccess()) {
                        logger.info("库存变更成功：{}", resVO);
                    }
                } catch (Exception e) {
                    logger.error("调用库存变更服务异常：{}", e);
                }
            }
        });
    }

}