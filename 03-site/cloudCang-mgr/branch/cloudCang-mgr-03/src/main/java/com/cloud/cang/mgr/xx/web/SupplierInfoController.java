package com.cloud.cang.mgr.xx.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sh.dao.MerchantInfoDao;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.tec.vo.ScheduleLogVo;
import com.cloud.cang.mgr.xx.domain.SupplierInfoDomain;
import com.cloud.cang.mgr.xx.service.MsgTemplateService;
import com.cloud.cang.mgr.xx.service.SupplierInfoService;
import com.cloud.cang.mgr.xx.vo.MsgTemplateMainVo;
import com.cloud.cang.mgr.xx.vo.SupplierInfoVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.tec.ScheduleLog;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信供应商管理
 *
 * @author ChangTanchang
 * @version 1.0
 * @time:2018-01-22 11:47:00
 */
@Controller
@RequestMapping("/supplierInfo")
public class SupplierInfoController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(SupplierInfoController.class);

    // 注入serv
    @Autowired
    private SupplierInfoService supplierInfoService;

    // 模板从表
    @Autowired
    private MsgTemplateService msgTemplateService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    MerchantInfoService merchantInfoService;

    /**
     * 短信供应商列表页
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {

        return "xx/supplierInfo-list";
    }

    /**
     * 消息供应商信息表列表数据
     *
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<SupplierInfoDomain>> querySupplierInfo(ParamPage paramPage, SupplierInfoVo supplierInfoVo) {
        PageListVo<List<SupplierInfoDomain>> pageListVo = new PageListVo<List<SupplierInfoDomain>>();
        Page<SupplierInfoDomain> page = new Page<SupplierInfoDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getLimit());
        supplierInfoVo.setIdelFlag(0);
        Map<String, Object> map = new HashMap<String, Object>();
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 120);
        map.put("condition", sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            supplierInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = supplierInfoService.selectSupplierInfo(page, supplierInfoVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 批量修改供应商列表页
     *
     * @param sid
     * @param isBatchUpdate
     * @param map
     * @return
     */
    @RequestMapping("/selectList")
    public String selectList(String sid, Integer isBatchUpdate, ModelMap map) {
        if (null != isBatchUpdate) {
            map.put("isBatchUpdate", isBatchUpdate);
            map.put("sid", sid);
        }
        return "xx/msgTemplate-select";
    }

    /**
     * 批量修改供应商弹出框列表数据
     *
     * @param paramPage
     * @param supplierInfoVo
     * @return
     */
    @RequestMapping("/queryBatData")
    public @ResponseBody
    PageListVo<List<SupplierInfo>> queryBatData(ParamPage paramPage, SupplierInfoVo supplierInfoVo) {
        PageListVo<List<SupplierInfo>> pageListVo = new PageListVo<List<SupplierInfo>>();
        Page<SupplierInfo> page = new Page<SupplierInfo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getLimit());
        supplierInfoVo.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            supplierInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = supplierInfoService.selectSupplierInfoMsgTemp(page, supplierInfoVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 营销短信列表页
     *
     * @return
     */
    @RequestMapping("/marketinglist")
    public String marketingList() {
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
    PageListVo<List<SupplierInfo>> queryMarketing(ParamPage paramPage, SupplierInfoVo supplierInfoVo) {
        PageListVo<List<SupplierInfo>> pageListVo = new PageListVo<List<SupplierInfo>>();
        Page<SupplierInfo> page = new Page<SupplierInfo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getLimit());
        supplierInfoVo.setIdelFlag(0);
        supplierInfoVo.setIintention(1);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            supplierInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = supplierInfoService.selectMarketing(page, supplierInfoVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 营销短信(短信供应商)
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/selectMarketList")
    public String selectMarketList(String sid, ModelMap map) {
        if (null != sid) {
            map.put("sid", sid);
        }
        return "xx/msgTemplate-table";
    }

    /**
     * 消息供应商信息表列表数据(营销短信)
     *
     * @param
     * @return
     */
    @RequestMapping("/queryMarketData")
    public @ResponseBody
    PageListVo<List<SupplierInfo>> queryMarketData(ParamPage paramPage, SupplierInfoVo supplierInfoVo) {
        PageListVo<List<SupplierInfo>> pageListVo = new PageListVo<List<SupplierInfo>>();
        Page<SupplierInfo> page = new Page<SupplierInfo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getLimit());
        supplierInfoVo.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            supplierInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = supplierInfoService.selectSupplierInfoMarket(page, supplierInfoVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 消息协议模板(短信供应商列表)
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/selectMsgTemplateList")
    public String selectMsgTemplateList(String sid, ModelMap map) {
        if (null != sid) {
            map.put("sid", sid);
        }
        return "xx/msgTemplate-msgTempLateList";
    }

    /**
     * 消息协议模板短信供应商列表数据
     *
     * @param paramPage
     * @param supplierInfoVo
     * @return
     */
    @RequestMapping("/queryMsgTempData")
    public @ResponseBody
    PageListVo<List<SupplierInfo>> queryMsgTempData(ParamPage paramPage, SupplierInfoVo supplierInfoVo) {
        PageListVo<List<SupplierInfo>> pageListVo = new PageListVo<List<SupplierInfo>>();
        Page<SupplierInfo> page = new Page<SupplierInfo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getLimit());
        supplierInfoVo.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            supplierInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = supplierInfoService.selectSupplierInfoMsgTemp(page, supplierInfoVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    @RequestMapping("/getById")
    public @ResponseBody
    ResponseVo<SupplierInfo> queryDateGetById(String id) {
        SupplierInfo supplierInfo = supplierInfoService.selectByPrimaryKey(id);
        return ResponseVo.getSuccessResponse(supplierInfo);
    }

    /**
     * 跳转短信供应商添加/编辑页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap modelMap, String sid) {
        try {
            SupplierInfo supplierInfo = supplierInfoService.selectByPrimaryKey(sid);
            if (supplierInfo == null) {
                supplierInfo = new SupplierInfo();
            }
            MerchantInfo merchantInfo = merchantInfoService.selectOne(supplierInfo.getSmerchantId());//商户基础信息表
            modelMap.put("supplierInfo", supplierInfo);
            modelMap.put("merchantInfo", merchantInfo);
            return "xx/supplierInfo-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

    @RequestMapping("/editMarket")
    public String marketList(ModelMap modelMap, String sid) {
        SupplierInfo supplierInfo = supplierInfoService.selectByPrimaryKey(sid);
        if (supplierInfo == null) {
            supplierInfo = new SupplierInfo();
        }
        modelMap.put("supplierInfo", supplierInfo);
        return "xx/marketing-editMarket";
    }

    /**
     * 保存消息供应商信息表
     *
     * @param
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<SupplierInfo> save(SupplierInfo supplierInfo) {
        // 如果id为空就就添加
        Date nowDate = new Date();
        if (StringUtils.isBlank(supplierInfo.getId())) {
            //根据商户ID查询商户
            String merchantId = supplierInfo.getSmerchantId();//商户ID
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(merchantId);
            String merchantCode = merchantInfo.getScode();//商户编号
            supplierInfo.setScode(CoreUtils.newCode(EntityTables.XX_SUPPLIER_INFO));
            supplierInfo.setSmerchantCode(merchantCode); // 商户编号
            supplierInfo.setTaddTime(nowDate);
            supplierInfo.setTupdateTime(nowDate);
            supplierInfo.setIdelFlag(0);
            supplierInfo.setIisDisable(1); // 默认启用
            supplierInfoService.insert(supplierInfo);
            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("xxcon.add.sms.provider.name",null)+"{0},"+MessageSourceUtils.getMessageByKey("main.code",null)+"{1}", supplierInfo.getSname(), supplierInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        } else {
            // 修改
            String merchantId = supplierInfo.getSmerchantId();//商户ID
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(merchantId);
            String merchantCode = merchantInfo.getScode();//商户编号
            supplierInfo.setSmerchantCode(merchantCode); // 商户编号
            supplierInfo.setIdelFlag(0);
//            SupplierInfo oldSupplierInfo = supplierInfoService.selectByPrimaryKey(supplierInfo.getId());
//            supplierInfo.setScode(oldSupplierInfo.getScode());
//            supplierInfo.setSmerchantCode(oldSupplierInfo.getSmerchantCode());
            supplierInfo.setTupdateTime(nowDate);
            supplierInfoService.updateBySelective(supplierInfo);
            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.update.sms.supplier")+MessageSourceUtils.getMessageByKey("main.name",null)+"{0},"+MessageSourceUtils.getMessageByKey("main.code",null)+"{1}", supplierInfo.getSname(), supplierInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        }
        return ResponseVo.getSuccessResponse(supplierInfo);
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
            supplierInfoService.delete(checkboxId);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("xx.msg.supplier.delete.disable"));
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
            List<MsgTemplate> msgTemplateList = msgTemplateService.selectBySsupplierId(checkboxId);
            for (MsgTemplate msgTemplate : msgTemplateList) {
                if (msgTemplate.getIisEnable().equals(1)) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("xx.msg.template.is.used"));
                }
            }
            SupplierInfo supplierInfo = new SupplierInfo();
            supplierInfo.setId(checkboxId);
            supplierInfo.setIisDisable(type);
            supplierInfo.setTupdateTime(DateUtils.getCurrentDateTime());
            supplierInfoService.updateBySelective(supplierInfo);
            for (MsgTemplate msgTemplate : msgTemplateList) {
                if (msgTemplate.getIisEnable().equals(0) && msgTemplate.getSsupplierId().equals(supplierInfo.getId())) {
                    msgTemplateService.deleteByPrimaryKey(msgTemplate.getId());
                    msgTemplateService.updateBySelective(msgTemplate);
                }
            }

            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("xxcon.sms.provider.enabled",null), supplierInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("短信供应商启用/禁用异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("xxcon.sms.provider.enabled.failed",null));
        }
    }
}
