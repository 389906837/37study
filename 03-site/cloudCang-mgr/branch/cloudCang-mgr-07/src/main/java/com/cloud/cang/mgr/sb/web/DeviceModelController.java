package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.domain.DeviceModelDomain;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.service.DeviceModelService;
import com.cloud.cang.mgr.sb.vo.DeviceModelVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceModel;
import com.cloud.cang.model.sys.Operator;
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

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.List;

/**
 * @version 1.0
 * @ClassName DeviceController
 * @Description 设备详细信息controller
 * @Author zengzexiong
 * @Date 2018年6月4日09:29:57
 */
@Controller
@RequestMapping("/device")
public class DeviceModelController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceCommodityController.class);

    @Autowired
    OperatorService operatorService;

    @Autowired
    DeviceInfoService deviceInfoService;//0.设备基础信息

    @Autowired
    DeviceModelService deviceModelService;//3.设备详细信息


    /* ----------3.设备详细信息开始 ----------*/

    /**
     * 设备信息列表
     *
     * @return
     */
    @RequestMapping("/model/list")
    public String modelList() {
        return "sb/deviceModel/deviceModel-list";
    }

    /**
     * 设备详细信息列表数据
     * @param deviceModelVo 初始化页面对象
     * @param paramPage 初始化分页对象
     * @return
     */
    @RequestMapping("/model/queryData")
    @ResponseBody
    public PageListVo<List<DeviceModelDomain>> queryData(DeviceModelVo deviceModelVo, ParamPage paramPage) {
        PageListVo<List<DeviceModelDomain>> pageListVo = new PageListVo<List<DeviceModelDomain>>();//返回的页面对象
        Page<DeviceModelDomain> page = new Page<DeviceModelDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        if (null == deviceModelVo) {
            deviceModelVo = new DeviceModelVo();
        }

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator,180);
        deviceModelVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceModelVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }

        //分页查询
        page = deviceModelService.selectPageByDomainWhere(page, deviceModelVo);


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
     * 根据设备详细信息表ID查询设备信息，商户信息
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/deviceModel/toView")
    public String modeltoView(String sid,ModelMap map) {
        try {
            //数据库查询该设备详细信息
            if (StringUtils.isNotBlank(sid)) {
                DeviceModel deviceModel = deviceModelService.selectByPrimaryKey(sid);
                if (deviceModel != null) {
                    map.put("deviceModel", deviceModel);
                    //操作日志
                    DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceModel.getSdeviceId());
                    String logText = MessageFormat.format("查询设备详细信息，设备编号{0}", deviceInfo.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/deviceModel/deviceModel-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 设备详细信息按钮跳转页面
     *
     * @param sid
     * @return
     */
    @RequestMapping("/deviceModel/toEdit")
    public String deviceModeltoEdit(String sid, ModelMap modelMap) {
        try {
            //数据库查询该设备信息
            if (StringUtils.isNotBlank(sid)) {
                DeviceModel deviceModel = deviceModelService.selectByPrimaryKey(sid);
                if (deviceModel != null) {
                    modelMap.put("deviceModel", deviceModel);
                    //操作日志
                    DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceModel.getSdeviceId());
                    String logText = MessageFormat.format("设备详细信息按钮，设备编号{0}", deviceInfo.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/deviceModel/deviceModel-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑设备详细信息
     * @param deviceModel
     * @return
     */
    @RequestMapping("/deviceModel/edit")
    public @ResponseBody
    ResponseVo<DeviceModel> deviceModelEdit(DeviceModel deviceModel, HttpServletRequest request) {
        try {
            String id = deviceModel.getId();//ID
            String sbId = deviceModel.getSdeviceId();//设备ID
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(sbId)) {
                String scameraMethod1 = request.getParameter("scameraMethod1");
                if (StringUtil.isNotBlank(scameraMethod1)) {
                    deviceModel.setScameraMethod(scameraMethod1);
                }
                deviceModel.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
                deviceModel.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 修改人 */
                /*  修改数据入库 SB_DEVICE_MODEL   */
                deviceModelService.updateBySelectiveVo(deviceModel);
                //操作日志
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(sbId);
                if (null != deviceInfo) {
                    String logText = MessageFormat.format("编辑设备详细信息，设备编号{0}", deviceInfo.getScode());
                    LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                }
                return ResponseVo.getSuccessResponse(deviceModel);
            }
        } catch (ServiceException e) {
            logger.error("编辑设备详细信息出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("编辑设备详细信息出现Exception异常：{}",e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("编辑设备详细信息service异常");
    }

    /**
     * 根据摄像头方法查询摄像头其他信息
     *
     * @return
     */
    @RequestMapping("/deviceModel/getCaremaInfo")
    @ResponseBody
    public ResponseVo<String> getCaremaInfo(String method) {
        try {
            String caremaInfo = "";
            if (StringUtil.isNotBlank(method)) {
                caremaInfo = deviceModelService.getCaremaInfoByMethod(method); // 根据摄像头方法查询摄像头其他信息
            }
            return ResponseVo.getSuccessResponse(caremaInfo);
        } catch (Exception e) {
            logger.error("根据摄像头方法查询摄像头其他信息异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo();
    }

    /* ----------3.设备详细信息结束 ----------*/


}
