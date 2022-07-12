package com.cloud.cang.mgr.sl.service.impl;

import java.util.List;

import com.cloud.cang.mgr.sl.vo.VistLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sl.dao.VistLogDao;
import com.cloud.cang.model.sl.VistLog;
import com.cloud.cang.mgr.sl.service.VistLogService;

@Service
public class VistLogServiceImpl extends GenericServiceImpl<VistLog, String> implements
		VistLogService {

	@Autowired
	VistLogDao vistLogDao;

	
	@Override
	public GenericDao<VistLog, String> getDao() {
		return vistLogDao;
	}


	@Override
	public Page<VistLog> queryVistLog(Page<VistLog> page, VistLogVo vistLogVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return vistLogDao.queryVistLog(vistLogVo);
	}
}