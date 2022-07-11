package com.cloud.cang.bzc.sm.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cloud.cang.bzc.hy.service.MemberInfoService;
import com.cloud.cang.bzc.sb.dao.DeviceModelDao;
import com.cloud.cang.bzc.sb.service.DeviceInfoService;
import com.cloud.cang.bzc.sl.service.DeviceOperService;
import com.cloud.cang.bzc.sl.service.SystemAlarmService;
import com.cloud.cang.bzc.sm.dao.DeviceStockDao;
import com.cloud.cang.bzc.sm.service.DeviceStockService;
import com.cloud.cang.bzc.sm.vo.MultiCommodityMatch;
import com.cloud.cang.bzc.sm.vo.StockVo;
import com.cloud.cang.bzc.sp.service.CommodityInfoService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.common.utils.BigDecimalUtils;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.inventory.*;
import com.cloud.cang.jy.*;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceModel;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.model.sl.SystemAlarm;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.rm.*;
import com.cloud.cang.utils.StringUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeviceStockServiceImpl extends GenericServiceImpl<DeviceStock, String> implements
        DeviceStockService {

    @Autowired
    DeviceStockDao deviceStockDao;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private CommodityInfoService commodityInfoService;
    @Autowired
    private DeviceOperService deviceOperService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private SystemAlarmService systemAlarmService;
    @Autowired
    private DeviceModelDao deviceModelDao;
    @Autowired
    private ICached iCached;
    private static final Logger logger = LoggerFactory.getLogger(DeviceStockServiceImpl.class);

    @Override
    public GenericDao<DeviceStock, String> getDao() {
        return deviceStockDao;
    }

    @Override
    public int selectByMap(String sdeviceId, String commodityId) {
        return deviceStockDao.selectByMap(sdeviceId, commodityId);
    }

    @Override
    public int updateIstock(Map<String, Object> map) {
        return deviceStockDao.updateIstock(map);
    }


    /**
     * 处理底层盘点服务
     *
     * @param inventoryDto 盘点参数
     * @return Integer 10 创建购物订单成功 20 创建手动支付订单成功  30 补货成功 40 审核订单成功 50 处理成功
     */
    @Override
    public ResponseVo<InventoryResult> dealwithInventory(InventoryDto inventoryDto) throws Exception {
        ResponseVo<InventoryResult> responseVo = ResponseVo.getSuccessResponse();
        //验证参数数据
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(inventoryDto.getDeviceId());
        if (null == deviceInfo || deviceInfo.getIstatus().intValue() != 20 ||
                deviceInfo.getIoperateStatus().intValue() == 20) {
            logger.error("设备数据异常，设备ID：{}", inventoryDto.getDeviceId());
            logger.error("设备数据异常，设备数据：{}", deviceInfo);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，设备数据异常");
        }
        //关门盘点 会员不能为空
        if (inventoryDto.getInventoryType().intValue() == 10 && StringUtil.isBlank(inventoryDto.getMemberId())) {
            logger.error("底层盘点关门盘点会员数据异常：{}", inventoryDto);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }
        if (StringUtil.isBlank(inventoryDto.getMemberId())) {
            //获取最近开门会员信息
            DeviceOper deviceOper = deviceOperService.selectLastByDeviceId(deviceInfo.getScode());
            if (null == deviceOper) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
            }
            inventoryDto.setMemberId(deviceOper.getSmemberId());
            inventoryDto.setIsourceClientType(deviceOper.getIclientType());
        }
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(inventoryDto.getMemberId());
        if (null == memberInfo || !memberInfo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }
        //1、库存对比 计算商品差
        List<CommodityVo> commodityVoList = new ArrayList(Arrays.asList(new Object[inventoryDto.getCommodityVos().size()]));
        Collections.copy(commodityVoList, inventoryDto.getCommodityVos());
        Map<String, Object> map = this.calcCommodityDiffByType(inventoryDto.getCommodityVos(), deviceInfo, memberInfo);
        List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) map.get("addVoList");
        List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");
        //2、根据业务处理是否生成订单还是异常订单还是补货单
        if (inventoryDto.getInventoryType().intValue() == 10) {//关门盘点
            //盘点是否购物
            if (inventoryDto.getCloseType().intValue() == 10) {//购物关门
                if (null != addVoList && addVoList.size() > 0) {//新增商品
                    //新增报警日志
                    addSystemAlarmLog(memberInfo, inventoryDto.getSip(), addVoList);
                    //变更设备库存 不需要生成补货单 商品状态 已售商品
                    //stockChange(20,addVoList,subVoList,deviceInfo,"");
                }
                if (null != subVoList && subVoList.size() > 0) {
                    //生成购物订单
                    ResponseVo<GeneratingOrderResults> resVO = generateOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                    if (null != resVO) {
                        logger.info("调用订单生成服务返回数据：{}", JSON.toJSONString(resVO));
                        if (resVO.isSuccess()) {
                            GeneratingOrderResults orderResults = resVO.getData();
                            List<CreatOrderResult> orderRecords = orderResults.getCreatOrderResultList();
                            StringBuffer sb = new StringBuffer();
                            for (CreatOrderResult orderRecord : orderRecords) {
                                sb.append(orderRecord.getOrderRecord().getId() + ",");
                            }
                            //异步操作库存
                            stockChange(20, null, subVoList, deviceInfo, sb.toString().substring(0, sb.toString().length() - 1));
                            //返回盘点结果
                            InventoryResult result = new InventoryResult();
                            result.setMerchantCode(deviceInfo.getSmerchantCode());
                            result.setIsFirstOrder(orderResults.getIsFirstOrder());
                            OrderRecord orderRecord = orderRecords.get(0).getOrderRecord();
                            if (orderRecord.getIchargebackWay().intValue() == 10 || orderRecord.getIchargebackWay().intValue() == 40  || orderRecord.getIchargebackWay().intValue() == 30) {
                                result.setItype(10);
                            } else {
                                result.setItype(20);
                            }
                            result.setOrderRecords(orderRecords);
                            responseVo.setData(result);
                            return responseVo;
                        } else {
                            stockChange(20, null, subVoList, deviceInfo, "");
                            generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 30);
                            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("订单生成异常");
                        }
                    } else {
                        stockChange(20, null, subVoList, deviceInfo, "");
                        generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 30);
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单异常");
                    }
                }
            } else if (inventoryDto.getCloseType().intValue() == 20) {//补货员关门
                if ((null != addVoList && addVoList.size() > 0) || (null != subVoList && subVoList.size() > 0)) {
                    //库存变更服务 生成补货单 商品状态正常
                    MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + memberInfo.getSmerchantCode());
                    if (null == clientConf.getIisReplenConfirm() || clientConf.getIisReplenConfirm() == 0) {
                        ResponseVo<ReplenishmentResult> resVO = generateReplenishment(deviceInfo, memberInfo, subVoList, addVoList, inventoryDto);
                        if (null != resVO) {
                            logger.info("调用补货单生成服务返回数据：{}", JSON.toJSONString(resVO));
                            if (resVO.isSuccess()) {
                                InventoryResult result = new InventoryResult();
                                result.setMerchantCode(deviceInfo.getSmerchantCode());
                                result.setItype(30);
                                result.setReplenishmentResult(resVO.getData());
                                responseVo.setData(result);
                                return responseVo;
                            } else {
                                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("补货单生成异常，请重新补货");
                            }
                        } else {
                            throw new ServiceException("补货单生成异常");
                        }
                    } else if (clientConf.getIisReplenConfirm() == 1) {
                        ReplenishmentConfirmResult replenishmentConfirmResult = getReplenishmentConfirm(map, deviceInfo);
                        replenishmentConfirmResult.setDeviceCode(deviceInfo.getScode());
                        replenishmentConfirmResult.setDeviceId(deviceInfo.getId());
                        replenishmentConfirmResult.setUserId(memberInfo.getId());
                        Map<String, Object> map1 = new HashMap();
                        map1.put("visionMap", map);
                        map1.put("sourceClientType", inventoryDto.getIsourceClientType());
                        iCached.put("IISREPLENCONFIRM_" + deviceInfo.getScode(), JSON.toJSONString(map1), 60 * 60 * 2);
                        InventoryResult result = new InventoryResult();
                        result.setMerchantCode(deviceInfo.getSmerchantCode());
                        result.setReplenishmentConfirmResult(replenishmentConfirmResult);
                        result.setItype(70);
                        responseVo.setData(result);
                        return responseVo;
                    }
                }
            }
        } else if (inventoryDto.getInventoryType().intValue() == 20 || inventoryDto.getInventoryType().intValue() == 30) {//20 定时盘点 //30 主动盘点
            if (null != subVoList && subVoList.size() > 0) {
                //生成异常审核订单
                ResponseVo<String> resVO = generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 10);
                if (null != resVO) {
                    logger.info("调用审核订单生成服务返回数据：{}", JSON.toJSONString(resVO));
                    if (resVO.isSuccess()) {
                        InventoryResult result = new InventoryResult();
                        result.setMerchantCode(deviceInfo.getSmerchantCode());
                        result.setItype(40);
                        result.setAuditOrderId(resVO.getData());
                        responseVo.setData(result);
                        //异步变更库存
                        stockChange(30, addVoList, subVoList, deviceInfo, resVO.getData());
                        return responseVo;
                    } else {
                        //异步变更库存
                        stockChange(30, addVoList, subVoList, deviceInfo, "");
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("生成审核订单异常");
                    }
                } else {
                    //异步变更库存
                    stockChange(30, addVoList, subVoList, deviceInfo, "");
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建审核订单异常");
                }
            }
            if (null != addVoList && addVoList.size() > 0) {
                //异步变更库存
                stockChange(30, addVoList, null, deviceInfo, "");
            }
        }
        InventoryResult result = new InventoryResult();
        result.setMerchantCode(deviceInfo.getSmerchantCode());
        result.setItype(50);//处理成功
        responseVo.setData(result);
        return responseVo;
    }

    /**
     * 新增报警日志
     */
    private void addSystemAlarmLog(MemberInfo memberInfo, String sip, List<CommodityDiffVo> addVoList) {
        StringBuffer desc = new StringBuffer();
        SystemAlarm alarm = new SystemAlarm();
        alarm.setIisDealwith(0);
        alarm.setIproblemType(10);
        alarm.setIsystemType("cloud-cang-bzc");
        alarm.setItype(10);
        alarm.setSmemberCode(memberInfo.getScode());
        alarm.setSmemberId(memberInfo.getId());
        alarm.setSmemberName(memberInfo.getSmemberName());
        Date operDate = new Date();
        alarm.setTaddTime(operDate);
        alarm.setTalarmTime(operDate);
        alarm.setTupdateTime(operDate);
        alarm.setSoperIp(sip);
        for (CommodityDiffVo diffVo : addVoList) {
            desc.append("商品编号：" + diffVo.getScommodityCode() + ",商品名称：" + diffVo.getScommodityFullName() + ",上架数量：" + diffVo.getNumber() + "\r\n");
        }
        alarm.setSproblemDesc(desc.toString());
        systemAlarmService.insert(alarm);
    }

    /**
     * 新增报警日志
     */
    private void addSystemAlarmLog(MemberInfo memberInfo, String sip, List<CommodityDiffVo> addVoList, String msg) {
        StringBuffer desc = new StringBuffer();
        SystemAlarm alarm = new SystemAlarm();
        alarm.setIisDealwith(0);
        alarm.setIproblemType(10);
        alarm.setIsystemType("cloud-cang-bzc");
        alarm.setItype(10);
        alarm.setSmemberCode(memberInfo.getScode());
        alarm.setSmemberId(memberInfo.getId());
        alarm.setSmemberName(memberInfo.getSmemberName());
        Date operDate = new Date();
        alarm.setTaddTime(operDate);
        alarm.setTalarmTime(operDate);
        alarm.setTupdateTime(operDate);
        alarm.setSoperIp(sip);
        for (CommodityDiffVo diffVo : addVoList) {
            desc.append(msg + "商品编号：" + diffVo.getScommodityCode() + ",商品名称：" + diffVo.getScommodityFullName() + ",数量：" + diffVo.getNumber() + "\r\n");
        }
        alarm.setSproblemDesc(desc.toString());
        systemAlarmService.insert(alarm);
    }

    /**
     * 新增报警日志
     */
    private void addSystemAlarmLog(MemberInfo memberInfo, String sip, String msg) {
        StringBuffer desc = new StringBuffer();
        SystemAlarm alarm = new SystemAlarm();
        alarm.setIisDealwith(0);
        alarm.setIproblemType(10);
        alarm.setIsystemType("cloud-cang-bzc");
        alarm.setItype(10);
        alarm.setSmemberCode(memberInfo.getScode());
        alarm.setSmemberId(memberInfo.getId());
        alarm.setSmemberName(memberInfo.getSmemberName());
        Date operDate = new Date();
        alarm.setTaddTime(operDate);
        alarm.setTalarmTime(operDate);
        alarm.setTupdateTime(operDate);
        alarm.setSoperIp(sip);
        desc.append(msg);
        alarm.setSproblemDesc(desc.toString());
        systemAlarmService.insert(alarm);
    }


    /***
     * 生成购物订单
     * @param deviceInfo 设备信息
     * @param memberInfo 用户信息
     * @param subVoList 盘点商品减少信息
     * @param inventoryDto 盘点参数
     * @throws Exception
     */
    private ResponseVo<GeneratingOrderResults> generateOrder(DeviceInfo deviceInfo, MemberInfo memberInfo, List<CommodityDiffVo> subVoList, InventoryDto inventoryDto) {
        try {
            OrderCommDto commDto = new OrderCommDto();
            OrderDto orderDto = new OrderDto();
            orderDto.setIsourceClientType(inventoryDto.getIsourceClientType());
            orderDto.setSdeviceId(deviceInfo.getId());
            orderDto.setSdeviceCode(deviceInfo.getScode());
            orderDto.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
            orderDto.setSreaderSerialNumber(deviceInfo.getSreaderSerialNumber());
            orderDto.setSmerchantId(deviceInfo.getSmerchantId());
            orderDto.setSmerchantCode(deviceInfo.getSmerchantCode());
            orderDto.setSmemberCode(memberInfo.getScode());
            orderDto.setSmemberId(memberInfo.getId());
            orderDto.setSdeviceName(deviceInfo.getSname());
            orderDto.setSmemberName(memberInfo.getSmemberName());
            commDto.setOrderDto(orderDto);
            OrderCommodityDto orderCommodityDto = null;
            List<OrderCommodityDto> comms = new ArrayList<OrderCommodityDto>();
            for (CommodityDiffVo diffVo : subVoList) {
                orderCommodityDto = new OrderCommodityDto();
                BeanUtils.copyProperties(orderCommodityDto, diffVo);//复制对象
                orderCommodityDto.setForderCount(diffVo.getNumber());
                orderCommodityDto.setFcostAmount(diffVo.getFcostPrice());
                comms.add(orderCommodityDto);
            }
            commDto.setComms(comms);
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OrderDiscountDefine.CREATE_ORDER);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GeneratingOrderResults>>() {
            });
            invoke.setRequestObj(commDto); // post 参数
            ResponseVo<GeneratingOrderResults> resVO = (ResponseVo<GeneratingOrderResults>) invoke.invoke();
            return resVO;
        } catch (Exception e) {
            logger.error("订单生成异常：{}", e);
        }
        return null;
    }

    /**
     * 生成补货单
     *
     * @param deviceInfo   设备信息
     * @param memberInfo   补货员信息
     * @param subVoList    盘点商品减少信息
     * @param addVoList    盘点商品增加信息
     * @param inventoryDto 盘点参数
     */
    private ResponseVo<ReplenishmentResult> generateReplenishment(DeviceInfo deviceInfo, MemberInfo memberInfo, List<CommodityDiffVo> subVoList, List<CommodityDiffVo> addVoList, InventoryDto inventoryDto) {
        try {
            ReplenishmentDto replenishmentDto = new ReplenishmentDto();
            replenishmentDto.setIsourceClientType(inventoryDto.getIsourceClientType());
            replenishmentDto.setItype(10);
            replenishmentDto.setAddVoList(addVoList);
            replenishmentDto.setSubVoList(subVoList);
            replenishmentDto.setSdeviceId(deviceInfo.getId());
            replenishmentDto.setSdeviceCode(deviceInfo.getScode());
            replenishmentDto.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
            replenishmentDto.setSreaderSerialNumber(deviceInfo.getSreaderSerialNumber());
            replenishmentDto.setSmerchantId(deviceInfo.getSmerchantId());
            replenishmentDto.setSmerchantCode(deviceInfo.getSmerchantCode());
            replenishmentDto.setSmemberName(memberInfo.getSmemberName());
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(StockServicesDefine.GENERATE_REPLENISHMENT_SERVICE);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<ReplenishmentResult>>() {
            });
            invoke.setRequestObj(replenishmentDto); // post 参数
            ResponseVo<ReplenishmentResult> resVO = (ResponseVo<ReplenishmentResult>) invoke.invoke();
            return resVO;
        } catch (Exception e) {
            logger.error("补货单生成异常：{}", e);
        }
        return null;
    }

    /***
     * 生成异常审核订单
     * @param deviceInfo 设备信息
     * @param memberInfo 用户信息
     * @param subVoList 盘点商品减少信息
     * @param inventoryDto 盘点参数
     * @throws Exception
     */
    private ResponseVo<String> generateExceptionOrder(DeviceInfo deviceInfo, MemberInfo memberInfo, List<CommodityDiffVo> subVoList, InventoryDto inventoryDto, Integer itype) {
        try {
            //生成异常审核订单
            ExceptionOrderDto exceptionOrderDto = new ExceptionOrderDto();
            exceptionOrderDto.setSdeviceId(deviceInfo.getId());
            exceptionOrderDto.setSdeviceCode(deviceInfo.getScode());
            exceptionOrderDto.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
            exceptionOrderDto.setSreaderSerialNumber(deviceInfo.getSreaderSerialNumber());
            exceptionOrderDto.setSmerchantId(deviceInfo.getSmerchantId());
            exceptionOrderDto.setSmerchantCode(deviceInfo.getSmerchantCode());
            exceptionOrderDto.setSmemberCode(memberInfo.getScode());
            exceptionOrderDto.setSmemberId(memberInfo.getId());
            exceptionOrderDto.setSmemberName(memberInfo.getSmemberName());
            exceptionOrderDto.setSdeviceName(deviceInfo.getSname());
            exceptionOrderDto.setIsourceClientType(inventoryDto.getIsourceClientType());
            exceptionOrderDto.setItype(itype);//盘点异常
            exceptionOrderDto.setSext(inventoryDto.getSext());//扩展字段


            BigDecimal allAmount = new BigDecimal("0");
            List<ExceptionOrderCommodityDto> commodityDtos = new ArrayList<ExceptionOrderCommodityDto>();
            ExceptionOrderCommodityDto commodityDto = null;
            for (CommodityDiffVo diffVo : subVoList) {
                commodityDto = new ExceptionOrderCommodityDto();
                BeanUtils.copyProperties(commodityDto, diffVo);//复制对象

                commodityDto.setForderCount(diffVo.getNumber());
                commodityDto.setFcommodityAmount(commodityDto.getFcommodityPrice().multiply(new BigDecimal(commodityDto.getForderCount())));
                allAmount = allAmount.add(commodityDto.getFcommodityAmount());
                commodityDtos.add(commodityDto);
            }
            exceptionOrderDto.setExceptionOrderCommodityDtoList(commodityDtos);
            exceptionOrderDto.setFtotalAmount(allAmount);
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OrderDiscountDefine.CREATE_EXCEPTION_ORDER);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            invoke.setRequestObj(exceptionOrderDto); // post 参数
            ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
            return resVO;
        } catch (Exception e) {
            logger.error("异常订单生成异常：{}", e);
        }
        return null;
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

    /**
     * 组装视觉商品差
     *
     * @param stockVo     商品库存数据
     * @param commodityVo 商品信息
     * @param deviceInfo  设备信息
     */
    private CommodityDiffVo assemblyVision(StockVo stockVo, CommodityVo commodityVo, DeviceInfo deviceInfo) {
        CommodityDiffVo tempDiffVo = new CommodityDiffVo();
        //CommodityInfo commodityInfo = commodityInfoService.selectByVrCode(commodityVo.getSvrCode(),deviceInfo.getSmerchantId());
        if (!stockVo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return null;
        }
        tempDiffVo.setScommodityFullName(stockVo.getSbrandName() + stockVo.getSname() + stockVo.getStaste() + stockVo.getIspecWeight() + stockVo.getSspecUnit() + "/" + stockVo.getSpackageUnit());
        tempDiffVo.setSvrCode(commodityVo.getSvrCode());
        tempDiffVo.setScommodityCode(stockVo.getScode());
        tempDiffVo.setScommodityId(stockVo.getId());
        tempDiffVo.setScommodityName(stockVo.getSname());
        tempDiffVo.setFcostPrice(stockVo.getFcostPrice());
        tempDiffVo.setIstatus(stockVo.getIstatus());
        tempDiffVo.setSpackageUnit(stockVo.getSpackageUnit());
        tempDiffVo.setSbrandId(stockVo.getSbrandId());
        tempDiffVo.setSsmallCategoryId(stockVo.getSsmallCategoryId());
        tempDiffVo.setScommodityImg(stockVo.getScommodityImg());
        tempDiffVo.setIweigth(stockVo.getIweigth());
        tempDiffVo.setIcommodityFloat(stockVo.getIcommodityFloat());
        if (null != stockVo.getIlifeType() && stockVo.getIlifeType().intValue() == 10) {
            tempDiffVo.setSshelfLife(stockVo.getIshelfLife() + "天");
        } else if (null != stockVo.getIlifeType() && stockVo.getIlifeType().intValue() == 20) {
            tempDiffVo.setSshelfLife(stockVo.getIshelfLife() + "月");
        }
        Integer stockTemp = stockVo.getIstock();
        if (null == stockTemp || commodityVo.getCommodityNum() > stockTemp.intValue()) {
            stockTemp = (null == stockTemp) ? 0 : stockTemp;
            tempDiffVo.setItype(10);
            tempDiffVo.setNumber(commodityVo.getCommodityNum() - stockTemp);
            tempDiffVo.setFcommodityPrice(stockVo.getFsalePrice());
            tempDiffVo.setCurrStock(commodityVo.getCommodityNum());
        } else if (commodityVo.getCommodityNum() < stockTemp) {
            tempDiffVo.setItype(20);
            tempDiffVo.setNumber(stockTemp - commodityVo.getCommodityNum());
            //获取商品库存销售价格
            DeviceStock deviceStock = this.selectDeviceStockByCommodityId(deviceInfo.getId(), stockVo.getId());
            tempDiffVo.setFcommodityPrice(deviceStock.getFsalePrice());
            //tempDiffVo.setFtaxPoint(deviceStock.getFsalePrice());//税点
            tempDiffVo.setCurrStock(commodityVo.getCommodityNum());
        }
        return tempDiffVo;
    }

    /**
     * 组装视觉商品差
     *
     * @param stockTemp   商品库存
     * @param commodityVo 商品信息
     * @param deviceInfo  设备信息
     */
    private CommodityDiffVo assemblyVision(Integer stockTemp, CommodityVo commodityVo, DeviceInfo deviceInfo) {
        CommodityDiffVo tempDiffVo = new CommodityDiffVo();
        tempDiffVo.setSvrCode(commodityVo.getSvrCode());
        CommodityInfo commodityInfo = commodityInfoService.selectByVrCode(commodityVo.getSvrCode(), deviceInfo.getSmerchantId());
        if (null != commodityInfo) {
            tempDiffVo.setScommodityCode(commodityInfo.getScode());
            tempDiffVo.setScommodityId(commodityInfo.getId());
            tempDiffVo.setScommodityFullName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
            tempDiffVo.setScommodityName(commodityInfo.getSname());
            tempDiffVo.setFcostPrice(commodityInfo.getFcostPrice());
            tempDiffVo.setIstatus(commodityInfo.getIstatus());
            tempDiffVo.setSpackageUnit(commodityInfo.getSpackageUnit());
            tempDiffVo.setSbrandId(commodityInfo.getSbrandId());
            tempDiffVo.setSsmallCategoryId(commodityInfo.getSsmallCategoryId());
            tempDiffVo.setScommodityImg(commodityInfo.getScommodityImg());
            tempDiffVo.setIweigth(commodityInfo.getIweigth());
            tempDiffVo.setIcommodityFloat(commodityInfo.getIcommodityFloat());
            if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 10) {
                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "天");
            } else if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 20) {
                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "个月");
            }
        } else {
            logger.error("视觉盘点商品不存在，商户编号-视觉编号：{}", deviceInfo.getSmerchantCode() + "-" + commodityVo.getSvrCode());
            return null;
        }
        if (null == stockTemp || commodityVo.getCommodityNum() > stockTemp.intValue()) {
            stockTemp = (null == stockTemp) ? 0 : stockTemp;
            tempDiffVo.setItype(10);
            tempDiffVo.setNumber(commodityVo.getCommodityNum() - stockTemp);
            tempDiffVo.setFcommodityPrice(commodityInfo.getFsalePrice());
            tempDiffVo.setCurrStock(commodityVo.getCommodityNum());
        } else if (commodityVo.getCommodityNum() < stockTemp) {
            tempDiffVo.setItype(20);
            tempDiffVo.setNumber(stockTemp - commodityVo.getCommodityNum());
            //获取商品库存销售价格
            DeviceStock deviceStock = this.selectDeviceStockByCommodityId(deviceInfo.getId(), commodityInfo.getId());
            tempDiffVo.setFcommodityPrice(deviceStock.getFsalePrice());
            //tempDiffVo.setFtaxPoint(deviceStock.get);//税点
            tempDiffVo.setCurrStock(commodityVo.getCommodityNum());
        }
        return tempDiffVo;
    }

    /**
     * 查询设备商品编号库存明细
     *
     * @param deviceId 设备ID
     */
    @Override
    public List<StockVo> selectStockByDeviceId(String deviceId) {
        return deviceStockDao.selectStockByDeviceId(deviceId);
    }

    /**
     * 查询设备商品编号库存明细(重力识别柜)
     *
     * @param deviceId 设备ID
     */
    public List<StockVo> selectGravityStockByDeviceId(String deviceId, BigDecimal diffData) {
        Map<String, Object> map = new HashMap();
        map.put("deviceId", deviceId);
        map.put("diffData", diffData);
        return deviceStockDao.selectGravityStockByDeviceId(map);
    }

    /**
     * 查询匹配最近几个月内设备售出的最小重量的商品
     *
     * @param deviceId 设备ID
     */
    public List<StockVo> selectSellGoodsByDeviceId(String deviceId, BigDecimal diffData, Integer month) {
        Map<String, Object> map = new HashMap();
        map.put("deviceId", deviceId);
        map.put("diffData", diffData);
        map.put("month", month);
        return deviceStockDao.selectSellGoodsByDeviceId(map);
    }

    /***
     * 查询设备库存
     * @param deviceId 设备ID
     * @param commodityId 商品ID
     */
    @Override
    public DeviceStock selectDeviceStockByCommodityId(String deviceId, String commodityId) {
        return deviceStockDao.selectDeviceStockByCommodityId(deviceId, commodityId);
    }

    /**
     * 计算设备库存商品差
     *
     * @param commodityVos 盘点商品集合
     * @param deviceInfo   设备数据
     * @param memberInfo   会员数据
     */
    @Override
    public Map<String, Object> calcCommodityDiffByType(List<CommodityVo> commodityVos, DeviceInfo deviceInfo, MemberInfo memberInfo) {

        Map<String, Object> map = new HashMap<String, Object>();
        //查询对应设备的库存
        List<StockVo> stocks = null;
        List<CommodityDiffVo> subVoList = new ArrayList<CommodityDiffVo>();
        List<CommodityDiffVo> addVoList = new ArrayList<CommodityDiffVo>();
        CommodityDiffVo tempDiffVo = null;
        Integer stockUnchangedNum = 0;
        Integer stockTemp = null;
        if (deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.VISION.getCode()
                || deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.CLOUD_VISION.getCode()
                || deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.CLOUD_ZL_VISION.getCode()
                || deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.QD_ZL_VISION.getCode()) {//视觉设备盘点
            stocks = this.selectStockByDeviceId(deviceInfo.getId());
            if (null != stocks && stocks.size() > 0) {//数据库库存不为0
                List<CommodityVo> tempDiff = new ArrayList<CommodityVo>();
                if (null != commodityVos && commodityVos.size() > 0) {//盘点设备库存不为0 商品可能上架 出售 下架 已售
                    CommodityVo tempVo = null;
                    for (StockVo stockVo : stocks) {//循环系统库存商品
                        tempVo = null;
                        for (CommodityVo commodityVo : commodityVos) {//循环设备库存
                            if (commodityVo.getSvrCode().equals(stockVo.getSvrCode())) {//判断商品否相同
                                tempVo = commodityVo;
                                tempDiff.add(commodityVo);
                                break;
                            }
                        }
                        if (null != tempVo) {//系统库存和设备库存有相同的商品
                            stockTemp = stockVo.getIstock();
                            if (null != stockTemp && stockTemp.intValue() == tempVo.getCommodityNum().intValue()) {
                                //库存不变
                                stockUnchangedNum += stockTemp.intValue();
                                continue;
                            }
                            tempDiffVo = assemblyVision(stockTemp, tempVo, deviceInfo);
                            if (null == tempDiffVo) {
                                continue;
                            }
                            if (tempDiffVo.getItype().intValue() == 10) {
                                addVoList.add(tempDiffVo);
                            } else {
                                subVoList.add(tempDiffVo);
                            }
                        } else {//盘点后设备单个商品库存为0 商品下架或已出售
                            if (stockVo.getIstock() > 0) {//库存大于0 说明销售
                                subCommodity(subVoList, stockVo, deviceInfo);
                            }
                        }
                    }
                    //计算是否有新增商品  系统库存没有  设备库存存在
                    commodityVos.removeAll(tempDiff);
                    if (commodityVos.size() > 0) {
                        addVoList = addCommodity(addVoList, commodityVos, deviceInfo);
                    }
                } else {//盘点后设备商品库存都为0  所有商品下架或已出售
                    subVoList = subCommodity(subVoList, stocks, deviceInfo);
                }
            } else {//数据库 系统库存为0
                if (null != commodityVos && commodityVos.size() > 0) {//盘点后设备商品库存不为0  所有商品都是新增
                    //所有商品都是新增
                    addVoList = addCommodity(addVoList, commodityVos, deviceInfo);
                }
            }
        }
        map.put("subVoList", subVoList);
        map.put("addVoList", addVoList);
        map.put("stockUnchangedNum", stockUnchangedNum);
        return map;
    }

    /**
     * 计算设备库存商品差
     *
     * @param map        盘点商品集合
     * @param deviceInfo 设备数据
     */
/*    private ReplenishmentConfirmResult getReplenishmentConfirm(List<CommodityVo> commodityVos, DeviceInfo deviceInfo) {*/
    private ReplenishmentConfirmResult getReplenishmentConfirm(Map<String, Object> map, DeviceInfo deviceInfo) {
      /*  ReplenishmentConfirmResult replenishmentConfirmResult = new ReplenishmentConfirmResult();
        ReplenishmentConfirmCommodity replenishmentConfirmCommodity = null;
        List<ReplenishmentConfirmCommodity> replenishmentConfirmCommodities = new ArrayList<>();
        List<CommodityDiffVo> subVoList = new ArrayList<CommodityDiffVo>();
        List<CommodityDiffVo> addVoList = new ArrayList<CommodityDiffVo>();
        Integer upShelfTotal = 0;
        Integer lowShelfTotal = 0;
        Integer currentTotal = 0;
        //查询对应设备的库存
        List<StockVo> stocks = null;
        CommodityDiffVo tempDiffVo = null;
        Integer stockTemp = null;
        if (deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.VISION.getCode()
                || deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.CLOUD_VISION.getCode()
                || deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.CLOUD_ZL_VISION.getCode()
                || deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.QD_ZL_VISION.getCode()) {//视觉设备盘点
            stocks = this.selectStockByDeviceId(deviceInfo.getId());
            if (null != stocks && stocks.size() > 0) {//数据库库存不为0
                List<CommodityVo> tempDiff = new ArrayList<CommodityVo>();
                if (null != commodityVos && commodityVos.size() > 0) {//盘点设备库存不为0 商品可能上架 出售 下架 已售
                    CommodityVo tempVo = null;
                    for (StockVo stockVo : stocks) {//循环系统库存商品
                        replenishmentConfirmCommodity = new ReplenishmentConfirmCommodity();
                        tempVo = null;
                        for (CommodityVo commodityVo : commodityVos) {//循环设备库存
                            if (commodityVo.getSvrCode().equals(stockVo.getSvrCode())) {//判断商品否相同
                                tempVo = commodityVo;
                                tempDiff.add(commodityVo);
                                break;
                            }
                        }
                        if (null != tempVo) {//系统库存和设备库存有相同的商品
                            stockTemp = stockVo.getIstock();
                            if (null != stockTemp && stockTemp.intValue() == tempVo.getCommodityNum().intValue()) {
                                //库存不变
                                replenishmentConfirmCommodity.setNum(0);
                                replenishmentConfirmCommodity.setType(30);
                                replenishmentConfirmCommodity.setCurrentNum(stockTemp.intValue());
                                replenishmentConfirmCommodity.setScommodityFullName(stockVo.getSbrandName() + stockVo.getSname() + stockVo.getStaste() + stockVo.getIspecWeight() + stockVo.getSspecUnit() + "/" + stockVo.getSpackageUnit());
                                replenishmentConfirmCommodities.add(replenishmentConfirmCommodity);
                                continue;
                            }
                            tempDiffVo = assemblyVision(stockTemp, tempVo, deviceInfo);
                            if (null == tempDiffVo) {
                                continue;
                            }
                            if (tempDiffVo.getItype().intValue() == 10) {  //10 增加  20 减少
                                replenishmentConfirmCommodity.setNum(tempDiffVo.getNumber());
                                replenishmentConfirmCommodity.setType(10);
                                replenishmentConfirmCommodity.setCurrentNum(stockTemp.intValue() + tempDiffVo.getNumber());
                                replenishmentConfirmCommodity.setScommodityFullName(tempDiffVo.getScommodityFullName());
                                replenishmentConfirmCommodities.add(replenishmentConfirmCommodity);
                                upShelfTotal += tempDiffVo.getNumber();
                                currentTotal += stockTemp.intValue() + tempDiffVo.getNumber();
                                addVoList.add(tempDiffVo);
                            } else {
                                replenishmentConfirmCommodity.setNum(tempDiffVo.getNumber());
                                replenishmentConfirmCommodity.setType(20);
                                replenishmentConfirmCommodity.setCurrentNum(stockTemp.intValue() - tempDiffVo.getNumber());
                                replenishmentConfirmCommodity.setScommodityFullName(tempDiffVo.getScommodityFullName());
                                replenishmentConfirmCommodities.add(replenishmentConfirmCommodity);
                                lowShelfTotal += tempDiffVo.getNumber();
                                currentTotal += stockTemp.intValue() - tempDiffVo.getNumber();
                                subVoList.add(tempDiffVo);
                            }
                        } else {//盘点后设备单个商品库存为0 商品下架或已出售
                            if (stockVo.getIstock() > 0) {//库存大于0 说明销售
                                // subCommodity(subVoList, stockVo, deviceInfo);
                                subCommodityConfirm(stocks, deviceInfo, replenishmentConfirmCommodities, lowShelfTotal, currentTotal);
                            }
                        }
                    }
                    //计算是否有新增商品  系统库存没有  设备库存存在
                    commodityVos.removeAll(tempDiff);
                    if (commodityVos.size() > 0) {
                        //  addVoList = addCommodity(addVoList, commodityVos, deviceInfo);
                        addCommodityConfirm(commodityVos, deviceInfo, replenishmentConfirmCommodities, upShelfTotal, currentTotal);
                    }
                } else {//盘点后设备商品库存都为0  所有商品下架或已出售
                    subCommodityConfirm(stocks, deviceInfo, replenishmentConfirmCommodities, lowShelfTotal, currentTotal);
                }
            } else {//数据库 系统库存为0
                if (null != commodityVos && commodityVos.size() > 0) {//盘点后设备商品库存不为0  所有商品都是新增
                    //所有商品都是新增
                    //addVoList = addCommodity(addVoList, commodityVos, deviceInfo);
                    addCommodityConfirm(commodityVos, deviceInfo, replenishmentConfirmCommodities, upShelfTotal, currentTotal);
                }
            }
        }
        replenishmentConfirmResult.setCurrentTotal(currentTotal);
        replenishmentConfirmResult.setLowShelfTotal(lowShelfTotal);
        replenishmentConfirmResult.setCurrentTotal(currentTotal);
        replenishmentConfirmResult.setReplenishmentConfirmCommodityList(replenishmentConfirmCommodities);*/
        ReplenishmentConfirmResult replenishmentConfirmResult = new ReplenishmentConfirmResult();
        ReplenishmentConfirmCommodity replenishmentConfirmCommodity = null;
        List<ReplenishmentConfirmCommodity> replenishmentConfirmCommodities = new ArrayList<>();
        Integer upShelfTotal = 0;
        Integer lowShelfTotal = 0;
        Integer currentTotal = 0;
        List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) map.get("addVoList");
        List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");
        if (addVoList != null && !addVoList.isEmpty()) {
            for (CommodityDiffVo commodityDiffVo : addVoList) {
                replenishmentConfirmCommodity = new ReplenishmentConfirmCommodity();
                replenishmentConfirmCommodity.setType(10);
                replenishmentConfirmCommodity.setScommodityFullName(commodityDiffVo.getScommodityFullName());
                replenishmentConfirmCommodity.setCurrentNum(commodityDiffVo.getCurrStock());
                replenishmentConfirmCommodity.setNum(commodityDiffVo.getNumber());
                upShelfTotal += commodityDiffVo.getNumber();
                currentTotal += commodityDiffVo.getCurrStock();
                replenishmentConfirmCommodities.add(replenishmentConfirmCommodity);
            }
        }
        if (subVoList != null && !subVoList.isEmpty()) {
            for (CommodityDiffVo commodityDiffVo : subVoList) {
                replenishmentConfirmCommodity = new ReplenishmentConfirmCommodity();
                replenishmentConfirmCommodity.setType(20);
                replenishmentConfirmCommodity.setScommodityFullName(commodityDiffVo.getScommodityFullName());
                replenishmentConfirmCommodity.setCurrentNum(commodityDiffVo.getCurrStock());
                replenishmentConfirmCommodity.setNum(commodityDiffVo.getNumber());
                lowShelfTotal += commodityDiffVo.getNumber();
                currentTotal += commodityDiffVo.getCurrStock();
                replenishmentConfirmCommodities.add(replenishmentConfirmCommodity);
            }
        }
        Integer stockUnchangedNum = (Integer) map.get("stockUnchangedNum");
        replenishmentConfirmResult.setCurrentTotal(currentTotal);
        replenishmentConfirmResult.setLowShelfTotal(lowShelfTotal);
        replenishmentConfirmResult.setUpShelfTotal(upShelfTotal);
        replenishmentConfirmResult.setCurrentTotal(currentTotal + stockUnchangedNum);
        replenishmentConfirmResult.setReplenishmentConfirmCommodityList(replenishmentConfirmCommodities);
        return replenishmentConfirmResult;
    }


    /**
     * 减少商品
     *
     * @param subVoList  减少商品集合
     * @param stocks     需要减少的商品集合
     * @param deviceInfo 设备信息
     * @return
     */
    private List<CommodityDiffVo> subCommodity(List<CommodityDiffVo> subVoList, List<StockVo> stocks, DeviceInfo deviceInfo) {
        CommodityVo commodityVoTemp = null;
        CommodityDiffVo tempDiffVo = null;
        for (StockVo stockVo : stocks) {
            if (stockVo.getIstock() > 0) {
                commodityVoTemp = new CommodityVo();
                commodityVoTemp.setCommodityNum(0);
                commodityVoTemp.setSvrCode(stockVo.getSvrCode());
                tempDiffVo = assemblyVision(stockVo, commodityVoTemp, deviceInfo);
                if (null == tempDiffVo) {
                    continue;
                }
                subVoList.add(tempDiffVo);
            }
        }
        return subVoList;
    }

    /**
     * 减少商品
     *
     * @param
     * @param stocks     需要减少的商品集合
     * @param deviceInfo 设备信息
     * @return
     */
    private List<ReplenishmentConfirmCommodity> subCommodityConfirm(List<StockVo> stocks,
                                                                    DeviceInfo deviceInfo,
                                                                    List<ReplenishmentConfirmCommodity> replenishmentConfirmCommodities,
                                                                    Integer lowShelfTotal, Integer currentTotal) {
        CommodityVo commodityVoTemp = null;
        CommodityDiffVo tempDiffVo = null;
        ReplenishmentConfirmCommodity replenishmentConfirmCommodity = null;
        for (StockVo stockVo : stocks) {
            if (stockVo.getIstock() > 0) {
                commodityVoTemp = new CommodityVo();
                commodityVoTemp.setCommodityNum(0);
                commodityVoTemp.setSvrCode(stockVo.getSvrCode());
                tempDiffVo = assemblyVision(stockVo, commodityVoTemp, deviceInfo);
                if (null == tempDiffVo) {
                    continue;
                }
                replenishmentConfirmCommodity = new ReplenishmentConfirmCommodity();
                replenishmentConfirmCommodity.setNum(tempDiffVo.getNumber());
                replenishmentConfirmCommodity.setType(20);
                replenishmentConfirmCommodity.setCurrentNum(0);
                replenishmentConfirmCommodity.setScommodityFullName(tempDiffVo.getScommodityFullName());
                replenishmentConfirmCommodities.add(replenishmentConfirmCommodity);
                lowShelfTotal += tempDiffVo.getNumber();
                currentTotal += 0;
            }
        }
        return replenishmentConfirmCommodities;
    }


    /**
     * 减少商品
     *
     * @param subVoList  减少商品集合
     * @param stockVo    需要减少的商品信息
     * @param deviceInfo 设备信息
     * @return
     */
    private List<CommodityDiffVo> subCommodity(List<CommodityDiffVo> subVoList, StockVo stockVo, DeviceInfo deviceInfo) {
        CommodityVo commodityVoTemp = new CommodityVo();
        commodityVoTemp.setCommodityNum(0);
        commodityVoTemp.setSvrCode(stockVo.getSvrCode());
        CommodityDiffVo tempDiffVo = assemblyVision(stockVo, commodityVoTemp, deviceInfo);
        if (null != tempDiffVo) {
            tempDiffVo.setItype(20);
            subVoList.add(tempDiffVo);
        }
        return subVoList;
    }

    /**
     * 新增商品
     *
     * @param addVoList    新增商品集合
     * @param commodityVos 需要新增的商品集合
     * @param deviceInfo   设备信息
     * @return
     */
    private List<CommodityDiffVo> addCommodity(List<CommodityDiffVo> addVoList, List<CommodityVo> commodityVos, DeviceInfo deviceInfo) {
        for (CommodityVo commodityVo : commodityVos) {
            //新增已售库存
            CommodityDiffVo tempDiffVo = assemblyVision(0, commodityVo, deviceInfo);
            if (null == tempDiffVo) {
                continue;
            }
            tempDiffVo.setItype(10);
            addVoList.add(tempDiffVo);
        }
        return addVoList;
    }

    /**
     * 新增商品
     *
     * @param
     * @param commodityVos 需要新增的商品集合
     * @param deviceInfo   设备信息
     * @return
     */
    private List<ReplenishmentConfirmCommodity> addCommodityConfirm(
            List<CommodityVo> commodityVos,
            DeviceInfo deviceInfo, List<ReplenishmentConfirmCommodity> replenishmentConfirmCommodities,
            Integer upShelfTotal, Integer currentTotal) {
        ReplenishmentConfirmCommodity replenishmentConfirmCommodity = null;
        for (CommodityVo commodityVo : commodityVos) {
            //新增已售库存
            CommodityDiffVo tempDiffVo = assemblyVision(0, commodityVo, deviceInfo);
            if (null == tempDiffVo) {
                continue;
            }
            tempDiffVo.setItype(10);
            replenishmentConfirmCommodity = new ReplenishmentConfirmCommodity();
            replenishmentConfirmCommodity.setNum(tempDiffVo.getNumber());
            replenishmentConfirmCommodity.setType(10);
            replenishmentConfirmCommodity.setCurrentNum(tempDiffVo.getNumber());
            replenishmentConfirmCommodity.setScommodityFullName(tempDiffVo.getScommodityFullName());
            replenishmentConfirmCommodities.add(replenishmentConfirmCommodity);
            upShelfTotal += tempDiffVo.getNumber();
            currentTotal += tempDiffVo.getNumber();
        }
        return replenishmentConfirmCommodities;
    }

    /**
     * 根据商品数量差，重力差
     * 生成订单
     *
     * @param inventoryDiffDto
     * @return
     */
    @Override
    public ResponseVo<InventoryDiffResult> dealwithCommodityDiff(InventoryDiffDto inventoryDiffDto) {
        ResponseVo<InventoryDiffResult> responseVo = ResponseVo.getSuccessResponse();
        //验证参数数据
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(inventoryDiffDto.getDeviceId());
        if (null == deviceInfo || deviceInfo.getIstatus().intValue() != 20 ||
                deviceInfo.getIoperateStatus().intValue() == 20) {
            logger.error("设备数据异常，设备ID：{}", inventoryDiffDto.getDeviceId());
            logger.error("设备数据异常，设备数据：{}", deviceInfo);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，设备数据异常");
        }
        //关门盘点 会员不能为空
        if (inventoryDiffDto.getInventoryType().intValue() == 10 && StringUtil.isBlank(inventoryDiffDto.getMemberId())) {
            logger.error("底层盘点关门盘点会员数据异常：{}", inventoryDiffDto);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }
        if (StringUtil.isBlank(inventoryDiffDto.getMemberId())) {
            //获取最近开门会员信息
            DeviceOper deviceOper = deviceOperService.selectLastByDeviceId(deviceInfo.getScode());
            if (null == deviceOper) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
            }
            inventoryDiffDto.setMemberId(deviceOper.getSmemberId());
            inventoryDiffDto.setIsourceClientType(deviceOper.getIclientType());
        }
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(inventoryDiffDto.getMemberId());
        if (null == memberInfo || !memberInfo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }
        //1、计算商品差
        Map<String, Object> map = calcIncrementsAndDecrements(inventoryDiffDto.getInventoryCommodityDiffVoList(), deviceInfo, memberInfo);
        List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) map.get("addVoList");
        List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");
        List<CommodityVo> commodityVos = (List<CommodityVo>) map.get("commodityVos");       // 设备当前库存


        // 1.1计算重力差值，如果安卓端传输重力值为空集合，则不进行重力差值比较
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", true);
        if (CollectionUtils.isNotEmpty(inventoryDiffDto.getLayerGravityVoList())) {
            Map<String, BigDecimal> weightMap = calculateCommodityWeight(inventoryDiffDto.getInventoryCommodityDiffVoList(), deviceInfo.getId());
            resultMap = compareWeightDiff(inventoryDiffDto.getLayerGravityVoList(), weightMap, inventoryDiffDto.getCloseType());
        }
        boolean compareResult = (boolean) resultMap.get("result");

        //2、根据业务处理是否生成订单还是异常点单还是补货单
        int inventoryType = inventoryDiffDto.getInventoryType().intValue(); // 盘点类型 10 关门盘点 20 定时盘点 30 主动盘点
        int closeType = inventoryDiffDto.getCloseType().intValue();         // 10购物 20补货
        if (inventoryType == 10) {
            if (BooleanUtils.isFalse(compareResult)) {
                InventoryDiffResult inventoryDiffResult = new InventoryDiffResult();
                if (closeType == 10) {
                    inventoryDiffResult.setItype(60);
                    responseVo.setMsg("重力差值较大生成购物异常订单，请联系客服");
                    logger.debug("重力差对比商品数量差超过阈值");
                    generateGravityVisiionExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDiffDto, 40, inventoryDiffDto.getLayerGravityVoList());
                    responseVo.setData(inventoryDiffResult);
                    return responseVo;
                }
            }
            if (closeType == 10) {
                if (CollectionUtils.isNotEmpty(addVoList)) {    // 购物时出现增加商品异常
                    // 新增报警日志
                    addSystemAlarmLog(memberInfo, inventoryDiffDto.getSip(), addVoList);
                }
                if (CollectionUtils.isNotEmpty(subVoList)) {    // 购物商品正常减少
                    // 生成购物订单
                    ResponseVo<GeneratingOrderResults> resVO = generateGravityVisionOrder(deviceInfo, memberInfo, subVoList, inventoryDiffDto);
                    if (null != resVO) {
                        logger.info("调用点单生成服务返回数据：{}", JSON.toJSONString(resVO));
                        if (resVO.isSuccess()) {
                            // 异步对比生成订单后的库存是否出现异常数据
                            asynCompareInventoryAndStock(inventoryDiffDto.getInventoryCommodityDiffVoList(), deviceInfo, memberInfo, inventoryDiffDto.getSip());

                            // 返回订单信息
                            GeneratingOrderResults orderResults = resVO.getData();
                            List<CreatOrderResult> orderRecords = orderResults.getCreatOrderResultList();
                            StringBuffer sb = new StringBuffer();
                            for (CreatOrderResult orderRecord : orderRecords) {
                                sb.append(orderRecord.getOrderRecord().getId() + ",");
                            }

                            // 异步操作库存（根据剩余商品数量计算库存剩余值）
                            stockChange(20, null, subVoList, deviceInfo, sb.toString().substring(0, sb.toString().length() - 1));

                            //返回盘点结果
                            InventoryDiffResult result = new InventoryDiffResult();
                            result.setMerchantCode(deviceInfo.getSmerchantCode());
                            result.setIsFirstOrder(orderResults.getIsFirstOrder());
                            OrderRecord orderRecord = orderRecords.get(0).getOrderRecord();
                            if (orderRecord.getIchargebackWay().intValue() == 10 || orderRecord.getIchargebackWay().intValue() == 40 || orderRecord.getIchargebackWay().intValue() == 30) {
                                result.setItype(10);
                            } else {
                                result.setItype(20);
                            }
                            result.setOrderRecords(orderRecords);
                            responseVo.setData(result);
                            return responseVo;
                        } else {
                            generateGravityVisiionExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDiffDto, 30, null);
                            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("订单生成异常");
                        }
                    } else {
                        generateGravityVisiionExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDiffDto, 30, null);
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单异常");
                    }
                } else {
                    //返回盘点结果
                    InventoryDiffResult result = new InventoryDiffResult();
                    result.setItype(50);
                    responseVo.setData(result);
                    return responseVo;
                }
            } else if (closeType == 20) {   // 补货员关门
                if (CollectionUtils.isNotEmpty(subVoList) || CollectionUtils.isNotEmpty(addVoList)) {
                    // 生成补货单 商品状态正常
                    // 库存对比 计算商品差
                    Map<String, Object> stockMap = this.calcCommodityDiffByType(commodityVos, deviceInfo, memberInfo);
                    List<CommodityDiffVo> stockAddVoList = (List<CommodityDiffVo>) stockMap.get("addVoList");
                    List<CommodityDiffVo> stockSubVoList = (List<CommodityDiffVo>) stockMap.get("subVoList");
                    ResponseVo<ReplenishmentResult> resVO = generateGravityVisiionReplenishment(deviceInfo, memberInfo, subVoList, addVoList, inventoryDiffDto, stockAddVoList, stockSubVoList);
                    if (null != resVO) {
                        logger.info("调用补货单生成服务返回数据：{}", JSON.toJSONString(resVO));
                        if (resVO.isSuccess()) {
                            InventoryDiffResult result = new InventoryDiffResult();
                            result.setMerchantCode(deviceInfo.getSmerchantCode());
                            result.setItype(30);
                            result.setReplenishmentResult(resVO.getData());
                            responseVo.setData(result);
                            return responseVo;
                        } else {
                            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("补货单生成异常，请重新补货");
                        }
                    } else {
                        throw new ServiceException("补货单生成异常");
                    }
                } else {
                    //返回盘点结果
                    InventoryDiffResult result = new InventoryDiffResult();
                    result.setItype(50);
                    responseVo.setData(result);
                    return responseVo;
                }
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("盘点类型异常！");
    }


    /**
     * 修改设备库存
     *
     * @param addMap
     */
    private void updateIstockByAdd(Map<String, String> addMap) {
        deviceStockDao.updateIstockByAdd(addMap);
    }


    /**
     * 生成补货单
     *
     * @param deviceInfo       设备信息
     * @param memberInfo       会员信息
     * @param subVoList        减少商品集合
     * @param addVoList        增加商品集合
     * @param inventoryDiffDto 入参
     * @param stockAddVoList
     * @param stockSubVoList
     * @return 补货结果
     */
    private ResponseVo<ReplenishmentResult> generateGravityVisiionReplenishment(DeviceInfo deviceInfo, MemberInfo memberInfo, List<CommodityDiffVo> subVoList, List<CommodityDiffVo> addVoList, InventoryDiffDto inventoryDiffDto, List<CommodityDiffVo> stockAddVoList, List<CommodityDiffVo> stockSubVoList) {
        try {
            ReplenishmentDto replenishmentDto = new ReplenishmentDto();
            replenishmentDto.setAddVoStockList(stockAddVoList);
            replenishmentDto.setSubVoStockList(stockSubVoList);
            replenishmentDto.setIrepWay(20);
            replenishmentDto.setIsourceClientType(inventoryDiffDto.getIsourceClientType());
            replenishmentDto.setItype(10);
            replenishmentDto.setAddVoList(addVoList);
            replenishmentDto.setSubVoList(subVoList);
            replenishmentDto.setSdeviceId(deviceInfo.getId());
            replenishmentDto.setSdeviceCode(deviceInfo.getScode());
            replenishmentDto.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
            replenishmentDto.setSreaderSerialNumber(deviceInfo.getSreaderSerialNumber());
            replenishmentDto.setSmerchantId(deviceInfo.getSmerchantId());
            replenishmentDto.setSmerchantCode(deviceInfo.getSmerchantCode());
            replenishmentDto.setSmemberName(memberInfo.getSmemberName());
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(StockServicesDefine.GENERATE_REPLENISHMENT_SERVICE);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<ReplenishmentResult>>() {
            });
            invoke.setRequestObj(replenishmentDto); // post 参数
            ResponseVo<ReplenishmentResult> resVO = (ResponseVo<ReplenishmentResult>) invoke.invoke();
            return resVO;
        } catch (Exception e) {
            logger.error("补货单生成异常：{}", e);
        }
        return null;
    }

    /**
     * 生成重力视觉柜子异常审核订单
     * 记录重力异常数据
     *
     * @param deviceInfo         设备信息
     * @param memberInfo         用户信息
     * @param subVoList          盘点商品减少信息
     * @param inventoryDiffDto   入参
     * @param itype              类型
     * @param layerGravityVoList 重力模型
     */
    private ResponseVo<String> generateGravityVisiionExceptionOrder(DeviceInfo deviceInfo, MemberInfo memberInfo, List<CommodityDiffVo> subVoList, InventoryDiffDto inventoryDiffDto, int itype, List<LayerGravityVo> layerGravityVoList) {
        try {
            // 组装重力异常数据明细
            StringBuffer stringBuffer = new StringBuffer();
            if (null != layerGravityVoList) {
                for (LayerGravityVo layer : layerGravityVoList) {
                    String layerNo = layer.getLayerNo();
                    String decrement = layer.getLayerGravityDecrement();
                    stringBuffer.append("层板编号：").append(layerNo).append("，减少了：").append(decrement);
                }
            }

            //生成异常审核订单
            ExceptionOrderDto exceptionOrderDto = new ExceptionOrderDto();
            exceptionOrderDto.setSdeviceId(deviceInfo.getId());
            exceptionOrderDto.setSdeviceCode(deviceInfo.getScode());
            exceptionOrderDto.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
            exceptionOrderDto.setSreaderSerialNumber(deviceInfo.getSreaderSerialNumber());
            exceptionOrderDto.setSmerchantId(deviceInfo.getSmerchantId());
            exceptionOrderDto.setSmerchantCode(deviceInfo.getSmerchantCode());
            exceptionOrderDto.setSmemberCode(memberInfo.getScode());
            exceptionOrderDto.setSmemberId(memberInfo.getId());
            exceptionOrderDto.setSmemberName(memberInfo.getSmemberName());
            exceptionOrderDto.setSdeviceName(deviceInfo.getSname());
            exceptionOrderDto.setIsourceClientType(inventoryDiffDto.getIsourceClientType());
            exceptionOrderDto.setItype(itype);//盘点异常
            exceptionOrderDto.setShandlerRemark(stringBuffer.toString());   // 增加异常重力信息

            BigDecimal allAmount = new BigDecimal("0");
            List<ExceptionOrderCommodityDto> commodityDtos = new ArrayList<ExceptionOrderCommodityDto>();
            ExceptionOrderCommodityDto commodityDto = null;
            for (CommodityDiffVo diffVo : subVoList) {
                commodityDto = new ExceptionOrderCommodityDto();
                BeanUtils.copyProperties(commodityDto, diffVo);//复制对象

                commodityDto.setForderCount(diffVo.getNumber());
                commodityDto.setFcommodityAmount(commodityDto.getFcommodityPrice().multiply(new BigDecimal(commodityDto.getForderCount())));
                allAmount = allAmount.add(commodityDto.getFcommodityAmount());
                commodityDtos.add(commodityDto);
            }
            exceptionOrderDto.setExceptionOrderCommodityDtoList(commodityDtos);
            exceptionOrderDto.setFtotalAmount(allAmount);
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OrderDiscountDefine.CREATE_EXCEPTION_ORDER);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            invoke.setRequestObj(exceptionOrderDto); // post 参数
            ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
            return resVO;
        } catch (Exception e) {
            logger.error("异常订单生成异常：{}", e);
        }
        return null;
    }

    /**
     * 开关门商品数量差生成订单
     *
     * @param deviceInfo       设备信息
     * @param memberInfo       会员信息
     * @param subVoList        较少商品
     * @param inventoryDiffDto 开关门盘货结果
     * @return
     */
    private ResponseVo<GeneratingOrderResults> generateGravityVisionOrder(DeviceInfo deviceInfo, MemberInfo memberInfo, List<CommodityDiffVo> subVoList, InventoryDiffDto inventoryDiffDto) {
        try {
            OrderCommDto commDto = new OrderCommDto();
            OrderDto orderDto = new OrderDto();
            orderDto.setIsourceClientType(inventoryDiffDto.getIsourceClientType());
            orderDto.setSdeviceId(deviceInfo.getId());
            orderDto.setSdeviceCode(deviceInfo.getScode());
            orderDto.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
            orderDto.setSreaderSerialNumber(deviceInfo.getSreaderSerialNumber());
            orderDto.setSmerchantId(deviceInfo.getSmerchantId());
            orderDto.setSmerchantCode(deviceInfo.getSmerchantCode());
            orderDto.setSmemberCode(memberInfo.getScode());
            orderDto.setSmemberId(memberInfo.getId());
            orderDto.setSdeviceName(deviceInfo.getSname());
            orderDto.setSmemberName(memberInfo.getSmemberName());
            commDto.setOrderDto(orderDto);
            OrderCommodityDto orderCommodityDto = null;
            List<OrderCommodityDto> comms = new ArrayList<OrderCommodityDto>();
            for (CommodityDiffVo diffVo : subVoList) {
                orderCommodityDto = new OrderCommodityDto();
                BeanUtils.copyProperties(orderCommodityDto, diffVo);//复制对象
                orderCommodityDto.setForderCount(diffVo.getNumber());
                orderCommodityDto.setFcostAmount(diffVo.getFcostPrice());
                comms.add(orderCommodityDto);
            }
            commDto.setComms(comms);
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OrderDiscountDefine.CREATE_ORDER);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GeneratingOrderResults>>() {
            });
            invoke.setRequestObj(commDto); // post 参数
            ResponseVo<GeneratingOrderResults> resVO = (ResponseVo<GeneratingOrderResults>) invoke.invoke();
            return resVO;
        } catch (Exception e) {
            logger.error("订单生成异常：{}", e);
        }
        return null;
    }

    /**
     * 关门时根据商品差计算订单商品信息
     *
     * @param inventoryCommodityDiffVoList
     * @param deviceInfo
     * @param memberInfo
     * @return
     */
    @Override
    public Map<String, Object> calcIncrementsAndDecrements(List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList, DeviceInfo deviceInfo, MemberInfo memberInfo) {
        // 根据差值计算
        Map<String, Object> map = new HashMap<>();
        List<CommodityDiffVo> subVoList = new ArrayList<CommodityDiffVo>(); // 增加的商品信息
        List<CommodityDiffVo> addVoList = new ArrayList<CommodityDiffVo>(); // 减少的商品信息
        List<CommodityVo> commodityVos = new ArrayList<>(); // 当前设备库存信息
        if (CollectionUtils.isEmpty(inventoryCommodityDiffVoList)) {
            map.put("addVoList", addVoList);
            map.put("subVoList", subVoList);
            map.put("commodityVos", commodityVos);
            return map;
        }
        for (InventoryCommodityDiffVo commodityDiffVo : inventoryCommodityDiffVoList) {
            Integer in = commodityDiffVo.getIncrement();
            Integer de = commodityDiffVo.getDecrement();
            CommodityDiffVo commodityDiffVoTemp = null;
            CommodityVo commodityVo = new CommodityVo();
            // 记录商品库存
            commodityVo.setSvrCode(commodityDiffVo.getSvrCode());
            commodityVo.setCommodityNum(commodityDiffVo.getRemainsNum());
            commodityVos.add(commodityVo);

            if (in.intValue() > 0 && de.intValue() == 0) {  // 增加量
                commodityDiffVoTemp = assemblyOrderCommodity(10, commodityDiffVo, deviceInfo);
                if (null != commodityDiffVoTemp) {
                    addVoList.add(commodityDiffVoTemp);
                } else {
                    continue;
                }
            } else if (in.intValue() == 0 && de.intValue() > 0) { // 减少量
                commodityDiffVoTemp = assemblyOrderCommodity(20, commodityDiffVo, deviceInfo);
                if (null != commodityDiffVoTemp) {
                    subVoList.add(commodityDiffVoTemp);
                } else {
                    continue;
                }
            } else if (in.intValue() == 0 && de.intValue() == 0) { //
                logger.debug("安卓传输数据，增加量：0，减少量：0");
                continue;
            }
        }
        map.put("addVoList", addVoList);
        map.put("subVoList", subVoList);
        map.put("commodityVos", commodityVos);
        return map;
    }


    /**
     * 组装增量商品信息或者减量商品信息
     *
     * @param type            10增加 20减少
     * @param commodityDiffVo 商品视觉编号与数量信息
     * @param deviceInfo      设备信息
     * @return
     */
    private CommodityDiffVo assemblyOrderCommodity(int type, InventoryCommodityDiffVo commodityDiffVo, DeviceInfo deviceInfo) {
        CommodityDiffVo tempDiffVo = new CommodityDiffVo();
        tempDiffVo.setSvrCode(commodityDiffVo.getSvrCode());
        CommodityInfo commodityInfo = commodityInfoService.selectByVrCode(commodityDiffVo.getSvrCode(), deviceInfo.getSmerchantId());
        if (null != commodityInfo) {
            tempDiffVo.setScommodityCode(commodityInfo.getScode());
            tempDiffVo.setScommodityId(commodityInfo.getId());
            tempDiffVo.setScommodityFullName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
            tempDiffVo.setScommodityName(commodityInfo.getSname());
            tempDiffVo.setFcostPrice(commodityInfo.getFcostPrice());
            tempDiffVo.setFcommodityPrice(commodityInfo.getFsalePrice());
            tempDiffVo.setIstatus(commodityInfo.getIstatus());
            tempDiffVo.setSpackageUnit(commodityInfo.getSpackageUnit());
            tempDiffVo.setSbrandId(commodityInfo.getSbrandId());
            tempDiffVo.setSsmallCategoryId(commodityInfo.getSsmallCategoryId());
            tempDiffVo.setScommodityImg(commodityInfo.getScommodityImg());
            tempDiffVo.setIweigth(commodityInfo.getIweigth());
            if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 10) {
                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "天");
            } else if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 20) {
                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "个月");
            }
            if (type == 10) {   // 增量
                tempDiffVo.setItype(10);
                tempDiffVo.setNumber(commodityDiffVo.getIncrement());
                tempDiffVo.setCurrStock(commodityDiffVo.getRemainsNum());
            } else {            // 减量
                tempDiffVo.setItype(20);
                tempDiffVo.setNumber(commodityDiffVo.getDecrement());
                tempDiffVo.setCurrStock(commodityDiffVo.getRemainsNum());
            }
        } else {
            logger.error("视觉盘点商品不存在，商户编号-视觉编号：{}", deviceInfo.getSmerchantCode() + "-" + commodityDiffVo.getSvrCode());
            return null;
        }
        return tempDiffVo;
    }


    /**
     * 异步比较设备盘货结果与生成订单修改库存后的商品数量的对比
     * 如果出现差别的情况进行异常预警
     *
     * @param inventoryCommodityDiffVoList 盘货商品结果集合
     * @param deviceInfo                   设备信息
     * @param memberInfo                   会员信息
     * @param sip
     */
    private void asynCompareInventoryAndStock(final List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList, final DeviceInfo deviceInfo, final MemberInfo memberInfo, final String sip) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CommodityVo> commodityVos = new ArrayList<>();
                    CommodityVo commodityVo = null;
                    if (CollectionUtils.isNotEmpty(inventoryCommodityDiffVoList)) {
                        for (InventoryCommodityDiffVo in : inventoryCommodityDiffVoList) {
                            commodityVo = new CommodityVo();
                            commodityVo.setSvrCode(in.getSvrCode());
                            commodityVo.setCommodityNum(in.getRemainsNum());
                            commodityVos.add(commodityVo);
                        }
                    }
                    // 调用计算商品差方法计算
                    Map<String, Object> map = calcCommodityDiffByType(commodityVos, deviceInfo, memberInfo);
                    List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) map.get("addVoList");
                    List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");
                    StringBuffer sb = new StringBuffer();
                    if (CollectionUtils.isNotEmpty(addVoList)) {
                        sb.append("库存增加的商品信息为：");
                        for (CommodityDiffVo com : addVoList) {
                            sb.append("商品编号：").append(com.getScommodityCode()).append("商品名称：").append(com.getScommodityFullName()).append("数量：").append(com.getNumber()).append("\r\n");
                        }
                    }
                    if (CollectionUtils.isNotEmpty(subVoList)) {
                        sb.append("库存减少的商品信息为：");
                        for (CommodityDiffVo com : subVoList) {
                            sb.append("商品编号：").append(com.getScommodityCode()).append("商品名称：").append(com.getScommodityFullName()).append("数量：").append(com.getNumber()).append("\r\n");
                        }
                    }
                    String systemAlarmLog = sb.toString();

                    // 开始插入警告日志
                    SystemAlarm alarm = new SystemAlarm();
                    alarm.setIisDealwith(0);
                    alarm.setIproblemType(20);  // 10=购物会员库存上架 20=购物会员关门库存对比
                    alarm.setIsystemType("cloud-cang-bzc");
                    alarm.setItype(10); // 操作类型：10=购物会员 20=补货会员 30=系统用户
                    alarm.setSmemberCode(memberInfo.getScode());
                    alarm.setSmemberId(memberInfo.getId());
                    alarm.setSmemberName(memberInfo.getSmemberName());
                    Date operDate = new Date();
                    alarm.setTaddTime(operDate);
                    alarm.setTalarmTime(operDate);
                    alarm.setTupdateTime(operDate);
                    alarm.setSoperIp(sip);
                    alarm.setSproblemDesc(systemAlarmLog);
                    systemAlarmService.insert(alarm);
                } catch (Exception e) {
                    logger.error("异步比较设备盘货结果与库存商品数量出现异常：{}", e);
                }
            }
        });
    }

    /**
     * 开门前盘货对比最近一次关门盘货库存
     *
     * @param beforeOpenDoorInventoryDto
     * @return
     */
    @Override
    public ResponseVo<String> dealwithBeforeOpenDoorInventory(BeforeOpenDoorInventoryDto beforeOpenDoorInventoryDto) {
        try {
            String deviceId = beforeOpenDoorInventoryDto.getDeviceId();
            List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList = beforeOpenDoorInventoryDto.getInventoryCommodityDiffVoList();
            List<CommodityVo> commodityVoList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(inventoryCommodityDiffVoList)) {
                CommodityVo commodityVo = null;
                for (InventoryCommodityDiffVo in : inventoryCommodityDiffVoList) {
                    commodityVo = new CommodityVo();
                    commodityVo.setSvrCode(in.getSvrCode());
                    commodityVo.setCommodityNum(in.getRemainsNum());
                    commodityVoList.add(commodityVo);
                }
            }
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
            //1、库存对比 计算商品差
            Map<String, Object> map = this.calcCommodityDiffByType(commodityVoList, deviceInfo, null);
            List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) map.get("addVoList");
            List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");
            StringBuffer sb = new StringBuffer();
            if (CollectionUtils.isNotEmpty(addVoList)) {
                sb.append("开门前对比上次关门后库存，增加的商品信息为：");
                for (CommodityDiffVo com : addVoList) {
                    sb.append("商品编号：").append(com.getScommodityCode()).append("商品名称：").append(com.getScommodityFullName()).append("数量：").append(com.getNumber()).append("\r\n");
                }
            }
            if (CollectionUtils.isNotEmpty(subVoList)) {
                sb.append("开门前对比上次关门后库存，减少的商品信息为：");
                for (CommodityDiffVo com : subVoList) {
                    sb.append("商品编号：").append(com.getScommodityCode()).append("商品名称：").append(com.getScommodityFullName()).append("数量：").append(com.getNumber()).append("\r\n");
                }
            }
            String systemAlarmLog = sb.toString();
            if (StringUtils.isNotBlank(systemAlarmLog)) {   // 存在告警信息
                // 查询设备上次开门信息（关门信息可能没有）
                DeviceOper deviceOper = deviceOperService.selectLastByDeviceId(deviceInfo.getScode());

                // 开始插入警告日志
                SystemAlarm alarm = new SystemAlarm();
                alarm.setIisDealwith(0);
                alarm.setIproblemType(30);  // 10=购物会员库存上架 20=购物会员关门库存对比 30=开门前盘货对比上次关门库存
                alarm.setIsystemType("cloud-cang-bzc");
                alarm.setItype(30); // 操作类型：10=购物会员 20=补货会员 30=系统用户
                alarm.setSmemberCode(deviceOper.getSmemberCode());
                alarm.setSmemberId(deviceOper.getSmemberId());
                alarm.setSmemberName(deviceOper.getSmemberName());
                Date operDate = new Date();
                alarm.setTaddTime(operDate);
                alarm.setTalarmTime(operDate);
                alarm.setTupdateTime(operDate);
                alarm.setSoperIp(deviceOper.getSip());
                alarm.setSproblemDesc(systemAlarmLog);
                systemAlarmService.insert(alarm);
            }
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("开门前盘货对比最近一次关门盘货库存出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("开门前盘货对比最近一次关门盘货库存出现错误");
    }

    /**
     * 计算理论上增加、减少的商品重力差值
     *
     * @param inventoryCommodityDiffVoList 商品集合
     * @param deviceId                     设备ID
     * @return
     */
    private Map<String, BigDecimal> calculateCommodityWeight(List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList, String deviceId) {
        String vrCode = "";
        CommodityInfo commodityInfoVo = null;
        CommodityInfo commodityInfo = null;
        BigDecimal incrementWeight = new BigDecimal("0");   // 增加总重量
        BigDecimal decrementWeight = new BigDecimal("0");   // 减少总重量
        BigDecimal comInDeviation = new BigDecimal("0");    // 商品增加总偏差值
        BigDecimal comDeDeviation = new BigDecimal("0");    // 商品减少总偏差值
        BigDecimal deviceDeviation = new BigDecimal("0");    // 设备电子秤误差值

        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
        // 获取设备电子秤误差值
        deviceDeviation = deviceInfo.getIelectronicFloat();

        // 获取商品误差值
        for (InventoryCommodityDiffVo local : inventoryCommodityDiffVoList) {
            vrCode = local.getSvrCode();
            int increment = local.getIncrement();
            int decrement = local.getDecrement();
            if (StringUtils.isNotBlank(vrCode)) {
                commodityInfoVo = new CommodityInfo();
                commodityInfoVo.setIstatus(10);         // 正常状态
                commodityInfoVo.setIdelFlag(0);
                commodityInfoVo.setIstoreDevice(10);    // 视觉商品
                commodityInfoVo.setSmerchantId(deviceInfo.getSmerchantId());
                commodityInfoVo.setSvrCode(vrCode);
                List<CommodityInfo> commodityInfoList = commodityInfoService.selectByEntityWhere(commodityInfoVo);
                if (CollectionUtils.isNotEmpty(commodityInfoList)) {    // 组装商品增加减少的重量，商品误差值范围
                    commodityInfo = commodityInfoList.get(0);
                    if (increment > 0 && decrement < 0) {
                        incrementWeight = BigDecimalUtils.add(BigDecimalUtils.multiply(commodityInfo.getIweigth(), increment), incrementWeight);            // 累加增加的重量
                        comInDeviation = BigDecimalUtils.add(BigDecimalUtils.multiply(commodityInfo.getIcommodityFloat(), increment), comInDeviation);      // 累加商品重量误差值
                    } else if (increment == 0 && decrement > 0) {
                        decrementWeight = BigDecimalUtils.add(BigDecimalUtils.multiply(commodityInfo.getIweigth(), decrement), decrementWeight);            // 累加增加的重量
                        comDeDeviation = BigDecimalUtils.add(BigDecimalUtils.multiply(commodityInfo.getIcommodityFloat(), decrement), comDeDeviation);      // 累加商品重量误差值
                    } else {
                        logger.debug("商品视觉识别号：{},，增加数量：{}，减少数量：" + decrement, vrCode, increment);
                        continue;
                    }
                }
            }
        }
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("incrementWeight", incrementWeight);
        map.put("comInDeviation", comInDeviation);
        map.put("decrementWeight", decrementWeight);
        map.put("comDeDeviation", comDeDeviation);
        map.put("deviceDeviation", deviceDeviation);
        return map;
    }

    /**
     * 比较关门后视觉商品数量与重力是否匹配
     *
     * @param layerGravityVoList 实际商品每层重力集合
     * @param weightMap          理论上增加、减少的商品重力差值
     * @param openDoorType       开门类型
     * @return
     */
    private Map<String, Object> compareWeightDiff(List<LayerGravityVo> layerGravityVoList, Map<String, BigDecimal> weightMap, Integer openDoorType) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", false);
        BigDecimal incrementWeight = weightMap.get("incrementWeight");
        BigDecimal comInDeviation = weightMap.get("comInDeviation");
        BigDecimal decrementWeight = weightMap.get("decrementWeight");
        BigDecimal comDeDeviation = weightMap.get("comDeDeviation");
        BigDecimal deviceDeviation = weightMap.get("deviceDeviation");
        BigDecimal totalDeviceDeviation = new BigDecimal("0");      // 设备每层电子秤的误差值
        BigDecimal gravityIncrement = new BigDecimal("0");
        BigDecimal gravityDecrement = new BigDecimal("0");
        if (CollectionUtils.isNotEmpty(layerGravityVoList)) {
            for (LayerGravityVo layer : layerGravityVoList) {
                BigDecimal tempIncrement = new BigDecimal(layer.getLayerGravityIncrement());
                BigDecimal tempDecrement = new BigDecimal(layer.getLayerGravityDecrement());
                gravityIncrement = BigDecimalUtils.add(tempIncrement, gravityIncrement);
                gravityDecrement = BigDecimalUtils.add(tempDecrement, gravityDecrement);
            }
            totalDeviceDeviation = BigDecimalUtils.multiply(deviceDeviation, layerGravityVoList.size());
        } else {
            logger.error("安卓传输过来的商品重力值为空集合，不进行视觉商品数量值与重力差值比较");
            resultMap.put("result", true);
            return resultMap;
        }

        if (10 == openDoorType) {   // 购物
            // 只计算减少的商品是否在误差范围内，不管没有增加商品
            // 理论值 - 实际值 < 0 ，包装还在东西被吃了
            BigDecimal decrementDiff = BigDecimalUtils.sub(decrementWeight, gravityDecrement);
            BigDecimal deviationDiff = BigDecimalUtils.add(comDeDeviation, totalDeviceDeviation);
            logger.debug("顾客购物商品对比实际重力差为：{}", decrementDiff);
            if (BigDecimalUtils.gtOrEqZero(decrementDiff)) {
                resultMap.put("result", true);
                return resultMap;
            } else if (BigDecimalUtils.ltZero(decrementDiff) && BigDecimalUtils.compareTo(deviationDiff, decrementDiff.abs())) {  // 误差范围内
                resultMap.put("result", true);
                return resultMap;
            }
            logger.error("顾客购物商品对比重力差超过阈值");
        } else if (20 == openDoorType) { // 补货
            logger.debug("补货员开门");
            resultMap.put("result", true);
            return resultMap;
        } else {
            logger.error("未知类型的补货");
        }
        return resultMap;

    }

    /**
     * 关门时，根据商品数量生成订单服务
     *
     * @param inventoryDto 订单参数
     * @return
     */
    @Override
    public ResponseVo<InventoryResult> closeGravityCommodityDeal(InventoryDto inventoryDto) {

        ResponseVo<InventoryResult> responseVo = ResponseVo.getSuccessResponse();
        //验证参数数据
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(inventoryDto.getDeviceId());
        if (null == deviceInfo || deviceInfo.getIstatus().intValue() != 20 ||
                deviceInfo.getIoperateStatus().intValue() == 20) {
            logger.error("设备数据异常，设备ID：{}", inventoryDto.getDeviceId());
            logger.error("设备数据异常，设备数据：{}", deviceInfo);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，设备数据异常");
        }
        //关门盘点 会员不能为空
        if (inventoryDto.getInventoryType().intValue() == 10 && StringUtil.isBlank(inventoryDto.getMemberId())) {
            logger.error("底层盘点关门盘点会员数据异常：{}", inventoryDto);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }
        if (StringUtil.isBlank(inventoryDto.getMemberId())) {
            //获取最近开门会员信息
            DeviceOper deviceOper = deviceOperService.selectLastByDeviceId(deviceInfo.getScode());
            if (null == deviceOper) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
            }
            inventoryDto.setMemberId(deviceOper.getSmemberId());
            inventoryDto.setIsourceClientType(deviceOper.getIclientType());
        }
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(inventoryDto.getMemberId());
        if (null == memberInfo || !memberInfo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }
        //1、库存对比 计算商品差
        logger.info("库存对比 计算商品差：{}", inventoryDto.getCommodityVos());
        Map<String, Object> map = null;
        try {
            DeviceModel deviceModel = deviceModelDao.selectByDeviceId(deviceInfo.getId());
            logger.info("重力视觉柜计算开关门对比,设备盘货方式：{}", deviceModel.getScontrastMode());
            if (null != deviceModel && StringUtil.isNotBlank(deviceModel.getScontrastMode())
                    && deviceModel.getScontrastMode().equals("rawstock")) {
                //查询设备库存
                map = this.calcCommodityDiffByType(inventoryDto.getCommodityVos(), deviceInfo, memberInfo);
            } else {
                String goodMap = (String) iCached.get(NettyConst.CLOUD_DEVICE_OPEN_DOOR_COMMODITYS + deviceInfo.getId());    // 在Redis中记录开门商品
                logger.info("重力视觉柜计算开关门对比差异，开门商品数据,{}", goodMap);
                Map<String, Integer> openDoorMap = JSON.parseObject(goodMap, new TypeReference<Map<String, Integer>>() {
                });
                map = assemblyData(openDoorMap, inventoryDto, deviceInfo);
                logger.info("重力视觉柜计算开关门商品差异完成：{}", deviceInfo.getScode());
            }
        } catch (Exception e) {
            logger.error("从Redis中获取开门商品集合失败，设备编号：{}", deviceInfo.getScode());
        }
        List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) map.get("addVoList");
        List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");
        logger.info("重力视觉柜开关门对比增加部分：{}", addVoList);
        logger.info("重力视觉柜开关门对比减少部分：{}", subVoList);
        BigDecimal stockWeight = inventoryDto.getStockTotalWeight();
        BigDecimal totalWeight = inventoryDto.getTotalWeight();
        logger.info("开门前库存总重量:{}", stockWeight);
        logger.info("盘货总重量:{}", totalWeight);
        BigDecimal subTemp = stockWeight.subtract(totalWeight);
        if (subTemp.abs().compareTo(deviceInfo.getIelectronicFloat()) <= 0) {  //库存重力等于盘货重力    -->重力不变
            if ((!subVoList.isEmpty()) || (subVoList.isEmpty() && addVoList.isEmpty())) {
                //视觉减少  || 视觉不变     --> 不出订单
                logger.info("开关门盘货重力不变,视觉减少 || 视觉不变--> 不出订单");
            } else if (!addVoList.isEmpty()) {
                //视觉变多,出警报
                addSystemAlarmLog(memberInfo, inventoryDto.getSip(), addVoList, "开门盘货重力等于稳定盘货重力,视觉有商品增加!");
                logger.info("开关门盘货重力不变,视觉变多-->出警报");
            }
        } else if (totalWeight.compareTo(stockWeight.add(deviceInfo.getIelectronicFloat())) > 0) { //库存重力小于盘货重力  -->重力变多
            //出警报
            logger.info("开门盘货重力小于关门盘货重力--> 出警报");
            if (addVoList != null && !addVoList.isEmpty()) {
                addSystemAlarmLog(memberInfo, inventoryDto.getSip(), addVoList, "开门盘货重力小于关门盘货重力,视觉有商品增加!");
            } else {
                addSystemAlarmLog(memberInfo, inventoryDto.getSip(), "开门盘货重力小于稳定盘货重力,货柜可能有有物品添加！");
            }
        } else {  //库存重力大于盘货重力  -->重力变少
            logger.info("开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化");
            BigDecimal subResult = stockWeight.subtract(totalWeight);//重力减少的值
            BigDecimal subTotal = BigDecimal.ZERO;//视觉盘点减少的重力值
            BigDecimal floatResult = BigDecimal.ZERO;//商品浮动值
            List<String> visionList = new ArrayList();//视觉List
            for (CommodityDiffVo commodityDiffVo : subVoList) {
                BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                subTotal = subTotal.add(multiplyData);
                floatResult = floatResult.add(commodityDiffVo.getIcommodityFloat());
                visionList.add(commodityDiffVo.getSvrCode());
            }
            BigDecimal iele = deviceInfo.getIelectronicFloat();
            logger.info("floatResult:" + floatResult + "##:iele" + iele);
            if (subResult.subtract(subTotal).abs().compareTo(floatResult.add(iele)) <= 0) {
                //在误差范围内,直接出订单
                logger.info("开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->误差范围内,直接出订单");
                //生成购物订单
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            } else {
                //如果重力和视觉变化的相差巨大，且重力可以准确匹配到相对应的结果，以重力匹配的结果为准输出订单，如果两方均无法准确匹配出异常订单
                //查询设备库存商品
                logger.info("开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品");
                if (null != subVoList & !subVoList.isEmpty()) {
                    logger.info("开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->视觉有减少");
                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto, subResult, subTotal, 10, floatResult, visionList);
                } else {
                    //匹配商品
                    logger.info("开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->视觉不变或增多");
                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto, subResult, null, 20, null, visionList);
                }
            }
        }
        InventoryResult result = new InventoryResult();
        result.setMerchantCode(deviceInfo.getSmerchantCode());
        result.setItype(50);//处理成功
        responseVo.setData(result);
        return responseVo;
    }

    /**
     * 重力分层柜关门时，根据商品数量生成订单服务
     *
     * @param layeredInventoryDto 订单参数
     * @return
     */
    @Override
    public ResponseVo<InventoryResult> closeGravityLayeredCommodityDeal(LayeredInventoryDto layeredInventoryDto) {

        ResponseVo<InventoryResult> responseVo = ResponseVo.getSuccessResponse();
        //验证参数数据
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(layeredInventoryDto.getDeviceId());
        if (null == deviceInfo || deviceInfo.getIstatus().intValue() != 20 ||
                deviceInfo.getIoperateStatus().intValue() == 20) {
            logger.error("设备数据异常，设备ID：{}", layeredInventoryDto.getDeviceId());
            logger.error("设备数据异常，设备数据：{}", deviceInfo);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，设备数据异常");
        }
        //关门盘点 会员不能为空
        if (layeredInventoryDto.getInventoryType().intValue() == 10 && StringUtil.isBlank(layeredInventoryDto.getMemberId())) {
            logger.error("底层盘点关门盘点会员数据异常：{}", layeredInventoryDto);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }
        if (StringUtil.isBlank(layeredInventoryDto.getMemberId())) {
            //获取最近开门会员信息
            DeviceOper deviceOper = deviceOperService.selectLastByDeviceId(deviceInfo.getScode());
            if (null == deviceOper) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
            }
            layeredInventoryDto.setMemberId(deviceOper.getSmemberId());
            layeredInventoryDto.setIsourceClientType(deviceOper.getIclientType());
        }
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(layeredInventoryDto.getMemberId());
        if (null == memberInfo || !memberInfo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }
        //1、库存对比 计算商品差
        List<LayeredGoodsVo> openGoods = layeredInventoryDto.getOpenLayeredGoods();
        List<LayeredGoodsVo> closeGoods = layeredInventoryDto.getCloseLayeredGoods();
        logger.info("重力视觉分层， 开门商品数据：{}", openGoods);
        logger.info("重力视觉分层， 关门商品数据：{}", closeGoods);
        List<ContrastGoodsVo> contrastGoodsModelList = calcLayeredCommodityDiffData(closeGoods, openGoods, deviceInfo);
        Map<String, CommodityDiffVo> addMap = new HashMap<>();
        Map<String, CommodityDiffVo> subMap = new HashMap<>();
        BigDecimal weighLayered = BigDecimal.ZERO;
        BigDecimal subResult = BigDecimal.ZERO;
        for (ContrastGoodsVo contrastGoodsModel : contrastGoodsModelList) {
            Boolean iisShop = contrastGoodsModel.isIisShop();
            if (!iisShop) {
                continue;
            }
            Map<String, Object> contrastMap = contrastGoodsModel.getContrastMap();
            List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) contrastMap.get("subVoList");
            List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) contrastMap.get("addVoList");
            for (CommodityDiffVo commodityDiffVo : subVoList) {
                String vrCode = commodityDiffVo.getSvrCode();
                if (subMap.get(vrCode) == null) {
                    subMap.put(vrCode, commodityDiffVo);
                } else {
                    //修改数量
                    CommodityDiffVo temp = subMap.get(vrCode);
                    temp.setNumber(temp.getNumber() + commodityDiffVo.getNumber());
                    subMap.put(vrCode, temp);
                }
            }
            for (CommodityDiffVo commodityDiffVo : addVoList) {
                String vrCode = commodityDiffVo.getSvrCode();
                if (addMap.get(vrCode) == null) {
                    addMap.put(vrCode, commodityDiffVo);
                } else {
                    //修改数量
                    CommodityDiffVo temp = addMap.get(vrCode);
                    temp.setNumber(temp.getNumber() + commodityDiffVo.getNumber());
                    addMap.put(vrCode, temp);
                }
            }
            weighLayered = weighLayered.add(BigDecimal.ONE);
            subResult = subResult.add(contrastGoodsModel.getOpenWeight().subtract(contrastGoodsModel.getRealWeight()));
        }
        logger.info("重力视觉分层，购物订单-->重力视觉柜实时盘货开关门对比商品差异完成：{}", deviceInfo.getScode());
        List<CommodityDiffVo> subVoList = new ArrayList<>();
        Iterator<Map.Entry<String, CommodityDiffVo>> it = subMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, CommodityDiffVo> entry = it.next();
            String vrCode = entry.getKey();
            CommodityDiffVo subCommodity = entry.getValue();
            CommodityDiffVo addCommodity = addMap.get(vrCode);
            int diffValue = subCommodity.getNumber();
            if (null != addCommodity) {
                diffValue = subCommodity.getNumber() - addCommodity.getNumber() <= 0 ? 0 : subCommodity.getNumber() - addCommodity.getNumber();
                if (diffValue == 0) {
                    continue;
                }
            }
            subCommodity.setNumber(diffValue);
            subCommodity.setCurrStock(diffValue);
            subVoList.add(subCommodity);
        }
        BigDecimal openWeightTotal = layeredInventoryDto.getOpenWeightTotal();
        BigDecimal closeWeightTotal = layeredInventoryDto.getCloseWeightTotal();
        BigDecimal subTemp = openWeightTotal.subtract(closeWeightTotal);
        logger.info("重力视觉分层订单，开门库存总重量:{}", openWeightTotal);
        logger.info("重力视觉分层订单，关门库存总重量:{}", closeWeightTotal);
        List<CommodityDiffVo> addVoList = new ArrayList<>();
        Iterator<Map.Entry<String, CommodityDiffVo>> addIt = addMap.entrySet().iterator();
        while (addIt.hasNext()) {
            Map.Entry<String, CommodityDiffVo> entry = addIt.next();
            String vrCode = entry.getKey();
            CommodityDiffVo addCommodity = entry.getValue();
            CommodityDiffVo subCommodity = subMap.get(vrCode);
            int diffValue = addCommodity.getNumber();
            if (null != subCommodity) {
                diffValue = addCommodity.getNumber() - subCommodity.getNumber() <= 0 ? 0 : addCommodity.getNumber() - subCommodity.getNumber();
                if (diffValue == 0) {
                    continue;
                }
            }
            addCommodity.setNumber(diffValue);
            addCommodity.setCurrStock(diffValue);
            addVoList.add(addCommodity);
        }
        //如果盘货重量小于
     /*   DeviceModel deviceModel = deviceModelDao.selectByDeviceId(deviceInfo.getId());
        Integer ilayerNum = deviceModel.getIlayerNum();*/
        BigDecimal ieleTotal = deviceInfo.getIelectronicFloat().multiply(weighLayered);
        logger.info("设备传感器总误差ieleTotal：" + ieleTotal);
        if (subTemp.abs().compareTo(ieleTotal) <= 0) {  //库存重力等于盘货重力    -->重力不变
            if ((!subVoList.isEmpty()) || (subVoList.isEmpty() && addVoList.isEmpty())) {
                //视觉减少  || 视觉不变     --> 不出订单
                logger.info("重力视觉分层，开关门盘货重力不变,视觉减少 || 视觉不变--> 不出订单");
            } else if (!addVoList.isEmpty()) {
                //视觉变多,出警报
                addSystemAlarmLog(memberInfo, layeredInventoryDto.getSip(), addVoList, "开门盘货重力等于稳定盘货重力,视觉有商品增加!");
                logger.info("重力视觉分层，开关门盘货重力不变,视觉变多-->出警报");
            }
            logger.info("重力视觉分层，开关门盘货重力不变,视觉减少 || 视觉不变--> 不出订单");
        } else if (closeWeightTotal.compareTo(openWeightTotal.add(ieleTotal)) > 0) { //库存重力小于盘货重力  -->重力变多
            //出警报
            logger.info("重力视觉分层，开门盘货重力小于关门盘货重力--> 出警报");
            if (addVoList != null && !addVoList.isEmpty()) {
                addSystemAlarmLog(memberInfo, layeredInventoryDto.getSip(), addVoList, "开门盘货重力小于关门盘货重力,视觉有商品增加!");
            } else {
                addSystemAlarmLog(memberInfo, layeredInventoryDto.getSip(), "开门盘货重力小于稳定盘货重力,货柜可能有有物品添加！");
            }
        } else {
            if (!subVoList.isEmpty()) {
                Collections.sort(subVoList, new Comparator<CommodityDiffVo>() {
                    @Override
                    public int compare(CommodityDiffVo o1, CommodityDiffVo o2) {
                        int temp1 = o1.getNumber();
                        int temp2 = o2.getNumber();
                        if (temp1 > temp2) {
                            return -1;
                        } else if (temp1 < temp2) {
                            return 1;
                        }
                        return 0;
                    }
                });
            }
            logger.info("分层购物实时订单-->subVoList排序后：{}", subVoList);
            //库存重力大于盘货重力  -->重力变少
            logger.info("重力视觉分层，购物订单-->有视觉减少-->对比视觉和库存重量的变化");
            BigDecimal subTotal = BigDecimal.ZERO;//视觉盘点减少的重力值
            BigDecimal floatResult = BigDecimal.ZERO;//商品浮动值
            List<String> visionList = new ArrayList();//视觉List存储商品识别编号
            for (CommodityDiffVo commodityDiffVo : subVoList) {
                BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                subTotal = subTotal.add(multiplyData);
                floatResult = floatResult.add(commodityDiffVo.getIcommodityFloat());
                visionList.add(commodityDiffVo.getSvrCode());
            }
            BigDecimal iele = deviceInfo.getIelectronicFloat().multiply(weighLayered);
            logger.info("重力视觉分层，购物订单-->商品浮动值总计floatResult:" + floatResult + "##:设备传感器浮动值iele" + iele + "##:计算层数weighLayered:" + weighLayered);
            logger.info("重力减少subResult:{}", subResult);
            logger.info("视觉减少subTotal:{}", subTotal);
            if (subResult.subtract(subTotal).abs().compareTo(floatResult.add(iele)) <= 0) {
                //在误差范围内,直接出订单
                logger.info("重力视觉分层，开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->误差范围内,直接出订单");
                //生成购物订单
                InventoryDto inventoryDto = new InventoryDto();
                inventoryDto.setIsourceClientType(layeredInventoryDto.getIsourceClientType());
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            } else {
                String temp = BizParaUtil.get("weight_layered_warm");
                if (StringUtils.isBlank(temp)) {
                    temp = "5";
                }
                BigDecimal weightLayeredWarm = new BigDecimal(temp);
                BigDecimal weightLayeredWarmTotal = weightLayeredWarm.multiply(weighLayered);
                if (subResult.subtract(subTotal).abs().compareTo(iele.add(weightLayeredWarmTotal).add(floatResult)) <= 0) {
                    if (!subVoList.isEmpty()) {
                        //生成购物订单
                        InventoryDto inventoryDto = new InventoryDto();
                        inventoryDto.setIsourceClientType(layeredInventoryDto.getIsourceClientType());
                        return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                    } else {
                        InventoryResult result = new InventoryResult();
                        result.setMerchantCode(deviceInfo.getSmerchantCode());
                        result.setItype(50);//处理成功
                        responseVo.setData(result);
                        return responseVo;
                    }
                }
                //如果重力和视觉变化的相差巨大，且重力可以准确匹配到相对应的结果，以重力匹配的结果为准输出订单，如果两方均无法准确匹配出异常订单
                //查询设备库存商品
                logger.info("重力视觉分层，开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品");
                if (null != subVoList & !subVoList.isEmpty()) {
                    logger.info("重力视觉分层，开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->视觉有减少");
                    return layeredGeOrder2(iele, deviceInfo, memberInfo, subVoList, layeredInventoryDto, subResult, subTotal, 10, floatResult, visionList);
                } else {
                    //匹配商品
                    logger.info("重力视觉分层，开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->视觉不变或增多");
                    return layeredGeOrder2(iele, deviceInfo, memberInfo, subVoList, layeredInventoryDto, subResult, null, 20, null, visionList);
                }
            }
        }
        InventoryResult result = new InventoryResult();
        result.setMerchantCode(deviceInfo.getSmerchantCode());
        result.setItype(50);//处理成功
        responseVo.setData(result);
        return responseVo;
    }


    /**
     * 组装数据assembly
     *
     * @param openDoorMap
     * @param inventoryDto
     * @param deviceInfo
     * @return
     */
    private Map<String, Object> assemblyData(Map<String, Integer> openDoorMap, InventoryDto inventoryDto, DeviceInfo deviceInfo) {
        logger.info("重力视觉柜计算开关门商品差异：{}", deviceInfo.getScode());
        Map<String, Integer> closeDoorMap = new HashMap<>();  // 临时存放从云端返回的图片中商品集合
        // 遍历云端返回response
        if (CollectionUtils.isNotEmpty(inventoryDto.getCommodityVos())) {
            for (CommodityVo good : inventoryDto.getCommodityVos()) {
                String vrCode = good.getSvrCode();       // 商品视觉编号
                Integer num = good.getCommodityNum();          // 商品数量
                if (!closeDoorMap.containsKey(vrCode)) {
                    closeDoorMap.put(vrCode, num);
                } else {
                    Integer tempNum = closeDoorMap.get(vrCode);
                    closeDoorMap.put(vrCode, tempNum + num);
                }
            }
        }
        logger.info("重力视觉柜计算开关门对比差异，关门商品数据,{}", closeDoorMap);
        // 开关门前后都有商品，计算增加与减少的商品信息
        if (null == closeDoorMap) {
            closeDoorMap = new HashMap<String, Integer>();
        }
        if (null == openDoorMap) {
            openDoorMap = new HashMap<String, Integer>();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        List<CommodityDiffVo> subVoList = new ArrayList<CommodityDiffVo>();
        List<CommodityDiffVo> addVoList = new ArrayList<CommodityDiffVo>();
        for (Map.Entry<String, Integer> openDoor : openDoorMap.entrySet()) {
            CommodityDiffVo tempDiffVo = null;
            String openId = openDoor.getKey();
            Integer openNum = openDoor.getValue() != null ? openDoor.getValue() : 0;
            Integer closeNum = 0;
            if (closeDoorMap.containsKey(openId)) {//如果关门存在开门时的商品
                closeNum = closeDoorMap.get(openId) != null ? closeDoorMap.get(openId) : 0;
            }
            Integer resultNum = openNum - closeNum;
            tempDiffVo = assemblyVisionTemp(resultNum, openId, deviceInfo, closeNum);
            if (null == tempDiffVo) {
                continue;
            }
            if (resultNum == 0) { // 商品没变化
                continue;
            } else if (resultNum < 0) { // 商品增加
                addVoList.add(tempDiffVo);
            } else {    // 商品减少
                subVoList.add(tempDiffVo);
            }
        }
        for (Map.Entry<String, Integer> closeDoor : closeDoorMap.entrySet()) {
            String closeId = closeDoor.getKey();
            Integer closeNum = closeDoor.getValue() != null ? closeDoor.getValue() : 0;
            if (!openDoorMap.containsKey(closeId)) {//如果关门不存在开门时的商品 新增
                CommodityDiffVo tempDiffVo = assemblyVisionTemp(-closeNum, closeId, deviceInfo, closeNum);
                if (null == tempDiffVo) {
                    continue;
                }
                addVoList.add(tempDiffVo);
            }
        }
        map.put("subVoList", subVoList);
        map.put("addVoList", addVoList);
        return map;
    }

    /**
     * 组装差异值
     *
     * @param resultNum
     * @param openId
     * @param deviceInfo
     * @return
     */
    private CommodityDiffVo assemblyVisionTemp(Integer resultNum, String openId, DeviceInfo deviceInfo, Integer closeNum) {
        CommodityDiffVo tempDiffVo = new CommodityDiffVo();
        tempDiffVo.setSvrCode(openId);
        CommodityInfo commodityInfo = commodityInfoService.selectByVrCode(openId, deviceInfo.getSmerchantId());
        if (null != commodityInfo && commodityInfo.getIstatus() == 10 && commodityInfo.getIdelFlag() == 0) {
            tempDiffVo.setScommodityCode(commodityInfo.getScode());
            tempDiffVo.setScommodityId(commodityInfo.getId());
            tempDiffVo.setScommodityFullName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
            tempDiffVo.setScommodityName(commodityInfo.getSname());
            tempDiffVo.setFcostPrice(commodityInfo.getFcostPrice());
            tempDiffVo.setIstatus(commodityInfo.getIstatus());
            tempDiffVo.setSpackageUnit(commodityInfo.getSpackageUnit());
            tempDiffVo.setSbrandId(commodityInfo.getSbrandId());
            tempDiffVo.setSsmallCategoryId(commodityInfo.getSsmallCategoryId());
            tempDiffVo.setScommodityImg(commodityInfo.getScommodityImg());
            tempDiffVo.setIweigth(commodityInfo.getIweigth());
            tempDiffVo.setIcommodityFloat(commodityInfo.getIcommodityFloat());
            if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 10) {
                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "天");
            } else if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 20) {
                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "个月");
            }
        } else {
            logger.error("视觉盘点商品不存在，商户编号-视觉编号：{}", deviceInfo.getSmerchantCode() + "-" + openId);
            return null;
        }
        if (null != resultNum && resultNum > 0) {//商品减少
            tempDiffVo.setItype(20);
            tempDiffVo.setNumber(resultNum);
            tempDiffVo.setFcommodityPrice(commodityInfo.getFsalePrice());
            tempDiffVo.setCurrStock(closeNum);
        } else if (null != resultNum && resultNum < 0) { //商品增加
            tempDiffVo.setItype(10);
            tempDiffVo.setNumber(Math.abs(resultNum));
            //获取商品库存销售价格
            DeviceStock deviceStock = this.selectDeviceStockByCommodityId(deviceInfo.getId(), commodityInfo.getId());
            tempDiffVo.setFcommodityPrice(deviceStock.getFsalePrice());
            tempDiffVo.setCurrStock(Math.abs(closeNum));
        }
        return tempDiffVo;
    }

    /**
     * 重力视觉柜生成订单
     *
     * @param deviceInfo
     * @param memberInfo
     * @param subVoList
     * @param inventoryDto
     * @param subResult
     * @param subTotal
     * @param type         10:重力减少,视觉减少  20:重力减少,视觉不变或增多
     * @param visionList   视觉List<>
     * @return
     */
    private ResponseVo<InventoryResult> geOrder(DeviceInfo deviceInfo, MemberInfo
            memberInfo, List<CommodityDiffVo> subVoList, InventoryDto inventoryDto, BigDecimal subResult,
                                                BigDecimal subTotal, Integer type, BigDecimal floatResult, List<String> visionList) {
        logger.info("视觉识别结果：{}", visionList);
        BigDecimal diffData = BigDecimal.ZERO;
        Boolean flagTemp = false;//是否仅以重力为主匹配
        logger.info("重力视觉柜生成订单，subResult:" + subResult + "##subTotal：" + subTotal);
        if (type == 10) {
            diffData = subResult.subtract(subTotal);
            if (diffData.compareTo(BigDecimal.ZERO) < 0) {
                diffData = subResult;
                flagTemp = true;
                logger.info("重力减少的比视觉减少的还要少");
            }
        } else {
            flagTemp = true;
            diffData = subResult;
        }
        logger.info("计算匹配商品重量：{}", diffData);
        BigDecimal iele = deviceInfo.getIelectronicFloat();
        if (null == iele) {
            iele = BigDecimal.ZERO;
        }
        List<StockVo> diffStocks = this.selectGravityStockByDeviceId(deviceInfo.getId(), diffData.add(iele));
        logger.info("重力视觉柜生成订单，匹配商品:{}", diffStocks);
        if (null != diffStocks && !diffStocks.isEmpty()) {
            //循环库存商品  匹配单一商品
            //匹配大于一种或者没有匹配到为false  匹配到有且只有一种商品满足为true
            BigDecimal subNum = BigDecimal.ZERO;
            List<String> gravityList = new ArrayList();
            for (int i = 0; i < diffStocks.size(); i++) {
                //重量差%商品重量的值  divResults[0] 为商  divResults[1] 为余数
                StockVo stockVo = diffStocks.get(i);
                //如果余数小于等于商品的误差值,证明商品重量可以匹配正确,此时商为可匹配商品的数量
                BigDecimal icommodityFloat = stockVo.getIcommodityFloat();
                if (null == icommodityFloat) {
                    icommodityFloat = BigDecimal.ZERO;
                }
                BigDecimal[] divResults = diffData.divideAndRemainder(stockVo.getIweigth());
                if (divResults[1].compareTo(icommodityFloat.add(iele)) <= 0 || diffData.subtract(stockVo.getIweigth()).abs().compareTo(icommodityFloat.add(iele)) <= 0) {
                    if (divResults[0].compareTo(BigDecimal.ZERO) == 0) {
                        subNum = BigDecimal.ONE;
                    } else {
                        subNum = divResults[0];
                    }
                    gravityList.add(stockVo.getSvrCode() + "_" + subNum + "_" + i);
                } else if (divResults[0] != BigDecimal.ZERO) {
                    BigDecimal temp = diffData.subtract(divResults[0].add(BigDecimal.ONE).multiply(stockVo.getIweigth()));
                    if (temp.abs().compareTo(icommodityFloat.add(iele)) <= 0) {
                        subNum = divResults[0].add(BigDecimal.ONE);
                        gravityList.add(stockVo.getSvrCode() + "_" + subNum + "_" + i);
                    }
                }
            }
            if (gravityList.size() == 1) {
                //在误差范围内,出订单
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->在误差范围内,匹配商品:{}", gravityList);
                String gravityResult = gravityList.get(0);
                String svrCode = gravityResult.split("_")[0];
                subNum = new BigDecimal(gravityResult.split("_")[1]);
                Integer size = new Integer(gravityResult.split("_")[2]);
                logger.info("重力匹配的商品视觉编号为：{}", svrCode);
              /*  if (visionList.contains(svrCode)) {*/
                logger.info("库库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->重力匹配视觉商品匹配到唯一值,出订单:{}", svrCode);
                StockVo stockVo = diffStocks.get(size);
                subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                        subVoList, subNum);
                //生成购物订单
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
             /*   } else {
                    logger.info("库库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->重力匹配视觉商品未匹配到,出异常订单:{}", gravityList);
                    generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 40);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单失败,生成异常订单");
                }*/
            } else if (gravityList.size() == 0) {
                //没有匹配到商品,出异常订单
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->没有匹配到商品,出异常订单:{}");
                generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 40);
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单失败,生成异常订单");
            } else {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理:{}", gravityList);
                List<String> result = new ArrayList<>();
                for (int x = 0; x < gravityList.size(); x++) {
                    String gravityResult = gravityList.get(x);
                    String svrCode = gravityResult.split("_")[0];
                    subNum = new BigDecimal(gravityResult.split("_")[1]);
                    Integer size = new Integer(gravityResult.split("_")[2]);
                    if (visionList.contains(svrCode)) {
                        result.add(subNum + "_" + size);
                    }
                }
                if (result.size() == 1) {
                    logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->只有一个满足:{}", result);
                    String resultData = result.get(0);
                    subNum = new BigDecimal(resultData.split("_")[0]);
                    Integer size = new Integer(resultData.split("_")[1]);
                    StockVo stockVo = diffStocks.get(size);
                    subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                            subVoList, subNum);
                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                } else {
                    //没有匹配到商品,出异常订单
                    logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->没有满足的", result);
                    generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 40);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单失败,生成异常订单");
                }
            }
        } else {
            if (type == 10 && !flagTemp) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉有减少（重力减少比视觉大）,以视觉减少为准出订单！", subVoList);
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            } else if (type == 10 && flagTemp) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉有减少,游客订单！", flagTemp);
            } else if (type == 20) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉没减少,游客订单！", subVoList);
            } else {
                //库存没有匹配的商品,出异常订单
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->库存商品重量大于差值,出异常订单");
            }
            ResponseVo<InventoryResult> responseVo = ResponseVo.getSuccessResponse();
            InventoryResult result = new InventoryResult();
            result.setMerchantCode(deviceInfo.getSmerchantCode());
            result.setItype(50);//处理成功
            responseVo.setData(result);
            return responseVo;
        }
    }

    /**
     * 重力视觉柜分层生成订单
     *
     * @param deviceInfo
     * @param memberInfo
     * @param subVoList
     * @param layeredInventoryDto
     * @param subResult
     * @param subTotal
     * @param type                10:重力减少,视觉减少  20:重力减少,视觉不变或增多
     * @param visionList          视觉List<>
     * @return
     */
    private ResponseVo<InventoryResult> layeredGeOrder(BigDecimal iele, DeviceInfo deviceInfo, MemberInfo
            memberInfo, List<CommodityDiffVo> subVoList, LayeredInventoryDto layeredInventoryDto, BigDecimal subResult,
                                                       BigDecimal subTotal, Integer type, BigDecimal floatResult, List<String> visionList) {
        logger.info("视觉识别结果：{}", visionList);
        BigDecimal diffData = BigDecimal.ZERO;
        Boolean flagTemp = false;//是否仅以重力为主匹配
        logger.info("重力视觉柜生成订单，subResult:" + subResult + "##subTotal：" + subTotal);
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setIsourceClientType(layeredInventoryDto.getIsourceClientType());
        if (type == 10) {
            diffData = subResult.subtract(subTotal);
            if (diffData.compareTo(BigDecimal.ZERO) < 0) {
                diffData = subResult;
                flagTemp = true;
                logger.info("重力减少的比视觉减少的还要少");
            }
        } else {
            flagTemp = true;
            diffData = subResult;
        }
        logger.info("计算匹配商品重量：{}", diffData);
        List<StockVo> diffStocks = this.selectGravityStockByDeviceId(deviceInfo.getId(), diffData.add(iele));
        logger.info("分层购物实时订单-->重力视觉柜生成订单，重量匹配商品:{}", diffStocks);
        logger.info("分层购物实时订单-->重力视觉柜生成订单，视觉匹配商品:{}", visionList);
        if (null != diffStocks && !diffStocks.isEmpty()) {
            if (type == 10) {
                logger.info("视觉减少不为空,开始匹配商品：{}", type);
                //视觉有减少,遍历出在视觉变化里的商品集合
                if (flagTemp) {
                    Iterator<StockVo> it = diffStocks.iterator();
                    while (it.hasNext()) {
                        StockVo stockVo = it.next();
                        if (!visionList.contains(stockVo.getSvrCode())) {
                            it.remove();
                        }
                    }
                }
                logger.info("重量匹配商品对比视觉减少的商品：{}", diffStocks);
                if (diffStocks.isEmpty() || diffStocks.size() == 1) {
                    return getOrder(diffStocks, diffData, iele, deviceInfo, subVoList, memberInfo, flagTemp, visionList, inventoryDto);
                }
                //循环库存商品  匹配单一商品
                //匹配大于一种或者没有匹配到为false  匹配到有且只有一种商品满足为true
                BigDecimal subNum = BigDecimal.ZERO;
                List<String> gravityList = new ArrayList();
                //循环库存商品  匹配单一商品
                //匹配大于一种或者没有匹配到为false  匹配到有且只有一种商品满足为true
                for (int i = 0; i < diffStocks.size(); i++) {
                    StockVo stockVo = diffStocks.get(i);
                    //如果余数小于等于商品的误差值,证明商品重量可以匹配正确,此时商为可匹配商品的数量
                    BigDecimal[] divResults = diffData.divideAndRemainder(stockVo.getIweigth());
                    subNum = divResults[0];
                    logger.info("视觉减少不为空,开始匹配商品,商品组成订单的个数最多为：{}", subNum);
                    break;
                }
                boolean variedFlag = false;
                for (int x = 0; x < diffStocks.size(); x++) {
                    for (int y = 1; y < diffStocks.size(); y++) {
                        for (int i = 1; i < subNum.intValue(); i++) {
                            StockVo stockVo = diffStocks.get(x);
                            if (stockVo.getIstock() < i) {
                                break;
                            }
                            //两个商品总的重量
                            BigDecimal stockVoiweightl = stockVo.getIweigth().multiply(new BigDecimal(i));
                            if (diffData.subtract(stockVoiweightl).compareTo(BigDecimal.ZERO) < 0
                                    /*diffData.subtract(stockVoiweightl).abs().compareTo(iele.add(stockVo.getIcommodityFloat())) >= 0*/) {
                                break;
                            }
                            for (int z = 1; z <= subNum.intValue() - i; z++) {
                                StockVo stockVo2 = diffStocks.get(y);
                                if (stockVo2.getIstock() < z) {
                                    break;
                                }
                                //两个商品总的重量
                                BigDecimal stockVoiweight2 = stockVo2.getIweigth().multiply(new BigDecimal(z));
                                BigDecimal stockWeightTotal = stockVoiweightl.add(stockVoiweight2);
                                //总浮动值
                                BigDecimal icommodityFloat = stockVo.getIcommodityFloat().add(stockVo2.getIcommodityFloat());
                                if (diffData.subtract(stockWeightTotal).compareTo(iele.add(icommodityFloat)) <= 0) {
                                    //确认商品
                                    variedFlag = true;
                                    subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                                            subVoList, new BigDecimal(i));
                                    subVoList = getSubList(stockVo2, deviceInfo, false,
                                            subVoList, new BigDecimal(z));
                                  /*  subVoList = getNewSubList(stockVo, new BigDecimal(i), deviceInfo, null);
                                    subVoList = getNewSubList(stockVo2, new BigDecimal(z), deviceInfo, subVoList);*/
                                    //生成订单
                                    logger.info("多商品可以匹配成功" + i + "个" + stockVo + "#####" + z + "个" + stockVo2);
                                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                                } else if (diffData.subtract(stockWeightTotal).compareTo(BigDecimal.ZERO) < 0
                                        /*diffData.subtract(stockWeightTotal).abs().compareTo(iele.add(icommodityFloat)) > 0*/) {
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!variedFlag) {
                    //未匹配到多种商品组合
                    logger.info("未匹配到多种商品的可能：{}", variedFlag);
                    return getOrder(diffStocks, diffData, iele, deviceInfo, subVoList, memberInfo, flagTemp, visionList, inventoryDto);
                }
            } else if (type == 20) {
                logger.info("视觉减少为空,开始匹配单个商品：{}", type);
                //未匹配到多种商品组合
                return getOrder(diffStocks, diffData, iele, deviceInfo, subVoList, memberInfo, flagTemp, visionList, inventoryDto);
            }
            logger.info("未匹配到商品,出异常订单type:{}", type);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单失败,生成异常订单");
        } else {
            if (type == 10 && !flagTemp) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉有减少（重力减少比视觉大）,以视觉减少为准出订单！", subVoList);
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            } else if (type == 10 && flagTemp) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉有减少,游客订单！", flagTemp);
            } else if (type == 20) {
                //查询最近几个月
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉没减少,游客订单！", subVoList);
                // return recentMonthInventory(iele, subResult, deviceInfo, memberInfo, inventoryDto);
            } else {
                //库存没有匹配的商品,出异常订单
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->库存商品重量大于差值,出异常订单");
            }
            ResponseVo<InventoryResult> responseVo = ResponseVo.getSuccessResponse();
            InventoryResult result = new InventoryResult();
            result.setMerchantCode(deviceInfo.getSmerchantCode());
            result.setItype(50);//处理成功
            responseVo.setData(result);
            return responseVo;
        }
    }

    /**
     * 重力视觉柜分层生成订单
     *
     * @param deviceInfo
     * @param memberInfo
     * @param subVoList
     * @param layeredInventoryDto
     * @param subResult
     * @param subTotal
     * @param type                10:重力减少,视觉减少  20:重力减少,视觉不变或增多
     * @param visionList          视觉List<>
     * @return
     */
    private ResponseVo<InventoryResult> layeredGeOrder2(BigDecimal iele, DeviceInfo deviceInfo, MemberInfo
            memberInfo, List<CommodityDiffVo> subVoList, LayeredInventoryDto layeredInventoryDto, BigDecimal subResult,
                                                        BigDecimal subTotal, Integer type, BigDecimal floatResult, List<String> visionList) {
        BigDecimal diffData = BigDecimal.ZERO;
        Boolean flagTemp = false;//是否仅以重力为主匹配
        logger.info("分层购物实时订单-->重力视觉柜生成订单，视觉匹配商品:{}", visionList);
        logger.info("重力视觉柜生成订单，subResult:" + subResult + "##subTotal：" + subTotal);
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setSext(layeredInventoryDto.getSext());
        inventoryDto.setIsourceClientType(layeredInventoryDto.getIsourceClientType());
        if (type == 10) {
            diffData = subResult.subtract(subTotal);
            if (diffData.compareTo(BigDecimal.ZERO) < 0) {
                diffData = subResult;
                flagTemp = true;
                logger.info("重力减少的比视觉减少的还要少");
            }
        } else {
            flagTemp = true;
            diffData = subResult;
        }
        logger.info("计算匹配商品重量：{}", diffData);
        ResponseVo<InventoryResult> resultResponseVoTemp = yanzheng(type, deviceInfo, diffData, iele, flagTemp,
                memberInfo, subVoList, subResult, subTotal, inventoryDto);
        if (null != resultResponseVoTemp) {
            return resultResponseVoTemp;
        }
        if (10 == type && !flagTemp) {
            logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品：{}", diffData);
            ResponseVo<InventoryResult> resultTemp = matchSingleGoods(deviceInfo, diffData, iele, subVoList, flagTemp, memberInfo, inventoryDto);
            if (null != resultTemp) {
                return resultTemp;
            }
            logger.info("重量减少大于视觉减少,根据重力减少部分匹配商品：{}", subResult);
            resultTemp = matchSingleGoods(deviceInfo, subResult, iele, subVoList, true, memberInfo, inventoryDto);
            if (null != resultTemp) {
                return resultTemp;
            }
            String temp = BizParaUtil.get("recent_month_inventory_goods");
            if (StringUtils.isBlank(temp)) {
                temp = "1";
            }
            Integer month = Integer.valueOf(temp);
        /*    temp = BizParaUtil.get("minimum_weight_percentage");
            if (StringUtils.isBlank(temp)) {
                temp = "80";
            }
            Integer percentage = Integer.valueOf(temp);*/
            List<StockVo> diffStocks = this.selectSellGoodsByDeviceId(deviceInfo.getId(), diffData.add(iele), month);
            logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,查询所有库存商品集合：{}", diffStocks);
            if (diffStocks != null && !diffStocks.isEmpty()) {
            /*    StockVo te = diffStocks.get(0);
                if (te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(diffData).compareTo(BigDecimal.ZERO) >= 0) {
                    logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,库存最小重量小于重力减少的值,以视觉为准出订单");
                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                } else if (subResult.compareTo(te.getIweigth().subtract(te.getIcommodityFloat().subtract(iele))) <= 0 && te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(subResult).compareTo(BigDecimal.ZERO) < 0) {
                    //确定就是这个商品
                    //出订单
                    subVoList = getSubList(te, deviceInfo, flagTemp,
                            subVoList, new BigDecimal(1));
                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                }*/
                if (diffStocks.size() == 1) {
                    ResponseVo<InventoryResult> resultResponseVo = getOrder2(diffStocks, diffData, iele, deviceInfo, subVoList, memberInfo, inventoryDto);
                    if (null != resultResponseVo) {
                        return resultResponseVo;
                    } else {
                        return layeredGeOrder3(iele, deviceInfo, subVoList, subResult, BigDecimal.ZERO, type, floatResult, memberInfo, visionList, flagTemp, inventoryDto);
                    }
                }
                BigDecimal subNum = BigDecimal.ZERO;
                //循环库存商品  匹配单一商品
                //匹配大于一种或者没有匹配到为false  匹配到有且只有一种商品满足为true
                for (int i = 0; i < diffStocks.size(); i++) {
                    StockVo stockVo = diffStocks.get(i);
                    //如果余数小于等于商品的误差值,证明商品重量可以匹配正确,此时商为可匹配商品的数量
                    BigDecimal[] divResults = diffData.divideAndRemainder(stockVo.getIweigth());
                    subNum = divResults[0];
                    logger.info("视觉减少不为空,开始匹配商品,商品组成订单的个数最多为：{}", subNum);
                    break;
                }
                boolean variedFlag = false;
                List<MultiCommodityMatch> multiCommodityMatches = new ArrayList<>();
                MultiCommodityMatch multiCommodityMatch = null;
                List<StockVo> stockVoList = null;
                for (int x = 0; x < diffStocks.size(); x++) {
                    for (int y = x + 1; y < diffStocks.size(); y++) {
                        for (int i = 1; i < subNum.intValue(); i++) {
                            StockVo stockVo = diffStocks.get(x);
                            //商品重量
                            BigDecimal stockVoiweightl = stockVo.getIweigth().multiply(new BigDecimal(i));
                            if (diffData.subtract(stockVoiweightl).compareTo(BigDecimal.ZERO) < 0) {
                                break;
                            }
                            for (int z = 1; z <= subNum.intValue() - i; z++) {
                                StockVo stockVo2 = diffStocks.get(y);
                                //两个商品总的重量
                                BigDecimal stockVoiweight2 = stockVo2.getIweigth().multiply(new BigDecimal(z));
                                BigDecimal stockWeightTotal = stockVoiweightl.add(stockVoiweight2);
                                //总浮动值
                                BigDecimal icommodityFloat = stockVo.getIcommodityFloat().add(stockVo2.getIcommodityFloat());
                                if (diffData.subtract(stockWeightTotal).abs().compareTo(iele.add(icommodityFloat)) <= 0) {
                                    //确认商品
                                    multiCommodityMatch = new MultiCommodityMatch();
                                    multiCommodityMatch.setVisionTotal(i + z);
                                    multiCommodityMatch.setStockTotal(stockVo.getIstock() + stockVo2.getIstock());
                                    multiCommodityMatch.setVisionValue(i + "_" + z);
                                    stockVoList = new ArrayList<>();
                                    stockVoList.add(stockVo);
                                    stockVoList.add(stockVo2);
                                    multiCommodityMatch.setStocks(stockVoList);
                                    multiCommodityMatches.add(multiCommodityMatch);
                                    variedFlag = true;
                                } else if (diffData.subtract(stockWeightTotal).compareTo(BigDecimal.ZERO) < 0) {
                                    break;
                                }
                            }
                        }
                    }
                }
                if (variedFlag) {
                    logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品，多商品匹配可以匹配出的集合：{}", multiCommodityMatches);
                    if (!multiCommodityMatches.isEmpty() && multiCommodityMatches.size() == 1) {
                        MultiCommodityMatch multiCommodityMatchTemp = multiCommodityMatches.get(0);
                        String value = multiCommodityMatchTemp.getVisionValue();
                        String[] str = value.split("_");
                        List<StockVo> stockVoListTemp = multiCommodityMatchTemp.getStocks();
                        subVoList = getSubList(stockVoListTemp.get(0), deviceInfo, false,
                                subVoList, new BigDecimal(str[0]));
                        subVoList = getSubList(stockVoListTemp.get(1), deviceInfo, false,
                                subVoList, new BigDecimal(str[1]));
                        //生成订单
                        logger.info("多商品可以匹配成功" + str[0] + "个" + stockVoListTemp.get(0) + "####" + str[1] + "个" + stockVoListTemp.get(1));

                        return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                    } else {
                        generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 40);
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单失败,生成异常订单");
                    }
                } else {
                    //重量未匹配到多种商品组合
                    logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品，多商品匹配不出来,匹配单个：{}", variedFlag);
                    ResponseVo<InventoryResult> resultResponseVo = getOrder2(diffStocks, diffData, iele, deviceInfo, subVoList, memberInfo, inventoryDto);

                    logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品，多商品匹配不出来,匹配单个，匹配结果：{}", resultResponseVo);
                    if (resultResponseVo != null) {
                        return resultResponseVo;
                    } else {
                        return layeredGeOrder3(iele, deviceInfo, subVoList, subResult, BigDecimal.ZERO, type, floatResult, memberInfo, visionList, flagTemp, inventoryDto);
                    }
                }
            } else {
                return layeredGeOrder3(iele, deviceInfo, subVoList, subResult, BigDecimal.ZERO, type, floatResult, memberInfo, visionList, flagTemp, inventoryDto);
            }
        } else if (10 == type && flagTemp) {
            logger.info("重量减少小于视觉减少,根据重力匹配：{}", diffData);
            logger.info("重量减少小于视觉减少,根据重力匹配,匹配单个商品：{}", diffData);
            ResponseVo<InventoryResult> resultTemp = matchSingleGoods(deviceInfo, diffData, iele, subVoList, flagTemp, memberInfo, inventoryDto);
            if (null != resultTemp) {
                return resultTemp;
            }
          /*  List<StockVo> diffStocksTemp = this.selectGravityStockByDeviceId(deviceInfo.getId(), diffData.add(iele));
            List<StockVo> stockVos = new ArrayList<>();
            if (diffStocksTemp != null && !diffStocksTemp.isEmpty()) {
                for (StockVo stockVo : diffStocksTemp) {
                    if (diffData.subtract(stockVo.getIweigth()).abs().compareTo(iele.add(stockVo.getIcommodityFloat())) <= 0) {
                        stockVos.add(stockVo);
                        //确定商品出订单
                    }
                }
            }
            logger.info("重量减少小于视觉减少,根据重力匹配,匹配单个商品,可以匹配出商品：{}", stockVos);
            if (!stockVos.isEmpty() && stockVos.size() == 1) {
                StockVo stockVo = stockVos.get(0);
                subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                        subVoList, new BigDecimal(1));
                logger.info("重量减少小于视觉减少,根据重力匹配,匹配单个商品,可以匹配出商品：{}", stockVo);
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            } else if (!stockVos.isEmpty() && stockVos.size() > 1) {
                logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,匹配单个商品,可以匹配出多个商品：{}", stockVos.size());
            }*/
            logger.info("重量减少小于视觉减少,根据重力匹配,视觉减少集合：{}", visionList);
            List<StockVo> diffStocks = this.selectGravityStockByDeviceId(deviceInfo.getId(), diffData.add(iele));
            logger.info("重量减少小于视觉减少,根据重力匹配,重量查询结果：{}", diffStocks);
            if (diffStocks != null && !diffStocks.isEmpty()) {
                Iterator<StockVo> it = diffStocks.iterator();
                while (it.hasNext()) {
                    StockVo stockVo = it.next();
                    if (!visionList.contains(stockVo.getSvrCode())) {
                        it.remove();
                    }
                }
                logger.info("重量减少小于视觉减少,根据重力匹配,重量查询结果后删除视觉以外的商品：{}", diffStocks);
                if (diffStocks.size() <= 1) {
                    return layeredGeOrder3(iele, deviceInfo, subVoList, subResult, subTotal, type, floatResult, memberInfo, visionList, flagTemp, inventoryDto);

                }
                BigDecimal bigDecimalTemp = diffStocks.get(0).getIweigth();
                logger.info("视觉减少不为空,开始匹配商品,删除视觉以外的商品后最小的商品重量：{}", bigDecimalTemp);
                List<StockVo> diffStocksFinal = new ArrayList<>();
                for (int x = 0; x < visionList.size(); x++) {
                    for (int y = 0; y < diffStocks.size(); y++) {
                        StockVo stockVo = diffStocks.get(y);
                        if (stockVo.getSvrCode().equals(visionList.get(x))) {
                            diffStocksFinal.add(stockVo);
                            break;
                        }
                    }
                }
                logger.info("重量减少小于视觉减少,根据重力匹配,重量查询结果后删除视觉以外的商品并根据视觉减少的排序后：{}", diffStocksFinal);
                BigDecimal subNum = BigDecimal.ZERO;
                //循环库存商品  匹配单一商品
                //匹配大于一种或者没有匹配到为false  匹配到有且只有一种商品满足为true
                //如果余数小于等于商品的误差值,证明商品重量可以匹配正确,此时商为可匹配商品的数量
                BigDecimal[] divResults = diffData.divideAndRemainder(bigDecimalTemp);
                subNum = divResults[0];
                logger.info("视觉减少不为空,开始匹配商品,商品组成订单的个数最多为：{}", subNum);
                boolean variedFlag = false;
                for (int x = 0; x < diffStocksFinal.size(); x++) {
                    for (int y = x + 1; y < diffStocksFinal.size(); y++) {
                        for (int i = 1; i < subNum.intValue(); i++) {
                            StockVo stockVo = diffStocksFinal.get(x);
                            if (stockVo.getIstock() < i) {
                                break;
                            }
                            //商品重量
                            BigDecimal stockVoiweightl = stockVo.getIweigth().multiply(new BigDecimal(i));
                            if (diffData.subtract(stockVoiweightl).compareTo(BigDecimal.ZERO) < 0) {
                                break;
                            }
                            for (int z = 1; z <= subNum.intValue() - i; z++) {
                                StockVo stockVo2 = diffStocksFinal.get(y);
                                if (stockVo2.getIstock() < z) {
                                    break;
                                }
                                //两个商品总的重量
                                BigDecimal stockVoiweight2 = stockVo2.getIweigth().multiply(new BigDecimal(z));
                                BigDecimal stockWeightTotal = stockVoiweightl.add(stockVoiweight2);
                                //总浮动值
                                BigDecimal icommodityFloat = stockVo.getIcommodityFloat().add(stockVo2.getIcommodityFloat());
                                if (diffData.subtract(stockWeightTotal).abs().compareTo(iele.add(icommodityFloat)) <= 0) {
                                    //把视觉减少的list转换成map<k,v> k为视觉编号,v为CommodityDiffVo
                                    Map<String, CommodityDiffVo> subCommodityDiffVoMap = subVoList.stream().collect(
                                            Collectors.toMap(CommodityDiffVo::getSvrCode, (p) -> p));
                                    CommodityDiffVo commodityDiffVo = subCommodityDiffVoMap.get(stockVo.getSvrCode());
                                    CommodityDiffVo commodityDiffVo1 = subCommodityDiffVoMap.get(stockVo2.getSvrCode());
                                    if (i <= commodityDiffVo.getNumber() && z <= commodityDiffVo1.getNumber()) {
                                        //确认商品
                                        variedFlag = true;
                                        subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                                                subVoList, new BigDecimal(i));
                                        subVoList = getSubList(stockVo2, deviceInfo, false,
                                                subVoList, new BigDecimal(z));
                                        //生成订单
                                        logger.info("多商品可以匹配成功" + i + "个" + stockVo + "####" + z + "个" + stockVo2);
                                        return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                                    }
                                } else if (diffData.subtract(stockWeightTotal).compareTo(BigDecimal.ZERO) < 0) {
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!variedFlag) {
                    logger.info("重量减少小于视觉减少,根据重力匹配,多商品未匹配成功：{}", variedFlag);
                    return layeredGeOrder3(iele, deviceInfo, subVoList, subResult, subTotal, type, floatResult, memberInfo, visionList, flagTemp, inventoryDto);
                }
            } else {
                return layeredGeOrder3(iele, deviceInfo, subVoList, subResult, subTotal, type, floatResult, memberInfo, visionList, flagTemp, inventoryDto);
            }
        } else if (type == 20) {
            logger.info("重量有减少,视觉为空,根据重量减少匹配：{}", diffData);
            logger.info("重量有减少,视觉为空,匹配单个商品：{}", diffData);
            ResponseVo<InventoryResult> resultTemp = matchSingleGoods(deviceInfo, diffData, iele, subVoList, flagTemp, memberInfo, inventoryDto);
            if (null != resultTemp) {
                return resultTemp;
            }
          /*  List<StockVo> diffStocksTemp = this.selectGravityStockByDeviceId(deviceInfo.getId(), diffData.add(iele));
            List<StockVo> stockVos = new ArrayList<>();
            if (diffStocksTemp != null && !diffStocksTemp.isEmpty()) {
                for (StockVo stockVo : diffStocksTemp) {
                    if (diffData.subtract(stockVo.getIweigth()).abs().compareTo(iele.add(stockVo.getIcommodityFloat())) <= 0) {
                        stockVos.add(stockVo);
                    }
                }
            }
            logger.info("重量有减少,视觉为空,根据重量减少匹配,匹配单个商品,可以匹配出商品：{}", stockVos);
            if (!stockVos.isEmpty() && stockVos.size() == 1) {
                StockVo stockVo = stockVos.get(0);
                subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                        subVoList, new BigDecimal(1));
                logger.info("重量有减少,视觉为空,根据重量减少匹配,匹配单个商品,匹配出商品：{}", stockVo);
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            } else if (!stockVos.isEmpty() && stockVos.size() > 1) {
                logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,匹配单个商品,可以匹配出多个商品：{}", stockVos.size());
            }*/
            return layeredGeOrder3(iele, deviceInfo, subVoList, subResult, subTotal, type, floatResult, memberInfo, visionList, flagTemp, inventoryDto);
        }
        ResponseVo<InventoryResult> responseVo = ResponseVo.getSuccessResponse();
        InventoryResult result = new InventoryResult();
        result.setMerchantCode(deviceInfo.getSmerchantCode());
        result.setItype(50);//处理成功
        responseVo.setData(result);
        return responseVo;
    }

    /**
     * 查询匹配最近几个月内设备售出的最小重量的商品
     *
     * @param subResult
     * @param deviceInfo
     * @param memberInfo
     * @param inventoryDto
     * @return
     */
    private ResponseVo<InventoryResult> recentMonthInventory(BigDecimal iele, BigDecimal subResult, DeviceInfo deviceInfo, MemberInfo memberInfo, InventoryDto inventoryDto) {
        String temp = BizParaUtil.get("recent_month_inventory_goods");
        if (StringUtils.isBlank(temp)) {
            temp = "1";
        }
        Integer month = Integer.valueOf(temp);
        List<StockVo> diffStocks = this.selectSellGoodsByDeviceId(deviceInfo.getId(), subResult.add(iele), month);
        BigDecimal subNum = BigDecimal.ZERO;
        for (int i = 0; i < diffStocks.size(); i++) {
            StockVo stockVo = diffStocks.get(i);
            //如果余数小于等于商品的误差值,证明商品重量可以匹配正确,此时商为可匹配商品的数量
            BigDecimal[] divResults = subResult.divideAndRemainder(stockVo.getIweigth());
            subNum = divResults[0];
            logger.info("视觉减少不为空,开始匹配商品,商品组成订单的个数最多为：{}", subNum);
            break;
        }
        List<CommodityDiffVo> subVoList = new ArrayList();
        Boolean variedFlag = false;
        for (int x = 0; x < diffStocks.size(); x++) {
            for (int y = 1; y < diffStocks.size(); y++) {
                for (int i = 1; i < subNum.intValue(); i++) {
                    StockVo stockVo = diffStocks.get(x);
                    //两个商品总的重量
                    BigDecimal stockVoiweightl = stockVo.getIweigth().multiply(new BigDecimal(i));
                    if (subResult.subtract(stockVoiweightl).compareTo(BigDecimal.ZERO) < 0) {
                        break;
                    }
                    for (int z = 1; z <= subNum.intValue() - i; z++) {
                        StockVo stockVo2 = diffStocks.get(y);
                        //两个商品总的重量
                        BigDecimal stockVoiweight2 = stockVo2.getIweigth().multiply(new BigDecimal(z));
                        BigDecimal stockWeightTotal = stockVoiweightl.add(stockVoiweight2);
                        //总浮动值
                        BigDecimal icommodityFloat = stockVo.getIcommodityFloat().add(stockVo2.getIcommodityFloat());
                        if (subResult.subtract(stockWeightTotal).compareTo(iele.add(icommodityFloat)) <= 0) {
                            //确认商品
                            variedFlag = true;
                            subVoList = getSubList(stockVo, deviceInfo, true,
                                    new ArrayList<CommodityDiffVo>(), new BigDecimal(i));
                            subVoList = getSubList(stockVo2, deviceInfo, false,
                                    subVoList, new BigDecimal(z));
                            //生成订单
                            logger.info("多商品可以匹配成功" + i + "个" + stockVo + "#####" + z + "个" + stockVo2);
                            return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                        } else if (subResult.subtract(stockWeightTotal).compareTo(BigDecimal.ZERO) < 0) {
                            break;
                        }
                    }
                }
            }
        }
        String minimumWeight = BizParaUtil.get("minimum_weight_percentage");

        return null;
    }

    /**
     * 获得SubList
     *
     * @param deviceInfo
     * @param flagTemp
     * @param subVoList
     * @param subNum
     * @return
     */
    private List<CommodityDiffVo> getSubList(StockVo stockVo, DeviceInfo deviceInfo, Boolean flagTemp,
                                             List<CommodityDiffVo> subVoList, BigDecimal subNum) {

        CommodityDiffVo tempDiffVo = new CommodityDiffVo();
        if (!stockVo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return null;
        }
        CommodityInfo commodityInfo = commodityInfoService.selectByVrCode(stockVo.getSvrCode(), deviceInfo.getSmerchantId());
        if (flagTemp) {
            logger.info("差异巨大出订单,重力减少的比视觉减少的少,仅以重力匹配为主！");
            subVoList = new ArrayList<CommodityDiffVo>();
            tempDiffVo.setScommodityCode(commodityInfo.getScode());
            tempDiffVo.setScommodityId(commodityInfo.getId());
            tempDiffVo.setScommodityFullName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
            tempDiffVo.setScommodityName(commodityInfo.getSname());
            tempDiffVo.setFcostPrice(commodityInfo.getFcostPrice());
            tempDiffVo.setIstatus(commodityInfo.getIstatus());
            tempDiffVo.setSpackageUnit(commodityInfo.getSpackageUnit());
            tempDiffVo.setSbrandId(commodityInfo.getSbrandId());
            tempDiffVo.setSsmallCategoryId(commodityInfo.getSsmallCategoryId());
            tempDiffVo.setScommodityImg(commodityInfo.getScommodityImg());
            tempDiffVo.setIweigth(commodityInfo.getIweigth());
            tempDiffVo.setIcommodityFloat(commodityInfo.getIcommodityFloat());
            if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 10) {
                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "天");
            } else if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 20) {
                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "个月");
            }
            tempDiffVo.setItype(20);
            tempDiffVo.setFcommodityPrice(stockVo.getFsalePrice());
            tempDiffVo.setNumber(subNum.intValue());//减少的商品数量
            tempDiffVo.setCurrStock(stockVo.getIstock());
            tempDiffVo.setSvrCode(commodityInfo.getSvrCode());
            subVoList.add(tempDiffVo);
        } else {
            logger.info("差异巨大出订单,重力减少的比视觉减少的多,可以匹配到商品,在原有订单上修改！");
            if (subVoList.isEmpty()) {
                logger.info("差异巨大出订单,重力减少的比视觉减少的多,可以匹配到商品,在原有订单上修改-->视觉订单订单为空,新增！");
                tempDiffVo.setScommodityCode(commodityInfo.getScode());
                tempDiffVo.setScommodityId(commodityInfo.getId());
                tempDiffVo.setScommodityFullName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
                tempDiffVo.setScommodityName(commodityInfo.getSname());
                tempDiffVo.setFcostPrice(commodityInfo.getFcostPrice());
                tempDiffVo.setIstatus(commodityInfo.getIstatus());
                tempDiffVo.setSpackageUnit(commodityInfo.getSpackageUnit());
                tempDiffVo.setSbrandId(commodityInfo.getSbrandId());
                tempDiffVo.setSsmallCategoryId(commodityInfo.getSsmallCategoryId());
                tempDiffVo.setScommodityImg(commodityInfo.getScommodityImg());
                tempDiffVo.setIweigth(commodityInfo.getIweigth());
                tempDiffVo.setIcommodityFloat(commodityInfo.getIcommodityFloat());
                if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 10) {
                    tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "天");
                } else if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 20) {
                    tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "个月");
                }
                tempDiffVo.setItype(20);
                tempDiffVo.setFcommodityPrice(stockVo.getFsalePrice());
                tempDiffVo.setNumber(subNum.intValue());//减少的商品数量
                tempDiffVo.setCurrStock(stockVo.getIstock());
                tempDiffVo.setSvrCode(commodityInfo.getSvrCode());
                subVoList.add(tempDiffVo);
            } else {
                logger.info("差异巨大出订单,重力减少的比视觉减少的多,可以匹配到商品,在原有订单上修改-->原视觉不为空！");

                Integer size = null;
                for (int x = 0; x < subVoList.size(); x++) {
                    CommodityDiffVo commodityDiffVo = subVoList.get(x);
                    if (commodityDiffVo.getSvrCode().equals(commodityInfo.getSvrCode())) {
                        size = x;
                        break;
                    }
                }
                if (null == size) {
                    //新增
                    tempDiffVo.setScommodityCode(commodityInfo.getScode());
                    tempDiffVo.setScommodityId(commodityInfo.getId());
                    tempDiffVo.setScommodityFullName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
                    tempDiffVo.setScommodityName(commodityInfo.getSname());
                    tempDiffVo.setFcostPrice(commodityInfo.getFcostPrice());
                    tempDiffVo.setIstatus(commodityInfo.getIstatus());
                    tempDiffVo.setSpackageUnit(commodityInfo.getSpackageUnit());
                    tempDiffVo.setSbrandId(commodityInfo.getSbrandId());
                    tempDiffVo.setSsmallCategoryId(commodityInfo.getSsmallCategoryId());
                    tempDiffVo.setScommodityImg(commodityInfo.getScommodityImg());
                    tempDiffVo.setIweigth(commodityInfo.getIweigth());
                    tempDiffVo.setIcommodityFloat(commodityInfo.getIcommodityFloat());
                    if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 10) {
                        tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "天");
                    } else if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 20) {
                        tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "个月");
                    }
                    tempDiffVo.setItype(20);
                    tempDiffVo.setFcommodityPrice(stockVo.getFsalePrice());
                    tempDiffVo.setNumber(subNum.intValue());//减少的商品数量
                    tempDiffVo.setCurrStock(stockVo.getIstock());
                    tempDiffVo.setSvrCode(commodityInfo.getSvrCode());
                    subVoList.add(tempDiffVo);
                } else {
                    //修改
                    CommodityDiffVo commodityDiffVo = subVoList.get(size);
                    commodityDiffVo.setNumber(subNum.intValue() + commodityDiffVo.getNumber());
                }
              /*  synchronized (subVoList) {
                    ListIterator<CommodityDiffVo> commodityDiffVoListIterator = subVoList.listIterator();
                    while (commodityDiffVoListIterator.hasNext()) {
                        CommodityDiffVo commodityDiffVo = commodityDiffVoListIterator.next();
                        if (commodityDiffVo.getSvrCode().equals(commodityInfo.getSvrCode())) {
                            commodityDiffVo.setNumber(subNum.intValue() + commodityDiffVo.getNumber());
                        } else {
                            tempDiffVo.setScommodityCode(commodityInfo.getScode());
                            tempDiffVo.setScommodityId(commodityInfo.getId());
                            tempDiffVo.setScommodityFullName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
                            tempDiffVo.setScommodityName(commodityInfo.getSname());
                            tempDiffVo.setFcostPrice(commodityInfo.getFcostPrice());
                            tempDiffVo.setIstatus(commodityInfo.getIstatus());
                            tempDiffVo.setSpackageUnit(commodityInfo.getSpackageUnit());
                            tempDiffVo.setSbrandId(commodityInfo.getSbrandId());
                            tempDiffVo.setSsmallCategoryId(commodityInfo.getSsmallCategoryId());
                            tempDiffVo.setScommodityImg(commodityInfo.getScommodityImg());
                            tempDiffVo.setIweigth(commodityInfo.getIweigth());
                            tempDiffVo.setIcommodityFloat(commodityInfo.getIcommodityFloat());
                            if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 10) {
                                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "天");
                            } else if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 20) {
                                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "个月");
                            }
                            tempDiffVo.setItype(20);
                            tempDiffVo.setFcommodityPrice(stockVo.getFsalePrice());
                            tempDiffVo.setNumber(subNum.intValue());//减少的商品数量
                            tempDiffVo.setCurrStock(stockVo.getIstock());
                            subVoList.add(tempDiffVo);
                        }
                    }
                }*/
            }
          /*  for (CommodityDiffVo commodityDiffVo : subVoList) {
                if (commodityDiffVo.getSvrCode().equals(commodityInfo.getSvrCode())) {
                    commodityDiffVo.setNumber(subNum.intValue() + commodityDiffVo.getNumber());
                } else {
                    tempDiffVo.setScommodityCode(commodityInfo.getScode());
                    tempDiffVo.setScommodityId(commodityInfo.getId());
                    tempDiffVo.setScommodityFullName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
                    tempDiffVo.setScommodityName(commodityInfo.getSname());
                    tempDiffVo.setFcostPrice(commodityInfo.getFcostPrice());
                    tempDiffVo.setIstatus(commodityInfo.getIstatus());
                    tempDiffVo.setSpackageUnit(commodityInfo.getSpackageUnit());
                    tempDiffVo.setSbrandId(commodityInfo.getSbrandId());
                    tempDiffVo.setSsmallCategoryId(commodityInfo.getSsmallCategoryId());
                    tempDiffVo.setScommodityImg(commodityInfo.getScommodityImg());
                    tempDiffVo.setIweigth(commodityInfo.getIweigth());
                    tempDiffVo.setIcommodityFloat(commodityInfo.getIcommodityFloat());
                    if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 10) {
                        tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "天");
                    } else if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 20) {
                        tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "个月");
                    }
                    tempDiffVo.setItype(20);
                    tempDiffVo.setFcommodityPrice(stockVo.getFsalePrice());
                    tempDiffVo.setNumber(subNum.intValue());//减少的商品数量
                    tempDiffVo.setCurrStock(stockVo.getIstock());
                    subVoList.add(tempDiffVo);
                }
            }*/
        }
        return subVoList;
    }

    /**
     * 重力视觉柜生成订单
     *
     * @param deviceInfo
     * @param memberInfo
     * @param subVoList
     * @param inventoryDto
     * @return
     */
    private ResponseVo<InventoryResult> geOrder(DeviceInfo deviceInfo, MemberInfo
            memberInfo, List<CommodityDiffVo> subVoList, InventoryDto inventoryDto) {
        ResponseVo<GeneratingOrderResults> resVO = generateOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
        ResponseVo<InventoryResult> responseVo = ResponseVo.getSuccessResponse();
        logger.info("调用订单生成服务返回数据：{}", JSON.toJSONString(resVO));
        if (null != resVO) {
            if (resVO.isSuccess()) {
                GeneratingOrderResults orderResults = resVO.getData();
                List<CreatOrderResult> orderRecords = orderResults.getCreatOrderResultList();
                StringBuffer sb = new StringBuffer();
                for (CreatOrderResult orderRecord : orderRecords) {
                    sb.append(orderRecord.getOrderRecord().getId() + ",");
                }
                //异步操作库存
                stockChange(20, null, subVoList, deviceInfo, sb.toString().substring(0, sb.toString().length() - 1));
                //返回盘点结果
                InventoryResult result = new InventoryResult();
                result.setMerchantCode(deviceInfo.getSmerchantCode());
                result.setIsFirstOrder(orderResults.getIsFirstOrder());
                OrderRecord orderRecord = orderRecords.get(0).getOrderRecord();
                if (orderRecord.getIchargebackWay().intValue() == 10 || orderRecord.getIchargebackWay().intValue() == 40 || orderRecord.getIchargebackWay().intValue() == 30) {
                    result.setItype(10);
                } else {
                    result.setItype(20);
                }
                result.setOrderRecords(orderRecords);
                responseVo.setData(result);
                return responseVo;
            } else {
                logger.info("调用订单生成服务返回错误,订单生成异常");
                generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 40);
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("订单生成异常");
            }
        } else {
            logger.info("调用订单生成服务返回错误,生成异常订单");
            generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 40);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单异常");
        }
    }

    /**
     * 开门对比实时订单
     *
     * @param realGoods
     * @param openGoods
     * @param deviceInfo
     * @return
     */
    private List<ContrastGoodsVo> calcLayeredCommodityDiffData(List<LayeredGoodsVo> realGoods, List<LayeredGoodsVo> openGoods, DeviceInfo deviceInfo) {
        logger.info("重力视觉柜计算开关门商品差异：{}", deviceInfo.getScode());
        // 遍历云端返回response
        // 开关门前后都有商品，计算增加与减少的商品信息
        ContrastGoodsVo contrastGoodsVo = null;
        String temp = BizParaUtil.get("weight_layered_warm");
        BigDecimal weightLayeredWarm = new BigDecimal(temp);
        List<ContrastGoodsVo> contrastGoodsVoList = new ArrayList<>();
        for (int x = 0; x < realGoods.size(); x++) {
            LayeredGoodsVo layeredGoods = realGoods.get(x);
            LayeredGoodsVo openLayeredGoods = openGoods.get(x);
            List<CommodityVo> realModelList = layeredGoods.getCommodityVos();
            List<CommodityVo> openModelList = openLayeredGoods.getCommodityVos();
            Map<String, Integer> realGoodsMap = getGoodsMap(realModelList);
            Map<String, Integer> openDoorMap = getGoodsMap(openModelList);
            Map<String, Object> map = new HashMap<String, Object>();
            List<CommodityDiffVo> subVoList = new ArrayList<CommodityDiffVo>();
            List<CommodityDiffVo> addVoList = new ArrayList<CommodityDiffVo>();
            BigDecimal diffValue = layeredGoods.getLayeredWeight().subtract(openGoods.get(x).getLayeredWeight());
       /*     if (diffValue.abs().compareTo(deviceInfo.getIelectronicFloat().add(weightLayeredWarm)) <= 0) {
                //什么都没拿
                contrastGoodsVo = new ContrastGoodsVo();
                contrastGoodsVo.setCameraIp(layeredGoods.getCameraIp());
                contrastGoodsVo.setRealWeight(layeredGoods.getLayeredWeight());
                contrastGoodsVo.setOpenWeight(openGoods.get(x).getLayeredWeight());
                contrastGoodsVo.setIisShop(false);
                contrastGoodsVoList.add(contrastGoodsVo);
                logger.info("在误差范围内,商品没有变化");
                continue;
            }*/
            for (Map.Entry<String, Integer> openDoor : openDoorMap.entrySet()) {
                CommodityDiffVo tempDiffVo = null;
                String openId = openDoor.getKey();
                DeviceStock deviceStock = deviceStockDao.selectDeviceStockByVrCode(deviceInfo.getId(), openId);
                if (null == deviceStock || null == deviceStock.getIstock() || deviceStock.getIstock() == 0) {
                    continue;
                }
                Integer openNum = openDoor.getValue() != null ? openDoor.getValue() : 0;
                Integer closeNum = 0;
                if (realGoodsMap.containsKey(openId)) {//如果关门存在开门时的商品
                    closeNum = realGoodsMap.get(openId) != null ? realGoodsMap.get(openId) : 0;
                }
                Integer resultNum = openNum - closeNum;
                if (resultNum > 0) {//商品减少
                    tempDiffVo = assemblyVisionTemp(resultNum, openId, deviceInfo, closeNum);
                    if (null == tempDiffVo) {
                        continue;
                    }
                    subVoList.add(tempDiffVo);
                } else if (resultNum < 0) {
                    tempDiffVo = assemblyVisionTemp(resultNum, openId, deviceInfo, closeNum);
                    if (null == tempDiffVo) {
                        continue;
                    }
                    addVoList.add(tempDiffVo);
                }
            }
            for (Map.Entry<String, Integer> closeDoor : realGoodsMap.entrySet()) {
                String closeId = closeDoor.getKey();
                DeviceStock deviceStock = deviceStockDao.selectDeviceStockByVrCode(deviceInfo.getId(), closeId);
                if (null == deviceStock || null == deviceStock.getIstock() || deviceStock.getIstock() == 0) {
                    continue;
                }
                Integer closeNum = closeDoor.getValue() != null ? closeDoor.getValue() : 0;
                if (!openDoorMap.containsKey(closeId)) {//如果关门不存在开门时的商品 新增
                    CommodityDiffVo tempDiffVo = assemblyVisionTemp(-closeNum, closeId, deviceInfo, closeNum);
                    if (null == tempDiffVo) {
                        continue;
                    }
                    addVoList.add(tempDiffVo);
                }
            }
            if (subVoList.isEmpty() && addVoList.isEmpty() && diffValue.abs().compareTo(deviceInfo.getIelectronicFloat().add(weightLayeredWarm)) <= 0) {
                contrastGoodsVo = new ContrastGoodsVo();
                contrastGoodsVo.setCameraIp(layeredGoods.getCameraIp());
                contrastGoodsVo.setRealWeight(layeredGoods.getLayeredWeight());
                contrastGoodsVo.setOpenWeight(openGoods.get(x).getLayeredWeight());
                contrastGoodsVo.setIisShop(false);
                contrastGoodsVoList.add(contrastGoodsVo);
                logger.info("在误差范围内,商品没有变化");
                continue;
            }
            map.put("subVoList", subVoList);
            map.put("addVoList", addVoList);
            contrastGoodsVo = new ContrastGoodsVo();
            contrastGoodsVo.setCameraIp(layeredGoods.getCameraIp());
            contrastGoodsVo.setContrastMap(map);
            contrastGoodsVo.setIisShop(true);
            contrastGoodsVo.setRealWeight(layeredGoods.getLayeredWeight());
            contrastGoodsVo.setOpenWeight(openGoods.get(x).getLayeredWeight());
            contrastGoodsVoList.add(contrastGoodsVo);
        }
        return contrastGoodsVoList;
    }

    /**
     * @param commodityVoList
     * @return
     */
    private Map<String, Integer> getGoodsMap(List<CommodityVo> commodityVoList) {
        Map<String, Integer> goodsMap = new HashMap<>();  // 临时存放从云端返回的图片中商品集合
        // 遍历云端返回response
        if (CollectionUtils.isNotEmpty(commodityVoList)) {
            for (CommodityVo good : commodityVoList) {
                String vrCode = good.getSvrCode();       // 商品视觉编号
                Integer num = good.getCommodityNum();          // 商品数量
                if (!goodsMap.containsKey(vrCode)) {
                    goodsMap.put(vrCode, num);
                } else {
                    Integer tempNum = goodsMap.get(vrCode);
                    goodsMap.put(vrCode, tempNum + num);
                }
            }
        }
        return goodsMap;
    }

    private ResponseVo<InventoryResult> getOrder(List<StockVo> diffStocks, BigDecimal diffData, BigDecimal iele, DeviceInfo deviceInfo, List<CommodityDiffVo> subVoList
            , MemberInfo memberInfo, boolean flagTemp, List visionList, InventoryDto inventoryDto) {
        //循环库存商品  匹配单一商品
        //匹配大于一种或者没有匹配到为false  匹配到有且只有一种商品满足为true
        BigDecimal subNum = BigDecimal.ZERO;
        List<String> gravityList = new ArrayList();
        for (int i = 0; i < diffStocks.size(); i++) {
            //重量差%商品重量的值  divResults[0] 为商  divResults[1] 为余数
            StockVo stockVo = diffStocks.get(i);
            //如果余数小于等于商品的误差值,证明商品重量可以匹配正确,此时商为可匹配商品的数量
            BigDecimal icommodityFloat = stockVo.getIcommodityFloat();
            if (null == icommodityFloat) {
                icommodityFloat = BigDecimal.ZERO;
            }
            BigDecimal[] divResults = diffData.divideAndRemainder(stockVo.getIweigth());
            if (divResults[1].compareTo(icommodityFloat.add(iele)) <= 0 || diffData.subtract(stockVo.getIweigth()).abs().compareTo(icommodityFloat.add(iele)) <= 0) {
                if (divResults[0].compareTo(BigDecimal.ZERO) == 0) {
                    subNum = BigDecimal.ONE;
                } else {
                    if (subNum.intValue() > stockVo.getIstock()) {
                        continue;
                    }
                    subNum = divResults[0];
                }
                gravityList.add(stockVo.getSvrCode() + "_" + subNum + "_" + i);
            } else if (divResults[0] != BigDecimal.ZERO) {
                BigDecimal temp = diffData.subtract(divResults[0].add(BigDecimal.ONE).multiply(stockVo.getIweigth()));
                if (temp.abs().compareTo(icommodityFloat.add(iele)) <= 0) {
                    subNum = divResults[0].add(BigDecimal.ONE);
                    if (subNum.intValue() > stockVo.getIstock()) {
                        continue;
                    }
                    gravityList.add(stockVo.getSvrCode() + "_" + subNum + "_" + i);
                }
            }
        }
        if (gravityList.size() == 1) {
            //在误差范围内,出订单
            logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->在误差范围内,匹配商品:{}", gravityList);
            String gravityResult = gravityList.get(0);
            String svrCode = gravityResult.split("_")[0];
            subNum = new BigDecimal(gravityResult.split("_")[1]);
            Integer size = new Integer(gravityResult.split("_")[2]);
            logger.info("重力匹配的商品视觉编号为：{}", svrCode);
            logger.info("库库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->重力匹配视觉商品匹配到唯一值,出订单:{}", svrCode);
            StockVo stockVo = diffStocks.get(size);
            subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                    subVoList, subNum);
            //生成购物订单

            return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
        } else if (gravityList.size() == 0) {
            //没有匹配到商品,出异常订单
            logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->没有匹配到商品,出异常订单:{}");
            generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 40);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单失败,生成异常订单");
        } else {
            logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理:{}", gravityList);
            List<String> result = new ArrayList<>();
            for (int x = 0; x < gravityList.size(); x++) {
                String gravityResult = gravityList.get(x);
                String svrCode = gravityResult.split("_")[0];
                subNum = new BigDecimal(gravityResult.split("_")[1]);
                Integer size = new Integer(gravityResult.split("_")[2]);
                if (visionList.contains(svrCode)) {
                    result.add(subNum + "_" + size);
                }
            }
            if (result.size() == 1) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->只有一个满足:{}", result);
                String resultData = result.get(0);
                subNum = new BigDecimal(resultData.split("_")[0]);
                Integer size = new Integer(resultData.split("_")[1]);
                StockVo stockVo = diffStocks.get(size);
                subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                        subVoList, subNum);
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            } else {
                //没有匹配到商品,出异常订单
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->没有满足的", result);
                generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 40);
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单失败,生成异常订单");
            }
        }
    }

    private List<CommodityDiffVo> getNewSubList(StockVo stockVo, BigDecimal size, DeviceInfo deviceInfo, List<CommodityDiffVo> subVoList) {
        CommodityDiffVo tempDiffVo = new CommodityDiffVo();
        if (!stockVo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return null;
        }
        if (subVoList == null) {
            subVoList = new ArrayList<>();
        }
        CommodityInfo commodityInfo = commodityInfoService.selectByVrCode(stockVo.getSvrCode(), deviceInfo.getSmerchantId());
        tempDiffVo.setScommodityCode(commodityInfo.getScode());
        tempDiffVo.setScommodityId(commodityInfo.getId());
        tempDiffVo.setScommodityFullName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
        tempDiffVo.setScommodityName(commodityInfo.getSname());
        tempDiffVo.setFcostPrice(commodityInfo.getFcostPrice());
        tempDiffVo.setIstatus(commodityInfo.getIstatus());
        tempDiffVo.setSpackageUnit(commodityInfo.getSpackageUnit());
        tempDiffVo.setSbrandId(commodityInfo.getSbrandId());
        tempDiffVo.setSsmallCategoryId(commodityInfo.getSsmallCategoryId());
        tempDiffVo.setScommodityImg(commodityInfo.getScommodityImg());
        tempDiffVo.setIweigth(commodityInfo.getIweigth());
        tempDiffVo.setIcommodityFloat(commodityInfo.getIcommodityFloat());
        if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 10) {
            tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "天");
        } else if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 20) {
            tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "个月");
        }
        tempDiffVo.setItype(20);
        tempDiffVo.setFcommodityPrice(stockVo.getFsalePrice());
        tempDiffVo.setNumber(size.intValue());//减少的商品数量
        tempDiffVo.setCurrStock(stockVo.getIstock());
        tempDiffVo.setSvrCode(commodityInfo.getSvrCode());
        subVoList.add(tempDiffVo);
        return subVoList;
    }

    /**
     * @param diffStocks
     * @param diffData
     * @param iele
     * @param deviceInfo
     * @param subVoList
     * @throws Exception
     */

    private ResponseVo<InventoryResult> getOrder2(List<StockVo> diffStocks, BigDecimal diffData, BigDecimal iele, DeviceInfo deviceInfo, List<CommodityDiffVo> subVoList, MemberInfo memberInfo, InventoryDto inventoryDto) {
        //视觉减少的商品没有满足的,可能是未识别的单一商品
        //循环库存商品  匹配单一商品
        //匹配大于一种或者没有匹配到为false  匹配到有且只有一种商品满足为true
        BigDecimal subNum = BigDecimal.ZERO;
        Boolean iisMatching = false;
        if (diffStocks.size() == 1) {
            StockVo stockVo = diffStocks.get(0);
            BigDecimal icommodityFloat = stockVo.getIcommodityFloat();
            if (null == icommodityFloat) {
                icommodityFloat = BigDecimal.ZERO;
            }
            BigDecimal[] divResults = diffData.divideAndRemainder(stockVo.getIweigth());
            if (divResults[1].compareTo(icommodityFloat.add(iele)) <= 0 || diffData.subtract(stockVo.getIweigth()).abs().compareTo(icommodityFloat.add(iele)) <= 0) {
                if (divResults[0].compareTo(BigDecimal.ZERO) == 0) {
                    subNum = BigDecimal.ONE;
                    iisMatching = true;
                } else {
                    subNum = divResults[0];
                    iisMatching = true;
                }
            } else if (divResults[0] != BigDecimal.ZERO) {
                BigDecimal temp = diffData.subtract(divResults[0].add(BigDecimal.ONE).multiply(stockVo.getIweigth()));
                if (temp.abs().compareTo(icommodityFloat.add(iele)) <= 0) {
                    subNum = divResults[0].add(BigDecimal.ONE);
                    iisMatching = true;
                }
            }
            //在误差范围内,出订单
            if (iisMatching) {
                subVoList = getSubList(stockVo, deviceInfo, false,
                        subVoList, subNum);
                //生成订单
                // geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            }
            return null;
        } else {
            List<String> gravityList = new ArrayList();
            for (int i = 0; i < diffStocks.size(); i++) {
                //重量差%商品重量的值  divResults[0] 为商  divResults[1] 为余数
                StockVo stockVo = diffStocks.get(i);
                //如果余数小于等于商品的误差值,证明商品重量可以匹配正确,此时商为可匹配商品的数量
                BigDecimal icommodityFloat = stockVo.getIcommodityFloat();
                if (null == icommodityFloat) {
                    icommodityFloat = BigDecimal.ZERO;
                }
                BigDecimal[] divResults = diffData.divideAndRemainder(stockVo.getIweigth());
                if (divResults[1].compareTo(icommodityFloat.add(iele)) <= 0 || diffData.subtract(stockVo.getIweigth()).abs().compareTo(icommodityFloat.add(iele)) <= 0) {
                    if (divResults[0].compareTo(BigDecimal.ZERO) == 0) {
                        subNum = BigDecimal.ONE;
                    } else {
                        if (subNum.intValue() > stockVo.getIstock()) {
                            continue;
                        }
                        subNum = divResults[0];
                    }
                    gravityList.add(stockVo.getSvrCode() + "_" + subNum + "_" + i);
                } else if (divResults[0] != BigDecimal.ZERO) {
                    BigDecimal temp = diffData.subtract(divResults[0].add(BigDecimal.ONE).multiply(stockVo.getIweigth()));
                    if (temp.abs().compareTo(icommodityFloat.add(iele)) <= 0) {
                        subNum = divResults[0].add(BigDecimal.ONE);
                        if (subNum.intValue() > stockVo.getIstock()) {
                            continue;
                        }
                        gravityList.add(stockVo.getSvrCode() + "_" + subNum + "_" + i);
                    }
                }
            }
            if (gravityList.size() == 1) {
                //在误差范围内,出订单
                logger.info("购物实时订单-->库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->在误差范围内,匹配视觉商品出订单");
                String gravityResult = gravityList.get(0);
                String svrCode = gravityResult.split("_")[0];
                subNum = new BigDecimal(gravityResult.split("_")[1]);
                Integer size = new Integer(gravityResult.split("_")[2]);
                logger.info("重力匹配的商品视觉编号为：{}", svrCode);
                logger.info("库库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->重力匹配视觉商品匹配到唯一值,出订单:{}", svrCode);
                StockVo stockVo = diffStocks.get(size);
                subVoList = getSubList(stockVo, deviceInfo, false,
                        subVoList, subNum);
                //生成订单
                //geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                // iisMatching = true;
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            } else if (gravityList.size() == 0) {
                return null;
                //没有匹配到
            } else {
                //没有匹配或者匹配到多种商品可能,出异常订单
                //库存没有匹配的商品,出异常订单
                //如果视觉为空,且重力可以匹配到多种,选取数量最少的出识别订单
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,视觉不为空优化处理:{}", gravityList);
                Collections.sort(gravityList, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        BigDecimal subNum = new BigDecimal(o1.split("_")[1]);
                        BigDecimal subNumTemp = new BigDecimal(o2.split("_")[1]);
                        if (subNum.compareTo(subNumTemp) > 0) {
                            return 1;
                        } else if (subNum.compareTo(subNumTemp) < 0) {
                            return -1;
                        } else
                            return 0;
                    }
                });
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->有多个商品满足,选取数量最少商品出实时订单:{}");
                String resultData = gravityList.get(0);
                subNum = new BigDecimal(resultData.split("_")[1]);
                Integer size = new Integer(resultData.split("_")[2]);
                StockVo stockVo = diffStocks.get(size);
                subVoList = getSubList(stockVo, deviceInfo, false,
                        subVoList, subNum);
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            }
        }
    }

    /**
     * 重力视觉柜分层生成订单
     *
     * @param deviceInfo
     * @param subResult
     * @param subTotal
     * @param type       10:重力减少,视觉减少  20:重力减少,视觉不变或增多
     * @return
     */
    private ResponseVo<InventoryResult> layeredGeOrder3(BigDecimal iele, DeviceInfo deviceInfo, List<CommodityDiffVo> subVoList, BigDecimal subResult,
                                                        BigDecimal subTotal, Integer type, BigDecimal floatResult, MemberInfo memberInfo, List<String> visionList, Boolean flagTemp, InventoryDto inventoryDto) {
        ResponseVo<InventoryResult> responseVo = ResponseVo.getSuccessResponse();
        logger.info("#########开始匹配单个商品####:{}", type);
        String temp = BizParaUtil.get("recent_month_inventory_goods");
        if (StringUtils.isBlank(temp)) {
            temp = "1";
        }
        Integer month = Integer.valueOf(temp);
     /*   temp = BizParaUtil.get("minimum_weight_percentage");
        if (StringUtils.isBlank(temp)) {
            temp = "80";
        }
        Integer percentage = Integer.valueOf(temp);*/
        List<StockVo> diffStocks = this.selectSellGoodsByDeviceId(deviceInfo.getId(), subResult.add(iele), month);
        logger.info("#########开始匹配单个商品,查询商品集合####:{}", diffStocks);
        if (diffStocks != null && !diffStocks.isEmpty()) {
        /*    StockVo te = diffStocks.get(0);
            if (type == 10 && flagTemp) {
                BigDecimal diffTemp = subTotal.subtract(subResult);
                if (te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(diffTemp).compareTo(BigDecimal.ZERO) >= 0) {
                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                }
                subTotal = BigDecimal.ZERO;
            }*/
         /*   if (te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(subResult).compareTo(BigDecimal.ZERO) >= 0) {
                if (type == 10) {
                    logger.info("#########开始匹配单个商品,变化值未查询出来,出视觉结果####:{}", subVoList);
                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                } else if (20 == type) {
                    logger.info("#########开始匹配单个商品,变化值未查询出来,没有视觉变化,购物车空空如也####:{}", type);
                    InventoryResult result = new InventoryResult();
                    result.setMerchantCode(deviceInfo.getSmerchantCode());
                    result.setItype(50);//处理成功
                    responseVo.setData(result);
                    return responseVo;
                }
            }else if (subResult.subtract(te.getIweigth()).abs().compareTo(te.getIcommodityFloat().add(iele)) <= 0 *//**//*&& te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(subResult).compareTo(BigDecimal.ZERO) < 0*//**//*) {
                //确定就是这个商品
                //出订单
                logger.info("#########开始匹配单个商品,变化值查询出来,根据最小商品出订单####:{}", type);
                subVoList = getSubList(te, deviceInfo, true,
                        subVoList, new BigDecimal(1));
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            }*/
            if (diffStocks.size() == 1) {
                logger.info("#########开始匹配单个商品,变化值查询出来只有一个,根据这个商品出订单####:{}", diffStocks);
                return getOrder2(diffStocks, subResult, iele, deviceInfo, subVoList, memberInfo, inventoryDto, true, type, visionList, subTotal);
            }
            BigDecimal subNum = BigDecimal.ZERO;
            //循环库存商品  匹配单一商品
            //匹配大于一种或者没有匹配到为false  匹配到有且只有一种商品满足为true
            for (int i = 0; i < diffStocks.size(); i++) {
                StockVo stockVo = diffStocks.get(i);
                //如果余数小于等于商品的误差值,证明商品重量可以匹配正确,此时商为可匹配商品的数量
                BigDecimal[] divResults = subResult.divideAndRemainder(stockVo.getIweigth());
                subNum = divResults[0];
                logger.info("视觉减少不为空,开始匹配商品,商品组成订单的个数最多为：{}", subNum);
                break;
            }
            boolean variedFlag = false;
            List<MultiCommodityMatch> multiCommodityMatches = new ArrayList<>();
            MultiCommodityMatch multiCommodityMatch = null;
            List<StockVo> stockVoList = null;
            for (int x = 0; x < diffStocks.size(); x++) {
                for (int y = x + 1; y < diffStocks.size(); y++) {
                    for (int i = 1; i < subNum.intValue(); i++) {
                        StockVo stockVo = diffStocks.get(x);
                        //商品重量
                        BigDecimal stockVoiweightl = stockVo.getIweigth().multiply(new BigDecimal(i));
                        if (subResult.subtract(stockVoiweightl).compareTo(BigDecimal.ZERO) < 0) {
                            break;
                        }
                        for (int z = 1; z <= subNum.intValue() - i; z++) {
                            StockVo stockVo2 = diffStocks.get(y);
                            //两个商品总的重量
                            BigDecimal stockVoiweight2 = stockVo2.getIweigth().multiply(new BigDecimal(z));
                            BigDecimal stockWeightTotal = stockVoiweightl.add(stockVoiweight2);
                            //总浮动值
                            BigDecimal icommodityFloat = stockVo.getIcommodityFloat().add(stockVo2.getIcommodityFloat());
                            if (subResult.subtract(stockWeightTotal).abs().compareTo(iele.add(icommodityFloat)) <= 0) {
                                //确认商品
                                multiCommodityMatch = new MultiCommodityMatch();
                                multiCommodityMatch.setVisionTotal(i + z);
                                multiCommodityMatch.setStockTotal(stockVo.getIstock() + stockVo2.getIstock());
                                multiCommodityMatch.setVisionValue(i + "_" + z);
                                stockVoList = new ArrayList<>();
                                stockVoList.add(stockVo);
                                stockVoList.add(stockVo2);
                                multiCommodityMatch.setStocks(stockVoList);
                                multiCommodityMatches.add(multiCommodityMatch);
                                variedFlag = true;
                            } else if (subResult.subtract(stockWeightTotal).compareTo(BigDecimal.ZERO) < 0) {
                                break;
                            }
                        }
                    }
                }
            }
            if (variedFlag) {
                if (!multiCommodityMatches.isEmpty() && multiCommodityMatches.size() == 1) {
                    MultiCommodityMatch multiCommodityMatchTemp = multiCommodityMatches.get(0);
                    String value = multiCommodityMatchTemp.getVisionValue();
                    String[] str = value.split("_");
                    List<StockVo> stockVoListTemp = multiCommodityMatchTemp.getStocks();
                    subVoList = getSubList(stockVoListTemp.get(0), deviceInfo, true,
                            subVoList, new BigDecimal(str[0]));
                    subVoList = getSubList(stockVoListTemp.get(1), deviceInfo, false,
                            subVoList, new BigDecimal(str[1]));
                    //生成订单
                    logger.info("多商品可以匹配成功" + str[0] + "个" + stockVoListTemp.get(0) + "####" + str[1] + "个" + stockVoListTemp.get(1));
                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                } else {
                 /*   Collections.sort(multiCommodityMatches, new Comparator<MultiCommodityMatch>() {
                        @Override
                        public int compare(MultiCommodityMatch o1, MultiCommodityMatch o2) {
                            int visionTotal1 = o1.getVisionTotal();
                            int visionTotal2 = o2.getVisionTotal();
                            if (visionTotal1 > visionTotal2) {
                                return 1;
                            } else if (visionTotal1 < visionTotal2) {
                                return -1;
                            } else if (visionTotal1 == visionTotal2) {
                                int stockTotal1 = o1.getStockTotal();
                                int stockTotal2 = o2.getStockTotal();
                                if (stockTotal1 > stockTotal2) {
                                    return -1;
                                } else if (stockTotal1 < stockTotal2) {
                                    return 1;
                                }
                            }
                            return 0;
                        }
                    });
                    MultiCommodityMatch multiCommodityMatchTemp = multiCommodityMatches.get(0);
                    String value = multiCommodityMatchTemp.getVisionValue();
                    String[] str = value.split("_");
                    List<StockVo> stockVoListTemp = multiCommodityMatchTemp.getStocks();
                    subVoList = getSubList(stockVoListTemp.get(0), deviceInfo, true,
                            subVoList, new BigDecimal(str[0]));
                    subVoList = getSubList(stockVoListTemp.get(1), deviceInfo, false,
                            subVoList, new BigDecimal(str[1]));
                    //生成订单
                    logger.info("多商品可以匹配成功" + str[0] + "个" + stockVoListTemp.get(0) + "####" + str[1] + "个" + stockVoListTemp.get(1));
                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);*/
                    generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 40);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单失败,生成异常订单");
                }
            } else {
                //重量未匹配到多种商品组合,匹配单种
               /* if (type == 10 && !flagTemp) {
                    return getOrder2(diffStocks, subResult, iele, deviceInfo, subVoList, memberInfo, inventoryDto, true, type, visionList, subTotal);
                } else if (type == 10 && flagTemp) {
                    return getOrder2(diffStocks, subResult, iele, deviceInfo, subVoList, memberInfo, inventoryDto, true, type, visionList, subTotal);
                } else if (type == 20) {
                    return getOrder2(diffStocks, subResult, iele, deviceInfo, subVoList, memberInfo, inventoryDto, true, type, visionList, subTotal);
                }*/
                return getOrder2(diffStocks, subResult, iele, deviceInfo, subVoList, memberInfo, inventoryDto, true, type, visionList, subTotal);
            }
        } /*else {
          *//*  MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);*//*
            InventoryResult result = new InventoryResult();
            result.setMerchantCode(deviceInfo.getSmerchantCode());
            result.setItype(50);//处理成功
            responseVo.setData(result);
            return responseVo;
        }*/
        InventoryResult result = new InventoryResult();
        result.setMerchantCode(deviceInfo.getSmerchantCode());
        result.setItype(50);//处理成功
        responseVo.setData(result);
        return responseVo;
    }

    /**
     * @param diffStocks
     * @param diffData
     * @param iele
     * @param deviceInfo
     * @param subVoList
     * @param flagTemp
     * @param type
     * @param visionList
     * @throws Exception
     */

    private ResponseVo<InventoryResult> getOrder2(List<StockVo> diffStocks, BigDecimal diffData, BigDecimal iele, DeviceInfo deviceInfo, List<CommodityDiffVo> subVoList
            , MemberInfo memberInfo, InventoryDto inventoryDto, boolean flagTemp, Integer type, List visionList, BigDecimal subTotal) {
        logger.info("#########开始匹配单个商品,变化值查询出来只有一个,根据这个商品出订单####:{}", diffStocks);
        //视觉减少的商品没有满足的,可能是未识别的单一商品
        //循环库存商品  匹配单一商品
        //匹配大于一种或者没有匹配到为false  匹配到有且只有一种商品满足为true
        BigDecimal subNum = BigDecimal.ZERO;
        List<String> gravityList = new ArrayList();
        for (int i = 0; i < diffStocks.size(); i++) {
            //重量差%商品重量的值  divResults[0] 为商  divResults[1] 为余数
            StockVo stockVo = diffStocks.get(i);
            //如果余数小于等于商品的误差值,证明商品重量可以匹配正确,此时商为可匹配商品的数量
            BigDecimal icommodityFloat = stockVo.getIcommodityFloat();
            if (null == icommodityFloat) {
                icommodityFloat = BigDecimal.ZERO;
            }
            BigDecimal[] divResults = diffData.divideAndRemainder(stockVo.getIweigth());
            if (divResults[1].compareTo(icommodityFloat.add(iele)) <= 0 || diffData.subtract(stockVo.getIweigth()).abs().compareTo(icommodityFloat.add(iele)) <= 0) {
                if (divResults[0].compareTo(BigDecimal.ZERO) == 0) {
                    subNum = BigDecimal.ONE;
                } else {
                    subNum = divResults[0];
                }
                gravityList.add(stockVo.getSvrCode() + "_" + subNum + "_" + i);
            } else if (divResults[0] != BigDecimal.ZERO) {
                BigDecimal temp = diffData.subtract(divResults[0].add(BigDecimal.ONE).multiply(stockVo.getIweigth()));
                if (temp.abs().compareTo(icommodityFloat.add(iele)) <= 0) {
                    subNum = divResults[0].add(BigDecimal.ONE);
                    gravityList.add(stockVo.getSvrCode() + "_" + subNum + "_" + i);
                }
            }
        }
        if (gravityList.size() == 1) {
            //在误差范围内,出订单
            logger.info("购物实时订单-->库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->在误差范围内,匹配视觉商品出订单");
            String gravityResult = gravityList.get(0);
            String svrCode = gravityResult.split("_")[0];
            subNum = new BigDecimal(gravityResult.split("_")[1]);
            Integer size = new Integer(gravityResult.split("_")[2]);
            logger.info("重力匹配的商品视觉编号为：{}", svrCode);
            logger.info("库库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->重力匹配视觉商品匹配到唯一值,出订单:{}", svrCode);
            StockVo stockVo = diffStocks.get(size);
            subVoList = getSubList(stockVo, deviceInfo, true,
                    subVoList, subNum);
            //生成订单
            return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
        } else if (gravityList.size() == 0) {
            logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->没有匹配到商品,出异常订单:{}");
            generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 40);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单失败,生成异常订单");
        } else {
            //没有匹配或者匹配到多种商品可能,出异常订单
            //库存没有匹配的商品,出异常订单
            //如果视觉为空,且重力可以匹配到多种,选取数量最少的出识别订单
            List<String> result = new ArrayList<>();
            for (int x = 0; x < gravityList.size(); x++) {
                String gravityResult = gravityList.get(x);
                String svrCode = gravityResult.split("_")[0];
                subNum = new BigDecimal(gravityResult.split("_")[1]);
                Integer size = new Integer(gravityResult.split("_")[2]);
                if (visionList.contains(svrCode)) {
                    result.add(subNum + "_" + size);
                }
            }
            if (result.size() == 1) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->只有一个满足:{}", result);
                String resultData = result.get(0);
                subNum = new BigDecimal(resultData.split("_")[0]);
                Integer size = new Integer(resultData.split("_")[1]);
                StockVo stockVo = diffStocks.get(size);
                subVoList = getSubList(stockVo, deviceInfo, true,
                        subVoList, subNum);
                return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
            } else {
                generateExceptionOrder(deviceInfo, memberInfo, subVoList, inventoryDto, 40);
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("创建订单失败,生成异常订单");
            }
        }
    }

    private ResponseVo<InventoryResult> yanzheng(Integer type, DeviceInfo deviceInfo, BigDecimal diffData, BigDecimal iele, Boolean flagTemp
            , MemberInfo memberInfo, List<CommodityDiffVo> subVoList, BigDecimal subResult, BigDecimal subTotal, InventoryDto inventoryDto
    ) {
        logger.info("验证商品视觉重力误差值是否有效:{}", type);
        String temp = BizParaUtil.get("recent_month_inventory_goods");
        if (StringUtils.isBlank(temp)) {
            temp = "1";
        }
        Integer month = Integer.valueOf(temp);
        temp = BizParaUtil.get("minimum_weight_percentage");
        if (StringUtils.isBlank(temp)) {
            temp = "80";
        }
        Integer percentage = Integer.valueOf(temp);
        List<StockVo> diffStocks = this.selectSellGoodsByDeviceId(deviceInfo.getId(), diffData.add(iele), month);
        logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,查询所有库存商品集合：{}", diffStocks);
        if (diffStocks != null && !diffStocks.isEmpty()) {
            StockVo te = diffStocks.get(0);
            if (10 == type && !flagTemp) {
                if (te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(diffData).compareTo(BigDecimal.ZERO) >= 0) {
                    logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,库存最小重量小于重力减少的值,以视觉为准出订单");
                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                }
            } else if (10 == type && flagTemp) {
              /*  BigDecimal diffTemp = subTotal.subtract(subResult);
                if (te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(diffTemp).compareTo(BigDecimal.ZERO) >= 0) {
                    return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
                }*/
            } else if (20 == type) {
                if (te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(subResult).compareTo(BigDecimal.ZERO) >= 0) {
                    logger.info("#########开始匹配单个商品,变化值未查询出来,没有视觉变化,购物车空空如也####:{}", type);
                    ResponseVo<InventoryResult> responseVo = ResponseVo.getSuccessResponse();
                    InventoryResult result = new InventoryResult();
                    result.setMerchantCode(deviceInfo.getSmerchantCode());
                    result.setItype(50);//处理成功
                    responseVo.setData(result);
                    return responseVo;
                }
            }
        }
        return null;
    }

    /**
     * 匹配单种单个商品
     *
     * @param deviceInfo
     * @param matchWeight
     * @param iele
     * @param subVoList
     * @param flagTemp
     * @param memberInfo
     * @return
     * @throws Exception
     */
    private ResponseVo<InventoryResult> matchSingleGoods(DeviceInfo deviceInfo, BigDecimal matchWeight, BigDecimal iele
            , List<CommodityDiffVo> subVoList, Boolean flagTemp, MemberInfo memberInfo, InventoryDto inventoryDto) {
        logger.info("重量有减少,视觉为空,匹配单个商品：{}", matchWeight);
        List<StockVo> diffStocksTemp = this.selectGravityStockByDeviceId(deviceInfo.getId(), matchWeight.add(iele));
        List<StockVo> stockVos = new ArrayList<>();
        if (diffStocksTemp != null && !diffStocksTemp.isEmpty()) {
            for (StockVo stockVo : diffStocksTemp) {
                if (matchWeight.subtract(stockVo.getIweigth()).abs().compareTo(iele.add(stockVo.getIcommodityFloat())) <= 0) {
                    stockVos.add(stockVo);
                }
            }
        }
        logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,匹配单个商品,可以匹配出商品：{}", stockVos);
        if (!stockVos.isEmpty() && stockVos.size() == 1) {
            StockVo stockVo = stockVos.get(0);
            subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                    subVoList, new BigDecimal(1));
            // geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
            logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,匹配单个商品,可以匹配出商品：{}", stockVo);
            return geOrder(deviceInfo, memberInfo, subVoList, inventoryDto);
        } else if (!stockVos.isEmpty() && stockVos.size() > 1) {
            logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,匹配单个商品,可以匹配出多个商品：{}", stockVos.size());
        }
        return null;
    }
}