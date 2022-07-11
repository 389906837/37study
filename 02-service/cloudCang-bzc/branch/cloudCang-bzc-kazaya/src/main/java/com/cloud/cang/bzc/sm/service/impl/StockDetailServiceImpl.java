package com.cloud.cang.bzc.sm.service.impl;

import com.cloud.cang.bzc.sm.dao.StockDetailDao;
import com.cloud.cang.bzc.sm.service.StockDetailService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.inventory.CommodityVo;
import com.cloud.cang.model.sm.StockDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class StockDetailServiceImpl extends GenericServiceImpl<StockDetail, String> implements
		StockDetailService {

	@Autowired
	StockDetailDao stockDetailDao;

	
	@Override
	public GenericDao<StockDetail, String> getDao() {
		return stockDetailDao;
	}

	/**
	 * 查询设备库存
	 * @param deviceId 设备ID
	 * @return
	 */
	@Override
	public Map<String, Integer> selectMapByDeviceId(String deviceId) {
		List<Map<String, Object>> list = stockDetailDao.selectMapByDeviceId(deviceId);
		Map<String, Integer> map = new HashMap<>();
		if (null != list && list.size() > 0) {
			for (Map<String, Object> temp : list) {
				map.put(String.valueOf(temp.get("SVR_CODE")), Integer.parseInt(String.valueOf(temp.get("ISTOCK"))));
			}
		}
		return map;
	}
}