/*
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
package com.cloud.cang.security.filter;

import com.alibaba.druid.support.json.JSONUtils;
import com.cloud.cang.security.utils.URLUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

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
    	if(request.getParameter("tokenId")!=null)return true;
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
        
        String head = httpRequest.getHeader("X-Requested-With");
        if (head!=null&&head.equals("XMLHttpRequest")) {
        	//??????ajax??????
        	httpResponse.setContentType("text/json");  
        	httpResponse.setDateHeader("Expires", 0);  
            PrintWriter out = httpResponse.getWriter(); 
            
            Map<String, Object> outPut=new HashMap<String, Object>();
            outPut.put("sessionTimeout", true);
            outPut.put("loginUrl", getLoginUrl());
            httpResponse.setHeader("sessionstatus", "sessionOut");  
            if(StringUtil.isNotNull(httpRequest.getParameter("callback"))&& StringUtil.isNotNull(httpRequest.getParameter("_"))){
            	//jsonP
            	out.println(httpRequest.getParameter("callback")+"("+JSONUtils.toJSONString(outPut)+")");
            }else{
            	//??????ajax
            	out.println(JSONUtils.toJSONString(outPut));
            }
            out.flush();
            out.close();
        }else{
        	//saveRequest(request, URLUtils.handleLoginUrl(httpRequest),null);
        	//redirectToLogin(request, response,URLUtils.handleLoginUrl(httpRequest));
        	URLUtils.saveRequestAndRedirectToLogin(httpRequest, httpResponse, this.getLoginUrl());
        }
        return false;
        
    }
    
    
  
    
}
