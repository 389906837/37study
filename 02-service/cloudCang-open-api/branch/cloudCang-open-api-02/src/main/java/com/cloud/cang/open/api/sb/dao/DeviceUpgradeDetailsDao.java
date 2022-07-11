package com.cloud.cang.open.api.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sb.DeviceUpgradeDetails;

/** 设备升级明细表(SB_DEVICE_UPGRADE_DETAILS) **/
public interface DeviceUpgradeDetailsDao extends GenericDao<DeviceUpgradeDetails, String> {
    /**
     * 更新数据
     * @param paramMap 更新参数
     * @return
     */
    Integer updateByMap(Map<String, Object> paramMap);
}