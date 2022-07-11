package com.cloud.cang.api.socketIo;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.WebSocketConstant;
import com.cloud.cang.api.common.utils.IPUtils;
import com.cloud.cang.api.common.utils.LogUtils;
import com.cloud.cang.api.netty.service.SocketIOService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * socketio服务端
 * Created by Alex on 2018/3/28.
 */
@Component
public class SocketIoServer extends AbsConfigurationHandler {

    private final static Logger logger = LoggerFactory.getLogger(SocketIoServer.class);
    private static SocketIOServer server = null;
    private ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();

    // 弱类型引用
    private static Interner<String> interner = Interners.newWeakInterner();

    @Autowired
    private ICached iCached;

    @Autowired
    private SocketIOService socketIOService;
    private String nettyIp = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG,"netty_socket_io_ip");    //数据字典配置
    private String nettyPort = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG,"netty_socket_io_port");
//    private String nettyIp = "10.0.101.225";
//    private String nettyPort = "28860";
    @PostConstruct
    public void startServer() throws Exception {
        logger.info("socketIO服务端开始启动...");
        Configuration config = new Configuration();
        //服务器主机ip
        config.setHostname(nettyIp);
        //解决跨域问题
        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData handshakeData) {
                return true;
            }
        });
        config.setPort(Integer.parseInt(nettyPort));//端口
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        server = new SocketIOServer(config);
        final String socketIO = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG,"socketIoEvent");//数据字典配置
        //监听客户端发送消息事件，advert_info为事件名称，自定义
        server.addEventListener(socketIO, String.class, new DataListener<String>(){
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws Exception {
                logger.debug("收到自定义事件数据：{}", data);
                String address = client.getRemoteAddress().toString(); //获取客户端连接的地址
                Map<String, List<String>> params = client.getHandshakeData().getUrlParams();//获取客户端url参数
                logger.info("客户端消息参数：{}", JSON.toJSONString(params));
                Map<String, Object> map = JSON.parseObject(data,Map.class);
                if(null == map) {
                    logger.error("客户端连接异常，参数不正确，正在断开重连:{}",address);
                    client.disconnect();
                }
                Integer types = (Integer) map.get("types"); //开门类型
                String userId = (String) map.get("userId");//会员ID
                SessionVo sessionVo = (SessionVo) iCached.get("soketIo_user_session_" + userId);
                /*
                String address = client.getRemoteAddress().toString(); //获取客户端连接的地址
                //String linkUserId = getParamsByName(client, "userId");
                SessionVo sessionVo = (SessionVo) iCached.get(address);*/
                if (null == sessionVo) {
                    logger.error("客户端连接异常，正在断开重连:{}",address);
                    client.disconnect();
                }

                try {
                    if (null != types && (types.intValue() == 10 || types.intValue() == 20)) {  //10 购物开门 20 补货开门 30装机开门
                        synchronized (interner.intern(userId)) {
                            if (WebSocketConstant.OPEN_REQUEST_FLAG) {
                                WebSocketConstant.OPEN_REQUEST_FLAG = false;
                                client.sendEvent(socketIO, "success");//发送收到消息
                                String deviceId = (String) map.get("deviceId");
                                String openDoorKey = (String) map.get("openDoorKey");
                                if (StringUtil.isNotBlank(openDoorKey)) {
                                    iCached.remove(openDoorKey);//删除本次开门的KEY
                                }
                                iCached.remove(deviceId + "_" + userId + "_open_door");//删除主动开门自动开门的KEY
                                iCached.remove("is_replenishment_scan_" + userId);//删除是否补货员开门的KEY
                                if (StringUtil.isNotBlank(userId) && sessionVo.getUserId().equals(userId)
                                        && StringUtil.isNotBlank(deviceId) && sessionVo.getDeviceId().equals(deviceId)) {
                                    sessionVo.setTypes(types);  //设置开门类型
                                    iCached.put("soketIo_user_session_" + userId, sessionVo, 24 * 60 * 60);//缓存时间1天
                                    ResponseVo<String> responseVo = socketIOService.openDoor(deviceId, userId, types);
                                    if (!responseVo.isSuccess()) {
                                        SocketResponseVo<String> responseVo1 = SocketResponseVo.getSuccessResponse();
                                        responseVo1.setSuccess(false);
                                        responseVo1.setTypes(types);
                                        responseVo1.setErrorCode(responseVo.getErrorCode());
                                        responseVo1.setMsg(responseVo.getMsg());
                                        client.sendEvent(socketIO, JSON.toJSON(responseVo1));
                                    }
                                }
                                ExecutorManager.getInstance().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        int djsTime = WebSocketConstant.djsTime;
                                        while (djsTime > 0) {
                                            djsTime--;
                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        WebSocketConstant.OPEN_REQUEST_FLAG = true;
                                    }
                                });
                            } else {
                                logger.info(address + "并发开锁忽略处理用户ID:{}", userId);
                                SocketResponseVo<String> responseVo1 = SocketResponseVo.getSuccessResponse();
                                responseVo1.setSuccess(false);
                                responseVo1.setTypes(types);
                                responseVo1.setErrorCode(ReturnCodeEnum.FAIL_DOOR_OPEN.getCode());
                                responseVo1.setMsg(ReturnCodeEnum.getNameByCode(responseVo1.getErrorCode()));
                                client.sendEvent(socketIO, JSON.toJSON(responseVo1));
                            }
                        }
                    }
                    //System.out.println(address + "：客户端：************" + "deviceId 为：" + deviceId + data);
                } catch (Exception e) {
                    logger.error(address + "处理自定义事情异常:{}", e);
                    SocketResponseVo<String> responseVo1 = SocketResponseVo.getSuccessResponse();
                    responseVo1.setSuccess(false);
                    responseVo1.setTypes(types);
                    responseVo1.setErrorCode(-1001);
                    responseVo1.setMsg("服务器异常");
                    client.sendEvent(socketIO, JSON.toJSON(responseVo1));
                }
            }
        });
        /**
         * 添加客户端连接事件
         */
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                //获取连接客户端的ip和端口
                String address = client.getRemoteAddress().toString();
                try {
                    String clientIp = address.substring(1, address.indexOf(":"));//获取设备ip
                    logger.info(clientIp+"-------------------------"+"客户端已连接");
                    Map<String, List<String>> params = client.getHandshakeData().getUrlParams();//获取客户端url参数
                    logger.info("客户端连接参数：{}", JSON.toJSONString(params));
                    String deviceId = getParamsByName(client, "deviceId");//设备ID
                    String deviceCode = getParamsByName(client, "deviceCode");//设备编号
                    String userId = getParamsByName(client, "userId");//会员ID
                    String userCode = getParamsByName(client, "userCode");//会员编号
                    String userName = getParamsByName(client, "userName");//会员名
                    String ip = getParamsByName(client, "clientIp");//客户端IP
                    String isourceClientType = getParamsByName(client,"isourceClientType");//来源客户端类型
                    SessionVo sessionVo = null;
                    if (StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(userId)) {
                        //根据IP端口存放用户数据redis
                        sessionVo = new SessionVo();
                        SessionVo temp = (SessionVo) iCached.get("soketIo_user_session_" + userId);
                        if (temp != null) {
                            sessionVo.setTypes(temp.getTypes());
                        }
                        sessionVo.setDeviceId(deviceId);
                        sessionVo.setUserId(userId);
                        sessionVo.setUserCode(userCode);
                        sessionVo.setUserName(userName);
                        sessionVo.setDeviceCode(deviceCode);
                        sessionVo.setSip(ip);
                        sessionVo.setClientId(client.getSessionId().toString());
                        if (StringUtil.isNotBlank(isourceClientType)) {
                            sessionVo.setIsourceClientType(Integer.parseInt(isourceClientType));
                        } else {
                            sessionVo.setIsourceClientType(30);
                        }
                        iCached.put("soketIo_user_session_" + userId, sessionVo, 24*60*60);//缓存时间1天
                    }
                    if (null != sessionVo) {
                        SocketIOClient temp = socketIoMap.get(deviceId+"_"+userId);
                        if (null != temp) {//通道已经存在
                            //iCached.remove(temp.getRemoteAddress().toString());//移除之前连接对象
                            socketIoMap.get(deviceId+"_"+userId).disconnect();//断开连接
                        }
                        socketIoMap.put(deviceId+"_"+userId, client);
                    } else {
                        client.disconnect();//断开连接
                    }
                    //给客户端发送消息
                    //client.sendEvent("advert_info",clientIp+"客户端你好，我是服务端，有什么能帮助你的？");
                } catch (Exception e) {
                    logger.error(address + "连接异常：{}", e);
                    client.disconnect();//断开连接
                }
            }
        });
        //添加客户端断开连接事件
        server.addDisconnectListener(new DisconnectListener(){
            @Override
            public void onDisconnect(SocketIOClient client) {
                String address = client.getRemoteAddress().toString();
                String userId = getParamsByName(client, "userId");//会员ID
                try {
                    logger.info(address + "----------------------" + userId + "-------------------------" + "客户端已断开连接");
                    SessionVo sessionVo = (SessionVo) iCached.get("soketIo_user_session_"+userId);
                    if (null != sessionVo && client.getSessionId().toString().equals(sessionVo.getClientId())) {//集合移除连接
                        socketIoMap.remove(sessionVo.getDeviceId() + "_" + sessionVo.getUserId());
                    }
                } catch (Exception e) {
                    logger.error(address + "断开异常：{}", e);
                    try {
                        iCached.remove("soketIo_user_session_" + userId);
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


    public void stopServer(){
        if(server != null){
            server.stop();
            logger.info("socketIO服务端已停止...");
            server = null;
        }
    }
    /**
     *  给所有连接客户端推送消息
     * @param eventType 推送的事件类型
     * @param message  推送的内容
     */
    public void sendMessageToAllClient(String eventType,String message){
        Collection<SocketIOClient> clients = server.getAllClients();
        for(SocketIOClient client: clients){
            client.sendEvent(eventType,message);
        }
    }


    /**
     * 给具体的客户端推送消息
     * @param mapKey 集合对应通道的key
     * @param eventType 推送事件类型
     * @param message 推送的消息内容
     */
    public void sendMessageToOneClient(String mapKey, String eventType, String message){
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
     * @param socketIOClient 通道
     * @param name 需要获取的参数名称
     * @return 如果有多个返回null
     */
    private String getParamsByName(SocketIOClient socketIOClient,String name) {
        String value = socketIOClient.getHandshakeData().getSingleUrlParam(name); //只能获取一个参数对应一个值，如果一个参数对应多个值返回null
        return value;
    }
}
