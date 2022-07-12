package com.cloud.cang.mgr.report.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.report.domain.ExchangeOfFundsDomain;
import com.cloud.cang.mgr.report.service.ReportService;
import com.cloud.cang.mgr.report.vo.ExchangeOfFundsMoneyStaVo;
import com.cloud.cang.mgr.report.vo.StockReportVo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * 批次报表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/batchReport")
public class BatchReportController {
    private static final Logger log = LoggerFactory.getLogger(BatchReportController.class);
    @Autowired
    private ReportService reportService;

    @RequestMapping("/list")
    public String list() {
        return "report/batch/batchReport-list";
    }

    /**
     * 批次报表
     *
     * @param commodityName 商品名
     * @param deviceName    设备名
     * @return PageListVo<List<StockReportVo>>
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<StockReportVo>> queryCommodityReport(ParamPage paramPage, String commodityName, String deviceName) {
        PageListVo<List<StockReportVo>> pageListVo = new PageListVo<List<StockReportVo>>();
        Page<StockReportVo> page = new Page<StockReportVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getLimit());
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        Map<String, String> map = new HashMap();
        map.put("merchantId", merchantId);
        map.put("commodityName", commodityName);
        map.put("deviceName", deviceName);
        page = reportService.selectStockReportPageByDomain(page, map);
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
     * @param commodityName
     * @param deviceName
     * @return ResponseVo
     */
    @RequestMapping("/queryTotalData")
    @ResponseBody
    public ResponseVo<HashMap<String,BigDecimal>> queryTotalData(String commodityName, String deviceName) {
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        Map<String, String> map = new HashMap();
        map.put("merchantId", merchantId);
        map.put("commodityName", commodityName);
        map.put("deviceName", deviceName);
        HashMap<String,BigDecimal> map1 = reportService.queryBatchReportTotalData(map);
        return ResponseVo.getSuccessResponse(map1);
    }

}
