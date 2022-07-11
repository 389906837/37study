package com.cloud.cang.tec.rm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.rm.ReplenishmentRecord;

/** 商品补货记录信息表(RM_REPLENISHMENT_RECORD) **/
public interface ReplenishmentRecordDao extends GenericDao<ReplenishmentRecord, String> {


    /**
     * 查询昨日补货数
     *
     * @param merchantId 商户ID
     * @return int
     */
    int selectYesterdayReplenishmentNum(String merchantId);}