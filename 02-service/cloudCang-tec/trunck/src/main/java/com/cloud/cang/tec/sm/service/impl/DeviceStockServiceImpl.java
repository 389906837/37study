package com.cloud.cang.tec.sm.service.impl;

import java.util.List;
import java.util.Map;

import com.cloud.cang.tec.sm.service.StandardDetailService;
import com.cloud.cang.tec.sm.vo.RealtimeInventorySynVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.sm.dao.DeviceStockDao;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.tec.sm.service.DeviceStockService;

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
     * 查询商品实时库存 根据商户ID
     *
     * @param merchantId 商户ID
     */
    @Override
    public List<RealtimeInventorySynVo> selectRealtimeInventorySyn(String merchantId) {
        return deviceStockDao.selectRealtimeInventorySyn(merchantId);
    }

}