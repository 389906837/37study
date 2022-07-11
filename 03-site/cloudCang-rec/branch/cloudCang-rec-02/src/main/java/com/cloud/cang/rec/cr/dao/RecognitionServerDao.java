package com.cloud.cang.rec.cr.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.cr.RecognitionServer;
import com.cloud.cang.rec.cr.domain.RecognitionServerDomain;
import com.github.pagehelper.Page;

/**
 * 识别服务器管理(CR_RECOGNITION_SERVER)
 **/
public interface RecognitionServerDao extends GenericDao<RecognitionServer, String> {


    /**
     * 查询列表
     *
     * @param recognitionServerDomain
     * @return
     */
    Page<RecognitionServer> selectPageByDomainWhere(RecognitionServerDomain recognitionServerDomain);
}