package com.cloud.cang.open.api.op.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.open.api.op.dao.InterfaceInfoDao;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.open.api.op.service.InterfaceInfoService;

@Service
public class InterfaceInfoServiceImpl extends GenericServiceImpl<InterfaceInfo, String> implements
		InterfaceInfoService {

	@Autowired
	InterfaceInfoDao interfaceInfoDao;

	
	@Override
	public GenericDao<InterfaceInfo, String> getDao() {
		return interfaceInfoDao;
	}

	/**
	 * 验证接口权限
	 * @param methodName 接口名称
	 * @param userId 用户ID
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public InterfaceInfo verifyInterfaceAuth(String methodName, String userId) throws ServiceException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("methodName",methodName);
		paramMap.put("userId",userId);
		InterfaceInfo info = interfaceInfoDao.selectByMap(paramMap);
		if (null == info) {
			throw new ServiceException("NO-PERMISSION");
		}
		return info;
	}
}