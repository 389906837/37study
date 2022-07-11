package com.cloud.cang.rec.cr.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.cr.RecognitionServer;
import com.cloud.cang.rec.cr.domain.RecognitionServerDomain;
import com.github.pagehelper.Page;

import java.math.BigDecimal;

public interface RecognitionServerService extends GenericService<RecognitionServer, String> {
    /**
     * 查询列表
     *
     * @param page
     * @param recognitionServerDomain
     * @return
     */
    Page<RecognitionServer> selectPageByDomainWhere(Page<RecognitionServer> page, RecognitionServerDomain recognitionServerDomain);

    /**
     * 新增
     *
     * @param recognitionServer
     * @return
     */
    void saveRecognitionServer(RecognitionServer recognitionServer);

    /**
     * 修改
     *
     * @param recognitionServer
     * @return
     */
    void upRecognitionServer(RecognitionServer recognitionServer);

    /**
     * 审核
     *
     * @param recognitionServer
     * @return
     */
    void recognitionServerAudit(RecognitionServer recognitionServer);


    /**
     * 删除
     *
     * @param id
     */
    void deleteRecognitionServer(String[] id);

    /**
     * 更新模型
     *
     * @param recognitionServer
     * @param
     * @return
     */
    ResponseVo saveUpModel(RecognitionServer recognitionServer, /*Integer iisRestartImmediately,*/ BigDecimal fvisThresh) throws Exception;


    /**
     * 重启识别服务器
     *
     * @param irangeServer
     * @param recognitionServerIds
     * @param recognitionServerCodes
     * @return
     */
    ResponseVo reboot(Integer irangeServer, String recognitionServerIds, String recognitionServerCodes) throws Exception;

}