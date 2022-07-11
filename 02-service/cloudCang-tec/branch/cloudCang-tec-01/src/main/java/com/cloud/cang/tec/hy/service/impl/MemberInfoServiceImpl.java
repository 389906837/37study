package com.cloud.cang.tec.hy.service.impl;

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

import com.cloud.cang.tec.hy.dao.MemberInfoDao;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.tec.hy.service.MemberInfoService;

@Service
public class MemberInfoServiceImpl extends GenericServiceImpl<MemberInfo, String> implements
		MemberInfoService {

	@Autowired
	MemberInfoDao memberInfoDao;

	
	@Override
	public GenericDao<MemberInfo, String> getDao() {
		return memberInfoDao;
	}

	
	

}