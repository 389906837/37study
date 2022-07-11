package com.cloud.cang.wap.sp.service.impl;

import java.util.List;
import java.util.Map;

import com.cloud.cang.wap.ac.vo.CommodityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.wap.sp.dao.CommodityInfoDao;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.wap.sp.service.CommodityInfoService;

@Service
public class CommodityInfoServiceImpl extends GenericServiceImpl<CommodityInfo, String> implements
        CommodityInfoService {

    @Autowired
    CommodityInfoDao commodityInfoDao;


    @Override
    public GenericDao<CommodityInfo, String> getDao() {
        return commodityInfoDao;
    }


    @Override
    public List<CommodityVo> selectByCondition(Map<String, String> map) {
        return commodityInfoDao.selectByCondition(map);
    }
}