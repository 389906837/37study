package com.cloud.cang.mgr.sm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sm.domain.DeviceStockDomain;
import com.cloud.cang.mgr.sm.vo.DeviceStockVo;
import com.cloud.cang.model.sm.DeviceStock;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/** 单设备库存记录表(SM_DEVICE_STOCK) **/
public interface DeviceStockDao extends GenericDao<DeviceStock, String> {

    /**
     * 总库存查询
     * @param deviceStockVo
     * @return
     */
    Page<DeviceStockDomain> selectDeviceStock(DeviceStockVo deviceStockVo);

    /**
     * 单机库存查询
     * @param deviceStockVo
     * @return
     */
    Page<DeviceStockDomain> selectOneDeviceStock(DeviceStockVo deviceStockVo);

    /**
     * 删除单设备库存记录表中对应商品记录
     * @param scommodityId
     */
    void deleteByCommodityId(String scommodityId);

}