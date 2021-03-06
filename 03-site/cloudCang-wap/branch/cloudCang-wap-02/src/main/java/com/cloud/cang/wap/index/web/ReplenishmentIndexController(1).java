package com.cloud.cang.wap.index.web;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.inventory.CommodityDiffVo;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.rm.ReplenishmentConfirmResult;
import com.cloud.cang.rm.ReplenishmentDto;
import com.cloud.cang.rm.ReplenishmentResult;
import com.cloud.cang.rm.StockServicesDefine;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.SiteResponseVo;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.service.MemberInfoService;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/***
 * ???????????????
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
    @Autowired
    private MemberInfoService memberInfoService;

    private static Logger logger = LoggerFactory.getLogger(ReplenishmentIndexController.class);

    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(DeviceVo deviceVo, ModelMap map, HttpServletRequest request) throws Exception {
        //??????????????????
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        deviceVo = indexService.indexExceptionDealwith(authVo, deviceVo, request);
        if (StringUtil.isNotBlank(deviceVo.getErrorCode())) {
            return WapUtils.errorDealWith(deviceVo.getErrorCode(), "");
        }
        //???????????????
        Operator operator = operatorService.selectByMemberName(authVo.getUserName(), authVo.getSmerchantCode());
        if (null == operator) {
            map.put("resVo", new SiteResponseVo(false, -1001, "??????????????????????????????", -1));
            return new ModelAndView("error/error");
        }
        map.put("srealName", operator.getSrealName());//????????????
        //??????????????????
        indexService.getIndexData(deviceVo, authVo, request, 20, map);
        iCached.remove("is_replenishment_scan_" + authVo.getId());
        return new ModelAndView("replenishment/index_new");
    }

    /**
     * ????????????
     *
     * @throws Exception
     */
    @RequestMapping("/openSuccess")
    @ResponseBody
    public ResponseVo openSuccess() throws Exception {
        logger.debug("?????????????????????...");
        ReOpenSuccessData reOpenSuccessData = new ReOpenSuccessData();
        DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
        logger.info("????????????????????????????????????:{}", deviceVo);
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
        Map<String, Object> params = new HashMap<>();
        //???????????????????????????
        params = indexService.getMerchantClientConfByMap(params, deviceInfo.getSmerchantCode());
        reOpenSuccessData.setOpen(params);
        reOpenSuccessData.setOpenHint((String) params.get("openHint"));
        //????????????
        MerchantInfo merchantInfo = merchantInfoService.selectByCode(deviceInfo.getSmerchantCode());
        if (null != merchantInfo) {
            //map.put("merchantCode", merchantInfo.getScode());
            reOpenSuccessData.setMerchantCode(merchantInfo.getScode());
            MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode());
            //map.put("merchantName", clientConf.getSshortName());
            reOpenSuccessData.setMerchantName(clientConf.getSshortName());
        }
        //??????????????????
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        //????????????
       /* map.put("deviceCode", deviceInfo.getScode());//????????????
        map.put("deviceName", deviceInfo.getSname());//????????????
        map.put("deviceAddress", CoreUtils.generateDeviceAddress(deviceInfo));//????????????*/
        reOpenSuccessData.setDeviceCode(deviceInfo.getScode());
        reOpenSuccessData.setDeviceName(deviceInfo.getSname());
        reOpenSuccessData.setDeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
        //???????????????
        Operator operator = operatorService.selectByMemberName(authVo.getUserName(), authVo.getSmerchantCode());
        if (null != operator) {
            //map.put("srealName", operator.getSrealName());//????????????
            reOpenSuccessData.setSrealName(operator.getSrealName());
        }
        //  String openTime = DateUtils.dateToString(new Date(), "yyyy???MM???dd??? HH:mm:ss");
        String openTime = DateUtils.dateToString(new Date(), "yyyy/MM/dd HH:mm");
        iCached.put("openTime_" + authVo.getId(), openTime);
        //????????????
        //map.put("openTime", openTime);
        reOpenSuccessData.setOpenTime(openTime);
        return ResponseVo.getSuccessResponse(reOpenSuccessData);
    }

    /**
     * ????????????????????? ????????????
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/closeDoorSuccess")
    public String closeDoorSuccess(String recordCode, ModelMap modelMap) throws Exception {
        logger.info("?????????????????????");
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
        //????????????
        MerchantInfo merchantInfo = merchantInfoService.selectByCode(deviceInfo.getSmerchantCode());
        if (null != merchantInfo) {
            modelMap.put("merchantCode", merchantInfo.getScode());
            MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode());
            modelMap.put("merchantName", clientConf.getSshortName());
        }
        //??????????????????
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        //????????????
        modelMap.put("deviceCode", deviceInfo.getScode());//????????????
        modelMap.put("deviceName", deviceInfo.getSname());//????????????
        modelMap.put("deviceAddress", CoreUtils.generateDeviceAddress(deviceInfo));//????????????
        //???????????????
        Operator operator = operatorService.selectByMemberName(authVo.getUserName(), authVo.getSmerchantCode());
        if (null != operator) {
            modelMap.put("srealName", operator.getSrealName());//????????????
        }
        //String closeTime = DateUtils.dateToString(new Date(), "yyyy???MM???dd??? HH:mm:ss");
        String closeTime = DateUtils.dateToString(new Date(), "yyyy/MM/dd HH:mm");
        //????????????
        modelMap.put("openTime", iCached.get("openTime_" + authVo.getId()));
        iCached.remove("openTime_" + authVo.getId());
        modelMap.put("closeTime", closeTime);
        //????????????
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sreplenishmentId", record.getId());
        params.put("itype", 10);
        params.put("deviceId", deviceInfo.getId());
        List<ReplenishmentCommodityVo> addList = replenishmentCommodityService.selectByMap(params);
        if (null != addList) {
            modelMap.put("addList", addList);
            modelMap.put("addSize", record.getIactualShelves());
        }
        //????????????
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

    /**
     * ????????????????????? ????????????
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/closeDoorConfirm")
    public String closeDoorConfirm(HttpServletRequest request, ModelMap modelMap) throws Exception {
        logger.info("?????????????????????");
        String deviceCode = request.getParameter("confirmData");
        if (StringUtil.isBlank(deviceCode)) {
            return "replenishment/empty";
        }
        String json = (String) iCached.get("CONFIRM_" + deviceCode);
        if (StringUtil.isBlank(json)) {
            return "replenishment/empty";
        }
        ReplenishmentConfirmResult replenishmentConfirmResult = JSON.parseObject(json, ReplenishmentConfirmResult.class);
        if (null == replenishmentConfirmResult) {
            return "replenishment/empty";
        }
        modelMap.put("replenishmentConfirmResult", replenishmentConfirmResult);
        modelMap.put("deviceId", replenishmentConfirmResult.getDeviceId());
        modelMap.put("userId", replenishmentConfirmResult.getUserId());
        return "replenishment/replenishmentConfirm";
    }

    /**
     * ????????????????????? ????????????
     *
     * @return
     */
    @RequestMapping("/confirm")
    @ResponseBody
    public ResponseVo confirm(String deviceId, String userId) throws Exception {
        logger.info("?????????????????????");
        ResponseVo responseVo = ResponseVo.getSuccessResponse();
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(userId);
        String json = (String) iCached.get("IISREPLENCONFIRM_" + deviceInfo.getScode());
        Map<String, Object> map = JSON.parseObject(json, Map.class);
        Map<String, Object> visionMap = (Map<String, Object>) map.get("visionMap");
        List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) visionMap.get("addVoList");
        List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) visionMap.get("subVoList");
        Integer sourceClientType = (Integer) map.get("sourceClientType");
        ResponseVo<ReplenishmentResult> resVO = generateReplenishment(deviceInfo, memberInfo, subVoList, addVoList, sourceClientType);
        iCached.remove("CONFIRM_" + deviceInfo.getScode());
        iCached.remove("IISREPLENCONFIRM_" + deviceInfo.getScode());
        if (null != resVO) {
            logger.info("??????????????????????????????????????????{}", JSON.toJSONString(resVO));
            if (resVO.isSuccess()) {
            } else {
                responseVo.setSuccess(false);
            }
        }
        return responseVo;
    }

    /**
     * ???????????????
     *
     * @param deviceInfo       ????????????
     * @param memberInfo       ???????????????
     * @param subVoList        ????????????????????????
     * @param addVoList        ????????????????????????
     * @param sourceClientType ????????????
     */
    private ResponseVo<ReplenishmentResult> generateReplenishment(DeviceInfo deviceInfo, MemberInfo memberInfo, List<CommodityDiffVo> subVoList, List<CommodityDiffVo> addVoList, Integer sourceClientType) {
        try {
            ReplenishmentDto replenishmentDto = new ReplenishmentDto();
            replenishmentDto.setIsourceClientType(sourceClientType);
            replenishmentDto.setItype(10);
            replenishmentDto.setAddVoList(addVoList);
            replenishmentDto.setSubVoList(subVoList);
            replenishmentDto.setSdeviceId(deviceInfo.getId());
            replenishmentDto.setSdeviceCode(deviceInfo.getScode());
            replenishmentDto.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
            replenishmentDto.setSreaderSerialNumber(deviceInfo.getSreaderSerialNumber());
            replenishmentDto.setSmerchantId(deviceInfo.getSmerchantId());
            replenishmentDto.setSmerchantCode(deviceInfo.getSmerchantCode());
            replenishmentDto.setSmemberName(memberInfo.getSmemberName());
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(StockServicesDefine.GENERATE_REPLENISHMENT_SERVICE);// ????????????
            // ??????????????????????????????????????????????????????????????????????????????
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<ReplenishmentResult>>() {
            });
            invoke.setRequestObj(replenishmentDto); // post ??????
            ResponseVo<ReplenishmentResult> resVO = (ResponseVo<ReplenishmentResult>) invoke.invoke();
            return resVO;
        } catch (Exception e) {
            logger.error("????????????????????????{}", e);
        }
        return null;
    }


    /***
     * ?????? ????????????
     * @param deviceCode ????????????
     * @return
     */
    @RequestMapping("/markingDone")
    public @ResponseBody
    ResponseVo<String> markingDone(String deviceCode) {
        try {
            if (StringUtil.isBlank(deviceCode)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????");
            }
            //??????????????????
            DeviceInfo deviceInfo = deviceInfoService.selectByCode(deviceCode);
            if (null == deviceInfo) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("???????????????");
            }
            //??????????????????
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            if (!authVo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????????????????????????????");
            }
            replenishmentPlanService.updateByLastTime(deviceCode);
            ResponseVo<String> responseVo = ResponseVo.getSuccessResponse("????????????");
            responseVo.setMsg("????????????");
            return responseVo;
        } catch (Exception e) {
            logger.error("???????????????????????????{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????????????????????????????????????????");
    }

    /**
     * ????????????????????????????????????
     *
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
     * ??????????????????
     *
     * @param map
     * @return
     */
    @RequestMapping("/deviceStock")
    public String deviceStock(ModelMap map) throws Exception {
        //??????????????????
        //????????????????????? ????????????
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
