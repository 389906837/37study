package com.cloud.cang.bzc.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.IntegralAccount;

/** 会员积分账户(HY_INTEGRAL_ACCOUNT) **/
public interface IntegralAccountDao extends GenericDao<IntegralAccount, String> {


	/**
     * 根据会员id查询用户积分主表
     *
     **/
    IntegralAccount selectBySemerId(String  semerId);


    int  updateBySmemberId(IntegralAccount integralAccount);
}