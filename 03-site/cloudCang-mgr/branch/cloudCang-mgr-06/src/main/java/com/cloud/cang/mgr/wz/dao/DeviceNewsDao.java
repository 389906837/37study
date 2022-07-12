package com.cloud.cang.mgr.wz.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.wz.DeviceNews;

/**
 * 设备广告公告信息表(WZ_DEVICE_NEWS)
 **/
public interface DeviceNewsDao extends GenericDao<DeviceNews, String> {


    /**
     * 删除广告资源记录
     *
     * @param advertisId 资源ID
     */
    void deleteByAdvertisId(String advertisId);

    /**
     * 查询设备广告信息
     *
     * @param paramMap deviceId 设备ID advId 资讯ID
     */
    DeviceNews selectByAdvIdAndDeviceId(Map<String, Object> paramMap);

    /**
     * 删除广告资源记录
     *
     * @param paramMap advertisId 资源ID deviceId 设备ID
     */
    void deleteByAdvIdAndDeviceId(Map<String, Object> paramMap);
}