package com.cloud.cang.mgr.ac.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.mgr.ac.domain.RecommendRecordDomain;
import com.cloud.cang.mgr.ac.vo.RecommendRecordVo;
import com.cloud.cang.model.ac.RecommendRecord;
import com.github.pagehelper.Page;

public interface RecommendRecordService extends GenericService<RecommendRecord, String> {

    /**
     * 好友推荐查询
     * @param page
     * @param recommendRecordVo
     * @return
     */
    Page<RecommendRecordDomain> selectQueryData(Page<RecommendRecordDomain> page, RecommendRecordVo recommendRecordVo);
}