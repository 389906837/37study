package com.cloud.cang.mgr.sb.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sb.dao.AiInfoDao;
import com.cloud.cang.mgr.sb.dao.DeviceInfoDao;
import com.cloud.cang.mgr.sb.dao.DeviceRegisterDao;
import com.cloud.cang.mgr.sb.domain.AiRegisterDomain;
import com.cloud.cang.mgr.sb.domain.DeviceRegisterDomain;
import com.cloud.cang.mgr.sb.service.DeviceRegisterService;
import com.cloud.cang.mgr.sb.vo.AiRegisterVo;
import com.cloud.cang.mgr.sb.vo.DeviceRegisterVo;
import com.cloud.cang.mgr.sh.dao.MerchantInfoDao;
import com.cloud.cang.model.sb.AiInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.ObjectUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceRegisterServiceImpl extends GenericServiceImpl<DeviceRegister, String> implements
		DeviceRegisterService {

	@Autowired
	DeviceRegisterDao deviceRegisterDao;

	@Autowired
	DeviceInfoDao deviceInfoDao;//0.设备基础信息

	@Autowired
	MerchantInfoDao merchantInfoDao;//商户

	@Autowired
	AiInfoDao aiInfoDao;//AI设备基础信息



	@Override
	public GenericDao<DeviceRegister, String> getDao() {
		return deviceRegisterDao;
	}


	@Override
	public Page<DeviceRegisterDomain> selectPageByDomainWhere(Page<DeviceRegisterDomain> page, DeviceRegisterVo deviceRegisterVo) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<DeviceRegisterDomain>) deviceRegisterDao.selectByDomainWhere(deviceRegisterVo);
	}

	@Override
	public void deleteByIds(String[] checkboxId) {
		DeviceRegister deviceRegister = null;
		for (String deviceId : checkboxId) {
			deviceRegister = new DeviceRegister();
			String id = deviceRegisterDao.selectIdByDeviceId(deviceId);//根据设备ID查询设备注册表主键ID
			deviceRegister.setId(id);
			deviceRegister.setIdelFlag(1);
			//逻辑删除，将表中是否删除改为1
			deviceRegisterDao.updateByIdSelective(deviceRegister);
		}
	}

	@Override
	public DeviceRegister selectByDeviceId(String deviceId) {
		return deviceRegisterDao.selectByDeviceId(deviceId);
	}

	@Override
	public DeviceRegisterDomain selectViewBySid(String sid) {

		DeviceRegisterDomain deviceRegisterDomain = new DeviceRegisterDomain();
		DeviceRegister deviceRegister = deviceRegisterDao.selectByPrimaryKey(sid);//获取设备注册信息
		DeviceInfo deviceInfo = null;
		MerchantInfo merchantInfo = null;
		if (null != deviceRegister) {
			String sbId = deviceRegister.getSdeviceId();//设备ID
			if (StringUtils.isNotBlank(sbId)) {
				deviceInfo = deviceInfoDao.selectByPrimaryKey(sbId);//获取设备信息.
				String shId = deviceInfo.getSmerchantId();
				if (StringUtils.isNotBlank(shId)) {
					merchantInfo = merchantInfoDao.selectByPrimaryKey(shId);//获取商户信息
				}
			}

			if (null != deviceInfo && null != merchantInfo) {//
				ObjectUtils.copyObjValue(deviceRegister, deviceRegisterDomain);//
				//set属性
				deviceRegisterDomain.setId(deviceRegister.getId());//设备注册表ID
				deviceRegisterDomain.setSname(deviceInfo.getSname());//设备名称
				deviceRegisterDomain.setMerchantCode(merchantInfo.getScode());//商户编号
				deviceRegisterDomain.setMerchantName(merchantInfo.getSname());//商户名称
				return deviceRegisterDomain;
			}
		}
		return deviceRegisterDomain;
	}

	@Override
	public String updateState(DeviceRegister deviceRegister) {
		String id = deviceRegister.getId();
		if (StringUtils.isNotBlank(id)) {
			/*  设置属性    */
			deviceRegister.setTauditTime(DateUtils.getCurrentDateTime());/* 审核日期 */
			deviceRegister.setSauditUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 审核人 */
			deviceRegister.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
			deviceRegister.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 修改人 */
			/*  修改数据入库    */
			deviceRegisterDao.updateByIdSelectiveVo(deviceRegister);
			return "success";
		}
		return "faild";
	}

	@Override
	public String updateStateByEdit(DeviceRegister deviceRegister) {
		String id = deviceRegister.getId();
		if (StringUtils.isNotBlank(id)) {
			/*  设置属性    */
			deviceRegister.setIstatus(10);//状态： 10 待审核 20  审核通过  30 审核拒绝 40 已注册
			deviceRegister.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
			deviceRegister.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 修改人 */
			/*  修改数据入库    */
			deviceRegisterDao.updateByIdSelectiveVo(deviceRegister);
			return "success";
		}
		return "faild";
	}

	/**
	 * AI设备注册码分页查询
	 *
	 * @param page         分页对象
	 * @param aiRegisterVo 查询条件
	 * @return
	 */
	@Override
	public Page<AiRegisterDomain> selectAiPageByDomainWhere(Page<AiRegisterDomain> page, AiRegisterVo aiRegisterVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<AiRegisterDomain>) deviceRegisterDao.selectAiByDomainWhere(aiRegisterVo);
	}

	/**
	 * 根据设备注册ID查询AI设备注册信息，AI设备编号名称，商户编号名称
	 *
	 * @param sid 注册表ID
	 * @return
	 */
	@Override
	public AiRegisterDomain selectAiViewBySid(String sid) {
		AiRegisterDomain aiRegisterDomain = new AiRegisterDomain();
		DeviceRegister deviceRegister = deviceRegisterDao.selectByPrimaryKey(sid);//获取设备注册信息
		AiInfo aiInfo = null;
		if (null != deviceRegister) {
			String sbId = deviceRegister.getSdeviceId();//设备ID
			if (StringUtils.isNotBlank(sbId)) {
				aiInfo = aiInfoDao.selectByPrimaryKey(sbId);
				if (null != aiInfo) {
					aiRegisterDomain.setIstatus(deviceRegister.getIstatus());
					aiRegisterDomain.setSaiName(aiInfo.getSaiName());
					aiRegisterDomain.setSdeviceId(deviceRegister.getSdeviceId());
					aiRegisterDomain.setSaiCode(deviceRegister.getSdeviceCode());
					aiRegisterDomain.setId(deviceRegister.getId());
					aiRegisterDomain.setOldReqIp(deviceRegister.getSregIp());
					aiRegisterDomain.setSremark(deviceRegister.getSremark());
				}
			}
		}
		return aiRegisterDomain;
	}

	/**
	 * 逻辑删除
	 * @param aiRegister ai设备注册信息
	 */
	@Override
	public void updateAiRegisterByAiId(DeviceRegister aiRegister) {
		deviceRegisterDao.updateAiRegisterLogicDelete(aiRegister);
		return;
	}

}