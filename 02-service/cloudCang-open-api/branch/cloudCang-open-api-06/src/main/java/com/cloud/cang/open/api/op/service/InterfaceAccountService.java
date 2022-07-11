package com.cloud.cang.open.api.op.service;

import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface InterfaceAccountService extends GenericService<InterfaceAccount, String> {


    /**
     * 根据查询参数获取接口账户
     * @param paramMap 查询参数
     * @return
     */
    InterfaceAccount selectByMap(Map<String, Object> paramMap);

    /**
     * 锁住接口账户信息
     * @param id 账户ID
     * @return
     */
    InterfaceAccount selectByPrimaryKeySync(String id);

    /**
     * 根据查询参数获取接口账户数据
     * @param paramMap 查询参数
     * @return
     */
    InterfaceAccount selectByParamsMap(Map<String, Object> paramMap);
}