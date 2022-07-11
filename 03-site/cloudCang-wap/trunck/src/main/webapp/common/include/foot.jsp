<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<div class="foot" >
    <div class="foot-row md">
        <a href="javascript:void(0);" onclick="jumpIndex();">
            <c:if test="${param.selected eq 1 }">
                <img src="${staticSource}/static/images/wallet-yellow.png" style="height:2.15rem;" />
                <p class="active">首页</p>
            </c:if>
            <c:if test="${param.selected ne 1 }">
                <img src="${staticSource}/static/images/wallet.png" style="height:2.15rem;" />
                <p>首页</p>
            </c:if>
        </a>
    </div>
    <div class="foot-row md">
        <a href="javascript:void(0);" onclick="myOrder();">
            <c:if test="${param.selected eq 2 }">
                <img src="${staticSource}/static/images/order-yellow.png" style="height:2.15rem;" />
                <p class="active">订单</p>
            </c:if>
            <c:if test="${param.selected ne 2 }">
                <img src="${staticSource}/static/images/order.png" style="height:2.15rem;" />
                <p>订单</p>
            </c:if>
        </a>
    </div>
    <div class="foot-row md">
        <a href="javascript:void(0);" onclick="myCoupon();">
            <c:if test="${param.selected eq 3 }">
                <img src="${staticSource}/static/images/coupons-yellow.png" style="height:2.15rem;" />
                <p class="active">券包</p>
            </c:if>
            <c:if test="${param.selected ne 3 }">
                <img src="${staticSource}/static/images/coupons.png" style="height:2.15rem;" />
                <p>券包</p>
            </c:if>
        </a>
    </div>
    <div class="foot-row md">
        <a href="javascript:void(0);" onclick="mineInfo();">
            <c:if test="${param.selected eq 4 }">
                <img src="${staticSource}/static/images/my-yellow.png" style="height:2.2rem;" />
                <p class="active">我的</p>
            </c:if>
            <c:if test="${param.selected ne 4 }">
                <img src="${staticSource}/static/images/my.png" style="height:2.2rem;" />
                <p>我的</p>
            </c:if>
        </a>
    </div>
</div>

