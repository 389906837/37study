package com.cloud.cang.api.antbox;


import com.cloud.cang.api.antbox.constant.CommandStatus;
import com.cloud.cang.api.antbox.exception.SendCommandException;
import com.cloud.cang.api.antbox.handler.BoxMessageHandler;
import com.cloud.cang.api.antbox.listener.MessageListener;
import com.cloud.cang.api.antbox.message.AntboxMessage;
import com.cloud.cang.api.antbox.protocol.AckableMessage;
import com.cloud.cang.api.antbox.protocol.AntboxAck;
import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动售货机服务<br>
 * NOTE: 与售货机进行SOCKET长连接，因此不能主动关闭连接。
 *
 * @author yong.ma
 * @see 0.0.1
 */
@Sharable
@Component
class BoxChannelHandler extends SimpleChannelInboundHandler<ToServerDataPkg>
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(BoxChannelHandler.class);

    @Autowired
    private ApplicationContext appContext;
    /**
     * 每种类型的设备消息需要用哪个的处理器来执行
     * GetBoxInfoResponse --> GetBoxInfoResponseHandler
     * SwitchOnMessage --> DoorOpenedMessageHandler
     * AcCheckMessag --> AcCheckMessageHandler
     * AntboxHeartbeat --> HeartbeatHandler
     * RebootMessage --> RebootMessageHandler
     * GetExpiredTimeResponse --> BoxMessageHandler
     * SwitchOffMessage --> DoorClosedMessageHandler
     * CardMessage --> CardMessageHandler
     * SetClockResponse --> SetClockResponseHandler
     * GetServerConfigCommand --> GetServerConfigCommandHandler
     * OpenTimeoutMessage --> OpenTimeoutMessageHandler
     * GetParamResponse --> GetParamResponseHanler
     * UnlockTimeoutMessage --> UnlockTimeoutMessageHandler
     * AntboxAck --> AckHandler
     */
    private final Map<Class<?>, BoxMessageHandler> parentHandlers = new HashMap<>();

    @PostConstruct
    private void init() {
        /**
         * {Class@3399} "class com.cloud.cang.api.antbox.message.GetBoxInfoResponse" -> {GetBoxInfoResponseListener@3400}
         * {Class@3401} "class com.cloud.cang.api.antbox.message.AcCheckMessage" -> {AcCheckMessageListener@3402}
         * {Class@3403} "class com.cloud.cang.api.antbox.message.UnlockTimeoutMessage" -> {UnlockTimeoutMessageListener@3404}
         * {Class@3405} "class com.cloud.cang.api.antbox.message.AntboxHeartbeat" -> {AntboxHeartbeatListener@3406}
         * {Class@3407} "class com.cloud.cang.api.antbox.message.SwitchOffMessage" -> {DoorClosedMessageListener@3408}
         * {Class@3409} "class com.cloud.cang.api.antbox.message.InventoryConclusion" -> {InventoryConclusionListener@3410}
         * {Class@3411} "class com.cloud.cang.api.antbox.message.SwitchOnMessage" -> {DoorOpenedMessageListener@3412}
         */
        Map<Class<?>, MessageListener> bizHandlers = scanMessageListeners();
        /**
         * {Class@3399} "class com.cloud.cang.api.antbox.message.GetBoxInfoResponse" -> {GetBoxInfoResponseHandler@3436}
         * {Class@3437} "class com.cloud.cang.api.antbox.protocol.AntboxAck" -> {AckHandler@3438}
         * {Class@3439} "class com.cloud.cang.api.antbox.message.RebootMessage" -> {RebootMessageHandler@3440}
         * {Class@3441} "class com.cloud.cang.api.antbox.message.GetParamResponse" -> {GetParamResponseHanler@3442}
         * {Class@3443} "class com.cloud.cang.api.antbox.message.SetClockResponse" -> {SetClockResponseHandler@3444}
         * {Class@3401} "class com.cloud.cang.api.antbox.message.AcCheckMessage" -> {AcCheckMessageHandler@3445}
         * {Class@3446} "class com.cloud.cang.api.antbox.message.OpenTimeoutMessage" -> {OpenTimeoutMessageHandler@3447}
         * {Class@3403} "class com.cloud.cang.api.antbox.message.UnlockTimeoutMessage" -> {UnlockTimeoutMessageHandler@3448}
         * {Class@3407} "class com.cloud.cang.api.antbox.message.SwitchOffMessage" -> {DoorClosedMessageHandler@3449}
         * {Class@3405} "class com.cloud.cang.api.antbox.message.AntboxHeartbeat" -> {HeartbeatHandler@3450}
         * {Class@3451} "class com.cloud.cang.api.antbox.message.CardMessage" -> {CardMessageHandler@3452}
         * {Class@3411} "class com.cloud.cang.api.antbox.message.SwitchOnMessage" -> {DoorOpenedMessageHandler@3453}
         * {Class@3454} "class com.cloud.cang.api.antbox.message.GetExpiredTimeResponse" -> {GetExpiredTimeResponseHandler@3455}
         * {Class@3456} "class com.cloud.cang.api.antbox.message.GetServerConfigCommand" -> {GetServerConfigCommandHandler@3457}
         */
        scanMessageParentHandlers(bizHandlers);
    }

    private void scanMessageParentHandlers(Map<Class<?>, MessageListener> bizHandlers) {
        LOG.info("scanMessageParentHandlers begin.");
        Map<String, BoxMessageHandler> handlerMap = appContext
                .getBeansOfType(BoxMessageHandler.class);
        if (handlerMap == null || handlerMap.isEmpty())
            return;
        for (BoxMessageHandler handler : handlerMap.values()) {
            ParameterizedType type = (ParameterizedType) handler.getClass().getGenericSuperclass();
            Type msgType = type.getActualTypeArguments()[0];
            Type bizMsgType = type.getActualTypeArguments()[1];
            Class<?> clz = (Class<?>) msgType;
            parentHandlers.put(clz, handler);
            LOG.info("register handler: {} for type: {}", handler.getClass().getSimpleName(),
                    clz.getSimpleName());

            MessageListener bizHandler = bizHandlers.get(bizMsgType);
            if (bizHandler != null)
                // 在handler处理器上注册监听器
                handler.registerBizHandler(bizHandler);
        }
        LOG.info("scanMessageParentHandlers done.");
    }

    private Map<Class<?>, MessageListener> scanMessageListeners() {
        LOG.info("scanMessageListeners begin.");
        /**
         * === appContext.getBeansOfType(MessageListener.class) ===
         * 获取所有实现MessageListener接口的Bean
         * 7个MessageListener (beanName --> instance)
         * AcCheckMessageListener (com.cloud.cang.api.antbox.listener)
         * GetBoxInfoResponseListener (com.cloud.cang.api.antbox.listener)
         * InventoryConclusionListener (com.cloud.cang.api.antbox.listener)
         * AntboxHeartbeatListener (com.cloud.cang.api.antbox.listener)
         * DoorOpenedMessageListener (com.cloud.cang.api.antbox.listener)
         * DoorClosedMessageListener (com.cloud.cang.api.antbox.listener)
         * UnlockTimeoutMessageListener (com.cloud.cang.api.antbox.listener)
         */
        Map<String, MessageListener> handlerMap = appContext.getBeansOfType(MessageListener.class);
        if (handlerMap == null || handlerMap.isEmpty())
            return null;

        Map<Class<?>, MessageListener> listeners = new HashMap<>();
        for (MessageListener handler : handlerMap.values()) {
            ParameterizedType type = (ParameterizedType) handler.getClass().getGenericInterfaces()[0];
            Type tp = type.getActualTypeArguments()[0];
            Class<?> clz = (Class<?>) tp;
            listeners.put(clz, handler);
            LOG.info("found bizHandler: {} for type: {}", handler.getClass().getSimpleName(),
                    clz.getSimpleName());
        }
        LOG.info("scanMessageListeners done.");
        return listeners;
    }

    /**
     * 处理客户端发来的消息（对命令的响应或者心跳）.<br>
     * 统一发送ACK、防重检查。
     */
    @Override
    protected void channelRead0(ChannelHandlerContext handlerCtx, ToServerDataPkg pkg)
            throws Exception {
        BoxContext ctx = AntboxUtil.getAntboxChannelContext(handlerCtx);
        ctx.pulsating();
        ctx.logInfo("receive message[{}]: {}", pkg.getClass().getSimpleName(), pkg);

        // 状态检查
        if (ctx.getBoxInfo().getBoxId() == null && pkg instanceof AntboxMessage) {
            ctx.logError("AntboxMessage ignored");
            return;
        }
        // 防重检查（非心跳ACK = AntboxHeartbeatAck）
        if (!(pkg instanceof AntboxAck)) {
            if (ctx.getClientRollCode() == pkg.getRollCode()) {
                ctx.logError("Duplicate message ignored");
                return;
            } else {
                ctx.setClientRollCode(pkg.getRollCode());
            }
        }
        // 普通
        if (pkg instanceof AckableMessage) {
            try {
                ctx.ack(pkg.ackObject(CommandStatus.SUCCESS));
            } catch (SendCommandException e) {
                ctx.logError(e, "ack exception.");
                return;
            }
        }

        // 处理消息
        BoxMessageHandler handler = parentHandlers.get(pkg.getClass());
        if (handler != null) {
            try {
                handler.handle(ctx, pkg);
            } catch (Exception e) {
                ctx.logError(e, "Message handle exception, error: {}", e.getMessage());
            }
        }

        // 发送消息已处理
        if (pkg instanceof AntboxMessage) {
            ctx.messageProcessed((AntboxMessage) pkg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext handlerCtx, Throwable cause) {
        BoxContext ctx;
        try {
            ctx = AntboxUtil.getAntboxChannelContext(handlerCtx);
            String ipAddress = AntboxUtil.getRemoteIp(handlerCtx);

            if (!AntboxUtil.isLvsListen(ipAddress)) {
                handlerCtx.close();
                LOG.error("exceptionCaught from ip : {} , cause : {},handlerCtx : {}", ipAddress, cause, handlerCtx);
                if (null != ctx) {
                    ctx.logError(cause, cause.getMessage());
                    ctx.logError("exceptionCaught from ip : {} , cause : {},handlerCtx : {}", ipAddress, cause, handlerCtx);
                }
            }
        } catch (Exception e) {
            LOG.error("exceptionCaught try catch , exception message : {}", e);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String ipAddress = AntboxUtil.getRemoteIp(ctx);
        LOG.info("======客户端连接======"+ipAddress);
        super.channelActive(ctx);
        BoxContext boxCtx = new BoxContext(ctx, appContext);
        AntboxUtil.bindAntboxChannelContext(ctx.channel(), boxCtx);
    }

}
