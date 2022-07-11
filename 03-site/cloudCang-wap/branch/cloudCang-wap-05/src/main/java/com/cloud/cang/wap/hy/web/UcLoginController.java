package com.cloud.cang.wap.hy.web;

import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RequestUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.hy.ThirdAuthorise;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.core.CaptchaUsernamePasswordToken;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.wap.common.CodeEnum;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.common.utils.gt.GeetestConfig;
import com.cloud.cang.wap.common.utils.gt.GeetestLib;
import com.cloud.cang.wap.hy.service.ThirdAuthoriseService;
import com.cloud.cang.wap.hy.vo.RegisterVo;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.om.service.OrderRecordService;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import com.cloud.cang.wap.sh.service.MerchantClientConfService;
import com.cloud.cang.wap.sh.service.MerchantInfoService;
import com.cloud.cang.wap.sys.service.OperatorService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import com.cloud.cang.wap.common.SiteResponseVo;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.hy.service.MemberInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.weixin.api.SnsAPI;
import com.cloud.cang.weixin.bean.SnsToken;
import com.cloud.cang.weixin.bean.User;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhouhong
 *         会员登录  授权登录 控制器
 */
@Controller
@RequestMapping("/uc")
public class UcLoginController {

    private static final Logger logger = LoggerFactory.getLogger(UcLoginController.class);
    @Autowired
    private ICached iCached;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private ThirdAuthoriseService thirdAuthoriseService;
    @Autowired
    private IndexService indexService;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    MerchantClientConfService merchantClientConfService;

    /**
     * 处理登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        try {
            AuthorizationVO authorizationVO = SessionUserUtils.getSessionAttributeForUserDtl();
            if (authorizationVO != null) {
                //跳转页面
                String fallbackUrl = (String) SessionUserUtils.getSession().getAttribute("authc.fallbackUrl");
                if (StringUtil.isNotBlank(fallbackUrl)) {
                    SessionUserUtils.getSession().setAttribute("authc.fallbackUrl", "");
                    return new ModelAndView("redirect:" + fallbackUrl);
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("merchantCode", authorizationVO.getSmerchantCode());
                map.put("isScan", "0");
                //1、处理访问来源
                if (WapUtils.isWXRequest(request)) {
                    map.put("openSource", "wechat");
                } else if (WapUtils.isAlipayRequest(request)) {
                    map.put("openSource", "alipay");
                }
                return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/index/main.html", map);
            }
            String url = "";
            if (WapUtils.isWXRequest(request)) {// 是微信浏览器
                url = indexService.getWxUrlForXW(request);
            } else if (WapUtils.isAlipayRequest(request)) {//是支付宝浏览器
                url = indexService.getAlipayUrlForXW(request);
            }
            if (StringUtil.isNotBlank(url)) {
                return new ModelAndView("redirect:" + url);
            }
            return new ModelAndView("uc/register_new");
        } catch (Exception e) {
            logger.error("登录系统异常:{}", e);
            return WapUtils.errorDealWith("10008", "");
        }
    }

    /**
     * 支付宝绑定手机号页面
     *
     * @return
     */
    @RequestMapping("/mobileAlipayBind")
    public String mobileAlipayBind() {
        return "uc/register_new";
    }

    /**
     * 微信绑定手机号页面
     *
     * @return
     */
    @RequestMapping("/mobileWxBind")
    public String mobileWxBind() {
        return "uc/register_new";
    }

    /**
     * 不是微信支付宝打开 跳转错误页面
     *
     * @param currentUrl 访问url
     * @param errorCode  错误码
     * @return
     */
    @RequestMapping("/error")
    public String error(Integer errorCode, String currentUrl, String code, ModelMap map) {
        if (null != errorCode) {
            if (errorCode == -1) {
                map.put("errorMsg", currentUrl);
                map.put("errorCode", errorCode);
            } else {
                if (StringUtil.isNotBlank(currentUrl)) {
                    map.put("currentUrl", currentUrl);
                }
                map.put("errorCode", errorCode);
                if (10020 == errorCode) {
                    map.put("title", "订单出现异常");
                }
                map.put("errorMsg", CodeEnum.getNameByCode(errorCode));
                if (StringUtil.isNotBlank(code)) {
                    map.put("code", code);
                }
                /*if (errorCode.equals("10015")) {
                    //购物异常
                } else if (errorCode.equals("10016")) {
                    //补货异常
                }*/
            }
        }
        return "uc/error";
    }

    /**
     * 支付宝联合回调
     *
     * @param request
     * @return
     */
    @RequestMapping("/callback/alipay")
    public ModelAndView callbackAlipay(HttpServletRequest request, ModelMap map) throws Exception {
        String appId = request.getParameter("app_id");
        String code = request.getParameter("auth_code");
        String back_uri = request.getParameter("back_uri");
        if (StringUtil.isNotBlank(back_uri)) {
            back_uri = back_uri.replaceAll("-", "&");
        }
        /*String state = request.getParameter("state");
        Map<String, String> map = new HashMap<String, String>();
		map.put("back_uri", back_uri);
		map.put("code", code);
		map.put("state", state);
		map.put("appId", appId);
		//return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/uc/alipayBind", map);*/
        return dealWithCallbackAlipay(request, appId, code, back_uri, map);


    }

    private ModelAndView dealWithCallbackAlipay(HttpServletRequest request, String appId, String code, String back_uri, ModelMap map) throws Exception {
        //获取商户编号
        String merchantCode = WapUtils.getMerchantCookie(request);
        //获取支付宝商户配置数据
        MerchantConf conf = indexService.getAlipayMerchantConf(merchantCode, 1);
        if (!appId.equals(conf.getSappId())) {
            map.put("resVo", new SiteResponseVo(false, -1, "商户数据异常，请尝试重新登录操作！", -1));
            return new ModelAndView("error/error");
        }
        AlipayClient alipayClient = indexService.getAlipayClient(conf.getSmerchantCode());
        if (null == alipayClient) {
            map.put("resVo", new SiteResponseVo(false, -1, "居然出现这个错误，在重新登录试试呢！", -1));
            return new ModelAndView("error/error");
        }
        AlipaySystemOauthTokenRequest authTokenRequest = new AlipaySystemOauthTokenRequest();
        authTokenRequest.setCode(code);
        authTokenRequest.setGrantType("authorization_code");
        Long startTime = System.nanoTime();
        AlipaySystemOauthTokenResponse authTokenResponse = alipayClient.execute(authTokenRequest);
        Long endTime = System.nanoTime();
        logger.info("支付宝获取accectToken执行时间：" + ((endTime - startTime) / 1000 / 1000) + "毫秒");
        AlipayUserInfoShareRequest userInfoShareRequest = new AlipayUserInfoShareRequest();
        startTime = System.nanoTime();
        AlipayUserInfoShareResponse userInfoShareResponse = alipayClient.execute(userInfoShareRequest, authTokenResponse.getAccessToken());
        endTime = System.nanoTime();
        logger.info("支付宝获取会员信息执行时间：" + ((endTime - startTime) / 1000 / 1000) + "毫秒");
        if (userInfoShareResponse.isSuccess()) {
            // 将微信用户信息缓存与session中
            SessionUserUtils.getSession().setAttribute(WapConstants.ALIPAY_SESSION_USER, userInfoShareResponse);
            // 将微信授权码缓存与session中
            SessionUserUtils.getSession().setAttribute(WapConstants.ALIPAY_SESSION_AUTH_CODE, code);
            SessionUserUtils.getSession().setAttribute("authc.fallbackUrl", back_uri);
        } else {
            map.put("resVo", new SiteResponseVo(false, -1, "授权信息获取失败，请尝试重新登录操作！", -1));
            return new ModelAndView("error/error");
        }
        List<ThirdAuthorise> list = thirdAuthoriseService.selectThirdAuthoriseByOpenId(merchantCode, userInfoShareResponse.getUserId(), 20);
        MemberInfo memberInfo = null;
        if (null != list && list.size() > 0) {
            ThirdAuthorise thirdAuthorise = list.get(0);
            memberInfo = memberInfoService.selectByPrimaryKey(thirdAuthorise.getSmemberId());
            // 登录授权
            Subject currentUser = SecurityUtils.getSubject();
            String remoteIp = SessionUserUtils.getIpAddr(request);
            CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(memberInfo.getSmobile(), memberInfo.getSloginPwd(), false, remoteIp, "", memberInfo.getSmerchantCode());
            token.setLoginForm("y");
            currentUser.login(token);
            SessionUserUtils.getSession().setAttribute("LOGIN_TYPE", 10);
            SessionUserUtils.getSession().setAttribute("LOGIN_DEVICE_TYPE", 20);
        } else {
            return toBindAlipayPage(userInfoShareResponse);
        }
        if (StringUtils.isNotBlank(back_uri)) {//登录跳转地址
            return new ModelAndView("redirect:" + back_uri);
        } else {//不存在跳转首页
            return indexService.getIndexUrl(request);
        }
    }

    /**
     * 支付宝联合回调
     *
     * @param request
     * @return
     */
    @RequestMapping("/alipayBind")
    public ModelAndView alipayBind(HttpServletRequest request, ModelMap map) throws Exception {
        String appId = request.getParameter("appId");
        String code = request.getParameter("code");
        String back_uri = request.getParameter("back_uri");
        return dealWithCallbackAlipay(request, appId, code, back_uri, map);
    }

    /**
     * 跳转到支付宝绑定手机号页面
     *
     * @param userInfoShareResponse 支付宝授权信息
     * @return
     */
    private ModelAndView toBindAlipayPage(AlipayUserInfoShareResponse userInfoShareResponse) {
        SessionUserUtils.getSession().setAttribute(WapConstants.ALIPAY_SESSION_USER, userInfoShareResponse);
        return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/uc/mobileAlipayBind");
    }

    /**
     * 微信联合登录回调
     *
     * @param request
     * @return
     */
    @RequestMapping("/callback/weixin")
    public ModelAndView callbackWechat(HttpServletRequest request, ModelMap map) throws Exception {
        String code = request.getParameter("code");
        String back_uri = request.getParameter("back_uri");
        if (StringUtil.isNotBlank(back_uri)) {
            back_uri = back_uri.replaceAll("-", "&");
        }
        /*String state = request.getParameter("state");
        Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("state", state);
		map.put("back_uri", back_uri);
		return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/uc/wxbind", map);*/
        return dealWithCallbackWechat(request, code, back_uri, map);
    }

    private ModelAndView dealWithCallbackWechat(HttpServletRequest request, String code, String back_uri, ModelMap map) throws Exception {
        //获取商户编号
        String merchantCode = WapUtils.getMerchantCookie(request);
        //获取商户微信配置数据
        MerchantConf conf = indexService.getWechatMerchantConf(merchantCode, 1);
        if (null == conf) {//异常情况返回空
            map.put("resVo", new SiteResponseVo(false, -1, "居然出现这个错误，在重新登录试试呢！", -1));
            return new ModelAndView("error/error");
        }
        String appId = conf.getSappId();
        String secret = conf.getSappSecret();
        Long startTime = System.nanoTime();
        // 获取Token
        SnsToken snsToken = getSnsToken(appId, code, secret);
        // 获取用户信息
        User user = SnsAPI.userinfo(snsToken.getAccess_token(), snsToken.getOpenid(), "zh_CN");
        Long endTime = System.nanoTime();
        logger.info("微信获取用户信息执行时间：" + ((endTime - startTime) / 1000 / 1000) + "毫秒");
        if (null == user) {
            map.put("resVo", new SiteResponseVo(false, -1, "居然出现这个错误，在重新登录试试呢！", -1));
            return new ModelAndView("error/error");
        }

        // 将微信用户信息缓存与session中
        SessionUserUtils.getSession().setAttribute(WapConstants.WEIXIN_SESSION_USER, user);
        // 将微信授权码缓存与session中
        SessionUserUtils.getSession().setAttribute(WapConstants.WEIXIN_SESSION_AUTH_CODE, code);
        SessionUserUtils.getSession().setAttribute("authc.fallbackUrl", back_uri);

        List<ThirdAuthorise> list = thirdAuthoriseService.selectThirdAuthoriseByOpenId(merchantCode, user.getOpenid(), 10);
        MemberInfo memberInfo = null;
        if (null != list && list.size() > 0) {
            ThirdAuthorise thirdAuthorise = list.get(0);
            memberInfo = memberInfoService.selectByPrimaryKey(thirdAuthorise.getSmemberId());
            // 登录授权
            Subject currentUser = SecurityUtils.getSubject();
            String remoteIp = SessionUserUtils.getIpAddr(request);
            CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(memberInfo.getSmobile(), memberInfo.getSloginPwd(), false, remoteIp, "", memberInfo.getSmerchantCode());
            token.setLoginForm("y");
            currentUser.login(token);
            SessionUserUtils.getSession().setAttribute("LOGIN_TYPE", 10);
            SessionUserUtils.getSession().setAttribute("LOGIN_DEVICE_TYPE", 10);
        } else {
            return toWxBindPage(user);
        }
        if (StringUtils.isNotBlank(back_uri)) {//登录跳转地址
            return new ModelAndView("redirect:" + back_uri);
        } else {//不存在跳转首页
            return indexService.getIndexUrl(request);
        }
    }

    /**
     * 微信联合登录回调
     *
     * @param request
     * @return
     */
    @RequestMapping("/wxbind")
    public ModelAndView wxbind(HttpServletRequest request, ModelMap map) throws Exception {
        String code = request.getParameter("code");
        String back_uri = request.getParameter("back_uri");
        return dealWithCallbackWechat(request, code, back_uri, map);
    }

    /**
     * 微信跳转到绑定手机号页面
     *
     * @param user 微信返回的用户
     * @return
     */
    private ModelAndView toWxBindPage(User user) {
        SessionUserUtils.getSession().setAttribute(WapConstants.WEIXIN_SESSION_USER, user);
        return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/uc/mobileWxBind");
    }

    /**
     * 授权获取微信信息
     *
     * @param appid  微信APPID
     * @param code   授权码
     * @param secret 秘钥
     * @return
     */
    private SnsToken getSnsToken(String appid, String code, String secret) {
        SnsToken snsToken = SnsAPI.oauth2AccessToken(appid, secret, code);
        return snsToken;
    }


    @RequestMapping("/testWebsoket")
    public String testWebsoket() {
        return "uc/testWebsoket";
    }


    /**
     * 获取验证码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getGtCode")
    @ResponseBody
    public ResponseVo<String> getGtCode(HttpServletRequest request) {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                    GeetestConfig.isnewfailback());
            String userid = "";
            if (WapUtils.isWXRequest(request)) {
                User user = (User) SessionUserUtils.getSession().getAttribute(WapConstants.WEIXIN_SESSION_USER);
                if (null != user) {
                    userid = user.getOpenid();
                }
            } else if (WapUtils.isAlipayRequest(request)) {
                AlipayUserInfoShareResponse response = (AlipayUserInfoShareResponse) SessionUserUtils.getSession().getAttribute(WapConstants.ALIPAY_SESSION_USER);
                if (null != response) {
                    userid = response.getUserId();
                }
            }
            if (StringUtil.isNotBlank(userid)) {
                //自定义参数,可选择添加
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("user_id", userid); //网站用户id
                param.put("client_type", "h5"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
                param.put("ip_address", RequestUtils.getIpAddr(request)); //传输用户请求验证时所携带的IP

                //进行验证预处理
                int gtServerStatus = gtSdk.preProcess(param);

                //将服务器状态设置到session中
                request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
                //将userid设置到session中
                request.getSession().setAttribute("userid", userid);
                responseVo.setData(gtSdk.getResponseStr());
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("系统异常:{}", e.getMessage());
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("验证码获取异常");
    }


    /**
     * 验证是否开门要扫码
     *
     * @param operType   0 系统主动开门 1 用户点击开门操作
     * @param memberType 会员类型 0 普通会员 1 补货会员
     * @throws Exception
     */
    @RequestMapping("/isAutoOpen")
    @ResponseBody
    public ResponseVo<String> isAutoOpen(Integer operType, Integer memberType, HttpServletRequest request) throws Exception {
        DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        if (null == authVo) {
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo("访问登录超时，请退出重新登录");
        }
        logger.info("设备信息:{}", deviceVo);
        if (null != memberType && memberType == 1) {
            //补货员信息
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userName", authVo.getUserName());
            paramMap.put("merchantCode", authVo.getSmerchantCode());
            paramMap.put("deviceCode", deviceVo.getDeviceId());
            Operator operator = operatorService.selectByMap(paramMap);
            if (null == operator) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("无开门权限，请确认您的身份！");
            }
        }

        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //0 不能直接开门
        responseVo.setData("0");
        //扫码方式 1:微信  2:支付宝
        Integer sweepType = 1;
        if (WapUtils.isWXRequest(request)) {
            responseVo.setMsg("1");
        } else if (WapUtils.isAlipayRequest(request)) {
            sweepType = 2;
            responseVo.setMsg("2");
        }
        if (null != operType && operType.intValue() == 0) {//系统主动触发开门
            String deviceId = (String) iCached.get(deviceVo.getDeviceId() + "_" + authVo.getId() + "_open_door");
            if (StringUtil.isNotBlank(deviceId)) {
                ResponseVo<String> resVo = verifyOpen(authVo.getId(), memberType, operType, sweepType);
                if (!resVo.isSuccess()) {
                    iCached.remove(deviceId + "_" + authVo.getId() + "_open_door");//删除主动开门自动开门的KEY
                    return resVo;
                }
                if (StringUtil.isNotBlank(deviceVo.getOpenDoorKey())) {//判读开门的KEY 是否存在 存在直接开门 不存在扫码
                    String deviceIdBySession = (String) iCached.get(deviceVo.getOpenDoorKey());
                    if (StringUtil.isNotBlank(deviceIdBySession) && deviceIdBySession.equals(deviceVo.getDeviceId())) {
                        responseVo.setData("1");
                        return responseVo;
                    }
                }
            }
        } else {
            ResponseVo<String> resVo = verifyOpen(authVo.getId(), memberType, operType, sweepType);
            if (!resVo.isSuccess()) {
                return resVo;
            }
            //是否直接调用开门
            if (StringUtil.isNotBlank(deviceVo.getOpenDoorKey())) {//判读开门的KEY 是否存在 存在直接开门 不存在扫码
                String deviceIdBySession = (String) iCached.get(deviceVo.getOpenDoorKey());
                if (StringUtil.isNotBlank(deviceIdBySession) && deviceIdBySession.equals(deviceVo.getDeviceId())) {
                    //1 能直接开门
                    responseVo.setData("1");
                    return responseVo;
                }
            }
            /*//支付宝浏览器 存放缓存
            if (WapUtils.isWXRequest(request)) {
                iCached.remove("is_replenishment_scan_" + authVo.getId());
            } else if (WapUtils.isAlipayRequest(request)) {
                //用户主动扫码开门 记录状态放到缓存
                iCached.put(deviceVo.getDeviceId() + "_" + authVo.getId() + "_open_door", deviceVo.getDeviceId());
            }*/
        }
        return responseVo;
    }

    /***
     * 验证开门 数据有效性
     * @param memberId 会员Id
     * @param memberType 会员类型 0 购物会员 1 补货员
     * @param operType 操作类型  0 系统主动开门 1 会员主动开门
     * @param sweepType 扫码方式 1:微信  2:支付宝
     * @return
     */
    private ResponseVo<String> verifyOpen(String memberId, Integer memberType, Integer operType, Integer sweepType) throws Exception {
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);
        if (memberInfo == null || memberInfo.getIstatus().intValue() != 1) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员状态异常");
        }
        if (null != memberType && memberType.intValue() == 1) {
            /*if (null != operType && operType.intValue() == 1) {
                iCached.put("is_replenishment_scan_"+memberId, 1);
            }*/
        } else {
            /*if (null != operType && operType.intValue() == 1) {
                iCached.put("is_replenishment_scan_"+memberId, 0);
            }*/
            //判断是否能开门  未支付订单不能开门
            List<OrderRecord> orderRecords = orderRecordService.selectExceptionOrder(memberId);
            if (null != orderRecords && orderRecords.size() > 0) {
                //有异常订单
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("开门异常有未处理异常订单");
            }
            //判断商户是否仅代扣   判断会员是否开通免密
            MerchantClientConf merchantClientConf = merchantClientConfService.selectByPrimaryKey(memberInfo.getSmerchantId());
            if (null != merchantClientConf.getIsupportPayWay() && 20 == merchantClientConf.getIsupportPayWay()) {
                if ((sweepType == 1 && 0 == memberInfo.getIwechatOpen()) ||
                        (sweepType == 2 && 0 == memberInfo.getIaipayOpen())) {
                    //有异常订单
                    return ErrorCodeEnum.ERROR_COMMON_OPEN_FREEPAY.getResponseVo("立即开通免密支付功能");
                }
            }
        }
        return ResponseVo.getSuccessResponse();
    }
}
