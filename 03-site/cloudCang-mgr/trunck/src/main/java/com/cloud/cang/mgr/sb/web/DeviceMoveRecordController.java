package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.sys.service.CityService;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.domain.DeviceMoveRecordDomain;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.service.DeviceMoveRecordService;
import com.cloud.cang.mgr.sb.vo.DeviceMoveRecordVo;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceMoveRecord;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.City;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.Province;
import com.cloud.cang.model.sys.Town;
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

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @ClassName DeviceController
 * @Description 设备搬迁记录controller
 * @Author ChangTanchang
 * @Date 2018年6月13日13:47:20
 */
@Controller
@RequestMapping("/device")
public class DeviceMoveRecordController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceMoveRecordController.class);

    @Autowired
    OperatorService operatorService;

    @Autowired
    MerchantInfoService merchantInfoService;//商户信息

    @Autowired
    DeviceInfoService deviceInfoService;//设备基础信息

    @Autowired
    DeviceMoveRecordService deviceMoveRecordService; // 设备搬迁记录

    @Autowired
    CityService cityService;//省市区缓存服务

    /**
     * 设备搬迁记录
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sb/deviceMoveRecord-list";
    }

    /**
     * 设备搬迁记录列表数据
     *
     * @param deviceMoveRecordVo 初始化页面对象
     * @param paramPage          初始化分页对象
     * @return
     */

    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<DeviceMoveRecordDomain>> queryData(DeviceMoveRecordVo deviceMoveRecordVo, ParamPage paramPage) {
        PageListVo<List<DeviceMoveRecordDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<DeviceMoveRecordDomain>>();
        Page<DeviceMoveRecordDomain> page = new Page<DeviceMoveRecordDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == deviceMoveRecordVo) {
            deviceMoveRecordVo = new DeviceMoveRecordVo();
        }
        deviceMoveRecordVo.setIdelFlag(0);/* 是否删除0否1是 */
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 180);
        deviceMoveRecordVo.setCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            if (paramPage.getSidx().equals("merchantName")) {
                deviceMoveRecordVo.setOrderStr(" C.sname " + paramPage.getSord() + ",");
            } else {
                deviceMoveRecordVo.setOrderStr(" A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
            }
        }

        //分页查询
        page = deviceMoveRecordService.selectqueryData(page, deviceMoveRecordVo);
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
            DeviceMoveRecord deviceMoveRecord = deviceMoveRecordService.selectByPrimaryKey(sid);
            if (deviceMoveRecord == null) {
                deviceMoveRecord = new DeviceMoveRecord();
            }
            MerchantInfo merchantInfo = merchantInfoService.selectOne(deviceMoveRecord.getSmerchantId());//商户基础信息表
            DeviceInfo deviceInfo = deviceInfoService.selectBySid(deviceMoveRecord.getSdeviceId());//设备基础信息表
            Set<Province> provinceSet = cityService.selectAllProvices();//获取省份信息
            modelMap.put("deviceMoveRecord", deviceMoveRecord);
            modelMap.put("merchantInfo", merchantInfo);
            modelMap.put("deviceInfo", deviceInfo);
            modelMap.put("provinceSet", provinceSet);
            if (StringUtil.isNotBlank(deviceMoveRecord.getSprovinceId())) {
                Set<City> citySet = cityService.selectCitysByProvinceId(deviceMoveRecord.getSprovinceId());
                modelMap.put("citySet", citySet);//城市
            }
            if (StringUtil.isNotBlank(deviceMoveRecord.getScityId())) {
                Set<Town> townsSet = cityService.selectTownsByCityId(deviceMoveRecord.getScityId());
                modelMap.put("townsSet", townsSet);//区县
            }
            return "sb/deviceMoveRecord-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

    /**
     * 保存搬迁信息
     *
     * @param deviceMoveRecord
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<DeviceMoveRecord> save(DeviceMoveRecord deviceMoveRecord) {
        Date nowDate = new Date();
        String sdeviceId = deviceMoveRecord.getSdeviceId(); // 设备Id
        if (StringUtils.isBlank(deviceMoveRecord.getId())) {
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(sdeviceId);
            String deviceCode = deviceInfo.getScode();
            //设置属性
            if (StringUtil.isNotBlank(deviceMoveRecord.getSprovinceName())) {//省份
                deviceMoveRecord.setSprovinceId(deviceMoveRecord.getSprovinceName().split("_")[0]);
                deviceMoveRecord.setSprovinceName(deviceMoveRecord.getSprovinceName().split("_")[1]);
            }
            if (StringUtil.isNotBlank(deviceMoveRecord.getScityName())) {//城市
                deviceMoveRecord.setScityId(deviceMoveRecord.getScityName().split("_")[0]);
                deviceMoveRecord.setScityName(deviceMoveRecord.getScityName().split("_")[1]);
            }
            if (StringUtil.isNotBlank(deviceMoveRecord.getSareaName())) {//区县
                deviceMoveRecord.setSareaId(deviceMoveRecord.getSareaName().split("_")[0]);
                deviceMoveRecord.setSareaName(deviceMoveRecord.getSareaName().split("_")[1]);
            }
            deviceMoveRecord.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            deviceMoveRecord.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
            deviceMoveRecord.setTaddTime(nowDate);
            deviceMoveRecord.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            deviceMoveRecord.setIdelFlag(0);
            deviceMoveRecord.setSdeviceCode(deviceCode);
            deviceMoveRecord.setTapplyTime(nowDate);
            deviceMoveRecord.setIstatus(10);
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
            deviceMoveRecord.setSdeviceOldAddress(sb.toString());
            deviceMoveRecordService.insert(deviceMoveRecord);
            // 操作日志
            String logText = MessageFormat.format("新增设备搬迁{0},编号{1}", deviceMoveRecord.getSdeviceCode());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        } else {
            // 修改
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(sdeviceId);
            String deviceCode = deviceInfo.getScode();
            deviceMoveRecord.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            deviceMoveRecord.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
            deviceMoveRecord.setIdelFlag(0);
            deviceMoveRecord.setTupdateTime(nowDate);
            deviceMoveRecord.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            deviceMoveRecord.setSdeviceCode(deviceCode);
            deviceMoveRecordService.updateBySelective(deviceMoveRecord);
            // 操作日志
            String logText = MessageFormat.format("修改设备搬迁{0},编号{1}", deviceMoveRecord.getSdeviceCode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        }
        return ResponseVo.getSuccessResponse(deviceMoveRecord);
    }

    /**
     * 删除设备搬迁记录
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            deviceMoveRecordService.delete(checkboxId);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("删除设备搬迁记录异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("删除设备搬迁信息失败！");
        }
    }

    /**
     * 查询城市信息
     *
     * @return
     */
    @RequestMapping("/getCity")
    public @ResponseBody
    ResponseVo<Set<City>> getCityByProvicesId(String pid) {
        try {
            Set<City> citySet = cityService.selectCitysByProvinceId(pid);//根据省份ID查询城市信息
            return ResponseVo.getSuccessResponse(citySet);
        } catch (Exception e) {
            logger.error("查询城市信息异常：{}", e);
        }
        return null;
    }

    /**
     * 查询县镇信息
     *
     * @return
     */
    @RequestMapping("/getTown")
    public @ResponseBody
    ResponseVo<Set<Town>> getCityByCityId(String cityId) {
        try {
            Set<Town> townsSet = cityService.selectTownsByCityId(cityId);//根据城市ID查询区县信息
            return ResponseVo.getSuccessResponse(townsSet);
        } catch (Exception e) {
            logger.error("查询城市信息异常：{}", e);
        }
        return null;
    }

    /**
     * 设备搬迁跳转页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/deviceMoveState")
    public String deviceFailureState(ModelMap modelMap, String sid) {
        try {
            DeviceMoveRecord deviceMoveRecord = deviceMoveRecordService.selectByPrimaryKey(sid);
            if (null != deviceMoveRecord) {
                modelMap.put("deviceMoveRecord", deviceMoveRecord);
            }
            return "sb/deviceMoveState-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 修改审核状态
     *
     * @param deviceMoveRecord
     * @return
     */
    @RequestMapping("/deviceMove")
    public @ResponseBody
    ResponseVo<DeviceMoveRecord> deviceMove(DeviceMoveRecord deviceMoveRecord) {
        Date nowDate = new Date();
        try {
            if (!deviceMoveRecord.getIstatus().equals(10)) {
                deviceMoveRecord.setIstatus(deviceMoveRecord.getIstatus());
                deviceMoveRecord.setTauditTime(nowDate);
                deviceMoveRecord.setSauditUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                deviceMoveRecordService.updateBySelective(deviceMoveRecord);
            }
            return ResponseVo.getSuccessResponse(deviceMoveRecord);
        } catch (Exception e) {
            logger.error("修改审核状态异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("手动修改设备搬迁审核状态失败");
        }
    }


    /**
     * 设备搬迁完成跳转页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/deviceMoveCom")
    public String deviceMoveCmp(ModelMap modelMap, String sid) {
        try {
            DeviceMoveRecord deviceMoveRecord = deviceMoveRecordService.selectByPrimaryKey(sid);
            if (null != deviceMoveRecord) {
                modelMap.put("deviceMoveRecord", deviceMoveRecord);
            }
            return "sb/deviceMoveCom-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 搬迁完成状态
     *
     * @param
     * @return
     */
    @RequestMapping("/deviceComplete")
    public @ResponseBody
    ResponseVo<DeviceMoveRecord> deviceComplete(DeviceMoveRecord deviceMoveRecord) {
        try {
            if (deviceMoveRecord.getIstatus().equals(20)) {
                deviceMoveRecord.setIstatus(40);
                deviceMoveRecord.setTmoveTime(deviceMoveRecord.getTmoveTime());
                deviceMoveRecord.setSprincipal(deviceMoveRecord.getSprincipal());
                deviceMoveRecordService.updateBySelective(deviceMoveRecord);
            }
            return ResponseVo.getSuccessResponse(deviceMoveRecord);
        } catch (Exception e) {
            logger.error("搬迁状态修改异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("搬迁状态修改失败");
        }
    }

    /**
     * 取消状态
     *
     * @param checkboxId
     * @param type
     * @return
     */
    @RequestMapping("/deviceClose")
    public @ResponseBody
    ResponseVo<String> deviceClose(String checkboxId, Integer type) {
        try {
            DeviceMoveRecord deviceMoveRecord = new DeviceMoveRecord();
            deviceMoveRecord.setId(checkboxId);
            deviceMoveRecord.setIstatus(type);
            deviceMoveRecordService.updateBySelective(deviceMoveRecord);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("搬迁取消状态修改失败:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("搬迁取消状态修改失败");
        }
    }
}
