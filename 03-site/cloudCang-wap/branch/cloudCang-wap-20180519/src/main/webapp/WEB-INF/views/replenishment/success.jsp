<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>补货成功</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css">
    <link rel="stylesheet" href="${staticSource}/static/css/style.css">
</head>
<body>
    <div class="container">
        <%@ include file="/common/include/commonHeader.jsp"%>
        <div class="buy-success md">
            <img class="buy-successicon" src="${staticSource}/static/images/buy-successicon.png" >
            <h4>已关门！请确认补货信息!</h4>
        </div>
        <p>补货单编号：${recordCode},补货单详情 未完成</p>
        <a class="fanhui-btn" href="javascript:void(0);" onclick="markingDone();">标记完成</a>
        <%@ include file="/common/include/foot.jsp"%>
    </div>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/socket.io.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/soketUtil.js"></script>
    <script type="text/javascript">
        function markingDone() {
            //标记完成
            Util.Alert("标记完成");
        }
        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>