<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<div class="order-titlebg" style="margin-top: 0rem;">
    <div class="order-title">
        <div<c:if test="${type eq 'All'}"> class="hover"</c:if>><a href="${ctx}/personal/orderAll.html">全部订单</a></div>
        <div<c:if test="${type eq 'Fail'}"> class="hover"</c:if>><a href="${ctx}/personal/orderFail.html">待付款订单</a></div>
        <div<c:if test="${type eq 'Success'}"> class="hover"</c:if>><a href="${ctx}/personal/orderSuccess.html">已完成订单</a></div>
        <div<c:if test="${type eq 'Refund'}"> class="hover"</c:if>><a href="${ctx}/personal/orderRefund.html">退款记录</a></div>
    </div>
</div>
