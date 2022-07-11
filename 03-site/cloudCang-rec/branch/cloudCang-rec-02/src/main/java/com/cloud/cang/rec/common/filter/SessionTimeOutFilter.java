package com.cloud.cang.rec.common.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
		if (!servletRequest.getServletPath().equalsIgnoreCase("/rec/login")){
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
