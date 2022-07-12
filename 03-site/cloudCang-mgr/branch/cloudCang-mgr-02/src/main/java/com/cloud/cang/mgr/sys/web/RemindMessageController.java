package com.cloud.cang.mgr.sys.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;

import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.sys.domain.RemindMessageDomain;
import com.cloud.cang.core.sys.service.RemindMessageService;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.mgr.common.ParamPage;

import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.model.sys.RemindMessage;
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
import java.util.Map;

/**
 * 温馨提示配置
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/remindMessage")
public class RemindMessageController {
    @Autowired
    private RemindMessageService remindMessageService;

    private static final Logger log = LoggerFactory.getLogger(RemindMessageController.class);

    @RequestMapping("/list")
    public String list(ModelMap map) {

        Map<Integer, String> meeTypeMap = BizTypeDefinitionEnum.RemindMeeType.getValue();
        map.put("meeTypeMap", meeTypeMap);
        return "sys/remindMessage/remindMessage-list";
    }

    @RequestMapping("/edit")
    public String list(ModelMap map, String sid) {
        try {
            RemindMessage remindMessage = remindMessageService.selectByPrimaryKey(sid);
            if (null == remindMessage) {
                remindMessage = new RemindMessage();
            }
            map.put("remindMessage", remindMessage);
            //类型
            return "sys/remindMessage/remindMessage-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 消息提醒配置列表数据
     *
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<RemindMessage>> queryDataByCondition(RemindMessageDomain remindMessageDomain,
                                                         ParamPage paramPage) {
        PageListVo<List<RemindMessage>> pageListVo = new PageListVo<List<RemindMessage>>();
        Page<RemindMessage> page = new Page<RemindMessage>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            remindMessageDomain.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = remindMessageService.selectPageByDomainWhere(page, remindMessageDomain);
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
    ResponseVo<RemindMessage> queryDataByCondition(String id) {
        RemindMessage remindMessage = remindMessageService.selectByPrimaryKey(id);
        return ResponseVo.getSuccessResponse(remindMessage);
    }

    /**
     * 保存或修改消息提醒配置列表数据
     *
     * @param
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<RemindMessage> save(RemindMessage remindMessage) {
        try {
            // 如果id为空就就添加
            if (StringUtils.isBlank(remindMessage.getId())) {
                remindMessage.setScode(CoreUtils.newCode("sys_remind_message"));
                remindMessageService.insert(remindMessage);
                //操作日志
                String logText = MessageFormat.format("新增温馨提醒，编号{0}", remindMessage.getScode());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            } else {
                // 修改
                RemindMessage oldRemindMessage = remindMessageService.selectByPrimaryKey(remindMessage.getId());
                remindMessage.setScode(oldRemindMessage.getScode());
                remindMessageService.updateBySelective(remindMessage);
                //操作日志
                String logText = MessageFormat.format("编辑温馨提醒，编号{0}", remindMessage.getScode());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            }
            return ResponseVo.getSuccessResponse(remindMessage);
        } catch (Exception ex) {
            log.error("保存或修改消息提醒配置列表数据异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("保存消息提醒信息失败");
        }
    }

    /**
     * 根据ID数据删除消息提醒配置
     *
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            remindMessageService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除温馨提醒，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("数据删除消息提醒配置异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }
}
