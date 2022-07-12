package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.InviteCodeUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sb.domain.AiRegisterDomain;
import com.cloud.cang.mgr.sb.service.AiInfoService;
import com.cloud.cang.mgr.sb.service.DeviceRegisterService;
import com.cloud.cang.mgr.sb.vo.AiRegisterVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.AiInfo;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
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

/**
 * @version 1.0
 * @ClassName AiDeviceRegisterController
 * @Description AI设备注册管理controller
 * @Author zengzexiong
 * @Date 2018年7月31日14:16:26
 */
@Controller
@RequestMapping("/ai")
public class AiDeviceRegisterController {

    private static final Logger logger = LoggerFactory.getLogger(AiDeviceRegisterController.class);

    @Autowired
    OperatorService operatorService;

    @Autowired
    DeviceRegisterService deviceRegisterService;

    @Autowired
    AiInfoService aiInfoService;

    /**
     * AI设备注册信息列表
     * @return
     */
    @RequestMapping("/aiRegister/list")
    public String registerList() {
        return "sb/aiRegister/aiRegister-list";
    }

    /**
     * AI设备注册信息列表数据
     *
     * @param aiRegisterVo 初始化页面对象
     * @param paramPage        初始化分页对象
     * @return
     */
    @RequestMapping("/aiRegister/queryData")
    @ResponseBody
    public PageListVo<List<AiRegisterDomain>> queryData(AiRegisterVo aiRegisterVo, ParamPage paramPage) {

        PageListVo<List<AiRegisterDomain>> pageListVo = new PageListVo<List<AiRegisterDomain>>();//返回的页面对象
        Page<AiRegisterDomain> page = new Page<AiRegisterDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        aiRegisterVo.setIdelFlag(0);/* 是否删除0否1是 */

        if (null == aiRegisterVo) {
            aiRegisterVo = new AiRegisterVo();
        }

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 50);
        aiRegisterVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            aiRegisterVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        //分页查询
        page = deviceRegisterService.selectAiPageByDomainWhere(page, aiRegisterVo);


        //将分页查询结果转换成页面List集合
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * 设备注册审核按钮跳转页面
     *
     * @param sid
     * @return
     */
    @RequestMapping("/aiRegister/toAudit")
    public String registerToCheck(String sid, ModelMap modelMap) {
        try {
            //数据库查询该设备信息
            if (StringUtils.isNotBlank(sid)) {
//                DeviceRegisterDomain deviceRegisterDomain = deviceRegisterService.selectViewBySid(sid);
                AiRegisterDomain aiRegisterDomain = deviceRegisterService.selectAiViewBySid(sid);
                if (aiRegisterDomain != null) {
                    modelMap.put("aiRegisterDomain", aiRegisterDomain);
                    //操作日志
                    String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.ai.device.register.audit.button"), aiRegisterDomain.getSaiCode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/aiRegister/aiRegister-toAudit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

    /**
     * 审核设备注册信息
     * @param deviceRegister
     * @return
     */
    @RequestMapping("/aiRegister/audit")
    public @ResponseBody
    ResponseVo<String> audit(DeviceRegister deviceRegister) {
        try {
            String msg = deviceRegisterService.updateState(deviceRegister);//根据页面对象修改设备审核状态
            //操作日志
            if (StringUtils.isNotBlank(deviceRegister.getSdeviceId())) {
                AiInfo aiInfo = aiInfoService.selectByPrimaryKey(deviceRegister.getSdeviceId());
                if (null != aiInfo) {
                    String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.ai.device.register.audit"), aiInfo.getSaiCode());
                    LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                }
            }
            return ResponseVo.getSuccessResponse("success");
        } catch (ServiceException e) {
            logger.error("审核AI设备注册信息出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("审核AI设备注册信息出现Exception异常：{}",e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.aidevice.register.error"));
    }

    /**
     * 设备注册审核按钮跳转页面
     *
     * @param sid
     * @return
     */
    @RequestMapping("/aiRegister/toEdit")
    public String registerToEdit(String sid, ModelMap modelMap) {
        try {
            //数据库查询该设备信息
            if (StringUtils.isNotBlank(sid)) {
                AiRegisterDomain aiRegisterDomain = deviceRegisterService.selectAiViewBySid(sid);
                if (aiRegisterDomain != null) {

                    //重新获取注册码
                    String strIp = InviteCodeUtil.buildDeviceRegisterCode();
                    aiRegisterDomain.setSregIp(strIp);
                    modelMap.put("aiRegisterDomain", aiRegisterDomain);
                    //操作日志
                    String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.ai.device.register.edit.button"), aiRegisterDomain.getSaiCode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/aiRegister/aiRegister-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑设备注册码信息
     * @param deviceRegister
     * @return
     */
    @RequestMapping("/aiRegister/edit")
    public @ResponseBody ResponseVo<String> registerEdit(DeviceRegister deviceRegister) {
        try {
            deviceRegisterService.updateStateByEdit(deviceRegister);//根据页面对象修改设备状态为待审核
            //操作日志
            if (StringUtils.isNotBlank(deviceRegister.getSdeviceId())) {
                AiInfo aiInfo = aiInfoService.selectByPrimaryKey(deviceRegister.getSdeviceId());
                if (null != aiInfo) {
                    String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.ai.device.edit.register.code"), aiInfo.getSaiCode());
                    LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                }
            }
            return ResponseVo.getSuccessResponse("success");
        } catch (ServiceException e) {
            logger.error("编辑AI设备注册码出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("编辑AI设备注册码出现Exception异常：{}",e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.aidevice.eidt.error"));
    }

}
