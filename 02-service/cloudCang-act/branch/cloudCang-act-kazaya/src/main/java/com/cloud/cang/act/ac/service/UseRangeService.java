package com.cloud.cang.act.ac.service;

import com.cloud.cang.model.ac.UseRange;
import com.cloud.cang.generic.GenericService;

public interface UseRangeService extends GenericService<UseRange, String> {

    /**
     * 根据活动ID查询活动范围
     *
     * @Param acId 活动ID
     */
    UseRange selectByAcId(String acId);
}