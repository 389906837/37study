package com.cloud.cang.wap.rm.service.impl;

import java.util.List;
import java.util.Map;

import com.cloud.cang.wap.rm.vo.HistoryReplenishmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.wap.rm.dao.ReplenishmentRecordDao;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.wap.rm.service.ReplenishmentRecordService;

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
     * 获取补货单
     *
     * @param recordCode 补货单编号
     * @return
     */
    @Override
    public ReplenishmentRecord selectByCode(String recordCode) {
        return replenishmentRecordDao.selectByCode(recordCode);
    }

    /**
     * 获取历史补货单
     *
     * @param
     * @return
     */
    @Override
    public Page<HistoryReplenishmentVo> selectHistoryReplenishment(Page<HistoryReplenishmentVo> page, Map<String, Object> map) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return replenishmentRecordDao.selectHistoryReplenishment(map);
    }
}