<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>购买成功</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css">
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">
</head>
<body>
<div class="container">
    <div class="buy_success">
        <div class="success">
            <div></div>
        </div>
        <p>购买成功</p>
        <p style="color: #6d6d6d;font-size: 1rem;padding-top: 2rem;"><c:if
                test="${ichargebackWay eq 10}">您的订单会自动扣款,</c:if><c:if test="${ichargebackWay ne 10}">您的订单需手动付款,</c:if><a
                href="javascript:void(0);" onclick="myOrder()" class="a-code" style="color:#3080e1;">点击订单查看详情</a></p>
        <p style="color: #6d6d6d;font-size: 1rem;">如有任何问题请联系客服</p>
    </div>
    <c:if test="${fn:length(coupons) > 0 or integral>0}">
        <c:if test="${fn:length(coupons) > 0}">
            <div class="buy_havecoupon">
                <p class="buy_line"></p>
                <p class="buy_linetext">叮~有券到</p>
            </div>
            <div class="buy_main  <c:if test="${couponNum ==1}">buy_mainc</c:if>
            <c:if test="${couponNum !=1}">
                    <c:if test="${couponNum %2== 0}"> buy_mainc </c:if>
                    <c:if test="${couponNum %2!= 0}"> buy_mainl</c:if>
            </c:if> ">
                <c:forEach var="item" varStatus="L" items="${coupons}">
                    <c:forEach begin="1" end="${item.count}">

                        <div class="buy_coupon <c:if test="${item.couponType eq 10}">buy_xj</c:if>
                                <c:if test="${item.couponType eq 20}">buy_mj</c:if>
                                <c:if test="${item.couponType eq 30}">buy_dk</c:if>
                                <c:if test="${item.couponType eq 40}">buy_sp</c:if> ">
                            <p class="buy_couponlogo"><i> <c:if test="${item.couponType ne 40}">
                                ￥<em><f:formatNumber value="${item.couponValue}" pattern="#0.##"/> </em>
                            </c:if>
                                <c:if test="${item.couponType eq 40}">
                                    <em>免费</em>
                                </c:if></i> <span><c:if test="${item.couponType eq 10}">现金券</c:if>
                                <c:if test="${item.couponType eq 20}">满减券</c:if>
                                <c:if test="${item.couponType eq 30}">抵扣券</c:if>
                                <c:if test="${item.couponType eq 40}">商品券</c:if></span></p>
                            <p>${item.sbriefDesc}</p>
                            <p class="buy_date"><fmt:formatDate value="${item.effectiveDate}" pattern="yy/MM/dd"/> -
                                <fmt:formatDate value="${item.expiryDate}" pattern="yy/MM/dd"/></p>
                        </div>
                    </c:forEach>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${integral>0}">
            <div class="jifen-quan md">
                <img class="jifen-icon" src="${staticSource}/static/images/jifen-icon.png">
                +${integral}积分
            </div>
        </c:if>
    </c:if>
    <%-- <%@ include file="/common/include/foot.jsp" %>--%>

</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script type="text/javascript">
    $(function () {
        //  setTitle("购买成功");
        // $('.container-c').css('height', $(window.top).height());
    });
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>