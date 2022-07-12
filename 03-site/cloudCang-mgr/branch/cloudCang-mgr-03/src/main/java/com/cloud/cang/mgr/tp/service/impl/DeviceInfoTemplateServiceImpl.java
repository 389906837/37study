package com.cloud.cang.mgr.tp.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.tp.dao.DeviceInfoTemplateDao;
import com.cloud.cang.mgr.tp.domain.DeviceInfoTemplateDomain;
import com.cloud.cang.mgr.tp.service.DeviceInfoTemplateService;
import com.cloud.cang.mgr.tp.vo.DeviceInfoTemplateVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.tp.DeviceInfoTemplate;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceInfoTemplateServiceImpl extends GenericServiceImpl<DeviceInfoTemplate, String> implements
		DeviceInfoTemplateService {

	private static final Logger logger = LoggerFactory.getLogger(DeviceInfoTemplateServiceImpl.class);

	@Autowired
	private DeviceInfoTemplateDao deviceInfoTemplateDao;

	
	@Override
	public GenericDao<DeviceInfoTemplate, String> getDao() {
		return deviceInfoTemplateDao;
	}


	@Override
	public Page<DeviceInfoTemplateDomain> selectPageByDomainWhere(Page<DeviceInfoTemplateDomain> page, DeviceInfoTemplateVo deviceInfoTemplateVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<DeviceInfoTemplateDomain>) deviceInfoTemplateDao.selectByDomainWhere(deviceInfoTemplateVo);
	}

	/**
	 * 新增设备基础信息模板
	 *
	 * @param deviceInfoTemplate
	 * @return
	 */
	@Override
	public ResponseVo<DeviceInfoTemplate> insertTemplate(DeviceInfoTemplate deviceInfoTemplate) {
//		DeviceInfoTemplate deviceInfoTemplateVo = new DeviceInfoTemplate();
//		deviceInfoTemplateVo.setSname(deviceInfoTemplateVo.getSname());
//		deviceInfoTemplateVo.setSmerchantId(deviceInfoTemplateVo.getSmerchantId());
//		deviceInfoTemplateVo.setSmerchantCode(deviceInfoTemplateVo.getSmerchantCode());
//		deviceInfoTemplateVo.setIdelFlag(0);
//		List<DeviceInfoTemplate> deviceModelTemplateList = deviceInfoTemplateDao.selectByEntityWhere(deviceInfoTemplateVo);
//		if (CollectionUtils.isNotEmpty(deviceModelTemplateList)) {
//			return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("该模板名称已经存在");
//		}

		String code = CoreUtils.newCode(EntityTables.TP_DEVICE_INFO_TEMPLATE);        // 编号
		if (StringUtils.isBlank(code)) {
			logger.error("设备基础信息模板生成出错");
			return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("main.add.error",null));
		}
		deviceInfoTemplate.setScode(code);
		deviceInfoTemplate.setIstatus(20);
		deviceInfoTemplateDao.insert(deviceInfoTemplate);
		return ResponseVo.getSuccessResponse();
	}

	/**
	 * 逻辑删除
	 *
	 * @param checkboxId
	 * @return
	 */
	@Override
	public ResponseVo<String> deleteByLogic(String[] checkboxId) {
		for (String id : checkboxId) {
			DeviceInfoTemplate deviceInfoTemplate = new DeviceInfoTemplate();
			deviceInfoTemplate.setId(id);
			deviceInfoTemplate.setIdelFlag(1);
			deviceInfoTemplateDao.updateByIdSelective(deviceInfoTemplate);
		}
		return ResponseVo.getSuccessResponse(StringUtils.join(checkboxId));
	}

	@Override
	public ResponseVo<DeviceInfoTemplate> updateBySelectiveVo(DeviceInfoTemplate deviceInfoTemplate, DeviceInfoTemplate deviceInfoTemplateVo) {
//		List<DeviceInfoTemplate> deviceModelTemplateList = deviceInfoTemplateDao.selectByEntityWhere(deviceInfoTemplateVo);
//		if (CollectionUtils.isNotEmpty(deviceModelTemplateList)) {
//			return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("该模板名称已经存在");
//		}
		deviceInfoTemplateDao.updateByIdSelective(deviceInfoTemplate);
		return ResponseVo.getSuccessResponse(deviceInfoTemplateDao.selectByPrimaryKey(deviceInfoTemplate.getId()));
	}

	/**
	 *
	 * @param checkboxId
	 * @return
	 */
	@Override
	public ResponseVo<String> changeStatus(String[] checkboxId) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String id : checkboxId) {
			if (StringUtils.isNotBlank(id)) {
				DeviceInfoTemplate deviceInfoTemplate = deviceInfoTemplateDao.selectByPrimaryKey(id);
				if (null == deviceInfoTemplate) continue;
				DeviceInfoTemplate deviceInfoTemplateVo = new DeviceInfoTemplate();
				if (deviceInfoTemplate.getIstatus() == 10) {
					deviceInfoTemplateVo.setIstatus(20);
				} else if (deviceInfoTemplate.getIstatus() == 20) {
					deviceInfoTemplateVo.setIstatus(10);
				}
				deviceInfoTemplateVo.setId(id);
				deviceInfoTemplateDao.updateByIdSelective(deviceInfoTemplateVo);
				stringBuffer.append(deviceInfoTemplate.getScode()).append(",");
			}
		}
		String codes = stringBuffer.substring(0, stringBuffer.lastIndexOf(","));
		return ResponseVo.getSuccessResponse(codes);
	}

	/**
	 *
	 * @param merchantId
	 * @return
	 */
	@Override
	public List<DeviceInfoTemplate> selectTemplateByMerchant(String merchantId) {
		DeviceInfoTemplate deviceInfoTemplateVo = new DeviceInfoTemplate();
		deviceInfoTemplateVo.setIdelFlag(0);
		deviceInfoTemplateVo.setIstatus(10);
		deviceInfoTemplateVo.setSmerchantId(merchantId);
		return deviceInfoTemplateDao.selectByEntityWhere(deviceInfoTemplateVo);
	}
}