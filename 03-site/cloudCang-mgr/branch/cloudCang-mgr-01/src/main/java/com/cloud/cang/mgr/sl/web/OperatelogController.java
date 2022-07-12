package com.cloud.cang.mgr.sl.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.sl.service.OperLogService;
import com.cloud.cang.mgr.sl.vo.VistLogVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sl.OperLog;
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
 * 系统操作日志
 * @author ChangTanchang
 * @time:2018-01-15 20:51:00
 * @version 1.0
 */
@Controller
@RequestMapping("/operateLog")
public class OperatelogController extends GenericController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(OperatelogController.class);

    // 注入serv
    @Autowired
    private OperLogService operLogService;

    @Autowired
    private OperatorService operatorService;

    /**
     * 后台操作日志
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sl/operateLog-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<OperLog>> queryDataLog(ParamPage paramPage, VistLogVo vistLogVo) {
        PageListVo<List<OperLog>> pageListVo = new PageListVo<List<OperLog>>();

        Page<OperLog> page = new Page<OperLog>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 130);
        vistLogVo.setCondition(sql);
        vistLogVo.setSsourceSystem("cloudCang");
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            vistLogVo.setOrderStr("SOL." + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = operLogService.queryOperLog(page,vistLogVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }



}
