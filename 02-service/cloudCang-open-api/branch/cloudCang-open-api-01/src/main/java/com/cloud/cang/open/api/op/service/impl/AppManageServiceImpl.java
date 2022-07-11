package com.cloud.cang.open.api.op.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.open.api.op.dao.AppManageDao;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.open.api.op.service.AppManageService;

@Service
public class AppManageServiceImpl extends GenericServiceImpl<AppManage, String> implements
		AppManageService {

	@Autowired
	AppManageDao appManageDao;

	
	@Override
	public GenericDao<AppManage, String> getDao() {
		return appManageDao;
	}

	/**
	 * 获取应用信息
	 * @param appId 应用ID
	 * @return
	 */
	@Override
	public AppManage selectAppManageByAppId(String appId) {
		return appManageDao.selectAppManageByAppId(appId);
	}

	/**
	 * 验证商户APPID 是否有效
	 * @param appId 商户appId
	 * @return 0 代表成功 -1 失败
	 * @throws ServiceException
	 */
	@Override
	public AppManage verifyAppId(String appId) throws ServiceException {
		AppManage app = this.selectAppManageByAppId(appId);
		if (app == null) {
			new ServiceException("APPID-ERROR");
		}
		return app;
	}
}