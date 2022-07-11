package com.cloud.cang.wap.sb.service.impl;

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

import com.cloud.cang.wap.sb.dao.AiInfoDao;
import com.cloud.cang.model.sb.AiInfo;
import com.cloud.cang.wap.sb.service.AiInfoService;

@Service
public class AiInfoServiceImpl extends GenericServiceImpl<AiInfo, String> implements
		AiInfoService {

	@Autowired
	AiInfoDao aiInfoDao;

	
	@Override
	public GenericDao<AiInfo, String> getDao() {
		return aiInfoDao;
	}

	
	

}