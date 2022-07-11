package com.cloud.cang.bzc.ac.service;

import com.cloud.cang.model.ac.DiscountDetail;
import com.cloud.cang.generic.GenericService;

public interface DiscountDetailService extends GenericService<DiscountDetail, String> {
    /***
     *根据活动编号查询活动
     *@param sconfCode
     @return
     */
    DiscountDetail selectBySacCode(String sconfCode);
}