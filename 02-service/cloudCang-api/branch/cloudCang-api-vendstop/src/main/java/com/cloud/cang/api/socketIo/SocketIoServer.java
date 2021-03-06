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
 * socketio?????????
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
    //?????????SocketServer
    private static SocketIOServer server = null;
    //???????????????SocketIOClient
    private ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();
    //IP and Port
    private String IP = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG, "netty_socket_io_ip");
    private String PORT = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG, "netty_socket_io_port");

    @PostConstruct
    public void startServer() throws Exception {
        logger.info("socketIO?????????????????????...");
        Configuration config = new Configuration();
        //ip
        config.setHostname(IP);
        //??????
        config.setPort(Integer.parseInt(PORT));
        // ???????????????????????????token?????????
        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData handshakeData) {
                return true;
            }
        });
        //????????????????????????
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        server = new SocketIOServer(config);

        /**
         * ?????????????????????????????????????????????
         * 1.???????????????eventName????????????????????????????????????
         * 2.???????????????eventClase????????????????????????
         * 3.???????????????listener????????????????????????????????????????????????????????????eventClass??????
         */
        server.addEventListener(NettyConst.EVENT_OPEN_DOOR, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws Exception {
                SocketResponseVo socketResVo = SocketResponseVo.getSuccessResponse();
                String address = client.getRemoteAddress().toString();
                Map<String, Object> map = null;
                try{
                    map= JSON.parseObject(data, Map.class);
                    logger.info("????????????????????????{}", JSON.toJSONString(map));
                }catch (Exception e){
                    logger.error(address + "??????????????????:{}", e);
                    socketResVo.setSuccess(false);
                    socketResVo.setErrorCode(-1004);
                    socketResVo.setMsg("Data exception");
                    client.sendEvent(NettyConst.EVENT_OPEN_DOOR, JSON.toJSON(socketResVo));
                    return;
                }

                //??????????????????
                if (null == map || StringUtil.isBlank(map.get("userId")+"")
                    || StringUtil.isBlank(map.get("machineId")+"")) {
                    logger.error("{}?????????????????????????????????", address);
                    socketResVo.setSuccess(false);
                    socketResVo.setErrorCode(-1000);
                    socketResVo.setMsg("parameters is error");
                    client.sendEvent(NettyConst.EVENT_OPEN_DOOR,JSON.toJSON(socketResVo));
                    return;
                }

                Integer types = Integer.valueOf(map.get("types")+"");
                String thirdUserId = (String) map.get("userId");
                String deviceId = (String) map.get("machineId");

                //SessionVo??????
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

                //????????????
                try {
                    synchronized (interner.intern(userId)) {
                        iCached.remove(deviceId + "_" + userId + "_open_door");//?????????????????????????????????KEY
                        iCached.remove("is_replenishment_scan_" + userId);//??????????????????????????????KEY

                        //????????????????????????????????????
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
                    logger.error(address + "???????????????????????????:{}", e);
                    socketResVo.setSuccess(false);
                    socketResVo.setTypes(types);
                    socketResVo.setErrorCode(-1004);
                    socketResVo.setMsg("Server exception");
                    client.sendEvent(NettyConst.EVENT_OPEN_DOOR, JSON.toJSON(socketResVo));
                }
            }
        });


        /**
         * ?????????????????????????????????????????????????????????????????????
         */
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient ioClient) {
                String address = ioClient.getRemoteAddress().toString();
                String clientIp = address.substring(1, address.indexOf(":"));
                logger.info("====??????????????????:{}",clientIp);
                try {
                    Map<String, List<String>> params = ioClient.getHandshakeData().getUrlParams();
                    logger.info("====????????????????????????{}", JSON.toJSONString(params));
                    //1,???????????????????????????
                    SocketResponseVo resVo = validateParam_OnConnect(ioClient,params);
                    if(!resVo.isSuccess()){
                        logger.error("====???????????????,???????????????{}",JSON.toJSON(resVo));
                        ioClient.sendEvent(NettyConst.EVENT_CONNECTION,JSON.toJSON(resVo));
                        ioClient.disconnect();
                        return;
                    }

                    //2,??????SessionVo
                    SessionVo sessionVo = createSessionVo(ioClient);

                    //3,????????????
                    MemberInfo memberInfo = vendstopService.registUser(sessionVo);
                    if(memberInfo == null){
                        logger.error("====???????????????????????????");
                        return;
                    }

                    //4, SessionVo????????????
                    sessionVo.setUserId(memberInfo.getId());
                    sessionVo.setUserCode(memberInfo.getScode());
                    iCached.put(NettyConst.CACHE_USER_KEY + sessionVo.getUserId(), sessionVo, 24 * 60 * 60);
                    //???????????????(??????thirdUserId???????????????????????????id)
                    iCached.put(sessionVo.getThirdUserId()+sessionVo.getDeviceId(), sessionVo.getUserId(), 24 * 60 * 60);

                    //5,??????socketIoMap
                    SocketIOClient temp = socketIoMap.get(sessionVo.getDeviceId() + "_" + sessionVo.getUserId());
                    if (null != temp) {//???????????????
                        socketIoMap.get(sessionVo.getDeviceId() + "_" + sessionVo.getUserId()).disconnect();
                    }
                    socketIoMap.put(sessionVo.getDeviceId() + "_" + sessionVo.getUserId(), ioClient);

                    //6,???????????????????????????????????????
                    ioClient.sendEvent(NettyConst.EVENT_CONNECTION,JSON.toJSON(resVo));
                } catch (Exception e) {
                    logger.error(address + "????????????????????????{}", e);
                    ioClient.disconnect();
                }
            }
        });

        /**
         * ?????????????????????????????????
         */
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
                logger.info("====??????????????????????????????{}", JSON.toJSONString(params));

                String address = client.getRemoteAddress().toString();
                String thirdUserId = getParamsByName(client, "userId");//??????ID
                String deviceId = getParamsByName(client, "machineId");//??????ID
                String userId = "";
                try {
                    //SessionVo??????
                    userId = (String) iCached.get(thirdUserId+ deviceId);
                    if (StringUtil.isBlank(userId)) {
                        return;
                    }
                    logger.info(address + "----------------------" + userId + "-------------------------" + "????????????????????????");
                    SessionVo sessionVo = (SessionVo) iCached.get(NettyConst.CACHE_USER_KEY + userId);
                    if (null != sessionVo && client.getSessionId().toString().equals(sessionVo.getClientId())) {//??????????????????
                        socketIoMap.remove(sessionVo.getDeviceId() + "_" + sessionVo.getUserId());
                    }
                } catch (Exception e) {
                    logger.error(address + "???????????????{}", e);
                    try {
                        iCached.remove(NettyConst.CACHE_USER_KEY + userId);
                        //iCached.remove(address);
                    } catch (Exception e1) {
                        logger.error(address + "redis???????????????{}", e1);
                    }
                }
            }
        });
        server.start();
        logger.info("socketIO?????????????????????...");
    }


    public void stopServer() {
        if (server != null) {
            server.stop();
            logger.info("socketIO??????????????????...");
            server = null;
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param eventType ?????????????????????
     * @param message   ???????????????
     */
    public void sendMessageToAllClient(String eventType, String message) {
        Collection<SocketIOClient> clients = server.getAllClients();
        for (SocketIOClient client : clients) {
            client.sendEvent(eventType, message);
        }
    }


    /**
     * ?????????????????????????????????
     *
     * @param mapKey    ?????????????????????key
     * @param eventType ??????????????????
     * @param message   ?????????????????????
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
            logger.error("???????????????????????????????????????{}", e);
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
            logger.error("socketIO?????????????????????{}", e);
        }
        return false;
    }

    /**
     * ??????name???url?????????????????????
     *
     * @param socketIOClient ??????
     * @param name           ???????????????????????????
     * @return ?????????????????????null
     */
    private String getParamsByName(SocketIOClient socketIOClient, String name) {
        String value = socketIOClient.getHandshakeData().getSingleUrlParam(name); //?????????????????????????????????????????????????????????????????????????????????null
        return value;
    }

    /**
     * ???????????????????????????
     * @param param
     * @return
     */
    private SocketResponseVo validateParam_OnConnect(SocketIOClient client,Map<String, List<String>> param){
        SocketResponseVo<String> response = SocketResponseVo.getSuccessResponse();
        String types = getParamsByName(client, "types");                        //10=??????  20=?????????
        String mobileDeviceId = getParamsByName(client, "deviceId");              //mobile device id
        String machineCode = getParamsByName(client, "machineCode");               //?????????????????????
        String machineId = getParamsByName(client, "machineId");                  //???????????????ID
        String thirdUserId = getParamsByName(client, "userId");                   //APP??????ID
        String userCode = getParamsByName(client, "userCode");                    //APP????????????
        String userName = getParamsByName(client, "userName");                    //APP?????????
        String phoneNumber = getParamsByName(client, "phoneNumber");              //APP???????????????
        String app_apiKey = getParamsByName(client, "apiKey");                    //??????vendstop server??????
        String app_userAuthToken = getParamsByName(client, "userAuthToken");      //userAuthToken
        String app_sessionId = getParamsByName(client,"sessionId");

        //??????????????????
        if(StringUtil.isBlank(machineCode) || StringUtil.isBlank(machineId)){
            response.setSuccess(false);
            response.setErrorCode(-1001);
            response.setMsg("device parameters is error");
            return response;
        }
        //????????????????????????
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
        //??????userAuthToken
        if(StringUtil.isBlank(app_userAuthToken)){
            response.setSuccess(false);
            response.setErrorCode(-1003);
            response.setMsg("userAuthToken parameters is error");
            return response;
        }
        //??????apiKey
        if(StringUtil.isBlank(app_apiKey)){
            response.setSuccess(false);
            response.setErrorCode(-1004);
            response.setMsg("appKey parameters is error");
            return response;
        }
        return response;
    }

    /**
     * ??????SessionVo
     * @param client
     * @return
     * @throws Exception
     */
    private SessionVo createSessionVo(SocketIOClient client)throws Exception{
        String address = client.getRemoteAddress().toString();
        String clientIp = address.substring(1, address.indexOf(":"));
        String types = getParamsByName(client, "types");                          //10=??????  20=?????????
        String mobileDeviceId = getParamsByName(client, "deviceId");              //????????????ID
        String machineId = getParamsByName(client, "machineId");                  //???????????????ID
        String machineCode = getParamsByName(client, "machineCode");              //??????????????????
        String thirdUserId = getParamsByName(client, "userId");                   //??????ID
        String userName = getParamsByName(client, "userName");                    //?????????
        String phoneNumber = getParamsByName(client, "phoneNumber");              //???????????????
        String app_apiKey = getParamsByName(client, "apiKey");                    //??????vendstop server??????
        String app_userAuthToken = getParamsByName(client, "userAuthToken");      //userAuthToken
        String app_sessionId = getParamsByName(client,"sessionId");               //sessionId
        //??????SessionVo
        SessionVo sessionVo = new SessionVo();;
        sessionVo.setTypes(Integer.valueOf(types));
        sessionVo.setDeviceId(machineId); //???????????????ID
        sessionVo.setDeviceCode(machineCode);//??????????????????
        sessionVo.setApp_deviceId(mobileDeviceId);//????????????ID
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
