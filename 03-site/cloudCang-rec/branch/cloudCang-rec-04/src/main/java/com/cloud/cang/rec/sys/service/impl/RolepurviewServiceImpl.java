package com.cloud.cang.rec.sys.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.rec.sys.dao.RolepurviewDao;
import com.cloud.cang.rec.sys.service.RolepurviewService;
import com.cloud.cang.model.sys.Rolepurview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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