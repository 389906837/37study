package com.cloud.cang.mgr.lm.service.impl;

import java.util.List;

import com.cloud.cang.mgr.lm.domain.LabelBatchCommodityDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.lm.dao.LabelBatchCommodityDao;
import com.cloud.cang.model.lm.LabelBatchCommodity;
import com.cloud.cang.mgr.lm.service.LabelBatchCommodityService;

@Service
public class LabelBatchCommodityServiceImpl extends GenericServiceImpl<LabelBatchCommodity, String> implements
        LabelBatchCommodityService {

    @Autowired
    LabelBatchCommodityDao labelBatchCommodityDao;


    @Override
    public GenericDao<LabelBatchCommodity, String> getDao() {
        return labelBatchCommodityDao;
    }


    /**
     * 根据视觉编号修改
     *
     * @param labelBatchCommodityDomain
     */
    @Override
    public void updateBySvrCode(LabelBatchCommodityDomain labelBatchCommodityDomain) {
         labelBatchCommodityDao.updateBySvrCode(labelBatchCommodityDomain);
    }

}