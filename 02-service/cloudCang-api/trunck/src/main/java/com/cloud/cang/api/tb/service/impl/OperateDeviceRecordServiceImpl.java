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

import com.cloud.cang.api.tb.dao.OperateDeviceRecordDao;
import com.cloud.cang.model.tb.OperateDeviceRecord;
import com.cloud.cang.api.tb.service.OperateDeviceRecordService;

@Service
public class OperateDeviceRecordServiceImpl extends GenericServiceImpl<OperateDeviceRecord, String> implements
		OperateDeviceRecordService {

	@Autowired
	OperateDeviceRecordDao operateDeviceRecordDao;

	
	@Override
	public GenericDao<OperateDeviceRecord, String> getDao() {
		return operateDeviceRecordDao;
	}

	
	

}