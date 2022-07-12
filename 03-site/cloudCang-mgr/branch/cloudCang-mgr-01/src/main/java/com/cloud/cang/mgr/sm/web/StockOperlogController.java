package com.cloud.cang.mgr.sm.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.sm.domain.StockOperRecordDomain;
import com.cloud.cang.mgr.sm.service.StockOperRecordService;
import com.cloud.cang.mgr.sm.vo.StockOperLogVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.tec.vo.ScheduleLogVo;
import com.cloud.cang.model.sm.StockOperRecord;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.tec.ScheduleLog;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 库存操作日志
 * @author ChangTanchang
 * @time:2018-01-15 20:51:00
 * @version 1.0
 */
@Controller
@RequestMapping("/stockoperLog")
public class StockOperlogController extends GenericController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(StockOperlogController.class);

    // 注入serv
    @Autowired
    private StockOperRecordService stockOperRecordService;

    @Autowired
    private OperatorService operatorService;

    /**
     * 库存操作日志
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sm/stockoperLog-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<StockOperRecordDomain>> queryDataLog(ParamPage paramPage, StockOperLogVo stockOperLogVo) {
        PageListVo<List<StockOperRecordDomain>> pageListVo = new PageListVo<List<StockOperRecordDomain>>();
        Page<StockOperRecordDomain> page = new Page<StockOperRecordDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 130);
        stockOperLogVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            stockOperLogVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = stockOperRecordService.stockoperLog(page,stockOperLogVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }



}
