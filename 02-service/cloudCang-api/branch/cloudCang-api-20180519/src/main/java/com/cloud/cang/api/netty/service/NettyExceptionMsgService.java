package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.AndroidErrorCode;
import com.cloud.cang.api.common.utils.CommonUtils;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.pojo.BaseResponseVo;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @version 1.0
 * @ClassName NettyExceptionMsgService
 * @Description Netty异常消息处理类
 * @Author zengzexiong
 * @Date 2018年5月7日13:56:34
 */
@Service("NettyExceptionMsgService")
public class NettyExceptionMsgService {

    @Autowired
    private ICached iCached;

    @Autowired
    DeviceInfoService deviceInfoService;

    private static final Logger logger = LoggerFactory.getLogger(NettyExceptionMsgService.class);
    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();  //netty通道
    private static ConcurrentMap<String, SocketIOClient> socketIoMap = SingletonClientMap.newInstance().getSocketIoMap();   //socketIo通道


    public void handlerExceptionMessage(final BaseResponseVo baseResponseVo) {
        logger.info("开始处理异常消息，数据：{}", baseResponseVo);
        final boolean success = baseResponseVo.isSuccess();
        final Integer code = baseResponseVo.getCode();
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (BooleanUtils.isFalse(success)) {    // code 不等于 200，都是false


                        if (AndroidErrorCode.ERROR_OPENDOOR_FAILED.getCode() == code) {                             // 10000 开门失败
                            logger.debug("设备发送开门失败异常消息到netty服务器");
                            openDoorException(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_LOCK_CORE_FALL_EXCEPTION.getCode() == code) {             // 10001 锁芯落下异常，需要相关人员去检测
                            logger.debug("锁芯落下异常");
                            lockCoreFallException(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_CLOSEDOOR_FAILED.getCode() == code) {                     // 10002 关门失败，长时间没有检测到关门状态
                            logger.debug("关门失败");
                            closeDoorException(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_RECLOSEDOOR.getCode() == code) {                          // 10003 检测到关门动作，但是门没有关好
                            logger.debug("设备门未关好，请重新关门");
                            reCloseDoorException(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_VROS_INIT_FAILED.getCode() == code) {                     // 20001 视觉系统初始化失败
                            logger.debug("视觉系统初始化失败");
                            vrosInitFailedException(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_VROS_SHUTDOWN_FAILED.getCode() == code) {                     // 20002 视觉系统关闭失败
                            logger.debug("视觉系统关闭失败");
                            vrosShutdownFailedException(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_CHANNEL_OPEN_FAILED.getCode() == code) {                     // 20003 通道打开失败
                            logger.debug("通道打开失败");
                            channelOpenFailed(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_OPEN_VR_DISTINGUISH_COMMAND_EXCEPTION.getCode() == code) {   // 20004 打开视觉识别命令异常
                            logger.debug("打开视觉识别命令异常");
                            openVrDistinguishCommandException(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_SHUTDOWN_SOME_CHANNEL_FAILED.getCode() == code) {             // 20005 关闭某通道失败
                            logger.debug("关闭某通道失败");
                            shutdownSomeChannelFailed(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_SHUTDOWN_CHANNEL_COMMAND_FAILED.getCode() == code) {          // 20006 关闭通道命令执行失败
                            logger.debug("关闭通道命令执行失败");
                            shutdownChannelCommandFailed(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_INVENTORY_FAILED.getCode() == code) {                          // 20007 盘货失败
                            logger.debug("盘货失败");
                            inventoryFailed(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_CHECK_DISTINGUISH_UNIT_RUN_STATUS_FAILED.getCode() == code) {  // 20008 查询识别单元的运行状态失败
                            logger.debug("查询识别单元的运行状态失败");
                            checkDistinguishUnitRunStatusFailed(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_QUERY_VERSION_NO_FAILED.getCode() == code) {                     // 20009 查询版本号失败
                            logger.debug("查询版本号失败");
                            queryVersionNoFailed(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_SHUTDOWN_FAILED.getCode() == code) {                     // 30000 关机失败
                            logger.debug("关机失败");
                            shutdownFailed(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_SHORT_CONNECTION_UNKNOWN.getCode() == code) {            // 30001 短连接异常
                            logger.debug("短连接异常");
                            errorShortConnectionUnknown(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_UPDATE_OPENDOOR_SPEECH_FAILED.getCode() == code) {        // 30002 更新开门语音失败
                            logger.debug("更新开门语音失败");
                            updateOpendoorSpeechFailed(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_UPDATE_OPENDOOR_SHOPPING_VOICE_FAILED.getCode() == code) { // 30003 更新开门购物语音失败
                            logger.debug("更新开门购物语音失败");
                            updateOpendoorShoppingVoiceFailed(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_UPDATE_CLOSEDOOR_VOICE_FAILED.getCode() == code) {         // 30004 更新关门语音失败
                            logger.debug("更新关门语音失败");
                            updateClosedoorVoiceFailed(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_CURRENT_OS_SERVICE_IS_LATEST_VERSION.getCode() == code) {  // 30005 系统服务已是最新版本
                            logger.debug("系统服务已是最新版本");
                            currentOsServiceIsLatestVersion(baseResponseVo);
                        } else if (AndroidErrorCode.ERROR_TCP_CONNECTION_UNKNOWN1.getCode() == code) {              // 30007 长连接出现未知异常
                            logger.debug("长连接未知异常");
                            tcpConnectionUnknownException(baseResponseVo);
                        }




                    } else {
                        logger.error("未知类型的异常消息");
                    }
                } catch (Exception e) {
                    logger.error("处理设备发送过来的异常消息出现异常：{}", e);
                }
            }
        });

    }

    /**
     * 查询版本号失败20009
     * @param baseResponseVo
     */
    private void queryVersionNoFailed(BaseResponseVo baseResponseVo) {

    }

    /**
     * 查询识别单元的运行状态失败20008
     * @param baseResponseVo
     */
    private void checkDistinguishUnitRunStatusFailed(BaseResponseVo baseResponseVo) {

    }

    /**
     * 盘货失败20007
     * @param baseResponseVo
     */
    private void inventoryFailed(BaseResponseVo baseResponseVo) {


    }

    /**
     * 关闭通道命令执行失败20006
     * @param baseResponseVo
     */
    private void shutdownChannelCommandFailed(BaseResponseVo baseResponseVo) {
        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
        sendSMSToDeviceMaintain(baseResponseVo, "关闭通道命令执行失败");       // 发送短信给设备维护人员
    }

    /**
     * 关闭某通道失败20005
     * @param baseResponseVo
     */
    private void shutdownSomeChannelFailed(BaseResponseVo baseResponseVo) {
        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
        sendSMSToDeviceMaintain(baseResponseVo, "关闭某通道失败");       // 发送短信给设备维护人员
    }

    /**
     * 打开视觉识别命令异常20004
     * @param baseResponseVo
     */
    private void openVrDistinguishCommandException(BaseResponseVo baseResponseVo) {
        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
        sendSMSToDeviceMaintain(baseResponseVo, "视觉系统关闭失败");       // 发送短信给设备维护人员
    }

    /**
     * 通道打开失败20003
     * @param baseResponseVo
     */
    private void channelOpenFailed(BaseResponseVo baseResponseVo) {
        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
        sendSMSToDeviceMaintain(baseResponseVo, "视觉系统关闭失败");       // 发送短信给设备维护人员
    }

    /**
     * 视觉系统关闭失败20002
     * @param baseResponseVo
     */
    private void vrosShutdownFailedException(BaseResponseVo baseResponseVo) {
        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
        sendSMSToDeviceMaintain(baseResponseVo, "视觉系统关闭失败");       // 发送短信给设备维护人员
    }

    /**
     * 视觉系统初始化失败20001
     * @param baseResponseVo
     */
    private void vrosInitFailedException(BaseResponseVo baseResponseVo) {
        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
        sendSMSToDeviceMaintain(baseResponseVo, "视觉系统初始化失败");       // 发送短信给设备维护人员
    }


    /**
     * 短连接异常30001
     * @param baseResponseVo
     */

    private void errorShortConnectionUnknown(BaseResponseVo baseResponseVo) {
        // 待确认需求

    }

    /**
     * 长连接出现未知异常30007
     * @param baseResponseVo
     */
    private void tcpConnectionUnknownException(BaseResponseVo baseResponseVo) {
        // 待确认需求
        // helloworld
    }

    /**
     * 系统服务已是最新版本30005
     * @param baseResponseVo
     */
    private void currentOsServiceIsLatestVersion(BaseResponseVo baseResponseVo) {
        // 待确认需求

    }

    /**
     * 更新关门语音失败30004
     * @param baseResponseVo
     */
    private void updateClosedoorVoiceFailed(BaseResponseVo baseResponseVo) {
        // 待确认需求

    }

    /**
     * 更新开门购物语音失败30003
     * @param baseResponseVo
     */
    private void updateOpendoorShoppingVoiceFailed(BaseResponseVo baseResponseVo) {
        // 待确认需求
    }

    /**
     * 更新开门语音失败30002
     * @param baseResponseVo
     */
    private void updateOpendoorSpeechFailed(BaseResponseVo baseResponseVo) {
        // 待确认需求

    }

    /**
     * 关机失败30000
     * @param baseResponseVo
     */
    private void shutdownFailed(BaseResponseVo baseResponseVo) {
        // 待确认需求

    }

    /**
     * 锁芯落下异常10001
     * @param baseResponseVo
     */
    private void lockCoreFallException(BaseResponseVo baseResponseVo) {
        // 待确认需求
        asynSendMsgToCellPhone(baseResponseVo, "门锁锁芯落下异常");
        logger.error("锁芯落下异常,请派人处理，暂时将门锁改为关闭状态");
        // 暂时将门锁改为关闭状态，实际上应该派人去检测
        CommonUtils.asynChangeDoorStatusToClose(baseResponseVo.getDeviceId(), iCached, logger);
    }

    /**
     * 门未关好，请重新关门10003
     *
     * @param baseResponseVo
     */
    private void reCloseDoorException(BaseResponseVo baseResponseVo) {
        logger.debug("开始处理关门未关好的消息");
        //  记录关门异常消息
        logger.error("关门异常");

        // 给手机端发送长时间没有检测到门未关闭的消息
        asynSendMsgToCellPhone(baseResponseVo, "门未关好，请重新关门");

    }

    /**
     * 处理关门失败异常消息10002
     * @param baseResponseVo
     */
    private void closeDoorException(BaseResponseVo baseResponseVo) {
        logger.debug("开始处理关门失败异常消息");
        //  记录关门异常消息
        logger.error("关门异常");

        // 给手机端发送长时间没有检测到门未关闭的消息
        asynSendMsgToCellPhone(baseResponseVo, "长时间未检测到关门动作，请确认是否");
    }



    /**
     * 处理开门失败异常消息10000
     *
     * @param baseResponseVo
     */
    private void openDoorException(BaseResponseVo baseResponseVo) {
        logger.debug("开始处理开门失败异常消息");

        // 记录开门异常消息
        logger.error("开门异常");

        // 异步发送消息给手机端
        asynSendMsgToCellPhone(baseResponseVo,"开门异常");

    }

    /**
     * 给手机异步发送消息
     * @param baseResponseVo
     * @param msg
     */
    private void asynSendMsgToCellPhone(final BaseResponseVo baseResponseVo, final String msg) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                String userId = baseResponseVo.getUserId();
                final SocketResponseVo socketResponseVo = SocketResponseVo.getSuccessResponse();
                String id = deviceId + "_" + userId;
                final SocketIOClient socketIOClient = socketIoMap.get(id);

                if (null == socketIOClient) {
                    logger.error("手机端离线，用户ID:{}", userId);
                    return;
                }

                //从redis中获取手机端用户消息
                SessionVo sessionVo = null;
                try {
                    sessionVo = (SessionVo) iCached.get(socketIOClient.getRemoteAddress().toString());
                    if (null == sessionVo) {
                        logger.error("手机端连接信息不在redis中，处理关门成功消息失败，用户ID:{}", userId);
                        return;
                    }
                } catch (Exception e) {
                    logger.error("手机端连接信息不再redis中");
                    return;
                }

                Integer type = sessionVo.getTypes();    // 开门类型
                socketResponseVo.setErrorCode(ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode());
                socketResponseVo.setSuccess(false);
                socketResponseVo.setMsg(msg);
                if (Integer.valueOf(10).equals(type)) {     // 顾客开门
                    socketResponseVo.setTypes(50);  // 购物异常
                } else if (Integer.valueOf(20).equals(type)) {  // 补货员开门
                    socketResponseVo.setTypes(60);  // 补货异常
                }
                try {
                    socketIOClient.sendEvent(NettyConst.SOCKETIO_EVENT, JSON.toJSON(socketResponseVo));
                } catch (Exception e) {
                    logger.error("向手机客户端发送消息异常：{}", e);
                }
            }
        });
    }

    /**
     * 给手机客户端异步发送消息
     *
     * @param baseResponseVo
     */
    private void asynSendOpenDoorMsgToSocketIo(final BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                String userId = baseResponseVo.getUserId();
                final SocketResponseVo socketResponseVo = SocketResponseVo.getSuccessResponse();
                String id = deviceId + "_" + userId;
                final SocketIOClient socketIOClient = socketIoMap.get(id);

                if (null == socketIOClient) {
                    logger.error("手机端离线，用户ID:{}", userId);
                    return;
                }

                //从redis中获取手机端用户消息
                SessionVo sessionVo = null;
                try {
                    sessionVo = (SessionVo) iCached.get(socketIOClient.getRemoteAddress().toString());
                    if (null == sessionVo) {
                        logger.error("手机端连接信息不在redis中，处理关门成功消息失败，用户ID:{}", userId);
                        return;
                    }
                } catch (Exception e) {
                    logger.error("手机端连接信息不再redis中");
                    return;
                }

                Integer type = sessionVo.getTypes();    // 开门类型
                socketResponseVo.setErrorCode(ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode());
                socketResponseVo.setSuccess(false);
                socketResponseVo.setMsg("开门失败");
                if (Integer.valueOf(10).equals(type)) {     // 顾客开门
                    socketResponseVo.setTypes(50);  // 购物异常
                } else if (Integer.valueOf(20).equals(type)) {  // 补货员开门
                    socketResponseVo.setTypes(60);  // 补货异常
                }
                try {
                    socketIOClient.sendEvent(NettyConst.SOCKETIO_EVENT, JSON.toJSON(socketResponseVo));
                } catch (Exception e) {
                    logger.error("向手机客户端发送消息异常：{}", e);
                }
            }
        });
    }

    /**
     * 异步修改设备状态为故障
     * @param baseResponseVo
     */
    private void changeDeviceStatusTomalfunction(final BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
                if (null != deviceInfo) {
                    deviceInfo = new DeviceInfo();
                    deviceInfo.setId(deviceId);
                    deviceInfo.setIstatus(30);    // 10=未注册 20=正常 30=故障 40=报废 50=离线
                    deviceInfoService.updateBySelective(deviceInfo);
                }
            }
        });

    }

    /**
     * 发送短信给设备维护人员
     *
     * @param baseResponseVo 售货机设备发送的消息体
     * @param exceptionMsg 异常信息
     */
    private void sendSMSToDeviceMaintain(BaseResponseVo baseResponseVo, String exceptionMsg) {
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(baseResponseVo.getDeviceId());
        if (null != deviceInfo) {
            InnerMessageDto innerMessageDto = new InnerMessageDto();
            // 模板编号
            innerMessageDto.setTemplateCode("TPE0080");
            // 内容
            Map<String, Object> contentParamMap = new HashMap<>();
            contentParamMap.put("deviceCode", deviceInfo.getScode());
            contentParamMap.put("exceptionMsg", exceptionMsg);
            innerMessageDto.setContentParam(contentParamMap);
            // 权限编码
            innerMessageDto.setPurviewCode("DEVICE_MAINTAIN_WARN");
            //商户Id
            innerMessageDto.setMerchantId(deviceInfo.getSmerchantId());
            try {
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
                invoke.setRequestObj(innerMessageDto); // post 参数
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            } catch (Exception e) {
                logger.error("警告短信发送失败", e);
            }
        }
    }
}
