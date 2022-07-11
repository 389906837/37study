package com.cang.os.mgr.wz.service.impl;

import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.bean.wz.Region;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.mgr.wz.dao.RegionDao;
import com.cang.os.mgr.wz.service.RegionService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RegionServiceImpl extends BaseServiceImpl<Region> implements
        RegionService {

	@Autowired
    RegionDao regionDao;

	
	@Override
	public BaseMongoDao<Region> getDao() {
		return regionDao;
	}

	
	

}