package com.cloud.cang.api.sl.service.impl;

import com.cloud.cang.api.sl.dao.DeviceOperDao;
import com.cloud.cang.api.sl.service.DeviceOperService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.sl.DeviceOper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeviceOperServiceImpl extends GenericServiceImpl<DeviceOper, String> implements
        DeviceOperService {

    @Autowired
    DeviceOperDao deviceOperDao;


    @Override
    public GenericDao<DeviceOper, String> getDao() {
        return deviceOperDao;
    }


    @Override
    public String selectLastOpLog(Map<String, String> map) {
        return deviceOperDao.selectLastOpLog(map);
    }

    /**
     * 查找最后一个开门人
     *
     * @param deviceCode
     * @return
     */
    @Override
    public String selectLastUserByDeviceCode(String deviceCode) {
        return deviceOperDao.selectLastUserByDeviceCode(deviceCode);
    }

    /**
     * 查询用户在设备上最后一次开门记录
     *
     * @param userId
     * @param deviceCode
     * @return
     */
    @Override
    public DeviceOper selectByUserIdAndDeviceCode(String userId, String deviceCode) {
        return deviceOperDao.selectByUserIdAndDeviceCode(userId, deviceCode);
    }

    /**
     * 查询设备最后一次关门记录
     *
     * @param deviceCode
     * @return
     */
    @Override
    public DeviceOper selectLastCloseDoorByDeviceCode(String deviceCode){
        return deviceOperDao.selectLastCloseDoorByDeviceCode(deviceCode);
    }
}