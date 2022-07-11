<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>系统崩溃了</title>
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
    <div class="container-c">
        <div class="md error-main">
            <img class="pic500" src="${staticSource}/static/images/pic500.png">
            <p>哎呀，服务器出错了，刷新一下试试</p>
            <a class="fanhui-btn" href="javascript:void(0);" onclick="jumpIndexMain();">返回首页</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
<script type="text/javascript">
    $(function () {
        $('.container-c').css('height', $(window.top).height());
        var allHg=$(document).height();
        $(".error-main").css("padding-top",allHg/4.5);
    });
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>