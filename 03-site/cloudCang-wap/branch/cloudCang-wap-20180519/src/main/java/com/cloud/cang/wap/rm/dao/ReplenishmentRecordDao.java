package com.cloud.cang.wap.rm.dao;

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
     * 获取补货单
     * @param recordCode 补货单编号
     * @return
     */
    ReplenishmentRecord selectByCode(String recordCode);

}