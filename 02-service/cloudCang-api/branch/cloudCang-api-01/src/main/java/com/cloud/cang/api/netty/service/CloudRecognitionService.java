package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.utils.BigDecimalUtils;
import com.cloud.cang.api.common.utils.LogUtils;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.openSdk.service.OpenSdkApi;
import com.cloud.cang.api.sb.dao.DeviceInfoDao;
import com.cloud.cang.api.sb.dao.DeviceRegisterDao;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.api.sh.service.MerchantInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.api.sp.service.CommodityInfoService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.*;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.inventory.CommodityVo;
import com.cloud.cang.inventory.InventoryDto;
import com.cloud.cang.inventory.InventoryResult;
import com.cloud.cang.inventory.InventoryServicesDefine;
import com.cloud.cang.jy.CreatOrderResult;
import com.cloud.cang.model.GoodModel;
import com.cloud.cang.model.http.HttpCloudOrderModel;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.open.sdk.model.request.ImageDetail;
import com.cloud.cang.open.sdk.model.response.GoodDetail;
import com.cloud.cang.open.sdk.model.response.ImgResultDetail;
import com.cloud.cang.open.sdk.response.ImgRecognitionResponse;
import com.cloud.cang.pay.FreePaymentDto;
import com.cloud.cang.pay.FreePaymentResult;
import com.cloud.cang.pay.FreeServicesDefine;
import com.cloud.cang.utils.StringUtil;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @version 1.0
 * @ClassName CloudRecognitionService
 * @Description 云端图片识别处理类
 * @Author zengzexiong
 * @Date 2018年9月20日11:13:55
 */
@Service("CloudRecognitionService")
public class CloudRecognitionService {

    @Autowired
    private ICached iCached;    // 系统Redis缓存

    @Autowired
    private DeviceInfoService deviceInfoService; // 设备基础信息

    @Autowired
    private DeviceRegisterService deviceRegisterService;    // 设备注册信息

    @Autowired
    private CommodityInfoService commodityInfoService; // 商品信息

    @Autowired
    private MerchantInfoService merchantInfoService; // 商户信息

    private static final Logger logger = LoggerFactory.getLogger(CloudRecognitionService.class);

    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();      //netty通道
    private static ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();   //socketIo通道


    /**
     * 关门结算
     *
     * @param deviceId     设备ID
     * @param key          通信密钥
     * @param userId       用户ID
     * @param openDoorType 开门类型
     * @param imgDetail    图片base64集合
     * @param request      请求对象
     * @return
     */
    public ResponseVo<HttpCloudOrderModel> closeDoorSettle(String deviceId, String key, String userId, Integer openDoorType, List<ImageDetail> imgDetail, HttpServletRequest request) {
        logger.debug("关门结算服务开始，设备ID：{}", deviceId);
        ResponseVo<HttpCloudOrderModel> responseVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        SessionVo sessionVo = null;                                                 // 用户信息
        ClientVo clientVo = null;                                                   // 设备信息
        SocketIOClient socketIOClient = null;                                       // 服务器与手机端通信通道
        Integer types = null;    //10 开门 20 补货开门
        Integer sourceClientType = null; //来源客户端类型
        String clientIp = "";   // 开门IP地址
        String deviceCode = ""; // 设备编号

        /* 1.0 校验参数有效性*/
        logger.debug("校验参数有效性开始");
        // 注册信息有效性
        DeviceRegister deviceRegisterVo = new DeviceRegister();
        deviceRegisterVo.setSdeviceId(deviceId);
        deviceRegisterVo.setSsecurityKey(key);
        deviceRegisterVo.setIdelFlag(0);
        List<DeviceRegister> deviceRegisterList = deviceRegisterService.selectByEntityWhere(deviceRegisterVo);
        if (CollectionUtils.isEmpty(deviceRegisterList)) {
            logger.error("设备注册信息有误，设备ID：{}", deviceId);
            responseVo.setMsg("设备的注册信息有误");
            return responseVo;
        }
        // 设备状态有效性
        DeviceInfo deviceInfoVo = new DeviceInfo();
        deviceInfoVo.setId(deviceId);
        deviceInfoVo.setItype(40);  //  10 传统 20 RFID射频 30 视觉 40 云识别
        deviceInfoVo.setIstatus(20);  // 10=未注册 20=正常 30=故障 40=报废 50=离线
        deviceInfoVo.setIdelFlag(0);
        deviceInfoVo.setIoperateStatus(10); //  10 启用 20 禁用
        List<DeviceInfo> deviceInfoList = deviceInfoService.selectByEntityWhere(deviceInfoVo);
        if (CollectionUtils.isEmpty(deviceInfoList)) {
            logger.error("设备状态不正确，设备ID：{}", deviceId);
            responseVo.setMsg("设备为非正常状态");
            return responseVo;
        }
        logger.debug("校验参数有效性结束");

        /* 2.0 调用图像云识别服务*/
        logger.debug("调用图像云识别服务开始");
        // 获取设备相关信息
        DeviceInfo deviceInfo = deviceInfoList.get(0);
        ImgRecognitionResponse response = null;
        MerchantConf conf = new MerchantConf(); // 待解决
        List<ImgResultDetail> imgResultDetailList = new ArrayList<>();
        Map<String, Integer> goodMapTemp = new HashMap<>();  // 临时存放从云端返回的图片中商品集合
        response = OpenSdkApi.recognition(conf, deviceId, "outBatchNo", "desc", imgDetail);
        if (BooleanUtils.isTrue(response.isSuccess())) {
            imgResultDetailList = response.getImgResultDetail();
            // 遍历云端返回response
            if (CollectionUtils.isNotEmpty(imgResultDetailList)) {
                for (ImgResultDetail img : imgResultDetailList) {   // 遍历所有图片
                    List<GoodDetail> goodDetailList = img.getGoodDetail();
                    if (CollectionUtils.isNotEmpty(goodDetailList)) {   // 遍历每张图片中的商品及数量
                        for (GoodDetail good : goodDetailList) {
                            String vrCode = good.getVrCode();       // 商品视觉编号
                            String num = good.getNumber();          // 商品数量
                            if (!goodMapTemp.containsKey(vrCode)) {
                                goodMapTemp.put(vrCode, new Integer(num));
                            } else {
                                Integer tempNum = goodMapTemp.get(vrCode);
                                goodMapTemp.put(vrCode, tempNum + new Integer(num));
                            }
                        }
                    }
                }
            }


            /* 3.0 调用后台订单服务*/
            logger.debug("调用后台订单服务开始");
            // 开始封装订单参数
            List<CommodityVo> commodityVoList = new ArrayList<>();      // 视觉商品盘点入参
            if (MapUtils.isNotEmpty(goodMapTemp)) {
                CommodityVo commodityVo = null;
                for (Map.Entry<String, Integer> map : goodMapTemp.entrySet()) {
                    commodityVo.setSvrCode(map.getKey());
                    commodityVo.setCommodityNum(map.getValue());
                    commodityVoList.add(commodityVo);
                }
            }
            logger.debug("当前开门来源为扫码开门");
            Map<String, Object> verifyMap = verifyVo(deviceId, userId);
            Boolean isNotNull = (Boolean) verifyMap.get("isNotNull");                   // 基础参数，以及相关对象--sessionVo,clientVo,socketIOClient ====>校验结果
            if (BooleanUtils.isFalse(isNotNull)) {
                logger.error("http关门结算从Redis中获取设备及手机端信息为空，设备ID：{}", deviceId);
                responseVo.setMsg("手机端离线");
                return responseVo;
            }
            // 获取相应对象
            sessionVo = (SessionVo) verifyMap.get("sessionVo");
            clientVo = (ClientVo) verifyMap.get("clientVo");
            socketIOClient = (SocketIOClient) verifyMap.get("socketIOClient");
            types = sessionVo.getTypes();
            sourceClientType = sessionVo.getIsourceClientType();                //来源客户端类型
            clientIp = sessionVo.getSip();
            deviceCode = clientVo.getDeviceCode();                               //设备编号
            // 创建Rest服务客户端,调用关门盘货服务
            RestServiceInvoker invoke = null;
            try {
                invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.INVENTORY_DEALWITH_SERVICE);

                InventoryDto inventoryDto = new InventoryDto();
                inventoryDto.setDeviceId(deviceId);                     // 设备ID
                inventoryDto.setCloseType(openDoorType);                // 关门类型 10 购物 20 补货员 关门盘点必填
                inventoryDto.setInventoryType(10);                      // 盘点类型 10 关门盘点 20 定时盘点 30 主动盘点
                inventoryDto.setMemberId(userId);                       // 用户ID
                inventoryDto.setIsourceClientType(sourceClientType);          // 来源类型
                inventoryDto.setSip(clientIp);
                if (CollectionUtils.isEmpty(commodityVoList)) {
                    logger.info("售货机上传的图片中商品集合对象为空");
                }

                inventoryDto.setCommodityVos(commodityVoList);
                invoke.setRequestObj(inventoryDto);
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<InventoryResult>>() {
                });
                ResponseVo<InventoryResult> responseInventoryResult = (ResponseVo<InventoryResult>) invoke.invoke();

                SocketResponseVo<String> socketResponseVo = SocketResponseVo.getSuccessResponse();
                logger.debug("扫码开门 -> 发送http关门盘货请求，调用关门盘货服务完成，调用结果：{}，设备ID：{}", responseVo.isSuccess(), deviceId);
                // 处理调用服务成功的结果
                if (responseInventoryResult.isSuccess()) {
                    logger.debug("扫码开门调用http关门盘货服务成功,设备ID：{}", deviceId);
                    InventoryResult inventoryResult = responseInventoryResult.getData();
                    //发送消息给终端
                    //发送消息给手机客户端
                    if (inventoryResult.getItype().intValue() == 10 || inventoryResult.getItype().intValue() == 20) { //购物关门
                        socketResponseVo.setTypes(30);
                        StringBuffer sb = new StringBuffer();
                        List<CreatOrderResult> orderRecords = inventoryResult.getOrderRecords();
                        for (CreatOrderResult orderResult : orderRecords) {
                            sb.append(orderResult.getOrderRecord().getSorderCode() + ",");
                        }
                        if (sb.toString().length() > 0) {
                            socketResponseVo.setData(sb.toString().substring(0, sb.toString().length() - 1));//返回订单集合 多个用，隔开
                        }
                        socketResponseVo.setMsg(inventoryResult.getIsFirstOrder() + "");//是否首单
                        asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);

                        //更新关门日志
                        LogUtils.updateOPLog(deviceCode, userId, 10);

                        // 给设备推送订单 10 自动扣款      20 手动支付
                        if (responseInventoryResult.getData().getItype().intValue() == 10) {    //创建代扣支付订单成功
                            logger.debug("扫码开门 -> 关门盘货 -> http给大屏返回支付代扣订单,设备ID：{}", deviceId);
                            return sendHttpAutomaticPayOrder(deviceId, userId, responseInventoryResult.getData().getMerchantCode(), orderRecords, responseVo);
                        } else if (responseInventoryResult.getData().getItype().intValue() == 20) {
                            logger.debug("扫码开门 -> 关门盘货 -> http给大屏设备返回购物订单，设备ID：{}", deviceId);
                            return sendHttpManualPayOrder(deviceId, userId, responseInventoryResult.getData().getItype().intValue(), orderRecords, responseVo);
                        }


                    } else if (inventoryResult.getItype().intValue() == 30) { //补货关门
                        socketResponseVo.setTypes(40);
                        String recordCode = inventoryResult.getReplenishmentResult().getReplenishmentRecord().getScode();
                        if (StringUtil.isNotBlank(recordCode)) {
                            socketResponseVo.setData(recordCode);//补货单号
                        }
                        asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);

                        //更新关门日志
                        LogUtils.updateOPLog(deviceCode, userId, 20);

                        // 给设备推送补货单
                        logger.debug("扫码开门 -> 关门盘货 -> 给设备返回补货单，设备ID：{}", deviceId);
                        return sendReplenishmentOrders(deviceId, userId);

                    } else if (inventoryResult.getItype().intValue() == 50) { // 游客
                        Integer userType = null;
                        if (openDoorType == 10) {//购物开门
                            logger.debug("扫码开门 -> 关门盘货 -> 游客类型，给设备返回消息，设备ID：{}", deviceId);
                            socketResponseVo.setTypes(30);
                            userType = 10;
                            LogUtils.updateOPLog(deviceCode, userId, 10);   // 更新关门日志
                        } else if (openDoorType == 20) {//补货员开门
                            logger.debug("扫码开门 -> 关门盘货 -> 补货员类型，给设备返回消息，设备ID：{}", deviceId);
                            socketResponseVo.setTypes(40);
                            userType = 20;
                            LogUtils.updateOPLog(deviceCode, userId, 20);   // 更新关门日志
                        }
                        asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                        return sendTouristOrder(deviceId, userId, userType, responseVo);
                    }

                } else {
                    logger.error("调用关门盘货服务失败，设备ID：{}", deviceId);
                    if (openDoorType == 20) {
                        socketResponseVo.setTypes(60);//补货异常
                        socketResponseVo.setErrorCode(ReturnCodeEnum.ERROR_REPLENISHMENT_EXCEPTION.getCode()); // 补货异常错误代码
                    } else {
                        socketResponseVo.setTypes(50);//购物异常
                        socketResponseVo.setErrorCode(ReturnCodeEnum.ERROR_SHOPPING_EXCEPTION.getCode()); // 购物异常错误代码

                    }
                    socketResponseVo.setMsg(responseVo.getMsg());
                    socketResponseVo.setSuccess(false);
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);

                    logger.error("扫码开门 -> 关门盘货 -> 向设备发送调用关门盘货服务失败消息，设备ID：{}，用户ID:{}", deviceId, userId);
                    responseVo.setMsg("调用关门盘货服务失败");
                    return responseVo;
                }
            } catch (Exception e) {
                responseVo.setMsg("调用关门盘货服务失败");
                return responseVo;
            }
            logger.debug("调用后台订单服务结束");
        }
        logger.debug("调用图像云识别服务结束，调用结果：失败");
        return responseVo;
    }


    /**
     * 没有购物或者补货，游客关门
     *
     * @param deviceId   设备ID
     * @param userId     用户ID
     * @param userType   用户类型
     * @param responseVo
     * @return
     */
    private ResponseVo<HttpCloudOrderModel> sendTouristOrder(String deviceId, String userId, Integer userType, ResponseVo<HttpCloudOrderModel> responseVo) {
        if (null == userType) {
            logger.error("用户类型为空，设备ID：{}，用户ID：{}", deviceId, userId);
            responseVo.setMsg("用户类型为空");
            return responseVo;
        }
        // 封装参数
        HttpCloudOrderModel httpCloudOrderModel = new HttpCloudOrderModel();
        httpCloudOrderModel.setOrderType(30);   // 10 购物，20 补货，30游客
        if (userType == 10) {
            logger.debug("http记录游客行为成功，设备ID:{}，用户ID：{}", deviceId, userId);
        } else {
            logger.debug("http记录游客行为成功，设备ID:{}，补货员ID：{}", deviceId, userId);
        }
        return ResponseVo.getSuccessResponse(httpCloudOrderModel);
    }

    /**
     * 补货单
     *
     * @param deviceId 设备ID
     * @param userId   补货员ID
     * @return
     */
    private ResponseVo<HttpCloudOrderModel> sendReplenishmentOrders(String deviceId, String userId) {
        // 封装参数
        HttpCloudOrderModel httpCloudOrderModel = new HttpCloudOrderModel();
        httpCloudOrderModel.setOrderType(20);   // 10 购物，20 补货，30游客
        logger.debug("http生成补货单成功，设备ID:{}，补货员ID：{}", deviceId, userId);
        return ResponseVo.getSuccessResponse(httpCloudOrderModel);
    }

    /**
     * 代扣订单
     *
     * @param deviceId     设备ID
     * @param userId       用户ID
     * @param merchantCode 商户ID
     * @param orderRecords 订单
     * @param responseVo
     * @return
     */
    private ResponseVo<HttpCloudOrderModel> sendHttpAutomaticPayOrder(String deviceId, String userId, String merchantCode, List<CreatOrderResult> orderRecords, ResponseVo<HttpCloudOrderModel> responseVo) {
        boolean flag = true;
        try {
            logger.info("创建免密支付订单,参数：{}", orderRecords.toArray());
            OrderRecord orderRecord = null;
            FreePaymentDto paymentDto = null;

            MerchantClientConf clientConf = null;
            String smerchantCode = merchantCode;
            try {
                clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
                if (null == clientConf || clientConf.getIisConfAlipay().intValue() == 0) {//没有配置获取默认商户编号
                    smerchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
                }
            } catch (Exception e) {
                logger.error("获取商户客户端配置异常：{}", e);
                responseVo.setMsg("获取商户客户端配置异常");
                return responseVo;
            }
            MerchantInfo merchantInfo = merchantInfoService.selectByCode(smerchantCode);
            if (null == merchantInfo) {
                logger.error("收款商户异常，商户编号：{}", smerchantCode);
                responseVo.setMsg("收款商户异常");
                return responseVo;
            }
            RestServiceInvoker invoke = null;
            ResponseVo<FreePaymentResult> resVO = null;
            for (CreatOrderResult orderResult : orderRecords) {
                orderRecord = orderResult.getOrderRecord();
                if (!merchantCode.equals(orderRecord.getSmerchantCode())) {
                    logger.debug("商户订单异常，商户编号：{}", merchantCode + "===========" + orderRecord.getSmemberCode());
                    continue;
                }
                if (orderRecord.getIstatus().intValue() == 100 && orderRecord.getIchargebackWay().intValue() == 10) {//待支付  商户代扣
                    //创建支付
                    paymentDto = new FreePaymentDto();
                    paymentDto.setSsubject(merchantInfo.getSname() + "-" + orderRecord.getSorderCode());//商户明细+订单编号
                    paymentDto.setSorderId(orderRecord.getId());
                    paymentDto.setSmerchantCode(smerchantCode);
                    paymentDto.setSmemberId(orderRecord.getSmemberId());
                    paymentDto.setSmemberName(orderRecord.getSmemberName());
                    paymentDto.setSmemberCode(orderRecord.getSmemberCode());
                    paymentDto.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());//代扣
                    paymentDto.setIisIgnoreStatus(0);
                    String url = "";
                    if (orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode()) {//微信代扣服务
                        url = FreeServicesDefine.WECHAT_PAYMENT;
                    } else if (orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode()) {//支付宝代扣服务
                        url = FreeServicesDefine.ALIPAY_PAYMENT;
                    }
                    if (StringUtil.isNotBlank(url)) {
                        try {
                            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(url);// 服务名称
                            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {
                            });
                            invoke.setRequestObj(paymentDto); // post 参数
                            resVO = (ResponseVo<FreePaymentResult>) invoke.invoke();
                            if (resVO.isSuccess()) {
                                logger.info("订单代扣支付成功:{}", resVO.getData());
                                continue;
                            } else {
                                flag = false;
                                logger.error("订单代扣支付异常:{}", resVO);
                            }
                        } catch (Exception e) {
                            flag = false;
                            logger.error("商户订单创建支付异常，收款商户编号===========订单编号：{}", smerchantCode + "===========" + orderRecord.getId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            flag = false;
        }
        if (flag) {
            if (null == orderRecords || CollectionUtils.isEmpty(orderRecords)) {
                logger.error("http代扣支付订单生成出错，设备ID：{}", deviceId);
                responseVo.setMsg("代扣支付订单生成出错");
                return responseVo;
            }

            OrderRecord orderRecord = null;
            HttpCloudOrderModel httpCloudOrderModel = new HttpCloudOrderModel();
            Integer number = 0;                 // 商品总数量
            BigDecimal amountPayMoneyTemp = new BigDecimal("0");        // 订单总金额
            BigDecimal actualPayMoneyTemp = new BigDecimal("0");        // 实付金额
            BigDecimal discountedMoneyTemp = new BigDecimal("0");       // 优惠金额

            List<GoodModel> goodsList = new ArrayList<GoodModel>();  // 具体订单商品

            // 转换对象 订单Vo-----> BaseRequestVo
            Integer num = 0;
            for (CreatOrderResult creatOrderResult : orderRecords) {

                orderRecord = creatOrderResult.getOrderRecord();
                if (num == 0) {
                    if (orderRecord.getIisDismantling() != null && orderRecord.getIisDismantling().intValue() == 1) {
                        httpCloudOrderModel.setOrderNo(orderRecord.getSdismantlingCode());
                    } else {
                        httpCloudOrderModel.setOrderNo(orderRecord.getSorderCode());
                    }
                }
                amountPayMoneyTemp = BigDecimalUtils.add(amountPayMoneyTemp, orderRecord.getFtotalAmount());            // 累加订单总金额
                actualPayMoneyTemp = BigDecimalUtils.add(actualPayMoneyTemp, orderRecord.getFactualPayAmount());        // 累加订单实付金额
                discountedMoneyTemp = BigDecimalUtils.add(discountedMoneyTemp, orderRecord.getFdiscountAmount());       // 累加订单优惠金额

                List<OrderCommodity> orderCommodityList = creatOrderResult.getOrderCommodityList();
                if (CollectionUtils.isNotEmpty(orderCommodityList)) {
                    GoodModel goodModel = null;
                    CommodityInfo commodityInfo = null;
                    String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);
                    for (OrderCommodity orderCommodity : orderCommodityList) {
                        goodModel = new GoodModel();
                        goodModel.setGoodName(orderCommodity.getScommodityName());                                      // 商品名称
                        goodModel.setGoodPrice(orderCommodity.getFcommodityPrice().toString());                         // 商品价格
                        goodModel.setGoodsNumber(orderCommodity.getForderCount());                                      // 商品数量
                        commodityInfo = commodityInfoService.selectByPrimaryKey(orderCommodity.getScommodityId());
                        if (null != commodityInfo) {
                            goodModel.setGoodName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
                            goodModel.setGoodImgUrl(preUrl + commodityInfo.getScommodityImg());                         // 商品图片地址
                        }
                        goodsList.add(goodModel);
                        number = number + orderCommodity.getForderCount();
                    }
                }
                num++;
            }

            // 封装参数
            httpCloudOrderModel.setAmountPayMoney(amountPayMoneyTemp.toString());
            httpCloudOrderModel.setActualPayMoney(actualPayMoneyTemp.toString());
            httpCloudOrderModel.setDiscountedMoney(discountedMoneyTemp.toString());
            httpCloudOrderModel.setNumber(number);
            httpCloudOrderModel.setPayType(10);
            httpCloudOrderModel.setGoodsList(goodsList);
            httpCloudOrderModel.setOrderType(10);

            logger.debug("http生成代扣支付订单成功，设备ID:{}", deviceId);
            return ResponseVo.getSuccessResponse(httpCloudOrderModel);
        } else {
            logger.debug("http生成代扣订单失败，设备ID：{}", deviceId);
            responseVo.setMsg("代扣订单失败");
            return responseVo;
        }

    }

    /**
     * 手动支付订单
     *
     * @param deviceId     设备ID
     * @param userId       用户ID
     * @param type         支付类型 20 手动支付
     * @param orderRecords 订单
     * @param responseVo
     * @return
     */
    private ResponseVo<HttpCloudOrderModel> sendHttpManualPayOrder(String deviceId, String userId, Integer type, List<CreatOrderResult> orderRecords, ResponseVo<HttpCloudOrderModel> responseVo) {
        if (null == orderRecords || CollectionUtils.isEmpty(orderRecords)) {
            logger.error("http手动支付订单生成出错，设备ID：{}", deviceId);
            responseVo.setMsg("手动支付订单生成出错");
            return responseVo;
        }

        OrderRecord orderRecord = null;
        HttpCloudOrderModel httpCloudOrderModel = new HttpCloudOrderModel();
        Integer number = 0;                 // 商品总数量
        BigDecimal amountPayMoneyTemp = new BigDecimal("0");        // 订单总金额
        BigDecimal actualPayMoneyTemp = new BigDecimal("0");        // 实付金额
        BigDecimal discountedMoneyTemp = new BigDecimal("0");       // 优惠金额

        List<GoodModel> goodsList = new ArrayList<GoodModel>();  // 具体订单商品

        // 转换对象 订单Vo-----> BaseRequestVo
        Integer num = 0;
        for (CreatOrderResult creatOrderResult : orderRecords) {

            orderRecord = creatOrderResult.getOrderRecord();
            if (num == 0) {
                if (orderRecord.getIisDismantling() != null && orderRecord.getIisDismantling().intValue() == 1) {
                    httpCloudOrderModel.setOrderNo(orderRecord.getSdismantlingCode());
                } else {
                    httpCloudOrderModel.setOrderNo(orderRecord.getSorderCode());
                }
            }
            amountPayMoneyTemp = BigDecimalUtils.add(amountPayMoneyTemp, orderRecord.getFtotalAmount());            // 累加订单总金额
            actualPayMoneyTemp = BigDecimalUtils.add(actualPayMoneyTemp, orderRecord.getFactualPayAmount());        // 累加订单实付金额
            discountedMoneyTemp = BigDecimalUtils.add(discountedMoneyTemp, orderRecord.getFdiscountAmount());       // 累加订单优惠金额

            List<OrderCommodity> orderCommodityList = creatOrderResult.getOrderCommodityList();
            if (CollectionUtils.isNotEmpty(orderCommodityList)) {
                GoodModel goodModel = null;
                CommodityInfo commodityInfo = null;
                String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);
                for (OrderCommodity orderCommodity : orderCommodityList) {
                    goodModel = new GoodModel();
                    goodModel.setGoodName(orderCommodity.getScommodityName());                                      // 商品名称
                    goodModel.setGoodPrice(orderCommodity.getFcommodityPrice().toString());                         // 商品价格
                    goodModel.setGoodsNumber(orderCommodity.getForderCount());                                      // 商品数量
                    commodityInfo = commodityInfoService.selectByPrimaryKey(orderCommodity.getScommodityId());
                    if (null != commodityInfo) {
                        goodModel.setGoodName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
                        goodModel.setGoodImgUrl(preUrl + commodityInfo.getScommodityImg());                         // 商品图片地址
                    }
                    goodsList.add(goodModel);
                    number = number + orderCommodity.getForderCount();
                }
            }
            num++;
        }

        // 封装参数
        httpCloudOrderModel.setAmountPayMoney(amountPayMoneyTemp.toString());
        httpCloudOrderModel.setActualPayMoney(actualPayMoneyTemp.toString());
        httpCloudOrderModel.setDiscountedMoney(discountedMoneyTemp.toString());
        httpCloudOrderModel.setNumber(number);
        httpCloudOrderModel.setPayType(type);
        httpCloudOrderModel.setGoodsList(goodsList);
        httpCloudOrderModel.setOrderType(10);

        logger.debug("http生成手动支付订单成功，设备ID:{}", deviceId);
        return ResponseVo.getSuccessResponse(httpCloudOrderModel);
    }


    /**
     * 校验基础参数
     * 从redis中获取相关对象
     *
     * @param deviceId 设备ID
     * @param userId   用户ID
     * @return
     */
    private Map<String, Object> verifyVo(String deviceId, String userId) {
        String id = deviceId + "_" + userId;
        SocketIOClient socketIOClient = null;                   // 服务器与手机端通信通道
        SessionVo sessionVo = null;                             // 用户信息
        ClientVo clientVo = null;                               // 设备信息
        SocketResponseVo<String> socketResponseVo = null;       // 返回给手机端消息体

        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(userId)) {
            logger.error("设备发送过来的消息中，用户ID为空");
            map.put("isNotNull", false);
            return map;
        }

        // 获取服务器与手机端通信通道
        socketIOClient = socketIoMap.get(id);

        // 从redis中获取扫码用户相关信息
        try {
            sessionVo = (SessionVo) iCached.get("soketIo_user_session_" + userId);
            if (null == sessionVo) {
                logger.error("从redis中获取扫码用户相关信息失败，处理关门成功消息失败，用户ID:{}", userId);
                map.put("isNotNull", false);
                socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "从redis中获取扫码用户相关信息失败");
                socketResponseVo.setTypes(50); // redis中没有找到对应的开门类型，默认为购物开门异常
                logger.error("默认为开门类型");
                asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                return map;
            }
        } catch (Exception e) {
            logger.error("从redis中获取扫码用户相关信息出现异常:{}", e);
            map.put("isNotNull", false);
            socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "从redis中获取扫码用户相关信息出现异常");
            socketResponseVo.setTypes(50); // redis中没有找到对应的开门类型，默认为购物开门异常
            logger.error("默认为开门类型");
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            return map;
        }

        // 从redis中获取设备相关信息
        try {
            clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, deviceId);
            if (null == clientVo) {
                logger.error("从redis中获取设备信息失败，处理关门成功消息失败，设备ID:{}", deviceId);
                map.put("isNotNull", false);
                socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "从redis中获取设备信息失败");
                Integer errorType = setErrorTypeByUserOpenDoorType(sessionVo.getTypes());
                socketResponseVo.setTypes(errorType);
                asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                return map;
            }
        } catch (Exception e) {
            logger.error("从redis中获取设备相关信息出现异常：{}", e);
            map.put("isNotNull", false);
            socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "从redis中获取设备相关信息出现异常");
            Integer errorType = setErrorTypeByUserOpenDoorType(sessionVo.getTypes());
            socketResponseVo.setTypes(errorType);
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            return map;
        }

        // 校验通过返回校验结果和对象
        map.put("socketIOClient", socketIOClient);
        map.put("sessionVo", sessionVo);
        map.put("clientVo", clientVo);
        map.put("isNotNull", true);
        return map;
    }


    /**
     * 给手机客户端异步发送消息
     *
     * @param socketEvent      事件类型
     * @param socketResponseVo 返回消息体
     * @param socketIOClient   socket对象
     */
    private void asynSendMsgToSocketIo(String socketEvent, SocketResponseVo socketResponseVo, SocketIOClient socketIOClient) {
        logger.info("socketIO事件类型：{}", socketEvent);
        logger.info("向手机客户端发送消息内容：{}", socketResponseVo);
        try {
            if (null != socketIOClient) {
                socketIOClient.sendEvent(socketEvent, JSON.toJSON(socketResponseVo));
            }
        } catch (Exception e) {
            logger.error("向手机客户端发送消息异常：{}", e);
        }
    }


    /**
     * 10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
     *
     * @param types
     * @return
     */
    private Integer setErrorTypeByUserOpenDoorType(Integer types) {
        Integer result = new Integer(50); // 默认为购物开门异常
        if (types.equals(20)) { //补货开门异常
            result = 60;
        }
        return result;
    }


}
