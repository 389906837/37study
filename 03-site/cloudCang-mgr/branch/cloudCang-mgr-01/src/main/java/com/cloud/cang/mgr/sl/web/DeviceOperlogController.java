package com.cloud.cang.mgr.sl.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.sl.domain.OperatelogDomain;
import com.cloud.cang.mgr.sl.service.CheckLogService;
import com.cloud.cang.mgr.sl.service.DeviceOperService;
import com.cloud.cang.mgr.sl.vo.CheckLogVo;
import com.cloud.cang.mgr.sl.vo.DeviceLogVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.sl.CheckLog;
import com.cloud.cang.model.sl.DeviceOper;
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
 * 设备操作日志
 * @author ChangTanchang
 * @time:2018-01-15 20:51:00
 * @version 1.0
 */
@Controller
@RequestMapping("/deviceLog")
public class DeviceOperlogController extends GenericController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(DeviceOperlogController.class);

    // 注入serv
    @Autowired
    private DeviceOperService deviceOperService;

    @Autowired
    private OperatorService operatorService;

    /**
     * 设备操作日志
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sl/deviceLog-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<OperatelogDomain>> queryDataLog(ParamPage paramPage, DeviceLogVo deviceLogVo) {
        PageListVo<List<OperatelogDomain>> pageListVo = new PageListVo<List<OperatelogDomain>>();
        Page<OperatelogDomain> page = new Page<OperatelogDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 130);
        deviceLogVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceLogVo.setOrderStr("SDO." + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = deviceOperService.deviceOperLog(page,deviceLogVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

}
