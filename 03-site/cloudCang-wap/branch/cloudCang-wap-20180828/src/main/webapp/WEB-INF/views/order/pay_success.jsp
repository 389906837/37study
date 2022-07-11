<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>支付成功</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css">
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?21">
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp"%>
    <div class="container">
        <div class="container-c">
            <div class="md error-main buy-success">
                <img class="buy-successicon" src="${staticSource}/static/images/buy-successicon.png" />
                <h4>订单支付成功！订单编号：${orderCode}</h4>
                <a class="fanhui-btn" href="javascript:void(0);" onclick="jumpIndexMain();">返回首页</a>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
    <script type="text/javascript">
        $(function () {
            setTitle("订单支付成功");
            $('.container-c').css('height', $(window.top).height());
        });
        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>