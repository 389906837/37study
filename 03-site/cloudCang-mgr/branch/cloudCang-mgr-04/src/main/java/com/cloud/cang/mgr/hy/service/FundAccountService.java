package com.cloud.cang.mgr.hy.service;

import com.cloud.cang.mgr.hy.domain.FundAccountDomain;
import com.cloud.cang.mgr.hy.vo.FundAccountVo;
import com.cloud.cang.model.hy.FundAccount;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface FundAccountService extends GenericService<FundAccount, String> {

    /**
     * 会员资金账户查询
     * @param page
     * @param fundAccountVo
     * @return
     */
    Page<FundAccountDomain> selectAccountAll(Page<FundAccountDomain> page, FundAccountVo fundAccountVo);
}