package com.cloud.cang.open.api.op.service.impl;

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

import com.cloud.cang.open.api.op.dao.UserInterfaceAuthDao;
import com.cloud.cang.model.op.UserInterfaceAuth;
import com.cloud.cang.open.api.op.service.UserInterfaceAuthService;

@Service
public class UserInterfaceAuthServiceImpl extends GenericServiceImpl<UserInterfaceAuth, String> implements
		UserInterfaceAuthService {

	@Autowired
	UserInterfaceAuthDao userInterfaceAuthDao;

	
	@Override
	public GenericDao<UserInterfaceAuth, String> getDao() {
		return userInterfaceAuthDao;
	}

	
	

}