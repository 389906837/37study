package com.cloud.cang.bzc.ac.service.impl;

import com.cloud.cang.bzc.ac.dao.ActivityConfDao;
import com.cloud.cang.bzc.ac.service.ActivityConfService;
import com.cloud.cang.bzc.om.vo.ActivityVo;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.ac.ActivityConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityConfServiceImpl extends GenericServiceImpl<ActivityConf, String> implements
        ActivityConfService {

    @Autowired
    ActivityConfDao activityConfDao;


    @Override
    public GenericDao<ActivityConf, String> getDao() {
        return activityConfDao;
    }


    @Override
    public List<ActivityVo> selectAllInfo(String smerchantCode) {
        return activityConfDao.selectAllActivity(smerchantCode);
    }


}