package com.cloud.cang.api.common.utils;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.facePojo.FaceResponseVo;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 1.0
 * @ClassName: FaceMsgToJsonUtils
 * @Description: 将消息发送给人脸识别设备
 * @Author: zengzexiong
 * @Date: 2018年7月28日12:37:25
 */
public class FaceMsgToJsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(FaceMsgToJsonUtils.class);


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
    public static void SendFaceRegisterErrorMsgByCtx(final String deviceId, final ChannelHandlerContext ctx, final Boolean success,
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
                    String msg = setFaceMsgWithCode(success, code, data, deviceId, userId, methodType);
                    ctx.writeAndFlush(msg);
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }


    /**
     * 发送人脸识别设备注册成功消息
     * @param deviceId
     * @param ctx
     */
    public static void sendFaceRegisterSuccessMsgByCtx(final String deviceId, final ChannelHandlerContext ctx) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("设备ID：{}", deviceId);
                logger.info("socket地址 :{}", ctx.channel().remoteAddress());
                logger.info("方法类型：{}", "注册通道到服务器");
                logger.info("成功/失败：{}", true);
                logger.info("向设备发送消息内容：{}", "注册成功");
                try {
                    String msg = setFaceMsgWithCode(true, 200, "注册成功", deviceId, null, null);
                    ctx.writeAndFlush(msg);
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }

    /**
     * netty异步发送消息给人脸识别设备
     *
     * @param deviceId   设备ID
     * @param faceCtx     通道集合
     * @param success    成功/失败
     * @param data       发送数据
     * @param userId     用户ID（非必填）
     * @param methodType 消息类型
     */
    public static void SendFaceCommonNoCodeMsgByCtx(final String deviceId, final ChannelHandlerContext faceCtx, final Boolean success,
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
                    String msg = FaceMsgToJsonUtils.setFaceDeviceMsgNoCode(success, data, deviceId, userId, methodType);
                    if (null != faceCtx) {
                        faceCtx.writeAndFlush(msg);
                    } else {
                        logger.error("通道不存在，向AI设备发送消息失败售货机设备ID：{}", deviceId);
                    }
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }

    /**
     * 服务端发送给AI设备的消息
     *
     * @param success    true成功，false失败
     * @param data       返回数据data
     * @param deviceId   设备ID
     * @param userId     用户ID
     * @param methodType 方法
     * @return 返回转换后的字符串
     * @throws Exception
     */
    public static String setFaceDeviceMsgNoCode(Boolean success, Object data, String deviceId, String userId, String methodType) throws Exception {

        FaceResponseVo faceResponseVo = new FaceResponseVo();
        faceResponseVo.setDeviceId(deviceId);
        faceResponseVo.setUserId(userId);
        faceResponseVo.setMethodType(methodType);
        faceResponseVo.setSuccess(success);
        if (null == data) {
            faceResponseVo.setData(null);
        } else if (data.getClass() == String.class) {
            faceResponseVo.setData((String) data);
        } else {
            faceResponseVo.setData(JSON.toJSONString(data));
        }
        String resultMsg = JSON.toJSONString(faceResponseVo) + System.lineSeparator();
        return resultMsg;
    }

    /**
     * 服务端发送给人脸识别客户端消息对象
     *
     * @param success    true成功，false失败
     * @param code       返回码
     * @param data       返回数据data
     * @param deviceId   设备ID
     * @param userId     用户ID
     * @param methodType 方法
     * @return
     * @throws Exception
     */
    public static String setFaceMsgWithCode(Boolean success, int code, Object data, String deviceId, String userId, String methodType) throws Exception {
        FaceResponseVo faceResponseVo = new FaceResponseVo();
        faceResponseVo.setCode(code);
        faceResponseVo.setDeviceId(deviceId);
        faceResponseVo.setUserId(userId);
        faceResponseVo.setMethodType(methodType);
        faceResponseVo.setSuccess(success);
        if (null == data) {
            faceResponseVo.setData(null);
        } else {
            faceResponseVo.setData(JSON.toJSONString(data));
        }
        String resultMsg = JSON.toJSONString(faceResponseVo) + System.lineSeparator();
        return resultMsg;
    }

    /**
     * 向AI设备发送注册信息已经被使用
     *
     * @param aiId
     * @param ctx
     * @param success
     * @param code
     * @param data
     * @param userId
     * @param methodType
     */
    public static void sendRegisterInfoAlreadyUsed(String aiId, ChannelHandlerContext ctx, boolean success, final int code, final Object data, final String userId, final String methodType) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("AI设备ID：{}", aiId);
                logger.info("成功/失败：{}", success);
                logger.info("向设备发送消息内容：{}", JSON.toJSONString(data));
                try {
                    String msg = FaceMsgToJsonUtils.setFaceMsgWithCode(success, code, data, aiId, userId, methodType);
                    ChannelFuture channelFuture = ctx.writeAndFlush(msg);
                    channelFuture.addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            future.channel().close();
                        }
                    });
                } catch (Exception e) {
                    logger.error("向设备发送消息异常：{}", e);
                }
            }
        });
    }


}
