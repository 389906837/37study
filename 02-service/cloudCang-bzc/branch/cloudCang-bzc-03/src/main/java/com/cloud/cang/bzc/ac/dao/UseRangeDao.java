package com.cloud.cang.bzc.ac.dao;

import com.cloud.cang.bzc.om.vo.ActivityUseRangeVo;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.ac.UseRange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动应用范围明细表(AC_USE_RANGE)
 **/
public interface UseRangeDao extends GenericDao<UseRange, String> {


    /**
     * codegen
     **/
    ActivityUseRangeVo selectRangeBySacCode(String code);

    /***
     *根基商户Id查询启用的活动配置详情表
     *@param merchantId
     @return UseRange
     */
    UseRange selectBySmerchantId(@Param("merchantId") String merchantId,@Param("iscenesType") Integer iscenesType);
}