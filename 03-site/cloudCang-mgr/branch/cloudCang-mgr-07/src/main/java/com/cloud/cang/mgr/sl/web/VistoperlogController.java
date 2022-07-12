package com.cloud.cang.mgr.sl.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.sl.service.VistLogService;
import com.cloud.cang.mgr.sl.vo.VistLogVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sl.VistLog;
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
 * 系统访问日志
 * @author ChangTanchang
 * @time:2018-01-15 20:51:00
 * @version 1.0
 */
@Controller
@RequestMapping("/vistLog")
public class VistoperlogController extends GenericController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(VistoperlogController.class);

    // 注入serv
    @Autowired
    private VistLogService vistLogService;

    @Autowired
    private OperatorService operatorService;

    /**
     * 后台访问日志
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sl/vistLog-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<VistLog>> queryDataLog(ParamPage paramPage, VistLogVo vistLogVo) {
        PageListVo<List<VistLog>> pageListVo = new PageListVo<List<VistLog>>();
        Page<VistLog> page = new Page<VistLog>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 160);
        vistLogVo.setCondition(sql);
        vistLogVo.setSsourceSystem("cloudCang");
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            vistLogVo.setOrderStr("SVL." + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = vistLogService.queryVistLog(page,vistLogVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }
}
