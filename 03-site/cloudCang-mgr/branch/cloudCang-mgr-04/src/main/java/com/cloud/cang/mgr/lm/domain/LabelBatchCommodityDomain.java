package com.cloud.cang.mgr.lm.domain;

import com.cloud.cang.model.lm.LabelBatchCommodity;

/**
 * 视觉商品扩展类
 * Created by yan on 2018/10/30.
 */
public class LabelBatchCommodityDomain extends LabelBatchCommodity {
    private String oldSvrCode;//视觉商品以前的视觉编号

    public String getOldSvrCode() {
        return oldSvrCode;
    }

    public void setOldSvrCode(String oldSvrCode) {
        this.oldSvrCode = oldSvrCode;
    }
}
