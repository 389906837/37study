package com.cloud.cang.security.filter;

import com.cloud.cang.security.listener.LoginListener;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;

import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 权限过滤器
 *
 * @author zhouhong
 */
@Component("authFilter")
public class AuthFilter implements Filter {
    
    private final static Logger LOGGER=LoggerFactory.getLogger(LoginListener.class);
    
    public static final String NO_AUTH_URL="/customer/openFund";
    
	public void destroy() {
	}

    public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		AuthorizationVO authorizationVO= SessionUserUtils.getSessionAttributeForUserDtl();
        String currentURL = httpRequest.getRequestURI();
        String contextPath=httpRequest.getContextPath();
        if(StringUtils.isNotBlank(contextPath)){
            currentURL = httpRequest.getRequestURI().replaceFirst(contextPath, "");
        }
        String authUrl= EvnUtils.getValue("auth.url");
        if(authorizationVO!=null&&isAuthUrl(currentURL, authUrl)){
        	if (!authorizationVO.isAuth()) {
    			 LOGGER.debug("********用户没有开通托管账户，重定向到开通托管账户页面********");
                 
                 //用户登录以后需手动添加session
                 httpResponse.sendRedirect(EvnUtils.getValue("uic.http.domain")+NO_AUTH_URL);
                 return;
    	    }
        }
        
		chain.doFilter(request, response);
	}

    /**
     * 功能描述: <br>
     * 是否为需要权限验证的URL
     *
     * @param targetURL
     * @return
     */
    public static boolean isAuthUrl(String targetURL,String authUrl){
    	if(StringUtil.isNull(authUrl)){
    		return false;
    	}
        String[] authUrls=authUrl.split(",");
        for(int i=0;i<authUrls.length;i++){
            String url=authUrls[i].trim();
            if(StringUtil.isNull(url)){
            	return false;
            }
            
            //支持通配符
            PatternMatcher patternMatcher=new AntPathMatcher();
            if(patternMatcher.matches(url, targetURL)){
            	//地址是个人中心， 企业用户才要拦截
            	if(patternMatcher.matches("/hy/index**", targetURL)){
            		AuthorizationVO authorizationVO=SessionUserUtils.getSessionAttributeForUserDtl();
                    return authorizationVO.getMemberType() == 2;
            	}else{
            		//其他地址正常拦截
            		return true;
            	}
            }
        }
        return false;
    }
    
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
}
