package com.cang.os.security.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cang.os.security.core.CaptchaUsernamePasswordToken;
import com.cang.os.security.exception.IncorrectCaptchaException;
import com.cang.os.security.utils.ClientSavedRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cang.os.security.exception.ShiroExceptionUtils;
import com.cang.os.security.utils.SessionUserUtils;

/**
 * 
 * 
 * 类功能描述: 实现Shiro的默认登录认证，增加了验证码功能
 * 
 * @author terryLi
 * @version V1.0, 2014-4-17
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter
{
    
    private static final Logger log = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);
    
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";//验证码控件名
    
    public static String SESSION_LOGIN_ERROR_COUNT = "LOGIN_ERROR_COUNT";
    
    
    /**
     * 验证码名称
     */
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    
    public String getCaptchaParam()
    {
        return captchaParam;
    }
    
    public void setCaptchaParam(String captchaParam)
    {
        this.captchaParam = captchaParam;
    }
    
    protected String getCaptcha(ServletRequest request)
    {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }
    // 创建 Token
    protected CaptchaUsernamePasswordToken createToken(ServletRequest request, ServletResponse response)
    {
        
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host =SessionUserUtils.getIpAddr((HttpServletRequest)request);
        return new CaptchaUsernamePasswordToken(username, password, rememberMe, host, captcha);
    }
    
    /**
     * 验证码校验
     * 
     * @param request
     * @param token
     */
    protected void doCaptchaValidate(HttpServletRequest request, CaptchaUsernamePasswordToken token)
    {
        
        String captcha =
            (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //if (isValidateCaptchaImage(request))
            if (captcha == null || !captcha.equalsIgnoreCase(token.getCaptcha()))
            {
                throw new IncorrectCaptchaException("验证码错误！");
            }
    }
    
    public boolean isValidateCaptchaImage(HttpServletRequest request)
    {
        int errorTimes = 3;// 设置默认值
        Integer count = (Integer)request.getSession().getAttribute(SESSION_LOGIN_ERROR_COUNT);
        if (count == null)
            count = 0;
        if (count.intValue() >= errorTimes)
            return true;
        return false;
    }

    /**
     * 
     * 执行登录认证
     */
    protected boolean executeLogin(ServletRequest request, ServletResponse response)
        throws Exception
    {
        CaptchaUsernamePasswordToken token = createToken(request, response);
        
        try
        {
            doCaptchaValidate((HttpServletRequest)request, token);
            
            Subject subject = getSubject(request, response);
            token.setRememberMe(this.isRememberMe(request));  
            subject.login(token);
            
            return onLoginSuccess(token, subject, request, response);
        }
        catch (AuthenticationException e)
        {
            return onLoginFailure(token, e, request, response);
        }
    }
    
    /**
     * 记录登录次数
     */
    public void addLoginErrorCountForSession(HttpServletRequest request)
    {
        Integer count = (Integer)request.getSession().getAttribute(SESSION_LOGIN_ERROR_COUNT);
        if (count == null)
        {
            count = new Integer(1);
        }
        else
        {
            count = new Integer(count.intValue() + 1);
        }
        request.getSession().setAttribute(SESSION_LOGIN_ERROR_COUNT, count);
    }
    
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        String fallbackUrl = (String) getSubject(request, response)
                .getSession().getAttribute("authc.fallbackUrl");
        if(StringUtils.isEmpty(fallbackUrl)) {
            fallbackUrl = getSuccessUrl();
        }
        WebUtils.redirectToSavedRequest(request, response, fallbackUrl);
    }
    
    /*
     * 
     * 主要是针对登入成功的处理方法。对于请求头是AJAX的之间返回JSON字符串。
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
        ServletResponse response)
        throws Exception
    {
        initRoleAndPurview();
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletRequest.getSession().setAttribute(SESSION_LOGIN_ERROR_COUNT, null);
        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")))
        {// 不是ajax请求
            issueSuccessRedirect(request, response);
        }
        else
        {
            httpServletResponse.setContentType("text/html;charset=UTF-8");
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = httpServletResponse.getWriter();
            out.println("{\"success\":true,\"message\":\"登入成功\"}");
            out.flush();
            out.close();
        }
        return false;
    }
    
    public void initRoleAndPurview(){
        SessionUserUtils.hasRole("");//登录成功后，立即加载权限 
    }
    
    protected void setFailureAttribute(ServletRequest request, String msg) {
        request.setAttribute(getFailureKeyAttribute(), msg);
    }
    /**
     * 主要是处理登入失败的方法
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
        ServletResponse response)
    {
        addLoginErrorCountForSession((HttpServletRequest)request);
        if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest)request).getHeader("X-Requested-With")))
        {// 不是ajax请求
            setFailureAttribute(request,  ShiroExceptionUtils.getExceptionCString(e.getClass().getSimpleName()));
            return true;
        }
        try
        {
            HttpServletRequest httprequest = (HttpServletRequest)request;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String message = e.getClass().getSimpleName();
            out.println("{\"success\":false,\"message\":\"" + ShiroExceptionUtils.getExceptionCString(message)
                + "\",\"errortimes\":" + (Integer)httprequest.getSession().getAttribute(SESSION_LOGIN_ERROR_COUNT)
                + "}");
            out.flush();
            out.close();
        }
        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return false;
    }
    
  /*  @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String backUrl = request.getParameter("backUrl");
        saveRequest(request, backUrl, getDefaultBackUrl(WebUtils.toHttp(request)));
        redirectToLogin(request, response);
        return false;
    }
    */
    /**
     * 所有请求都会经过的方法。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
        throws Exception
    {
        
        if (isLoginRequest(request, response))
        {
            
            if (isLoginSubmission(request, response))
            {
                if (log.isTraceEnabled())
                {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            }
            else
            {
                if (log.isTraceEnabled())
                {
                    log.trace("Login page view.");
                }
                return true;
            }
        }
        else
        {
            if (log.isTraceEnabled())
            {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the "
                    + "Authentication url [" + getLoginUrl() + "]");
            }
            if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest)request).getHeader("X-Requested-With")))
            {// 不是ajax请求
               // saveRequestAndRedirectToLogin(request, response);
            	
            	String backurl=(String)SessionUserUtils.getSessionAttribute("authc.fallbackUrl");
            	String backurlFromParam= request.getParameter("backUrl");
            	if(backurl==null 
            			&& SessionUserUtils.getSessionAttribute(WebUtils.SAVED_REQUEST_KEY)==null 
            			&& backurlFromParam==null){
            		saveRequest(request, backurlFromParam, getDefaultBackUrl(WebUtils.toHttp(request)));
                    redirectToLogin(request, response);
            	}
            }
            else
            {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("{message:'login'}");
                out.flush();
                out.close();
            }
            return false;
        }
    }
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        return subject.isAuthenticated();
    }

    
    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (isLoginRequest(request, response) && isAccessAllowed(request, response, null)){
            String fallbackUrl = getSuccessUrl();
            System.out.println("用户重新转到登录页，但实际已经登录了"+fallbackUrl);
            WebUtils.redirectToSavedRequest(request, response, fallbackUrl);
            return;
        }else{
            String backurlFromParam= request.getParameter("backUrl");
            if(StringUtils.isNotBlank(backurlFromParam)){
                saveRequest(request, backurlFromParam, getDefaultBackUrl(WebUtils.toHttp(request)));
                redirectToLogin(request, response);
            }
        }
        super.doFilterInternal(request, response, chain);
    }

    protected void saveRequest(ServletRequest request, String backUrl, String fallbackUrl) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        session.setAttribute("authc.fallbackUrl", fallbackUrl);
        SavedRequest savedRequest = new ClientSavedRequest(httpRequest, backUrl);
        session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);
    }
    private String getDefaultBackUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String domain = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuilder backUrl = new StringBuilder(scheme);
        backUrl.append("://");
        backUrl.append(domain);
        if("http".equalsIgnoreCase(scheme) && port != 80) {
            backUrl.append(":").append(String.valueOf(port));
        } else if("https".equalsIgnoreCase(scheme) && port != 443) {
            backUrl.append(":").append(String.valueOf(port));
        }
        backUrl.append(contextPath);
        backUrl.append(getSuccessUrl());
        return backUrl.toString();
    }
    
}