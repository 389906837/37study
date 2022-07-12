package com.cloud.cang.mgr.sb.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sb.dao.DeviceUpgradeDetailsDao;
import com.cloud.cang.mgr.sb.domain.DeviceUpgradeDetailsDomain;
import com.cloud.cang.mgr.sb.service.DeviceUpgradeDetailsService;
import com.cloud.cang.mgr.sb.vo.DeviceUpgradeDetailsVo;
import com.cloud.cang.model.sb.DeviceUpgradeDetails;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceUpgradeDetailsServiceImpl extends GenericServiceImpl<DeviceUpgradeDetails, String> implements
		DeviceUpgradeDetailsService {

	@Autowired
	DeviceUpgradeDetailsDao deviceUpgradeDetailsDao;

	
	@Override
	public GenericDao<DeviceUpgradeDetails, String> getDao() {
		return deviceUpgradeDetailsDao;
	}


	/**
	 * 分页查询设备升级记录明细
	 *
	 * @param page
	 * @param deviceUpgradeDetailsVo
	 * @return
	 */
	@Override
	public Page<DeviceUpgradeDetailsDomain> selectPageByDomainWhere(Page<DeviceUpgradeDetailsDomain> page, DeviceUpgradeDetailsVo deviceUpgradeDetailsVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<DeviceUpgradeDetailsDomain>) deviceUpgradeDetailsDao.selectByDomainWhere(deviceUpgradeDetailsVo);
	}

	/**
	 * 根据ID查询domain信息
	 *
	 * @param sid
	 * @return
	 */
	@Override
	public DeviceUpgradeDetailsDomain selectDomainById(String sid) {
		return deviceUpgradeDetailsDao.selectDomainById(sid);
	}


}