package com.cloud.cang.mgr.lm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.lm.domain.LabelBatchCommodityDomain;
import com.cloud.cang.model.lm.LabelBatchCommodity;

/**
 * 标注批次管理信息表商品表(LM_LABEL_BATCH_COMMODITY)
 **/
public interface LabelBatchCommodityDao extends GenericDao<LabelBatchCommodity, String> {


    /**
     * 根据视觉编号修改
     *
     * @param labelBatchCommodityDomain
     */
    void updateBySvrCode(LabelBatchCommodityDomain labelBatchCommodityDomain);
}