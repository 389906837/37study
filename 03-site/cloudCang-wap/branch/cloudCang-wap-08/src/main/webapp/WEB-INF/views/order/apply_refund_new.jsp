<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>申请退款</title>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css"/>
</head>
<body class="refund_bg">
<%@ include file="/common/include/commonHeader.jsp" %>
<div class="refund_container">
    <form action="${ctx}/order/generateRefundOrder" method="post" id="myForm" onsubmit="return false;"
          enctype="multipart/form-data">
        <div class="refund_info m-b-07">
            <h4 class="refund_info_title">申请退款信息</h4>
            <div class="refund_info_checkAll">
                <i id="commoditityMsg">请选择您要申请退款的商品</i>
                <span><img src="${staticSource}/static/images/check_icon.png"
                           msrc="${staticSource}/static/images/check_icon_hover.png" class="check_icon">全选</span>

            </div>
        </div>

        <div class="refund_list m-b-07">
            <p class="refund_infor_num">订单号：${orderRecord.sorderCode}</p>
            <c:set var="totalActualAmount" value="0"/>
            <c:if test="${fn:length(commodities) > 0}">

                <ul>
                    <c:forEach items="${commodities}" var="item" varStatus="L">
                        <c:if test="${orderRecord.ipayType  eq 90 or (item.forderCount>item.frefundCount and item.factualAmount > 0)}">
                            <li class="refund_inforLi_flex" data="${item.id}">
                                <div <%--class="refund_check"--%> class="check-pic" data="${item.id}">
                                    <img class="check_icon"
                                         src="${staticSource}/static/images/check_icon.png"
                                         msrc="${staticSource}/static/images/check_icon_hover.png">
                                </div>
                                <div class="refund_img"><img src="${dynamicSource}${item.scommodityImg}">
                                </div>
                                <div class="shop_info">
                                    <h4>${item.scommodityName}</h4>
                                    <div class="shop_num">
                                        <a class="left">数量：</a>
                                        <div class="add_shop_num md">
                                            <span class="reduce_num" min="1"
                                                  favgActualAmount="${empty item.favgActualAmount ? 0 : item.favgActualAmount}">-</span>
                                            <input class="add_shop_input md" id="num_${item.id}" name="num_${item.id}"
                                                   type="text" value="1" readonly/>
                                            <span class="add_num"
                                                  factualAmount="${empty item.factualAmount ? 0 : item.factualAmount}"
                                                  favgActualAmount="${empty item.favgActualAmount ? 0 : item.favgActualAmount}"
                                                  frefundAmount="${empty item.frefundAmount ? 0 : item.frefundAmount}"
                                                  frefundCount="${empty item.frefundCount ? 0 : item.frefundCount }"
                                                  max="${(empty item.forderCount ? 0 : item.forderCount)-(empty item.frefundCount ? 0 : item.frefundCount)}">+</span>
                                        </div>
                                    </div>
                                    <c:set var="totalActualAmount"
                                           value="${totalActualAmount+(empty item.favgActualAmount ? 0 : item.favgActualAmount) }"/>
                                    <p class="shop_pay_money">实付金额：￥<span id="actualAmount_${item.id}"><f:formatNumber
                                            pattern="##0.00"
                                            value="${empty item.favgActualAmount ? 0 : item.favgActualAmount }"/></span>
                                    </p>
                                </div>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </c:if>

            <div class="shop_money_total">实付款:<span>￥<i id="totalRefundAmount"><f:formatNumber pattern="##0.00"
                                                                                               value="${empty totalActualAmount ? 0 : totalActualAmount }"/></i></span>
            </div>
        </div>

        <div class="refund_money m-b-07">
            <h4>申请退款金额：￥<i class="totalprice" id="applyRefundAmount">0.00</i></h4>
        </div>
        <input id="commoditieIds" name="commoditieIds" type="hidden" value=""/>
        <input id="orderCode" name="orderCode" type="hidden" value="${orderRecord.sorderCode}"/>

        <div class="refund_reason m-b-07">
            <h4>申请原因</h4>
            <textarea placeholder="请输入退款原因" id="refundReason" onkeyup="checkLength(this);" name="refundReason"
                      class="refund_textarea" oninput="if(value.length>50)value=value.slice(0,50);"
                      maxlength="50"></textarea>
            <i class="refund_reason_tips">0/50</i>
        </div>
        <c:if test="${(empty uploadNum ? 0 : uploadNum) > 0}">
            <div class="refund_pic_sm">
                <h4>图片说明</h4>
                <ul class="clearfix">
                    <c:forEach begin="1" end="${uploadNum}" var="item">
                        <input type="hidden" id="pic_${item}" name="pic_${item}" value=""/>
                        <li onclick="selectPhoto(this);" data="${item}">
                            <img style="width:1.65rem;height:1.65rem;vertical-align:middle;"
                                 src="${staticSource}/static/images/refund-jiapic.png"/>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
        <div class="p_1">
            <a class="reg_btn" id="myFormSub" href="javascript:void(0);">提 交</a>
        </div>
    </form>
</div>

<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script src="${staticSource}/static/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/formatUtil.js"></script>
<c:if test="${alipay eq 1}">
    <%@ include file="/common/include/alipayInit.jsp" %>
</c:if>
<c:if test="${wechat eq 1}">
    <%@ include file="/common/include/wechatInit.jsp" %>
</c:if>
<script type="text/javascript">
    function checkLength(obj) {
        var val = $(obj).val();
        if (Util.isEmpty(val)) {
            $(".refund_reason_tips").html("0/50");
        } else {
            $(".refund_reason_tips").html(val.length + "/50");
        }
    }
    var src, msrc;
    var srcImg, msrcImg;
    var nnum;

    $(function () {
        //减少
        $(".reduce_num").click(function () {
            var sid = $(this).parents(".refund_inforLi_flex").attr("data");//商品ID

            nnum = $(this).siblings("input").val();
            nnum = Number(nnum) - 1;
            var tempNow = 0;
            var temp = 0;
            if (nnum >= 1) {
                $(this).siblings("input").val(nnum);
                tempNow = Number($(this).attr("favgActualAmount")) * Number(nnum);
                temp = Number($("#actualAmount_" + sid).html()) - tempNow;//本次减少单个商品金额
                $("#actualAmount_" + sid).html(formateMoneyNo(tempNow));
                $("#totalRefundAmount").html(formateMoneyNo(Number($("#totalRefundAmount").html()) - temp));
                var imgSrc = $(this).parents(".refund-inforLi-flex").find(".check-pic").find("img").attr("msrc");
                if (imgSrc.indexOf("check_icon_hover") == -1) {
                    $("#applyRefundAmount").html(formateMoneyNo(Number($("#applyRefundAmount").html()) - temp));
                }
            }
        });
        //增加
        $(".add_num").click(function () {
            var sid = $(this).parents(".refund_inforLi_flex").attr("data");//商品ID
            nnum = $(this).siblings("input").val();
            nnum = Number(nnum) + 1;
            var tempNow = 0;
            var temp = 0;
            if (nnum >= Number($(this).attr("max"))) {//本次退款数量 = 原订单数量-原订单已退款数量
                nnum = Number($(this).attr("max"));
                //本次退款实付金额 = 原订单实付金额-原订单已退款金额
                tempNow = Number($(this).attr("factualAmount")) - Number($(this).attr("frefundAmount"));
                temp = tempNow - Number($("#actualAmount_" + sid).html());//本次新增单个商品金额
            } else {
                tempNow = Number($(this).attr("favgActualAmount")) * Number(nnum);
                temp = tempNow - Number($("#actualAmount_" + sid).html());//本次新增单个商品金额
            }
            $("#actualAmount_" + sid).html(formateMoneyNo(tempNow));
            $("#totalRefundAmount").html(formateMoneyNo(Number($("#totalRefundAmount").html()) + temp));
            var imgSrc = $(this).parents(".refund_inforLi_flex").find(".check-pic").find("img").attr("msrc");
            if (imgSrc.indexOf("check_icon_hover") == -1) {
                $("#applyRefundAmount").html(formateMoneyNo(Number($("#applyRefundAmount").html()) + temp));
            }
            $(this).siblings("input").val(nnum);
        });
        //全选
        $(".refund_info_checkAll .check_icon").click(function () {
            src = $(this).attr("src");
            msrc = $(this).attr("msrc");
            $(this).attr("src", msrc);
            $(this).attr("msrc", src);

            $("#commoditieIds").val("");
            var str = "";
            if (src.indexOf("check_icon_hover") == -1) {
                //全选
                $(".check-pic img").each(function () {
                    var sid = $(this).parent().attr("data");
                    str += sid + ",";
                    srcImg = $(this).attr("src");
                    msrcImg = $(this).attr("msrc");
                    if (srcImg.indexOf("check_icon_hover") == -1) {
                        $(this).attr("src", msrcImg);
                        $(this).attr("msrc", srcImg);
                    }
                });
                if (str.length > 0) {
                    $("#commoditieIds").val(str);
                }
                //$("#commoditityMsg").hide();
                $("#applyRefundAmount").html(formateMoneyNo(Number($("#totalRefundAmount").html())));
            } else {
                $(".check-pic img").each(function () {
                    srcImg = $(this).attr("src");
                    msrcImg = $(this).attr("msrc");
                    if (srcImg.indexOf("check_icon_hover") != -1) {
                        $(this).attr("src", msrcImg);
                        $(this).attr("msrc", srcImg);
                    }
                });
                //取消全选
                //$("#commoditityMsg").show();
                $("#applyRefundAmount").html("0.00");
            }
        });
        //图片选择
        $(".check-pic img").click(function () {
            srcImg = $(this).attr("src");
            msrcImg = $(this).attr("msrc");
            $(this).attr("src", msrcImg);
            $(this).attr("msrc", srcImg);

            var commoditieIds = $("#commoditieIds").val();
            var temp = $(this).parent().attr("data");
            if (srcImg.indexOf("check_icon_hover") == -1) {//选择
                if (commoditieIds.indexOf(temp) == -1) {
                    commoditieIds += temp + ",";
                    $("#applyRefundAmount").html(formateMoneyNo(Number($("#applyRefundAmount").html()) + Number($("#actualAmount_" + temp).html())));
                }
                //增加全选的选择
                var flag = true;
                $(".check-pic img").each(function () {
                    var tempSrcImg = $(this).attr("src");
                    if (tempSrcImg.indexOf("check_icon_hover") == -1) {
                        flag = false;
                        return false
                    }
                });
                if (flag) {
                    //增加全选
                    src = $(".refund_info_checkAll .check_icon").attr("src");
                    msrc = $(".refund_info_checkAll .check_icon").attr("msrc");
                    if (src.indexOf("check_icon_hover") == -1) {
                        $(".refund_info_checkAll .check_icon").attr("src", msrc);
                        $(".refund_info_checkAll .check_icon").attr("msrc", src);
                    }
                    $("#applyRefundAmount").html(formateMoneyNo(Number($("#totalRefundAmount").html())));
                }
            } else {//取消选择
                commoditieIds = commoditieIds.replace(temp + ",", "");
                $("#applyRefundAmount").html(formateMoneyNo(Number($("#applyRefundAmount").html()) - Number($("#actualAmount_" + temp).html())));
                //取消全选的选择
                src = $(".refund_info_checkAll .check_icon").attr("src");
                msrc = $(".refund_info_checkAll .check_icon").attr("msrc");
                if (src.indexOf("check_icon_hover") != -1) {
                    $(".refund_info_checkAll .check_icon").attr("src", msrc);
                    $(".refund_info_checkAll .check_icon").attr("msrc", src);
                }
                //$("#commoditityMsg").hide();
            }
            $("#commoditieIds").val(commoditieIds);
            if (Util.isEmpty(commoditieIds)) {
                //$("#commoditityMsg").show();
                $("#applyRefundAmount").html("0.00");
            } else {
                //$("#commoditityMsg").hide();
            }
        });

        //退款保存
        $("#myFormSub").click(function () {
            if (Util.isEmpty($("#commoditieIds").val())) {
                Util.Alert("请选择退款商品");
                return;
            }
            /* if(Util.isEmpty($("#refundReason").val())) {
             Util.Alert("退款原因不能为空");
             return;
             }*/
            Util.Waiting({wid: 'wait_popup'});
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
                        Util.waitingClose($("#wait_popup"), function () {
                            Util.localHref(msg.data);
                        });
                    } else {
                        Util.waitingClose($("#wait_popup"), function () {
                            Util.Alert(msg.msg);
                        });
                    }
                }
            });
        });
    });
    function selectPhoto(obj) {
        if (isAliPay()) {
            alipayPhoto($(obj), $("#pic_" + $(obj).attr("data")));
        } else if (isWechatPay()) {
            wechatPhoto($(obj), $("#pic_" + $(obj).attr("data")));
        }
    }
    <%@ include file="/common/include/baidu.jsp"%>
    <!-- -->
</script>

</body>
</html>