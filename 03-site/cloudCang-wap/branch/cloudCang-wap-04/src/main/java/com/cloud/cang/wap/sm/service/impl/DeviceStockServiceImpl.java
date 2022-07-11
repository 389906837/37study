package com.cloud.cang.wap.sm.service.impl;

import java.util.List;

import com.cloud.cang.wap.sm.vo.DeviceStockList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.wap.sm.dao.DeviceStockDao;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.wap.sm.service.DeviceStockService;

@Service
public class DeviceStockServiceImpl extends GenericServiceImpl<DeviceStock, String> implements
        DeviceStockService {

    @Autowired
    DeviceStockDao deviceStockDao;


    @Override
    public GenericDao<DeviceStock, String> getDao() {
        return deviceStockDao;
    }

    /**
     * 根据设备编号查询设备商品库存
     *
     * @param deviceCode
     * @return
     */
    @Override
    public List<DeviceStockList> selectDeviceStock(String deviceCode) {
        return deviceStockDao.selectDeviceStock(deviceCode);
    }


}