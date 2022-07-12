package com.cloud.cang.mgr.sm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.mgr.sb.dao.DeviceModelDao;
import com.cloud.cang.mgr.sb.domain.DetailStockDomain;
import com.cloud.cang.model.sb.DeviceModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sm.dao.StandardDetailDao;
import com.cloud.cang.model.sm.StandardDetail;
import com.cloud.cang.mgr.sm.service.StandardDetailService;

@Service
public class StandardDetailServiceImpl extends GenericServiceImpl<StandardDetail, String> implements
		StandardDetailService {

	@Autowired
	StandardDetailDao standardDetailDao;

	@Autowired
	DeviceModelDao deviceModelDao;

	
	@Override
	public GenericDao<StandardDetail, String> getDao() {
		return standardDetailDao;
	}


	/**
	 * 查询设备标准库存异常
	 * @param deviceId 设备编号
	 * @param layerNum 设备层数
	 * @return
	 */
	@Override
	public List<List<DetailStockDomain>> selectDetailStandard(String deviceId, Integer layerNum) {
		List<List<DetailStockDomain>> lists = new ArrayList<List<DetailStockDomain>>();
		Map<String, Object> queryMap = null;
		for (int i = 1; i <= layerNum ; i++) {
			queryMap = new HashMap<String, Object>();
			queryMap.put("sdeviceId", deviceId);
			queryMap.put("ilayerNum", i);
			List<DetailStockDomain> list = standardDetailDao.selectDetailStandard(queryMap);
			if (CollectionUtils.isNotEmpty(list)) {        //查询到商品
				lists.add(list);
			} else {    //没有查询到商品
				lists.add(new ArrayList<DetailStockDomain>());
			}
		}
		return lists;
	}
	/**
	 * 查询设备标准库存商品信息
	 * @param deviceId 设备Id
	 * @return
	 */
	@Override
	public List<DetailStockDomain> selectByDeviceId(String deviceId) {
		return standardDetailDao.selectByDeviceId(deviceId);
	}
	/**
	 * 删除设备标准商品明细
	 * @param sdeviceId 设备Id
	 */
	@Override
	public void deleteByDeviceId(String sdeviceId) {
		standardDetailDao.deleteByDeviceId(sdeviceId);
	}
}