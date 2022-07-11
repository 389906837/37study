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
        <a class="fanhui-btn" href="javascript:void(0);" onclick="alipayExitApp();">关闭</a>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
<c:if test="${alipay eq 1}">
    <script type="text/javascript" src="${staticSource}/static/js/alipayjsapi.min.js"></script>
</c:if>
<c:if test="${wechat eq 1}">
    <script type="text/javascript" src="${staticSource}/static/js/jweixin-1.2.0.js"></script>
</c:if>
<script type="text/javascript">
    $(function () {
        var allHg = $(document).height();
        $(".error-main").css("padding-top", allHg / 4.5);
    });
    <c:if test="${wechat eq 1}">
    function wechatExitApp() {
        window.location.href = ctxRoot + "/logout";
        wx.config(${jsonStr});
        wx.error(function (res) {
            Util.Alert(res.errMsg);
        });
        wx.ready(function () {
            wx.closeWindow();
        });
    }
    </c:if>
    <c:if test="${alipay eq 1}">
    function alipayReady(callback) {
        if (window.AlipayJSBridge) {
            callback && callback();
        } else {
            // 如果没有注入则监听注入的事件
            document.addEventListener('AlipayJSBridgeReady', callback, false);
        }
    }
    function alipayCallback() {
        document.addEventListener('resume', function (event) {
            event.preventDefault();
            alipayReady(alipayCallback);
        });
    }
    alipayReady(alipayCallback);

    function alipayExitApp() {
        window.location.href = ctxRoot + "/logout";
        AlipayJSBridge.call('exitApp');
    }
    </c:if>
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>