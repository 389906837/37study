package com.cloud.cang.mgr.hy.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.mgr.hy.domain.IntegralAccountDomain;
import com.cloud.cang.mgr.hy.vo.IntegralAccountVo;
import com.cloud.cang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.hy.dao.IntegralAccountDao;
import com.cloud.cang.model.hy.IntegralAccount;
import com.cloud.cang.mgr.hy.service.IntegralAccountService;

@Service
public class IntegralAccountServiceImpl extends GenericServiceImpl<IntegralAccount, String> implements
		IntegralAccountService {

	@Autowired
	IntegralAccountDao integralAccountDao;

	
	@Override
	public GenericDao<IntegralAccount, String> getDao() {
		return integralAccountDao;
	}


	@Override
	public Page<IntegralAccountDomain> selectAllAccount(Page<IntegralAccountDomain> page, IntegralAccountVo integralAccountVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return integralAccountDao.selectAllAccount(integralAccountVo);
	}

	@Override
	public List<Map<String, String>> selectIntegralAccountId(String sid) {
		return integralAccountDao.selectIntegralAccountId(sid);
	}

	@Override
	public IntegralAccount saveOrUpdate(IntegralAccount integralAccount) {
		if (StringUtil.isNotBlank(integralAccount.getId())){
			integralAccount.setTupdateTime(new Date());
			this.updateBySelective(integralAccount);
		}
		return integralAccount;
	}

}