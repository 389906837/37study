package com.cloud.cang.mgr.fr.service.impl;

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

import com.cloud.cang.mgr.fr.dao.FaceOperLogDao;
import com.cloud.cang.model.fr.FaceOperLog;
import com.cloud.cang.mgr.fr.service.FaceOperLogService;

@Service
public class FaceOperLogServiceImpl extends GenericServiceImpl<FaceOperLog, String> implements
		FaceOperLogService {

	@Autowired
	FaceOperLogDao faceOperLogDao;

	
	@Override
	public GenericDao<FaceOperLog, String> getDao() {
		return faceOperLogDao;
	}

	
	

}