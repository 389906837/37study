package com.cloud.cang.wap.common.filter;

import com.cloud.cang.wap.common.utils.EnviornConfig;
import com.cloud.cang.wap.common.utils.WapUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cloud.cang.utils.SpringContext;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.ui.ModelMap;


/**
 * 所有请求拦截
 * 判断是否是正确的浏览器打开
 */
public class RequestFilter implements Filter {
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		 HttpServletRequest httpRequest = (HttpServletRequest) request;
		String currentUrl = httpRequest.getServletPath();
		if (!WapUtils.isWXRequest(httpRequest) && !WapUtils.isAlipayRequest(httpRequest)) {//不是微信浏览器 不是支付宝浏览器
			//并且不是静态资源文件
			if(currentUrl.indexOf("/uc/error") == -1 && currentUrl.indexOf("/static/") == -1 &&
					currentUrl.indexOf("/error/") == -1 && currentUrl.indexOf("/favicon.ico") == -1) {
				if(SpringContext.getBean(EnviornConfig.class).isProduct()) {
					String url = EvnUtils.getValue("wap.http.domain")+"/uc/error";
					ModelMap map = new ModelMap();
					map.put("currentUrl",  EvnUtils.getValue("wap.http.domain")+currentUrl);
					WebUtils.issueRedirect(request, response, url, map);
					return;
				}
			}
		}
		filterChain.doFilter(request, response);  
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
}
