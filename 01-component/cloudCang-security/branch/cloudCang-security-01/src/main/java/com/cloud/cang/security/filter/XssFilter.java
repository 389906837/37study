package com.cloud.cang.security.filter;

import com.cloud.cang.security.utils.XssHttpServletRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Xss跨站脚本特殊字符过滤
 * @author sunql
 * @version 1.0
 */
@Component("xssFilter")
public class XssFilter extends OncePerRequestFilter {

	static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"  
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|drop|execute|owa_util\\.signature|owa_util\\.listprint)\\b)";  
  
static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request) ;
		
		if(sqlPattern.matcher(xssRequest.getRequestURL()).find()){
			 response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			 request.getRequestDispatcher("/WEB-INF/views/error/500.jsp").forward(request,response);
			 return;
		}
		String queryString=xssRequest.getQueryString();
		if(queryString!=null){
			if(sqlPattern.matcher(queryString).find()){
				 response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				 request.getRequestDispatcher("/WEB-INF/views/error/500.jsp").forward(request,response);
				 return;
			}
		}
		filterChain.doFilter(xssRequest, response) ;
	}
	
	public static void main(String[] args) {
		String w="owa_util .signature";
		
		System.out.println(sqlPattern.matcher(w).find());
	}

}
