package com.cloud.cang.act.ac.service.impl;

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

import com.cloud.cang.act.ac.dao.UseRangeDao;
import com.cloud.cang.model.ac.UseRange;
import com.cloud.cang.act.ac.service.UseRangeService;

@Service
public class UseRangeServiceImpl extends GenericServiceImpl<UseRange, String> implements
        UseRangeService {

    @Autowired
    UseRangeDao useRangeDao;


    @Override
    public GenericDao<UseRange, String> getDao() {
        return useRangeDao;
    }


    /**
     * 根据活动ID查询活动范围
     *
     * @Param acId 活动ID
     */
    @Override
    public UseRange selectByAcId(String acId) {
        return useRangeDao.selectByAcId(acId);
    }

}