package com.cloud.cang.bzc.ac.dao;

import com.cloud.cang.bzc.om.vo.ActivityVo;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.ActivityConf;

import java.util.List;

/**
 * 活动配置表(AC_ACTIVITY_CONF)
 **/
public interface ActivityConfDao extends GenericDao<ActivityConf, String> {
    List<ActivityVo> selectAllActivity(String smerchantCode);

}