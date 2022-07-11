package com.cloud.cang.tec.rm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.rm.dao.ReplenishmentRecordDao;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.tec.rm.service.ReplenishmentRecordService;

@Service
public class ReplenishmentRecordServiceImpl extends GenericServiceImpl<ReplenishmentRecord, String> implements
        ReplenishmentRecordService {

    @Autowired
    ReplenishmentRecordDao replenishmentRecordDao;


    @Override
    public GenericDao<ReplenishmentRecord, String> getDao() {
        return replenishmentRecordDao;
    }


    /**
     * 查询昨日补货数
     *
     * @param merchantId 商户ID
     * @return int
     */
    @Override
    public int selectYesterdayReplenishmentNum(String merchantId) {
        return replenishmentRecordDao.selectYesterdayReplenishmentNum(merchantId);
    }
}