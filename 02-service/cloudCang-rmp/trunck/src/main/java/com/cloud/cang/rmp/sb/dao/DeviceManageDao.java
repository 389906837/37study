package com.cloud.cang.rmp.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sb.DeviceManage;

/** 设备管理信息表(SB_DEVICE_MANAGE) **/
public interface DeviceManageDao extends GenericDao<DeviceManage, String> {
    /**
     * 根据设备查询 设备管理人员信息
     * @param sdeviceId
     * @return
     */
    DeviceManage selectByDeviceId(String sdeviceId);


}