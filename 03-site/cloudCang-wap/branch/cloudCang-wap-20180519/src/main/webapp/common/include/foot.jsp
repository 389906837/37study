<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<div class="foot" >
    <div class="foot-row md">
        <a href="javascript:void(0);" onclick="jumpIndex();">
            <img src="${staticSource}/static/images/wallet.png" style="height:2.15rem;" >
            <p>首页</p>
        </a>
    </div>
    <div class="foot-row md">
        <a href="javascript:void(0);" onclick="myOrder();">
            <img src="${staticSource}/static/images/order.png" style="height:2.15rem;" >
            <p>订单</p>
        </a>
    </div>
    <div class="foot-row md">
        <a href="javascript:void(0);" onclick="myCoupon();">
            <img src="${staticSource}/static/images/coupons.png" style="height:2.3rem;" >
            <p>卡券</p>
        </a>
    </div>
    <div class="foot-row md">
        <a href="javascript:void(0);" onclick="mineInfo();">
            <img src="${staticSource}/static/images/my.png" style="height:2.2rem;" >
            <p>我的</p>
        </a>
    </div>
</div>

