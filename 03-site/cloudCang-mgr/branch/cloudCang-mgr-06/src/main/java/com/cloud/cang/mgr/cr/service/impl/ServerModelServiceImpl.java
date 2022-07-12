package com.cloud.cang.mgr.cr.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.cr.dao.ServerModelDao;
import com.cloud.cang.mgr.cr.domain.ServerModelDomain;
import com.cloud.cang.mgr.cr.service.ServerModelService;
import com.cloud.cang.model.cr.ServerModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerModelServiceImpl extends GenericServiceImpl<ServerModel, String> implements
		ServerModelService {

	@Autowired
	ServerModelDao serverModelDao;

	
	@Override
	public GenericDao<ServerModel, String> getDao() {
		return serverModelDao;
	}


	/**
	 * 查询列表
	 *
	 * @param page
	 * @param serverModelDomain
	 * @return
	 */
	@Override
	public Page<ServerModel> selectPageByDomainWhere(Page<ServerModel> page, ServerModelDomain serverModelDomain) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return serverModelDao.selectPageByDomainWhere(serverModelDomain);
	}



}