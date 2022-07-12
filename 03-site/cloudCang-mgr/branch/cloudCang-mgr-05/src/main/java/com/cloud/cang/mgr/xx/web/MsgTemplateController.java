package com.cloud.cang.mgr.xx.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.xx.domain.MsgTemplateDomain;
import com.cloud.cang.mgr.xx.service.MsgTemplateMainService;
import com.cloud.cang.mgr.xx.service.MsgTemplateService;
import com.cloud.cang.mgr.xx.service.SupplierInfoService;
import com.cloud.cang.mgr.xx.vo.MsgTemplateMainVo;
import com.cloud.cang.mgr.xx.vo.MsgTemplateVo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.model.xx.MsgTemplateMain;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息/协议模板管理
 *
 * @author ChangTanchang
 * @version 1.0
 * @time:2018-01-22 10:07:00
 */
@Controller
@RequestMapping("/msgTemplate")
public class MsgTemplateController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(MsgTemplateController.class);

    // 注入serv
    @Autowired
    private MsgTemplateService msgTemplateService;

    @Autowired
    private SupplierInfoService supplierInfoService;

    @Autowired
    private MsgTemplateMainService msgTemplateMainService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    MerchantInfoService merchantInfoService;

    /**
     * 返回消息/协议模板列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "xx/msgTemplate-list-left";
    }

    /**
     * 查询消息/协议模板从表列表数据
     *
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<MsgTemplate>> queryData(ParamPage paramPage, MsgTemplateVo msgTemplateVo) {
        PageListVo<List<MsgTemplate>> pageListVo = new PageListVo<List<MsgTemplate>>();
        Page<MsgTemplate> page = new Page<MsgTemplate>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        msgTemplateVo.setBisDelete(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            msgTemplateVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = msgTemplateService.selectMsgTemplate(page, msgTemplateVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 获取模板主表
     *
     * @param
     * @return
     */
    @RequestMapping("/getMsgTemplateList")
    public @ResponseBody
    ResponseVo<List<MsgTemplateMain>> queryDataProvince(MsgTemplateMainVo msgTemplateMainVo) {
        if (null == msgTemplateMainVo) {
            msgTemplateMainVo = new MsgTemplateMainVo();
        }
        msgTemplateMainVo.setIdelFlag(0);
        List<MsgTemplateMain> msgTemplateMainList = msgTemplateMainService.selectGetMsgTemplateList(msgTemplateMainVo);
        return ResponseVo.getSuccessResponse(msgTemplateMainList);
    }

    /**
     * 跳转模板从表页
     *
     * @param menuId
     * @param map
     * @return
     */
    @RequestMapping("/page")
    public String msgTemplateLeft(String menuId, ModelMap map) {
        try {
            if (StringUtil.isNotBlank(menuId)) {
                map.put("menuId", menuId);
            }
            return "xx/msgTemplate-list";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 模板主表ID对应从表的SMAINID
     * 根据SMAINID获取对应的每条数据
     *
     * @param menuId
     * @return
     */
    @RequestMapping("/getBySmainId")
    public @ResponseBody
    PageListVo<List<MsgTemplateDomain>> queryDateGetBySmainId(ParamPage paramPage, String menuId) {
        PageListVo<List<MsgTemplateDomain>> pageListVo = new PageListVo<List<MsgTemplateDomain>>();
        /*if (StringUtil.isBlank(menuId)){
            return pageListVo;
        }*/
        Map<String, Object> map = new HashMap<String, Object>();
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 120);
        map.put("condition", sql);
        if (StringUtil.isNotBlank(menuId)) {
            map.put("smainId", menuId);
        }
        map.put("bisDelete", 0);

        Page<MsgTemplateDomain> page = new Page<MsgTemplateDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        page = msgTemplateService.selectByMainId(page, map);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 跳转编辑页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap modelMap, String sid) {
        try {
            MsgTemplate msgTemplate = msgTemplateService.selectByPrimaryKey(sid);
            List<MsgTemplateMain> msgTemplateList = msgTemplateMainService.selectAllMsgTemplateMain();
            if (msgTemplate == null) {
                msgTemplate = new MsgTemplate();
            }
            modelMap.put("msgTemplate", msgTemplate);
            modelMap.put("msgTemplateList", msgTemplateList);
            return "xx/msgTemplate-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 保存
     *
     * @param
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<MsgTemplate> save(MsgTemplate msgTemplate) {
        String startTime = msgTemplate.getSstarttime();
        String endTime = msgTemplate.getSendtime();
        if (StringUtils.isBlank(msgTemplate.getStemplateContent())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcon.template.empty",null));
        }
        if (msgTemplate.getIisRealtime().intValue() == 2) {//短信非实时发送
            if (StringUtils.isBlank(startTime)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcom.send.start.time.empty",null));
            }
            if (StringUtils.isBlank(endTime)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcom.send.end.time.empty",null));
            }
            if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                if (endTime.compareTo(startTime) < 0) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcom.send.time.error",null));
                }
            }
        }

        if (StringUtils.isBlank(msgTemplate.getSsupplierCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcon.vendor.number.empty",null));
        }

        // 如果ID为空就添加
        if (StringUtils.isBlank(msgTemplate.getId())) {
            msgTemplate.setDaddDate(DateUtils.getCurrentDateTime());
            msgTemplate.setDmodifyDate(DateUtils.getCurrentDateTime());
            msgTemplate.setSaddOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            msgTemplate.setSmodifyOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            msgTemplate.setBisDelete(0);
            msgTemplate.setIisEnable(1); // 默认启用
            msgTemplateService.insert(msgTemplate);
            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("xxcon.add.template.table.name",null)+"{0},"+MessageSourceUtils.getMessageByKey("main.title.msg",null)+"{1}", msgTemplate.getStemplateName(), msgTemplate.getStemplateTitle());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        } else {
            // 修改
            msgTemplate.setBisDelete(0);
            msgTemplate.setDmodifyDate(DateUtils.getCurrentDateTime());
            msgTemplate.setSmodifyOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            msgTemplateService.updateBySelective(msgTemplate);
            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("xxcon.modify.template.table.name",null)+"{0},"+ MessageSourceUtils.getMessageByKey("main.title.msg",null)+"{1}", msgTemplate.getStemplateName(), msgTemplate.getStemplateTitle());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        }
        return ResponseVo.getSuccessResponse(msgTemplate);
    }

    /**
     * 根据ID删除消息/协议模板从表
     *
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            msgTemplateService.delete(checkboxId);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("根据ID删除消息/协议模板从表异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcon.template.delete.error",null));
        }
    }

    /**
     * 批量修改供应商
     *
     * @param checkboxId
     * @param supplierId
     * @return
     */
    @RequestMapping("/bat")
    public @ResponseBody
    ResponseVo<String> batSupplierInfo(String[] checkboxId, String supplierId, String supplierCode) {
        SupplierInfo supplierInfo = supplierInfoService.selectByPrimaryKey(supplierId);
        if (supplierInfo != null) {
            for (String id : checkboxId) {
                MsgTemplate msgTemplate = msgTemplateService.selectByPrimaryKey(id);
                if (msgTemplate != null) {
                    msgTemplate.setSsupplierId(supplierId);
                    msgTemplate.setSsupplierCode(supplierCode);
                    msgTemplateService.updateByPrimaryKey(msgTemplate);
                    // 操作日志
                    String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("xxcon.modify.template",null)+"【{0}】"+ MessageSourceUtils.getMessageByKey("xxcon.sms.provider.for",null)+"【{1}】", msgTemplate.getStemplateName(), supplierInfo.getSname());
                    LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                }
            }
            return ResponseVo.getSuccessResponse();
        } else {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcon.supplier.not.exist",null));
        }
    }


    /**
     * 启用/禁用
     *
     * @param checkboxId
     * @param type
     * @return
     */
    @RequestMapping("/enable")
    public @ResponseBody
    ResponseVo<String> enable(String checkboxId, Integer type) {
        try {
//            MsgTemplate msgTemplate = msgTemplateService.selectByPrimaryKey(checkboxId);
//            SupplierInfo supplierInfo = supplierInfoService.selectByPrimaryKey(msgTemplate.getSsupplierId());
//            if (supplierInfo.getIisDisable().equals(1) && msgTemplate.getIisEnable().equals(1)){
//                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("消息/协议模板正在使用,请勿禁用");
//            }
            MsgTemplate msgTemplateNew = new MsgTemplate();
            msgTemplateNew.setId(checkboxId);
            msgTemplateNew.setIisEnable(type);
            msgTemplateNew.setDmodifyDate(DateUtils.getCurrentDateTime());
            msgTemplateNew.setSmodifyOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            msgTemplateService.updateBySelective(msgTemplateNew);
            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("xxcon.sms.provider.enabled",null), msgTemplateNew.getStemplateTitle());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("消息/协议模板启禁用异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo( MessageSourceUtils.getMessageByKey("xxcon.protocol.template.enabled.failed",null));
        }
    }
}
