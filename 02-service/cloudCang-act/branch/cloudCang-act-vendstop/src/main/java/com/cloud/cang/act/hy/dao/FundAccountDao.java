package com.cloud.cang.act.hy.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.FundAccount;

/** 用户资金账户表(HY_FUND_ACCOUNT) **/
public interface FundAccountDao extends GenericDao<FundAccount, String> {


	/**
	 * 根据会员ID查找资金账户
	 * @param memberId
	 * @return
	 */
	FundAccount selectFundAccountByMemberId(String memberId);
	
	/**
	 * 获取活动专用户
	 * @param memberId
	 * @return
	 */
	FundAccount selectActivityFundAccount(Map map);
	
	
}