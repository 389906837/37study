package com.cloud.cang.bzc.om.service.impl;

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

import com.cloud.cang.bzc.om.dao.OrderAuditDao;
import com.cloud.cang.model.om.OrderAudit;
import com.cloud.cang.bzc.om.service.OrderAuditService;

@Service
public class OrderAuditServiceImpl extends GenericServiceImpl<OrderAudit, String> implements
		OrderAuditService {

	@Autowired
	OrderAuditDao orderAuditDao;

	
	@Override
	public GenericDao<OrderAudit, String> getDao() {
		return orderAuditDao;
	}

	
	

}