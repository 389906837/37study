package com.cloud.cang.rec.sh.service;

import com.cloud.cang.rec.sh.domain.MerchantInfoDomain;
import com.cloud.cang.rec.sh.vo.MerchantInfoVo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface MerchantInfoService extends GenericService<MerchantInfo, String> {

    Page<MerchantInfoVo> selectPageByDomainWhere(Page<MerchantInfoVo> page, MerchantInfoDomain merchantInfoDomain);

    MerchantInfo selectByCode(String code);

}