package com.cloud.cang.mgr.tec.service;

import com.cloud.cang.mgr.tec.vo.ScheduleLogVo;
import com.cloud.cang.model.tec.ScheduleLog;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface ScheduleLogService extends GenericService<ScheduleLog, String> {

    /**
     * 定时任务执行日志接口
     * @param page
     * @param scheduleLogVo
     * @return
     */
    Page<ScheduleLog> scheduleLog(Page<ScheduleLog> page, ScheduleLogVo scheduleLogVo);
}