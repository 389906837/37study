package com.cloud.cang.mgr.fr.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.fr.domain.FaceRegInfoDomain;
import com.cloud.cang.mgr.fr.vo.FaceRegInfoVo;
import com.cloud.cang.model.fr.FaceRegInfo;
import com.github.pagehelper.Page;

/**
 * 人脸识别会员注册表(FR_FACE_REG_INFO)
 **/
public interface FaceRegInfoDao extends GenericDao<FaceRegInfo, String> {

    /**
     * 分页查询
     *
     * @param faceRegInfoVo
     * @return
     */
    Page<FaceRegInfoDomain> selectByDomainWhere(FaceRegInfoVo faceRegInfoVo);


    /** codegen **/
}