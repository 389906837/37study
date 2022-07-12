package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sb.domain.DeviceManageDomain;
import com.cloud.cang.mgr.sb.domain.DeviceModelDomain;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.service.DeviceManageService;
import com.cloud.cang.mgr.sb.vo.DeviceManageVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceManage;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.Set;

/**
 * @version 1.0
 * @ClassName DeviceManageController
 * @Description 设备负责人controller
 * @Author zengzexiong
 * @Date 2018年6月4日09:29:37
 */
@Controller
@RequestMapping("/device")
public class DeviceManageController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceManageController.class);


    @Autowired
    OperatorService operatorService;


    @Autowired
    DeviceInfoService deviceInfoService;//0.设备基础信息

    @Autowired
    DeviceManageService deviceManageService;//1.设备管理信息


     /* ----------1.设备管理信息（负责人）开始 ----------*/

    /**
     * 设备管理（负责人）信息列表
     *
     * @return
     */
    @RequestMapping("/manage/list")
    public String manageList(ModelMap modelMap) {
        Set<ParameterGroupDetail> parameterGroupDetailList = GrpParaUtil.get(GrpParaUtil.DEVICE_MANAGER_AREA);
        if (CollectionUtils.isNotEmpty(parameterGroupDetailList)) {
            modelMap.put("dic", parameterGroupDetailList);
        }
        return "sb/deviceManage/deviceManage-list";
    }

    /**
     * 设备管理（负责人）列表数据
     * @param deviceManageVo 初始化页面对象
     * @param paramPage 初始化分页对象
     * @return
     */
    @RequestMapping("/manage/queryData")
    @ResponseBody
    public PageListVo<List<DeviceModelDomain>> queryData(DeviceManageVo deviceManageVo, ParamPage paramPage) {
        PageListVo<List<DeviceModelDomain>> pageListVo = new PageListVo<List<DeviceModelDomain>>();//返回的页面对象
        Page<DeviceModelDomain> page = new Page<DeviceModelDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        if (null == deviceManageVo) {
            deviceManageVo = new DeviceManageVo();
        }

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator,50);
        deviceManageVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceManageVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }

        //分页查询
        page = deviceManageService.selectPageByDomainWhere(page, deviceManageVo);


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
     * 根据设备管理（负责人）表ID查询设备管理（负责人）信息，设备编号名称，商户编号名称
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/manage/toView")
    public String managetoView(String sid,ModelMap map) {
        try {
            //数据库查询该设备管理（负责人）信息
            if (StringUtils.isNotBlank(sid)) {
                DeviceManageDomain deviceManageDomain = deviceManageService.selectViewBySid(sid);
                if (deviceManageDomain != null) {
                    // 设备区域
                    if (StringUtils.isNotBlank(deviceManageDomain.getSareaCode())) {
                        String areaName = GrpParaUtil.getName(GrpParaUtil.DEVICE_OPERATE_AREA, deviceManageDomain.getSareaCode());
                        deviceManageDomain.setSareaCode(areaName);
                    }
                    map.put("deviceManage", deviceManageDomain);
                    //操作日志
                    String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.manage.to.view.device.manage")+ MessageSourceUtils.getMessageByKey("main.code",null)+"{0}", deviceManageDomain.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/deviceManage/deviceManage-view";
                }
            }
        }catch (Exception e) {
            logger.error("跳转页面异常：{}",e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 设备管理（负责人）编辑按钮跳转页面
     *
     * @param sid
     * @return
     */
    @RequestMapping("/manage/toEdit")
    public String managetoEdit(String sid, ModelMap modelMap) {
        try {
            //数据库查询该设备信息
            if (StringUtils.isNotBlank(sid)) {
                DeviceManageDomain deviceManageDomain = deviceManageService.selectViewBySid(sid);
                if (deviceManageDomain != null) {
                    modelMap.put("deviceManageDomain", deviceManageDomain);
                    //操作日志
                    String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.manage.to.view.device.manage1"), deviceManageDomain.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/deviceManage/deviceManage-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑设备管理（负责人）
     * @param deviceManage
     * @return
     */
    @RequestMapping("/manage/edit")
    public @ResponseBody
    ResponseVo<DeviceManage> manageEdit(DeviceManage deviceManage) {
        try {
            String id = deviceManage.getId();//ID
            String sbId = deviceManage.getSdeviceId();//设备ID
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(sbId)) {
                deviceManage.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
                deviceManage.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 修改人 */
                /*  修改数据入库 SB_DEVICE_MANAGE   */
                deviceManageService.updateBySelectiveVO1(deviceManage);
                //操作日志
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(sbId);
                if (null != deviceInfo) {
                    String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.manage.to.edit.device.manage"), deviceInfo.getScode());
                    LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                }
                return ResponseVo.getSuccessResponse(deviceManage);
            }
        } catch (ServiceException e) {
            logger.error("编辑设备管理（负责人）信息出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("编辑设备管理（负责人）信息出现Exception异常：{}",e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.device.edit.service.error"));
    }
    /* ----------1.设备管理（负责人）信息结束 ----------*/

}
