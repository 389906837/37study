package com.cloud.cang.act.ac.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.ActivityConf;

import java.util.List;
import java.util.Map;

/**
 * 活动配置表(AC_ACTIVITY_CONF)
 **/
public interface ActivityConfDao extends GenericDao<ActivityConf, String> {


    /**
     * 根据活动编号查找活动
     *
     * @param code
     * @return
     */
    ActivityConf selectActivityConfByCode(String code);

    /**
     * 根据活动编号和商户Id查找活动
     *
     * @param map
     * @return
     */
    ActivityConf selectActivityConfByCodeAndMerId(Map<String, String> map);

    /**
     * 查看设备活动
     *
     * @param deviceId
     * @return
     */
    List<String> viewActivityInformation(String deviceId);

    /**
     * 查询活动配置并锁表
     *
     * @param
     * @return
     */
    ActivityConf selectByKeyLocked(String acId);
}