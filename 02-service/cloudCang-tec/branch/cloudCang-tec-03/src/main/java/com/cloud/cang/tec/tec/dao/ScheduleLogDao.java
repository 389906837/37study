package com.cloud.cang.tec.tec.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.tec.ScheduleLog;

/** 任务中心日志表(TEC_SCHEDULE_LOG) **/
public interface ScheduleLogDao extends GenericDao<ScheduleLog, String> {


	/**
     * 今日有记录job数量(去重)
     * @return
     */
    int getScheduleCountToday();
}