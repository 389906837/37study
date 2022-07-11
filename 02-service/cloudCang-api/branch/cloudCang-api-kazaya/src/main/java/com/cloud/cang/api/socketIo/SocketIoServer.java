package com.cloud.cang.api.socketIo;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.WebSocketConstant;
import com.cloud.cang.api.hy.service.MemberInfoService;
import com.cloud.cang.api.hy.service.VendstopService;
import com.cloud.cang.api.netty.service.SocketIOService;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * socketio服务端
 * Created by Alex on 2018/3/28.
 */
@Component
public class SocketIoServer extends AbsConfigurationHandler {
    private final static Logger logger = LoggerFactory.getLogger(SocketIoServer.class);
    @Autowired
    private ICached iCached;
    @Autowired
    private SocketIOService socketIOService;
    @Autowired
    private VendstopService vendstopService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private MemberInfoService memberInfoService;
    private static Interner<String> interner = Interners.newWeakInterner();
    //服务端SocketServer
    private static SocketIOServer server = null;
    //客户端缓存SocketIOClient
    private ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();
    //IP and Port
    private String IP = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG, "netty_socket_io_ip");
    private String PORT = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG, "netty_socket_io_port");
    @PostConstruct
    public void startServer() throws Exception {
        logger.info("socketIO服务端开始启动...");
        Configuration config = new Configuration();
        //ip
        config.setHostname(IP);
//        config.setHostname("10.0.101.227");
        //端口
        config.setPort(Integer.parseInt(PORT));
//        config.setPort(28860);
        // 连接认证，这里使用token更合适
        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData handshakeData) {
                return true;
            }
        });
        //最大请求内容长度
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        server = new SocketIOServer(config);

        /**
         * 查询我的订单
         */
        server.addEventListener("event_order_list", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws Exception {
                SocketResponseVo resVo = SocketResponseVo.getSuccessResponse();
                resVo.setData("OM2019070500407");
                resVo.setTypes(30);
                resVo.setMsg("Close Door Success");
                client.sendEvent(NettyConst.EVENT_CLOSE_DOOR,JSON.toJSON(resVo));
            }
        });

        /**
         * 添加连接监听事件，监听是否与客户端连接到服务器
         */
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient ioClient) {
                String address = ioClient.getRemoteAddress().toString();
                String clientIp = address.substring(1, address.indexOf(":"));
                logger.info("====有客户端连接:{}",clientIp);
                SocketResponseVo socketResVo = SocketResponseVo.getSuccessResponse("Open Success");
                try {
                    Map<String, List<String>> params = ioClient.getHandshakeData().getUrlParams();
                    logger.info("====客户端连接参数：{}", JSON.toJSONString(params));
                    //1,校验客户端连接参数
                    SocketResponseVo resVo = validateParam_OnConnect(ioClient,params);
                    if(!resVo.isSuccess()){
                        logger.error("====客户端连接,参数异常：{}",JSON.toJSON(resVo));
                        ioClient.sendEvent(NettyConst.EVENT_OPEN_DOOR,JSON.toJSON(resVo));
                        ioClient.disconnect();
                        return;
                    }
                    //2,缓存SocketIOClient
                    SessionVo sessionVo = createSessionVo(ioClient);

                    //3，开门前注册用户
                    MemberInfo memberInfo = vendstopService.registUser(sessionVo);
                    if(memberInfo == null){
                        logger.error("====开门时注册用户失败");
                        socketResVo.setSuccess(false);
                        socketResVo.setErrorCode(-1003);
                        socketResVo.setMsg("Please re-operate");
                        ioClient.sendEvent(NettyConst.EVENT_OPEN_DOOR, JSON.toJSON(socketResVo));
                        return;
                    }
                    sessionVo.setUserId(memberInfo.getId());
                    iCached.put(NettyConst.CACHE_USER_KEY + sessionVo.getUserId(), sessionVo, 24 * 60 * 60);

                    SocketIOClient temp = socketIoMap.get(sessionVo.getDeviceId() + "_" + sessionVo.getUserId());
                    if (null != temp) {
                        socketIoMap.get(sessionVo.getDeviceId() + "_" + sessionVo.getUserId()).disconnect();
                    }
                    socketIoMap.put(sessionVo.getDeviceId() + "_" + sessionVo.getUserId(), ioClient);

                    //3,建立连接之后，向设备发送开门指令
                    openDoor(sessionVo,ioClient);

                } catch (Exception e) {
                    logger.error(address + "客户端连接异常：{}", e);
                    ioClient.disconnect();
                }
            }
        });

        /**
         * 添加客户端断开连接事件
         */
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                String address = client.getRemoteAddress().toString();
                String userId = getParamsByName(client, "userId");//会员ID
                try {
                    logger.info(address + "----------------------" + userId + "-------------------------" + "客户端已断开连接");
                    SessionVo sessionVo = (SessionVo) iCached.get(NettyConst.CACHE_USER_KEY + userId);
                    if (null != sessionVo && client.getSessionId().toString().equals(sessionVo.getClientId())) {//集合移除连接
                        socketIoMap.remove(sessionVo.getDeviceId() + "_" + sessionVo.getUserId());
                    }
                } catch (Exception e) {
                    logger.error(address + "断开异常：{}", e);
                    try {
                        iCached.remove(NettyConst.CACHE_USER_KEY + userId);
                        //iCached.remove(address);
                    } catch (Exception e1) {
                        logger.error(address + "redis移除异常：{}", e1);
                    }
                }
            }
        });
        server.start();
        logger.info("socketIO服务端启动成功...");
    }


    public void stopServer() {
        if (server != null) {
            server.stop();
            logger.info("socketIO服务端已停止...");
            server = null;
        }
    }

    /**
     * 给所有连接客户端推送消息
     *
     * @param eventType 推送的事件类型
     * @param message   推送的内容
     */
    public void sendMessageToAllClient(String eventType, String message) {
        Collection<SocketIOClient> clients = server.getAllClients();
        for (SocketIOClient client : clients) {
            client.sendEvent(eventType, message);
        }
    }


    /**
     * 给具体的客户端推送消息
     *
     * @param mapKey    集合对应通道的key
     * @param eventType 推送事件类型
     * @param message   推送的消息内容
     */
    public void sendMessageToOneClient(String mapKey, String eventType, String message) {
        try {
            if (StringUtils.isNotBlank(mapKey)) {
                SocketIOClient client = socketIoMap.get(mapKey);
                if (client != null) {
                    client.sendEvent(eventType, message);
                }
            }
        } catch (Exception e) {
            logger.error("给指定客户端发送消息异常：{}", e);
        }
    }

    public static SocketIOServer getServer() {
        return server;
    }

    @Override
    public boolean configurationHandler(String keyName, String value) {
        try {
            startServer();
            return true;
        } catch (Exception e) {
            logger.error("socketIO服务启动异常：{}", e);
        }
        return false;
    }

    /**
     * 根据name从url中获取对应参数
     *
     * @param socketIOClient 通道
     * @param name           需要获取的参数名称
     * @return 如果有多个返回null
     */
    private String getParamsByName(SocketIOClient socketIOClient, String name) {
        String value = socketIOClient.getHandshakeData().getSingleUrlParam(name); //只能获取一个参数对应一个值，如果一个参数对应多个值返回null
        return value;
    }

    /**
     * 校验客户端连接参数
     * @param param
     * @return
     */
    private SocketResponseVo validateParam_OnConnect(SocketIOClient client,Map<String, List<String>> param){
        SocketResponseVo<String> response = SocketResponseVo.getSuccessResponse();
        String deviceId = getParamsByName(client, "deviceId");                    //设备ID
        String deviceCode = getParamsByName(client, "deviceCode");                //设备编号
        String types = getParamsByName(client, "types");                          //用户类型 补货员，普通购买用户
        String thirdUserId = getParamsByName(client, "userId");                   //第三方会员ID
        String userName = getParamsByName(client, "userName");                    //会员名
        String phoneNumber = getParamsByName(client, "phoneNumber");              //会员手机号

        //缺少设备参数
        if(StringUtil.isBlank(deviceId) || StringUtil.isBlank(deviceCode)){
            response.setSuccess(false);
            response.setErrorCode(-1001);
            response.setMsg("device parameters is error");
            return response;
        }

        if(StringUtil.isBlank(types)){
            response.setSuccess(false);
            response.setErrorCode(-1002);
            response.setMsg("userInfo parameters is error");
            return response;
        }

        if(StringUtil.isBlank(phoneNumber)){
            response.setSuccess(false);
            response.setErrorCode(-1002);
            response.setMsg("userInfo parameters is error");
            return response;
        }

        if(types.equals(BizTypeDefinitionEnum.MemberType.M1_MEMBER.getCode())){
            if(StringUtil.isBlank(thirdUserId) || StringUtil.isBlank(userName)){
                response.setSuccess(false);
                response.setErrorCode(-1002);
                response.setMsg("userInfo parameters is error");
                return response;
            }
        }

        //设备是否存在
        DeviceInfo device = deviceInfoService.selectByPrimaryKey(deviceId);
        if(device == null || !deviceCode.equals(device.getScode())){
            response.setSuccess(false);
            response.setErrorCode(-1003);
            response.setMsg("device does not exist");
            return response;
        }

        //若是补货员，补货员是否存在
        if(types.equals(BizTypeDefinitionEnum.MemberType.M2_MEMBER.getCode())){
            MemberInfo entity = new MemberInfo();
            entity.setSmobile(phoneNumber);
            entity.setSmerchantCode(device.getSmerchantCode());
            entity.setImemberType(20);
            entity.setIdelFlag(0);
            List<MemberInfo> memberInfos = memberInfoService.selectByEntityWhere(entity);
            if(CollectionUtils.isEmpty(memberInfos)){
                response.setMsg("replenisher does not exist");
                response.setSuccess(false);
                response.setErrorCode(-1001);
                return response;
            }
        }
        return response;
    }

    /**
     * 创建SessionVo
     * @param client
     * @return
     * @throws Exception
     */
    private SessionVo createSessionVo(SocketIOClient client)throws Exception{
        String address = client.getRemoteAddress().toString();
        String clientIp = address.substring(1, address.indexOf(":"));
        String deviceId = getParamsByName(client, "deviceId");                    //设备ID
        String deviceCode = getParamsByName(client, "deviceCode");                //设备编号
        String userName = getParamsByName(client, "userName");                    //会员名
        String phoneNumber = getParamsByName(client, "phoneNumber");              //会员手机号
        String thirdUserId = getParamsByName(client, "userId");                        //第三方会员ID
        String types = getParamsByName(client, "types");

        SessionVo sessionVo = new SessionVo();
        sessionVo.setThirdUserId(thirdUserId);
        sessionVo.setTypes(Integer.valueOf(types));
        sessionVo.setDeviceId(deviceId);
        sessionVo.setUserName(userName);
        sessionVo.setDeviceCode(deviceCode);
        sessionVo.setSip(clientIp);
        sessionVo.setClientId(client.getSessionId().toString());
        sessionVo.setIsourceClientType(BizTypeDefinitionEnum.RegClientType.VENDSTOP.getCode());
        sessionVo.setPhoneNumber(phoneNumber);

        return sessionVo;
    }

    private void openDoor(SessionVo sessionVo,SocketIOClient ioClient) {
        String address = ioClient.getRemoteAddress().toString();
        String clientIp = address.substring(1, address.indexOf(":"));
        SocketResponseVo socketResVo = SocketResponseVo.getSuccessResponse("Open Success");
        try {
            synchronized (interner.intern(sessionVo.getUserId())) {
                WebSocketConstant.OPEN_REQUEST_FLAG = false;
                iCached.remove(sessionVo.getDeviceId() + "_" + sessionVo.getUserId() + "_open_door");//删除主动开门自动开门的KEY
                iCached.remove("is_replenishment_scan_" + sessionVo.getUserId() );//删除是否补货员开门的KEY
                iCached.put(NettyConst.CACHE_USER_KEY + sessionVo.getUserId() , sessionVo, 24 * 60 * 60);//缓存时间1天

                //向终端设备，发送开门指令
                ResponseVo<String> responseVo = socketIOService.openRfidDoor(sessionVo.getDeviceId(), sessionVo.getUserId(), sessionVo.getTypes());
                if (!responseVo.isSuccess()) {
                    socketResVo.setSuccess(false);
                    socketResVo.setErrorCode(responseVo.getErrorCode());
                    socketResVo.setMsg(responseVo.getMsg());
                    ioClient.sendEvent(NettyConst.EVENT_OPEN_DOOR, JSON.toJSON(socketResVo));
                    return;
                }
                socketResVo.setMsg(responseVo.getMsg());
                ioClient.sendEvent(NettyConst.EVENT_OPEN_DOOR, JSON.toJSON(socketResVo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
