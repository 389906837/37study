package com.cloud.cang.act.ac.service;

import com.cloud.cang.model.ac.DiscountDetail;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface DiscountDetailService extends GenericService<DiscountDetail, String> {

    List<DiscountDetail> selectBySacCode(String sacCode);

}