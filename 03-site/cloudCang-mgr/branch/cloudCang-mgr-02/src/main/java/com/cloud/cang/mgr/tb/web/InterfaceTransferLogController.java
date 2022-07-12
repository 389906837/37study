package com.cloud.cang.mgr.tb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.tb.domain.InterfaceTransferLogDomain;
import com.cloud.cang.mgr.tb.service.InterfaceTransferLogService;
import com.cloud.cang.mgr.tb.vo.InterfaceTransferLogVo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.tb.InterfaceTransferLog;
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

import java.text.MessageFormat;
import java.util.List;


/**
 * @version 1.0
 * @ClassName InterfaceTransferLogController
 * @Description 第三方接口调用记录
 * @Author zengzexiong
 * @Date 2018年1月24日15:10:20
 */
@Controller
@RequestMapping("/tbInterfaceTransferLog")
public class InterfaceTransferLogController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(OperateDeviceRecordController.class);

    @Autowired
    OperatorService operatorService;    // 查询条件

    @Autowired
    InterfaceTransferLogService interfaceTransferLogService;    // 第三方接口调用记录

    /**
     * 第三方订单数据列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list(String method, ModelMap map) {
        if (StringUtil.isNotBlank(method)) {
            map.put("method", method);
        }
        return "tb/interfaceTransferLog/thirdInterfaceTransferLog-list";
    }

    /**
     * 第三方接口调用查询
     *
     * @param interfaceTransferLogVo 初始化页面对象
     * @param paramPage             初始化分页对象
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<InterfaceTransferLogDomain>> queryData(InterfaceTransferLogVo interfaceTransferLogVo, ParamPage paramPage) {
        logger.debug("开始分页查询第三方商户订单");
        PageListVo<List<InterfaceTransferLogDomain>> pageListVo;
        pageListVo = new PageListVo<List<InterfaceTransferLogDomain>>();
        Page<InterfaceTransferLogDomain> page = new Page<>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == interfaceTransferLogVo) {
            interfaceTransferLogVo = new InterfaceTransferLogVo();
        }
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 50);
        interfaceTransferLogVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            if (paramPage.getSidx().equals("merchantName")) {
                interfaceTransferLogVo.setOrderStr(" D.sname " + paramPage.getSord() + ",");
            } else {
                interfaceTransferLogVo.setOrderStr(" A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
            }
        }

        //分页查询
        page = interfaceTransferLogService.selectPageByDomainWhere(page, interfaceTransferLogVo);
        logger.debug("第三方分页查询完成");


        //将分页查询结果转换成页面List集合
//        pageToPageList(pageListVo, page);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }

        return pageListVo;
    }


    /**
     * 查看详情页面
     *
     * @param sid
     * @param modelMap
     * @return
     */
    @RequestMapping("/view")
    public String infoView(String sid, ModelMap modelMap) {
        logger.debug("开始查看第三方接口调用信息详情，接口调用表ID：{}", sid);
        try {
            if (StringUtils.isNotBlank(sid)) {
                String deviceCode = "";
                String logCode = "";
                InterfaceTransferLog interfaceTransferLog = interfaceTransferLogService.selectByPrimaryKey(sid);
                if (null != interfaceTransferLog) {
                    deviceCode = interfaceTransferLog.getSdeviceCode();
                    logCode = interfaceTransferLog.getScode();
                    // 10=请求接口 20=返回接口
                    if ("10".equals(interfaceTransferLog.getIinterfaceType())) {
                        interfaceTransferLog.setSresponseData("nullString");
                    } else {
                        interfaceTransferLog.setSrequestData("nullString");
                    }
                    modelMap.put("interfaceTransferLog", interfaceTransferLog);
                    String logText4 = MessageFormat.format("查看第三方接口调用信息详情，记录编号：{0}，设备编号：{1}", logCode, deviceCode);
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    return "tb/interfaceTransferLog/thirdInterfaceTransferLog-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }


}
