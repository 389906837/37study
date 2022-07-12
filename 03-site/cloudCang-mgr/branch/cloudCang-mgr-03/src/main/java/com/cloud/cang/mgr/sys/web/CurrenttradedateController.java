package com.cloud.cang.mgr.sys.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.cs.service.CurrenttradedateService;
import com.cloud.cang.mgr.sys.domain.CurrenttradedateDomain;
import com.cloud.cang.model.cs.Currenttradedate;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
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

/**
 * 节假日管理
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/currenttradeDate")
public class CurrenttradedateController {
    private static final Logger log = LoggerFactory.getLogger(CurrenttradedateController.class);

    @Autowired
    CurrenttradedateService currenttradedateService;


    @RequestMapping("/list")
    public String list(ModelMap map) {
        List yearList = new ArrayList();
        for (int i = 2018; i < 2038; i++) {
            yearList.add(i);
        }
        map.put("yearList", yearList);
        List monthList = new ArrayList();
        for (int i = 1; i < 13; i++) {
            monthList.add(i);
        }
        map.put("monthList", monthList);
        return "cs/currenttradeDate-list";
    }

    /**
     * 分页列表数据
     *
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<Currenttradedate>> queryDataByCondition(CurrenttradedateDomain currenttradedateDomain,
                                                            ParamPage paramPage) {
        PageListVo<List<Currenttradedate>> pageListVo = new PageListVo<List<Currenttradedate>>();
        Page<Currenttradedate> page = new Page<Currenttradedate>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            currenttradedateDomain.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = currenttradedateService.selectPageByDomainWhere(page, currenttradedateDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    @RequestMapping("/toInitFestivalDay")
    public String toInitFestivalDay(ModelMap map) {
        List dataList = new ArrayList();
        for (int i = 2018; i < 2038; i++) {
            dataList.add(i);
        }
        map.put("dataList", dataList);
        return "cs/currenttradeDate-edit";
    }

    /**
     * @param year
     * @return
     */
    @RequestMapping("/initFestivalDay")
    public @ResponseBody
    ResponseVo<String> initFestivalDay(String year) {
        try {
            currenttradedateService.initFestivalDay(year);
            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.init.curr.date",null)+"{0}", year);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("初始化节假日管理异常：{}", e);
            return ErrorCodeEnum.ERROR_PAC_CHANGE_INTEGRAL.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.init.curr.date.error",null));
        }
    }

    @RequestMapping("/setWork")
    public @ResponseBody
    ResponseVo<String> setWork(String checkboxId) {
        currenttradedateService.setWork(checkboxId);
        return ResponseVo.getSuccessResponse();
    }


    @RequestMapping("/setRest")
    public @ResponseBody
    ResponseVo<String> setRest(String checkboxId) {
        currenttradedateService.setRest(checkboxId);
        return ResponseVo.getSuccessResponse();
    }

}
