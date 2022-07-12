package com.cloud.cang.mgr.xx.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.xx.service.MsgTemplateMainService;
import com.cloud.cang.mgr.xx.vo.MsgTemplateMainVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.xx.MsgTemplateMain;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消息模板分类管理
 * @author ChangTanchang
 * @time:2018-01-19 10:07:00
 * @version 1.0
 */
@Controller
@RequestMapping("/msgTemplateMain")
public class MsgTemplateMainController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(MsgTemplateMainController.class);

    // 注入serv
    @Autowired
    private MsgTemplateMainService msgTemplateMainService;

    @Autowired
    MerchantInfoService merchantInfoService;

    @RequestMapping("/list")
    public String list(){
        return "xx/msgTemplateMain-list";
    }

    /**
     * 模板主表表列表数据
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<Map<String,String>>> queryMsgTemplate(ParamPage paramPage, MsgTemplateMainVo msgTemplateMainVo){
        PageListVo<List<Map<String,String>>> pageListVo = new PageListVo<List<Map<String,String>>>();
        Page<Map<String,String>> page = new Page<Map<String, String>>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        msgTemplateMainVo.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            msgTemplateMainVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = msgTemplateMainService.selectMsgTemplate(page,msgTemplateMainVo);
        if (page != null){
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 模板主表left列表数据
     * @param
     * @return
     */
    @RequestMapping("/queryDataForLeft")
    public @ResponseBody ResponseVo<List<Map<String,String>>> queryDataForLeft(ParamPage paramPage,MsgTemplateMainVo msgTemplateMainVo){
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        list = msgTemplateMainService.selectDataForLeft(msgTemplateMainVo);
        return ResponseVo.getSuccessResponse(list.size(),list);
    }

    @RequestMapping("/getById")
    public @ResponseBody ResponseVo<MsgTemplateMain> queryDateGetById(String id){
        MsgTemplateMain msgTemplateMain = msgTemplateMainService.selectByPrimaryKey(id);
        return ResponseVo.getSuccessResponse(msgTemplateMain);
    }

    /**
     * 模板分类跳转添加/编辑页面
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap modelMap,String sid){
        try {
        MsgTemplateMain msgTemplateMain = msgTemplateMainService.selectByPrimaryKey(sid);
        if (msgTemplateMain == null){
            msgTemplateMain = new MsgTemplateMain();
        }
        MerchantInfo merchantInfo = merchantInfoService.selectOne(msgTemplateMain.getSmerchantId());//商户基础信息表
        modelMap.put("msgTemplateMain",msgTemplateMain);
        modelMap.put("merchantInfo",merchantInfo);
        return "xx/msgTemplateMain-edit";
        }catch (Exception e) {
            log.error("跳转页面异常：{}",e);
        }
        return ExceptionUtil.to404();
    }

    /**
     * 保存
     * @param
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody ResponseVo<MsgTemplateMain> save(MsgTemplateMain msgTemplateMain){
        // 判断是否重复
        MsgTemplateMain exist = msgTemplateMainService.selectByExist(msgTemplateMain);
        if (exist != null){
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("模板分类名称已存在！");
        }
        // 如果ID为空就添加
        if (StringUtils.isBlank(msgTemplateMain.getId())){
            //根据商户ID查询商户
            String merchantId = msgTemplateMain.getSmerchantId();//商户ID
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(merchantId);
            String merchantCode = merchantInfo.getScode();//商户编号
            msgTemplateMain.setScode(CoreUtils.newCode(EntityTables.XX_MSG_TEMPLATE_MAIN));
            msgTemplateMain.setSmerchantCode(merchantCode); // 商户编号
            msgTemplateMain.setTaddtime(DateUtils.getCurrentDateTime());
            msgTemplateMain.setTupdatetime(DateUtils.getCurrentDateTime());
            msgTemplateMain.setIdelFlag(0);
            msgTemplateMainService.insert(msgTemplateMain);
            // 操作日志
            String logText= MessageFormat.format("新增模板分类名称{0},编号{1}",msgTemplateMain.getSmsgName(),msgTemplateMain.getScode() );
            LogUtil.addOPLog(LogUtil.AC_ADD,logText, null);
        }else {
            // 修改
            String merchantId = msgTemplateMain.getSmerchantId();//商户ID
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(merchantId);
            String merchantCode = merchantInfo.getScode();//商户编号
            msgTemplateMain.setSmerchantCode(merchantCode); // 商户编号
//            MsgTemplateMain oldMsgTemplateMain = msgTemplateMainService.selectByPrimaryKey(msgTemplateMain.getId());
//            msgTemplateMain.setScode(oldMsgTemplateMain.getScode());
//            msgTemplateMain.setSmerchantCode(oldMsgTemplateMain.getSmerchantCode());
            msgTemplateMain.setTupdatetime(DateUtils.getCurrentDateTime());
            msgTemplateMain.setIdelFlag(0);
            // 操作日志
            String logText=MessageFormat.format("修改模板分类名称{0},编号{1}",msgTemplateMain.getSmsgName(),msgTemplateMain.getScode() );
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            msgTemplateMainService.updateBySelective(msgTemplateMain);
        }
        return ResponseVo.getSuccessResponse(msgTemplateMain);
    }

    /**
     *  根据ID删除模板主表
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public @ResponseBody ResponseVo<String> delete (String [] checkboxId){
        try {
        msgTemplateMainService.delete(checkboxId);
        return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("根据ID删除模板主表异常：{}",e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("模板正在使用中,请勿删除！");
        }
    }
}
