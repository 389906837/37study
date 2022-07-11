package com.cloud.cang.act.cr.service.impl;

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

import com.cloud.cang.act.cr.dao.ReportManDao;
import com.cloud.cang.model.cr.ReportMan;
import com.cloud.cang.act.cr.service.ReportManService;

@Service
public class ReportManServiceImpl extends GenericServiceImpl<ReportMan, String> implements
		ReportManService {

	@Autowired
	ReportManDao reportManDao;

	
	@Override
	public GenericDao<ReportMan, String> getDao() {
		return reportManDao;
	}

	
	

}