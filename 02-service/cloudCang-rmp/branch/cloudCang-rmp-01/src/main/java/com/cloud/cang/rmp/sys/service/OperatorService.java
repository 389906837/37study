package com.cloud.cang.rmp.sys.service;

import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.generic.GenericService;

public interface OperatorService extends GenericService<Operator, String> {

    /**
     * 根据手机号 获取系统用户
     * @param smemberName 会员手机号
     * @return
     */
    Operator selectByMoblie(String smemberName, String smerchantId);
}