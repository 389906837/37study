package com.cloud.cang.mgr.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.ac.domain.RecommendRecordDomain;
import com.cloud.cang.mgr.ac.vo.RecommendRecordVo;
import com.cloud.cang.model.ac.RecommendRecord;
import com.github.pagehelper.Page;

/** 好友推荐记录表
(AC_RECOMMEND_RECORD) **/
public interface RecommendRecordDao extends GenericDao<RecommendRecord, String> {

    Page<RecommendRecordDomain> selectQueryData(RecommendRecordVo recommendRecordVo);
}