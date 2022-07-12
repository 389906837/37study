package com.cloud.cang.mgr.sb.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sb.dao.DeviceUpgradeDao;
import com.cloud.cang.mgr.sb.domain.DeviceUpgradeDetailsDomain;
import com.cloud.cang.mgr.sb.domain.DeviceUpgradeDomain;
import com.cloud.cang.mgr.sb.service.DeviceUpgradeService;
import com.cloud.cang.mgr.sb.vo.DeviceUpgradeVo;
import com.cloud.cang.model.sb.DeviceUpgrade;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceUpgradeServiceImpl extends GenericServiceImpl<DeviceUpgrade, String> implements
		DeviceUpgradeService {

	@Autowired
	DeviceUpgradeDao deviceUpgradeDao;

	
	@Override
	public GenericDao<DeviceUpgrade, String> getDao() {
		return deviceUpgradeDao;
	}


	/**
	 * 设备升级记录分页查询
	 *
	 * @param page
	 * @param deviceUpgradeVo
	 * @return
	 */
	@Override
	public Page<DeviceUpgradeDomain> selectPageByDomainWhere(Page<DeviceUpgradeDomain> page, DeviceUpgradeVo deviceUpgradeVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<DeviceUpgradeDomain>) deviceUpgradeDao.selectByDomainWhere(deviceUpgradeVo);
	}
}