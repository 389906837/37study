package com.cloud.cang.tec.tj.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.tj.SummarizationDataPlf;
import com.cloud.cang.tj.StatisticsVo;

/** 平台数据汇总表(TJ_SUMMARIZATION_DATA_PLF) **/
public interface SummarizationDataPlfDao extends GenericDao<SummarizationDataPlf, String> {
    /**
     * 查询最近一次统计结果
     * @param merchantId 商户Id
     * @return
     */
    SummarizationDataPlf selectLatelySummarizationDataPlf(String merchantId);

    /**
     * 查询当天
     * @param map 查询时间 商户Id
     * @return
     */
    StatisticsVo selectTodayStatisticsByDate(Map<String, Object> map);

    /**
     * 查询平台统计数据
     * @param map 查询统计截止日期 商户ID
     * @return
     */
    StatisticsVo selectStatisticsByDate(Map<String, Object> map);
}