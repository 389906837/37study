package com.cloud.cang.mgr.tb.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.tb.dao.OperateDeviceRecordDao;
import com.cloud.cang.mgr.tb.domain.OperateDeviceRecordDomain;
import com.cloud.cang.mgr.tb.service.OperateDeviceRecordService;
import com.cloud.cang.mgr.tb.vo.OperateDeviceRecordVo;
import com.cloud.cang.model.tb.OperateDeviceRecord;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperateDeviceRecordServiceImpl extends GenericServiceImpl<OperateDeviceRecord, String> implements
		OperateDeviceRecordService {

	@Autowired
	OperateDeviceRecordDao operateDeviceRecordDao;

	
	@Override
	public GenericDao<OperateDeviceRecord, String> getDao() {
		return operateDeviceRecordDao;
	}


	/**
	 * 分页查询
	 *
	 * @param page
	 * @param operateDeviceRecordVo
	 * @return
	 */
	@Override
	public Page<OperateDeviceRecordDomain> selectPageByDomainWhere(Page<OperateDeviceRecordDomain> page, OperateDeviceRecordVo operateDeviceRecordVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<OperateDeviceRecordDomain>) operateDeviceRecordDao.selectByDomainWhere(operateDeviceRecordVo);
	}
}