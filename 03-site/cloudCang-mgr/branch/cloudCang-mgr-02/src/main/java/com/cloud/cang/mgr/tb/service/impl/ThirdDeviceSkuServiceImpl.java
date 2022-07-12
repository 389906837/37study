package com.cloud.cang.mgr.tb.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.tb.dao.ThirdDeviceSkuDao;
import com.cloud.cang.mgr.tb.domain.ThirdDeviceSkuDomain;
import com.cloud.cang.mgr.tb.service.ThirdDeviceSkuService;
import com.cloud.cang.mgr.tb.vo.ThirdDeviceSkuVo;
import com.cloud.cang.model.tb.ThirdDeviceSku;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThirdDeviceSkuServiceImpl extends GenericServiceImpl<ThirdDeviceSku, String> implements
		ThirdDeviceSkuService {

	@Autowired
	ThirdDeviceSkuDao thirdDeviceSkuDao;

	
	@Override
	public GenericDao<ThirdDeviceSku, String> getDao() {
		return thirdDeviceSkuDao;
	}


	/**
	 * 第三方商户设备SKU库分页查询
	 *
	 * @param page
	 * @param thirdDeviceSkuVo
	 * @return
	 */
	@Override
	public Page<ThirdDeviceSkuDomain> selectPageByDomainWhere(Page<ThirdDeviceSkuDomain> page, ThirdDeviceSkuVo thirdDeviceSkuVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<ThirdDeviceSkuDomain>) thirdDeviceSkuDao.selectByDomainWhere(thirdDeviceSkuVo);
	}

	/**
	 * 第三方商户设备SKU库商品详情分页查询
	 *
	 * @param page
	 * @param thirdDeviceSkuVo
	 * @return
	 */
	@Override
	public Page<ThirdDeviceSku> selectViewPageByDomainWhere(Page<ThirdDeviceSku> page, ThirdDeviceSku thirdDeviceSkuVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<ThirdDeviceSku>) thirdDeviceSkuDao.selectViewByDomainWhere(thirdDeviceSkuVo);
	}
}