package com.cloud.cang.tec.op.service.impl;

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

import com.cloud.cang.tec.op.dao.InterfaceAcceptDao;
import com.cloud.cang.model.op.InterfaceAccept;
import com.cloud.cang.tec.op.service.InterfaceAcceptService;

@Service
public class InterfaceAcceptServiceImpl extends GenericServiceImpl<InterfaceAccept, String> implements
		InterfaceAcceptService {

	@Autowired
	InterfaceAcceptDao interfaceAcceptDao;

	
	@Override
	public GenericDao<InterfaceAccept, String> getDao() {
		return interfaceAcceptDao;
	}

	
	

}