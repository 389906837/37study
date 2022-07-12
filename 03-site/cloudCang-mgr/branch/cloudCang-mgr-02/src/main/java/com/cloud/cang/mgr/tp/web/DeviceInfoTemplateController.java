package com.cloud.cang.mgr.tp.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.tp.domain.DeviceInfoTemplateDomain;
import com.cloud.cang.mgr.tp.service.DeviceInfoTemplateService;
import com.cloud.cang.mgr.tp.vo.DeviceInfoTemplateVo;
import com.cloud.cang.model.tp.DeviceInfoTemplate;
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
 * @ClassName DeviceInfoTemplateController
 * @Description 设备基础信息模板
 * @Author zengzexiong
 * @Date 2019年3月4日19:22:25
 */
@Controller
@RequestMapping("/deviceInfoTemplate")
public class DeviceInfoTemplateController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceModelTemplateController.class);


    @Autowired
    private DeviceInfoTemplateService deviceInfoTemplateService;  // 设备基础信息模板


    /**
     * 设备基础信息模板
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "tp/deviceInfo/deviceInfoTemplate-list";
    }


    /**
     * 分页查询设备基础信息模板
     *
     * @param paramPage
     * @param deviceInfoTemplateVo
     * @return
     */
    @RequestMapping("/list/queryData")
    @ResponseBody
    public PageListVo<List<DeviceInfoTemplateDomain>> queryCommodityData(ParamPage paramPage, DeviceInfoTemplateVo deviceInfoTemplateVo) {
        logger.debug("<===分页查询设备基础信息模板开始===>");
        PageListVo<List<DeviceInfoTemplateDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<DeviceInfoTemplateDomain>>();
        Page<DeviceInfoTemplateDomain> page = new Page<DeviceInfoTemplateDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == deviceInfoTemplateVo) {
            deviceInfoTemplateVo = new DeviceInfoTemplateVo();
        }
        deviceInfoTemplateVo.setIdelFlag(0);/* 是否删除0否1是 */

        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceInfoTemplateVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        //分页查询
        page = deviceInfoTemplateService.selectPageByDomainWhere(page, deviceInfoTemplateVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        logger.debug("<===分页查询设备基础信息模板结束===>", pageListVo);
        return pageListVo;
    }

    /**
     * 添加设备基础信息模板按钮
     *
     * @param map
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(ModelMap map) {
        DeviceInfoTemplate deviceInfoTemplate = new DeviceInfoTemplate();
        map.put("deviceInfoTemplate", deviceInfoTemplate);
        return "tp/deviceInfo/deviceInfoTemplate-toAdd";
    }

    /**
     * 添加设备基础信息模板
     *
     * @param deviceInfoTemplate
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseVo<DeviceInfoTemplate> commodityAdd(DeviceInfoTemplate deviceInfoTemplate) {
        try {
            String sname = deviceInfoTemplate.getSname();
            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
            if (StringUtils.isBlank(sname)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("模板名称不能为空");
            }
            deviceInfoTemplate.setSmerchantId(merchantId);
            deviceInfoTemplate.setSmerchantCode(merchantCode);
            deviceInfoTemplate.setIdelFlag(0);/* 是否删除0否1是 */
            deviceInfoTemplate.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
            deviceInfoTemplate.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人人
            deviceInfoTemplate.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
            deviceInfoTemplate.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            ResponseVo<DeviceInfoTemplate> responseVo = deviceInfoTemplateService.insertTemplate(deviceInfoTemplate);
            if (BooleanUtils.isTrue(responseVo.isSuccess())) {
                //操作日志
                String logText = MessageFormat.format("新增设备基础信息模板，名称{0}", sname);
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            }
            return responseVo;
        } catch (ServiceException e) {
            logger.error("添加设备基础信息模板出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("添加设备基础信息模板出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("添加设备详细信息模板出错");
    }

    /**
     * 删除设备基础信息模板信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseVo<String> delete(String[] checkboxId) {
        logger.debug("删除设备基础信息模板开始");
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                ResponseVo<String> responseVo = deviceInfoTemplateService.deleteByLogic(checkboxId);
                //操作日志
                String logText = MessageFormat.format("删除设备基础信息模板信息", "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                logger.debug("删除设备基础信息模板结束");
                return responseVo;
            }
        } catch (ServiceException e) {
            logger.info("删除设备基础信息模板信息失败");
        } catch (Exception e) {
            logger.error("删除设备基础信息模板出现ServiceException异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除设备详细信息模板失败！");
    }

    /**
     * 设备基础信息模板编辑按钮
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/toEdit")
    public String toEdit(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                DeviceInfoTemplate deviceInfoTemplate = deviceInfoTemplateService.selectByPrimaryKey(sid);
                if (null != deviceInfoTemplate) {
                    map.put("deviceInfoTemplate", deviceInfoTemplate);
                    String logText4 = MessageFormat.format("设备详细信息模板,模板编号{0}", deviceInfoTemplate.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("设备基础信息模板按钮结束");
                    return "tp/deviceInfo/deviceInfoTemplate-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑设备基础信息模板
     *
     * @param deviceInfoTemplate
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseVo<DeviceInfoTemplate> edit(DeviceInfoTemplate deviceInfoTemplate) {
        logger.debug("编辑设备基础信息模板开始");
        String sid = deviceInfoTemplate.getId();
        String sname = deviceInfoTemplate.getSname();
        if (StringUtils.isBlank(sid) && StringUtils.isBlank(sname)) {
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("该设备详细信息模板名称不能为空");
        }
        try {
            deviceInfoTemplate.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人人
            deviceInfoTemplate.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */

            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
            DeviceInfoTemplate deviceInfoTemplateVo = new DeviceInfoTemplate();
            deviceInfoTemplateVo.setSmerchantId(merchantId);
            deviceInfoTemplateVo.setSmerchantCode(merchantCode);
            deviceInfoTemplateVo.setSname(sname);
            deviceInfoTemplateVo.setIdelFlag(0);

            ResponseVo<DeviceInfoTemplate> responseVo = deviceInfoTemplateService.updateBySelectiveVo(deviceInfoTemplate, deviceInfoTemplateVo);
            //操作日志
            String logText1 = MessageFormat.format("修改设备基础信息模板信息，编号{0}", deviceInfoTemplate.getScode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
            logger.debug("编辑设备基础信息模板信息结束", deviceInfoTemplate.getScode());
            return responseVo;
        } catch (ServiceException e) {
            logger.error("编辑设备基础信息模板信息出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("编辑设备基础信息模板信息出现Exception异常：{}", e);
        }

        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备基础信息模板失败！");
    }

    /**
     * 查看设备基础信息模板页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/view")
    public String View(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                DeviceInfoTemplate deviceInfoTemplate = deviceInfoTemplateService.selectByPrimaryKey(sid);
                if (null != deviceInfoTemplate) {
                    map.put("deviceInfoTemplate", deviceInfoTemplate);
                    String logText4 = MessageFormat.format("查看设备基础信息模板信息，名称{0}", deviceInfoTemplate.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("查看设备基础信息模板详情页面结束", deviceInfoTemplate.getScode());
                    return "tp/deviceInfo/deviceInfoTemplate-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑设备基础信息模板状态
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/changeStatus")
    @ResponseBody
    public ResponseVo<String> changeStatus(String[] checkboxId) {
        if (ArrayUtils.isEmpty(checkboxId)) {
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("状态错误");
        }
        try {
            ResponseVo<String> responseVo = deviceInfoTemplateService.changeStatus(checkboxId);
            //操作日志
            String logText1 = MessageFormat.format("修改设备基础信息模板状态信息，编号{0}", responseVo.getData());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
            return responseVo;
        } catch (ServiceException e) {
            logger.error("编辑设备基础信息模板状态信息出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("编辑设备基础信息模板状态信息出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("编辑设备基础信息模板状态失败！");
    }


    /**
     *
     *
     * @param pid
     * @return
     */
    @RequestMapping("/getInfoTemplate")
    @ResponseBody
    public ResponseVo<DeviceInfoTemplate> getInfoTemplate(String pid) {
        if (StringUtils.isEmpty(pid)) {
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("查询出错");
        }
        DeviceInfoTemplate deviceInfoTemplate = deviceInfoTemplateService.selectByPrimaryKey(pid);
        if (null != deviceInfoTemplate) {
            return ResponseVo.getSuccessResponse(deviceInfoTemplate);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("查询出错！");
    }


}
