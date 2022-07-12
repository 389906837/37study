package com.cloud.cang.mgr.lm.service;

import com.cloud.cang.mgr.lm.domain.LabelBatchCommodityDomain;
import com.cloud.cang.model.lm.LabelBatchCommodity;
import com.cloud.cang.generic.GenericService;

public interface LabelBatchCommodityService extends GenericService<LabelBatchCommodity, String> {

    /**
     * 根据视觉编号修改
     *
     * @param labelBatchCommodityDomain
     */
    void updateBySvrCode(LabelBatchCommodityDomain labelBatchCommodityDomain);
}