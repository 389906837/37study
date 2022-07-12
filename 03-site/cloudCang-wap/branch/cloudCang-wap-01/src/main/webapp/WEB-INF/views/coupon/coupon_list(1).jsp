<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>卡券列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?16.11"/>
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp" %>
<div class="container">
    <div class="container-c">
        <div class="order-titlebg">
            <div class="order-title coupon-title" style="margin: 0 0;">
                <div class="hover"><a>所有卡券</a></div>
                <div><a>有效卡券</a></div>
                <div><a>失效卡券</a></div>
            </div>
        </div>
        <div class="coupon" id="coupon-box">
            <ul id="coupon-sel">
                <li>
                    <div class="coupon-li">
                        <div id="couponList10"></div>
                        <div id="foot_div10"></div>
                    </div>
                </li>
                <li>
                    <div class="coupon-li">
                        <div id="couponList20"></div>
                        <div id="foot_div20"></div>
                    </div>
                </li>
                <li>
                    <div class="coupon-li">
                        <div id="couponList30"></div>
                        <div id="foot_div30"></div>
                    </div>
                </li>
            </ul>
        </div>

        <form id="myForm" action="${ctx}/personal/myCouponList" method="post">
            <input type="hidden" name="itype" id="itype" value="${itype}"/>
            <input type="hidden" name="limit" id="limit"/>
            <input type="hidden" name="start" id="start"/>
            <input type="hidden" name="page_index10" id="page_index10" value="0" autocomplete="off"/>
            <input type="hidden" name="page_index20" id="page_index20" value="0" autocomplete="off"/>
            <input type="hidden" name="page_index30" id="page_index30" value="0" autocomplete="off"/>
            <script id="myCoupon_tmpl" type="text/x-jsrender">
                    {{for #data}}
                   <div class="coupon-wrap">
                      {{if icouponType ==10}}
                         <div class="coupon-bg coupon-bg-1">
                        {{else icouponType ==20}}
                             <div class="coupon-bg coupon-bg-2">
                         {{else icouponType ==30}}
                              <div class="coupon-bg coupon-bg-3">
                          {{else icouponType ==40}}
                              <div class="coupon-bg coupon-bg-4">
                           {{/if}}
                              <div class="coupon-desc">
                                  <div class="coupon-desc-p">
                                      <p>1.{{:sbriefDesc}}</p>
                                        {{if isAllUse==1}}
                                          <p>2.可适用于所有商品</p>
                                        {{else}}
                                          <p>2.可适用商品<a class="commodity-v" onclick="commodityDown('{{: id}}');">商品列表</a></p>
                                        {{/if}}
                                         {{if iisAllUseDevice==1}}
                                           <p>3.可适用于所有设备</p>
                                         {{else}}
                                           <p>3.可适用售货机<a class="equipment-v" onclick="deviceDown('{{: id}}');">售货机列表</a></p>
                                         {{/if}}
                                          <p>4.有效期：{{formatDate:dcouponEffectiveDate 'yyyy/MM/dd'}}-{{formatDate:dcouponExpiryDate 'yyyy/MM/dd'}}</p>
                                  </div>
                                  <div class="coupon-down" onclick="couponDown(this);">
                                    <img src="${staticSource}/static/images/coupon-down.png">
                                  </div>
                              </div>
                              <div class="coupon-name">
                                  <div style="width: 100%">
                                  {{if icouponType ==40}}
                                        <h2><span class="coupon-name-title">免费</span></h2>
                                    {{else}}
                                        <h2>￥<span class="coupon-name-price">{{: fcouponValue}}</span></h2>
                                    {{/if}}
                                      <h4>
                                      {{if icouponType ==10}}现金券
                                      {{else icouponType ==20}}满减券
                                      {{else icouponType ==30}}抵扣券
                                      {{else icouponType ==40}}商品券{{/if}}
                                      </h4>
                                  </div>
                              </div>
                             {{if istatus==2}}
                               <div class="coupon-disabled">
                                   <img src="${staticSource}/static/images/coupon-completed.png">
                               </div>
                              {{else istatus==3}}
                              <div class="coupon-disabled">
                                  <img src="${staticSource}/static/images/coupon-expired.png">
                              </div>
                              {{/if}}
                          </div>
                        <div class="coupon-explain">
                            <p>说明：{{: scouponInstruction}}</p>
                        </div>
                    </div>
                    {{/for}}




            </script>
        </form>
    </div>
    <%@ include file="/common/include/service-tell.jsp" %>
    <jsp:include page="/common/include/foot.jsp">
        <jsp:param  name="selected" value="3" />
    </jsp:include>
</div>

<div class="eject"></div>
<!-- 可用商品券 -->
<div id="commodity" class="popup-bg" style="width: 100%;">
    <div class="shelf-details md">
        <p class="kaimen-tip" style="padding-top: 1.5rem">可用券商品</p>
        <div class="tab-top">
            <table class="table">
                <thead>
                <tr>
                    <th>商品名称</th>
                   <%-- <th>规格</th>--%>
                    <th>单价</th>
                </tr>
                </thead>
            </table>

        </div>
        <div class="tab">
            <table class="table">
                <tbody class="table-bordered" id="commodityBody">
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- 可用券设备 -->
<div id="equipment" class="popup-bg" style="width: 100%;">
    <div class="shelf-details md">
        <p class="kaimen-tip" style="padding-top: 1.5rem">可用券设备</p>
        <div class="tab-top">
            <table class="table">
                <thead>
                <tr>
                    <th>所属地区</th>
                    <th>设备名称</th>
                    <th>设备地址</th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="tab">
            <table class="table">
                <tbody class="table-bordered" id="deviceBody">

                </tbody>
            </table>
        </div>
    </div>
</div>

<script id="commodity_top" type="text/x-jsrender">
           {{for  data}}
                 <tr>
                        <td>{{:commodityFullName}}</td>
                        <%--<td>{{:specifications}}</td>--%>
                        <td>￥{{:fsalePrice}}</td>
                  </tr>
          {{/for}}
</script>
<script id="device_top" type="text/x-jsrender">
           {{for  data}}
                  <tr>
                    <td>{{:scityName}}</td>
                    <td>{{:sname}}</td>
                    <td>{{:deviceAddress}}</td>
                </tr>
          {{/for}}
</script>

<!-- -->
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script src="${staticSource}/static/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script src="${staticSource}/static/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.converters.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.touchSlider.js"></script>
<!-- -->
<script type="text/javascript">
    $(function () {
        setTitle("我的券包");
        $('.container-c').css('height', $(window.top).height());


        //取得图片对应宽度的高度
        var swidth = document.body.clientWidth;
        $('#coupon-box').css('width', swidth);
        $('#coupon-sel').children('li').css('width', swidth);
        $dragBln = false;
        var maxwd = $("body").width();
        var midwd = 5;
        $(".order-title div").each(function () {
            midwd = $(this).outerWidth(true) + midwd;
        });
        $(".order-title").width(midwd);
        var wdnum = $(".order-title div").length;// 获取个数

        $("#coupon-sel").touchSlider({
            flexible: true,
            speed: 200,
            page: parseInt("${itype}") / 10,
            paging: $(".order-title div"),
            counter: function (e) {
                var e = e || window.event;//add
                //a 下方图标点击事件
                $("#coupon-sel").children("li").removeClass("don").eq(e.current - 1).addClass("don");
                $(".order-title div").removeClass("hover").eq(e.current - 1).addClass("hover");
                var hh = $("#coupon-sel li").eq(e.current - 1).height() + "px";
                $('#coupon-box').height(hh);
                $("#itype").val(parseInt(e.current) * 10);
                if (parseInt($("#page_index" + $("#itype").val()).val()) <= 0) {
                    reloadDataByItype();
                }
                var itt = 0;
                var lihg = $(".shop-infor-flex").height();
                $(".order-main-listli-num").css("line-height", lihg + 'px');
                if (wdnum > 3) {
                    $(".order-title div").css({"width": "auto", "margin": "0 2%"});
                }
                if (e.current < wdnum) {
                    for (var i = 0; i <= e.current; i++) {
                        itt = $(".order-title div").eq(i).outerWidth(true) + itt;
                    }
                    if (itt >= maxwd) {
                        $(".order-titlebg").scrollLeft(itt - maxwd);
                    } else {
                        $(".order-titlebg").scrollLeft(0);
                    }
                } else if (e.current == wdnum) {
                    $(".order-titlebg").scrollLeft(midwd - maxwd);
                }
            }
        });
        $("#coupon-sel").bind("mousedown", function () {
            $dragBln = false;
        });
        $("#coupon-sel").bind("dragstart", function () {
            $dragBln = true;
        });

        //可用商品券
        $('.eject').on('click', function () {
            $('.eject,#commodity,#equipment').css('display', 'none');
        });
    });


    function couponDown(obj) {
        //优惠券说明显示隐藏切换
        $(obj).closest('.coupon-bg').next('.coupon-explain').slideToggle();
    }
    function commodityDown(couponId) {
        $.ajax({
            type: "POST",
            url: "${ctx}/personal/useGoods",
            data: {"couponId": couponId},
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var html = $("#commodity_top").render(data);
                    $("#commodityBody").html(html);
                    $('.eject,#commodity').css('display', 'block');
                }
            }
        });
    }
    function deviceDown(couponId) {
        $.ajax({
            type: "POST",
            url: "${ctx}/personal/useDevice",
            data: {"couponId": couponId},
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var html = $("#device_top").render(data);
                    $("#deviceBody").html(html);
                    $('.eject,#equipment').css('display', 'block');
                }
            }
        });
    }
    //数据请求
    var params = {};
    params.limit = "${pageSize}";
    params.start = 0;
    var count = 0;
    var totalProperty = 0;
    function loadQueryDate(page_index, itype, async) {
        params.start = page_index * params.limit;
        page_index = parseInt(page_index) + 1;
        if (false != async) {
            async = true;
        }
        $('#myForm').ajaxSubmit({
            type: "POST",
            dataType: "json",
            async: async,
            beforeSerialize: function () {
                $('#start').val(params.start);
                $('#limit').val(params.limit);
            },
            error: function (msg) {
                $("body").html(msg.responseText);
            },
            success: function (msg) {
                if (msg.success) {
                    totalProperty = msg.totalProperty;
                    count = Math.ceil(msg.totalProperty / params.limit);
                    if (true == async) {
                        var data = msg.data;
                        if ("" != data) {
                            //渲染数据
                            var html = $("#myCoupon_tmpl").render(data);

                            if (data.length == 0) {
                                if (parseInt(page_index) == 1) {
                                    Util.EmptyData("暂无卡券记录!",{
                                            wid: "loading_empty" + itype,
                                            wrapId: "couponList" + itype,
                                    });
                                    //page_index = parseInt(page_index)-1;
                                    $("#page_index" + itype).val(page_index);
                                    return;
                                } else {
                                    Util.Loadfoot("数据加载失败，点击重新加载", {
                                        imageUrl: staticPathRoot + '/static/images/loading.gif',
                                        onClick: reloadData,
                                        wrapId: "couponList" + itype,
                                        wid: "loading_foot" + itype
                                    });
                                    page_index = parseInt(page_index) - 1;
                                }
                            }
                            if (parseInt(page_index) == 1) {
                                $("#couponList" + itype).html(html);
                            } else {
                                $("#couponList" + itype).append(html);
                            }


                            $("#page_index" + itype).val(page_index);
                            if (parseInt(page_index) >= parseInt(count)) {
                                //Util.Loadfoot("已加载全部",{wrapId:"foot_div"+itype,wid:"loading_foot"+itype});
                                var html = "<div class=\"footer-tips\"><span>已经到底啦</span></div>";
                                $("#foot_div" + itype).html(html);
                            } else {
                                Util.Loadfoot("点击加载更多", {
                                    onClick: reloadData,
                                    wrapId: "foot_div" + itype,
                                    wid: "loading_foot" + itype
                                });
                            }
                            if (parseInt(page_index) == 1) {
                                judgescroll();
                            }
                            return;
                        }
                    }
                    if (parseInt(page_index) == 1) {
                        Util.EmptyData("暂无卡券记录!",{
                            wid: "loading_empty" + itype,
                            wrapId: "couponList" + itype,
                        });
                    } else {
                        Util.Loadfoot("数据加载失败，点击重新加载", {
                            imageUrl: staticPathRoot + '/static/images/loading.gif',
                            onClick: reloadData,
                            wid: "loading_foot" + itype,
                            wrapId: "foot_div" + itype
                        });
                        page_index = parseInt(page_index) - 1;
                    }
                    $("#page_index" + itype).val(page_index);
                } else {
                    //加载失败
                    if (parseInt(page_index) == 1) {
                        Util.Loadfailure("数据加载失败!", {
                            wrapId: "couponList" + itype,
                            wid: "loading_failure" + itype
                        });
                        $('#loading_failure').css('height', ($(window.top).height()-$(".order-titlebg").height())/2);
                        $('#loading_failure').css('padding-top',($(window.top).height()-$(".order-titlebg").height())/2-40);
                    } else {
                        Util.Loadfoot("数据加载失败，点击重新加载", {
                            imageUrl: staticPathRoot + '/static/images/loading.gif',
                            onClick: reloadData,
                            wid: "loading_foot" + itype,
                            wrapId: "foot_div" + itype
                        });
                        page_index = parseInt(page_index) - 1;
                    }
                    $("#page_index" + itype).val(page_index);
                }
            }
        });
        return count;
    }
    $(window).scroll(function () {
        if ($(window).scrollTop() == $(document).height() - $(window).height()) {
            var itype = $("#itype").val();
            $("#loading_foot" + itype).click();
        }
    });
    //判断不超出一屏的loading
    function judgescroll() {
        var judcroll = $(document).scrollTop();
        if (judcroll <= 0) {
            var itype = $("#itype").val();
            $("#loading_foot" + itype).click();
        }
    }
    function reloadData() {
        var itype = $("#itype").val();
        Util.Loadfoot("加载中...", {
            imageUrl: staticPathRoot + '/static/images/loading.gif',
            wid: "loading_foot" + itype,
            wrapId: "foot_div" + itype
        });
        loadQueryDate($("#page_index" + itype).val(), itype, true);
    }
    function reloadDataByItype() {
        var itype = $("#itype").val();
        Util.Loading('加载中...', {wrapId: "couponList" + itype, wid: "loading" + itype});
        $('#loading').css('height', ($(window.top).height()-$(".order-titlebg").height())/2);
        $('#loading').css('padding-top',($(window.top).height()-$(".order-titlebg").height())/2-40);
        loadQueryDate($("#page_index" + itype).val(), itype, true);
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>