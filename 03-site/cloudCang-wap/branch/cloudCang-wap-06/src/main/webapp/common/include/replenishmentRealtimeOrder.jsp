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
</head>
<body class="container_bg">
<div class="container close" id="replenishmentRealtime">
    <div class="listing">
        <div class="listing_pack">
            <div class="listing_menu">
                <a class="sj active">上架清单 </a>
                <a class="xj">下架清单</a>
            </div>
            <ul class="listing_list sjlist" id="upShelfGoods">
            </ul>
            <ul class="listing_list xjlist none" id="lowShelfGoods">
            </ul>
        </div>
    </div>

    <div class="list_fixed" id="upShelfTotalGoods" style="display: block">
        当前已上架共计：<em id="upNum">0件</em>
    </div>
    <div class="list_fixed" id="lowShelfTotalGoods" style="display: none">
        当前已下架共计：<em id="lowNum">0件</em>
    </div>
</div>
<script>
    $(function () {
        $('.sj').on('click', function () {
            $('.listing_menu a').removeClass('active');
            $(this).addClass('active');
            $('.listing_list').removeClass('none');
            $('.xjlist').addClass('none');
            $("#lowShelfTotalGoods").css('display', 'none');
            $("#upShelfTotalGoods").css('display', 'block');
            $(document).attr('title', '上架');
        });
        $('.xj').on('click', function () {
            $('.listing_menu a').removeClass('active');
            $(this).addClass('active');
            $('.listing_list').removeClass('none');
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
            <div class="listing_item">
                <img src="${dynamicSource}{{:scommodityImg}}">
                <div class="listing_item_title">{{:scommodityFullName}}</div>
                <span>{{:number}}件</span>
            </div>
        </li>
  {{/for}}

</script>
<script id="low_shelf" type="text/x-jsrender">
   {{for  #data}}
         <li>
            <div class="listing_item">
                <img src="${dynamicSource}{{:scommodityImg}}">
                <div class="listing_item_title">{{:scommodityFullName}}</div>
                <span>{{:number}}件</span>
            </div>
        </li>
  {{/for}}

</script>
<script type="text/javascript">
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
        if (num == 0 || num == 1) {
            $("#upNum").html("0件");
            $("#upShelfGoods").html("");
        }
        if (num == 0 || num == 2) {
            $("#lowNum").html("0件");
            $("#lowShelfGoods").html("");
        }
    }
</script>
</body>
</html>