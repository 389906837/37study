package com.cloud.cang.mgr.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class SessionTimeOutFilter implements Filter {

	public SessionTimeOutFilter() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain arg2) throws IOException,
			ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		if (!servletRequest.getServletPath().equalsIgnoreCase("/mgr/login")){
			if (servletRequest.getHeader("x-requested-with") != null
					&& servletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				 Subject subject =SecurityUtils.getSubject();
		            // If principal is not null, then the user is known and should be allowed access.
		            if(subject.getPrincipal() != null)
		            	servletResponse.addHeader("sessionstatus", "timeout");
			}
		}
		arg2.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
