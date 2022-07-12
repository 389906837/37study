package com.cloud.cang.mgr.wz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.wz.dao.DeviceNewsDao;
import com.cloud.cang.model.wz.DeviceNews;
import com.cloud.cang.mgr.wz.service.DeviceNewsService;

@Service
public class DeviceNewsServiceImpl extends GenericServiceImpl<DeviceNews, String> implements
        DeviceNewsService {

    @Autowired
    DeviceNewsDao deviceNewsDao;


    @Override
    public GenericDao<DeviceNews, String> getDao() {
        return deviceNewsDao;
    }


    /**
     * 删除广告资源记录
     *
     * @param advertisId 资源ID
     */
    @Override
    public void deleteByAdvertisId(String advertisId) {
        deviceNewsDao.deleteByAdvertisId(advertisId);
    }

    /**
     * 查询设备广告信息
     *
     * @param deviceId 设备ID
     * @param advId    资讯ID
     */
    @Override
    public DeviceNews selectByAdvIdAndDeviceId(String deviceId, String advId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("advId", advId);
        return deviceNewsDao.selectByAdvIdAndDeviceId(paramMap);
    }

    /**
     * 删除广告资源记录
     *
     * @param advertisId 资源ID
     * @param deviceId   设备ID
     */
    @Override
    public void deleteByAdvIdAndDeviceId(String advertisId, String deviceId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("advertisId", advertisId);
        deviceNewsDao.deleteByAdvIdAndDeviceId(paramMap);
    }

}