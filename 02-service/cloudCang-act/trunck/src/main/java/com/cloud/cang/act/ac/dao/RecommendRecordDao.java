package com.cloud.cang.act.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.RecommendRecord;

/** 好友推荐记录表
(AC_RECOMMEND_RECORD) **/
public interface RecommendRecordDao extends GenericDao<RecommendRecord, String> {


    /**
     * 根据被推荐用户统计好友推荐记录
     * @param userId
     * @return
     */

    Integer countRecommendRecordBySpresenteeId(String userId);

    /**
     * 根据被推荐人查找推荐记录
     * @param userId
     * @return
     */
    RecommendRecord selectRecommendRecordBySpresenteeId(String userId);
}