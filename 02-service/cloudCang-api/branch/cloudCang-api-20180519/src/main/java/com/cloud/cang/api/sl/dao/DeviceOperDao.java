package com.cloud.cang.api.sl.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sl.DeviceOper;

/** 设备操作日志(SL_DEVICE_OPER) **/
public interface DeviceOperDao extends GenericDao<DeviceOper, String> {


    /**
     * 查询设备最后一个开门日志
     * @param map
     * @return
     */
    String selectLastOpLog(Map<String, String> map);



    /** codegen **/
}