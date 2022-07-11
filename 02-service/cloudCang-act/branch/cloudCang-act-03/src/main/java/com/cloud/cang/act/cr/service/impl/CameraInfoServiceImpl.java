package com.cloud.cang.act.cr.service.impl;

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

import com.cloud.cang.act.cr.dao.CameraInfoDao;
import com.cloud.cang.model.cr.CameraInfo;
import com.cloud.cang.act.cr.service.CameraInfoService;

@Service
public class CameraInfoServiceImpl extends GenericServiceImpl<CameraInfo, String> implements
        CameraInfoService {

    @Autowired
    CameraInfoDao cameraInfoDao;


    @Override
    public GenericDao<CameraInfo, String> getDao() {
        return cameraInfoDao;
    }


    /**
     * 根据编号查询相机
     *
     * @param code
     * @return
     */
    @Override
    public CameraInfo selectByCode(String code) {
        return cameraInfoDao.selectByCode(code);
    }

}