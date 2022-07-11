<%@page import="com.cloud.cang.zookeeper.utils.EvnUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="format-detection" content="telephone=no" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta name="wap-font-scale" content="no">
<meta http-equiv="Pragma" content="no-cache">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="${staticSource}/static/css/comm.css?version=3" rel="stylesheet" type="text/css">
<script src="${staticSource}/static/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var staticPathRoot = "${staticSource }";
	var ctxRoot = "${ctx }";
	var djstime=3;
	var msgtime=90;
	var timer;
	var validCode = true;
	var regPhone=/^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]19[0-9])\d{8}$/;//手机号码
	var regPwd=/^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{6,20}$/;//登录密码
</script>
