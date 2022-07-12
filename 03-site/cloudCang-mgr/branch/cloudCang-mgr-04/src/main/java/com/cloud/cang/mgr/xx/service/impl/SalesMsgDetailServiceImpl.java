package com.cloud.cang.mgr.xx.service.impl;

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

import com.cloud.cang.mgr.xx.dao.SalesMsgDetailDao;
import com.cloud.cang.model.xx.SalesMsgDetail;
import com.cloud.cang.mgr.xx.service.SalesMsgDetailService;

@Service
public class SalesMsgDetailServiceImpl extends GenericServiceImpl<SalesMsgDetail, String> implements
		SalesMsgDetailService {

	@Autowired
	SalesMsgDetailDao salesMsgDetailDao;

	
	@Override
	public GenericDao<SalesMsgDetail, String> getDao() {
		return salesMsgDetailDao;
	}

	
	

}