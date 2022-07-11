package com.cloud.cang.pay.ac.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.ActivityConf;

/** 活动配置表(AC_ACTIVITY_CONF) **/
public interface ActivityConfDao extends GenericDao<ActivityConf, String> {
    /**
     * 查询活动信息
     * @param map 查询参数
     * @return
     */
    ActivityConf selectByMap(Map<String, Object> map);
}