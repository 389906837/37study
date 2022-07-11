package com.cloud.cang.wap.index.web;


import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.SiteResponseVo;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.index.vo.ReOpenSuccessData;
import com.cloud.cang.wap.rm.service.ReplenishmentCommodityService;
import com.cloud.cang.wap.rm.service.ReplenishmentPlanService;
import com.cloud.cang.wap.rm.service.ReplenishmentRecordService;
import com.cloud.cang.wap.rm.vo.ReplenishmentCommodityVo;
import com.cloud.cang.wap.sb.service.DeviceInfoService;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import com.cloud.cang.wap.sh.service.MerchantInfoService;
import com.cloud.cang.wap.sm.service.DeviceStockService;
import com.cloud.cang.wap.sm.vo.DeviceStockVo;
import com.cloud.cang.wap.sys.service.OperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/***
 * 补货员首页
 */
@Controller
@RequestMapping("/replenishment")
public class ReplenishmentIndexController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private ReplenishmentRecordService replenishmentRecordService;
    @Autowired
    private ReplenishmentCommodityService replenishmentCommodityService;
    @Autowired
    private ReplenishmentPlanService replenishmentPlanService;
    @Autowired
    private ICached iCached;
    @Autowired
    private DeviceStockService deviceStockService;
    private static Logger logger = LoggerFactory.getLogger(ReplenishmentIndexController.class);

    /**
     * 首页数据
     *
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(DeviceVo deviceVo, ModelMap map, HttpServletRequest request) throws Exception {
        //获取用户数据
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        deviceVo = indexService.indexExceptionDealwith(authVo, deviceVo, request);
        if (StringUtil.isNotBlank(deviceVo.getErrorCode())) {
            return WapUtils.errorDealWith(deviceVo.getErrorCode(), "");
        }
        //补货员信息
        Operator operator = operatorService.selectByMemberName(authVo.getUserName(), authVo.getSmerchantCode());
        if (null == operator) {
            map.put("resVo", new SiteResponseVo(false, -1001, "访问异常，请重新操作", -1));
            return new ModelAndView("error/error");
        }
        map.put("srealName", operator.getSrealName());//真实姓名
        //获取首页数据
        indexService.getIndexData(deviceVo, authVo, request, 20, map);
        iCached.remove("is_replenishment_scan_" + authVo.getId());
        return new ModelAndView("replenishment/index_new");
    }

    /**
     * 开门成功
     *
     * @throws Exception
     */
    @RequestMapping("/openSuccess")
    @ResponseBody
    public ResponseVo openSuccess() throws Exception {
        logger.debug("补货员开门成功...");
        ReOpenSuccessData reOpenSuccessData = new ReOpenSuccessData();
        DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
        logger.info("补货员开门成功，设备信息:{}", deviceVo);
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
        Map<String, Object> params = new HashMap<>();
        //商户客户端配置信息
        params = indexService.getMerchantClientConfByMap(params, deviceInfo.getSmerchantCode());
        reOpenSuccessData.setOpen(params);
        reOpenSuccessData.setOpenHint((String) params.get("openHint"));
        //商户信息
        MerchantInfo merchantInfo = merchantInfoService.selectByCode(deviceInfo.getSmerchantCode());
        if (null != merchantInfo) {
            //map.put("merchantCode", merchantInfo.getScode());
            reOpenSuccessData.setMerchantCode(merchantInfo.getScode());
            MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode());
            //map.put("merchantName", clientConf.getSshortName());
            reOpenSuccessData.setMerchantName(clientConf.getSshortName());
        }
        //获取用户数据
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        //设备信息
       /* map.put("deviceCode", deviceInfo.getScode());//设备编号
        map.put("deviceName", deviceInfo.getSname());//设备编号
        map.put("deviceAddress", CoreUtils.generateDeviceAddress(deviceInfo));//设备地址*/
        reOpenSuccessData.setDeviceCode(deviceInfo.getScode());
        reOpenSuccessData.setDeviceName(deviceInfo.getSname());
        reOpenSuccessData.setDeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
        //补货员信息
        Operator operator = operatorService.selectByMemberName(authVo.getUserName(), authVo.getSmerchantCode());
        if (null != operator) {
            //map.put("srealName", operator.getSrealName());//真实姓名
            reOpenSuccessData.setSrealName(operator.getSrealName());
        }
      //  String openTime = DateUtils.dateToString(new Date(), "yyyy年MM月dd日 HH:mm:ss");
        String openTime = DateUtils.dateToString(new Date(), "yyyy/MM/dd HH:mm");
        iCached.put("openTime_" + authVo.getId(), openTime);
        //开门时间
        //map.put("openTime", openTime);
        reOpenSuccessData.setOpenTime(openTime);
        return ResponseVo.getSuccessResponse(reOpenSuccessData);
    }

    /**
     * 补货员关门成功 补货成功
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/closeDoorSuccess")
    public String closeDoorSuccess(String recordCode, ModelMap modelMap) throws Exception {
        logger.info("补货员关门成功");
        if (StringUtil.isBlank(recordCode)) {
            return "replenishment/empty";
        }
        modelMap.put("recordCode", recordCode);
        ReplenishmentRecord record = replenishmentRecordService.selectByCode(recordCode);
        if (null == record) {
            return "replenishment/empty";
        }
        modelMap.put("record", record);

        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(record.getSdeviceId());
        //商户信息
        MerchantInfo merchantInfo = merchantInfoService.selectByCode(deviceInfo.getSmerchantCode());
        if (null != merchantInfo) {
            modelMap.put("merchantCode", merchantInfo.getScode());
            MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode());
            modelMap.put("merchantName", clientConf.getSshortName());
        }
        //获取用户数据
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        //设备信息
        modelMap.put("deviceCode", deviceInfo.getScode());//设备编号
        modelMap.put("deviceName", deviceInfo.getSname());//设备编号
        modelMap.put("deviceAddress", CoreUtils.generateDeviceAddress(deviceInfo));//设备地址
        //补货员信息
        Operator operator = operatorService.selectByMemberName(authVo.getUserName(), authVo.getSmerchantCode());
        if (null != operator) {
            modelMap.put("srealName", operator.getSrealName());//真实姓名
        }
        //String closeTime = DateUtils.dateToString(new Date(), "yyyy年MM月dd日 HH:mm:ss");
        String closeTime = DateUtils.dateToString(new Date(), "yyyy/MM/dd HH:mm");
        //开门时间
        modelMap.put("openTime", iCached.get("openTime_" + authVo.getId()));
        iCached.remove("openTime_" + authVo.getId());
        modelMap.put("closeTime", closeTime);
        //上架商品
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sreplenishmentId", record.getId());
        params.put("itype", 10);
        params.put("deviceId", deviceInfo.getId());
        List<ReplenishmentCommodityVo> addList = replenishmentCommodityService.selectByMap(params);
        if (null != addList) {
            modelMap.put("addList", addList);
            modelMap.put("addSize", record.getIactualShelves());
        }
        //下架商品
        params = new HashMap<String, Object>();
        params.put("sreplenishmentId", record.getId());
        params.put("itype", 20);
        params.put("deviceId", deviceInfo.getId());
        List<ReplenishmentCommodityVo> subList = replenishmentCommodityService.selectByMap(params);
        if (null != subList) {
            modelMap.put("subList", subList);
            modelMap.put("subSize", record.getIactualUnder());
        }

        return "replenishment/success_new";
    }

    /***
     * 补货 标记完成
     * @param deviceCode 设备编号
     * @return
     */
    @RequestMapping("/markingDone")
    public @ResponseBody
    ResponseVo<String> markingDone(String deviceCode) {
        try {
            if (StringUtil.isBlank(deviceCode)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数异常");
            }
            //获取商户数据
            DeviceInfo deviceInfo = deviceInfoService.selectByCode(deviceCode);
            if (null == deviceInfo) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备不存在");
            }
            //获取用户数据
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            if (!authVo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("非法操作，如有疑问请联系客服");
            }
            replenishmentPlanService.updateByLastTime(deviceCode);
            ResponseVo<String> responseVo = ResponseVo.getSuccessResponse("标记完成");
            responseVo.setMsg("标记完成");
            return responseVo;
        } catch (Exception e) {
            logger.error("补货完成标记失败：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("补货完成标记失败，请重新操作");
    }

    /**
     * 是否扫码进入查看设备库存
     * @param request
     * @return
     */
    @RequestMapping("/selectDevice")
    @ResponseBody
    public ResponseVo<DeviceVo> selectDevice(HttpServletRequest request) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setSuccess(true);
        if (WapUtils.isWXRequest(request)) {
            responseVo.setMsg("1");
        } else if (WapUtils.isAlipayRequest(request)) {
            responseVo.setMsg("2");
        }
        DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
        if (null != deviceVo) {
            return responseVo;
        }
        responseVo.setSuccess(false);
        return responseVo;
    }

    /**
     * 查看设备库存
     *
     * @param map
     * @return
     */
    @RequestMapping("/deviceStock")
    public String deviceStock(ModelMap map) throws Exception {
        //获取用户数据
        //设备信息不存在 获取缓存
        DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
        if (null != deviceVo) {
            DeviceInfo deviceInfo = deviceInfoService.selectByCode(deviceVo.getDeviceCode());
            MerchantInfo merchantInfo = merchantInfoService.selectByCode(deviceVo.getMerchantCode());
            map.put("address", CoreUtils.generateDeviceAddress(deviceInfo));
            map.put("deviceInfo", deviceInfo);
            map.put("merchantInfo", merchantInfo);
        }
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        iCached.remove("is_device_stock_scan_" + authVo.getId());
        return "replenishment/device_stock";
    }
}
