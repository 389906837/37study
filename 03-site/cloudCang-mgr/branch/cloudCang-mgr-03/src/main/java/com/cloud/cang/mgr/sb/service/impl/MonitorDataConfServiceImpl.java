package com.cloud.cang.mgr.sb.service.impl;

import java.util.List;

import com.cloud.cang.mgr.sb.dao.DeviceInfoDao;
import com.cloud.cang.mgr.sb.domain.MonitorDataConfDomain;
import com.cloud.cang.mgr.sb.vo.MonitorDataConfVo;
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

import com.cloud.cang.mgr.sb.dao.MonitorDataConfDao;
import com.cloud.cang.model.sb.MonitorDataConf;
import com.cloud.cang.mgr.sb.service.MonitorDataConfService;

@Service
public class MonitorDataConfServiceImpl extends GenericServiceImpl<MonitorDataConf, String> implements
		MonitorDataConfService {

	@Autowired
	MonitorDataConfDao monitorDataConfDao;

	@Autowired
	DeviceInfoDao deviceInfoDao;

	@Autowired
	MerchantInfoDao merchantInfoDao;

	
	@Override
	public GenericDao<MonitorDataConf, String> getDao() {
		return monitorDataConfDao;
	}


	@Override
	public MonitorDataConf selectByDeviceId(String deviceId) {

		return monitorDataConfDao.selectByDeviceId(deviceId);
	}

	@Override
	public Page<MonitorDataConfDomain> selectPageByDomainWhere(Page<MonitorDataConfDomain> page, MonitorDataConfVo monitorDataConfVo) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<MonitorDataConfDomain>) monitorDataConfDao.selectByDomainWhere(monitorDataConfVo);
	}

	/**
	 * 根据设备监控ID查询监控信息，设备信息，商户信息
	 * @param sid
	 * @return
	 */
	@Override
	public MonitorDataConfDomain selectViewBySid(String sid) {
		MonitorDataConfDomain monitorDataConfDomain = new MonitorDataConfDomain();
		MonitorDataConf monitorDataConf = monitorDataConfDao.selectByPrimaryKey(sid);//获取设备监控信息
		DeviceInfo deviceInfo = null;
		MerchantInfo merchantInfo = null;
		if (null != monitorDataConf) {
			String sbId = monitorDataConf.getSdeviceId();
			if (StringUtils.isNotBlank(sbId)) {
				deviceInfo = deviceInfoDao.selectByPrimaryKey(sbId);//获取设备信息
				String shId = deviceInfo.getSmerchantId();
				if (StringUtils.isNotBlank(shId)) {
					merchantInfo = merchantInfoDao.selectByPrimaryKey(shId);//获取商户信息
				}
			}

			if (null != deviceInfo && null != merchantInfo) {//
				ObjectUtils.copyObjValue(monitorDataConf, monitorDataConfDomain);//
				//set属性
				monitorDataConfDomain.setId(monitorDataConf.getId());//监控配置表ID
				monitorDataConfDomain.setScode(deviceInfo.getScode());//设备编号
				monitorDataConfDomain.setSname(deviceInfo.getSname());//设备名称
				monitorDataConfDomain.setMerchantCode(merchantInfo.getScode());//商户编号
				monitorDataConfDomain.setMerchantName(merchantInfo.getSname());//商户名称
				return monitorDataConfDomain;
			}
		}
		return monitorDataConfDomain;
	}
}