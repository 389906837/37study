package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.AndroidErrorCode;
import com.cloud.cang.api.common.WebSocketConstant;
import com.cloud.cang.api.common.utils.CommonUtils;
import com.cloud.cang.api.common.utils.LogUtils;
import com.cloud.cang.api.common.utils.MsgToJsonUtils;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.api.sl.service.DeviceOperService;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.TypeConstant;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.pojo.BaseResponseVo;
import com.cloud.cang.utils.DateUtils;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    DeviceOperService deviceOperService;

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
                    if (BooleanUtils.isFalse(success) && null != code) {    // code 不等于 200，都是false

                        /* 10000 -- 19999*/
                        if (code >= 10000 && code < 20000 && TypeConstant.EXCEPTION.equals(baseResponseVo.getExceptionGrade())) {
                            if (AndroidErrorCode.ERROR_OPENDOOR_FAILED.getCode() == code) {                             // 10000 开门失败
                                WebSocketConstant.OPEN_REQUEST_FLAG = true;//更改请求开门状态
                                logger.debug("设备ID:{}发送开门失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                sendOpenDoorFailedToCellPhone(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.EXCEPTION, null, TypeConstant.EXCEPTION);
                            } else if (AndroidErrorCode.ERROR_LOCK_CORE_FALL_EXCEPTION.getCode() == code) {             // 10001 锁芯落下异常，需要相关人员去检测
                                logger.debug("设备ID:{}发送锁芯落下异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                lockCoreFallException(baseResponseVo);
                                addLogRecordAndSendMsg(baseResponseVo, "锁芯落下异常");/* 记录故障日志，并且发送短信给内部维护人员*/
                                changeDeviceStatusTomalfunction(baseResponseVo);/* 修改设备状态为故障*/
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.FAULT);
                            } else if (AndroidErrorCode.ERROR_CLOSEDOOR_FAILED.getCode() == code) {                     // 10002 关门失败，长时间没有检测到关门状态
                                logger.debug("设备ID:{}发送关门失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                closeDoorException(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.EXCEPTION, null, TypeConstant.EXCEPTION);
                            } else if (AndroidErrorCode.ERROR_RECLOSEDOOR.getCode() == code) {                          // 10003 检测到关门动作，但是门没有关好
                                logger.debug("设备ID:{}发送设备门未关好，请重新关门异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                reCloseDoorException(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.EXCEPTION, null, TypeConstant.EXCEPTION);
                            }

                            /* 20000 -- 29999*/
                        } else if (code < 30000 && TypeConstant.FAULT.equals(baseResponseVo.getExceptionGrade())) {
                            if (AndroidErrorCode.ERROR_VROS_INIT_FAILED.getCode() == code) {                     // 20001 视觉系统初始化失败
                                logger.debug("设备ID:{}发送视觉系统初始化失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                vrOsInitFailedException(baseResponseVo);
                                addLogRecordAndSendMsg(baseResponseVo, "视觉系统初始化失败");/* 记录故障日志，并且发送短信给内部维护人员*/
                                changeDeviceStatusTomalfunction(baseResponseVo);
                                sengFaultMsgToCellphone(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.FAULT);
                            } else if (AndroidErrorCode.ERROR_VROS_SHUTDOWN_FAILED.getCode() == code) {                     // 20002 视觉系统关闭失败
                                logger.debug("设备ID:{}发送视觉系统关闭失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                vrOsShutdownFailedException(baseResponseVo);
                                addLogRecordAndSendMsg(baseResponseVo, "视觉系统关闭失败");/* 记录故障日志，并且发送短信给内部维护人员*/
                                changeDeviceStatusTomalfunction(baseResponseVo);
                                sengFaultMsgToCellphone(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.FAULT);
                            } else if (AndroidErrorCode.ERROR_CHANNEL_OPEN_FAILED.getCode() == code) {                     // 20003 通道打开失败
                                logger.debug("设备ID:{}发送通道打开失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                channelOpenFailed(baseResponseVo);
                                addLogRecordAndSendMsg(baseResponseVo, "通道打开失败");/* 记录故障日志，并且发送短信给内部维护人员*/
                                changeDeviceStatusTomalfunction(baseResponseVo);
                                sengFaultMsgToCellphone(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.FAULT);
                            } else if (AndroidErrorCode.ERROR_OPEN_VR_DISTINGUISH_COMMAND_EXCEPTION.getCode() == code) {   // 20004 打开视觉识别命令异常
                                logger.debug("设备ID:{}发送打开视觉识别命令异常异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                openVrDistinguishCommandException(baseResponseVo);
                                addLogRecordAndSendMsg(baseResponseVo, "打开视觉识别命令异常");/* 记录故障日志，并且发送短信给内部维护人员*/
                                changeDeviceStatusTomalfunction(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.FAULT);
                            } else if (AndroidErrorCode.ERROR_SHUTDOWN_SOME_CHANNEL_FAILED.getCode() == code) {             // 20005 关闭某通道失败
                                logger.debug("设备ID:{}发送关闭某通道失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                shutdownSomeChannelFailed(baseResponseVo);
                                addLogRecordAndSendMsg(baseResponseVo, "关闭某通道失败");/* 记录故障日志，并且发送短信给内部维护人员*/
                                changeDeviceStatusTomalfunction(baseResponseVo);
                                sengFaultMsgToCellphone(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.FAULT);
                            } else if (AndroidErrorCode.ERROR_SHUTDOWN_CHANNEL_COMMAND_FAILED.getCode() == code) {          // 20006 关闭通道命令执行失败
                                logger.debug("设备ID:{}发送关闭通道命令执行失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                shutdownChannelCommandFailed(baseResponseVo);
                                addLogRecordAndSendMsg(baseResponseVo, "关闭通道命令执行失败");/* 记录故障日志，并且发送短信给内部维护人员*/
                                changeDeviceStatusTomalfunction(baseResponseVo);
                                sengFaultMsgToCellphone(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.FAULT);
                            } else if (AndroidErrorCode.ERROR_INVENTORY_FAILED_ONCE.getCode() == code) {                          // 20007 盘货失败(一次)，只记录盘货失败日志
                                logger.debug("设备ID:{}发送盘货失败（一次）异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
//                                inventoryFailed(baseResponseVo);
                                addLogRecordAndSendMsg(baseResponseVo, "盘货失败(一次)");/* 记录故障日志，并且发送短信给内部维护人员*/
//                                changeDeviceStatusTomalfunction(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.EXCEPTION);
                            } else if (AndroidErrorCode.ERROR_CHECK_DISTINGUISH_UNIT_RUN_STATUS_FAILED.getCode() == code) {  // 20008 查询识别单元的运行状态失败
                                logger.debug("设备ID:{}发送查询识别单元的运行状态失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                checkDistinguishUnitRunStatusFailed(baseResponseVo);
                                addLogRecordAndSendMsg(baseResponseVo, "查询识别单元的运行状态失败");/* 记录故障日志，并且发送短信给内部维护人员*/
                                changeDeviceStatusTomalfunction(baseResponseVo);
                                sengFaultMsgToCellphone(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.FAULT);
                            } else if (AndroidErrorCode.ERROR_QUERY_VERSION_NO_FAILED.getCode() == code) {                     // 20009 查询视觉系统版本号失败
                                logger.debug("设备ID:{}发送查询版本号失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                queryVersionNoFailed(baseResponseVo);
                                addLogRecordAndSendMsg(baseResponseVo, "查询视觉系统版本号失败");/* 记录故障日志，并且发送短信给内部维护人员*/
                                changeDeviceStatusTomalfunction(baseResponseVo);
                                sengFaultMsgToCellphone(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.FAULT);
                            } else if (AndroidErrorCode.ERROR_INVENTORY_FAILED_FIVE_TIMES.getCode() == code) {                     // 20010 盘货失败（五次），修改设备状态为故障
                                logger.debug("设备ID:{}发送盘货失败（五次）异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                addLogRecordAndSendMsg(baseResponseVo, "盘货失败（五次）");/* 记录故障日志，并且发送短信给内部维护人员*/
                                changeDeviceStatusTomalfunction(baseResponseVo);
                                // sengFaultMsgToCellphone(baseResponseVo);
                                if (iCached.exists("user_operate_device_key_" + baseResponseVo.getUserId())) {
                                    //如果存在用户开门KEY
                                    iCached.put("inventory_failed_five_user_operate_device_key_" + baseResponseVo.getUserId(), 1, 10 * 60);
                                } else {
                                    MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.FAULT);
                                }
                            } else {
                                logger.error("设备ID:{}发送未定义错误代码code：{}的异常消息，内容为：{}", baseResponseVo.getDeviceId(), code, baseResponseVo.getMsg());
                                LogUtils.insertMalfunctionLog(baseResponseVo.getDeviceId(), 10, code, "未定义错误代码的异常消息:" + baseResponseVo.getMsg(), null, baseResponseVo.getExceptionGrade());
                                baseResponseVo.setCode(AndroidErrorCode.ERROR_UNKNOWN_CODE.getCode());
                                sengFaultMsgToCellphone(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.FAULT, null, TypeConstant.FAULT);
                            }


                        } else if (code < 40000 && TypeConstant.EXCEPTION.equals(baseResponseVo.getExceptionGrade())) {  /* 30000 -- 39999*/
                            if (AndroidErrorCode.ERROR_SHUTDOWN_FAILED.getCode() == code) {                     // 30000 关机失败
                                logger.debug("设备ID:{}发送关机失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                shutdownFailed(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.EXCEPTION, null, TypeConstant.EXCEPTION);
                            } else if (AndroidErrorCode.ERROR_SHORT_CONNECTION_UNKNOWN.getCode() == code) {            // 30001 短连接异常
                                logger.debug("设备ID:{}发送短连接异常异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                errorShortConnectionUnknown(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.EXCEPTION, null, TypeConstant.EXCEPTION);
                            } else if (AndroidErrorCode.ERROR_UPDATE_OPENDOOR_SPEECH_FAILED.getCode() == code) {        // 30002 更新开门语音失败
                                logger.debug("设备ID:{}发送更新开门语音失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                updateOpendoorSpeechFailed(baseResponseVo);
                            } else if (AndroidErrorCode.ERROR_UPDATE_OPENDOOR_SHOPPING_VOICE_FAILED.getCode() == code) { // 30003 更新开门购物语音失败
                                logger.debug("设备ID:{}发送更新开门购物语音失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                updateOpendoorShoppingVoiceFailed(baseResponseVo);
                            } else if (AndroidErrorCode.ERROR_UPDATE_CLOSEDOOR_VOICE_FAILED.getCode() == code) {         // 30004 更新关门语音失败
                                logger.debug("设备ID:{}发送更新关门语音失败异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                updateClosedoorVoiceFailed(baseResponseVo);
                            } else if (AndroidErrorCode.ERROR_CURRENT_OS_SERVICE_IS_LATEST_VERSION.getCode() == code) {  // 30005 系统服务已是最新版本
                                logger.debug("设备ID:{}发送系统服务已是最新版本异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                currentOsServiceIsLatestVersion(baseResponseVo);
                            } else if (AndroidErrorCode.ERROR_TCP_CONNECTION_UNKNOWN1.getCode() == code) {              // 30007 长连接出现未知异常
                                logger.debug("设备ID:{}发送长连接未知异常消息到netty服务器，错误代码：{}", baseResponseVo.getDeviceId(), code);
                                tcpConnectionUnknownException(baseResponseVo);
                                MsgToJsonUtils.asynSendNoCodeMsgByCtxMap(baseResponseVo.getDeviceId(), ctxMap, true, TypeConstant.EXCEPTION, null, TypeConstant.EXCEPTION);
                            } else {
                                logger.error("设备ID:{}发送未定义错误代码code：{}的异常消息，内容为：{}", baseResponseVo.getDeviceId(), code, baseResponseVo.getMsg());
                                baseResponseVo.setMsg("未定义错误代码的异常消息:" + baseResponseVo.getMsg());
                                sengFaultMsgToCellphone(baseResponseVo);    // 向手机端发送故障消息
                            }
                            LogUtils.insertMalfunctionLog(baseResponseVo.getDeviceId(), 10, code, baseResponseVo.getMsg(), null, baseResponseVo.getExceptionGrade());

                        } else {
                            logger.error("设备ID:{}发送错误代码code不在10000-40000范围内：{}的异常消息", baseResponseVo.getDeviceId(), baseResponseVo.getMsg());
                            sengFaultMsgToCellphone(baseResponseVo);    // 向手机端发送故障消息
                            LogUtils.insertMalfunctionLog(baseResponseVo.getDeviceId(), 10, code, "错误代码code不在10000-40000范围内的异常消息:" + baseResponseVo.getMsg(), null, baseResponseVo.getExceptionGrade());
                        }
                    } else {
                        logger.error("设备ID:{}发送code为空的异常消息", baseResponseVo.getDeviceId());
                        baseResponseVo.setCode(AndroidErrorCode.ERROR_NULL_CODE.getCode());
                        sengFaultMsgToCellphone(baseResponseVo);    // 向手机端发送故障消息
                        LogUtils.insertMalfunctionLog(baseResponseVo.getDeviceId(), 10, code, "code为空的异常消息:" + baseResponseVo.getMsg(), null, baseResponseVo.getExceptionGrade());
                    }
                } catch (Exception e) {
                    logger.error("处理设备ID:{}发送过来的异常消息出现异常：{}", baseResponseVo.getDeviceId(), e);
                }
            }
        });

    }

    /**
     * 出现异常时给手机端发送消息
     *
     * @param baseResponseVo
     * @throws Exception
     */
    private void sengFaultMsgToCellphone(BaseResponseVo baseResponseVo) throws Exception {
        ClientVo clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, baseResponseVo.getDeviceId());
        if (null != clientVo) {
            // 10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
            Integer door = clientVo.getDoor();  // 10关，20开
            Integer openDoorType = clientVo.getOpenDoorType();  //开门类型 10 开门 20 补货开门 30游客开门（没买东西）
            Integer type = null;
            if (StringUtils.isBlank(baseResponseVo.getUserId())) {
                logger.error("没有手机在线,出现异常时给手机端发送消息失败，设备ID：{}", baseResponseVo.getDeviceId());
                return;
            }
            if (null != openDoorType && 10 == openDoorType) {
                type = 50;
            } else if (null != openDoorType && 20 == openDoorType) {
                type = 60;
            }
            String id = baseResponseVo.getDeviceId() + "_" + baseResponseVo.getUserId();
            SocketIOClient socketIOClient = socketIoMap.get(id);
            MsgToJsonUtils.asynSendMsgToCellPhone(type, "", baseResponseVo.getCode(), baseResponseVo.getMsg(), true, socketIOClient, baseResponseVo.getUserId());
        }
    }

    /**
     * 查询版本号失败20009
     *
     * @param baseResponseVo
     */
    private void queryVersionNoFailed(BaseResponseVo baseResponseVo) {

    }

    /**
     * 查询识别单元的运行状态失败20008
     *
     * @param baseResponseVo
     */
    private void checkDistinguishUnitRunStatusFailed(BaseResponseVo baseResponseVo) {

    }

    /**
     * 盘货失败20007
     *
     * @param baseResponseVo
     */
    private void inventoryFailed(BaseResponseVo baseResponseVo) {
        // 向最后一个关门人发送盘货失败异常结果
        String deviceId = baseResponseVo.getDeviceId();
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
        if (null != deviceInfo) {
            String userId = deviceOperService.selectLastUserByDeviceCode(deviceInfo.getScode());
            if (StringUtils.isNotBlank(userId)) {
                baseResponseVo.setUserId(userId);
                asynSendMsgToCellPhone(baseResponseVo, "关门后视觉服务器盘货失败");
                logger.error("关门后视觉服务器盘货失败2007");
            }
        }
    }

    /**
     * 关闭通道命令执行失败20006
     *
     * @param baseResponseVo
     */
    private void shutdownChannelCommandFailed(BaseResponseVo baseResponseVo) {
//        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
//        sendSMSToDeviceMaintain(baseResponseVo, "关闭通道命令执行失败");       // 发送短信给设备维护人员
    }

    /**
     * 关闭某通道失败20005
     *
     * @param baseResponseVo
     */
    private void shutdownSomeChannelFailed(BaseResponseVo baseResponseVo) {
//        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
//        sendSMSToDeviceMaintain(baseResponseVo, "关闭某通道失败");       // 发送短信给设备维护人员
    }

    /**
     * 打开视觉识别命令异常20004
     *
     * @param baseResponseVo
     */
    private void openVrDistinguishCommandException(BaseResponseVo baseResponseVo) {
//        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
//        sendSMSToDeviceMaintain(baseResponseVo, "视觉系统关闭失败");       // 发送短信给设备维护人员
    }

    /**
     * 通道打开失败20003
     *
     * @param baseResponseVo
     */
    private void channelOpenFailed(BaseResponseVo baseResponseVo) {
//        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
//        sendSMSToDeviceMaintain(baseResponseVo, "视觉系统关闭失败");       // 发送短信给设备维护人员
    }

    /**
     * 视觉系统关闭失败20002
     *
     * @param baseResponseVo
     */
    private void vrOsShutdownFailedException(BaseResponseVo baseResponseVo) {
//        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
//        sendSMSToDeviceMaintain(baseResponseVo, "视觉系统关闭失败");       // 发送短信给设备维护人员
    }

    /**
     * 视觉系统初始化失败20001
     *
     * @param baseResponseVo
     */
    private void vrOsInitFailedException(BaseResponseVo baseResponseVo) {
//        changeDeviceStatusTomalfunction(baseResponseVo);                                    // 修改设备状态为故障
//        sendSMSToDeviceMaintain(baseResponseVo, "视觉系统初始化失败");       // 发送短信给设备维护人员
    }


    /**
     * 短连接异常30001
     *
     * @param baseResponseVo
     */

    private void errorShortConnectionUnknown(BaseResponseVo baseResponseVo) {
        // 待确认需求

    }

    /**
     * 长连接出现未知异常30007
     *
     * @param baseResponseVo
     */
    private void tcpConnectionUnknownException(BaseResponseVo baseResponseVo) {
        // 待确认需求
        // helloworld
    }

    /**
     * 系统服务已是最新版本30005
     *
     * @param baseResponseVo
     */
    private void currentOsServiceIsLatestVersion(BaseResponseVo baseResponseVo) {
        // 待确认需求

    }

    /**
     * 更新关门语音失败30004
     *
     * @param baseResponseVo
     */
    private void updateClosedoorVoiceFailed(BaseResponseVo baseResponseVo) {
        // 待确认需求

    }

    /**
     * 更新开门购物语音失败30003
     *
     * @param baseResponseVo
     */
    private void updateOpendoorShoppingVoiceFailed(BaseResponseVo baseResponseVo) {
        // 待确认需求
    }

    /**
     * 更新开门语音失败30002
     *
     * @param baseResponseVo
     */
    private void updateOpendoorSpeechFailed(BaseResponseVo baseResponseVo) {
        // 待确认需求

    }

    /**
     * 关机失败30000
     *
     * @param baseResponseVo
     */
    private void shutdownFailed(BaseResponseVo baseResponseVo) {
        // 待确认需求

    }

    /**
     * 锁芯落下异常10001
     *
     * @param baseResponseVo
     */
    private void lockCoreFallException(BaseResponseVo baseResponseVo) {
        //向手机发送锁芯落下异常的消息
        // 从设备操作表中查找最后一个开门人，向该对象发送关门消息
        String deviceId = baseResponseVo.getDeviceId();
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
        if (null != deviceInfo) {
            String userId = deviceOperService.selectLastUserByDeviceCode(deviceInfo.getScode());
            if (StringUtils.isNotBlank(userId)) {
                baseResponseVo.setUserId(userId);
                sendCloseDoorFailedToCellPhone(baseResponseVo, "门锁锁芯落下异常");
                logger.error("向手机发送锁芯落下异常的消息");
            }
        }
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
        sendCloseDoorFailedToCellPhone(baseResponseVo, "门未关好，请重新关门");

    }

    /**
     * 处理关门失败异常消息10002
     *
     * @param baseResponseVo
     */
    private void closeDoorException(BaseResponseVo baseResponseVo) {
        logger.debug("开始处理关门失败异常消息");
        //  记录关门异常消息
        logger.error("关门异常");

        // 给手机端发送长时间没有检测到门未关闭的消息
        sendCloseDoorFailedToCellPhone(baseResponseVo, "长时间未检测到关门动作，请确认是否关门");
    }


    /**
     * 处理开门失败异常消息10000
     *
     * @param baseResponseVo
     */
    private void openDoorException(final BaseResponseVo baseResponseVo) {
        logger.debug("开始处理开门失败异常消息");

        // 异步发送消息给手机端
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.debug("开始向手机端:{}发送异常消息：{}", baseResponseVo.getUserId(), "开门失败");
                String deviceId = baseResponseVo.getDeviceId();
                String userId = baseResponseVo.getUserId();
                final SocketResponseVo socketResponseVo = SocketResponseVo.getSuccessResponse();
                String id = deviceId + "_" + userId;
                final SocketIOClient socketIOClient = socketIoMap.get(id);

                //从redis中获取手机端用户消息
                SessionVo sessionVo = null;
                try {
                    sessionVo = (SessionVo) iCached.get("soketIo_user_session_" + userId);
                    if (null == sessionVo) {
                        logger.error("手机端用户ID：{} 连接信息不在redis中，处理关门成功消息失败", userId);
                        return;
                    }
                } catch (Exception e) {
                    logger.error("从redis中获取用户ID：{} sessionVo信息出现异常", userId);
                    return;
                }

                Integer type = sessionVo.getTypes();    // 开门类型 10 开门 20 补货开门
                Integer typeTemp = null;
                if (10 == type.intValue()) {
                    typeTemp = 10;
                } else if (20 == type.intValue()) {
                    typeTemp = 20;
                }
                socketResponseVo.setTypes(typeTemp);
                socketResponseVo.setErrorCode(ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode());
                socketResponseVo.setSuccess(false);
                socketResponseVo.setMsg("开门失败");    //10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
                try {
                    if (null != socketIOClient) {
                        socketIOClient.sendEvent(NettyConst.SOCKETIO_EVENT, JSON.toJSON(socketResponseVo));
                        logger.debug("向手机端用户userId:{},发送异常消息：{} 完成", userId, JSON.toJSON(socketResponseVo));
                    }
                } catch (Exception e) {
                    logger.error("向手机端userId:{}发送消息异常出现异常：{}", userId, e);
                }
            }
        });

    }

    /**
     * 给手机异步发送消息
     *
     * @param baseResponseVo
     * @param msg
     */
    private void asynSendMsgToCellPhone(final BaseResponseVo baseResponseVo, final String msg) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.debug("开始向手机端:{}发送异常消息：{}", baseResponseVo.getUserId(), msg);
                String deviceId = baseResponseVo.getDeviceId();
                String userId = baseResponseVo.getUserId();
                final SocketResponseVo socketResponseVo = SocketResponseVo.getSuccessResponse();
                String id = deviceId + "_" + userId;
                final SocketIOClient socketIOClient = socketIoMap.get(id);

                //从redis中获取手机端用户消息
                SessionVo sessionVo = null;
                try {
                    sessionVo = (SessionVo) iCached.get("soketIo_user_session_" + userId);
                    if (null == sessionVo) {
                        logger.error("手机端用户ID：{} 连接信息不在redis中，处理关门成功消息失败", userId);
                        return;
                    }
                } catch (Exception e) {
                    logger.error("从redis中获取用户ID：{} sessionVo信息出现异常", userId);
                    return;
                }

                Integer type = sessionVo.getTypes();    // 开门类型
                socketResponseVo.setTypes(type);
                socketResponseVo.setErrorCode(ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode());
                socketResponseVo.setSuccess(false);
                socketResponseVo.setMsg(msg);
                try {
                    if (null != socketIOClient) {
                        socketIOClient.sendEvent(NettyConst.SOCKETIO_EVENT, JSON.toJSON(socketResponseVo));
                        logger.debug("向手机端用户userId:{},发送异常消息：{} 完成", userId, JSON.toJSON(socketResponseVo));
                    }
                } catch (Exception e) {
                    logger.error("向手机端userId:{}发送消息异常出现异常：{}", userId, e);
                }
            }
        });
    }

    /**
     * 给手机发送开门失败消息
     *
     * @param baseResponseVo
     */
    private void sendOpenDoorFailedToCellPhone(final BaseResponseVo baseResponseVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String deviceId = baseResponseVo.getDeviceId();
                String userId = baseResponseVo.getUserId();
                final SocketResponseVo socketResponseVo = new SocketResponseVo();
                String id = deviceId + "_" + userId;
                final SocketIOClient socketIOClient = socketIoMap.get(id);

                //从redis中获取手机端用户消息
                SessionVo sessionVo = null;
                try {
                    sessionVo = (SessionVo) iCached.get("soketIo_user_session_" + userId);
                    if (null == sessionVo) {
                        logger.error("手机端连接信息不在redis中，处理开门失败消息失败，用户ID:{}，设备ID：{}", userId, deviceId);
                        return;
                    }
                } catch (Exception e) {
                    logger.error("手机端连接信息不再redis中,处理开门失败消息出现异常，用户ID:{}，设备ID：{}", userId, deviceId);
                    return;
                }

                Integer type = sessionVo.getTypes();    // 开门类型
                socketResponseVo.setErrorCode(ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode());
                socketResponseVo.setSuccess(false);
                socketResponseVo.setMsg("开门失败");
                if (10 == type.intValue()) {     // 顾客开门
                    socketResponseVo.setTypes(10);  //  10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
                } else if (20 == type.intValue()) {  // 补货员开门
                    socketResponseVo.setTypes(20);  //
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
     * 向手机端发送设备关门失败的消息
     *
     * @param baseResponseVo 设备发来的消息体
     * @param msg            失败原因
     */
    private void sendCloseDoorFailedToCellPhone(final BaseResponseVo baseResponseVo, final String msg) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.debug("开始处理关门失败消息，用户ID：{}，设备ID：{}", baseResponseVo.getUserId(), baseResponseVo.getDeviceId());
                String deviceId = baseResponseVo.getDeviceId();
                String userId = baseResponseVo.getUserId();
                final SocketResponseVo socketResponseVo = new SocketResponseVo();
                String id = deviceId + "_" + userId;
                final SocketIOClient socketIOClient = socketIoMap.get(id);

                //从redis中获取手机端用户消息
                SessionVo sessionVo = null;
                try {
                    sessionVo = (SessionVo) iCached.get("soketIo_user_session_" + userId);
                    if (null == sessionVo) {
                        logger.error("手机端连接信息不在redis中，处理关门失败消息失败，用户ID:{}，设备ID：{}", userId, deviceId);
                        return;
                    }
                } catch (Exception e) {
                    logger.error("处理关门失败消息出现异常，用户ID:{}，设备ID：{}", userId, deviceId);
                    return;
                }

                Integer type = sessionVo.getTypes();    // 开门类型
                socketResponseVo.setErrorCode(ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode());
                socketResponseVo.setSuccess(false);
                socketResponseVo.setMsg(msg);
                if (10 == type.intValue()) {     // 顾客开门
                    socketResponseVo.setTypes(30);  //  10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
                } else if (20 == type.intValue()) {  // 补货员开门
                    socketResponseVo.setTypes(40);  //
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
     *
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
                    logger.error("记录设备故障日志zzx");
                    logger.error("修改设备从正常为故障状态,故障原因是：{},故障类型是：{},异常代码：{}", baseResponseVo.getMsg(), baseResponseVo.getMethodType(), baseResponseVo.getCode());
                }
            }
        });

    }

    /**
     * 发送短信给设备维护人员
     *
     * @param baseResponseVo 售货机设备发送的消息体
     * @param exceptionMsg   异常信息
     */
//    private void sendSMSToDeviceMaintain(BaseResponseVo baseResponseVo, String exceptionMsg) {
//        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(baseResponseVo.getDeviceId());
//        if (null != deviceInfo) {
//            InnerMessageDto innerMessageDto = new InnerMessageDto();
//            // 模板编号
//            innerMessageDto.setTemplateCode("TPE0086");
//            // 内容
//            Map<String, Object> contentParamMap = new HashMap<>();
//            contentParamMap.put("deviceCode", deviceInfo.getScode());
//            contentParamMap.put("exceptionMsg", exceptionMsg);
//            innerMessageDto.setContentParam(contentParamMap);
//            // 权限编码
//            innerMessageDto.setPurviewCode("DEVICE_MAINTAIN_WARN");
//            //商户Id
//            innerMessageDto.setSmerchantId(deviceInfo.getSmerchantId());
//            innerMessageDto.setSmerchantCode(deviceInfo.getSmerchantCode());
//            try {
//                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
//                invoke.setRequestObj(innerMessageDto); // post 参数
//                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
//                });
//                ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
//            } catch (Exception e) {
//                logger.error("警告短信发送失败", e);
//            }
//        }
//    }


    /**
     * 记录异常日志
     * 发送内部人员短信通知
     *
     * @param baseResponseVo 设备发送来的消息
     * @param faultReason    故障原因
     */
    private void addLogRecordAndSendMsg(BaseResponseVo baseResponseVo, String faultReason) {
        // 记录长连接设备故障日志
        if (StringUtils.isNotBlank(baseResponseVo.getDeviceId()) && StringUtils.isNotBlank(baseResponseVo.getMsg())) {
            LogUtils.tcpMalfunctionLog(baseResponseVo.getDeviceId(), baseResponseVo.getMsg(), baseResponseVo.getCode(), baseResponseVo.getExceptionGrade());
            String faultTime = DateUtils.dateToString(new Date());
            LogUtils.sendMessageTointernal(faultTime, baseResponseVo.getDeviceId(), faultReason, baseResponseVo.getCode());
        }
    }

    /**
     * 记录日志
     *
     * @param baseResponseVo
     */
    private void insertLogRecord(BaseResponseVo baseResponseVo) {
        if (StringUtils.isNotBlank(baseResponseVo.getDeviceId()) && StringUtils.isNotBlank(baseResponseVo.getMsg())) {
            LogUtils.tcpMalfunctionLog(baseResponseVo.getDeviceId(), baseResponseVo.getMsg(), baseResponseVo.getCode(), baseResponseVo.getExceptionGrade());
        }
    }

    /**
     * 发送设备故障短信给内部人员
     *
     * @param baseResponseVo 设备发送消息体
     * @param faultReason    故障原因
     */
    private void sendFaultMessage(BaseResponseVo baseResponseVo, String faultReason) {
        if (StringUtils.isNotBlank(baseResponseVo.getDeviceId()) && StringUtils.isNotBlank(baseResponseVo.getMsg())) {
            String faultTime = DateUtils.dateToString(new Date());
            LogUtils.sendMessageTointernal(faultTime, baseResponseVo.getDeviceId(), faultReason, baseResponseVo.getCode());
        }
    }

}
