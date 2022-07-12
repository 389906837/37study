package com.cloud.cang.mgr.hy.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.hy.domain.FundAccountDomain;
import com.cloud.cang.mgr.hy.service.FundAccountService;
import com.cloud.cang.mgr.hy.vo.FundAccountVo;
import com.cloud.cang.mgr.hy.vo.MbrPurviewVo;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.hy.FundAccount;
import com.cloud.cang.model.hy.MbrPurview;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description 会员资金账户
 * @author ChangTanchang
 * @time 2018-02-07 14:31:00
 * @fileName MbrRoleController.java
 * @version 1.0
 */
@Controller
@RequestMapping("/fundaccount")
public class FundAccountController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(FundAccountController.class);

    // 注入serv类
    @Autowired
    private FundAccountService fundAccountService;

    @Autowired
    private PurviewService purviewService; // 权限码表
    
    @RequestMapping("/list")
    public String list() {
        return "hy/fundaccount-list";
    }

    /**
     * 会员资金账户列表数据
     * @param fundAccountVo
     * @param paramPage
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<FundAccountDomain>> queryDataPurview(ParamPage paramPage, FundAccountVo fundAccountVo) {
        PageListVo<List<FundAccountDomain>> pageListVo = new PageListVo<List<FundAccountDomain>>();
        Page<FundAccountDomain> page = new Page<FundAccountDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            fundAccountVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = fundAccountService.selectAccountAll(page, fundAccountVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

}
