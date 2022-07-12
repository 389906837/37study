package com.cloud.cang.mgr.tp.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.tp.domain.DeviceManageTemplateDomain;
import com.cloud.cang.mgr.tp.service.DeviceManageTemplateService;
import com.cloud.cang.mgr.tp.vo.DeviceManageTemplateVo;
import com.cloud.cang.model.tp.DeviceManageTemplate;
import com.cloud.cang.model.tp.DeviceModelTemplate;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
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
 * @ClassName DeviceManageTemplateController
 * @Description 设备管理信息模板
 * @Author zengzexiong
 * @Date 2019年3月5日13:41:14
 */
@Controller
@RequestMapping("/deviceManageTemplate")
public class DeviceManageTemplateController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceManageTemplateController.class);


    @Autowired
    private DeviceManageTemplateService deviceManageTemplateService;  // 设备管理信息模板


    /**
     * 设备详细信息模板
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "tp/deviceManage/deviceManageTemplate-list";
    }


    /**
     * 分页查询设备管理信息模板
     *
     * @param paramPage
     * @param deviceManageTemplateVo
     * @return
     */
    @RequestMapping("/list/queryData")
    @ResponseBody
    public PageListVo<List<DeviceManageTemplateDomain>> queryCommodityData(ParamPage paramPage, DeviceManageTemplateVo deviceManageTemplateVo) {
        logger.debug("<===分页查询设备管理信息模板开始===>");
        PageListVo<List<DeviceManageTemplateDomain>> pageListVo;
        pageListVo = new PageListVo<List<DeviceManageTemplateDomain>>();
        Page<DeviceManageTemplateDomain> page = new Page<DeviceManageTemplateDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == deviceManageTemplateVo) {
            deviceManageTemplateVo = new DeviceManageTemplateVo();
        }
        deviceManageTemplateVo.setIdelFlag(0);/* 是否删除0否1是 */

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceManageTemplateVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        //分页查询
        page = deviceManageTemplateService.selectPageByDomainWhere(page, deviceManageTemplateVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        logger.debug("<===分页查询设备管理信息模板结束===>", pageListVo);
        return pageListVo;
    }

    /**
     * 添加设备管理信息模板按钮
     *
     * @param map
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(ModelMap map) {
        DeviceManageTemplate deviceManageTemplate = new DeviceManageTemplate();
        map.put("deviceManageTemplate", deviceManageTemplate);
        return "tp/deviceManage/deviceManageTemplate-toAdd";
    }

    /**
     * 添加设备管理信息模板
     *
     * @param deviceManageTemplate
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseVo<DeviceModelTemplate> commodityAdd(DeviceManageTemplate deviceManageTemplate) {
        try {
            String sname = deviceManageTemplate.getSname();
            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
            if (StringUtils.isBlank(sname)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("tpcon.template.name.empty",null));
            }
            deviceManageTemplate.setSmerchantId(merchantId);
            deviceManageTemplate.setSmerchantCode(merchantCode);
            deviceManageTemplate.setIdelFlag(0);/* 是否删除0否1是 */
            deviceManageTemplate.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
            deviceManageTemplate.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人人
            deviceManageTemplate.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
            deviceManageTemplate.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            ResponseVo<DeviceModelTemplate> responseVo = deviceManageTemplateService.insertTemplate(deviceManageTemplate);
            if (BooleanUtils.isTrue(responseVo.isSuccess())) {
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("tpcon.add.device.manage.info.template",null)+"，"+MessageSourceUtils.getMessageByKey("main.name",null)+"{0}", sname);
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            }
            return responseVo;
        } catch (ServiceException e) {
            logger.error("添加设备管理信息模板出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("添加设备管理信息模板出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("tpcon.add.device.manage.info.template.error",null));
    }

    /**
     * 删除设备管理信息模板信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseVo<String> delete(String[] checkboxId) {
        logger.debug("删除设备管理信息模板开始");
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                ResponseVo<String> responseVo = deviceManageTemplateService.deleteByLogic(checkboxId);
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("tpcon.delete.device.manage.info.template",null), "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                logger.debug("删除设备管理信息模板结束");
                return responseVo;
            }
        } catch (ServiceException e) {
            logger.info("品牌被视觉商品使用，无法删除");
        } catch (Exception e) {
            logger.error("删除设备管理信息模板出现ServiceException异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("tpcon.delete.device.manage.info.template.error",null));
    }

    /**
     * 设备管理信息模板编辑按钮
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/toEdit")
    public String toEdit(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                DeviceManageTemplate deviceManageTemplate = deviceManageTemplateService.selectByPrimaryKey(sid);
                if (null != deviceManageTemplate) {
                    map.put("deviceManageTemplate", deviceManageTemplate);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("tpcon.edit.device.manage.info.template",null)+"{0}", deviceManageTemplate.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("设备管理信息模板按钮结束");
                    return "tp/deviceManage/deviceManageTemplate-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑设备管理信息模板
     *
     * @param deviceManageTemplate
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseVo<DeviceManageTemplate> edit(DeviceManageTemplate deviceManageTemplate) {
        logger.debug("编辑设备管理信息模板开始");
        String sid = deviceManageTemplate.getId();
        String sname = deviceManageTemplate.getSname();
        if (StringUtils.isBlank(sid) && StringUtils.isBlank(sname)) {
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("tpcon.device.manage.info.template.name.empty",null));
        }
        try {
            deviceManageTemplate.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人人
            deviceManageTemplate.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */

            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
            DeviceManageTemplate deviceManageTemplateVo = new DeviceManageTemplate();
            deviceManageTemplateVo.setSmerchantId(merchantId);
            deviceManageTemplateVo.setSmerchantCode(merchantCode);
            deviceManageTemplateVo.setSname(sname);
            deviceManageTemplateVo.setIdelFlag(0);

            ResponseVo<DeviceManageTemplate> responseVo = deviceManageTemplateService.updateBySelectiveVo(deviceManageTemplate, deviceManageTemplateVo);
            //操作日志
            String logText1 = MessageFormat.format(MessageSourceUtils.getMessageByKey("tpcon.modify.device.manage.info.template",null)+"，"+ MessageSourceUtils.getMessageByKey("main.code",null)+"{0}", responseVo.getData().getScode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
            logger.debug("编辑设备管理信息模板信息结束", deviceManageTemplate.getScode());
            return responseVo;
        } catch (ServiceException e) {
            logger.error("编辑设备管理信息模板信息出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("编辑设备管理信息模板信息出现Exception异常：{}", e);
        }

        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("tpcon.modify.device.manage.info.template.error",null));
    }

    /**
     * 查看设备管理信息模板页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/view")
    public String brandInfoView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                DeviceManageTemplate deviceManageTemplate = deviceManageTemplateService.selectByPrimaryKey(sid);
                if (null != deviceManageTemplate) {
                    map.put("deviceManageTemplate", deviceManageTemplate);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("tpcon.view.device.manage.info.template",null)+"，"+MessageSourceUtils.getMessageByKey("main.name",null)+"{0}", deviceManageTemplate.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("查看设备管理信息模板详情页面结束", deviceManageTemplate.getScode());
                    return "tp/deviceManage/deviceManageTemplate-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 编辑设备详细信息模板状态
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/changeStatus")
    @ResponseBody
    public ResponseVo<String> changeStatus(String[] checkboxId) {
        if (ArrayUtils.isEmpty(checkboxId)) {
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("main.status.error",null));
        }
        try {
            ResponseVo<String> responseVo = deviceManageTemplateService.changeStatus(checkboxId);
            //操作日志
            String logText1 = MessageFormat.format(MessageSourceUtils.getMessageByKey("tpcon.modify.device.manage.info.template.status",null)+"，"+MessageSourceUtils.getMessageByKey("main.code",null)+"{0}", responseVo.getData());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
            return responseVo;
        } catch (ServiceException e) {
            logger.error("编辑设备详细信息模板状态信息出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("编辑设备详细信息模板状态信息出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("tpcon.modify.device.manage.info.template.status.error",null));
    }


    @RequestMapping("/getManageTemplate")
    @ResponseBody
    public ResponseVo<DeviceManageTemplate> getManageTemplate(String pid) {
        if (StringUtils.isEmpty(pid)) {
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("main.query.error",null));
        }
        DeviceManageTemplate deviceManageTemplate = deviceManageTemplateService.selectByPrimaryKey(pid);
        if (null != deviceManageTemplate) {
            return ResponseVo.getSuccessResponse(deviceManageTemplate);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("main.query.error",null));
    }


}
