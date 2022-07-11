package com.cloud.cang.api.sb.service.impl;

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

import com.cloud.cang.api.sb.dao.GroupManageDao;
import com.cloud.cang.model.sb.GroupManage;
import com.cloud.cang.api.sb.service.GroupManageService;

@Service
public class GroupManageServiceImpl extends GenericServiceImpl<GroupManage, String> implements
		GroupManageService {

	@Autowired
	GroupManageDao groupManageDao;

	
	@Override
	public GenericDao<GroupManage, String> getDao() {
		return groupManageDao;
	}

	
	

}