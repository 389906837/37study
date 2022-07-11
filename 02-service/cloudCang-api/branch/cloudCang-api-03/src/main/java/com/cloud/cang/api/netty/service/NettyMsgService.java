package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.AndroidErrorCode;
import com.cloud.cang.api.common.NettyConstant;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.WebSocketConstant;
import com.cloud.cang.api.common.utils.*;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.netty.vo.aiFace.FaceDeviceVo;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceUpgradeDetailsService;
import com.cloud.cang.api.sh.service.MerchantInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.sl.service.DeviceOperService;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.api.sp.service.CommodityInfoService;
import com.cloud.cang.api.tb.service.InterfaceTransferLogService;
import com.cloud.cang.api.tb.service.OperateDeviceDetailService;
import com.cloud.cang.api.tb.service.OperateDeviceRecordService;
import com.cloud.cang.api.tb.service.ThirdDeviceSkuService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.CommonConstants;
import com.cloud.cang.common.ErrorCode;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.common.TypeConstant;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.*;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.faceCommon.FaceType;
import com.cloud.cang.inventory.*;
import com.cloud.cang.jy.CreatOrderResult;
import com.cloud.cang.model.*;
import com.cloud.cang.model.LocalGravityVision.CargoRoadGravityModel;
import com.cloud.cang.model.LocalGravityVision.LayerGravityModel;
import com.cloud.cang.model.LocalGravityVision.LocalGVGoodModel;
import com.cloud.cang.model.LocalGravityVision.LocalGVGoods;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceUpgradeDetails;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.tb.OperateDeviceDetail;
import com.cloud.cang.model.tb.OperateDeviceRecord;
import com.cloud.cang.model.tb.ThirdDeviceSku;
import com.cloud.cang.pay.FreePaymentDto;
import com.cloud.cang.pay.FreePaymentResult;
import com.cloud.cang.pay.FreeServicesDefine;
import com.cloud.cang.pojo.BaseResponseVo;
import com.cloud.cang.utils.DateUtils;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * @version 1.0
 * @ClassName NettyMsgService
 * @Description netty后台消息处理类
 * @Author zengzexiong
 * @Date 2018年3月31日13:52:34
 */
@Service("nettyMsgService")
public class NettyMsgService {

    @Autowired
    private ICached iCached;

    @Autowired
    DeviceInfoService deviceInfoService;

    @Autowired
    CommodityInfoService commodityInfoService;

    @Autowired
    DeviceUpgradeDetailsService deviceUpgradeDetailsService;    // 升级明细表

    @Autowired
    ThirdDeviceSkuService thirdDeviceSkuService;    // 第三方商户sku

    @Autowired
    OperateDeviceRecordService operateDeviceRecordService; // 第三方商户操作设备记录表

    @Autowired
    OperateDeviceDetailService operateDeviceDetailService;  // 第三方商户操作设备记录明细表

    @Autowired
    DeviceOperService deviceOperService;    //  设备操作日志

    @Autowired
    InterfaceTransferLogService interfaceTransferLogService;    // 第三方接口调用记录表


    @Autowired
    private MerchantInfoService merchantInfoService;
    private static final Logger logger = LoggerFactory.getLogger(NettyMsgService.class);
    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();      //netty通道
    private static ConcurrentMap<String, ChannelHandlerContext> faceMap = SingletonClientMap.newInstance().getAiFaceMap();  //face通道
    private static ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();   //socketIo通道


    /**
     * 设备发送过来的开门成功消息
     * @param baseResponseVo 设备消息体
     * @return
     * @throws Exception
     */
    public void openDoorSuccess(BaseResponseVo baseResponseVo)  {
        logger.info("准备向手机端发送开门成功消息,用户ID：{}", baseResponseVo.getUserId());
        String deviceId = baseResponseVo.getDeviceId();
        String userId = baseResponseVo.getUserId();
        SocketResponseVo socketResponseVo = SocketResponseVo.getSuccessResponse();
        String id = deviceId + "_" + userId;
        SocketIOClient socketIOClient = socketIoMap.get(id);
        Integer types = null;
        Integer openSource = baseResponseVo.getOpenSource();

        // 判断开门来源类型，10：人脸识别，20：扫码开门
        if (null == openSource || (null != openSource && 20 == openSource)) {
            if (null == openSource) {
                logger.debug("开门来源为空，手机端用户ID：{}", userId);
            }
            //手机端与服务器通信通道断开
            if (null == socketIOClient) {
                logger.error("手机端与服务器通信通道连接断开，手机端用户ID:{}", userId);
                return;
            }

            //记录开门人信息到redis
            SessionVo sessionVo = null;
            try {
                sessionVo = (SessionVo) iCached.get("soketIo_user_session_" + userId);
            } catch (Exception e) {
                logger.error("从redis中查询 SessionVo 信息出现异常：{}", e);
                return;
            }
            if (null == sessionVo) {
                logger.error("支付宝连接信息不在redis中，处理关门成功消息失败，用户ID:{}", userId);
                return;
            }

            types = sessionVo.getTypes();   // 开门类型
            socketResponseVo.setTypes(types);
            socketResponseVo.setData(deviceId);//返回开门设备ID

            //设备操作日志--记录开门成功
            //String ip = IPUtils.getIpString(socketIOClient);
            LogUtils.addOPLog(sessionVo);

            //发送消息给手机客户端
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);

            //如果有小屏设备，发送消息给小屏
            sendMsgToAiFace(deviceId, userId, "大屏扫码开门成功");

        } else if (null != openSource && 10 == openSource) {    // 人脸识别设备开门成功
            types = 10;
            //发送消息给小屏
            sendMsgToAiFace(deviceId, userId, "刷脸登录开门成功");
        } else {
            logger.error("接收到售货机设备发送开门成功消息，但是未知开门来源类型，开门来源类型:{}，用户ID：{}", openSource, userId);
            return;
        }

        // 跟新clientVo信息，放入redis中
        ClientVo clientVo = null;
        try {
            clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, deviceId);

            if (null == clientVo) { // 从redis中获取该设备自定义信息为空
                clientVo = new ClientVo();
            }
        } catch (Exception e) {
            logger.error("从redis中查询 ClientVo 信息出现异常：{}", e);
            return;
        }

        clientVo.setOpenDoorType(types);
        clientVo.setUserId(userId);                         //开门人ID
        clientVo.setOpenDoorTime(DateUtils.getCurrentDateTime());
        clientVo.setDoor(20);   // 门状态 10关闭 20开着
        clientVo.setDeviceId(deviceId);
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
        if (null != deviceInfo) {
            clientVo.setDeviceCode(deviceInfo.getScode());
        }
        ChannelHandlerContext ctx = ctxMap.get(deviceId);
        if (null != ctx) {
            clientVo.setCtxId(ctx.channel().id().asLongText());
        }

        try {
            iCached.hset(NettyConst.SYN_CLIENT_MAP, deviceId, clientVo);
            iCached.hset(NettyConst.OPENDOOR_USER_ID, deviceId, userId);            // 设备开门人ID
        } catch (Exception e) {
            logger.error("向redis中写入ClientVo出现异常：{}", e);
        }

        logger.debug("设备开门处理完成，用户ID:{}", userId);
        return;
    }

    /**
     * 关门盘货消息
     *
     * @param baseResponseVo
     * @return
     * @throws Exception
     */
    public void closeDoorInventorySuccess(BaseResponseVo baseResponseVo)  {
        logger.info("开始处理关门盘货成功消息消息,设备ID：{}，用户ID：{}", baseResponseVo.getDeviceId(), baseResponseVo.getUserId());
        String deviceId = baseResponseVo.getDeviceId();
        String userId = baseResponseVo.getUserId();
        String goodsString = baseResponseVo.getData();
        Goods goods = new Goods();                                                  // 设备商品集合mode
        Integer closeType = null;                                                   // 关门类型 10购物 20补货 30游客
        SessionVo sessionVo = null;                                                 // 用户信息
        ClientVo clientVo = null;                                                   // 设备信息
        SocketIOClient socketIOClient = null;                                       // 服务器与手机端通信通道
        String deviceCode = "";//设备编号
        Integer sourceClientType = null; //来源客户端类型
        Integer openDoorType = null; //开门类型 10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
        String clientIp = "";   // 开门IP地址
        Integer types = null;    //10 开门 20 补货开门
        Integer openSource = baseResponseVo.getOpenSource();//开门来源 10 人脸识别，20 扫码（支付宝，微信）

        // 判断开门来源 10：人脸识别，20或者null：扫码
        if (null == openSource || (null != openSource && 20 == openSource)) {
            logger.debug("当前开门来源为扫码开门");
            // 校验基础参数，判断从redis中获取相关对象是否为空
            Map<String, Object> verifyMap = verifyVo(deviceId, userId);
            Boolean isNotNull = (Boolean) verifyMap.get("isNotNull");                   // 基础参数，以及相关对象--sessionVo,clientVo,socketIOClient ====>校验结果
            if (BooleanUtils.isFalse(isNotNull)) {
                return;
            }
            // 获取相应对象
            sessionVo = (SessionVo) verifyMap.get("sessionVo");
            clientVo = (ClientVo) verifyMap.get("clientVo");
            socketIOClient = (SocketIOClient) verifyMap.get("socketIOClient");
            types = sessionVo.getTypes();


            // 将商品对象由字符串转化为对象
            try {
                if (StringUtil.isNotBlank(goodsString) && !goodsString.equals("[]")) {
                    goods = JSON.parseObject(goodsString, Goods.class);                 //商品集合
                }
            } catch (Exception e) {
                logger.error("商品集合json格式转化出现异常:{}", e);
                SocketResponseVo socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "商品集合json格式转化出现异常");
                Integer errorType = setErrorTypeByUserOpenDoorType(types);
                socketResponseVo.setTypes(errorType);
                asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                return;
            }

            deviceCode = clientVo.getDeviceCode();                               //设备编号
            sourceClientType = sessionVo.getIsourceClientType();                //来源客户端类型
            clientIp = sessionVo.getSip();
            openDoorType = sessionVo.getTypes();                          //开门类型
            if (null == openDoorType) {
                logger.debug("开门类型为空");
            } else if (10 == openDoorType) {
                logger.debug("购物开门");
            } else if (20 == openDoorType) {
                logger.debug("补货开门");
            }


            // 创建Rest服务客户端,调用关门盘货服务
            RestServiceInvoker invoke = null;
            try {
                invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.INVENTORY_DEALWITH_SERVICE);

                InventoryDto inventoryDto = new InventoryDto();
                inventoryDto.setDeviceId(deviceId);                     // 设备ID
                inventoryDto.setCloseType(openDoorType);                // 关门类型 10 购物 20 补货员 关门盘点必填
                inventoryDto.setInventoryType(10);                      // 盘点类型 10 关门盘点
                inventoryDto.setMemberId(userId);                       // 用户ID
                inventoryDto.setIsourceClientType(sourceClientType);          // 来源类型
                inventoryDto.setSip(clientIp);
                List<CommodityVo> commodityVoList = new ArrayList<>();      // 临时商品对象
                List<TagModel> goodsList = goods.getGoodsList();
                if (CollectionUtils.isNotEmpty(goodsList)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                    commodityVoList = assemblyCommodityVo(commodityVoList, goodsList);//组装商品信息
                }
                if (CollectionUtils.isEmpty(commodityVoList)) {
                    logger.info("Android设备传输过来的商品集合对象为空");
                }

                inventoryDto.setCommodityVos(commodityVoList);
                invoke.setRequestObj(inventoryDto);
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<InventoryResult>>() {
                });
                ResponseVo<InventoryResult> responseVo = (ResponseVo<InventoryResult>) invoke.invoke();

                SocketResponseVo<String> socketResponseVo = SocketResponseVo.getSuccessResponse();
                logger.debug("扫码开门 -> 关门盘货，调用关门盘货服务完成，调用结果：{}，设备ID：{}", responseVo.isSuccess(), deviceId);
                // 处理调用服务成功的结果
                if (responseVo.isSuccess()) {
                    logger.debug("扫码开门调用关门盘货服务成功,设备ID：{}", deviceId);
                    InventoryResult inventoryResult = responseVo.getData();
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

                        // 给设备推送订单
                        asynSendShoppingOrderByCtxMap(deviceId, userId, responseVo.getData().getItype(), orderRecords, ctxMap, 10);
                        logger.debug("扫码开门 -> 关门盘货 -> 给大屏设备推送购物订单，设备ID：{}", deviceId);

                        if (responseVo.getData().getItype().intValue() == 10) {//创建代扣支付订单成功
                            createPayOrder(deviceId, userId, responseVo.getData().getMerchantCode(), responseVo.getData().getOrderRecords(), ctxMap);
                            logger.debug("扫码开门 -> 关门盘货 -> 创建支付代扣订单成功,设备ID：{}", deviceId);
                        }


                    } else if (inventoryResult.getItype().intValue() == 30) { //补货关门
                        socketResponseVo.setTypes(40);
                        String recordCode = inventoryResult.getReplenishmentResult().getReplenishmentRecord().getScode();
                        if (StringUtil.isNotBlank(recordCode)) {
                            socketResponseVo.setData(recordCode);//补货单号
                        }
                        asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);

                        // 给设备推送补货单
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.GENERATE_REPLENISHMENT_ORDERS, userId, TypeConstant.CLOSE_DOOR_REPLENISH_ORDER);
                        logger.debug("扫码开门 -> 关门盘货 -> 给设备推送补货单，设备ID：{}", deviceId);

                    } else if (inventoryResult.getItype().intValue() == 50) { // 游客
                        if (openDoorType == 10) {//购物开门
                            socketResponseVo.setTypes(30);
                            closeType = 30;
                            //给设备推送关门结果
                            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.NO_CHANGE_IN_PRODUCT_QUANTITY, userId, TypeConstant.CLOSE_DOOR_ORDER);
                            logger.debug("扫码开门 -> 关门盘货 -> 游客类型，给设备推送消息，设备ID：{}", deviceId);
                        } else if (openDoorType == 20) {//补货员开门
                            socketResponseVo.setTypes(40);
                            closeType = 40;
                            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.NO_CHANGE_IN_PRODUCT_QUANTITY, userId, TypeConstant.CLOSE_DOOR_REPLENISH_ORDER_NULL);
                            logger.debug("扫码开门 -> 关门盘货 -> 补货员类型，给设备推送消息，设备ID：{}", deviceId);
                        }
                        asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);


                    }
                    //更新关门日志
                    LogUtils.updateOPLog(deviceCode, userId, closeType);
                    logger.info("设备关门盘货处理完成");


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

                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后调用设备盘点服务失败", userId, TypeConstant.CLOSE_DOOR + "_" + TypeConstant.INVENTORY);
                    logger.error("扫码开门 -> 关门盘货 -> 向设备发送调用关门盘货服务失败，设备ID：{}", deviceId);
                }
            } catch (Exception e) {
                logger.error("关门成功后调用设备盘点服务出现异常：{}", e);
                final int errorCode = ReturnCodeEnum.ERROR_CLOSEDOOR_INVENTORY_EXCEPTION.getCode();
                SocketResponseVo socketResponseVo = new SocketResponseVo(false, errorCode, "关门盘货出现异常");
                Integer errorType = setErrorTypeByUserOpenDoorType(types);
                socketResponseVo.setTypes(errorType);
                asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后调用设备盘点服务出现异常", userId, TypeConstant.CLOSE_DOOR + "_" + TypeConstant.INVENTORY);
                logger.error("扫码开门 -> 关门盘货 -> 调用设备盘点服务出现异常，向设备推送消息完成，设备ID：{}", deviceId);
            }
            // 跟新门的状态为关门状态
            CommonUtils.asynChangeDoorStatusToClose(deviceId, iCached, logger);

            // 给小屏推送关门消息
            String aiId = getAiIdByDeviceId(deviceId, "扫码关门消息");
            if (StringUtils.isNotBlank(aiId)) {
                logger.debug("大屏设备带有小屏设备，扫码关门给小屏推送关门消息，大屏ID：{}，小屏ID：{}", deviceId, aiId);
                FaceMsgToJsonUtils.SendFaceCommonNoCodeMsgByCtx(aiId, faceMap.get(aiId), true, CommonConstants.NO_CHANGE_IN_PRODUCT_QUANTITY, userId, FaceType.CLOSE_DOOR);
                logger.debug("扫码关门给小屏推送关门消息完成");
            }

            try {
                String openDoorUserId = (String) iCached.hget(NettyConst.OPENDOOR_USER_ID, deviceId);
                if (StringUtils.isNotBlank(openDoorUserId)) { // 从redis中获取该设备开门人Id
                    if (!openDoorUserId.equals(userId)) {
                        LogUtils.tcpMalfunctionLog(deviceId, "设备开门人ID：" + openDoorUserId + "，与设备关门人ID：" + userId + ",不一致", AndroidErrorCode.ERROR_UNKNOWN_CODE.getCode(), TypeConstant.EXCEPTION);
                    }
                }
            } catch (Exception e) {
                logger.error("从redis中查询 ClientVo 信息出现异常：{}", e);
                return;
            }

            /* 人脸识别关门计算*/
        } else if (null != openSource && 10 == openSource) {
            logger.debug("刷脸开门 -> 关门盘货，设备ID：{}", deviceId);
            FaceDeviceVo faceDeviceVo = null;
            String aiId = "";
            try {
                faceDeviceVo = (FaceDeviceVo) iCached.get(NettyConst.FACE_DEVICE_VO + deviceId);
                if (null == faceDeviceVo) {
                    logger.error("从redis中获取AI设备信息失败，处理刷脸开门,关门盘货消息失败，设备ID:{}", deviceId);
                    return;
                }
            } catch (Exception e) {
                logger.error("从redis中获取AI设备相关信息出现异常：{}", e);
                return;
            }

            // 将商品对象由字符串转化为对象
            try {
                if (StringUtil.isNotBlank(goodsString) && !goodsString.equals("[]")) {
                    goods = JSON.parseObject(goodsString, Goods.class);                 //商品集合
                }
            } catch (Exception e) {
                logger.error("商品集合json格式转化出现异常:{}", e);
                return;
            }

            aiId = faceDeviceVo.getAiId();                  // AI设备ID
            deviceCode = faceDeviceVo.getDeviceCode();                               //设备编号
            sourceClientType = faceDeviceVo.getOpenDoorPayType();                //来源客户端类型
            clientIp = faceDeviceVo.getIp();
            openDoorType = 10;                          //开门类型


            // 创建Rest服务客户端,调用关门盘货服务
            RestServiceInvoker invoke = null;
            try {
                logger.debug("刷脸开门 -> 关门盘货，开始调用关门盘货服务，设备ID：{}", deviceId);
                invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.INVENTORY_DEALWITH_SERVICE);

                InventoryDto inventoryDto = new InventoryDto();
                inventoryDto.setDeviceId(deviceId);                     // 设备ID
                inventoryDto.setCloseType(openDoorType);                // 关门类型
                inventoryDto.setInventoryType(10);                      // 盘点类型 10 关门盘点 20 定时盘点 30 主动盘点
                inventoryDto.setMemberId(userId);                       // 用户ID
                inventoryDto.setIsourceClientType(sourceClientType);          // 来源类型
                inventoryDto.setSip(clientIp);
                List<CommodityVo> commodityVoList = new ArrayList<>();      // 临时商品对象
                List<TagModel> goodsList = goods.getGoodsList();
                if (CollectionUtils.isNotEmpty(goodsList)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                    commodityVoList = assemblyCommodityVo(commodityVoList, goodsList);//组装商品信息
                }
                if (CollectionUtils.isEmpty(commodityVoList)) {
                    logger.info("Android设备传输过来的商品集合对象为空");
                }

                inventoryDto.setCommodityVos(commodityVoList);
                invoke.setRequestObj(inventoryDto);
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<InventoryResult>>() {
                });
                ResponseVo<InventoryResult> responseVo = (ResponseVo<InventoryResult>) invoke.invoke();
                logger.debug("刷脸开门 -> 关门盘货，调用关门盘货服务完成，调用结果：{}，设备ID：{}", responseVo.isSuccess(), deviceId);
                // 处理调用服务成功的结果
                if (responseVo.isSuccess()) {
                    InventoryResult inventoryResult = responseVo.getData();
                    //发送消息给终端
                    //发送消息给手机客户端
                    if (inventoryResult.getItype().intValue() == 10 || inventoryResult.getItype().intValue() == 20) { //购物关门
                        List<CreatOrderResult> orderRecords = inventoryResult.getOrderRecords();

                        // 给设备推送订单
                        asynSendShoppingOrderByCtxMap(deviceId, userId, responseVo.getData().getItype(), orderRecords, ctxMap, 10);

                        if (responseVo.getData().getItype().intValue() == 10) {//创建代扣支付订单成功
                            createPayOrder(deviceId, userId, responseVo.getData().getMerchantCode(), responseVo.getData().getOrderRecords(), ctxMap);
                        }


                    } else if (inventoryResult.getItype().intValue() == 50) { // 游客
                        if (openDoorType == 10) {//购物开门
                            closeType = 30;
                            //给设备推送关门结果
                            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.NO_CHANGE_IN_PRODUCT_QUANTITY, userId, TypeConstant.CLOSE_DOOR);
                        }

                    }

                    // 给AI设备推送消息
                    FaceMsgToJsonUtils.SendFaceCommonNoCodeMsgByCtx(deviceId, faceMap.get(aiId), true, null, userId, FaceType.CLOSE_DOOR);

                    //更新关门日志
                    LogUtils.updateOPLog(deviceCode, userId, closeType);
                    logger.info("设备关门盘货处理完成");


                } else {
                    logger.error("刷脸开门 -> 关门盘货，调用关门盘货服务失败，设备ID：{}", deviceId);
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后调用关门盘货服务失败", userId, TypeConstant.CLOSE_DOOR + "_" + TypeConstant.INVENTORY);
                }
            } catch (Exception e) {
                logger.error("刷脸开门 -> 关门盘货，关门成功后调用设备盘点服务出现异常：{}", e);
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后调用设备盘点服务出现异常", userId, TypeConstant.CLOSE_DOOR + "_" + TypeConstant.INVENTORY);
                FaceMsgToJsonUtils.SendFaceCommonNoCodeMsgByCtx(deviceId, faceMap.get(aiId), false, "关门成功后调用设备盘点服务出现异常", userId, FaceType.CLOSE_DOOR);
            }
            // 跟新门的状态为关门状态
            CommonUtils.asynChangeDoorStatusToClose(deviceId, iCached, logger);

            try {
                String openDoorUserId = faceDeviceVo.getOpenDoorUserId();
                if (StringUtils.isNotBlank(openDoorUserId)) { // 从redis中获取该设备开门人Id
                    if (!openDoorUserId.equals(userId)) {
                        LogUtils.tcpMalfunctionLog(deviceId, "设备开门人ID：" + openDoorUserId + "，与设备关门人ID：" + userId + ",不一致", AndroidErrorCode.ERROR_UNKNOWN_CODE.getCode(), TypeConstant.EXCEPTION);
                    }
                }
            } catch (Exception e) {
                logger.error("刷脸开门 -> 关门盘货，从redis中查询 ClientVo 信息出现异常：{}", e);
                return;
            }

        } else {
            logger.error("刷脸开门 -> 关门盘货，未知开门来源类型：{}", openSource);
            return;
        }


    }

   /**
     * 创建支付订单
     * @param merchantCode 商户编号
     * @param orderRecords 订单集合
     */
    public void createPayOrder(final String deviceId, final String userId, final String merchantCode, final List<CreatOrderResult> orderRecords, final ConcurrentMap<String, ChannelHandlerContext> ctxMap) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
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
                        return;
                    }
                    MerchantInfo merchantInfo = merchantInfoService.selectByCode(smerchantCode);
                    if (null == merchantInfo) {
                        logger.error("收款商户异常，商户编号：{}", smerchantCode);
                        return;
                    }
                    RestServiceInvoker invoke = null;
                    ResponseVo<FreePaymentResult> resVO = null;
                    for (CreatOrderResult orderResult : orderRecords) {
                        orderRecord = orderResult.getOrderRecord();
                        if (!merchantCode.equals(orderRecord.getSmerchantCode())) {
                            logger.error("商户订单异常，商户编号：{}", merchantCode + "===========" + orderRecord.getSmemberCode());
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
                                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {});
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
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, "success", userId, TypeConstant.PAY_SUCCESS);
                } else {
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, "fail", userId, TypeConstant.PAY_FAIL);
                }
            }
        });
    }


    /**
     * 10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
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
     * 将调用订单服务返回的结果转成OrderModel
     *
     * @param orderRecords    服务返回订单对象
     * @param goodModelList   商品集合model
     * @param number          订单商品数量
     * @param actualPayMoney  实际支付金额
     * @param discountedMoney 优惠金额
     * @param amountPayMoney  商品总金额
     * @return
     */
    private OrderModel convertOrderRecordsToOrderModel(List<CreatOrderResult> orderRecords,
                                                       List<GoodModel> goodModelList, Integer number, BigDecimal actualPayMoney, BigDecimal discountedMoney, BigDecimal amountPayMoney) {
        OrderModel orderModel = new OrderModel();
        for (CreatOrderResult result : orderRecords) {
            OrderRecord orderRecord = result.getOrderRecord();
            List<OrderCommodity> orderCommodityList = result.getOrderCommodityList();
            actualPayMoney = BigDecimalUtils.add(actualPayMoney, orderRecord.getFactualPayAmount()); // 订单实付总额
            discountedMoney = BigDecimalUtils.add(discountedMoney, orderRecord.getFdiscountAmount());  // 订单优惠总额
            amountPayMoney = BigDecimalUtils.add(amountPayMoney, orderRecord.getFtotalAmount());     // 订单总额
            if (CollectionUtils.isNotEmpty(orderCommodityList)) {
                GoodModel goodModel = null;
                for (OrderCommodity commodity : orderCommodityList) {
                    goodModel = new GoodModel();
                    goodModel.setGoodName(commodity.getScommodityName());                   // 商品名称
                    goodModel.setGoodsNumber(commodity.getForderCount());                   // 商品数量
                    goodModel.setGoodPrice(commodity.getFcommodityPrice().toString());      // 商品单价格
                    number = number + commodity.getForderCount();                           //累加商品数量
                    goodModelList.add(goodModel);
                }
            }
        }
        orderModel.setGoodsList(goodModelList);
        orderModel.setNumber(number);
        orderModel.setAmountPayMoney(amountPayMoney.toString());
        orderModel.setActualPayMoney(actualPayMoney.toString());
        orderModel.setDiscountedMoney(discountedMoney.toString());
        return orderModel;
    }

    /**
     * 校验基础参数
     * 从redis中获取相关对象
     *
     * @param deviceId       设备ID
     * @param userId         用户ID
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
//        if (null == socketIOClient) {
//            logger.error("从通道中获取手机用户信息失败，处理关门成功消息失败，设备ID_用户ID：{}", id);
//            map.put("isNotNull", false);
//            return map;
//        }

        // 从redis中获取扫码用户相关信息
        try {
//            sessionVo = (SessionVo) iCached.get(IPUtils.getRemoteAddress(socketIOClient));
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
     * 调用免密支付服务
     * @param userId
     * @param userCode
     * @param userName
     * @param result
     * @throws Exception
     */
    private void invokePayService(String userId, String userCode, String userName, CreatOrderResult result) throws Exception {
        // 调用免密支付服务
        OrderRecord payOrderRecord = result.getOrderRecord();
        String catcheKey = RedisConst.MERCHANT_INFO_DETAILS + payOrderRecord.getSmerchantCode();
        MerchantInfo merchantInfo = null;
        try {
            String merchant = (String) iCached.hget(RedisConst.MERCHANT_INFO, catcheKey);
            if (StringUtils.isNotBlank(merchant)) {
                merchantInfo = JSON.parseObject(merchant, MerchantInfo.class);
            }
            if (merchantInfo == null || StringUtils.isEmpty(merchantInfo.getSname())) {
                return;
            }
        } catch (Exception e) {
            logger.error("查询商户信息出现异常");
            return;
        }
        FreePaymentDto freePaymentDto = new FreePaymentDto();
        freePaymentDto.setSmerchantCode(payOrderRecord.getSmerchantCode());
        freePaymentDto.setSmemberId(userId);
        freePaymentDto.setSmemberName(userName);
        freePaymentDto.setSmemberCode(userCode);
        freePaymentDto.setSorderId(payOrderRecord.getId());
        freePaymentDto.setSsubject(merchantInfo.getSname() + "-" + payOrderRecord.getId());
        freePaymentDto.setIpayWay(payOrderRecord.getIpayWay());
        freePaymentDto.setIisIgnoreStatus(0);
        // 创建Rest服务客户端,免密支付服务
        RestServiceInvoker payInvoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_PAYMENT);
        payInvoke.setRequestObj(freePaymentDto);
        payInvoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {
        });
        ResponseVo<FreePaymentResult> payResponseVo = (ResponseVo<FreePaymentResult>) payInvoke.invoke();
        if (!payResponseVo.isSuccess()) {
            logger.error("调用支付服务失败，订单编号为:{}" + payOrderRecord.getSorderCode());
        }
    }


    /**
     * netty处理消息handler
     *
     * @param baseResponseVo 设备发送过来的消息对象
     */
    public void handlerMessage(BaseResponseVo baseResponseVo) {
        logger.info("开始处理普通消息，数据：{}", baseResponseVo);
        String methodType = baseResponseVo.getMethodType();
        boolean success = baseResponseVo.isSuccess();
        Integer code = baseResponseVo.getCode();
        String deviceId = baseResponseVo.getDeviceId();


        if (StringUtils.isBlank(methodType)) {  //方法名为空直接返回
            logger.error("设备ID为：" + deviceId + "发送消息的方法名为空！");
            return;
        }

        // 消息
        if (BooleanUtils.isTrue(success)) {
            if (TypeConstant.OPEN_DOOR.equals(methodType) && Integer.valueOf(200).equals(code)) {    //开门成功
                logger.debug("设备:{}发送开门成功消息到netty服务器", deviceId);
                WebSocketConstant.OPEN_REQUEST_FLAG = true;//更改请求开门状态
                openDoorSuccess(baseResponseVo);
            } else if ((TypeConstant.CLOSE_DOOR +"_"+ TypeConstant.INVENTORY).equals(methodType) && Integer.valueOf(200).equals(code)) { // 关门成功
                logger.debug("设备:{}发送关门盘货成功消息到netty服务器", deviceId);
                closeDoorInventorySuccess(baseResponseVo);
            } else if (TypeConstant.OPEN_DOOR_INVENTORY.equals(methodType) && Integer.valueOf(200).equals(code)) {  //开门状态下盘货结果
                logger.debug("设备:{}发送开门状态实时盘货结果消息到netty服务器", deviceId);
                openDoorInventory(baseResponseVo);
            } else if (TypeConstant.IS_ALIVE.equals(methodType) && Integer.valueOf(200).equals(code)) { // 设备是否在线
                logger.debug("设备:{}发送在线消息到netty服务器", deviceId);
                asynUpdateRedisDeviceStatus(baseResponseVo);
            } else if (TypeConstant.CLOSE_DOOR.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备:{}发送关门成功消息到netty服务器", deviceId);
                closeDoorSuccess(baseResponseVo);
            } else if (TypeConstant.UPGRADE_SYSTEM.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备:{}发送Apk升级成功消息到netty服务器", deviceId);
                upgradeApkResult(baseResponseVo);
            } else if (TypeConstant.UPGRADE_VOICE.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备:{}发送语音升级成功消息到netty服务器", deviceId);
                upgradeVoiceResult(baseResponseVo);
            } else if (TypeConstant.CHECK_TCP.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备：{}，检测长连接是否成功", deviceId);
                checkTcp(baseResponseVo);
            } else if (TypeConstant.TIMING_INVENTOTY.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备：{}，手动或定时盘货成功成功", deviceId);
                timingInventory(baseResponseVo);
            } else if (TypeConstant.INIT_VISION_SYSTEM.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备：{}，售货机自检后如果设备为故障变为正常", deviceId);
                selfTestToNormal(baseResponseVo);
            } else if (TypeConstant.SUNING_SHED.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备：{}，售货机接收到苏宁的棚格图信息", deviceId);
                suningShedInfo(baseResponseVo);
            } else if (TypeConstant.SUNING_OPENDOOR.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备：{}，收货机接收到苏宁的开门信息", deviceId);
                suningOpenDoor(baseResponseVo);
            } else if (TypeConstant.SUNING_CLOSEDOOR.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备：{}，收货机接收到苏宁的关门信息", deviceId);
                suningCloseDoor(baseResponseVo);
            } else if (TypeConstant.SUNING_INVENTORY.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备：{}，收货机接收到苏宁的盘货信息", deviceId);
                suningInventory(baseResponseVo);
            } else if (TypeConstant.SUNING_REPLENISHMENT.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备：{}，收货机接收到苏宁的上货信息", deviceId);
                suningReplenishment(baseResponseVo);
            } else if (TypeConstant.TB_INTERFACE_TRANSFER_LOG.equals(methodType) && Integer.valueOf(200).equals(code)) {
                String deviceCode = getDeviceCodeByDeviceId(deviceId);
                logger.debug("设备编号：{}，接受到第三方接口调用记录", deviceCode);
                insertThirdInterfaceTransferLog(deviceId, deviceCode, baseResponseVo);
            } else if (TypeConstant.LOCAL_GRAVITY_VISION_CLOSE_DOOR_INVENTORY.equals(methodType) && Integer.valueOf(200).equals(code)) {
                String deviceCode = getDeviceCodeByDeviceId(deviceId);
                logger.debug("设备编号：{}，发送本地视觉识别关门盘货消息", deviceCode);
                localGravityVisionCloseDoorInventorySuccess(deviceId, deviceCode, baseResponseVo);
            } else if (TypeConstant.LOCAL_GRAVITY_VISION_BEFORE_OPEN_DOOR_INVENTORY.equals(methodType) && Integer.valueOf(200).equals(code)) {
                String deviceCode = getDeviceCodeByDeviceId(deviceId);
                logger.debug("设备编号：{}，发送本地视觉识别开门前盘货消息", deviceCode);
                localGravityVisionBeforeOpenDoorInventorySuccess(deviceId, deviceCode, baseResponseVo);
            } else if (TypeConstant.LOCAL_GRAVITY_VISION_OPEN_DOOR_INVENTORY.equals(methodType) && Integer.valueOf(200).equals(code)) {
                String deviceCode = getDeviceCodeByDeviceId(deviceId);
                logger.debug("设备编号：{}，发送本地视觉识别开门实时盘货消息", deviceCode);
                localGravityVisionOpenDoorInventorySuccess(deviceId, deviceCode, baseResponseVo);
            } else if (TypeConstant.LOCAL_ACTIVE_INVENTORY.equals(methodType) && Integer.valueOf(200).equals(code)) {
                String deviceCode = getDeviceCodeByDeviceId(deviceId);
                logger.debug("设备编号：{}，发送mgr后台主动盘货结果", deviceCode);
                handlerActiveInventory(deviceId, deviceCode, baseResponseVo);
            } else if (TypeConstant.CLOUD_REALTIME_JUMP.equals(methodType) && Integer.valueOf(200).equals(code)) {
                String deviceCode = getDeviceCodeByDeviceId(deviceId);
                logger.debug("设备编号：{}，云端识别实时盘货跳转页面结果", deviceCode);
                cloudRealtimeJump(deviceId, deviceCode, baseResponseVo);
            }
            return;
        } else {
            if (TypeConstant.QR_CODE.equals(methodType)) {   // 设备请求发送二维码
                logger.error("二维码重新发送:{}", code);
                resendQrCode(baseResponseVo);
            } else if (TypeConstant.UPGRADE_SYSTEM.equals(methodType)) {
                logger.debug("设备:{}发送Apk升级失败消息到netty服务器", deviceId);
                upgradeApkResult(baseResponseVo);
            } else if (TypeConstant.UPGRADE_VOICE.equals(methodType)) {
                logger.debug("设备:{}发送语音升级失败消息到netty服务器", deviceId);
                upgradeVoiceResult(baseResponseVo);
            }
            return;
        }

    }

    /**
     * 云端识别实时盘货跳转页面结果
     *
     * @param deviceId
     * @param deviceCode
     * @param baseResponseVo
     */
    private void cloudRealtimeJump(String deviceId, String deviceCode, BaseResponseVo baseResponseVo) {
        logger.info("准备向手机端发送云端识别实时盘货跳转页面结果消息,用户ID：{},设备编号：{}", baseResponseVo.getUserId(), deviceCode);
        String userId = baseResponseVo.getUserId();
        SocketResponseVo socketResponseVo = SocketResponseVo.getSuccessResponse();
        String id = deviceId + "_" + userId;
        SocketIOClient socketIOClient = socketIoMap.get(id);
        Integer types = null;

        //手机端与服务器通信通道断开
        if (null == socketIOClient) {
            logger.error("手机端与服务器通信通道连接断开，手机端用户ID:{}", userId);
            return;
        }

        //记录开门人信息到redis
        SessionVo sessionVo = null;
        try {
            sessionVo = (SessionVo) iCached.get("soketIo_user_session_" + userId);
        } catch (Exception e) {
            logger.error("从redis中查询 SessionVo 信息出现异常：{}", e);
            return;
        }
        if (null == sessionVo) {
            logger.error("连接信息不在redis中，处理关门成功消息失败，用户ID:{}", userId);
            return;
        }

        types = sessionVo.getTypes();   // 开门类型
        if (null != types && types == 20) {
            socketResponseVo.setTypes(80);
            socketResponseVo.setMsg("补货员实时订单开启");
        } else {
            socketResponseVo.setTypes(70);
            socketResponseVo.setMsg("会员购物实时订单开启");
        }
        socketResponseVo.setData(deviceId+"_"+userId);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            logger.error("休眠异常，异常信息:{}", e);
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            logger.debug("云端识别实时盘货跳转页面结果完成，用户ID:{}", userId);
            return;
        }
        asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
        logger.debug("云端识别实时盘货跳转页面结果完成，用户ID:{}", userId);
        return;
    }

    /**
     * 重力视觉柜子开门实时订单计算
     *
     * @param deviceId       设备ID
     * @param deviceCode     设备编号
     * @param baseResponseVo 消息体
     */
    private void localGravityVisionOpenDoorInventorySuccess(String deviceId, String deviceCode, BaseResponseVo baseResponseVo) {
        logger.debug("开始处理售货机设备发送过来的开门实时盘货结果");
        String dataString = baseResponseVo.getData();                           // 商品盘货结果
        LocalGVGoods localGVGoods = new LocalGVGoods();
        OrderModel orderModel = new OrderModel();                               // 根据盘货的结果计算订单对象
        ReplenishOrderModel replenishOrderModel = new ReplenishOrderModel();    // 补货实时订单对象
        String userId = baseResponseVo.getUserId();

        if (StringUtils.isBlank(userId)) {
            logger.error("重力视觉柜子开门实时盘货消息体中会员ID不能为空");
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.MEMBER_ID_ISNULL.getCode(), ctxMap, "重力视觉柜子开门实时盘货消息体中会员ID不能为空", null, TypeConstant.LOCAL_GRAVITY_VISION_OPEN_DOOR_INVENTORY);
            return;
        }

        // json转换商品对象
        try {
            if (StringUtils.isNotBlank(dataString)) {
                localGVGoods = JSON.parseObject(dataString, LocalGVGoods.class);
            }
        } catch (Exception e) {
            logger.error("处理售货机设备发送过来的开门实时盘货结果，json转换出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.JSON_CONVERT_EXCEPTION.getCode(), ctxMap, "实时盘货结果中商品数据json转换异常", userId, TypeConstant.LOCAL_GRAVITY_VISION_OPEN_DOOR_INVENTORY);
            return;
        }

        // 获取开门类型 10 购物开门 20 补货员开门
        String openDoorType = localGVGoods.getOpenDoorType();
        String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);

        // 创建Rest服务客户端,调用计算实时订单服务
        RestServiceInvoker invoke = null;

        try {
            if (NettyConstant.SHOPPING.equals(openDoorType)) {   // 购物开门
                invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.CREATE_REAL_TIME_COMMOFITY_DIFF_SERVICE);
                RealTimeCommodityDiffOrderDto realTimeCommodityDiffOrderDto = new RealTimeCommodityDiffOrderDto();
                realTimeCommodityDiffOrderDto.setDeviceId(deviceId);
                realTimeCommodityDiffOrderDto.setMemberId(userId);                       // 会员ID--购物
                realTimeCommodityDiffOrderDto.setIsourceClientType(40);                  // 10=传统 20=RFID射频 30=视觉 40=重力视觉
                List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList = new ArrayList<>();
                List<LocalGVGoodModel> localGVGoodModelList = localGVGoods.getLocalGVGoodModelList();
                if (CollectionUtils.isNotEmpty(localGVGoodModelList)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                    InventoryCommodityDiffVo inventoryCommodityDiffVo = null;
                    for (LocalGVGoodModel local : localGVGoodModelList) {
                        inventoryCommodityDiffVo = new InventoryCommodityDiffVo();
                        inventoryCommodityDiffVo.setSvrCode(local.getSvrCode());
                        inventoryCommodityDiffVo.setIncrement(new Integer(local.getIncrement()));
                        inventoryCommodityDiffVo.setDecrement(new Integer(local.getDecrement()));
                        inventoryCommodityDiffVoList.add(inventoryCommodityDiffVo);
                    }
                }
                realTimeCommodityDiffOrderDto.setInventoryCommodityDiffVoList(inventoryCommodityDiffVoList);
                invoke.setRequestObj(realTimeCommodityDiffOrderDto);
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<RealTimeOrderResult>>() {});
                ResponseVo<RealTimeOrderResult> responseVo = (ResponseVo<RealTimeOrderResult>) invoke.invoke();
                if (responseVo.isSuccess()) {
                    logger.debug("调用购物开门实时盘货服务成功");
                    RealTimeOrderResult realTimeOrderResult = responseVo.getData();
                    if (null != realTimeOrderResult) {
                        logger.debug("商品数量对比库存有变化，调用购物开门实时盘货服务有订单生成");
                        List<GoodModel> goodModelList = new ArrayList<>();
                        GoodModel goodModel = null;
                        Integer amountNumber = new Integer("0");    // 初始值
                        for (RealTimeCommodityResult result : realTimeOrderResult.getResults()) {
                            goodModel = new GoodModel();
                            goodModel.setGoodsNumber(result.getNumber());                           // 商品数量
                            goodModel.setGoodName(result.getScommodityFullName());                      // 商品名称
                            goodModel.setGoodPrice(result.getFcommodityPrice().toString());
                            goodModel.setGoodImgUrl(preUrl + result.getScommodityImg()); // 商品图片地址
                            // 商品销售单价
                            amountNumber = amountNumber + result.getNumber();                       // 累加订单商品数量
                            goodModelList.add(goodModel);
                        }
                        orderModel.setAmountPayMoney(realTimeOrderResult.getFtotalAmount().toString());     // 订单总金额
                        orderModel.setActualPayMoney(realTimeOrderResult.getFactualPayAmount().toString()); // 订单实际支付金额
                        orderModel.setDiscountedMoney(realTimeOrderResult.getFdiscountAmount().toString()); // 订单优惠金额
                        orderModel.setNumber(amountNumber);                                                 // 订单商品总数量
                        orderModel.setGoodsList(goodModelList);                                             // 订单商品集合

                        // 发送消息
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, orderModel, userId, TypeConstant.LOCAL_GRAVITY_VISION_OPEN_DOOR_INVENTORY);
                    } else {
                        // 发送消息
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, null, userId, TypeConstant.LOCAL_GRAVITY_VISION_OPEN_DOOR_INVENTORY);
                        logger.debug("商品数量没有变化，没有购物开门实时盘货订单生成");
                    }
                } else {
                    logger.error("创建购物开门实时盘货生成实时订单失败，错误码：{}，错误信息：{}", responseVo.getErrorCode(), responseVo.getMsg());
                    MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_FAILED.getCode(), ctxMap, "创建开门实时盘货生成实时订单失败", userId, TypeConstant.LOCAL_GRAVITY_VISION_OPEN_DOOR_INVENTORY);
                }
            } else if (NettyConstant.REPLENISHMENT.equals(openDoorType)) {    // 补货开门,计算补货实时订单
                invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.REPLENISHMENT_REAL_TIME_COMMOFITY_DIFF_SERVICE);
                RealTimeCommodityDiffOrderDto realTimeCommodityDiffOrderDto = new RealTimeCommodityDiffOrderDto();
                realTimeCommodityDiffOrderDto.setDeviceId(deviceId);
                realTimeCommodityDiffOrderDto.setMemberId(userId);
                realTimeCommodityDiffOrderDto.setIsourceClientType(30);
                List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList = new ArrayList<>(); // 临时商品对象
                List<LocalGVGoodModel> localGVGoodModelList = localGVGoods.getLocalGVGoodModelList();
                if (CollectionUtils.isNotEmpty(localGVGoodModelList)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                    InventoryCommodityDiffVo inventoryCommodityDiffVo = null;
                    for (LocalGVGoodModel local : localGVGoodModelList) {
                        inventoryCommodityDiffVo = new InventoryCommodityDiffVo();
                        inventoryCommodityDiffVo.setSvrCode(local.getSvrCode());
                        inventoryCommodityDiffVo.setIncrement(new Integer(local.getIncrement()));
                        inventoryCommodityDiffVo.setDecrement(new Integer(local.getDecrement()));
                        inventoryCommodityDiffVoList.add(inventoryCommodityDiffVo);
                    }
                }
                realTimeCommodityDiffOrderDto.setInventoryCommodityDiffVoList(inventoryCommodityDiffVoList);
                invoke.setRequestObj(realTimeCommodityDiffOrderDto);
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<ReplenishRealTimeOrderResult>>() {
                });
                ResponseVo<ReplenishRealTimeOrderResult> responseVo = (ResponseVo<ReplenishRealTimeOrderResult>) invoke.invoke();
                if (responseVo.isSuccess()) {
                    logger.debug("调用补货开门实时盘货服务成功");
                    ReplenishRealTimeOrderResult replenishRealTimeOrderResult = responseVo.getData();
                    List<ReplenishRealTimeCommodityResult> dropOffCommoditys = replenishRealTimeOrderResult.getDropOffCommoditys();
                    List<ReplenishRealTimeCommodityResult> shelfCommoditys = replenishRealTimeOrderResult.getShelfCommoditys();
                    List<GoodModel> dropOffGoods = new ArrayList<>();
                    List<GoodModel> shelfGoods = new ArrayList<>();
                    Integer addNumTemp = new Integer(0);
                    Integer subNumTemp = new Integer(0);
                    /* 组装返回参数 */
                    GoodModel goodModel = null;
                    if (CollectionUtils.isNotEmpty(dropOffCommoditys)) {
                        for (ReplenishRealTimeCommodityResult repCom : dropOffCommoditys) {
                            goodModel = new GoodModel();
                            goodModel.setGoodName(repCom.getScommodityFullName());                      // 商品名称
                            goodModel.setGoodsNumber(repCom.getNumber());
                            goodModel.setGoodImgUrl(preUrl + repCom.getScommodityUrl());
                            subNumTemp = subNumTemp + repCom.getNumber();   // 累加下架商品数量
                            dropOffGoods.add(goodModel);
                        }
                    }

                    // 上架商品图片地址赋值
                    if (CollectionUtils.isNotEmpty(shelfCommoditys)) {
                        for (ReplenishRealTimeCommodityResult repCom : shelfCommoditys) {
                            goodModel = new GoodModel();
                            goodModel.setGoodName(repCom.getScommodityFullName());                      // 商品名称
                            goodModel.setGoodsNumber(repCom.getNumber());
                            goodModel.setGoodImgUrl(preUrl + repCom.getScommodityUrl());
                            addNumTemp = addNumTemp + repCom.getNumber();    // 累加下架商品数量
                            shelfGoods.add(goodModel);
                        }
                    }
                    replenishOrderModel.setAddGoodsList(shelfGoods);
                    replenishOrderModel.setSubGoodsList(dropOffGoods);
                    replenishOrderModel.setAddNum(addNumTemp);
                    replenishOrderModel.setSubNum(subNumTemp);

                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, replenishOrderModel, userId, TypeConstant.LOCAL_GRAVITY_VISION_REPLENISH_OPEN_DOOR_INVENTORY);
                } else {
                    logger.error("调用补货实时订单服务失败");
                    MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_EXCEPTION.getCode(), ctxMap, "调用补货实时订单服务失败", userId, TypeConstant.LOCAL_GRAVITY_VISION_REPLENISH_OPEN_DOOR_INVENTORY);
                }

            } else {
                logger.error("创建开门盘货生成实时订单出现错误，开门类型不能为空");
                MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_EXCEPTION.getCode(), ctxMap, "创建开门盘货生成实时订单出现错误，开门类型不能为空", userId, TypeConstant.LOCAL_GRAVITY_VISION_REPLENISH_OPEN_DOOR_INVENTORY);
            }

        } catch (Exception e) {
            logger.error("创建开门盘货生成实时订单出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_EXCEPTION.getCode(), ctxMap, "创建开门盘货生成实时订单出现异常", userId, TypeConstant.LOCAL_GRAVITY_VISION_OPEN_DOOR_INVENTORY);
        }

    }

    /**
     * 本地重力视觉柜子开门前盘货
     * 对比上次关门盘货时库存是否一致，如果不一致，生成警告日志
     *
     * @param deviceId       设备ID
     * @param deviceCode     设备编号
     * @param baseResponseVo 消息体
     */
    private void localGravityVisionBeforeOpenDoorInventorySuccess(String deviceId, String deviceCode, BaseResponseVo baseResponseVo) {
        List<LocalGVGoodModel> localGVGoodModelList = null;
        LocalGVGoods localGVGoods = null;                                           // 设备商品集合mode
        String goodsString = baseResponseVo.getData();
        // 将商品对象由字符串转化为对象
        try {
            if (StringUtil.isNotBlank(goodsString) && !goodsString.equals("[]")) {
                localGVGoods = JSON.parseObject(goodsString, LocalGVGoods.class);                 //商品集合
            }
        } catch (Exception e) {
            logger.error("商品集合json格式转化出现异常:{}", e);
            return;
        }
        if (null != localGVGoods) {
            localGVGoodModelList = localGVGoods.getLocalGVGoodModelList();
        }

        // 对比上次关门时的库存
        comapreLastCloseDoorStock(localGVGoodModelList, deviceCode, deviceId);
    }

    /**
     * 对比上次关门时库存数量
     * 如果两次对比有差异生成警告日志
     *
     * @param localGVGoodModelList
     * @param deviceCode
     * @param deviceId
     */
    private void comapreLastCloseDoorStock(List<LocalGVGoodModel> localGVGoodModelList, String deviceCode, String deviceId) {
        // 创建Rest服务客户端,调用关门盘货服务
        RestServiceInvoker invoke = null;
        try {
            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.BEFORE_OPEN_DOOR_INVENTORY_SERVICE);
            BeforeOpenDoorInventoryDto beforeOpenDoorInventoryDto = new BeforeOpenDoorInventoryDto();
            beforeOpenDoorInventoryDto.setDeviceId(deviceId);
            List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList = new ArrayList<>();    // 临时商品对象
            if (CollectionUtils.isNotEmpty(localGVGoodModelList)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                inventoryCommodityDiffVoList = commodityBeanCopy(inventoryCommodityDiffVoList, localGVGoodModelList);   //  bean复制
                beforeOpenDoorInventoryDto.setInventoryCommodityDiffVoList(inventoryCommodityDiffVoList);
            }
            if (CollectionUtils.isEmpty(inventoryCommodityDiffVoList)) {
                logger.info("Android设备传输过来的商品集合对象为空");
            }
            invoke.setRequestObj(beforeOpenDoorInventoryDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<InventoryResult>>() {
            });
            ResponseVo<InventoryResult> responseVo = (ResponseVo<InventoryResult>) invoke.invoke();
            logger.debug("调用开门前盘货对比上次关门时库存服务完成，调用结果：{}，设备编号：{}", responseVo.isSuccess(), getDeviceCodeByDeviceId(deviceId));
            // 处理调用服务成功的结果
            if (responseVo.isSuccess()) {
                logger.debug("调用开门前盘货对比上次关门时库存服务成功,设备编号：{}", getDeviceCodeByDeviceId(deviceId));
            } else {
                logger.debug("调用开门前盘货对比上次关门时库存服务失败,设备编号：{}", getDeviceCodeByDeviceId(deviceId));
            }
        } catch (Exception e) {
            logger.debug("调用开门前盘货对比上次关门时库存服务失败,设备编号：{}，异常原因：{}", getDeviceCodeByDeviceId(deviceId), e);
        }
    }

    /**
     * 本地重力视觉识别关门盘货成功消息
     *
     * @param deviceId
     * @param deviceCode
     * @param baseResponseVo
     */
    private void localGravityVisionCloseDoorInventorySuccess(String deviceId, String deviceCode, BaseResponseVo baseResponseVo) {
        logger.info("开始处理本地重力视觉识别关门盘货成功消息消息,设备编号：{}，用户ID：{}", deviceCode, baseResponseVo.getUserId());
        String userId = baseResponseVo.getUserId();
        String goodsString = baseResponseVo.getData();
        LocalGVGoods localGVGoods = null;                                           // 设备商品集合mode
        List<LocalGVGoodModel> localGVGoodModelList = new ArrayList<>();
        List<LayerGravityModel> layerGravityModelList = new ArrayList<>();
        Integer closeType = null;                                                   // 关门类型 10购物 20补货 30游客
        SessionVo sessionVo = null;                                                 // 用户信息
        ClientVo clientVo = null;                                                   // 设备信息
        SocketIOClient socketIOClient = null;                                       // 服务器与手机端通信通道
        Integer sourceClientType = null;    //来源客户端类型
        Integer openDoorType = null;        //开门类型 10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
        String clientIp = "";               // 开门IP地址
        Integer types = null;               //10 开门 20 补货开门

        // 校验基础参数，判断从redis中获取相关对象是否为空
        Map<String, Object> verifyMap = verifyVo(deviceId, userId);
        Boolean isNotNull = (Boolean) verifyMap.get("isNotNull");                   // 基础参数，以及相关对象--sessionVo,clientVo,socketIOClient ====>校验结果
        if (BooleanUtils.isFalse(isNotNull)) {
            logger.error("基础参数校验失败");
            return;
        }
        // 获取相应对象
        sessionVo = (SessionVo) verifyMap.get("sessionVo");
        clientVo = (ClientVo) verifyMap.get("clientVo");
        socketIOClient = (SocketIOClient) verifyMap.get("socketIOClient");
        types = sessionVo.getTypes();


        // 将商品对象由字符串转化为对象
        try {
            if (StringUtil.isNotBlank(goodsString) && !goodsString.equals("[]")) {
                localGVGoods = JSON.parseObject(goodsString, LocalGVGoods.class);                 //商品集合
            }
        } catch (Exception e) {
            logger.error("商品集合json格式转化出现异常:{}", e);
            SocketResponseVo socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "商品集合json格式转化出现异常");
            Integer errorType = setErrorTypeByUserOpenDoorType(types);
            socketResponseVo.setTypes(errorType);
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            return;
        }

        sourceClientType = sessionVo.getIsourceClientType();                //来源客户端类型
        clientIp = sessionVo.getSip();
        openDoorType = sessionVo.getTypes();                          //开门类型
        if (null == openDoorType) {
            logger.debug("开门类型为空");
        } else if (10 == openDoorType) {
            logger.debug("购物开门");
        } else if (20 == openDoorType) {
            logger.debug("补货开门");
        }


        // 计算正常订单
        // 创建Rest服务客户端,调用关门盘货服务
        RestServiceInvoker invoke = null;
        try {
            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.INVENTORY_COMMODITY_DIFF_SERVICE);
            InventoryDiffDto inventoryDiffDto = new InventoryDiffDto();
            inventoryDiffDto.setDeviceId(deviceId);                     // 设备ID
            inventoryDiffDto.setCloseType(openDoorType);                // 关门类型 10 购物 20 补货员 关门盘点必填
            inventoryDiffDto.setInventoryType(10);                      // 盘点类型 10 关门盘点
            inventoryDiffDto.setMemberId(userId);                       // 用户ID
            inventoryDiffDto.setIsourceClientType(sourceClientType);    // 来源类型
            inventoryDiffDto.setSip(clientIp);
            localGVGoodModelList = localGVGoods.getLocalGVGoodModelList();
            layerGravityModelList = localGVGoods.getLayerGravityModelList();
            List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList = new ArrayList<>();    // 临时商品对象
            List<LayerGravityVo> layerGravityVoList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(localGVGoodModelList)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                inventoryCommodityDiffVoList = commodityBeanCopy(inventoryCommodityDiffVoList, localGVGoodModelList);   //  bean复制
                inventoryDiffDto.setInventoryCommodityDiffVoList(inventoryCommodityDiffVoList);
            }
            if (CollectionUtils.isNotEmpty(layerGravityModelList)) {                // 商品集合非空，进行重力值对象转换
                layerGravityVoList = gravityBeanCopy(layerGravityVoList, layerGravityModelList);   //  bean复制
                inventoryDiffDto.setLayerGravityVoList(layerGravityVoList);
            }
            if (CollectionUtils.isEmpty(inventoryCommodityDiffVoList)) {
                logger.info("Android设备传输过来的商品集合对象为空");
            }
            invoke.setRequestObj(inventoryDiffDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<InventoryResult>>() {
            });
            ResponseVo<InventoryResult> responseVo = (ResponseVo<InventoryResult>) invoke.invoke();

            SocketResponseVo<String> socketResponseVo = SocketResponseVo.getSuccessResponse();
            logger.debug("扫码开门 -> 关门盘货差，调用关门盘货服务完成，调用结果：{}，设备编号：{}", responseVo.isSuccess(), getDeviceCodeByDeviceId(deviceId));
            // 处理调用服务成功的结果
            if (responseVo.isSuccess()) {
                logger.debug("扫码开门调用关门盘货服务成功,设备ID：{}", deviceId);
                InventoryResult inventoryResult = responseVo.getData();
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

                    // 给设备推送订单
                    asynSendShoppingOrderByCtxMap(deviceId, userId, responseVo.getData().getItype(), orderRecords, ctxMap, 20);
                    logger.debug("扫码开门 -> 关门盘货 -> 给大屏设备推送购物订单，设备ID：{}", deviceId);

                    if (responseVo.getData().getItype().intValue() == 10) {//创建代扣支付订单成功
                        createPayOrder(deviceId, userId, responseVo.getData().getMerchantCode(), responseVo.getData().getOrderRecords(), ctxMap);
                        logger.debug("扫码开门 -> 关门盘货 -> 创建支付代扣订单成功,设备ID：{}", deviceId);
                    }


                } else if (inventoryResult.getItype().intValue() == 30) { //补货关门
                    socketResponseVo.setTypes(40);
                    String recordCode = inventoryResult.getReplenishmentResult().getReplenishmentRecord().getScode();
                    if (StringUtil.isNotBlank(recordCode)) {
                        socketResponseVo.setData(recordCode);//补货单号
                    }
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);

                    // 给设备推送补货单
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.GENERATE_REPLENISHMENT_ORDERS, userId, TypeConstant.LOCAL_GRAVITY_VISION_CLOSE_DOOR_REPLENISH_ORDER);
                    logger.debug("扫码开门 -> 关门盘货 -> 给设备推送补货单，设备ID：{}", deviceId);

                } else if (inventoryResult.getItype().intValue() == 50) { // 游客
                    if (openDoorType == 10) {//购物开门
                        socketResponseVo.setTypes(30);
                        closeType = 30;
                        //给设备推送关门结果
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.NO_CHANGE_IN_PRODUCT_QUANTITY, userId, TypeConstant.LOCAL_GRAVITY_VISION_CLOSE_DOOR_ORDER);
                        logger.debug("扫码开门 -> 关门盘货 -> 游客类型，给设备推送消息，设备ID：{}", deviceId);
                    } else if (openDoorType == 20) {//补货员开门
                        socketResponseVo.setTypes(40);
                        closeType = 40;
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.NO_CHANGE_IN_PRODUCT_QUANTITY, userId, TypeConstant.LOCAL_GRAVITY_VISION_CLOSE_DOOR_REPLENISH_ORDER_NULL);
                        logger.debug("扫码开门 -> 关门盘货 -> 补货员类型，给设备推送消息，设备ID：{}", deviceId);
                    }
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);


                } else if (inventoryResult.getItype().intValue() == 60) {  // 商品差与重量差对比，产生购物异常订单
                    socketResponseVo.setTypes(50);//补货异常
                    socketResponseVo.setErrorCode(ReturnCodeEnum.ERROR_SHOPPING_EXCEPTION.getCode()); // 购物异常错误代码
                    socketResponseVo.setMsg(responseVo.getMsg());
                    socketResponseVo.setSuccess(false);
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, "关门成功后商品数量差与重量差对比超过阈值，生成购物异常订单", userId, TypeConstant.SHOPPING_EXCEPTION_ORDER);
                    logger.error("扫码开门,关门盘货,根据重力视觉差生成异常订单 -> 向设备发送重力差超阈值的购物异常订单，设备编号：{}", getDeviceCodeByDeviceId(deviceId));
                }

                //更新关门日志
                LogUtils.updateOPLog(deviceCode, userId, closeType);
                logger.info("设备关门盘货处理完成");


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

                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后调用设备盘点服务失败", userId, TypeConstant.LOCAL_GRAVITY_VISION_CLOSE_DOOR_INVENTORY);
                logger.error("扫码开门 -> 关门盘货 -> 向设备发送调用关门盘货服务失败，设备ID：{}", deviceId);
            }
        } catch (Exception e) {
            logger.error("关门成功后调用设备盘点服务出现异常：{}", e);
            final int errorCode = ReturnCodeEnum.ERROR_CLOSEDOOR_INVENTORY_EXCEPTION.getCode();
            SocketResponseVo socketResponseVo = new SocketResponseVo(false, errorCode, "关门盘货出现异常");
            Integer errorType = setErrorTypeByUserOpenDoorType(types);
            socketResponseVo.setTypes(errorType);
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后调用设备盘点服务出现异常", userId, TypeConstant.LOCAL_GRAVITY_VISION_CLOSE_DOOR_INVENTORY);
            logger.error("扫码开门 -> 关门盘货 -> 调用设备盘点服务出现异常，向设备推送消息完成，设备ID：{}", deviceId);
        }


    }



    /**
     * 记录第三方接口调用日志
     *
     * @param deviceId       设备ID
     * @param deviceCode     设备编号
     * @param baseResponseVo 售货机发送结果
     */
    private void insertThirdInterfaceTransferLog(String deviceId, String deviceCode, BaseResponseVo baseResponseVo) {
        logger.debug("开始处理记录第三方接口调用日志信息，设备：{}", deviceId);
        String data = baseResponseVo.getData();
        logger.debug("模型json字符串：{}", data);
        TbInterfaceTransferModel tbInterfaceTransferModel = null;
        // 将字符串转化为对象
        try {
            if (StringUtil.isNotBlank(data) && !data.equals("[]")) {
                tbInterfaceTransferModel = JSON.parseObject(data, TbInterfaceTransferModel.class);
                logger.debug("记录第三方接口调用日志对象json转化完成，设备ID：{}", deviceId);
            }
        } catch (Exception e) {
            logger.error("记录第三方接口调用日志信息json格式转化出现异常:{}", e);
            return;
        }
        if (null != tbInterfaceTransferModel) {
            String userId = baseResponseVo.getUserId();
            Integer userType = tbInterfaceTransferModel.getUserType();           // 用户类型 10 普通用户 20 管理员用户
            String thirdCode = tbInterfaceTransferModel.getThirdCode();           // 第三方编号
            String thirdName = tbInterfaceTransferModel.getThirdName();           // 第三方名称
            Integer interfaceType = tbInterfaceTransferModel.getInterfaceType();      // 接口类型 10 请求接口 20 返回接口
            String interfaceAction = tbInterfaceTransferModel.getInterfaceAction();     // 接口动作
            String interfaceName = tbInterfaceTransferModel.getInterfaceName();       // 接口名称
            String reqParams = tbInterfaceTransferModel.getReqParams();           // 请求参数
            String reqTime = tbInterfaceTransferModel.getReqTime();             // 请求时间
            String respParams = tbInterfaceTransferModel.getRespParams();          // 相应参数
            String respTime = tbInterfaceTransferModel.getRespTime();            // 相应时间
            Date reqDate = null;
            Date respDate = null;
            if (StringUtils.isNotBlank(reqTime)) {
                reqDate = DateUtils.convertToDateTime(reqTime);
            }
            if (StringUtils.isNotBlank(respTime)) {
                respDate = DateUtils.convertToDateTime(respTime);
            }
            Date addTime = DateUtils.getCurrentDateTime();
            // 记录调用日志
            interfaceTransferLogService.insertLog(deviceId, deviceCode, userId, userType, thirdCode, thirdName, interfaceType, interfaceAction, interfaceName, reqParams, reqDate, respParams, respDate, "", addTime);
        }
    }


    /**
     * 语音升级结果
     *
     * @param baseResponseVo
     */
    private void upgradeVoiceResult(BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.debug("开始处理记录语音升级结果，设备ID:{}", baseResponseVo.getDeviceId());
                String deviceId = baseResponseVo.getDeviceId();
                String dataString = baseResponseVo.getData();
                UpgradeResultModel upgradeResultModel = null;

                try {
                    if (StringUtils.isNotBlank(dataString)) {
                        upgradeResultModel = JSON.parseObject(dataString, UpgradeResultModel.class);
                    }
                } catch (Exception e) {
                    logger.error("处理售货机设备:{}发送过来的语音升级结果，json转换出现异常:{}", deviceId, e);
                    return;
                }

                if (null != upgradeResultModel) {
                    String updateId = upgradeResultModel.getUpgradeId();        // 升级明细表ID
                    String startTime = "";
                    String endTime = "";
                    String success = "";
                    String version = "";
                    String reason = "";
                    if (StringUtils.isNotEmpty(upgradeResultModel.getStartTime())) {
                        startTime = upgradeResultModel.getStartTime();       // 升级开始时间
                    }
                    if (StringUtils.isNotEmpty(upgradeResultModel.getEndTime())) {
                        endTime = upgradeResultModel.getEndTime();           // 升级结束时间
                    }
                    if (StringUtils.isNotEmpty(upgradeResultModel.getSuccess())) {
                        success = upgradeResultModel.getSuccess();           // 升级成功/失败
                    }
                    if (StringUtils.isNotEmpty(upgradeResultModel.getVersion())) {
                        version = upgradeResultModel.getVersion();           // 升级版本号
                    }
                    if (StringUtils.isNotEmpty(upgradeResultModel.getReason())) {
                        reason = upgradeResultModel.getReason();             // 升级失败原因
                    }
                    logger.debug("开始向数据库中添加记录语音升级结果，明细表ID:{}，开始升级时间：{}，升级结束时间：{}，结果：{}，设备ID：{}", updateId, startTime, endTime, success.equals("1") ? "成功" : "失败", deviceId);
                    if (StringUtils.isNotBlank(updateId) && StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)
                            && StringUtils.isNotBlank(success)) {
                        DeviceUpgradeDetails deviceUpgradeDetails = new DeviceUpgradeDetails();
                        if ("1".equals(success)) {
                            deviceUpgradeDetails.setIstatus(20);                                                // 10待处理 20升级成功 30升级失败
                            logger.debug("语音升级结果:成功，设备ID：{}", upgradeResultModel.getUpgradeId());
                        } else if ("0".equals(success)) {
                            deviceUpgradeDetails.setIstatus(30);
                            deviceUpgradeDetails.setSexceptionDesc(reason);                                     //  升级失败原因
                            logger.debug("语音升级结果：失败，原因:{}，设备ID：{}", reason, upgradeResultModel.getUpgradeId());
                        }
                        deviceUpgradeDetails.setTstartTime(DateUtils.convertToDateTime(startTime));             //  升级开始时间
                        deviceUpgradeDetails.setTendTime(DateUtils.convertToDateTime(endTime));                 //  升级结束时间
                        deviceUpgradeDetails.setId(updateId);                                                   //  升级明细表ID
                        deviceUpgradeDetailsService.updateBySelective(deviceUpgradeDetails);
                        logger.debug("记录语音升级结果完成，明细表ID:{}，设备ID：{}", upgradeResultModel.getUpgradeId());
                    }
                }
            }
        });


    }

    /**
     * 处理苏宁设备的上货信息
     *
     * @param baseResponseVo
     */
    private void suningReplenishment(BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                String userId = baseResponseVo.getUserId();
                Map<String, ThirdDeviceSku> deviceSkuPrice = new HashMap<>();
                Integer userType = null;    // 用户类型
                List<SuNingGoodChangeModel> goodChangeModelList = null; // 变化商品集合
                logger.debug("开始记录苏宁设备的上货信息，设备ID:{}", deviceId);
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                if (null == deviceInfo) {
                    logger.error("设备不存在，设备ID：{}", deviceId);
                    return;
                }
                String deviceCode = deviceInfo.getScode();
                logger.debug("开始处理苏宁接口传输的上货信息，设备：{}", deviceId);
                String data = baseResponseVo.getData();
                logger.debug("模型json字符串：{}", data);
                SuNingGoods suNingGoods = null;
                // 将字符串转化为对象
                try {
                    if (StringUtil.isNotBlank(data) && !data.equals("[]")) {
                        suNingGoods = JSON.parseObject(data, SuNingGoods.class);
                        logger.debug("苏宁上货对象json转化完成，设备ID：{}", deviceId);
                    }
                } catch (Exception e) {
                    logger.error("苏宁上货信息json格式转化出现异常:{}", e);
                    return;
                }

                if (null != suNingGoods) {
                    userType = suNingGoods.getUserType();
                    goodChangeModelList = suNingGoods.getSuNingGoodChangeModelList();
                } else {
                    logger.debug("不存在商品模型，设备ID：{}", deviceId);
                }

                String tbOrderCode = CoreUtils.newCode(EntityTables.TB_OPERATE_DEVICE_RECORD);//订单编号
                if (StringUtils.isBlank(tbOrderCode)) {
                    logger.error("苏宁上货订单编号生成出错");
                    return;
                }

            }

        });

    }

    /**
     * 处理苏宁设备的盘货信息
     *
     * @param baseResponseVo
     */
    private void suningInventory(BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                String userId = baseResponseVo.getUserId();
                Map<String, ThirdDeviceSku> deviceSkuPrice = new HashMap<>();
                Integer userType = null;    // 用户类型
                List<SuNingGoodChangeModel> goodChangeModelList = null; // 变化商品集合
                logger.debug("开始记录苏宁设备的盘货信息，设备ID:{}", deviceId);
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                if (null == deviceInfo) {
                    logger.error("设备不存在，设备ID：{}", deviceId);
                    return;
                }
                String deviceCode = deviceInfo.getScode();
                logger.debug("开始处理苏宁接口传输的盘货信息，设备：{}", deviceId);
                String data = baseResponseVo.getData();
                logger.debug("模型json字符串：{}", data);
                SuNingGoods suNingGoods = null;
                // 将字符串转化为对象
                try {
                    if (StringUtil.isNotBlank(data) && !data.equals("[]")) {
                        suNingGoods = JSON.parseObject(data, SuNingGoods.class);
                        logger.debug("苏宁盘货对象json转化完成，设备ID：{}", deviceId);
                    }
                } catch (Exception e) {
                    logger.error("苏宁盘货信息json格式转化出现异常:{}", e);
                    return;
                }

                if (null != suNingGoods) {
                    userType = suNingGoods.getUserType();
                    goodChangeModelList = suNingGoods.getSuNingGoodChangeModelList();
                } else {
                    logger.debug("不存在商品模型，设备ID：{}", deviceId);
                }

                String tbOrderCode = CoreUtils.newCode(EntityTables.TB_OPERATE_DEVICE_RECORD);//订单编号
                if (StringUtils.isBlank(tbOrderCode)) {
                    logger.error("苏宁盘货订单编号生成出错");
                    return;
                }

            }

        });
    }

    /**
     * 处理苏宁设备的关门信息
     *
     * @param baseResponseVo
     */
    private void suningCloseDoor(BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                String userId = baseResponseVo.getUserId();
                Map<String, ThirdDeviceSku> deviceSkuPrice = new HashMap<>();
                if (StringUtils.isBlank(userId)) {
                    try {
                        userId = (String) iCached.get(deviceId + NettyConst.THIRD_USER_ID);
                    } catch (Exception e) {
                        logger.error("从Redis中获取开门用户ID失败");
                    }
                    logger.debug("从Redis中获取开门的用户ID：{}，设备ID：{}", userId, deviceId);
                }
                Integer userType = null;
                List<SuNingGoodChangeModel> goodChangeModelList = null;
                logger.debug("开始记录苏宁设备的关门信息，设备ID:{}", deviceId);
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                if (null == deviceInfo) {
                    logger.error("设备不存在，设备ID：{}", deviceId);
                    return;
                }
                String deviceCode = deviceInfo.getScode();
                logger.debug("开始处理苏宁接口传输的关门信息，设备：{}", deviceId);
                String data = baseResponseVo.getData();
                logger.debug("模型json字符串：{}", data);
                SuNingGoods suNingGoods = null;
                // 将字符串转化为对象
                try {
                    if (StringUtil.isNotBlank(data) && !data.equals("[]")) {
                        suNingGoods = JSON.parseObject(data, SuNingGoods.class);
                        logger.debug("苏宁关门对象json转化完成，设备ID：{}", deviceId);
                    }
                } catch (Exception e) {
                    logger.error("苏宁关门信息json格式转化出现异常:{}", e);
                    return;
                }
                if (null != suNingGoods) {
                    userType = suNingGoods.getUserType();
                    goodChangeModelList = suNingGoods.getSuNingGoodChangeModelList();
                }

                String tbOrderCode = CoreUtils.newCode(EntityTables.TB_OPERATE_DEVICE_RECORD);//订单编号
                if (StringUtils.isBlank(tbOrderCode)) {
                    logger.error("苏宁关门订单编号生成出错");
                    return;
                }
                Date timeStamp = DateUtils.getCurrentDateTime();
                try {
                    // 计算订单中商品增加减少总额以及总数
                    BigDecimal orderAddTotalAmount = new BigDecimal("0.00");        // 订单增加总额
                    BigDecimal orderSubTotalAmount = new BigDecimal("0.00");        // 订单减少总额
                    Integer orderAddTotalNum = new Integer(0);                    // 订单增加总数
                    Integer orderSubTotalNum= new Integer(0);                     // 订单减少总数
                    if (CollectionUtils.isNotEmpty(goodChangeModelList)) {
                        for (SuNingGoodChangeModel s : goodChangeModelList) {
                            String addNum = s.getIncrement();   // 商品增加数量
                            String subNum = s.getDecrement();   // 商品减少数量
                            String vrCode = s.getVisualId();      // 本地视觉库视觉识别编号
                            String thirdSkuCode = s.getSkuCode();   // 第三方视觉商品编号
                            Integer addNumBd = new Integer(0);
                            Integer subNumBd = new Integer(0);
                            BigDecimal commodityAddTempAmount = new BigDecimal("0.00");        // 某类商品增加总额
                            BigDecimal commoditySubTempAmount = new BigDecimal("0.00");        // 某类商品减少总额
                            try {
                                if (StringUtils.isNotBlank(addNum)) {
                                    addNumBd = new Integer(addNum);
                                }
                                if (StringUtils.isNotBlank(subNum)) {
                                    subNumBd = new Integer(subNum);
                                }
                            } catch (Exception e) {
                                logger.error("处理苏宁设备的关门信息转换出错");
                                continue;
                            }
                            ThirdDeviceSku thirdDeviceSkuVo = new ThirdDeviceSku();
                            thirdDeviceSkuVo.setSvrCode(vrCode);
//                            thirdDeviceSkuVo.setIisLowerShelf(0);
                            thirdDeviceSkuVo.setSdeviceId(deviceId);
                            thirdDeviceSkuVo.setSdeviceCode(deviceCode);
                            thirdDeviceSkuVo.setSthirdPartSkuCode(thirdSkuCode);
                            List<ThirdDeviceSku> thirdDeviceSkuList = thirdDeviceSkuService.selectByEntityWhere(thirdDeviceSkuVo);
                            if (CollectionUtils.isEmpty(thirdDeviceSkuList)) {   // 没查到对应视觉商品信息
                                logger.error("从第三方SKU库中没有查到对应视觉商品信息，本地视觉商品识别编号ID：{}，第三方视觉商品编号：{}", vrCode, thirdSkuCode);
                                continue;
                            }
                            ThirdDeviceSku thirdDeviceSku = thirdDeviceSkuList.get(0);
                            deviceSkuPrice.put(thirdDeviceSku.getSvrCode(), thirdDeviceSku);
                            commodityAddTempAmount = BigDecimalUtils.multiply(thirdDeviceSku.getSprice(), addNumBd);
                            commoditySubTempAmount = BigDecimalUtils.multiply(thirdDeviceSku.getSprice(), subNumBd);
                            orderAddTotalAmount = BigDecimalUtils.add(orderAddTotalAmount, commodityAddTempAmount);     // 累加订单增加总额
                            orderSubTotalAmount = BigDecimalUtils.add(orderSubTotalAmount, commoditySubTempAmount);     // 累加订单减少总额
                            orderAddTotalNum += addNumBd;       // 累加增加数量
                            orderSubTotalNum += subNumBd;       // 累加减少数量
                        }
                        // 插入关门操作记录
                        logger.debug("开始插入关门操作记录，设备ID：{}", deviceId);
                        OperateDeviceRecord operateDeviceRecord = new OperateDeviceRecord();
                        operateDeviceRecord.setSuserId(userId);         // 用户ID
                        if (userType == 10) {
                            operateDeviceRecord.setIorderType(10);
                        } else if (userType == 20) {
                            operateDeviceRecord.setIorderType(20);
                        }
                        operateDeviceRecord.setIuserType(userType);     // 10 普通用户，20 管理员
                        operateDeviceRecord.setSdeviceId(deviceId);
                        operateDeviceRecord.setSdeviceCode(deviceCode);
                        operateDeviceRecord.setTorderTime(timeStamp);
                        operateDeviceRecord.setTaddTime(timeStamp);
                        operateDeviceRecord.setFaddTotalAmount(orderAddTotalAmount);
                        operateDeviceRecord.setFaddTotalNum(orderAddTotalNum);
                        operateDeviceRecord.setFsubTotalAmount(orderSubTotalAmount);
                        operateDeviceRecord.setFsubTotalNum(orderSubTotalNum);
                        operateDeviceRecord.setScode(tbOrderCode);
                        operateDeviceRecordService.insert(operateDeviceRecord);
                        String orderId = operateDeviceRecord.getId();
                        logger.debug("插入关门操作记录完成，设备ID：{}，记录表ID：{}", deviceId, operateDeviceRecord.getId());

                        // 插入操作记录明细
                        logger.debug("开始插入关门操作记录明细,设备ID：{}", deviceId);
                        StringBuffer stringBuffer = new StringBuffer();

                        if (CollectionUtils.isNotEmpty(goodChangeModelList)) {
                            for (SuNingGoodChangeModel s : goodChangeModelList) {
                                String vrCode = s.getVisualId();  // 本地视觉商品识别编号
                                if (StringUtils.isBlank(vrCode)) {
                                    logger.error("商品本地视觉识别编号为空");
                                    continue;
                                }
                                String addNum = s.getIncrement();   // 商品增加数量
                                String subNum = s.getDecrement();   // 商品减少数量
                                String totalAmountNum = s.getTotalAmount();   // 商品总数量
                                Integer addNumBd = new Integer(0);
                                Integer subNumBd = new Integer(0);
                                Integer totalAmountBd = new Integer(0);
                                BigDecimal commodityAddTempAmount = new BigDecimal("0.00");        // 某类商品增加总额
                                BigDecimal commoditySubTempAmount = new BigDecimal("0.00");        // 某类商品减少总额
                                try {
                                    if (StringUtils.isNotBlank(addNum)) {
                                        addNumBd = new Integer(addNum);
                                    }
                                    if (StringUtils.isNotBlank(subNum)) {
                                        subNumBd = new Integer(subNum);
                                    }
                                    if (StringUtils.isNotBlank(totalAmountNum)) {
                                        totalAmountBd = new Integer(totalAmountNum);
                                    }
                                } catch (Exception e) {
                                    logger.error("处理苏宁设备的关门信息转换出错");
                                    continue;
                                }
                                logger.debug("商品增加数量：", addNumBd);
                                logger.debug("商品减少数量：", subNumBd);
                                ThirdDeviceSku thirdDeviceSkuTemp = deviceSkuPrice.get(vrCode);
                                if (null != thirdDeviceSkuTemp) {
                                    commodityAddTempAmount = BigDecimalUtils.multiply(thirdDeviceSkuTemp.getSprice(), addNumBd);
                                    commoditySubTempAmount = BigDecimalUtils.multiply(thirdDeviceSkuTemp.getSprice(), subNumBd);
                                    OperateDeviceDetail operateDeviceDetail = new OperateDeviceDetail();
                                    operateDeviceDetail.setSoperateId(orderId);                                 // 订单主键
                                    operateDeviceDetail.setSoperateCode(tbOrderCode);                           // 订单编号
                                    operateDeviceDetail.setSthirdPartSkuCode(s.getSkuCode());                   // 第三方视觉商品sku编号
                                    operateDeviceDetail.setSvrName(thirdDeviceSkuTemp.getSname());              // 苏宁商品名称
                                    operateDeviceDetail.setSvrCode(vrCode);                                     // 本地商品视觉识别编号
                                    operateDeviceDetail.setSvrIncrement(addNumBd);                              // 增量
                                    operateDeviceDetail.setSvrDecrease(subNumBd);                               // 减量
                                    operateDeviceDetail.setSvrTotalAmount(totalAmountBd);                       // 总量
                                    operateDeviceDetail.setFaddTotalAmount(commodityAddTempAmount);             // 商品增加总额
                                    operateDeviceDetail.setFsubTotalAmount(commoditySubTempAmount);             // 商品减少总额
                                    operateDeviceDetail.setFprice(thirdDeviceSkuTemp.getSprice());              // 售价
                                    operateDeviceDetail.setTaddTime(timeStamp);
                                    operateDeviceDetailService.insert(operateDeviceDetail);
                                    stringBuffer.append("明细表ID：" + operateDeviceDetail.getId() + ",第三方sku编号：" + s.getSkuCode() + ",本地库ID:" + s.getVisualId() + ";");
                                } else {
                                    logger.error("视觉识别码：{}，商品未匹配到", vrCode);
                                }
                            }
                            logger.debug("插入关门操作记录明细完成");
                            String stringBuf = stringBuffer.toString();
                            if (StringUtils.isNotBlank(stringBuf)) {
                                logger.debug("插入数据为：{}", stringBuf);
                            } else {
                                logger.debug("没有商品数据");
                            }
                        }
                    }
                    // 设备关门操作记录

                    if (StringUtils.isNotBlank(userId)) {
                        logger.debug("开始查询该用户最后一次开门日志，设备ID:{},用户：{}", deviceId, userId);
                        Map<String, String> map = new HashMap<>();
                        map.put("sdeviceCode", deviceCode);
                        map.put("smemberId", userId);
                        String id = deviceOperService.selectLastOpLog(map);
                        if (StringUtils.isBlank(id)) {
                            logger.error("未查询到该用户的最近一次开门记录");
                            return;
                        }
                        logger.debug("开始插入设备关门日志，设备ID：{}，用户ID：{}", deviceId, userId);
                        DeviceOper deviceOper = new DeviceOper();
                        deviceOper.setId(id);
                        if (null != userType && 10 == userType) {           // 普通会员
                            if (CollectionUtils.isEmpty(goodChangeModelList)) {
                                deviceOper.setItype(30);                    // 10 购物，20 补货，30 购物访客，40 补货访客
                            } else {
                                deviceOper.setItype(10);
                            }
                        } else if (null != userType && 20 == userType) {    // 管理员
                            if (CollectionUtils.isEmpty(goodChangeModelList)) {
                                deviceOper.setItype(40);
                            } else {
                                deviceOper.setItype(20);
                            }
                        }
                        deviceOper.setTcloseTime(timeStamp);
                        deviceOperService.updateBySelective(deviceOper);
                        logger.debug("修改用户设备关门日志完成，设备ID：{}，用户ID:{}", deviceId, userId);
                    } else {
                        logger.error("用户ID为空，插入设备开门日志失败");
                    }

                    return;
                } catch (Exception e) {
                    logger.error("插入关门操作数据出现异常，设备ID：{}，异常原因：{}", deviceId, e);
                    return;
                }
            }
        });

    }


    /**
     * 处理苏宁设备的开门信息
     *
     * @param baseResponseVo
     */
    private void suningOpenDoor(BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                String userId = baseResponseVo.getUserId();
                String ip = "";
                logger.debug("开始记录苏宁设备的开门信息，设备ID:{}", deviceId, userId);
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                if (null == deviceInfo ) {
                    logger.error("设备不存在，设备ID：{}", deviceId);
                    return;
                }
                String deviceCode = deviceInfo.getScode();
                logger.debug("开始处理苏宁接口传输的开门信息，设备：{}", deviceId);
                String data = baseResponseVo.getData();
                logger.debug("模型json字符串：{}", data);
                SuNingGoods suNingGoods = null;
                // 将字符串转化为棚格图对象
                try {
                    if (StringUtil.isNotBlank(data) && !data.equals("[]")) {
                        suNingGoods = JSON.parseObject(data, SuNingGoods.class); //苏宁开门信息
                        logger.debug("苏宁开门对象json转化完成，设备ID：{}", deviceId);
                    }
                } catch (Exception e) {
                    logger.error("苏宁开门信息json格式转化出现异常:{}", e);
                    return;
                }
                Integer userType = null;
                if (null != suNingGoods) {
                    userType = suNingGoods.getUserType();
                    if (StringUtils.isNotBlank(suNingGoods.getIp())) {
                        ip = suNingGoods.getIp();
                    }
                }
                try {
                    Date timeStamp = DateUtils.getCurrentDateTime();

                    // 设备开门操作记录
                    if (StringUtils.isNotBlank(userId)) {
                        logger.debug("开始插入设备开门日志，设备ID：{}，用户ID：{}", deviceId, userId);
                        DeviceOper deviceOper = new DeviceOper();
                        if (null != userType && 10 == userType) {
                            deviceOper.setSmemberName("苏宁普通用户");    // 会员名称（苏宁）
                        } else if (null != userType && 20 == userType) {
                            deviceOper.setSmemberName("苏宁管理员");     // 会员名称（苏宁）
                        } else {
                            deviceOper.setSmemberName("苏宁未知身份用户");
                        }
                        deviceOper.setSmemberCode("suning");    // 会员编号（苏宁）
                        deviceOper.setTaddTime(timeStamp);      // 添加时间
                        deviceOper.setTopenTime(timeStamp);     // 开门时间
                        deviceOper.setSmemberId(userId);        // 会员ID
                        deviceOper.setSdeviceCode(deviceCode);  // 设备编号
                        deviceOper.setSip(ip);                  // IP地址
                        deviceOper.setIclientType(30);          // 来源
                        deviceOperService.insert(deviceOper);

                        try {
                            iCached.put(deviceId + NettyConst.THIRD_USER_ID, userId, 60 * 60);
                        } catch (Exception e) {
                            logger.error("向redis中写入ClientVo出现异常：{}", e);
                        }
                    } else {
                        logger.error("用户ID为空，插入设备开门日志失败");
                    }


                    return;
                } catch (Exception e) {
                    logger.error("插入开门操作数据出现异常，设备ID：{}，异常原因：{}", deviceId, e);
                    return;
                }
            }
        });
    }

    /**
     * @param baseResponseVo 处理苏宁的棚格图信息
     */
    private void suningShedInfo(BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                if (null == deviceInfo ) {
                    logger.error("设备不存在，设备ID：{}", deviceId);
                    return;
                }
                String merchantId = deviceInfo.getSmerchantId();
                String merchantCode = deviceInfo.getSmerchantCode();
                String deviceCode = deviceInfo.getScode();
                logger.debug("开始处理苏宁接口传输的棚格图信息，设备：{}", deviceId);
                String data = baseResponseVo.getData();
                logger.debug("模型json字符串：{}", data);
                SuNingShedModel suNingShedModel = new SuNingShedModel();  // 苏宁棚格图信息
                // 将字符串转化为棚格图对象
                try {
                    if (StringUtil.isNotBlank(data) && !data.equals("[]")) {
                        suNingShedModel = JSON.parseObject(data, SuNingShedModel.class); //苏宁棚格图信息
                        logger.debug("苏宁棚格图对象json转化完成，设备ID：{}", deviceId);
                    }
                } catch (Exception e) {
                    logger.error("棚格图信息json格式转化出现异常:{}", e);
                    return;
                }
                // 查询以前第三方SKU信息
                ThirdDeviceSku thirdDeviceSkuVo = new ThirdDeviceSku();
                thirdDeviceSkuVo.setSdeviceId(deviceId);
                List<ThirdDeviceSku> oldThirdDeviceSkuList = thirdDeviceSkuService.selectByEntityWhere(thirdDeviceSkuVo);

                Map<String, ThirdDeviceSku> oldThirdDeviceSkuMap = new HashMap<>(); // 以前该设备的sku
                Map<String, SuNingShedGoodModel> tempThirdDeviceSkuMap = new HashMap<>();// 以前的与现在的视觉商品交集
                Map<String, SuNingShedGoodModel> newThirdDeviceSkuMap = new HashMap<>(); // 现在的视觉商品sku

                // 存放设备以前的sku
                if (CollectionUtils.isNotEmpty(oldThirdDeviceSkuList)) {
                    for (ThirdDeviceSku t : oldThirdDeviceSkuList) {
                        oldThirdDeviceSkuMap.put(t.getSvrCode(), t);
                    }
                }

                List<SuNingShedGoodModel> suNingShedModelList = suNingShedModel.getSuNingShedGoodModelList();
                if (CollectionUtils.isEmpty(suNingShedModelList)) {
                    logger.debug("从棚格中获取苏宁商品集合为空,设备ID：{}", deviceId);
                }
                // 存放设备新的sku
                if (CollectionUtils.isNotEmpty(suNingShedModelList)) {
                    for (SuNingShedGoodModel s : suNingShedModelList) {
                        if (StringUtils.isBlank(s.getVisualId())) {
                            logger.error("本地视觉库视觉识别编号为空，商品名称：{}，设备ID：{}", s.getName(), deviceId);
                            continue;
                        }
                        if (StringUtils.isBlank(s.getThirdSkuCode())) {
                            logger.error("第三方视觉编号为空，本地视觉库视觉ID：{}，设备ID：{}", s.getVisualId(), deviceId);
                            continue;
                        }
                        newThirdDeviceSkuMap.put(s.getVisualId(), s);
                    }
                }
                // 获取新旧sku交集
                if (MapUtils.isNotEmpty(newThirdDeviceSkuMap) && MapUtils.isNotEmpty(oldThirdDeviceSkuMap)) {
                    for (Map.Entry<String, SuNingShedGoodModel> entry : newThirdDeviceSkuMap.entrySet()) {
                        for (Map.Entry<String, ThirdDeviceSku> entry1 : oldThirdDeviceSkuMap.entrySet()) {
                            String svrCode = entry1.getKey();
                            SuNingShedGoodModel suNingShedGoodModel = entry.getValue();
                            if (entry.getKey().equals(svrCode)) {
                                suNingShedGoodModel.setId(entry1.getValue().getId());   // 第三方商户设备SKU库主键ID
                                tempThirdDeviceSkuMap.put(entry.getKey(), suNingShedGoodModel);
                            }
                        }
                    }
                }
                // 新sku与旧sku同时移除已经存在的sku
                if (MapUtils.isNotEmpty(tempThirdDeviceSkuMap)) {
                    for (Map.Entry<String, SuNingShedGoodModel> entry : tempThirdDeviceSkuMap.entrySet()) {
                        String key = entry.getKey();
                        newThirdDeviceSkuMap.remove(key);
                        oldThirdDeviceSkuMap.remove(key);
                    }
                }

                // 插入设备新的sku
                for (Map.Entry<String,SuNingShedGoodModel> map : newThirdDeviceSkuMap.entrySet()) {
                    SuNingShedGoodModel s = map.getValue();
                    // 插入新数据
                    ThirdDeviceSku thirdDeviceSku = new ThirdDeviceSku();
                    thirdDeviceSku.setTupdateTime(DateUtils.getCurrentDateTime());
                    thirdDeviceSku.setSdeviceId(deviceId);
                    thirdDeviceSku.setSdeviceCode(deviceCode);
                    thirdDeviceSku.setSmerchantId(merchantId);
                    thirdDeviceSku.setSmerchantCode(merchantCode);
                    thirdDeviceSku.setSvrCode(s.getVisualId());                     // 本地视觉库视觉识别编号
                    thirdDeviceSku.setSthirdPartSkuCode(s.getThirdSkuCode());       // 第三方视觉识别编号
                    thirdDeviceSku.setSname(s.getName());                           // 商品名称
                    thirdDeviceSku.setSprice(new BigDecimal(s.getPrice()));         // 价格
                    thirdDeviceSku.setIweight(new BigDecimal(s.getUnitWeight()));   // 重量
                    thirdDeviceSku.setIverson(1);       // 版本
                    thirdDeviceSku.setIisLowerShelf(0); // 上架
                    thirdDeviceSku.setTupdateTime(DateUtils.getCurrentDateTime());  // 修改时间
                    thirdDeviceSkuService.insert(thirdDeviceSku);
                }

                // 修改旧的视觉商品为下架
                for (Map.Entry<String, ThirdDeviceSku> map : oldThirdDeviceSkuMap.entrySet()) {
                    ThirdDeviceSku thirdDeviceSku = map.getValue();
                    ThirdDeviceSku tempThirdDeviceSku = new ThirdDeviceSku();
                    tempThirdDeviceSku.setId(thirdDeviceSku.getId());
                    tempThirdDeviceSku.setIisLowerShelf(1); // 下架
                    tempThirdDeviceSku.setTupdateTime(DateUtils.getCurrentDateTime());  // 修改时间
                    thirdDeviceSkuService.updateBySelective(tempThirdDeviceSku);
                }

                // 修改交集中视觉商品的各种属性
                for (Map.Entry<String, SuNingShedGoodModel> map : tempThirdDeviceSkuMap.entrySet()) {
                    SuNingShedGoodModel s = map.getValue();
                    ThirdDeviceSku tempThirdDeviceSku = new ThirdDeviceSku();
                    tempThirdDeviceSku.setSname(s.getName());                           // 商品名称
                    tempThirdDeviceSku.setSprice(new BigDecimal(s.getPrice()));         // 商品价格
                    tempThirdDeviceSku.setIweight(new BigDecimal(s.getUnitWeight()));   // 商品重量
                    tempThirdDeviceSku.setSthirdPartSkuCode(s.getThirdSkuCode());       // 修改编号
                    tempThirdDeviceSku.setId(s.getId());                                // 主键ID
                    tempThirdDeviceSku.setTupdateTime(DateUtils.getCurrentDateTime());  // 修改时间
                    tempThirdDeviceSku.setIisLowerShelf(0);
                    thirdDeviceSkuService.updateBySelective(tempThirdDeviceSku);
                }

                }
        });
    }

    /**
     * 售货机自检
     * 如果设备为故障则修改为正常
     *
     * @param baseResponseVo
     */
    private void selfTestToNormal(BaseResponseVo baseResponseVo) {
        String deviceId = baseResponseVo.getDeviceId();
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
        // 状态： 10=未注册 20=正常 30=故障 40=报废 50=离线
        if (null != deviceInfo && 30 == deviceInfo.getIstatus()) {
            DeviceInfo deviceInfoTemp = new DeviceInfo();
            deviceInfoTemp.setId(deviceId);
            deviceInfoTemp.setIstatus(20);
            deviceInfoTemp.setTupdateTime(DateUtils.getCurrentDateTime());
            deviceInfoService.updateBySelective(deviceInfoTemp);
            logger.debug("接收到设备自检成功消息，将设备变为正常状态，设备ID：{}", baseResponseVo.getDeviceId());
        }
    }

    /**
     * 定时或者手动盘货
     *
     * @param baseResponseVo
     */
    private void timingInventory(BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                String goodsString = baseResponseVo.getData();              // 商品盘货结果
                Goods goods = new Goods();                                  // 设备商品集合mode
                // 将商品对象由字符串转化为对象
                try {
                    if (StringUtil.isNotBlank(goodsString) && !goodsString.equals("[]")) {
                        goods = JSON.parseObject(goodsString, Goods.class);                 //商品集合
                    }
                } catch (Exception e) {
                    logger.error("商品集合json格式转化出现异常:{}", e);
                    return;
                }

                // 创建Rest服务客户端,调用关门盘货服务
                RestServiceInvoker invoke = null;
                try {
                    invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.INVENTORY_DEALWITH_SERVICE);

                    InventoryDto inventoryDto = new InventoryDto();
                    inventoryDto.setDeviceId(deviceId);                     // 设备ID
                    inventoryDto.setInventoryType(20);                      // 盘点类型 10 关门盘点 20 定时盘点 30 主动盘点
                    List<CommodityVo> commodityVoList = new ArrayList<>();      // 临时商品对象
                    List<TagModel> goodsList = goods.getGoodsList();
                    if (CollectionUtils.isNotEmpty(goodsList)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                        commodityVoList = assemblyCommodityVo(commodityVoList, goodsList);//组装商品信息
                    }
                    if (CollectionUtils.isEmpty(commodityVoList)) {
                        logger.info("Android设备传输过来的商品集合对象为空");
                    }

                    inventoryDto.setCommodityVos(commodityVoList);
                    invoke.setRequestObj(inventoryDto);
                    // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<InventoryResult>>() {
                    });
                    ResponseVo<InventoryResult> responseVo = (ResponseVo<InventoryResult>) invoke.invoke();
                    if (responseVo.isSuccess()) {
                        logger.debug("调用定时盘货成功");
                    } else {
                        logger.error("调用定时盘货失败，原因：{}", responseVo.getMsg());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 售货机设备发送长连接正常
     *
     * @param baseResponseVo
     */
    private void checkTcp(final BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, TypeConstant.CHECK_TCP, null, TypeConstant.CHECK_TCP);
            }
        });


    }

    /**
     * apk升级结果处理
     *
     * @param baseResponseVo
     */
    private void upgradeApkResult(final BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                String dataString = baseResponseVo.getData();
                UpgradeResultModel upgradeResultModel = null;

                try {
                    if (StringUtils.isNotBlank(dataString)) {
                        upgradeResultModel = JSON.parseObject(dataString, UpgradeResultModel.class);
                    }
                } catch (Exception e) {
                    logger.error("处理售货机设备:{}发送过来的apk升级成功结果，json转换出现异常:{}", deviceId, e);
                    return;
                }

                if (null != upgradeResultModel) {
                    String updateId = upgradeResultModel.getUpgradeId();        // 升级明细表ID
                    String startTime = "";
                    String endTime = "";
                    String success = "";
                    String version = "";
                    String reason = "";
                    if (StringUtils.isNotEmpty(upgradeResultModel.getStartTime())) {
                        startTime = upgradeResultModel.getStartTime();       // 升级开始时间
                    }
                    if (StringUtils.isNotEmpty(upgradeResultModel.getEndTime())) {
                        endTime = upgradeResultModel.getEndTime();           // 升级结束时间
                    }
                    if (StringUtils.isNotEmpty(upgradeResultModel.getSuccess())) {
                        success = upgradeResultModel.getSuccess();           // 升级成功/失败
                    }
                    if (StringUtils.isNotEmpty(upgradeResultModel.getVersion())) {
                        version = upgradeResultModel.getVersion();           // 升级版本号
                    }
                    if (StringUtils.isNotEmpty(upgradeResultModel.getReason())) {
                        reason = upgradeResultModel.getReason();             // 升级失败原因
                    }

                    if (StringUtils.isNotBlank(updateId) && StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)
                            && StringUtils.isNotBlank(success)) {
                        DeviceUpgradeDetails deviceUpgradeDetails = new DeviceUpgradeDetails();
                        if ("1".equals(success)) {
                            deviceUpgradeDetails.setIstatus(20);                                                // 10待处理 20升级成功 30升级失败
                        } else if ("0".equals(success)) {
                            deviceUpgradeDetails.setIstatus(30);
                            deviceUpgradeDetails.setSexceptionDesc(reason);                                     //  升级失败原因
                        }
                        deviceUpgradeDetails.setTstartTime(DateUtils.convertToDateTime(startTime));             //  升级开始时间
                        deviceUpgradeDetails.setTendTime(DateUtils.convertToDateTime(endTime));                 //  升级结束时间
                        deviceUpgradeDetails.setId(updateId);                                                   //  升级明细表ID
                        deviceUpgradeDetailsService.updateBySelective(deviceUpgradeDetails);
                    }
                }
            }
        });

    }

    /**
     * 关门成功
     * @param baseResponseVo
     */
    private void closeDoorSuccess(BaseResponseVo baseResponseVo) {
        // 处理关门消息
        CommonUtils.asynChangeDoorStatusToClose(baseResponseVo.getDeviceId(), iCached, logger);
    }

    /**
     * 异步处理设备发来的在线消息
     *
     * @param baseResponseVo
     */
    private void asynUpdateRedisDeviceStatus(final BaseResponseVo baseResponseVo) {
        logger.debug("开始处理设备发送过来的在线消息");
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                String uuid = baseResponseVo.getData();
                try {
                    Boolean result = (Boolean) iCached.get(deviceId + uuid);
                    if (result != null && BooleanUtils.isTrue(result)) {
                        iCached.remove(deviceId + uuid);
                        iCached.put(deviceId + uuid, false, 300);
                    }
                } catch (Exception e) {
                    logger.error("从redis中获取在线消息出现异常：{}", e);
                }
            }
        });
        logger.debug("处理设备发送过来的在线消息结束");
    }


    /**
     * 处理售货机设备发送过来的开门实时盘货结果
     * @param baseResponseVo
     * @throws Exception
     */
    private void openDoorInventory(BaseResponseVo baseResponseVo)  {
        logger.debug("开始处理售货机设备发送过来的开门实时盘货结果");
        String dataString = baseResponseVo.getData();              // 商品盘货结果
        Goods goods = new Goods();                                  // json转化后的临时商品集合对象
        OrderModel orderModel = new OrderModel();                   // 根据盘货的结果计算订单对象
        ReplenishOrderModel replenishOrderModel = new ReplenishOrderModel();    // 补货实时订单对象
        String deviceId = baseResponseVo.getDeviceId();             // 设备ID
        String userId = baseResponseVo.getUserId();

        if (StringUtils.isBlank(userId)) {
            logger.error("开门盘货消息体中会员ID不能为空");
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.MEMBER_ID_ISNULL.getCode(), ctxMap, "开门盘货消息体中会员ID不能为空", null, TypeConstant.OPEN_DOOR_INVENTORY);
            return;
        }

        // json转换商品对象
        try {
            if (StringUtils.isNotBlank(dataString)) {
                goods = JSON.parseObject(dataString, Goods.class);
            }
        } catch (Exception e) {
            logger.error("处理售货机设备发送过来的开门实时盘货结果，json转换出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.JSON_CONVERT_EXCEPTION.getCode(), ctxMap, "实时盘货结果中商品数据json转换异常", userId, TypeConstant.OPEN_DOOR_INVENTORY);
            return;
        }

        // 获取开门类型 10 购物开门 20 补货员开门
        String openDoorType = goods.getOpenDoorType();
        String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);
        //DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);

        // 根绝设备所属商户查询商户下所有商品信息
       /* CommodityInfo commodityInfoVo = new CommodityInfo();
        commodityInfoVo.setIstatus(10);         // 正常状态
        commodityInfoVo.setIdelFlag(0);
        commodityInfoVo.setIstoreDevice(10);    // 视觉商品
        commodityInfoVo.setSmerchantId(deviceInfo.getSmerchantId());
        List<CommodityInfo> commodityInfoList = commodityInfoService.selectByEntityWhere(commodityInfoVo);*/

        // 创建Rest服务客户端,调用计算实时订单服务
        RestServiceInvoker invoke = null;

        try {
            if (NettyConstant.SHOPPING.equals(openDoorType)) {   // 购物开门
                invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.CREATE_REAL_TIME_SERVICE);

                RealTimeOrderDto realTimeOrderDto = new RealTimeOrderDto();
                realTimeOrderDto.setDeviceId(deviceId);
                realTimeOrderDto.setMemberId(userId);                       // 会员ID--购物
                realTimeOrderDto.setIsourceClientType(30);                  // 10=传统 20=RFID射频 30=视觉
                List<CommodityVo> commodityVoList = new ArrayList<>();      // 临时商品对象
                List<TagModel> goodsList = goods.getGoodsList();
                if (CollectionUtils.isNotEmpty(goodsList)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                    commodityVoList = assemblyCommodityVo(commodityVoList, goodsList);
                }

                realTimeOrderDto.setCommodityVos(commodityVoList);
                invoke.setRequestObj(realTimeOrderDto);
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<RealTimeOrderResult>>() {});
                ResponseVo<RealTimeOrderResult> responseVo = (ResponseVo<RealTimeOrderResult>) invoke.invoke();
                if (responseVo.isSuccess()) {
                    logger.debug("调用购物开门实时盘货服务成功");


                    RealTimeOrderResult realTimeOrderResult = responseVo.getData();
                    if (null != realTimeOrderResult) {
                        logger.debug("商品数量对比库存有变化，调用购物开门实时盘货服务有订单生成");
                        List<GoodModel> goodModelList = new ArrayList<>();
                        GoodModel goodModel = null;
                        Integer amountNumber = new Integer("0");    // 初始值
                        for (RealTimeCommodityResult result : realTimeOrderResult.getResults()) {
                            goodModel = new GoodModel();
                            goodModel.setGoodsNumber(result.getNumber());                           // 商品数量
                            //goodModel.setGoodName(result.getScommodityName());                      // 商品名称
                            goodModel.setGoodName(result.getScommodityFullName());                      // 商品名称
                            goodModel.setGoodPrice(result.getFcommodityPrice().toString());
                            goodModel.setGoodImgUrl(preUrl + result.getScommodityImg()); // 商品图片地址
                            // 商品销售单价
                           /* if (CollectionUtils.isNotEmpty(commodityInfoList)) {                    // 遍历商户下所有商品
                                for (CommodityInfo com : commodityInfoList) {
                                    if (result.getScommodityId().equals(com.getId())) {
                                        goodModel.setGoodImgUrl(preUrl + com.getScommodityImg()); // 商品图片地址
                                    }
                                }
                            }*/
                            amountNumber = amountNumber + result.getNumber();                       // 累加订单商品数量
                            goodModelList.add(goodModel);
                        }
                        orderModel.setAmountPayMoney(realTimeOrderResult.getFtotalAmount().toString());     // 订单总金额
                        orderModel.setActualPayMoney(realTimeOrderResult.getFactualPayAmount().toString()); // 订单实际支付金额
                        orderModel.setDiscountedMoney(realTimeOrderResult.getFdiscountAmount().toString()); // 订单优惠金额
                        orderModel.setNumber(amountNumber);                                                 // 订单商品总数量
                        orderModel.setGoodsList(goodModelList);                                             // 订单商品集合

                        // 发送消息
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, orderModel, userId, TypeConstant.OPEN_DOOR_INVENTORY);
                    } else {
                        // 发送消息
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
                        logger.debug("商品数量没有变化，没有购物开门实时盘货订单生成");
                    }
                } else {
                    logger.error("创建购物开门实时盘货生成实时订单失败，错误码：{}，错误信息：{}", responseVo.getErrorCode(), responseVo.getMsg());
                    MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_FAILED.getCode(), ctxMap, "创建开门实时盘货生成实时订单失败", userId, TypeConstant.OPEN_DOOR_INVENTORY);
                }
            } else {
                if (NettyConstant.REPLENISHMENT.equals(openDoorType)) {    // 补货开门,计算补货实时订单
                    invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.REPLENISHMENT_REAL_TIME_SERVICE);
                    ReplenishRealTimeOrderDto replenishRealTimeOrderDto = new ReplenishRealTimeOrderDto();
                    replenishRealTimeOrderDto.setDeviceId(deviceId);
                    replenishRealTimeOrderDto.setMemberId(userId);
                    replenishRealTimeOrderDto.setIsourceClientType(30);

                    List<CommodityVo> commodityVoList = new ArrayList<>();      // 临时商品对象
                    List<TagModel> goodsList = goods.getGoodsList();
                    if (CollectionUtils.isNotEmpty(goodsList)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                        commodityVoList = assemblyCommodityVo(commodityVoList, goodsList);
                    }
                    replenishRealTimeOrderDto.setCommodityVos(commodityVoList);
                    invoke.setRequestObj(replenishRealTimeOrderDto);
                    // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<ReplenishRealTimeOrderResult>>() {
                    });
                    ResponseVo<ReplenishRealTimeOrderResult> responseVo = (ResponseVo<ReplenishRealTimeOrderResult>) invoke.invoke();
                    if (responseVo.isSuccess()) {
                        logger.debug("调用补货开门实时盘货服务成功");
                        ReplenishRealTimeOrderResult replenishRealTimeOrderResult = responseVo.getData();

                        List<ReplenishRealTimeCommodityResult> dropOffCommoditys = replenishRealTimeOrderResult.getDropOffCommoditys();
                        List<ReplenishRealTimeCommodityResult> shelfCommoditys = replenishRealTimeOrderResult.getShelfCommoditys();
//                    List<ReplenishRealTimeCommodityResult> results = replenishRealTimeOrderResult.getResults();


                        List<GoodModel> dropOffGoods = new ArrayList<>();
                        List<GoodModel> shelfGoods = new ArrayList<>();
                        Integer addNumTemp = new Integer(0);
                        Integer subNumTemp = new Integer(0);



                    /* 组装返回参数 */
                        GoodModel goodModel = null;

                        if (CollectionUtils.isNotEmpty(dropOffCommoditys)) {
                            for (ReplenishRealTimeCommodityResult repCom : dropOffCommoditys) {
                                goodModel = new GoodModel();
                                //goodModel.setGoodName(repCom.getScommodityName());
                                goodModel.setGoodName(repCom.getScommodityFullName());                      // 商品名称
                                goodModel.setGoodsNumber(repCom.getNumber());
                                goodModel.setGoodImgUrl(preUrl + repCom.getScommodityUrl());
                              /*  for (CommodityInfo com : commodityInfoList) {   // 下架商品图片地址赋值
                                    if (repCom.getScommodityId().equals(com.getId())) {
                                        goodModel.setGoodImgUrl(preUrl + com.getScommodityImg());
                                    }
                                }*/
                                subNumTemp = subNumTemp + repCom.getNumber();   // 累加下架商品数量
                                dropOffGoods.add(goodModel);
                            }
                        }

                        // 上架商品图片地址赋值
                        if (CollectionUtils.isNotEmpty(shelfCommoditys)) {
                            for (ReplenishRealTimeCommodityResult repCom : shelfCommoditys) {
                                goodModel = new GoodModel();
                                //goodModel.setGoodName(repCom.getScommodityName());
                                goodModel.setGoodName(repCom.getScommodityFullName());                      // 商品名称
                                goodModel.setGoodsNumber(repCom.getNumber());
                                goodModel.setGoodImgUrl(preUrl + repCom.getScommodityUrl());
                               /* for (CommodityInfo com : commodityInfoList) {
                                    if (repCom.getScommodityId().equals(com.getId())) {
                                        goodModel.setGoodImgUrl(preUrl + com.getScommodityImg());
                                    }
                                }*/
                                addNumTemp = addNumTemp + repCom.getNumber();    // 累加下架商品数量
                                shelfGoods.add(goodModel);
                            }
                        }
                        replenishOrderModel.setAddGoodsList(shelfGoods);
                        replenishOrderModel.setSubGoodsList(dropOffGoods);
                        replenishOrderModel.setAddNum(addNumTemp);
                        replenishOrderModel.setSubNum(subNumTemp);

                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, replenishOrderModel, userId, TypeConstant.REPLENISH_OPEN_DOOR_INVENTORY);
                    }

                } else {
                    logger.error("创建补货开门生成实时订单出现错误开门类型不能为空");
                    MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_EXCEPTION.getCode(), ctxMap, "创建开门实时盘货生成实时订单出现错误，开门类型不能为空", userId, TypeConstant.OPEN_DOOR_INVENTORY);
                }
            }
        } catch (Exception e) {
            logger.error("创建开门实时盘货生成实时订单出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_EXCEPTION.getCode(), ctxMap, "创建开门实时盘货生成实时订单出现异常", userId, TypeConstant.OPEN_DOOR_INVENTORY);
        }
    }

    /**
     * 组装android客户端视觉商品
     * @param commodityVoList 返回对象
     * @param goodsList 视觉商品集合
     * @return
     */
    private List<CommodityVo> assemblyCommodityVo(List<CommodityVo> commodityVoList, List<TagModel> goodsList) {
        CommodityVo commodityVo = null;
        String vrCode = "";
        Map<String, CommodityVo> map = new HashMap<String, CommodityVo>();
        for (TagModel tagModel : goodsList) {
            vrCode = tagModel.getSvrCode();
            commodityVo = map.get(vrCode);
            if (null == commodityVo) {
                commodityVo = new CommodityVo();
                if (StringUtils.isBlank(vrCode)) {
                    logger.error("视觉商品识别码为空");
                    continue;
                }
                commodityVo.setSvrCode(vrCode);
                commodityVo.setCommodityNum(0);
            }
            commodityVo.setCommodityNum(commodityVo.getCommodityNum() + tagModel.getNumber());  // 累加编号相同的商品数量（安卓传过来的数据不友好）
            map.put(vrCode, commodityVo);
        }
        if (map.size() > 0) {
            for (String vrCodes : map.keySet()) {
                commodityVoList.add(map.get(vrCodes));
            }
        }
        return commodityVoList;
    }

    /**
     * 重新发送二维码
     * @param baseResponseVo
     */
    private void resendQrCode(BaseResponseVo baseResponseVo) {
        logger.debug("准备向Android设备发送二位码");
        String deviceId = baseResponseVo.getDeviceId();
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
        if (null != deviceInfo) {
            String qrCodeUrl = deviceInfo.getSqrUrl();
            if (StringUtils.isNotBlank(qrCodeUrl)) {
                try {
                    String resultMsg = MsgToJsonUtils.setAndroidMsgNoCode(true, qrCodeUrl, deviceId, null, TypeConstant.QR_CODE);
                    MsgToJsonUtils.ctxSendMsg(deviceId, ctxMap, resultMsg);
                    logger.info("重新发送二维码");
                } catch (Exception e) {
                    logger.error("发送二维码出现异常");
                }
            }
        }
        return;
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
//        SessionVo sessionVo = null;
        try {
//            sessionVo = (SessionVo) iCached.get(socketIOClient.getRemoteAddress().toString());
//            if (null != sessionVo) {
//                logger.info("用户编号:{}", sessionVo.getUserCode());
//            }
            if (null != socketIOClient) {
                socketIOClient.sendEvent(socketEvent, JSON.toJSON(socketResponseVo));
            }
        } catch (Exception e) {
            logger.error("向手机客户端发送消息异常：{}", e);
        }
    }

//    private void asynSendMsgToSocketIo(final String socketEvent, final SocketResponseVo socketResponseVo, final SocketIOClient socketIOClient) {
//        ExecutorManager.getInstance().execute(new Runnable() {
//            @Override
//            public void run() {
//                logger.info("socketIO事件类型：{}", socketEvent);
//                logger.info("向手机客户端发送消息内容：{}", socketResponseVo);
//                SessionVo sessionVo = null;
//                try {
//                    sessionVo = (SessionVo) iCached.get(socketIOClient.getRemoteAddress().toString());
//                    if (null != sessionVo) {
//                        logger.info("用户编号:{}", sessionVo.getUserCode());
//                    }
//
//                    socketIOClient.sendEvent(socketEvent, JSON.toJSON(socketResponseVo));
//                } catch (Exception e) {
//                    logger.error("向手机客户端发送消息异常：{}", e);
//                }
//            }
//        });
//    }

    /**
     * 接收到设备发送过来的主动盘货结果
     * 存入Redis中
     *
     * @param deviceId       设备ID
     * @param deviceCode     设备编号
     * @param baseResponseVo
     */
    private void handlerActiveInventory(String deviceId, String deviceCode, BaseResponseVo baseResponseVo) {
        String goodsString = baseResponseVo.getData();
        // 将商品对象由字符串转化为对象
        try {
            if (StringUtil.isNotBlank(goodsString) && !goodsString.equals("[]")) {
                // 获取商品信息集合对象字符串，放入redis中
                logger.debug("设备编号：{},接受到主动盘货商品信息：{},存入Redis中", deviceCode, goodsString);
                iCached.hset(NettyConst.DEVICE_ACTIVE_INVENTORY_COMMODITY, deviceId, goodsString);
            } else {
                logger.info("商品集合对象为空");
                iCached.hset(NettyConst.DEVICE_ACTIVE_INVENTORY_COMMODITY, deviceId, NettyConst.DEVICE_STOCK_IS_ZERO);
            }
        } catch (Exception e) {
            logger.error("商品集合json格式转化出现异常:{}", e);
        }
    }

    /**
     * 向设备发送关门盘货购物订单
     *
     * @param deviceId
     * @param userId
     * @param itype
     * @param orderRecords
     * @param ctxMap
     * @param methodType 10=库存对比，20=商品差
     */
    private void asynSendShoppingOrderByCtxMap(String deviceId, String userId, Integer itype, List<CreatOrderResult> orderRecords, ConcurrentMap<String, ChannelHandlerContext> ctxMap, Integer methodType) {
        if (null != orderRecords && CollectionUtils.isNotEmpty(orderRecords)) {
            OrderRecord orderRecord = null;
            OrderModel orderModel = new OrderModel();

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
                        orderModel.setOrderNo(orderRecord.getSdismantlingCode());
                    } else {
                        orderModel.setOrderNo(orderRecord.getSorderCode());
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
//                        goodModel.setDiscountType();    // 优惠类型
                        goodsList.add(goodModel);
                        number = number + orderCommodity.getForderCount();
                    }
                }
                num++;
            }

            // 封装参数
            orderModel.setAmountPayMoney(amountPayMoneyTemp.toString());
            orderModel.setActualPayMoney(actualPayMoneyTemp.toString());
            orderModel.setDiscountedMoney(discountedMoneyTemp.toString());
            orderModel.setNumber(number);
            orderModel.setPayType(itype);
            orderModel.setGoodsList(goodsList);
            if (methodType == 10) {
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, orderModel, userId, TypeConstant.CLOSE_DOOR_ORDER);
            } else if (methodType == 20) {
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, orderModel, userId, TypeConstant.LOCAL_GRAVITY_VISION_CLOSE_DOOR_ORDER);
            }
            return;
        }
        if (methodType == 10) {
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, null, userId, TypeConstant.CLOSE_DOOR_ORDER);
        } else if (methodType == 20) {
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, null, userId, TypeConstant.LOCAL_GRAVITY_VISION_CLOSE_DOOR_ORDER);
        }
    }


    /**
     * 在redis中存储 DeviceInfoVo 设备基础信息
     *
     * @param deviceId
     * @param deviceInfo
     */
    public void assemblyDeviceInfoVo(String deviceId, DeviceInfo deviceInfo) {
        try {
            DeviceInfo deviceInfoTemp = (DeviceInfo) iCached.hget(NettyConst.DEVICE_INFO, deviceId);
            if (deviceInfoTemp != null) {
                iCached.hremove(NettyConst.DEVICE_INFO, deviceId);
                iCached.hset(NettyConst.DEVICE_INFO, deviceId, deviceInfo);
            } else {
                iCached.hset(NettyConst.DEVICE_INFO, deviceId, deviceInfo);    //将设备相关信息存入Redis中
            }
        } catch (Exception e) {
            logger.error("设备ID：{} 从redis中获取或者存入数据异常：{}", deviceId, e);
        }
    }

    /**
     * 发送消息给小屏设备
     *
     * @param deviceId 售货机设备ID
     * @param userId   用户ID
     * @param debugLog 发送消息类型
     */
    private void sendMsgToAiFace(String deviceId, String userId, String debugLog) {
        String aiId = getAiIdByDeviceId(deviceId, debugLog);
        if (StringUtils.isNotBlank(aiId)) {
            logger.debug("设备支持AI设备，设备ID：{}，AI设备ID：{}", deviceId, aiId);
            ChannelHandlerContext faceCtx = faceMap.get(aiId);
            if (null != faceCtx) {
                logger.debug("向小屏设备推送消息：{}", debugLog);
                FaceMsgToJsonUtils.SendFaceCommonNoCodeMsgByCtx(deviceId, faceCtx, true, "open door success", userId, FaceType.OPEN_DOOR);
            } else {
                logger.debug("售货机设备附带的AI设备不在线，不用向小屏设备推送消息，设备ID：{}，AI设备ID：{}", deviceId, aiId);
            }
        }
    }

    /**
     * 通过设备ID获取小屏ID
     *
     * @param deviceId
     * @param log
     * @return
     */
    private String getAiIdByDeviceId(String deviceId, String log) {
        FaceDeviceVo faceDeviceVo = null;
        String aiId = "";
        logger.debug("售货机设备ID：{}，消息内容：{}", deviceId, log);
        try {
            faceDeviceVo = (FaceDeviceVo) iCached.get(NettyConst.FACE_DEVICE_VO + deviceId);
            if (null == faceDeviceVo) {
                logger.error("从redis中获取AI设备信息失败，处理消息:{}失败，设备ID:{}", log, deviceId);
                return aiId;
            }
        } catch (Exception e) {
            logger.error("从redis中获取AI设备相关信息出现异常：{}", e);
            return aiId;
        }
        if (null != faceDeviceVo) {
            aiId = faceDeviceVo.getAiId();
        } else {
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
            if (deviceInfo == null) {
                logger.error("未查询到相关设备信息，设备ID：{}", deviceId);
                return aiId;
            }
            aiId = deviceInfo.getSaiId();
        }
        return aiId;
    }

    /**
     * 通过设备ID查询设备编号
     *
     * @param deviceId 设备ID
     * @return 设备编号
     */
    private String getDeviceCodeByDeviceId(String deviceId) {
        try {
            DeviceInfo deviceInfo = (DeviceInfo) iCached.hget(NettyConst.DEVICE_INFO, deviceId);
            if (deviceInfo != null) {
                return deviceInfo.getScode();
            }
        } catch (Exception e) {
            logger.error("设备ID：{} 从redis中获取或者存入数据异常：{}", deviceId, e);
        }
        return "";
    }

    /**
     * 将安卓设备传过来的商品转换成服务商品入参
     *
     * @param inventoryCommodityDiffVoList 服务入参
     * @param localGVGoodModelList         源商品参数
     * @return
     */
    private List<InventoryCommodityDiffVo> commodityBeanCopy(List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList, List<LocalGVGoodModel> localGVGoodModelList) {
        InventoryCommodityDiffVo commodityDiffVo = null;
        for (LocalGVGoodModel local : localGVGoodModelList) {
            commodityDiffVo = new InventoryCommodityDiffVo();
            commodityDiffVo.setSvrCode(local.getSvrCode());
            commodityDiffVo.setIncrement(new Integer(local.getIncrement()));
            commodityDiffVo.setDecrement(new Integer(local.getDecrement()));
            commodityDiffVo.setRemainsNum(new Integer(local.getRemainsNum()));
            inventoryCommodityDiffVoList.add(commodityDiffVo);
        }
        return inventoryCommodityDiffVoList;
    }

    /**
     * bean转换
     *
     * @param layerGravityVoList
     * @param layerGravityModelList
     * @return
     */
    private List<LayerGravityVo> gravityBeanCopy(List<LayerGravityVo> layerGravityVoList, List<LayerGravityModel> layerGravityModelList) {
        LayerGravityVo layerGravityVo = null;
        CargoRoadGravityVo cargoRoadGravityVo = null;
        for (LayerGravityModel layer : layerGravityModelList) {
            layerGravityVo = new LayerGravityVo();
            layerGravityVo.setLayerGravityDecrement(layer.getLayerGravityDecrement());
            layerGravityVo.setLayerGravityIncrement(layer.getLayerGravityIncrement());
            layerGravityVo.setLayerGravityRemain(layer.getLayerGravityRemain());
            layerGravityVo.setLayerNo(layer.getLayerNo());
            List<CargoRoadGravityVo> cargoRoadGravityVoList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(layer.getCargoRoadGravityModelList())) {
                for (CargoRoadGravityModel cargo : layer.getCargoRoadGravityModelList()) {
                    cargoRoadGravityVo = new CargoRoadGravityVo();
                    cargoRoadGravityVo.setCargoRoadNo(cargo.getCargoRoadNo());
                    cargoRoadGravityVo.setGravityDecrement(cargo.getGravityDecrement());
                    cargoRoadGravityVo.setGravityIncrement(cargo.getGravityIncrement());
                    cargoRoadGravityVo.setGravityRemain(cargo.getGravityRemain());
                    cargoRoadGravityVoList.add(cargoRoadGravityVo);
                }
                layerGravityVo.setCargoRoadGravityModelList(cargoRoadGravityVoList);
            }
            layerGravityVoList.add(layerGravityVo);
        }
        return layerGravityVoList;
    }

}
