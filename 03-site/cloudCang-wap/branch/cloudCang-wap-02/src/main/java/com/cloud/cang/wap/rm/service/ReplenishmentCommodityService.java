package com.cloud.cang.wap.rm.service;

import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.wap.rm.vo.ReplenishmentCommodityVo;

import java.util.List;
import java.util.Map;

public interface ReplenishmentCommodityService extends GenericService<ReplenishmentCommodity, String> {


    /**
     * 查询补货单商品明细
     * @param params 查询参数
     * @return
     */
    List<ReplenishmentCommodityVo> selectByMap(Map<String, Object> params);
}