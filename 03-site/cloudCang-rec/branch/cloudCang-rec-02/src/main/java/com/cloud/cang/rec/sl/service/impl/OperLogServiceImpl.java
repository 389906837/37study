package com.cloud.cang.rec.sl.service.impl;

import java.util.List;

import com.cloud.cang.rec.sl.vo.VistLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.rec.sl.dao.OperLogDao;
import com.cloud.cang.model.sl.OperLog;
import com.cloud.cang.rec.sl.service.OperLogService;

@Service
public class OperLogServiceImpl extends GenericServiceImpl<OperLog, String> implements
		OperLogService {

	@Autowired
	OperLogDao operLogDao;

	
	@Override
	public GenericDao<OperLog, String> getDao() {
		return operLogDao;
	}

	@Override
	public Page<OperLog> queryOperLog(Page<OperLog> page, VistLogVo vistLogVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return operLogDao.queryOperLog(vistLogVo);
	}
	

}