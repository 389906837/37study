package com.cloud.cang.mgr.tec.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.tec.service.ScheduleLogService;
import com.cloud.cang.mgr.tec.vo.ScheduleLogVo;
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
 * 定时任务执行日志
 * @author ChangTanchang
 * @time:2018-01-15 20:51:00
 * @version 1.0
 */
@Controller
@RequestMapping("/scheduleLog")
public class SchedulelogController extends GenericController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(SchedulelogController.class);

    // 注入serv
    @Autowired
    private ScheduleLogService scheduleLogService;

    @Autowired
    private OperatorService operatorService;

    /**
     * 定时任务执行日志
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "tec/scheduleLog-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<ScheduleLog>> queryDataLog(ParamPage paramPage, ScheduleLogVo scheduleLogVo) {
        PageListVo<List<ScheduleLog>> pageListVo = new PageListVo<List<ScheduleLog>>();
        Page<ScheduleLog> page = new Page<ScheduleLog>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 160);
        scheduleLogVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            scheduleLogVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = scheduleLogService.scheduleLog(page,scheduleLogVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }



}
