package com.cloud.cang.rec.sl.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.sl.VistLog;
import com.cloud.cang.rec.sl.dao.VistLogDao;
import com.cloud.cang.rec.sl.service.VistLogService;
import com.cloud.cang.rec.sl.vo.VistLogVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VistLogServiceImpl extends GenericServiceImpl<VistLog, String> implements
		VistLogService {

	@Autowired
	VistLogDao vistLogDao;

	
	@Override
	public GenericDao<VistLog, String> getDao() {
		return vistLogDao;
	}



	@Override
	public Page<VistLog> queryVistLog(Page<VistLog> page, VistLogVo vistLogVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return vistLogDao.queryVistLog(vistLogVo);
	}
}