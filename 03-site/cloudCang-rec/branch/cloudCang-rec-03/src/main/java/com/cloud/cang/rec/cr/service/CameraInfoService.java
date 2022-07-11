package com.cloud.cang.rec.cr.service;

import com.cloud.cang.model.cr.CameraInfo;
import com.cloud.cang.generic.GenericService;

public interface CameraInfoService extends GenericService<CameraInfo, String> {


    CameraInfo selectBySelective(CameraInfo  cameraInfo);
}