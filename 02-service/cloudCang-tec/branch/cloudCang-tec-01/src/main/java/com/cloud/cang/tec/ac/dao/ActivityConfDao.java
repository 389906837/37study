package com.cloud.cang.tec.ac.dao;

import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.tec.ac.vo.ActivityConfVo;

/**
 * 活动配置表(AC_ACTIVITY_CONF)
 **/
public interface ActivityConfDao extends GenericDao<ActivityConf, String> {


    int upActivityStatus(Map map);

    ActivityConfVo selectExpireSceneActivity(Map<String, String> map);

    List<ActivityConf> selectExpireActivity(String merchantId);
}