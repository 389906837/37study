package com.cloud.cang.api.sm.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.cloud.cang.api.sm.vo.StockVo;
import com.cloud.cang.sb.InventoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.api.sm.dao.DeviceStockDao;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.api.sm.service.DeviceStockService;

@Service
public class DeviceStockServiceImpl extends GenericServiceImpl<DeviceStock, String> implements
        DeviceStockService {

    @Autowired
    DeviceStockDao deviceStockDao;


    @Override
    public GenericDao<DeviceStock, String> getDao() {
        return deviceStockDao;
    }


 /*   @Override
    public InventoryDto calculateCommodity(String deviceId) {
        DeviceStock deviceStock = new DeviceStock();
        deviceStock.setSdeviceId(deviceId);
        List<DeviceStock> deviceStockList = deviceStockDao.selectByEntityWhere(deviceStock);
//		List<>
        return null;
    }*/

    /**
     * 查询设备库存商品总重量
     *
     * @param deviceId
     * @return
     */
    @Override
    public BigDecimal selectTotalWeight(String deviceId) {
        return deviceStockDao.selectTotalWeight(deviceId);
    }

    /**
     * 查询设备商品编号库存明细
     * @param deviceId 设备ID
     */
    @Override
    public List<StockVo> selectStockByDeviceId(String deviceId) {
        return deviceStockDao.selectStockByDeviceId(deviceId);
    }

    @Override
    public DeviceStock selectDeviceStockByCommodityId(String deviceId, String commodityId) {
        return deviceStockDao.selectDeviceStockByCommodityId(deviceId, commodityId);
    }

}