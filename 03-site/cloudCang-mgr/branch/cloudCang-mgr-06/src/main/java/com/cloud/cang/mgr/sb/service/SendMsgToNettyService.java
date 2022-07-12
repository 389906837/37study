package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.sb.TcpResult;

/**
 * @version 1.0
 * @ClassName: SendMsgToNettyService
 * @Description: 后台发送消息给netty服务类
 * @Author: zengzexiong
 * @Date: 2018年4月12日20:42:32
 */
public interface SendMsgToNettyService {


    /**
     * 将设备手动离线
     * @param id 设备ID
     * @return
     */
    ResponseVo<String> offline(String id);

    /**
     * 向设备发送二位码
     *
     * @param deviceId
     * @param qrCodeUrl
     * @return
     */
    ResponseVo<String> sendQrCode(String deviceId, String qrCodeUrl);

    /**
     * 向设备发送运营位1广告
     *
     * @param deviceIds
     * @param userId
     * @param merchantCode
     * @return
     */
    ResponseVo<String> sendAdOne(String deviceIds, String userId, String merchantCode);


    /**
     * 向设备发送运营位2广告
     *
     * @param deviceIds
     * @param userId
     * @param merchantCode
     * @return
     */
    ResponseVo<String> sendAdTwo(String deviceIds, String userId, String merchantCode);

    /**
     * 向设备发送运营位3广告
     *
     * @param deviceIds
     * @param userId
     * @param merchantCode
     * @return
     */
    ResponseVo<String> sendAdThree(String deviceIds, String userId, String merchantCode);


    /**
     * 查询所有未注册的TCP连接
     * @return
     */
    TcpResult selectUnRegistered();

    /**
     * 查询所有已经注册的TCP连接
     * @return
     */
    TcpResult selectRegistered();


    /**
     * 断开未注册的TCP连接
     * @param channelId
     * @return
     */
    ResponseVo<String> disconnetTcp(String channelId);

    /**
     * 断开已经注册到服务器的TCP连接
     * @param deviceId
     * @return
     */
    ResponseVo<String> disconnectRegisterTcp(String deviceId);


}
