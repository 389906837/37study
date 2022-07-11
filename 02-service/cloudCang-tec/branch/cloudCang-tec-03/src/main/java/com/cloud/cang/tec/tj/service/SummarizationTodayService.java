package com.cloud.cang.tec.tj.service;

import com.cloud.cang.model.tj.SummarizationToday;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.tj.StatisticsVo;

import java.util.Date;

public interface SummarizationTodayService extends GenericService<SummarizationToday, String> {


    /**
     * 查询商户每日数据统计
     * @param queryDate 查询日期
     * @param merchantId 商户ID
     * @return
     */
    SummarizationToday selectSummarizationTodayByDate(Date queryDate, String merchantId);

    /**
     * 查询当前日期的平台数据 当天
     * @param queryDate 查询日期
     * @param merchantId 商户Id
     * @return
     */
    SummarizationToday selectTodayStatisticsByDate(Date queryDate, String merchantId);
}