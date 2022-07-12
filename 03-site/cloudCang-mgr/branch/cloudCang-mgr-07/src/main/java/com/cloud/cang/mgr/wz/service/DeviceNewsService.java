package com.cloud.cang.mgr.wz.service;

import com.cloud.cang.model.wz.DeviceNews;
import com.cloud.cang.generic.GenericService;

public interface DeviceNewsService extends GenericService<DeviceNews, String> {

    /**
     * 删除广告资源记录
     * @param advertisId 资源ID
     */
    void deleteByAdvertisId(String advertisId);

    /**
     * 查询设备广告信息
     * @param deviceId 设备ID
     * @param advId 资讯ID
     */
    DeviceNews selectByAdvIdAndDeviceId(String deviceId, String advId);
    /**
     * 删除广告资源记录
     * @param advertisId 资源ID
     * @param deviceId 设备ID
     */
    void deleteByAdvIdAndDeviceId(String advertisId, String deviceId);
}