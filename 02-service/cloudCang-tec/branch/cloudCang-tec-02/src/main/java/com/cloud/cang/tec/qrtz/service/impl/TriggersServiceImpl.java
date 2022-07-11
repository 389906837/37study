package com.cloud.cang.tec.qrtz.service.impl;

import java.util.List;
import java.util.Map;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.qrtz.Triggers;
import com.cloud.cang.tec.qrtz.dao.TriggersDao;
import com.cloud.cang.tec.qrtz.service.TriggersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class TriggersServiceImpl extends GenericServiceImpl<Triggers, String> implements
        TriggersService {

	@Autowired
	TriggersDao triggersDao;

	
	@Override
	public GenericDao<Triggers, String> getDao() {
		return triggersDao;
	}
	public List<com.cloud.cang.tec.quartz.vo.Triggers> selectVoByMapWhere(Map t){
		return this.triggersDao.selectVoByMapWhere(t);
	}
	
	

}