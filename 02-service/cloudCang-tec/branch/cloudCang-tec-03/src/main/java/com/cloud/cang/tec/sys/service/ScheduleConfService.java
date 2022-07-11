package com.cloud.cang.tec.sys.service;



import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sys.ScheduleConf;

import java.util.List;

public interface ScheduleConfService extends GenericService<ScheduleConf, String> {
	List<ScheduleConf> selectNotAddTrigByEntityWhere();
    
}