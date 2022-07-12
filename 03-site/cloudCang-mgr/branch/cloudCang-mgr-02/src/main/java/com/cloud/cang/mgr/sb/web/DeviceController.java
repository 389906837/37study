package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.sys.service.CityService;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.Constrants;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.ReadFileUtil;
import com.cloud.cang.mgr.rm.service.ReplenishmentPlanService;
import com.cloud.cang.mgr.sb.domain.DetailStockDomain;
import com.cloud.cang.mgr.sb.domain.DeviceInfoDomain;
import com.cloud.cang.mgr.sb.domain.DeviceInventoryStockDomain;
import com.cloud.cang.mgr.sb.service.*;
import com.cloud.cang.mgr.sb.vo.DeviceInfoVo;
import com.cloud.cang.mgr.sb.vo.ReplenishmentVo;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sm.service.StandardDetailService;
import com.cloud.cang.mgr.sm.service.StandardStockService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.tp.service.DeviceInfoTemplateService;
import com.cloud.cang.mgr.tp.service.DeviceManageTemplateService;
import com.cloud.cang.mgr.tp.service.DeviceModelTemplateService;
import com.cloud.cang.model.rm.ReplenishmentPlan;
import com.cloud.cang.model.sb.*;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sm.StandardStock;
import com.cloud.cang.model.sys.City;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.Province;
import com.cloud.cang.model.sys.Town;
import com.cloud.cang.model.tp.DeviceInfoTemplate;
import com.cloud.cang.model.tp.DeviceManageTemplate;
import com.cloud.cang.model.tp.DeviceModelTemplate;
import com.cloud.cang.rm.CommodityResult;
import com.cloud.cang.rm.DynamicReplenishmentDto;
import com.cloud.cang.rm.DynamicReplenishmentResult;
import com.cloud.cang.rm.ReplenishmentPlanServicesDefine;
import com.cloud.cang.sb.TcpVo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import com.github.pagehelper.Page;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * @version 1.0
 * @ClassName DeviceController
 * @Description 设备管理controller
 * @Author zengzexiong
 * @Date 2018年1月24日15:10:20
 */
@Controller
@RequestMapping("/device")
public class DeviceController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    CityService cityService;//省市区缓存服务

    @Autowired
    OperatorService operatorService;

    @Autowired
    MerchantInfoService merchantInfoService;//商户信息

    @Autowired
    DeviceInfoService deviceInfoService;//0.设备基础信息

    @Autowired
    DeviceManageService deviceManageService;//1.设备管理信息

    @Autowired
    DeviceRegisterService deviceRegisterService;//2.设备注册信息

    @Autowired
    DeviceModelService deviceModelService;//3.设备详细信息

    @Autowired
    DeviceCommodityService deviceCommodityService;//4.设备商品信息

    @Autowired
    MonitorDataConfService monitorDataConfService;//5.设备监控配置信息

    @Autowired
    GroupManageService groupManageService;//6.设备分组管理

    @Autowired
    GroupRelationshipService groupRelationshipService;//7.设备分组关系

    @Autowired
    StandardStockService standardStockService;//设备标准库存

    @Autowired
    StandardDetailService standardDetailService;//设备标准库存明细表

    @Autowired
    DeviceUpgradeService deviceUpgradeService;  // 设备升级主表

    @Autowired
    AiInfoService aiInfoService;

    @Autowired
    DeviceInfoTemplateService deviceInfoTemplateService;

    @Autowired
    DeviceManageTemplateService deviceManageTemplateService;

    @Autowired
    DeviceModelTemplateService deviceModelTemplateService;


    @Autowired
    private ReplenishmentPlanService replenishmentPlanService;



    /* ----------0.设备基础信息信息开始 ----------*/

    /**
     * 设备信息列表
     *
     * @return
     */
    @RequestMapping("/info/list")
    public String list(String method, ModelMap map) {
        if (StringUtil.isNotBlank(method)) {
            map.put("method", method);
        }
        return "sb/deviceInfo/deviceInfo-list";
    }

    /**
     * 查询设备列表数据
     *
     * @param deviceInfoVo 初始化页面对象
     * @param paramPage    初始化分页对象
     * @return
     */

    @RequestMapping("/info/queryData")
    @ResponseBody
    public PageListVo<List<DeviceInfoDomain>> queryData(DeviceInfoVo deviceInfoVo, ParamPage paramPage) {
        PageListVo<List<DeviceInfoDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<DeviceInfoDomain>>();
        Page<DeviceInfoDomain> page = new Page<DeviceInfoDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == deviceInfoVo) {
            deviceInfoVo = new DeviceInfoVo();
        }
        deviceInfoVo.setIdelFlag(0);/* 是否删除0否1是 */
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 50);
        deviceInfoVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            if (paramPage.getSidx().equals("merchantName")) {
                deviceInfoVo.setOrderStr(" D.sname " + paramPage.getSord() + ",");
            } else {
                deviceInfoVo.setOrderStr(" A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
            }
        }

        //分页查询
        page = deviceInfoService.selectPageByDomainWhere(page, deviceInfoVo);

        //将分页查询结果转换成页面List集合
//        pageToPageList(pageListVo, page);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }

        return pageListVo;
    }


    /**
     * 查询城市信息
     *
     * @return
     */
    @RequestMapping("/info/getCity")
    @ResponseBody
    public ResponseVo<Set<City>> getCityByProvicesId(String pid) {
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
    @RequestMapping("/info/getTown")
    @ResponseBody
    public ResponseVo<Set<Town>> getCityByCityId(String cityId) {
        try {
            Set<Town> townsSet = cityService.selectTownsByCityId(cityId);//根据城市ID查询区县信息
            return ResponseVo.getSuccessResponse(townsSet);
        } catch (Exception e) {
            logger.error("查询城市信息异常：{}", e);
        }
        return null;
    }


    /**
     * 删除设备信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/info/delete")
    @ResponseBody
    public ResponseVo<String> delete(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                deviceInfoService.delete(checkboxId);//逻辑删除
                //操作日志
                String logText = MessageFormat.format("删除设备信息", "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                return ResponseVo.getSuccessResponse();
            }
        } catch (ServiceException e) {
            logger.error("删除设备信息失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("删除设备信息失败");
        } catch (Exception e) {
            logger.error("系统异常删除设备与设备注册失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除设备与设备注册异常！");
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除设备与设备注册失败！");
    }

    /**
     * 修改设备状态为离线
     * 同时向netty服务器发出断开设备长连接指令
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/info/offline")
    @ResponseBody
    public ResponseVo<String> offline(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                ResponseVo responseVo = deviceInfoService.updateDeviceStatus(checkboxId);
                //操作日志
                String logText = MessageFormat.format("修改设备为离线状态", "");
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("修改设备为离线状态失败：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("修改设备为离线状态失败！");
    }

    /**
     * 启用该设备
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/info/operateingEanbled")
    @ResponseBody
    public ResponseVo<String> operateingEanbled(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                ResponseVo<String> responseVo = deviceInfoService.updateDeviceOperatingStatus(checkboxId, Constrants.DEVICE_ENABLE);
                //操作日志
                String logText = MessageFormat.format("启用设备", "");
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("启用该状态出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("启用该设备失败！");
    }

    /**
     * 禁用该设备
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/info/operateingDisabled")
    @ResponseBody
    public ResponseVo<String> operateingDisabled(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                ResponseVo<String> responseVo = deviceInfoService.updateDeviceOperatingStatus(checkboxId, Constrants.DEVICE_DISABLE);
                //操作日志
                String logText = MessageFormat.format("禁用设备", "");
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("禁用该设备出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("禁用该设备失败！");
    }


    /**
     * 批量操作设备（音量，盘货，升级语音，升级系统，重启，关机，定时关机，停用）
     *
     * @param checkboxId    设备ID集合
     * @param method        调用方法名
     * @param operateParams 操作参数
     * @return
     */
    @RequestMapping("/info/operate/*")
    @ResponseBody
    public ResponseVo<String> operateDevice(String[] checkboxId, String method, String operateParams) {
        logger.debug("批量操作设备开始");
        ResponseVo<String> responseVo = null;
        //类型
        try {

            //校验消息类型，如果是带参数的消息，需要检查参数格式是否正确，不带参数的只检查是否为空，或不能识别的消息
//            responseVo = validMethodAndParam(checkboxId, method, operateParams);


            //操作参数不为空（音量，定时关机）
            if (ArrayUtils.isNotEmpty(checkboxId) && StringUtils.isNotBlank(method) && StringUtils.isNotBlank(operateParams)) {
                responseVo = deviceInfoService.operate(checkboxId, method, operateParams);
                return responseVo;

                //操作参数为空（盘货，升级语音，系统升级，重启，关机，停用）
            } else if (ArrayUtils.isNotEmpty(checkboxId) && StringUtils.isNotBlank(method)) {
                responseVo = deviceInfoService.operate(checkboxId, method);
                return responseVo;

                //参数非法
            } else {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("参数非法，系统异常调整失败！");
            }
        } catch (Exception e) {
            logger.error("批量操作设备出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常调整失败！");
    }


    /**
     * 未注册长连接
     *
     * @return
     */
    @RequestMapping("/info/unRegistered")
    public String unRegistered(ModelMap modelMap) {
        try {
            List<TcpVo> tcpVoList = deviceInfoService.selectUnRegistered();
            modelMap.put("tcpVoList", tcpVoList);
            return "sb/deviceInfo/deviceInfo-unRegistered";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

    /**
     * 已注册长连接
     *
     * @return
     */
    @RequestMapping("/info/registered")
    public String registered(ModelMap modelMap) {
        try {
            List<TcpVo> tcpVoList = deviceInfoService.selectRegistered();
            modelMap.put("tcpVoList", tcpVoList);
            return "sb/deviceInfo/deviceInfo-registered";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }


    /**
     * 断开未注册的TCP
     *
     * @param channelId
     * @return
     */
    @RequestMapping("/info/disconnectTcp")
    public @ResponseBody
    ResponseVo<String> disconnectTcp(String channelId) {
        logger.debug("断开未注册的TCP开始");
        ResponseVo<String> responseVo = null;
        try {
            if (StringUtils.isNotBlank(channelId)) {
                return deviceInfoService.disconnetTcp(channelId);
            }
        } catch (Exception e) {
            logger.error("断开未注册的TCP出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常断开失败！");
    }

    /**
     * 断开已注册的TCP
     *
     * @param deviceId
     * @return
     */
    @RequestMapping("/info/disconnectRegisterTcp")
    public @ResponseBody
    ResponseVo<String> disconnectRegisterTcp(String deviceId) {
        logger.debug("断开已注册的TCP开始");
        ResponseVo<String> responseVo = null;
        try {
            if (StringUtils.isNotBlank(deviceId)) {
                return deviceInfoService.disconnectRegisterTcp(deviceId);
            }
        } catch (Exception e) {
            logger.error("断开已注册的TCP出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常断开失败！");
    }

    /**
     * 查看详情页面
     *
     * @param sid
     * @param modelMap
     * @return
     */
    @RequestMapping("/info/view")
    public String infoView(String sid, ModelMap modelMap) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(sid);
                DeviceManage deviceManage = null;
                DeviceRegister deviceRegister = null;
                DeviceModel deviceModel = null;
                MonitorDataConf monitorDataConf = null;
                MerchantInfo merchantInfo = null;
                GroupRelationship groupRelationship = null;
                GroupManage groupManage = null;
                AiInfo aiInfo = null;

                String deviceId = deviceInfo.getId();
                if (StringUtils.isNotBlank(deviceId)) {
                    deviceManage = deviceManageService.selectByDeviceId(deviceId);//设备管理表
                    deviceRegister = deviceRegisterService.selectByDeviceId(deviceId);//设备注册表
                    deviceModel = deviceModelService.selectByDeviceId(deviceId);//设备详细信息表
                    monitorDataConf = monitorDataConfService.selectByDeviceId(deviceId);//设备配置信息表
                    merchantInfo = merchantInfoService.selectOne(deviceInfo.getSmerchantId());//商户信息表
                    groupRelationship = groupRelationshipService.selectByDevieId(deviceId);//设备分组关系表
                    aiInfo = aiInfoService.selectByPrimaryKey(deviceInfo.getSaiId());

                    String groupId = "";
                    if (null != groupRelationship) {
                        groupId = groupRelationship.getSgroupId();
                    }
                    if (StringUtils.isNotBlank(groupId)) {
                        groupManage = groupManageService.selectByPrimaryKey(groupId);//设备分组表
                        modelMap.put("groupManage", groupManage);
                    }

                    // 设备区域
                    if (StringUtils.isNotBlank(deviceManage.getSareaCode())) {
                        String areaName = GrpParaUtil.getName(GrpParaUtil.DEVICE_OPERATE_AREA, deviceManage.getSareaCode());
                        deviceManage.setSareaCode(areaName);
                    }

                    modelMap.put("deviceInfo", deviceInfo);
                    modelMap.put("deviceManage", deviceManage);
                    modelMap.put("deviceRegister", deviceRegister);
                    modelMap.put("deviceModel", deviceModel);
                    modelMap.put("monitorDataConf", monitorDataConf);
                    modelMap.put("merchantInfo", merchantInfo);
                    modelMap.put("aiInfo", aiInfo);
                    return "sb/deviceInfo/deviceInfo-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

    /**
     * 增加按钮跳转页面
     *
     * @param map
     * @return
     */
    @RequestMapping("/info/toAdd")
    public String infoToAdd(ModelMap map) {
        String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
        //new 对象
        DeviceInfo deviceInfo = new DeviceInfo();//设备基础信息表
        DeviceManage deviceManage = new DeviceManage();//设备管理信息表
        DeviceModel deviceModel = new DeviceModel();//设备详细信息表
        MonitorDataConf monitorDataConf = new MonitorDataConf();//设备配置信息表
        map.put("deviceInfo", deviceInfo);
        map.put("deviceManage", deviceManage);
        map.put("deviceModel", deviceModel);
        map.put("monitorDataConf", monitorDataConf);
        Set<Province> provinceSet = cityService.selectAllProvices();//获取省份信息
        map.put("provinceSet", provinceSet);

        // 模板信息

        List<DeviceInfoTemplate> deviceInfoTemplateList = deviceInfoTemplateService.selectTemplateByMerchant(merchantId);
        List<DeviceManageTemplate> deviceManageTemplateList = deviceManageTemplateService.selectTemplateByMerchant(merchantId);
        List<DeviceModelTemplate> deviceModelTemplateList = deviceModelTemplateService.selectTemplateByMerchant(merchantId);
        map.put("deviceInfoTemplateList", deviceInfoTemplateList);
        map.put("deviceManageTemplateList", deviceManageTemplateList);
        map.put("deviceModelTemplateList", deviceModelTemplateList);
        return "sb/deviceInfo/deviceInfo-toAdd";
    }

    /**
     * 增加设备信息
     *
     * @param deviceInfo
     * @return
     */
    @RequestMapping("/info/add")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<DeviceInfo> add(DeviceInfo deviceInfo, DeviceManage deviceManage, DeviceModel deviceModel, MonitorDataConf monitorDataConf, HttpServletRequest request) {

        String merchantId = deviceInfo.getSmerchantId();//商户ID
        String readerID = deviceInfo.getSreaderSerialNumber();//读写器序列号
        try {
            if (StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(readerID)) {
                ResponseVo<DeviceInfo> responseVo = deviceInfoService.insertDeviceInfo(deviceInfo, deviceManage, deviceModel, monitorDataConf, request, readerID, merchantId);//设备基础信息，设备注册信息表与设备管理表，设备详细信息表，设备配置表
                DeviceInfo deviceInfoDomain = responseVo.getData();
                if (null == deviceInfoDomain.getIdockingType() || deviceInfoDomain.getIdockingType() == 10) {   // 自主研发设备有二维码
                    reSetQRcode(deviceInfoDomain.getId()); //  生成二维码
                }
                return responseVo;
            }
        } catch (ServiceException e) {
            logger.error("增加设备信息出现ServiceException异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("增加设备信息出现Exception异常：{}", e);
        }
        return new ResponseVo<>(false, 111, "添加设备出错");
    }

    /**
     * 编辑按钮跳转页面
     *
     * @param sid
     * @return
     */
    @RequestMapping("/info/toEdit")
    public String infoToEdit(String sid, ModelMap modelMap) {
        try {
            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            if (StringUtils.isBlank(merchantId)) {
                return ExceptionUtil.to404();
            }
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该设备信息
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(sid);//设备基础信息
                DeviceManage deviceManage = deviceManageService.selectByDeviceId(sid);//设备管理信息表
                DeviceModel deviceModel = deviceModelService.selectByDeviceId(sid);//设备详细信息表
                MonitorDataConf monitorDataConf = monitorDataConfService.selectByDeviceId(sid);//设备配置信息表

                if (deviceInfo != null) {
                    MerchantInfo merchantInfo = merchantInfoService.selectOne(deviceInfo.getSmerchantId());//商户基础信息表
                    GroupRelationship groupRelationship = new GroupRelationship();
                    groupRelationship.setSdeviceId(deviceInfo.getId());
                    List<GroupRelationship> groupRelationshipList = groupRelationshipService.selectByEntityWhere(groupRelationship);//设备分组关系表
                    if (CollectionUtils.isNotEmpty(groupRelationshipList)) {
                        groupRelationship = groupRelationshipList.get(0);
                        modelMap.put("groupRelationship", groupRelationship);
                    }

                    modelMap.put("deviceInfo", deviceInfo);
                    modelMap.put("merchantInfo", merchantInfo);
                    modelMap.put("deviceManage", deviceManage);
                    modelMap.put("deviceModel", deviceModel);
                    modelMap.put("monitorDataConf", monitorDataConf);

                    // 模板信息

                    List<DeviceInfoTemplate> deviceInfoTemplateList = deviceInfoTemplateService.selectTemplateByMerchant(merchantId);
                    List<DeviceManageTemplate> deviceManageTemplateList = deviceManageTemplateService.selectTemplateByMerchant(merchantId);
                    List<DeviceModelTemplate> deviceModelTemplateList = deviceModelTemplateService.selectTemplateByMerchant(merchantId);
                    modelMap.put("deviceInfoTemplateList", deviceInfoTemplateList);
                    modelMap.put("deviceManageTemplateList", deviceManageTemplateList);
                    modelMap.put("deviceModelTemplateList", deviceModelTemplateList);

                    Set<Province> provinceSet = cityService.selectAllProvices();
                    modelMap.put("provinceSet", provinceSet);//省份
                    if (StringUtil.isNotBlank(deviceInfo.getSprovinceId())) {
                        Set<City> citySet = cityService.selectCitysByProvinceId(deviceInfo.getSprovinceId());
                        modelMap.put("citySet", citySet);//城市
                    }
                    if (StringUtil.isNotBlank(deviceInfo.getScityId())) {
                        Set<Town> townsSet = cityService.selectTownsByCityId(deviceInfo.getScityId());
                        modelMap.put("townsSet", townsSet);//区县
                    }
                    MerchantInfo merchantInfo1 = merchantInfoService.selectByPrimaryKey(merchantId);
                    if (null != merchantInfo1 && "0".equals(merchantInfo1.getSrootId())) {
                        return "sb/deviceInfo/deviceInfo-toEdit";
                    } else if (null != merchantInfo1) {
                        List<GroupManage> groupManageLists = groupManageService.selectDeviceGroup(merchantId);
                        modelMap.put("groupManageLists", groupManageLists);//区县
                        return "sb/deviceInfo/deviceInfo-child-toEdit";
                    }
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

    /**
     * 修改设备信息
     *
     * @param deviceInfo      设备基础信息
     * @param deviceManage    设备管理信息
     * @param deviceModel     设备详细信息
     * @param monitorDataConf 设备配置信息
     * @param request         获取设备ID，设备管理主键ID，设备详细信息主键ID，设备配置信息主键ID
     * @return
     */
    @RequestMapping("/info/edit")
    @ResponseBody
    public ResponseVo<DeviceInfo> update(DeviceInfo deviceInfo, DeviceManage deviceManage, DeviceModel deviceModel, MonitorDataConf monitorDataConf, HttpServletRequest request) {
        try {

            String deviceId = request.getParameter("deviceInfoId");//设备ID
            String deviceCode = deviceInfo.getScode();//设备编号
            String merchantId = deviceInfo.getSmerchantId();//商户ID
            String merchantCode = deviceInfo.getSmerchantCode();//商户编号
            String readerSerialNumber = deviceInfo.getSreaderSerialNumber();//读取器序列号


            if (StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(deviceCode) && StringUtils.isNotBlank(merchantId)
                    && StringUtils.isNotBlank(merchantCode) && StringUtils.isNotBlank(readerSerialNumber)) {
                // 二维码
                DeviceInfo deviceInfo1 = deviceInfoService.selectByPrimaryKey(deviceId);
                Integer oldIdockingType = deviceInfo1.getIdockingType();
                Integer newIdockingType = deviceInfo.getIdockingType();
                deviceInfo.setSqrUrl(deviceInfo1.getSqrUrl());
                deviceInfo.setTqrGeneratetime(deviceInfo1.getTqrGeneratetime());
                if (null == oldIdockingType || oldIdockingType == 20) { // 原来没有二维码，现在增加
                    if (newIdockingType == 10) {
                        ResponseVo<DeviceInfo> responseVo = reSetQRcode(deviceId);
                        deviceInfo.setSqrUrl(responseVo.getData().getSqrUrl());
                        deviceInfo.setTqrGeneratetime(responseVo.getData().getTqrGeneratetime());
                    }
                } else if (oldIdockingType == 10) {
                    if (null == newIdockingType || newIdockingType == 20) {   // 原来有二维码，现在没有
                        deviceInfo.setSqrUrl(null);
                        deviceInfo.setTqrGeneratetime(null);
                    }
                }
                return deviceInfoService.updateInfo(deviceInfo1, deviceInfo, deviceManage, deviceModel, monitorDataConf, request);//修改数据入库
            }
        } catch (ServiceException e) {
            logger.error("修改设备信息出现ServiceException异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("修改设备信息出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("编辑设备信息失败！");
    }


    @RequestMapping(" /info/childEdit")
    public @ResponseBody
    ResponseVo<DeviceInfo> childEdit(DeviceInfo deviceInfo, DeviceManage deviceManage, HttpServletRequest request) {
        try {

            String deviceId = request.getParameter("deviceInfoId");//设备ID
            String deviceCode = deviceInfo.getScode();//设备编号
            String merchantId = deviceInfo.getSmerchantId();//商户ID
            String merchantCode = deviceInfo.getSmerchantCode();//商户编号


            if (StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(deviceCode) && StringUtils.isNotBlank(merchantId)
                    && StringUtils.isNotBlank(merchantCode)) {
                return deviceInfoService.updateInfo(deviceInfo, deviceManage, request);//修改数据入库
            }
        } catch (ServiceException e) {
            logger.error("修改设备信息出现ServiceException异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("修改设备信息出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("编辑设备信息失败！");
    }


    /**
     * 查看设备二维码
     *
     * @param sid
     * @param modelMap
     * @return
     */
    @RequestMapping("/info/qrCode")
    public String qrCode(String sid, ModelMap modelMap, HttpServletRequest request) {
        logger.debug("查看设备二维码");
        FileInputStream inputStream = null;
        try {
            String id = sid;//设备ID
            if (StringUtils.isNotBlank(id)) {
                //查询设备信息
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(id);
                if (deviceInfo != null) {
                    String nowMerchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE); // 当前操作员商户编号
                    String qrCodeUrl = deviceInfo.getSqrUrl();
                    if (StringUtils.isBlank(qrCodeUrl)) {
                        DeviceInfo deviceInfo1 = new DeviceInfo();

                        //生成二维码
                        String newDate = DateUtils.getCurrentTimeNumber(); //二维码日期
                        String contents = EvnUtils.getValue("wap.http.domain") + "/index/visit.html?deviceId=" + id + "&isScan=1&generateTime=" + newDate;  //二维码文本

                        int width = 430; // 二维码图片宽度 300
                        int height = 430; // 二维码图片高度300

                        String format = "png";// 二维码的图片格式 png
                        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();

                        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
                        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码
                        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,//要编码的内容
                                BarcodeFormat.QR_CODE,      //二维码
                                width, //条形码的宽度
                                height, //条形码的高度
                                hints);//生成条形码时的一些配置,此项可选
                        File file = new File(System.getProperty("catalina.home") + File.separator + "temp" + File.separator + DateUtils.getCurrentSTimeNumber() + ".png");//指定输出路径
                        MatrixToImageWriter.writeToFile(bitMatrix, format, file);
                        inputStream = new FileInputStream(file);
                        MultipartFile multipartFile = new MockMultipartFile("file", IOUtils.toByteArray(inputStream));
                        //上传图片到图片服务器
                        if (null != file) {
                            //图片上传
                            String url = uploadHome(multipartFile, "deviceQRCode");
                            if (StringUtils.isNotBlank(url)) {
                                deviceInfo1.setSqrUrl(url);     //二维码地址
                                if (inputStream != null) {      //关闭流
                                    inputStream.close();
                                }
                                if (file.exists() && file.isFile()) {
                                    file.delete();
                                    System.out.println("已经删除文件：" + file.getPath());
                                }
                            }
                        }
                        deviceInfo1.setId(id);  //设备ID
                        deviceInfo1.setTqrGeneratetime(DateUtils.parseDateByFormat(newDate, "yyyyMMddHHmmss"));     //二维码生成时间
                        deviceInfoService.updateBySelective(deviceInfo1);
                        String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);                            // 图片地址前缀
                        deviceInfoService.sendQrCode(deviceInfo.getId(), preUrl + deviceInfo1.getSqrUrl()); // 向设备发送二维码
                        modelMap.put("deviceInfoVo", deviceInfo1);
                        modelMap.put("nowMerchantCode", nowMerchantCode);
                    } else {
                        modelMap.put("deviceInfoVo", deviceInfo);
                        modelMap.put("nowMerchantCode", nowMerchantCode);
                    }

                    return "sb/deviceInfo/deviceInfo-QRcode";
                }
            }
        } catch (ServiceException e) {
            logger.error("修改设备二维码出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("修改设备二维码出现Exception异常：{}", e);
        } finally {
            if (inputStream != null) {      //关闭流
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("修改设备二维码输入流关闭异常：{}", e);
                }
            }
        }
        return ExceptionUtil.to500();
    }

    /**
     * 重置设备二维码
     *
     * @param id
     * @return
     */
    @RequestMapping("/info/reSetQRcode")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<DeviceInfo> reSetQRcode(String id) {
        logger.debug("重置设备二维码");
        FileInputStream inputStream = null;
        try {
            if (StringUtils.isNotBlank(id)) {
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(id);
                if (null != deviceInfo) {
                    DeviceInfo deviceInfo1 = new DeviceInfo();
                    //生成二维码
                    String newDate = DateUtils.getCurrentTimeNumber(); //二维码日期
                    String contents = EvnUtils.getValue("wap.http.domain") + "/index/visit.html?deviceId=" + id + "&isScan=1&generateTime=" + newDate;  //二维码文本
                    int width = 430; // 二维码图片宽度 300
                    int height = 430; // 二维码图片高度300
                    String format = "png";// 二维码的图片格式 png
                    Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();

                    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
                    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码
                    BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,//要编码的内容
                            BarcodeFormat.QR_CODE,
                            width, //条形码的宽度
                            height, //条形码的高度
                            hints);//生成条形码时的一些配置,此项可选
                    File file = new File(System.getProperty("catalina.home") + File.separator + "temp" + File.separator + DateUtils.getCurrentSTimeNumber() + ".png");//指定输出路径
                    MatrixToImageWriter.writeToFile(bitMatrix, format, file);
                    inputStream = new FileInputStream(file);
                    MultipartFile multipartFile = new MockMultipartFile("file", IOUtils.toByteArray(inputStream));
                    //上传图片到图片服务器
                    if (null != file) {
                        //图片上传
                        String url = uploadHome(multipartFile, "deviceQRCode");
                        if (StringUtils.isNotBlank(url)) {
                            deviceInfo1.setSqrUrl(url);     //二维码地址
                            if (inputStream != null) {      //关闭流
                                inputStream.close();
                            }
                            if (file.exists() && file.isFile()) {
                                file.delete();
                                System.out.println("已经删除文件：" + file.getPath());
                            }
                        }
                    }
                    deviceInfo1.setId(id);  //设备ID
                    deviceInfo1.setTqrGeneratetime(DateUtils.parseDateByFormat(newDate, "yyyyMMddHHmmss"));     //二维码生成时间
                    deviceInfoService.updateBySelective(deviceInfo1);
                    String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);                            // 图片地址前缀
                    deviceInfoService.sendQrCode(deviceInfo.getId(), preUrl + deviceInfo1.getSqrUrl()); //向设备发送二维码
                    return ResponseVo.getSuccessResponse(deviceInfo1);
                }
            }
        } catch (ServiceException e) {
            logger.error("修改设备二维码信息ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("修改设备信息出现Exception异常：{}", e);
        } finally {
            if (inputStream != null) {      //关闭流
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("修改设备信息出现输入流关闭异常：{}", e);
                }
            }
        }
        return new ResponseVo<>(false, 111, "重置设备二维码出错");
    }

    /* ----------0.设备基础信息结束 ----------*/

    /**
     * 重启设备
     *
     * @return
     */
    @RequestMapping("/info/deviceReboot")
    public String deviceInventory() {
        try {
            return "sb/deviceInfo/deviceInfo-reboot";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 重启设备
     *
     * @param deviceIds    设备IDs
     * @param irangeDevice 全部/部分
     * @param deviceCodes  设备编号
     * @return
     */
    @RequestMapping("/info/reboot")
    @ResponseBody
    public ResponseVo<String> reboot(String deviceIds, String irangeDevice, String deviceCodes) {
        logger.debug("重启设备操作开始");
        if (StringUtils.isBlank(irangeDevice)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择升级设备：全部/部分");
        }
        if (irangeDevice.equals("20") && StringUtils.isBlank(deviceIds)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择设备");
        }
        String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
        String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
        String user = SessionUserUtils.getSessionUserName();
        try {
            ResponseVo<String> responseVo = deviceInfoService.reboot(deviceIds, irangeDevice, merchantId, user, deviceCodes, merchantCode);
            //操作日志
            String logText = "";
            if ("10".equals(irangeDevice)) {
                logText = MessageFormat.format("全部设备", "");
                logger.debug("重启设备操作完成");
            } else {
                logText = MessageFormat.format("设备编号：{0}", deviceCodes);
                logger.debug("重启设备操作完成，设备编号：{}", deviceCodes);
            }
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            if (responseVo.isSuccess()) {
                return responseVo;
            }
        } catch (ServiceException e) {
            logger.error("重启设备操作开始出现Service异常：{}", e);
        } catch (Exception e) {
            logger.error("重启设备操作开始出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("重启设备操作失败");
    }

    /**
     * 关机
     *
     * @return
     */
    @RequestMapping("/info/deviceShutdown")
    public String deviceShutdown() {
        try {
            return "sb/deviceInfo/deviceInfo-shutdown";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 关机
     *
     * @param deviceIds    设备IDs
     * @param irangeDevice 全部/部分 10=全部，20=部分
     * @param deviceCodes  设备编号
     * @return
     */
    @RequestMapping("/info/shutdown")
    @ResponseBody
    public ResponseVo<String> shutdown(String deviceIds, String irangeDevice, String deviceCodes) {
        logger.debug("设备关机操作开始");
        if (StringUtils.isBlank(irangeDevice)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择升级设备：全部/部分");
        }
        if (irangeDevice.equals("20") && StringUtils.isBlank(deviceIds)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择设备");
        }
        String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
        String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
        String user = SessionUserUtils.getSessionUserName();
        try {
            ResponseVo<String> responseVo = deviceInfoService.shutdown(deviceIds, deviceCodes, irangeDevice, merchantId, merchantCode, user);
            //操作日志
            String logText = "";
            if ("10".equals(irangeDevice)) {
                logText = MessageFormat.format("设备关机操作：{0}", "");
                logger.debug("设备关机操作完成");
            } else {
                logText = MessageFormat.format("设备关机操作，设备编号：{0}", deviceCodes);
                logger.debug("设备关机操作完成，设备编号：{}", deviceCodes);
            }
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            logger.debug("设备关机操作完成，设备编号：{}", deviceCodes);
            if (responseVo.isSuccess()) {
                return responseVo;
            }
        } catch (ServiceException e) {
            logger.error("设备关机操作出现Service异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("设备关机操作出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("设备关机操作失败");
    }


    /**
     * 调节音量大小
     *
     * @return
     */
    @RequestMapping("/info/deviceAdjustVolume")
    public String deviceAdjustVolume() {
        try {
            return "sb/deviceInfo/deviceInfo-adjustVolume";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 调整音量值大小
     *
     * @param volumeValue  音量值大小
     * @param deviceIds    设备IDs
     * @param irangeDevice 全部/部分
     * @param deviceCodes  设备编号
     * @return
     */
    @RequestMapping("/info/adjustVolume")
    @ResponseBody
    public ResponseVo<String> adjustVolume(String volumeValue, String deviceIds, String irangeDevice, String deviceCodes) {
        logger.debug("调节设备音量大小操作开始");
        if (StringUtils.isBlank(volumeValue)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请填写音量值");
        }
        if (StringUtils.isBlank(irangeDevice)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择升级设备：全部/部分");
        }
        if (irangeDevice.equals("20") && StringUtils.isBlank(deviceIds)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择设备");
        }
        String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
        String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
        String user = SessionUserUtils.getSessionUserName();
        try {
            ResponseVo<String> responseVo = deviceInfoService.adjustVolume(volumeValue, deviceIds, irangeDevice, merchantId, user, deviceCodes, merchantCode);
            //操作日志
            String logText = "";
            if ("10".equals(irangeDevice)) {
                logText = MessageFormat.format("设备音量大小：{0}", "");
                logger.debug("调节设备音量大小操作完成，音量大小：{}");
            } else {
                logText = MessageFormat.format("设备音量大小：{0},设备编号：{1}", volumeValue, deviceCodes);
                logger.debug("调节设备音量大小操作完成，设备编号：{}，音量大小：{}", deviceCodes, volumeValue);
            }
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            if (responseVo.isSuccess()) {
                return responseVo;
            }
        } catch (ServiceException e) {
            logger.error("调节设备音量大小操作出现Service异常：{}", e);
        } catch (Exception e) {
            logger.error("调节设备音量大小操作出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("调节设备音量大小失败");
    }

    /**
     * 设备升级语音包
     *
     * @return
     */
    @RequestMapping("/info/deviceUpgradeVoice")
    public String deviceUpgradeVoice() {
        try {
            return "sb/deviceInfo/deviceInfo-upgradeVoice";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * @param file         音频文件
     * @param voiceType    音频内容类型
     * @param deviceIds    设备IDs
     * @param irangeDevice 设备：全部/部分
     * @param deviceCodes  设备编号
     * @return
     */
    @RequestMapping("/info/updateVoice")
    @ResponseBody
    public ResponseVo<String> updateVoice(@RequestParam(value = "file", required = false) MultipartFile file, String voiceType, String deviceIds, String irangeDevice, String deviceCodes) {
        logger.debug("升级语音包操作开始");
        if (null == file) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择上传文件");
        }
        if (StringUtils.isBlank(voiceType)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择语音类型");
        }
        if (StringUtils.isBlank(irangeDevice)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择升级设备：全部/部分");
        }
        if (irangeDevice.equals("20") && StringUtils.isBlank(deviceIds)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择设备");
        }
        String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
        String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
        String user = SessionUserUtils.getSessionUserName();
        try {
            ResponseVo responseVo = deviceInfoService.upgradeVoice(file, voiceType, deviceIds, irangeDevice, merchantId, user, deviceCodes, merchantCode);
            //操作日志
            String voiceTypeStr = "";
            if (voiceType.equals("10")) {
                voiceTypeStr = "开门语音";
            } else if (voiceType.equals("20")) {
                voiceTypeStr = "购物语音";
            } else if (voiceType.equals("30")) {
                voiceTypeStr = "关门语音";
            }
            String logText = "";
            if (irangeDevice.equals("10")) {
                logText = MessageFormat.format("升级全部设备语音类型:{0},", voiceTypeStr);
                logger.debug("升级语音包操作完成，升级设备范围为：全部设备，语音类型：{}", voiceTypeStr);
            } else {
                logText = MessageFormat.format("升级部分设备语音类型：{0},设备编号：{1}", voiceTypeStr, deviceCodes);
                logger.debug("升级语音包操作完成，升级设备范围为：部分设备，设备编号：{},语音类型：{}", deviceCodes, voiceTypeStr);
            }
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            if (responseVo.isSuccess()) {
                return ResponseVo.getSuccessResponse("升级语音包操作完成");
            }
        } catch (ServiceException e) {
            logger.error("升级语音包操作出现Service异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("升级语音包操作出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("升级语音包操作失败");
    }

    /**
     * 设备升级APK
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/info/deviceUpgradeAPK")
    public String deviceUpgradeAPK(ModelMap modelMap) {
        try {
            List<DeviceInfoDomain> deviceList = new ArrayList<>();
            return "sb/deviceInfo/deviceInfo-upgradeAPK";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 上传apk文件到FTP服务器
     *
     * @param guid
     */
    @RequestMapping("/info/upApkToFtp")
    @ResponseBody
    public ResponseVo upApkToTtp(HttpServletRequest request, String guid, Integer chunk, MultipartFile file, Integer folderType) {
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart && null != file && !file.isEmpty()) {
                //以下的代码是将文件file重新命名并存入Tomcat的webapps目录下项目的下级目录fileDir
                //文件原名
                String fileRealName = file.getOriginalFilename();     //获得原始文件名;
                String[] fileNameSplit = fileRealName.split("\\.");//点号的位置
                String exp = fileNameSplit[fileNameSplit.length - 1];  //截取文件后缀
                //图片类型限制
                if (!"zip".equals(exp)) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("预处理失败,文件只能选取zip文件类型！");
                }
                // 临时目录用来存放所有分片文件  
                String folderName = "";
                if (10 == folderType) {
                    folderName = "gradeVrLib";
                } else if (20 == folderType) {
                    folderName = "apkTempFile";
                }
                String saveFile = System.getProperty("catalina.home") + File.separator + folderName + File.separator;
                String tempFileDir = saveFile + guid;
                File parentFileDir = new File(tempFileDir);
                if (!parentFileDir.exists()) {
                    parentFileDir.mkdirs();
                }
                // 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台  
                if (null == chunk) {
                    chunk = 0;
                }
                File tempPartFile = new File(parentFileDir, guid + "_" + chunk + "." + exp);
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempPartFile);
                return ResponseVo.getSuccessResponse();
            }
            logger.info("上传文件到服务器失败：{}", isMultipart);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("上传文件到服务器失败");
        } catch (Exception e) {
            logger.error("上传apk文件到FTP服务器异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("上传文件到服务器失败");
        }
    }


    /**
     *      * 组合文件
     *      * @param guid
     *      * @param fileName
     *      * @throws Exception
     *     
     */
    @RequestMapping("/info/mergeFile")
    @ResponseBody
    public ResponseVo<String> mergeFile(String guid, String fileName, Integer folderType) {
        // 得到 destTempFile 就是最终的文件  
        //  JsonResult jsonReulst = new JsonResult();
        ResponseVo<String> responseVo = null;
        try {
            String[] fileNameSplit = fileName.split("\\.");//点号的位置
            String exp = fileNameSplit[fileNameSplit.length - 1];  //截取文件后缀
            String folderName = "";
            if (10 == folderType) {
                folderName = "gradeVrLib";
            } else if (20 == folderType) {
                folderName = "apkTempFile";
            }
            String file = System.getProperty("catalina.home") + File.separator + folderName;
            File parentFileDir = new File(file + File.separator + guid);
            String proFile = file + File.separator + DateUtils.getCurrentSTimeNumber() + "." + exp;
            if (parentFileDir.isDirectory()) {
                File destTempFile = new File(proFile);
                for (int i = 0; i < parentFileDir.listFiles().length; i++) {
                    File partFile = new File(parentFileDir, guid + "_" + i + "." + exp);
                    FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
                    //遍历"所有分片文件"到"最终文件"中  
                    FileUtils.copyFile(partFile, destTempfos);
                    destTempfos.close();
                }
                // 删除临时目录中的分片文件  
                FileUtils.deleteDirectory(parentFileDir);
                if (folderType == 20) {
                    //解压文件
                    ResponseVo<List<String>> responseVo1 = ReadFileUtil.unZip(destTempFile, file + File.separator, new ArrayList<String>());
                    if (responseVo1 != null && responseVo1.isSuccess()) {
                        ReadFileUtil.deleteFile(destTempFile);
                        return new ResponseVo<>(responseVo1.getData().get(0));
                    }
                } else if (10 == folderType) {
                    return new ResponseVo<>(proFile);
                }
            }
            return new ResponseVo<>(proFile);
        } catch (Exception e) {
            logger.error("组合文件异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("上传失败！");
        }
    }

    /**
     * 删除临时文件
     *
     * @param apkTempFile
     */
    @RequestMapping("/info/deleTempFile")
    public void deleTempFile(String apkTempFile) {
        File file = new File(apkTempFile);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    /**
     * 向设备发送升级地址和版本号
     *
     * @param apkTempFile  apk文件
     * @param deviceIds    选择部分时，需要设备ID，全部时不需要
     * @param version      版本号
     * @param irangeDevice 0全部，1部分
     * @param dproduceDate 定时时间
     * @param timeType     0立即，1定时
     * @return
     */
    @RequestMapping("/info/updateApk")
    @ResponseBody
    public ResponseVo<String> updateApk(String apkTempFile, String uid, String deviceIds, String version, String irangeDevice, String dproduceDate, String timeType, Integer upFileType, String localAddress) {
        try {
            if (null == upFileType) {
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择上传APK方式");
            }
          /*  if (StringUtils.isBlank(apkTempFile)) {
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择上传文件");
            }*/
            File apkFile = null;
            if (10 == upFileType) {
                apkFile = new File(apkTempFile);
                if (!apkFile.exists()) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("本地APK文件不存在");
                }
            }
            if (20 == upFileType) {
                if (StringUtils.isBlank(localAddress)) {
                    return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请输入本地APK地址");
                } else {
                    if (!localAddress.endsWith("apk")) {
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请输入本地APK文件正确路径");
                    }
                  /*  File temp = new File(localAddress);
                    if (!temp.exists()) {
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("本地APK文件不存在");
                    }*/
                }
            }
            if (StringUtils.isBlank(irangeDevice)) {
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("类型不能为空");
            }
            if (StringUtils.isBlank(version)) {
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("版本号不能为空");
            }
            if (StringUtils.isBlank(timeType)) {
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("升级时间类型不能为空");
            }
            String user = SessionUserUtils.getSessionUserName();
            String[] ids = null;
            if ("0".equals(irangeDevice)) {     // 全部
                String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
                List<DeviceInfo> deviceInfoList = deviceInfoService.selectAllValidDeviceByMerchantId(merchantId);
                if (CollectionUtils.isNotEmpty(deviceInfoList)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (DeviceInfo deviceInfo : deviceInfoList) {
                        if (deviceInfo.getItype() == 30 || deviceInfo.getItype() == 40 || deviceInfo.getItype() == 50 || deviceInfo.getItype() == 60 || deviceInfo.getItype() == 70) {
                            stringBuilder.append(deviceInfo.getId());
                            stringBuilder.append(",");
                        }
                    }
                    ids = stringBuilder.toString().split(",");
                }
            } else {    // 部分
                if (StringUtils.isNotBlank(deviceIds)) {
                    ids = deviceIds.split(",");
                }
            }

            if ("0".equals(timeType)) { // 立即升级
                dproduceDate = "updateNow";
            }
            if (null == ids || ArrayUtils.isEmpty(ids)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("没有正常设备可以升级");
            }
            ResponseVo responseVo = deviceInfoService.recordAndSendApkUpgradeMsg(ids, timeType, version, dproduceDate, user, irangeDevice, apkFile, upFileType, localAddress);

            return responseVo;

        } catch (ServiceException e) {
            logger.error("设备升级Apk失败", e);
        } catch (Exception e) {
            logger.error("设备升级Apk出现Exception异常", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("apk升级出现异常");
    }


    /**
     * 设备升级APK
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/info/vrServerUpgrade")
    public String vrServerUpgrade(ModelMap modelMap) {
        try {
            List<DeviceInfoDomain> deviceList = new ArrayList<>();
            return "sb/deviceInfo/deviceInfo-upgradeVrServer";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 向设备发送视觉服务器升级包地址
     *
     * @param deviceIds    选择部分时，需要设备ID，全部时不需要
     * @param url          升级地址
     * @param version      版本
     * @param irangeDevice 0全部，1部分
     * @param dproduceDate 定时时间
     * @param timeType     0立即，1定时
     * @return
     */
    @RequestMapping("/info/updateVrServer")
    public @ResponseBody
    ResponseVo<String> updateVrServer(String deviceIds, String url, String version, String irangeDevice, String dproduceDate, String timeType) {
        try {
            if (StringUtils.isNotBlank(irangeDevice) && StringUtils.isNotBlank(url) && StringUtils.isNotBlank(version)) {
                String user = SessionUserUtils.getSessionUserName();
                String[] ids = null;
                if ("0".equals(irangeDevice)) {     // 全部
                    String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
                    List<DeviceInfo> deviceInfoList = deviceInfoService.selectAllValidDeviceByMerchantId(merchantId);
                    if (CollectionUtils.isNotEmpty(deviceInfoList)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (DeviceInfo deviceInfo : deviceInfoList) {
                            if (deviceInfo.getItype() == 30 || deviceInfo.getItype() == 40) {
                                stringBuilder.append(deviceInfo.getId());
                                stringBuilder.append(",");
                            }
                        }
                        ids = stringBuilder.toString().split(",");
                    }
                } else {    // 部分
                    if (StringUtils.isNotBlank(deviceIds)) {
                        ids = deviceIds.split(",");
                    }
                }

                if ("0".equals(timeType)) { // 立即升级
                    dproduceDate = "updateNow";
                }
                if (null == ids || ArrayUtils.isEmpty(ids)) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("没有正常设备可以升级");
                }
                String urlPrefix = GrpParaUtil.getValue(GrpParaUtil.UPDATE_URL_PREX_CONFIG, "vrOsServerUpgrade");
                ResponseVo responseVo = deviceInfoService.recordAndSendOsServiceUpgradeMsg(ids, timeType, urlPrefix + url, version, dproduceDate, user, irangeDevice);
                return responseVo;
            } else {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("地址不能为空");
            }
        } catch (Exception e) {
            logger.error("设备视觉服务器升级出现Exception异常", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("视觉服务器升级包出现异常");
    }


    /**
     * 设备升级
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/info/vrLibUpgrade")
    public String vrLibUpgrade(ModelMap modelMap) {
        try {
            return "sb/deviceInfo/deviceInfo-upgradeVrLib";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 向设备发送视觉识别库升级包地址
     *
     * @param deviceIds    选择部分时，需要设备ID，全部时为空
     * @param url          升级包地址
     * @param version      版本
     * @param irangeDevice 0全部，1部分
     * @param dproduceDate 定时时间
     * @param timeType     0立即，1定时
     * @return
     */
    @RequestMapping("/info/updateVrLib")
    public @ResponseBody
    ResponseVo<String> updateVrLib(String deviceIds, String url, String version, String irangeDevice, String dproduceDate, String timeType,
                                   Integer upFileType, String tempFile) {
        try {
           /* if (StringUtils.isNotBlank(irangeDevice) && StringUtils.isNotBlank(url)) {*/
            if (null == upFileType) {
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请选择上传视觉库方式！");
            }
            File vrFile = null;
            if (10 == upFileType) {
                vrFile = new File(tempFile);
                if (!vrFile.exists()) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("本地APK文件不存在");
                }
            } else if (20 == upFileType && StringUtils.isBlank(url)) {
                //本地文件上传
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("视觉识别库升级包地址不能为空");
            }

            String user = SessionUserUtils.getSessionUserName();
            String[] ids = null;
            if ("0".equals(irangeDevice)) {     // 全部
                String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
                List<DeviceInfo> deviceInfoList = deviceInfoService.selectAllValidDeviceByMerchantId(merchantId);
                if (CollectionUtils.isNotEmpty(deviceInfoList)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (DeviceInfo deviceInfo : deviceInfoList) {
                        if (deviceInfo.getItype() == 30) {
                            stringBuilder.append(deviceInfo.getId());
                            stringBuilder.append(",");
                        }
                    }
                    ids = stringBuilder.toString().split(",");
                }
            } else {    // 部分
                if (StringUtils.isNotBlank(deviceIds)) {
                    ids = deviceIds.split(",");
                }
            }

            if ("0".equals(timeType)) { // 立即升级
                dproduceDate = "updateNow";
            }
            if (null == ids || ArrayUtils.isEmpty(ids)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("没有正常设备可以升级");
            }
            String urlPrefix = GrpParaUtil.getValue(GrpParaUtil.UPDATE_URL_PREX_CONFIG, "updateVrOsFeatureLib");
            ResponseVo responseVo = deviceInfoService.recordAndSendVrOsFeatureLibUpgradeMsg(ids, timeType, urlPrefix + url, version, dproduceDate, user, irangeDevice, upFileType, vrFile);
            return responseVo;
           /* } else {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("视觉识别库升级包地址不能为空");
            }*/
        } catch (Exception e) {
            logger.error("设备视觉识别库升级出现Exception异常", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("视觉识别库升级出现异常");
    }


    /**
     * 给设备推送广告
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/info/sendAdToDevice")
    public String sendAdToDevice(ModelMap modelMap) {
        try {
            return "sb/deviceInfo/deviceInfo-sendAdToDevice";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * @param deviceIds
     * @param irangeDevice
     * @param adPositon
     * @return
     */
    @RequestMapping("/info/sendAdInfo")
    public @ResponseBody
    ResponseVo<String> sendAdInfo(String deviceIds, String irangeDevice, String adPositon) {

        try {
            if (StringUtils.isNotBlank(irangeDevice) && StringUtils.isNotBlank(adPositon)) {
                String ids = "";
                String user = SessionUserUtils.getSessionUserName();
                String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
                String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
                if ("0".equals(irangeDevice)) {     // 全部
                    List<DeviceInfo> deviceInfoList = deviceInfoService.selectAllValidDeviceByMerchantId(merchantId);
                    if (CollectionUtils.isNotEmpty(deviceInfoList)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (DeviceInfo deviceInfo : deviceInfoList) {
                            if (deviceInfo.getItype() == 30) {
                                stringBuilder.append(deviceInfo.getId());
                                stringBuilder.append(",");
                            }
                        }
                        if (StringUtils.isNotBlank(stringBuilder.toString())) {
                            ids = stringBuilder.toString();
                        }
                    }
                } else {    // 部分
                    if (StringUtils.isNotBlank(deviceIds)) {
                        ids = deviceIds;
                    }
                }

                // 给在线设备发消息
                if (StringUtils.isNotBlank(ids)) {
                    if ("1".equals(adPositon)) {
                        deviceInfoService.sendAdOne(ids, user, merchantCode);
                    } else if ("2".equals(adPositon)) {
                        deviceInfoService.sendAdTwo(ids, user, merchantCode);
                    } else if ("3".equals(adPositon)) {
                        deviceInfoService.sendAdThree(ids, user, merchantCode);
                    }
                    return ResponseVo.getSuccessResponse();
                } else {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("没有正常的视觉设备可以推送广告");
                }
            } else {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("广告位置不能为空");
            }
        } catch (Exception e) {
            logger.error("设备推送广告出现Exception异常", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备推送广告出现异常");
    }

    /**
     * 设置设备实时盘货开关
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/info/sendRealTimeReplentshment")
    public String sendRealTimeReplentshment(ModelMap modelMap) {
        try {
            return "sb/deviceInfo/deviceInfo-realtimeSwitch";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 批量修改云端柜子实时盘货购物车开关
     *
     * @param shoppingInventory      顾客购物车
     * @param replenishmentInventory 补货员购物车
     * @param deviceIds              设备ID
     * @param deviceCodes            设备编号
     * @param irangeDevice           设备类型 10=全部，20=部分
     * @return
     */
    @RequestMapping("/info/batchRealtimeSwitch")
    @ResponseBody
    public ResponseVo<String> realtimeSwitch(String shoppingInventory, String replenishmentInventory, String deviceIds, String deviceCodes, String irangeDevice) {
        if (StringUtils.isBlank(irangeDevice)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择操作设备类型");
        }
        if ("20".equals(irangeDevice) && StringUtils.isBlank(deviceIds) && StringUtils.isBlank(deviceCodes)) {   // 选择具体设备
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择操作设备");
        }
        if (StringUtils.isBlank(shoppingInventory)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("顾客购物车类型不能为空");
        }
        if (StringUtils.isBlank(replenishmentInventory)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("补货员购物车不能为空");
        }
        String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
        String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
        try {
            return deviceInfoService.batchRealTimeShoppingCartSwitch(merchantId, merchantCode, shoppingInventory, replenishmentInventory, deviceIds, deviceCodes, irangeDevice);
        } catch (Exception e) {
            logger.error("推送实时盘货开关出现Exception异常", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("推送实时盘货开关失败");
    }


    /**
     * 设备选择分组（单选）
     *
     * @param sid      设备ID集合
     * @param modelMap
     * @return
     */
    @RequestMapping("/info/deviceAddToGroup")
    public String radioSelectList(String sid, ModelMap modelMap) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                List<DeviceInfoDomain> deviceList = new ArrayList<>();
                String[] deviceArray = sid.split(",");
                deviceList = deviceInfoService.selectBykeys(deviceArray);
                modelMap.put("sid", sid);
                if (CollectionUtils.isNotEmpty(deviceList)) {
                    modelMap.put("deviceList", deviceList);
                }
                return "/sb/groupManage/groupManage-select-list";
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 设备加入分组
     *
     * @param deviceIds
     * @param groupId
     * @return
     */
    @RequestMapping("/groupManage/addToGroup")
    public @ResponseBody
    ResponseVo<String> addToGroup(String deviceIds, String groupId) {
        try {
            if (StringUtil.isNotBlank(deviceIds) && StringUtils.isNotBlank(groupId)) {//分组ID_设备IDs
                //设备和分组信息入设备分组关系表
                groupRelationshipService.insertDeviceIds(deviceIds, groupId);

                //操作日志
                GroupManage groupManage = groupManageService.selectByPrimaryKey(groupId);
                String logText = MessageFormat.format("设备加入分组，分组名称{0}", groupManage.getSgroupName());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                logger.debug("设备加入分组：{}", groupId);
                return ResponseVo.getSuccessResponse(groupId);
            }
        } catch (ServiceException e) {
            logger.error("设备分组出现ServiceException异常", e);
        } catch (Exception e) {
            logger.error("设备分组出现Exception异常", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备分组service异常");
    }

    /**
     * 根据设备ID查询设备标准库存
     *
     * @param deviceId 设备ID
     * @return 标准库存
     */
    @RequestMapping("/info/getCargoRoadCommodity")
    public @ResponseBody
    ResponseVo<List<List<DetailStockDomain>>> getCargoRoadCommodity(String deviceId) {
        try {
            if (StringUtils.isNotBlank(deviceId)) {
                Integer layerNum = this.getDeviceLayerNum(deviceId);
                List<List<DetailStockDomain>> detailStockDomain = standardDetailService.selectDetailStandard(deviceId, layerNum);
                if (CollectionUtils.isNotEmpty(detailStockDomain)) {
                    return ResponseVo.getSuccessResponse(detailStockDomain);
                }
            }
        } catch (Exception e) {
            logger.error("查询设备标准库存异常", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("查询设备标准库存异常");
    }


    /**
     * 查询该设备所属商户的分组信息
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(" /info/getDeviceGroup")
    public @ResponseBody
    ResponseVo<List<GroupManage>> getDeviceGroup(String merchantId) {
        try {
            if (StringUtils.isNotBlank(merchantId)) {
                List<GroupManage> list = groupManageService.selectDeviceGroup(merchantId);
                return ResponseVo.getSuccessResponse(list);
            }
        } catch (Exception e) {
            logger.error("查询该设备所属商户的分组信息异常", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("查询该设备所属商户的分组信息出错");
    }

    /**
     * 设备二维码上传
     *
     * @param file     图片文件
     * @param pathName 图片路径
     * @return
     */
    private String uploadHome(MultipartFile file, String pathName) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
            String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/
            String tempName = DateUtils.getCurrentSTimeNumber() + ".png";//文件名=="时间"+"."+"原图片名后缀"
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-03-07/20180307151504492.jpg
            }
        } catch (IOException e) {
            logger.error("上传文件出现IOException异常：{}", e);
        }
        return null;
    }

    /**
     * 获取设备层数
     *
     * @param deviceId 设备Id
     * @return
     */
    private Integer getDeviceLayerNum(String deviceId) {
        DeviceModel deviceModel = deviceModelService.selectByDeviceId(deviceId);//设备详细信息
        //设备层数
        Integer ilayerNum = 5;
        if (null != deviceModel && null != deviceModel.getIlayerNum() &&
                deviceModel.getIlayerNum().intValue() > 0) {
            ilayerNum = deviceModel.getIlayerNum();
        } else {
            String layerNumStr = SysParaUtil.getValue("default_layer_num");
            if (StringUtil.isNotBlank(layerNumStr)) {
                try {
                    ilayerNum = Integer.parseInt(layerNumStr);
                } catch (Exception e) {
                    ilayerNum = 5;
                }
            }
        }
        return ilayerNum;
    }

    /**
     * 跳转到标准库存页面
     *
     * @param sid      设备ID
     * @param modelMap
     * @return
     */
    @RequestMapping("/info/standardStock")
    public String toStandardStock(String sid, ModelMap modelMap) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //查询设备信息
                DeviceInfoDomain deviceInfoDomain = deviceInfoService.selectBydeviceId(sid);//设备基础信息
                StandardStock standardStocktemp = new StandardStock();
                standardStocktemp.setSdeviceId(deviceInfoDomain.getId());
                List<StandardStock> standardStockList = standardStockService.selectByEntityWhere(standardStocktemp);
                if (deviceInfoDomain != null) {
                    StandardStock standardStock = new StandardStock();
                    standardStock.setSdeviceId(deviceInfoDomain.getId());
                    if (CollectionUtils.isNotEmpty(standardStockList)) {
                        standardStock = standardStockList.get(0);
                    }
                    //设备层数
                    Integer ilayerNum = this.getDeviceLayerNum(deviceInfoDomain.getId());
                    modelMap.put("ilayerNum", ilayerNum);//设备层数

                    //获取设备标准库存数据
                    List<List<DetailStockDomain>> detailList = standardDetailService.selectDetailStandard(deviceInfoDomain.getId(), ilayerNum);
                    if (CollectionUtils.isNotEmpty(detailList)) {
                        modelMap.put("detailList", detailList);//标准库存明细
                    }

                    modelMap.put("deviceInfoDomain", deviceInfoDomain);//设备信息
                    modelMap.put("standardStock", standardStock);//标准库存
                    return "sb/deviceInfo/standardStock-list";
                }
            }
        } catch (Exception e) {
            logger.error("编辑标准库存出现Exception异常:{}", e);
        }
        return ExceptionUtil.to500();
    }

    /***
     *  操作设备标准库存
     * @param standardStock 标准库存数据
     * @param request
     * @return
     */
    @RequestMapping("/standardStock/operCommodity")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<StandardStock> operCommodity(StandardStock standardStock, HttpServletRequest request) {
        try {
            if (null == standardStock || StringUtil.isBlank(standardStock.getSdeviceId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("数据异常！");
            }
            if (null == standardStock.getIstatus() || (standardStock.getIstatus().intValue() != 10
                    && standardStock.getIstatus().intValue() != 20)) {
                standardStock.setIstatus(20);
            }
            //获取设备层数
            Integer ilayerNum = this.getDeviceLayerNum(standardStock.getSdeviceId());
            standardStockService.updateStandardStock(standardStock, ilayerNum, request);
            String logText = MessageFormat.format("更新设备标准库存，设备编号{0},标准库存状态{1}", standardStock.getSdeviceCode(), standardStock.getIstatus());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse(standardStock);
        } catch (ServiceException e) {
            logger.error("更新设备标准库存异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("操作设备标准库存异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("标准库存更新异常！");
    }

    /**
     * 动态生成补货单
     *
     * @param sid      设备ID
     * @param modelMap
     * @return
     */
    @RequestMapping("/info/generateDynamicReplenishment")
    public String toGenerateDynamicReplenishment(String sid, ModelMap modelMap) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //查询设备信息
                DeviceInfoDomain deviceInfoDomain = deviceInfoService.selectBydeviceId(sid);//设备基础信息
                if (null != deviceInfoDomain) {
                    DynamicReplenishmentDto replenishmentDto = new DynamicReplenishmentDto();
                    replenishmentDto.setSdeviceId(deviceInfoDomain.getId());
                    //调用服务
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ReplenishmentPlanServicesDefine.DYNAMIC_REPLENISHMENT_SERVICE);// 服务名称
                    // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<DynamicReplenishmentResult>>() {
                    });
                    invoke.setRequestObj(replenishmentDto); // post 参数
                    ResponseVo<DynamicReplenishmentResult> resVO = (ResponseVo<DynamicReplenishmentResult>) invoke.invoke();
                    if (resVO.isSuccess()) {
                        DynamicReplenishmentResult result = resVO.getData();
                        // 查询设备商品表中商品信息，拼接商品名称
                        List<DetailStockDomain> commodityNameList = standardDetailService.selectByDeviceId(deviceInfoDomain.getId());
                        List<CommodityResult> commodityResultList = result.getCommodityResults();
                        if (CollectionUtils.isNotEmpty(commodityNameList) && CollectionUtils.isNotEmpty(commodityResultList)) {
                            Map<String, String> commodityMap = new HashMap<>(); //  商品去重
                            for (DetailStockDomain stockDomain : commodityNameList) {
                                commodityMap.put(stockDomain.getScommodityId(), stockDomain.getCommodityFullName());
                            }
                            if (MapUtils.isNotEmpty(commodityMap)) {
                                for (Map.Entry<String, String> map : commodityMap.entrySet()) {
                                    for (CommodityResult commodityResult : commodityResultList) {
                                        if (map.getKey().equals(commodityResult.getScommodityId())) {
                                            commodityResult.setScommodityName(map.getValue());
                                        }
                                    }
                                }
                            }
                        }
                        modelMap.put("replenishment", result);
                        if (result.isUpdate()) {
                            return "sb/deviceInfo/dynamic_replenishment";//可以修改
                        } else {
                            return "sb/deviceInfo/dynamic_replenishment_view";//不能修改
                        }
                    }
                    return ExceptionUtil.toBussinessError(resVO.getMsg(), -1, modelMap);
                }
            }
            return ExceptionUtil.to404();
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 动态生成补货单
     *
     * @param replenishmentVo 补货单参数
     * @param request
     * @return
     */
    @RequestMapping("/replenishment/generate")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<ReplenishmentPlan> generateReplenishment(ReplenishmentVo replenishmentVo, HttpServletRequest request) {
        try {
            if (null == replenishmentVo || null == replenishmentVo.getCommodityIds() || replenishmentVo.getCommodityIds().length <= 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("数据异常！");
            }
            if (StringUtil.isBlank(replenishmentVo.getDeviceId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("补货单设备信息数据异常！");
            }
            ResponseVo<ReplenishmentPlan> responseVo = replenishmentPlanService.generateReplenishment(replenishmentVo, request);
            if (responseVo.isSuccess()) {
                String logText = MessageFormat.format("动态生成补货单，设备编号{0}, 生成时间：{1}", responseVo.getData().getSdeviceCode(), DateUtils.dateToString(responseVo.getData().getTgenerateTime()));
                LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
                return responseVo;
            } else {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(responseVo.getMsg());
            }
        } catch (ServiceException e) {
            logger.error("动态生成补货单异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("操作动态生成补货单异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("动态生成补货单异常！");
    }

    /**
     * 跳转到向设备发送盘货指令页面
     *
     * @param deviceId 设备ID
     * @return
     */
    @RequestMapping("/info/inventory")
    public String inventory(String deviceId, ModelMap modelMap) {
        try {
            if (StringUtils.isEmpty(deviceId)) {
                return ExceptionUtil.to404();
            }
            modelMap.put("deviceId", deviceId);
            return "sb/deviceInfo/deviceInfo-activeInventory";
        } catch (ServiceException e) {
            logger.error("主动盘货消息发送异常：{}", e);
        } catch (Exception e) {
            logger.error("向设备发送盘货指令失败：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 发送盘货指令
     *
     * @param deviceId
     * @return
     */
    @RequestMapping("/info/sendDeviceStockMsg")
    @ResponseBody
    public ResponseVo<String> sendDeviceStockMsg(String deviceId) {
        try {
            if (StringUtils.isEmpty(deviceId)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备ID为空");
            }
            ResponseVo<String> responseVo = deviceInfoService.inventory(deviceId);
            //操作日志
            String logText = MessageFormat.format("获取设备当前库存信息", "");
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return responseVo;
        } catch (ServiceException e) {
            logger.error("发送盘货指令出现Service异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("发送盘货指令出现异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("获取设备当前库存信息出现异常！");
        }
    }

    /**
     * 获取设备当前库存信息
     *
     * @param deviceId 设备ID
     * @return
     */
    @RequestMapping("/info/getDeviceStock")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public ResponseVo<List<DeviceInventoryStockDomain>> getDeviceStock(String deviceId) {
        try {
            if (StringUtils.isEmpty(deviceId)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备ID为空");
            }
            ResponseVo<List<DeviceInventoryStockDomain>> responseVo = deviceInfoService.getDeviceStock(deviceId);
            //操作日志
            String logText = MessageFormat.format("获取设备当前库存信息", "");
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return responseVo;
        } catch (ServiceException e) {
            logger.error("获取设备当前库存信息出现Service异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("获取设备当前库存信息出现异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("获取设备当前库存信息出现异常！");
        }
    }

    /**
     * 修改当前库存信息
     *
     * @param deviceId 设备ID
     * @param request  商品信息
     * @return
     */
    @RequestMapping("/info/saveActiveInventoryStock")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public ResponseVo<String> saveActiveInventoryStock(String deviceId, String commodityIds, HttpServletRequest request) {
        try {
            if (StringUtils.isEmpty(deviceId)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备ID为空");
            }
            ResponseVo<String> responseVo = deviceInfoService.saveActiveInventoryStockInfo(deviceId, commodityIds, request);
            //操作日志
            String logText = MessageFormat.format("保存设备当前库存信息", "");
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return responseVo;
        } catch (ServiceException e) {
            logger.error("保存设备当前库存信息出现Service异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("保存设备当前库存信息出现异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("保存设备当前库存信息出现异常！");
        }
    }


}
