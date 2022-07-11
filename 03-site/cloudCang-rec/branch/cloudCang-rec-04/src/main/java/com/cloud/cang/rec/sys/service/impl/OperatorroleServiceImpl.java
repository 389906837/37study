package com.cloud.cang.rec.sys.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.rec.sys.dao.OperatorroleDao;
import com.cloud.cang.rec.sys.service.OperatorroleService;
import com.cloud.cang.model.sys.Operatorrole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorroleServiceImpl extends GenericServiceImpl<Operatorrole, String> implements
		OperatorroleService {

	@Autowired
	OperatorroleDao operatorroleDao;

	
	@Override
	public GenericDao<Operatorrole, String> getDao() {
		return operatorroleDao;
	}

	
	

}