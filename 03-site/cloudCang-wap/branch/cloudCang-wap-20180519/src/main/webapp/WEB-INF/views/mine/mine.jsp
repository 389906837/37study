<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>我的</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
<link rel="stylesheet" href="${staticSource}/static/css/style.css?3" />
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp"%>
<div class="my-contant">
    <div class="my-infor">
        <ul>
            <li>
                <img src="${staticSource}/static/images/iphone-nameicon.png" >
                <span>账户名称</span>
                <em>${user.snickName }</em>
            </li>
            <li>
                <img src="${staticSource}/static/images/iphone-numbericon.png" >
                <span>注册手机号</span>
                <em>${user.smobile }</em>
            </li>
        </ul>
    </div>
    <div class="my-infor">
        <ul>
            <li>
                <a href="javascript:void(0);" onclick="myCoupon();">
                    <img src="${staticSource}/static/images/bankcard-icon.png" >
                    <span>我的券包</span>
                    <em style="top:2.3rem;"><img class="my-arrowicon" src="${staticSource}/static/images/my-arrowicon.png" ></em>
                </a>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="myOrder();">
                    <img src="${staticSource}/static/images/real-nameicon.png" >
                    <span>我的订单</span>
                    <em style="top:2.3rem;"><img class="my-arrowicon" src="${staticSource}/static/images/my-arrowicon.png" ></em>
                </a>
            </li>
            <c:if test="${isReplenishment eq 1}">
                <li>
                    <a href="javascript:void(0);" onclick="myReplenishmentOrder();">
                        <img src="${staticSource}/static/images/setUp-icon.png" >
                        <span>我的补货单</span>
                        <em style="top:2.3rem;"><img class="my-arrowicon" src="${staticSource}/static/images/my-arrowicon.png" ></em>
                    </a>
                </li>
            </c:if>
        </ul>
    </div>
    <a href="javascript:void(0);" onclick="exitApp();" class="button-icon check-btn" style="width: 100%;margin: 0 0;">退出</a>
    <a href="javascript:void(0);" onclick="testBtn('/personal/wechatUnsign.html');" class="button-icon check-btn" style="width: 100%;margin: 0 0;">解约</a>
    <a href="javascript:void(0);" onclick="testBtn('/personal/wechatQuerySign.html');" class="button-icon check-btn" style="width: 100%;margin: 0 0;">查询协议</a>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?1"></script>
<script type="text/javascript">
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>