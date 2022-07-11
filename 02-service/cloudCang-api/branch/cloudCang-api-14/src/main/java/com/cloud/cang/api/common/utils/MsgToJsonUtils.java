package com.cloud.cang.api.common.utils;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.AndroidErrorCode;
import com.cloud.cang.api.common.NettyConstant;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.pojo.BaseRequestVo;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.ChannelHandlerContext;
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
    public static String setAndroidMsgWithCode(Boolean success, int code, Object data, String deviceId, String userId, String methodType) throws Exception {
        BaseRequestVo baseRequestVo = new BaseRequestVo();
        baseRequestVo.setCode(code);
        baseRequestVo.setDeviceId(deviceId);
        baseRequestVo.setUserId(userId);
        baseRequestVo.setMethodType(methodType);
        baseRequestVo.setSuccess(success);
        if (null == data) {
            baseRequestVo.setData(null);
        } else {
            baseRequestVo.setData(JSON.toJSONString(data));
        }
        String resultMsg = JSON.toJSONString(baseRequestVo) + System.lineSeparator();
        return resultMsg;
    }



    /**
     * 服务端发送给售货机设备的消息
     * @param success true成功，false失败
     * @param data 返回数据data
     * @param deviceId 设备ID
     * @param userId 用户ID
     * @param methodType 方法
     * @return 返回转换后的字符串
     * @throws Exception
     */
    public static String setAndroidMsgNoCode(Boolean success, Object data, String deviceId, String userId, String methodType) throws Exception {
        BaseRequestVo baseRequestVo = new BaseRequestVo();
        baseRequestVo.setDeviceId(deviceId);
        baseRequestVo.setUserId(userId);
        baseRequestVo.setMethodType(methodType);
        baseRequestVo.setSuccess(success);
        if (null == data) {
            baseRequestVo.setData(null);
        } else if (data.getClass() == String.class) {
            baseRequestVo.setData((String) data);
        } else {
            baseRequestVo.setData(JSON.toJSONString(data));
        }
        String resultMsg = JSON.toJSONString(baseRequestVo) + System.lineSeparator();
        return resultMsg;
    }

    /**
     *
     * @param success
     * @param data
     * @param deviceId
     * @param userId
     * @param methodType
     * @param openSource
     * @return
     * @throws Exception
     */
    public static String setAndroidMsgNoCode(Boolean success, Object data, String deviceId, String userId, String methodType, Integer openSource) throws Exception {
        BaseRequestVo baseRequestVo = new BaseRequestVo();
        baseRequestVo.setDeviceId(deviceId);
        baseRequestVo.setUserId(userId);
        baseRequestVo.setMethodType(methodType);
        baseRequestVo.setSuccess(success);
        baseRequestVo.setOpenSource(openSource);
        if (null == data) {
            baseRequestVo.setData(null);
        } else if (data.getClass() == String.class) {
            baseRequestVo.setData((String) data);
        } else {
            baseRequestVo.setData(JSON.toJSONString(data));
        }
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
     *
     * @param deviceId   设备ID
     * @param ctxMap     通道集合
     * @param success    成功/失败
     * @param data       发送数据
     * @param userId     用户ID（非必填）
     * @param methodType 消息类型
     */
    public static void asynSendNoCodeMsgByCtxMap(final String deviceId, final ConcurrentMap<String, ChannelHandlerContext> ctxMap, final Boolean success,
                                                 final Object data, final String userId, final String methodType) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：" + deviceId + ",用户ID：" + userId + ",方法类型：" + methodType + ",消息boolean类型：" + success);
                logger.info("向设备发送消息内容：{}", JSON.toJSONString(data));
                try {
                    ChannelHandlerContext context = ctxMap.get(deviceId);
                    if (null != context) {
                        String msg = MsgToJsonUtils.setAndroidMsgNoCode(success, data, deviceId, userId, methodType);
                        context.writeAndFlush(msg);
                    } else {
                        logger.error("设备ID：{}离线无法发送消息，消息类型：{}", deviceId, methodType);
                    }
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }

    /**
     * netty异步发送消息给设备
     *
     * @param deviceId   设备ID
     * @param deviceCode 设备编号
     * @param ctxMap     通道集合
     * @param success    成功/失败
     * @param data       发送数据
     * @param userId     用户ID（非必填）
     * @param methodType 消息类型
     */
    public static void asynSendNoCodeMsgByCtxMap(final String deviceId, final String deviceCode, final ConcurrentMap<String, ChannelHandlerContext> ctxMap, final Boolean success,
                                                 final Object data, final String userId, final String methodType) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：" + deviceId + "设备编号：" + deviceCode + ",用户ID：" + userId + ",方法类型：" + methodType + ",成功/失败：" + success);
                logger.info("向设备发送消息内容：{}", JSON.toJSONString(data));
                try {
                    ChannelHandlerContext context = ctxMap.get(deviceId);
                    if (null != context) {
                        String msg = MsgToJsonUtils.setAndroidMsgNoCode(success, data, deviceId, userId, methodType);
                        context.writeAndFlush(msg);
                    }
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }

    /**
     * 带有开门来源
     *
     * @param deviceId   设备ID
     * @param ctxMap     通道集合
     * @param success    成功/失败
     * @param data       数据模型
     * @param userId     用户ID
     * @param methodType 方法
     * @param openSource 开门来源
     */
    public static void asynSendNoCodeMsgByCtxMap(final String deviceId, final ConcurrentMap<String, ChannelHandlerContext> ctxMap, final Boolean success,
                                                 final Object data, final String userId, final String methodType, final Integer openSource) {
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
                        String msg = MsgToJsonUtils.setAndroidMsgNoCode(success, data, deviceId, userId, methodType, openSource);
                        context.writeAndFlush(msg);
                    }
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }

    /**
     * 给手机端发送信息
     *  @param type           操作类型
     * @param data           数据
     * @param errorCode      错误码
     * @param msg            错误信息
     * @param success        结果
     * @param socketIOClient 手机端通道
     * @param userId
     */
    public static void asynSendMsgToCellPhone(final Integer type, final String data, final Integer errorCode, final String msg, final boolean success, final SocketIOClient socketIOClient, final String userId) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                SocketResponseVo<String> socketResponseVo = new SocketResponseVo();
                socketResponseVo.setTypes(type);
                socketResponseVo.setData(data);
                socketResponseVo.setMsg(msg);
                socketResponseVo.setSuccess(success);
                if (null != errorCode) {
                    socketResponseVo.setErrorCode(errorCode);
                } else {
                    socketResponseVo.setErrorCode(AndroidErrorCode.ERROR_NULL_CODE.getCode());
                }
                logger.info("向手机客户端ID:{}发送实体消息内容：{}，类型：{}，错误码：{}，错误消息：{}", userId, data, type, errorCode, msg);
                try {
                    if (null != socketIOClient) {
                        socketIOClient.sendEvent(NettyConst.SOCKETIO_EVENT, JSON.toJSON(socketResponseVo));
                    } else {
                        logger.error("手机端ID:{}离线，无法发送消息", userId);
                    }
                } catch (Exception e) {
                    logger.error("向手机客户端ID:{},发送消息异常：{}", userId, e);
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
    public static void asynSendMsgByCtx(final String deviceId, final ChannelHandlerContext ctx, final Boolean success,
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
                    String msg = MsgToJsonUtils.setAndroidMsgNoCode(success, data, deviceId, userId, methodType);
                    ctx.writeAndFlush(msg);
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }

    /**
     * netty异步发送消息给设备
     *
     * @param deviceId   设备ID
     * @param ctx        通道
     * @param success    成功/失败
     * @param data       发送数据
     * @param userId     用户ID（非必填）
     * @param methodType 消息类型
     * @param openSource 开门来源
     */
    public static void asynSendMsgByCtx(final String deviceId, final ChannelHandlerContext ctx, final Boolean success,
                                        final Object data, final String userId, final String methodType, final Integer openSource) {

        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：{}", deviceId);
                logger.info("用户ID：{}", userId);
                logger.info("方法类型：{}", methodType);
                logger.info("成功/失败：{}", success);
                logger.info("向设备发送消息内容：{}", JSON.toJSONString(data));
                try {
                    String msg = MsgToJsonUtils.setAndroidMsgNoCode(success, data, deviceId, userId, methodType, openSource);
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
     * 201, "该注册信息已经被使用"
     * 202, "没有该设备注册信息"
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
                    String msg = MsgToJsonUtils.setAndroidMsgWithCode(success, code, data, deviceId, userId, methodType);
                    ctx.writeAndFlush(msg);
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }


    /**
     * 心跳消息，200
     * @param deviceId
     * @param ctx
     */
    public static void asynCtxSendHeartBeatByCtx(final String deviceId, final ChannelHandlerContext ctx) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：{}", deviceId);
                logger.info("方法类型：{}", "心跳");
                logger.info("成功/失败：{}", true);
                logger.info("向设备发送消息内容：{}", "心跳消息成功");
                try {
                    String msg = MsgToJsonUtils.setAndroidMsgWithCode(true, 200, "HeartBeat Success", deviceId, null, null);
                    ctx.writeAndFlush(msg);
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });

    }

    /**
     * 发送注册成功消息
     * @param deviceId
     * @param ctx
     */
    public static void asynSendRegisterSuccessMsgByCtx(final String deviceId, final ChannelHandlerContext ctx) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：{}", deviceId);
                logger.info("socket地址 :{}", ctx.channel().remoteAddress());
                logger.info("方法类型：{}", "注册通道到服务器");
                logger.info("成功/失败：{}", true);
                logger.info("向设备发送消息内容：{}", "注册成功");
                try {
                    String msg = MsgToJsonUtils.setAndroidMsgWithCode(true, 200, "注册成功", deviceId, null, null);
                    ctx.writeAndFlush(msg);
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }


    /**
     * 向设备客户端发送请求消息
     * @param deviceInfo 设备返回消息
     * @param ctx 通道参数
     * @param methodType 消息类型
     * @param data
     */
    public static void asynSendRequestMsgByCtx(DeviceInfo deviceInfo, ChannelHandlerContext ctx,
                                        String methodType, Object data) {
        try {
            BaseRequestVo baseRequestVo = new BaseRequestVo();
            //baseRequestVo.setCode(ReturnCodeEnum.SUCCESS.getCode());
            //baseRequestVo.setErrorMsg(ReturnCodeEnum.getNameByCode(baseRequestVo.getCode()));
            baseRequestVo.setDeviceId(deviceInfo.getId());
            baseRequestVo.setMethodType(methodType);
            baseRequestVo.setSuccess(true);
          /*  String token = (String) iCached.get(RedisConst.NETTY_DEVICE_TOKEN + deviceInfo.getScode());
            baseRequestVo.setToken(token);*/
            if (null != data) {
                baseRequestVo.setData(JSON.toJSONString(data));
            }
            String resultMsg = JSON.toJSONString(baseRequestVo) + NettyConstant.lineSeparator;
            logger.info("向客户端发送请求消息，设备编号：{}, 消息内容：{}",deviceInfo.getScode(), resultMsg);
            ctx.writeAndFlush(resultMsg);
        } catch (Exception e) {
            logger.error("向客户端发送请求消息异常：{}", e);
        }
    }

}
