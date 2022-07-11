package com.cloud.cang.api.sb.service.impl;

import com.cloud.cang.api.sb.dao.DeviceModelDao;
import com.cloud.cang.api.sb.service.DeviceModelService;
import com.cloud.cang.api.ws.domain.DeviceModelConfigInfo;
import com.cloud.cang.api.ws.domain.LockPinConfigInfo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.sb.DeviceModel;
import com.cloud.cang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceModelServiceImpl extends GenericServiceImpl<DeviceModel, String> implements
		DeviceModelService {

	@Autowired
	DeviceModelDao deviceModelDao;

	
	@Override
	public GenericDao<DeviceModel, String> getDao() {
		return deviceModelDao;
	}


	/**
	 * 摄像头相关参数 = 摄像头+品牌+型号+方法
	 *
	 * @param deviceId 设备ID
	 * @return
	 */
	@Override
	public ResponseVo<String> getDeviceCaremaConfigInfo(String deviceId) {
		String configInfo = "";
		DeviceModel deviceModel = deviceModelDao.selectByDeviceId(deviceId);
		if (null != deviceModel) {
			configInfo = deviceModel.getScameraType() + deviceModel.getScameraBrand()
					+ deviceModel.getScameraModel() + deviceModel.getScameraMethod();
		}
		if (StringUtil.isNotBlank(configInfo)) {
			return ResponseVo.getSuccessResponse(configInfo);
		}
		return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("获取摄像头相关参数失败");
	}

	/**
	 * 获取设备芯片引脚配置信息
	 *
	 * @param deviceId 设备ID
	 * @return LockPinConfigInfo
	 */
	@Override
	public ResponseVo<LockPinConfigInfo> getLockPinConfigInfo(String deviceId) {
		DeviceModel deviceModel = deviceModelDao.selectByDeviceId(deviceId);
		LockPinConfigInfo lockPinConfigInfo = new LockPinConfigInfo();
		if (null != deviceModel) {
			lockPinConfigInfo.setIdoorPinSn(deviceModel.getIdoorPinSn());
			lockPinConfigInfo.setIhallPinSn(deviceModel.getIhallPinSn());
			lockPinConfigInfo.setIopendoorPinSn(deviceModel.getIopendoorPinSn());
			lockPinConfigInfo.setIlockCylinder(deviceModel.getIlockCylinder());
			return ResponseVo.getSuccessResponse(lockPinConfigInfo);
		}
		return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("获取设备芯片引脚配置信息失败");
	}

	/**
	 * 摄像头配置--锁引脚参数信息
	 *
	 * @param deviceId
	 * @return
	 */
	@Override
	public ResponseVo<DeviceModelConfigInfo> getDeviceModelConfigInfo(String deviceId) {
		DeviceModel deviceModel = deviceModelDao.selectByDeviceId(deviceId);
		DeviceModelConfigInfo deviceModelConfigInfo = new DeviceModelConfigInfo();
		if (null != deviceModel) {
			deviceModelConfigInfo.setIdoorPinSn(deviceModel.getIdoorPinSn());
			deviceModelConfigInfo.setIhallPinSn(deviceModel.getIhallPinSn());
			deviceModelConfigInfo.setIopendoorPinSn(deviceModel.getIopendoorPinSn());
			deviceModelConfigInfo.setIlockCylinder(deviceModel.getIlockCylinder());
			deviceModelConfigInfo.setIisUseExpandGpio(deviceModel.getIisUseExpandGpio() + "");
			deviceModelConfigInfo.setIisDetectHall(deviceModel.getIisDetectHall() + "");
			deviceModelConfigInfo.setSvisualServiceProvider(deviceModel.getSvisualServiceProvider());
			deviceModelConfigInfo.setSpcbModel(deviceModel.getSpcbModel());
			deviceModelConfigInfo.setCaremaConfig(deviceModel.getScameraMethod());
			return ResponseVo.getSuccessResponse(deviceModelConfigInfo);
		}
		return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("获取设备芯片引脚配置信息失败");

	}

}