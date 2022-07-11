package com.cloud.cang.wap.hy.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.hy.MbrPurview;
import com.cloud.cang.wap.hy.dao.MbrPurviewDao;
import com.cloud.cang.wap.hy.service.MbrPurviewService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service

public class MbrPurviewServiceImpl extends GenericServiceImpl<MbrPurview, String> implements
        MbrPurviewService {

	@Autowired
    MbrPurviewDao mbrPurviewDao;

	
	@Override
	public GenericDao<MbrPurview, String> getDao() {
		return mbrPurviewDao;
	}

	
	@Override
	public List<MbrPurview> selectPurviewByUserId(String userId) {
		return mbrPurviewDao.selectPurviewByUserId(userId);
	}

}