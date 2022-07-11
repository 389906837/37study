package com.cloud.cang.tec.sm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.sm.dao.StockOperRecordDao;
import com.cloud.cang.model.sm.StockOperRecord;
import com.cloud.cang.tec.sm.service.StockOperRecordService;

@Service
public class StockOperRecordServiceImpl extends GenericServiceImpl<StockOperRecord, String> implements
        StockOperRecordService {

    @Autowired
    StockOperRecordDao stockOperRecordDao;


    @Override
    public GenericDao<StockOperRecord, String> getDao() {
        return stockOperRecordDao;
    }


    /**
     * 查询昨日库存变化数(销售数)
     *
     * @param merchantId 商户ID
     * @return int
     */
    @Override
    public Map selectYesterdayStockChangeAndReplenishmentNum(String merchantId) {
        return stockOperRecordDao.selectYesterdayStockChangeAndReplenishmentNum(merchantId);
    }

}