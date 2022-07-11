package com.cloud.cang.api.socketIo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.WebSocketConstant;
import com.cloud.cang.api.hy.service.VendstopService;
import com.cloud.cang.api.netty.service.SocketIOService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.jy.CreatOrderResult;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
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
        //端口
        config.setPort(Integer.parseInt(PORT));
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
         * 添加监听事件，监听客户端的事件
         * 1.第一个参数eventName需要与客户端的事件要一致
         * 2.第二个参数eventClase是传输的数据类型
         * 3.第三个参数listener是用于接收客户端传的数据，数据类型需要与eventClass一致
         */
        server.addEventListener(NettyConst.EVENT_OPEN_DOOR, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws Exception {
                SocketResponseVo socketResVo = SocketResponseVo.getSuccessResponse();
                String address = client.getRemoteAddress().toString();
                Map<String, Object> map = null;
                try{
                    map= JSON.parseObject(data, Map.class);
                    logger.info("客户端开门请求：{}", JSON.toJSONString(map));
                }catch (Exception e){
                    logger.error(address + "请求数据异常:{}", e);
                    socketResVo.setSuccess(false);
                    socketResVo.setErrorCode(-1004);
                    socketResVo.setMsg("Data exception");
                    client.sendEvent(NettyConst.EVENT_OPEN_DOOR, JSON.toJSON(socketResVo));
                    return;
                }

                //请求参数校验
                if (null == map || StringUtil.isBlank(map.get("userId")+"")
                    || StringUtil.isBlank(map.get("machineId")+"")) {
                    logger.error("{}客户端开门请求参数异常", address);
                    socketResVo.setSuccess(false);
                    socketResVo.setErrorCode(-1000);
                    socketResVo.setMsg("parameters is error");
                    client.sendEvent(NettyConst.EVENT_OPEN_DOOR,JSON.toJSON(socketResVo));
                    return;
                }

                Integer types = Integer.valueOf(map.get("types")+"");
                String thirdUserId = (String) map.get("userId");
                String deviceId = (String) map.get("machineId");

                //SessionVo超时
                String userId = (String) iCached.get(thirdUserId+ deviceId);
                if (StringUtil.isBlank(userId)) {
                    logger.error("session is overtime,Please scan QRcode again..{}", thirdUserId,deviceId);
                    socketResVo.setSuccess(false);
                    socketResVo.setErrorCode(-1002);
                    socketResVo.setMsg("session is overtime,Please scan QRcode again.");
                    client.sendEvent(NettyConst.EVENT_OPEN_DOOR,JSON.toJSON(socketResVo));
                    client.disconnect();
                    return;
                }

                //业务处理
                try {
                    synchronized (interner.intern(userId)) {
                        iCached.remove(deviceId + "_" + userId + "_open_door");//删除主动开门自动开门的KEY
                        iCached.remove("is_replenishment_scan_" + userId);//删除是否补货员开门的KEY

                        //向终端设备，发送开门指令
                        ResponseVo<String> responseVo = socketIOService.openDoor(deviceId, userId, types);
                        if (!responseVo.isSuccess()) {
                            socketResVo.setSuccess(false);
                            socketResVo.setTypes(types);
                            socketResVo.setErrorCode(responseVo.getErrorCode());
                            socketResVo.setMsg(responseVo.getMsg());
                            client.sendEvent(NettyConst.EVENT_OPEN_DOOR, JSON.toJSON(socketResVo));
                            return;
                        }
                    }
                } catch (Exception e) {
                    logger.error(address + "处理自定义事情异常:{}", e);
                    socketResVo.setSuccess(false);
                    socketResVo.setTypes(types);
                    socketResVo.setErrorCode(-1004);
                    socketResVo.setMsg("Server exception");
                    client.sendEvent(NettyConst.EVENT_OPEN_DOOR, JSON.toJSON(socketResVo));
                }
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
                try {
                    Map<String, List<String>> params = ioClient.getHandshakeData().getUrlParams();
                    logger.info("====客户端连接参数：{}", JSON.toJSONString(params));
                    //1,校验客户端连接参数
                    SocketResponseVo resVo = validateParam_OnConnect(ioClient,params);
                    if(!resVo.isSuccess()){
                        logger.error("====客户端连接,参数异常：{}",JSON.toJSON(resVo));
                        ioClient.sendEvent(NettyConst.EVENT_CONNECTION,JSON.toJSON(resVo));
                        ioClient.disconnect();
                        return;
                    }

                    //2,构建SessionVo
                    SessionVo sessionVo = createSessionVo(ioClient);

                    //3,注册用户
                    MemberInfo memberInfo = vendstopService.registUser(sessionVo);
                    if(memberInfo == null){
                        logger.error("====开门时注册用户失败");
                        return;
                    }

                    //4, SessionVo放入缓存
                    sessionVo.setUserId(memberInfo.getId());
                    sessionVo.setUserCode(memberInfo.getScode());
                    iCached.put(NettyConst.CACHE_USER_KEY + sessionVo.getUserId(), sessionVo, 24 * 60 * 60);
                    //开门时用到(根据thirdUserId查询我们系统的用户id)
                    iCached.put(sessionVo.getThirdUserId()+sessionVo.getDeviceId(), sessionVo.getUserId(), 24 * 60 * 60);

                    //5,维护socketIoMap
                    SocketIOClient temp = socketIoMap.get(sessionVo.getDeviceId() + "_" + sessionVo.getUserId());
                    if (null != temp) {//断开旧连接
                        socketIoMap.get(sessionVo.getDeviceId() + "_" + sessionVo.getUserId()).disconnect();
                    }
                    socketIoMap.put(sessionVo.getDeviceId() + "_" + sessionVo.getUserId(), ioClient);

                    //6,连接成功，给客户端发送消息
                    ioClient.sendEvent(NettyConst.EVENT_CONNECTION,JSON.toJSON(resVo));
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
                Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
                logger.info("====客户端断开连接参数：{}", JSON.toJSONString(params));

                String address = client.getRemoteAddress().toString();
                String thirdUserId = getParamsByName(client, "userId");//会员ID
                String deviceId = getParamsByName(client, "machineId");//会员ID
                String userId = "";
                try {
                    //SessionVo超时
                    userId = (String) iCached.get(thirdUserId+ deviceId);
                    if (StringUtil.isBlank(userId)) {
                        return;
                    }
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
        String types = getParamsByName(client, "types");                        //10=购物  20=补货员
        String mobileDeviceId = getParamsByName(client, "deviceId");              //mobile device id
        String machineCode = getParamsByName(client, "machineCode");               //售货机设备编号
        String machineId = getParamsByName(client, "machineId");                  //售货机设备ID
        String thirdUserId = getParamsByName(client, "userId");                   //APP会员ID
        String userCode = getParamsByName(client, "userCode");                    //APP会员编号
        String userName = getParamsByName(client, "userName");                    //APP会员名
        String phoneNumber = getParamsByName(client, "phoneNumber");              //APP会员手机号
        String app_apiKey = getParamsByName(client, "apiKey");                    //调用vendstop server需要
        String app_userAuthToken = getParamsByName(client, "userAuthToken");      //userAuthToken
        String app_sessionId = getParamsByName(client,"sessionId");

        //缺少设备参数
        if(StringUtil.isBlank(machineCode) || StringUtil.isBlank(machineId)){
            response.setSuccess(false);
            response.setErrorCode(-1001);
            response.setMsg("device parameters is error");
            return response;
        }
        //缺少用户信息参数
        if(StringUtil.isBlank(thirdUserId) || StringUtil.isBlank(types)
                || StringUtil.isBlank(userName) ||StringUtil.isBlank(phoneNumber)){
            response.setSuccess(false);
            response.setErrorCode(-1002);
            response.setMsg("userInfo parameters is error");
            return response;
        }
        if(!types.equals("10") && !types.equals("20")){
            response.setSuccess(false);
            response.setErrorCode(-1002);
            response.setMsg("types parameters is error");
            return response;
        }
        //缺少userAuthToken
        if(StringUtil.isBlank(app_userAuthToken)){
            response.setSuccess(false);
            response.setErrorCode(-1003);
            response.setMsg("userAuthToken parameters is error");
            return response;
        }
        //缺少apiKey
        if(StringUtil.isBlank(app_apiKey)){
            response.setSuccess(false);
            response.setErrorCode(-1004);
            response.setMsg("appKey parameters is error");
            return response;
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
        String types = getParamsByName(client, "types");                          //10=购物  20=补货员
        String mobileDeviceId = getParamsByName(client, "deviceId");              //手机设备ID
        String machineId = getParamsByName(client, "machineId");                  //售货机设备ID
        String machineCode = getParamsByName(client, "machineCode");              //售货机设备号
        String thirdUserId = getParamsByName(client, "userId");                   //会员ID
        String userName = getParamsByName(client, "userName");                    //会员名
        String phoneNumber = getParamsByName(client, "phoneNumber");              //会员手机号
        String app_apiKey = getParamsByName(client, "apiKey");                    //调用vendstop server需要
        String app_userAuthToken = getParamsByName(client, "userAuthToken");      //userAuthToken
        String app_sessionId = getParamsByName(client,"sessionId");               //sessionId
        //构建SessionVo
        SessionVo sessionVo = new SessionVo();;
        sessionVo.setTypes(Integer.valueOf(types));
        sessionVo.setDeviceId(machineId); //售货机设备ID
        sessionVo.setDeviceCode(machineCode);//售货机设备号
        sessionVo.setApp_deviceId(mobileDeviceId);//手机设备ID
        sessionVo.setUserName(userName);
        sessionVo.setSip(clientIp);
        sessionVo.setClientId(client.getSessionId().toString());
        sessionVo.setIsourceClientType(BizTypeDefinitionEnum.RegClientType.VENDSTOP.getCode());
        sessionVo.setApp_apiKey(app_apiKey);
        sessionVo.setApp_userAuthToken(app_userAuthToken);
        sessionVo.setApp_sessionId(app_sessionId);
        sessionVo.setPhoneNumber(phoneNumber);
        sessionVo.setThirdUserId(thirdUserId);
        return sessionVo;
    }
}
