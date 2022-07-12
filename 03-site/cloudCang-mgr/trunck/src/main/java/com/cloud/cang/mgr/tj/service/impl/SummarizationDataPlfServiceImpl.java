package com.cloud.cang.mgr.tj.service.impl;

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

import com.cloud.cang.mgr.tj.dao.SummarizationDataPlfDao;
import com.cloud.cang.model.tj.SummarizationDataPlf;
import com.cloud.cang.mgr.tj.service.SummarizationDataPlfService;

@Service
public class SummarizationDataPlfServiceImpl extends GenericServiceImpl<SummarizationDataPlf, String> implements
        SummarizationDataPlfService {

    @Autowired
    SummarizationDataPlfDao summarizationDataPlfDao;


    @Override
    public GenericDao<SummarizationDataPlf, String> getDao() {
        return summarizationDataPlfDao;
    }


    @Override
/**
 * 根据商户Id查询统计表
 * @param
 * @return
 */
    public  SummarizationDataPlf selectByMerchantId(String merchantId){
        return  summarizationDataPlfDao.selectByMerchantId(merchantId);
    }
}