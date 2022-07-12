package com.cloud.cang.mgr.hy.service.impl;

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

import com.cloud.cang.mgr.hy.dao.MbrRolePurDao;
import com.cloud.cang.model.hy.MbrRolePur;
import com.cloud.cang.mgr.hy.service.MbrRolePurService;

@Service
public class MbrRolePurServiceImpl extends GenericServiceImpl<MbrRolePur, String> implements
		MbrRolePurService {

	@Autowired
	MbrRolePurDao mbrRolePurDao;

	
	@Override
	public GenericDao<MbrRolePur, String> getDao() {
		return mbrRolePurDao;
	}


	@Override
	public void deleteByRoleId(String roleId) {
		mbrRolePurDao.deleteByRoleId(roleId);
	}
}