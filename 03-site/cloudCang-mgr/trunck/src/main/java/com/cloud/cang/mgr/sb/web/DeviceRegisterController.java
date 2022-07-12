package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.InviteCodeUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.domain.DeviceRegisterDomain;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.service.DeviceRegisterService;
import com.cloud.cang.mgr.sb.vo.DeviceRegisterVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.DeviceInfo;
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
 * @ClassName DeviceRegisterController
 * @Description 设备注册管理controller
 * @Author zengzexiong
 * @Date 2018年6月4日09:32:00
 */
@Controller
@RequestMapping("/device")
public class DeviceRegisterController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceRegisterController.class);

    @Autowired
    OperatorService operatorService;

    @Autowired
    DeviceRegisterService deviceRegisterService;//2.设备注册信息

    @Autowired
    DeviceInfoService deviceInfoService;

     /* ----------2.设备注册信息开始 ----------*/
    /**
     * 设备注册信息列表
     * @return
     */
    @RequestMapping("/register/list")
    public String registerList() {
        return "sb/deviceRegister/deviceRegister-list";
    }

    /**
     * 设备注册信息列表数据
     * @param deviceRegisterVo 初始化页面对象
     * @param paramPage 初始化分页对象
     * @return
     */
    @RequestMapping("/register/queryData")
    @ResponseBody
    public PageListVo<List<DeviceRegisterDomain>> queryData(DeviceRegisterVo deviceRegisterVo, ParamPage paramPage) {
        PageListVo<List<DeviceRegisterDomain>> pageListVo = new PageListVo<List<DeviceRegisterDomain>>();//返回的页面对象
        Page<DeviceRegisterDomain> page = new Page<DeviceRegisterDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        deviceRegisterVo.setIdelFlag(0);/* 是否删除0否1是 */

        if (null == deviceRegisterVo) {
            deviceRegisterVo = new DeviceRegisterVo();
        }

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator,180);
        deviceRegisterVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceRegisterVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }

        //分页查询
        page = deviceRegisterService.selectPageByDomainWhere(page, deviceRegisterVo);


        //将分页查询结果转换成页面List集合
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    @RequestMapping("/register/toView")
    public String registertoView(String sid,ModelMap map) {
        try {
            //数据库查询该设备信息
            if (StringUtils.isNotBlank(sid)) {
                DeviceRegisterDomain deviceRegisterDomain = deviceRegisterService.selectViewBySid(sid);
                if (deviceRegisterDomain != null) {
                    map.put("deviceRegisterDomain", deviceRegisterDomain);
                    //操作日志
                    String logText = MessageFormat.format("查询设备注册信息，设备编号{0}", deviceRegisterDomain.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/deviceRegister/deviceRegister-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}",e);
        }
        return ExceptionUtil.to404();
    }


    /**
     * 设备注册审核按钮跳转页面
     *
     * @param sid
     * @return
     */
    @RequestMapping("/register/toAudit")
    public String registerToCheck(String sid, ModelMap modelMap) {
        try {
            //数据库查询该设备信息
            if (StringUtils.isNotBlank(sid)) {
                DeviceRegisterDomain deviceRegisterDomain = deviceRegisterService.selectViewBySid(sid);
                if (deviceRegisterDomain != null) {
                    modelMap.put("deviceRegisterDomain", deviceRegisterDomain);
                    //操作日志
                    String logText = MessageFormat.format("设备注册信息审核按钮，设备编号{0}", deviceRegisterDomain.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/deviceRegister/deviceRegister-toAudit";
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
    @RequestMapping("/register/audit")
    public @ResponseBody
    ResponseVo<String> audit(DeviceRegister deviceRegister) {
        try {
            String msg = deviceRegisterService.updateState(deviceRegister);//根据页面对象修改设备审核状态
            //操作日志
            if (StringUtils.isNotBlank(deviceRegister.getSdeviceId())) {
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceRegister.getSdeviceId());
                if (null != deviceInfo) {
                    String logText = MessageFormat.format("审核设备注册信息，设备编号{0}", deviceInfo.getScode());
                    LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                }
            }
            return ResponseVo.getSuccessResponse("success");
        } catch (ServiceException e) {
            logger.error("审核设备注册信息出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("审核设备注册信息出现Exception异常：{}",e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核设备注册信息异常");
    }

    /**
     * 设备注册审核按钮跳转页面
     *
     * @param sid
     * @return
     */
    @RequestMapping("/register/toEdit")
    public String registerToEdit(String sid, ModelMap modelMap) {
        try {
            //数据库查询该设备信息
            if (StringUtils.isNotBlank(sid)) {
                DeviceRegisterDomain deviceRegisterDomain = deviceRegisterService.selectViewBySid(sid);
                if (deviceRegisterDomain != null) {
                    deviceRegisterDomain.setOldReqIp(deviceRegisterDomain.getSregIp());//原始的注册码

                    //重新获取注册码
                    String strIp = InviteCodeUtil.buildDeviceRegisterCode();
                    deviceRegisterDomain.setSregIp(strIp);
                    modelMap.put("deviceRegisterDomain", deviceRegisterDomain);
                    //操作日志
                    String logText = MessageFormat.format("设备注册信息编辑按钮，设备编号{0}", deviceRegisterDomain.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/deviceRegister/deviceRegister-toEdit";
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
    @RequestMapping("/register/edit")
    public @ResponseBody ResponseVo<String> registerEdit(DeviceRegister deviceRegister) {
        try {
            String msg = deviceRegisterService.updateStateByEdit(deviceRegister);//根据页面对象修改设备状态为待审核
            //操作日志
            if (StringUtils.isNotBlank(deviceRegister.getSdeviceId())) {
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceRegister.getSdeviceId());
                if (null != deviceInfo) {
                    String logText = MessageFormat.format("编辑设备注册码，设备编号{0}", deviceInfo.getScode());
                    LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                }
            }
            return ResponseVo.getSuccessResponse("success");
        } catch (ServiceException e) {
            logger.error("编辑设备注册码出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("编辑设备注册码出现Exception异常：{}",e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("编辑设备注册码service异常");
    }

    /* ----------2.设备注册信息结束 ----------*/


}
