package com.cloud.cang.mgr.hy.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.hy.dao.IntegralSerialDao;
import com.cloud.cang.mgr.hy.service.IntegralSerialService;
import com.cloud.cang.mgr.hy.vo.IntegralSerialVo;
import com.cloud.cang.model.hy.IntegralSerial;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IntegralSerialServiceImpl extends GenericServiceImpl<IntegralSerial, String> implements
		IntegralSerialService {

	@Autowired
	IntegralSerialDao integralSerialDao;

	
	@Override
	public GenericDao<IntegralSerial, String> getDao() {
		return integralSerialDao;
	}


//	@Override
//	public Page<IntegralSerial> selectAllSerial(Page<IntegralSerial> page, IntegralSerialVo integralSerialVo) {
//		PageHelper.startPage(page.getPageNum(), page.getPageSize());
//		return integralSerialDao.selectSerialDetailById(integralSerialVo);
//	}

	@Override
	public Map<String, String> selectIntegralTotal(IntegralSerialVo integralSerialVo) {
		return integralSerialDao.selectIntegralTotal(integralSerialVo);
	}

	@Override
	public List<IntegralSerial> selectMember(String sid) {
		return integralSerialDao.selectMember(sid);
	}

}