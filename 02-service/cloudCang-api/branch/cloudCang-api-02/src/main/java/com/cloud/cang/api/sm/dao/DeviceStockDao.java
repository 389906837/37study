package com.cloud.cang.api.sm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sm.DeviceStock;

/** 单设备库存记录表(SM_DEVICE_STOCK) **/
public interface DeviceStockDao extends GenericDao<DeviceStock, String> {


	/** codegen **/
}