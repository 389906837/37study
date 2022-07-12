package com.cloud.cang.mgr.fr.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.fr.domain.FaceRegisterInfoDomain;
import com.cloud.cang.mgr.fr.vo.FaceRegisterInfoVo;
import com.cloud.cang.model.fr.FaceRegisterInfo;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface FaceRegisterInfoService extends GenericService<FaceRegisterInfo, String> {

    /**
     * 分页查询
     *
     * @param page               分页对象
     * @param faceRegisterInfoVo 查询条件
     * @return
     */
    Page<FaceRegisterInfoDomain> selectPageByDomainWhere(Page<FaceRegisterInfoDomain> page, FaceRegisterInfoVo faceRegisterInfoVo);


    /**
     * 删除人脸注册信息
     *
     * @param checkboxId 人脸信息ID
     * @return
     */
    ResponseVo<String> deleteFaceInfo(String[] checkboxId);

}