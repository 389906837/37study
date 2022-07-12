package com.cloud.cang.mgr.sl.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sl.dao.DeviceOperDao;
import com.cloud.cang.mgr.sl.domain.OperatelogDomain;
import com.cloud.cang.mgr.sl.service.DeviceOperService;
import com.cloud.cang.mgr.sl.vo.DeviceLogVo;
import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceOperServiceImpl extends GenericServiceImpl<DeviceOper, String> implements
		DeviceOperService {

	@Autowired
	DeviceOperDao deviceOperDao;

	
	@Override
	public GenericDao<DeviceOper, String> getDao() {
		return deviceOperDao;
	}


	@Override
	public Page<OperatelogDomain> deviceOperLog(Page<OperatelogDomain> page, DeviceLogVo deviceLogVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		String adress = deviceLogVo.getAdress();
		if (StringUtil.isNotBlank(adress)){
			char [] chars = adress.toCharArray();
			deviceLogVo.setAdress(StringUtils.join(chars,'%'));
		}
		return deviceOperDao.queryDeviceLog(deviceLogVo);
	}
}