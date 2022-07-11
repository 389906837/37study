package com.cloud.cang.api.sh.service;

import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.rfid.MerchantDto;

import java.util.List;

public interface MerchantInfoService extends GenericService<MerchantInfo, String> {
    /**
     * 获取商户信息
     * @param merchantCode 商户编号
     * @return
     */
    MerchantInfo selectByCode(String merchantCode);
    /**
     * 根据用户名查询商户信息
     * @param username 用户名或者手机号
     * @param password
     * @return
     */
    List<MerchantDto> selectMerchantsByuserNameOrmobile(String username, String password);
}