package com.cloud.cang.mgr.tb.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.tb.dao.InterfaceTransferLogDao;
import com.cloud.cang.mgr.tb.domain.InterfaceTransferLogDomain;
import com.cloud.cang.mgr.tb.service.InterfaceTransferLogService;
import com.cloud.cang.mgr.tb.vo.InterfaceTransferLogVo;
import com.cloud.cang.model.tb.InterfaceTransferLog;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterfaceTransferLogServiceImpl extends GenericServiceImpl<InterfaceTransferLog, String> implements
		InterfaceTransferLogService {

	@Autowired
	InterfaceTransferLogDao interfaceTransferLogDao;

	
	@Override
	public GenericDao<InterfaceTransferLog, String> getDao() {
		return interfaceTransferLogDao;
	}


	/**
	 * 第三方接口调用查询
	 *
	 * @param page
	 * @param interfaceTransferLogVo
	 * @return
	 */
	@Override
	public Page<InterfaceTransferLogDomain> selectPageByDomainWhere(Page<InterfaceTransferLogDomain> page, InterfaceTransferLogVo interfaceTransferLogVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<InterfaceTransferLogDomain>) interfaceTransferLogDao.selectByDomainWhere(interfaceTransferLogVo);
	}
}