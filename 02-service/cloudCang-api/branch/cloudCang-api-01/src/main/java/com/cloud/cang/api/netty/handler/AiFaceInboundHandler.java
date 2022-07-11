package com.cloud.cang.api.netty.handler;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.TypeEnum;
import com.cloud.cang.api.common.utils.FaceMsgToJsonUtils;
import com.cloud.cang.api.common.utils.IPUtils;
import com.cloud.cang.api.common.utils.MsgToJsonUtils;
import com.cloud.cang.api.netty.service.AiFaceExceptionMsgService;
import com.cloud.cang.api.netty.service.AiFaceMsgService;
import com.cloud.cang.api.netty.vo.aiFace.FaceDeviceVo;
import com.cloud.cang.api.sb.service.AiInfoService;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.DeviceConst;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.facePojo.FaceResponseVo;
import com.cloud.cang.model.sb.AiInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceRegister;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @version 1.0
 * @ClassName: ServerInboundHandler
 * @Description: handler处理类
 * @Author: zengzexiong
 * @Date: 2018年1月22日19:10:21
 */
@ChannelHandler.Sharable
public class AiFaceInboundHandler extends SimpleChannelInboundHandler<String> {
    private static Logger logger = LoggerFactory.getLogger(AiFaceInboundHandler.class);

    private ConcurrentMap<String, ChannelHandlerContext> aiFaceMap = null;              //  人脸识别设备
    private ChannelGroup aiFaceChannelGroup = null;                                     //  netty自带的通道组(用来通知所有在线的客户端)
    private DeviceRegisterService registerService = null;                               //  设备注册service
    private DeviceInfoService deviceInfoService = null;                                 //  设备信息
    private AiInfoService aiInfoService = null;
    private ICached iCached = null;                                                     //  将设备相关信息存放在redis中
    private AiFaceMsgService aiFaceMsgService = null;                                   //  接收消息处理类
    private AiFaceExceptionMsgService aiFaceExceptionMsgService = null;                 //  接收消息处理类

    /**
     * aiFace服务器启动时初始化
     *
     * @param aiFaceChannelGroup        netty自带通道组
     * @param aiFaceExceptionMsgService 异常消息处理
     * @param deviceRegisterService     设备注册服务
     * @param iCached                   Redis缓存
     * @param aiFaceMap                 aiFace的map
     * @param aiFaceMsgService          设备信息
     */
    public AiFaceInboundHandler(ChannelGroup aiFaceChannelGroup, AiFaceExceptionMsgService aiFaceExceptionMsgService, DeviceRegisterService deviceRegisterService, ICached iCached, ConcurrentMap<String,
            ChannelHandlerContext> aiFaceMap, AiFaceMsgService aiFaceMsgService, DeviceInfoService deviceInfoService, AiInfoService aiInfoService) {
        this.aiFaceMap = aiFaceMap;
        this.aiFaceChannelGroup = aiFaceChannelGroup;
        this.iCached = iCached;
        this.registerService = deviceRegisterService;
        this.aiFaceMsgService = aiFaceMsgService;
        this.aiFaceExceptionMsgService = aiFaceExceptionMsgService;
        this.deviceInfoService = deviceInfoService;
        this.aiInfoService = aiInfoService;
    }

    /**
     * 连接激活时，将通道加入
     * aiFaceChannelGroup
     * aiFaceMap
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("****** client " + IPUtils.getRemoteAddress(ctx) + " === ctxId->" + ctx.channel().id().asLongText() + " 连接 ******");
        aiFaceChannelGroup.add(ctx.channel());// 将新的连接加入到ChannelGroup，当连接断开ChannelGroup会自动移除对应的Channel

        // 打印日志
        logger.info("人脸识别自带通道组中的TCP连接数:" + aiFaceChannelGroup.size());
        for (Channel channels : aiFaceChannelGroup) {
            logger.info("人脸识别所有TCP连接的IP地址为:" + channels.remoteAddress());
        }
        logger.info("人脸识别注册客户端的TCP连接数:" + aiFaceMap.size());
        for (Map.Entry<String, ChannelHandlerContext> channel : aiFaceMap.entrySet()) {
            logger.info("人脸识别注册的长连接,设备ID为：" + channel.getKey() + " , IP地址为：" + channel.getValue().channel().remoteAddress());
        }
    }

    /**
     * 连接关闭时调用，将通道上下文保存在channelMap中
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String client = IPUtils.getRemoteAddress(ctx);
        logger.info("****** client " + client + " === ctxId->" + ctx.channel().id().asLongText() + " 断开连接 ******");

        /* 从synclientMap中将对应ctx移除 */
        removeClient(ctx);

        // 打印日志
        logger.info("netty自带通道组中的TCP连接数:" + aiFaceChannelGroup.size());
        for (Channel channels : aiFaceChannelGroup) {
            logger.info("所有TCP连接的IP地址为:" + channels.remoteAddress());
        }
        logger.info("注册客户端的TCP连接数:" + aiFaceMap.size());
        for (Map.Entry<String, ChannelHandlerContext> channel : aiFaceMap.entrySet()) {
            logger.info("注册的长连接,设备ID为：" + channel.getKey() + " , IP地址为：" + channel.getValue().channel().remoteAddress());
        }
    }

    /**
     * 异常情况下关闭该通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(getCtxId(ctx) + "----通道出现异常，地址为-->" + IPUtils.getRemoteAddress(ctx));
        cause.printStackTrace();//打印异常消息

        // 打印日志
        logger.info("netty自带通道组中的TCP连接数:" + aiFaceChannelGroup.size());
        for (Channel channels : aiFaceChannelGroup) {
            logger.info("所有TCP连接的IP地址为:" + channels.remoteAddress());
        }
        logger.info("注册客户端的TCP连接数:" + aiFaceMap.size());
        for (Map.Entry<String, ChannelHandlerContext> channel : aiFaceMap.entrySet()) {
            logger.info("注册的长连接,设备ID为：" + channel.getKey() + " , IP地址为：" + channel.getValue().channel().remoteAddress());
        }
    }

    /**
     * 心跳检测代码
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.error("触发心跳时间" + new Date());
        logger.error("服务端未检测到人脸识别设备发送的心跳消息,该设备IP地址：{}", ctx.channel().remoteAddress());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                try {
                    Integer loss_connect_time = (Integer) iCached.get(NettyConst.FACE_HEART + getCtxId(ctx));
                    if (null == loss_connect_time) {
                        loss_connect_time = 0;
                        iCached.put(NettyConst.FACE_HEART + getCtxId(ctx), loss_connect_time, 30); // 第一次未检测心跳消息，开始记录,超时时间30秒
                    } else {
                        loss_connect_time++;
                        iCached.put(NettyConst.FACE_HEART + getCtxId(ctx), loss_connect_time, 30); // 累加心跳
                        if (loss_connect_time == 1) {
                            logger.error("两次未检测到人脸识别客户端：{}心跳，关闭这个不活跃的channel", ctx.channel().remoteAddress());
                            removeClientAndCloseCtx(ctx); // 移除该客户端,关闭通道
                        }
                    }
                } catch (Exception e) {
                    logger.error("从redis中获取设备心跳相关信息出现异常：{}", e);
                }

            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }


    /**
     * 读取人脸识别客户端消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        boolean paramsNotEmpty = false;//参数（token,data）是否为空
        FaceResponseVo faceResponseVo = null;

        /*
        * 0.解析json信息->msgVo对象
        */
        ConcurrentMap<String, Object> jsonMap = null;
        try {
            jsonMap = jsonToMsgVo(msg);
        } catch (Exception e) {
            logger.error("解析协议出现异常:{}", e);
        }

        /*
        * 1.校验客户端信息(deviceId,token)是否为空
        */
        if ((boolean) jsonMap.get("convertflag")) {
            faceResponseVo = (FaceResponseVo) jsonMap.get("faceResponseVo");
            try {
                paramsNotEmpty = validateCreateParam(faceResponseVo);//校验信息是否为空
            } catch (Exception e) {
                logger.error("校验参数出现异常:{}", e);
            }
        }

        /*
        * 2.校验deviceId与token有效性。
        *   判断该客户端是否是第一次连接。
        *   如果是第一次连接，需要发送注册消息，将该客户端连接注册到synclientMap中。
        *   如果已经建立连接，直接进行操作。
        */
        if (paramsNotEmpty) {
            try {
                msgHandler(faceResponseVo, ctx);
            } catch (Exception e) {
                logger.error("处理消息出现异常:{}", e);
            }
        } else {
            logger.error("校验消息不通过");
        }
        if (!msg.contains("\"type\":10")) {
            logger.debug("服务端处理消息完成,设备IP为->" + IPUtils.getRemoteAddress(ctx) + "======msg->" + msg);
        }
    }

    /**
     * @param faceResponseVo
     * @param ctx
     * @return
     * @throws Exception
     */
    private void msgHandler(FaceResponseVo faceResponseVo, ChannelHandlerContext ctx) {
//        logger.debug("服务端校验token开始ctxId->" + getCtxId(ctx) + "设备IP为" + IPUtils.getRemoteAddress(ctx));
        Integer type = faceResponseVo.getType();         //消息类型 10=心跳消息 20=普通消息 30=异常消息 40=注册消息
        String token = faceResponseVo.getToken();    //建立连接时的32位通道密钥
        String aiId = faceResponseVo.getDeviceId();  //设备ID
        String ctxId = getCtxId(ctx);     //通道ID

        //刚连接时，将该设备注册到synclientMap中
        if (TypeEnum.MsgType.REGISTER.getCode() == type) {  //type == 40
            //如果设备已经注册到服务器，对当前连接发送的消息不做任何处理
            if (aiFaceMap.containsKey(aiId)) {
                logger.error("该AI设备信息已经注册到服务器上，已经注册的设备ID为:{}" + aiId);
//                MsgToJsonUtils.asynSendRegisterErrorMsgByCtx(aiId, ctx, false, ReturnCodeEnum.ERROR_DEVICEINFO_REGISTERED.getCode(), "该AI注册信息已经被使用，请更换注册信息", null, null);
//                ctx.close();    // 断开连接
                FaceMsgToJsonUtils.sendRegisterInfoAlreadyUsed(aiId, ctx, false, ReturnCodeEnum.ERROR_DEVICEINFO_REGISTERED.getCode(), "该AI注册信息已经被使用，请更换注册信息，接收该信息后通信将通道关闭", null, null);
                return;
            }

            DeviceRegister deviceRegister = registerService.selectRegister(aiId, token);    //从设备注册表中查询相关信息
            if (null != deviceRegister) {
                // 修改数据库设备状态为正常
                AiInfo aiInfo = aiInfoService.selectByPrimaryKey(aiId);
                if (null != aiInfo) {
                    AiInfo aiInfoTemp = new AiInfo();
                    aiInfoTemp.setId(aiId);
                    aiInfoTemp.setIstatus(DeviceConst.DEVICE_NORMAL);        // 10未注册 20正常 30故障 40报废 50离线
                    aiInfoService.updateBySelective(aiInfoTemp);                // 修改设备状态

                    aiFaceMap.put(aiId, ctx);                                  //设备ID，netty会话

                    FaceMsgToJsonUtils.sendFaceRegisterSuccessMsgByCtx(aiId, ctx);  // 发送注册成功消息

                    // 向Redis中存入AI设备基础信息
                    DeviceInfo deviceInfoVo = new DeviceInfo();
                    deviceInfoVo.setSaiId(aiId);
                    deviceInfoVo.setIsupportAi(1);
                    deviceInfoVo.setIdelFlag(0);
                    deviceInfoVo.setIstatus(20);
                    deviceInfoVo.setIoperateStatus(10);
                    List<DeviceInfo> deviceInfoList = deviceInfoService.selectByEntityWhere(deviceInfoVo);
                    if (CollectionUtils.isNotEmpty(deviceInfoList)) {
                        DeviceInfo deviceInfo = deviceInfoList.get(0);
                        String deviceId = deviceInfo.getId();   // 售货机设备ID
                        FaceDeviceVo faceDeviceVo = new FaceDeviceVo();
                        faceDeviceVo.setAiId(aiId);
                        faceDeviceVo.setDeviceId(deviceId);
                        faceDeviceVo.setDeviceCode(deviceInfo.getScode());
                        faceDeviceVo.setAiCode(deviceInfo.getSaiCode());
                        try {
                            logger.debug("AI设备连接时，向Redis中存入AI设备基础信息");
                            iCached.put(NettyConst.FACE_DEVICE_VO + deviceId, faceDeviceVo);
                        } catch (Exception e) {
                            logger.error("AI设备连接时，向Redis中存储人脸识别基础信息出现异常，AI设备ID：{},售货机设备ID：{}", aiId, deviceId);
                        }
                    }
                }

                return;
            } else {
                logger.error("没有查询到该AI设备的注册信息");
                FaceMsgToJsonUtils.SendFaceRegisterErrorMsgByCtx(aiId, ctx, false, ReturnCodeEnum.ERROR_NO_REGISTER_INFO.getCode(), "没有查询到该AI设备的信息，请确认设备ID与KEY是否正确", null, null);

                return;
            }

            //接受设备发送过来的消息  心跳消息 || 普通消息
        } else if (TypeEnum.MsgType.HEARTBEAT.getCode() == type || TypeEnum.MsgType.COMMON.getCode() == type) {

            //判断该连接是否已经存储在channelMap中，注册后会将设备ID与ctx放入该map中
            if (!aiFaceMap.containsKey(aiId)) {
                logger.error("当前设备->" + IPUtils.getRemoteAddress(ctx) + "没有与AI服务器建立连接，设备连接服务器后必须先发送注册消息才能和服务器通信!");
                return;
            }

            //下列代码用来关闭没有注册通道，但是使用已经注册到服务器上的通道的相关信息进行非法操作
            ChannelHandlerContext context = aiFaceMap.get(aiId); //  已注册设备
            String registerCtxId = getCtxId(context); //已注册到服务器的设备的ctxId
            if (!registerCtxId.equals(ctxId)) { //发送消息的通道与已经注册的通道不一致
                logger.error("该通道socket为：" + IPUtils.getRemoteAddress(ctx) + "没有注册到AI服务器，但是使用已经注册到服务器上的AI设备ID与Token");
                return;
            }

            //连接已经存储到map中
            if (TypeEnum.MsgType.HEARTBEAT.getCode() == type) {    //心跳
                return;
            } else if (TypeEnum.MsgType.COMMON.getCode() == type) {  //设备发送过来的普通信息
                aiFaceMsgService.handlerMessage(faceResponseVo);         //具体方法处理类
                return;
            }

            //设备发送的异常消息
        } else if (TypeEnum.MsgType.EXCEPTION.getCode() == type) {

            //判断该连接是否已经存储在channelMap中，注册后会将设备ID与ctx放入该map中
            if (!aiFaceMap.containsKey(aiId)) {
                logger.error("当前设备->"+getCtxId(ctx)+"没有与AI服务器建立连接，设备连接AI服务器后必须先发送注册消息才能和服务器通信!");
                return;
            }

            //下列代码用来检测没有注册通道，但是使用已经注册到服务器上的通道的相关信息进行非法操作
            ChannelHandlerContext context = aiFaceMap.get(aiId); //  已注册设备
            String registerCtxId = getCtxId(context); //已注册到服务器的设备的ctxId
            if (!registerCtxId.equals(ctxId)) { //发送消息的通道与已经注册的通道不一致
                logger.error("该通道socket为："+IPUtils.getRemoteAddress(ctx)+"没有注册到AI服务器，但是使用已经注册到AI服务器上的AI设备ID与Token");
                return;
            }
            aiFaceExceptionMsgService.handlerExceptionMessage(faceResponseVo);   //具体异常方法处理类
            return;

            //消息类型不正确,关闭长连接
        } else {
            logger.error(getCtxId(ctx) + "AI设备接收到的消息类型不正确，当前消息的类型为：" + type);
        }
        return;
    }

    /**
     * 将json字符串解析成msgVo对象
     *
     * @param msg json消息体
     * @return 转换成功返回true，否则返回错误消息
     */
    private ConcurrentHashMap<String, Object> jsonToMsgVo(String msg) throws Exception {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        boolean convertflag = false;//json消息体转换是否成功
        FaceResponseVo faceResponseVo = new FaceResponseVo();
        try {
//            logger.info("设备发送过来的消息体为：{}" + msg);
            faceResponseVo = JSON.parseObject(msg, FaceResponseVo.class);   //fastjson解析消息
//            logger.info("设备发送过来的消息转化之后的对象为：{}" + faceResponseVo.toString());
            convertflag = true;
        } catch (Exception e) {
            logger.error("AI设备接收消息时，json格式转换失败,错误消息格式内容为：{}" + msg, e.getMessage());
        }
        map.put("convertflag", convertflag);
        map.put("faceResponseVo", faceResponseVo);
        return map;
    }


    /**
     * 校验参数是否为null(token,data)
     *
     * @param faceResponseVo 消息对象
     * @return 校验通过返回true，否则返回false
     */
    private boolean validateCreateParam(FaceResponseVo faceResponseVo) throws Exception {
        if (StringUtils.isBlank(faceResponseVo.getDeviceId())) {
            logger.error("设备ID为空");
            return false;
        }
        if (StringUtils.isBlank(faceResponseVo.getToken())) {
            logger.error("token为空");
            return false;
        }
        if (null == faceResponseVo.getType()) {
            logger.error("消息类型为空");
            return false;
        }
        return true;
    }

    /**
     * 客户端断开连接或连接出现异常时，从该集合中将该客户端信息从map中移除
     *
     * @param ctx
     */
    public void removeClient(ChannelHandlerContext ctx) {

        // 从aiFaceMap中移除注册过的人脸识别设备
        String channelId = ctx.channel().id().asLongText();
        String clientId = "";//设备ID
        for (Map.Entry<String, ChannelHandlerContext> entry : aiFaceMap.entrySet()) {
            String tempId = getCtxId(entry.getValue());
            if (channelId.equals(tempId)) {
                clientId = entry.getKey();//设备ID
                logger.error("从aiFaceMap中移除已经注册的设备，IP地址为：{}", entry.getValue().channel().remoteAddress());
                break;//匹配到对应的设备跳出循环
            }
        }
        aiFaceMap.remove(clientId);

        // 将数据库中的人脸识别设备状态改为离线
        if (StringUtils.isNotBlank(clientId)) {
            AiInfo aiInfo = aiInfoService.selectByPrimaryKey(clientId);
            if (null != aiInfo && Integer.valueOf(DeviceConst.DEVICE_NORMAL).equals(aiInfo.getIstatus())) {
                AiInfo aiInfoTemp = new AiInfo();
                aiInfoTemp.setId(clientId);
                aiInfoTemp.setIstatus(DeviceConst.DEVICE_OFFLINE);
                aiInfoService.updateBySelective(aiInfoTemp);
            }
        }

        //  移除售货机设备发送到服务器心跳在redis中记录的缓存数量
        try {
            Integer count = (Integer) iCached.get(NettyConst.FACE_HEART + getCtxId(ctx));
            if (null != count) {
                iCached.remove(NettyConst.FACE_HEART + getCtxId(ctx));
            }
        } catch (Exception e) {
            logger.error("从Redis中移除人脸识别设备的心跳缓存数据出现异常，通道ip:{} ", ctx.channel().remoteAddress());
        }

    }

    /**
     * 从synclientMap移除客户端信息，并关闭连接
     *
     * @param ctx
     */
    public void removeClientAndCloseCtx(ChannelHandlerContext ctx) {
        removeClient(ctx);
        if (ctx.channel().isActive()) {
            ctx.close();
        }
    }

    /**
     * 获取Netty通道的id
     *
     * @param ctx
     * @return
     */
    private String getCtxId(ChannelHandlerContext ctx) {
        return ctx.channel().id().asLongText();
    }
}
