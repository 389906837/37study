package com.cloud.cang.mgr.report.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.report.domain.CommodityReportDomain;
import com.cloud.cang.mgr.report.domain.ExchangeOfFundsDomain;
import com.cloud.cang.mgr.report.service.ReportService;
import com.cloud.cang.mgr.report.vo.ExchangeOfFundsDetailVo;
import com.cloud.cang.mgr.report.vo.ExchangeOfFundsMoneyStaVo;
import com.cloud.cang.mgr.report.vo.ExchangeOfFundsVo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 资金往来报表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/exchangeOfFundsReport")
public class ExchangeOfFundsReportController {
    private static final Logger log = LoggerFactory.getLogger(ExchangeOfFundsReportController.class);
    @Autowired
    private ReportService reportService;

    @RequestMapping("/list")
    public String list() {
        return "report/exchangeOfFunds/exchangeOfFundsReport-list";
    }

    /**
     * 资金往来报表
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<ExchangeOfFundsVo>> queryCommodityReport(ParamPage paramPage, ExchangeOfFundsDomain exchangeOfFundsDomain) {
        PageListVo<List<ExchangeOfFundsVo>> pageListVo = new PageListVo<List<ExchangeOfFundsVo>>();
        Page<ExchangeOfFundsVo> page = new Page<ExchangeOfFundsVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getLimit());
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        exchangeOfFundsDomain.setMerchantId(merchantId);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            exchangeOfFundsDomain.setOrderStr("A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = reportService.selectExchangeOfFundsReportPageByDomain(page, exchangeOfFundsDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 资金往来报表详情
     */
    @RequestMapping("/detailList")
    public String detailList(Integer type, String dateResult, ModelMap map) throws ParseException {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            dateResult = format.format(new Date(Long.valueOf(dateResult)));
            map.put("type", type);
            map.put("dateResult", dateResult);
            return "report/exchangeOfFunds/exchangeOfFundsReportDetail";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 资金往来报表
     */
    @RequestMapping("/queryDataDetail")
    public @ResponseBody
    PageListVo<List<ExchangeOfFundsDetailVo>> queryDataDetail(ParamPage paramPage, Integer type, String dateResult, String commodityName) {
        PageListVo<List<ExchangeOfFundsDetailVo>> pageListVo = new PageListVo<List<ExchangeOfFundsDetailVo>>();
        Page<ExchangeOfFundsDetailVo> page = new Page<ExchangeOfFundsDetailVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getLimit());
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        Map<String, String> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("dateResult", dateResult);
        map.put("commodityName", commodityName);
        //1.查询收款明细
        //2.查询付款明细
        if (1 == type) {
            page = reportService.selectExchangeOfFundsReportDetailPageByDomain(page, map);
        } else if (2 == type) {
            page = reportService.selectExchangeOfFundsReportPaymentDetailPage(page, map);
        }
      /*  if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            exchangeOfFundsDomain.setOrderStr("A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }*/
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 商品报表列表页脚总统计
     *
     * @param exchangeOfFundsDomain
     * @return ResponseVo
     */
    @RequestMapping("/queryTotalData")
    @ResponseBody
    public ResponseVo< List<ExchangeOfFundsMoneyStaVo>> queryTotalData(ExchangeOfFundsDomain exchangeOfFundsDomain) {
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        exchangeOfFundsDomain.setMerchantId(merchantId);
        List<ExchangeOfFundsMoneyStaVo> exchangeOfFundsMoneyStaVo = reportService.queryExchangeOfFundsTotalData(exchangeOfFundsDomain);
        return ResponseVo.getSuccessResponse(exchangeOfFundsMoneyStaVo);
    }
}
