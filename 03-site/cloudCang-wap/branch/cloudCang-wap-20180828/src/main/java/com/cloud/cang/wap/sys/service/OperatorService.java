package com.cloud.cang.wap.sys.service;

import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.generic.GenericService;

public interface OperatorService extends GenericService<Operator, String> {

    /**
     * 获取系统用户
     * @param userName 会员名
     * @param merchantCode 商户编号
     * @return
     */
    Operator selectByMemberName(String userName, String merchantCode);
}