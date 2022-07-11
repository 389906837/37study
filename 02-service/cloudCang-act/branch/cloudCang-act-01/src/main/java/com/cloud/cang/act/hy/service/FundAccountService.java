package com.cloud.cang.act.hy.service;

import com.cloud.cang.model.hy.FundAccount;
import com.cloud.cang.generic.GenericService;

public interface FundAccountService extends GenericService<FundAccount, String> {
	
	/**
	 * 根据会员ID查找资金账户
	 * @param memberId
	 * @return
	 */
	FundAccount selectFundAccountByMemberId(String memberId);
	
	FundAccount selectActivityFundAccount();
 
    
}