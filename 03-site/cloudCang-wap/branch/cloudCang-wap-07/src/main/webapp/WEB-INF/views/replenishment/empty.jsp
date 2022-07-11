<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>关门成功</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css">
    <link rel="stylesheet" href="${staticSource}/static/css/index.css?21">
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp"%>
<div class="open_container">
    <div class="open_success">
        <div class="success">
            <div></div>
        </div>
        <p class="open_title">补货关门成功！</p>
        <p class="open_desc"><i style="color:red;" id="secondHtml">3</i>秒后跳转......</p>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script type="text/javascript">
    var timer;
    var djsTime = 3;
    $(function () {
        timer = setInterval('showTime()',1000);
    });
    function showTime() {
        djsTime = djsTime - 1;
        if(djsTime == 0) {
            jumpReplenishmentIndex();
            clearInterval(timer);
            return;
        }
        $("#secondHtml").html(djsTime);
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>