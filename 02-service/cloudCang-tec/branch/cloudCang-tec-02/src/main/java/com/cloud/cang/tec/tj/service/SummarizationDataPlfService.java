package com.cloud.cang.tec.tj.service;

import com.cloud.cang.model.tj.SummarizationDataPlf;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.tj.StatisticsVo;

import java.util.Date;

public interface SummarizationDataPlfService extends GenericService<SummarizationDataPlf, String> {

    /**
     * 查询最近一次统计结果
     * @param merchantId 商户Id
     * @return
     */
    SummarizationDataPlf selectLatelySummarizationDataPlf(String merchantId);

    /**
     * 查询当天 数据
     * @param queryDate 查询时间
     * @param merchantId 商户Id
     * @return
     */
    StatisticsVo selectTodayStatisticsByDate(Date queryDate,String merchantId);

    /**
     * 查询平台统计数据
     * @param bqueryDate 查询统计开始日期
     * @param equeryDate 查询统计截止日期
     * @param merchantId 商户ID
     * @return
     */
    StatisticsVo selectStatisticsByDate(Date bqueryDate, Date equeryDate, String merchantId);
}