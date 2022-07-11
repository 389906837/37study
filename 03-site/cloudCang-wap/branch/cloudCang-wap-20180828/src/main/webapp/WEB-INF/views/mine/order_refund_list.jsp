<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>退款记录</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css?1" />
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?6" />
    <style type="text/css">
        .m-t{margin: 1rem 1rem 0rem 1rem;}
    </style>
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp"%>
<div class="container">
    <div class="container-c">
        <%@ include file="/common/include/orderHeader.jsp"%>
        <div id="order-box">
            <div class="order-box-main">
                <div class="order-main-ab">
                    <div id="dataList"></div>
                    <div id="foot_div"></div>
                </div> <!-- order-main-ab -->
            </div>
            <form id="myForm" action="${ctx}/personal/myOrderList" method="post">
                <input type="hidden" name="limit" id="limit" />
                <input type="hidden" name="start" id="start" />
                <input type="hidden" name="type" id="type" value="${type}" />
                <input type="hidden" name="page_index" id="page_index" value="0" autocomplete="off" />
                <script id="myRefundOrder_tmpl" type="text/x-jsrender">
                    {{for #data}}
                         <div class="refundrecord-main-list m-t">
                                <div class="refundrecord-main-listtitle relative" >
                                    <h4>原订单号：{{:sorderCode}}</h4>
                                    <i>{{if iauditStatus == 10}}待审核{{/if}}{{if iauditStatus == 30}}审核拒绝{{/if}}
                                    {{if iauditStatus == 20 && irefundStatus == 10}}待退款{{/if}}
                                    {{if iauditStatus == 20 && irefundStatus == 20}}退款失败{{/if}}
                                    {{if iauditStatus == 20 && irefundStatus == 30}}已退款{{/if}}</i>
                                </div>
                                <div class="refundrecord-main-listtitle relative m-t1" >
                                    <h4>退款单号：{{:refundCode}}</h4>
                                    <span>申请时间：{{formatDate:applyTime 'yyyy/MM/dd HH:mm'}}</span>
                                </div>
                                <ul>
                                    {{for commoditys}}
                                        <li class="refundrecord-main-listli refund-inforLi-flex">
                                            <div class="shop-infor-flex">
                                                <h4>{{:commodityName}}</h4>
                                                <p>￥{{:favgActualAmount}}</p>
                                            </div>
                                            <div class="check-pic-flex refundrecord-main-listli-num">x{{:orderNum}}</div>
                                        </li>
                                    {{/for}}
                                </ul>
                                <div class="order-main-total clearfix">
                                    <p>共{{:totalNum}}件商品  合计：¥<span>{{:actualRefundAmount}}</span> （优惠金额 ¥{{:discountAmount}}）</p>
                                    <div class="right md">
                                        <a class="apply-button look-btn" onclick="jumpRefundOrderDetail('{{:refundCode}}');" href="javascript:void(0);">查看详情</a>
                                        {{if iauditStatus == 30}}
                                            <a class="apply-button refund-btn" onclick="jumpApplyRefund('{{:sorderCode}}');" href="javascript:void(0);">重新申请</a>
                                        {{/if}}
                                    </div>
                                </div>
                            </div>
                    {{/for}}
                </script>
            </form>
        </div>
    </div>
    <%@ include file="/common/include/service-tell.jsp"%>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script src="${staticSource}/static/js/jquery.form.js"  type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script src="${staticSource}/static/js/jsrender.min.js"  type="text/javascript"></script>
<script src="${staticSource}/static/js/jsrender.converters.js"  type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/order-pay.js?4"></script>
<script type="text/javascript">
    $(function() {
        setTitle("退款记录");
        $('.container-c').css('height', $(window.top).height());
        Util.Loading('加载中...');
        $('#loading').css('height', ($(window.top).height()-$(".order-titlebg").height())/2);
        $('#loading').css('padding-top',($(window.top).height()-$(".order-titlebg").height())/2-40);
        loadQueryDate($("#page_index").val(),true);
    });

    //数据请求
    var params = {};
    params.limit = "${pageSize}";
    params.start = 0;
    var count = 0;
    var totalProperty = 0;
    function loadQueryDate(page_index,async) {
        params.start = page_index * params.limit;
        page_index = parseInt(page_index)+1;
        if(false != async){
            async = true;
        }
        $('#myForm').ajaxSubmit({
            type : "POST",
            dataType : "json",
            async: async,
            beforeSerialize:function(){
                $('#start').val(params.start);
                $('#limit').val(params.limit);
            },
            error:function(msg) {
                $("body").html(msg.responseText);
            },
            success:function(msg) {
                //成功返回
                if (msg.success) {
                    totalProperty = msg.totalProperty;
                    count = Math.ceil(msg.totalProperty / params.limit);
                    if(true == async) {
                        var data = msg.data;
                        if("" != data) {
                            //渲染数据
                            var html = $("#myRefundOrder_tmpl").render(data);
                            if (data.length ==0) {
                                if(parseInt(page_index) == 1) {
                                    Util.EmptyData("暂无退款记录!");
                                    //page_index = parseInt(page_index)-1;
                                    $("#page_index").val(page_index);
                                    return;
                                } else {
                                    Util.Loadfoot("数据加载失败，点击重新加载",{imageUrl:staticPathRoot+'/static/images/loading.gif',onClick:reloadData})
                                    page_index = parseInt(page_index)-1;
                                }
                            }
                            if(parseInt(page_index) == 1) {
                                $("#dataList").html(html);
                            } else {
                                $("#dataList").append(html);
                            }
                            $("#page_index").val(page_index);
                            if(parseInt(page_index) >= parseInt(count)) {
                                //Util.Loadfoot("已加载全部");
                                var html = "<div class=\"footer-tips\"><span>已经到底啦</span></div>";
                                $("#foot_div").html(html);
                            } else {
                                Util.Loadfoot("点击加载更多",{onClick:reloadData})
                            }
                            return;
                        }
                    }
                    if(parseInt(page_index) == 1) {
                        Util.EmptyData("暂无退款记录!");
                    } else {
                        Util.Loadfoot("数据加载失败，点击重新加载",{imageUrl:staticPathRoot+'/static/images/loading.gif',onClick:reloadData})
                        page_index = parseInt(page_index)-1;
                    }
                    $("#page_index").val(page_index);
                } else {
                    //加载失败
                    if(parseInt(page_index) == 1) {
                        Util.Loadfailure("数据加载失败!");
                        $('#loading_failure').css('height', ($(window.top).height()-$(".order-titlebg").height())/2);
                        $('#loading_failure').css('padding-top',($(window.top).height()-$(".order-titlebg").height())/2-40);
                    } else {
                        Util.Loadfoot("数据加载失败，点击重新加载",{imageUrl:staticPathRoot+'/static/images/loading.gif',onClick:reloadData})
                        page_index = parseInt(page_index)-1;
                    }
                    $("#page_index").val(page_index);
                }
            }
        });
        return count;
    }
    function reloadData() {
        Util.Loadfoot("加载中...",{imageUrl:staticPathRoot+'/static/images/loading.gif'})
        loadQueryDate($("#page_index").val(),true);
    }

    $(window).scroll(function(){
        if($(window).scrollTop() == $(document).height() - $(window).height()) {
            $("#loading_foot").click();
        }
    });
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>