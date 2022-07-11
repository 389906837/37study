package com.cloud.cang.wap.om.service.impl;

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

import com.cloud.cang.wap.om.dao.RefundOperlogDao;
import com.cloud.cang.model.om.RefundOperlog;
import com.cloud.cang.wap.om.service.RefundOperlogService;

@Service
public class RefundOperlogServiceImpl extends GenericServiceImpl<RefundOperlog, String> implements
		RefundOperlogService {

	@Autowired
	RefundOperlogDao refundOperlogDao;

	
	@Override
	public GenericDao<RefundOperlog, String> getDao() {
		return refundOperlogDao;
	}

	
	

}