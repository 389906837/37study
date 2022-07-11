package com.cloud.cang.tec.sq.service.impl;

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

import com.cloud.cang.tec.sq.dao.CreatApplyDao;
import com.cloud.cang.model.sq.CreatApply;
import com.cloud.cang.tec.sq.service.CreatApplyService;

@Service
public class CreatApplyServiceImpl extends GenericServiceImpl<CreatApply, String> implements
		CreatApplyService {

	@Autowired
	CreatApplyDao creatApplyDao;

	
	@Override
	public GenericDao<CreatApply, String> getDao() {
		return creatApplyDao;
	}

	
	

}