package com.cloud.cang.act.ac.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.act.ac.dao.ActivityConfDao;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.act.ac.service.ActivityConfService;

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
    public ActivityConf selectActivityConfByCode(String code) {
        return activityConfDao.selectActivityConfByCode(code);
    }

    /**
     * 查看设备活动
     *
     * @param
     * @return
     */
    @Override
    public List<String> viewActivityInformation(String deviceId) {
        return activityConfDao.viewActivityInformation(deviceId);
    }


    /**
     * 根据活动编号和商户查找有效活动
     *
     * @param code
     * @return
     */
    @Override
    public ActivityConf selectActivityConfByCodeAndMerId(String code, String merchantId) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("merchantId", merchantId);
        return activityConfDao.selectActivityConfByCodeAndMerId(map);
    }

    /**
     * 查询活动配置并锁表
     *
     * @param
     * @return
     */
    @Override
    public ActivityConf selectByKeyLocked(String acId) {
        return activityConfDao.selectByKeyLocked(acId);
    }

}