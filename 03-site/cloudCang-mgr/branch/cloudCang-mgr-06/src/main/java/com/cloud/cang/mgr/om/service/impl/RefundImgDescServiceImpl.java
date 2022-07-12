package com.cloud.cang.mgr.om.service.impl;

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

import com.cloud.cang.mgr.om.dao.RefundImgDescDao;
import com.cloud.cang.model.om.RefundImgDesc;
import com.cloud.cang.mgr.om.service.RefundImgDescService;

@Service
public class RefundImgDescServiceImpl extends GenericServiceImpl<RefundImgDesc, String> implements
		RefundImgDescService {

	@Autowired
	RefundImgDescDao refundImgDescDao;

	
	@Override
	public GenericDao<RefundImgDesc, String> getDao() {
		return refundImgDescDao;
	}

	
	

}