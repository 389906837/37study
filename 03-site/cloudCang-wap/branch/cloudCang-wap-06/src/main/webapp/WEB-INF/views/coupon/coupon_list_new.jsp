<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>优惠券列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/index.css"/>
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>
</head>
<body class="coupon_bgcolor">
<div class="coupon_container" id="touchslider">
    <ul class="hd order_nav">
        <li class="hover"><a>所有卡券</a></li>
        <li><a>有效卡券</a></li>
        <li><a>失效卡券</a></li>
    </ul>
    <ul class="bd">
        <div>
            <li class="coupon_tab">
                <div class="coupon_li" id="couponList10"></div>
                <div id="foot_div10"></div>
            </li>
        </div>
        <div>
            <li class="coupon_tab">
                <div class="coupon_li" id="couponList20"></div>
                <div id="foot_div20"></div>
            </li>
        </div>
        <div>
            <li class="coupon_tab">
                <div class="coupon_li" id="couponList30"></div>
                <div id="foot_div30"></div>
            </li>
        </div>
    </ul>
    <form id="myForm" action="${ctx}/personal/myCouponList" method="post">
        <input type="hidden" name="itype" id="itype" value="${itype}"/>
        <input type="hidden" name="limit" id="limit"/>
        <input type="hidden" name="start" id="start"/>
        <input type="hidden" name="page_index10" id="page_index10" value="0" autocomplete="off"/>
        <input type="hidden" name="page_index20" id="page_index20" value="0" autocomplete="off"/>
        <input type="hidden" name="page_index30" id="page_index30" value="0" autocomplete="off"/>
        <script id="myCoupon_tmpl" type="text/x-jsrender">
         {{for #data}}
                   <div class="coupon_wrap">
                      {{if icouponType ==10}}
                         <div class="coupon_bg coupon_bg_1">
                        {{else icouponType ==20}}
                             <div class="coupon_bg coupon_bg_2">
                         {{else icouponType ==30}}
                              <div class="coupon_bg coupon_bg_3">
                          {{else icouponType ==40}}
                              <div class="coupon_bg coupon_bg_4">
                           {{/if}}
                              <div class="coupon_desc">
                                  <div class="coupon_desc_p">
                                      <p>1.{{:sbriefDesc}}</p>
                                        {{if isAllUse==1}}
                                          <p>2.可适用于所有商品</p>
                                        {{else}}
                                          <p>2.可适用商品<a class="sptk" onclick="commodityDown('{{: id}}');">商品列表</a></p>
                                        {{/if}}
                                         {{if iisAllUseDevice==1}}
                                           <p>3.可适用于所有设备</p>
                                         {{else}}
                                           <p>3.可适用售货机<a class="sptk" onclick="deviceDown('{{: id}}');">售货机列表</a></p>
                                         {{/if}}
                                          <p>4.有效期：{{formatDate:dcouponEffectiveDate 'yyyy/MM/dd'}}-{{formatDate:dcouponExpiryDate 'yyyy/MM/dd'}}</p>
                                  </div>
                                  <div class="coupon_down" onclick="couponDown(this);">
                                    <img src="${staticSource}/static/images/coupon_down.png">
                                  </div>
                              </div>
                              <div class="coupon_name">
                                  <div style="width: 100%">
                                  {{if icouponType ==40}}
                                        <h2><span class="coupon_name_title">免费</span></h2>
                                    {{else}}
                                        <h2>￥<span class="coupon_name_price">{{: fcouponValue}}</span></h2>
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
                               <div class="coupon_disabled">
                                   <img src="${staticSource}/static/images/coupon_completed.png">
                               </div>
                              {{else istatus==3}}
                              <div class="coupon_disabled">
                                  <img src="${staticSource}/static/images/coupon_expired.png">
                              </div>
                              {{/if}}
                          </div>
                        <div class="coupon_explain">
                            <p>说明：{{: scouponInstruction}}</p>
                        </div>
                    </div>
              {{/for}}

        </script>
    </form>
</div>
<jsp:include page="/common/include/foot.jsp">
    <jsp:param name="selected" value="3"/>
</jsp:include>

<!-- 可用券商品弹框 -->
<div class="popup_bg close" id="useCommodity">
    <div class="shelfdetails md">
        <p class="shelfdetails_name">可用券商品</p>
        <div class="shelfdetails_title">
            <table class="table">
                <thead>
                <tr>
                    <th>商品名称</th>
                    <th>单价(元)</th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="shelfdetails_list">
            <table class="table">
                <tbody class="table-bordered" id="commodityBody">
                </tbody>
            </table>
        </div>
    </div>
</div>
<script id="commodity_top" type="text/x-jsrender">
   {{for  #data}}
         <tr>
                <td>{{:commodityFullName}}</td>
                <td>{{:fsalePrice}}</td>
          </tr>
  {{/for}}

</script>
<!-- 可用券设备弹框 -->
<div class="popup_bg close" id="useDevice">
    <div class="shelfdetails md">
        <p class="shelfdetails_name">可用券设备</p>
        <div class="shelfdetails_title">
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
        <div class="shelfdetails_list">
            <table class="table">
                <tbody class="table-bordered" id="deviceBody">
                </tbody>
            </table>
        </div>
    </div>
</div>
<script id="device_top" type="text/x-jsrender">
   {{for  #data}}
          <tr>
            <td>{{:scityName}}</td>
            <td>{{:sname}}</td>
            <td>{{:deviceAddress}}</td>
        </tr>
  {{/for}}

</script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script src="${staticSource}/static/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script src="${staticSource}/static/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.converters.js" type="text/javascript"></script>
<script src="${staticSource}/static/js/TouchSlide.1.1.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">

    TouchSlide({
        slideCell: '#touchslider',
        titOnClassName: 'hover',
        endFun: function (i, c) {
            if (i == 0) {
                $("#itype").val(10);
                reloadDataByItype(10);
            } else if (i == 1) {
                $("#itype").val(20);
                reloadDataByItype(20);
            } else if (i == 2) {
                $("#itype").val(30);
                reloadDataByItype(30);
            }
        }
    });
    function commodityDown(couponId) {
        $.ajax({
            type: "POST",
            url: "${ctx}/personal/useGoods",
            data: {"couponId": couponId},
            dataType: "json",
            success: function (data) {
                if (data.success) {
                        var html = $("#commodity_top").render(data.data);
                        $("#commodityBody").html(html);
                        $('#useCommodity').removeClass('close');
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
                    var html = $("#device_top").render(data.data);
                    $("#deviceBody").html(html);
                    $('#useDevice').removeClass('close');
                }
            }
        });
    }


    $('.popup_bg').on('click', function () {
        $('.popup_bg').addClass('close');
    });

    //优惠券说明显示隐藏切换
    function couponDown(obj) {
        if ($(obj).children().attr('src') == '${staticSource}/static/images/coupon-down.png') {
            $(obj).children().attr('src', '${staticSource}/static/images/coupon-up.png');
        } else {
            $(obj).children().attr('src', '${staticSource}/static/images/coupon-down.png');
        }
        $(obj).closest('.coupon_bg').next('.coupon_explain').slideToggle();
    }

    //数据请求
    var params = {};
    params.limit = "${pageSize}";
    params.start = 0;
    var count = 0;
    var totalProperty = 0;
    function loadQueryDate(page_index, itype, async) {
        $("#itype").val(itype);
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
                                    Util.EmptyData("暂无卡券记录!", {
                                        wid: "loading_empty" + itype,
                                        wrapId: "couponList" + itype
                                    });
                                    $("#page_index" + itype).val(page_index);
                                } else {
                                    Util.Loadfoot("数据加载失败，点击重新加载", {
                                        imageUrl: staticPathRoot + '/static/images/loading.gif',
                                        onClick: reloadData,
                                        wrapId: "couponList" + itype,
                                        wid: "loading_foot" + itype
                                    });
                                    page_index = parseInt(page_index) - 1;
                                }

                                $('.bd').height($('#couponList' + itype).parent("li").outerHeight());
                                console.log('1:' + itype);
                                return;
                            }
                            if (parseInt(page_index) == 1) {
                                $("#couponList" + itype).html(html);
                            } else {
                                $("#couponList" + itype).append(html);
                            }
                            $("#page_index" + itype).val(page_index);
                            if (parseInt(page_index) >= parseInt(count)) {
                                /* var html = "<div class=\"footer-tips\"><span>已经到底啦</span></div>";*/
                                var html = " <div class=\"buy_havecoupon\">\n" +
                                    "                    <p class=\"buy_line\"></p>\n" +
                                    "                    <p class=\"buy_linetext footer_tips\">已经到底拉</p>\n" +
                                    "                </div>";
                                $("#foot_div" + itype).html(html);
                            } else {
                                Util.Loadfoot("点击加载更多", {
                                    onClick: reloadData,
                                    wrapId: "foot_div" + itype,
                                    wid: "loading_foot" + itype
                                });
                            }
                            $('.bd').height($('#couponList' + itype).parent("li").outerHeight());
                            console.log('2:' + itype, $('#couponList' + itype).parent("li").outerHeight());
                            console.log('2:' + itype, ($('#couponList' + itype)).get(0).clientHeight);
                            console.log('2:' + itype, ($('#couponList' + itype).parent("li")).get(0).clientHeight);
                            /* if (parseInt(page_index) == 1) {
                             judgescroll();
                             }*/
                            return;
                        }
                    }
                    if (parseInt(page_index) == 1) {
                        Util.EmptyData("暂无卡券记录!", {
                            wid: "loading_empty" + itype,
                            wrapId: "couponList" + itype
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

                    $('.bd').height($('#couponList' + itype).parent("li").outerHeight());
                    console.log('3:' + itype, $('#couponList' + itype).html());
                    console.log('3:' + itype, ($('#couponList' + itype)).get(0).clientHeight);
                    console.log('3:' + itype, ($('#couponList' + itype).parent("li")).get(0).clientHeight);
                    console.log('3:' + itype, $('#couponList' + itype).parent("li").outerHeight());
                } else {
                    //加载失败
                    if (parseInt(page_index) == 1) {
                        Util.Loadfailure("数据加载失败!", {
                            wrapId: "couponList" + itype,
                            wid: "loading_failure" + itype
                        });
                        $('#loading_failure').css('height', ($(window.top).height() - $(".order-titlebg").height()) / 2);
                        $('#loading_failure').css('padding-top', ($(window.top).height() - $(".order-titlebg").height()) / 2 - 40);
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
                    $('.bd').height($('#couponList' + itype).parent("li").outerHeight());
                    console.log('4:' + itype);
                }
            }
        });
        return count;
    }
    /*    //判断不超出一屏的loading
     function judgescroll() {
     var judcroll = $(document).scrollTop();
     if (judcroll <= 0) {
     var itype = $("#itype").val();
     $("#loading_foot" + itype).click();
     }
     }*/
    function reloadData() {
        var itype = $("#itype").val();
        Util.Loadfoot("加载中...", {
            imageUrl: staticPathRoot + '/static/images/loading.gif',
            onClick: reloadData,
            wid: "loading_foot" + itype,
            wrapId: "foot_div" + itype
        });
        loadQueryDate($("#page_index" + itype).val(), itype, true);
    }
    function reloadDataByItype(itype) {
        if (Util.isEmpty(itype)) {
            itype = $("#itype").val();
        }
        if (Number($("#page_index" + itype).val()) == 0) {
            Util.Loading('加载中...', {wrapId: "couponList" + itype, wid: "loading" + itype});
            $('#loading').css('height', ($(window.top).height() - $(".order-titlebg").height()) / 2);
            $('#loading').css('padding-top', ($(window.top).height() - $(".order-titlebg").height()) / 2 - 40);
            loadQueryDate($("#page_index" + itype).val(), itype, true);
        } else {
            $('.bd').height($('#couponList' + itype).parent("li").outerHeight());
            console.log('5:' + itype);
        }
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>