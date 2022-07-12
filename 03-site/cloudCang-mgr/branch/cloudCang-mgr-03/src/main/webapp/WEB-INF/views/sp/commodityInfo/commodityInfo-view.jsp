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
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.merchant.number" />：</td>
                <td class="text-right" style="width:32%">${merchantInfo.scode}</td>
                <td class="text-left" style="width:18%"><spring:message code="main.merchant.name" />：</td>
                <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.product.number" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.scode}</td>
                <td class="text-left"  style="width:18%"><spring:message code="main.product.name" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.sname}</td>
            </tr>
            <c:if test="${commodityInfo.istoreDevice == 10}">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityInfo.visual.product.number" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.svrCommodityCode}</td>
                <td class="text-left"  style="width:18%"><spring:message code="sp.commodityInfo.visual.product.identification.number" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.svrCode}</td>
            </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%" ><spring:message code="sp.brand.brand.name" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.sbrandName}</td>
                <td class="text-left"  style="width:18%"><spring:message code="sp.commodityInfo.product.label.name" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.slabelName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%" ><spring:message code="sp.commodityInfo.large.class.name" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.sbigCategoryName}</td>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityInfo.small.class.name" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.ssmallCategoryName}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"> ><spring:message code="sp.commodityBatch.cost.price" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.fcostPrice}</td>
                <td class="text-left" style="width:18%" ><spring:message code="sp.commodityInfo.market.price" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.fmarketPrice}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%" ><spring:message code="sp.commodityInfo.sale.price" />：</td>
                <td class="text-right" style="width:32%">${commodityInfo.fsalePrice}</td>
                <td class="text-left"  style="width:18%"><spring:message code="sp.commodityInfo.minimum.sales.packaging.unit" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.spackageUnit}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityInfo.specification.unit" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.sspecUnit}</td>
                <td class="text-left"  style="width:18%"><spring:message code="sp.commodityInfo.specification/weight" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.ispecWeight}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%" ><spring:message code="sm.shelf.life" />：</td>
                <td class="text-right" style="width:32%">${commodityInfo.ishelfLife}</td>
                <td class="text-left"  style="width:18%" ><spring:message code="sp.commodityInfo.shelf.life.type" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commodityInfo.ilifeType == 10 }"><spring:message code="sp.commodityInfo.day" /></c:if>
                    <c:if test="${commodityInfo.ilifeType == 20 }"><spring:message code="sp.commodityInfo.month" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityInfo.taste" />：</td>
                <td class="text-right" style="width:32%">${commodityInfo.staste}</td>
                <td class="text-left"  style="width:18%"><spring:message code="sp.commodityInfo.packaging.material" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.spackagingMaterial}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityInfo.place.of.origin" />：</td>
                <td class="text-right" style="width:32%">${commodityInfo.sorigin}</td>
                <td class="text-left"  style="width:18%"><spring:message code="sp.commodityInfo.Identification.type" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commodityInfo.iidentificationType == 10 }"><spring:message code="sp.commodityInfo.specialty" /></c:if>
                    <c:if test="${commodityInfo.iidentificationType == 20 }"><spring:message code="sp.commodityInfo.import" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityInfo.product.barcode" />：</td>
                <td class="text-right" style="width:32%">${commodityInfo.sproductBarcode}</td>
                <td class="text-left"  style="width:18%" ><spring:message code="sp.commodityInfo.product.short.name" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.sshortName}</td>
            </tr>
            <%--<tr>--%>
                <%--<td class="text-left"  style="width:18%"><spring:message code="sp.commodityInfo.whether.it.is.possible.to.deduct.the.points" />：</td>--%>
                <%--<td class="text-right"  style="width:32%">--%>
                    <%--<c:if test="${commodityInfo.iisUseIntegral == 0 }"><spring:message code="main.no" /></c:if>--%>
                    <%--<c:if test="${commodityInfo.iisUseIntegral == 1 }"><spring:message code="main.yes" /></c:if>--%>
                <%--</td>--%>
                <%--<td class="text-left" style="width:18%"><spring:message code="sp.commodityInfo.maximum.deduction.credit" />：</td>--%>
                <%--<td class="text-right" style="width:32%">${commodityInfo.imaxIntegral}</td>--%>
            <%--</tr>--%>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sm.product.status" />：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${commodityInfo.istatus == 10 }"><spring:message code="main.normal" /></c:if>
                    <c:if test="${commodityInfo.istatus == 20 }"><spring:message code="main.invalid" /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code="sp.commodityInfo.total.sales" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.isaleNum }</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityInfo.average.sales.price" />：</td>
                <td class="text-right" style="width:32%">${commodityInfo.favgSalePrice }</td>
                <td class="text-left"  style="width:18%"><spring:message code="sp.commodityInfo.average" /> ><spring:message code="sp.commodityBatch.cost.price" />：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.favgCostPrice }</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityInfo.commodity.weight" />（g）：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.iweigth}</td>
                <td class="text-left" style="width:18%"><spring:message code="sp.commodityInfo.product.weight.floating.value" />（g）：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.icommodityFloat}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="sp.category.product.image" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${!empty  commodityInfo.scommodityImg}">
                        <p style="text-align: left;">
                            <img src="${dynamicSource}${commodityInfo.scommodityImg}" style='width: 50px; height: 45px;'>
                        </p>
                    </c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code="main.remarks" />：</td>
                <td class="text-right" style="width:32%">${commodityInfo.sremark}</td>
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

