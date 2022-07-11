<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>购物车列表页</title>
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css">
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?21">
</head>
<body class="cart_bg">
<div class="cart_container">
    <div class="cartlist">
        <ul class="cart_item" id="cart">
           <%-- <c:forEach items="${shoppingCartDoamin.commodityInfoList}" var="item" varStatus="L">
                <li id="goodsTemplate">
                    <img class="cart_goods" src="${dynamicSource}${item.scommodityImg}">
                    <div class="cart_info">
                        <p class="cart_info_title">${item.scommodityFullName}</p>
                        <p class="cart_info_num">数量：${item.number}</p>
                        <p class="cart_info_price">￥${item.fcommodityPrice}</p>
                    </div>
                        &lt;%&ndash;<div class="cart_tag cart_tag_mj"><img src="images/cart_mj.png"><span> 满减</span></div>&ndash;%&gt;
                </li>
            </c:forEach>--%>
        </ul>
    </div>

    <div class="cart_fixed">
        <span>总数量：<i id="totalNum"><${shoppingCartDoamin.totalNum}</i></span>&nbsp;
        <span>优惠金额：￥<i id="discountedPrice">${shoppingCartDoamin.discountedPrice}</i></span>
        <span class="cart_fixed_money"><em>实付金额：</em>￥<i id="actuallyPaid">${shoppingCartDoamin.actuallyPaid}</i></span>
    </div>
</div>
<!--引入js-->
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script src="${staticSource}/static/js/jsrender.min.js"  type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.converters.js"  type="text/javascript"></script>
<script id="shopping_tmpl" type="text/x-jsrender">
    {{for #commodityInfoList}}
         <li id="goodsTemplate">
            <img class="cart_goods" src="${dynamicSource}{{:scommodityImg}}">
            <div class="cart_info">
                <p class="cart_info_title">{{:scommodityFullName}}</p>
                <p class="cart_info_num">数量：{{:number}</p>
                <p class="cart_info_price">￥{{:fcommodityPrice}</p>
            </div>
                &lt;%&ndash;<div class="cart_tag cart_tag_mj"><img src="images/cart_mj.png"><span> 满减</span></div>&ndash;%&gt;
        </li>
    {{/for}}
</script>

<script type="text/javascript">

    function showCart(msg) {
        var data = msg.commodityInfoList;
        if("" != data) {
            var html = $("#shopping_tmpl").render(data);
            $("#cart").html(html);
        }
        $("#totalNum").html(msg.totalNum);
        $("#discountedPrice").html(msg.discountedPrice);
        $("#actuallyPaid").html(msg.actuallyPaid);
    }
    function hideCart() {
        $("#totalNum").html("0");
        $("#discountedPrice").html("0");
        $("#actuallyPaid").html("0");
        $("#cart").html("");
    }
</script>
<%--<script>--%>
    <%--function addToCart(goods) {--%>
        <%--$('<div></div>').append($("#goodsTemplate").prop("outerHTML"))--%>
            <%--.css({ height: 0, opacity: 0, })--%>
            <%--.animate({ height: '7.57rem' }, 400)--%>
            <%--.animate({ opacity: 1 }, 400)--%>
            <%--.prependTo('#cart');--%>
    <%--}--%>
    <%--$(function() {--%>
        <%--var i = 3;--%>
        <%--function mock() {--%>
            <%--addToCart();--%>
            <%--if(i>0){--%>
                <%--setTimeout(mock, parseInt(2000 * Math.random()) + 800);--%>
            <%--}--%>
            <%--i--;--%>
        <%--}--%>
        <%--mock()--%>
    <%--});--%>

<%--</script>--%>

</body>
</html>