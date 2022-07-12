package com.cloud.cang.mgr.sl.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sl.dao.LoginLogDao;
import com.cloud.cang.mgr.sl.domain.LoginDomain;
import com.cloud.cang.mgr.sl.service.LoginLogService;
import com.cloud.cang.mgr.sl.vo.LoginLogVo;
import com.cloud.cang.model.sl.LoginLog;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl extends GenericServiceImpl<LoginLog, String> implements
		LoginLogService {

	@Autowired
	LoginLogDao loginLogDao;

	
	@Override
	public GenericDao<LoginLog, String> getDao() {
		return loginLogDao;
	}


	@Override
	public Page<LoginDomain> queryLoginLog(Page<LoginDomain> page, LoginLogVo loginLogVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return loginLogDao.queryLoginLog(loginLogVo);
	}
}