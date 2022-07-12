package com.cloud.cang.mgr.tp.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.tp.domain.DeviceModelTemplateDomain;
import com.cloud.cang.mgr.tp.service.DeviceModelTemplateService;
import com.cloud.cang.mgr.tp.vo.DeviceModelTemplateVo;
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
 * @ClassName DeviceModelTemplateController
 * @Description 设备详细信息模板
 * @Author zengzexiong
 * @Date 2019年3月4日17:23:43
 */
@Controller
@RequestMapping("/deviceModelTemplate")
public class DeviceModelTemplateController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceModelTemplateController.class);


    @Autowired
    private DeviceModelTemplateService deviceModelTemplateService;  // 设备详细信息模板


    /**
     * 设备详细信息模板
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "tp/deviceModel/deviceModelTemplate-list";
    }


    /**
     * 分页查询设备详细信息模板
     *
     * @param paramPage
     * @param deviceModelTemplateVo
     * @return
     */
    @RequestMapping("/list/queryData")
    @ResponseBody
    public PageListVo<List<DeviceModelTemplateDomain>> queryCommodityData(ParamPage paramPage, DeviceModelTemplateVo deviceModelTemplateVo) {
        logger.debug("<===分页查询设备详细信息模板开始===>");
        PageListVo<List<DeviceModelTemplateDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<DeviceModelTemplateDomain>>();
        Page<DeviceModelTemplateDomain> page = new Page<DeviceModelTemplateDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == deviceModelTemplateVo) {
            deviceModelTemplateVo = new DeviceModelTemplateVo();
        }
        deviceModelTemplateVo.setIdelFlag(0);/* 是否删除0否1是 */

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceModelTemplateVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        //分页查询
        page = deviceModelTemplateService.selectPageByDomainWhere(page, deviceModelTemplateVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        logger.debug("<===分页查询设备详细信息模板结束===>", pageListVo);
        return pageListVo;
    }

    /**
     * 添加设备详细信息模板按钮
     *
     * @param map
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(ModelMap map) {
        DeviceModelTemplate deviceModelTemplate = new DeviceModelTemplate();
        map.put("deviceModelTemplate", deviceModelTemplate);
        return "tp/deviceModel/deviceModelTemplate-toAdd";
    }

    /**
     * 添加设备详细信息模板
     *
     * @param deviceModelTemplate
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseVo<DeviceModelTemplate> commodityAdd(DeviceModelTemplate deviceModelTemplate) {
        try {
            String sname = deviceModelTemplate.getSname();
            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
            if (StringUtils.isBlank(sname)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("模板名称不能为空");
            }
            deviceModelTemplate.setSmerchantId(merchantId);
            deviceModelTemplate.setSmerchantCode(merchantCode);
            deviceModelTemplate.setIdelFlag(0);/* 是否删除0否1是 */
            deviceModelTemplate.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
            deviceModelTemplate.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人人
            deviceModelTemplate.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
            deviceModelTemplate.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            ResponseVo<DeviceModelTemplate> responseVo = deviceModelTemplateService.insertTemplate(deviceModelTemplate);
            if (BooleanUtils.isTrue(responseVo.isSuccess())) {
                //操作日志
                String logText = MessageFormat.format("新增设备详细信息模板，名称{0}", sname);
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            }
            return responseVo;
        } catch (ServiceException e) {
            logger.error("添加设备详细信息模板出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("添加设备详细信息模板出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("添加设备详细信息模板出错");
    }

    /**
     * 删除设备详细信息模板信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseVo<String> delete(String[] checkboxId) {
        logger.debug("删除设备详细信息模板开始");
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                ResponseVo<String> responseVo = deviceModelTemplateService.deleteByLogic(checkboxId);
                //操作日志
                String logText = MessageFormat.format("删除设备详细信息模板信息", "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                logger.debug("删除设备详细信息模板结束");
                return responseVo;
            }
        } catch (ServiceException e) {
            logger.info("品牌被视觉商品使用，无法删除");
        } catch (Exception e) {
            logger.error("删除设备详细信息模板出现ServiceException异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除设备详细信息模板失败！");
    }

    /**
     * 设备详细信息模板编辑按钮
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/toEdit")
    public String toEdit(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                DeviceModelTemplate deviceModelTemplate = deviceModelTemplateService.selectByPrimaryKey(sid);
                if (null != deviceModelTemplate) {
                    map.put("deviceModelTemplate", deviceModelTemplate);
                    String logText4 = MessageFormat.format("设备详细信息模板,模板编号{0}", deviceModelTemplate.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("设备详细信息模板按钮结束");
                    return "tp/deviceModel/deviceModelTemplate-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑设备详细信息模板
     *
     * @param deviceModelTemplate
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseVo<DeviceModelTemplate> edit(DeviceModelTemplate deviceModelTemplate) {
        logger.debug("编辑设备详细信息模板开始");
        String sid = deviceModelTemplate.getId();
        String sname = deviceModelTemplate.getSname();
        if (StringUtils.isBlank(sid) && StringUtils.isBlank(sname)) {
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("该设备详细信息模板名称不能为空");
        }
        try {
            deviceModelTemplate.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人人
            deviceModelTemplate.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */

            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
            DeviceModelTemplate deviceModelTemplateVo = new DeviceModelTemplate();
            deviceModelTemplateVo.setSmerchantId(merchantId);
            deviceModelTemplateVo.setSmerchantCode(merchantCode);
            deviceModelTemplateVo.setSname(sname);
            deviceModelTemplateVo.setIdelFlag(0);

            ResponseVo<DeviceModelTemplate> responseVo = deviceModelTemplateService.updateBySelectiveVo(deviceModelTemplate, deviceModelTemplateVo);
            //操作日志
            String logText1 = MessageFormat.format("修改设备详细信息模板信息，编号{0}", responseVo.getData().getScode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
            logger.debug("编辑设备详细信息模板信息结束", deviceModelTemplate.getScode());
            return responseVo;
        } catch (ServiceException e) {
            logger.error("编辑设备详细信息模板信息出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("编辑设备详细信息模板信息出现Exception异常：{}", e);
        }

        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备详细信息模板失败！");
    }

    /**
     * 查看设备详细信息模板页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/view")
    public String brandInfoView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                DeviceModelTemplate deviceModelTemplate = deviceModelTemplateService.selectByPrimaryKey(sid);
                if (null != deviceModelTemplate) {
                    map.put("deviceModelTemplate", deviceModelTemplate);
                    String logText4 = MessageFormat.format("查看设备详细信息模板信息，名称{0}", deviceModelTemplate.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("查看设备详细信息模板详情页面结束", deviceModelTemplate.getScode());
                    return "tp/deviceModel/deviceModelTemplate-view";
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
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("状态错误");
        }
        try {
            ResponseVo<String> responseVo = deviceModelTemplateService.changeStatus(checkboxId);
            //操作日志
            String logText1 = MessageFormat.format("修改设备详细信息模板状态信息，编号{0}", responseVo.getData());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
            return responseVo;
        } catch (ServiceException e) {
            logger.error("编辑设备详细信息模板状态信息出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("编辑设备详细信息模板状态信息出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("编辑设备详细信息模板状态失败！");
    }


    @RequestMapping("/getModelTemplate")
    @ResponseBody
    public ResponseVo<DeviceModelTemplate> getModelTemplate(String pid) {
        if (StringUtils.isEmpty(pid)) {
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("查询出错");
        }
        DeviceModelTemplate deviceModelTemplate = deviceModelTemplateService.selectByPrimaryKey(pid);
        if (null != deviceModelTemplate) {
            return ResponseVo.getSuccessResponse(deviceModelTemplate);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("查询出错！");
    }


}
