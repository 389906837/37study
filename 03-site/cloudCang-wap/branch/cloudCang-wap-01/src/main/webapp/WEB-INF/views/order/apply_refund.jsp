<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>申请退款</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css">
    <link rel="stylesheet" href="${staticSource}/static/css/style.css?1">
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp"%>
    <div class="container">
        <div class="container-c">
            <form action="${ctx}/order/generateRefundOrder" method="post" id="myForm" onsubmit="return false;" enctype="multipart/form-data">
                <div class="refund" style="margin-top: 1rem;">
                    <div class="refund-infor">
                        <div class="m-l">
                            <h4 class="refund-infor-title">申请退款信息</h4>
                            <p class="refund-infor-num">订单号：${orderRecord.sorderCode}</p>
                            <div class="refund-infor-checkAll">
                                <img class="check-icon" src="${staticSource}/static/images/check-icon.png" msrc="${staticSource}/static/images/check-icon-hover.png"/>
                                全选
                                <i id="commoditityMsg">请选择您要申请退款的商品</i>
                            </div>
                        </div>
                    </div>
                    <c:set var="totalActualAmount" value="0" />
                    <div class="refund-list">
                        <c:if test="${fn:length(commodities) > 0}">
                            <ul>
                                <c:forEach items="${commodities}" var="item" varStatus="L">
                                    <c:if test="${item.forderCount>item.frefundCount and item.factualAmount > 0}">
                                        <li class="refund-inforLi-flex" data="${item.id}">
                                            <div class="check-pic check-pic-flex" data="${item.id}">
                                                <img class="check-icon" src="${staticSource}/static/images/check-icon.png" msrc="${staticSource}/static/images/check-icon-hover.png" />
                                            </div>
                                            <div class="shop-infor shop-infor-flex">
                                                <h4 >商品名称：${item.scommodityName}</h4>
                                                <div class="shop-num">
                                                    <a class="left">数量：</a>
                                                    <div class="add-shop-num md">
                                                        <span class="reduce-num" min="1" favgActualAmount="${empty item.favgActualAmount ? 0 : item.favgActualAmount}">-</span>
                                                        <input class="add-shop-input md" id="num_${item.id}" name="num_${item.id}" type="text" value="1" readonly />
                                                        <span class="add-num" factualAmount="${empty item.factualAmount ? 0 : item.factualAmount}" favgActualAmount="${empty item.favgActualAmount ? 0 : item.favgActualAmount}" frefundAmount="${empty item.frefundAmount ? 0 : item.frefundAmount}" frefundCount="${empty item.frefundCount ? 0 : item.frefundCount }" max="${(empty item.forderCount ? 0 : item.forderCount)-(empty item.frefundCount ? 0 : item.frefundCount)}">+</span>
                                                    </div>
                                                </div>
                                                <c:set var="totalActualAmount" value="${totalActualAmount+(empty item.favgActualAmount ? 0 : item.favgActualAmount) }" />
                                                <p class="shop-pay-money">实付金额：￥<span id="actualAmount_${item.id}"><f:formatNumber pattern="##0.00" value="${empty item.favgActualAmount ? 0 : item.favgActualAmount }"/></span></p>
                                                <%--<p class="shop-pay-money"><c:if test="${(empty item.frefundAmount ? 0 : item.frefundAmount) > 0}">已退款金额：<f:formatNumber pattern=",##0.00" value="${empty item.frefundAmount ? 0 : item.frefundAmount}"/>，
                                                </c:if>实付金额：￥<f:formatNumber pattern=",##0.00" value="${(empty item.factualAmount ? 0 : item.factualAmount)-(empty item.frefundAmount ? 0 : item.frefundAmount)}"/></p>--%>
                                            </div>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                            <div class="shop-money-total">实付总额:<span>￥<i id="totalRefundAmount"><f:formatNumber pattern="##0.00" value="${empty totalActualAmount ? 0 : totalActualAmount }"/></i></span></div>
                        </c:if>
                        <%--<div class="shop-money-total"><c:if test="${(empty orderRecord.factualRefundAmount ? 0 : orderRecord.factualRefundAmount) > 0}">已退款金额：<f:formatNumber pattern=",##0.00" value="${empty orderRecord.factualRefundAmount ? 0 : orderRecord.factualRefundAmount}"/>，</c:if>
                            实付款:<span>￥<f:formatNumber pattern=",##0.00" value="${(empty orderRecord.factualPayAmount ? 0 : orderRecord.factualPayAmount)-(empty orderRecord.factualRefundAmount ? 0 : orderRecord.factualRefundAmount) }"/></span></div>--%>
                    </div>
                    <div class="refund-money">
                        <h4>申请退款金额：￥<i class="totalprice" id="applyRefundAmount">0.00</i></h4>
                    </div>
                </div>
                <input id="commoditieIds" name="commoditieIds" type="hidden" value="" />
                <input id="orderCode" name="orderCode" type="hidden" value="${orderRecord.sorderCode}" />
                <div class="refund-reason">
                    <h4>退款原因</h4>
                    <div class="relative">
                        <textarea placeholder="请输入退款原因" id="refundReason" onkeyup="checkLength(this);" name="refundReason" class="refund-textarea" oninput="if(value.length>50)value=value.slice(0,50);" maxlength="50"></textarea>
                        <i class="refund-reason-tips">0/50</i>
                    </div>
                </div>
                <c:if test="${(empty uploadNum ? 0 : uploadNum) > 0}">
                    <div class="refund-pic-sm refund-reason" style="padding-bottom:0;">
                        <h4>图片说明</h4>
                        <ul class="clearfix">
                            <c:forEach begin="1" end="${uploadNum}" var="item">
                                <input type="hidden" id="pic_${item}" name="pic_${item}" value="" />
                                <li onclick="selectPhoto(this);" data="${item}">
                                    <img style="width:1.65rem;height:1.65rem;vertical-align:middle;"
                                         src="${staticSource}/static/images/refund-jiapic.png" />
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
                <div style="margin:1rem 0;">
                    <a class="button-icon check-btn md" id="myFormSub" href="javascript:void(0);">提交</a>
                </div>
            </form>
        </div>
    </div>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
    <script src="${staticSource}/static/js/jquery.form.js"  type="text/javascript"></script>
    <script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/formatUtil.js"></script>
    <script type="text/javascript">

        function checkLength(obj) {
            var val = $(obj).val();
            if (Util.isEmpty(val)) {
                $(".refund-reason-tips").html("0/50");
            } else {
                $(".refund-reason-tips").html(val.length+"/50");
            }
        }
        var src,msrc;
        var srcImg,msrcImg;
        var nnum;
        $(function() {
            setTitle("申请退款");
            $('.container-c').css('height', $(window.top).height());
            //减少
            $(".reduce-num").click(function(){
                var sid = $(this).parents(".refund-inforLi-flex").attr("data");//商品ID

                nnum = $(this).siblings("input").val();
                nnum = Number(nnum) - 1;
                var tempNow = 0;
                var temp = 0;
                if(nnum>=1){
                    $(this).siblings("input").val(nnum);
                    tempNow = Number($(this).attr("favgActualAmount")) * Number(nnum);
                    temp = Number($("#actualAmount_"+sid).html()) - tempNow;//本次减少单个商品金额
                    $("#actualAmount_"+sid).html(formateMoneyNo(tempNow));
                    $("#totalRefundAmount").html(formateMoneyNo(Number($("#totalRefundAmount").html()) - temp));
                    var imgSrc = $(this).parents(".refund-inforLi-flex").find(".check-pic").find("img").attr("msrc");
                    if(imgSrc.indexOf("check-icon-hover") == -1) {
                        $("#applyRefundAmount").html(formateMoneyNo(Number($("#applyRefundAmount").html()) - temp));
                    }
                }
            });
            //增加
            $(".add-num").click(function(){
                var sid = $(this).parents(".refund-inforLi-flex").attr("data");//商品ID
                nnum = $(this).siblings("input").val();
                nnum = Number(nnum) + 1;
                var tempNow = 0;
                var temp = 0;
                if(nnum>=Number($(this).attr("max"))) {//本次退款数量 = 原订单数量-原订单已退款数量
                    nnum = Number($(this).attr("max"));
                    //本次退款实付金额 = 原订单实付金额-原订单已退款金额
                    tempNow = Number($(this).attr("factualAmount")) - Number($(this).attr("frefundAmount"));
                    temp = tempNow - Number($("#actualAmount_"+sid).html());//本次新增单个商品金额
                } else {
                    tempNow = Number($(this).attr("favgActualAmount")) * Number(nnum);
                    temp = tempNow - Number($("#actualAmount_"+sid).html());//本次新增单个商品金额
                }
                $("#actualAmount_"+sid).html(formateMoneyNo(tempNow));
                $("#totalRefundAmount").html(formateMoneyNo(Number($("#totalRefundAmount").html()) + temp));
                var imgSrc = $(this).parents(".refund-inforLi-flex").find(".check-pic").find("img").attr("msrc");
                if(imgSrc.indexOf("check-icon-hover") == -1) {
                    $("#applyRefundAmount").html(formateMoneyNo(Number($("#applyRefundAmount").html()) + temp));
                }
                $(this).siblings("input").val(nnum);
            });
            //全选
            $(".refund-infor-checkAll .check-icon").click(function(){
                src=$(this).attr("src");
                msrc=$(this).attr("msrc");
                $(this).attr("src",msrc);
                $(this).attr("msrc",src);

                $("#commoditieIds").val("");
                var str = "";
                if (src.indexOf("check-icon-hover") == -1) {
                    //全选
                    $(".check-pic img").each(function() {
                        var sid = $(this).parent().attr("data");
                        str += sid+",";
                        srcImg=$(this).attr("src");
                        msrcImg=$(this).attr("msrc");
                        if(srcImg.indexOf("check-icon-hover") == -1) {
                            $(this).attr("src",msrcImg);
                            $(this).attr("msrc",srcImg);
                        }
                    });
                    if(str.length > 0) {
                        $("#commoditieIds").val(str);
                    }
                    $("#commoditityMsg").hide();
                    $("#applyRefundAmount").html(formateMoneyNo(Number($("#totalRefundAmount").html())));
                } else {
                    $(".check-pic img").each(function() {
                        srcImg=$(this).attr("src");
                        msrcImg=$(this).attr("msrc");
                        if(srcImg.indexOf("check-icon-hover") != -1) {
                            $(this).attr("src",msrcImg);
                            $(this).attr("msrc",srcImg);
                        }
                    });
                    //取消全选
                    $("#commoditityMsg").show();
                    $("#applyRefundAmount").html("0.00");
                }
            });
            //图片选择
            $(".check-pic img").click(function(){
                srcImg=$(this).attr("src");
                msrcImg=$(this).attr("msrc");
                $(this).attr("src",msrcImg);
                $(this).attr("msrc",srcImg);

                var commoditieIds = $("#commoditieIds").val();
                var temp = $(this).parent().attr("data");
                if(srcImg.indexOf("check-icon-hover") == -1) {//选择
                    if(commoditieIds.indexOf(temp) == -1) {
                        commoditieIds += temp + ",";
                        $("#applyRefundAmount").html(formateMoneyNo(Number($("#applyRefundAmount").html())+Number($("#actualAmount_"+temp).html())));
                    }
                    //增加全选的选择
                    var flag = true;
                    $(".check-pic img").each(function() {
                        var tempSrcImg=$(this).attr("src");
                        if(tempSrcImg.indexOf("check-icon-hover") == -1) {
                            flag = false;
                            return false
                        }
                    });
                    if(flag) {
                        //增加全选
                        src=$(".refund-infor-checkAll .check-icon").attr("src");
                        msrc=$(".refund-infor-checkAll .check-icon").attr("msrc");
                        if (src.indexOf("check-icon-hover") == -1) {
                            $(".refund-infor-checkAll .check-icon").attr("src",msrc);
                            $(".refund-infor-checkAll .check-icon").attr("msrc",src);
                        }
                        $("#applyRefundAmount").html(formateMoneyNo(Number($("#totalRefundAmount").html())));
                    }
                } else {//取消选择
                    commoditieIds = commoditieIds.replace(temp+",", "");
                    $("#applyRefundAmount").html(formateMoneyNo(Number($("#applyRefundAmount").html())-Number($("#actualAmount_"+temp).html())));
                    //取消全选的选择
                    src=$(".refund-infor-checkAll .check-icon").attr("src");
                    msrc=$(".refund-infor-checkAll .check-icon").attr("msrc");
                    if (src.indexOf("check-icon-hover") != -1) {
                        $(".refund-infor-checkAll .check-icon").attr("src",msrc);
                        $(".refund-infor-checkAll .check-icon").attr("msrc",src);
                    }
                    $("#commoditityMsg").hide();
                }
                $("#commoditieIds").val(commoditieIds);
                if(Util.isEmpty(commoditieIds)) {
                    $("#commoditityMsg").show();
                    $("#applyRefundAmount").html("0.00");
                } else {
                    $("#commoditityMsg").hide();
                }
            });

            //退款保存
            $("#myFormSub").click(function() {
                if(Util.isEmpty($("#commoditieIds").val())) {
                    Util.Alert("请选择退款商品");
                    return;
                }
               /* if(Util.isEmpty($("#refundReason").val())) {
                    Util.Alert("退款原因不能为空");
                    return;
                }*/
                Util.Waiting({wid : 'wait_popup'});
                $('#myForm').ajaxSubmit({
                    type: "post",
                    dataType: "json",
                    async: true,
                    error: function (msg) {
                        $("body").html(msg.responseText);
                    },
                    success: function (msg) {
                        //成功返回
                        if (msg.success) {
                            Util.waitingClose($("#wait_popup"), function() {
                                Util.localHref(msg.data);
                            });
                        } else {
                            Util.waitingClose($("#wait_popup"), function() {
                                Util.Alert(msg.msg);
                            });
                        }
                    }
                });
            });
        });

        function selectPhoto(obj) {
            if (isAliPay()) {
                parent.alipayPhoto($(obj), $("#pic_"+$(obj).attr("data")));
            } else if (isWechatPay()) {
                parent.wechatPhoto($(obj), $("#pic_"+$(obj).attr("data")));
            }
        }
        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>