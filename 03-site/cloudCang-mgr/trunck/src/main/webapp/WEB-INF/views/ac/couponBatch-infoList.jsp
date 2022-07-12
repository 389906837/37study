<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>优惠券批量下发详情</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%">发放批次编号：</td>
                <td class="text-right" style="width:32%">${couponBatch.sbatchCode}</td>
                <td class="text-left" style="width:18%">券类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponBatch.icouponType == 10}">现金券</c:if>
                    <c:if test="${couponBatch.icouponType == 20}">满减券</c:if>
                    <c:if test="${couponBatch.icouponType == 30}">抵扣券</c:if>
                    <c:if test="${couponBatch.icouponType == 40}">商品券</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">券面值(元)：</td>
                <td class="text-right"  style="width:32%"><fmt:formatNumber value="${couponBatch.fcouponValue}" pattern="#0.##元"/></td>
                <td class="text-left"  style="width:18%">券有效期天数：</td>
                <td class="text-right"  style="width:32%">${couponBatch.scouponValidityValue}天</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">券生效日期：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponBatch.dcouponEffectiveDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td class="text-left"  style="width:18%">券失效日期：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponBatch.dcouponExpiryDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">下发类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponBatch.itype == 0 }">优惠券</c:if>
                    <c:if test="${couponBatch.itype == 1 }">优惠券码</c:if>
                    <c:if test="${couponBatch.itype == 2 }">营销活动券</c:if>
                </td>
                <td class="text-left" style="width:18%">发放数量：</td>
                <td class="text-right"  style="width:32%">${couponBatch.ibatchNumber}张</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">审核状态：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponBatch.istatus == 10 }">草稿</c:if>
                    <c:if test="${couponBatch.istatus == 11 }">待审核</c:if>
                    <c:if test="${couponBatch.istatus == 20 }">审核通过</c:if>
                    <c:if test="${couponBatch.istatus == 21 }">审核拒绝</c:if>
                </td>
                <td class="text-left" style="width:18%">使用订单实付金额(元)：</td>
                <td class="text-right" style="width:32%">${couponBatch.fuseLimitAmount}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">审核人：</td>
                <td class="text-right"  style="width:32%">${couponBatch.sauditOperatorName}</td>
                <td class="text-left"  style="width:18%">审核日期：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponBatch.tauditDatetime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">券说明：</td>
                <td class="text-right"  style="width:32%">${couponBatch.scouponInstruction}</td>
                <td class="text-left" style="width:18%">券简述：</td>
                <td class="text-right"  style="width:32%">${couponBatch.sbriefDesc}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">使用商品限制：</td>
                <td class="text-right"  style="width:32%">${(empty couponBatch.suseLimitCommodity) ? "不限制" : couponBatch.suseLimitCommodity}</td>
                <td class="text-left" style="width:18%">使用设备限制：</td>
                <td class="text-right" style="width:32%">${(empty couponBatch.suseLimitDevice) ? "不限制" : couponBatch.suseLimitDevice}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">使用商品分类限制：</td>
                <td class="text-right" style="width:32%">${(empty useLimitCategory) ? "不限制" : useLimitCategory}</td>
                <td class="text-left"  style="width:18%">使用商品品牌限制：</td>
                <td class="text-right"  style="width:32%">${(empty useLimitBrand) ? "不限制" : useLimitBrand}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">审核意见:</td>
                <td class="text-right" style="width:32%">${couponBatch.sauditOpinion}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
        </table>
    </div>
    <table class="layui-table">
        <colgroup>
            <col width="65">
            <col width="166">
            <col width="165">
            <col width="65">
            <col>
        </colgroup>
        <div class="wfsplitt" style="margin-top: 20px">
            <p>优惠券批量下发用户</p>
        </div>
        <thead>
        <tr>
            <th>序号</th>
            <th>会员编号</th>
            <th>会员用户名</th>
            <th>发送数量</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${couponUserSendList}" var="item" varStatus="L">
            <tr>
                <td>${L.index+1}</td>
                <td>${item.smemberCode}</td>
                <td>${item.smemberName}</td>
                <td>${item.inumber}</td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>
