package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.utils.LogUtils;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.netty.vo.wap.ShoppingCartDomain;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.api.sh.service.MerchantInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.api.sp.service.CommodityInfoService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.inventory.*;
import com.cloud.cang.jy.CreatOrderResult;
import com.cloud.cang.jy.ExceptionOrderCommodityDto;
import com.cloud.cang.jy.ExceptionOrderDto;
import com.cloud.cang.jy.OrderDiscountDefine;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.open.sdk.model.response.GoodDetail;
import com.cloud.cang.open.sdk.model.response.ImgResultDetail;
import com.cloud.cang.utils.StringUtil;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

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
    private NettyMsgService nettyMsgService;
    @Autowired
    private MerchantInfoService merchantInfoService; // 商户信息

    private static final Logger logger = LoggerFactory.getLogger(CloudRecognitionService.class);

    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();      //netty通道
    private static ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();   //socketIo通道

    private static final String DEIVCE_STATUS = "deviceStatus";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String OPENDOOR = "openDoor";
    private static final String CLOSEDOOR = "closeDoor";
    private static final String REALTIME = "realTime";
    private static final String IS_OPEN_REALTIME = "isOpenRealTime";


    /**
     * 关门结算
     *
     * @param deviceId            设备ID
     * @param key                 通信密钥
     * @param userId              用户ID
     * @param openDoorType        开门类型
     * @param imgResultDetailList 图片base64集合
     * @return
     */
    public ResponseVo<String> closeDoorSettle(String deviceId, String key, String userId, Integer openDoorType, List<ImgResultDetail> imgResultDetailList) {
        logger.debug("关门结算服务开始，设备ID：{}", deviceId);
        ResponseVo<String> responseVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        SessionVo sessionVo = null;                                                 // 用户信息
        ClientVo clientVo = null;                                                   // 设备信息
        SocketIOClient socketIOClient = null;                                       // 服务器与手机端通信通道
        Integer types = null;    //10 开门 20 补货开门
        Integer sourceClientType = null; //来源客户端类型
        String clientIp = "";   // 开门IP地址
        String deviceCode = ""; // 设备编号

        /* 1.0 校验参数有效性*/
        logger.debug("校验参数有效性开始");
        Map<String, Object> statusMap = verifyDeviceInfo(deviceId, key, CLOSEDOOR);
        if (BooleanUtils.isFalse((Boolean) statusMap.get(DEIVCE_STATUS))) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo((String) statusMap.get(ERROR_MESSAGE));
        }
        logger.debug("校验参数有效性通过");

        logger.debug("对比开门前商品结果开始");
        // 获取设备相关信息
        Map<String, Integer> goodMapTemp = new HashMap<>();  // 临时存放从云端返回的图片中商品集合

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

        // 计算商品差，调用商品差盘货服务
        List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList = calcCloudCommodityDiff(goodMapTemp, deviceId);


        /* 3.0 调用后台订单服务*/
        Map<String, Object> verifyMap = verifyVo(deviceId, userId);
        Boolean isNotNull = (Boolean) verifyMap.get("isNotNull");                   // 基础参数，以及相关对象--sessionVo,clientVo,socketIOClient ====>校验结果
        if (BooleanUtils.isFalse(isNotNull)) {
            logger.error("http关门结算从Redis中获取设备及手机端信息为空，设备编号：{}", getDeviceCode(deviceId));
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

        // 创建Rest服务客户端,调用设备关门盘点（根据商品数量差）服务
        logger.debug("调用后台关门计算订单服务开始");
        RestServiceInvoker invoke = null;
        try {
            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.INVENTORY_COMMODITY_DIFF_SERVICE);

            InventoryDiffDto inventoryDiffDto = new InventoryDiffDto();
            inventoryDiffDto.setDeviceId(deviceId);                     // 设备ID
            inventoryDiffDto.setCloseType(openDoorType);                // 关门类型 10 购物 20 补货员 关门盘点必填
            inventoryDiffDto.setInventoryType(10);                      // 盘点类型 10 关门盘点
            inventoryDiffDto.setMemberId(userId);                       // 用户ID
            inventoryDiffDto.setIsourceClientType(sourceClientType);    // 来源类型
            inventoryDiffDto.setSip(clientIp);                          // 开门IP
            inventoryDiffDto.setInventoryCommodityDiffVoList(inventoryCommodityDiffVoList); // 关门对比商品集合

            invoke.setRequestObj(inventoryDiffDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<InventoryResult>>() {
            });
            ResponseVo<InventoryResult> responseInventoryResult = (ResponseVo<InventoryResult>) invoke.invoke();

            SocketResponseVo<String> socketResponseVo = SocketResponseVo.getSuccessResponse();
            logger.debug("云端识别柜子 --> 扫码开门 -> 发送http关门盘货请求，调用关门盘货服务完成，调用结果：{}，设备编号：{}", responseVo.isSuccess(), getDeviceCode(deviceId));
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


                    //创建代扣支付订单成功
                    if (inventoryResult.getItype().intValue() == 10) {//创建代扣支付订单成功
                        nettyMsgService.createPayOrder(deviceId, userId, inventoryResult.getMerchantCode(), inventoryResult.getOrderRecords(), ctxMap);
                        logger.debug("扫码开门 -> 关门盘货 -> 创建支付代扣订单成功,设备ID：{}", deviceId);
                    }

                    //更新关门日志
                    LogUtils.updateOPLog(deviceCode, userId, 10);

                    return ResponseVo.getSuccessResponse();

                } else if (inventoryResult.getItype().intValue() == 30) { //补货关门
                    socketResponseVo.setTypes(40);
                    String recordCode = inventoryResult.getReplenishmentResult().getReplenishmentRecord().getScode();
                    if (StringUtil.isNotBlank(recordCode)) {
                        socketResponseVo.setData(recordCode);//补货单号
                    }
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);

                    //更新关门日志
                    LogUtils.updateOPLog(deviceCode, userId, 20);
                    logger.debug("更新关门日志，设备编号：{}，用户ID：{}", deviceCode, userId);
                    return ResponseVo.getSuccessResponse();

                } else if (inventoryResult.getItype().intValue() == 50) { // 游客
                    Integer userType = null;
                    if (openDoorType == 10) {//购物开门
                        logger.debug("扫码开门 -> 关门盘货 -> 游客类型，给设备返回消息，设备编号：{}", getDeviceCode(deviceId));
                        socketResponseVo.setTypes(30);
                        LogUtils.updateOPLog(deviceCode, userId, 10);   // 更新关门日志
                        logger.debug("更新关门日志，设备编号：{}，用户ID：{}", deviceCode, userId);
                    } else if (openDoorType == 20) {//补货员开门
                        logger.debug("扫码开门 -> 关门盘货 -> 补货员类型，给设备返回消息，设备编号：{}", getDeviceCode(deviceId));
                        socketResponseVo.setTypes(40);
                        LogUtils.updateOPLog(deviceCode, userId, 20);   // 更新关门日志
                        logger.debug("更新关门日志，设备编号：{}，用户ID：{}", deviceCode, userId);
                    }
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                    return ResponseVo.getSuccessResponse();
                }

            } else {
                logger.error("调用关门盘货服务失败，设备编号：{}", getDeviceCode(deviceId));
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

                logger.error("扫码开门 -> 关门盘货 -> 向设备发送调用关门盘货服务失败消息，设备编号：{}，用户ID:{}", getDeviceCode(deviceId), userId);
                responseVo.setMsg("调用关门盘货服务失败");
                return responseVo;
            }
        } catch (Exception e) {
            responseVo.setMsg("调用关门盘货服务失败");
            return responseVo;
        }
        logger.debug("调用后台订单服务结束");
        return responseVo;
    }

    /**
     * 当前关门图片集合中的商品数量对比开门前图片集合中的商品数量
     *
     * @param closeDoorMap 关门商品集合
     * @return
     */
    private List<InventoryCommodityDiffVo> calcCloudCommodityDiff(Map<String, Integer> closeDoorMap, String deviceId) {
        Map<String, Integer> openDoorMap = null;
        List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList = new ArrayList<>();
        try {
            String goodMap = (String) iCached.get(NettyConst.CLOUD_DEVICE_OPEN_DOOR_COMMODITYS + deviceId);    // 在Redis中记录开门商品

            openDoorMap = JSON.parseObject(goodMap, new TypeReference<Map<String, Integer>>() {
            });
        } catch (Exception e) {
            logger.error("从Redis中获取开门商品集合失败，设备编号：{}", getDeviceCode(deviceId));
        }


        // 开关门前后都有商品，计算增加与减少的商品信息
        Map<String, Integer> publicCommodity = new HashMap<>(); // 相同的商品
        for (Map.Entry<String, Integer> openDoor : openDoorMap.entrySet()) {
            for (Map.Entry<String, Integer> closeDoor : closeDoorMap.entrySet()) {
                String openId = openDoor.getKey();
                String closeId = closeDoor.getKey();
                Integer openNum = openDoor.getValue();
                Integer closeNum = closeDoor.getValue();
                if (openId.equals(closeId)) {
                    InventoryCommodityDiffVo commodityDiffVo = null;
                    publicCommodity.put(openId, 0);  // 相同的商品
                    Integer resultNum = openNum - closeNum;
                    if (resultNum == 0) { // 商品没变化
                        commodityDiffVo = setInventoryCommodityDiffVo(openId, 0, 0, closeNum);
                    } else if (resultNum < 0) { // 商品增加
                        commodityDiffVo = setInventoryCommodityDiffVo(openId, Math.abs(resultNum), 0, closeNum);
                    } else {    // 商品减少
                        commodityDiffVo = setInventoryCommodityDiffVo(openId, 0, resultNum, closeNum);
                    }
                    inventoryCommodityDiffVoList.add(commodityDiffVo);
                }
            }
        }

        // 移除相同的商品，计算不同的商品
        Map<String, Integer> tempOpenMap = new HashMap<>();
        Map<String, Integer> tempCloseMap = new HashMap<>();
        tempCloseMap.putAll(closeDoorMap);
        tempOpenMap.putAll(openDoorMap);
        tempCloseMap = removeSameKey(tempCloseMap, publicCommodity);
        tempOpenMap = removeSameKey(openDoorMap, publicCommodity);

        // 除去相同的商品后新增
        if (MapUtils.isNotEmpty(tempCloseMap)) {
            for (Map.Entry<String, Integer> map : tempCloseMap.entrySet()) {
                InventoryCommodityDiffVo commodityDiffVo = setInventoryCommodityDiffVo(map.getKey(), map.getValue(), 0, map.getValue());
                inventoryCommodityDiffVoList.add(commodityDiffVo);
            }
        }

        // 除去相同的商品后减少
        if (MapUtils.isNotEmpty(tempOpenMap)) {
            for (Map.Entry<String, Integer> map : tempOpenMap.entrySet()) {
                InventoryCommodityDiffVo commodityDiffVo = setInventoryCommodityDiffVo(map.getKey(), 0, map.getValue(), 0);
                inventoryCommodityDiffVoList.add(commodityDiffVo);
            }
        }
        return inventoryCommodityDiffVoList;
    }

    /**
     * 移除相同键项
     *
     * @param tempCloseMap    待移除的map
     * @param publicCommodity 公共的map
     * @return 移除后的map
     */
    private Map<String, Integer> removeSameKey(Map<String, Integer> tempCloseMap, Map<String, Integer> publicCommodity) {
        for (Map.Entry<String, Integer> map : publicCommodity.entrySet()) {
            tempCloseMap.remove(map.getKey());
        }
        return tempCloseMap;
    }


    /**
     * 设置对象属性
     *
     * @param svrCode    视觉编号
     * @param increment  增加数量
     * @param decrement  减少数量
     * @param remainsNum 剩余数量
     * @return
     */
    private InventoryCommodityDiffVo setInventoryCommodityDiffVo(String svrCode, int increment, int decrement, int remainsNum) {
        InventoryCommodityDiffVo inventoryCommodityDiffVo = new InventoryCommodityDiffVo();
        inventoryCommodityDiffVo.setSvrCode(svrCode);
        inventoryCommodityDiffVo.setIncrement(increment);
        inventoryCommodityDiffVo.setDecrement(decrement);
        inventoryCommodityDiffVo.setRemainsNum(remainsNum);
        return inventoryCommodityDiffVo;
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
                logger.error("从redis中获取设备信息失败，处理关门成功消息失败，设备编号:{}", getDeviceCode(deviceId));
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


    /**
     * 云识别接口实时订单计算
     *
     * @param deviceId            设备ID
     * @param key                 通信秘钥
     * @param userId              用户ID
     * @param openDoorType        开门类型 10=开门 20=补货开门
     * @param imgResultDetailList 开门图片
     * @return
     */
    public ResponseVo<String> realtimeOrder(String deviceId, String key, String userId, Integer openDoorType, List<ImgResultDetail> imgResultDetailList) {
        logger.debug("实时订单计算服务开始，设备编号：{}", getDeviceCode(deviceId));
        SocketIOClient socketIOClient = null;                                       // 服务器与手机端通信通道

        Map<String, Object> verifyMap = verifyDeviceInfo(deviceId, key, REALTIME);
        if (BooleanUtils.isFalse((Boolean) verifyMap.get(DEIVCE_STATUS))) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo((String) verifyMap.get(ERROR_MESSAGE));
        }

        // 获取服务器与手机端通信通道
        socketIOClient = socketIoMap.get(deviceId + "_" + userId);
        if (null == socketIOClient) {
            logger.error("手机端离线");
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("手机端离线");
        }

        logger.debug("对比开门盘货结果开始");
        Map<String, Integer> goodMapTemp = new HashMap<>();  // 临时存放从云端返回的图片中商品集合
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

        List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList = calcCloudCommodityDiff(goodMapTemp, deviceId);

        // 补货员实时订单
        if (null != openDoorType && openDoorType == 20) {
            SocketResponseVo<String> socketResponseVo = SocketResponseVo.getSuccessResponse();
            ShoppingCartDomain shoppingCartDomain = new ShoppingCartDomain();
            List<RealTimeCommodityResult> addCommodityInfoList = new ArrayList<>(); // 上架商品（补货专用）
            List<RealTimeCommodityResult> subCommodityInfoList = new ArrayList<>(); // 下架商品（补货专用）
            RealTimeCommodityResult tempCom = null;
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
            Integer shelfNum = 0;
            Integer obtainedNum = 0;
            for (InventoryCommodityDiffVo com : inventoryCommodityDiffVoList) {
                Integer de = com.getDecrement();
                Integer in = com.getIncrement();
                if (de == 0 && in > 0) {
                    tempCom = assemblyCcommodity(com, deviceInfo, tempCom);
                    if (null != tempCom) {
                        tempCom.setNumber(in);
                        addCommodityInfoList.add(tempCom);
                        shelfNum += in;
                    }
                } else if (in == 0 && de > 0) {
                    tempCom = assemblyCcommodity(com, deviceInfo, tempCom);
                    if (null != tempCom) {
                        tempCom.setNumber(de);
                        subCommodityInfoList.add(tempCom);
                        obtainedNum += de;
                    }
                }
            }

            shoppingCartDomain.setAddCommodityInfoList(addCommodityInfoList);
            shoppingCartDomain.setSubCommodityInfoList(subCommodityInfoList);
            shoppingCartDomain.setObtainedNum(obtainedNum);
            shoppingCartDomain.setShelfNum(shelfNum);

            try {
                iCached.put(RedisConst.CLOUD_REALTIME_DISTINGUISH + "_" + deviceId + "_" + userId, JSON.toJSONString(shoppingCartDomain), 7200);
            } catch (Exception e) {
                logger.error("补货员补货实时盘货从Redis中取值出现错误");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("补货员补货出现错误");
            }
            socketResponseVo.setTypes(80);
            socketResponseVo.setData("");
            socketResponseVo.setMsg("补货购物车为空");
            if (CollectionUtils.isNotEmpty(subCommodityInfoList) || CollectionUtils.isNotEmpty(addCommodityInfoList)) {
                socketResponseVo.setData(deviceId + "_" + userId);
                socketResponseVo.setMsg("补货购物车中有商品");
            }
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            return ResponseVo.getSuccessResponse("调用云端识别实时订单服务成功");
        }

        logger.debug("调用（根据商品数量差）设备开门实时盘货服务开始");
        RestServiceInvoker invoke = null;
        try {
            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.CREATE_REAL_TIME_COMMOFITY_DIFF_SERVICE);
            RealTimeCommodityDiffOrderDto realTimeDto = new RealTimeCommodityDiffOrderDto();
            realTimeDto.setDeviceId(deviceId);
            realTimeDto.setMemberId(userId);
            realTimeDto.setIsourceClientType(50);
            realTimeDto.setInventoryCommodityDiffVoList(inventoryCommodityDiffVoList);
            invoke.setRequestObj(realTimeDto);
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<RealTimeOrderResult>>() {
            });
            ResponseVo<RealTimeOrderResult> responseInventoryResult = (ResponseVo<RealTimeOrderResult>) invoke.invoke();
            SocketResponseVo<String> socketResponseVo = SocketResponseVo.getSuccessResponse();
            logger.debug("云端识别柜子 --> 扫码开门 -> 发送http关门盘货请求，调用关门盘货服务完成，调用结果：{}，设备编号：{}", responseInventoryResult.isSuccess(), getDeviceCode(deviceId));
            if (responseInventoryResult.isSuccess()) {
                RealTimeOrderResult realTimeOrderResult = responseInventoryResult.getData();
                if (null != realTimeOrderResult) {
                    ShoppingCartDomain shoppingCartDomain = new ShoppingCartDomain();
                    shoppingCartDomain.setActuallyPaid(realTimeOrderResult.getFactualPayAmount().toString());
                    shoppingCartDomain.setDiscountedPrice(realTimeOrderResult.getFdiscountAmount().toString());
                    List<RealTimeCommodityResult> commodityResultList = realTimeOrderResult.getResults();
                    Integer tempNmu = new Integer(0);
                    for (RealTimeCommodityResult commodity : commodityResultList) {
                        tempNmu = tempNmu + commodity.getNumber();
                    }
                    shoppingCartDomain.setTotalNum(tempNmu);
                    shoppingCartDomain.setCommodityInfoList(commodityResultList);
                    iCached.put(RedisConst.CLOUD_REALTIME_DISTINGUISH + "_" + deviceId + "_" + userId, JSON.toJSONString(shoppingCartDomain), 7200);
                    socketResponseVo.setTypes(70);
                    socketResponseVo.setData(deviceId + "_" + userId);
                    socketResponseVo.setMsg("设备ID_用户ID，购物车");
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                } else {
                    socketResponseVo.setTypes(70);
                    socketResponseVo.setData("");
                    socketResponseVo.setMsg("购物车为空");
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                }
                return ResponseVo.getSuccessResponse("调用云端识别实时订单服务成功");
            }
        } catch (Exception e) {
            logger.error("云端识别调用开门实时盘货服务出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用开门实时服务失败");
    }


    /**
     * 验证设备状态及信息是否正确
     *
     * @param deviceId 设备ID
     * @param key      通信秘钥
     * @param type     方法类型
     * @return
     */
    public Map<String, Object> verifyDeviceInfo(String deviceId, String key, String type) {
        Map<String, Object> map = new HashMap<>();
        map.put(DEIVCE_STATUS, false);
        map.put(IS_OPEN_REALTIME, "close");

        // 注册信息有效性
        DeviceRegister deviceRegisterVo = new DeviceRegister();
        deviceRegisterVo.setSdeviceId(deviceId);
        deviceRegisterVo.setSsecurityKey(key);
        deviceRegisterVo.setIdelFlag(0);
        List<DeviceRegister> deviceRegisterList = deviceRegisterService.selectByEntityWhere(deviceRegisterVo);
        if (CollectionUtils.isEmpty(deviceRegisterList)) {
            logger.error("设备注册信息有误，设备设备编号：{}", getDeviceCode(deviceId));
            map.put(ERROR_MESSAGE, "设备的注册信息有误");
            return map;
        }
        // 设备状态有效性
        DeviceInfo deviceInfoVo = new DeviceInfo();
        deviceInfoVo.setId(deviceId);
        deviceInfoVo.setItype(50);  //  10 传统 20 RFID射频 30 视觉 40 云识别
        deviceInfoVo.setIstatus(20);  // 10=未注册 20=正常 30=故障 40=报废 50=离线
        deviceInfoVo.setIdelFlag(0);
        deviceInfoVo.setIoperateStatus(10); //  10 启用 20 禁用
        List<DeviceInfo> deviceInfoList = deviceInfoService.selectByEntityWhere(deviceInfoVo);
        if (CollectionUtils.isEmpty(deviceInfoList)) {
            logger.error("设备状态不正确，设备设备编号：{}", getDeviceCode(deviceId));
            map.put(ERROR_MESSAGE, "设备为非正常运营状态");
            return map;
        }
        // 判断实时订单选项是否开启(待数据库更新)
        if (StringUtils.isNotBlank(type) && type.equals(REALTIME)) {
            DeviceInfo deviceInfo = deviceInfoList.get(0);
            if (deviceInfo.getIisOpeningInventory() == 1) {
                map.put(IS_OPEN_REALTIME, "open");
            }
        }
        map.put(DEIVCE_STATUS, true);
        return map;
    }


    /**
     * 通过设备ID查询设备编号
     *
     * @param deviceId 设备ID
     * @return 设备编号
     */
    private String getDeviceCode(String deviceId) {
        try {
            DeviceInfo deviceInfo = (DeviceInfo) iCached.hget(NettyConst.DEVICE_INFO, deviceId);
            if (deviceInfo != null) {
                return deviceInfo.getScode();
            }
        } catch (Exception e) {
            logger.error("设备ID：{} 从redis中获取或者存入数据异常：{}", deviceId, e);
        }
        return "未知编号";
    }

    /**
     * 记录云端识别开门盘货商品参数
     *
     * @param deviceId            设备ID
     * @param imgResultDetailList openSdk识别结果
     * @return 是否开启实时盘货结果
     */
    public ResponseVo<String> openDoorSettle(String deviceId, List<ImgResultDetail> imgResultDetailList) {
        Map<String, Integer> goodMapTemp = new HashMap<>();  // 临时存放从云端返回的图片中商品集合
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

        // 记录设备商品库存信息（有没有库存概念，只需要对比开门关门差吗）
        String goodMap = JSON.toJSONString(goodMapTemp);
        try {
            iCached.put(NettyConst.CLOUD_DEVICE_OPEN_DOOR_COMMODITYS + deviceId, goodMap,2*60*60);
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
            if (null != deviceInfo.getIisOpeningInventory() && deviceInfo.getIisOpeningInventory() == 1) {
                return ResponseVo.getSuccessResponse("open");     // 0:否,1:是
            }
            return ResponseVo.getSuccessResponse("close");     // 0:否,1:是
        } catch (Exception e) {
            logger.error("云端识别开门商品集合记录出现异常");
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
    }

    /**
     * 组装补货商品详细信息
     * @param com
     * @param deviceInfo
     * @param tempCom
     * @return
     */
    private RealTimeCommodityResult assemblyCcommodity(InventoryCommodityDiffVo com, DeviceInfo deviceInfo, RealTimeCommodityResult tempCom) {
        tempCom = new RealTimeCommodityResult();
        CommodityInfo commodityInfoVo = new CommodityInfo();
        commodityInfoVo.setSvrCode(com.getSvrCode());
        commodityInfoVo.setSmerchantId(deviceInfo.getSmerchantId());
        commodityInfoVo.setIstatus(10);
        commodityInfoVo.setIdelFlag(0);
        List<CommodityInfo> commodityInfoList = commodityInfoService.selectByEntityWhere(commodityInfoVo);
        if (CollectionUtils.isNotEmpty(commodityInfoList)) {
            CommodityInfo commodityInfo = commodityInfoList.get(0);
            if (null != commodityInfo) {
                tempCom.setScommodityCode(commodityInfo.getScode());
                tempCom.setScommodityId(commodityInfo.getId());
                tempCom.setScommodityFullName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit());
                tempCom.setScommodityName(commodityInfo.getSname());
                tempCom.setFcommodityPrice(commodityInfo.getFsalePrice());
                tempCom.setScommodityImg(commodityInfo.getScommodityImg());
                return tempCom;
            }
        }
        return null;
    }


    public ResponseVo<String> cloudException(String deviceId, String key, String userId, Integer openDoorType, Integer methodType, String errorCode) throws Exception {
        logger.info("开关门异常处理, 校验参数有效性开始 错误代码:{}", errorCode);
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        Map<String, Object> statusMap = verifyDeviceInfo(deviceId, key, CLOSEDOOR);
        if (BooleanUtils.isFalse((Boolean) statusMap.get(DEIVCE_STATUS))) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo((String) statusMap.get(ERROR_MESSAGE));
        }
        SocketIOClient socketIOClient = null;
        SessionVo sessionVo = null;                                                 // 用户信息
        Map<String, Object> verifyMap = verifyVo(deviceId, userId);
        Boolean isNotNull = (Boolean) verifyMap.get("isNotNull");                   // 基础参数，以及相关对象--sessionVo,clientVo,socketIOClient ====>校验结果
        if (BooleanUtils.isFalse(isNotNull)) {
            logger.error("http关门结算从Redis中获取设备及手机端信息为空，设备编号：{}", getDeviceCode(deviceId));
            responseVo.setMsg("手机端离线");
            return responseVo;
        }
        // 获取相应对象
        socketIOClient = (SocketIOClient) verifyMap.get("socketIOClient");
        sessionVo = (SessionVo) verifyMap.get("sessionVo");
        SocketResponseVo<String> socketResponseVo = SocketResponseVo.getSuccessResponse();
        if(methodType.intValue() == 10) {//开门
            if (openDoorType == 20) {
                socketResponseVo.setTypes(20);//补货开门异常
            } else {
                socketResponseVo.setTypes(10);//购物开门异常
            }
            socketResponseVo.setErrorCode(ReturnCodeEnum.ERROR_OPENDOOR_FAILED.getCode()); // 购物异常错误代码
            socketResponseVo.setMsg("开门异常，请联系客服");
            //socketResponseVo.setSuccess(true);
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);

        } else if(methodType.intValue() == 20) {//关门

            logger.error("调用关门盘货服务失败，设备编号：{}", getDeviceCode(deviceId));
            if (openDoorType == 20) {
                socketResponseVo.setTypes(60);//补货异常
                socketResponseVo.setErrorCode(ReturnCodeEnum.ERROR_REPLENISHMENT_EXCEPTION.getCode()); // 补货异常错误代码
                socketResponseVo.setMsg("补货异常，请联系客服");
            } else {
                socketResponseVo.setTypes(50);//购物异常
                socketResponseVo.setErrorCode(ReturnCodeEnum.ERROR_SHOPPING_EXCEPTION.getCode()); // 购物异常错误代码
                socketResponseVo.setMsg("购物异常，请联系客服");
            }
            //socketResponseVo.setSuccess(true);
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            logger.error("扫码开门 -> 关门盘货 -> 向设备发送调用关门盘货服务失败消息，设备编号：{}，用户ID:{}", getDeviceCode(deviceId), userId);
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
            if (openDoorType != 20) {
                generateExceptionOrder(deviceInfo, sessionVo);
            }
        }
        return responseVo;
    }



    private ResponseVo<String> generateExceptionOrder(DeviceInfo deviceInfo, SessionVo sessionVo) {
        try {
            //生成异常审核订单
            ExceptionOrderDto exceptionOrderDto = new ExceptionOrderDto();
            exceptionOrderDto.setSdeviceId(deviceInfo.getId());
            exceptionOrderDto.setSdeviceCode(deviceInfo.getScode());
            exceptionOrderDto.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
            exceptionOrderDto.setSreaderSerialNumber(deviceInfo.getSreaderSerialNumber());
            exceptionOrderDto.setSmerchantId(deviceInfo.getSmerchantId());
            exceptionOrderDto.setSmerchantCode(deviceInfo.getSmerchantCode());
            exceptionOrderDto.setSmemberCode(sessionVo.getUserCode());
            exceptionOrderDto.setSmemberId(sessionVo.getUserId());
            exceptionOrderDto.setSmemberName(sessionVo.getUserName());
            exceptionOrderDto.setSdeviceName(deviceInfo.getSname());
            exceptionOrderDto.setIsourceClientType(sessionVo.getIsourceClientType());
            exceptionOrderDto.setItype(10);//盘点异常
            exceptionOrderDto.setFtotalAmount(BigDecimal.ZERO);

            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OrderDiscountDefine.CREATE_EXCEPTION_ORDER);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {});
            invoke.setRequestObj(exceptionOrderDto); // post 参数
            ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
            return resVO;
        } catch (Exception e) {
            logger.error("异常订单生成异常：{}", e);
        }
        return null;
    }
}
