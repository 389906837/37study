package com.cloud.cang.bzc.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.DiscountRecord;

import java.util.Map;

/**
 * 活动优惠记录表(AC_DISCOUNT_RECORD)
 **/
public interface DiscountRecordDao extends GenericDao<DiscountRecord, String> {

    Map<String,Integer> selectUserDayCountByUserId();

    Map<String,Integer> selectUserAllCountByUserId();

    Map<String,Integer> selectDeviceDayCountByDeviceCode();

    Map<String,Integer> selectDeviceAllCountByDeviceCode();


    /** codegen **/
}