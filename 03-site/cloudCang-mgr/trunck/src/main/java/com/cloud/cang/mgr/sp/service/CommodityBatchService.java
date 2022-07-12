package com.cloud.cang.mgr.sp.service;

import com.cloud.cang.mgr.sp.domain.CommodityBatchDomain;
import com.cloud.cang.mgr.sp.vo.CommodityBatchVo;
import com.cloud.cang.model.sp.CommodityBatch;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;

public interface CommodityBatchService extends GenericService<CommodityBatch, String> {


    /**
     * 分页查询商品批次信息
     * @param page
     * @param commodityBatchVo
     * @return
     */
    Page<CommodityBatchDomain> selectPageByDomainWhere(Page<CommodityBatchDomain> page, CommodityBatchVo commodityBatchVo);


    /**
     * 根据商品ID查询商品批次信息
     * @param sid
     * @return
     */
    List<CommodityBatch> selectByCommodityId(String sid);

    /**
     * 批量添加商品批次信息
     * @param commodityIds
     * @param commodityBatch
     */
    void insertCommodityIds(String commodityIds, CommodityBatch commodityBatch);


    /**
     * 添加商品批次信息
     *
     * @param commodityBatch
     */
    void insertCommodityBatch(CommodityBatch commodityBatch, String sbatchNo);

    /**
     * 自定义修改批次信息
     * @param commodityBatch
     */
    void updateBySelectiveVo(CommodityBatch commodityBatch);


}