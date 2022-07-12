package com.cloud.cang.mgr.cr.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.cr.domain.ServerModelDomain;
import com.cloud.cang.mgr.cr.service.ServerModelService;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.security.utils.SessionUserUtils;
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

import java.util.List;

/**
 * 识别服务模型管理Controller
 *
 * @author yanlingfeng
 * @version 1.0
 * @time:2018-09-20 13:29:00
 */
@Controller
@RequestMapping("/serverModel")
public class ServerModelController {
    private static final Logger log = LoggerFactory.getLogger(ServerModelController.class);

    @Autowired
    private ServerModelService serverModelService;

    @RequestMapping("/selectServerModel")
    public String selectServerModel(ModelMap modelMap, String smerchantCode,String smerchantId) {
        modelMap.put("smerchantCode", smerchantCode);
        modelMap.put("smerchantId", smerchantId);
        return "cr/serverModel/serverModel-selectServerModel";
    }

    /**
     * 查询列表
     *
     * @param paramPage
     * @param serverModelDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<ServerModel>> queryDataLog(ParamPage paramPage, ServerModelDomain serverModelDomain, String selectServerModel, String smerchantCode,String smerchantId) {
        PageListVo<List<ServerModel>> pageListVo = new PageListVo<List<ServerModel>>();
        Page<ServerModel> page = new Page<ServerModel>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        serverModelDomain.setIdelFlag(0);
        if (StringUtils.isBlank(smerchantCode)) {
            smerchantCode = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
        }
        serverModelDomain.setMerchantCode(smerchantCode);
        serverModelDomain.setMerchantId(smerchantId);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            serverModelDomain.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        if (StringUtils.isNotBlank(selectServerModel) && "true".equals(selectServerModel)) {
            serverModelDomain.setIstatus(20);
        }
        page = serverModelService.selectPageByDomainWhere(page, serverModelDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }
}
