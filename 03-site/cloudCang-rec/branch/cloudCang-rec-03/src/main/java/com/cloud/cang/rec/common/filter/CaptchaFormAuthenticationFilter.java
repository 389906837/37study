package com.cloud.cang.rec.common.filter;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.rec.sys.service.OperatorService;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.core.CaptchaUsernamePasswordToken;
import com.cloud.cang.security.exception.IncorrectCaptchaException;
import com.cloud.cang.security.utils.ClientSavedRequest;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
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
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 
 * 
 * 类功能描述: 实现Shiro的默认登录认证，增加了验证码功能
 * @author zhouhong
 * @version V1.0, 2014-4-17
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter
{
	@Autowired
    ICached iCached;
	
	@Autowired
    OperatorService operatorService;
    
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
    /* (non-Javadoc)
     * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#createToken(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    protected CaptchaUsernamePasswordToken createToken(ServletRequest request, ServletResponse response)
    {
        String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = SessionUserUtils.getIpAddr((HttpServletRequest)request);
        
        return new CaptchaUsernamePasswordToken(username, password, false, host, captcha,merchantCode);
    }
    
    /**
     * 验证码校验
     * 
     * @param request
     * @param token
     */
    protected void doCaptchaValidate(HttpServletRequest request, CaptchaUsernamePasswordToken token)
    {
    		String requestIp=SessionUserUtils.getIpAddr(request);
            String msgCode=request.getParameter("msgCode");
            //短信验证码
            Integer LOGIN_IP_STATUS=(Integer)request.getSession().getAttribute("LOGIN_IP_STATUS");
            String smobile=(String)request.getSession().getAttribute("LOGIN_SMOBILE");
            String key= RedisConst.MGR_LOGIN_CODE+requestIp+"_"+smobile+"_"+ DateUtils.getCurrentDTimeNumber();//验证码key
            if (LOGIN_IP_STATUS!=null){
	            if(3==LOGIN_IP_STATUS){
	            		String msgCodeServer;
						try {
							msgCodeServer =iCached.get(key)+"";
							request.getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);//移掉图形
						}catch (Exception e) {
							e.printStackTrace();
							 throw new IncorrectCaptchaException("系统错误!");
						}
	        			if(msgCodeServer==null){
	        				 throw new IncorrectCaptchaException("短信验证码已过期!");
	        			}
	        			if(!msgCodeServer.equals(msgCode)){
	        				throw new IncorrectCaptchaException("短信验证码不正确!");
	        			}
	                    try {
							iCached.remove(key);
						} catch (Exception e) {
							e.printStackTrace();
						}
	        			
	            } else if (1==LOGIN_IP_STATUS){//白名单只要图片验证码
	                String captcha =(String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
                    if ( captcha == null || !captcha.equalsIgnoreCase(token.getCaptcha())) {
                        throw new IncorrectCaptchaException("图形验证码错误！");
                    }
                }else{
                    throw new IncorrectCaptchaException("图形验证码错误或已过期！");
                }
            } else {
            	throw new IncorrectCaptchaException("图形验证码错误或已过期！");
            }
            
            
    }
    
    public boolean isValidateCaptchaImage(HttpServletRequest request)
    {
        int errorTimes = 3;// 设置默认值
        Integer count = (Integer)request.getSession().getAttribute(SESSION_LOGIN_ERROR_COUNT);
        if (count == null)
            count = 0;
        return count.intValue() >= errorTimes;
    }
    
    /**
     * 
     * 执行登录认证
     */
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        CaptchaUsernamePasswordToken token = createToken(request, response);
        try {
            //检查用户是不是被禁用
             Operator operator = operatorService.selectByUsernameAndMerchant(token.getUsername(), (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE));
             if (null == operator) {
                 throw new IncorrectCaptchaException("系统用户不存在!");
             }
            if(operator.getBisFreeze() == 0 || operator.getBisDelete()==1){
                //禁用
                throw new IncorrectCaptchaException("该用户已禁用或删除!");
            }

            doCaptchaValidate((HttpServletRequest)request, token);
            Subject subject = getSubject(request, response);
            token.setRememberMe(this.isRememberMe(request));  
            subject.login(token);
            
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
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
        redirectToSavedRequest(request, response, fallbackUrl);
    }
    
    /**
     * Redirects the to the request url from a previously
     * {@link #saveRequest(ServletRequest) saved} request, or if there is no saved request, redirects the
     * end user to the specified {@code fallbackUrl}.  If there is no saved request or fallback url, this method
     * throws an {@link IllegalStateException}.
     * <p/>
     * This method is primarily used to support a common login scenario - if an unauthenticated user accesses a
     * page that requires authentication, it is expected that request is
     * {@link #saveRequest(ServletRequest) saved} first and then redirected to the login page. Then,
     * after a successful login, this method can be called to redirect them back to their originally requested URL, a
     * nice usability feature.
     *
     * @param request     the incoming request
     * @param response    the outgoing response
     * @param fallbackUrl the fallback url to redirect to if there is no saved request available.
     * @throws IllegalStateException if there is no saved request and the {@code fallbackUrl} is {@code null}.
     * @throws IOException           if there is an error redirecting
     * @since 1.0
     */
    public static void redirectToSavedRequest(ServletRequest request, ServletResponse response, String fallbackUrl)
            throws IOException {
        String successUrl = null;
        boolean contextRelative = true;
        /* SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
            successUrl = savedRequest.getRequestUrl();
            contextRelative = false;
        }*/

       /* if (successUrl == null) {
            successUrl = fallbackUrl;
        }*/
        
        if (successUrl == null) {
            successUrl = "/rec/main";
        }

        if (successUrl == null) {
            throw new IllegalStateException("Success URL not available via saved request or via the " +
                    "successUrlFallback method parameter. One of these must be non-null for " +
                    "issueSuccessRedirect() to work.");
        }

        WebUtils.issueRedirect(request, response, successUrl, null, contextRelative);
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
        
        String exceptionClassName = e.getClass().getSimpleName();
        
        String message="";
        if(exceptionClassName==null){
        	message= "用户名或密码错误！";
        }
        //UsernamePasswordToken.class
        if (exceptionClassName.indexOf("IncorrectCredentialsException")!=-1) {
        	message= "用户名或密码错误！";
        }  else if (exceptionClassName.indexOf("LockedAccountException")!=-1) {
        	message= "账号被锁定！";
        }  else if (exceptionClassName.indexOf("LockedAccountException")!=-1) {
        	message= "用户名或密码错误！";
        } else if (exceptionClassName.indexOf("IncorrectCaptchaException")!=-1) {
        	message= e.getMessage();
        } else {
        	message= "用户名或密码错误！";
        }
        
        if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest)request).getHeader("X-Requested-With")))
        {// 不是ajax请求
            setFailureAttribute(request,  message);
            return true;
        }
        try
        {
            HttpServletRequest httprequest = (HttpServletRequest)request;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

          	out.println("{\"success\":false,\"message\":\"" + message
                    + "\",\"errortimes\":" + httprequest.getSession().getAttribute(SESSION_LOGIN_ERROR_COUNT)
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