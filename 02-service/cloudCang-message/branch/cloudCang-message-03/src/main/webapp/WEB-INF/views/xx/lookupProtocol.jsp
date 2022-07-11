<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
<c:when test="${empty htmlXY }">
没有找到协议
</c:when>
<c:otherwise>
${htmlXY }
</c:otherwise>
</c:choose>

