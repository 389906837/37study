package com.cloud.cang.wap.common.utils;


import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.CodeEnum;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.weixin.api.TicketAPI;
import com.cloud.cang.weixin.api.TokenAPI;
import com.cloud.cang.weixin.bean.Ticket;
import com.cloud.cang.weixin.bean.Token;
import com.cloud.cang.weixin.util.JsUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class WapUtils {

    /**
     * 判断是不是微信浏览器
     * @param httpRequest
     * @return
     */
	public static boolean isWXRequest(HttpServletRequest httpRequest){
		 String userAgent = httpRequest.getHeader("user-agent").toLowerCase();
    	 if (userAgent.indexOf("micromessenger") > 0) {//是微信浏览器
    		 return true;
    	 }
    	 return false;
	}
    /**
     * 判断是不是支付宝
     * @param httpRequest
     * @return
     */
    public static boolean isAlipayRequest(HttpServletRequest httpRequest){
        String userAgent = httpRequest.getHeader("user-agent").toLowerCase();
        if (userAgent.indexOf("alipay") > 0) {//是支付宝浏览器
            return true;
        }
        return false;
    }
    /**
     * 获取当前地址
     * @param request
     * @return
     */
	public static String getDefaultBackUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String domain = request.getServerName();
        int port = request.getServerPort();
        StringBuilder backUrl = new StringBuilder(scheme);
        backUrl.append("://");
        backUrl.append(domain);
        if("http".equalsIgnoreCase(scheme) && port != 80) {
            backUrl.append(":").append(String.valueOf(port));
        } else if("https".equalsIgnoreCase(scheme) && port != 443) {
            backUrl.append(":").append(String.valueOf(port));
        }
        backUrl.append(request.getRequestURI());
        if(StringUtils.isNotBlank(request.getQueryString())){
            backUrl.append("?"+request.getQueryString());
        }
        return backUrl.toString();
    }

    /**
     * 从cookie获取商户编号
     * cookie 没有获取默认商户编号
     * @return
     */
    public static String getMerchantCookie(HttpServletRequest request) throws Exception {
        String merchantCode = CookieUtils.getTokenByCookie(request, WapConstants.MERCHANT_COOKIE_NAME);
        if (StringUtil.isBlank(merchantCode)) {
            merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_merchant_code").getSvalue();
        }
        return merchantCode;
    }

    /**
     * 从cookie获取设备编号#//#设备ID
     * @return
     */
    public static String getDeviceCodeCookie(HttpServletRequest request) throws Exception {
        return CookieUtils.getTokenByCookie(request, WapConstants.DEVICE_COOKIE_NAME);
    }
    /**
     * 错误处理 工具
     * @param errorCode 错误码
     * @param currentUrl 当前路径
     * @return
     */
    public static ModelAndView errorDealWith(String errorCode, String currentUrl) {
        String rootPath = EvnUtils.getValue("wap.http.domain");
        Map<String, String> map = new HashMap<String, String>();

        map.put("errorCode", errorCode);//错误码
        map.put("currentUrl", currentUrl);//当前路径
        return new ModelAndView("redirect:" + rootPath + "/uc/error", map);
    }


    /**
     * 设置设备编号的cookie
     * @param deviceStr 设备编号#//#设备ID
     * @param response
     */
    public static void setDeviceCookie(HttpServletResponse response, String deviceStr) {
        //计算cookie 有效期
        int hours = 1;//小时
        String validityStr = BizParaUtil.get(WapConstants.DEVICE_COOKIE_VALIDITY);
        try {
            hours = Integer.parseInt(validityStr);
        } catch (Exception e) {
            hours = 1;
        }
        int expiry = hours * 60 * 60;
        CookieUtils.addCookie(response, WapConstants.DEVICE_COOKIE_NAME, deviceStr, expiry);
    }

    /**
     * 设置商户的cookie
     * @param merchantCode 商户编号
     * @param response
     */
    public static void setMerchantCookie(HttpServletResponse response, String merchantCode) {
        //计算cookie 有效期
        int hours = 24;//小时
        String validityStr = BizParaUtil.get(WapConstants.MERCHANT_COOKIE_VALIDITY);
        try {
            hours = Integer.parseInt(validityStr);
        } catch (Exception e) {
            hours = 24;
        }
        int expiry = hours * 60 * 60;
        CookieUtils.addCookie(response, WapConstants.MERCHANT_COOKIE_NAME, merchantCode, expiry);
    }
}
