<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>订单列表</title>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css?1.0.1" />
    <link rel="stylesheet" href="${staticSource}/static/css/index.css?1.1" />

</head>
<body class="order_bg">
<%@ include file="/common/include/commonHeader.jsp" %>
<div class="order_container" id="touchslider">
    <ul class=" hd order_nav">
        <li class="hover"><a>全部订单</a></li>
        <li><a>待付款订单</a></li>
        <li><a>已完成订单</a></li>
        <li><a>退款记录</a></li>
    </ul>
    <ul class="bd">
        <div>
            <li class="order_tab" id="bd_0">
                <div id="dataListAll"></div>
                <div id="foot_divAll"></div>
            </li>
        </div>
        <div>
            <li class="order_tab" id="bd_1">
                <div id="dataListFail"></div>
                <div id="foot_divFail"></div>
            </li>
        </div>
        <div>
            <li class="order_tab" id="bd_2">
                <div id="dataListSuccess"></div>
                <div id="foot_divSuccess"></div>
            </li>
        </div>
        <div>
            <li class="order_tab" id="bd_3">
                <div id="dataListRefund"></div>
                <div id="foot_divRefund"></div>
            </li>
        </div>




    </ul>

    <form id="myForm" action="${ctx}/personal/myOrderList" method="post">
        <input type="hidden" name="type" id="type" value="${type}"/>
        <input type="hidden" name="limit" id="limit" />
        <input type="hidden" name="start" id="start" />
        <input type="hidden" name="page_indexAll" id="page_indexAll" value="0" autocomplete="off"/>
        <input type="hidden" name="page_indexFail" id="page_indexFail" value="0" autocomplete="off"/>
        <input type="hidden" name="page_indexSuccess" id="page_indexSuccess" value="0" autocomplete="off"/>
        <input type="hidden" name="page_indexRefund" id="page_indexRefund" value="0" autocomplete="off"/>
        <script id="myOrder_tmpl" type="text/x-jsrender">
        {{for #data}}
             <div class="order_list">
                  <div class="order_pro_list">
                    <div class="order_title"><span>订单号：{{:sorderCode}}</span>
                    <span class="pr-03 fr">{{if istatus == 10}}已完成{{/if}}{{if istatus == 100}}待支付{{/if}}
                                    {{if istatus == 110}}支付处理中{{/if}}{{if istatus == 20}}付款失败{{/if}}</span></div>

                     {{for commoditys}}
                     <div class="pro_list">
                       <div class="pro_img"><img src="${dynamicSource}{{:scommodityImg}}"></div>
                        <div class="pro_info">
                            <h4 class="m-b-05">{{:commodityName}}</h4>
                            <p class="m-b-07">数量：{{:orderNum}}</p>
                            <p>{{formatDate:orderTimeTemp 'yyyy/MM/dd HH:mm'}}</p>
                        </div>
                        <div class="pro_price pr-03"><span>￥</span><i>{{:favgActualAmount}}</i></div>
                        </div>
                     {{/for}}
                    <div class="order_book">
                        <p class="tr">共{{:totalNum}}件商品（优惠金额￥{{:discountAmount}}） 合计：<span>￥</span><i>{{:actualPayAmount}}</i></p>
                        <div class="pd tr">
                                {{if refundOrderAmount > 0}}
                                   <a class="btn btn_gray"  onclick="jumpRefundOrderDetail('{{:refundCode}}');" href="javascript:void(0);">查看详情</a>
                                    {{else}}
                                    <a class="btn btn_gray"  onclick="jumpOrderDetail('{{:sorderCode}}');" href="javascript:void(0);">查看详情</a>
                                 {{/if}}
                             {{if istatus == 10 && actualPayAmount > 0 && actualPayAmount > actualRefundAmount }}
                                    <a class="btn btn_blue"  onclick="jumpApplyRefund('{{:sorderCode}}');" href="javascript:void(0);">申请退款</a>
                             {{/if}}
                              {{if istatus == 100 || istatus == 20}}
                                    <a class="btn btn_blue" onclick="payment('{{:sorderCode}}');" href="javascript:void(0);">付款</a>
                              {{/if}}
                        </div>
                    </div>
                </div>
             </div>
        {{/for}}
        </script>
    </form>
</div>
<jsp:include page="/common/include/foot.jsp">
    <jsp:param name="selected" value="2"/>
</jsp:include>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?1.2"></script>
<script src="${staticSource}/static/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js?136"></script>
<script src="${staticSource}/static/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.converters.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/order-pay.js?v=1.9"></script>
<script type="text/javascript" src="${staticSource}/static/js/TouchSlide.1.1.js" charset="utf-8"></script>
<script type="text/javascript">
    TouchSlide({
        slideCell: '#touchslider',
        titOnClassName: 'hover',
        defaultIndex:<c:if test="${type eq 'All'}">0</c:if><c:if test="${type eq 'Fail'}">1</c:if><c:if test="${type eq 'Success'}">2</c:if><c:if test="${type eq 'Refund'}">3</c:if>,
        endFun: function (i, c) {
            if (i == 0) {
                $("#type").val("All");
                reloadDataByItype("All");
            } else if (i == 1) {
                $("#type").val("Fail");
                reloadDataByItype("Fail");
            } else if (i == 2) {
                $("#type").val("Success");
                reloadDataByItype("Success");
            } else if (i == 3) {
                $("#type").val("Refund");
                reloadDataByItype("Refund");
            }
        }
    });
    //数据请求
    var params = {};
    params.limit = "${pageSize}";
    params.start = 0;
    var count = 0;
    var totalProperty = 0;
    function loadQueryDate(page_index, type, async) {
        $("#type").val(type);
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
                //成功返回
                if (msg.success) {
                    totalProperty = msg.totalProperty;
                    count = Math.ceil(msg.totalProperty / params.limit);
                    if (true == async) {
                        var data = msg.data;
                        if ("" != data) {
                            //渲染数据
                            var html = $("#myOrder_tmpl").render(data);

                            if (data.length == 0) {
                                if (parseInt(page_index) == 1) {
                                    Util.EmptyData("暂无订单记录!", {
                                        wid: "loading_empty" + type,
                                        wrapId: "dataList" + type
                                    });
                                    $("#page_index").val(page_index);
                                } else {
                                    Util.Loadfoot("数据加载失败，点击重新加载", {
                                        imageUrl: staticPathRoot + '/static/images/loading.gif',
                                        onClick: reloadData,
                                        wrapId: "dataList" + type,
                                        wid: "loading_foot" + type
                                    });
                                    page_index = parseInt(page_index) - 1;
                                }
                                $('.bd').height($('#dataList' + type).parent("li").outerHeight());
                                return;
                            }
                            if (parseInt(page_index) == 1) {
                                $("#dataList" + type).html(html);
                            } else {
                                $("#dataList" + type).append(html);
                            }

                            $("#page_index" + type).val(page_index);
                            if (parseInt(page_index) >= parseInt(count)) {
                                var html = " <div class=\"buy_havecoupon\">\n" +
                                    "                    <p class=\"buy_line\"></p>\n" +
                                    "                    <p class=\"buy_linetext footer_tips\">已经到底拉</p>\n" +
                                    "                </div>";
                                $("#foot_div" + type).html(html);
                            } else {
                                Util.Loadfoot("点击加载更多", {
                                    onClick: reloadData,
                                    wrapId: "foot_div" + type,
                                    wid: "loading_foot" + type
                                });
                            }
                            $('.bd').height($('#dataList' + type).parent("li").outerHeight());
                            return;
                        }
                    }
                    if (parseInt(page_index) == 1) {
                        Util.EmptyData("暂无订单记录!", {
                            wid: "loading_empty" + type,
                            wrapId: "dataList" + type
                        });

                    } else {
                        Util.Loadfoot("数据加载失败，点击重新加载", {
                            imageUrl: staticPathRoot + '/static/images/loading.gif',
                            onClick: reloadData,
                            wid: "loading_foot" + type,
                            wrapId: "foot_div" + type
                        });
                        page_index = parseInt(page_index) - 1;
                    }
                    $("#page_index" + type).val(page_index);
                    $('.bd').height($('#dataList' + type).parent("li").outerHeight());
                } else { //加载失败
                    if (parseInt(page_index) == 1) {
                        Util.Loadfailure("数据加载失败!", {
                            wrapId: "dataList" + type,
                            wid: "loading_failure" + type
                        });
                        $('#loading_failure').css('height', ($(window.top).height() - $(".order-titlebg").height()) / 2);
                        $('#loading_failure').css('padding-top', ($(window.top).height() - $(".order-titlebg").height()) / 2 - 40);
                    } else {
                        Util.Loadfoot("数据加载失败，点击重新加载", {
                            imageUrl: staticPathRoot + '/static/images/loading.gif',
                            onClick: reloadData,
                            wid: "loading_foot" + type,
                            wrapId: "foot_div" + type
                        });
                        page_index = parseInt(page_index) - 1;
                    }
                    $("#page_index" + type).val(page_index);
                    $('.bd').height($('#dataList' + type).parent("li").outerHeight());
                }
            }
        });
        return count;
    }

    function reloadData() {
        var type = $("#type").val();
        Util.Loadfoot("加载中...", {
            imageUrl: staticPathRoot + '/static/images/loading.gif',
            onClick: reloadData,
            wid: "loading_foot" + type,
            wrapId: "foot_div" + type
        });
        loadQueryDate($("#page_index" + type).val(), type, true);
    }
    function reloadDataByItype(type, callback) {
        if (Util.isEmpty(type)) {
            type = $("#type").val();
        }
        if(Number($("#page_index"+type).val()) == 0) {
            Util.Loading('加载中...', {wrapId: "dataList" + type, wid: "loading" + type});
            $('#loading').css('height', ($(window.top).height() - $(".order-titlebg").height()) / 2);
            $('#loading').css('padding-top', ($(window.top).height() - $(".order-titlebg").height()) / 2 - 40);
            loadQueryDate($("#page_index" + type).val(), type, true, callback);
        } else {
            $('.bd').height($('#dataList' + type).parent("li").outerHeight());
        }
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>

</body>
</html>