<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>访问异常</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
    <link rel="stylesheet" href="${staticSource}/static/css/index.css" />
</head>
<body class="container_bg">
<%@ include file="/common/include/commonHeader.jsp" %>
<div class="container error_main">
    <div class="md">
        <img class="error_img" src="${staticSource}/static/images/error.png">
        <p>您无法访问此页面！</p>
        <div style="margin: 0 1rem;">
            <a class="error_btn" href="${ctx}/logout">返回首页</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
<script type="text/javascript">
    var allHg=$(document).height();
    $(".error_main").css("padding-top",allHg/6);
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>
