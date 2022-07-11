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

import com.cloud.cang.open.api.op.dao.UserInfoDao;
import com.cloud.cang.model.op.UserInfo;
import com.cloud.cang.open.api.op.service.UserInfoService;

@Service
public class UserInfoServiceImpl extends GenericServiceImpl<UserInfo, String> implements
		UserInfoService {

	@Autowired
	UserInfoDao userInfoDao;

	
	@Override
	public GenericDao<UserInfo, String> getDao() {
		return userInfoDao;
	}

	
	

}