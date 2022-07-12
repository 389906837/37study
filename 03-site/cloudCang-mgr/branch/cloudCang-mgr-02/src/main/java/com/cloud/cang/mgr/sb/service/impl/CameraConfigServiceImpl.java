package com.cloud.cang.mgr.sb.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sb.dao.CameraConfigDao;
import com.cloud.cang.mgr.sb.dao.DeviceInfoDao;
import com.cloud.cang.mgr.sb.domain.CameraConfigDomain;
import com.cloud.cang.mgr.sb.service.CameraConfigService;
import com.cloud.cang.mgr.sb.vo.CameraConfigVo;
import com.cloud.cang.model.sb.CameraConfig;
import com.cloud.cang.model.sb.DeviceInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CameraConfigServiceImpl extends GenericServiceImpl<CameraConfig, String> implements
		CameraConfigService {

	@Autowired
	CameraConfigDao cameraConfigDao;

	@Autowired
	DeviceInfoDao deviceInfoDao;

	
	@Override
	public GenericDao<CameraConfig, String> getDao() {
		return cameraConfigDao;
	}


	@Override
	public Page<CameraConfigDomain> selectPageByDomainWhere(Page<CameraConfigDomain> page, CameraConfigVo cameraConfigVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return cameraConfigDao.selectPageByDomainWhere(cameraConfigVo);
	}

	@Override
	public ResponseVo<CameraConfig> insertCameraConfig(CameraConfig cameraConfig) {
		// 判断IP和端口号序列号是否存在
		CameraConfig cameraConfigVo = new CameraConfig();
		cameraConfigVo.setSdeviceId(cameraConfig.getSdeviceId());
		cameraConfigVo.setSip(cameraConfig.getSip());
		cameraConfigVo.setSport(cameraConfig.getSport());
		cameraConfigVo.setIdelFlag(0);
		List<CameraConfig> cameraConfigList = cameraConfigDao.selectByEntityWhere(cameraConfigVo);
		if (CollectionUtils.isNotEmpty(cameraConfigList)) {
			return  ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("IP，端口号已存在");
		}
		DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(cameraConfig.getSdeviceId());
		if (null != deviceInfo) {
			cameraConfig.setSdeviceCode(deviceInfo.getScode());
			cameraConfig.setIdelFlag(0);
			cameraConfigDao.insert(cameraConfig);
			return ResponseVo.getSuccessResponse(cameraConfig);
		}
		return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("未查询到对象设备编号");
	}

	/**
	 *
	 * @param checkboxId
	 */
	@Override
	public void delete(String[] checkboxId) {
		for (String id : checkboxId) {
			CameraConfig cameraConfig = new CameraConfig();
			cameraConfig.setId(id);
			cameraConfig.setIdelFlag(1);
			cameraConfigDao.updateByIdSelective(cameraConfig);
		}
	}

	@Override
	public void updateBySelectiveVo(CameraConfig cameraConfig) {
		cameraConfigDao.updateByIdSelectiveVo(cameraConfig);
	}

}