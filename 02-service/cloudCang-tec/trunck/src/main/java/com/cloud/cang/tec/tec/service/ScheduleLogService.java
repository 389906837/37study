package com.cloud.cang.tec.tec.service;

import com.cloud.cang.model.tec.ScheduleLog;
import com.cloud.cang.generic.GenericService;

public interface ScheduleLogService extends GenericService<ScheduleLog, String> {


	/**
     * 今日有记录job数量(去重)
     * @return
     */
    int getScheduleCountToday();

    
}