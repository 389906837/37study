package com.cloud.cang.mgr.sys.dao;

import com.cloud.cang.mgr.sys.vo.CommoditySaleRankingVo;
import com.cloud.cang.mgr.sys.vo.DeviceSaleRankingVo;
import com.cloud.cang.mgr.sys.vo.EchartsVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 首页展示 Dao
 *
 * @author yanlingfeng
 * @version 1.0
 * @time:2018-03-26
 */
public interface IndexDao {
    //近七天销售额
    BigDecimal selectSevenDSaleTotal(String merchantId);

    //已上架商品数
    Integer selectUpShelfCommodity(String merchantId);

    //已下架商品数
    Integer selectDownShelfCommodity(String merchantId);

    //库存预警数
    Integer selectCommodityStockWarn(String merchantId);

    //过期预警数
    Integer selectCommodityExpireWarn(@Param("merchantId") String merchantId, @Param("commodityDexpire") String commodityDexpire);

    //今日新增用户
    Integer selectTdAdd(String merchantId);

    //昨日新增用户
    Integer selectYdAdd(String merchantId);

    //本月新增用户
    Integer selectMAdd(String merchantId);

    //用户总数
    Integer selectMemberTotal(String merchantId);

    //待支付订单数
    Integer selectPPaymentOrderNum(String merchantId);

    //待处理退款数
    Integer selectPRefundNum(String merchantId);

    //待补货订单数
    Integer selectPReplenishmentOrderNum(String merchantId);

    //设备故障数
    Integer selectDeviceFaultNum(String merchantId);

    //商品异常数
    Integer selectCommodityErrorNum(String merchantId);

    //盘货异常数
    Integer selectStocktakingError(String merchantId);

    //商品销售Top10
    List<CommoditySaleRankingVo> selectCommoditySaleRanking(Map<String, String> map);

    //设备销售Top10
    List<DeviceSaleRankingVo> selectDeviceSaleRanking(Map<String, String> map);

    //订单统计数据
    List<EchartsVo> selectOrderStatistics(Map<String, String> map);

    //今日/昨日订单统计数据
    List<EchartsVo> selectSpecialdayOrderStatistics(Map<String, String> map);

    //本月订单总数
    BigDecimal selectThisMonthOrderNum(Map<String, String> map);

    //上月订单总数
    BigDecimal selectLastMonthOrderNum(Map<String, String> map);

    //本周订单总数
    BigDecimal selectThisWeekOrderNum(Map<String, String> map);

    //上周订单总数
    BigDecimal selectLastWeekOrderNum(Map<String, String> map);

    //会员统计数据
    List<EchartsVo> selectMemberStatistics(Map<String, String> map);

    // 今日/昨日 会员统计数据
    List<EchartsVo> selectSpecialdayMemberStatistics(Map<String, String> map);

    //本月会员总数
    BigDecimal selectThisMonthMemberNum(Map<String, String> map);

    //上月会员总数
    BigDecimal selectLastMonthMemberNum(Map<String, String> map);

    //本周会员总数
    BigDecimal selectThisWeekMemberNum(Map<String, String> map);

    //上周会员总数
    BigDecimal selectLastWeekMemberNum(Map<String, String> map);

    //销售统计数据
    List<EchartsVo> selectSalesAmountStatistics(Map<String, String> map);

    //今日/昨日 销售统计数据
    List<EchartsVo> selectSpecialdaySalesStatistics(Map<String, String> map);

    //本月销售额
    BigDecimal selectThisMonthSaleAmount(Map<String, String> map);

    //上月销售额
    BigDecimal selectLastMonthSaleAmount(Map<String, String> map);

    //本周销售额
    BigDecimal selectThisWeekSaleAmount(Map<String, String> map);

    //上周销售额
    BigDecimal selectLastWeekSaleAmount(Map<String, String> map);
}