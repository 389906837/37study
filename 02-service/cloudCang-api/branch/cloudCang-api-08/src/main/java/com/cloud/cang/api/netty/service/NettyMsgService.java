package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cloud.cang.api.common.AndroidErrorCode;
import com.cloud.cang.api.common.NettyConstant;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.WebSocketConstant;
import com.cloud.cang.api.common.utils.*;
import com.cloud.cang.api.hy.service.MemberInfoService;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.netty.vo.MultiCommodityMatch;
import com.cloud.cang.api.netty.vo.aiFace.FaceDeviceVo;
import com.cloud.cang.api.om.service.OrderCommodityService;
import com.cloud.cang.api.om.service.OrderRecordService;
import com.cloud.cang.api.sb.dao.DeviceModelDao;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceUpgradeDetailsService;
import com.cloud.cang.api.sh.service.MerchantInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.sl.service.DeviceOperService;
import com.cloud.cang.api.sm.dao.DeviceStockDao;
import com.cloud.cang.api.sm.service.DeviceStockService;
import com.cloud.cang.api.sm.vo.StockVo;
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
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.faceCommon.FaceType;
import com.cloud.cang.generatorMysql.Generator;
import com.cloud.cang.inventory.*;
import com.cloud.cang.jy.*;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.*;
import com.cloud.cang.model.LocalGravityVision.CargoRoadGravityModel;
import com.cloud.cang.model.LocalGravityVision.LayerGravityModel;
import com.cloud.cang.model.LocalGravityVision.LocalGVGoodModel;
import com.cloud.cang.model.LocalGravityVision.LocalGVGoods;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.op.UserInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceModel;
import com.cloud.cang.model.sb.DeviceUpgradeDetails;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.tb.OperateDeviceDetail;
import com.cloud.cang.model.tb.OperateDeviceRecord;
import com.cloud.cang.model.tb.ThirdDeviceSku;
import com.cloud.cang.pay.*;
import com.cloud.cang.pojo.BaseResponseVo;
import com.cloud.cang.utils.DateUtils;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

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
    DeviceStockDao deviceStockDao;

    @Autowired
    OrderRecordService orderRecordService;
    @Autowired
    OrderCommodityService orderCommodityService;

    @Autowired
    InterfaceTransferLogService interfaceTransferLogService;    // 第三方接口调用记录表
    @Autowired
    MemberInfoService memberInfoService;
    @Autowired
    private DeviceStockService deviceStockService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private DeviceModelDao deviceModelDao;
    private static final Logger logger = LoggerFactory.getLogger(NettyMsgService.class);
    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();      //netty通道
    private static ConcurrentMap<String, ChannelHandlerContext> faceMap = SingletonClientMap.newInstance().getAiFaceMap();  //face通道
    private static ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();   //socketIo通道


    /**
     * 设备发送过来的开门成功消息
     *
     * @param baseResponseVo 设备消息体
     * @return
     * @throws Exception
     */
    public void openDoorSuccess(BaseResponseVo baseResponseVo) {
        logger.info("准备向手机端发送开门成功消息,用户ID：{}", baseResponseVo.getUserId());
        String deviceId = baseResponseVo.getDeviceId();
        String userId = baseResponseVo.getUserId();
        SocketResponseVo socketResponseVo = SocketResponseVo.getSuccessResponse();
        String id = deviceId + "_" + userId;
        SocketIOClient socketIOClient = socketIoMap.get(id);
        Integer types = null;
        Integer openSource = baseResponseVo.getOpenSource();

        //开门成功 记录开门信息
        try {
            iCached.put("user_operate_device_key_" + userId, 1, 10 * 60);
        } catch (Exception e) {
            logger.error("缓存用户开门异常:{}", e);
        }

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
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(userId);
        MerchantConf conf = null;
        try {
            conf = merchantInfoService.getWechatMerchantConf(memberInfo.getSmerchantCode(), 2);
        } catch (Exception e) {
            logger.info("开门成功获取商户微信配置信息异常：{}", memberInfo.getSmerchantCode());
        }
        if (types == 10 && null != conf && conf.getIwechatWithholdType() == 20 && memberInfo.getIwechatPointOpen() == 1) {
            //创建空的微信支付分订单
            CreatSmartretailOrderDto creatSmartretailOrderDto = new CreatSmartretailOrderDto();
            //creatSmartretailOrderDto.setSmerchantCode(deviceInfo.getSmerchantCode());
            creatSmartretailOrderDto.setSmerchantCode(conf.getSmerchantCode());
            creatSmartretailOrderDto.setDeviceCode(deviceInfo.getScode());
            creatSmartretailOrderDto.setDeviceId(deviceInfo.getId());
            creatSmartretailOrderDto.setSmemberId(userId);
            creatSmartretailOrderDto.setSmemberCode(memberInfo.getScode());
            RestServiceInvoker invoke = null;//服务名称
            try {
                invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_POINT_CREAT_ORDER_N);
                invoke.setRequestObj(creatSmartretailOrderDto); //post 参数
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<CreatSmartretailOrderResult>>() {
                });
                ResponseVo<CreatSmartretailOrderResult> responseVo = (ResponseVo<CreatSmartretailOrderResult>) invoke.invoke();
                if (null != responseVo && responseVo.isSuccess()) {
                    logger.info("开门成功创建微信支付分订单成功：{}", responseVo.getData().getOut_order_no());
                } else {
                    logger.info("开门成功创建微信支付分订单失败：{}", responseVo.getMsg());
                }
            } catch (Exception e) {
                logger.info("开门成功创建微信支付分订单异常：{}", userId + "_" + deviceInfo.getScode());
            }
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
     * 开门前的校验
     *
     * @param baseResponseVo 设备消息体
     * @return
     * @throws Exception
     */
    public void gravityOpenDoor(BaseResponseVo baseResponseVo) {
        logger.debug("开始处理售货机设备发送过来的开门实时盘货结果");
        String dataString = baseResponseVo.getData();              // 商品盘货结果
        Goods goods = new Goods();                                  // json转化后的临时商品集合对象
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
            DeviceModel deviceModel = deviceModelDao.selectByDeviceId(deviceId);
            logger.info("开关门验证,设备购物/盘货方式：{}", deviceModel.getScontrastMode());
          /*  rawstock 原始库存
            openDoor 开门前后 */
            if (null != deviceModel && StringUtil.isNotBlank(deviceModel.getScontrastMode())
                    && deviceModel.getScontrastMode().equals("openDoor")) {
                Map<String, Integer> goodMapTemp = new HashMap<>();
                String goodsList = null;
                if (CollectionUtils.isNotEmpty(goods.getGoodsList())) {   // 遍历开门商品及数量
                    for (TagModel good : goods.getGoodsList()) {
                        String vrCode = good.getSvrCode();       // 商品视觉编号
                        Integer num = good.getNumber();          // 商品数量
                        if (!goodMapTemp.containsKey(vrCode)) {
                            goodMapTemp.put(vrCode, num);
                        } else {
                            Integer tempNum = goodMapTemp.get(vrCode);
                            goodMapTemp.put(vrCode, tempNum + num);
                        }
                    }
                    goodsList = JSON.toJSONString(goodMapTemp);
                } else {
                    goodsList = JSON.toJSONString(goodMapTemp);
                }
                iCached.put("device_total_weight_" + deviceId, goods.getTotalWeight());
                iCached.put(NettyConst.CLOUD_DEVICE_OPEN_DOOR_COMMODITYS + deviceId, goodsList, 2 * 60 * 60);
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, true, userId, TypeConstant.WEIGHT_OPEN_DOOR);
            } else {
                //对比重量
                BigDecimal totalWeight = deviceStockService.selectTotalWeight(deviceId);
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                if (goods.getTotalWeight().subtract(totalWeight).abs().compareTo(deviceInfo.getIelectronicFloat()) > 0) {
                    //重量不相等,不开门
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, false, userId, TypeConstant.WEIGHT_OPEN_DOOR);
                } else {
                    //重量相等,返回开门消息
                    //将开门重力放入缓存
                    iCached.put("device_total_weight_" + deviceId, goods.getTotalWeight());
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, true, userId, TypeConstant.WEIGHT_OPEN_DOOR);
                }
            }
        } catch (Exception e) {
            logger.error("处理售货机设备发送过来的开门实时盘货结果，json转换出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.JSON_CONVERT_EXCEPTION.getCode(), ctxMap, "实时盘货结果中商品数据json转换异常", userId, TypeConstant.OPEN_DOOR_INVENTORY);
            return;
        }

    }

    /**
     * 重力分层开门前的校验
     *
     * @param baseResponseVo 设备消息体
     * @return
     * @throws Exception
     */
    public void gravityLayeredOpenDoor(BaseResponseVo baseResponseVo) {
        logger.debug("开始处理售货机设备发送过来的开门实时盘货结果");
        String dataString = baseResponseVo.getData();              // 商品盘货结果
        ShopLayeredGoods shopLayeredGoods = new ShopLayeredGoods();
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
                shopLayeredGoods = JSON.parseObject(dataString, ShopLayeredGoods.class);
            }
            DeviceModel deviceModel = deviceModelDao.selectByDeviceId(deviceId);
            logger.info("开关门验证,设备购物/盘货方式：{}", deviceModel.getScontrastMode());
          /*  rawstock 原始库存
            openDoor 开门前后 */
            List<LayeredGoods> layeredGoodsList = shopLayeredGoods.getLayeredGoodsList();
            List<LayeredWeight> layeredWeightList = shopLayeredGoods.getLayeredWeightList();
            if (layeredGoodsList == null || layeredGoodsList.isEmpty() || layeredWeightList.isEmpty() || layeredWeightList == null || layeredGoodsList.size() != layeredWeightList.size()) {
                logger.info("开门验证重量和视觉参数错误,不开门!");
                logger.info("开门检验视觉参数：{}", layeredGoodsList);
                logger.info("开门检验重力参数：{}", layeredWeightList);
                MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.JSON_CONVERT_EXCEPTION.getCode(), ctxMap, "实时盘货结果中商品数据json转换异常", userId, TypeConstant.OPEN_DOOR_INVENTORY);
                /* MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, false, userId, TypeConstant.WEIGHT_OPEN_DOOR);*/
                return;
            }
            //汇总重力视觉结果
            //汇总视觉称重结果
            Map<String, Object> map = mergelayeredResult(layeredGoodsList, layeredWeightList);
            layeredGoodsList = (List<LayeredGoods>) map.get("layeredGoodsList");
            BigDecimal totalWeight = (BigDecimal) map.get("totalWeight");
            String goodsList = JSON.toJSONString(layeredGoodsList);
            iCached.put("layered_device_total_weight_" + deviceId, totalWeight);
            iCached.put(NettyConst.LAYERED_CLOUD_DEVICE_OPEN_DOOR_COMMODITYS + deviceId, goodsList, 2 * 60 * 60);
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, true, userId, TypeConstant.WEIGHT_OPEN_DOOR);
        } catch (Exception e) {
            logger.error("处理售货机设备发送过来的开门实时盘货结果，json转换出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.JSON_CONVERT_EXCEPTION.getCode(), ctxMap, "实时盘货结果中商品数据json转换异常", userId, TypeConstant.OPEN_DOOR_INVENTORY);
            return;
        }

    }

    /**
     * 重力分层补货开门前的校验
     *
     * @param baseResponseVo 设备消息体
     * @return
     * @throws Exception
     */
    public void gravityLayeredReplenOpenDoor(BaseResponseVo baseResponseVo) {
        logger.debug("重力分层补货开门前的数据");
        String dataString = baseResponseVo.getData();              // 商品盘货结果
        ShopLayeredGoods shopLayeredGoods = new ShopLayeredGoods();
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
                shopLayeredGoods = JSON.parseObject(dataString, ShopLayeredGoods.class);
            }
            List<LayeredGoods> layeredGoodsList = shopLayeredGoods.getLayeredGoodsList();
            List<LayeredWeight> layeredWeightList = shopLayeredGoods.getLayeredWeightList();
            if (layeredGoodsList == null || layeredGoodsList.isEmpty() || layeredWeightList.isEmpty() || layeredWeightList == null || layeredGoodsList.size() != layeredWeightList.size()) {
                logger.info("开门验证重量和视觉参数错误,不开门!");
                logger.info("开门检验视觉参数：{}", layeredGoodsList);
                logger.info("开门检验重力参数：{}", layeredWeightList);
                MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.JSON_CONVERT_EXCEPTION.getCode(), ctxMap, "实时盘货结果中商品数据json转换异常", userId, TypeConstant.OPEN_DOOR_INVENTORY);
                return;
            }
            //汇总重力视觉结果
            //汇总视觉称重结果
            Map<String, Object> map = mergelayeredResult(layeredGoodsList, layeredWeightList);
            layeredGoodsList = (List<LayeredGoods>) map.get("layeredGoodsList");
            BigDecimal totalWeight = (BigDecimal) map.get("totalWeight");
            String goodsList = JSON.toJSONString(layeredGoodsList);
            iCached.put("layered_device_replen_total_weight_" + deviceId, totalWeight);
            iCached.put(NettyConst.LAYERED_CLOUD_DEVICE_REPLEN_OPEN_DOOR_COMMODITYS + deviceId, goodsList, 2 * 60 * 60);
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, true, userId, TypeConstant.WEIGHT_REPLEN_OPEN_DOOR);
        } catch (Exception e) {
            logger.error("处理售货机设备发送过来的开门实时盘货结果，json转换出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.JSON_CONVERT_EXCEPTION.getCode(), ctxMap, "实时盘货结果中商品数据json转换异常", userId, TypeConstant.OPEN_DOOR_INVENTORY);
            return;
        }
    }

    /**
     * 关门盘货消息
     *
     * @param baseResponseVo
     * @return
     * @throws Exception
     */

    public void closeDoorInventorySuccess(BaseResponseVo baseResponseVo) {
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
                removeUserOpenDoorKey(userId);
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
                            createPayOrder(sessionVo, deviceId, userId, responseVo.getData().getMerchantCode(), responseVo.getData().getOrderRecords(), ctxMap);
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
                            unsignAlipay(sessionVo, responseVo.getData().getMerchantCode());//支付宝解密
                            cancelWechatPointOrder(userId, responseVo.getData().getMerchantCode(), deviceId);

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
                        //取消订单
                        cancelWechatPointOrder(userId, "", deviceId);
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
            removeUserOpenDoorKey(userId);
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
                    removeUserOpenDoorKey(userId);
                    logger.error("从redis中获取AI设备信息失败，处理刷脸开门,关门盘货消息失败，设备ID:{}", deviceId);
                    return;
                }
            } catch (Exception e) {
                removeUserOpenDoorKey(userId);
                logger.error("从redis中获取AI设备相关信息出现异常：{}", e);
                return;
            }

            // 将商品对象由字符串转化为对象
            try {
                if (StringUtil.isNotBlank(goodsString) && !goodsString.equals("[]")) {
                    goods = JSON.parseObject(goodsString, Goods.class);                 //商品集合
                }
            } catch (Exception e) {
                removeUserOpenDoorKey(userId);
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
                            createPayOrder(sessionVo, deviceId, userId, responseVo.getData().getMerchantCode(), responseVo.getData().getOrderRecords(), ctxMap);
                        }


                    } else if (inventoryResult.getItype().intValue() == 50) { // 游客
                        if (openDoorType == 10) {//购物开门
                            closeType = 30;
                            //给设备推送关门结果
                            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.NO_CHANGE_IN_PRODUCT_QUANTITY, userId, TypeConstant.CLOSE_DOOR);
                            unsignAlipay(sessionVo, responseVo.getData().getMerchantCode());//支付宝解密
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
            removeUserOpenDoorKey(userId);
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
            removeUserOpenDoorKey(userId);
            return;
        }


    }

    /**
     * 删除用户 开门缓存
     *
     * @param userId 用户ID
     */
    private void removeUserOpenDoorKey(String userId) {
        try {
            iCached.remove("user_operate_device_key_" + userId);
        } catch (Exception e1) {
            logger.error("移除用户开门缓存移除:{}", e1);
        }
    }

    /**
     * 创建支付订单
     *
     * @param sessionVo    用户信息
     * @param merchantCode 商户编号
     * @param orderRecords 订单集合
     */
    public void createPayOrder(final SessionVo sessionVo, final String deviceId, final String userId, final String merchantCode, final List<CreatOrderResult> orderRecords, final ConcurrentMap<String, ChannelHandlerContext> ctxMap) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                OrderRecord orderRecord = null;
                try {
                    String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + merchantCode);
                   /* if (StringUtil.isBlank(json)) {
                        return null;
                    }*/
                    MerchantConf merchantConf = JSONObject.parseObject(json, MerchantConf.class);
                   /* 微信代扣方式 10:签约免密  20:微信支付分 */

                    logger.info("创建免密支付订单,参数：{}", orderRecords.toArray());
                    FreePaymentDto paymentDto = null;

                    MerchantClientConf clientConf = null;
                    String smerchantCode = merchantCode;

                    RestServiceInvoker invoke = null;
                    ResponseVo<FreePaymentResult> resVO = null;
                    boolean payFlag = false;
                    for (CreatOrderResult orderResult : orderRecords) {
                        orderRecord = orderResult.getOrderRecord();
                        try {
                            clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
                            if (null == clientConf || (orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode() && clientConf.getIisConfWechat().intValue() == 0)
                                    || (orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode() && clientConf.getIisConfAlipay().intValue() == 0)) {//没有配置获取默认商户编号
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
                        if (!merchantCode.equals(orderRecord.getSmerchantCode())) {
                            logger.error("商户订单异常，商户编号：{}", merchantCode + "===========" + orderRecord.getSmemberCode());
                            continue;
                        }
                        if (orderRecord.getIstatus().intValue() == 100 && (orderRecord.getIchargebackWay().intValue() == 10
                                || orderRecord.getIchargebackWay().intValue() == 30 || orderRecord.getIchargebackWay().intValue() == 40)) {//待支付  商户代扣
                            if ((orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode() &&
                                    (orderRecord.getIchargebackWay().intValue() == 10 || orderRecord.getIchargebackWay().intValue() == 30)  /*&& merchantConf.getIwechatWithholdType() == 10*/) || orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode()) {
                                payFlag = true;
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
                                if (!payFlag) {//没有支付
                                    unsignAlipay(sessionVo, merchantCode);//支付宝解密
                                }
                            } else if (orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode()
                                   /* && merchantConf.getIwechatWithholdType() == 20*/
                                    && orderRecord.getIchargebackWay().intValue() == 40) {
                                try {
                                    QueryAndEndSmartretailOrderDto queryAndEndSmartretailOrderDto = new QueryAndEndSmartretailOrderDto();
                                    queryAndEndSmartretailOrderDto.setSmerchantCode(merchantCode);
                                    queryAndEndSmartretailOrderDto.setSmemberId(userId);
                                    queryAndEndSmartretailOrderDto.setDeviceId(deviceId);
                                    List<FeeDto> feeDtos = null;
                                    FeeDto feeDto = null;
                                    List<OrderCommodity> orderCommodities = orderCommodityService.selectByOrderId(orderRecord.getId());
                                    if (null != orderCommodities && !orderCommodities.isEmpty()) {
                                        feeDtos = new ArrayList<>();
                                        for (OrderCommodity orderCommodity : orderCommodities) {
                                            feeDto = new FeeDto();
                                            feeDto.setFee_name(orderCommodity.getScommodityName());
                                            feeDto.setFee_count(orderCommodity.getForderCount());
                                            feeDto.setFee_amount(orderCommodity.getFactualAmount().multiply(new BigDecimal(100)).intValue());
                                            feeDtos.add(feeDto);
                                        }
                                        queryAndEndSmartretailOrderDto.setFees(feeDtos);
                                    }
                                    queryAndEndSmartretailOrderDto.setFinish_type(2);
                                    queryAndEndSmartretailOrderDto.setTotal_amount(orderRecord.getFactualPayAmount().multiply(new BigDecimal(100)).intValue());
                                    queryAndEndSmartretailOrderDto.setOrderId(orderRecord.getId());
                                    queryAndEndSmartretailOrderDto.setProfit_sharing(false);
                                    String url = FreeServicesDefine.WECHAT_POINT_QUERY_AND_END_ORDER;
                                    invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(url);// 服务名称
                                    // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<EndSmartretailOrderResult>>() {
                                    });
                                    invoke.setRequestObj(queryAndEndSmartretailOrderDto); // post 参数
                                    ResponseVo<EndSmartretailOrderResult> responseVo = (ResponseVo<EndSmartretailOrderResult>) invoke.invoke();
                                    if (null != responseVo && responseVo.isSuccess()) {
                                        logger.error("微信支付分查询并且完结订单成功:{}", JSON.toJSONString(responseVo));
                                        continue;
                                    } else {
                                        flag = false;
                                        logger.error("微信支付分查询并且完结订单异常:{}", JSON.toJSONString(responseVo));
                                    }
                                } catch (Exception e) {
                                    flag = false;
                                    logger.error("微信支付分查询并且完结订单异常，收款商户编号===========订单编号：{}", smerchantCode + "===========" + orderRecord.getId());
                                }
                             /*   ResponseVo<EndSmartretailOrderResult> responseVo = new ResponseVo<EndSmartretailOrderResult>();
                                //创建支付
                                CreatSmartretailOrderDto creatSmartretailOrderDto = new CreatSmartretailOrderDto();
                                creatSmartretailOrderDto.setOrderId(orderRecord.getId());
                                FeeDto feeDto = null;
                                List<FeeDto> feeDtos = null;
                                List<OrderCommodity> orderCommodities = orderCommodityService.selectByOrderId(orderRecord.getId());
                                if (null != orderCommodities && !orderCommodities.isEmpty()) {
                                    feeDtos = new ArrayList<>();
                                    for (OrderCommodity orderCommodity : orderCommodities) {
                                        feeDto = new FeeDto();
                                        feeDto.setFee_name(orderCommodity.getScommodityName());
                                        feeDto.setFee_count(orderCommodity.getForderCount());
                                        feeDto.setFee_amount(orderCommodity.getFactualAmount().multiply(new BigDecimal(100)).intValue());
                                        feeDtos.add(feeDto);
                                    }
                                    creatSmartretailOrderDto.setFees(feeDtos);
                                }
                                creatSmartretailOrderDto.setTotal_amount(orderRecord.getFactualPayAmount().multiply(new BigDecimal(100)).intValue());
                                String url = FreeServicesDefine.WECHAT_POINT_DENERATING_ORDER;
                                try {
                                    invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(url);// 服务名称
                                    // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<EndSmartretailOrderResult>>() {
                                    });
                                    invoke.setRequestObj(creatSmartretailOrderDto); // post 参数
                                    responseVo = (ResponseVo<EndSmartretailOrderResult>) invoke.invoke();
                                    if (responseVo.isSuccess()) {
                                        logger.info("订单代扣支付成功:{}", responseVo.getData());
                                        continue;
                                    } else {
                                        flag = false;
                                        logger.error("订单代扣支付异常:{}", responseVo);
                                    }
                                } catch (Exception e) {
                                    flag = false;
                                    logger.error("微信支付分创建订单异常，收款商户编号===========订单编号：{}", smerchantCode + "===========" + orderRecord.getId());
                                }*/
                               /* if (!payFlag) {//没有支付
                                    unsignAlipay(sessionVo, merchantCode);//支付宝解密
                                }*/
                            }
                        }
                    }
                } catch (Exception e) {
                    flag = false;
                }
                if (flag) {
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, orderRecord.getIchargebackWay(), userId, TypeConstant.PAY_SUCCESS);
                } else {
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, orderRecord.getIchargebackWay(), userId, TypeConstant.PAY_FAIL);
                }
            }
        });
    }


    /**
     * 支付宝解签
     *
     * @param merchantCode 商户编号
     * @param sessionVo    缓存中用户信息
     */
    public void unsignAlipay(final SessionVo sessionVo, final String merchantCode) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (StringUtil.isNotBlank(merchantCode)) {
                        String smerchantCode = merchantCode;
                        if (null != sessionVo && null != sessionVo.getIsourceClientType()
                                && sessionVo.getIsourceClientType().intValue() == 20) {
                            MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
                            if (null == clientConf || null == clientConf.getIisConfAlipay() || clientConf.getIisConfAlipay().intValue() == 0) {//没有配置获取默认商户编号
                                smerchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
                                clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + smerchantCode);
                            }
                            if (null != clientConf && null != clientConf.getIwithholdingWay() && clientConf.getIwithholdingWay() == 20) {//判断是单次代扣
                                UnsignDto unsignDto = new UnsignDto();
                                unsignDto.setSmerchantCode(smerchantCode);
                                unsignDto.setSmemberId(sessionVo.getUserId());
                                unsignDto.setSmemberMerchantCode(merchantCode);
                                unsignDto.setSip(sessionVo.getSip());
                                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_UNSIGN);// 服务名称
                                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                                });
                                invoke.setRequestObj(unsignDto); // post 参数
                                ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
                                if (!resVO.isSuccess()) {
                                    logger.error("单次代扣用户支付宝免密解约异常：{}", resVO.getMsg());
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("单次代扣支付宝解签免密支付异常：{}", e);
                }
            }
        });
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
     *
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
            } else if ((TypeConstant.CLOSE_DOOR + "_" + TypeConstant.INVENTORY).equals(methodType) && Integer.valueOf(200).equals(code)) { // 关门成功
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
            } else if (TypeConstant.WEIGHT_OPEN_DOOR.equals(methodType) && Integer.valueOf(200).equals(code)) {    //开门前的校验
                logger.debug("设备:{},设备开门前的校验", deviceId);
                gravityOpenDoor(baseResponseVo);
            } else if (TypeConstant.WEIGHT_LAYERED_OPEN_DOOR.equals(methodType) && Integer.valueOf(200).equals(code)) {    //开门成功
                logger.debug("设备:{},设备开门前的校验", deviceId);
                gravityLayeredOpenDoor(baseResponseVo);
            } else if (TypeConstant.WEIGHT_LAYERED_REPLEN_OPEN_DOOR.equals(methodType) && Integer.valueOf(200).equals(code)) {    //开门成功
                logger.debug("设备:{},设备补货开门前的校验", deviceId);
                gravityLayeredReplenOpenDoor(baseResponseVo);
            } else if (TypeConstant.LOCAL_GRAVITY_LAYERED_REPLEN_OPEN_DOOR_INVENTORY.equals(methodType) && Integer.valueOf(200).equals(code)) {
                String deviceCode = getDeviceCodeByDeviceId(deviceId);
                logger.debug("设备编号：{}，重力识别分层补货实时盘货跳转页面结果", deviceCode);
                localGravityLayeredReplenOpenDoorInventory(deviceId, deviceCode, baseResponseVo);
            } else if (TypeConstant.LOCAL_GRAVITY_OPEN_DOOR_INVENTORY.equals(methodType) && Integer.valueOf(200).equals(code)) {
                String deviceCode = getDeviceCodeByDeviceId(deviceId);
                logger.debug("设备编号：{}，重力识别实时盘货跳转页面结果", deviceCode);
                localGravityOpenDoorInventory(deviceId, deviceCode, baseResponseVo);
            } else if (TypeConstant.LOCAL_GRAVITY_LAYERED_OPEN_DOOR_INVENTORY.equals(methodType) && Integer.valueOf(200).equals(code)) {
                String deviceCode = getDeviceCodeByDeviceId(deviceId);
                logger.debug("设备编号：{}，重力识别分层实时盘货跳转页面结果", deviceCode);
                localGravityLayeredOpenDoorInventory(deviceId, deviceCode, baseResponseVo);
            } else if (TypeConstant.LOCAL_GRAVITY_CLOSE_DOOR.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.info("设备:{},重力视觉柜关门生成订单开始", deviceId);
                String deviceCode = getDeviceCodeByDeviceId(deviceId);
                localGravityCloseDoor(deviceCode, deviceId, baseResponseVo);
            } else if (TypeConstant.LOCAL_GRAVITY_LAYERED_CLOSE_DOOR.equals(methodType) && Integer.valueOf(200).equals(code)) {
                logger.info("设备:{},重力视觉柜分层关门生成订单开始", deviceId);
                String deviceCode = getDeviceCodeByDeviceId(deviceId);
                localGravityLayeredCloseDoor(deviceCode, deviceId, baseResponseVo);
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
     * netty处理测试消息handler
     *
     * @param baseResponseVo 设备发送过来的消息对象
     */
    public void handlerTestMessage(BaseResponseVo baseResponseVo, ChannelHandlerContext ctx) {
        logger.info("开始处理普通消测试息，数据：{}", baseResponseVo);
        String methodType = baseResponseVo.getMethodType();
        boolean success = baseResponseVo.isSuccess();
        Integer code = baseResponseVo.getCode();
        String deviceId = baseResponseVo.getDeviceId();
        if (StringUtils.isBlank(methodType)) {  //方法名为空直接返回
            logger.error("设备ID为：" + deviceId + "发送测试消息的方法名为空！");
            return;
        }
        // 消息
        if (BooleanUtils.isTrue(success)) {
            if (TypeConstant.TEST_CONNECTION.equals(methodType) && Integer.valueOf(200).equals(code)) {    //连接测试
                logger.debug("设备:{}发送测试连消息到netty服务器", deviceId);
                testConnection(baseResponseVo, ctx);
            }
        }
        return;
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
        socketResponseVo.setData(deviceId + "_" + userId);
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
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<RealTimeOrderResult>>() {
                });
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
                            // goodModel.setGoodImgUrl(preUrl + result.getScommodityImg()); // 商品图片地址
                            if (StringUtils.isNotBlank(result.getScommodityImg())) {
                                goodModel.setGoodImgUrl(preUrl + result.getScommodityImg());
                            }
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
                            if (StringUtils.isNotBlank(repCom.getScommodityUrl())) {
                                goodModel.setGoodImgUrl(preUrl + repCom.getScommodityUrl());
                            }
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
                            if (StringUtils.isNotBlank(repCom.getScommodityUrl())) {
                                goodModel.setGoodImgUrl(preUrl + repCom.getScommodityUrl());
                            }
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
     * 重力视觉柜子开门实时订单计算
     *
     * @param deviceId       设备ID
     * @param deviceCode     设备编号
     * @param baseResponseVo 消息体
     */
    private void localGravityOpenDoorInventory(String deviceId, String deviceCode, BaseResponseVo baseResponseVo) {
        logger.debug("开始处理售货机设备发送过来的开门实时盘货结果：{}", deviceCode);
        String dataString = baseResponseVo.getData();                           // 商品盘货结果
        Goods goods = new Goods();
        OrderModel orderModel = new OrderModel();                               // 根据盘货的结果计算订单对象
        String userId = baseResponseVo.getUserId();
        if (StringUtils.isBlank(userId)) {
            logger.error("重力视觉柜子开门实时盘货消息体中会员ID不能为空");
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.MEMBER_ID_ISNULL.getCode(), ctxMap, "重力视觉柜子开门实时盘货消息体中会员ID不能为空", null, TypeConstant.OPEN_DOOR_INVENTORY);
            return;
        }
        // json转换商品对象
        try {
            if (StringUtils.isNotBlank(dataString)) {
                goods = JSON.parseObject(dataString, Goods.class);
                logger.info("视觉重力实时盘货：{}", dataString);
            }
        } catch (Exception e) {
            logger.error("处理售货机设备发送过来的开门实时盘货结果，json转换出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.JSON_CONVERT_EXCEPTION.getCode(), ctxMap, "实时盘货结果中商品数据json转换异常", userId, TypeConstant.OPEN_DOOR_INVENTORY);
            return;
        }

        // 获取开门类型 10 购物开门 20 补货员开门
        String openDoorType = goods.getOpenDoorType();
        String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);

        try {
            if (NettyConstant.SHOPPING.equals(openDoorType)) {   // 购物开门
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                BigDecimal stockWeightTotal = (BigDecimal) iCached.get("device_total_weight_" + deviceId);//开门盘货重量
                logger.info("购物实时订单-->重力视觉柜库存商品重量：{}", stockWeightTotal);
                logger.info("购物实时订单-->Android商品重量：{}", goods.getTotalWeight());
                BigDecimal subTemp = stockWeightTotal.subtract(goods.getTotalWeight());
                logger.info("购物实时订单-->实时盘货开关门重力差值：{}", subTemp);
                if (subTemp.abs().compareTo(deviceInfo.getIelectronicFloat()) <= 0) {
                    //重力不变,不出订单
                    logger.info("购物实时订单-->重力视觉实时库存重力不变,不出订单");
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
                } else if (goods.getTotalWeight().compareTo(stockWeightTotal.add(deviceInfo.getIelectronicFloat())) > 0) {
                    //重力比库存重量大,不出订单,语音播报
                    logger.info("购物实时订单-->重力视觉实时库存重力不变,重力比库存重量大,不出订单,语音播报");
                    MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_GRAVITY_ORDER_EXCEPTION_EX.getCode(), ctxMap, "创建开门盘货生成实时订单,库存重量小于设备商品重量", userId, TypeConstant.OPEN_DOOR_INVENTORY);
                } else {
                    logger.debug("购物实时订单-->调用购物开门实时盘货服务成功");
                    Map<String, Object> map = null;
                    try {
                        DeviceModel deviceModel = deviceModelDao.selectByDeviceId(deviceInfo.getId());
                        logger.info("购物实时订单-->重力视觉柜实时盘货开关门对比,设备盘货方式：{}", deviceModel.getScontrastMode());
                        if (null != deviceModel && StringUtil.isNotBlank(deviceModel.getScontrastMode())
                                && deviceModel.getScontrastMode().equals("rawstock")) {
                            //查询设备库存
                            map = calcCommodityDiffByType(goods.getGoodsList(), deviceInfo);
                        } else {
                            String goodMap = (String) iCached.get(NettyConst.CLOUD_DEVICE_OPEN_DOOR_COMMODITYS + deviceInfo.getId());    // 在Redis中记录开门商品
                            logger.info("购物实时订单-->重力视觉柜实时盘货开关门对比，开门商品数据,{}", goodMap);
                            Map<String, Integer> openDoorMap = JSON.parseObject(goodMap, new TypeReference<Map<String, Integer>>() {
                            });
                            map = calcCommodityDiffData(openDoorMap, goods.getGoodsList(), deviceInfo);
                            logger.info("购物实时订单-->重力视觉柜实时盘货开关门对比商品差异完成：{}", deviceInfo.getScode());
                        }
                    } catch (Exception e) {
                        logger.error("从Redis中获取开门商品集合失败，设备编号：{}", deviceInfo.getScode());
                    }
                    List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");
                    logger.info("购物实时订单-->subVoList：{}", subVoList);
                    logger.info("购物实时订单-->有视觉减少-->对比视觉和库存重量的变化");
                    BigDecimal subResult = subTemp;//重力减少的值
                    BigDecimal subTotal = BigDecimal.ZERO;//视觉盘点减少的重力值
                    BigDecimal floatResult = BigDecimal.ZERO;//商品浮动值
                    List<String> visionList = new ArrayList();//视觉List存储商品识别编号
                    for (CommodityDiffVo commodityDiffVo : subVoList) {
                        BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                        subTotal = subTotal.add(multiplyData);
                        floatResult = floatResult.add(commodityDiffVo.getIcommodityFloat());
                        visionList.add(commodityDiffVo.getSvrCode());
                    }
                    BigDecimal iele = deviceInfo.getIelectronicFloat();
                    logger.info("购物实时订单-->商品浮动值总计floatResult:" + floatResult + "##:设备传感器浮动值iele" + iele);
                    if (subResult.subtract(subTotal).abs().compareTo(floatResult.add(iele)) <= 0) {
                        //在误差范围内,直接出订单
                        logger.info("购物实时订单-->开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->误差范围内,直接出订单");
                        //生成实时订单
                        geOrder(userId, deviceCode, subVoList, deviceId);
                    } else {
                        //如果重力和视觉变化的相差巨大，且重力可以准确匹配到相对应的结果，以重力匹配的结果为准输出实时订单，如果两方均无法准确匹配出视觉实时订单
                        //查询设备库存商品
                        logger.info("购物实时订单-->开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品");
                        if (null != subVoList && !subVoList.isEmpty()) {
                            logger.info("购物实时订单-->开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->视觉有减少");
                            geOrder(iele, deviceInfo, subVoList, subResult, subTotal, 10, floatResult, userId, visionList);
                        } else {
                            //匹配商品
                            logger.info("购物实时订单-->开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->视觉不变或增多");
                            geOrder(iele, deviceInfo, subVoList, subResult, null, 20, null, userId, visionList);
                        }
                    }
                }
            } else {
                logger.error("创建开门盘货生成实时订单出现错误，开门类型不能为空");
                MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_EXCEPTION.getCode(), ctxMap, "创建开门盘货生成实时订单出现错误，开门类型不能为空", userId, TypeConstant.OPEN_DOOR_INVENTORY);
            }

        } catch (Exception e) {
            logger.error("创建开门盘货生成实时订单出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_EXCEPTION.getCode(), ctxMap, "创建开门盘货生成实时订单出现异常", userId, TypeConstant.OPEN_DOOR_INVENTORY);
        }

    }

    /**
     * 重力视觉分层柜开门实时订单计算
     *
     * @param deviceId       设备ID
     * @param deviceCode     设备编号
     * @param baseResponseVo 消息体
     */
    private void localGravityLayeredOpenDoorInventory(String deviceId, String deviceCode, BaseResponseVo baseResponseVo) {
        logger.debug("开始处理售货机设备发送过来的开门实时盘货结果：{}", deviceCode);
        String dataString = baseResponseVo.getData();                           // 商品盘货结果
        ShopLayeredGoods shopLayeredGoods = new ShopLayeredGoods();
        String userId = baseResponseVo.getUserId();
        if (StringUtils.isBlank(userId)) {
            logger.error("重力视觉分层柜子开门实时盘货消息体中会员ID不能为空");
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.MEMBER_ID_ISNULL.getCode(), ctxMap, "重力视觉柜子开门实时盘货消息体中会员ID不能为空", null, TypeConstant.OPEN_DOOR_INVENTORY);
            return;
        }
        // json转换商品对象
        try {
            if (StringUtils.isNotBlank(dataString)) {
                shopLayeredGoods = JSON.parseObject(dataString, ShopLayeredGoods.class);
                logger.info("视觉重力实时盘货：{}", dataString);
            }
        } catch (Exception e) {
            logger.error("处理售货机设备发送过来的开门实时盘货结果，json转换出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.JSON_CONVERT_EXCEPTION.getCode(), ctxMap, "实时盘货结果中商品数据json转换异常", userId, TypeConstant.OPEN_DOOR_INVENTORY);
            return;
        }
        // 获取开门类型 10 购物开门 20 补货员开门
        String openDoorType = shopLayeredGoods.getOpenDoorType();
        try {
            if (NettyConstant.SHOPPING.equals(openDoorType)) {   // 购物开门
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                List<LayeredGoods> layeredGoodsList = shopLayeredGoods.getLayeredGoodsList();
                List<LayeredWeight> layeredWeightList = shopLayeredGoods.getLayeredWeightList();
                Map<String, Object> map = mergelayeredResult(layeredGoodsList, layeredWeightList);
                BigDecimal stockWeightTotal = (BigDecimal) iCached.get("layered_device_total_weight_" + deviceId);//开门盘货重量
                BigDecimal totalWeight = (BigDecimal) map.get("totalWeight");//实时订单盘货重量
                logger.info("购物实时订单-->开门盘货重量：{}", stockWeightTotal);
                logger.info("购物实时订单-->实时盘货重量：{}", totalWeight);
                BigDecimal subTemp = stockWeightTotal.subtract(totalWeight);
                logger.info("分层购物实时订单-->分层实时盘货重量差值：{}", subTemp);
                DeviceModel deviceModel = deviceModelDao.selectByDeviceId(deviceId);
                Integer ilayerNum = deviceModel.getIlayerNum();
                BigDecimal ieleTotal = deviceInfo.getIelectronicFloat().multiply(new BigDecimal(ilayerNum));
                logger.info("分层购物实时订单-->设备总误差：{}", ieleTotal);
                if (subTemp.abs().compareTo(ieleTotal) <= 0) {
                    //重力不变,不出订单
                    logger.info("分层购物实时订单-->重力视觉实时库存重力不变,不出订单");
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
                } else if (totalWeight.compareTo(stockWeightTotal.add(ieleTotal)) > 0) {
                    //重力比库存重量大,不出订单,语音播报
                    logger.info("分层购物实时订单-->重力视觉实时库存重力不变,重力比库存重量大,不出订单,语音播报");
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
                    MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_GRAVITY_ORDER_EXCEPTION_EX.getCode(), ctxMap, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
                } else {
                    logger.debug("分层购物实时订单-->调用购物开门实时盘货服务成功");

                    List<LayeredGoods> realGoods = (List<LayeredGoods>) map.get("layeredGoodsList");
                    String openMap = (String) iCached.get(NettyConst.LAYERED_CLOUD_DEVICE_OPEN_DOOR_COMMODITYS + deviceInfo.getId());    // 在Redis中记录开门商品
                    List<LayeredGoods> openGoods = JSON.parseObject(openMap, new TypeReference<List<LayeredGoods>>() {
                    });
                    logger.info("重力视觉柜分层计算开关门对比差异，开门数据,{}", openGoods);
                    logger.info("重力视觉柜分层计算开关门对比差异，关门数据,{}", realGoods);
                    List<ContrastGoodsModel> contrastGoodsModelList = calcLayeredCommodityDiffData(realGoods, openGoods, deviceInfo);
                    Map<String, CommodityDiffVo> addMap = new HashMap<>();
                    Map<String, CommodityDiffVo> subMap = new HashMap<>();
                    BigDecimal weighLayered = BigDecimal.ZERO;
                    BigDecimal subResult = BigDecimal.ZERO;
                    for (ContrastGoodsModel contrastGoodsModel : contrastGoodsModelList) {
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
                    logger.info("分层购物实时订单-->重力视觉柜实时盘货开关门对比商品差异完成：{}", deviceInfo.getScode());
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
                    logger.info("分层购物实时订单-->有视觉减少-->对比视觉和库存重量的变化");
                    //BigDecimal subResult = subTemp;//重力减少的值
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
                    logger.info("分层购物实时订单-->商品浮动值总计floatResult:" + floatResult + "##:设备传感器浮动值iele:" + iele + "##:计算层数weighLayered:" + weighLayered);
                    if (subResult.subtract(subTotal).abs().compareTo(floatResult.add(iele)) <= 0) {
                        //在误差范围内,直接出订单
                        logger.info("分层购物实时订单-->开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->误差范围内,直接出订单");
                        //生成实时订单
                        geOrder(userId, deviceCode, subVoList, deviceId);
                    } else {

                        String temp = BizParaUtil.get("weight_layered_warm");
                        if (StringUtils.isBlank(temp)) {
                            temp = "15";
                        }
                        BigDecimal weightLayeredWarm = new BigDecimal(temp);
                        BigDecimal weightLayeredWarmTotal = weightLayeredWarm.multiply(weighLayered);
                        if (subResult.subtract(subTotal).abs().compareTo(iele.add(weightLayeredWarmTotal).add(floatResult)) <= 0) {
                            if (!subVoList.isEmpty()) {
                                geOrder(userId, deviceCode, subVoList, deviceId);
                                return;
                            } else {
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
                                return;
                            }
                        }
                        //如果重力和视觉变化的相差巨大，且重力可以准确匹配到相对应的结果，以重力匹配的结果为准输出实时订单，如果两方均无法准确匹配出视觉实时订单
                        //查询设备库存商品
                        logger.info("分层购物实时订单-->开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品");
                        if (null != subVoList && !subVoList.isEmpty()) {
                            logger.info("分层购物实时订单-->开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->视觉有减少");
                            layeredGeOrder2(iele, deviceInfo, subVoList, subResult, subTotal, 10, floatResult, userId, visionList);
                        } else {
                            //匹配商品
                            logger.info("分层购物实时订单-->开门盘货重力大于关门盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->视觉不变或增多");
                            layeredGeOrder2(iele, deviceInfo, subVoList, subResult, null, 20, null, userId, visionList);
                        }
                    }
                }
            } else {
                logger.error("创建开门盘货生成实时订单出现错误，开门类型不能为空");
                MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_EXCEPTION.getCode(), ctxMap, "创建开门盘货生成实时订单出现错误，开门类型不能为空", userId, TypeConstant.OPEN_DOOR_INVENTORY);
            }
        } catch (Exception e) {
            logger.error("创建开门盘货生成实时订单出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_EXCEPTION.getCode(), ctxMap, "创建开门盘货生成实时订单出现异常", userId, TypeConstant.OPEN_DOOR_INVENTORY);
        }
    }

    /**
     * 重力视觉分层柜补货开门实时订单计算
     *
     * @param deviceId       设备ID
     * @param deviceCode     设备编号
     * @param baseResponseVo 消息体
     */
    private void localGravityLayeredReplenOpenDoorInventory(String deviceId, String deviceCode, BaseResponseVo baseResponseVo) {
        logger.debug("补货员补货,开始处理售货机设备发送过来的开门实时盘货结果：{}", deviceCode);
        String dataString = baseResponseVo.getData();                           // 商品盘货结果
        ShopLayeredGoods shopLayeredGoods = new ShopLayeredGoods();
        String userId = baseResponseVo.getUserId();
        if (StringUtils.isBlank(userId)) {
            logger.error("重力视觉分层柜子开门补货实时盘货消息体中会员ID不能为空");
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.MEMBER_ID_ISNULL.getCode(), ctxMap, "重力视觉柜子开门补货实时盘货消息体中会员ID不能为空", null, TypeConstant.REPLEN_OPEN_DOOR_INVENTORY);
            return;
        }
        // json转换商品对象
        try {
            if (StringUtils.isNotBlank(dataString)) {
                shopLayeredGoods = JSON.parseObject(dataString, ShopLayeredGoods.class);
                logger.info("视觉重力实时盘货：{}", dataString);
            }
        } catch (Exception e) {
            logger.error("处理售货机设备发送过来的开门实时盘货结果，json转换出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.JSON_CONVERT_EXCEPTION.getCode(), ctxMap, "实时盘货结果中商品数据json转换异常", userId, TypeConstant.REPLEN_OPEN_DOOR_INVENTORY);
            return;
        }
        // 获取开门类型 10 购物开门 20 补货员开门
        String openDoorType = shopLayeredGoods.getOpenDoorType();
        try {
            if (NettyConstant.REPLENISHMENT.equals(openDoorType)) {   // 补货开门
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                List<LayeredGoods> layeredGoodsList = shopLayeredGoods.getLayeredGoodsList();
                List<LayeredWeight> layeredWeightList = shopLayeredGoods.getLayeredWeightList();
                Map<String, Object> map = mergelayeredResult(layeredGoodsList, layeredWeightList);
                BigDecimal openWeightTotal = (BigDecimal) iCached.get("layered_device_replen_total_weight_" + deviceId);//开门盘货重量
                BigDecimal realWeightTotal = (BigDecimal) map.get("totalWeight");//实时订单盘货重量
                logger.info("补货实时订单-->开门盘货重量：{}", openWeightTotal);
                logger.info("补货实时订单-->实时盘货重量：{}", realWeightTotal);
                List<LayeredGoods> realGoods = (List<LayeredGoods>) map.get("layeredGoodsList");
                String openMap = (String) iCached.get(NettyConst.LAYERED_CLOUD_DEVICE_REPLEN_OPEN_DOOR_COMMODITYS + deviceInfo.getId());    // 在Redis中记录开门商品
                List<LayeredGoods> openGoods = JSON.parseObject(openMap, new TypeReference<List<LayeredGoods>>() {
                });
                logger.info("补货实时订单-->开门盘货商品：{}", openGoods);
                logger.info("补货实时订单-->实时盘货商品：{}", realGoods);
                List<LayeredReplenGoodModel> layeredReplenGoodModelList = calcLayeredReplenCommodityDiffData(realGoods, openGoods, deviceInfo);
                // 发送消息
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, layeredReplenGoodModelList, userId, TypeConstant.REPLEN_OPEN_DOOR_INVENTORY);
            } else {
                logger.error("创建补货开门实时订单错误，开门类型不能为空");
                MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_EXCEPTION.getCode(), ctxMap, "创建补货开门实时订单错误，开门类型不能为空", userId, TypeConstant.REPLEN_OPEN_DOOR_INVENTORY);
            }
        } catch (Exception e) {
            logger.error("创建开门盘货生成实时订单出现异常:{}", e);
            MsgToJsonUtils.asynSendErrorMsgByCtxMap(deviceId, ErrorCode.REALTIME_ORDER_EXCEPTION.getCode(), ctxMap, "创建补货开门实时订单错误", userId, TypeConstant.REPLEN_OPEN_DOOR_INVENTORY);
        }
    }


    /**
     * 重力视觉柜生成订单
     *
     * @param deviceInfo
     * @param subVoList
     * @param subResult
     * @param subTotal
     * @param type       10:重力减少,视觉减少  20:重力减少,视觉不变或增多
     * @return
     */
    private void geOrder(BigDecimal iele, DeviceInfo deviceInfo, List<CommodityDiffVo> subVoList, BigDecimal subResult,
                         BigDecimal subTotal, Integer type, BigDecimal floatResult, String userId, List<String> visionList) throws Exception {
        BigDecimal diffData = BigDecimal.ZERO;
        Boolean flagTemp = false;//是否仅以重力为主匹配
        logger.info("视觉识别结果：{}", visionList);
        logger.info("购物实时订单-->重力视觉柜生成订单，subResult:" + subResult + "##subTotal：" + subTotal);
        if (type == 10) {
            diffData = subResult.subtract(subTotal);
            if (diffData.compareTo(BigDecimal.ZERO) < 0) {
                diffData = subResult;
                flagTemp = true;
                logger.info("购物实时订单-->重力减少的比视觉减少的还要少");
            }
        } else {
            flagTemp = true;
            diffData = subResult;
        }
        logger.info("购物实时订单-->计算匹配商品重量：{}", diffData);
        List<StockVo> diffStocks = this.selectGravityStockByDeviceId(deviceInfo.getId(), diffData.add(iele));
        logger.info("购物实时订单-->重力视觉柜生成订单，匹配商品:{}", diffStocks);
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
                logger.info("购物实时订单-->库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->在误差范围内,匹配视觉商品出订单");
                String gravityResult = gravityList.get(0);
                String svrCode = gravityResult.split("_")[0];
                subNum = new BigDecimal(gravityResult.split("_")[1]);
                Integer size = new Integer(gravityResult.split("_")[2]);
                logger.info("重力匹配的商品视觉编号为：{}", svrCode);
                logger.info("库库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->重力匹配视觉商品匹配到唯一值,出订单:{}", svrCode);
                StockVo stockVo = diffStocks.get(size);
                subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                        subVoList, subNum);
                //生成订单
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
            } else if (gravityList.size() == 0) {
                if (type == 20) {
                    // 发送消息
                    logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->没有匹配到商品-->视觉未减少,空空如也");
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
                } else {
                    //没有匹配到商品,出异常订单
                    logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->没有匹配到商品,按识别结果出订单:{}");
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                }
            } else {
                //没有匹配或者匹配到多种商品可能,出异常订单
                //库存没有匹配的商品,出异常订单
                //如果视觉为空,且重力可以匹配到多种,选取数量最少的出识别订单
                if (type == 20) {
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
                    logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理且视觉为空,选取数量最少的商品出订单:{}", gravityList);
                    String resultData = gravityList.get(0);
                    subNum = new BigDecimal(resultData.split("_")[1]);
                    Integer size = new Integer(resultData.split("_")[2]);
                    StockVo stockVo = diffStocks.get(size);
                    subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                            subVoList, subNum);
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                    return;
                }
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,视觉不为空优化处理:{}", gravityList);
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
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                } else if (result.size() == 0) {
                    //没有匹配到商品,出异常订单
                    logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->没有满足的:{}", result);
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                } else {
                    Collections.sort(result, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            BigDecimal subNum = new BigDecimal(o1.split("_")[0]);
                            BigDecimal subNumTemp = new BigDecimal(o2.split("_")[0]);
                            if (subNum.compareTo(subNumTemp) > 0) {
                                return 1;
                            } else if (subNum.compareTo(subNumTemp) < 0) {
                                return -1;
                            } else
                                return 0;
                        }
                    });
                    logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->有多个商品满足,选取数量最少商品出实时订单:{}", result);
                    String resultData = result.get(0);
                    subNum = new BigDecimal(resultData.split("_")[0]);
                    Integer size = new Integer(resultData.split("_")[1]);
                    StockVo stockVo = diffStocks.get(size);
                    subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                            subVoList, subNum);
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                }
            }
        } else {
            if (10 == type && !flagTemp) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉有减少（重力减少比视觉大）,以视觉减少为准出订单：{}", subVoList);
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
            } else if (type == 10 && flagTemp) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉有减少,出购物车为空：{}", flagTemp);
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
            } else if (type == 20) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉没减少,出购物车为空：{}", subVoList);
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
            } else {
                //库存没有匹配的商品,出异常订单
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉没减少,出购物车为空");
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
            }
        }
    }

    /**
     * 重力视觉柜分层生成订单
     *
     * @param deviceInfo
     * @param subVoList
     * @param subResult
     * @param subTotal
     * @param type       10:重力减少,视觉减少  20:重力减少,视觉不变或增多
     * @return
     */
    private void layeredGeOrder(BigDecimal iele, DeviceInfo deviceInfo, List<CommodityDiffVo> subVoList, BigDecimal subResult,
                                BigDecimal subTotal, Integer type, BigDecimal floatResult, String userId, List<String> visionList, List<ContrastGoodsModel> contrastGoodsModelList) throws Exception {
        BigDecimal diffData = BigDecimal.ZERO;
        Boolean flagTemp = false;//是否仅以重力为主匹配
        logger.info("视觉识别结果：{}", visionList);
        logger.info("分层购物实时订单-->重力视觉柜生成订单，subResult:" + subResult + "##subTotal：" + subTotal);
        if (type == 10) {
            diffData = subResult.subtract(subTotal);
            if (diffData.compareTo(BigDecimal.ZERO) < 0) {
                diffData = subResult;
                flagTemp = true;
                logger.info("分层购物实时订单-->重力减少的比视觉减少的还要少");
            }
        } else {
            flagTemp = true;
            diffData = subResult;
        }
        logger.info("分层购物实时订单-->计算匹配商品重量：{}", diffData);
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
                    getOrder(diffStocks, diffData, iele, deviceInfo, subVoList, userId, flagTemp, type, visionList, subTotal);
                    return;
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
                    for (int y = x + 1; y < diffStocks.size(); y++) {
                        for (int i = 1; i < subNum.intValue(); i++) {
                            StockVo stockVo = diffStocks.get(x);
                            if (stockVo.getIstock() < i) {
                                break;
                            }
                            //商品重量
                            BigDecimal stockVoiweightl = stockVo.getIweigth().multiply(new BigDecimal(i));
                            if (diffData.subtract(stockVoiweightl).compareTo(BigDecimal.ZERO) < 0 /*&&
                                    diffData.subtract(stockVoiweightl).abs().compareTo(iele.add(stockVo.getIcommodityFloat())) >= 0*/) {
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
                                if (diffData.subtract(stockWeightTotal).abs().compareTo(iele.add(icommodityFloat)) <= 0) {
                                    //确认商品
                                    variedFlag = true;
                                    subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                                            subVoList, new BigDecimal(i));
                                    subVoList = getSubList(stockVo2, deviceInfo, false,
                                            subVoList, new BigDecimal(z));
                                 /*   subVoList = getNewSubList(stockVo2, new BigDecimal(z), deviceInfo, subVoList);*/
                                    //生成订单
                                    logger.info("多商品可以匹配成功" + i + "个" + stockVo + "####" + z + "个" + stockVo2);
                                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                                    return;
                                } else if (diffData.subtract(stockWeightTotal).compareTo(BigDecimal.ZERO) < 0 /*&&
                                        diffData.subtract(stockWeightTotal).abs().compareTo(iele.add(icommodityFloat)) > 0*/) {
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!variedFlag) {
                    logger.info("多商品可以匹配失败,开始匹配单个商品：{}", variedFlag);
                    //未匹配到多种商品组合
                    getOrder(diffStocks, diffData, iele, deviceInfo, subVoList, userId, flagTemp, type, visionList, subTotal);
                    return;
                }
            } else if (type == 20) {
                logger.info("视觉减少为空,开始匹配单个商品：{}", type);
                //未匹配到多种商品组合
                getOrder(diffStocks, diffData, iele, deviceInfo, subVoList, userId, flagTemp, type, visionList, subTotal);
                return;
            }
        } else {
            if (10 == type && !flagTemp) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉有减少（重力减少比视觉大）,以视觉减少为准出订单：{}", subVoList);
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
            } else if (type == 10 && flagTemp) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉有减少,出购物车为空：{}", flagTemp);
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
            } else if (type == 20) {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉没减少,出购物车为空：{}", subVoList);
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
            } else {
                //库存没有匹配的商品,出异常订单
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉没减少,出购物车为空");
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
            }
        }

    }

    /**
     * 重力视觉柜分层生成订单
     *
     * @param deviceInfo
     * @param subVoList
     * @param subResult
     * @param subTotal
     * @param type       10:重力减少,视觉减少  20:重力减少,视觉不变或增多
     * @return
     */
    private void layeredGeOrder2(BigDecimal iele, DeviceInfo deviceInfo, List<CommodityDiffVo> subVoList, BigDecimal subResult,
                                 BigDecimal subTotal, Integer type, BigDecimal floatResult, String userId, List<String> visionList) throws Exception {
        BigDecimal diffData = BigDecimal.ZERO;
        Boolean flagTemp = false;//是否仅以重力为主匹配
        logger.info("视觉识别结果：{}", visionList);
        logger.info("分层购物实时订单-->重力视觉柜生成订单，subResult:" + subResult + "##subTotal：" + subTotal);
        if (type == 10) {
            diffData = subResult.subtract(subTotal);
            if (diffData.compareTo(BigDecimal.ZERO) < 0) {
                diffData = subResult;
                flagTemp = true;
                logger.info("分层购物实时订单-->重力减少的比视觉减少的还要少");
            }
        } else {
            flagTemp = true;
            diffData = subResult;
        }
        Boolean yanzheng = yanzheng(type, deviceInfo, diffData, iele, flagTemp,
                userId, subVoList, subResult, subTotal);
        if (yanzheng) {
            return;
        }
        if (10 == type && !flagTemp) {
            logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品：{}", diffData);
            Boolean matchBoolean = matchSingleGoods(deviceInfo, diffData, iele, subVoList, flagTemp, userId);
            if (matchBoolean) {
                return;
            }
            logger.info("重量减少大于视觉减少,根据重力减少部分匹配商品：{}", subResult);
            matchBoolean = matchSingleGoods(deviceInfo, subResult, iele, subVoList, true, userId);
            if (matchBoolean) {
                return;
            }
            String temp = BizParaUtil.get("recent_month_inventory_goods");
            if (StringUtils.isBlank(temp)) {
                temp = "1";
            }
            Integer month = Integer.valueOf(temp);
     /*       temp = BizParaUtil.get("minimum_weight_percentage");
            if (StringUtils.isBlank(temp)) {
                temp = "80";
            }
            Integer percentage = Integer.valueOf(temp);*/
            List<StockVo> diffStocks = this.selectSellGoodsByDeviceId(deviceInfo.getId(), diffData.add(iele), month);
            logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,查询所有库存商品集合：{}", diffStocks);
            if (diffStocks != null && !diffStocks.isEmpty()) {
                if (diffStocks.size() == 1) {
                    Boolean iisMatching = getOrder2(diffStocks, diffData, iele, deviceInfo, subVoList, userId);
                    if (iisMatching) {
                        return;
                    } else {
                        layeredGeOrder3(iele, deviceInfo, subVoList, subResult, BigDecimal.ZERO, type, floatResult, userId, visionList, flagTemp);
                        return;
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
                        geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                        return;
                    } else {
                        Collections.sort(multiCommodityMatches, new Comparator<MultiCommodityMatch>() {
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
                        subVoList = getSubList(stockVoListTemp.get(0), deviceInfo, false,
                                subVoList, new BigDecimal(str[0]));
                        subVoList = getSubList(stockVoListTemp.get(1), deviceInfo, false,
                                subVoList, new BigDecimal(str[1]));
                        //生成订单
                        logger.info("多商品可以匹配成功" + str[0] + "个" + stockVoListTemp.get(0) + "####" + str[1] + "个" + stockVoListTemp.get(1));
                        geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                        return;
                    }
                } else {
                    //重量未匹配到多种商品组合
                    logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品，多商品匹配不出来,匹配单个：{}", variedFlag);
                    Boolean iisMatching = getOrder2(diffStocks, diffData, iele, deviceInfo, subVoList, userId);
                    logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品，多商品匹配不出来,匹配单个，匹配结果：{}", iisMatching);
                    if (iisMatching) {
                        return;
                    } else {
                        layeredGeOrder3(iele, deviceInfo, subVoList, subResult, BigDecimal.ZERO, type, floatResult, userId, visionList, flagTemp);
                        return;
                    }
                }
            } else {
                layeredGeOrder3(iele, deviceInfo, subVoList, subResult, BigDecimal.ZERO, type, floatResult, userId, visionList, flagTemp);
                return;
            }
        } else if (10 == type && flagTemp) {
            logger.info("重量减少小于视觉减少,根据重力匹配：{}", diffData);
            Boolean matchBoolean = matchSingleGoods(deviceInfo, diffData, iele, subVoList, flagTemp, userId);
            if (matchBoolean) {
                return;
            }
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
                    layeredGeOrder3(iele, deviceInfo, subVoList, subResult, BigDecimal.ZERO, type, floatResult, userId, visionList, flagTemp);
                    return;
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
              /*  for (int i = 0; i < diffStocksFinal.size(); i++) {*/
                    /*StockVo stockVoTe = diffStocksFinal.get(i);*/
                //如果余数小于等于商品的误差值,证明商品重量可以匹配正确,此时商为可匹配商品的数量
                BigDecimal[] divResults = diffData.divideAndRemainder(bigDecimalTemp/*stockVoTe.getIweigth()*/);
                subNum = divResults[0];
                logger.info("视觉减少不为空,开始匹配商品,商品组成订单的个数最多为：{}", subNum);
         /*           break;
                }*/
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
                                        geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                                        return;
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
                    layeredGeOrder3(iele, deviceInfo, subVoList, subResult, BigDecimal.ZERO, type, floatResult, userId, visionList, flagTemp);
                    return;
                }
            } else {
            /*    logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉有减少,出购物车为空：{}", flagTemp);
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);*/
                layeredGeOrder3(iele, deviceInfo, subVoList, subResult, BigDecimal.ZERO, type, floatResult, userId, visionList, flagTemp);
                return;
            }
        } else if (type == 20) {
            logger.info("重量有减少,视觉为空,根据重量减少匹配：{}", diffData);
         /*   List<StockVo> diffStocksTemp = this.selectGravityStockByDeviceId(deviceInfo.getId(), diffData.add(iele));
            List<StockVo> stockVos = new ArrayList<>();
            if (diffStocksTemp != null && !diffStocksTemp.isEmpty()) {
                for (StockVo stockVo : diffStocksTemp) {
                    if (diffData.subtract(stockVo.getIweigth()).abs().compareTo(iele.add(stockVo.getIcommodityFloat())) <= 0) {
                        stockVos.add(stockVo);
                        //确定商品出订单
                    }
                }
            }
            logger.info("重量有减少,视觉为空,根据重量减少匹配,匹配单个商品,可以匹配出商品：{}", stockVos);
            if (!stockVos.isEmpty() && stockVos.size() == 1) {
                StockVo stockVo = stockVos.get(0);
                subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                        subVoList, new BigDecimal(1));
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                logger.info("重量有减少,视觉为空,根据重量减少匹配,匹配单个商品,匹配出商品：{}", stockVo);
                return;
            } else if (!stockVos.isEmpty() && stockVos.size() > 1) {
                Collections.sort(stockVos, new Comparator<StockVo>() {
                    @Override
                    public int compare(StockVo o1, StockVo o2) {
                        int temp1 = o1.getIstock();
                        int temp2 = o2.getIstock();
                        if (temp1 > temp2) {
                            return -1;
                        } else if (temp1 < temp2) {
                            return 1;
                        }
                        return 0;
                    }
                });
                StockVo stockVo = stockVos.get(0);
                subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                        subVoList, new BigDecimal(1));
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                logger.info("重量有减少,视觉为空,根据重量减少匹配,匹配单个商品,匹配出商品：{}", stockVo);
                return;
            }*/
            Boolean matchBoolean = matchSingleGoods(deviceInfo, diffData, iele, subVoList, flagTemp, userId);
            if (matchBoolean) {
                return;
            }
            layeredGeOrder3(iele, deviceInfo, subVoList, subResult, subTotal, type, floatResult, userId, visionList, flagTemp);
            return;
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
    private void layeredGeOrder3(BigDecimal iele, DeviceInfo deviceInfo, List<CommodityDiffVo> subVoList, BigDecimal subResult,
                                 BigDecimal subTotal, Integer type, BigDecimal floatResult, String userId, List<String> visionList, Boolean flagTemp) throws Exception {

        logger.info("#########开始匹配单个商品####:{}", type);
        String temp = BizParaUtil.get("recent_month_inventory_goods");
        if (StringUtils.isBlank(temp)) {
            temp = "1";
        }
        Integer month = Integer.valueOf(temp);
  /*      temp = BizParaUtil.get("minimum_weight_percentage");
        if (StringUtils.isBlank(temp)) {
            temp = "80";
        }
        Integer percentage = Integer.valueOf(temp);*/
        List<StockVo> diffStocks = this.selectSellGoodsByDeviceId(deviceInfo.getId(), subResult.add(iele), month);
        logger.info("#########开始匹配单个商品,查询商品集合####:{}", diffStocks);
        if (diffStocks != null && !diffStocks.isEmpty()) {
      /*       StockVo te = diffStocks.get(0);
           if (type == 10 && flagTemp) {
                BigDecimal diffTemp = subTotal.subtract(subResult);
                if (te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(diffTemp).compareTo(BigDecimal.ZERO) >= 0) {
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                    return;
                }
                subTotal = BigDecimal.ZERO;
            }*/
 /*           if (te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(subResult).compareTo(BigDecimal.ZERO) >= 0) {
                if (type == 10) {
                    logger.info("#########开始匹配单个商品,变化值未查询出来,出视觉结果####:{}", subVoList);
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                    return;
                } else if (20 == type) {
                    logger.info("#########开始匹配单个商品,变化值未查询出来,没有视觉变化,购物车空空如也####:{}", type);
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
                    return;
                }
            } else if (subResult.subtract(te.getIweigth()).abs().compareTo(te.getIcommodityFloat().add(iele)) <= 0 *//*&& te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(subResult).compareTo(BigDecimal.ZERO) < 0*//*) {

                //确定就是这个商品
                //出订单
                logger.info("#########开始匹配单个商品,变化值查询出来,根据最小商品出订单####:{}", type);
                subVoList = getSubList(te, deviceInfo, true,
                        subVoList, new BigDecimal(1));
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                return;
            }*/
            if (diffStocks.size() == 1) {
                logger.info("#########开始匹配单个商品,变化值查询出来只有一个,根据这个商品出订单####:{}", diffStocks);
                getOrder(diffStocks, subResult, iele, deviceInfo, subVoList, userId, true, type, visionList, subTotal);
                return;
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
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                    return;
                } else {
                    Collections.sort(multiCommodityMatches, new Comparator<MultiCommodityMatch>() {
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
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                    return;
                }
            } else/* if (!variedFlag) */ {
                //重量未匹配到多种商品组合,匹配单种
             /*   if (type == 10 && !flagTemp) {
                    getOrder(diffStocks, subResult, iele, deviceInfo, subVoList, userId, true, type, visionList, subTotal);
                    return;
                } else if (type == 10 && flagTemp) {
                    getOrder(diffStocks, subResult, iele, deviceInfo, subVoList, userId, true, type, visionList, subTotal);
                    return;
                } else if (type == 20) {
                    getOrder(diffStocks, subResult, iele, deviceInfo, subVoList, userId, true, type, visionList, subTotal);
                    return;
                }*/
                getOrder(diffStocks, subResult, iele, deviceInfo, subVoList, userId, true, type, visionList, subTotal);
                return;
            }
        } else {
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
        }
    }

    /**
     * 重力视觉柜分层生成订单
     *
     * @param deviceInfo
     * @param subResult
     * @return
     */
 /*   private void layeredGeOrderDelWe(BigDecimal iele, DeviceInfo deviceInfo, BigDecimal subResult , String userId) throws Exception {
        BigDecimal diffData = BigDecimal.ZERO;
        Boolean flagTemp = false;//是否仅以重力为主匹配
        logger.info("分层购物实时订单-->重力视觉柜生成订单，subResult:" + subResult );
        logger.info("分层购物实时订单-->计算匹配商品重量：{}", diffData);
        List<StockVo> diffStocks = this.selectGravityStockByDeviceId(deviceInfo.getId(), diffData.add(iele));
        logger.info("分层购物实时订单-->重力视觉柜生成订单，重量匹配商品:{}", diffStocks);
        if (null != diffStocks && !diffStocks.isEmpty()) {
                //视觉有减少,遍历出在视觉变化里的商品集合
                logger.info("重量匹配商品对比视觉减少的商品：{}", diffStocks);
                if (diffStocks.isEmpty() || diffStocks.size() == 1) {
                 //   getOrder(diffStocks, diffData, iele, deviceInfo, userId, flagTemp);
                    return;
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
                    for (int y = x + 1; y < diffStocks.size(); y++) {
                        for (int i = 1; i < subNum.intValue(); i++) {
                            StockVo stockVo = diffStocks.get(x);
                            if (stockVo.getIstock() < i) {
                                break;
                            }
                            //商品重量
                            BigDecimal stockVoiweightl = stockVo.getIweigth().multiply(new BigDecimal(i));
                            if (diffData.subtract(stockVoiweightl).compareTo(BigDecimal.ZERO) < 0 *//*&&
                                    diffData.subtract(stockVoiweightl).abs().compareTo(iele.add(stockVo.getIcommodityFloat())) >= 0*//*) {
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
                                if (diffData.subtract(stockWeightTotal).abs().compareTo(iele.add(icommodityFloat)) <= 0) {
                                    //确认商品
                                    variedFlag = true;
                                    subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                                            subVoList, new BigDecimal(i));
                                    subVoList = getSubList(stockVo2, deviceInfo, false,
                                            subVoList, new BigDecimal(z));
                                 *//*   subVoList = getNewSubList(stockVo2, new BigDecimal(z), deviceInfo, subVoList);*//*
                                    //生成订单
                                    logger.info("多商品可以匹配成功" + i + "个" + stockVo + "####" + z + "个" + stockVo2);
                                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                                    return;
                                } else if (diffData.subtract(stockWeightTotal).compareTo(BigDecimal.ZERO) < 0 *//*&&
                                        diffData.subtract(stockWeightTotal).abs().compareTo(iele.add(icommodityFloat)) > 0*//*) {
                                    break;
                                }
                            }
                        }
                    }
                if (!variedFlag) {
                    logger.info("多商品可以匹配失败,开始匹配单个商品：{}", variedFlag);
                    //未匹配到多种商品组合
                    getOrder(diffStocks, diffData, iele, deviceInfo, subVoList, userId, flagTemp, type, visionList, subTotal);
                    return;
                }
            }
        } else {
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->未匹配到商品且视觉有减少（重力减少比视觉大）,以视觉减少为准出订单：{}", subVoList);
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
        }

    }*/


    /**
     * @param diffStocks
     * @param diffData
     * @param iele
     * @param deviceInfo
     * @param subVoList
     * @param userId
     * @param flagTemp
     * @param type
     * @param visionList
     * @throws Exception
     */

    private void getOrder(List<StockVo> diffStocks, BigDecimal diffData, BigDecimal iele, DeviceInfo deviceInfo, List<CommodityDiffVo> subVoList
            , String userId, boolean flagTemp, Integer type, List visionList, BigDecimal subTotal) throws Exception {
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
                /*    if (subNum.intValue() > stockVo.getIstock()) {
                        continue;
                    }*/
                    subNum = divResults[0];
                }
                gravityList.add(stockVo.getSvrCode() + "_" + subNum + "_" + i);
            } else if (divResults[0] != BigDecimal.ZERO) {
                BigDecimal temp = diffData.subtract(divResults[0].add(BigDecimal.ONE).multiply(stockVo.getIweigth()));
                if (temp.abs().compareTo(icommodityFloat.add(iele)) <= 0) {
                    subNum = divResults[0].add(BigDecimal.ONE);
                /*    if (subNum.intValue() > stockVo.getIstock()) {
                        continue;
                    }*/
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
            geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
        } else if (gravityList.size() == 0) {
            if (type == 20) {
                // 发送消息
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->没有匹配到商品-->视觉未减少,空空如也");
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
            } else {
                //没有匹配到商品,出异常订单
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->没有匹配到商品,按识别结果出订单:{}");
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
            }
        } else {
            //没有匹配或者匹配到多种商品可能,出异常订单
            //库存没有匹配的商品,出异常订单
            //如果视觉为空,且重力可以匹配到多种,选取数量最少的出识别订单
            if (type == 20) {
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
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理且视觉为空,选取数量最少的商品出订单:{}", gravityList);
                String resultData = gravityList.get(0);
                subNum = new BigDecimal(resultData.split("_")[1]);
                Integer size = new Integer(resultData.split("_")[2]);
                StockVo stockVo = diffStocks.get(size);
                subVoList = getSubList(stockVo, deviceInfo, true,
                        subVoList, subNum);
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                return;
            }
            logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,视觉不为空优化处理:{}", gravityList);
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
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
            } else if (result.size() == 0) {
                //没有匹配到商品,在匹配视觉外的商品
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
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理且视觉为空,选取数量最少的商品出订单:{}", gravityList);
                String resultData = gravityList.get(0);
                subNum = new BigDecimal(resultData.split("_")[1]);
                Integer size = new Integer(resultData.split("_")[2]);
                StockVo stockVo = diffStocks.get(size);
                subVoList = getSubList(stockVo, deviceInfo, true,
                        subVoList, subNum);
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                return;
              /*  logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->没有满足的:{}", result);
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());*/
            } else {
                Collections.sort(result, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        BigDecimal subNum = new BigDecimal(o1.split("_")[0]);
                        BigDecimal subNumTemp = new BigDecimal(o2.split("_")[0]);
                        if (subNum.compareTo(subNumTemp) > 0) {
                            return 1;
                        } else if (subNum.compareTo(subNumTemp) < 0) {
                            return -1;
                        } else
                            return 0;
                    }
                });
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->有多个商品满足,选取数量最少商品出实时订单:{}", result);
                String resultData = result.get(0);
                subNum = new BigDecimal(resultData.split("_")[0]);
                Integer size = new Integer(resultData.split("_")[1]);
                StockVo stockVo = diffStocks.get(size);
                subVoList = getSubList(stockVo, deviceInfo, true,
                        subVoList, subNum);
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
            }
        }
    }

    /**
     * @param diffStocks
     * @param diffData
     * @param iele
     * @param deviceInfo
     * @param subVoList
     * @param userId
     * @throws Exception
     */

    private Boolean getOrder2(List<StockVo> diffStocks, BigDecimal diffData, BigDecimal iele, DeviceInfo deviceInfo, List<CommodityDiffVo> subVoList, String userId) throws Exception {
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
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
            }
            return iisMatching;
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
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                iisMatching = true;
            } else if (gravityList.size() == 0) {
                //没有匹配到
            } else {
                //没有匹配或者匹配到多种商品可能,出异常订单
                //库存没有匹配的商品,出异常订单
                //如果视觉为空,且重力可以匹配到多种,选取数量最少的出识别订单
                logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,视觉不为空优化处理:{}", gravityList);
               /* List<String> result = new ArrayList<>();
                for (int x = 0; x < gravityList.size(); x++) {
                    String gravityResult = gravityList.get(x);
                    String svrCode = gravityResult.split("_")[0];
                    subNum = new BigDecimal(gravityResult.split("_")[1]);
                    Integer size = new Integer(gravityResult.split("_")[2]);
                }
                if (result.size() == 1) {
                    logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->只有一个满足:{}", result);
                    String resultData = result.get(0);
                    subNum = new BigDecimal(resultData.split("_")[0]);
                    Integer size = new Integer(resultData.split("_")[1]);
                    StockVo stockVo = diffStocks.get(size);
                    subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                            subVoList, subNum);
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                } else if (result.size() == 0) {
                    //没有匹配到商品,出异常订单
                    logger.info("库存重力大于盘货重力--> 对比视觉和库存重量的变化--->差异巨大,匹配商品-->匹配到多种商品,优化处理-->没有满足的:{}", result);
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                } else {*/
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
                geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                iisMatching = true;
               /* }*/
            }
            return iisMatching;
        }
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
        logger.info("实时订单生成订单：{}", stockVo);
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
                logger.info("差异巨大出订单,重力减少的比视觉减少的多,可以匹配到商品,在原有订单上修改-->原视觉为空！");
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
               /* synchronized (subVoList) {
                    int size = 0;
                    CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList(subVoList);
                    ListIterator<CommodityDiffVo> commodityDiffVoListIterator = copyOnWriteArrayList.listIterator();
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
                   /* for (CommodityDiffVo commodityDiffVo : subVoList) {
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
        }
        return subVoList;
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

    /**
     * 重力视觉柜生成订单
     *
     * @return
     */
    private void geOrder(String userId, String deviceCode,
                         List<CommodityDiffVo> subVoList, String deviceId) throws Exception {
        String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);
        OrderModel orderModel = new OrderModel();
        //出识别订单
        Collections.sort(subVoList, new Comparator<CommodityDiffVo>() {
            @Override
            public int compare(CommodityDiffVo o1, CommodityDiffVo o2) {
                String vrCode1 = o1.getSvrCode();
                String vrCode2 = o2.getSvrCode();
                if (vrCode1.compareTo(vrCode2) > 0) {
                    return 1;
                }
                if (vrCode1.compareTo(vrCode2) < 0) {
                    return -1;
                }
                if (vrCode1.compareTo(vrCode2) == 0) {
                    return 0;
                }
                return 0;
            }
        });
        RealTimeOrderResult realTimeOrderResult = calculationResult(subVoList, userId, deviceCode);
        logger.debug("商品数量对比库存有变化，调用购物开门实时盘货服务有订单生成");
        List<GoodModel> goodModelList = new ArrayList<>();
        GoodModel goodModel = null;
        Integer amountNumber = new Integer("0");    // 初始值
        for (RealTimeCommodityResult result : realTimeOrderResult.getResults()) {
            goodModel = new GoodModel();
            goodModel.setGoodsNumber(result.getNumber());                           // 商品数量
            goodModel.setGoodName(result.getScommodityFullName());                      // 商品名称
            goodModel.setGoodPrice(result.getFcommodityPrice().toString());
            if (StringUtils.isNotBlank(result.getScommodityImg())) {
                goodModel.setGoodImgUrl(preUrl + result.getScommodityImg()); // 商品图片地址
            }
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
        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, orderModel, userId, TypeConstant.OPEN_DOOR_INVENTORY);
    }


    /**
     * 开关门对比实时订单
     *
     * @param openDoorMap
     * @param goodsList
     * @param deviceInfo
     * @return
     */
    private Map<String, Object> calcCommodityDiffData(Map<String, Integer> openDoorMap, List<TagModel> goodsList, DeviceInfo deviceInfo) {
        logger.info("重力视觉柜计算开关门商品差异：{}", deviceInfo.getScode());
        Map<String, Integer> closeDoorMap = new HashMap<>();  // 临时存放从云端返回的图片中商品集合
        // 遍历云端返回response
        if (CollectionUtils.isNotEmpty(goodsList)) {
            for (TagModel good : goodsList) {
                String vrCode = good.getSvrCode();       // 商品视觉编号
                Integer num = good.getNumber();          // 商品数量
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
            if (resultNum > 0) {//商品减少
                tempDiffVo = assemblyVisionTemp(resultNum, openId, deviceInfo, closeNum);
                if (null == tempDiffVo) {
                    continue;
                }
                subVoList.add(tempDiffVo);
            } else {
                continue;
            }

        }
        map.put("subVoList", subVoList);
        map.put("addVoList", addVoList);
        return map;
    }

    /**
     * 开门对比实时订单
     *
     * @param realGoods
     * @param openGoods
     * @param deviceInfo
     * @return
     */
    private List<ContrastGoodsModel> calcLayeredCommodityDiffData(List<LayeredGoods> realGoods, List<LayeredGoods> openGoods, DeviceInfo deviceInfo) {
        logger.info("重力视觉柜分层计算开关门商品差异：{}", deviceInfo.getScode());
        // 遍历云端返回response
        // 开关门前后都有商品，计算增加与减少的商品信息
        ContrastGoodsModel contrastGoodsModel = null;
        String temp = BizParaUtil.get("weight_layered_warm");
        BigDecimal weightLayeredWarm = new BigDecimal(temp);
        List<ContrastGoodsModel> contrastGoodsModelList = new ArrayList<>();
        for (int x = 0; x < realGoods.size(); x++) {
            LayeredGoods layeredGoods = realGoods.get(x);
            LayeredGoods openLayeredGoods = openGoods.get(x);
            List<TagModel> realModelList = layeredGoods.getGoodsList();
            List<TagModel> openModelList = openLayeredGoods.getGoodsList();
            Map<String, Integer> realGoodsMap = getGoodsMap(realModelList);
            Map<String, Integer> openDoorMap = getGoodsMap(openModelList);
            Map<String, Object> map = new HashMap<String, Object>();
            List<CommodityDiffVo> subVoList = new ArrayList<CommodityDiffVo>();
            List<CommodityDiffVo> addVoList = new ArrayList<CommodityDiffVo>();
            BigDecimal diffValue = layeredGoods.getLayeredWeight().subtract(openLayeredGoods.getLayeredWeight());
      /*      if (diffValue.abs().compareTo(deviceInfo.getIelectronicFloat().add(weightLayeredWarm)) <= 0) {
                //什么都没拿
                contrastGoodsModel = new ContrastGoodsModel();
                contrastGoodsModel.setCameraIp(layeredGoods.getCameraIp());
                contrastGoodsModel.setRealWeight(layeredGoods.getLayeredWeight());
                contrastGoodsModel.setOpenWeight(openLayeredGoods.getLayeredWeight());
                contrastGoodsModel.setIisShop(false);
                contrastGoodsModelList.add(contrastGoodsModel);
                logger.info("在误差范围内,商品没有变化");
                continue;
            }*/
            for (Map.Entry<String, Integer> openDoor : openDoorMap.entrySet()) {
                CommodityDiffVo tempDiffVo = null;
                String openId = openDoor.getKey();
                DeviceStock deviceStock = deviceStockService.selectDeviceStockByVrCode(deviceInfo.getId(), openId);
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
                DeviceStock deviceStock = deviceStockService.selectDeviceStockByVrCode(deviceInfo.getId(), closeId);
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
           /* if (subVoList.isEmpty() && addVoList.isEmpty()) {
                if (diffValue.compareTo(BigDecimal.ZERO) < 0) {
                    //重量变少，但视觉没有增加和减少,重量减少的部分匹配商品
                    BigDecimal diffData = diffValue.abs();
                    subVoList = getResult(deviceInfo, diffData, subVoList, null, 20);
                } else if (diffValue.compareTo(BigDecimal.ZERO) > 0) {
                    addVoList = getResult(deviceInfo, diffValue, addVoList, null, 10);
                }
            } else if (subVoList.isEmpty() && !addVoList.isEmpty()) {
                //视觉有增加
                //1.增加部分为误识别（几率较小）
                //2.有减少部分未识别到
                BigDecimal addData = BigDecimal.ZERO;
                BigDecimal addfloatResult = BigDecimal.ZERO;
                List addVisionList = new ArrayList();
                for (CommodityDiffVo commodityDiffVo : addVoList) {
                    BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                    addData = addData.add(multiplyData);
                    addfloatResult = addfloatResult.add(commodityDiffVo.getIcommodityFloat());
                    addVisionList.add(commodityDiffVo.getSvrCode());
                }
                BigDecimal iele = deviceInfo.getIelectronicFloat();
                //视觉变多，且开门重力变大,匹配是否是这个商品
                if (diffValue.compareTo(BigDecimal.ZERO) > 0) {
                    //视觉增多重力有增多 (重力变多的部分大于商品重量最小值并且小于商品重量最大值)
                   *//* if (diffValue.compareTo(addData.subtract(iele).subtract(addfloatResult)) >= 0 && diffValue.compareTo(addData.add(iele).add(addfloatResult)) <= 0) {*//*
                    if (diffValue.subtract(addData).abs().compareTo(addfloatResult.add(iele)) <= 0) {
                        //在误差范围内,就是增加的这个商品
                    } else {
                        //匹配增加的商品
                        addVoList = getResult(deviceInfo, diffValue, addVoList, addVisionList, 10);
                    }
                } else if (diffValue.compareTo(BigDecimal.ZERO) < 0) {
                    //视觉增多重力有减少
                    //1.有减少部分未识别
                    //2.增多部分为误识别,减少部分未识别到(几率较小)
                    subVoList = getResult(deviceInfo, diffValue.abs().add(addData), subVoList, null, 20);
                }
            } else if (!subVoList.isEmpty() && addVoList.isEmpty()) {
                //视觉有减少
                // 重力在误差范围内-->求出增加的部分
                // 重力在误差范围外-->求出增加的部分
                BigDecimal subData = BigDecimal.ZERO;
                BigDecimal subfloatResult = BigDecimal.ZERO;
                List subVisionList = new ArrayList();
                for (CommodityDiffVo commodityDiffVo : addVoList) {
                    BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                    subData = subData.add(multiplyData);
                    subfloatResult = subfloatResult.add(commodityDiffVo.getIcommodityFloat());
                    subVisionList.add(commodityDiffVo.getSvrCode());
                }
                BigDecimal iele = deviceInfo.getIelectronicFloat();
                //视觉变多，且开门重力变大,匹配是否是这个商品
                if (diffValue.compareTo(BigDecimal.ZERO) < 0) {
                    //视觉减少重力有减少
                    if ((diffValue.abs().subtract(subData)).abs().compareTo(subfloatResult.add(iele)) <= 0) {
                        //在误差范围内,就是减少的这个商品
                    } else {
                        //匹配减少的商品
                        subVoList = getResult(deviceInfo, diffValue.abs(), subVoList, subVisionList, 20);
                    }
                } else if (diffValue.compareTo(BigDecimal.ZERO) > 0) {
                    //视觉减少重力有增多
                    //1.有增多部分未识别
                    //2.减少部分为误识别,增多部分未识别到(几率较小)
                    addVoList = getResult(deviceInfo, diffValue.add(subData), addVoList, null, 10);
                }
            } else if (!addVoList.isEmpty() && !subVoList.isEmpty()) {
                BigDecimal iele = deviceInfo.getIelectronicFloat();
                BigDecimal addData = BigDecimal.ZERO;
                BigDecimal addfloatResult = BigDecimal.ZERO;
                List addVisionList = new ArrayList();
                for (CommodityDiffVo commodityDiffVo : addVoList) {
                    BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                    addData = addData.add(multiplyData);
                    addfloatResult = addfloatResult.add(commodityDiffVo.getIcommodityFloat());
                    addVisionList.add(commodityDiffVo.getSvrCode());
                }
                BigDecimal subData = BigDecimal.ZERO;
                BigDecimal subfloatResult = BigDecimal.ZERO;
                List subVisionList = new ArrayList();
                for (CommodityDiffVo commodityDiffVo : addVoList) {
                    BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                    subData = subData.add(multiplyData);
                    subfloatResult = subfloatResult.add(commodityDiffVo.getIcommodityFloat());
                    subVisionList.add(commodityDiffVo.getSvrCode());
                }
                if (diffValue.compareTo(BigDecimal.ZERO) > 0) {
                    if (diffValue.compareTo(addData.add(addfloatResult).subtract(subData.subtract(subfloatResult)).add(iele)) <= 0
                            && diffValue.compareTo(addData.subtract(addfloatResult).subtract(subData.add(subfloatResult)).subtract(iele)) >= 0) {
                        //重力增加且视觉增加视觉减少的在重力误差范围内
                    } else {
                        //重力增加且视觉增加视觉减少的在重力误差范围外,
                        if (subData.compareTo(addData) > 0) {
                            //视觉减少的比视觉增多的多
                            //1.视觉减少的误识别(视觉减少的商品识别多了)
                            //2.视觉增多的误识别(视觉增多的商品识别少了（或有其他商品新增未识别 不考虑）)
                            if (subVoList.size() == 1) {
                                if (diffValue.compareTo(iele.add(addData.add(addfloatResult))) <= 0) {
                                    //减少部分为误识别 去除subList
                                    subVoList = new ArrayList<>();
                                }
                            } else {

                            }
                        } else if (subData.compareTo(addData) < 0) {

                        }
                    }
                } else if (diffValue.compareTo(BigDecimal.ZERO) < 0) {
                    if (diffValue.abs().compareTo(subData.add(subfloatResult).subtract(addData.subtract(addfloatResult)).add(iele)) <= 0
                            && diffValue.abs().compareTo(subData.subtract(subfloatResult).subtract(addData.subtract(addfloatResult)).subtract(iele)) >= 0) {
                        //重力增加且视觉增加视觉减少的在重力误差范围内
                    } else {

                    }
                }
            }*/
            if (subVoList.isEmpty() && addVoList.isEmpty() && diffValue.abs().compareTo(deviceInfo.getIelectronicFloat().add(weightLayeredWarm)) <= 0) {
                contrastGoodsModel = new ContrastGoodsModel();
                contrastGoodsModel.setCameraIp(layeredGoods.getCameraIp());
                contrastGoodsModel.setRealWeight(layeredGoods.getLayeredWeight());
                contrastGoodsModel.setOpenWeight(openLayeredGoods.getLayeredWeight());
                contrastGoodsModel.setIisShop(false);
                contrastGoodsModelList.add(contrastGoodsModel);
                logger.info("在误差范围内,商品没有变化");
                continue;
            }
              /*  if (diffValue.abs().compareTo(deviceInfo.getIelectronicFloat().add(weightLayeredWarm)) <= 0) {
                    //什么都没拿
                    contrastGoodsModel = new ContrastGoodsModel();
                    contrastGoodsModel.setCameraIp(layeredGoods.getCameraIp());
                    contrastGoodsModel.setRealWeight(layeredGoods.getLayeredWeight());
                    contrastGoodsModel.setOpenWeight(openLayeredGoods.getLayeredWeight());
                    contrastGoodsModel.setIisShop(false);
                    contrastGoodsModelList.add(contrastGoodsModel);
                    logger.info("在误差范围内,商品没有变化");
                    continue;
                }*/
            map.put("subVoList", subVoList);
            map.put("addVoList", addVoList);
            contrastGoodsModel = new ContrastGoodsModel();
            contrastGoodsModel.setCameraIp(layeredGoods.getCameraIp());
            contrastGoodsModel.setContrastMap(map);
            contrastGoodsModel.setIisShop(true);
            contrastGoodsModel.setRealWeight(layeredGoods.getLayeredWeight());
            contrastGoodsModel.setOpenWeight(openGoods.get(x).getLayeredWeight());
            contrastGoodsModelList.add(contrastGoodsModel);
        }
        return contrastGoodsModelList;
    }

    /**
     * 补货实时订单
     *
     * @param realGoods
     * @param openGoods
     * @param deviceInfo
     * @return
     */
    private List<LayeredReplenGoodModel> calcLayeredReplenCommodityDiffData(List<LayeredGoods> realGoods, List<LayeredGoods> openGoods, DeviceInfo deviceInfo) {
        logger.info("重力视觉柜分层补货计算开关门商品差异：{}", deviceInfo.getScode());
        // 遍历云端返回response
        // 开关门前后都有商品，计算增加与减少的商品信息
        String temp = BizParaUtil.get("weight_layered_warm");
        if (StringUtils.isBlank(temp)) {
            temp = "15";
        }
        BigDecimal weightLayeredWarm = new BigDecimal(temp);
        LayeredReplenGoods layeredReplenGoods = null;
        List<LayeredReplenGoods> layeredReplenGoodsList = null;
        LayeredReplenGoodModel layeredReplenGoodModel = null;
        List<LayeredReplenGoodModel> layeredReplenGoodModels = new ArrayList<>();
        for (int x = 0; x < realGoods.size(); x++) {
            layeredReplenGoodsList = new ArrayList<>();
            LayeredGoods layeredGoods = realGoods.get(x);
            LayeredGoods openLayeredGoods = openGoods.get(x);
            List<TagModel> realModelList = layeredGoods.getGoodsList();
            List<TagModel> openModelList = openLayeredGoods.getGoodsList();
            Map<String, Integer> realGoodsMap = getGoodsMap(realModelList);
            Map<String, Integer> openDoorMap = getGoodsMap(openModelList);
            List<CommodityDiffVo> subVoList = new ArrayList<CommodityDiffVo>();
            List<CommodityDiffVo> addVoList = new ArrayList<CommodityDiffVo>();
            BigDecimal diffValue = layeredGoods.getLayeredWeight().subtract(openLayeredGoods.getLayeredWeight());
            logger.info("分层补货实时订单,实时与开门重量差：{}", diffValue);
            for (Map.Entry<String, Integer> openDoor : openDoorMap.entrySet()) {
                CommodityDiffVo tempDiffVo = null;
                String openId = openDoor.getKey();
                Integer openNum = openDoor.getValue() != null ? openDoor.getValue() : 0;
                Integer closeNum = 0;
                if (realGoodsMap.containsKey(openId)) {//如果存在开门时的商品
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
                layeredReplenGoods = new LayeredReplenGoods();
                CommodityInfo commodityInfo = commodityInfoService.selectByVrCode(openId, deviceInfo.getSmerchantId());
                layeredReplenGoods.setGoodsOriginalNumber(openNum);
                layeredReplenGoods.setGoodsCurrentNumber(closeNum);
                Integer numTemp = closeNum - openNum;
                String numDiff = "";
                if (numTemp >= 0) {
                    numDiff = "+" + numTemp;
                } else {
                    numDiff = String.valueOf(numTemp);
                }
                layeredReplenGoods.setGoodsNumberDiff("(" + numDiff + ")");
                layeredReplenGoods.setGoodImgUrl(commodityInfo.getScommodityImg());
                layeredReplenGoods.setGoodName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit() + "(" + commodityInfo.getIweigth() + commodityInfo.getSspecUnit() + ")");
                layeredReplenGoodsList.add(layeredReplenGoods);
            }
            for (Map.Entry<String, Integer> closeDoor : realGoodsMap.entrySet()) {
                String closeId = closeDoor.getKey();
                Integer closeNum = closeDoor.getValue() != null ? closeDoor.getValue() : 0;
                if (!openDoorMap.containsKey(closeId)) {//如果关门不存在开门时的商品 新增
                    CommodityDiffVo tempDiffVo = assemblyVisionTemp(-closeNum, closeId, deviceInfo, closeNum);
                    if (null == tempDiffVo) {
                        continue;
                    }
                    addVoList.add(tempDiffVo);
                    layeredReplenGoods = new LayeredReplenGoods();
                    CommodityInfo commodityInfo = commodityInfoService.selectByVrCode(closeId, deviceInfo.getSmerchantId());
                    layeredReplenGoods.setGoodsOriginalNumber(0);
                    layeredReplenGoods.setGoodsCurrentNumber(closeNum);
                    layeredReplenGoods.setGoodsNumberDiff("(+" + String.valueOf(closeNum) + ")");
                    layeredReplenGoods.setGoodImgUrl(commodityInfo.getScommodityImg());
                    layeredReplenGoods.setGoodName(commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit() + "(" + commodityInfo.getIweigth() + commodityInfo.getSspecUnit() + ")");
                    layeredReplenGoodsList.add(layeredReplenGoods);
                }
            }
            layeredReplenGoodModel = new LayeredReplenGoodModel();
            if (addVoList.isEmpty() && subVoList.isEmpty()) {
                if (diffValue.abs().compareTo(deviceInfo.getIelectronicFloat().add(weightLayeredWarm)) <= 0) {
                    layeredReplenGoodModel.setIisReplenCorrect(true);
                }
            } else {
                //只有减少的(下架的)
                if (!subVoList.isEmpty() && addVoList.isEmpty()) {
                    BigDecimal subTotal = BigDecimal.ZERO;
                    BigDecimal subFloatResult = BigDecimal.ZERO;
                    for (CommodityDiffVo commodityDiffVo : subVoList) {
                        BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                        subTotal = subTotal.add(multiplyData);
                        subFloatResult = subFloatResult.add(commodityDiffVo.getIcommodityFloat());
                    }
                    if (subTotal.subtract(diffValue.abs()).abs().compareTo(deviceInfo.getIelectronicFloat().add(subFloatResult)) <= 0) {
                        //在误差范围内
                        layeredReplenGoodModel.setIisReplenCorrect(true);
                    }
                    //只有增加的
                } else if (subVoList.isEmpty() && !addVoList.isEmpty()) {
                    BigDecimal addTotal = BigDecimal.ZERO;
                    BigDecimal addFloatResult = BigDecimal.ZERO;
                    if (!addVoList.isEmpty()) {
                        for (CommodityDiffVo commodityDiffVo : addVoList) {
                            BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                            addTotal = addTotal.add(multiplyData);
                            addFloatResult = addFloatResult.add(commodityDiffVo.getIcommodityFloat());
                        }
                    }
                    if (addTotal.subtract(diffValue).abs().compareTo(deviceInfo.getIelectronicFloat().add(addFloatResult)) <= 0) {
                        //在误差范围内
                        layeredReplenGoodModel.setIisReplenCorrect(true);
                    }
                } else if (!subVoList.isEmpty() && !addVoList.isEmpty()) {
                    BigDecimal subTotal = BigDecimal.ZERO;
                    BigDecimal subFloatResult = BigDecimal.ZERO;
                    for (CommodityDiffVo commodityDiffVo : subVoList) {
                        BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                        subTotal = subTotal.add(multiplyData);
                        subFloatResult = subFloatResult.add(commodityDiffVo.getIcommodityFloat());
                    }
                    BigDecimal addTotal = BigDecimal.ZERO;
                    BigDecimal addFloatResult = BigDecimal.ZERO;
                    if (!addVoList.isEmpty()) {
                        for (CommodityDiffVo commodityDiffVo : addVoList) {
                            BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                            addTotal = addTotal.add(multiplyData);
                            addFloatResult = addFloatResult.add(commodityDiffVo.getIcommodityFloat());
                        }
                    }
                    if (addTotal.subtract(subTotal).subtract(diffValue).abs().compareTo(deviceInfo.getIelectronicFloat().add(addFloatResult).add(subFloatResult)) <= 0) {
                        //在误差范围内
                        layeredReplenGoodModel.setIisReplenCorrect(true);
                    }
                }
            }
            layeredReplenGoodModel.setIisReplenCorrect(false);
            layeredReplenGoodModel.setWeightDiff(diffValue + "g");
            layeredReplenGoodModel.setLayeredReplenGoods(layeredReplenGoodsList);
            layeredReplenGoodModels.add(layeredReplenGoodModel);
        }
        return layeredReplenGoodModels;
    }

    /**
     * @param tagModelList
     * @return
     */
    private Map<String, Integer> getGoodsMap(List<TagModel> tagModelList) {
        Map<String, Integer> goodsMap = new HashMap<>();  // 临时存放从云端返回的图片中商品集合
        // 遍历云端返回response
        if (CollectionUtils.isNotEmpty(tagModelList)) {
            for (TagModel good : tagModelList) {
                String vrCode = good.getSvrCode();       // 商品视觉编号
                Integer num = good.getNumber();          // 商品数量
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

    /**
     * @param deviceInfo
     * @param diffData
     * @param commodityDiffVoList
     * @param visionList
     * @param type                10 增加 20 减少
     * @return
     */
    private List<CommodityDiffVo> getResult(DeviceInfo deviceInfo, BigDecimal
            diffData, List<CommodityDiffVo> commodityDiffVoList, List<String> visionList, Integer type) {
        BigDecimal iele = deviceInfo.getIelectronicFloat();
        List<StockVo> diffStocks = this.selectGravityStockByDeviceId(deviceInfo.getId(), diffData.add(iele));
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
            if (null == visionList) {
                if (gravityList.size() == 1) {
                    //减少的就是这个商品
                    String gravityResult = gravityList.get(0);
                    String svrCode = gravityResult.split("_")[0];
                    subNum = new BigDecimal(gravityResult.split("_")[1]);
                    Integer num = subNum.intValue();
                    if (type == 20) {
                        num = -num;
                    }
                    CommodityDiffVo tempDiffVo = assemblyVisionTemp(num, svrCode, deviceInfo, subNum.intValue());
                    commodityDiffVoList.add(tempDiffVo);
                } else if (gravityList.size() > 0) {
                    //没有匹配或者匹配到多种商品可能,出异常订单
                    //库存没有匹配的商品,出异常订单
                    //如果视觉为空,且重力可以匹配到多种,选取数量最少的出识别订单
                    String resultData = gravityList.get(0);
                    subNum = new BigDecimal(resultData.split("_")[1]);
                    Integer size = new Integer(resultData.split("_")[2]);
                    StockVo stockVo = diffStocks.get(size);
                    return null;
                }
            } else {
                if (gravityList.size() == 1) {
                    //在误差范围内,出订单
                    String gravityResult = gravityList.get(0);
                    String svrCode = gravityResult.split("_")[0];
                    subNum = new BigDecimal(gravityResult.split("_")[1]);
                    Integer num = subNum.intValue();
                    if (type == 20) {
                        num = -num;
                    }
                    CommodityDiffVo tempDiffVo = assemblyVisionTemp(num, svrCode, deviceInfo, subNum.intValue());
                    commodityDiffVoList.add(tempDiffVo);
                } else if (gravityList.size() == 0) {
                    return null;
                } else {
                    List<String> result = new ArrayList<>();
                    for (int x = 0; x < gravityList.size(); x++) {
                        String gravityResult = gravityList.get(x);
                        String svrCode = gravityResult.split("_")[0];
                        subNum = new BigDecimal(gravityResult.split("_")[1]);
                        Integer size = new Integer(gravityResult.split("_")[2]);
                        if (visionList.contains(svrCode)) {
                            result.add(subNum + "_" + size + "_" + svrCode);
                        }
                    }
                    if (result.size() == 1) {
                        String resultData = result.get(0);
                        subNum = new BigDecimal(resultData.split("_")[0]);
                        String svrCode = resultData.split("_")[2];
                        Integer num = subNum.intValue();
                        if (type == 20) {
                            num = -num;
                        }
                        CommodityDiffVo tempDiffVo = assemblyVisionTemp(num, svrCode, deviceInfo, subNum.intValue());
                        commodityDiffVoList.add(tempDiffVo);
                    } else if (result.size() == 0) {
                        return null;
                    } else {
                        String resultData = result.get(0);
                        subNum = new BigDecimal(resultData.split("_")[0]);
                        Integer size = new Integer(resultData.split("_")[1]);
                        StockVo stockVo = diffStocks.get(size);
                        return null;
                    }
                }
            }
        }
        return commodityDiffVoList;
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
            DeviceStock deviceStock = deviceStockService.selectDeviceStockByCommodityId(deviceInfo.getId(), commodityInfo.getId());
            tempDiffVo.setFcommodityPrice(deviceStock.getFsalePrice());
            tempDiffVo.setCurrStock(Math.abs(closeNum));
        }
        return tempDiffVo;
    }


    /**
     * 视觉重力柜关门
     *
     * @param baseResponseVo
     */
    public void localGravityCloseDoor(String deviceCode, String deviceId, BaseResponseVo baseResponseVo) {
        logger.info("开始处理本地重力视觉识别关门盘货成功消息消息,设备编号：{}，用户ID：{}", deviceCode, baseResponseVo.getUserId());
        String userId = baseResponseVo.getUserId();
        String goodsString = baseResponseVo.getData();
        Integer closeType = null;                                                   // 关门类型 10购物 20补货 30游客
        SessionVo sessionVo = null;                                                 // 用户信息
        ClientVo clientVo = null;                                                   // 设备信息
        SocketIOClient socketIOClient = null;                                       // 服务器与手机端通信通道
        Integer sourceClientType = null;    //来源客户端类型
        Integer openDoorType = null;        //开门类型 10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
        String clientIp = "";               // 开门IP地址
        Integer types = null;               //10 开门 20 补货开门
        Goods goods = null;
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
                logger.info("重力视觉柜关门盘点商品:{}", goodsString);
                goods = JSON.parseObject(goodsString, Goods.class);                 //商品集合
            }
        } catch (Exception e) {
            logger.error("商品集合json格式转化出现异常:{}", e);
            SocketResponseVo socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "商品集合json格式转化出现异常");
            Integer errorType = setErrorTypeByUserOpenDoorType(types);
            socketResponseVo.setTypes(errorType);
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            removeUserOpenDoorKey(userId);
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
        //BigDecimal stockTotalWeight = deviceStockService.selectTotalWeight(deviceId);
        try {
            BigDecimal stockTotalWeight = (BigDecimal) iCached.get("device_total_weight_" + deviceId);
            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.LOCAL_GRAVITY_CLOSE_SERVICE);
            InventoryDto inventoryDto = new InventoryDto();
            inventoryDto.setInventoryType(10);
            inventoryDto.setCloseType(openDoorType);
            inventoryDto.setDeviceId(deviceId);
            inventoryDto.setTotalWeight(goods.getTotalWeight());
            inventoryDto.setStockTotalWeight(stockTotalWeight);
            inventoryDto.setMemberId(userId);
            inventoryDto.setSip(clientIp);
            List<CommodityVo> commodityVos = new ArrayList<>();    // 临时商品对象
            if (CollectionUtils.isNotEmpty(goods.getGoodsList())) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                commodityVos = commodityBeanTransformation(commodityVos, goods.getGoodsList());   //  bean复制
            }
            inventoryDto.setCommodityVos(commodityVos);
            inventoryDto.setIsourceClientType(sourceClientType);    // 来源类型
            invoke.setRequestObj(inventoryDto);
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
                        createPayOrder(sessionVo, deviceId, userId, responseVo.getData().getMerchantCode(), responseVo.getData().getOrderRecords(), ctxMap);
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
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.GENERATE_REPLENISHMENT_ORDERS, userId, TypeConstant.CLOSE_DOOR_ORDER);
                    logger.debug("扫码开门 -> 关门盘货 -> 给设备推送补货单，设备ID：{}", deviceId);

                } else if (inventoryResult.getItype().intValue() == 50) { // 游客
                    if (openDoorType == 10) {//购物开门
                        socketResponseVo.setTypes(30);
                        closeType = 30;
                        //给设备推送关门结果
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.NO_CHANGE_IN_PRODUCT_QUANTITY, userId, TypeConstant.CLOSE_DOOR_ORDER);
                        logger.debug("扫码开门 -> 关门盘货 -> 游客类型，给设备推送消息，设备ID：{}", deviceId);
                        unsignAlipay(sessionVo, responseVo.getData().getMerchantCode());//支付宝解密
                        cancelWechatPointOrder(userId, responseVo.getData().getMerchantCode(), deviceId);
                    } else if (openDoorType == 20) {//补货员开门
                        socketResponseVo.setTypes(40);
                        closeType = 40;
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.NO_CHANGE_IN_PRODUCT_QUANTITY, userId, TypeConstant.CLOSE_DOOR_ORDER);
                        logger.debug("扫码开门 -> 关门盘货 -> 补货员类型，给设备推送消息，设备ID：{}", deviceId);
                    }
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);


                } else if (inventoryResult.getItype().intValue() == 60) {  // 商品差与重量差对比，产生购物异常订单
                    socketResponseVo.setTypes(50);//补货异常
                    socketResponseVo.setErrorCode(ReturnCodeEnum.ERROR_SHOPPING_EXCEPTION.getCode()); // 购物异常错误代码
                    socketResponseVo.setMsg(responseVo.getMsg());
                    socketResponseVo.setSuccess(false);
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后商品数量差与重量差对比超过阈值，生成购物异常订单", userId, TypeConstant.CLOSE_DOOR_ORDER);
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
                    //取消订单
                    cancelWechatPointOrder(userId, "", deviceId);
                    socketResponseVo.setErrorCode(ReturnCodeEnum.ERROR_SHOPPING_EXCEPTION.getCode()); // 购物异常错误代码

                }
                socketResponseVo.setMsg(responseVo.getMsg());
                socketResponseVo.setSuccess(false);
                asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);

                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后调用设备盘点服务失败", userId, TypeConstant.CLOSE_DOOR_ORDER);
                logger.error("扫码开门 -> 关门盘货 -> 向设备发送调用关门盘货服务失败，设备ID：{}", deviceId);
            }
        } catch (Exception e) {
            logger.error("关门成功后调用设备盘点服务出现异常：{}", e);
            final int errorCode = ReturnCodeEnum.ERROR_CLOSEDOOR_INVENTORY_EXCEPTION.getCode();
            SocketResponseVo socketResponseVo = new SocketResponseVo(false, errorCode, "关门盘货出现异常");
            Integer errorType = setErrorTypeByUserOpenDoorType(types);
            socketResponseVo.setTypes(errorType);
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后调用设备盘点服务出现异常", userId, TypeConstant.CLOSE_DOOR_ORDER);
            logger.error("扫码开门 -> 关门盘货 -> 调用设备盘点服务出现异常，向设备推送消息完成，设备ID：{}", deviceId);
        }
        removeUserOpenDoorKey(userId);
    }

    /**
     * 视觉重力分层柜关门
     *
     * @param baseResponseVo
     */
    public void localGravityLayeredCloseDoor(String deviceCode, String deviceId, BaseResponseVo baseResponseVo) {
        logger.info("开始处理本地重力视觉识别关门盘货成功消息消息,设备编号：{}，用户ID：{}", deviceCode, baseResponseVo.getUserId());

        String userId = baseResponseVo.getUserId();
        String goodsString = baseResponseVo.getData();
        Integer closeType = null;                                                   // 关门类型 10购物 20补货 30游客
        SessionVo sessionVo = null;                                                 // 用户信息
        ClientVo clientVo = null;                                                   // 设备信息
        SocketIOClient socketIOClient = null;                                       // 服务器与手机端通信通道
        Integer sourceClientType = null;    //来源客户端类型
        Integer openDoorType = null;        //开门类型 10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
        String clientIp = "";               // 开门IP地址
        Integer types = null;               //10 开门 20 补货开门

        ShopLayeredGoods shopLayeredGoods = new ShopLayeredGoods();
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
                logger.info("重力视觉柜关门盘点商品:{}", goodsString);
                shopLayeredGoods = JSON.parseObject(goodsString, ShopLayeredGoods.class);                 //商品集合
            }
        } catch (Exception e) {
            logger.error("商品集合json格式转化出现异常:{}", e);
            SocketResponseVo socketResponseVo = new SocketResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "商品集合json格式转化出现异常");
            Integer errorType = setErrorTypeByUserOpenDoorType(types);
            socketResponseVo.setTypes(errorType);
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            removeUserOpenDoorKey(userId);
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
            List<LayeredGoods> layeredGoodsList = shopLayeredGoods.getLayeredGoodsList();
            List<LayeredWeight> layeredWeightList = shopLayeredGoods.getLayeredWeightList();
            Map<String, Object> map = mergelayeredResult(layeredGoodsList, layeredWeightList);
            BigDecimal openWeightTotal = (BigDecimal) iCached.get("layered_device_total_weight_" + deviceId);//开门盘货重量
            BigDecimal closeWeightTotal = (BigDecimal) map.get("totalWeight");//实时订单盘货重量
            List<LayeredGoods> closeGoods = (List<LayeredGoods>) map.get("layeredGoodsList");
            String openMap = (String) iCached.get(NettyConst.LAYERED_CLOUD_DEVICE_OPEN_DOOR_COMMODITYS + deviceId);    // 在Redis中记录开门商品
            logger.info("购物实时订单-->重力视觉柜实时盘货开关门对比，开门商品数据,{}", openMap);
            List<LayeredGoods> openGoods = JSON.parseObject(openMap, new TypeReference<List<LayeredGoods>>() {
            });
            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(InventoryServicesDefine.LOCAL_GRAVITY_LAYERED_CLOSE_SERVICE);
            LayeredInventoryDto layeredInventoryDto = new LayeredInventoryDto();
            layeredInventoryDto.setInventoryType(10);
            layeredInventoryDto.setCloseType(openDoorType);
            layeredInventoryDto.setDeviceId(deviceId);
            layeredInventoryDto.setMemberId(userId);
            layeredInventoryDto.setSip(clientIp);
            layeredInventoryDto.setCloseWeightTotal(closeWeightTotal);
            layeredInventoryDto.setOpenWeightTotal(openWeightTotal);
            List<LayeredGoodsVo> openLayeredGoodsVo = new ArrayList<>();    // 临时商品对象
            if (CollectionUtils.isNotEmpty(openGoods)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                openLayeredGoodsVo = commodityBeanTransformation2(openGoods);   //  bean复制
            }
            List<LayeredGoodsVo> closeLayeredGoodsVo = new ArrayList<>();    // 临时商品对象
            if (CollectionUtils.isNotEmpty(openGoods)) {                // 商品集合非空，进行商品对象转换，将goods转换成dto
                closeLayeredGoodsVo = commodityBeanTransformation2(closeGoods);   //  bean复制
            }
            layeredInventoryDto.setOpenLayeredGoods(openLayeredGoodsVo);
            layeredInventoryDto.setCloseLayeredGoods(closeLayeredGoodsVo);
            layeredInventoryDto.setIsourceClientType(sourceClientType);    // 来源类型
            invoke.setRequestObj(layeredInventoryDto);
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

                        createPayOrder(sessionVo, deviceId, userId, responseVo.getData().getMerchantCode(), responseVo.getData().getOrderRecords(), ctxMap);
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
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.GENERATE_REPLENISHMENT_ORDERS, userId, TypeConstant.CLOSE_DOOR_ORDER);
                    logger.debug("扫码开门 -> 关门盘货 -> 给设备推送补货单，设备ID：{}", deviceId);

                } else if (inventoryResult.getItype().intValue() == 50) { // 游客
                    if (openDoorType == 10) {//购物开门
                        socketResponseVo.setTypes(30);
                        closeType = 30;
                        //给设备推送关门结果
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.NO_CHANGE_IN_PRODUCT_QUANTITY, userId, TypeConstant.CLOSE_DOOR_ORDER);
                        logger.debug("扫码开门 -> 关门盘货 -> 游客类型，给设备推送消息，设备ID：{}", deviceId);
                        unsignAlipay(sessionVo, responseVo.getData().getMerchantCode());//支付宝解密
                        cancelWechatPointOrder(userId, responseVo.getData().getMerchantCode(), deviceId);
                    } else if (openDoorType == 20) {//补货员开门
                        socketResponseVo.setTypes(40);
                        closeType = 40;
                        MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, CommonConstants.NO_CHANGE_IN_PRODUCT_QUANTITY, userId, TypeConstant.CLOSE_DOOR_ORDER);
                        logger.debug("扫码开门 -> 关门盘货 -> 补货员类型，给设备推送消息，设备ID：{}", deviceId);
                    }
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);


                } else if (inventoryResult.getItype().intValue() == 60) {  // 商品差与重量差对比，产生购物异常订单
                    socketResponseVo.setTypes(50);//补货异常
                    socketResponseVo.setErrorCode(ReturnCodeEnum.ERROR_SHOPPING_EXCEPTION.getCode()); // 购物异常错误代码
                    socketResponseVo.setMsg(responseVo.getMsg());
                    socketResponseVo.setSuccess(false);
                    asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后商品数量差与重量差对比超过阈值，生成购物异常订单", userId, TypeConstant.CLOSE_DOOR_ORDER);
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
                    //取消订单
                    cancelWechatPointOrder(userId, "", deviceId);
                    socketResponseVo.setErrorCode(ReturnCodeEnum.ERROR_SHOPPING_EXCEPTION.getCode()); // 购物异常错误代码
                }
                socketResponseVo.setMsg(responseVo.getMsg());
                socketResponseVo.setSuccess(false);
                asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);

                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后调用设备盘点服务失败", userId, TypeConstant.CLOSE_DOOR_ORDER);
                logger.error("扫码开门 -> 关门盘货 -> 向设备发送调用关门盘货服务失败，设备ID：{}", deviceId);
                //更新关门日志
                LogUtils.updateOPLog(deviceCode, userId, closeType);
                logger.info("设备关门盘货处理完成");
            }
        } catch (Exception e) {
            logger.error("关门成功后调用设备盘点服务出现异常：{}", e);
            final int errorCode = ReturnCodeEnum.ERROR_CLOSEDOOR_INVENTORY_EXCEPTION.getCode();
            SocketResponseVo socketResponseVo = new SocketResponseVo(false, errorCode, "关门盘货出现异常");
            Integer errorType = setErrorTypeByUserOpenDoorType(types);
            socketResponseVo.setTypes(errorType);
            asynSendMsgToSocketIo(NettyConst.SOCKETIO_EVENT, socketResponseVo, socketIOClient);
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, false, "关门成功后调用设备盘点服务出现异常", userId, TypeConstant.CLOSE_DOOR_ORDER);
            logger.error("扫码开门 -> 关门盘货 -> 调用设备盘点服务出现异常，向设备推送消息完成，设备ID：{}", deviceId);
        }
        removeUserOpenDoorKey(userId);
    }

    /**
     * 取消订单
     *
     * @param userId
     * @param merchantCode
     */
    public void cancelWechatPointOrder(final String userId, final String merchantCode, final String deviceId) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(userId);
                    String temp = merchantCode;
                    if (StringUtil.isBlank(temp)) {
                        temp = memberInfo.getSmerchantCode();
                    }
                    MerchantConf conf = merchantInfoService.getWechatMerchantConf(temp, 2);
                    if (conf.getIwechatWithholdType() == 20 && memberInfo.getIwechatPointOpen() == 1) {
                        //取消订单
                        QueryAndEndSmartretailOrderDto queryAndEndSmartretailOrderDto = new QueryAndEndSmartretailOrderDto();
                        queryAndEndSmartretailOrderDto.setSmerchantCode(temp);
                        queryAndEndSmartretailOrderDto.setSmemberId(userId);
                        queryAndEndSmartretailOrderDto.setDeviceId(deviceId);
                        queryAndEndSmartretailOrderDto.setFinish_type(1);
                        queryAndEndSmartretailOrderDto.setCancel_reason("用户关门未购物取消订单");
                        queryAndEndSmartretailOrderDto.setProfit_sharing(false);
                        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_POINT_QUERY_AND_END_ORDER);// 服务名称
                        // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<EndSmartretailOrderResult>>() {
                        });
                        invoke.setRequestObj(queryAndEndSmartretailOrderDto); // post 参数
                        ResponseVo<EndSmartretailOrderResult> resVO = (ResponseVo<EndSmartretailOrderResult>) invoke.invoke();
                        if (null != resVO && resVO.isSuccess()) {
                            logger.info("用户未购物,取消订单成功");
                        }
                    }
                } catch (Exception e) {
                    logger.info("用户未购物,取消订单异常：{}", e);
                }
            }
        });
    }

    /**
     * 計算优惠
     *
     * @param subVoList
     * @param userId
     * @param deviceCode
     * @throws Exception
     */
    private RealTimeOrderResult calculationResult(List<CommodityDiffVo> subVoList, String userId, String deviceCode) throws Exception {
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(userId);
        RealTimeOrderResult result = new RealTimeOrderResult();
        logger.info("开始组装订单优惠参数");
        OrderDiscountInfoDto orderDiscountInfoDto = new OrderDiscountInfoDto();
        orderDiscountInfoDto.setId(memberInfo.getId());
        orderDiscountInfoDto.setScode(memberInfo.getScode());
        orderDiscountInfoDto.setSmemberName(memberInfo.getSmemberName());
        orderDiscountInfoDto.setSdeviceCode(deviceCode);
        List<CommodityDiscountDto> commodityDiscountDtoList = new ArrayList<>();
        for (CommodityDiffVo diffVo : subVoList) {
            CommodityDiscountDto commodityDiscountDto = new CommodityDiscountDto();
            BeanUtils.copyProperties(commodityDiscountDto, diffVo);//复制对象
            commodityDiscountDto.setForderCount(diffVo.getNumber());//订单商品数量
            commodityDiscountDtoList.add(commodityDiscountDto);
        }
        orderDiscountInfoDto.setCommodityDisList(commodityDiscountDtoList);


        logger.info("组装订单优惠参数完成 :" + orderDiscountInfoDto);
        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OrderDiscountDefine.CREATE_ORDER_DISCOUNT);
        invoke.setRequestObj(orderDiscountInfoDto);
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<OrderDiscountInfoResult>>() {
        });
        ResponseVo<OrderDiscountInfoResult> responseVo = (ResponseVo<OrderDiscountInfoResult>) invoke.invoke();
        if (null != responseVo && responseVo.isSuccess()) {//创建优惠成功
            OrderDiscountInfoResult discountInfoResult = responseVo.getData();
            BeanUtils.copyProperties(result, discountInfoResult);//复制对象
            List<RealTimeCommodityResult> results = new ArrayList<RealTimeCommodityResult>();
            RealTimeCommodityResult commodityResult = null;
            for (CommodityDiscountDto dto : discountInfoResult.getCommodityDisList()) {
                commodityResult = new RealTimeCommodityResult();
                BeanUtils.copyProperties(commodityResult, dto);//复制对象
                commodityResult.setNumber(dto.getForderCount());//订单商品数量
                results.add(commodityResult);
            }
            result.setResults(results);
        } else {
            //计算优惠失败  返回数据
            BigDecimal totalAmount = new BigDecimal("0");
            List<RealTimeCommodityResult> results = new ArrayList<RealTimeCommodityResult>();
            RealTimeCommodityResult commodityResult = null;
            for (CommodityDiffVo diffVo : subVoList) {
                commodityResult = new RealTimeCommodityResult();
                BeanUtils.copyProperties(commodityResult, diffVo);//复制对象
                results.add(commodityResult);
                totalAmount = totalAmount.add((commodityResult.getFcommodityPrice().multiply(new BigDecimal(commodityResult.getNumber()))));//计算总金额
            }
            result.setFactualPayAmount(totalAmount);
            result.setFcouponDeductionAmount(BigDecimal.ZERO);
            result.setFdiscountAmount(BigDecimal.ZERO);
            result.setFfirstDiscountAmount(BigDecimal.ZERO);
            result.setFotherDiscountAmount(BigDecimal.ZERO);
            result.setFpointDiscountAmount(BigDecimal.ZERO);
            result.setFpromotionDiscountAmount(BigDecimal.ZERO);
            result.setFtotalAmount(totalAmount);
            result.setResults(results);
        }
        return result;
    }

    /**
     * 对比库存
     *
     * @param tagModels
     * @param deviceInfo
     * @return
     */
    public Map<String, Object> calcCommodityDiffByType(List<TagModel> tagModels, DeviceInfo deviceInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        //查询对应设备的库存
        List<StockVo> stocks = null;
        List<CommodityDiffVo> subVoList = new ArrayList<CommodityDiffVo>();
        List<CommodityDiffVo> addVoList = new ArrayList<CommodityDiffVo>();
        CommodityDiffVo tempDiffVo = null;
        Integer stockTemp = null;
        if (deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.VISION.getCode()
                || deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.CLOUD_VISION.getCode()
                || deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.CLOUD_ZL_VISION.getCode()
                || deviceInfo.getItype().intValue() == BizTypeDefinitionEnum.DeviceType.QD_ZL_VISION.getCode()) {//视觉设备盘点
            stocks = deviceStockService.selectStockByDeviceId(deviceInfo.getId());
            if (null != stocks && stocks.size() > 0) {//数据库库存不为0
                List<TagModel> tempDiff = new ArrayList<TagModel>();
                if (null != tagModels && tagModels.size() > 0) {//盘点设备库存不为0 商品可能上架 出售 下架 已售
                    TagModel tempVo = null;
                    for (StockVo stockVo : stocks) {//循环系统库存商品
                        tempVo = null;
                        for (TagModel tagModel : tagModels) {//循环设备库存
                            if (tagModel.getSvrCode().equals(stockVo.getSvrCode())) {//判断商品否相同
                                tempVo = tagModel;
                                tempDiff.add(tagModel);
                                break;
                            }
                        }
                        if (null != tempVo) {//系统库存和设备库存有相同的商品
                            stockTemp = stockVo.getIstock();
                            if (null != stockTemp && stockTemp.intValue() == tempVo.getNumber().intValue()) {
                                //库存不变
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
                    tagModels.removeAll(tempDiff);
                    if (tagModels.size() > 0) {
                        addVoList = addCommodity(addVoList, tagModels, deviceInfo);
                    }
                } else {//盘点后设备商品库存都为0  所有商品下架或已出售
                    subVoList = subCommodity(subVoList, stocks, deviceInfo);
                }
            } else {//数据库 系统库存为0
                if (null != tagModels && tagModels.size() > 0) {//盘点后设备商品库存不为0  所有商品都是新增
                    //所有商品都是新增
                    addVoList = addCommodity(addVoList, tagModels, deviceInfo);
                }
            }
        }
        map.put("subVoList", subVoList);
        map.put("addVoList", addVoList);
        return map;
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
        TagModel tagModel = null;
        CommodityDiffVo tempDiffVo = null;
        for (StockVo stockVo : stocks) {
            if (stockVo.getIstock() > 0) {
                tagModel = new TagModel();
                tagModel.setNumber(0);
                tagModel.setSvrCode(stockVo.getSvrCode());
                tempDiffVo = assemblyVision(stockVo, tagModel, deviceInfo);
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
     * @param subVoList  减少商品集合
     * @param stockVo    需要减少的商品信息
     * @param deviceInfo 设备信息
     * @return
     */
    private List<CommodityDiffVo> subCommodity(List<CommodityDiffVo> subVoList, StockVo stockVo, DeviceInfo deviceInfo) {
        TagModel tagModel = new TagModel();
        tagModel.setNumber(0);
        tagModel.setSvrCode(stockVo.getSvrCode());
        CommodityDiffVo tempDiffVo = assemblyVision(stockVo, tagModel, deviceInfo);
        if (null != tempDiffVo) {
            tempDiffVo.setItype(20);
            subVoList.add(tempDiffVo);
        }
        return subVoList;
    }

    /**
     * 新增商品
     *
     * @param addVoList  新增商品集合
     * @param tagModels  需要新增的商品集合
     * @param deviceInfo 设备信息
     * @return
     */
    private List<CommodityDiffVo> addCommodity(List<CommodityDiffVo> addVoList, List<TagModel> tagModels, DeviceInfo deviceInfo) {
        for (TagModel tagModel : tagModels) {
            //新增已售库存
            CommodityDiffVo tempDiffVo = assemblyVision(0, tagModel, deviceInfo);
            if (null == tempDiffVo) {
                continue;
            }
            tempDiffVo.setItype(10);
            addVoList.add(tempDiffVo);
        }
        return addVoList;
    }


    /**
     * 组装视觉商品差
     *
     * @param stockTemp  商品库存
     * @param tagModel   商品信息
     * @param deviceInfo 设备信息
     */
    private CommodityDiffVo assemblyVision(Integer stockTemp, TagModel tagModel, DeviceInfo deviceInfo) {
        CommodityDiffVo tempDiffVo = new CommodityDiffVo();
        tempDiffVo.setSvrCode(tagModel.getSvrCode());
        CommodityInfo commodityInfo = commodityInfoService.selectByVrCode(tagModel.getSvrCode(), deviceInfo.getSmerchantId());
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
            if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 10) {
                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "天");
            } else if (null != commodityInfo.getIlifeType() && commodityInfo.getIlifeType().intValue() == 20) {
                tempDiffVo.setSshelfLife(commodityInfo.getIshelfLife() + "个月");
            }
        } else {
            logger.error("视觉盘点商品不存在，商户编号-视觉编号：{}", deviceInfo.getSmerchantCode() + "-" + tagModel.getSvrCode());
            return null;
        }
        if (null == stockTemp || tagModel.getNumber() > stockTemp.intValue()) {
            stockTemp = (null == stockTemp) ? 0 : stockTemp;
            tempDiffVo.setItype(10);
            tempDiffVo.setNumber(tagModel.getNumber() - stockTemp);
            tempDiffVo.setFcommodityPrice(commodityInfo.getFsalePrice());
            tempDiffVo.setCurrStock(tagModel.getNumber());

        } else if (tagModel.getNumber() < stockTemp) {
            tempDiffVo.setItype(20);
            tempDiffVo.setNumber(stockTemp - tagModel.getNumber());
            //获取商品库存销售价格
            DeviceStock deviceStock = deviceStockService.selectDeviceStockByCommodityId(deviceInfo.getId(), commodityInfo.getId());
            tempDiffVo.setFcommodityPrice(deviceStock.getFsalePrice());
            //tempDiffVo.setFtaxPoint(deviceStock.get);//税点
            tempDiffVo.setCurrStock(tagModel.getNumber());
        }
        return tempDiffVo;
    }

    /**
     * 组装视觉商品差
     *
     * @param stockVo    商品库存数据
     * @param tagModel   商品信息
     * @param deviceInfo 设备信息
     */
    private CommodityDiffVo assemblyVision(StockVo stockVo, TagModel tagModel, DeviceInfo deviceInfo) {
        CommodityDiffVo tempDiffVo = new CommodityDiffVo();
        //CommodityInfo commodityInfo = commodityInfoService.selectByVrCode(commodityVo.getSvrCode(),deviceInfo.getSmerchantId());
        if (!stockVo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return null;
        }
        tempDiffVo.setScommodityFullName(stockVo.getSbrandName() + stockVo.getSname() + stockVo.getStaste() + stockVo.getIspecWeight() + stockVo.getSspecUnit() + "/" + stockVo.getSpackageUnit());
        tempDiffVo.setSvrCode(tagModel.getSvrCode());
        tempDiffVo.setScommodityCode(stockVo.getScode());
        tempDiffVo.setScommodityId(stockVo.getId());
        tempDiffVo.setScommodityName(stockVo.getSname());
        tempDiffVo.setFcostPrice(stockVo.getFcostPrice());
        tempDiffVo.setIstatus(stockVo.getIstatus());
        tempDiffVo.setSpackageUnit(stockVo.getSpackageUnit());
        tempDiffVo.setSbrandId(stockVo.getSbrandId());
        tempDiffVo.setSsmallCategoryId(stockVo.getSsmallCategoryId());
        tempDiffVo.setScommodityImg(stockVo.getScommodityImg());
        if (null != stockVo.getIlifeType() && stockVo.getIlifeType().intValue() == 10) {
            tempDiffVo.setSshelfLife(stockVo.getIshelfLife() + "天");
        } else if (null != stockVo.getIlifeType() && stockVo.getIlifeType().intValue() == 20) {
            tempDiffVo.setSshelfLife(stockVo.getIshelfLife() + "月");
        }
        Integer stockTemp = stockVo.getIstock();
        if (null == stockTemp || tagModel.getNumber() > stockTemp.intValue()) {
            stockTemp = (null == stockTemp) ? 0 : stockTemp;
            tempDiffVo.setItype(10);
            tempDiffVo.setNumber(tagModel.getNumber() - stockTemp);
            tempDiffVo.setFcommodityPrice(stockVo.getFsalePrice());
            tempDiffVo.setCurrStock(tagModel.getNumber());
        } else if (tagModel.getNumber() < stockTemp) {
            tempDiffVo.setItype(20);
            tempDiffVo.setNumber(stockTemp - tagModel.getNumber());
            //获取商品库存销售价格
            DeviceStock deviceStock = deviceStockService.selectDeviceStockByCommodityId(deviceInfo.getId(), stockVo.getId());
            tempDiffVo.setFcommodityPrice(deviceStock.getFsalePrice());
            //tempDiffVo.setFtaxPoint(deviceStock.getFsalePrice());//税点
            tempDiffVo.setCurrStock(tagModel.getNumber());
        }
        return tempDiffVo;
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
            removeUserOpenDoorKey(userId);
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
                        createPayOrder(sessionVo, deviceId, userId, responseVo.getData().getMerchantCode(), responseVo.getData().getOrderRecords(), ctxMap);
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
                        unsignAlipay(sessionVo, responseVo.getData().getMerchantCode());//支付宝解密
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
        removeUserOpenDoorKey(userId);

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
                    Integer orderSubTotalNum = new Integer(0);                     // 订单减少总数
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
                if (null == deviceInfo) {
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
                if (null == deviceInfo) {
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
                for (Map.Entry<String, SuNingShedGoodModel> map : newThirdDeviceSkuMap.entrySet()) {
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
     *
     * @param baseResponseVo
     */
    private void closeDoorSuccess(BaseResponseVo baseResponseVo) {
        try {
            if (iCached.exists("inventory_failed_five_user_operate_device_key_" + baseResponseVo.getUserId())) {
                String deviceId = baseResponseVo.getDeviceId();
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                String userId = baseResponseVo.getUserId();
                SessionVo sessionVo = (SessionVo) iCached.get("soketIo_user_session_" + userId);
                Integer openDoorType = sessionVo.getTypes();//10 购物 20补货
                //如果存在
                if (10 == openDoorType) {
                    logger.info("购物过程中设备故障，出异常订单，直接发送设备故障消息到安卓端：{}", deviceInfo.getScode());
                    MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(baseResponseVo.getUserId());
                    generateExceptionOrder(deviceInfo, memberInfo, new ArrayList<>(), sessionVo.getIsourceClientType(), 40);
                    baseResponseVo.setMsg("设备故障,稍后后台会进行结算!");
                    sengFaultMsgToCellphone(baseResponseVo);
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, "设备故障,稍后后台会进行结算!", null, TypeConstant.FAULT);
                } else if (20 == openDoorType) {
                    logger.info("补货过程中设备故障，直接发送设备故障消息到安卓端：{}", deviceInfo.getScode());
                    baseResponseVo.setMsg("设备故障,请在设备正常后继续补货!");
                    sengFaultMsgToCellphone(baseResponseVo);
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, "设备故障,请在设备正常后继续补货!", null, TypeConstant.FAULT);
                }
                iCached.remove("inventory_failed_five_user_operate_device_key_" + baseResponseVo.getUserId());
                removeUserOpenDoorKey(baseResponseVo.getUserId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     *
     * @param baseResponseVo
     * @throws Exception
     */
    private void openDoorInventory(BaseResponseVo baseResponseVo) {
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
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<RealTimeOrderResult>>() {
                });
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
                            if (StringUtils.isNotBlank(result.getScommodityImg())) {
                                goodModel.setGoodImgUrl(preUrl + result.getScommodityImg());
                            }
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
                                if (StringUtils.isNotBlank(repCom.getScommodityUrl())) {
                                    goodModel.setGoodImgUrl(preUrl + repCom.getScommodityUrl());
                                }
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
                                if (StringUtils.isNotBlank(repCom.getScommodityUrl())) {
                                    goodModel.setGoodImgUrl(preUrl + repCom.getScommodityUrl());
                                }
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
     *
     * @param commodityVoList 返回对象
     * @param goodsList       视觉商品集合
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
     *
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
     * @param methodType   10=库存对比，20=商品差
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
                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, orderModel, userId, TypeConstant.CLOSE_DOOR_ORDER);
            }
            return;
        }
        if (methodType == 10) {
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, null, userId, TypeConstant.CLOSE_DOOR_ORDER);
        } else if (methodType == 20) {
            MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceId, ctxMap, true, null, userId, TypeConstant.CLOSE_DOOR_ORDER);
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
     * 将安卓设备传过来的商品转换成服务商品入参
     *
     * @param commodityVos 服务入参
     * @param tagModels    源商品参数
     * @return
     */
    private List<CommodityVo> commodityBeanTransformation(List<CommodityVo> commodityVos, List<TagModel> tagModels) {
        CommodityVo commodityVo = null;
        List<CommodityVo> commodityVoList = new ArrayList<>();
        for (TagModel tagModel : tagModels) {
            commodityVo = new CommodityVo();
            commodityVo.setSvrCode(tagModel.getSvrCode());
            commodityVo.setCommodityNum(tagModel.getNumber());
            commodityVoList.add(commodityVo);
        }
        return commodityVoList;
    }

    /**
     * 将安卓设备传过来的商品转换成服务商品入参
     *
     * @param layeredGoodsList 源商品参数
     * @return
     */
    private List<LayeredGoodsVo> commodityBeanTransformation2(List<LayeredGoods> layeredGoodsList) {
        LayeredGoodsVo layeredGoodsVo = null;
        List<LayeredGoodsVo> layeredGoodsVos1 = new ArrayList<>();
        for (LayeredGoods layeredGoods : layeredGoodsList) {
            layeredGoodsVo = new LayeredGoodsVo();
            layeredGoodsVo.setCameraIp(layeredGoods.getCameraIp());
            layeredGoodsVo.setLayeredWeight(layeredGoods.getLayeredWeight());
            List<CommodityVo> commodityVos = new ArrayList<>();    // 临时商品对象
            List<CommodityVo> list = commodityBeanTransformation(commodityVos, layeredGoods.getGoodsList());
            layeredGoodsVo.setCommodityVos(list);
            layeredGoodsVos1.add(layeredGoodsVo);
        }
        return layeredGoodsVos1;
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

    /**
     * 测试连接
     *
     * @param baseResponseVo
     * @return
     * @throws Exception
     */
    public void testConnection(BaseResponseVo baseResponseVo, ChannelHandlerContext ctx) {
        // 给设备推送补货单
        logger.debug("发送测试连接消息");
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = MsgToJsonUtils.setAndroidMsgNoCode(true, "", "", "", "");
                    ctx.writeAndFlush(msg);
                    logger.debug("发送测试连接消息完成：{}", msg);
                } catch (Exception e) {
                    logger.error("发送测试连接消息：{}", e);
                }
            }
        });
    }


    /**
     * 分层盘货合并视觉和重力结果
     *
     * @param layeredGoodsList
     * @param layeredWeightList
     * @return
     */
    private Map<String, Object> mergelayeredResult(List<LayeredGoods> layeredGoodsList, List<LayeredWeight> layeredWeightList) {
        Collections.sort(layeredGoodsList, new Comparator<LayeredGoods>() {
            @Override
            public int compare(LayeredGoods o1, LayeredGoods o2) {
                Integer temp1 = Integer.valueOf(o1.getCameraIp().substring(o1.getCameraIp().lastIndexOf(".") + 1));
                Integer temp2 = Integer.valueOf(o2.getCameraIp().substring(o2.getCameraIp().lastIndexOf(".") + 1));
                if (temp1.compareTo(temp2) > 0) {
                    return 1;
                } else if (temp1.compareTo(temp2) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        Collections.sort(layeredWeightList, new Comparator<LayeredWeight>() {
            @Override
            public int compare(LayeredWeight o1, LayeredWeight o2) {
                Integer temp1 = Integer.valueOf(o1.getWeightIp().substring(o1.getWeightIp().lastIndexOf(".") + 1));
                Integer temp2 = Integer.valueOf(o2.getWeightIp().substring(o2.getWeightIp().lastIndexOf(".") + 1));
                if (temp1.compareTo(temp2) > 0) {
                    return 1;
                } else if (temp1.compareTo(temp2) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        Map<String, Object> map = new HashMap();
        BigDecimal totalWeight = BigDecimal.ZERO;
        for (int x = 0; x < layeredGoodsList.size(); x++) {
            layeredGoodsList.get(x).setLayeredWeight(layeredWeightList.get(x).getLayeredWeight());
            totalWeight = totalWeight.add(layeredWeightList.get(x).getLayeredWeight());
        }
        map.put("layeredGoodsList", layeredGoodsList);
        map.put("totalWeight", totalWeight);
        return map;
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
     * @param subResult              重力的值
     * @param subTotal               视觉减少的的值
     * @param contrastGoodsModelList
     */
    private void layeredContrast(DeviceInfo deviceInfo, BigDecimal subResult, BigDecimal subTotal, List<ContrastGoodsModel> contrastGoodsModelList) {
        List<CommodityDiffVo> setSubVoList = new ArrayList<>();
        for (ContrastGoodsModel contrastGoodsModel : contrastGoodsModelList) {
            if (contrastGoodsModel.isIisShop()) {
                //当前层是否购物 true:购物 false:误差范围内无购物
                BigDecimal diff = contrastGoodsModel.getOpenWeight().subtract(contrastGoodsModel.getRealWeight());//开关门重量差
                Map<String, Object> contrastMap = contrastGoodsModel.getContrastMap();
                List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) contrastMap.get("subVoList");
                List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) contrastMap.get("addVoList");
                if (diff.compareTo(BigDecimal.ZERO) > 0) {
                    if (!subVoList.isEmpty() && addVoList.isEmpty()) {
                        //重量减少了,视觉也减少了
                        // 重力在误差范围内-->确定商品
                        // 重力在误差范围外-->求出增加的部分
                        BigDecimal subData = BigDecimal.ZERO;
                        BigDecimal subfloatResult = BigDecimal.ZERO;
                        List subVisionList = new ArrayList();
                        for (CommodityDiffVo commodityDiffVo : subVoList) {
                            BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                            subData = subData.add(multiplyData);
                            subfloatResult = subfloatResult.add(commodityDiffVo.getIcommodityFloat());
                            subVisionList.add(commodityDiffVo.getSvrCode());
                        }
                        BigDecimal iele = deviceInfo.getIelectronicFloat();
                        if (diff.subtract(subData).abs().compareTo(iele.add(subfloatResult)) <= 0) {
                            //在误差范围内 确定商品和数量
                            setSubVoList = subVoList;
                        } else {
                            //在误差范围外,可能有增加的部分未识别
                            addVoList = getResult(deviceInfo, subData.subtract(diff), addVoList, null, 10);
                            if (addVoList == null) {
                                //为匹配到增加商品或匹配到多种增加商品
                            } else {
                                //匹配到增加的商品
                            }
                        }
                    } else if (subVoList.isEmpty() && !addVoList.isEmpty()) {
                        //重量减少了,视觉增加
                        // 增加部分正常识别,有减少商品未识别出来
                        // 增加部分为误识别,有减少部分未识别出来（忽略）
                        BigDecimal addData = BigDecimal.ZERO;
                        BigDecimal addfloatResult = BigDecimal.ZERO;
                        List addVisionList = new ArrayList();
                        for (CommodityDiffVo commodityDiffVo : addVoList) {
                            BigDecimal multiplyData = new BigDecimal(commodityDiffVo.getNumber()).multiply(commodityDiffVo.getIweigth());
                            addData = addData.add(multiplyData);
                            addfloatResult = addfloatResult.add(commodityDiffVo.getIcommodityFloat());
                            addVisionList.add(commodityDiffVo.getSvrCode());
                        }
                        BigDecimal iele = deviceInfo.getIelectronicFloat();
                        //在误差范围外,可能有增加的部分未识别
                        addVoList = getResult(deviceInfo, addData.add(diff), addVoList, null, 10);
                    } else if (subVoList.isEmpty() && addVoList.isEmpty()) {
                        //重量减少了,视觉不变
                        // 有减少部分未识别出来
                        // 有增加部分和减少部分都未识别(忽略不计)
                        subVoList = getResult(deviceInfo, diff, subVoList, null, 10);

                    }
                }
            }
        }
    }

    /***
     * 生成异常审核订单
     * @param deviceInfo 设备信息
     * @param memberInfo 用户信息
     * @param subVoList 盘点商品减少信息
     * @param isourceClientType 盘点参数
     * @throws Exception
     */
    private ResponseVo<String> generateExceptionOrder(DeviceInfo deviceInfo, MemberInfo memberInfo, List<CommodityDiffVo> subVoList, Integer isourceClientType, Integer itype) {
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
            exceptionOrderDto.setIsourceClientType(isourceClientType);
            exceptionOrderDto.setItype(itype);//盘点异常
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
     * 出现异常时给手机端发送消息
     *
     * @param baseResponseVo
     * @throws Exception
     */
    private void sengFaultMsgToCellphone(BaseResponseVo baseResponseVo) throws Exception {
        ClientVo clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, baseResponseVo.getDeviceId());
        if (null != clientVo) {
            // 10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
            Integer door = clientVo.getDoor();  // 10关，20开
            Integer openDoorType = clientVo.getOpenDoorType();  //开门类型 10 开门 20 补货开门 30游客开门（没买东西）
            Integer type = null;
            if (StringUtils.isBlank(baseResponseVo.getUserId())) {
                logger.error("没有手机在线,出现异常时给手机端发送消息失败，设备ID：{}", baseResponseVo.getDeviceId());
                return;
            }
            if (null != openDoorType && 10 == openDoorType) {
                type = 50;
            } else if (null != openDoorType && 20 == openDoorType) {
                type = 60;
            }
            String id = baseResponseVo.getDeviceId() + "_" + baseResponseVo.getUserId();
            SocketIOClient socketIOClient = socketIoMap.get(id);
            MsgToJsonUtils.asynSendMsgToCellPhone(type, "", baseResponseVo.getCode(), baseResponseVo.getMsg(), false, socketIOClient, baseResponseVo.getUserId());
        }
    }

    /**
     * 匹配单种单个商品
     *
     * @param deviceInfo
     * @param matchWeight
     * @param iele
     * @param subVoList
     * @param flagTemp
     * @param userId
     * @return
     * @throws Exception
     */
    private Boolean matchSingleGoods(DeviceInfo deviceInfo, BigDecimal matchWeight, BigDecimal iele
            , List<CommodityDiffVo> subVoList, Boolean flagTemp, String userId) throws Exception {
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
            geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
            logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,匹配单个商品,可以匹配出商品：{}", stockVo);
            return true;
        } else if (!stockVos.isEmpty() && stockVos.size() > 1) {
            Collections.sort(stockVos, new Comparator<StockVo>() {
                @Override
                public int compare(StockVo o1, StockVo o2) {
                    BigDecimal weight1 = o1.getIweigth();
                    BigDecimal weight2 = o2.getIweigth();
                    BigDecimal diffValue1 = weight1.subtract(matchWeight).abs();
                    BigDecimal diffValue2 = weight2.subtract(matchWeight).abs();
                    if (diffValue1.compareTo(diffValue2) < 0) {
                        return -1;
                    } else if (diffValue1.compareTo(diffValue2) > 0) {
                        return 1;
                    } else {
                        int stock1 = o1.getIstock();
                        int stock2 = o2.getIstock();
                        if (stock1 > stock2) {
                            return -1;
                        } else if (stock1 < stock2) {
                            return 1;
                        }
                    }
                    return 0;
                }
            });
            StockVo stockVo = stockVos.get(0);
            subVoList = getSubList(stockVo, deviceInfo, flagTemp,
                    subVoList, new BigDecimal(1));
            geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
            logger.info("重量减少大于视觉减少,根据重力视觉差匹配商品,匹配单个商品,可以匹配出商品：{}", stockVo);
            return true;
        }
        return false;
    }

    private boolean yanzheng(Integer type, DeviceInfo deviceInfo, BigDecimal diffData, BigDecimal iele, Boolean flagTemp
            , String userId, List<CommodityDiffVo> subVoList, BigDecimal subResult, BigDecimal subTotal
    ) throws Exception {
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
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                    return true;
                }
            } else if (10 == type && flagTemp) {
              /*  BigDecimal diffTemp = subTotal.subtract(subResult);
                if (te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(diffTemp).compareTo(BigDecimal.ZERO) >= 0) {
                    geOrder(userId, deviceInfo.getScode(), subVoList, deviceInfo.getId());
                    return true;
                }*/
            } else if (20 == type) {
                if (te.getIweigth().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100)).subtract(subResult).compareTo(BigDecimal.ZERO) >= 0) {
                    logger.info("#########开始匹配单个商品,变化值未查询出来,没有视觉变化,购物车空空如也####:{}", type);
                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(deviceInfo.getId(), ctxMap, true, null, userId, TypeConstant.OPEN_DOOR_INVENTORY);
                    return true;
                }
            }
        }
        return false;
    }

}
