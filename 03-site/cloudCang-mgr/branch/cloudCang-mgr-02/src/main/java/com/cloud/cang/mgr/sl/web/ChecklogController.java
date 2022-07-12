package com.cloud.cang.mgr.sl.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.sl.service.CheckLogService;
import com.cloud.cang.mgr.sl.vo.CheckLogVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sl.CheckLog;
import com.cloud.cang.model.sys.Operator;
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
 * 对账日志
 * @author ChangTanchang
 * @time:2018-01-15 20:51:00
 * @version 1.0
 */
@Controller
@RequestMapping("/checkLog")
public class ChecklogController extends GenericController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(ChecklogController.class);

    // 注入serv
    @Autowired
    private CheckLogService checkLogService;

    @Autowired
    private OperatorService operatorService;

    /**
     * 对账日志
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sl/checkLog-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<CheckLog>> queryDataLog(ParamPage paramPage, CheckLogVo checkLogVo) {
        PageListVo<List<CheckLog>> pageListVo = new PageListVo<List<CheckLog>>();
        Page<CheckLog> page = new Page<CheckLog>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 160);
        checkLogVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            checkLogVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = checkLogService.checkLog(page,checkLogVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

}
