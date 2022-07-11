package com.cloud.cang.bzc.ac.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.ac.DiscountRecord;

import java.util.Map;

public interface DiscountRecordService extends GenericService<DiscountRecord, String> {



    /*  ----------查询用户参加促销活动的总次数----------*/
    Map<String,Integer> queryUserAllCount(String userId);

    /*  ----------查询用户当天参加促销活动的次数----------*/
    Map<String,Integer> queryUserDayCount(String userId);

    /*  ----------查询设备参加促销活动的总次数----------*/
    Map<String,Integer> queryDeviceAllCount(String sdeviceCode);

    /*  ----------查询设备当天参加促销活动的次数----------*/
    Map<String,Integer> queryDeviceDayCount(String sdeviceCode);

}