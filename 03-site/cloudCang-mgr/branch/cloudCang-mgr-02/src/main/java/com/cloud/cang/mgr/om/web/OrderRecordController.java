package com.cloud.cang.mgr.om.web;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.act.GiveActivityDto;
import com.cloud.cang.act.GiveActivityResult;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;

import com.cloud.cang.mgr.common.utils.ExcelExportUtil;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.hy.vo.MemberInfoVo;
import com.cloud.cang.mgr.om.domain.OrderCommodityDomain;
import com.cloud.cang.mgr.om.domain.OrderRecordDomain;
import com.cloud.cang.mgr.om.service.OrderCommodityService;
import com.cloud.cang.mgr.om.service.OrderRecordService;
import com.cloud.cang.mgr.om.vo.OrderCommodityVo;
import com.cloud.cang.mgr.om.vo.OrderRecordMoneyStaVo;
import com.cloud.cang.mgr.om.vo.OrderRecordVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sh.DomainConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sm.StandardStock;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.Purview;
import com.cloud.cang.pay.PayServicesDefine;
import com.cloud.cang.pay.RepairProcessDto;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ylf
 * @version 1.0
 * @description 订单列表
 * @time 2018-02-22
 * @fileName OrderRecordController
 */
@Controller
@RequestMapping("/orderRecord")
public class OrderRecordController {
    @Autowired
    private OrderCommodityService orderCommodityService;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private PurviewService purviewService;
    @Autowired
    private OperatorService operatorService;
    private static final Logger logger = LoggerFactory.getLogger(OrderRecordController.class);

    @RequestMapping("/list")
    public String list() {
        return "om/orderRecord/orderRecord-list";
    }

    /**
     * 订单商品表列表数据
     *
     * @param orderRecordDomain
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<OrderRecordVo>> queryDataByCondition(OrderRecordDomain orderRecordDomain, ParamPage paramPage) {
        PageListVo<List<OrderRecordVo>> pageListVo = new PageListVo<List<OrderRecordVo>>();
        Page<OrderRecordVo> page = new Page<OrderRecordVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            orderRecordDomain.setOrderStr("A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        orderRecordDomain.setIsAudit("10,20,100,110");
        orderRecordDomain.setIdelFlag(0);
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 90);
        if (StringUtil.isNotBlank(queryCondition)) {
            orderRecordDomain.setQueryCondition(queryCondition);
        }
        page = orderRecordService.selectPageByDomainWhere(page, orderRecordDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 编辑订单
     *
     * @param sid 订单ID
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ModelMap map, String sid) {
        try {
            OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(sid);
            if (orderRecord == null) {
                orderRecord = new OrderRecord();
            }
            map.put("orderRecord", orderRecord);
            OrderCommodityDomain orderCommodityDomain = new OrderCommodityDomain();
            orderCommodityDomain.setSorderId(sid);
            List<OrderCommodityVo> orderCommodityList = orderCommodityService.selectOrderDetail(orderCommodityDomain);
            map.put("orderCommodityList", orderCommodityList);
            return "om/orderRecord/orderRecord-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 保存订单
     *
     * @param orderRecord 订单
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo save(OrderRecord orderRecord, HttpServletRequest request, String commodityIds) {
        try {
            if (null == orderRecord || StringUtil.isBlank(commodityIds)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("数据异常！");
            }
            return orderRecordService.saveOrder(orderRecord, request, commodityIds);
        } catch (ServiceException se) {
            logger.error("保存订单异常：{}", se);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(se.getMessage());
        } catch (Exception e) {
            logger.error("保存订单异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("编辑订单异常！");
        }
    }

    /**
     * 查看订单详情
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryDetails")
    PageListVo<List<OrderCommodityVo>> queryDetails(ParamPage paramPage, OrderCommodityDomain orderCommodity) {
        PageListVo<List<OrderCommodityVo>> pageListVo = new PageListVo<List<OrderCommodityVo>>();
        Page<OrderCommodityVo> page = new Page<OrderCommodityVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            orderCommodity.setOrderStr("OOC." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = orderCommodityService.queryDetails(page, orderCommodity);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 订单支付补处理
     *
     * @param checkboxId 用户订单ID
     * @return ResponseVo
     */
    @RequestMapping("/rechargeAgain")
    public @ResponseBody
    ResponseVo rechargeAgain(String checkboxId) {
        try {
            if (StringUtils.isBlank(checkboxId)) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("数据异常，没有找到相关数据");
            }
            OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(checkboxId);
            if (null == orderRecord) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("没有找到相关订单！");
            }
            if (null == orderRecord.getIstatus() || BizTypeDefinitionEnum.OrderStatus.IN_PAYMENT.getCode() != orderRecord.getIstatus()) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("订单状态不支持补处理！");
            }
            // 组装数据
            RepairProcessDto repairProcessDto = new RepairProcessDto();
            repairProcessDto.setSmerchantCode(orderRecord.getSmerchantCode());
            repairProcessDto.setSorderId(orderRecord.getId());
            repairProcessDto.setSordercode(orderRecord.getSorderCode());
            repairProcessDto.setSmemberId(orderRecord.getSmemberId());
            repairProcessDto.setItype(10);//补处理类型  10 支付 20 退款
            if (StringUtil.isNotBlank(orderRecord.getSpaySerialNumber())) {//支付交易流水号  如果有就传
                repairProcessDto.setSpaySerialNumber(repairProcessDto.getSpaySerialNumber());
            }
            repairProcessDto.setIpayType(orderRecord.getIpayType());//订单支付方式 30 微信 40 支付宝
            try {
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.REPAIR_PROCESS);
                invoke.setRequestObj(repairProcessDto); // post 参数
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<HashMap<String, Object>>>() {
                });
                ResponseVo<HashMap<String, Object>> responseVo = (ResponseVo<HashMap<String, Object>>) invoke.invoke();
                if (null != responseVo && responseVo.isSuccess()) {
                    //补处理成功,订单状态修改为已支付
                    logger.info("调用订单支付补处理接口服务成功");
                    /*orderRecord.setIstatus(BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode());
                    orderRecordService.updateBySelective(orderRecord);
                    logger.info("修改订单状态成功");*/
                    String logText = MessageFormat.format("订单支付补处理成功{0}", orderRecord.getSorderCode());
                    LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
                    return ResponseVo.getSuccessResponse("补处理成功!");
                } else {
                    logger.error("调用订单支付补处理接口服务失败{}", JSON.toJSONString(responseVo));
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("补处理失败!");
                }
            } catch (Exception e) {
                logger.error("调用订单支付补处理接口服务失败", e);
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("补处理失败!");
            }
        } catch (Exception e) {
            logger.error("订单补处理失败", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("补处理失败!");
        }
    }

    /**
     * 后台导出Excel
     *
     * @param request
     * @param response
     */
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response, OrderRecordDomain orderRecordDomain, ParamPage paramPage) {
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            orderRecordDomain.setOrderStr("A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        orderRecordDomain.setIsAudit("10,20,100,110");
        orderRecordDomain.setIdelFlag(0);
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 90);
        if (StringUtil.isNotBlank(queryCondition)) {
            orderRecordDomain.setQueryCondition(queryCondition);
        }


        List<Map<String, Object>> list = orderRecordService.selectDownloadExcelData(orderRecordDomain);
        ExcelExportUtil e = new ExcelExportUtil();
        e.createRow(0);
        String[] names = new String[]{"序号", "商户编号", "商户名称", "订单编号", "交易流水号", "设备编号", "设备名称", "设备地址", "商品编号", "商品名称", "商品简称",
                "商品成本（元）", "商品销售单价（元）", "商品数量", "商品总金额（元）", "商品优惠金额（元）", "商品首单优惠金额（元）", "商品促销优惠金额（元）",
                "商品优惠券抵扣金额（元）", "商品实付总金额（元）", "商品退款数量", "商品退款总金额（元）", "会员用户名", "订单总金额（元）", "订单优惠金额（元）",
                "订单首单优惠金额（元）", "订单促销优惠金额（元）", "订单优惠券抵扣金额（元）", "订单实付总金额（元）", "订单退款总金额（元）", "订单时间", "扣款方式",
                "支付类型", "状态", "支付完成时间", "订单支付失败原因", "是否拆单", "拆单编号", "拆单类型"};
        for (int j = 0; j < names.length; j++) {//表头
            e.setCell(j, names[j]);
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> obj = list.get(i);
            String commodityName = String.valueOf(obj.get("COMMODITY_NAME"));
            String sbrandName = String.valueOf(obj.get("SBRAND_NAME"));
            String staste = String.valueOf(obj.get("STASTE"));
            String ispecWeight = String.valueOf(obj.get("ISPEC_WEIGHT"));
            String sspecUnit = String.valueOf(obj.get("SSPEC_UNIT"));
            String spackageUnit = String.valueOf(obj.get("SPACKAGE_UNIT"));
            String commodityFullName = sbrandName + commodityName + staste + ispecWeight + sspecUnit + "/" + spackageUnit;
            String smemberName = String.valueOf(obj.get("SMEMBER_NAME"));
            if (!SecurityUtils.getSubject().isPermitted("ORDER_MEMBER_NAME_DESENSITIZE")) {
                smemberName = DesensitizeUtil.mobilePhone(smemberName);
            }
            String ichargebackWay = String.valueOf(obj.get("ICHARGEBACK_WAY"));
            String istatus = String.valueOf(obj.get("ISTATUS"));
            String ipayType = String.valueOf(obj.get("IPAY_TYPE"));
            String spaySerialNumber = String.valueOf(obj.get("SPAY_SERIAL_NUMBER"));
            String tpayCompleteTime = String.valueOf(obj.get("TPAY_COMPLETE_TIME"));
            String iisDismantling = String.valueOf(obj.get("IIS_DISMANTLING"));
            String idismantlingType = String.valueOf(obj.get("IDISMANTLING_TYPE"));
            String sdismantlingCode = String.valueOf(obj.get("SDISMANTLING_CODE"));
            String spayFailureReason = String.valueOf(obj.get("SPAY_FAILURE_REASON"));
            String sshortName = String.valueOf(obj.get("SSHORT_NAME"));

            e.createRow(i + 1);
            e.setCell(0, String.valueOf(i + 1));
            e.setCell(1, String.valueOf(obj.get("SMERCHANT_CODE")));
            e.setCell(2, String.valueOf(obj.get("MERCHANT_NAME")));
            e.setCell(3, String.valueOf(obj.get("SORDER_CODE")));
            if ("null".equals(spaySerialNumber)) {
                e.setCell(4, "");
            } else {
                e.setCell(4, String.valueOf(obj.get("SPAY_SERIAL_NUMBER")));
            }
            e.setCell(5, String.valueOf(obj.get("SDEVICE_CODE")));
            e.setCell(6, String.valueOf(obj.get("SDEVICE_NAME")));
            e.setCell(7, String.valueOf(obj.get("SDEVICE_ADDRESS")));
            e.setCell(8, String.valueOf(obj.get("SCOMMODITY_CODE")));
            e.setCell(9, String.valueOf(commodityFullName));
            if ("null".equals(sshortName)) {
                e.setCell(10, sbrandName + commodityName);
            } else {
                e.setCell(10, sshortName);
            }
            e.setCell(11, Double.valueOf(obj.get("FCOST_AMOUNT").toString()));
            e.setCell(12, String.valueOf(obj.get("FCOMMODITY_PRICE")));
            e.setCell(13, String.valueOf(obj.get("FORDER_COUNT")));
            e.setCell(14, String.valueOf(obj.get("FCOMMODITY_AMOUNT")));
            e.setCell(15, String.valueOf(obj.get("FDISCOUNT_AMOUNT")));
            e.setCell(16, String.valueOf(obj.get("FFIRST_DISCOUNT_AMOUNT")));
            e.setCell(17, String.valueOf(obj.get("FPROMOTION_DISCOUNT_AMOUNT")));
            e.setCell(18, String.valueOf(obj.get("FCOUPON_DEDUCTION_AMOUNT")));
            e.setCell(19, String.valueOf(obj.get("FACTUAL_AMOUNT")));
            e.setCell(20, String.valueOf(obj.get("FREFUND_COUNT")));
            e.setCell(21, String.valueOf(obj.get("FREFUND_AMOUNT")));
            e.setCell(22, String.valueOf(smemberName));
            e.setCell(23, String.valueOf(obj.get("FTOTAL_AMOUNT")));
            e.setCell(24, String.valueOf(obj.get("ORDER_FDISCOUNT_AMOUNT")));
            e.setCell(25, String.valueOf(obj.get("ORDER_FFIRST_DISCOUNT_AMOUNT")));
            e.setCell(26, String.valueOf(obj.get("ORDER_FPROMOTION_DISCOUNT_AMOUNT")));
            e.setCell(27, String.valueOf(obj.get("ORDER_FCOUPON_DEDUCTION_AMOUNT")));
            e.setCell(28, String.valueOf(obj.get("FACTUAL_PAY_AMOUNT")));
            e.setCell(29, String.valueOf(obj.get("FACTUAL_REFUND_AMOUNT")));
            e.setCell(30, String.valueOf(obj.get("TORDER_TIME")));
            if ("10".equals(ichargebackWay)) {
                e.setCell(31, String.valueOf("商户代扣"));
            } else {
                e.setCell(31, String.valueOf("主动支付"));
            }
            if ("30".equals(ipayType)) {
                e.setCell(32, String.valueOf("微信支付"));
            } else if ("40".equals(ipayType)) {
                e.setCell(32, String.valueOf("支付宝支付"));
            } else {
                e.setCell(32, "");
            }
            if ("10".equals(istatus)) {
                e.setCell(33, String.valueOf("付款成功"));
            } else if ("20".equals(istatus)) {
                e.setCell(33, String.valueOf("付款失败"));
            } else if ("30".equals(istatus)) {
                e.setCell(33, String.valueOf("订单异常"));
            } else if ("100".equals(istatus)) {
                e.setCell(33, String.valueOf("待付款"));
            } else if ("110".equals(istatus)) {
                e.setCell(33, String.valueOf("付款处理中"));
            }
            if ("null".equals(tpayCompleteTime)) {
                e.setCell(34, "");
            } else {
                e.setCell(34, String.valueOf(obj.get("TPAY_COMPLETE_TIME")));
            }
            if ("null".equals(spayFailureReason)) {
                e.setCell(35, "");
            } else {
                e.setCell(35, String.valueOf(obj.get("SPAY_FAILURE_REASON")));
            }
            if ("0".equals(iisDismantling)) {
                e.setCell(36, String.valueOf("否"));
            } else if ("1".equals(iisDismantling)) {
                e.setCell(36, String.valueOf("是"));
            }
            if ("null".equals(sdismantlingCode)) {
                e.setCell(37, "");
            } else {
                e.setCell(37, String.valueOf(obj.get("SDISMANTLING_CODE")));
            }
            if ("10".equals(idismantlingType)) {
                e.setCell(38, String.valueOf("自动"));
            } else if ("20".equals(idismantlingType)) {
                e.setCell(38, String.valueOf("手动"));
            }
        }
        e.downloadExcel(request, response, "订单列表-" + DateUtils.getCurrentDTimeNumber() + ".xls");
    }

    /**
     * 订单列表页脚总统计
     *
     * @param orderRecordDomain
     * @return ResponseVo
     */
    @RequestMapping("/queryTotalData")
    @ResponseBody
    public ResponseVo<OrderRecordMoneyStaVo> queryTotalData(OrderRecordDomain orderRecordDomain) {
        orderRecordDomain.setIsAudit("10,20,100,110");
        orderRecordDomain.setIdelFlag(0);
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 90);
        if (StringUtil.isNotBlank(queryCondition)) {
            orderRecordDomain.setQueryCondition(queryCondition);
        }
        OrderRecordMoneyStaVo orderRecordMoneyStaVo = orderRecordService.queryTotalData(orderRecordDomain);
        return ResponseVo.getSuccessResponse(orderRecordMoneyStaVo);
    }

    /**
     * 删除订单
     *
     * @param checkboxId 删除订单ID
     * @return
     */

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseVo delete(String checkboxId) {
        try {
            if (StringUtils.isBlank(checkboxId)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("数据异常，没有找到相关数据");
            }
            OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(checkboxId);
            if (null == orderRecord) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("没有找到相关订单！");
            }
            if (null != orderRecord.getIstatus() && (20 == orderRecord.getIstatus() || 100 == orderRecord.getIstatus())) {
                OrderRecord temp = new OrderRecord();
                temp.setId(checkboxId);
                temp.setIdelFlag(1);
                orderRecordService.updateBySelective(temp);
                String logText = MessageFormat.format("订单删除成功,订单编号{0}", orderRecord.getSorderCode());
                LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
                return ResponseVo.getSuccessResponse();
            } else {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("编辑订单状态异常！");
            }
        } catch (Exception e) {
            logger.error("删除订单异常,订单ID:{}", checkboxId);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("编辑订单异常！");
        }
    }
}
