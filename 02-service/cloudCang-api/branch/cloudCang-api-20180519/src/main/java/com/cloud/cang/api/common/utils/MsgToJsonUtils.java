package com.cloud.cang.api.common.utils;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.pojo.BaseRequestVo;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;

/**
 * 将消息对象封装为String
 * Created by Alex on 2018/4/13.
 */
public class MsgToJsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(MsgToJsonUtils.class);

    /**
     * 服务端发送给客户端消息对象
     *
     * @param success    true成功，false失败
     * @param data       返回数据data
     * @param deviceId   设备ID
     * @param userId     用户ID
     * @param methodType 方法
     * @return 返回转换后的字符串
     * @throws Exception
     */
    public static String setAndroidMsg(Boolean success,int code, Object data, String deviceId, String userId, String methodType) throws Exception {
        BaseRequestVo baseRequestVo = new BaseRequestVo();
        baseRequestVo.setCode(code);
        baseRequestVo.setDeviceId(deviceId);
        baseRequestVo.setUserId(userId);
        baseRequestVo.setMethodType(methodType);
        baseRequestVo.setSuccess(success);
        baseRequestVo.setData(JSON.toJSONString(data));
        String resultMsg = JSON.toJSONString(baseRequestVo) + System.lineSeparator();
        return resultMsg;
    }

    /**
     * 发送消息方法
     * @param deviceId
     * @param ctxMap
     * @param msg
     * @throws Exception
     */
    public static void ctxSendMsg(String deviceId, ConcurrentMap<String, ChannelHandlerContext> ctxMap, String msg) throws Exception {
        ChannelHandlerContext context = ctxMap.get(deviceId);
        if (null != context) {
            context.writeAndFlush(msg);
        }
        return;
    }

    /**
     * netty异步发送消息给设备
     * @param deviceId 设备ID
     * @param ctxMap 通道集合
     * @param success 成功/失败
     * @param data 发送数据
     * @param userId 用户ID（非必填）
     * @param methodType 消息类型
     */
    public static void asynSendMsgByCtxMap(final String deviceId, final ConcurrentMap<String, ChannelHandlerContext> ctxMap, final Boolean success,
                                           final Object data, final String userId, final String methodType) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：{}", deviceId);
                logger.info("用户ID：{}", userId);
                logger.info("方法类型：{}", methodType);
                logger.info("成功/失败：{}", success);
                logger.info("向设备发送消息内容：{}", JSON.toJSONString(data));
                try {
                    ChannelHandlerContext context = ctxMap.get(deviceId);
                    if (null != context) {
                        String msg = MsgToJsonUtils.setAndroidMsg(success, 200, data, deviceId, userId, methodType);
                        context.writeAndFlush(msg);
                    }
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }

    /**
     * 发送错误消息给售货机设备
     * @param deviceId
     * @param errorCode
     * @param ctxMap
     * @param data
     * @param userId
     * @param methodType
     */
    public static void asynSendErrorMsgByCtxMap(final String deviceId, final int errorCode, final ConcurrentMap<String, ChannelHandlerContext> ctxMap,
                                                final Object data, final String userId, final String methodType) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：{}", deviceId);
                logger.info("错误代码：{}", errorCode);
                logger.info("用户ID：{}", userId);
                logger.info("方法类型：{}", methodType);
                logger.info("成功/失败：{}", false);
                logger.info("向设备发送消息内容：{}", JSON.toJSONString(data));
                try {
                    ChannelHandlerContext context = ctxMap.get(deviceId);
                    if (null != context) {
                        BaseRequestVo baseRequestVo = new BaseRequestVo();
                        baseRequestVo.setDeviceId(deviceId);                // 设备ID
                        baseRequestVo.setUserId(userId);                    // 用户ID
                        baseRequestVo.setMethodType(methodType);            // 操作方法
                        baseRequestVo.setCode(errorCode);                   // 错误代码
                        baseRequestVo.setSuccess(false);                    // false
                        baseRequestVo.setData(JSON.toJSONString(data));     // 消息内容
                        String resultMsg = JSON.toJSONString(baseRequestVo) + System.lineSeparator();
                        context.writeAndFlush(resultMsg);
                    }
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }

    /**
     * netty异步发送消息给设备
     * @param deviceId 设备ID
     * @param ctx 通道
     * @param success 成功/失败
     * @param data 发送数据
     * @param userId 用户ID（非必填）
     * @param methodType 消息类型
     */
    public static void asynCtxSendMsgByCtx(final String deviceId, final ChannelHandlerContext ctx, final Boolean success,
                                      final Object data, final String userId, final String methodType) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：{}", deviceId);
                logger.info("用户ID：{}", userId);
                logger.info("方法类型：{}", methodType);
                logger.info("成功/失败：{}", success);
                logger.info("向设备发送消息内容：{}", JSON.toJSONString(data));
                try {
                    String msg = MsgToJsonUtils.setAndroidMsg(success, 200, data, deviceId, userId, methodType);
                    ctx.writeAndFlush(msg);
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }

    /**
     * 注册异常
     *
     * @param deviceId   设备ID
     * @param ctx        通道
     * @param success    结果
     * @param code       错误码
     * @param data       数据
     * @param userId     用户ID
     * @param methodType 方法类型
     */
    public static void asynSendRegisterErrorMsgByCtx(final String deviceId, final ChannelHandlerContext ctx, final Boolean success,
                                                     final int code, final Object data, final String userId, final String methodType) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：{}", deviceId);
                logger.info("用户ID：{}", userId);
                logger.info("方法类型：{}", methodType);
                logger.info("成功/失败：{}", success);
                logger.info("向设备发送消息内容：{}", JSON.toJSONString(data));
                try {
                    String msg = MsgToJsonUtils.setAndroidMsg(success, code, data, deviceId, userId, methodType);
                    ctx.writeAndFlush(msg);
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }


}
