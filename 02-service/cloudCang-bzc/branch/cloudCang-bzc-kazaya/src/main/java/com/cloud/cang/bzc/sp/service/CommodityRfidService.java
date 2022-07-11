package com.cloud.cang.bzc.sp.service;

import com.cloud.cang.inventory.CommodityVo;
import com.cloud.cang.model.sp.CommodityRfid;
import com.cloud.cang.generic.GenericService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface CommodityRfidService extends GenericService<CommodityRfid, String> {

    /**
     * 根据rfids标签集合，查询标签所属哪个商品
     * @param lables
     * @return
     */
    List<CommodityVo> selectCommodityVoGruopByCommodityCode(@Param("lables") Set<String> lables);
}