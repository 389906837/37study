<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="cart_container close" id="shoppingRealtime">
    <div class="cartlist ">
        <ul class="cart_item" id="cart">
        </ul>
        <div class="empty_cart">
            <img src="${staticSource}/static/images/cart_empty.png">
            <p>您的购物车空空如也</p>
        </div>
    </div>
    <div class="cart_fixed close">
        <p>
            <span>总数量：<i id="totalNum">0</i></span>&nbsp;
            <span>优惠金额：￥<i id="discountedPrice">0.00</i></span>
        </p>
        <p class="cart_fixed_money"><em>实付金额：￥</em><i id="actuallyPaid">0.00</i></p>
    </div>
</div>
<script id="shopping_tmpl" type="text/x-jsrender">
{{for #data}}
        <li id="goodsTemplate">
            <img class="cart_goods" src="${dynamicSource}{{:scommodityImg}}" />
            <div class="cart_info">
                <p class="cart_info_title">{{:scommodityFullName}}</p>
                <p class="cart_info_num">数量：{{:number}}</p>
                <p class="cart_info_price">￥ {{:fcommodityPrice}}</p>
            </div>
        </li>
{{/for}}



</script>
<script type="text/javascript">

    $(function () {
        /*空购物车距离*/
        var allHg = $(document).height();
        $(".empty_cart").css("padding-top", allHg / 6);
    });
    function showCart(msg) {
        $(".cart_fixed").removeClass("close");
        $("#totalNum").html(msg.totalNum);
        $("#discountedPrice").html(msg.discountedPrice);
        $("#actuallyPaid").html(msg.actuallyPaid);
        var data = msg.commodityInfoList;
        if ("" != data) {
            var html = $("#shopping_tmpl").render(data);
            $(".empty_cart").hide();
            $(".cart_fixed").show();
            $("#cart").html(html);
        } else {
            hideCart()
        }
    }
    function hideCart() {
        if (!$(".cart_fixed").hasClass("close")) {
            $(".cart_fixed").addClass("close");
        }
        $("#totalNum").html("0");
        $("#discountedPrice").html("0.00");
        $("#actuallyPaid").html("0.00");
        $("#cart").html("");
        $(".empty_cart").show();
        $(".cart_fixed").hide();
    }
</script>