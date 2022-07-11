package com.cloud.cang.api.webSocket;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.WebSocketConstant;
import com.cloud.cang.api.netty.vo.send.ControlDeviceModel;
import com.cloud.cang.api.netty.vo.send.MessageBody;
import com.cloud.cang.api.webSocket.vo.WebVo;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.utils.StringUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: WebSocket 处理类
 * @Author: zhouhong
 * @Date: 2018/3/5 20:15
 */
@Service
public class SpringWebSocketHandler extends TextWebSocketHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(SpringWebSocketHandler.class);


    private ConcurrentMap<String, WebSocketSession> webMap = SingletonClientMap.newInstance().getWebMap();  //单例map,设备ID--会话
    private ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();  //单例map


    public SpringWebSocketHandler() {
    }


    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.debug("成功建立连接，当前数量:"+ webMap.size());
//        String userId = getDeviceID(session);
        String deviceId = getDeviceID(session);
        if (StringUtil.isNotBlank(deviceId)) {
            webMap.put(deviceId, session);
        }
        session.sendMessage(new TextMessage("成功建立socket连接"));
        LOGGER.debug("成功建立连接，当前数量:"+ webMap.size());
        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        //TextMessage returnMessage = new TextMessage("你将收到的离线");
        //session.sendMessage(returnMessage);
    }
    /**
     * 发送信息给指定用户
     * @param userId 用户标识Id
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String userId, TextMessage message) {
        if (webMap.get(userId) == null) return false;
        WebSocketSession session = webMap.get(userId);
        LOGGER.debug("发送消息给指定用户:" + session);
        if (!session.isOpen()) return false;
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            LOGGER.error("网络异常：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        LOGGER.debug("连接已关闭：" + closeStatus);
        webMap.remove(getDeviceID(session));
        LOGGER.debug("剩余在线用户"+ webMap.size());
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        TextMessage textMessage = new TextMessage("收到消息");
        session.sendMessage(textMessage);
        String msg = message.getPayload();
        WebVo webVo = null;
//        if (StringUtils.isNotBlank(msg)) {
//            try {
//                webVo = JSON.parseObject(msg, WebVo.class);
//            } catch (Exception e) {
//                LOGGER.debug("json 转换异常", e);
//            }
//        }

//        if (webVo != null) {
//            String deviceId = webVo.getDeviceId();
//            String userId = webVo.getUserId();
//            Integer type = webVo.getType(); //待修改
//            if (StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(userId)) {
////                session.getAttributes().put(WebSocketConstant.WEB_ID, deviceId); //将设备ID存到session
////                webMap.put(deviceId, session);  //存储websocket
//
//                if (ctxMap.containsKey(deviceId)) {
//                    ChannelHandlerContext context = ctxMap.get(deviceId);
//                    ControlDeviceModel controlDeviceModel = new ControlDeviceModel();
//                    MessageBody messageBody = new MessageBody();
//                    messageBody.setType(1);
//                    controlDeviceModel.setUserId(userId);//用户ID
//                    controlDeviceModel.setDeviceID(deviceId);//设备ID
//                    controlDeviceModel.setData(messageBody);//操作消息
//                    String resultMsg = JSON.toJSONString(controlDeviceModel) + System.lineSeparator();
//                    context.writeAndFlush(resultMsg);
//                } else {
//                    session.sendMessage(new TextMessage("设备状态异常，无法使用！"));
//                }
//            }
//            LOGGER.debug("消息为："+webVo.toString());
//        }

        ChannelHandlerContext context = ctxMap.get("1");
        ControlDeviceModel controlDeviceModel = new ControlDeviceModel();
        MessageBody messageBody = new MessageBody();
        messageBody.setType(1);
        controlDeviceModel.setUserId("AAAAA");//用户ID
        controlDeviceModel.setDeviceID("1");//设备ID
        controlDeviceModel.setData(messageBody);//操作消息
        String resultMsg = JSON.toJSONString(controlDeviceModel) + System.lineSeparator();
        context.writeAndFlush(resultMsg);

        System.out.println(message.getPayload());
        super.handleTextMessage(session, message);
    }

    /**
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        LOGGER.debug("连接出错，连接关闭");

        webMap.remove(session); //从存储的web中移除对应的websocket
    }

    public boolean supportsPartialMessages() {
        return false;
    }
    /**
     * 获取设备标识
     * @param session
     * @return
     */
    private String getDeviceID(WebSocketSession session) {
        try {
            return (String) session.getAttributes().get(WebSocketConstant.DEVICE_ID);
        } catch (Exception e) {
            return null;
        }
    }

}
