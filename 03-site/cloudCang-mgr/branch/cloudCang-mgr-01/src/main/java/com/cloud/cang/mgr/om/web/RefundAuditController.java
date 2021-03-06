package com.cloud.cang.mgr.om.web;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExcelExportUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.hy.service.MemberInfoService;
import com.cloud.cang.mgr.om.domain.OrderCommodityDomain;
import com.cloud.cang.mgr.om.domain.RefundAuditDomain;
import com.cloud.cang.mgr.om.domain.RefundCommodityDomain;
import com.cloud.cang.mgr.om.service.*;
import com.cloud.cang.mgr.om.vo.OrderAuditVo;
import com.cloud.cang.mgr.om.vo.RefunAuditVo;
import com.cloud.cang.mgr.om.vo.RefundCommodityVo;
import com.cloud.cang.mgr.sq.service.RefundApplyService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.sys.util.DateUtil;
import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.*;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sq.RefundApply;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.Purview;
import com.cloud.cang.pay.PayServicesDefine;
import com.cloud.cang.pay.RefundApplyDto;
import com.cloud.cang.pay.RepairProcessDto;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author yanlingfeng
 * @version 1.0
 * @description ??????????????????
 * @time 2018-02-22 9:54:19
 * @fileName RefundAuditController.java
 */
@Controller
@RequestMapping("/refundAudit")
public class RefundAuditController {
    @Autowired
    private RefundAuditService refundAuditService;
    @Autowired
    private RefundImgDescService refundImgDescService;
    @Autowired
    private RefundCommodityService refundCommodityService;
    @Autowired
    private PurviewService purviewService;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private RefundApplyService refundApplyService;
    @Autowired
    private RefundOperlogService refundOperlogService;
    @Autowired
    private OrderCommodityService orderCommodityService;
    private static final Logger logger = LoggerFactory.getLogger(RefundAuditController.class);

    @RequestMapping("/list")
    public String list() {
        return "om/refundAudit/refundAudit-list";
    }

    /**
     * ????????????????????????
     *
     * @param refundAuditDomain
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<RefunAuditVo>> queryDataByCondition(RefundAuditDomain refundAuditDomain,
                                                        ParamPage paramPage) {
        PageListVo<List<RefunAuditVo>> pageListVo = new PageListVo<List<RefunAuditVo>>();
        Page<RefunAuditVo> page = new Page<RefunAuditVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            if ("IPAY_TYPE".equals(paramPage.getSidx())) {
                refundAuditDomain.setOrderStr("OOR." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
            } else
                refundAuditDomain.setOrderStr("A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        refundAuditDomain.setIdelFlag(0);
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 90);
        if (StringUtil.isNotBlank(queryCondition)) {
            refundAuditDomain.setQueryCondition(queryCondition);
        }
        page = refundAuditService.selectPageByDomainWhere(page, refundAuditDomain);
       /* List<RefunAuditVo> refunAuditVoList = page.getResult();
        if (!hasPurCode("REFUND_AUDIT_MEMBER_NAME_DESENSITIZE")) {
            for (RefunAuditVo refunAuditVo : refunAuditVoList) {
                refunAuditVo.setSmemberName(DesensitizeUtil.mobilePhone(refunAuditVo.getSmemberName()));
            }
        }*/
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /****
     * ??????????????????????????????
     * @param purCode ?????????
     * @return
     */
    private boolean hasPurCode(String purCode) {
        List<String> list = purviewService.selectOperatorPurview(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        if (list.contains(purCode)) {
            return true;
        }
        return false;
    }

    @RequestMapping("/audit")
    public String audit(String sid, ModelMap map) {

        RefundAudit refundAudit = refundAuditService.selectByPrimaryKey(sid);
        if (null == refundAudit) {
            refundAudit = new RefundAudit();
        }
        map.put("refundAudit", refundAudit);
        return "om/refundAudit/refundAudit-audit";
    }

    /**
     * ??????
     *
     * @param refundAuditDomain
     * @return
     */
    @RequestMapping("/saveAudit")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo saveAudit(RefundAuditDomain refundAuditDomain, HttpServletRequest request) {
        try {
            RefundOperlog refundOperlog = new RefundOperlog();
            refundOperlog.setSrefundId(refundAuditDomain.getId());
            refundOperlog.setSrefundCode(refundAuditDomain.getSrefundCode());
            refundOperlog.setSoperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            refundOperlog.setSip(SessionUserUtils.getIpAddr(request));
            refundOperlog.setTaddTime(DateUtils.getCurrentDateTime());
            if (null == refundAuditDomain.getIauditStatus()) {
                refundOperlog.setScontent("????????????????????????,?????????????????????");//????????????
                refundOperlogService.insert(refundOperlog);
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????????????????,???????????????????????????");
            }
            if (BizTypeDefinitionEnum.DomainStatus.AUDIT_FAILURE.getCode() == refundAuditDomain.getIauditStatus() && StringUtils.isBlank(refundAuditDomain.getSauditRefuseReason())) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????????????????");
            }
            ResponseVo res = refundAuditService.audit(refundAuditDomain, request);
            if (res.isSuccess()) {
                if (BizTypeDefinitionEnum.DomainStatus.AUDIT_SUCCESS.getCode() == refundAuditDomain.getIauditStatus()) {
                    //?????????????????????????????????
                    refundOperlog.setScontent("????????????????????????!");//????????????
                } else if (BizTypeDefinitionEnum.DomainStatus.AUDIT_FAILURE.getCode() == refundAuditDomain.getIauditStatus()) {
                    //?????????????????????????????????
                    refundOperlog.setScontent("??????????????????????????????!");//????????????
                }
            } else {
                //?????????????????????????????????
                refundOperlog.setScontent(res.getMsg());//????????????
            }
            refundOperlogService.insert(refundOperlog);
            return res;
           /* ResponseVo res = refundAuditService.audit(refundAuditDomain);
            String merchantCode = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
            MerchantClientConf merchantClientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
            int isRefundAudit = merchantClientConf.getIisRefundAudit();
            RefundAudit upRefundAudit = new RefundAudit();
            //?????????????????????????????????????????????
            if (20 == refundAuditDomain.getIauditStatus() && 0 == isRefundAudit) {
                ResponseVo responseVo = refund(refundAuditDomain.getId());
                if (!responseVo.isSuccess()) {
                    logger.info("???????????????");
                }
                refundOperlog.setScontent("??????????????????,????????????!");//????????????
            } else if (20 == refundAuditDomain.getIauditStatus() && 1 == isRefundAudit) {
                upRefundAudit.setIrefundStatus(10);
                refundOperlog.setScontent("??????????????????,????????????!");//????????????
            } else if (30 == refundAuditDomain.getIauditStatus()) {
                //????????????
                //1.??????????????????????????????
                //2.??????????????????????????????,????????????
                RefundAudit refundAudit = refundAuditService.selectByPrimaryKey(refundAuditDomain.getId());
                //1.?????????????????? ???????????????
                OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(refundAuditDomain.getSorderId());
                if (null == orderRecord) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????????????????????????????");
                }
                orderRecord.setFactualRefundAmount(orderRecord.getFactualRefundAmount().subtract(refundAudit.getFactualRefundAmount()));
                orderRecordService.updateBySelective(orderRecord);
                //2.??????????????????????????????,????????????
                RefundCommodity refundCommodity = new RefundCommodity();
                refundCommodity.setSrefundId(refundAuditDomain.getId());
                List<RefundCommodity> refundCommodityList = refundCommodityService.selectByEntityWhere(refundCommodity);
                for (RefundCommodity re : refundCommodityList) {
                    OrderCommodity orderCommodity = orderCommodityService.selectByOrderIdAndComId(orderRecord.getId(), re.getScommodityId());
                    if (null != orderCommodity) {
                        orderCommodity.setFrefundAmount(orderCommodity.getFrefundAmount().subtract(re.getFrefundAmount()));
                        orderCommodity.setFrefundCount(orderCommodity.getFrefundCount() - re.getFrefundCount());
                        orderCommodityService.updateBySelective(orderCommodity);
                    }
                }
                refundOperlog.setScontent("??????????????????,????????????!");//????????????
            }
            upRefundAudit.setSauditRefuseReason(refundAuditDomain.getSauditRefuseReason());
            upRefundAudit.setId(refundAuditDomain.getId());
            upRefundAudit.setIauditStatus(refundAuditDomain.getIauditStatus());
            upRefundAudit.setTauditTime(DateUtils.getCurrentDateTime());
            upRefundAudit.setTupdateTime(DateUtils.getCurrentDateTime());
            upRefundAudit.setSauditOperId(SessionUserUtils.getSessionAttributeForUserDtl().getId());
            upRefundAudit.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            refundAuditService.updateBySelective(upRefundAudit);
            //?????????????????????????????????
            refundOperlog.setSrefundId(refundAuditDomain.getId());
            refundOperlog.setSrefundCode(refundAuditDomain.getSrefundCode());
            refundOperlog.setSoperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            refundOperlog.setSip(SessionUserUtils.getRequestIp());
            refundOperlog.setTaddTime(DateUtils.getCurrentDateTime());
            refundOperlogService.insert(refundOperlog);*/
            //????????????
           /* String logText = MessageFormat.format("??????????????????,??????Id{}", refundAuditDomain.getId());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);*/
        } catch (Exception e) {
            RefundOperlog refundOperlog = new RefundOperlog();
            refundOperlog.setSrefundId(refundAuditDomain.getId());
            refundOperlog.setSrefundCode(refundAuditDomain.getSrefundCode());
            refundOperlog.setSoperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            refundOperlog.setSip(SessionUserUtils.getIpAddr(request));
            refundOperlog.setTaddTime(DateUtils.getCurrentDateTime());
            refundOperlog.setScontent("??????????????????,???????????????");//????????????
            logger.error("??????????????????,???????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????");
        }
    }

    /**
     * ????????????????????????
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryDetails")
    PageListVo<List<RefundCommodityVo>> queryDetails(ParamPage paramPage, RefundCommodityDomain refundCommodityDomain) {
        PageListVo<List<RefundCommodityVo>> pageListVo = new PageListVo<List<RefundCommodityVo>>();
        Page<RefundCommodityVo> page = new Page<RefundCommodityVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            refundCommodityDomain.setOrderStr("ORC." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = refundCommodityService.queryDetails(page, refundCommodityDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * ??????????????????
     *
     * @param checkboxId ????????????Id
     * @return ResponseVo
     */
    @RequestMapping("/refund")
    public @ResponseBody
    ResponseVo refund(String checkboxId, HttpServletRequest request) {
        if (StringUtils.isBlank(checkboxId)) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????????????????");
        }
        RefundAudit refundAudit = refundAuditService.selectByPrimaryKey(checkboxId);
        if (null == refundAudit) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????????????????");
        }
        try {
            return refundAuditService.refund(refundAudit, request);
        } catch (ServiceException se) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(se.getMessage());
        } catch (Exception e) {
            logger.error("???????????????", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????!");
        }
   /*     OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(refundAudit.getSorderId());
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(orderRecord.getSmemberId());
        //???????????????????????????
        RefundOperlog refundOperlog = new RefundOperlog();
        refundOperlog.setSrefundId(refundAudit.getId());
        refundOperlog.setSrefundCode(refundAudit.getSrefundCode());
        refundOperlog.setSoperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        refundOperlog.setSip(SessionUserUtils.getRequestIp());
        refundOperlog.setTaddTime(DateUtils.getCurrentDateTime());
        // ????????????
        //======??????=========
        RefundApplyDto refundApplyDto = new RefundApplyDto();
        refundApplyDto.setSmerchantCode(refundAudit.getSmerchantCode());
        refundApplyDto.setSorderId(refundAudit.getSorderId());
        refundApplyDto.setSorderCode(refundAudit.getSorderCode());
        refundApplyDto.setSauditOrderId(refundAudit.getId());
        refundApplyDto.setSauditOrderCode(refundAudit.getSrefundCode());
        refundApplyDto.setStransactionId(orderRecord.getSpaySerialNumber());

        refundApplyDto.setFtotalMoney(refundAudit.getFtotalAmount());//????????????
        refundApplyDto.setFrefundMoney(refundAudit.getFapplyRefundAmount()); //??????????????????
        refundApplyDto.setSmemberId(refundAudit.getSmemberId());
        refundApplyDto.setSmemberCode(refundAudit.getSmemberCode());
        refundApplyDto.setSmemberName(refundAudit.getSmemberName());
        //??????IP
        refundApplyDto.setSip(memberInfo.getSmemberRegip());
        refundApplyDto.setIpayType(orderRecord.getIpayType());//????????????
        //======??????=========
        //????????????????????????????????????????????????????????????????????????????????????????????????
        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.REFUND_APPLY);
            invoke.setRequestObj(refundApplyDto); // post ??????
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<HashMap<String, Object>>>() {
            });
            ResponseVo<HashMap<String, Object>> responseVo = (ResponseVo<HashMap<String, Object>>) invoke.invoke();
            if (null != responseVo && responseVo.isSuccess()) {
                refundAudit.setIrefundStatus(30);//????????????
                refundAudit.setTrefundTime(DateUtils.getCurrentDateTime());//????????????
                Long trefundCompleteTime = (Long) responseVo.getData().get("refundTime");
                refundAudit.setTrefundCompleteTime(new Date(trefundCompleteTime));//??????????????????
                refundAudit.setSrefundOperId(SessionUserUtils.getSessionAttributeForUserDtl().getId());//???????????????ID
                refundAudit.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//?????????????????????
                refundAuditService.updateByPrimaryKey(refundAudit);
                logger.info("??????????????????????????????????????????");
                //?????????????????????????????????
                refundOperlog.setScontent("?????????????????????????????????");
                refundOperlogService.insert(refundOperlog);
                return ResponseVo.getSuccessResponse();
            } else {
                refundAudit.setIrefundStatus(20);//????????????
                refundAudit.setTrefundTime(DateUtils.getCurrentDateTime());//????????????
                refundAuditService.updateByPrimaryKey(refundAudit);
                refundOperlog.setScontent("?????????????????????????????????");
                refundOperlogService.insert(refundOperlog);
                logger.error("??????????????????????????????????????????{}" + JSON.toJSONString(responseVo));
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????!");
            }
        } catch (Exception e) {
            refundAudit.setIrefundStatus(20);//????????????
            refundAudit.setTrefundTime(DateUtils.getCurrentDateTime());//????????????
            refundAuditService.updateByPrimaryKey(refundAudit);
            refundOperlog.setScontent("?????????????????????????????????");
            refundOperlogService.insert(refundOperlog);
            logger.error("??????????????????????????????????????????", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????!");
        }*/
    }

    /**
     * ??????????????????
     *
     * @param sid ????????????Id
     * @return
     */
    @RequestMapping("/viewImg")
    public String viewImg(ModelMap map, String sid) {
        RefundImgDesc refundImgDesc = new RefundImgDesc();
        refundImgDesc.setSrefundId(sid);

        List<RefundImgDesc> refundImgDescList = refundImgDescService.selectByEntityWhere(refundImgDesc);
        if (null == refundImgDescList || refundImgDescList.size() <= 0) {
            refundImgDescList = new ArrayList<>();
        }
        map.put("refundImgDescList", refundImgDescList);
        return "om/refundAudit/refund_img_desc";
    }

    /**
     * ??????????????????
     *
     * @param checkboxId ????????????Id
     * @return
     */
    @RequestMapping("/dismissal")
    @ResponseBody
    public ResponseVo dismissal(String checkboxId, HttpServletRequest request) {
        try {
            if (StringUtils.isBlank(checkboxId)) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????????????????");
            }
            RefundAudit refundAudit = refundAuditService.selectByPrimaryKey(checkboxId);
            if (null == refundAudit) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????????????????");
            }
            //????????????
            //1.??????????????????????????????
            //2.??????????????????????????????,????????????

            //1.?????????????????? ???????????????
            OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(refundAudit.getSorderId());
            if (null == orderRecord) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????????????????????????????");
            }
            OrderRecord uporderRecord = new OrderRecord();
            uporderRecord.setId(orderRecord.getId());
            uporderRecord.setFactualRefundAmount(orderRecord.getFactualRefundAmount().subtract(refundAudit.getFactualRefundAmount()));
            orderRecordService.updateBySelective(uporderRecord);
            //2.??????????????????????????????,????????????
            RefundCommodity refundCommodity = new RefundCommodity();
            refundCommodity.setSrefundId(checkboxId);
            List<RefundCommodity> refundCommodityList = refundCommodityService.selectByEntityWhere(refundCommodity);
            for (RefundCommodity re : refundCommodityList) {
                OrderCommodity orderCommodity = orderCommodityService.selectByOrderIdAndComId(orderRecord.getId(), re.getScommodityId());
                if (null != orderCommodity) {
                    OrderCommodity upOrderCommodity = new OrderCommodity();
                    upOrderCommodity.setId(orderCommodity.getId());
                    upOrderCommodity.setFrefundAmount(orderCommodity.getFrefundAmount().subtract(re.getFrefundAmount()));
                    upOrderCommodity.setFrefundCount(orderCommodity.getFrefundCount() - re.getFrefundCount());
                    orderCommodityService.updateBySelective(upOrderCommodity);
                }
            }
            RefundAudit updata = new RefundAudit();
            updata.setId(refundAudit.getId());
            updata.setIauditStatus(30);
            updata.setSauditRefuseReason("????????????????????????????????????");
            updata.setTauditTime(DateUtils.getCurrentDateTime());
            updata.setTupdateTime(DateUtils.getCurrentDateTime());
            updata.setSauditOperId(SessionUserUtils.getSessionAttributeForUserDtl().getId());
            updata.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            refundAuditService.updateBySelective(updata);

            RefundOperlog refundOperlog = new RefundOperlog();
            refundOperlog.setSrefundId(refundAudit.getId());
            refundOperlog.setSrefundCode(refundAudit.getSrefundCode());
            refundOperlog.setScontent("????????????????????????????????????");
            refundOperlog.setTaddTime(DateUtils.getCurrentDateTime());
            refundOperlog.setSoperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            refundOperlog.setSip(SessionUserUtils.getIpAddr(request));
            refundOperlogService.insert(refundOperlog);
            return ResponseVo.getSuccessResponse("?????????????????????");
        } catch (Exception e) {
            logger.error("???????????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????");
        }
    }

    /**
     * ???????????????
     *
     * @param checkboxId ?????????????????????Id
     * @return
     */
    @RequestMapping("/rechargeAgain")

    public @ResponseBody
    ResponseVo rechargeAgain(String checkboxId) {
        if (StringUtils.isBlank(checkboxId)) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????????????????");
        }
        RefundAudit refundAudit = refundAuditService.selectByPrimaryKey(checkboxId);
        if (null == refundAudit) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????");
        }
        OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(refundAudit.getSorderId());
        RefundApply refundApply = new RefundApply();
        refundApply.setSauditOrderId(checkboxId);
        refundApply = refundApplyService.selectByOrderId(refundApply);
        if (null == refundApply) {
            logger.info("???????????????????????????");
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????!");
        }
        // ????????????
        RepairProcessDto repairProcessDto = new RepairProcessDto();
        repairProcessDto.setSmerchantCode(orderRecord.getSmerchantCode());
        repairProcessDto.setSorderId(orderRecord.getId());
        repairProcessDto.setSordercode(orderRecord.getSorderCode());
        repairProcessDto.setSmemberId(orderRecord.getSmemberId());
        repairProcessDto.setItype(20);//???????????????  10 ?????? 20 ??????
        if (StringUtil.isNotBlank(orderRecord.getSpaySerialNumber())) {//?????????????????????  ???????????????
            repairProcessDto.setSpaySerialNumber(orderRecord.getSpaySerialNumber());
        }
       /* if (StringUtil.isNotBlank(refundAudit.getSrefundCode())) {//????????????   ???????????????
            repairProcessDto.setSrefundNo(refundAudit.getSrefundCode());
        }*/
        repairProcessDto.setSrefundId(refundApply.getId());//????????????(???????????????ID)
        repairProcessDto.setIpayType(orderRecord.getIpayType());//?????????????????? 30 ?????? 40 ?????????

        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.REPAIR_PROCESS);
            invoke.setRequestObj(repairProcessDto); // post ??????
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<HashMap<String, Object>>>() {
            });
            ResponseVo<HashMap<String, Object>> responseVo = (ResponseVo<HashMap<String, Object>>) invoke.invoke();
            if (null != responseVo && responseVo.isSuccess()) {
                refundAudit.setIrefundStatus(30);
                refundAudit.setTrefundCompleteTime(DateUtils.getCurrentDateTime());//??????????????????
                refundAuditService.updateBySelective(refundAudit);
                logger.info("?????????????????????????????????????????????");
                return ResponseVo.getSuccessResponse("???????????????????????????!");
            } else {
                logger.error("?????????????????????????????????????????????{}" + JSON.toJSONString(responseVo));
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????!");
            }
        } catch (Exception e) {
            logger.error("????????????????????????????????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????!");
        }
    }

    /**
     * ????????????Excel
     *
     * @param request  t
     * @param response
     */
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) {
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 90);
        List<Map<String, Object>> list = refundAuditService.selectDownloadExcelData(queryCondition);
        ExcelExportUtil e = new ExcelExportUtil();
        e.createRow(0);
        String[] names = new String[]{"??????", "????????????", "????????????", "????????????", "???????????????", "????????????", "????????????", "????????????", "????????????", "????????????", "????????????", "????????????",
                "?????????????????????", "????????????", "????????????????????????", "???????????????????????????", "???????????????", "????????????????????????", "??????????????????????????????", "???????????????????????????",
                "????????????", "????????????", "????????????", "????????????", "?????????", "????????????"};
        for (int j = 0; j < names.length; j++) {//??????
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
            if (!SecurityUtils.getSubject().isPermitted("REFUND_AUDIT_MEMBER_NAME_DESENSITIZE")) {
                smemberName = DesensitizeUtil.mobilePhone(smemberName);
            }
            String irefundStatus = String.valueOf(obj.get("IREFUND_STATUS"));
            String ipayType = String.valueOf(obj.get("IPAY_TYPE"));
            String iauditStatus = String.valueOf(obj.get("IAUDIT_STATUS"));
            String sauditOperName = String.valueOf(obj.get("SAUDIT_OPER_NAME"));
            String tauditTime = String.valueOf(obj.get("TAUDIT_TIME"));
            String spaySerialNumber = String.valueOf(obj.get("SPAY_SERIAL_NUMBER"));
            String sshortName = String.valueOf(obj.get("SSHORT_NAME"));

            e.createRow(i + 1);
            e.setCell(0, String.valueOf(i + 1));
            e.setCell(1, String.valueOf(obj.get("SMERCHANT_CODE")));
            e.setCell(2, String.valueOf(obj.get("MERCHANT_NAME")));
            e.setCell(3, String.valueOf(obj.get("SREFUND_CODE")));
            if ("null".equals(spaySerialNumber)) {
                e.setCell(4, "");

            } else {
                e.setCell(4, String.valueOf(obj.get("SPAY_SERIAL_NUMBER")));
            }
            e.setCell(5, String.valueOf(obj.get("SORDER_CODE")));
            e.setCell(6, String.valueOf(obj.get("SDEVICE_CODE")));
            e.setCell(7, String.valueOf(obj.get("SDEVICE_NAME")));
            e.setCell(8, String.valueOf(obj.get("SDEVICE_ADDRESS")));
            e.setCell(9, String.valueOf(obj.get("COMMODITY_CODE")));
            e.setCell(10, String.valueOf(commodityFullName));
            if ("null".equals(sshortName)) {
                e.setCell(11, sbrandName + commodityName);
            } else {
                e.setCell(11, sshortName);
            }
            e.setCell(12, String.valueOf(obj.get("FCOMMODITY_PRICE")));
            e.setCell(13, String.valueOf(obj.get("FREFUND_COUNT")));
            e.setCell(14, String.valueOf(obj.get("FCOMMODITY_AMOUNT")));
            e.setCell(15, String.valueOf(obj.get("FREFUND_AMOUNT")));
            e.setCell(16, String.valueOf(smemberName));
            e.setCell(17, String.valueOf(obj.get("FTOTAL_AMOUNT")));
            e.setCell(18, String.valueOf(obj.get("FACTUAL_PAY_AMOUNT")));
            e.setCell(19, String.valueOf(obj.get("FAPPLY_REFUND_AMOUNT")));
            if ("10".equals(irefundStatus)) {
                e.setCell(20, String.valueOf("?????????"));
            } else if ("20".equals(irefundStatus)) {
                e.setCell(20, String.valueOf("????????????"));
            } else if ("30".equals(irefundStatus)) {
                e.setCell(20, String.valueOf("????????????"));
            } else {
                e.setCell(20, "");
            }
            if ("30".equals(ipayType)) {
                e.setCell(21, String.valueOf("??????"));
            } else if ("40".equals(ipayType)) {
                e.setCell(21, String.valueOf("?????????"));
            } else {
                e.setCell(21, "");
            }

            e.setCell(22, String.valueOf(obj.get("TAPPLY_TIME")));

            if ("10".equals(iauditStatus)) {
                e.setCell(23, String.valueOf("?????????"));
            } else if ("20".equals(iauditStatus)) {
                e.setCell(23, String.valueOf("????????????"));
            } else if ("30".equals(iauditStatus)) {
                e.setCell(23, String.valueOf("????????????"));
            } else {
                e.setCell(23, "");
            }
            if ("null".equals(sauditOperName)) {
                e.setCell(24, "");
            } else {
                e.setCell(24, String.valueOf(obj.get("SAUDIT_OPER_NAME")));
            }
            if ("null".equals(tauditTime)) {
                e.setCell(25, "");
            } else {
                e.setCell(25, String.valueOf(obj.get("TAUDIT_TIME")));
            }
        }
        e.downloadExcel(request, response, "??????????????????-" + DateUtils.getCurrentDTimeNumber() + ".xls");
    }

    /**
     * ?????????????????????????????????
     *
     * @param
     * @return
     */
    @RequestMapping("/queryTotalData")
    @ResponseBody
    public ResponseVo queryTotalData(RefundAuditDomain refundAuditDomain) {
        refundAuditDomain.setIdelFlag(0);
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 90);
        if (StringUtil.isNotBlank(queryCondition)) {
            refundAuditDomain.setQueryCondition(queryCondition);
        }
        BigDecimal fapplyRefundAmount = refundAuditService.queryTotalData(refundAuditDomain);
        return ResponseVo.getSuccessResponse(fapplyRefundAmount);
    }
}
