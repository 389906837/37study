package com.cloud.cang.mgr.hy.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.hy.dao.FundAccountDao;
import com.cloud.cang.mgr.hy.domain.FundAccountDomain;
import com.cloud.cang.mgr.hy.service.FundAccountService;
import com.cloud.cang.mgr.hy.vo.FundAccountVo;
import com.cloud.cang.model.hy.FundAccount;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundAccountServiceImpl extends GenericServiceImpl<FundAccount, String> implements
		FundAccountService {

	@Autowired
	FundAccountDao fundAccountDao;

	
	@Override
	public GenericDao<FundAccount, String> getDao() {
		return fundAccountDao;
	}


	@Override
	public Page<FundAccountDomain> selectAccountAll(Page<FundAccountDomain> page, FundAccountVo fundAccountVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return fundAccountDao.selectAccountAll(fundAccountVo);
	}
}