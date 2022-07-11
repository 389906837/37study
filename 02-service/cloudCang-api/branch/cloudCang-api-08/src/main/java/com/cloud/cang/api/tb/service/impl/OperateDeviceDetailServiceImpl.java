package com.cloud.cang.api.tb.service.impl;

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

import com.cloud.cang.api.tb.dao.OperateDeviceDetailDao;
import com.cloud.cang.model.tb.OperateDeviceDetail;
import com.cloud.cang.api.tb.service.OperateDeviceDetailService;

@Service
public class OperateDeviceDetailServiceImpl extends GenericServiceImpl<OperateDeviceDetail, String> implements
		OperateDeviceDetailService {

	@Autowired
	OperateDeviceDetailDao operateDeviceDetailDao;

	
	@Override
	public GenericDao<OperateDeviceDetail, String> getDao() {
		return operateDeviceDetailDao;
	}

	
	

}