package com.cang.os.security.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Set;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cang.os.security.vo.AuthorizationVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.cang.os.security.utils.SessionUserUtils;


/**
 * 访问日志过滤器实现
 */
@Component("accessLogFilter")
public class AccessLogFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(AccessLogFilter.class);

    private static final String STR_IP = "ip";

    private static final String STR_USER = "userCode";

    private static final String STR_SESSION_ID = "sessionId";

    private static final String STR_INVOKENO = "invokeNo";

    private static final String MIDDLE_LINE = "-";

    private static final String BLANK = "";

    private static final String STR_EQ = "=";

    private static final String STR_AND = "&";
    
    // 截取参数的最大长度
    protected int maxLength = 20;

    // 不允许记录的action参数列表
    protected Set<String> excludeParams;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();
      
        
        // 获取用户登录IP
        String ip = getIPAddress(request);

        // 向MDC里面set ip、user
        String sessionId=SessionUserUtils.getShiroSessionId();
        String userName="";
        MDC.put(STR_IP, ip);
        MDC.put(STR_SESSION_ID,sessionId );
     // 调用流水号
        MDC.put(STR_INVOKENO, UUID.randomUUID().toString().replace(MIDDLE_LINE, BLANK));
        
        AuthorizationVO authorizationVO=SessionUserUtils.getSessionAttributeForUserDtl();
        if(authorizationVO!=null){
        	userName= authorizationVO.getUserName();
        	MDC.put(STR_USER,userName);
        }
        // 取parameters
        Enumeration parameters = request.getParameterNames();
        // 计算action method执行方法
        long startTime = System.currentTimeMillis();
        long executionTime = 0L;

        // 拼接LOG信息
        StringBuilder message = new StringBuilder(500);
        try {// 调用用户访问的CONTROLLER
        	if(request.getParameter("q")!=null){
            	request.getSession().setAttribute(SessionUserUtils.SESSION_KEY_SOURCE,request.getParameter("q"));
        	}
        	
            chain.doFilter(request, response);
            executionTime = System.currentTimeMillis() - startTime;
        } finally {
            message.append("Controller:");
            message.append(path);
            message.append("|Params:");
            StringBuilder params = new StringBuilder();
            while (parameters.hasMoreElements()) {
                String param = (String) parameters.nextElement();
                String value = request.getParameter(param);
                params.append(param);
                params.append(STR_EQ);
                params.append(StringUtils.substring(value, 0,  this.maxLength));
                params.append(STR_AND);
            }
            if (params.toString().length() > 0) {
                message.append(params.toString().substring(0, params.toString().length() - 1));
            }
            message.append("|ip:").append(ip);
            message.append("|sessionId:").append(sessionId);
            message.append("|user-agent:").append(request.getHeader("user-agent"));
            message.append("|userName:").append(userName);
            message.append("|referer:").append(request.getHeader("referer"));
            message.append("|cookie:").append(getCookies(request));
            message.append("|Spend:").append(executionTime);
            
            // 记录日志
            LOG.info(message.toString());

            // 清除MDC里面的历史信息
            MDC.remove(STR_IP);
            MDC.remove(STR_USER);
            MDC.remove(STR_SESSION_ID);
            MDC.remove(STR_INVOKENO);
            
            if(request.getParameter("q")!=null){
               
        	}
        }

    }
    private String getCookies(HttpServletRequest request){
    	Cookie[] cookies = request.getCookies();
    	String kv="";
    	if(cookies!=null)
    	{
    	 for(Cookie cookie : cookies){
    		 kv+=cookie.getName()+STR_EQ;
    		 kv+=cookie.getValue()+STR_AND;
    	 }
    	 if (kv.toString().length() > 0) {
    		 kv=kv.substring(0,kv.length()-1);
         }
    	}
    	return kv;
    }
    /**
     * 获取客户端IP地址
     * 
     * @param request
     * @return
     */
    private static String getIPAddress(HttpServletRequest request) {
        String ip = null;
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
    }

    /**
     * @return the maxLength
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

}
