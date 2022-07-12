package com.cloud.cang.mgr.report.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.report.domain.EnterWarehouseReportDomain;
import com.cloud.cang.mgr.report.service.ReportService;
import com.cloud.cang.mgr.report.vo.EnterWarehouseVo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


/**
 * 出库汇总报表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/getOutWarehouseReport")
public class GetOutWarehouseReportController {
    private static final Logger log = LoggerFactory.getLogger(GetOutWarehouseReportController.class);
    @Autowired
    private ReportService reportService;

    @RequestMapping("/list")
    public String list() {
        return "report/getOutWarehouse/getOutWarehouseReport-list";
    }

    /**
     * 出库汇总报表
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<EnterWarehouseVo>> queryCommodityReport(ParamPage paramPage, EnterWarehouseReportDomain enterWarehouseReportDomain) {
        PageListVo<List<EnterWarehouseVo>> pageListVo = new PageListVo<List<EnterWarehouseVo>>();
        Page<EnterWarehouseVo> page = new Page<EnterWarehouseVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getLimit());
        enterWarehouseReportDomain.setType(20);
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        enterWarehouseReportDomain.setMerchantId(merchantId);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            enterWarehouseReportDomain.setOrderStr("RRC." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = reportService.selectEnterWarehouseReportPageByDomain(page, enterWarehouseReportDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 出库汇总列表页脚总统计
     *
     * @param enterWarehouseReportDomain
     * @return ResponseVo
     */
    @RequestMapping("/queryTotalData")
    @ResponseBody
    public ResponseVo<HashMap<String, BigDecimal>> queryTotalData(EnterWarehouseReportDomain enterWarehouseReportDomain) {
        enterWarehouseReportDomain.setType(20);
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        enterWarehouseReportDomain.setMerchantId(merchantId);
        HashMap<String, BigDecimal> map = reportService.queryEnterWarehouseTotalData(enterWarehouseReportDomain);
        return ResponseVo.getSuccessResponse(map);
    }
}
