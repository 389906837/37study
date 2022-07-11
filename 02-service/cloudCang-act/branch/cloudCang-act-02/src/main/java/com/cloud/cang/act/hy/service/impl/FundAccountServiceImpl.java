package com.cloud.cang.act.hy.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.cloud.cang.act.hy.service.FundAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.act.hy.dao.FundAccountDao;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.hy.FundAccount;

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
	public FundAccount selectFundAccountByMemberId(String memberId) {
		return fundAccountDao.selectFundAccountByMemberId(memberId);
	}


	@Override
	public FundAccount selectActivityFundAccount() {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		//paramMap.put("fundAccountUse", BizTypeDefinitionEnum.FundAccountUse.ACTIVITY_FEE.getCode());
		//paramMap.put("iaccountType", BizTypeDefinitionEnum.FundAccountType.PLATFORM_ACCOUNT.getCode());
		return fundAccountDao.selectActivityFundAccount(paramMap);
	}

	
	

}