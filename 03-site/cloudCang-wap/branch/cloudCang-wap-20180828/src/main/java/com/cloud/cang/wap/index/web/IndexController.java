package com.cloud.cang.wap.index.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.*;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.AiInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.sb.AuthorizeAiFaceDto;
import com.cloud.cang.sb.DeviceServicesDefine;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.CodeEnum;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.CookieUtils;
import com.cloud.cang.wap.common.utils.IdGenerator;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.sb.service.AiInfoService;
import com.cloud.cang.wap.sb.service.DeviceInfoService;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import com.cloud.cang.weixin.api.TicketAPI;
import com.cloud.cang.weixin.api.TokenAPI;
import com.cloud.cang.weixin.bean.Ticket;
import com.cloud.cang.weixin.bean.Token;
import com.cloud.cang.weixin.util.JsUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private ICached iCached;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private AiInfoService aiInfoService;
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * ????????????
     *
     * @param deviceVo ??????????????????
     * @return
     */
    @RequestMapping("/main")
    public ModelAndView main(DeviceVo deviceVo, ModelMap map, HttpServletRequest request) throws Exception {
        logger.info("????????????:{}", deviceVo);
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//??????????????????
        if (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode())) {
            //????????????????????? ????????????
            deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
            if (null == deviceVo) {
                deviceVo = new DeviceVo();
            }
            if (StringUtil.isBlank(deviceVo.getMerchantCode())) {
                String merchantCode = WapUtils.getMerchantCookie(request);
                if (StringUtil.isNotBlank(merchantCode)) {
                    deviceVo.setMerchantCode(merchantCode);
                }
            }
            if (StringUtil.isBlank(deviceVo.getDeviceId())) {
                String deviceStr = WapUtils.getDeviceCodeCookie(request);
                if (StringUtil.isNotBlank(deviceStr)) {
                    deviceVo.setDeviceCode(deviceStr.split("#//#")[0]);
                    deviceVo.setDeviceId(deviceStr.split("#//#")[1]);
                }
            }
            if (StringUtil.isNotBlank(deviceVo.getOpenDoorKey())) {
                iCached.remove(deviceVo.getOpenDoorKey());//?????????????????????KEY
            }
        }
        if (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode())) {
            return WapUtils.errorDealWith("10000", "");
        }
        if (!authVo.getSmerchantCode().equals(deviceVo.getMerchantCode())) {
            return WapUtils.errorDealWith("10014", "");
        }
        if (StringUtil.isNotBlank(deviceVo.getDeviceId())) {
            Map<String, Object> connectParam = new HashMap<String, Object>();
            connectParam.put("deviceId", deviceVo.getDeviceId());
            connectParam.put("deviceCode", deviceVo.getDeviceCode());
            connectParam.put("userId", authVo.getId());
            connectParam.put("userCode", authVo.getCode());
            connectParam.put("userName", authVo.getUserName());
            connectParam.put("clientIp", RequestUtils.getIpAddr(request));
            if (StringUtil.isBlank(deviceVo.getOpenSource())) {
                if (WapUtils.isWXRequest(request)) {
                    deviceVo.setOpenSource("wechat");
                } else if (WapUtils.isAlipayRequest(request)) {
                    deviceVo.setOpenSource("alipay");
                }
            }
            if (deviceVo.getOpenSource().equals("wechat")) {
                connectParam.put("isourceClientType", 10);
            } else if (deviceVo.getOpenSource().equals("alipay")) {
                connectParam.put("isourceClientType", 20);
            }
            map.put("connectParam", JSON.toJSONString(connectParam));
        }
        //?????????????????????????????????
        MerchantClientConf clientConf = indexService.getMerchantClientConfByCode(deviceVo.getMerchantCode());
        //????????????
        if (clientConf != null && StringUtil.isNotBlank(clientConf.getStitle())) {
            map.put("indexTitle", clientConf.getStitle());
        } else {
            map.put("indexTitle", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_title"));
        }
        SessionUserUtils.getSession().setAttribute("session_device", deviceVo);//???????????????session???
        Integer replenishmentScan = (Integer) iCached.get("is_replenishment_scan_" + authVo.getId());
        //Integer replenishmentScan = (Integer) SessionUserUtils.getSession().getAttribute("is_replenishment_scan");
        String indexPath = "/index/page";
        if (replenishmentScan == null) {
            MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(authVo.getId());
            if (null != memberInfo.getIisReplenishment() && memberInfo.getIisReplenishment().intValue() == 1) {
                indexPath = "/replenishment/index";
            }
        }
        if (null != replenishmentScan && replenishmentScan.intValue() == 1) {
            indexPath = "/replenishment/index";
        }
        map.put("indexPath", indexPath);
        if (WapUtils.isWXRequest(request)) {
            map.put("wechat", 1);

            StringBuffer url = request.getRequestURL();
            if (request.getQueryString() != null) {
                url.append('?');
                url.append(request.getQueryString());
            }
            String jsUrl = url.toString();
            String jsonStr = getwxConfig(jsUrl, false);
            map.put("jsonStr", jsonStr);

        } else if (WapUtils.isAlipayRequest(request)) {
            map.put("alipay", 1);
        }

        return new ModelAndView("index/index_main");
    }

    /**
     * ????????????????????????
     *
     * @param url
     * @param debug
     * @return
     * @throws Exception
     */
    private String getwxConfig(String url, boolean debug) throws Exception {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        MerchantConf conf = indexService.getWechatMerchantConf(authVo.getSmerchantCode(), 1);
        Long timestamp = new Date().getTime();
        Long getWxAccessTime = iCached.get("getWxAccessTime_" + authVo.getSmerchantCode()) == null ? 0 : (Long) iCached.get("getWxAccessTime_" + authVo.getSmerchantCode());
        String jsapiTicket = (String) iCached.get("jsapiTicket_" + authVo.getSmerchantCode());
        // ????????????7200s
        if ((timestamp - getWxAccessTime) / 1000 > 7200 || timestamp - getWxAccessTime <= 0) {
            Token token = TokenAPI.token(conf.getSappId(), conf.getSappSecret());
            Ticket tiket = TicketAPI.ticketGetticket(token.getAccess_token());
            jsapiTicket = tiket.getTicket();
            iCached.put("getWxAccessTime_" + authVo.getSmerchantCode(), timestamp);
            iCached.put("accessToken_" + authVo.getSmerchantCode(), token.getAccess_token());
            iCached.put("jsapiTicket_" + authVo.getSmerchantCode(), jsapiTicket);
        }
        String jsonStr = JsUtil.generateConfigJson(jsapiTicket, debug, conf.getSappId(), url, null);
        return jsonStr;
    }

    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping("/page")
    public ModelAndView page(ModelMap map, HttpServletRequest request) throws Exception {

        DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
        if (null == deviceVo) {
            deviceVo = new DeviceVo();
        }
        logger.info("????????????:{}", deviceVo);
        if (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode())) {
            if (StringUtil.isBlank(deviceVo.getMerchantCode())) {
                String merchantCode = WapUtils.getMerchantCookie(request);
                if (StringUtil.isNotBlank(merchantCode)) {
                    deviceVo.setMerchantCode(merchantCode);
                }
            }
            if (StringUtil.isBlank(deviceVo.getDeviceId())) {
                String deviceStr = WapUtils.getDeviceCodeCookie(request);
                if (StringUtil.isNotBlank(deviceStr)) {
                    deviceVo.setDeviceCode(deviceStr.split("#//#")[0]);
                    deviceVo.setDeviceId(deviceStr.split("#//#")[1]);
                }
            }
            if (StringUtil.isNotBlank(deviceVo.getOpenDoorKey())) {
                iCached.remove(deviceVo.getOpenDoorKey());//?????????????????????KEY
            }
        }
        if (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode())) {
            return WapUtils.errorDealWith("10000", "");
        }

        if (StringUtil.isBlank(deviceVo.getOpenSource())) {
            if (WapUtils.isWXRequest(request)) {
                deviceVo.setOpenSource("wechat");
            } else if (WapUtils.isAlipayRequest(request)) {
                deviceVo.setOpenSource("alipay");
            }
        }
        if (WapUtils.isWXRequest(request)) {
            map.put("signPage", "/personal/wechatSign.html");
        } else if (WapUtils.isAlipayRequest(request)) {
            map.put("signPage", "/personal/alipaySign.html");
        }
        Map<String, Object> mapData = indexService.getMainData(deviceVo);
        map.put("index", mapData);

        return new ModelAndView("index/index");
    }


    /**
     * ????????????
     * @throws Exception
     */
    @RequestMapping("/openSuccess")
    public ModelAndView openSuccess(ModelMap map) throws Exception {
        logger.debug("??????????????????...");
        DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
        logger.info("?????????????????????????????????:{}", deviceVo);
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
        Map<String, Object> params = new HashMap<>();
        //???????????????????????????
        params = indexService.getMerchantClientConfByMap(params, deviceInfo.getSmerchantCode());
        //??????????????????
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        params.put("userId", authVo.getId());
        map.put("open", params);
        //??????????????????
        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ActivityServicesDefine.QUERY_SDEVICE_ACTIVITY_SDESCRIPTION);// ????????????
        // ??????????????????????????????????????????????????????????????????????????????
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<List<String>>>() {
        });
        invoke.setRequestObj(deviceInfo.getId()); // post ??????
        ResponseVo<List<String>> resVO = (ResponseVo<List<String>>) invoke.invoke();
        if (resVO.isSuccess()) {
            map.put("preferentialInfos", resVO.getData());
        }
        return new ModelAndView("index/openSuccess");
    }



    /**
     * ?????????????????? ???????????????KEY
     */
    @RequestMapping("/cancelScan")
    @ResponseBody
    public ResponseVo<String> cancelScan() {
        try {
            DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            logger.info("????????????:{}", deviceVo);
            iCached.remove(deviceVo.getDeviceId() + "_" + authVo.getId() + "_open_door");
            iCached.remove("is_replenishment_scan_" + authVo.getId());
        } catch (Exception e) {
            logger.error("?????????????????????{}", e);
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * ????????????????????????
     * @param memberType ???????????? 0 ???????????? 1 ????????????
     */
    @RequestMapping("/successScan")
    @ResponseBody
    public ResponseVo<String> successScan(Integer memberType) {
        try {
            DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            logger.info("????????????:{}", deviceVo);
            //???????????????????????? ????????????????????????
            iCached.put(deviceVo.getDeviceId() + "_" + authVo.getId() + "_open_door", deviceVo.getDeviceId());
            if (null != memberType && memberType.intValue() == 1) {
                iCached.put("is_replenishment_scan_" + authVo.getId(), 1);
            } else {
                iCached.put("is_replenishment_scan_" + authVo.getId(), 0);
            }
        } catch (Exception e) {
            logger.error("?????????????????????????????????{}", e);
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * ??????????????????????????????
     */
    @RequestMapping("/noRemind")
    @ResponseBody
    public ResponseVo<String> noRemind() {
        try {
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            iCached.put("is_remind_open_free_data_" + authVo.getId(), authVo.getId());
        } catch (Exception e) {
            logger.error("???????????????????????????????????????{}", e);
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * ????????????????????????
     */
    @RequestMapping("/getServiceTell")
    @ResponseBody
    public ResponseVo<String> getServiceTell() {
        ResponseVo<String>  responseVo = ResponseVo.getSuccessResponse();
        try {
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            MerchantClientConf conf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + authVo.getSmerchantCode());
            if (null != conf && StringUtil.isNotBlank(conf.getScontactMobile())) {
                responseVo.setData(conf.getScontactMobile());
            }
        } catch (Exception e) {
            logger.error("?????????????????????????????????{}", e);
        }
        return responseVo;
    }

    /**
     * ???????????? ?????? ??????????????????
     * ????????????
     *
     * @param deviceVo
     * @return
     */
    @RequestMapping("/visit")
    public ModelAndView visit(DeviceVo deviceVo, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("????????????????????????...");

        String rootPath = EvnUtils.getValue("wap.http.domain");
        String currentUrl = rootPath + request.getServletPath();
        try {
            logger.info("???????????????????????????{}", currentUrl);
            //????????????
            //???????????????????????? ???????????????Id ?????????????????????
            if (null == deviceVo || (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode()))) {//??????????????????
                return WapUtils.errorDealWith("10001", currentUrl);
            }
            logger.info("???????????????????????????{}", deviceVo);
            //1?????????????????????
            if (WapUtils.isWXRequest(request)) {
                deviceVo.setOpenSource("wechat");
            } else if (WapUtils.isAlipayRequest(request)) {
                deviceVo.setOpenSource("alipay");
            }
            //2?????????????????????
            if (StringUtil.isNotBlank(deviceVo.getDeviceId())) {
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
                if (null == deviceInfo) {//???????????????
                    return WapUtils.errorDealWith("10002", currentUrl);
                } else if (deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.NO_REGISTER.getCode()
                        || deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.SCRAPPED.getCode()) {
                    //???????????????
                    return WapUtils.errorDealWith("10003", currentUrl);
                } else if (deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.OFFLINE.getCode()) {
                    //????????????
                    return WapUtils.errorDealWith("10013", currentUrl);
                } else if (deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.MALFUNCTION.getCode()) {
                    //????????????
                    return WapUtils.errorDealWith("10004", currentUrl);
                } else if (deviceInfo.getIoperateStatus().intValue() == BizTypeDefinitionEnum.OperateStatus.DISABLED.getCode()) {
                    //???????????????
                    return WapUtils.errorDealWith("10007", currentUrl);
                }
                //???????????????????????????
                String temp = (String) request.getParameter("generateTime");
                if (StringUtil.isBlank(temp)) {
                    return WapUtils.errorDealWith("10017", currentUrl);
                }
                Long a = DateUtils.parseDateByFormat(temp, "yyyyMMddHHmmss").getTime();
                Long b = deviceInfo.getTqrGeneratetime().getTime();
                if (a.compareTo(b) != 0) {
                    return WapUtils.errorDealWith("10017", currentUrl);
                }
                deviceVo.setMerchantCode(deviceInfo.getSmerchantCode());
                deviceVo.setIsScan(1);
                deviceVo.setDeviceCode(deviceInfo.getScode());
                //3??????????????????????????????
                //????????????  ???????????????
                int validity = 30;//30???
                String validityStr = BizParaUtil.get(WapConstants.OPEN_DOOR_VALIDITY);
                try {
                    validity = Integer.parseInt(validityStr);
                } catch (Exception e) {
                    validity = 30;
                }
                String openDoorKey = IdGenerator.randomUUID(32);
                iCached.put(openDoorKey, deviceVo.getDeviceId(), validity);
                deviceVo.setOpenDoorKey(openDoorKey);
                //???????????????cookie
                WapUtils.setMerchantCookie(response, deviceVo.getMerchantCode());
                //??????????????????cookie
                WapUtils.setDeviceCookie(response, deviceInfo.getScode() + "#//#" + deviceInfo.getId());
                request.getSession().setAttribute("openDoorKey", openDoorKey);//???????????????key ???????????????

            } else if (StringUtil.isNotBlank(deviceVo.getMerchantCode())) {
                MerchantInfo merchantInfo = null;
                String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.MERCHANT_INFO_DETAILS + deviceVo.getMerchantCode());
                if (StringUtil.isNotBlank(json)) {
                    merchantInfo = JSONObject.parseObject(json, MerchantInfo.class);
                }
                //MerchantInfo merchantInfo = merchantInfoService.selectByCode(deviceVo.getMerchantCode());
                if (null == merchantInfo) {
                    return WapUtils.errorDealWith("10005", currentUrl);
                } else if (merchantInfo.getIstatus().intValue()
                        != BizTypeDefinitionEnum.IcompanyStatus.ALREADYSIGNED.getCode()) {
                    return WapUtils.errorDealWith("10006", currentUrl);
                }
                deviceVo.setIsScan(0);
                //???????????????cookie
                WapUtils.setMerchantCookie(response, deviceVo.getMerchantCode());
                CookieUtils.addCookie(response, WapConstants.DEVICE_COOKIE_NAME, "", 0);//????????????????????????
            } else {
                return WapUtils.errorDealWith("10008", currentUrl);
            }

            Map<String, String> map = new HashMap<String, String>();
            if (StringUtil.isNotBlank(deviceVo.getDeviceId())) {
                map.put("deviceId", deviceVo.getDeviceId());
            }
            if (StringUtil.isNotBlank(deviceVo.getDeviceCode())) {
                map.put("deviceCode", deviceVo.getDeviceCode());
            }
            if (StringUtil.isNotBlank(deviceVo.getMerchantCode())) {
                map.put("merchantCode", deviceVo.getMerchantCode());
            }
            if (null != deviceVo.getIsScan()) {
                map.put("isScan", String.valueOf(deviceVo.getIsScan()));
            }
            if (StringUtil.isNotBlank(deviceVo.getOpenDoorKey())) {
                map.put("openDoorKey", deviceVo.getOpenDoorKey());
            }
            if (StringUtil.isNotBlank(deviceVo.getOpenSource())) {
                map.put("openSource", deviceVo.getOpenSource());
            }
            return new ModelAndView("redirect:" + rootPath + "/index/main", map);
        } catch (Exception e) {
            logger.error("??????????????????:{}", e);
            return WapUtils.errorDealWith("10008", currentUrl);
        }
    }

    /**
     * ???????????????
     * ?????????????????????????????????
     *
     * @param deviceVo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/faceAiVisit")
    public ModelAndView faceAiVisit(DeviceVo deviceVo, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("??????????????????????????????????????????...");

        String rootPath = EvnUtils.getValue("wap.http.domain");
        String currentUrl = rootPath + request.getServletPath();
        String faceToken = "";
        String aiId = deviceVo.getAiId();
        try {
            logger.info("???????????????????????????????????????{}", currentUrl);
            //????????????
            //???????????????????????? ???????????????Id ?????????????????????
            if (null == deviceVo || (StringUtil.isBlank(aiId) && StringUtil.isBlank(deviceVo.getMerchantCode()))) {//??????????????????
                return faceErrorDealWith("10001", currentUrl);
            }
            logger.info("???????????????????????????????????????{}", deviceVo);
            //1?????????????????????
            if (WapUtils.isWXRequest(request)) {
                deviceVo.setOpenSource("wechat");
            } else if (WapUtils.isAlipayRequest(request)) {
                deviceVo.setOpenSource("alipay");
            }
            //2?????????????????????
            if (StringUtil.isNotBlank(aiId)) {
                AiInfo aiInfo = aiInfoService.selectByPrimaryKey(aiId);
                if (null == aiInfo) {//???????????????????????????
                    return faceErrorDealWith("20000", currentUrl);
                } else if (aiInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.NO_REGISTER.getCode()
                        || aiInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.SCRAPPED.getCode()) {
                    //???????????????????????????
                    return faceErrorDealWith("20001", currentUrl);
                } else if (aiInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.OFFLINE.getCode()) {
                    //????????????????????????
                    return faceErrorDealWith("20002", currentUrl);
                } else if (aiInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.MALFUNCTION.getCode()) {
                    //????????????????????????
                    return faceErrorDealWith("20003", currentUrl);
                } else if (aiInfo.getIoperType().intValue() == BizTypeDefinitionEnum.OperateStatus.DISABLED.getCode()) {
                    //???????????????????????????
                    return faceErrorDealWith("20004", currentUrl);
                }


                // ??????token
                faceToken = request.getParameter("token");
                // ??????????????????????????????
                String qrCodeIsScan = (String) iCached.get(NettyConst.FACE_REGISTER_QRCODE_ISSCAN + faceToken);
                if (StringUtils.isBlank(qrCodeIsScan) || (StringUtils.isNotBlank(qrCodeIsScan) && qrCodeIsScan.equals("yes"))) {
                    return faceErrorDealWith("20006", currentUrl);
                }

                // ?????????????????????????????????
                String qrCodeUrl = (String) iCached.get(NettyConst.FACE_REGISTER_QR_CODE + faceToken);
                if (StringUtils.isBlank(qrCodeUrl)) {
                    // ?????????????????????????????????
                    return faceErrorDealWith("20005", currentUrl);
                }

                // ??????token????????????
                if (StringUtils.isNotBlank(faceToken)) {
                    String cacheToken = (String) iCached.get(NettyConst.FACE_REGISTER_TOKEN + faceToken);
                    if (StringUtils.isNotBlank(cacheToken) && cacheToken.equals("valid")) { // ??????
                        iCached.put(NettyConst.FACE_REGISTER_QRCODE_ISSCAN + faceToken, "yes", 3600); // ???????????????????????????
                        //????????????????????????????????????
                        scanSuccessService(deviceVo.getAiId(), faceToken);
                    } else {  // ??????
                        return faceErrorDealWith("20007", currentUrl);
                    }
                }

                // ???????????????????????????,??????????????????
                AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//??????????????????
                if (null != authVo && StringUtils.isNotBlank(authVo.getId())) {
                    MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(authVo.getId());
                    if (null != memberInfo && null != memberInfo.getIisRegFace() && 1 == memberInfo.getIisRegFace()) {
                        return faceErrorDealWith("20008", currentUrl);
                    }
                }

                deviceVo.setMerchantCode(aiInfo.getSmerchantCode());
                deviceVo.setIsScan(1);
                deviceVo.setAiCode(aiInfo.getSaiCode());
                Map<String, String> map = new HashMap<String, String>();
                if (StringUtil.isNotBlank(deviceVo.getMerchantCode())) {
                    map.put("merchantCode", deviceVo.getMerchantCode());
                }
                if (null != deviceVo.getIsScan()) {
                    map.put("isScan", String.valueOf(deviceVo.getIsScan()));
                }
                if (StringUtil.isNotBlank(deviceVo.getAiId())) {
                    map.put("aiId", deviceVo.getAiId());
                }
                if (StringUtil.isNotBlank(deviceVo.getDeviceCode())) {
                    map.put("deviceCode", deviceVo.getDeviceCode());
                } else {
                    DeviceInfo deviceInfoVo = new DeviceInfo();
                    deviceInfoVo.setSaiId(aiId);
                    deviceInfoVo.setIdelFlag(0);
                    deviceInfoVo.setIsupportAi(1);
                    deviceInfoVo.setItype(30);
                    List<DeviceInfo> deviceInfoList = deviceInfoService.selectByEntityWhere(deviceInfoVo);
                    if (CollectionUtils.isNotEmpty(deviceInfoList)) {
                        DeviceInfo deviceInfoDomain = deviceInfoList.get(0);
                        map.put("deviceCode", deviceInfoDomain.getScode());
                        deviceVo.setDeviceCode(deviceInfoDomain.getScode());
                        deviceVo.setDeviceId(deviceInfoDomain.getId());
                    }
                }
                if (StringUtils.isNotBlank(deviceVo.getOpenSource())) {
                    map.put("openSource", deviceVo.getOpenSource());
                }

                map.put("token", faceToken);
                return new ModelAndView("redirect:" + rootPath + "/index/authorizeFaceRegister", map);
            } else {
                return faceErrorDealWith("10008", currentUrl);
            }
        } catch (Exception e) {
            logger.error("??????????????????????????????:{}", e);
            return faceErrorDealWith("10008", currentUrl);
        }
    }


    /**
     * ??????????????????
     *
     * @param deviceVo
     * @param map
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/authorizeFaceRegister")
    public ModelAndView authorizeFaceRegister(DeviceVo deviceVo, ModelMap map, HttpServletRequest request) {
        logger.info("????????????:{}", deviceVo);
        ResponseVo<String> responseVo = new ResponseVo<>(false, 3333, "??????????????????!");
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//??????????????????
        String faceToken = request.getParameter("token");
        if (StringUtil.isNotBlank(deviceVo.getAiId())) {
            map.put("aiId", deviceVo.getAiId());
            map.put("deviceCode", deviceVo.getDeviceCode());
            map.put("userId", authVo.getId());
            map.put("userCode", authVo.getCode());
            map.put("userName", authVo.getUserName());
            map.put("clientIp", RequestUtils.getIpAddr(request));
            map.put("token", faceToken);
            map.put("trusteeAccountCode", authVo.getTrusteeAccountCode());// ???????????????
            map.put("time", DateUtils.dateParseString(new Date()));// ??????
            map.put("openSource",deviceVo.getOpenSource());// ??????
        }
        return new ModelAndView("index/authFaceRegister");
    }

    /**
     * ????????????????????????
     *
     * @param deviceVo
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/authorizeRegisterSuccess")
    public ModelAndView authorizeRegisterSuccess(DeviceVo deviceVo, ModelMap map, HttpServletRequest request) throws Exception {
        logger.info("????????????:{}", deviceVo);
        ResponseVo responseVo = null;
        String faceToken = request.getParameter("token");
        String userId = request.getParameter("userId");
        String openSource = request.getParameter("openSource");
        AuthorizeAiFaceDto authorizeAiFaceDto = new AuthorizeAiFaceDto();
        String rootPath = EvnUtils.getValue("wap.http.domain");
        String currentUrl = rootPath + request.getServletPath();

        // ??????token?????????????????????????????????
        // token?????????(??????="valid"?????????="cancel"?????????="invalid"||""
        String tokenValue = (String) iCached.get(NettyConst.FACE_REGISTER_TOKEN + faceToken);
        if (StringUtils.isNotBlank(tokenValue) && "cancel".equals(tokenValue)) {
            return faceErrorDealWith("20010", currentUrl);  // ???????????????????????????????????????
        } else if (StringUtils.isNotBlank(tokenValue) && "invalid".equals(tokenValue)) {
            return faceErrorDealWith("20011", currentUrl);  //  ??????
        }

        // ?????????????????????????????????
        String qrCodeUrl = (String) iCached.get(NettyConst.FACE_REGISTER_QR_CODE + faceToken);
        if (StringUtils.isBlank(qrCodeUrl)) {
            // ?????????????????????????????????
            return faceErrorDealWith("20012", currentUrl);
        }


        // //??????????????????????????????????????????netty????????????????????????????????????????????????
        logger.debug("?????????????????????????????????" + DeviceServicesDefine.SCAN_CODE_AUTHORIZE_AI_FACE);
        authorizeAiFaceDto.setAiId(deviceVo.getAiId());
        authorizeAiFaceDto.setAuthorizeType(3);
        if (StringUtils.isNotBlank(faceToken)) {
            authorizeAiFaceDto.setToken(faceToken);
        }
        if (StringUtils.isNotBlank(openSource)) {
            if ("wechat".equals(openSource)) {
                authorizeAiFaceDto.setPayType(10);
            } else if ("alipay".equals(openSource)) {
                authorizeAiFaceDto.setPayType(20);
            }
        }
        if (StringUtils.isNotBlank(userId)) {
            authorizeAiFaceDto.setUserId(userId);
        }
        try {
            // ??????Rest???????????????
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.SCAN_CODE_AUTHORIZE_AI_FACE);
            invoke.setRequestObj(authorizeAiFaceDto);
            // ??????????????????????????????????????????????????????????????????????????????
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.debug("??????????????????????????????????????????????????????{} ??????????????????{}");
                if (WapUtils.isWXRequest(request)) {
                    map.put("wechat", 1);
                    StringBuffer url = request.getRequestURL();
                    if (request.getQueryString() != null) {
                        url.append('?');
                        url.append(request.getQueryString());
                    }
                    String jsUrl = url.toString();
                    String jsonStr = null;
                    try {
                        jsonStr = getwxConfig(jsUrl, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    map.put("jsonStr", jsonStr);

                } else if (WapUtils.isAlipayRequest(request)) {
                    map.put("alipay", 1);
                }
                return new ModelAndView("index/authFaceRegisterSuccess", map);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.error("??????????????????:{}");

        return faceErrorDealWith("20009", currentUrl);
    }

    /**
     * ????????????????????????
     *
     * @param deviceVo
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/authorizeRegisterFail")
    public ModelAndView authorizeRegisterFail(DeviceVo deviceVo, ModelMap map, HttpServletRequest request) throws Exception {
        logger.info("????????????:{}", deviceVo);
        ResponseVo responseVo = null;
        String faceToken = request.getParameter("token");
        AuthorizeAiFaceDto authorizeAiFaceDto = new AuthorizeAiFaceDto();
        // //??????????????????????????????????????????netty????????????????????????????????????????????????
        logger.debug("?????????????????????????????????" + DeviceServicesDefine.SCAN_CODE_AUTHORIZE_AI_FACE);
        authorizeAiFaceDto.setAiId(deviceVo.getAiId());
        authorizeAiFaceDto.setAuthorizeType(4);
        authorizeAiFaceDto.setToken(faceToken);
        try {
            // ??????Rest???????????????
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.SCAN_CODE_AUTHORIZE_AI_FACE);
            invoke.setRequestObj(authorizeAiFaceDto);
            // ??????????????????????????????????????????????????????????????????????????????
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.debug("??????????????????????????????????????????????????????{} ??????????????????{}");
                if (WapUtils.isWXRequest(request)) {
                    map.put("wechat", 1);
                    StringBuffer url = request.getRequestURL();
                    if (request.getQueryString() != null) {
                        url.append('?');
                        url.append(request.getQueryString());
                    }
                    String jsUrl = url.toString();
                    String jsonStr = null;
                    try {
                        jsonStr = getwxConfig(jsUrl, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    map.put("jsonStr", jsonStr);

                } else if (WapUtils.isAlipayRequest(request)) {
                    map.put("alipay", 1);
                }
                return new ModelAndView("index/authFaceRegisterFail", map);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        String rootPath = EvnUtils.getValue("wap.http.domain");
        String currentUrl = rootPath + request.getServletPath();
        return faceErrorDealWith("20009", currentUrl);
    }

    /**
     * ????????????????????????
     *
     * @param aiId
     * @param faceToken
     * @return
     */
    private ResponseVo<String> scanSuccessService(String aiId, String faceToken) {
        AuthorizeAiFaceDto authorizeAiFaceDto = new AuthorizeAiFaceDto();
        authorizeAiFaceDto.setAiId(aiId);
        authorizeAiFaceDto.setAuthorizeType(1);
        authorizeAiFaceDto.setToken(faceToken);
        ResponseVo responseVo = null;
        try {
            // ??????Rest???????????????
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.SCAN_CODE_AUTHORIZE_AI_FACE);
            invoke.setRequestObj(authorizeAiFaceDto);
            // ??????????????????????????????????????????????????????????????????????????????
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return responseVo;
    }

    /**
     * ????????????
     *
     * @param errorCode  ?????????
     * @param currentUrl ??????
     * @throws Exception
     */
    public ModelAndView faceErrorDealWith(String errorCode, String currentUrl) {
        String rootPath = EvnUtils.getValue("wap.http.domain");
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("errorCode", errorCode);//?????????
        map.put("currentUrl", currentUrl);//????????????
        return new ModelAndView("redirect:" + rootPath + "/index/error", map);
    }


    /**
     * @param errorCode
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/error")
    public String error(Integer errorCode, ModelMap map, HttpServletRequest request) {
        if (null != errorCode) {
            map.put("errorCode", errorCode);
            map.put("errorMsg", CodeEnum.getNameByCode(errorCode));
        }

        if (WapUtils.isWXRequest(request)) {
            map.put("wechat", 1);
            StringBuffer url = request.getRequestURL();
            if (request.getQueryString() != null) {
                url.append('?');
                url.append(request.getQueryString());
            }
            String jsUrl = url.toString();
            String jsonStr = null;
            try {
                jsonStr = getwxConfig(jsUrl, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("jsonStr", jsonStr);

        } else if (WapUtils.isAlipayRequest(request)) {
            map.put("alipay", 1);
        }
        return "index/authError";
    }

}
