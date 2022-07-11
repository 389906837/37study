package com.cloud.cang.rmp.sm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.rmp.sm.dao.StandardStockDao;
import com.cloud.cang.model.sm.StandardStock;
import com.cloud.cang.rmp.sm.service.StandardStockService;

@Service
public class StandardStockServiceImpl extends GenericServiceImpl<StandardStock, String> implements
		StandardStockService {

	@Autowired
	StandardStockDao standardStockDao;


	@Override
	public GenericDao<StandardStock, String> getDao() {
		return standardStockDao;
	}

	/**
	 * 获取设备标准库存配置
	 * @param sdeviceId 设备ID
	 * @return
	 */
	@Override
	public StandardStock selectBySdeviceId(String sdeviceId) {
		return standardStockDao.selectBySdeviceId(sdeviceId);
	}
}