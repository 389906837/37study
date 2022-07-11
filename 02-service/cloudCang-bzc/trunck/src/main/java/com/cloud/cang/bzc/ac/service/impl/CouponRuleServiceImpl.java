package com.cloud.cang.bzc.ac.service.impl;

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

import com.cloud.cang.bzc.ac.dao.CouponRuleDao;
import com.cloud.cang.model.ac.CouponRule;
import com.cloud.cang.bzc.ac.service.CouponRuleService;

@Service
public class CouponRuleServiceImpl extends GenericServiceImpl<CouponRule, String> implements
        CouponRuleService {

    @Autowired
    CouponRuleDao couponRuleDao;


    @Override
    public GenericDao<CouponRule, String> getDao() {
        return couponRuleDao;
    }

    @Override
    public CouponRule selectBySacId(String sacId) {
        return couponRuleDao.selectBySacId(sacId);
    }


}