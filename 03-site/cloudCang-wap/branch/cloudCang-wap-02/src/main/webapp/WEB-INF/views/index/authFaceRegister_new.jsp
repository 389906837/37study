<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>人脸登录授权</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">

</head>
<body class="authorize_bg">
<div class="container">
    <div class="authorize md">
        <img src="${staticSource}/static/images/Authorization_icon.png">
        <h4>申请授权人脸登录</h4>
        <p>请确认为本人操作</p>
    </div>
    <div class="cells">
        <div class="cell">
            <div class="cell-hd"><label class="cell-label">授权账号</label></div>
            <div class="cell-bd">${userName}</div>
        </div>
        <div class="cell">
            <div class="cell-hd"><label class="cell-label">操作时间</label></div>
            <div class="cell-bd">${time}</div>
        </div>
        <div class="cell">
            <div class="cell-hd"><label class="cell-label">操作IP</label></div>
            <div class="cell-bd">${clientIp}</div>
        </div>
        <div class="cell">
            <div class="cell-hd"><label class="cell-label">设备名称</label></div>
            <div class="cell-bd">${deviceName}</div>
        </div>

    </div>
    <div class="page-hd"></div>
    <div class="page__bd_spacing">
        <a href="${ctx}/index/authorizeRegisterSuccess?aiId=${aiId}&token=${token}&userId=${userId}&openSource=${openSource}"
           class="cell-btn cell-btn_primary">允许登录</a>
        <a href="${ctx}/index/authorizeRegisterFail?aiId=${aiId}&token=${token}"
           class="cell-btn cell-btn_plain-disabled">拒 绝</a>
    </div>

</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript">
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>