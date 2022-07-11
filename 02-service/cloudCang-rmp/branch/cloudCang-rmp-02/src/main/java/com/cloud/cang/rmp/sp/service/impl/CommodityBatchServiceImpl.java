package com.cloud.cang.rmp.sp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.rmp.sp.dao.CommodityBatchDao;
import com.cloud.cang.model.sp.CommodityBatch;
import com.cloud.cang.rmp.sp.service.CommodityBatchService;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommodityBatchServiceImpl extends GenericServiceImpl<CommodityBatch, String> implements
		CommodityBatchService {

	@Autowired
	CommodityBatchDao commodityBatchDao;

	
	@Override
	public GenericDao<CommodityBatch, String> getDao() {
		return commodityBatchDao;
	}

	/**
	 * 获取商品批次数据
	 * @param commodityId 商品ID
	 * @return
	 */
	@Override
	public CommodityBatch selectByCommodityId(String commodityId) {
		return commodityBatchDao.selectByCommodityId(commodityId);
	}

	/**
	 *  根据主键 锁定批次
	 * @param id 批次ID
	 * @return
	 */
	@Override
	public CommodityBatch selectByPrimaryKeyForUpdate(String id) {
		return commodityBatchDao.selectByPrimaryKeyForUpdate(id);
	}

	/**
	 * 更新商品批次数据
	 * @param sbatchNo 批次号
	 * @param commodityId 商品ID
	 */
	@Override
	public int updateBySbatchNo(String sbatchNo, String commodityId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sbatchNo", sbatchNo);
		map.put("commodityId", commodityId);
		return commodityBatchDao.updateBySbatchNo(map);
	}
}