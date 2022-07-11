package com.cloud.cang.rmp.sp.service;

import com.cloud.cang.model.sp.CommodityBatch;
import com.cloud.cang.generic.GenericService;

public interface CommodityBatchService extends GenericService<CommodityBatch, String> {


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
     * @param sbatchNo 批次号
     * @param commodityId 商品ID
     */
    int updateBySbatchNo(String sbatchNo, String commodityId);
}