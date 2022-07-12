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
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?33">
</head>
<body class="cart_bg">
<div class="cart_container">
    <div class="cartlist">
        <ul class="cart_item" id="cart_list">
        </ul>
        <div class="empty_cart">
            <img src="${staticSource}/static/images/cart_empty.png">
            <p>您的购物车空空如也</p>
        </div>
    </div>

    <div class="cart_fixed">
        <p>
            <span>总数量：<i id="totalNum">0</i></span>&nbsp;
            <span>优惠金额：￥<i id="discountedPrice">0.00</i></span>
        </p>
        <p><span class="cart_fixed_money">实付金额：<em>￥</em><i id="actuallyPaid">0.00</i></span></p>
    </div>
</div>

<script id="shopping_tmpl" type="text/x-jsrender">
    {{for #data}}
         <li>
            <div class="cart_pic">
                <img class="cart_goods" src="${dynamicSource}{{:scommodityImg}}" />
            </div>
            <div class="cart_info">
                <p class="cart_info_title">{{:scommodityFullName}}</p>
                <p class="cart_info_num">数量：{{:number}}</p>
                <p class="cart_info_price">￥<em>{{:fcommodityPrice}}</em></p>
            </div>
        </li>
    {{/for}}
</script>

<!--引入js-->
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script src="${staticSource}/static/js/jsrender.min.js"  type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.converters.js"  type="text/javascript"></script>

<script type="text/javascript">

    $(function () {
        /*空购物车距离*/
        var allHg=$(document).height();
        $(".empty_cart").css("padding-top",allHg/6);
    });
    function showCart(msg) {
        $("#totalNum").html(msg.totalNum);
        $("#discountedPrice").html(msg.discountedPrice);
        $("#actuallyPaid").html(msg.actuallyPaid);
        var data = msg.commodityInfoList;
        if("" != data) {
            var html = $("#shopping_tmpl").render(data);
            $(".empty_cart").hide();
            $(".cart_fixed").show();
            $("#cart_list").html(html);
        } else {
            hideCart()
        }
    }
    function hideCart() {
        $("#totalNum").html("0");
        $("#discountedPrice").html("0.00");
        $("#actuallyPaid").html("0.00");
        $("#cart_list").html("");
        $(".empty_cart").show();
        $(".cart_fixed").hide();
    }

</script>
</body>
</html>