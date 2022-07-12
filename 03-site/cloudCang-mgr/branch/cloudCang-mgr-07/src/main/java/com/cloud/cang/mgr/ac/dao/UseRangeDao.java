package com.cloud.cang.mgr.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.ac.domain.UseRangeDomain;
import com.cloud.cang.model.ac.UseRange;

import java.util.List;

/** 活动应用范围明细表(AC_USE_RANGE) **/
public interface UseRangeDao extends GenericDao<UseRange, String> {
    /**
     * @Author: zhouhong
     * @Description: 获取设备的应用范围明细
     * @param: actId 活动ID
     * @Date: 2018/2/10 10:23
     * @return com.cloud.cang.model.ac.UseRange
     */
    UseRange selectByActId(String actId);

    List<UseRangeDomain> selectUseRange(String actId);
}