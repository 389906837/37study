<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<footer class="footer">
    <div class="tab">
        <a class="tab_a<c:if test="${param.selected eq 1 }"> active </c:if>" href="javascript:void(0);" onclick="jumpIndex();">
            <img src="${staticSource}/static/images/home_active.png" class="actived">
            <img src="${staticSource}/static/images/home.png" class="unactived">
            <p>首页</p>
        </a>
        <a class="tab_a<c:if test="${param.selected eq 2 }"> active</c:if>" href="javascript:void(0);" onclick="myOrder();">
            <img src="${staticSource}/static/images/order_active.png" class="actived">
            <img src="${staticSource}/static/images/order.png" class="unactived">
            <p>订单</p>
        </a>
        <a class="tab_a<c:if test="${param.selected eq 3 }"> active</c:if>" href="javascript:void(0);" onclick="myCoupon();">
            <img src="${staticSource}/static/images/coupon_active.png" class="actived">
            <img src="${staticSource}/static/images/coupon.png" class="unactived">
            <p>卡券</p>
        </a>
        <a class="tab_a<c:if test="${param.selected eq 4 }"> active</c:if>" href="javascript:void(0);" onclick="mineInfo();">
            <img src="${staticSource}/static/images/my_active.png" class="actived">
            <img src="${staticSource}/static/images/my.png" class="unactived">
            <p>我的</p>
        </a>
    </div>
</footer>


