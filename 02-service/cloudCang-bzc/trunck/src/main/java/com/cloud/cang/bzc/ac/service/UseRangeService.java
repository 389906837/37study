package com.cloud.cang.bzc.ac.service;

import com.cloud.cang.bzc.om.vo.ActivityUseRangeVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.ac.UseRange;

import java.util.List;

public interface UseRangeService extends GenericService<UseRange, String> {


    ActivityUseRangeVo selectRangeBySacCode(String scode);

    /***
     *根基商户Id查询启用的活动配置详情表
     *@param merchantId
     @return UseRange
     */
    UseRange selectBySmerchantId(String merchantId, Integer iscenesType);
}