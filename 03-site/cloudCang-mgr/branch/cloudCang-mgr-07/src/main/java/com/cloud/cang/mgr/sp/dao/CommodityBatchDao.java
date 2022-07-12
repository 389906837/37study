package com.cloud.cang.mgr.sp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sp.domain.CommodityBatchDomain;
import com.cloud.cang.mgr.sp.vo.CommodityBatchVo;
import com.cloud.cang.model.sp.CommodityBatch;
import com.github.pagehelper.Page;

/** 商品批次管理信息表(SP_COMMODITY_BATCH) **/
public interface CommodityBatchDao extends GenericDao<CommodityBatch, String> {

    /**
     * 分页查询商品批次信息
     * @param commodityBatchVo
     */
    Page<CommodityBatchDomain> selectByDomainWhere(CommodityBatchVo commodityBatchVo);

    /**
     * 根据商品ID查询商品批次信息
     * @param sid
     * @return
     */
    List<CommodityBatch> selectByCommodityId(String sid);

    /**
     * 自定义修改方法
     * @param commodityBatch
     */
    int updateByIdSelectiveVo(CommodityBatch commodityBatch);



    /** codegen **/
}