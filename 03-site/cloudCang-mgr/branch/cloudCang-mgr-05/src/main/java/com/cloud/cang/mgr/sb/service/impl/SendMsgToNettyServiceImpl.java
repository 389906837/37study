package com.cloud.cang.mgr.sb.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sb.service.SendMsgToNettyService;
import com.cloud.cang.sb.*;
import com.cloud.cang.security.utils.SessionUserUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @version 1.0
 * @ClassName: SendMsgToNettyService
 * @Description: 后台发送消息给netty 实现类
 * @Author: zengzexiong
 * @Date: 2018年4月12日20:42:32
 */
@Service
public class SendMsgToNettyServiceImpl implements SendMsgToNettyService {

    private static final Logger logger = LoggerFactory.getLogger(SendMsgToNettyServiceImpl.class);

    /**
     * 将设备手动离线
     *
     * @param id 设备
     */
    @Override
    public ResponseVo<String> offline(String id) {
        ResponseVo<String> responseVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.device.offline.fail"));
        SetDeviceOfflineDto deviceOfflineDto = new SetDeviceOfflineDto();
        deviceOfflineDto.setId(id);
        try {
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.SET_DEVICE_OFFLINE);
            invoke.setRequestObj(deviceOfflineDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.info("调用长连接服务成功，操作类型为：", NettyConst.OFFLINE);

                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.send.to.device.operate.msg"), NettyConst.OFFLINE);
                LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("向设备发送信息出现Exception异常", e);
        }
        return responseVo;
    }

    /**
     * 向设备发送二维码
     * @param deviceId
     * @param qrCodeUrl
     * @return
     */
    @Override
    public ResponseVo<String> sendQrCode(String deviceId, String qrCodeUrl) {
        return sendMsgToDevice(deviceId, NettyConst.QR_CODE, qrCodeUrl);
    }

    /**
     * 向设备发送运营位1广告
     *
     * @param deviceIds 设备 “，”拼接
     * @param userId    操作员ID
     * @return 发送消息结果
     */
    @Override
    public ResponseVo<String> sendAdOne(String deviceIds, String userId, String merchantCode) {
        return sendAdToDevice(deviceIds, userId, NettyConst.AD_ONE, merchantCode);
    }

    /**
     * 向设备发送运营位2广告
     *
     * @param deviceIds 设备ID “，”拼接
     * @param userId    操作员ID
     * @return 发送消息结果
     */
    @Override
    public ResponseVo<String> sendAdTwo(String deviceIds, String userId, String merchantCode) {
        return sendAdToDevice(deviceIds, userId, NettyConst.AD_TWO, merchantCode);
    }


    /**
     * 向设备发送运营位3广告
     *
     * @param deviceIds 设备ID “，”拼接
     * @param userId    操作员ID
     * @return 发送消息结果
     */
    @Override
    public ResponseVo<String> sendAdThree(String deviceIds, String userId, String merchantCode) {
        return sendAdToDevice(deviceIds, userId, NettyConst.AD_THREE, merchantCode);
    }

    @Override
    public TcpResult selectUnRegistered() {
        logger.debug("开始调用从netty服务器查询未注册长连接信息服务");

        ResponseVo<TcpResult> responseVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.call.unregister.tcp.fail"));
        try {
            TcpDto tcpDto = new TcpDto();
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.GET_CONNECTED_TCP);
            invoke.setRequestObj(tcpDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<TcpResult>>() {
            });
            responseVo = (ResponseVo<TcpResult>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.debug("开始调用从netty服务器查询未注册长连接信息服务成功");
                return responseVo.getData();
            }
        } catch (Exception e) {
            logger.error("开始调用从netty服务器查询未注册长连接信息服务出现Exception异常", e);
        }
        return null;
    }

    @Override
    public TcpResult selectRegistered() {
        logger.debug("开始调用从netty服务器查询已注册长连接信息服务");

        ResponseVo<TcpResult> responseVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.call.registered.tcp.fail"));
        try {
            TcpDto tcpDto = new TcpDto();
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.GET_REGISTER_TCP);
            invoke.setRequestObj(tcpDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<TcpResult>>() {
            });
            responseVo = (ResponseVo<TcpResult>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.debug("开始调用从netty服务器查询已注册长连接信息服务成功");
                return responseVo.getData();
            }
        } catch (Exception e) {
            logger.error("开始调用从netty服务器查询已注册长连接信息服务出现Exception异常", e);
        }
        return null;
    }

    /**
     * 调用断开未注册TCP连接服务
     * @param channelId
     * @return
     */
    @Override
    public ResponseVo<String> disconnetTcp(String channelId) {
        ResponseVo<String> responseVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.call.disconnect.unregister.tcp.server.fail"));

        try {
            UnRegisterTcpDto unRegisterTcpDto = new UnRegisterTcpDto();
            unRegisterTcpDto.setChannelId(channelId);
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.DISCONNECT_UNREGISTER_TCP);
            invoke.setRequestObj(unRegisterTcpDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<TcpResult>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.debug("开始调用断开未注册TCP连接服务成功");
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("开始调用断开未注册TCP连接服务出现Exception异常", e);
        }
        return responseVo;
    }

    /**
     * 调用断开已注册TCP连接服务
     * @param deviceId
     * @return
     */
    @Override
    public ResponseVo<String> disconnectRegisterTcp(String deviceId) {
        ResponseVo<String> responseVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.call.disconnect.register.tcp.server.fail"));

        try {
            RegisterTcpDto registerTcpDto = new RegisterTcpDto();
            registerTcpDto.setDeviceId(deviceId);

            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.DISCONNECT_REGISTER_TCP);
            invoke.setRequestObj(registerTcpDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<TcpResult>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.debug("开始调用断开已注册TCP连接服务成功");
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("开始调用断开已注册TCP连接服务出现Exception异常", e);
        }
        return responseVo;

    }


    /**
     * 调用发送广告服务接口
     * @param deviceIds
     * @param userId
     * @param position
     * @param merchantCode
     * @return
     */
    private ResponseVo<String> sendAdToDevice(String deviceIds, String userId, String position, String merchantCode) {
        logger.debug("后台管理系统向设备发送广告，位置：{}", position);
        ResponseVo<String> responseVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.call.send.operating.advertisinga.fail"));
        // 封装参数
        SendOperatingAdvertisingDto sendOperatingAdvertisingDto = new SendOperatingAdvertisingDto();
        sendOperatingAdvertisingDto.setDeviceIds(deviceIds);
        sendOperatingAdvertisingDto.setUserId(userId);
        sendOperatingAdvertisingDto.setPosition(position);
        sendOperatingAdvertisingDto.setMerchantCode(merchantCode);
        logger.debug("开始调用发送广告服务");

        try {
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.SEND_OPERATING_ADVERTISING);
            invoke.setRequestObj(sendOperatingAdvertisingDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.send.to.device.admsg"), "");
                LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("向设备发送广告信息出现Exception异常", e);
        }
        return responseVo;
    }

    /**
     * 向设备发送操作消息
     *
     * @param deviceIds 设备ID集合
     * @param method    操作类型
     * @param data    参数（非必填）
     * @return 调用服务结果
     */
    private ResponseVo<String> sendMsgToDevice(Object deviceIds, String method, String data) {

        ResponseVo<String> responseVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.send.msg.to.device.fail"));
        //调用长连接服务前，对DTO对象进行赋值
        DeviceDto deviceDto = new DeviceDto();
        String deviceId = "";
        if (deviceIds instanceof String) {
            deviceId = (String) deviceIds;
        } else if (deviceIds instanceof String[]) {
            deviceId = StringUtils.join(deviceIds, ","); //设备ID数组使用“,”拼接成字符串
        }
        deviceDto.setId(deviceId);
        deviceDto.setFunction(method);//调用设备执行的方法名
        deviceDto.setUserId(SessionUserUtils.getSessionUserId());   //操作员ID
        if (StringUtils.isNotBlank(data)) {//方法执行参数
            deviceDto.setOperateParam(data);
        }

        //开始调用长连接服务接口，调用netty提供的接口，发送消息给对应的设备
        logger.debug("开始调用长连接服务接口");
        try {
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.OPERATE_DEVICE);
            invoke.setRequestObj(deviceDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.info("调用长连接服务成功，操作类型为：", method);

                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.send.to.device.operate.msg"), method);
                LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("向设备发送信息出现Exception异常", e);
        }

        return responseVo;
    }

}
