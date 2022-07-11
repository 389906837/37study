package com.cloud.cang.api.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sb.MonitorDataConf;

/**
 * 设备监控数据配置信息表(SB_MONITOR_DATA_CONF)
 **/
public interface MonitorDataConfDao extends GenericDao<MonitorDataConf, String> {

    /**
     * 根据设备ID查询设备监控配置信息
     * @param deviceId
     * @return
     */
    MonitorDataConf selectByDeviceId(String deviceId);


    /** codegen **/
}