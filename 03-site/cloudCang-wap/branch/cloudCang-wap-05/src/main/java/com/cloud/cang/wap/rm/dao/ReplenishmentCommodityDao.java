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
import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.wap.rm.vo.ReplenishmentCommodityVo;

/** 补货商品明细表(RM_REPLENISHMENT_COMMODITY) **/
public interface ReplenishmentCommodityDao extends GenericDao<ReplenishmentCommodity, String> {
    /**
     * 查询补货单商品明细
     * @param params 查询参数
     * @return
     */
    List<ReplenishmentCommodityVo> selectByMap(Map<String, Object> params);

}