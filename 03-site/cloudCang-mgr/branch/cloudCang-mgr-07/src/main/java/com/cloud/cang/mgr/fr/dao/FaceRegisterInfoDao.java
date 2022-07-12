package com.cloud.cang.mgr.fr.dao;

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


    FaceRegisterInfo selectByYSSelectTive(FaceRegisterInfo faceRegisterInfo);
}