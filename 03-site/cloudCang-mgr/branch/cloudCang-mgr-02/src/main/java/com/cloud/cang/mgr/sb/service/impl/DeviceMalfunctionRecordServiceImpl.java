package com.cloud.cang.mgr.sb.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sb.dao.DeviceMalfunctionRecordDao;
import com.cloud.cang.mgr.sb.domain.DeviceMalfunctionRecordDomain;
import com.cloud.cang.mgr.sb.service.DeviceMalfunctionRecordService;
import com.cloud.cang.mgr.sb.vo.DeviceMalfunctionRecordVo;
import com.cloud.cang.model.sb.DeviceMalfunctionRecord;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceMalfunctionRecordServiceImpl extends GenericServiceImpl<DeviceMalfunctionRecord, String> implements
				DeviceMalfunctionRecordService {

	@Autowired
	DeviceMalfunctionRecordDao deviceMalfunctionRecordDao;

	
	@Override
	public GenericDao<DeviceMalfunctionRecord, String> getDao() {
		return deviceMalfunctionRecordDao;
	}


	/**
	 * 查询设备故障所有信息
	 * @param page
	 * @param deviceMalfunctionRecordVo
	 * @return
	 */
	@Override
	public Page<DeviceMalfunctionRecordDomain> selectqueryData(Page<DeviceMalfunctionRecordDomain> page, DeviceMalfunctionRecordVo deviceMalfunctionRecordVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		String address = deviceMalfunctionRecordVo.getSdeviceAddress();
		if (StringUtils.isNotBlank(address)) {
			char[] chars = address.toCharArray();
			deviceMalfunctionRecordVo.setSdeviceAddress(StringUtils.join(chars, '%'));
		}
		return deviceMalfunctionRecordDao.selectqueryData(deviceMalfunctionRecordVo);
	}

	/**
	 * 删除设备故障信息
	 * @param checkboxId
	 */
	@Override
	public void delete(String[] checkboxId) {
		DeviceMalfunctionRecord deviceMalfunctionRecord = null;
		for (String id : checkboxId){
			deviceMalfunctionRecord = new DeviceMalfunctionRecord();
			deviceMalfunctionRecord.setId(id);
			deviceMalfunctionRecord.setIdelFlag(1);
			deviceMalfunctionRecord.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			deviceMalfunctionRecord.setTupdateTime(DateUtils.getCurrentDateTime());
			deviceMalfunctionRecordDao.updateByIdSelective(deviceMalfunctionRecord);
		}
	}
}