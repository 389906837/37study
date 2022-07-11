package com.cloud.cang.api.sb.service;

import com.cloud.cang.api.ws.domain.DeviceModelConfigInfo;
import com.cloud.cang.api.ws.domain.LockPinConfigInfo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.sb.DeviceModel;
import com.cloud.cang.generic.GenericService;

public interface DeviceModelService extends GenericService<DeviceModel, String> {


    /**
     * 摄像头相关参数 = 摄像头+品牌+型号+方法
     *
     * @param deviceId 设备ID
     * @return 摄像头+品牌+型号+方法
     */
    ResponseVo<String> getDeviceCaremaConfigInfo(String deviceId);

    /**
     * 获取设备芯片引脚配置信息失败
     *
     * @param deviceId
     * @return
     */
    ResponseVo<LockPinConfigInfo> getLockPinConfigInfo(String deviceId);

    /**
     * 摄像头配置--锁引脚参数信息
     *
     * @param deviceId
     * @return
     */
    ResponseVo<DeviceModelConfigInfo> getDeviceModelConfigInfo(String deviceId);


    /**
     * 根据设备Id获取设备型号信息
     *
     * @param deviceId
     * @return
     */
    DeviceModel selectByDeviceId(String deviceId);
}