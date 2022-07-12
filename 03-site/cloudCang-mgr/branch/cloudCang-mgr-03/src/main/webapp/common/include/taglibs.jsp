<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.cloud.cang.zookeeper.utils.EvnUtils"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="cang" uri="/WEB-INF/tld/37cang.tld"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	//request.setAttribute("staticSource", request.getContextPath());
	request.setAttribute("currentLanguage", LocaleContextHolder.getLocale().getLanguage());
	request.setAttribute("staticSource", EvnUtils.getValue("static.http.domain"));
	request.setAttribute("dynamicSource", EvnUtils.getValue("dynamic.http.domain"));
%>
