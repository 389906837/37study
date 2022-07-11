package com.cloud.cang.api.singleton;

import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @desc 存放长连接map
 * @author zengzexiong
 * @date 2018年3月23日09:41:11
 */
public class SingletonClientMap {

    private ConcurrentMap<String, ChannelHandlerContext> ctxMap;    //设备ID--netty通道

    private ConcurrentMap<String, ChannelHandlerContext> aiFaceMap;    //人脸识别设备ID--netty通道

    private ConcurrentMap<String, WebSocketSession> webMap;        //设备ID--webSocket通道

    private ConcurrentMap<String, SocketIOClient> socketIoMap;        //设备ID--socketIo通道

    private ChannelGroup channelGroup;        //所有通道

    private ChannelGroup aiFaceChannelGroup;    // 人脸识别所有通道

    private static SingletonClientMap instance = new SingletonClientMap();

    private SingletonClientMap() {
        webMap = new ConcurrentHashMap<>();
        ctxMap = new ConcurrentHashMap<>();
        aiFaceMap = new ConcurrentHashMap<>();
        socketIoMap = new ConcurrentHashMap<>();
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        aiFaceChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    public static SingletonClientMap newInstance() {
        return instance;
    }

    public ConcurrentMap<String, WebSocketSession> getWebMap() {
        return webMap;
    }

    public void setWebMap(ConcurrentMap<String, WebSocketSession> webMap) {
        this.webMap = webMap;
    }

    public ConcurrentMap<String, ChannelHandlerContext> getCtxMap() {
        return ctxMap;
    }

    public void setCtxMap(ConcurrentMap<String, ChannelHandlerContext> ctxMap) {
        this.ctxMap = ctxMap;
    }

    public ConcurrentMap<String, SocketIOClient> getSocketIoMap() {
        return socketIoMap;
    }

    public void setSocketIoMap(ConcurrentMap<String, SocketIOClient> socketIoMap) {
        this.socketIoMap = socketIoMap;
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public void setChannelGroup(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    public ConcurrentMap<String, ChannelHandlerContext> getAiFaceMap() {
        return aiFaceMap;
    }

    public void setAiFaceMap(ConcurrentMap<String, ChannelHandlerContext> aiFaceMap) {
        this.aiFaceMap = aiFaceMap;
    }

    public ChannelGroup getAiFaceChannelGroup() {
        return aiFaceChannelGroup;
    }

    public void setAiFaceChannelGroup(ChannelGroup aiFaceChannelGroup) {
        this.aiFaceChannelGroup = aiFaceChannelGroup;
    }
}
