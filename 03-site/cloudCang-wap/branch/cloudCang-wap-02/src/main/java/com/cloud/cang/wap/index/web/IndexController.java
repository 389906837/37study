package com.cloud.cang.wap.index.web;


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
import com.cloud.cang.wap.index.vo.OpenSuccessData;
import com.cloud.cang.wap.sb.service.AiInfoService;
import com.cloud.cang.wap.sb.service.DeviceInfoService;
import com.cloud.cang.wap.sb.vo.DeviceVo;
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
     * 会员首页
     *
     * @param deviceVo 访问设备参数
     * @return
     */
    @RequestMapping("/main")
    public ModelAndView main(DeviceVo deviceVo, ModelMap map, HttpServletRequest request) throws Exception {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
        deviceVo = indexService.indexExceptionDealwith(authVo, deviceVo, request);
        if (StringUtil.isNotBlank(deviceVo.getErrorCode())) {
            return WapUtils.errorDealWith(deviceVo.getErrorCode(), "");
        }
        //获取首页数据
        indexService.getIndexData(deviceVo, authVo, request, 10, map);
        iCached.remove("is_replenishment_scan_" + authVo.getId());
        return new ModelAndView("/index/index_new");
    }


    /**
     * 开门成功异步获取数据
     *
     * @throws Exception
     */
    @RequestMapping("/openSuccess")
    @ResponseBody
    public ResponseVo openSuccess() throws Exception {
        OpenSuccessData openSuccessData = new OpenSuccessData();
        logger.debug("会员开门成功...");
        DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
        logger.info("会员开门成功，设备信息:{}", deviceVo);
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
        Map<String, Object> params = new HashMap<>();
        //商户客户端配置信息
        params = indexService.getMerchantClientConfByMap(params, deviceInfo.getSmerchantCode());
        //获取用户数据
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        params.put("userId", authVo.getId());
        openSuccessData.setOpen(params);
        openSuccessData.setOpenHint((String) params.get("openHint"));
        //本机优惠信息
        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ActivityServicesDefine.QUERY_SDEVICE_ACTIVITY_SDESCRIPTION);// 服务名称
        // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<List<String>>>() {
        });
        invoke.setRequestObj(deviceInfo.getId()); // post 参数
        ResponseVo<List<String>> resVO = (ResponseVo<List<String>>) invoke.invoke();
        if (resVO.isSuccess()) {
            if (null != resVO.getData() && resVO.getData().size() > 0) {
                openSuccessData.setPreferentialInfos(resVO.getData());
            }
        }
        return ResponseVo.getSuccessResponse(openSuccessData);
    }


    /**
     * 会员取消扫码 删除开门的KEY
     */
    @RequestMapping("/cancelScan")
    @ResponseBody
    public ResponseVo<String> cancelScan() {
        try {
            DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            logger.info("设备信息:{}", deviceVo);
            iCached.remove(deviceVo.getDeviceId() + "_" + authVo.getId() + "_open_door");
            iCached.remove("is_replenishment_scan_" + authVo.getId());
        } catch (Exception e) {
            logger.error("取消扫码异常：{}", e);
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 扫码成功回调
     *
     * @param memberType 会员类型 0 普通会员 1 补货会员 2 补货员查看设备库存
     */
    @RequestMapping("/successScan")
    @ResponseBody
    public ResponseVo<String> successScan(Integer memberType) {
        try {
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();

            if (null != memberType && memberType.intValue() == 2) {
                iCached.put("is_device_stock_scan_" + authVo.getId(), 1);
                return ResponseVo.getSuccessResponse();
            }
            DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
            logger.info("设备信息:{}", deviceVo);
            //用户主动扫码开门 记录状态放到缓存
            iCached.put(deviceVo.getDeviceId() + "_" + authVo.getId() + "_open_door", deviceVo.getDeviceId());
            if (null != memberType && memberType.intValue() == 1) {
                iCached.put("is_replenishment_scan_" + authVo.getId(), 1);
            } else {
                iCached.put("is_replenishment_scan_" + authVo.getId(), 0);
            }
        } catch (Exception e) {
            logger.error("扫码成功回调异常：{}", e);
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 会员开通免密不在提醒
     */
    @RequestMapping("/noRemind")
    @ResponseBody
    public ResponseVo<String> noRemind() {
        try {
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            iCached.put("is_remind_open_free_data_" + authVo.getId(), authVo.getId());
        } catch (Exception e) {
            logger.error("会员开通免密不在提醒异常：{}", e);
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 会员授权垃圾箱不再提醒
     */
    @RequestMapping("/authNoRemind")
    @ResponseBody
    public ResponseVo<String> authNoRemind(String trashBrand) {
        try {
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            iCached.put("is_remind_auth_trash_data_" + authVo.getId(), authVo.getId());
        } catch (Exception e) {
            logger.error("会员授权垃圾箱不再提醒异常：{}", e);
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 获取商户服务电话
     */
    @RequestMapping("/getServiceTell")
    @ResponseBody
    public ResponseVo<String> getServiceTell() {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
            MerchantClientConf conf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + authVo.getSmerchantCode());
            if (null != conf && StringUtil.isNotBlank(conf.getScontactMobile())) {
                responseVo.setData(conf.getScontactMobile());
            } else {
                responseVo.setData(GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_contact_mobile").getSvalue());
            }
            if (StringUtil.isNotBlank(conf.getScustomerServiceTime())) {
                responseVo.setMsg(conf.getScustomerServiceTime());
            } else {
                responseVo.setMsg(GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_customer_service_time").getSvalue());
            }
        } catch (Exception e) {
            logger.error("获取商户服务电话异常：{}", e);
        }
        return responseVo;
    }

    /**
     * 设备访问 入口 判读访问来源
     * 统一入口
     *
     * @param deviceVo
     * @return
     */
    @RequestMapping("/visit")
    public ModelAndView visit(DeviceVo deviceVo, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("统一访问入口开始...");

        String rootPath = EvnUtils.getValue("wap.http.domain");
        String currentUrl = rootPath + request.getServletPath();
        try {
            logger.info("统一入口访问地址：{}", currentUrl);
            //处理操作
            //判断是否正确地址 要么有设备Id 要么有商户编号
            if (null == deviceVo || (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode()))) {//来源地址错误
                return WapUtils.errorDealWith("10001", currentUrl);
            }
            logger.info("统一入口访问参数：{}", deviceVo);
            //1、处理访问来源
            if (WapUtils.isWXRequest(request)) {
                deviceVo.setOpenSource("wechat");
            } else if (WapUtils.isAlipayRequest(request)) {
                deviceVo.setOpenSource("alipay");
            }
            //2、验证设备信息
            if (StringUtil.isNotBlank(deviceVo.getDeviceId())) {
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
                if (null == deviceInfo) {//设备不存在
                    return WapUtils.errorDealWith("10002", currentUrl);
                } else if (deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.NO_REGISTER.getCode()
                        || deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.SCRAPPED.getCode()) {
                    //设备不可用
                    return WapUtils.errorDealWith("10003", currentUrl);
                } else if (deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.OFFLINE.getCode()) {
                    //设备离线
                    return WapUtils.errorDealWith("10013", currentUrl);
                } else if (deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.MALFUNCTION.getCode()) {
                    //设备故障
                    return WapUtils.errorDealWith("10004", currentUrl);
                } else if (deviceInfo.getIoperateStatus().intValue() == BizTypeDefinitionEnum.OperateStatus.DISABLED.getCode()) {
                    //设备已停运
                    return WapUtils.errorDealWith("10007", currentUrl);
                }
                //设备二维码不能使用
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
                //3、处理设备访问时效性
                //写入缓存  生成随机值
                int validity = 30;//30秒
                String validityStr = BizParaUtil.get(WapConstants.OPEN_DOOR_VALIDITY);
                try {
                    validity = Integer.parseInt(validityStr);
                } catch (Exception e) {
                    validity = 30;
                }
                String openDoorKey = IdGenerator.randomUUID(32);
                iCached.put(openDoorKey, deviceVo.getDeviceId(), validity);
                deviceVo.setOpenDoorKey(openDoorKey);
                //将商户写入cookie
                WapUtils.setMerchantCookie(response, deviceVo.getMerchantCode());
                //设备编号写入cookie
                WapUtils.setDeviceCookie(response, deviceInfo.getScode() + "#//#" + deviceInfo.getId());
                request.getSession().setAttribute("openDoorKey", openDoorKey);//存放开门的key 方便跳转用

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
                //将商户写入cookie
                WapUtils.setMerchantCookie(response, deviceVo.getMerchantCode());
                CookieUtils.addCookie(response, WapConstants.DEVICE_COOKIE_NAME, "", 0);//删除本地设备数据
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
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
            //判断扫码会员类型
            Integer replenishmentScan = (Integer) iCached.get("is_replenishment_scan_" + authVo.getId());
            String indexPath = "/index/main";//购物会员
            if ((replenishmentScan == null && authVo.isReplenishment()) || (null != replenishmentScan && replenishmentScan.intValue() == 1)) {
                indexPath = "/replenishment/index";//补货员购物
            }
            Integer deviceStockScan = (Integer) iCached.get("is_device_stock_scan_" + authVo.getId());
            if (null != deviceStockScan && 1 == deviceStockScan) {
                SessionUserUtils.getSession().setAttribute("session_device", deviceVo);//数据存放到session中
                indexPath = "/replenishment/deviceStock";//补货员进入设备库存页面
            }
            return new ModelAndView("redirect:" + rootPath + indexPath, map);
        } catch (Exception e) {
            logger.error("入口访问异常:{}", e);
            return WapUtils.errorDealWith("10008", currentUrl);
        }
    }

    /**
     * 已注册会员
     * 人脸识别注册码访问路径
     *
     * @param deviceVo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/faceAiVisit")
    public ModelAndView faceAiVisit(DeviceVo deviceVo, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("人脸识别注册授权访问入口开始...");

        String rootPath = EvnUtils.getValue("wap.http.domain");
        String currentUrl = rootPath + request.getServletPath();
        String faceToken = "";
        String aiId = deviceVo.getAiId();
        try {
            logger.info("人脸识别注册授权访问地址：{}", currentUrl);
            //处理操作
            //判断是否正确地址 要么有设备Id 要么有商户编号
            if (null == deviceVo || (StringUtil.isBlank(aiId) && StringUtil.isBlank(deviceVo.getMerchantCode()))) {//来源地址错误
                return faceErrorDealWith("10001", currentUrl);
            }
            logger.info("人脸识别注册授权访问参数：{}", deviceVo);
            //1、处理访问来源
            if (WapUtils.isWXRequest(request)) {
                deviceVo.setOpenSource("wechat");
            } else if (WapUtils.isAlipayRequest(request)) {
                deviceVo.setOpenSource("alipay");
            }
            //2、验证设备信息
            if (StringUtil.isNotBlank(aiId)) {
                AiInfo aiInfo = aiInfoService.selectByPrimaryKey(aiId);
                if (null == aiInfo) {//人脸识别设备不存在
                    return faceErrorDealWith("20000", currentUrl);
                } else if (aiInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.NO_REGISTER.getCode()
                        || aiInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.SCRAPPED.getCode()) {
                    //人脸识别设备不可用
                    return faceErrorDealWith("20001", currentUrl);
                } else if (aiInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.OFFLINE.getCode()) {
                    //人脸识别设备离线
                    return faceErrorDealWith("20002", currentUrl);
                } else if (aiInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.MALFUNCTION.getCode()) {
                    //人脸识别设备故障
                    return faceErrorDealWith("20003", currentUrl);
                } else if (aiInfo.getIoperType().intValue() == BizTypeDefinitionEnum.OperateStatus.DISABLED.getCode()) {
                    //人脸识别设备已停运
                    return faceErrorDealWith("20004", currentUrl);
                }

                // 获取token
                faceToken = request.getParameter("token");

                // 判断授权二维码是否过期
                String qrCodeUrl = (String) iCached.get(NettyConst.FACE_REGISTER_QR_CODE + faceToken);
                if (StringUtils.isBlank(qrCodeUrl)) {
                    // 二维码授权连接已经过期
                    return faceErrorDealWith("20005", currentUrl);
                }


                // 判断二维码是否被扫过
                String qrCodeIsScan = (String) iCached.get(NettyConst.FACE_REGISTER_QRCODE_ISSCAN + faceToken);
                if (StringUtils.isBlank(qrCodeIsScan) || (StringUtils.isNotBlank(qrCodeIsScan) && qrCodeIsScan.equals("yes"))) {
                    return faceErrorDealWith("20006", currentUrl);
                }

                // 判断token有效时长
                if (StringUtils.isNotBlank(faceToken)) {
                    String cacheToken = (String) iCached.get(NettyConst.FACE_REGISTER_TOKEN + faceToken);
                    if (StringUtils.isNotBlank(cacheToken) && cacheToken.equals("valid")) { // 有效
                        iCached.put(NettyConst.FACE_REGISTER_QRCODE_ISSCAN + faceToken, "yes", 3600); // 二维码是否被扫码过
                        //调用扫码成功服务发送消息
                        scanSuccessService(deviceVo.getAiId(), faceToken);
                    } else {  // 取消
                        return faceErrorDealWith("20007", currentUrl);
                    }
                }

                // 判断用户是否注册过,不能重复注册
                AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
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
            logger.error("人脸识别入口访问异常:{}", e);
            return faceErrorDealWith("10008", currentUrl);
        }
    }


    /**
     * 授权登录页面
     *
     * @param deviceVo
     * @param map
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/authorizeFaceRegister")
    public ModelAndView authorizeFaceRegister(DeviceVo deviceVo, ModelMap map, HttpServletRequest request) {
        logger.info("设备信息:{}", deviceVo);
        ResponseVo<String> responseVo = new ResponseVo<>(false, 3333, "操作设备失败!");
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
        String faceToken = request.getParameter("token");
        if (StringUtil.isNotBlank(deviceVo.getAiId())) {
            DeviceInfo deviceInfo = deviceInfoService.selectByAiId(deviceVo.getAiId());
            map.put("aiId", deviceVo.getAiId());
            map.put("deviceCode", deviceVo.getDeviceCode());
            if (null != deviceInfo) {
                map.put("deviceName", deviceInfo.getSname());
            }
            map.put("userId", authVo.getId());
            map.put("userCode", authVo.getCode());
            map.put("userName", authVo.getUserName());
            map.put("clientIp", RequestUtils.getIpAddr(request));
            map.put("token", faceToken);
            map.put("trusteeAccountCode", authVo.getTrusteeAccountCode());// 第三方账户
            map.put("time", DateUtils.dateParseString(new Date()));// 时间
            map.put("openSource", deviceVo.getOpenSource());// 时间

        }
        return new ModelAndView("index/authFaceRegister_new");
    }

    /**
     * 跳转授权成功页面
     *
     * @param deviceVo
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/authorizeRegisterSuccess")
    public ModelAndView authorizeRegisterSuccess(DeviceVo deviceVo, ModelMap map, HttpServletRequest request) throws Exception {
        logger.info("设备信息:{}", deviceVo);
        ResponseVo responseVo = null;
        String faceToken = request.getParameter("token");
        String userId = request.getParameter("userId");
        String openSource = request.getParameter("openSource");
        AuthorizeAiFaceDto authorizeAiFaceDto = new AuthorizeAiFaceDto();
        String rootPath = EvnUtils.getValue("wap.http.domain");
        String currentUrl = rootPath + request.getServletPath();

        // 判断token有效性是否取消或者失效
        // token值范围(有效="valid"，取消="cancel"，失效="invalid"||""
        String tokenValue = (String) iCached.get(NettyConst.FACE_REGISTER_TOKEN + faceToken);
        if (StringUtils.isNotBlank(tokenValue) && "cancel".equals(tokenValue)) {
            return faceErrorDealWith("20010", currentUrl);  // 用户在小屏上取消该授权操作
        } else if (StringUtils.isNotBlank(tokenValue) && "invalid".equals(tokenValue)) {
            return faceErrorDealWith("20011", currentUrl);  //  失效
        }
        logger.debug("开始调用长连接服务接口" + DeviceServicesDefine.SCAN_CODE_AUTHORIZE_AI_FACE);
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
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.SCAN_CODE_AUTHORIZE_AI_FACE);
            invoke.setRequestObj(authorizeAiFaceDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.debug("调用长连接服务接口成功，执行方法名：{} ，方法参数：{}");
                map = indexService.getPayConfig(map, request);
                return new ModelAndView("index/authFaceRegisterSuccess_new", map);
            }
        } catch (Exception e) {
            logger.error("授权入口访问异常：{}", e);
        }
        return faceErrorDealWith("20009", currentUrl);
    }

    /**
     * 跳转授权拒绝页面
     *
     * @param deviceVo
     * @param request
     * @throws Exception
     */
    @RequestMapping("/authorizeRegisterFail")
    public ModelAndView authorizeRegisterFail(DeviceVo deviceVo, ModelMap map, HttpServletRequest request) throws Exception {
        logger.info("设备信息:{}", deviceVo);
        ResponseVo responseVo = null;
        String faceToken = request.getParameter("token");
        AuthorizeAiFaceDto authorizeAiFaceDto = new AuthorizeAiFaceDto();
        // //开始调用长连接服务接口，调用netty提供的接口，发送消息给对应的设备
        logger.debug("开始调用长连接服务接口" + DeviceServicesDefine.SCAN_CODE_AUTHORIZE_AI_FACE);
        authorizeAiFaceDto.setAiId(deviceVo.getAiId());
        authorizeAiFaceDto.setAuthorizeType(4);
        authorizeAiFaceDto.setToken(faceToken);
        try {
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.SCAN_CODE_AUTHORIZE_AI_FACE);
            invoke.setRequestObj(authorizeAiFaceDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.debug("调用长连接服务接口成功，执行方法名：{} ，方法参数：{}");
                map = indexService.getPayConfig(map, request);
                return new ModelAndView("index/authFaceRegisterFail_new", map);
            }
        } catch (Exception e) {
            logger.error("授权拒绝访问异常：{}", e);
        }
        String rootPath = EvnUtils.getValue("wap.http.domain");
        String currentUrl = rootPath + request.getServletPath();
        return faceErrorDealWith("20009", currentUrl);
    }

    /**
     * 扫码成功推送消息
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
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.SCAN_CODE_AUTHORIZE_AI_FACE);
            invoke.setRequestObj(authorizeAiFaceDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
        } catch (Exception e) {
            logger.error("扫码异常：{}", e);
        }
        return responseVo;
    }

    /**
     * 错误跳转
     *
     * @param errorCode  错误码
     * @param currentUrl 路径
     * @throws Exception
     */
    public ModelAndView faceErrorDealWith(String errorCode, String currentUrl) {
        String rootPath = EvnUtils.getValue("wap.http.domain");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorCode", errorCode);//错误码
        map.put("currentUrl", currentUrl);//当前路径
        return new ModelAndView("redirect:" + rootPath + "/index/error", map);
    }

    /**
     * 错误页面跳转
     *
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
        map = indexService.getPayConfig(map, request);
        return "index/authError";
    }
}
