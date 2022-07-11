package com.cloud.cang.tec.sm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.tec.sm.vo.RealtimeInventorySynVo;

/**
 * 单设备库存记录表(SM_DEVICE_STOCK)
 **/
public interface DeviceStockDao extends GenericDao<DeviceStock, String> {


    List<RealtimeInventorySynVo> selectRealtimeInventorySyn(String merchantId);


}