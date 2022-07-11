package com.cloud.cang.api.sm.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.inventory.CommodityVo;
import com.cloud.cang.model.sm.StockDetail;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StockDetailService extends GenericService<StockDetail, String> {

    /**
     * 查询设备库存
     * @param deviceId 设备ID
     * @return
     */
    Map<String,Integer> selectMapByDeviceId(String deviceId);

    /**
     * 根据SIDENTIFIES查询出所有的库存详细，按照商品分类
     * @param lables
     * @return
     */
    List<CommodityVo> selectCommodityVoGruopByCommodityCode(Set<String> lables);
}