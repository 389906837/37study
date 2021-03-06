package com.cloud.cang.wap.index.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.AlipayConfigure;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.common.RequestUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.hy.TrashAuthorise;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.model.wz.Announcement;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import com.cloud.cang.wap.hy.service.TrashAuthoriseService;
import com.cloud.cang.wap.index.dao.IndexDao;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.om.service.OrderRecordService;
import com.cloud.cang.wap.sb.service.DeviceInfoService;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import com.cloud.cang.wap.sys.service.OperatorService;
import com.cloud.cang.weixin.api.SnsAPI;
import com.cloud.cang.weixin.api.TicketAPI;
import com.cloud.cang.weixin.api.TokenAPI;
import com.cloud.cang.weixin.bean.Ticket;
import com.cloud.cang.weixin.bean.Token;
import com.cloud.cang.weixin.util.JsUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * ?????? Copyright (C) 2016 37cang All rights reserved Author: zhao Date: 2016-3-18
 * Description:IndexServiceImpl.java
 */
@Service
public class IndexServiceImpl extends GenericServiceImpl<Object, String> implements IndexService {

    @Autowired
    private IndexDao indexDao;
    @Autowired
    private ICached iCached;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private TrashAuthoriseService trashAuthoriseService;
    @Autowired
    private DeviceInfoService deviceInfoService;

    @Override
    public GenericDao<Object, String> getDao() {
        return indexDao;
    }

    private static Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    /**
     * ????????????????????????
     *
     * @param request
     * @throws Exception
     */
    @Override
    public ModelAndView getIndexUrl(HttpServletRequest request) throws Exception {
        String rootPath = EvnUtils.getValue("wap.http.domain");
        Map<String, String> map = new HashMap<String, String>();
        //1?????????????????????
        if (WapUtils.isWXRequest(request)) {
            map.put("openSource", "wechat");
        } else if (WapUtils.isAlipayRequest(request)) {
            map.put("openSource", "alipay");
        }
        //??????cookie????????????
        String merchantCode = WapUtils.getMerchantCookie(request);
        if (StringUtil.isNotBlank(merchantCode)) {
            map.put("merchantCode", merchantCode);
        }
        //??????cookie????????????
        String deviceStr = WapUtils.getDeviceCodeCookie(request);
        if (StringUtil.isNotBlank(deviceStr)) {
            map.put("deviceId", deviceStr.split("#//#")[1]);
            map.put("deviceCode", deviceStr.split("#//#")[0]);
            map.put("isScan", "1");
            String openDoorKey = (String) request.getSession().getAttribute("openDoorKey");
            if (StringUtil.isNotBlank(openDoorKey)) {
                map.put("openDoorKey", openDoorKey);
            }
        } else {
            map.put("isScan", "0");
        }
        return new ModelAndView("redirect:" + rootPath + "/index/main", map);
    }

    /**
     * ????????????????????????
     *
     * @param httpRequest
     * @return ????????????
     * @throws Exception
     */
    public String getWxUrlForXW(HttpServletRequest httpRequest) throws Exception {
        //??????????????????
        String merchantCode = WapUtils.getMerchantCookie(httpRequest);
        //??????????????????????????????
        MerchantConf conf = this.getWechatMerchantConf(merchantCode, 1);
        if (null == conf) {//?????????????????????
            return null;
        }
        String appId = conf.getSappId();
        String back_uri = httpRequest.getParameter("backUrl");
        if (StringUtil.isBlank(back_uri)) {
            back_uri = WapUtils.getDefaultBackUrl(WebUtils.toHttp(httpRequest));
        }
        String callBackUrl = conf.getScallBackUrl() + "?back_uri=" + back_uri.replaceAll("&", "-");
        callBackUrl = callBackUrl.replaceAll("&code.*&", "&");
        String url = SnsAPI.connectOauth2Authorize(appId, callBackUrl, true, "");
        return url;
    }


    /***
     * ???????????????????????????
     * @param httpRequest ??????
     * @return ????????????
     * @throws Exception
     */
    public String getAlipayUrlForXW(HttpServletRequest httpRequest) throws Exception {
        //??????????????????
        String merchantCode = WapUtils.getMerchantCookie(httpRequest);
        //?????????????????????????????????
        MerchantConf conf = this.getAlipayMerchantConf(merchantCode, 1);
        if (null == conf) {//?????????????????????
            return null;
        }
        String appId = conf.getSappId();
        String back_uri = httpRequest.getParameter("backUrl");
        if (StringUtil.isBlank(back_uri)) {
            back_uri = WapUtils.getDefaultBackUrl(WebUtils.toHttp(httpRequest));
        }
        String callBackUrl = conf.getScallBackUrl() + "?back_uri=" + back_uri.replaceAll("&", "-");
        callBackUrl = callBackUrl.replaceAll("&code.*&", "&");
        String url = SnsAPI.connectOauth2Alipay(appId, callBackUrl, true, null);
        return url;
    }

    /**
     * ??????AlipayClient ????????? ??????????????????sdk client
     *
     * @param merchantCode ????????????
     * @throws Exception
     */
    public AlipayClient getAlipayClient(String merchantCode) throws Exception {

        MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
        if (null == clientConf) {//?????????????????????
            return null;
        }
        if (clientConf.getIisConfAlipayShh().intValue() == 0) {//??????????????????????????????
            merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
        }
        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_ALIPAY_CONFIG + merchantCode);
        if (StringUtil.isBlank(json)) {//?????????????????????
            return null;
        }
        MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
        if (null == conf) {//?????????????????????
            return null;
        }
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigure.UNIFIED_ORDER_API, conf.getSappId(),
                conf.getSprivateKey(), AlipayConfigure.format, AlipayConfigure.charset, conf.getSpublicKey(), AlipayConfigure.sign_type);
        return alipayClient;
    }

    /**
     * ?????????????????????????????????
     *
     * @param merchantCode ????????????
     * @param type         ?????? 1 ????????? 2 ???????????????
     * @return ???????????????????????????
     * @throws Exception
     */
    @Override
    public MerchantConf getAlipayMerchantConf(String merchantCode, Integer type) throws Exception {
        MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
        if (clientConf == null) {
            return null;
        }
        int typeVal = -1;
        if (type.intValue() == 1) {
            typeVal = clientConf.getIisConfAlipayShh().intValue();//??????????????????????????????
        } else if (type.intValue() == 2) {
            typeVal = clientConf.getIisConfAlipay().intValue();//???????????????????????????
        }
        if (typeVal == 0) {//????????????????????????????????????
            merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
        }
        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_ALIPAY_CONFIG + merchantCode);
        if (StringUtil.isBlank(json)) {
            return null;
        }
        MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
        return conf;
    }

    /**
     * ????????????????????????????????????
     *
     * @param merchantCode ????????????
     * @param type         ?????? 1 ????????? 2 ????????????
     * @return ??????????????????????????????
     * @throws Exception
     */
    @Override
    public MerchantConf getWechatMerchantConf(String merchantCode, Integer type) throws Exception {
        MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
        if (clientConf == null) {
            return null;
        }
        int typeVal = -1;
        if (type.intValue() == 1) {
            typeVal = clientConf.getIisConfWechatGzh().intValue();//???????????????????????????
        } else if (type.intValue() == 2) {
            typeVal = clientConf.getIisConfWechat().intValue();//????????????????????????
        }
        if (typeVal == 0) {
            merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
        }
        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + merchantCode);
        if (StringUtil.isBlank(json)) {
            return null;
        }
        MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
        return conf;
    }

    /**
     * ??????request ????????????
     *
     * @param request http ??????
     * @return
     */
    @Override
    public Map<String, String> getAlipayRequestParams(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        // ??????????????????
        Map paramsMap = request.getParameterMap();
        for (Iterator iter = paramsMap.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) paramsMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            map.put(name, valueStr);
        }
        return map;
    }

    /**
     * ???????????????????????????????????????
     *
     * @return ???????????????????????????
     * @throws Exception
     */
    @Override
    public MerchantConf getDefaultAlipayMerchantConf() throws Exception {
        String merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_ALIPAY_CONFIG + merchantCode);
        if (StringUtil.isBlank(json)) {
            return null;
        }
        return JSONObject.parseObject(json, MerchantConf.class);
    }

    /**
     * ?????????????????????????????????
     *
     * @param merchantCode ????????????
     * @throws Exception
     */
    @Override
    public MerchantClientConf getMerchantClientConfByCode(String merchantCode) throws Exception {
        MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
        return clientConf;
    }

    /**
     * ??????????????????
     *
     * @param types    ????????????
     * @param deviceVo ????????????
     * @throws Exception
     */
    @Override
    public Map<String, Object> assemblyOpenParam(DeviceVo deviceVo, Integer types) throws Exception {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//??????????????????

        Map<String, Object> openParam = new HashMap<String, Object>();
        openParam.put("deviceId", deviceVo.getDeviceId());
        openParam.put("userId", authVo.getId());
        openParam.put("types", types);
        openParam.put("openDoorKey", deviceVo.getOpenDoorKey());

        return openParam;
    }

    @Override
    public Map<String, Object> getMerchantClientConfByMap(Map<String, Object> map, String merchantCode) throws Exception {
        //?????????????????????????????????
        MerchantClientConf clientConf = this.getMerchantClientConfByCode(merchantCode);

        if (StringUtil.isNotBlank(clientConf.getSloginLogo())) {
            map.put("slogo", clientConf.getSloginLogo());
        }

        if (null != clientConf.getIsupportPayWay()) {
            map.put("isupportPayWay", clientConf.getIsupportPayWay());
        } else {
            map.put("isupportPayWay", 20);
        }
        //????????????
        if (clientConf != null && StringUtil.isNotBlank(clientConf.getStitle())) {
            map.put("indexTitle", clientConf.getStitle());
        } else {
            map.put("indexTitle", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_title").getSvalue());
        }
        //???????????????
        if (clientConf != null && StringUtil.isNotBlank(clientConf.getSindexHint())) {
            map.put("indexHint", clientConf.getSindexHint());
        } else {
            map.put("indexHint", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_index_hint").getSvalue());
        }
        //????????????
        if (clientConf != null && StringUtil.isNotBlank(clientConf.getSindexBgurl())) {
            map.put("indexBgUrl", clientConf.getSindexBgurl());
        } else {
            map.put("indexBgUrl", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_index_bg_url").getSvalue());
        }
        //???????????? ????????????
        if (clientConf != null && StringUtil.isNotBlank(clientConf.getSsuccessBgurl())) {
            map.put("openBgUrl", clientConf.getSsuccessBgurl());
        } else {
            map.put("openBgUrl", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_open_success_bg_url").getSvalue());
        }
        //???????????? ?????????
        if (clientConf != null && StringUtil.isNotBlank(clientConf.getSsuccessHint())) {
            map.put("openHint", clientConf.getSsuccessHint());
        } else {
            map.put("openHint", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_open_success_hint").getSvalue());
        }
        return map;
    }

    @Override
    public String getAccessToken(String merchantCode) throws Exception {
        MerchantConf conf = this.getWechatMerchantConf(merchantCode, 1);
        Long timestamp = new Date().getTime();
        Long getWxAccessTime = iCached.get("getWxAccessTime_" + merchantCode) == null ? 0 : (Long) iCached.get("getWxAccessTime_" + merchantCode);
        String accessToken = (String) iCached.get("accessToken_" + merchantCode);
        // ????????????7200s
        if ((timestamp - getWxAccessTime) / 1000 > 7200 || timestamp - getWxAccessTime <= 0 || StringUtil.isBlank(accessToken)) {
            Token token = TokenAPI.token(conf.getSappId(), conf.getSappSecret());
            Ticket tiket = TicketAPI.ticketGetticket(token.getAccess_token());
            iCached.put("accessToken_" + merchantCode, token.getAccess_token());
            iCached.put("getWxAccessTime_" + merchantCode, timestamp);
            iCached.put("jsapiTicket_" + merchantCode, tiket.getTicket());
            return token.getAccess_token();
        }
        return accessToken;
    }

    /**
     * ????????????????????????
     *
     * @param map     ??????????????????
     * @param request ??????
     * @return
     */
    @Override
    public ModelMap getPayConfig(ModelMap map, HttpServletRequest request) {
        if (WapUtils.isWXRequest(request)) {
            map.put("wechat", 1);
            StringBuffer url = request.getRequestURL();
            if (request.getQueryString() != null) {
                url.append('?');
                url.append(request.getQueryString());
            }
            String jsUrl = url.toString();
            String jsonStr = null;
            String jsonStrPoint = null;
            try {
                jsonStr = this.getwxConfig(jsUrl, false);
                jsonStrPoint = this.getwxPointConfig(jsUrl, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("jsonStr", jsonStr);
            map.put("jsonStrPoint", jsonStrPoint);

        } else if (WapUtils.isAlipayRequest(request)) {
            map.put("alipay", 1);
        }
        return map;
    }

    /***
     * ??????????????????
     * @param deviceVo ????????????
     * @param authVo ????????????
     * @param types ???????????? 10 ???????????? 20 ????????????
     * @param request ????????????
     * @param map ??????????????????
     * @return
     */
    @Override
    public ModelMap getIndexData(DeviceVo deviceVo, AuthorizationVO authVo, HttpServletRequest request, Integer types, ModelMap map) throws Exception {
        SessionUserUtils.getSession().setAttribute("session_device", deviceVo);//???????????????session???
        //??????soketIO????????????
        Map<String, Object> connectParam = new HashMap<String, Object>();
        connectParam.put("deviceId", deviceVo.getDeviceId());
        connectParam.put("deviceCode", deviceVo.getDeviceCode());
        connectParam.put("userId", authVo.getId());
        connectParam.put("userCode", authVo.getCode());
        connectParam.put("userName", authVo.getUserName());
        connectParam.put("clientIp", RequestUtils.getIpAddr(request));
        if (WapUtils.isWXRequest(request)) {
            deviceVo.setOpenSource("wechat");
            connectParam.put("isourceClientType", 10);
            String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + authVo.getSmerchantCode());
            if (StringUtil.isBlank(json)) {
                return null;
            }
            MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
            if (conf.getIwechatWithholdType() == 10) {
                map.put("signPage", "/personal/wechatSign.html");
            } else if (conf.getIwechatWithholdType() == 20) {
                map.put("signPage", "/personal/wechatOpen.html");
            }
        } else if (WapUtils.isAlipayRequest(request)) {
            deviceVo.setOpenSource("alipay");
            connectParam.put("isourceClientType", 20);
            map.put("signPage", "/personal/alipaySign.html");
        }
        map.put("connectParam", JSON.toJSONString(connectParam));
        //????????????
        map = this.getPayConfig(map, request);

        map.put("deviceVo", deviceVo);
        map.put("userId", authVo.getId());
        map.put("userCode", authVo.getCode());
        map.put("userName", authVo.getUserName());
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //???????????????????????????
        dataMap = this.getMerchantClientConfByMap(dataMap, deviceVo.getMerchantCode());
        if (types == 10) {
            //???????????????
            if (authVo.isReplenishment()) {
                Operator operator = operatorService.selectByMemberName(authVo.getUserName(), authVo.getSmerchantCode());
                if (null != operator.getIdevType() && 0 == operator.getIdevType()) {
                    map.put("isReplenishment", 1);
                } else if (null != operator.getIdevType() && 2 == operator.getIdevType() && operator.getSgroupDecList().contains(deviceVo.getDeviceId())) {
                    map.put("isReplenishment", 1);
                } else {
                    map.put("isReplenishment", 0);
                }
            } else {
                map.put("isReplenishment", 0);
            }

            //????????????????????????????????? ?????????
            List<OrderRecord> orderRecords = orderRecordService.selectExceptionOrder(authVo.getId());
            if (null != orderRecords && orderRecords.size() > 0) {
                map.put("isExceptionOrder", 1);//???????????????
            }
            Integer isupportPayWay = (Integer) dataMap.get("isupportPayWay");
            if (isupportPayWay.intValue() == 10 || isupportPayWay.intValue() == 20) {
                MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(authVo.getId());
                //??????????????????
                if (deviceVo.getOpenSource().equals("wechat")) {//??????
                    MerchantConf merchantConf = getWechatMerchantConf(memberInfo.getSmerchantCode(), 2);
                    map.put("iwechatWithholdType", merchantConf.getIwechatWithholdType());/* ?????????????????? 10:????????????  20:??????????????? */
                    if (merchantConf.getIwechatWithholdType() == null || merchantConf.getIwechatWithholdType() == 10) {
                        map.put("isOpenFreeData", memberInfo.getIwechatOpen());
                    } else if (merchantConf.getIwechatWithholdType() != null && merchantConf.getIwechatWithholdType() == 20) {
                        map.put("isOpenFreeData", memberInfo.getIwechatPointOpen());
                    }
                } else if (deviceVo.getOpenSource().equals("alipay")) {//?????????
                    map.put("isOpenFreeData", memberInfo.getIaipayOpen());
                }
                if (isupportPayWay.intValue() == 20) {
                    map.put("isRemindOpenAndClose", 1);//????????????
                } else {
                    //????????????????????????
                    String isRemindOpen = (String) iCached.get("is_remind_open_free_data_" + authVo.getId());
                    if (StringUtil.isBlank(isRemindOpen)) {
                        map.put("isRemindOpen", 1);
                    }
                    //????????????????????????
                    String isRemindAuth = (String) iCached.get("is_remind_auth_trash_data_" + authVo.getId());
                    if (StringUtil.isBlank(isRemindAuth)) {
                        map.put("isRemindAuth", 1);
                    }
                }
            }
            //?????? ????????????
            String json = (String) iCached.hget(RedisConst.WZ_REGIO_ANNOUNCEMENT + deviceVo.getMerchantCode(), RedisConst.WZ_REGIO_DETAIL_ + "WZRE0053_" + deviceVo.getMerchantCode());
            if (StringUtil.isNotBlank(json)) {
                List<Announcement> temp = JSONObject.parseArray(json, Announcement.class);
                if (null != temp && temp.size() > 0) {
                    Date date = new Date();
                    List<Announcement> announcementList = new ArrayList<Announcement>();
                    for (Announcement announcement : temp) {
                        if (date.before(announcement.getTvalidDate()) && date.after(announcement.getTpublishDate())) {
                            announcementList.add(announcement);
                        }
                    }
                    map.put("announcementList", announcementList);
                }
            }
        }
        map.put("indexData", dataMap);


        //????????????
        String json = (String) iCached.hget(RedisConst.WZ_REGIO_ADVERTIS + deviceVo.getMerchantCode(), RedisConst.WZ_REGIO_DETAIL_ + "WZRE0056_" + deviceVo.getMerchantCode());
        if (StringUtil.isNotBlank(json)) {
            List<Advertis> temp = JSONObject.parseArray(json, Advertis.class);
            if (null != temp && temp.size() > 0) {
                Date date = new Date();
                List<Advertis> advertisList = new ArrayList<Advertis>();
                for (Advertis advertis : temp) {
                    if (date.before(advertis.getTendDate()) && date.after(advertis.getTstartDate())) {
                        advertisList.add(advertis);
                    }
                }
                map.put("advertisList", advertisList);
            }
        }
        //???????????????????????????
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
        if (null != deviceInfo.getIisBindTrash() && deviceInfo.getIisBindTrash() == 1) {
            Map<String, Object> map1 = new HashMap();
            map1.put("smemberId", authVo.getId());
            map1.put("strashCode", deviceInfo.getStrashCode());
            map1.put("strashBrand", deviceInfo.getStrashBrand());
            map1.put("istatus", 10);
            TrashAuthorise trashAuthorise = trashAuthoriseService.selectByUserIdAndTrash(map1);
            if (trashAuthorise == null) {
                map.put("authoriseTrash", 1);
                map.put("authoriseTrashPage", "/personal/authTrash.html");
                map.put("trashBrand", deviceInfo.getStrashBrand());
            }
        }
        //????????????
        Map<String, Object> openParam = this.assemblyOpenParam(deviceVo, types);
        map.put("openParam", JSON.toJSONString(openParam));
        return map;
    }

    /**
     * ??????????????????
     *
     * @param authVo   ????????????
     * @param deviceVo ????????????
     * @param request  ????????????
     * @return
     */
    @Override
    public DeviceVo indexExceptionDealwith(AuthorizationVO authVo, DeviceVo deviceVo, HttpServletRequest request) throws Exception {
        if (null == deviceVo || (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode()))) {
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
        deviceVo.setErrorCode("");
        if (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode())) {
            deviceVo.setErrorCode("10000");
        }
        if (!authVo.getSmerchantCode().equals(deviceVo.getMerchantCode())) {
            deviceVo.setErrorCode("10014");
        }
        logger.info("????????????:{}", deviceVo);
        return deviceVo;
    }

    /**
     * ????????????????????????
     *
     * @param url
     * @param debug
     * @throws Exception
     */
    private String getwxConfig(String url, boolean debug) throws Exception {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        MerchantConf conf = this.getWechatMerchantConf(authVo.getSmerchantCode(), 1);
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

        String jsonStr = JsUtil.generateConfigJson(jsapiTicket, debug, conf.getSappId(), url, "checkJsApi", "onMenuShareTimeline", "onMenuShareAppMessage", "onMenuShareQQ", "onMenuShareWeibo", "onMenuShareQZone", "chooseImage", "previewImage", "uploadImage", "downloadImage", "startRecord", "stopRecord", "onVoiceRecordEnd", "playVoice", "pauseVoice", "stopVoice", "onVoicePlayEnd", "uploadVoice", "downloadVoice", "translateVoice", "getNetworkType", "openLocation", "getLocation", "startMonitoringBeacons", "stopMonitoringBeacons", "onBeaconsInRange", "hideOptionMenu", "showOptionMenu", "closeWindow", "hideMenuItems", "showMenuItems", "hideAllNonBaseMenuItem", "showAllNonBaseMenuItem", "scanQRCode", "openProductSpecificView", "chooseCard", "addCard", "openCard", "chooseWXPay", "openBusinessView");
        return jsonStr;
    }


    /**
     * ????????????????????????
     *
     * @param url
     * @param debug
     * @throws Exception
     */
    private String getwxPointConfig(String url, boolean debug) throws Exception {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        MerchantConf conf = this.getWechatMerchantConf(authVo.getSmerchantCode(), 2);
        // ????????????7200s
        Token token = TokenAPI.token(conf.getSwechatPointAppid(), conf.getSwechatPointAppSecret());
        Ticket tiket = TicketAPI.ticketGetticket(token.getAccess_token());
        String jsapiTicket = tiket.getTicket();
        logger.info("#############jsapiTicket############:{}", jsapiTicket);
        String jsonStrPoint = JsUtil.generateConfigJson(jsapiTicket, debug, conf.getSwechatPointAppid(), url, "checkJsApi", "onMenuShareTimeline", "onMenuShareAppMessage", "onMenuShareQQ", "onMenuShareWeibo", "onMenuShareQZone", "chooseImage", "previewImage", "uploadImage", "downloadImage", "startRecord", "stopRecord", "onVoiceRecordEnd", "playVoice", "pauseVoice", "stopVoice", "onVoicePlayEnd", "uploadVoice", "downloadVoice", "translateVoice", "getNetworkType", "openLocation", "getLocation", "startMonitoringBeacons", "stopMonitoringBeacons", "onBeaconsInRange", "hideOptionMenu", "showOptionMenu", "closeWindow", "hideMenuItems", "showMenuItems", "hideAllNonBaseMenuItem", "showAllNonBaseMenuItem", "scanQRCode", "openProductSpecificView", "chooseCard", "addCard", "openCard", "chooseWXPay", "openBusinessView");
        logger.info("???????????????????????????JS?????????{}", jsonStrPoint);
        return jsonStrPoint;
    }
}
