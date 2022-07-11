package com.cloud.cang.act.cr.service;

import com.cloud.cang.model.cr.CameraInfo;
import com.cloud.cang.generic.GenericService;

public interface CameraInfoService extends GenericService<CameraInfo, String> {

    /**
     * 根据编号查询相机
     *
     * @param code
     * @return
     */
    CameraInfo selectByCode(String code);
}