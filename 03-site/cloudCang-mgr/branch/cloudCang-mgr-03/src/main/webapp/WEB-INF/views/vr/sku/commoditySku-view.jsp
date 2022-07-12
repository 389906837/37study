<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>视觉SKU商品详情</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code="vrsku.visual.identification.number" /></td>
                    <td class="text-right" style="width:32%">${commoditySku.svrCode}</td>
                    <td class="text-left" style="width:18%"><spring:message code="vrsku.barcode" /></td>
                    <td class="text-right" style="width:32%">${commoditySku.sproductBarcode}</td>
                </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.product.number" />：</td>
                <td class="text-right"  style="width:32%">${commoditySku.scode}</td>
                <td class="text-left" style="width:18%"><spring:message code="main.product.name" />：</td>
                <td class="text-right"  style="width:32%">${commoditySku.scommodityName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.brand.name" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.sbrandName}</td>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.main.category.name" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.scategoryName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.large.class.name" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.sbigCategoryName}</td>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.small.class.name" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.ssmallCategoryName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.specification.unit" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.sspecUnit}</td>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.specification.weight" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.ispecWeight}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.minimum.packaging.unit" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.spackageUnit}</td>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.manufacturer" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.smanufacturer}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.shelf.life" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.ishelfLife}</td>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.type.of.shelf.life" /></td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commoditySku.ilifeType == 10 }"><spring:message code="vrsku.day" /></c:if>
                    <c:if test="${commoditySku.ilifeType == 20 }"><spring:message code="vrsku.month" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.taste" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.staste}</td>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.packing.material" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.spackagingMaterial}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.product.weight" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.iweigth}</td>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.floating.value.of.commodity.weight" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.icommodityFloat}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.origin" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.sorigin}</td>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.identification.type" /></td>
                <td class="text-right"  style="width:32%">${markType}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.product.status" /></td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commoditySku.istatus == 10 }"><spring:message code="vrsku.on.sale" /></c:if>
                    <c:if test="${commoditySku.istatus == 20 }"><spring:message code="vrsku.discontinue.use" /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.on.state" /></td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commoditySku.ionlineStatus == 10 }"><spring:message code="vrsku.draft" /></c:if>
                    <c:if test="${commoditySku.ionlineStatus == 20 }"><spring:message code="vrsku.has.been.on" /></c:if>
                    <c:if test="${commoditySku.ionlineStatus == 30 }"><spring:message code="vrsku.obtained" /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.version.number" /></td>
                <td class="text-right"  style="width:32%">${commoditySku.iverson}</td>
                <td class="text-left"  style="width:18%"><spring:message code="main.remarks" />：</td>
                <td class="text-right"  style="width:32%">${commoditySku.sremark}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="vrsku.product.image" /></td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${!empty  commoditySku.scommodityImg}">
                    <p style="text-align: left;">
                    <img src="${dynamicSource}${commoditySku.scommodityImg}" style='width: 50px; height: 50px;'>
                    </p>
                    </c:if>
                </td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
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

