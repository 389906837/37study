package com.cloud.cang.api.fr.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.fr.FaceRegisterInfo;

/** 人脸识别会员注册表(FR_FACE_REGISTER_INFO) **/
public interface FaceRegisterInfoDao extends GenericDao<FaceRegisterInfo, String> {
    /**
     * 查询用户注册信息
     *
     * @param faceRegisterInfoVo
     * @return
     */
    FaceRegisterInfo selectUserFaceRegisterDomain(FaceRegisterInfo faceRegisterInfoVo);


    /** codegen **/
}