package com.cloud.cang.mgr.hy.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.hy.domain.ThirdAuthoriseInfoDomain;
import com.cloud.cang.mgr.hy.service.ThirdAuthoriseService;
import com.cloud.cang.mgr.hy.vo.ThirdAuthoriseInfoVo;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.hy.ThirdAuthorise;
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
 * @description 会员第三方授权信息查询
 * @author ChangTanchang
 * @time 2018-2-08 19:05:32
 * @fileName MemberThreeInfoController.java
 * @version 1.0
 */
@Controller
@RequestMapping("/thirdauthoriseinfo")
public class ThirdAuthoriseInfoController {

    // 本类日志
    private static final Logger logger = LoggerFactory.getLogger(ThirdAuthoriseInfoController.class);

    // 注入serv
    @Autowired
    private ThirdAuthoriseService thirdAuthoriseService;

    @Autowired
    private PurviewService purviewService; // 权限码表

    @RequestMapping("/list")
    public String list() {
        return "hy/thirdauthoriseinfo-list";
    }

    /**
     * 查询会员第三方授权信息表
     * @param paramPage
     * @param thirdAuthoriseInfoVo
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<ThirdAuthoriseInfoDomain>> queryDataThirdAuthorise(ParamPage paramPage, ThirdAuthoriseInfoVo thirdAuthoriseInfoVo){
        PageListVo<List<ThirdAuthoriseInfoDomain>> pageListVo = new PageListVo<List<ThirdAuthoriseInfoDomain>>();
        Page<ThirdAuthoriseInfoDomain> page = new Page<ThirdAuthoriseInfoDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            thirdAuthoriseInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = thirdAuthoriseService.selectThirdAuthorise(page,thirdAuthoriseInfoVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

}
