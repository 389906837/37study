package com.cang.os.mgr.wz.service.impl;

import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.mgr.wz.service.NavigationService;
import com.cang.os.bean.wz.Navigation;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.mgr.wz.dao.NavigationDao;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class NavigationServiceImpl extends BaseServiceImpl<Navigation> implements
        NavigationService {

	@Autowired
    NavigationDao navigationDao;

	
	@Override
	public BaseMongoDao<Navigation> getDao() {
		return navigationDao;
	}

	
	

}