package com.cloud.cang.rec.cr.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.cr.CameraInfo;

/** 相机基础信息(CR_CAMERA_INFO) **/
public interface CameraInfoDao extends GenericDao<CameraInfo, String> {


    CameraInfo selectBySelective(CameraInfo  cameraInfo);

}