package com.cloud.cang.bzc.sl.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sl.DeviceOper;

/** 设备操作日志(SL_DEVICE_OPER) **/
public interface DeviceOperDao extends GenericDao<DeviceOper, String> {
    /**
     * 获取最近一个会员的开门日志
     * @param deviceCode 设备编号
     */
    DeviceOper selectLastByDeviceId(String deviceCode);

}