package com.cloud.cang.rmp.sm.service.impl;

import java.util.List;

import com.cloud.cang.rmp.sm.vo.StandardStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.rmp.sm.dao.StandardDetailDao;
import com.cloud.cang.model.sm.StandardDetail;
import com.cloud.cang.rmp.sm.service.StandardDetailService;

@Service
public class StandardDetailServiceImpl extends GenericServiceImpl<StandardDetail, String> implements
		StandardDetailService {

	@Autowired
	StandardDetailDao standardDetailDao;


	@Override
	public GenericDao<StandardDetail, String> getDao() {
		return standardDetailDao;
	}

	/**
	 *  查询设备标准库存商品明细
	 * @param sdeviceId 设备编号
	 * @return
	 */
	@Override
	public List<StandardStockVo> selectStandardStockByDeviceId(String sdeviceId) {
		return standardDetailDao.selectStandardStockByDeviceId(sdeviceId);
	}
}