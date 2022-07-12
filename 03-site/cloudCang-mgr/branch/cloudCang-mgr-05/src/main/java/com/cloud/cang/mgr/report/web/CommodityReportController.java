package com.cloud.cang.mgr.report.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.report.domain.CommodityReportDomain;
import com.cloud.cang.mgr.report.domain.EnterWarehouseReportDomain;
import com.cloud.cang.mgr.report.service.ReportService;
import com.cloud.cang.mgr.report.vo.CommodityReportVo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/commodityReport")
public class CommodityReportController {
    private static final Logger log = LoggerFactory.getLogger(CommodityReportController.class);
    @Autowired
    private ReportService reportService;

    @RequestMapping("/list")
    public String list() {
        return "report/commodity/commodityReport-list";
    }

    /**
     * 商品报表
     *
     * @param commodityReportDomain
     * @return PageListVo<List<CommodityReportVo>>
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<CommodityReportVo>> ueryCommodityReport(ParamPage paramPage, CommodityReportDomain commodityReportDomain) {
        PageListVo<List<CommodityReportVo>> pageListVo = new PageListVo<List<CommodityReportVo>>();
        Page<CommodityReportVo> page = new Page<CommodityReportVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getLimit());
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
      /*  Map<String, String> map = new HashMap();
        map.put("commodityName", commodityName);
        map.put("merchantId", merchantId);*/
    /*    if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            start = format.format(new Date(start));
            end = format.format(new Date(end));
            String condition = "  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d') <='" + end + "'";
            map.put("condition", condition);
        }*/
        commodityReportDomain.setMerchantId(merchantId);
        page = reportService.selectCommodityReporPage(page, commodityReportDomain);
        List<CommodityReportVo> commodityReportVoList = page.getResult();
        if (null != commodityReportVoList && commodityReportVoList.size() > 0) {
            for (CommodityReportVo commodityReportVo : commodityReportVoList) {
                //商品毛利润
             /*   BigDecimal commodityGrossProfit = commodityReportVo.getCommoditySalesVolume().subtract
                        (commodityReportVo.getCommoditySaleNum().multiply(commodityReportVo.getCommodityFcost()));
                commodityReportVo.setCommodityGrossProfit(commodityGrossProfit);*///商品毛利润

                //税费 BigDecimal ftax = commodityReportVo.getCommoditySalesVolume().multiply(commodityReportVo.getCommodityFtaxPoint());

                //商品净利润 commodityReportVo.setCommodityNetProfit(commodityGrossProfit.subtract(ftax).setScale(2));

                //商品毛利率 BigDecimal a = commodityGrossProfit.divide(commodityReportVo.getCommoditySalesVolume());
                //下面将结果转化成百分比
                //商品毛利率
                commodityReportVo.setCommodityNetProfit(commodityReportVo.getCommodityNetProfit().setScale(2, BigDecimal.ROUND_UP));
                String res = getResult(commodityReportVo.getCommoditySalesVolume(), commodityReportVo.getCommodityFcost());
               /* NumberFormat percent = NumberFormat.getPercentInstance();
                percent.setMaximumFractionDigits(2);
                String result = percent.format(a.doubleValue());*/
                commodityReportVo.setGrossInterestRate(res);
            }

        }
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(commodityReportVoList);
        }
        return pageListVo;
    }

    private String getResult(BigDecimal thisData, BigDecimal LastData) {
        String result = "";
        BigDecimal subResult = thisData.subtract(LastData);
        if (0 == BigDecimal.ZERO.compareTo(subResult)) {
            result = "0%";
        } else if (0 == BigDecimal.ZERO.compareTo(thisData)) {
            result = LastData.multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString() + "%";
        } /*else if (0 == BigDecimal.ZERO.compareTo(LastData)) {
            result = thisData.multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString() + "%";
            plus = 1;
        } */ else {
            BigDecimal b = subResult.divide(thisData, 2, BigDecimal.ROUND_HALF_UP);
            //下面将结果转化成百分比
            NumberFormat percent = NumberFormat.getPercentInstance();
            percent.setMaximumFractionDigits(2);
            result = percent.format(b.doubleValue());
        }
        return result;
    }

    /**
     * 商品报表列表页脚总统计
     *
     * @param commodityReportDomain
     * @return ResponseVo
     */
    @RequestMapping("/queryTotalData")
    @ResponseBody
    public ResponseVo<HashMap<String, BigDecimal>> queryTotalData(CommodityReportDomain commodityReportDomain) {
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        commodityReportDomain.setMerchantId(merchantId);
        HashMap<String, BigDecimal> map = reportService.queryCommodityReportTotalData(commodityReportDomain);
        return ResponseVo.getSuccessResponse(map);
    }

}
