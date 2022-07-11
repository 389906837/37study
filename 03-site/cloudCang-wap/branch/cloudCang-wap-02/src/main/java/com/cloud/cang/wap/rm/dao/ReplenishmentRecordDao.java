package com.cloud.cang.wap.rm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.wap.rm.vo.HistoryReplenishmentVo;
import com.github.pagehelper.Page;

/**
 * 商品补货记录信息表(RM_REPLENISHMENT_RECORD)
 **/
public interface ReplenishmentRecordDao extends GenericDao<ReplenishmentRecord, String> {
    /**
     * 获取补货单
     *
     * @param recordCode 补货单编号
     * @return
     */
    ReplenishmentRecord selectByCode(String recordCode);

    /**
     * 获取历史补货单
     *
     * @param
     * @return
     */
    Page<HistoryReplenishmentVo> selectHistoryReplenishment(Map<String, Object> map);
}