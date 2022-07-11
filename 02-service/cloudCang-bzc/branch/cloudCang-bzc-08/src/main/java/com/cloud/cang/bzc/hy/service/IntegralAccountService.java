package com.cloud.cang.bzc.hy.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.jm.ChangeIntegralDto;
import com.cloud.cang.model.hy.IntegralAccount;
import com.cloud.cang.generic.GenericService;

public interface IntegralAccountService extends GenericService<IntegralAccount, String> {

    /***
     *更新积分主表
     *@param changeIntegralDto
     @return
     */
    ResponseVo changeIntegralAccoun(ChangeIntegralDto changeIntegralDto);
}