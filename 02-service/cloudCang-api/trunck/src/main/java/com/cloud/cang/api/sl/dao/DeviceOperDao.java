package com.cloud.cang.api.sl.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sl.DeviceOper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 设备操作日志(SL_DEVICE_OPER)
 **/
public interface DeviceOperDao extends GenericDao<DeviceOper, String> {


    /**
     * 查询设备最后一个开门日志
     *
     * @param map
     * @return
     */
    String selectLastOpLog(Map<String, String> map);

    /**
     * @param sdeviceCode
     * @return
     */
    String selectLastUserByDeviceCode(String sdeviceCode);


    /**
     * 查询用户在设备上最后一次开门记录
     *
     * @param userId
     * @param deviceCode
     * @return
     */
    DeviceOper selectByUserIdAndDeviceCode(@Param("userId") String userId, @Param("deviceCode") String deviceCode);

    /**
     * 查询设备最后一次关门记录
     *
     * @param deviceCode
     * @return
     */
    DeviceOper selectLastCloseDoorByDeviceCode(String deviceCode);
}