package com.cloud.cang.mgr.xx.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.AuthorityUtil;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.xx.service.MsgTaskService;
import com.cloud.cang.mgr.xx.vo.MsgTaskVo;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信发送记录
 * @author ChangTanchang
 * @time:2018-01-19 10:07:00
 * @version 1.0
 */
@Controller
@RequestMapping("/msgTask")
public class MsgTaskController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(MsgTaskController.class);

    // 注入serv
    @Autowired
    private MsgTaskService msgTaskService;

    @Autowired
    private OperatorService operatorService;

    /**消息类型 短信 */
    private final int IMSGTYPE_SHORT_MSG=1;

    /**消息类型 邮件 */
    private final int IMSGTYPE_MAIL=2;

    /**用途: 验证码 */
    private final int IUSAGE_CODE=1;

    /**用途: 普通 */
    private final int IUSAGE_Normal=2;

    // 短信
    @RequestMapping("/list")
    public String list(){
        return "xx/msgTask-list";
    }

    // 验证码
    @RequestMapping("/codeList")
    public String codeList(){
        return "xx/codeTask-list";
    }

    // 邮件
    @RequestMapping("/mailList")
    public String mailList(){
        return "xx/mailTask-list";
    }

    /**
     * 消息任务表列表数据 短信发送
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<Map<String,String>>> querySendMsg(ParamPage paramPage, MsgTaskVo msgTaskVo){
        PageListVo<List<Map<String,String>>> pageListVo = new PageListVo<List<Map<String,String>>>();
        Page<Map<String,String>> page = new Page<Map<String,String>>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        msgTaskVo.setImsgType(IMSGTYPE_SHORT_MSG);
        msgTaskVo.setIusage(IUSAGE_Normal);
        Map<String, Object> map = new HashMap<String, Object>();
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 120);
        map.put("condition", sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            msgTaskVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = msgTaskService.selectAllSendMsg(page,msgTaskVo);
        if (page != null){
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        // 手机号显示*号
        AuthorityUtil.permissionsMobile(page.getResult(),"SHOW_MSG_MOBILE","SMSG_RECIPIENT");
        return pageListVo;
    }

    /**
     * 消息任务表列表数据 邮件发送
     * @param
     * @return
     */
    @RequestMapping("/queryMailData")
    public @ResponseBody PageListVo<List<Map<String,String>>> queryMailData(ParamPage paramPage,MsgTaskVo msgTaskVo){
        PageListVo<List<Map<String,String>>> pageListVo = new PageListVo<List<Map<String,String>>>();
        Page<Map<String,String>> page = new Page<Map<String, String>>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        msgTaskVo.setImsgType(IMSGTYPE_MAIL);
        Map<String, Object> map = new HashMap<String, Object>();
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 120);
        map.put("condition", sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            msgTaskVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = msgTaskService.selectAllSendMsg(page,msgTaskVo);
        if (page != null){
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 消息任务表列表数据 验证码发送
     * @param
     * @return
     */
    @RequestMapping("/queryCodeData")
    public @ResponseBody PageListVo<List<Map<String,String>>> queryCodeData(ParamPage paramPage,MsgTaskVo msgTaskVo){
        PageListVo<List<Map<String,String>>> pageListVo = new PageListVo<List<Map<String,String>>>();
        Page<Map<String,String>> page = new Page<Map<String, String>>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        msgTaskVo.setImsgType(IMSGTYPE_SHORT_MSG);
        msgTaskVo.setIusage(IUSAGE_CODE);
        Map<String, Object> map = new HashMap<String, Object>();
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 120);
        map.put("condition", sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            msgTaskVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = msgTaskService.selectAllSendMsg(page,msgTaskVo);
        if (page != null){
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        // 手机号显示*号
        AuthorityUtil.permissionsMobile(page.getResult(),"SHOW_MSG_MOBILE","SMSG_RECIPIENT");
        return pageListVo;
    }
}
