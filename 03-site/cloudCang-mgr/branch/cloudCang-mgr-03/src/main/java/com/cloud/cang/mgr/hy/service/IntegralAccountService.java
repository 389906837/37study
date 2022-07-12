package com.cloud.cang.mgr.hy.service;

import com.cloud.cang.mgr.hy.domain.IntegralAccountDomain;
import com.cloud.cang.mgr.hy.vo.IntegralAccountVo;
import com.cloud.cang.model.hy.IntegralAccount;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface IntegralAccountService extends GenericService<IntegralAccount, String> {

    /**
     * 会员积分账户查询接口
     * @param page
     * @param integralAccountVo
     * @return
     */
    Page<IntegralAccountDomain> selectAllAccount(Page<IntegralAccountDomain> page, IntegralAccountVo integralAccountVo);

    List<Map<String,String>> selectIntegralAccountId(String sid);

    /**
     * 积分调整
     * @param integralAccount
     * @return
     */
    IntegralAccount saveOrUpdate(IntegralAccount integralAccount);
}