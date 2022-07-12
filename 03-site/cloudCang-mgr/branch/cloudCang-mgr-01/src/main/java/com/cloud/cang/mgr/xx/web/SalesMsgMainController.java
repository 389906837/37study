package com.cloud.cang.mgr.xx.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.xx.domain.SalesMsgMainDomain;
import com.cloud.cang.mgr.xx.service.SalesMsgDetailService;
import com.cloud.cang.mgr.xx.service.SalesMsgMainService;
import com.cloud.cang.mgr.xx.service.SupplierInfoService;
import com.cloud.cang.mgr.xx.vo.SalesMsgMainVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.xx.SalesMsgDetail;
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

import java.text.MessageFormat;
import java.util.*;

/**
 * 营销短信管理
 * @author ChangTanchang
 * @time:2018-01-22 11:47:00
 * @version 1.0
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
    private SalesMsgDetailService salesMsgDetailService;

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
    ResponseVo<SalesMsgMain> save(Map map, SalesMsgMain salesMsgMain, String ssupplierCode, String msgContent, String testMobile) {
        // 如果id为空就就添加
        SalesMsgDetail salesMsgDetail = null;
        Date nowDate = new Date();
        List<SupplierInfo> list = null;
        if (StringUtils.isBlank(salesMsgMain.getId())) {
            if (StringUtils.isNotBlank(ssupplierCode)) {
                list = supplierInfoService.selectByMapWhere(map);
                for (SupplierInfo supplierInfo : list) {
                    if (supplierInfo.getScode().equals(ssupplierCode)) {
                        salesMsgMain.setSspartnerId(supplierInfo.getSmerchantId());
                        salesMsgMain.setSsupplierCode(ssupplierCode);
                    }
                }
            }
            salesMsgMain.setScode(CoreUtils.newCode(EntityTables.XX_SALES_MSG_MAIN));
            salesMsgMain.setSsupplierCode(ssupplierCode);
            salesMsgMain.setScontent(msgContent);
            salesMsgMain.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            salesMsgMain.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
            salesMsgMain.setTaddtime(nowDate);
            salesMsgMain.setIstatus(10);
            salesMsgMain.setSadduser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            salesMsgMainService.insert(salesMsgMain);
            salesMsgDetail = new SalesMsgDetail();
            salesMsgMain = salesMsgMainService.selectByPrimaryKey(salesMsgMain.getId());
            salesMsgDetail.setSmainId(salesMsgMain.getId());
            salesMsgDetail.setSmobile(testMobile);
            salesMsgDetail.setScontext(msgContent);
            salesMsgDetail.setItype(1);
            salesMsgDetailService.insert(salesMsgDetail);
            // 操作日志
            String logText = MessageFormat.format("新增营销短信供应商名称{0},编号{1}", salesMsgMain.getScode());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        }
        return ResponseVo.getSuccessResponse(salesMsgMain);
    }


    /**
     *  根据ID删除消息供应商信息表
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public @ResponseBody ResponseVo delete(String [] checkboxId){
        try {
            salesMsgMainService.delete(checkboxId);
            return ResponseVo.getSuccessResponse();
        }catch (ServiceException e){
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常！");
    }


    /**
     * 发送营销短信
     * @param
     * @return
     */
    @RequestMapping("/sendMsg")
    public void sendSalesMessage(final String ssupplierCode,final String testMobile,final String msgContent) {
                try {
                    // 前台传入的多个手机号后台用逗号分隔
                    List<String> mobiles = new ArrayList<>();
                    String str[] = testMobile.split(",");
                    mobiles = Arrays.asList(str);
                    // 调用短信接口
                    if (mobiles != null) {

                        // 调用发送短信接口
                        MessageDto messageDto = new MessageDto();
                        messageDto.setTemplateType("send_sales_message");
                        // 内容
                        Map<String, Object> contentParamMap = new HashMap<>();
                        contentParamMap.put("ssupplierCode", ssupplierCode);
                        contentParamMap.put("mobiles",mobiles);
                        contentParamMap.put("msgContent", msgContent);
                        messageDto.setContentParam(contentParamMap);
                        // 发送手机号
                        messageDto.setMobiles(mobiles);
                        // 商户Id
                        messageDto.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                        messageDto.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());

                        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_BATCH_MESSAGE_SEND_SERVICE);
                        invoke.setRequestObj(messageDto); // post 参数
                        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                        });
                        ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                        if (responseVo.isSuccess()) {
                            log.debug("营销短信发送成功！");
                        } else {
                            log.error("营销短信发送失败");
                        }
                    }

                } catch (Exception e) {
                    log.error("发送营销短信失败：{}", e);
                }
    }

}
