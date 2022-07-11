package com.cloud.cang.wap.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sb.DeviceInfo;

/** 设备基础信息表(SB_DEVICE_INFO) **/
public interface DeviceInfoDao extends GenericDao<DeviceInfo, String> {
    /**
     * 获取设备信息
     * @param deviceCode 设备编号
     */
    DeviceInfo selectByCode(String deviceCode);

}