package com.cloud.cang.mgr.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.hy.domain.IntegralAccountDomain;
import com.cloud.cang.mgr.hy.vo.IntegralAccountVo;
import com.cloud.cang.model.hy.IntegralAccount;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/** 会员积分账户(HY_INTEGRAL_ACCOUNT) **/
public interface IntegralAccountDao extends GenericDao<IntegralAccount, String> {

    Page<IntegralAccountDomain> selectAllAccount(IntegralAccountVo integralAccountVo);

    List<Map<String,String>> selectIntegralAccountId(String sid);

}