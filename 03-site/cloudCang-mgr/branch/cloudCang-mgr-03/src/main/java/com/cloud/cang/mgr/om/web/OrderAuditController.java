package com.cloud.cang.mgr.om.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExcelExportUtil;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.om.domain.OrderAuditCommodityDomain;
import com.cloud.cang.mgr.om.domain.OrderAuditDomain;
import com.cloud.cang.mgr.om.service.OrderAuditCommodityService;
import com.cloud.cang.mgr.om.service.OrderAuditService;
import com.cloud.cang.mgr.om.vo.DownloadOrderAuditVo;
import com.cloud.cang.mgr.om.vo.OrderAuditCommodityVo;
import com.cloud.cang.mgr.om.vo.OrderAuditVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.model.om.OrderAudit;
import com.cloud.cang.model.om.OrderAuditCommodity;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ylf
 * @version 1.0
 * @description 订单审核
 * @time 2018-04-08
 * @fileName OrderAuditController
 */
@Controller
@RequestMapping("/orderAudit")
public class OrderAuditController {
    private static final Logger logger = LoggerFactory.getLogger(OrderAuditController.class);
    @Autowired
    private OrderAuditService orderAuditService;
    @Autowired
    private OrderAuditCommodityService orderAuditCommodityService;
    @Autowired
    private PurviewService purviewService;
    @Autowired
    private OperatorService operatorService;

    @RequestMapping("/orderAuditlist")
    public String Auditlist() {
        return "om/orderRecord/orderRecord-audit-list";
    }

    /**
     * 订单审核列表
     *
     * @param orderAuditDomain
     * @return
     */
    @RequestMapping("/queryOrderAuditData")
    public @ResponseBody
    PageListVo<List<OrderAuditVo>> queryAuditData(OrderAuditDomain orderAuditDomain, ParamPage paramPage) {
        PageListVo<List<OrderAuditVo>> pageListVo = new PageListVo<List<OrderAuditVo>>();
        Page<OrderAuditVo> page = new Page<OrderAuditVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
           /* if ("IPAY_TYPE".equals(paramPage.getSidx())) {
                orderAuditDomain.setOrderStr("OOR." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
            } else {*/
            orderAuditDomain.setOrderStr("A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
          /*  }*/
        }
        orderAuditDomain.setIdelFlag(0);
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 90);
        if (StringUtil.isNotBlank(queryCondition)) {
            orderAuditDomain.setQueryCondition(queryCondition);
        }
        page = orderAuditService.selectPageByDomainWhere(page, orderAuditDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 查看异常订单详情
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryDetails")
    PageListVo<List<OrderAuditCommodityVo>> queryDetails(ParamPage paramPage, OrderAuditCommodityDomain orderAuditCommodityDomain) {
        PageListVo<List<OrderAuditCommodityVo>> pageListVo = new PageListVo<List<OrderAuditCommodityVo>>();
        Page<OrderAuditCommodityVo> page = new Page<OrderAuditCommodityVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            orderAuditCommodityDomain.setOrderStr("OOAC." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = orderAuditCommodityService.queryDetails(page, orderAuditCommodityDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 忽略异常订单
     *
     * @param checkboxId 异常订单Id
     * @return
     */
    @RequestMapping("/ignoreOrder")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> ignoreOrder(String checkboxId) {
        try {
            OrderAudit orderAudit = new OrderAudit();
            orderAudit.setId(checkboxId);
            orderAudit.setIstatus(30);
            orderAudit.setShandlerUserid(SessionUserUtils.getSessionAttributeForUserDtl().getId());
            orderAudit.setShandlerUsername(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            orderAudit.setThandlerTime(DateUtils.getCurrentDateTime());
            orderAudit.setTupdateTime(DateUtils.getCurrentDateTime());
            orderAuditService.updateBySelective(orderAudit);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.ignore.abnormal.order"), checkboxId);
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("忽略异常订单异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("om.ignore.order.fail"));
        }
    }

    /**
     * 异常订单扣款
     *
     * @param checkboxId 异常订单Id
     * @return
     */
    @RequestMapping("/payOrder")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> payOrder(String checkboxId) {
        //1.生成订单
        try {
            return orderAuditService.payOrder(checkboxId);
        } catch (Exception e) {
            logger.error("异常订单扣款异常：{}",e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("om.abnormal.order.pay.error"));
        }
    }


    /**
     * 后台导出Excel
     *
     * @param request
     * @param response
     * @param orderAuditDomain
     */
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response, OrderAuditDomain orderAuditDomain) {
        ExcelExportUtil e = new ExcelExportUtil();
        orderAuditDomain.setIdelFlag(0);
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 90);
        if (StringUtil.isNotBlank(queryCondition)) {
            orderAuditDomain.setQueryCondition(queryCondition);
        }
        List<DownloadOrderAuditVo> downloadOrderAuditVoList = orderAuditService.selectByDomain(orderAuditDomain);
        e.createRow(0);
        String[] names = new String[]{
                MessageSourceUtils.getMessageByKey("main.serial.number"),
                MessageSourceUtils.getMessageByKey("main.merchant.number"),
                MessageSourceUtils.getMessageByKey("main.merchant.name"),
                MessageSourceUtils.getMessageByKey("main.order.number"),
                MessageSourceUtils.getMessageByKey("sm.device.id"),
                MessageSourceUtils.getMessageByKey("sm.device.name"),
                MessageSourceUtils.getMessageByKey("sm.device.address"),
                MessageSourceUtils.getMessageByKey("report.commodity.name"),
                MessageSourceUtils.getMessageByKey("report.unit.price.of.commodity.yuan"),
                MessageSourceUtils.getMessageByKey("sb.number.of.products"),
                MessageSourceUtils.getMessageByKey("om.total.commodities.yuan"),
                MessageSourceUtils.getMessageByKey("om.audit.type"),
                MessageSourceUtils.getMessageByKey("main.member.username"),
                MessageSourceUtils.getMessageByKey("sq.total.order.amount.yuan"),
                MessageSourceUtils.getMessageByKey("sh.state"),
                MessageSourceUtils.getMessageByKey("om.abnormal.time"),
                MessageSourceUtils.getMessageByKey("main.handler"),
                MessageSourceUtils.getMessageByKey("om.processing.time"),
                MessageSourceUtils.getMessageByKey("main.processing.notes")};
        for (int j = 0; j < names.length; j++) {//表头
            e.setCell(j, names[j]);
        }
        for (int i = 0; i < downloadOrderAuditVoList.size(); i++) {
            String shandlerRemark = String.valueOf(downloadOrderAuditVoList.get(i).getShandlerRemark());
            if ("null".equals(shandlerRemark)) {
                shandlerRemark = "";
            }
            Date thandlerTime = downloadOrderAuditVoList.get(i).getThandlerTime();

            e.createRow(i + 1);
            Integer itype = downloadOrderAuditVoList.get(i).getItype();
            Integer istatus = downloadOrderAuditVoList.get(i).getIstatus();
            e.setCell(0, String.valueOf(i + 1));
            e.setCell(1, downloadOrderAuditVoList.get(i).getSmerchantCode());
            e.setCell(2, downloadOrderAuditVoList.get(i).getMerchantName());
            e.setCell(3, downloadOrderAuditVoList.get(i).getSorderCode());
            e.setCell(4, downloadOrderAuditVoList.get(i).getSdeviceCode());
            e.setCell(5, downloadOrderAuditVoList.get(i).getSdeviceName());
            e.setCell(6, downloadOrderAuditVoList.get(i).getSdeviceAddress());
            e.setCell(7, String.valueOf(downloadOrderAuditVoList.get(i).getCommodityFullName()));
            e.setCell(8, String.valueOf(downloadOrderAuditVoList.get(i).getFcommodityPrice()));
            e.setCell(9, String.valueOf(downloadOrderAuditVoList.get(i).getForderCount()));
            e.setCell(10, String.valueOf(downloadOrderAuditVoList.get(i).getFcommodityAmount()));
            if (10 == itype) {
                e.setCell(11, MessageSourceUtils.getMessageByKey("om.inventory.abnormality"));
            } else if (20 == itype) {
                e.setCell(11, MessageSourceUtils.getMessageByKey("om.sold.out.button"));
            } else if (30 == itype) {
                e.setCell(11, MessageSourceUtils.getMessageByKey("om.create.order.exception"));
            }
            e.setCell(12, downloadOrderAuditVoList.get(i).getSmemberNameDesensitize());
            e.setCell(13, String.valueOf(downloadOrderAuditVoList.get(i).getFtotalAmount()));
            if (10 == istatus) {
                e.setCell(14, MessageSourceUtils.getMessageByKey("main.to.be.processed"));
            } else if (20 == istatus) {
                e.setCell(14, MessageSourceUtils.getMessageByKey("om.exception.handling.succeeded"));
            } else if (30 == istatus) {
                e.setCell(14, MessageSourceUtils.getMessageByKey("om.exception.handling.ignored"));
            }
            e.setCell(15, String.valueOf(DateUtils.dateParseString(downloadOrderAuditVoList.get(i).getTaddTime())));
            e.setCell(16, String.valueOf(downloadOrderAuditVoList.get(i).getShandlerUsername()));
            if (null == thandlerTime) {
                e.setCell(17, "");
            } else {
                e.setCell(17, String.valueOf(DateUtils.dateParseString(downloadOrderAuditVoList.get(i).getThandlerTime())));
            }
            e.setCell(18, String.valueOf(shandlerRemark));
        }
        e.downloadExcel(request, response, MessageSourceUtils.getMessageByKey("excel.order.abnormal.order.list")+"-" + DateUtils.getCurrentDTimeNumber() + ".xls");
    }

    /**
     * 异常订单审核列表页脚总合计
     *
     * @param
     * @return
     */
    @RequestMapping("/queryTotalData")
    @ResponseBody
    public ResponseVo queryTotalData(OrderAuditDomain orderAuditDomain) {
        orderAuditDomain.setIdelFlag(0);
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 90);
        if (StringUtil.isNotBlank(queryCondition)) {
            orderAuditDomain.setQueryCondition(queryCondition);
        }
        BigDecimal fapplyRefundAmount = orderAuditService.queryTotalData(orderAuditDomain);
        return ResponseVo.getSuccessResponse(fapplyRefundAmount);
    }

    /**
     * 编辑异常订单
     *
     * @param sid 订单ID
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ModelMap map, String sid) {
        try {
            OrderAudit orderAudit = orderAuditService.selectByPrimaryKey(sid);
            if (orderAudit == null) {
                orderAudit = new OrderAudit();
            }
            map.put("orderAudit", orderAudit);
            OrderAuditCommodity orderAuditCommodity = new OrderAuditCommodity();
            orderAuditCommodity.setSorderId(sid);
            List<OrderAuditCommodityVo> orderAuditCommodityVos = orderAuditCommodityService.selectOrderAuditDetail(orderAuditCommodity);
            map.put("orderAuditCommodityVos", orderAuditCommodityVos);
            return "om/orderRecord/orderRecord-audit-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 保存异常订单
     *
     * @param orderAudit 异常订单
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo save(OrderAudit orderAudit, HttpServletRequest request, String commodityIds) {
        try {
            if (null == orderAudit || StringUtil.isBlank(commodityIds)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("common.data.error"));
            }
            return orderAuditService.saveOrderAudit(orderAudit, request, commodityIds);
        } catch (ServiceException se) {
            logger.error("保存异常订单异常：{}", se);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(se.getMessage());
        } catch (Exception e) {
            logger.error("编辑审核订单异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("om.audit.order.error"));
        }
    }


}
