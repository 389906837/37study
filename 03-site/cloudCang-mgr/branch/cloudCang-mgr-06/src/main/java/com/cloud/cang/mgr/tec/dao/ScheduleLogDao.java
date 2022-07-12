package com.cloud.cang.mgr.tec.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.tec.vo.ScheduleLogVo;
import com.cloud.cang.model.tec.ScheduleLog;
import com.github.pagehelper.Page;

/** 任务中心日志表(TEC_SCHEDULE_LOG) **/
public interface ScheduleLogDao extends GenericDao<ScheduleLog, String> {

        Page<ScheduleLog> queryScheduleLog(ScheduleLogVo scheduleLogVo);
}