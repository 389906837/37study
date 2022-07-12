package com.cloud.cang.mgr.tj.service;

import com.cloud.cang.model.tj.SummarizationDataPlf;
import com.cloud.cang.generic.GenericService;

public interface SummarizationDataPlfService extends GenericService<SummarizationDataPlf, String> {
    /**
     * 根据商户Id查询统计表
     * @param
     * @return
     */
    SummarizationDataPlf selectByMerchantId(String merchantId);
}