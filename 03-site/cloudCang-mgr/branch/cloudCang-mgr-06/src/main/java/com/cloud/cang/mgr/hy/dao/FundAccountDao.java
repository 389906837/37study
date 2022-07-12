package com.cloud.cang.mgr.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.hy.domain.FundAccountDomain;
import com.cloud.cang.mgr.hy.vo.FundAccountVo;
import com.cloud.cang.model.hy.FundAccount;
import com.github.pagehelper.Page;

/** 用户资金账户表(HY_FUND_ACCOUNT) **/
public interface FundAccountDao extends GenericDao<FundAccount, String> {

    Page<FundAccountDomain> selectAccountAll(FundAccountVo fundAccountVo);
}