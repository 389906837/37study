package com.cloud.cang.api.common.utils;

import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
     * 从channel中获取socket地址
     * @param channel
     * @return
     */
    public static String getRemoteAddress(Channel channel) {
        String socketString = "";
        String socket = channel.remoteAddress().toString();
        if (StringUtils.isNotBlank(socket)) {
            return socket;
        }
        return socketString;
    }

    /**
     * 从ChannelHandlerContext 中获取IP地址
     * @param ctx
     * @return
     */
    public static String getIpString(ChannelHandlerContext ctx) {
        String socketString = "";
        String socket = ctx.channel().remoteAddress().toString();
        if (StringUtils.isNotBlank(socket)) {
            socketString = getIpString(socket);
        }
        return socketString;
    }

    /**
     * 从ChannelHandlerContext 中获取port号
     * @param ctx
     * @return
     */
    public static String getPort(ChannelHandlerContext ctx) {
        String portString = "";
        String socket = ctx.channel().remoteAddress().toString();
        if (StringUtils.isNotBlank(socket)) {
            portString = getPort(socket);
        }
        return portString;
    }

    /**
     * 从Channel中获取port号
     * @param channel
     * @return
     */
    public static String getPort(Channel channel) {
        String portString = "";
        String socket = channel.remoteAddress().toString();
        if (StringUtils.isNotBlank(socket)) {
            portString = getPort(socket);
        }
        return portString;
    }

    /**
     * 从channel中获取客户端IP地址
     *
     * @param channel
     * @return
     */
    public static String getIpString(Channel channel) {
        String ipString = "";
        String socketString = getIpString(channel.remoteAddress().toString());
        if (StringUtils.isNotBlank(socketString)) {
            ipString = socketString;
        }
        return ipString;
    }

    /**
     * 从channel中获取端口号
     * @param channel
     * @return
     */
    public static String getPortString(Channel channel) {
        String portString = "";
        String socketString = getPort(channel.remoteAddress().toString());
        if (StringUtils.isNotBlank(socketString)) {
            portString = socketString;
        }
        return portString;
    }

    /**
     * 从socket地址中截取端口号
     * @param ipString
     * @return
     */
    private static String getPort(String ipString) {
        String port = ipString.substring(ipString.indexOf(":") + 1);
        if (StringUtils.isNotBlank(port)) {
            return port;
        }
        return "";
    }


    /**
     * 从通道上下文获取客户端IP
     *
     * @param socketIOClient
     * @return 客户端IP
     */
    public static String getIpString(SocketIOClient socketIOClient) {
        String ipString = "";
        String socketString = socketIOClient.getRemoteAddress().toString();
        socketString = getIpString(socketString);
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
    public static String getIpString(String socketString) {
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

    /**
     * 从请求中获取IP地址
     *
     * @param request
     * @return
     */
    public static String getIpFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
