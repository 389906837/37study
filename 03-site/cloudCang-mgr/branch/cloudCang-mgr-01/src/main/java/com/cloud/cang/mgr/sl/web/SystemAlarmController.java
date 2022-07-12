package com.cloud.cang.mgr.sl.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.sl.domain.SystemAlarmDomain;
import com.cloud.cang.mgr.sl.service.SystemAlarmService;
import com.cloud.cang.mgr.sl.vo.DeviceLogVo;
import com.cloud.cang.mgr.sl.vo.SystemAlarmVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
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
 * 系统警报日志
 * @author ChangTanchang
 * @time:2018-06-15 09:24:00
 * @version 1.0
 */
@Controller
@RequestMapping("/systemAlarm")
public class SystemAlarmController extends GenericController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(SystemAlarmController.class);

    // 注入serv
    @Autowired
    private SystemAlarmService systemAlarmService;

    @Autowired
    private OperatorService operatorService;

    /**
     * 系统警报日志
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sl/systemAlarm-list";
    }

    /**
     * 分页查询系统警报日志
     * @param paramPage
     * @param systemAlarmVo
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<SystemAlarmDomain>> querySystemAlarm(ParamPage paramPage, SystemAlarmVo systemAlarmVo) {
        PageListVo<List<SystemAlarmDomain>> pageListVo = new PageListVo<List<SystemAlarmDomain>>();
        Page<SystemAlarmDomain> page = new Page<SystemAlarmDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 130);
        systemAlarmVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            systemAlarmVo.setOrderStr("SSA." + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = systemAlarmService.selectSystemAlarm(page,systemAlarmVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

}
