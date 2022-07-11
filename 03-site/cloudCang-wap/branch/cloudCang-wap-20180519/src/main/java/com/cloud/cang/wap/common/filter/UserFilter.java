package com.cloud.cang.wap.common.filter;/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.WapUtils;

import com.alibaba.druid.support.json.JSONUtils;

import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.security.utils.ClientSavedRequest;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.sh.service.MerchantInfoService;
import com.cloud.cang.weixin.api.SnsAPI;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * Filter that allows access to resources if the accessor is a known user, which is defined as
 * having a known principal.  This means that any user who is authenticated or remembered via a
 * 'remember me' feature will be allowed access from this filter.
 * <p/>
 * If the accessor is not a known user, then they will be redirected to the {@link #setLoginUrl(String) loginUrl}</p>
 *
 * @since 0.9
 */
public class UserFilter extends AccessControlFilter {

    @Autowired
    private IndexService indexService;
    /**
     * Returns <code>true</code> if the request is a
     * {@link #isLoginRequest(javax.servlet.ServletRequest, javax.servlet.ServletResponse) loginRequest} or
     * if the current {@link #getSubject(javax.servlet.ServletRequest, javax.servlet.ServletResponse) subject}
     * is not <code>null</code>, <code>false</code> otherwise.
     *
     * @return <code>true</code> if the request is a
     * {@link #isLoginRequest(javax.servlet.ServletRequest, javax.servlet.ServletResponse) loginRequest} or
     * if the current {@link #getSubject(javax.servlet.ServletRequest, javax.servlet.ServletResponse) subject}
     * is not <code>null</code>, <code>false</code> otherwise.
     */
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            // If principal is not null, then the user is known and should be allowed access.
            return subject.getPrincipal() != null;
        }
    }

    /**
     * This default implementation simply calls
     * {@link #saveRequestAndRedirectToLogin(javax.servlet.ServletRequest, javax.servlet.ServletResponse) saveRequestAndRedirectToLogin}
     * and then immediately returns <code>false</code>, thereby preventing the chain from continuing so the redirect may
     * execute.
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String head = httpRequest.getHeader("Ajax-Header");
        if (null != head && head.toUpperCase().equals("AJAX")) {
        	//拦截ajax请求
        	httpResponse.setContentType("text/json");  
        	httpResponse.setDateHeader("Expires", 0);  
            PrintWriter out = httpResponse.getWriter(); 
            
            Map<String, Object> outPut=new HashMap<String, Object>();
            outPut.put("sessionTimeout", true);
            outPut.put("loginUrl", getLoginUrl());
            httpResponse.setHeader("sessionstatus", "sessionOut");  
            if(StringUtil.isNotNull(httpRequest.getParameter("callback"))&&StringUtil.isNotNull(httpRequest.getParameter("_"))){
            	//jsonP
            	out.println(httpRequest.getParameter("callback")+"("+JSONUtils.toJSONString(outPut)+")");
            }else{
            	//普通ajax
            	out.println(JSONUtils.toJSONString(outPut));
            }
            out.flush();
            out.close();
        } else {
            String rootPath = EvnUtils.getValue("wap.http.domain");
	    	if(WapUtils.isWXRequest(httpRequest)) {// 是微信浏览器
	    		String urlBuffer = indexService.getWxUrlForXW( httpRequest);
	    		if (StringUtil.isBlank(urlBuffer)) {
                    WebUtils.issueRedirect(request, response, rootPath + "/uc/error?errorCode=10000");
                } else {
                    WebUtils.issueRedirect(request, response, urlBuffer);
                }
	            return false;
	    	} else if(WapUtils.isAlipayRequest(httpRequest)) {//是支付宝浏览器
                String urlBuffer = indexService.getAlipayUrlForXW(httpRequest);
                if (StringUtil.isBlank(urlBuffer)) {
                    WebUtils.issueRedirect(request, response, rootPath + "/uc/error?errorCode=10000");
                } else {
                    WebUtils.issueRedirect(request, response, urlBuffer);
                }
                return false;
            }
            WebUtils.issueRedirect(request, response, rootPath + "/uc/error?errorCode=10000");
        	/*String backUrl = request.getParameter("backUrl");
        	saveRequest(request, backUrl, WapUtils.getDefaultBackUrl(WebUtils.toHttp(request)));
        	redirectToLogin(request, response);*/
        }
        return false;
    }

    /*protected void saveRequest(ServletRequest request, String backUrl, String fallbackUrl) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        session.setAttribute("authc.fallbackUrl", fallbackUrl);
        SavedRequest savedRequest = new ClientSavedRequest(httpRequest, backUrl);
        session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);
    }*/

}
