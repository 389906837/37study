<%@ page import="java.util.Date" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="footer">
    <div class="pull-right">&copy; 2017-<fmt:formatDate value="<%=new Date()%>" pattern="yyyy"/>
        <a href="http://www.37cang.com" target="_blank"><spring:message code="main.company.name" /></a>
    </div>
</div>
