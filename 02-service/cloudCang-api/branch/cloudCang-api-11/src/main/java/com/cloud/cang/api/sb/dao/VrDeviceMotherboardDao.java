package com.cloud.cang.api.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sb.VrDeviceMotherboard;

/**
 * 视觉设备主板配置(SB_VR_DEVICE_MOTHERBOARD)
 **/
public interface VrDeviceMotherboardDao extends GenericDao<VrDeviceMotherboard, String> {


    VrDeviceMotherboard selectByDeviceId(String deviceId);




    /** codegen **/
}