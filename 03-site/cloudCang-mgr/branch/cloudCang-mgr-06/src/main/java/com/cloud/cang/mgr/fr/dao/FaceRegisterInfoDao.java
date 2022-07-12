package com.cloud.cang.mgr.fr.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.fr.domain.FaceRegisterInfoDomain;
import com.cloud.cang.mgr.fr.vo.FaceRegisterInfoVo;
import com.cloud.cang.model.fr.FaceRegisterInfo;
import com.github.pagehelper.Page;

/**
 * 人脸识别会员注册表(FR_FACE_REGISTER_INFO)
 **/
public interface FaceRegisterInfoDao extends GenericDao<FaceRegisterInfo, String> {

    /**
     * 分页查询
     *
     * @param faceRegisterInfoVo
     * @return
     */
    Page<FaceRegisterInfoDomain> selectByDomainWhere(FaceRegisterInfoVo faceRegisterInfoVo);


    /** codegen **/
}