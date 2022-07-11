package com.cloud.cang.act.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.DiscountDetail;

import java.util.List;

/** 活动优惠信息明细表(AC_DISCOUNT_DETAIL) **/
public interface DiscountDetailDao extends GenericDao<DiscountDetail, String> {


    List<DiscountDetail> selectBySacCode(String sacCode);
}