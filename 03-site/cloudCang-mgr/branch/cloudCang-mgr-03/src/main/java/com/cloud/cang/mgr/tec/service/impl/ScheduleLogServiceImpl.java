package com.cloud.cang.mgr.tec.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.tec.dao.ScheduleLogDao;
import com.cloud.cang.mgr.tec.service.ScheduleLogService;
import com.cloud.cang.mgr.tec.vo.ScheduleLogVo;
import com.cloud.cang.model.tec.ScheduleLog;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleLogServiceImpl extends GenericServiceImpl<ScheduleLog, String> implements
		ScheduleLogService {

	@Autowired
	ScheduleLogDao scheduleLogDao;

	
	@Override
	public GenericDao<ScheduleLog, String> getDao() {
		return scheduleLogDao;
	}


	@Override
	public Page<ScheduleLog> scheduleLog(Page<ScheduleLog> page, ScheduleLogVo scheduleLogVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return scheduleLogDao.queryScheduleLog(scheduleLogVo);
	}
}