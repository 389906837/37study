<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
<link rel="stylesheet" href="${staticSource}/static/css/style.css?1" />
</head>
<body>
    <%@ include file="/common/include/commonHeader.jsp"%>
    <div class="container">
        <div class="order-titlebg" style="margin-top: 0rem;">
            <div class="order-title">
                <div class="hover"><a>全部</a></div>
                <div><a>待付款</a></div>
                <div><a>已完成</a></div>
                <div><a>退款订单</a></div>
            </div>
        </div>
        <div id="order-box">
            <div class="order-box-main">
                <ul id="order-main-sel">
                    <li class="order-main-li">
                        <div class="order-main-ab">
                            <div id="dataList10"></div>
                            <div id="foot_div10"></div>
                        </div> <!-- order-main-ab -->
                    </li>
                    <li class="order-main-li">
                        <div class="order-main-ab">
                            <div id="dataList20"></div>
                            <div id="foot_div20"></div>
                        </div> <!-- order-main-ab -->
                    </li>
                    <li class="order-main-li">
                        <div class="order-main-ab">
                            <div id="dataList30"></div>
                            <div id="foot_div30"></div>
                        </div> <!-- order-main-ab -->
                    </li>
                    <li class="order-main-li">
                        <div class="order-main-ab">
                            <div id="dataList40"></div>
                            <div id="foot_div40"></div>
                        </div> <!-- order-main-ab -->
                    </li>
                </ul>
            </div>
        </div>
        <form id="myForm" action="${ctx}/personal/myOrderList" method="post">
            <input type="hidden" name="itype" id="itype" value="${itype }" />
            <input type="hidden" name="limit" id="limit" />
            <input type="hidden" name="start" id="start" />
            <input type="hidden" name="page_index10" id="page_index10" value="0" autocomplete="off" />
            <input type="hidden" name="page_index20" id="page_index20" value="0" autocomplete="off" />
            <input type="hidden" name="page_index30" id="page_index30" value="0" autocomplete="off" />
            <input type="hidden" name="page_index40" id="page_index40" value="0" autocomplete="off" />
            <script id="myOrder_tmpl" type="text/x-jsrender">
                {{for #data}}
                     <div class="order-main-list">
                            <div class="order-main-listtitle relative" >
                                <h4>订单号：{{:sorderCode}}</h4>
                                <p>{{formatDate:orderTime 'yyyy/MM/dd HH:mm'}}</p>
                                <i>{{if istatus == 10}}已完成{{/if}}{{if istatus == 100}}待支付{{/if}}
                                {{if istatus == 110}}支付处理中{{/if}}{{if istatus == 20}}付款失败{{/if}}</i>
                            </div>
                            <ul>
                                {{for commoditys}}
                                    <li class="order-main-listli refund-inforLi-flex">
                                        <div class="shop-infor-flex">
                                            <h4>{{:commodityName}}</h4>
                                            <p>￥{{:factualAmount}}</p>
                                        </div>
                                        <div class="check-pic-flex order-main-listli-num">x{{:orderNum}}</div>
                                    </li>
                                {{/for}}
                            </ul>
                            <div class="order-main-total clearfix">
                                <p>共{{:totalNum}}件商品  合计：¥{{:actualPayAmount}}（优惠金额 ¥{{:discountAmount}}）</p>
                                <div class="right md">
                                    <a class="apply-button refund-btn" onclick="jumpOrderDetail('{{:sorderCode}}');" href="javascript:void(0);">查看详情</a>
                                    {{if istatus == 10 && actualPayAmount > 0 && actualPayAmount > actualRefundAmount }}
                                        <a class="apply-button look-btn" onclick="jumpApplyRefund('{{:sorderCode}}');" href="javascript:void(0);">申请退款</a>
                                    {{/if}}
                                    {{if istatus == 100 || istatus == 20}}
                                        <a class="apply-button look-btn" onclick="payment('{{:sorderCode}}');" href="javascript:void(0);">付款</a>
                                    {{/if}}
                                </div>
                            </div>
                        </div>
                {{/for}}
            </script>
            <script id="myRefundOrder_tmpl" type="text/x-jsrender">
                {{for #data}}
                     <div class="order-main-list">
                            <div class="order-main-listtitle relative" >
                                <h4>原订单号：{{:sorderCode}}</h4>
                                <h4>退款订单号：{{:refundCode}}</h4>
                                <p>{{formatDate:applyTime 'yyyy/MM/dd HH:mm'}}</p>
                                <i>{{if iauditStatus == 10}}待审核{{/if}}{{if iauditStatus == 30}}审核拒绝{{/if}}
                                {{if iauditStatus == 20 && irefundStatus == 10}}待退款{{/if}}
                                {{if iauditStatus == 20 && irefundStatus == 20}}退款失败{{/if}}
                                {{if iauditStatus == 20 && irefundStatus == 30}}已退款{{/if}}</i>
                            </div>
                            <ul>
                                {{for commoditys}}
                                    <li class="order-main-listli refund-inforLi-flex">
                                        <div class="shop-infor-flex">
                                            <h4>{{:commodityName}}</h4>
                                            <p>￥{{:salePrice}}</p>
                                        </div>
                                        <div class="check-pic-flex order-main-listli-num">x{{:orderNum}}</div>
                                    </li>
                                {{/for}}
                            </ul>
                            <div class="order-main-total clearfix">
                                <p>共{{:totalNum}}件商品  合计：¥{{:refundOrderAmount}} （实际退款金额 ¥{{:actualRefundAmount}}）</p>
                                <div class="right md">
                                    <a class="apply-button refund-btn" onclick="jumpRefundOrderDetail('{{:refundCode}}');" href="javascript:void(0);">查看详情</a>
                                    {{if iauditStatus == 30}}
                                        <a class="apply-button look-btn" onclick="jumpApplyRefund('{{:sorderCode}}');" href="javascript:void(0);">重新申请</a>
                                    {{/if}}
                                </div>
                            </div>
                        </div>
                {{/for}}
            </script>
        </form>
    </div>
    <%@ include file="/common/include/service-tell.jsp"%>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js?2"></script>
    <script src="${staticSource}/static/js/jquery.form.js"  type="text/javascript"></script>
    <script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
    <script src="${staticSource}/static/js/jsrender.min.js"  type="text/javascript"></script>
    <script src="${staticSource}/static/js/jsrender.converters.js"  type="text/javascript"></script>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.touchSlider.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/order-pay.js"></script>
    <script type="text/javascript">
        $(function() {
            //取得图片对应宽度的高度
            var swidth = document.body.clientWidth;
            $('#order-box').css('width',swidth);
            $('#order-main-sel').children('li').css('width',swidth);
            $dragBln = false;
            var maxwd=$("body").width();
            var midwd=5;
            $(".order-title div").each(function(){
                midwd=$(this).outerWidth(true)+midwd;
            });
            $(".order-title").width(midwd);
            var wdnum=$(".order-title div").length;// 获取个数

            $("#order-main-sel").touchSlider({
                flexible : true,
                speed : 200,
                page : parseInt("${itype}")/10,
                paging : $(".order-title div"),
                counter : function (e) {
                    var e=e||window.event;//add
                    //a 下方图标点击事件
                    $("#order-main-sel").children("li").removeClass("don").eq(e.current-1).addClass("don");
                    $(".order-title div").removeClass("hover").eq(e.current-1).addClass("hover");
                    var hh = $("#order-main-sel li").eq(e.current-1).height()+"px";
                    $('#order-box').height(hh);
                    $("#itype").val(parseInt(e.current)*10);
                    if(parseInt($("#page_index"+$("#itype").val()).val())<=0) {
                        reloadDataByItype();
                    }
                    var itt=0;
                    var lihg=$(".shop-infor-flex").height();
                    $(".order-main-listli-num").css("line-height",lihg+'px');
                    if(wdnum>4){
                        $(".order-title div").css({"width":"auto","margin":"0 2%"});
                    }
                    if(e.current<wdnum) {
                        for(var i=0;i<=e.current;i++) {
                            itt=$(".order-title div").eq(i).outerWidth(true)+itt;
                        }
                        if(itt>=maxwd) {
                            $(".order-titlebg").scrollLeft(itt-maxwd);
                        } else {
                            $(".order-titlebg").scrollLeft(0);
                        }
                    } else if(e.current==wdnum) {
                        $(".order-titlebg").scrollLeft(midwd-maxwd);
                    }
                }
            });
            $("#order-main-sel").bind("mousedown", function() {
                $dragBln = false;
            });
            $("#order-main-sel").bind("dragstart", function() {
                $dragBln = true;
            });
        });
        //数据请求
        var params = {};
        params.limit = "${pageSize}";
        params.start = 0;
        var count = 0;
        var totalProperty = 0;
        function loadQueryDate(page_index, itype, async) {
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
                    if (msg.success) {
                        totalProperty = msg.totalProperty;
                        count = Math.ceil(msg.totalProperty / params.limit);
                        if(true == async) {
                            var data = msg.data;
                            if("" != data) {
                                //渲染数据
                                var html = "";
                                if(itype == 40) {//退款订单
                                    html = $("#myRefundOrder_tmpl").render(data);
                                } else {
                                    html = $("#myOrder_tmpl").render(data);
                                }
                                if (data.length ==0) {
                                    if(parseInt(page_index) == 1) {
                                        $("#dataList"+itype).html("");
                                        Util.Loadfailure("暂无订单记录!",{subheight:90,imageUrl : staticPathRoot+'/static/images/logo.png',wrapId:"dataList"+itype,wid:"loading_failure"+itype});
                                        //page_index = parseInt(page_index)-1;
                                        $("#page_index"+itype).val(page_index);
                                        return;
                                    } else {
                                        Util.Loadfoot("数据加载失败，点击重新加载",{imageUrl:staticPathRoot+'/static/images/loading.gif',onClick:reloadData,wrapId:"dataList"+itype,wid:"loading_foot"+itype})
                                        page_index = parseInt(page_index)-1;
                                    }
                                }
                                if(parseInt(page_index) == 1) {
                                    $("#dataList"+itype).html(html);
                                } else {
                                    $("#dataList"+itype).append(html);
                                }
                                $("#page_index"+itype).val(page_index);
                                if(parseInt(page_index) >= parseInt(count)) {
                                    //Util.Loadfoot("已加载全部",{wrapId:"foot_div"+itype,wid:"loading_foot"+itype});
                                    var html = "<div class=\"footer-tips\"><span>已经到底啦</span></div>";
                                    $("#dataList"+itype).append(html);
                                } else {
                                    Util.Loadfoot("点击加载更多",{onClick:reloadData,wrapId:"foot_div"+itype,wid:"loading_foot"+itype});
                                }
                                if(parseInt(page_index) == 1) {
                                    judgescroll();
                                }
                                return;
                            }
                        }
                        if(parseInt(page_index) == 1) {
                            $("#dataList"+itype).html("");
                            Util.Loadfailure("暂无订单记录!",{subheight:90,imageUrl : staticPathRoot+'/static/images/logo.png',wrapId:"dataList"+itype,wid:"loading_failure"+itype});
                        } else {
                            Util.Loadfoot("数据加载失败，点击重新加载",{imageUrl:staticPathRoot+'/static/images/loading.gif',onClick:reloadData,wid:"loading_foot"+itype,wrapId:"foot_div"+itype})
                            page_index = parseInt(page_index)-1;
                        }
                        $("#page_index"+itype).val(page_index);
                    } else {
                        //加载失败
                        if(parseInt(page_index) == 1) {
                            Util.Loadfailure("数据加载失败!",{subheight:90,wrapId:"dataList"+itype,wid:"loading_failure"+itype});
                        } else {
                            Util.Loadfoot("数据加载失败，点击重新加载",{imageUrl:staticPathRoot+'/static/images/loading.gif',onClick:reloadData,wid:"loading_foot"+itype,wrapId:"foot_div"+itype})
                            page_index = parseInt(page_index)-1;
                        }
                        $("#page_index"+itype).val(page_index);
                    }
                }
            });
            return count;
        }
        $(window).scroll(function(){
            if($(window).scrollTop() == $(document).height() - $(window).height()) {
                var itype = $("#itype").val();
                $("#loading_foot"+itype).click();
            }
        });
        //判断不超出一屏的loading
        function judgescroll() {
            var judcroll = $(document).scrollTop();
            if (judcroll <= 0) {
                var itype = $("#itype").val();
                $("#loading_foot"+itype).click();
            }
        }
        function reloadData() {
            var itype = $("#itype").val();
            Util.Loadfoot("加载中...", {
                imageUrl : staticPathRoot + '/static/images/loading.gif',wid:"loading_foot"+itype,wrapId:"foot_div"+itype
            })
            loadQueryDate($("#page_index"+itype).val(), itype, true);
        }
        function reloadDataByItype() {
            var itype = $("#itype").val();
            Util.Loading('加载中...',{subheight:90,wrapId:"dataList"+itype,wid:"loading"+itype});
            loadQueryDate($("#page_index"+itype).val(), itype, true);
        }

        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>