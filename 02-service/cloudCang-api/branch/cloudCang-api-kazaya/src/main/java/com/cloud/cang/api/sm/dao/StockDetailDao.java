package com.cloud.cang.api.sm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.inventory.CommodityVo;
import com.cloud.cang.model.sm.StockDetail;
import org.apache.ibatis.annotations.Param;

/** 设备实时库存明细(SM_STOCK_DETAIL) **/
public interface StockDetailDao extends GenericDao<StockDetail, String> {
    /**
     * 查询设备库存
     * @param deviceId 设备ID
     * @return
     */
    List<Map<String,Object>> selectMapByDeviceId(String deviceId);

    /**
     * 根据SIDENTIFIES查询出所有的库存详细，按照商品分类
     * @param lables
     * @return
     */
    List<CommodityVo> selectCommodityVoGruopByCommodityCode(@Param("set")Set<String> lables);

}