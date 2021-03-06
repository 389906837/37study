package com.cloud.cang.mgr.sb.service.impl;

import java.util.List;

import com.cloud.cang.mgr.sb.dao.DeviceInfoDao;
import com.cloud.cang.mgr.sb.domain.DeviceManageDomain;
import com.cloud.cang.mgr.sb.domain.DeviceModelDomain;
import com.cloud.cang.mgr.sb.vo.DeviceManageVo;
import com.cloud.cang.mgr.sh.dao.MerchantInfoDao;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.utils.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sb.dao.DeviceManageDao;
import com.cloud.cang.model.sb.DeviceManage;
import com.cloud.cang.mgr.sb.service.DeviceManageService;

@Service
public class DeviceManageServiceImpl extends GenericServiceImpl<DeviceManage, String> implements
		DeviceManageService {

	@Autowired
	DeviceManageDao deviceManageDao;

	@Autowired
	DeviceInfoDao deviceInfoDao;

	@Autowired
	MerchantInfoDao merchantInfoDao;

	
	@Override
	public GenericDao<DeviceManage, String> getDao() {
		return deviceManageDao;
	}


	@Override
	public DeviceManage selectByDeviceId(String deviceId) {
		return deviceManageDao.selectByDeviceId(deviceId);
	}

	@Override
	public Page<DeviceModelDomain> selectPageByDomainWhere(Page<DeviceModelDomain> page, DeviceManageVo deviceManageVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<DeviceModelDomain>) deviceManageDao.selectByDomainWhere(deviceManageVo);
	}

	@Override
	public DeviceManageDomain selectViewBySid(String sid) {
		DeviceManageDomain deviceManageDomain = new DeviceManageDomain();
		DeviceManage deviceManage = deviceManageDao.selectByPrimaryKey(sid);//???????????????????????????????????????
		DeviceInfo deviceInfo = null;
		MerchantInfo merchantInfo = null;
		if (null != deviceManage) {
			String sbId = deviceManage.getSdeviceId();
			if (StringUtils.isNotBlank(sbId)) {
				deviceInfo = deviceInfoDao.selectByPrimaryKey(sbId);//??????????????????
				String shId = deviceInfo.getSmerchantId();
				if (StringUtils.isNotBlank(shId)) {
					merchantInfo = merchantInfoDao.selectByPrimaryKey(shId);//??????????????????
				}
			}

			if (null != deviceInfo && null != merchantInfo) {//
				ObjectUtils.copyObjValue(deviceManage, deviceManageDomain);//
				//set??????
				deviceManageDomain.setId(deviceManage.getId());//??????????????????????????????ID
				deviceManageDomain.setScode(deviceInfo.getScode());//????????????
				deviceManageDomain.setSname(deviceInfo.getSname());//????????????
				deviceManageDomain.setMerchantCode(merchantInfo.getScode());//????????????
				deviceManageDomain.setMerchantName(merchantInfo.getSname());//????????????
				return deviceManageDomain;
			}
		}
		return deviceManageDomain;
	}

	@Override
	public int updateBySelectiveVO1(DeviceManage deviceManage) {
		return deviceManageDao.updateByIdSelectiveVo1(deviceManage);
	}

}