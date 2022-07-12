package com.cloud.cang.mgr.report.dao;

import com.cloud.cang.mgr.report.domain.CommodityReportDomain;
import com.cloud.cang.mgr.report.domain.DeviceSalesDetailReportDomain;
import com.cloud.cang.mgr.report.domain.EnterWarehouseReportDomain;
import com.cloud.cang.mgr.report.domain.ExchangeOfFundsDomain;
import com.cloud.cang.mgr.report.vo.*;
import com.github.pagehelper.Page;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表 Dao
 *
 * @author yanlingfeng
 * @version 1.0
 * @time:2018-04-17
 */
public interface ReportDao {
    /**
     * 商品报表
     *
     * @param commodityReportDomain
     * @return Page<CommodityReportVo>
     */
    Page<CommodityReportVo> selectCommodityReporPage(CommodityReportDomain commodityReportDomain);

    /**
     * 查询商品销售明细报表页脚总计
     *
     * @param commodityReportDomain
     * @return Page<CommodityReportVo>
     */
    HashMap<String, BigDecimal> queryCommodityReportTotalData(CommodityReportDomain commodityReportDomain);

    /**
     * 查询设备销售明细报表
     *
     * @param deviceSalesDetailReportDomain 设备销售明细Domain
     * @return Page<DeviceSalesDetailReportVo>
     */
    Page<DeviceSalesDetailReportVo> selectDeviceReportPageByDomain(DeviceSalesDetailReportDomain deviceSalesDetailReportDomain);

    /**
     * 查询设备销售明细报表页脚总合计
     *
     * @param deviceSalesDetailReportDomain
     * @return BigDecimal
     */
    BigDecimal queryDeviceReportTotalData(DeviceSalesDetailReportDomain deviceSalesDetailReportDomain);


    /**
     * 查询批次报表
     *
     * @param map{ : } 商品名 设备名
     * @return Page<StockReportVo>
     */
    Page<StockReportVo> selectStockReportPageByDomain(Map<String, String> map);

    /**
     * 查询批次表页脚汇总合计
     *
     * @param map
     * @return HashMap<String,BigDecimal>
     */
    HashMap<String, BigDecimal> queryBatchReportTotalData(Map<String, String> map);

    /**
     * 入库汇总报表
     *
     * @param enterWarehouseReportDomain
     * @return Page<StockReportVo>
     */
    Page<EnterWarehouseVo> selectEnterWarehouseReportPageByDomain(EnterWarehouseReportDomain enterWarehouseReportDomain);

    /**
     * 入库汇总表页脚总合计
     *
     * @param enterWarehouseReportDomain
     * @return Map
     */
    HashMap<String, BigDecimal> queryEnterWarehouseTotalData(EnterWarehouseReportDomain enterWarehouseReportDomain);

    /**
     * 资金往来报表
     *
     * @param
     * @return Page<StockReportVo>
     */
    Page<ExchangeOfFundsVo> selectExchangeOfFundsReportPageByDomain(ExchangeOfFundsDomain exchangeOfFundsDomain);

    /**
     * 资金往来汇总表页脚总合计
     *
     * @param exchangeOfFundsDomain
     * @return Page<ExchangeOfFundsVo>
     */
    List<ExchangeOfFundsMoneyStaVo> queryExchangeOfFundsTotalData(ExchangeOfFundsDomain exchangeOfFundsDomain);

    /**
     * 资金往来明细报表(收款)
     *
     * @param
     * @return Page<StockReportVo>
     */
    Page<ExchangeOfFundsDetailVo> selectExchangeOfFundsReportDetailPageByDomain(Map<String, String> map);

    /**
     * 资金往来明细报表(付款)
     *
     * @param
     * @return Page<StockReportVo>
     */
    Page<ExchangeOfFundsDetailVo> selectExchangeOfFundsReportPaymentDetailPage(Map<String, String> map);
}
