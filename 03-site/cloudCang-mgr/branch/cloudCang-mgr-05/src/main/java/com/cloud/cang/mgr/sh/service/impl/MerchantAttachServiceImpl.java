package com.cloud.cang.mgr.sh.service.impl;

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

import com.cloud.cang.mgr.sh.dao.MerchantAttachDao;
import com.cloud.cang.model.sh.MerchantAttach;
import com.cloud.cang.mgr.sh.service.MerchantAttachService;

@Service
public class MerchantAttachServiceImpl extends GenericServiceImpl<MerchantAttach, String> implements
        MerchantAttachService {

    @Autowired
    MerchantAttachDao merchantAttachDao;


    @Override
    public GenericDao<MerchantAttach, String> getDao() {
        return merchantAttachDao;
    }

    /**
     * 新增商户商户资质附件信息表
     *
     * @param
     * @return
     */
    @Override
    public void insertAll(MerchantAttach merchantAttach) {
        merchantAttachDao.insertAll(merchantAttach);
    }

    /**
     * 删除商户时删除其商户资质附件信息表
     *
     * @param merchantAttach
     */
    @Override
    public void upBymerchantId(MerchantAttach merchantAttach) {
        merchantAttachDao.upBymerchantId(merchantAttach);
    }

}