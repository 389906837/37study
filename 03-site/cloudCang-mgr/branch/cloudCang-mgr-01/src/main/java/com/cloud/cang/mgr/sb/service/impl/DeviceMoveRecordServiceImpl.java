package com.cloud.cang.mgr.sb.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sb.dao.DeviceMoveRecordDao;
import com.cloud.cang.mgr.sb.domain.DeviceMoveRecordDomain;
import com.cloud.cang.mgr.sb.service.DeviceMoveRecordService;
import com.cloud.cang.mgr.sb.vo.DeviceMoveRecordVo;
import com.cloud.cang.model.sb.DeviceMoveRecord;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceMoveRecordServiceImpl extends GenericServiceImpl<DeviceMoveRecord, String> implements
		DeviceMoveRecordService {

	@Autowired
	DeviceMoveRecordDao deviceMoveRecordDao;

	
	@Override
	public GenericDao<DeviceMoveRecord, String> getDao() {
		return deviceMoveRecordDao;
	}

	/**
	 * 查询设备搬迁信息
	 * @param page
	 * @param deviceMoveRecordVo
	 * @return
	 */
	@Override
	public Page<DeviceMoveRecordDomain> selectqueryData(Page<DeviceMoveRecordDomain> page, DeviceMoveRecordVo deviceMoveRecordVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		String address = deviceMoveRecordVo.getSdeviceOldAddress();
		if (StringUtils.isNotBlank(address)) {
			char[] chars = address.toCharArray();
			deviceMoveRecordVo.setSdeviceOldAddress(StringUtils.join(chars, '%'));
		}
		return deviceMoveRecordDao.selectqueryData(deviceMoveRecordVo);
	}

	/**
	 * 删除设备搬迁信息
	 * @param checkboxId
	 */
	@Override
	public void delete(String[] checkboxId) {
		DeviceMoveRecord deviceMoveRecord = null;
		for (String id : checkboxId){
			deviceMoveRecord = new DeviceMoveRecord();
			deviceMoveRecord.setId(id);
			deviceMoveRecord.setIdelFlag(1);
			deviceMoveRecord.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			deviceMoveRecord.setTupdateTime(DateUtils.getCurrentDateTime());
			deviceMoveRecordDao.updateByIdSelective(deviceMoveRecord);
		}
	}
}