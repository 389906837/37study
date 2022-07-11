package com.cloud.cang.bzc.hy.service.impl;

import com.cloud.cang.bzc.hy.dao.TrashAuthoriseDao;
import com.cloud.cang.bzc.hy.service.TrashAuthoriseService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.hy.TrashAuthorise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TrashAuthoriseServiceImpl extends GenericServiceImpl<TrashAuthorise, String> implements
		TrashAuthoriseService {

	@Autowired
	TrashAuthoriseDao trashAuthoriseDao;

	
	@Override
	public GenericDao<TrashAuthorise, String> getDao() {
		return trashAuthoriseDao;
	}

	@Override
	public TrashAuthorise selectByUserIdAndTrash(Map<String, Object> map) {
		return trashAuthoriseDao.selectByUserIdAndTrash(map);
	}
	

}