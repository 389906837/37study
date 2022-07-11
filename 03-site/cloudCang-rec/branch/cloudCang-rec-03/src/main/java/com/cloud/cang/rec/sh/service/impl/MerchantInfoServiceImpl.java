package com.cloud.cang.rec.sh.service.impl;

import java.util.List;

import com.cloud.cang.rec.sh.domain.MerchantInfoDomain;
import com.cloud.cang.rec.sh.vo.MerchantInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.rec.sh.dao.MerchantInfoDao;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.rec.sh.service.MerchantInfoService;

@Service
public class MerchantInfoServiceImpl extends GenericServiceImpl<MerchantInfo, String> implements
        MerchantInfoService {

    @Autowired
    MerchantInfoDao merchantInfoDao;


    @Override
    public GenericDao<MerchantInfo, String> getDao() {
        return merchantInfoDao;
    }


    @Override
    public Page<MerchantInfoVo> selectPageByDomainWhere(Page<MerchantInfoVo> page, MerchantInfoDomain merchantInfoDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return merchantInfoDao.selectPageByDomainWhere(merchantInfoDomain);
    }

    @Override
    public MerchantInfo selectByCode(String code){
        return merchantInfoDao.selectByCode(code);
    }


}