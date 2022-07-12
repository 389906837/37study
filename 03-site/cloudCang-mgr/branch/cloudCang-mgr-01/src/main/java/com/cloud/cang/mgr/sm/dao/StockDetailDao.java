package com.cloud.cang.mgr.sm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sm.vo.StockDetailVo;
import com.cloud.cang.model.sm.StockDetail;
import com.github.pagehelper.Page;

/** 设备实时库存明细(SM_STOCK_DETAIL) **/
public interface StockDetailDao extends GenericDao<StockDetail, String> {

    Page<StockDetail> selectStockDetail(StockDetailVo stockDetailVo);

    List<StockDetail> selectInfoId(String sid);

    /**
     * 根据库存查询对应明细信息
     *
     * @param sstockId
     * @return
     */
    List<StockDetail> selectSingleStockDetail(String sstockId);

    /**
     * 下架
     *
     * @param stockDetaiVo
     */
    void updateById(StockDetail stockDetaiVo);

}