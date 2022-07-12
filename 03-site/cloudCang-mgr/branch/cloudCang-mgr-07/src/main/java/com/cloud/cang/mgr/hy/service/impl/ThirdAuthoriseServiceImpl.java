package com.cloud.cang.mgr.hy.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.hy.dao.ThirdAuthoriseDao;
import com.cloud.cang.mgr.hy.domain.ThirdAuthoriseInfoDomain;
import com.cloud.cang.mgr.hy.service.ThirdAuthoriseService;
import com.cloud.cang.mgr.hy.vo.ThirdAuthoriseInfoVo;
import com.cloud.cang.model.hy.ThirdAuthorise;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThirdAuthoriseServiceImpl extends GenericServiceImpl<ThirdAuthorise, String> implements
		ThirdAuthoriseService {

	@Autowired
	ThirdAuthoriseDao thirdAuthoriseDao;

	
	@Override
	public GenericDao<ThirdAuthorise, String> getDao() {
		return thirdAuthoriseDao;
	}


	@Override
	public Page<ThirdAuthoriseInfoDomain> selectThirdAuthorise(Page<ThirdAuthoriseInfoDomain> page, ThirdAuthoriseInfoVo thirdAuthoriseInfoVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return thirdAuthoriseDao.selectThirdAuthorise(thirdAuthoriseInfoVo);
	}
}