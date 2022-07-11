<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>开门成功</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?36.1" />

</head>
<body>
<%@ include file="/common/include/commonHeader.jsp"%>
    <div class="container">
        <div class="container-c">
            <div class="buy-success md">
                <img class="buy-successicon" src="${staticSource}/static/images/buy-successicon.png" />
                <h4>${open.openHint}</h4>
                <p>请尽快完成补货，保持货柜整洁</p>
            </div>
            <img class="round-head m-t0" src="${staticSource}/static/images/round-head.png" >
            <div class="kaimen-bg md">
                <p class="opensuccess">商户名称：${merchantName}</p>
                <p class="opensuccess">设备编号：${deviceCode}</p>
                <p class="opensuccess">设备名称：${deviceName}</p>
                <p class="opensuccess">设备地址：${deviceAddress}</p>
                <p class="opensuccess opensuccess-c1">补货员姓名：${srealName}</p>
                <p class="opensuccess opensuccess-c1">补货时间：${openTime}</p>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
    <script type="text/javascript">
        $(function () {
            setTitle("开门成功");
            $('.container-c').css('height', $(window.top).height());
        });

        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>