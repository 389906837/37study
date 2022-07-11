<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Payment successful</title>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
    <link rel="stylesheet" href="${staticSource}/static/css/index.css" />
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp"%>
<div class="open_container">
    <div class="open_success">
        <div class="success">
            <div></div>
        </div>
        <p class="open_title">Payment successful</p>
        <p class="open_order">Order number：${orderCode}</p>
    </div>
    <div class="open_main md">
        <a href="javascript:void(0);" onclick="jumpIndexMain();"> Back to home </a>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script type="text/javascript">
    <%@ include file="/common/include/baidu.jsp" %>
</script>
</body>
</html>