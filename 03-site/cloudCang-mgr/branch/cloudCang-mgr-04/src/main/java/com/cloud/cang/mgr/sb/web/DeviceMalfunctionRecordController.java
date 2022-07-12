package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.domain.DeviceMalfunctionRecordDomain;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.service.DeviceMalfunctionRecordService;
import com.cloud.cang.mgr.sb.service.DeviceMoveRecordService;
import com.cloud.cang.mgr.sb.vo.DeviceMalfunctionRecordVo;
import com.cloud.cang.mgr.sb.vo.DeviceMoveRecordVo;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceMalfunctionRecord;
import com.cloud.cang.model.sh.MerchantInfo;
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
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @ClassName DeviceMalfunctionRecordController
 * @Description 设备故障信息记录
 * @Author ChangTanchang
 * @Date 2018年6月13日13:47:20
 */
@Controller
@RequestMapping("/deviceMal")
public class DeviceMalfunctionRecordController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceMalfunctionRecordController.class);

    @Autowired
    OperatorService operatorService;

    @Autowired
    MerchantInfoService merchantInfoService;//商户信息

    @Autowired
    DeviceInfoService deviceInfoService;//设备基础信息

    @Autowired
    DeviceMalfunctionRecordService deviceMalfunctionRecordService; // 设备故障记录

    /**
     * 设备故障信息记录
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sb/deviceMalfunctionRecord-list";
    }

    /**
     * 设备故障信息记录列表数据
     *
     * @param deviceMalfunctionRecordVo 初始化页面对象
     * @param paramPage                 初始化分页对象
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<DeviceMalfunctionRecordDomain>> queryData(DeviceMalfunctionRecordVo deviceMalfunctionRecordVo, ParamPage paramPage) {
        PageListVo<List<DeviceMalfunctionRecordDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<DeviceMalfunctionRecordDomain>>();
        Page<DeviceMalfunctionRecordDomain> page = new Page<DeviceMalfunctionRecordDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == deviceMalfunctionRecordVo) {
            deviceMalfunctionRecordVo = new DeviceMalfunctionRecordVo();
        }
        deviceMalfunctionRecordVo.setIdelFlag(0);/* 是否删除0否1是 */
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 180);
        deviceMalfunctionRecordVo.setCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            if (paramPage.getSidx().equals("merchantName")) {
                deviceMalfunctionRecordVo.setOrderStr(" C.sname " + paramPage.getSord() + ",");
            } else {
                deviceMalfunctionRecordVo.setOrderStr(" A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
            }
        }

        //分页查询
        page = deviceMalfunctionRecordService.selectqueryData(page, deviceMalfunctionRecordVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 跳转添加/编辑页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap modelMap, String sid) {
        try {
            DeviceMalfunctionRecord deviceMalfunctionRecord = deviceMalfunctionRecordService.selectByPrimaryKey(sid);
            if (deviceMalfunctionRecord == null) {
                deviceMalfunctionRecord = new DeviceMalfunctionRecord();
            }
            MerchantInfo merchantInfo = merchantInfoService.selectOne(deviceMalfunctionRecord.getSmerchantId());//商户基础信息表
            DeviceInfo deviceInfo = deviceInfoService.selectBySid(deviceMalfunctionRecord.getSdeviceId());//设备基础信息表
            modelMap.put("deviceMalfunctionRecord", deviceMalfunctionRecord);
            modelMap.put("merchantInfo", merchantInfo);
            modelMap.put("deviceInfo", deviceInfo);
            return "sb/deviceMalfunctionRecord-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

    /**
     * 保存故障信息
     *
     * @param deviceMalfunctionRecord
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<DeviceMalfunctionRecord> save(DeviceMalfunctionRecord deviceMalfunctionRecord) {
        Date nowDate = new Date();
        String sdeviceId = deviceMalfunctionRecord.getSdeviceId(); // 设备Id
        if (StringUtils.isBlank(deviceMalfunctionRecord.getId())) {
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(sdeviceId);
            String deviceCode = deviceInfo.getScode();
            deviceMalfunctionRecord.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            deviceMalfunctionRecord.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
            deviceMalfunctionRecord.setTaddTime(nowDate);
            deviceMalfunctionRecord.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            deviceMalfunctionRecord.setIdelFlag(0);
            deviceMalfunctionRecord.setSdeclareMan(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//申报人
            deviceMalfunctionRecord.setTdeclareTime(nowDate);//申报时间
            deviceMalfunctionRecord.setSdeviceCode(deviceCode);
            deviceMalfunctionRecord.setItype(30);//添加默认是手动申报状态
            deviceMalfunctionRecord.setIstatus(10);//默认待处理状态
            StringBuffer sb = new StringBuffer();
            if (StringUtils.isNotBlank(deviceInfo.getSprovinceName())) {
                sb.append(deviceInfo.getSprovinceName());
            }
            if (StringUtils.isNotBlank(deviceInfo.getScityName())) {
                sb.append(deviceInfo.getScityName());
            }
            if (StringUtils.isNotBlank(deviceInfo.getSareaName())) {
                sb.append(deviceInfo.getSareaName());
            }
            if (StringUtils.isNotBlank(deviceInfo.getSputAddress())) {
                sb.append(deviceInfo.getSputAddress());
            }
            deviceMalfunctionRecord.setSdeviceAddress(sb.toString());
            deviceMalfunctionRecordService.insert(deviceMalfunctionRecord);
            // 操作日志
            String logText = MessageFormat.format("新增设备故障{0},编号{1}", deviceMalfunctionRecord.getSdeviceCode());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        } else {
            // 修改
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(sdeviceId);
            String deviceCode = deviceInfo.getScode();
            deviceMalfunctionRecord.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            deviceMalfunctionRecord.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
            deviceMalfunctionRecord.setIdelFlag(0);
            deviceMalfunctionRecord.setTupdateTime(nowDate);
            deviceMalfunctionRecord.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            deviceMalfunctionRecord.setSdeviceCode(deviceCode);
            deviceMalfunctionRecordService.updateBySelective(deviceMalfunctionRecord);
            // 操作日志
            String logText = MessageFormat.format("修改设备故障{0},编号{1}", deviceMalfunctionRecord.getSdeviceCode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        }
        return ResponseVo.getSuccessResponse(deviceMalfunctionRecord);
    }

    /**
     * 删除设备故障信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            deviceMalfunctionRecordService.delete(checkboxId);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("删除设备异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("删除设备故障信息！");
        }
    }

    /**
     * 跳转修改故障状态页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/deviceFailureState")
    public String deviceFailureState(ModelMap modelMap, String sid) {
        try {
            DeviceMalfunctionRecord deviceMalfunctionRecord = deviceMalfunctionRecordService.selectByPrimaryKey(sid);
            if (null != deviceMalfunctionRecord) {
                modelMap.put("deviceMalfunctionRecord", deviceMalfunctionRecord);
            }
            return "sb/deviceFailureState-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 手动修改设备故障状态
     *
     * @return
     */
    @RequestMapping("/deviceFailure")
    public @ResponseBody
    ResponseVo<DeviceMalfunctionRecord> deviceFailure(DeviceMalfunctionRecord deviceMalfunctionRecord) {
        try {
            if (deviceMalfunctionRecord.getIstatus().equals(10)) {
                deviceMalfunctionRecord.setIstatus(20);
                deviceMalfunctionRecordService.updateBySelective(deviceMalfunctionRecord);
            }
            return ResponseVo.getSuccessResponse(deviceMalfunctionRecord);
        } catch (Exception e) {
            logger.error("手动修改设备故障状态异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("手动修改设备故障状态失败");
        }
    }
}
