package com.cloud.cang.tec.sys.service.impl;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.sys.ScheduleConf;
import com.cloud.cang.tec.sys.dao.ScheduleConfDao;
import com.cloud.cang.tec.sys.service.ScheduleConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleConfServiceImpl extends GenericServiceImpl<ScheduleConf, String> implements
        ScheduleConfService {

	@Autowired
    ScheduleConfDao scheduleConfDao;

	
	@Override
	public GenericDao<ScheduleConf, String> getDao() {
		return scheduleConfDao;
	}
	public List<ScheduleConf> selectNotAddTrigByEntityWhere(){
		return this.scheduleConfDao.selectNotAddTrigByEntityWhere()	;
	}
	
	

}