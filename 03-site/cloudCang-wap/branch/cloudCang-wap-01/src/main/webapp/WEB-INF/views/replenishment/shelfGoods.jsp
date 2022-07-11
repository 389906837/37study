<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <title>上架</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script src="${staticSource}/static/js/jsrender.min.js" type="text/javascript"></script>
    <script src="${staticSource}/static/js/jsrender.converters.js" type="text/javascript"></script>
</head>
<body>
<div class="container">
    <div class="listing">
        <div class="listing-pack">
            <div class="listing-menu">
                <a class="sj active">上架清单 </a>
                <a class="xj">下架清单</a>
            </div>
            <ul class="listing-list sjlist" id="upShelfGoods">
            </ul>
            <ul class="listing-list xjlist none" id="lowShelfGoods">
            </ul>
        </div>
    </div>

    <div class="list-fixed" id="upShelfTotalGoods" style="display: block">
        当前已上架共计：<em id="upNum">0件</em>
    </div>
    <div class="list-fixed" id="lowShelfTotalGoods" style="display: none">
        当前已下架共计：<em id="lowNum">0件</em>
    </div>
</div>
<script>
    $(function () {
        /*       $.ajax({
         type: "post",
         url: ctxRoot + "/order/shoppingCloudRealTimeOrder?userIdAndDeviceId=e40759ecfeb211e88477005056b70c44_04bd005b791811e8a6b1005056b70c44",
         dataType: "json",
         success: function (msg) {
         showAddCart(msg);
         showSubCart(msg);
         }
         });*/
        $('.sj').on('click', function () {
            $('.listing-menu a').removeClass('active');
            $(this).addClass('active');
            $('.listing-list').removeClass('none');
            $('.xjlist').addClass('none');
            $("#lowShelfTotalGoods").css('display', 'none');
            $("#upShelfTotalGoods").css('display', 'block');
            $(document).attr('title', '上架');

        });
        $('.xj').on('click', function () {
            $('.listing-menu a').removeClass('active');
            $(this).addClass('active');
            $('.listing-list').removeClass('none');
            $('.sjlist').addClass('none');
            $("#lowShelfTotalGoods").css('display', 'block');
            $("#upShelfTotalGoods").css('display', 'none');
            $(document).attr('title', '下架');
        })
    })
</script>
<script id="up_shelf" type="text/x-jsrender">
           {{for  #data}}
                 <li>
                    <div class="listing-item">
                        <img src="${dynamicSource}{{:scommodityImg}}">
                        <div class="listing-item-title">{{:scommodityFullName}}</div>
                        <span>{{:number}}件</span>
                    </div>
                </li>
          {{/for}}



</script>
<script id="low_shelf" type="text/x-jsrender">
           {{for  #data}}
                 <li>
                    <div class="listing-item">
                        <img src="${dynamicSource}{{:scommodityImg}}">
                        <div class="listing-item-title">{{:scommodityFullName}}</div>
                        <span>{{:number}}件</span>
                    </div>
                </li>
          {{/for}}



</script>
<script type="text/javascript">
    // 补货员购物车开始
    function showAddCart(msg) {
        var data = msg.addCommodityInfoList;
        var upNum = msg.shelfNum;
        $("#upNum").html(upNum + "件");
        if (typeof(data) != "undefined" && null != data && "" != data && "null" != data) {
            var html = $("#up_shelf").render(data);
            $("#upShelfGoods").html(html);
        } else {
            hideRepCart(1)
        }
    }
    function showSubCart(msg) {
        var data = msg.subCommodityInfoList;
        var lowNum = msg.obtainedNum;
        $("#lowNum").html(lowNum + "件");
        if (typeof(data) != "undefined" && null != data && "" != data && "null" != data) {
            var html = $("#low_shelf").render(data);
            $("#lowShelfGoods").html(html);
        } else {
            hideRepCart(2)
        }
    }
    function hideRepCart(num) {
        if(num == 0 || num == 1) {
            $("#upNum").html("0件");
            $("#upShelfGoods").html("");
        }
        if(num == 0 || num == 2) {
            $("#lowNum").html("0件");
            $("#lowShelfGoods").html("");
        }
    }
    // 补货员购物车结束
</script>

</body>
</html>