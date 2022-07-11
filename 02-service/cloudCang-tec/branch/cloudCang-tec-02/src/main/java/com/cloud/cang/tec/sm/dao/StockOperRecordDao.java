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
import com.cloud.cang.model.sm.StockOperRecord;

/** 单设备库存操作纪律(SM_STOCK_OPER_RECORD) **/
public interface StockOperRecordDao extends GenericDao<StockOperRecord, String> {


    /**
     * 查询昨日库存变化数(销售数)
     *
     * @param merchantId 商户ID
     * @return int
     */
    Map selectYesterdayStockChangeAndReplenishmentNum(String merchantId);
}