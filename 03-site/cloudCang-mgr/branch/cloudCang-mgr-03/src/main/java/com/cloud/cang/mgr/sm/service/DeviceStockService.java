package com.cloud.cang.mgr.sm.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.sm.domain.DeviceStockDomain;
import com.cloud.cang.mgr.sm.vo.DeviceStockVo;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface DeviceStockService extends GenericService<DeviceStock, String> {

    /**
     * 总库存列表接口
     *
     * @param page
     * @param deviceStockVo
     * @return
     */
    Page<DeviceStockDomain> selectDeviceStock(Page<DeviceStockDomain> page, DeviceStockVo deviceStockVo);

    /**
     * 单机库存查询
     *
     * @param page
     * @param deviceStockVo
     * @return
     */
    Page<DeviceStockDomain> selectOneDeviceStock(Page<DeviceStockDomain> page, DeviceStockVo deviceStockVo);

    /**
     * 修改库存和单价
     *
     * @param deviceStock
     */
    ResponseVo<DeviceStock> updatePriceAndStock(DeviceStock deviceStock);

    /**
     * 商品库存手动下架
     *
     * @param deviceStock
     * @param unType
     * @param unNum
     * @return
     */
    ResponseVo saveUndercarriage(DeviceStock deviceStock, Integer unType, Integer unNum);
}