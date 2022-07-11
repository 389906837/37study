<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<c:choose>
<c:when test="${empty htmlXY }">
没有找到协议
</c:when>
<c:otherwise>
${htmlXY }
</c:otherwise>
</c:choose>
