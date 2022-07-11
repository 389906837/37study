<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <title>授权页面</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?3.0">

</head>
<body class="authorize">
<%@ include file="/common/include/commonHeader.jsp"%>
<div class="container">
    <div class="buy-success md">
        <img src="${staticSource}/static/images/Authorization-icon.png" >
        <h4>申请授权对账号进行人脸注册</h4>
        <p>请确认为本人操作</p>
    </div>
    <div class="cells">
        <div class="cell">
            <div class="cell-hd"><label class="cell-label">授权账号</label></div>
            <div class="cell-bd"><input type="text" readonly= "true "  value="${userName}"></div>
        </div>
        <div class="cell">
            <div class="cell-hd"><label class="cell-label">操作时间</label></div>
            <div class="cell-bd"><input type="text" readonly= "true "  value="${time}"></div>
        </div>
        <div class="cell">
            <div class="cell-hd"><label class="cell-label">操作IP</label></div>
            <div class="cell-bd"><input type="text" readonly= "true "  value="${clientIp}"></div>
        </div>
        <div class="cell">
            <div class="cell-hd"><label class="cell-label">设备编号</label></div>
            <div class="cell-bd"><input type="text" readonly= "true "  value="${deviceCode}"></div>
        </div>

    </div>
    <div class="page-hd"></div>
    <div class="page__bd_spacing">
        <a href="${ctx}/index/authorizeRegisterSuccess?aiId=${aiId}&token=${token}&userId=${userId}&openSource=${openSource}" class="cell-btn cell-btn_primary">允许登录</a>
        <a href="${ctx}/index/authorizeRegisterFail?aiId=${aiId}&token=${token}" class="cell-btn cell-btn_plain-disabled">拒 绝</a>
    </div>

</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?2"></script>
<script src="${staticSource}/static/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script src="${staticSource}/static/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.converters.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/order-pay.js?4"></script>
<script type="text/javascript" >
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>