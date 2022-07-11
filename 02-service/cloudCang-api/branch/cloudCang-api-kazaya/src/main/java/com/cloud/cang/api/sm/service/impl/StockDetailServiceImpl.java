package com.cloud.cang.api.sm.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cloud.cang.inventory.CommodityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.api.sm.dao.StockDetailDao;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.api.sm.service.StockDetailService;

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

	/**
	 * 根据SIDENTIFIES查询出所有的库存详细，按照商品分类
	 * @param lables
	 * @return
	 */
	@Override
	public List<CommodityVo> selectCommodityVoGruopByCommodityCode(Set<String> lables) {
		return stockDetailDao.selectCommodityVoGruopByCommodityCode(lables);
	}
}