package com.cloud.cang.api.sb.service;

import com.cloud.cang.api.netty.vo.http.DeviceDomain;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.VoiceModel;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface DeviceRegisterService extends GenericService<DeviceRegister, String> {


    /**
     * 设备注册
     * @param reqIp
     * @return 返回注册码
     */
    ResponseVo<String> register(String reqIp);

    /**
     * 通过设备注册码查询设备信息
     *
     * @param deviceID
     * @param registerCode
     * @return 返回注册码
     */
    DeviceRegister selectRegister(String deviceID, String registerCode);

    /**
     * 查询所有已经注册的设备token
     * @return
     */
    List<String> selectAll();

    /**
     * 根据设备ID查询设备
     * @param id 设备ID
     * @return 设备
     */
    DeviceRegister selectOneById(String id);

    /**
     * 已注册设备获取设备信息
     * @param deviceId 设备ID
     * @param key 安全密钥
     * @return 设备信息----“设备编号_设备类型”
     */
    ResponseVo<DeviceDomain> getDeviceInfo(String deviceId, String key) throws Exception;


    /**
     * 人脸识别设备注册
     *
     * @param reqIp 注册码
     * @return
     */
    ResponseVo<String> faceRegister(String reqIp);

    /**
     * 获取语音包
     *
     * @param deviceId 设备ID
     * @param key      通信密钥
     * @return
     */
    ResponseVo<String> getUpdateVoice(String deviceId, String key);

    /**
     * 通过设备ID与通信秘钥校验设备是否通过注册审核
     *
     * @param deviceId 设备ID
     * @param key      通信秘钥
     * @return 通过返回true，否则返回false
     */
    Boolean checkDeviceIdAndKey(String deviceId, String key);

}