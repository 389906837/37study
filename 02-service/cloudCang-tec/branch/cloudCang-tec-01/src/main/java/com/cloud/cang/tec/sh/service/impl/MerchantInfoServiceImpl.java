package com.cloud.cang.tec.sh.service.impl;

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

import com.cloud.cang.tec.sh.dao.MerchantInfoDao;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.tec.sh.service.MerchantInfoService;

@Service
public class MerchantInfoServiceImpl extends GenericServiceImpl<MerchantInfo, String> implements
        MerchantInfoService {

    @Autowired
    MerchantInfoDao merchantInfoDao;


    @Override
    public GenericDao<MerchantInfo, String> getDao() {
        return merchantInfoDao;
    }


    /**
     * 商户签约到期状态变更定时任务
     *
     * @param merchantId 商户ID
     */
    @Override

    public List<MerchantInfo> selectExpired(String merchantId) {
        return merchantInfoDao.selectExpired(merchantId);
    }

}