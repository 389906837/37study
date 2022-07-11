package com.cloud.cang.tec.sm.service;

import com.cloud.cang.model.sm.StandardDetail;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.tec.sm.vo.StandardDetailVo;

import java.util.List;

public interface StandardDetailService extends GenericService<StandardDetail, String> {

    List<StandardDetailVo>  selectStockWarningByMerchant(String merchantId);
}