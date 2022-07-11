package com.cloud.cang.tec.xx.service.impl;

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

import com.cloud.cang.tec.xx.dao.SupplierInfoDao;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.tec.xx.service.SupplierInfoService;

@Service
public class SupplierInfoServiceImpl extends GenericServiceImpl<SupplierInfo, String> implements
        SupplierInfoService {

    @Autowired
    SupplierInfoDao supplierInfoDao;


    @Override
    public GenericDao<SupplierInfo, String> getDao() {
        return supplierInfoDao;
    }


    @Override
    public List<SupplierInfo> selectByMerchantId(String merchantId) {
        return supplierInfoDao.selectByMerchantId(merchantId);
    }

}