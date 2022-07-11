package com.cloud.cang.wap.hy.service.impl;

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

import com.cloud.cang.wap.hy.dao.WechatFreeDataDao;
import com.cloud.cang.model.hy.WechatFreeData;
import com.cloud.cang.wap.hy.service.WechatFreeDataService;

@Service
public class WechatFreeDataServiceImpl extends GenericServiceImpl<WechatFreeData, String> implements
		WechatFreeDataService {

	@Autowired
	WechatFreeDataDao wechatFreeDataDao;

	
	@Override
	public GenericDao<WechatFreeData, String> getDao() {
		return wechatFreeDataDao;
	}

	
	

}