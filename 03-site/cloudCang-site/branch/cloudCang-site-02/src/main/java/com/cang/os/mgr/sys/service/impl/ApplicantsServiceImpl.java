package com.cang.os.mgr.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.service.BaseServiceImpl;



import com.cang.os.mgr.sys.dao.ApplicantsDao;
import com.cang.os.bean.sys.Applicants;
import com.cang.os.mgr.sys.service.ApplicantsService;

@Service
public class ApplicantsServiceImpl extends BaseServiceImpl<Applicants> implements
		ApplicantsService {

	@Autowired
	ApplicantsDao applicantsDao;

	
	@Override
	public BaseMongoDao<Applicants> getDao() {
		return applicantsDao;
	}

	
	

}