<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>退款订单详情</title>
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css?1"/>
</head>
<body class="refdetails_bg">
<%@ include file="/common/include/commonHeader.jsp" %>
<%@ include file="/common/include/socket.jsp" %>
<div class="refdetails_container">
    <div class="refdetails_head md">
        <c:if test="${refundAudit.iauditStatus eq 10}">待审核</c:if>
        <c:if test="${refundAudit.iauditStatus eq 30}">审核驳回</c:if>
        <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 10}">审核通过退款中</c:if>
        <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 20}">退款失败</c:if>
        <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 30}">订单已退款</c:if>
    </div>

    <div class="refdetails_main m-b-07">
        <c:set var="ffirstDiscountAmount" value="0"/>
        <c:set var="fpromotionDiscountAmount" value="0"/>
        <c:set var="fcouponDeductionAmount" value="0"/>
        <c:if test="${fn:length(commodities) > 0}">
        <ul class="refdetails_list">
            <c:forEach items="${commodities}" var="item" varStatus="L">
                <c:set var="ffirstDiscountAmount"
                       value="${item.ffirstDiscountAmount+ffirstDiscountAmount}"/>
                <c:set var="fpromotionDiscountAmount"
                       value="${item.fpromotionDiscountAmount+fpromotionDiscountAmount}"/>
                <c:set var="fcouponDeductionAmount"
                       value="${item.fcouponDeductionAmount+fcouponDeductionAmount}"/>
                <li class="refdetails_flex">
                    <img src="${dynamicSource}${item.scommodityImg}">
                    <div class="refdetails_info">
                        <h4>${item.scommodityName}</h4>
                        <p>
                            <span>￥<f:formatNumber pattern=",##0.00" value="${item.favgActualAmount}"/> </span>
                            <span>￥<i><f:formatNumber pattern=",##0.00"
                                                      value="${item.fcommodityPrice}"/></i></span>
                        </p>

                    </div>
                    <div class="refdetails_num">
                        x${item.frefundCount}
                    </div>
                </li>
            </c:forEach>
        </ul>
        </c:if>
        <div class="refdetails_priceinfo">
            <div class="refdetails_discounts">
                原商品总价<span>￥<f:formatNumber pattern=",##0.00" value="${refundAudit.ftotalAmount}"/></span>
            </div>
            <c:if test="${!empty ffirstDiscountAmount and ffirstDiscountAmount > 0}">
                <div class="refdetails_discounts">
                    首单优惠<span>-￥<f:formatNumber pattern=",##0.00" value="${ffirstDiscountAmount}"/></span>
                </div>
            </c:if>
            <c:if test="${!empty fpromotionDiscountAmount and fpromotionDiscountAmount > 0}">
                <div class="refdetails_discounts">
                        ${promotionTitle}<span>-￥<f:formatNumber pattern=",##0.00"
                                                                 value="${fpromotionDiscountAmount}"/></span>
                </div>
            </c:if>
            <c:if test="${!empty fcouponDeductionAmount and fcouponDeductionAmount > 0}">
                <div class="refdetails_discounts">
                    优惠券抵扣<span>-￥<f:formatNumber pattern=",##0.00" value="${fcouponDeductionAmount}"/></span>
                </div>
            </c:if>
            <div class="shop_money">
                实际退款<span>￥<i class="totalprice"><f:formatNumber pattern=",##0.00" value="${empty refundAudit.factualRefundAmount ? 0 : refundAudit.factualRefundAmount}"/></i></span>
            </div>
        </div>
    </div>
    <div class="refdetails_data m-b-07">
        <p>原订单号：${refundAudit.sorderCode}</p>
        <p>退款单号：${refundAudit.srefundCode}</p>
        <p>申请时间：<f:formatDate value="${refundAudit.tapplyTime}" pattern="yyyy/MM/dd HH:mm"/></p>
    </div>
    <c:if test="${!empty refundAudit.srefundReason}">
        <div class="refdetails_reason m-b-07">
            <h4>退款原因</h4>
            <div class="relative">
                    ${refundAudit.srefundReason}
            </div>
        </div>
    </c:if>
    <c:if test="${fn:length(imgDescs) > 0}">
        <div class="refdetails_pic m-b-07">
            <h4>图片说明</h4>
            <ul class="clearfix">
                <c:forEach var="item" items="${imgDescs}" varStatus="L">
                    <li onclick="previewImage('${dynamicSource}${item.simgPath}',${L.index});">
                        <img src="${dynamicSource}${item.simgPath}">
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${refundAudit.iauditStatus eq 30}">
        <div class="refdetails_reason m-b-07">
            <h4>退款审核驳回原因</h4>
            <div class="relative">
                    ${refundAudit.sauditRefuseReason}
            </div>
        </div>
    </c:if>
    <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 20}">
        <div class="refdetails_reason m-b-07">
            <h4>退款失败原因</h4>
            <div class="relative">
                    ${refundAudit.srefundFailureReason}
            </div>
        </div>
    </c:if>
    <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 30}">
        <div class="refdetails_succeedtimer m-b-07">
            <a class="button-icon refund-date md" href="">退款成功时间：<f:formatDate
                    value="${refundAudit.trefundCompleteTime}" pattern="yyyy/MM/dd HH:mm"/></a>
        </div>
    </c:if>
    <%--<div class="footer-tips">
        <span>已经到底啦</span>
    </div>--%>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script src="${staticSource}/static/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<c:if test="${alipay eq 1}">
    <%@ include file="/common/include/alipayInit.jsp" %>
</c:if>
<c:if test="${wechat eq 1}">
    <%@ include file="/common/include/wechatInit.jsp" %>
</c:if>
<script type="text/javascript">

    function previewImage(path, index) {
        if (isWechatPay()) {
            var array = [];
            $("#refdetails_pic").find("li").each(function (i) {
                array[i] = $(this).find("img").attr("src")
            });
            wechatPreviewImage(path, array);
        } else if (isAliPay()) {
            var array = [];
            $("#refdetails_pic").find("li").each(function (i) {
                var opt = {u: $(this).find("img").attr("src")};
                array[i] = opt;
            });
            ailpayPreviewImage(index, array)
        }
    }
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>