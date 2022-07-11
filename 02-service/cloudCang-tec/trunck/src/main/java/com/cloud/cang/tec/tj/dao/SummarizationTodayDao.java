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
import com.cloud.cang.model.tj.SummarizationToday;

/** 供应商每日数据统计(TJ_SUMMARIZATION_TODAY) **/
public interface SummarizationTodayDao extends GenericDao<SummarizationToday, String> {
    /**
     * 查询商户每日数据统计
     * @param map 查询日期 商户ID
     * @return
     */
    SummarizationToday selectSummarizationTodayByDate(Map<String, Object> map);
    /**
     * 查询当前日期的平台数据 当天
     * @param map 查询日期 商户ID
     * @return
     */
    SummarizationToday selectTodayStatisticsByDate(Map<String, Object> map);

}