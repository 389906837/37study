package com.cloud.cang.mgr.report.service.impl;

import com.cloud.cang.mgr.report.dao.ReportDao;
import com.cloud.cang.mgr.report.domain.CommodityReportDomain;
import com.cloud.cang.mgr.report.domain.DeviceSalesDetailReportDomain;
import com.cloud.cang.mgr.report.domain.EnterWarehouseReportDomain;
import com.cloud.cang.mgr.report.domain.ExchangeOfFundsDomain;
import com.cloud.cang.mgr.report.service.ReportService;
import com.cloud.cang.mgr.report.vo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yan on 2018/4/17.
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;

    /**
     * 查询商品分析报表
     *
     * @param commodityReportDomain
     * @return Page<CommodityReportVo>
     */
    @Override
    public Page<CommodityReportVo> selectCommodityReporPage(Page<CommodityReportVo> page, CommodityReportDomain commodityReportDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        if (StringUtils.isNotBlank(commodityReportDomain.getCommodityName())) {
            char[] chars = commodityReportDomain.getCommodityName().toCharArray();
            commodityReportDomain.setCommodityName(StringUtils.join(chars, '%'));
        }
        return reportDao.selectCommodityReporPage(commodityReportDomain);

    }

    /**
     * 查询商品销售明细报表页脚总计
     *
     * @param commodityReportDomain
     * @return Page<CommodityReportVo>
     */
    @Override
    public HashMap<String, BigDecimal> queryCommodityReportTotalData(CommodityReportDomain commodityReportDomain) {
        if (StringUtils.isNotBlank(commodityReportDomain.getCommodityName())) {
            char[] chars = commodityReportDomain.getCommodityName().toCharArray();
            commodityReportDomain.setCommodityName(StringUtils.join(chars, '%'));
        }
        return reportDao.queryCommodityReportTotalData(commodityReportDomain);
    }

    /**
     * 查询设备销售报表
     *
     * @param deviceSalesDetailReportDomain
     * @return Page<DeviceSalesDetailReportVo>
     */
    @Override
    public Page<DeviceSalesDetailReportVo> selectDeviceReportPageByDomain(Page<DeviceSalesDetailReportVo> page, DeviceSalesDetailReportDomain deviceSalesDetailReportDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
       /* String start = deviceSalesDetailReportDomain.getStart();
        String end = deviceSalesDetailReportDomain.getEnd();
        if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            start = format.format(new Date(start));
            end = format.format(new Date(end));
            // long timeDifference = 0;
            long d1 = format.parse(start).getTime();
            long d2 = format.parse(end).getTime();
            //时间差
            long timeDifference = (d1 - d2) / (1000 * 60 * 60 * 24);

            String condition = "  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d') <='" + end + "'";
            deviceSalesDetailReportDomain.setCondition(condition);
            Calendar calendar = Calendar.getInstance();// 得到一个Calendar的实例
            calendar.setTime(format.parse(start));
            calendar.add(Calendar.YEAR, -1);
            Date date = calendar.getTime();
            start = format.format(date);
            calendar.setTime(format.parse(end));
            calendar.add(Calendar.YEAR, -1);
            date = calendar.getTime();
            end = format.format(date);
            String lastCondition = "  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d') <='" + end + "'";
            deviceSalesDetailReportDomain.setLastCondition(lastCondition);
            //end
            calendar.setTime(format.parse(start));
            calendar.add(Calendar.YEAR, 1);
            calendar.add(Calendar.DATE, -1);
            date = calendar.getTime();
            end = format.format(date);
            //start
            calendar.setTime(format.parse(end));
            calendar.add(Calendar.YEAR, 1);
            calendar.add(Calendar.DATE, (int) timeDifference);
            date = calendar.getTime();
            start = format.format(date);
            String lastTimeCondition = "  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d') <='" + end + "'";
            deviceSalesDetailReportDomain.setLastTimeCondition(lastTimeCondition);
        }*/
        return reportDao.selectDeviceReportPageByDomain(deviceSalesDetailReportDomain);
    }

    /**
     * 查询设备销售明细报表页脚总合计
     *
     * @param deviceSalesDetailReportDomain
     * @return BigDecimal
     */
    @Override
    public BigDecimal queryDeviceReportTotalData(DeviceSalesDetailReportDomain deviceSalesDetailReportDomain) {
        return reportDao.queryDeviceReportTotalData(deviceSalesDetailReportDomain);
    }

    /**
     * 查询批次明细表
     *
     * @param
     * @return
     */
    @Override
    public Page<StockReportVo> selectStockReportPageByDomain(Page<StockReportVo> page, Map<String, String> map) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        if (StringUtils.isNotBlank(map.get("commodityName"))) {
            char[] chars = map.get("commodityName").toCharArray();
            map.put("commodityName", (StringUtils.join(chars, '%')));
        }
        return reportDao.selectStockReportPageByDomain(map);
    }

    /**
     * 查询批次表页脚汇总合计
     *
     * @param map
     * @return HashMap<String,BigDecimal>
     */
    @Override
    public HashMap<String, BigDecimal> queryBatchReportTotalData(Map<String, String> map) {
        if (StringUtils.isNotBlank(map.get("commodityName"))) {
            char[] chars = map.get("commodityName").toCharArray();
            map.put("commodityName", (StringUtils.join(chars, '%')));
        }
        return reportDao.queryBatchReportTotalData(map);
    }

    /**
     * 入库/出库 汇总表
     *
     * @param
     * @return
     */
    @Override
    public Page<EnterWarehouseVo> selectEnterWarehouseReportPageByDomain(Page<EnterWarehouseVo> page, EnterWarehouseReportDomain enterWarehouseReportDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        if (StringUtils.isNotBlank(enterWarehouseReportDomain.getCommodityName())) {
            char[] chars = enterWarehouseReportDomain.getCommodityName().toCharArray();
            enterWarehouseReportDomain.setCommodityName(StringUtils.join(chars, '%'));
        }
        return reportDao.selectEnterWarehouseReportPageByDomain(enterWarehouseReportDomain);
    }

    /**
     * 入库/出库 汇总表页脚总合计
     *
     * @param enterWarehouseReportDomain
     * @return Map
     */
    @Override
    public HashMap<String, BigDecimal> queryEnterWarehouseTotalData(EnterWarehouseReportDomain enterWarehouseReportDomain) {
        if (StringUtils.isNotBlank(enterWarehouseReportDomain.getCommodityName())) {
            char[] chars = enterWarehouseReportDomain.getCommodityName().toCharArray();
            enterWarehouseReportDomain.setCommodityName(StringUtils.join(chars, '%'));
        }
        return reportDao.queryEnterWarehouseTotalData(enterWarehouseReportDomain);
    }

    /**
     * 资金往来汇总表
     *
     * @param
     * @return
     */
    @Override
    public Page<ExchangeOfFundsVo> selectExchangeOfFundsReportPageByDomain(Page<ExchangeOfFundsVo> page, ExchangeOfFundsDomain exchangeOfFundsDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return reportDao.selectExchangeOfFundsReportPageByDomain(exchangeOfFundsDomain);
    }

    /**
     * 资金往来汇总表页脚总合计
     *
     * @param exchangeOfFundsDomain
     * @return Page<ExchangeOfFundsVo>
     */
    @Override
    public List<ExchangeOfFundsMoneyStaVo> queryExchangeOfFundsTotalData(ExchangeOfFundsDomain exchangeOfFundsDomain) {
        return reportDao.queryExchangeOfFundsTotalData(exchangeOfFundsDomain);
    }


    /**
     * 资金往来汇总明细表(收款)
     *
     * @param
     * @return
     */
    @Override
    public Page<ExchangeOfFundsDetailVo> selectExchangeOfFundsReportDetailPageByDomain(Page<ExchangeOfFundsDetailVo> page, Map<String, String> map) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        if (StringUtils.isNotBlank(map.get("commodityName"))) {
            char[] chars = map.get("commodityName").toCharArray();
            map.put("commodityName", (StringUtils.join(chars, '%')));
        }
        return reportDao.selectExchangeOfFundsReportDetailPageByDomain(map);
    }

    /**
     * 资金往来汇总明细表(付款)
     *
     * @param
     * @return
     */
    @Override
    public Page<ExchangeOfFundsDetailVo> selectExchangeOfFundsReportPaymentDetailPage(Page<ExchangeOfFundsDetailVo> page, Map<String, String> map) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        if (StringUtils.isNotBlank(map.get("commodityName"))) {
            char[] chars = map.get("commodityName").toCharArray();
            map.put("commodityName", (StringUtils.join(chars, '%')));
        }
        return reportDao.selectExchangeOfFundsReportPaymentDetailPage(map);
    }
}
