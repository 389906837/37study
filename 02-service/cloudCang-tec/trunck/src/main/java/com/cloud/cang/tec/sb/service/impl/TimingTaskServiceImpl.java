package com.cloud.cang.tec.sb.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.sb.dao.TimingTaskDao;
import com.cloud.cang.model.sb.TimingTask;
import com.cloud.cang.tec.sb.service.TimingTaskService;

@Service
public class TimingTaskServiceImpl extends GenericServiceImpl<TimingTask, String> implements
		TimingTaskService {

	@Autowired
	TimingTaskDao timingTaskDao;

	
	@Override
	public GenericDao<TimingTask, String> getDao() {
		return timingTaskDao;
	}

	
	

}