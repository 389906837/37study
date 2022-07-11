package com.cloud.cang.tec.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sb.DeviceInfo;

/**
 * 设备基础信息表(SB_DEVICE_INFO)
 **/
public interface DeviceInfoDao extends GenericDao<DeviceInfo, String> {


    /**
     * 查看商户所有设备
     *
     * @param merchantId 商户ID
     */
    String selectDevice(String merchantId);
}