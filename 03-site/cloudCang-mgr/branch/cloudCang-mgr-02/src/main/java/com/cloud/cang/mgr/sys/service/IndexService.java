package com.cloud.cang.mgr.sys.service;


import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.sys.vo.CommoditySaleRankingVo;
import com.cloud.cang.mgr.sys.vo.DeviceSaleRankingVo;
import com.cloud.cang.mgr.sys.vo.EchartsVo;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IndexService {


    Map<String, Object> getMainData(HttpServletRequest request);

    /**
     * 商品销售排行TOP
     *
     * @param condition 搜索条件
     */
    ResponseVo<List<CommoditySaleRankingVo>> selectCommoditySaleRanking(String condition, String start, String end, Boolean isSearch);

    /**
     * 设备销售排行TOP
     * @param condition 搜索条件
     * @param start     搜索开始时间
     * @param end       搜索结束时间
     */
    ResponseVo<List<DeviceSaleRankingVo>> selectDeviceSaleRanking(String condition, String start, String end, Boolean isSearch);

    /**
     * 订单统计
     *
     * @param condition 搜索条件
     */
    List<EchartsVo> selectOrderStatistics(String condition, String start, String end, Boolean isSearch);

    /**
     * 销售统计
     * @param condition 搜索条件
     * @param start     搜索开始时间
     * @param end       搜索结束时间
     */
    List<EchartsVo> selectSalesAmountStatistics(String condition, String start, String end, Boolean isSearch);

    /**
     * 会员统计
     * @param condition 搜索条件
     * @param start     搜索开始时间
     * @param end       搜索结束时间
     */
    List<EchartsVo> selectMemberStatistics(String condition, String start, String end, Boolean isSearch);

}