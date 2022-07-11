package spring;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.utils.StringUtil;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import static io.socket.client.Socket.EVENT_MESSAGE;

/**
 * socketio服务端
 * Created by Alex on 2018/3/28.
 */
public class SocketIoServer {
    private static Interner<String> interner = Interners.newWeakInterner();
    //服务端SocketServer
    private static SocketIOServer server = null;
    //客户端缓存SocketIOClient
    private ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();
    //IP and Port
    private String IP = "10.0.101.162";
    private String PORT = "28860";
    private String EVENT_NAME= "socketEvent";

    public static void main(String[] args) throws Exception{
        new SocketIoServer().startServer();
    }

    public void startServer() throws Exception {
        System.out.println("socketIO服务端开始启动...");
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

        server.addEventListener(EVENT_NAME, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws Exception {
                System.out.println("===================socketEvent==收到客户端消息========================");
                Map<String, List<String>> params = client.getHandshakeData().getUrlParams();    //获取客户端url参数
                System.out.println("收到客户端消息参数UrlParams："+ JSON.toJSONString(params));
                System.out.println("收到客户端消息内容："+data);
                client.sendEvent("socketEvent","你好，客户端！我是服务端");
            }
        });

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient ioClient) {
                System.out.println("===================客户端连接========================");
                String address = ioClient.getRemoteAddress().toString();
                System.out.println("====有客户端连接:"+address);
                try {
                    Map<String, List<String>> params = ioClient.getHandshakeData().getUrlParams();
                    System.out.println("====客户端连接参数："+ JSON.toJSONString(params));
                    ioClient.sendEvent("event_connection","连接成功");
                } catch (Exception e) {
                    System.out.println(address + "客户端连接异常："+ e);
                    ioClient.disconnect();
                }
            }
        });


        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                System.out.println("==================客户端断开连接========================");
                String address = client.getRemoteAddress().toString();
                System.out.println(address+"断开连接....");
            }
        });
        server.start();
        System.out.println("socketIO服务端启动成功...");
    }

    private String getParamsByName(SocketIOClient socketIOClient, String name) {
        String value = socketIOClient.getHandshakeData().getSingleUrlParam(name); //只能获取一个参数对应一个值，如果一个参数对应多个值返回null
        return value;
    }
}
