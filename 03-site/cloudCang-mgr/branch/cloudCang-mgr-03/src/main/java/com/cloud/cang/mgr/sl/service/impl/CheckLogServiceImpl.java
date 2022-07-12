package com.cloud.cang.mgr.sl.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sl.dao.CheckLogDao;
import com.cloud.cang.mgr.sl.service.CheckLogService;
import com.cloud.cang.mgr.sl.vo.CheckLogVo;
import com.cloud.cang.model.sl.CheckLog;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckLogServiceImpl extends GenericServiceImpl<CheckLog, String> implements
		CheckLogService {

	@Autowired
	CheckLogDao checkLogDao;

	
	@Override
	public GenericDao<CheckLog, String> getDao() {
		return checkLogDao;
	}


	@Override
	public Page<CheckLog> checkLog(Page<CheckLog> page, CheckLogVo checkLogVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return checkLogDao.queryCheckLog(checkLogVo);
	}
}