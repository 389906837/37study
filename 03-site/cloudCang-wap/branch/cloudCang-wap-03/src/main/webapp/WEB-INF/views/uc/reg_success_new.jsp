<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>注册成功</title>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css" />
</head>
<body>
<div class="reg_container">
    <div class="reg_success">
        <div class="success">
            <div></div>
        </div>
        <p>注册成功</p>
    </div>
    <c:if test="${fn:length(coupons) > 0 or integral>0}">
        <div class="buy-quan">
            <c:if test="${fn:length(coupons) > 0}">
                <div class="buy-quan-title">
                    <span>叮~有券到</span>
                </div>
                <c:forEach var="item" varStatus="L" items="${coupons}">
                    <c:forEach begin="1" end="${item.count}">
                        <div class="quan <c:if test="${item.couponType eq 10}">xianjin-quan</c:if>
                            <c:if test="${item.couponType eq 20}">manjian-quan</c:if>
                            <c:if test="${item.couponType eq 30}">dikou-quan</c:if>
                            <c:if test="${item.couponType eq 40}">mainfei-quan</c:if>">
                            <div class="quan-contant">
                                <div class="quan-value">
                                    <div class="lay of">
                                        <c:if test="${item.couponType ne 40}">
                                            ￥<em><f:formatNumber value="${item.couponValue}" pattern="#0.##"/> </em>
                                        </c:if>
                                        <c:if test="${item.couponType eq 40}">
                                            <em>免费</em>
                                        </c:if>
                                    </div>
                                    <div class="lay paddinglay">
                                        <p class="quan-name">${item.couponTypeName}</p>
                                        <p class="quan-use">${item.activityName}</p>
                                    </div>
                                </div>
                                <p class="quan-tips">${item.sbriefDesc}</p>
                            </div>
                        </div>
                    </c:forEach>
                </c:forEach>
            </c:if>
            <c:if test="${integral>0}">
                <div class="jifen-quan md">
                    <img class="jifen-icon" src="${staticSource}/static/images/jifen-icon.png">
                    +${integral}积分
                </div>
            </c:if>
        </div>
    </c:if>

    <%--<div class="reg_main md">
        <a class="reg_btn">开通免密支付</a>
        <div class="m-t-1" style="font-size: .9rem;">
            开通后可自动结算扣款体验无感消费。<a style="color:#3080e1;text-decoration: underline;">暂不开通</a>
        </div>
    </div>--%>
    <c:if test="${isupportPayWay eq 10}">
        <div class="reg_main md">
            <a class="reg_btn" href="${ctx}${signPage}">开通免密支付</a>
            <div class="m-t-1" style="font-size: .9rem;">
                开通后可自动结算扣款体验无感消费。<a href="${ctx}/index/main.html" style="color:#3080e1;text-decoration: underline;">暂不开通</a>
            </div>

        </div>
    </c:if>
    <c:if test="${isupportPayWay eq 20}">
        <a href="${ctx}${signPage}" class="reg_btn">开通免密支付</a>
    </c:if>
    <c:if test="${isupportPayWay eq 30}">
        <a href="${ctx}/index/main.html" class="reg_btn">前往体验</a>
    </c:if>

</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
<script type="text/javascript">
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>