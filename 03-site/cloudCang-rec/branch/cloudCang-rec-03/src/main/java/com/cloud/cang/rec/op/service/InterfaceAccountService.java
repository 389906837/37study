package com.cloud.cang.rec.op.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.op.BuyRecord;
import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.op.UserInterfaceAuth;
import com.cloud.cang.rec.op.domain.InterfaceAccountDomain;
import com.cloud.cang.rec.op.domain.UserInterfaceAuthDomain;
import com.cloud.cang.rec.op.vo.InterfaceAccountVo;
import com.github.pagehelper.Page;

public interface InterfaceAccountService extends GenericService<InterfaceAccount, String> {

    /**
     * 查询列表
     *
     * @param page
     * @param interfaceAccountDomain
     * @return
     */
    Page<InterfaceAccountVo> selectPageByDomainWhere(Page<InterfaceAccountVo> page, InterfaceAccountDomain interfaceAccountDomain);

    /**
     * 接口充值
     *
     * @param interfaceAccount
     * @param buyRecord
     * @return
     */
    ResponseVo saveRecharge(Integer type, InterfaceAccount interfaceAccount, BuyRecord buyRecord);

    /**
     * 关闭接口账户
     *
     * @param userId
     * @param interfaceCode
     */
    void closeOrOpenByUserIdAndInterfaceCode(String userId, String interfaceCode, Integer istatus);
}