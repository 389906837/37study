package com.cloud.cang.act.cr.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.cr.CameraInfo;

/** 相机基础信息(CR_CAMERA_INFO) **/
public interface CameraInfoDao extends GenericDao<CameraInfo, String> {


    /**
     * 根据编号查询相机
     *
     * @param code
     * @return
     */
    CameraInfo selectByCode(String code);
}