package com.cloud.cang.mgr.fr.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.fr.domain.FaceRegInfoDomain;
import com.cloud.cang.mgr.fr.vo.FaceRegInfoVo;
import com.cloud.cang.model.fr.FaceRegInfo;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

public interface FaceRegInfoService extends GenericService<FaceRegInfo, String> {


    /**
     * 分页查询
     *
     * @param page
     * @param faceRegInfoVo
     * @return
     */
    Page<FaceRegInfoDomain> selectPageByDomainWhere(Page<FaceRegInfoDomain> page, FaceRegInfoVo faceRegInfoVo);

    /**
     * 人脸信息逻辑删除
     *
     * @param checkboxId 人脸信息ID
     * @param operater   操作员
     * @return
     */
    ResponseVo<String> deleteFaceInfo(String[] checkboxId, String operater);

    /**
     * 修改人脸信息
     *
     * @param faceRegInfo 人脸信息
     * @param faceImg     图片
     * @return
     */
    ResponseVo<FaceRegInfo> updateFaceInfo(FaceRegInfo faceRegInfo, MultipartFile faceImg);

    /**
     * 审核人脸信息
     *
     * @param faceRegInfo 人脸信息
     * @return
     */
    ResponseVo<FaceRegInfo> updateFaceInfoByAudit(FaceRegInfo faceRegInfo);

    /**
     * 插入用户信息
     *
     * @param faceImg     用户照片
     * @param faceRegInfo 用户信息
     * @return
     */
    ResponseVo<FaceRegInfo> insertFaceInfo(MultipartFile faceImg, FaceRegInfo faceRegInfo);


    /**
     * 批量导入年会人脸Excel识别信息
     *
     * @param file 照片+姓名文件
     * @return
     */
    ResponseVo<String> batchImportFaceInfo(MultipartFile file);

}