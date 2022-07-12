package com.cloud.cang.wap.hy.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.hy.MbrRole;
import com.cloud.cang.wap.hy.dao.MbrRoleDao;
import com.cloud.cang.wap.hy.service.MbrRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service

public class MbrRoleServiceImpl extends GenericServiceImpl<MbrRole, String> implements
        MbrRoleService {

	@Autowired
    MbrRoleDao mbrRoleDao;

	
	@Override
	public GenericDao<MbrRole, String> getDao() {
		return mbrRoleDao;
	}

	
	

}