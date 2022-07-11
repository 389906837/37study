<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="keywords" content="${article.skeys}" />
    <meta name="description" content="${article.snewsAbstract}" />
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
    <title>${article.stitle} - 江苏叁拾柒号仓智能科技有限公司</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/wap/css/common.css">
    <link rel="stylesheet" href="${staticSource}/static/wap/css/index.css">
    <script type="text/javascript" src="${staticSource}/static/wap/js/jquery.min.js"></script>
</head>
<body style="cursor:pointer;">
<%@ include file="/common/include/head.jsp" %>
<div class="content">
        <h4>${article.stitle}</h4>
        <p>${content.scontent}</p>
</div>
<%@ include file="/common/include/tail.jsp" %>
</body>
</html>
