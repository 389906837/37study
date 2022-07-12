package com.cloud.cang.mgr.sb.service.impl;

import java.util.List;

import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.mgr.sb.dao.DeviceInfoDao;
import com.cloud.cang.mgr.sb.domain.DeviceModelDomain;
import com.cloud.cang.mgr.sb.vo.DeviceModelVo;
import com.cloud.cang.mgr.sh.dao.MerchantInfoDao;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.utils.ObjectUtils;
import com.cloud.cang.utils.StringUtil;
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

import com.cloud.cang.mgr.sb.dao.DeviceModelDao;
import com.cloud.cang.model.sb.DeviceModel;
import com.cloud.cang.mgr.sb.service.DeviceModelService;

@Service
public class DeviceModelServiceImpl extends GenericServiceImpl<DeviceModel, String> implements
		DeviceModelService {

	@Autowired
	DeviceModelDao deviceModelDao;

	@Autowired
	DeviceInfoDao deviceInfoDao;

	@Autowired
	MerchantInfoDao merchantInfoDao;

	
	@Override
	public GenericDao<DeviceModel, String> getDao() {
		return deviceModelDao;
	}


	@Override
	public DeviceModel selectByDeviceId(String deviceId) {

		return deviceModelDao.selectByDeviceId(deviceId);
	}

	@Override
	public Page<DeviceModelDomain> selectPageByDomainWhere(Page<DeviceModelDomain> page, DeviceModelVo deviceModelVo) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<DeviceModelDomain>) deviceModelDao.selectByDomainWhere(deviceModelVo);
	}

	@Override
	public int updateBySelectiveVo(DeviceModel deviceModel) {
		return deviceModelDao.updateByIdSelectiveVo(deviceModel);
	}

	/**
	 * 根据摄像头方法查询摄像头其他信息
	 *
	 * @param method 数据字典缓存名
	 * @return
	 */
	@Override
	public String getCaremaInfoByMethod(String method) {
		String value = GrpParaUtil.getValue(GrpParaUtil.DEVICE_CAMERA_CONFIG, method);            // value值
		String explain = GrpParaUtil.getExplain(GrpParaUtil.DEVICE_CAMERA_CONFIG, method);        // 说明
		if (StringUtil.isNotBlank(value) && StringUtil.isNotBlank(explain)) {
			return value + "-//-" + explain;
		}
		return "";
	}

}