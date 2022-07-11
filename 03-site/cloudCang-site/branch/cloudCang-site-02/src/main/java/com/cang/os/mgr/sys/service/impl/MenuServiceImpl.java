package com.cang.os.mgr.sys.service.impl;

import com.cang.os.common.dao.BaseMongoDao;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import com.cang.os.common.service.BaseServiceImpl;



import com.cang.os.mgr.sys.dao.MenuDao;
import com.cang.os.bean.sys.Menu;
import com.cang.os.mgr.sys.service.MenuService;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements
		MenuService {

	@Autowired
	MenuDao menuDao;

	
	@Override
	public BaseMongoDao<Menu> getDao() {
		return menuDao;
	}

	
	

}