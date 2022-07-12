package com.cloud.cang.mgr.sl.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.sl.domain.LoginDomain;
import com.cloud.cang.mgr.sl.service.LoginLogService;
import com.cloud.cang.mgr.sl.vo.LoginLogVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.sl.LoginLog;
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
 * 登录日志
 * @author ChangTanchang
 * @time:2018-01-15 20:51:00
 * @version 1.0
 */
@Controller
@RequestMapping("/loginLog")
public class LoginlogController extends GenericController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(LoginlogController.class);

    // 注入serv
    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private PurviewService purviewService; // 权限码表

    @Autowired
    private OperatorService operatorService;

    /**
     * 登录日志
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sl/loginLog-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<LoginDomain>> queryDataLog(ParamPage paramPage, LoginLogVo loginLogVo) {
        PageListVo<List<LoginDomain>> pageListVo = new PageListVo<List<LoginDomain>>();
        Page<LoginDomain> page = new Page<LoginDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 130);
        loginLogVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            loginLogVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = loginLogService.queryLoginLog(page,loginLogVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

}
