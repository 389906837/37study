package com.cloud.cang.tec.sys.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.ScheduleConf;

import java.util.List;

/** 任务配置中心(MYSQL)(SYS_SCHEDULE_CONF) **/
public interface ScheduleConfDao extends GenericDao<ScheduleConf, String> {


	List<ScheduleConf> selectNotAddTrigByEntityWhere();
	/** codegen **/
}