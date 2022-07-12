package com.cloud.cang.mgr.sys.service.impl;

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

import com.cloud.cang.mgr.sys.dao.RolepurviewDao;
import com.cloud.cang.model.sys.Rolepurview;
import com.cloud.cang.mgr.sys.service.RolepurviewService;

@Service
public class RolepurviewServiceImpl extends GenericServiceImpl<Rolepurview, String> implements
        RolepurviewService {

    @Autowired
    RolepurviewDao rolepurviewDao;


    @Override
    public GenericDao<Rolepurview, String> getDao() {
        return rolepurviewDao;
    }




}