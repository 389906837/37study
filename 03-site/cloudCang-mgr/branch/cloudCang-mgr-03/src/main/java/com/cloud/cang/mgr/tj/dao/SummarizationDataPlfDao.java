package com.cloud.cang.mgr.tj.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.tj.SummarizationDataPlf;

/**
 * 平台数据汇总表(TJ_SUMMARIZATION_DATA_PLF)
 **/
public interface SummarizationDataPlfDao extends GenericDao<SummarizationDataPlf, String> {
    /**
     * 根据商户Id查询统计表
     *
     * @param
     * @return
     */
     SummarizationDataPlf selectByMerchantId(String smerchantId);
}
