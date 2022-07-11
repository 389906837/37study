package com.cloud.cang.api.webSocket;

import com.cloud.cang.api.common.WebSocketConstant;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: WebSocket拦截器
 * @Author: zhouhong
 * @Date: 2018/3/5 20:19
 */
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    private static Logger logger = LoggerFactory.getLogger(SpringWebSocketHandlerInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        logger.debug("WebSocket拦截器开始");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest httpRequest = (ServletServerHttpRequest) request;
            String userId = httpRequest.getServletRequest().getParameter("userId");
            String deviceId = httpRequest.getServletRequest().getParameter("deviceId");
            if (StringUtil.isNotBlank(userId)) {
                logger.debug("获取sessionId：" + userId);
                attributes.put(WebSocketConstant.WEB_ID, userId);
            }
            if (StringUtil.isNotBlank(deviceId)) {
                logger.debug("获取deviceId：" + deviceId);
                attributes.put(WebSocketConstant.DEVICE_ID, deviceId);
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
