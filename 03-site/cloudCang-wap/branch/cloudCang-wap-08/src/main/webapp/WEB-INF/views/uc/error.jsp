<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <c:if test="${empty title}">
        <title>你访问的页面出错了</title>
    </c:if>
    <c:if test="${not empty title}">
        <title>${title}</title>
    </c:if>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css"/>
</head>
<body class="container_bg">
<%@ include file="/common/include/commonHeader.jsp" %>
<div class="container error_main">
    <div class="md">
        <img class="error_img" src="${staticSource}/static/images/error.png">
        <p>${errorMsg}</p>
        <div style="margin: 0 1rem;">
            <c:if test="${not empty errorCode and  errorCode eq 10014}">
                <a class="error_btn" href="javascript:void(0);" onclick="jumpIndexMainAndLogOut();">返回首页</a>
            </c:if>
            <c:if test="${empty errorCode  or errorCode ne 10014}">
                <a class="error_btn" href="javascript:void(0);" onclick="jumpIndexMain();">返回首页</a>
            </c:if>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?2232333323"></script>
<script type="text/javascript">
    var allHg = $(document).height();
    $(".error_main").css("padding-top", allHg / 6);
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>
