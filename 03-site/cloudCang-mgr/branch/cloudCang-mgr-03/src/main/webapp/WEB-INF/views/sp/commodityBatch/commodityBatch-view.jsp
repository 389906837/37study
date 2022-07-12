<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品信息查看</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.merchant.number" />：</td>
                <td class="text-right" style="width:32%">${commodityBatch.smerchantCode}</td>
                <td class="text-left" style="width:18%"><spring:message code="main.merchant.name" />：</td>
                <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
            </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.product.number" />：</td>
                <td class="text-right" style="width:32%">${commodityBatch.scommodityCode}</td>
                <td class="text-left" style="width:18%"><spring:message code="main.product.name" />：</td>
                <td class="text-right" style="width:32%">${commodityInfo.sname}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="sm.batch.number" />：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.sbatchNo}</td>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityBatch.the.total.number" />：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.icommodityNum}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="sp.commodityBatch.number.of.shelves" />：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.ishelfNum}</td>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityBatch.commodity.loss" />：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.ilossGoodsNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sm.commodity.cost.price" />：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.fcostAmount }</td>
                <td class="text-left"  style="width:18%"><spring:message code="sp.commodityBatch.commodity.purchase.tax.point" />：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.ftaxPoint}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="sm.date.of.production" />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${commodityBatch.dproduceDate}" pattern="yyyy-MM-dd"/></td>
                <td class="text-left"  style="width:18%" ><spring:message code="sm.expiration.date" />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${commodityBatch.dexpiredDate}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityBatch.sales.status" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commodityBatch.isaleStatus == 10 }"><spring:message code="sp.commodityBatch.sale" /></c:if>
                    <c:if test="${commodityBatch.isaleStatus == 20 }"><spring:message code="sp.commodityBatch.sold.out" /></c:if>
                    <c:if test="${commodityBatch.isaleStatus == 30 }"><spring:message code="sp.commodityBatch.pending.sales" /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityBatch.inventory.status" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commodityBatch.istockStatus == 10 }"><spring:message code="sp.commodityBatch.waiting.to.be.put.on.the.shelves" /></c:if>
                    <c:if test="${commodityBatch.istockStatus == 20 }"><spring:message code="sp.commodityBatch.partly.on.the.shelf" /></c:if>
                    <c:if test="${commodityBatch.istockStatus == 30 }"><spring:message code="sp.commodityBatch.all.shelves" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityBatch.operation.state" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commodityBatch.istatus == 10 }"><spring:message code="main.enable" /></c:if>
                    <c:if test="${commodityBatch.istatus == 20 }"><spring:message code='main.disable' /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code="main.remarks" />：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.sremark}</td>
            </tr>
        </table>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

