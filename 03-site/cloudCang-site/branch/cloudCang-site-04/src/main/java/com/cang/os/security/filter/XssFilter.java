package com.cang.os.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cang.os.security.utils.XssHttpServletRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Xss跨站脚本特殊字符过滤
 * @author sunql
 * @version 1.0
 */
@Component("xssFilter")
public class XssFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request) ;
		filterChain.doFilter(xssRequest, response) ;
	}

}
