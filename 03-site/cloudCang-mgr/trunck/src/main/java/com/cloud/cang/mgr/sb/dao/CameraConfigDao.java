package com.cloud.cang.mgr.sb.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.CameraConfigDomain;
import com.cloud.cang.mgr.sb.vo.CameraConfigVo;
import com.cloud.cang.model.sb.CameraConfig;
import com.github.pagehelper.Page;

/**
 * 设备摄像头配置信息表(SB_CAMERA_CONFIG)
 **/
public interface CameraConfigDao extends GenericDao<CameraConfig, String> {

    /**
     *
     * @param cameraConfigVo
     * @return
     */
    Page<CameraConfigDomain> selectPageByDomainWhere(CameraConfigVo cameraConfigVo);



    int updateByIdSelectiveVo(CameraConfig cameraConfig);


    /** codegen **/
}