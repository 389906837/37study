package com.cang.os.mgr.wz.service.impl;

import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.mgr.wz.dao.ArticlecontentDao;
import com.cang.os.bean.wz.Articlecontent;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import com.cang.os.mgr.wz.service.ArticlecontentService;

@Service
public class ArticlecontentServiceImpl extends BaseServiceImpl<Articlecontent> implements
		ArticlecontentService {

	@Autowired
    ArticlecontentDao articlecontentDao;

	
	@Override
	public BaseMongoDao<Articlecontent> getDao() {
		return articlecontentDao;
	}

	
	

}