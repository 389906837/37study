<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>退款订单详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
<link rel="stylesheet" href="${staticSource}/static/css/style.css" />
</head>
<body>
<%@ include file="/common/include/commonHeader.jsp"%>
    <div class="container">
        <div class="container-c">
            <div class="refundrecord-details-main">
                <div class="refundrecord-details-list">
                    <div class="refundrecord-main-listtitle relative" >
                        <h4>原订单号：${refundAudit.sorderCode}</h4>
                        <i><c:if test="${refundAudit.iauditStatus eq 10}">待审核</c:if>
                            <c:if test="${refundAudit.iauditStatus eq 30}">审核拒绝</c:if>
                            <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 10}">待退款</c:if>
                            <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 20}">退款失败</c:if>
                            <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 30}">订单已退款</c:if></i>
                    </div>
                    <div class="refundrecord-main-listtitle relative m-t1" >
                        <h4>退款单号：${refundAudit.srefundCode}</h4>
                        <span>申请时间：<f:formatDate value="${refundAudit.tapplyTime}" pattern="yyyy/MM/dd HH:mm" /></span>
                    </div>
                    <c:set var="ffirstDiscountAmount" value="0" />
                    <c:set var="fpromotionDiscountAmount" value="0" />
                    <c:set var="fcouponDeductionAmount" value="0" />
                    <c:if test="${fn:length(commodities) > 0}">
                        <ul>
                            <c:forEach items="${commodities}" var="item" varStatus="L">
                                <c:set var="ffirstDiscountAmount" value="${item.ffirstDiscountAmount+ffirstDiscountAmount}" />
                                <c:set var="fpromotionDiscountAmount" value="${item.fpromotionDiscountAmount+fpromotionDiscountAmount}" />
                                <c:set var="fcouponDeductionAmount" value="${item.fcouponDeductionAmount+fcouponDeductionAmount}" />
                                <li class="refundrecord-main-listli refund-inforLi-flex">
                                    <div class="shop-infor-flex">
                                        <h4>${item.scommodityName}</h4>
                                        <p>￥<f:formatNumber pattern=",##0.00" value="${item.favgActualAmount}"/>
                                            <span>￥<f:formatNumber pattern=",##0.00" value="${item.fcommodityPrice}"/></span></p>
                                    </div>
                                    <div class="check-pic-flex refundrecord-main-listli-num trg">
                                        x${item.frefundCount}
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                    <div class="order-details-priceinfo">
                        <div class="order-details-discounts">
                            原商品总价<span>￥<f:formatNumber pattern=",##0.00" value="${refundAudit.ftotalAmount}"/></span>
                        </div>
                        <c:if test="${!empty ffirstDiscountAmount and ffirstDiscountAmount > 0}">
                            <div class="order-details-discounts">
                                首单优惠<span>-￥<f:formatNumber pattern=",##0.00" value="${ffirstDiscountAmount}"/></span>
                            </div>
                        </c:if>
                        <c:if test="${!empty fpromotionDiscountAmount and fpromotionDiscountAmount > 0}">
                            <div class="order-details-discounts">
                                    ${promotionTitle}<span>-￥<f:formatNumber pattern=",##0.00" value="${fpromotionDiscountAmount}"/></span>
                            </div>
                        </c:if>
                        <c:if test="${!empty fcouponDeductionAmount and fcouponDeductionAmount > 0}">
                            <div class="order-details-discounts">
                                优惠券抵扣<span>-￥<f:formatNumber pattern=",##0.00" value="${fcouponDeductionAmount}"/></span>
                            </div>
                        </c:if>
                        <%--<div class="order-details-discounts">
                            申请退款总金额<span>￥<f:formatNumber pattern=",##0.00" value="${refundAudit.fapplyRefundAmount}"/></span>
                        </div>--%>
                        <div class="order-details-discounts shop-money">
                            实际退款总金额<span>￥<i class="totalprice"><f:formatNumber pattern=",##0.00" value="${empty refundAudit.factualRefundAmount ? 0 : refundAudit.factualRefundAmount}"/></i></span>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${!empty refundAudit.srefundReason}">
                <div class="refund-reason">
                    <h4>退款原因</h4>
                    <div class="relative">
                        <div class="refund-textarea">${refundAudit.srefundReason}</div>
                    </div>
                </div>
            </c:if>
            <c:if test="${fn:length(imgDescs) > 0}">
                <div class="refund-pic-sm refund-reason" style="padding-bottom:0;">
                    <h4>图片说明</h4>
                    <ul class="clearfix" id="refund-pic">
                        <c:forEach var="item" items="${imgDescs}" varStatus="L">
                            <li onclick="previewImage('${dynamicSource}${item.simgPath}',${L.index});">
                                <img src="${dynamicSource}${item.simgPath}">
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
            <c:if test="${refundAudit.iauditStatus eq 30}">
                <div class="refund-reason">
                    <h4>审核驳回原因</h4>
                    <div class="relative">
                        <div class="refund-textarea">${refundAudit.sauditRefuseReason}</div>
                    </div>
                </div>
            </c:if>
            <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 20}">
                <div class="refund-reason">
                    <h4>退款失败原因</h4>
                    <div class="relative">
                        <div class="refund-textarea">${refundAudit.srefundFailureReason}</div>
                    </div>
                </div>
            </c:if>
            <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 30}">
                <div style="margin:1rem 0;">
                    <a class="button-icon refund-date md" href="">退款成功时间：<f:formatDate value="${refundAudit.trefundCompleteTime}" pattern="yyyy/MM/dd HH:mm" /></a>
                </div>
            </c:if>
<%--
            <c:if test="${refundAudit.iauditStatus eq 30}">
                <div class="order-main-total clearfix">
                    <div class="right md">
                        <a class="apply-button look-btn" onclick="jumpApplyRefund('${refundAudit.sorderCode}');" href="javascript:void(0);">重新申请</a>
                    </div>
                </div>
            </c:if>--%>
            <div class="footer-tips">
                <span>已经到底啦</span>
            </div>
        </div>
        <%@ include file="/common/include/service-tell.jsp"%>
    </div>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
    <script src="${staticSource}/static/js/jquery.form.js"  type="text/javascript"></script>
    <script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
    <script type="text/javascript">
        $(function () {
            setTitle("退款详情");
            $('.container-c').css('height', $(window.top).height());
        });
        function previewImage(path,index) {
            if (isWechatPay()) {
                var array = [];
                $("#refund-pic").find("li").each(function (i) {
                    array[i] = $(this).find("img").attr("src")
                });
                parent.wechatPreviewImage(path,array);
            } else if (isAliPay()) {
                var array = [];
                $("#refund-pic").find("li").each(function (i) {
                    var opt = {u:$(this).find("img").attr("src")};
                    array[i] = opt;
                });
                parent.ailpayPreviewImage(index,array)
            }
        }
        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>