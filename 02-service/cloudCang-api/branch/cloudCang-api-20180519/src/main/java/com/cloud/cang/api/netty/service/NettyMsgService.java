package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.utils.*;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.sp.vo.*;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.api.sp.service.CommodityInfoService;
import com.cloud.cang.api.sp.vo.CommodityVo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ErrorCode;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.common.TypeConstant;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.inventory.*;
//import com.cloud.cang.inventory.CommodityVo;
import com.cloud.cang.jy.CreatOrderResult;
import com.cloud.cang.model.GoodModel;
import com.cloud.cang.model.Goods;
import com.cloud.cang.model.OrderModel;
import com.cloud.cang.model.TagModel;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.pay.FreePaymentDto;
import com.cloud.cang.pay.FreePaymentResult;
import com.cloud.cang.pay.FreeServicesDefine;
import com.cloud.cang.pojo.BaseResponseVo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Alex on 2018/3/31.
 */

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

    private static final Logger logger = LoggerFactory.getLogger(NettyMsgService.class);
    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();  //netty通道
    private static ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();   //socketIo通道


    /**
     * 设备发送过来的开门成功消息
     * @param baseResponseVo 设备消息体
     * @return
     * @throws Exception
     */
    public void openDoorSuccess(BaseResponseVo baseResponseVo)  {
        logger.info("准备向支付宝页面发送开门成功消息");
        String deviceId = baseResponseVo.getDeviceId();
        String userId = baseResponseVo.getUserId();
        SocketResponseVo socketResponseVo = SocketResponseVo.getSuccessResponse();
        String id = deviceId + "_" + userId;
        SocketIOClient socketIOClient = socketIoMap.get(id);

        //手机端与服务器通信通道断开
        if (null == socketIOClient) {
            logger.error("手机端与服务器通信通道连接断开，手机端用户ID:{}", userId);
            return;
        }

        //记录开门人信息到redis
        SessionVo sessionVo = null;
        try {
            sessionVo = (SessionVo) iCached.get(socketIOClient.getRemoteAddress().toString());
        } catch (Exception e) {
            logger.error("从redis中查询 SessionVo 信息出现异常：{}", e);
            return;
        }
        if (null == sessionVo) {
            logger.error("支付宝连接信息不在redis中，处理关门成功消息失败，用户ID:{}", userId);
            return;
        }

        Integer types = sessionVo.getTypes();   // 开门类型
        socketResponseVo.setTypes(types);
        socketResponseVo.setData(deviceId);//返回开门设备ID

        //设备操作日志--记录开门成功
//        String ip = IPUtils.getIPString(socketIOClient);
//        LogUtils.addOPLog(sessionVo, ip);

        //发送消息给手机客户端
        asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);


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
//        clientVo.setDoor(20);   // 门状态 10关闭 20开着
        clientVo.setDeviceId(deviceId);
        clientVo.setDeviceCode(sessionVo.getDeviceCode());
        ChannelHandlerContext ctx = ctxMap.get(deviceId);
        if (null != ctx) {
            clientVo.setCtxId(ctx.channel().id().asLongText());
        }

        try {
            iCached.hset(NettyConst.SYN_CLIENT_MAP, deviceId, clientVo);
        } catch (Exception e) {
            logger.error("向redis中写入ClientVo出现异常：{}", e);
        }

        logger.info("设备开门处理完成，用户ID:{}", userId);
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
        logger.debug("开始处理关门盘货成功消息消息");
        String deviceId = baseResponseVo.getDeviceId();
        String userId = baseResponseVo.getUserId();
        String goodsString = baseResponseVo.getData();
        Goods goods = new Goods();                                                  // 设备商品集合mode
        Integer closeType = null;                                                   // 关门类型 10购物 20补货 30游客
        SessionVo sessionVo = null;                                                 // 用户信息
        ClientVo clientVo = null;                                                   // 设备信息
        SocketIOClient socketIOClient = null;                                       // 服务器与手机端通信通道

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


        // 将商品对象由字符串转化为对象
        try {
            if (StringUtil.isNotBlank(goodsString) && !goodsString.equals("[]")) {
                goods = JSON.parseObject(goodsString, Goods.class);                 //商品集合
            }
        } catch (Exception e) {
            logger.error("商品集合json格式转化出现异常:{}", e);
            SocketResponseVo socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "商品集合json格式转化出现异常");
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            return;
        }

        List<TagModel> goodsList = goods.getGoodsList();
        List<CommodityVo> commodityVoList = new ArrayList<>();                      //订单商品集合
        if (CollectionUtils.isNotEmpty(goodsList)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
            logger.debug("开始将视觉商品集合转换为普通商品集合");
            CommodityInfo commodityInfo = null;
            for (TagModel tagModel : goodsList) {
                CommodityVo commodityVo = new CommodityVo();
                String vrCode = tagModel.getSvrCode();
                if (StringUtils.isBlank(vrCode)) {
                    logger.error("视觉商品识别码为空");
                    SocketResponseVo socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "视觉商品识别码为空");
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                    return;
                }
                commodityInfo = commodityInfoService.selectByVrCode(vrCode);
                if (null != commodityInfo) {
                    commodityVo.setName(commodityInfo.getSvrCode());
                    commodityVo.setUrl("");
                    commodityVo.setPrice(commodityInfo.getFsalePrice().toString());
                    commodityVo.setNum(tagModel.getNumber().toString());
                    commodityVoList.add(commodityVo);
                }
            }
            logger.debug("视觉商品集合转换为普通商品集合完毕");
        }


        OrderWapVo orderWapVo = new OrderWapVo();   // 订单对象
        BigDecimal tempTotalPrice = new BigDecimal("0");
        for (CommodityVo commodityVo : commodityVoList) {
            BigDecimal tempPrice = new BigDecimal(commodityVo.getPrice());
            BigDecimal tempNum = new BigDecimal(commodityVo.getNum());
            tempTotalPrice = BigDecimalUtils.add(tempTotalPrice, BigDecimalUtils.multiply(tempPrice, tempNum));
        }
        orderWapVo.setAmount(tempTotalPrice.toString());
        orderWapVo.setCommodityVoList(commodityVoList);
        SocketResponseVo<String> socketResponseVo = SocketResponseVo.getSuccessResponse();
        socketResponseVo.setTypes(30); // 顾客购物
//        try {
//            socketResponseVo.setData(new String(JSON.toJSONString(orderWapVo).getBytes(),"UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            logger.error("socketIo转码异常：{}", e);
//        }
        socketResponseVo.setData(JSON.toJSONString(orderWapVo));
        asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);

        // 给设备推送订单
        MsgToJsonUtils.asynSendMsgByCtxMap(deviceId, ctxMap, true, "购物完成", userId, TypeConstant.CLOSE_DOOR_ORDER);

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
    private OrderModel convertOrderRecordsToOrderModel(List<CreatOrderResult> orderRecords, List<GoodModel> goodModelList, Integer number, BigDecimal actualPayMoney, BigDecimal discountedMoney, BigDecimal amountPayMoney) {
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
        if (null == socketIOClient) {
            logger.error("从通道中获取手机用户信息失败，处理关门成功消息失败，设备ID_用户ID：{}", id);
            map.put("isNotNull", false);
            return map;
        }

        // 从redis中获取扫码用户相关信息
        try {
            sessionVo = (SessionVo) iCached.get(IPUtils.getRemoteAddress(socketIOClient));
            if (null == sessionVo) {
                logger.error("从redis中获取扫码用户相关信息失败，处理关门成功消息失败，用户ID:{}", userId);
                map.put("isNotNull", false);
                socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "从redis中获取扫码用户相关信息失败");
                asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                return map;
            }
        } catch (Exception e) {
            logger.error("从redis中获取扫码用户相关信息出现异常:{}", e);
            map.put("isNotNull", false);
            socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "从redis中获取扫码用户相关信息出现异常");
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
                asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                return map;
            }
        } catch (Exception e) {
            logger.error("从redis中获取设备相关信息出现异常：{}", e);
            map.put("isNotNull", false);
            socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "从redis中获取设备相关信息出现异常");
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
                logger.debug("设备发送开门成功消息到netty服务器");
                openDoorSuccess(baseResponseVo);
            } else if ((TypeConstant.CLOSE_DOOR +"_"+ TypeConstant.INVENTORY).equals(methodType) && Integer.valueOf(200).equals(code)) { // 关门成功
                logger.debug("设备发送关门盘货成功消息到netty服务器");
                closeDoorInventorySuccess(baseResponseVo);
                //closeDoorSuccessTest(baseResponseVo);   //测试关门成功后，生成订单，支付成功失败
            } else if (TypeConstant.OPEN_DOOR_INVENTORY.equals(methodType) && Integer.valueOf(200).equals(code)) {  //开门状态下盘货结果
                logger.debug("设备发送开门状态实时盘货结果到netty服务器");
                openDoorInventory(baseResponseVo);
            } else if (TypeConstant.IS_ALIVE.equals(methodType) && Integer.valueOf(200).equals(code)) { // 设备是否在线
                logger.debug("设备发送在线消息到netty服务器");
                asynUpdateRedisDeviceStatus(baseResponseVo);
            } else if (TypeConstant.CLOSE_DOOR.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.debug("设备发送关门成功消息到netty服务器");
                closeDoorSuccess(baseResponseVo);
            }
            return;
        } else {
            if (TypeConstant.QR_CODE.equals(methodType)) {   // 设备请求发送二维码
                logger.error("二维码重新发送:{}", code);
                resendQrCode(baseResponseVo);
            }
            return;
        }

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

        List<TagModel> goodsList = goods.getGoodsList();
        List<CommodityVo> commodityVoList = new ArrayList<>();                      //订单商品集合
        if (CollectionUtils.isNotEmpty(goodsList)) {                                // 商品集合非空，进行商品对象转换，将goods转换成dto
            logger.debug("开始将视觉商品集合转换为普通商品集合");
            CommodityInfo commodityInfo = null;
            for (TagModel tagModel : goodsList) {
                CommodityVo commodityVo = new CommodityVo();
                String vrCode = tagModel.getSvrCode();
                commodityInfo = commodityInfoService.selectByVrCode(vrCode);
                if (null != commodityInfo) {
                    commodityVo.setName(commodityInfo.getSname());
                    commodityVo.setUrl(commodityInfo.getScommodityImg());
                    commodityVo.setPrice(commodityInfo.getFsalePrice().toString());
                    commodityVo.setNum(tagModel.getNumber().toString());
                    commodityVoList.add(commodityVo);
                }
            }
            logger.debug("视觉商品集合转换为普通商品集合完毕");
        }

        List<GoodModel> goodModelList = new ArrayList<>();
        GoodModel goodModel = null;
        Integer amountNumber = new Integer("0");    // 初始值
        BigDecimal tempAmountPrice = new BigDecimal("0");
//        String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);        //  图片服务器地址前缀
        for (CommodityVo commodityVo : commodityVoList) {
            goodModel = new GoodModel();
            goodModel.setGoodsNumber(new Integer(commodityVo.getNum()));                            // 商品数量
            goodModel.setGoodName(commodityVo.getName());                                           // 商品名称
            goodModel.setGoodPrice(commodityVo.getPrice());                                         // 商品销售单价
//            if (StringUtils.isNotBlank(preUrl)) {
//                goodModel.setGoodImgUrl(preUrl + commodityVo.getUrl());                             // 商品图片地址
//            }
            goodModel.setGoodImgUrl(commodityVo.getUrl());                                          // 商品图片地址
            amountNumber = amountNumber + new Integer(commodityVo.getNum());                        // 累加订单商品数量
            tempAmountPrice = BigDecimalUtils.add(tempAmountPrice, BigDecimalUtils.multiply(new BigDecimal(commodityVo.getNum()), new BigDecimal(commodityVo.getPrice())));
            goodModelList.add(goodModel);
        }
        orderModel.setAmountPayMoney(tempAmountPrice.toString());                           // 订单总金额
        orderModel.setActualPayMoney(tempAmountPrice.toString());
        orderModel.setDiscountedMoney("0.00");
        orderModel.setNumber(amountNumber);                                                 // 订单商品总数量
        orderModel.setGoodsList(goodModelList);                                             // 订单商品集合

        // 发送消息
        MsgToJsonUtils.asynSendMsgByCtxMap(deviceId, ctxMap, true, orderModel, userId, TypeConstant.OPEN_DOOR_INVENTORY);
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
                    String resultMsg = MsgToJsonUtils.setAndroidMsg(true,200, qrCodeUrl, deviceId, null, TypeConstant.QR_CODE);
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
        SessionVo sessionVo = null;
        try {
            sessionVo = (SessionVo) iCached.get(socketIOClient.getRemoteAddress().toString());
            if (null != sessionVo) {
                logger.info("用户编号:{}", sessionVo.getUserCode());
            }
            socketIOClient.sendEvent(socketEvent, JSON.toJSON(socketResponseVo));
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
     * 向设备发送关门盘货购物订单
     * @param deviceId
     * @param userId
     * @param orderRecords
     * @param ctxMap
     */
    private void asynSendShoppingOrderByCtxMap(String deviceId, String userId, List<CreatOrderResult> orderRecords, ConcurrentMap<String, ChannelHandlerContext> ctxMap) {
        if (CollectionUtils.isNotEmpty(orderRecords)) {
            OrderRecord orderRecord = null;
            OrderModel orderModel = new OrderModel();

            Integer number = 0;                 // 商品总数量
            BigDecimal amountPayMoneyTemp = new BigDecimal("0");        // 订单总金额
            BigDecimal actualPayMoneyTemp = new BigDecimal("0");        // 实付金额
            BigDecimal discountedMoneyTemp = new BigDecimal("0");       // 优惠金额

            List<GoodModel> goodsList = new ArrayList<GoodModel>();  // 具体订单商品

            // 转换对象 订单Vo-----> BaseRequestVo
            for (CreatOrderResult creatOrderResult : orderRecords) {
                orderRecord = creatOrderResult.getOrderRecord();
                amountPayMoneyTemp = BigDecimalUtils.add(amountPayMoneyTemp, orderRecord.getFtotalAmount());            // 累加订单总金额
                actualPayMoneyTemp = BigDecimalUtils.add(actualPayMoneyTemp, orderRecord.getFactualPayAmount());        // 累加订单实付金额
                discountedMoneyTemp = BigDecimalUtils.add(discountedMoneyTemp, orderRecord.getFdiscountAmount());       // 累加订单优惠金额

                List<OrderCommodity> orderCommodityList = creatOrderResult.getOrderCommodityList();
                if (CollectionUtils.isNotEmpty(orderCommodityList)) {
                    GoodModel goodModel = null;
                    for (OrderCommodity orderCommodity : orderCommodityList) {
                        goodModel = new GoodModel();
                        goodModel.setGoodName(orderCommodity.getScommodityName());                                      // 商品名称
                        goodModel.setGoodPrice(orderCommodity.getFcommodityPrice().toString());                         // 商品价格
                        goodModel.setGoodsNumber(orderCommodity.getForderCount());                                      // 商品数量
//                        goodModel.setGoodImgUrl();      // 商品图片地址
//                        goodModel.setDiscountType();    // 优惠类型
                        goodsList.add(goodModel);
                    }
                }
            }

            // 封装参数
            orderModel.setAmountPayMoney(amountPayMoneyTemp.toString());
            orderModel.setActualPayMoney(actualPayMoneyTemp.toString());
            orderModel.setDiscountedMoney(discountedMoneyTemp.toString());
            orderModel.setNumber(number);
            orderModel.setGoodsList(goodsList);
            MsgToJsonUtils.asynSendMsgByCtxMap(deviceId, ctxMap, true, orderModel, userId, TypeConstant.CLOSE_DOOR_ORDER);
        }
    }


    /**
     * 向设备发送关门盘货补货单
     * @param deviceId
     * @param userId
     * @param inventoryResult
     * @param ctxMap
     */
    private void asynSendReplenishmentOrderByCtxMap(String deviceId, String userId, InventoryResult inventoryResult, ConcurrentMap<String, ChannelHandlerContext> ctxMap) {
//        ReplenishmentResult replenishmentResult = inventoryResult.getReplenishmentResult(); // 补货单
//        if (null != replenishmentResult) {
//
//        }

    }


}
