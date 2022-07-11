package com.cloud.cang.api.sb.dao;

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
     * 通过商户ID获取正常使用的设备ID
     * @param merchantId
     * @return
     */
    List<String> selectIdByMerchantId(String merchantId);

    /**
     * 查询设备信息
     * @param deviceCode 设备编号
     * @return
     */
    DeviceInfo selectByCode(String deviceCode);
    /** codegen **/
}