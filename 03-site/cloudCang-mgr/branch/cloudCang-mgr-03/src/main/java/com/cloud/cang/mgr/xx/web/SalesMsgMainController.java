package com.cloud.cang.mgr.xx.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.message.SalesMsgDto;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.hy.service.MemberInfoService;
import com.cloud.cang.mgr.hy.vo.MemberInfoVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.xx.domain.SalesMsgMainDomain;
import com.cloud.cang.mgr.xx.service.SalesMsgMainService;
import com.cloud.cang.mgr.xx.service.SupplierInfoService;
import com.cloud.cang.mgr.xx.vo.SalesMsgMainVo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.model.xx.SalesMsgMain;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * 营销短信管理
 *
 * @author ChangTanchang
 * @version 1.0
 * @time:2018-01-22 11:47:00
 */
@Controller
@RequestMapping("/salesmsgmain")
public class SalesMsgMainController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(SalesMsgMainController.class);

    // 注入营销短信serv
    @Autowired
    private SalesMsgMainService salesMsgMainService;

    @Autowired
    private SupplierInfoService supplierInfoService;

    @Autowired
    private OperatorService operatorService;
    @Autowired
    private MemberInfoService memberInfoService;

    @RequestMapping("/list")
    public String list() {

        return "xx/marketing-list";
    }

    /**
     * 营销短信列表数据
     *
     * @param
     * @return
     */
    @RequestMapping("/queryDataMarketing")
    public @ResponseBody
    PageListVo<List<SalesMsgMainDomain>> queryMarketing(ParamPage paramPage, SalesMsgMainVo salesMsgMainVo) {
        PageListVo<List<SalesMsgMainDomain>> pageListVo = new PageListVo<List<SalesMsgMainDomain>>();
        Page<SalesMsgMainDomain> page = new Page<SalesMsgMainDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            salesMsgMainVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = salesMsgMainService.selectMarketing(page, salesMsgMainVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * 跳转到编辑添加页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/editMarket")
    public String marketList(ModelMap modelMap, String sid) {
        SupplierInfo supplierInfo = new SupplierInfo();
        supplierInfo.setIdelFlag(0);
        /* 渠道作用
            1：营销
            2：非营销 */
        supplierInfo.setIintention(1);
        supplierInfo.setIisDisable(1);
        supplierInfo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        List<SupplierInfo> supplierInfos = supplierInfoService.selectByEntityWhere(supplierInfo);
        modelMap.put("supplierInfos", supplierInfos);
        SalesMsgMain salesMsgMain = salesMsgMainService.selectByPrimaryKey(sid);
        if (salesMsgMain == null) {
            salesMsgMain = new SalesMsgMain();
        }
        modelMap.put("salesMsgMain", salesMsgMain);
        return "xx/marketing-editMarket";
    }


    /**
     * 保存营销短信信息表
     *
     * @param
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<SalesMsgMain> save(String ssupplierCode, String msgContent, String testMobile, String sendMsgIds) {
        if (StringUtils.isBlank(ssupplierCode)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcon.select.supplier.error",null));
        }
        if (StringUtils.isBlank(msgContent)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcon.fill.content.error",null));
        }
        if (StringUtils.isBlank(testMobile)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcon.send.sms.mobile.error",null));
        }
        if (StringUtils.isBlank(sendMsgIds)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcon.sms.member.error",null));
        }
        return salesMsgMainService.saveSalesMsgMain(ssupplierCode, msgContent, testMobile, sendMsgIds);
    }


    /**
     * 根据ID删除消息供应商信息表
     *
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo delete(String[] checkboxId) {
        try {
            salesMsgMainService.delete(checkboxId);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("main.system.abnormal",null));
    }


    /**
     * 发送营销短信
     *
     * @param
     * @return
     */
    @RequestMapping("/sendMsg")
    @ResponseBody
    public ResponseVo sendSalesMessage(final String ssupplierCode, final String testMobile, final String msgContent) {
        ResponseVo res = new ResponseVo();
        try {
            // 前台传入的多个手机号后台用逗号分隔
            String str[] = testMobile.split(",");
            List<String> mobiles = Arrays.asList(str);
            // 调用短信接口
            if (mobiles != null) {
                // 调用发送短信接口
                SalesMsgDto salesMsgDto = new SalesMsgDto();
                // 内容
                MsgTemplate msgTemplate = new MsgTemplate();
                if (StringUtils.isNotBlank(ssupplierCode)) {
                    String[] temp = ssupplierCode.split("_");
                    msgTemplate.setSsupplierCode(temp[1]);
                    msgTemplate.setSsupplierId(temp[0]);
                }
                salesMsgDto.setMerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                salesMsgDto.setMerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());                /* 每日发送次数限制 */
                msgTemplate.setSendCountLimit(0);
                /* 消息类型。 1： 短信 2：邮箱 */
                msgTemplate.setImsgType(1);
                /* 用途: 1：验证码  2：普通 */
                msgTemplate.setIusage(2);
                /* 0不超时:单位（分钟） */
                msgTemplate.setItimeout(0);
                /* 是否实时发送 1:实时 2：非实时 */
                msgTemplate.setIisRealtime(1);
                salesMsgDto.setContentParam(msgContent);
                salesMsgDto.setMsgTemplate(msgTemplate);
                // 发送手机号
                salesMsgDto.setMobiles(mobiles);

                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SALES_MESSAGE_SEND_SERVICE);
                invoke.setRequestObj(salesMsgDto); // post 参数
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                if (responseVo.isSuccess()) {
                    res.setSuccess(true);
                    log.info("营销短信发送成功！");
                } else {
                    res.setSuccess(false);
                    res.setMsg(MessageSourceUtils.getMessageByKey("xxcon.marketing.sms.send.error",null));
                    log.info("调用短信发送接口失败");
                }
            } else {
                res.setSuccess(false);
                res.setMsg(MessageSourceUtils.getMessageByKey("xxcon.marketing.sms.send.error",null));
                log.info("营销短信测试手机号为空");
            }
            return res;
        } catch (Exception e) {
            log.error("发送营销短信异常:{}", e);
            res.setSuccess(false);
            res.setMsg(MessageSourceUtils.getMessageByKey("xxcon.marketing.sms.send.error",null));
            return res;
        }
    }


    /**
     * 根据条件查询符合的会员
     *
     * @param memberInfoVo
     * @return
     */
    @RequestMapping("/selectParam")
    @ResponseBody
    public ResponseVo selectParam(MemberInfoVo memberInfoVo) {
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 70);//70 会员列表
        memberInfoVo.setQueryCondition(queryCondition);
        // 是否删除0否1是
        memberInfoVo.setIdelFlag(0);
        memberInfoVo.setIstatus(1);
        memberInfoVo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        String str = memberInfoService.selectMemberInfoByParam(memberInfoVo);
        return ResponseVo.getSuccessResponse(str);
    }

}
