<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>操作出错，请点击关闭按钮退出</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
    <link rel="stylesheet" href="${staticSource}/static/css/style.css" />
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp" %>
<div class="container">
    <div class="md error-main">
        <img class="pic404" src="${staticSource}/static/images/error.png">
        <p>${errorMsg }</p>
        <a class="fanhui-btn" href="javascript:void(0);" onclick="exitApp();">关闭</a>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
<c:if test="${alipay eq 1}">
    <%@ include file="/common/include/alipayInit.jsp" %>
</c:if>
<c:if test="${wechat eq 1}">
    <%@ include file="/common/include/wechatInit.jsp" %>
</c:if>
<script type="text/javascript">
    $(function () {
        var allHg = $(document).height();
        $(".error-main").css("padding-top", allHg / 4.5);
    });
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>