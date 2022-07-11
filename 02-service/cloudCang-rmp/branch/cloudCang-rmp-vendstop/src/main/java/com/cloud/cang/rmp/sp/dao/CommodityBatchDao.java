package com.cloud.cang.rmp.sp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sp.CommodityBatch;

/** 商品批次管理信息表(SP_COMMODITY_BATCH) **/
public interface CommodityBatchDao extends GenericDao<CommodityBatch, String> {
    /**
     * 获取商品批次数据
     * @param commodityId 商品ID
     * @return
     */
    CommodityBatch selectByCommodityId(String commodityId);
    /**
     *  根据主键 锁定批次
     * @param id 批次ID
     * @return
     */
    CommodityBatch selectByPrimaryKeyForUpdate(String id);
    /**
     * 更新商品批次数据
     * @param map commodityId 商品ID sbatchNo 批次号
     */
    int updateBySbatchNo(Map<String, Object> map);
}