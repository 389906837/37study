package com.cloud.cang.api.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sb.VrDeviceConf;

/**
 * 视觉设备配置(SB_VR_DEVICE_CONF)
 **/
public interface VrDeviceConfDao extends GenericDao<VrDeviceConf, String> {

    /**
     * 根据设备ID查询设备视觉设备配置
     * @param deviceId
     * @return
     */
    List<VrDeviceConf> selectByDeviceId(String deviceId);


    /** codegen **/
}