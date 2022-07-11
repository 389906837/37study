package com.cloud.cang.act.ac.service;

import com.cloud.cang.model.ac.RecommendRecord;
import com.cloud.cang.generic.GenericService;

public interface RecommendRecordService extends GenericService<RecommendRecord, String> {

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