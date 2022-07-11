package com.cloud.cang.api.netty.handler;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.TypeEnum;
import com.cloud.cang.api.common.utils.IPUtils;
import com.cloud.cang.api.common.utils.MsgToJsonUtils;
import com.cloud.cang.api.netty.service.NettyExceptionMsgService;
import com.cloud.cang.api.netty.service.NettyMsgService;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.DeviceConst;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.pojo.BaseRequestVo;
import com.cloud.cang.pojo.BaseResponseVo;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
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
public class ServerInboundHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger = LoggerFactory.getLogger(ServerInboundHandler.class);

    private ConcurrentMap<String, ChannelHandlerContext> channelMap = null;             //  （设备ID，netty会话）
    private ConcurrentMap<String, SocketIOClient> socketIOMap = null;                   //  socketIOMap，（设备ID，会话）
    private ChannelGroup channels = null;                                               //  netty自带的通道组(用来通知所有在线的客户端)
    private DeviceRegisterService registerService = null;                               //  设备注册service
    private DeviceInfoService deviceInfoService = null;                                 //  设备信息
    private ICached iCached = null;                                                     //  将设备相关信息存放在redis中
    private BaseResponseVo baseResponseVo = null;                                       //  返回消息
    private NettyMsgService nettyMsgService = null;                                     //  netty接收消息处理类
    private NettyExceptionMsgService nettyExceptionMsgService = null;                   //  Netty异常消息处理类
    private String resultMsg = null;
    private List<String> blackList = null;                                              //  黑名单


    /**
     * 服务器启动时初始化
     *
     * @param channels                 netty自带通道
     * @param channelMap               保存通道的map
     * @param deviceRegisterService    设备注册服务类
     * @param iCached                  redis缓存
     * @param socketIOMap              手机端通道map
     * @param nettyMsgService          netty普通消息处理服务
     * @param deviceInfoService        设备基础信息服务类
     * @param nettyExceptionMsgService netty异常消息处理服务
     */
    public ServerInboundHandler(ChannelGroup channels, ConcurrentMap<String, ChannelHandlerContext> channelMap, DeviceRegisterService deviceRegisterService,
                                ICached iCached, ConcurrentMap<String, SocketIOClient> socketIOMap, NettyMsgService nettyMsgService, DeviceInfoService deviceInfoService,
                                NettyExceptionMsgService nettyExceptionMsgService) {
        this.channels = channels;
        this.channelMap = channelMap;
        this.registerService = deviceRegisterService;
        this.iCached = iCached;
        this.socketIOMap = socketIOMap;
        this.nettyMsgService = nettyMsgService;
        this.deviceInfoService = deviceInfoService;
        this.nettyExceptionMsgService = nettyExceptionMsgService;
    }

    /**
     * 连接激活时，将通道加入ChannelGroup和channelMap
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("****** client " + IPUtils.getRemoteAddress(ctx) + " === ctxId->" + ctx.channel().id().asLongText() + " 连接 ******");
        channels.add(ctx.channel());// 将新的连接加入到ChannelGroup，当连接断开ChannelGroup会自动移除对应的Channel

        // 打印日志
        logger.info("netty自带通道组中的TCP连接数:" + channels.size());
        for (Channel channels : channels) {
            logger.info("所有TCP连接的IP地址为:" + channels.remoteAddress());
        }
        logger.info("注册客户端的TCP连接数:" + channelMap.size());
        for (Map.Entry<String, ChannelHandlerContext> channel : channelMap.entrySet()) {
            logger.info("注册的长连接,设备ID为：" + channel.getKey() + " , IP地址为：" + channel.getValue().channel().remoteAddress());
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
//        super.channelInactive(ctx);

        // 打印日志
        logger.info("netty自带通道组中的TCP连接数:" + channels.size());
        for (Channel channels : channels) {
            logger.info("所有TCP连接的IP地址为:" + channels.remoteAddress());
        }
        logger.info("注册客户端的TCP连接数:" + channelMap.size());
        for (Map.Entry<String, ChannelHandlerContext> channel : channelMap.entrySet()) {
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
//        removeClient(ctx);// 从synclientMap中将对应ctx移除
        logger.error(getCtxId(ctx) + "----通道出现异常，地址为-->" + IPUtils.getRemoteAddress(ctx));
        cause.printStackTrace();//打印异常消息

        // 打印日志
        logger.info("netty自带通道组中的TCP连接数:" + channels.size());
        for (Channel channels : channels) {
            logger.info("所有TCP连接的IP地址为:" + channels.remoteAddress());
        }
        logger.info("注册客户端的TCP连接数:" + channelMap.size());
        for (Map.Entry<String, ChannelHandlerContext> channel : channelMap.entrySet()) {
            logger.info("注册的长连接,设备ID为：" + channel.getKey() + " , IP地址为：" + channel.getValue().channel().remoteAddress());
        }
    }

    /**
     * 心跳代码
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.error("触发心跳时间" + new Date());
        logger.error("服务端未检测到设备发送的心跳消息,该设备IP地址：{}", ctx.channel().remoteAddress());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                try {
                    Integer loss_connect_time = (Integer) iCached.get(NettyConst.CLIENT_HEART + getCtxId(ctx));
                    if (null == loss_connect_time) {
                        loss_connect_time = 0;
                        iCached.put(NettyConst.CLIENT_HEART + getCtxId(ctx), loss_connect_time, 30); // 第一次未检测心跳消息，开始记录,超时时间30秒
                    } else {
                        loss_connect_time++;
                        iCached.put(NettyConst.CLIENT_HEART + getCtxId(ctx), loss_connect_time, 30); // 累加心跳
                        if (loss_connect_time == 1) {
                            logger.error("两次未检测到客户端：{}心跳，关闭这个不活跃的channel", ctx.channel().remoteAddress());
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
     * 服务端处理设备发送消息
     *
     * @param ctx 连接通道
     * @param msg 发送消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        boolean paramsNotEmpty = false;//参数（token,data）是否为空

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
            baseResponseVo = (BaseResponseVo) jsonMap.get("baseResponseVo");
            try {
                paramsNotEmpty = validateCreateParam(baseResponseVo);//校验信息是否为空
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
                msgHandler(baseResponseVo, ctx);
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
     * 将json字符串解析成msgVo对象
     *
     * @param msg json消息体
     * @return 转换成功返回true，否则返回错误消息
     */
    private ConcurrentHashMap<String, Object> jsonToMsgVo(String msg) throws Exception {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
        boolean convertflag = false;//json消息体转换是否成功
        BaseResponseVo baseResponseVo = new BaseResponseVo();
        try {
//            logger.info("设备发送过来的消息体为：{}" + msg);
            baseResponseVo = JSON.parseObject(msg, BaseResponseVo.class);   //fastjson解析消息
//            logger.info("设备发送过来的消息转化之后的对象为：{}" + baseResponseVo.toString());
            convertflag = true;
        } catch (Exception e) {
            logger.error("json格式转换失败,错误消息格式内容为：{}" + msg, e);
        }
        map.put("convertflag", convertflag);
        map.put("baseResponseVo", baseResponseVo);
        return map;
    }


    /**
     * 校验参数是否为null(token,data)
     *
     * @param baseResponseVo 消息对象
     * @return 校验通过返回true，否则返回false
     */
    private boolean validateCreateParam(BaseResponseVo baseResponseVo) throws Exception {
//        logger.debug("<==校验参数开始==>");
        if (StringUtils.isBlank(baseResponseVo.getDeviceId())) {
            logger.error("设备ID为空");
            return false;
        }
        if (StringUtils.isBlank(baseResponseVo.getToken())) {
            logger.error("token为空");
            return false;
        }
        if (null == baseResponseVo.getType()) {
            logger.error("消息类型为空");
            return false;
        }
//        logger.debug("<==校验参数完成==>");
        return true;
    }

    /**
     * @param baseResponseVo
     * @param ctx
     * @return
     * @throws Exception
     */
    private void msgHandler(BaseResponseVo baseResponseVo, ChannelHandlerContext ctx) {
//        logger.debug("服务端校验token开始ctxId->" + getCtxId(ctx) + "设备IP为" + IPUtils.getRemoteAddress(ctx));
        Integer type = baseResponseVo.getType();         //消息类型 10=心跳消息 20=普通消息 30=异常消息 40=注册消息
        String token = baseResponseVo.getToken();    //建立连接时的32位通道密钥
        String deviceId = baseResponseVo.getDeviceId();  //设备ID
        String ctxId = getCtxId(ctx);     //通道ID

        //刚连接时，将该设备注册到synclientMap中
        if (TypeEnum.MsgType.REGISTER.getCode() == type) {  //type == 40
            //如果设备已经注册到服务器，对当前连接发送的消息不做任何处理
            if (channelMap.containsKey(deviceId)) {
                logger.error("该设备信息已经注册到服务器上，已经注册的设备ID为:{}" + deviceId);
                MsgToJsonUtils.asynSendRegisterErrorMsgByCtx(deviceId, ctx, false, ReturnCodeEnum.ERROR_DEVICEINFO_REGISTERED.getCode(), "该注册信息已经被使用，请更换注册信息", null, null);
                ctx.close();    // 断开连接
                return;
            }

            DeviceRegister deviceRegister = registerService.selectRegister(deviceId, token);    //从设备注册表中查询相关信息
            if (null != deviceRegister) {
                // 修改数据库设备状态为正常
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                if (null == deviceInfo) {
                    logger.error("没有查询到该设备的信息,设备ID：{}", deviceId);
                    MsgToJsonUtils.asynSendRegisterErrorMsgByCtx(deviceId, ctx, false, ReturnCodeEnum.ERROR_NO_DEVICE_INFO.getCode(), "没有查询到该设备的信息，请确认设备ID是否存在", null, null);
                    return;
                }

                DeviceInfo deviceInfoTemp = new DeviceInfo();
                deviceInfoTemp.setId(deviceId);
                deviceInfoTemp.setIstatus(DeviceConst.DEVICE_NORMAL);        // 10未注册 20正常 30故障 40报废 50离线
                deviceInfoService.updateBySelective(deviceInfoTemp);

                ClientVo clientVo = new ClientVo();
                clientVo.setCtxId(ctxId);                                       //ctxId
                clientVo.setDeviceId(deviceId);
                clientVo.setDeviceCode(deviceRegister.getSdeviceCode());        //设备编号
                clientVo.setDoor(10);                                           //关门状态  10关，20开
                clientVo.setMerchantId(deviceInfo.getSmerchantId());            //商户ID
                clientVo.setMerchantCode(deviceInfo.getSmerchantCode());        //商户编号
                channelMap.put(deviceId, ctx);                                  //设备ID，netty会话
                try {
                    ClientVo clientVo1 = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, deviceId);
                    if (clientVo1 != null) {
                        iCached.hremove(NettyConst.SYN_CLIENT_MAP, deviceId);
                        clientVo1.setDoor(10);
                        iCached.hset(NettyConst.SYN_CLIENT_MAP, deviceId, clientVo1);
                    } else {
                        iCached.hset(NettyConst.SYN_CLIENT_MAP, deviceId, clientVo);    //将设备相关信息存入Redis中
                    }
                } catch (Exception e) {
                    logger.error("放入redis出现异常", e);
                }

                /* 连接时set设备基础信息到redis中 设备基础信息DeviceInfo*/
                nettyMsgService.assemblyDeviceInfoVo(deviceId, deviceInfo);

                MsgToJsonUtils.asynSendRegisterSuccessMsgByCtx(deviceId, ctx);
                return;
            } else {
                logger.error("没有查询到该设备的注册信息");
                MsgToJsonUtils.asynSendRegisterErrorMsgByCtx(deviceId, ctx, false, ReturnCodeEnum.ERROR_NO_REGISTER_INFO.getCode(), "没有查询到该设备的信息，请确认设备ID与KEY是否正确", null, null);
                return;
            }

            //接受设备发送过来的消息  心跳消息 || 普通消息
        } else if (TypeEnum.MsgType.HEARTBEAT.getCode() == type || TypeEnum.MsgType.COMMON.getCode() == type) {

            //判断该连接是否已经存储在channelMap中，注册后会将设备ID与ctx放入该map中
            if (!channelMap.containsKey(deviceId)) {
                logger.error("当前设备->" + IPUtils.getRemoteAddress(ctx) + "没有与netty服务器建立连接，设备连接服务器后必须先发送注册消息才能和服务器通信!");
                return;
            }

            //下列代码用来关闭没有注册通道，但是使用已经注册到服务器上的通道的相关信息进行非法操作
            ChannelHandlerContext context = channelMap.get(deviceId); //  已注册设备
            String registerCtxId = getCtxId(context); //已注册到服务器的设备的ctxId
            if (!registerCtxId.equals(ctxId)) { //发送消息的通道与已经注册的通道不一致
                logger.error("该通道socket为：" + IPUtils.getRemoteAddress(ctx) + "没有注册到netty服务器，但是使用已经注册到服务器上的deviceID与Token");
                return;
            }

            //连接已经存储到map中
            if (TypeEnum.MsgType.HEARTBEAT.getCode() == type) {    //心跳
//                resultMsg = MsgToJsonUtils.setAndroidMsgWithCode(true, "success", deviceId, null, null);
//                ctx.writeAndFlush(resultMsg);
//                MsgToJsonUtils.asynSendMsgByCtx(deviceId, ctx, true, "success", null, null);   //待删除
//                MsgToJsonUtils.asynCtxSendHeartBeatByCtx(deviceId, ctx);    // 返回码 200        // 待商量，吕威威20180530说不要回心跳消息
                return;
            } else if (TypeEnum.MsgType.COMMON.getCode() == type) {  //设备发送过来的普通信息
                nettyMsgService.handlerMessage(baseResponseVo);         //具体方法处理类
                return;
            }

            //设备发送的异常消息
        } else if (TypeEnum.MsgType.EXCEPTION.getCode() == type) {

            //判断该连接是否已经存储在channelMap中，注册后会将设备ID与ctx放入该map中
            if (!channelMap.containsKey(deviceId)) {
                logger.error("当前设备->" + getCtxId(ctx) + "没有与netty服务器建立连接，设备连接服务器后必须先发送注册消息才能和服务器通信!");
                return;
            }

            //下列代码用来检测没有注册通道，但是使用已经注册到服务器上的通道的相关信息进行非法操作
            ChannelHandlerContext context = channelMap.get(deviceId); //  已注册设备
            String registerCtxId = getCtxId(context); //已注册到服务器的设备的ctxId
            if (!registerCtxId.equals(ctxId)) { //发送消息的通道与已经注册的通道不一致
                logger.error("该通道socket为：" + IPUtils.getRemoteAddress(ctx) + "没有注册到netty服务器，但是使用已经注册到服务器上的deviceID与Token");
                return;
            }
            nettyExceptionMsgService.handlerExceptionMessage(baseResponseVo);   //具体异常方法处理类
            return;
            //设备发送的测试消息
        } else if (TypeEnum.MsgType.TEST.getCode() == type) {
            nettyMsgService.handlerTestMessage(baseResponseVo, ctx);         //具体方法处理类
        } else { //消息类型不正确,关闭长连接
            logger.error(getCtxId(ctx) + "消息类型不正确，当前消息的类型为：" + type);
        }
        return;
    }


    /**
     * 客户端断开连接或连接出现异常时，从该集合中将该客户端信息从map中移除
     *
     * @param ctx
     */
    public void removeClient(ChannelHandlerContext ctx) {

        // 从channelMap中移除注册过的设备
        String channelId = ctx.channel().id().asLongText();
        String clientId = "";//设备通道ID
        for (Map.Entry<String, ChannelHandlerContext> entry : channelMap.entrySet()) {
            String tempId = getCtxId(entry.getValue());
            if (channelId.equals(tempId)) {
                clientId = entry.getKey();//设备ID
                logger.error("从channelMap中移除已经注册的设备，IP地址为：{}", entry.getValue().channel().remoteAddress());
                break;//匹配到对应的设备跳出循环
            }
        }
        channelMap.remove(clientId);

        // 将数据库中的设备状态改为离线
        if (StringUtils.isNotBlank(clientId)) {
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(clientId);
            if (null != deviceInfo && Integer.valueOf(DeviceConst.DEVICE_NORMAL).equals(deviceInfo.getIstatus())) {
                DeviceInfo deviceInfoTemp = new DeviceInfo();
                deviceInfoTemp.setId(clientId);
                deviceInfoTemp.setIstatus(DeviceConst.DEVICE_OFFLINE);
                deviceInfoService.updateBySelective(deviceInfoTemp);
            }
        }

        //  移除售货机设备发送到服务器心跳在redis中记录的缓存数量
        try {
            Integer count = (Integer) iCached.get(NettyConst.CLIENT_HEART + getCtxId(ctx));
            if (null != count) {
                iCached.remove(NettyConst.CLIENT_HEART + getCtxId(ctx));
            }
        } catch (Exception e) {
            logger.error("从Redis中移除心跳缓存数据出现异常，通道ip:{} ", ctx.channel().remoteAddress());
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
     * 服务端发送给客户端消息对象
     *
     * @param success true成功，false失败
     * @param msg     返回信息，正确返回0，失败返回错误信息
     * @return
     */
    private String setResultMsg(Boolean success, String msg) throws Exception {
        BaseRequestVo baseRequestVo = new BaseRequestVo();
        baseRequestVo.setSuccess(success);
        baseRequestVo.setData(msg);
        String resultMsg = JSON.toJSONString(baseRequestVo) + System.lineSeparator();
        return resultMsg;
    }

    private String setSuccessMsg(String msg) throws Exception {
        return setResultMsg(true, msg);
    }

    private String setfailMsg(String msg) throws Exception {
        return setResultMsg(false, msg);
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


    /**
     * 异步修改
     * 设备表设备状态为正常
     * 设备注册表注册状态为已注册
     *
     * @param deviceInfo
     */
    private void asynUpdateDevice(final DeviceInfo deviceInfo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                DeviceInfo deviceInfoTemp = new DeviceInfo();
                deviceInfoTemp.setId(deviceInfo.getId());
                deviceInfoTemp.setIstatus(DeviceConst.DEVICE_NORMAL);        // 10未注册 20正常 30故障 40报废 50离线
                deviceInfoService.updateBySelective(deviceInfoTemp);
            }
        });
    }


}
