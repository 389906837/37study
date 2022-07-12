package com.cloud.cang.mgr.report.service;

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
 * Created by yan on 2018/4/17.
 */
public interface ReportService {
    /**
     * 查询商品销售明细报表
     *
     * @param commodityReportDomain
     * @return Page<CommodityReportVo>
     */
    Page<CommodityReportVo> selectCommodityReporPage(Page<CommodityReportVo> page, CommodityReportDomain commodityReportDomain);

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
     * @param deviceSalesDetailReportDomain
     * @return Page<DeviceSalesDetailReportVo>
     */
    Page<DeviceSalesDetailReportVo> selectDeviceReportPageByDomain(Page<DeviceSalesDetailReportVo> page, DeviceSalesDetailReportDomain deviceSalesDetailReportDomain);

    /**
     * 查询设备销售明细报表页脚总合计
     *
     * @param deviceSalesDetailReportDomain
     * @return BigDecimal
     */
    BigDecimal queryDeviceReportTotalData(DeviceSalesDetailReportDomain deviceSalesDetailReportDomain);

    /**
     * 查询批次表
     *
     * @param map
     * @return Page<StockReportVo>
     */
    Page<StockReportVo> selectStockReportPageByDomain(Page<StockReportVo> page, Map<String, String> map);

    /**
     * 查询批次表页脚汇总合计
     *
     * @param map
     * @return HashMap<String,BigDecimal>
     */
    HashMap<String, BigDecimal> queryBatchReportTotalData(Map<String, String> map);

    /**
     * 入库/出库 汇总表
     *
     * @param enterWarehouseReportDomain
     * @return Page<EnterWarehouseVo>
     */
    Page<EnterWarehouseVo> selectEnterWarehouseReportPageByDomain(Page<EnterWarehouseVo> page, EnterWarehouseReportDomain enterWarehouseReportDomain);

    /**
     * 入库/出库 汇总表页脚总合计
     *
     * @param enterWarehouseReportDomain
     * @return Map
     */
    HashMap<String, BigDecimal> queryEnterWarehouseTotalData(EnterWarehouseReportDomain enterWarehouseReportDomain);

    /**
     * 资金往来汇总表
     *
     * @param exchangeOfFundsDomain
     * @return Page<ExchangeOfFundsVo>
     */
    Page<ExchangeOfFundsVo> selectExchangeOfFundsReportPageByDomain(Page<ExchangeOfFundsVo> page, ExchangeOfFundsDomain exchangeOfFundsDomain);

    /**
     * 资金往来汇总表页脚总合计
     *
     * @param exchangeOfFundsDomain
     * @return Page<ExchangeOfFundsVo>
     */
    List<ExchangeOfFundsMoneyStaVo> queryExchangeOfFundsTotalData(ExchangeOfFundsDomain exchangeOfFundsDomain);


    /**
     * 资金往来汇总明细表(收款)
     *
     * @param map
     * @return Page<ExchangeOfFundsDetailVo>
     */
    Page<ExchangeOfFundsDetailVo> selectExchangeOfFundsReportDetailPageByDomain(Page<ExchangeOfFundsDetailVo> page, Map<String, String> map);

    /**
     * 资金往来汇总明细表(付款)
     *
     * @param map
     * @return Page<ExchangeOfFundsDetailVo>
     */
    Page<ExchangeOfFundsDetailVo> selectExchangeOfFundsReportPaymentDetailPage(Page<ExchangeOfFundsDetailVo> page, Map<String, String> map);


}
