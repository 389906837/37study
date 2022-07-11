package com.cloud.cang.api.common.utils;

import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 2018/1/23.
 */
public class IPUtils {

    /**
     * 从通道上下文获取客户端IP和端口号
     *
     * @param ctx
     * @return
     */
    public static String getRemoteAddress(ChannelHandlerContext ctx) {
        String socketString = "";
        socketString = ctx.channel().remoteAddress().toString().substring(1);
        return socketString;
    }

    /**
     * 从socketio中获取socket地址
     * @param socketIOClient
     * @return
     */
    public static String getRemoteAddress(SocketIOClient socketIOClient) {
        String socketString = "";
        String socket = socketIOClient.getRemoteAddress().toString();
        if (StringUtils.isNotBlank(socket)) {
            return socket;
        }
        return socketString;
    }


    /**
     * 从通道上下文获取客户端IP
     *
     * @param socketIOClient
     * @return 客户端IP
     */
    public static String getIPString(SocketIOClient socketIOClient) {
        String ipString = "";
        String socketString = socketIOClient.getRemoteAddress().toString();
        socketString = getIPString(socketString);
        if (StringUtils.isNotBlank(socketString)) {
            ipString = socketString;
        }
        return ipString;
    }

    /**
     * 从socket字符串中截取ip地址
     * @param socketString
     * @return
     */
    public static String getIPString(String socketString) {
        String ipString = "";
        String ip = socketString.substring(1, socketString.indexOf(":"));
        if (StringUtils.isNotBlank(ip)) {
            ipString = ip;
        }
        return ipString;
    }

    /**
     * IP字符串切割成List
     * @param str 客户端ip字符串，拼接方式为IP+","+IP
     * @return IP的list集合
     */
    public static List<String> stringToList(String str) {
        if (StringUtils.isNotBlank(str)) {
            String[] array = str.split(",");
            List<String> list = Arrays.asList(array);
            return list;
        }
        return null;
    }

}
