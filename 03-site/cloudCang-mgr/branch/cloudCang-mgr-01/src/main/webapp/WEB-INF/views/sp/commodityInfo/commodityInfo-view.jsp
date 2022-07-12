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
                <td class="text-left" style="width:18%">商户编号：</td>
                <td class="text-right" style="width:32%">${merchantInfo.scode}</td>
                <td class="text-left" style="width:18%">商户名称：</td>
                <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">商品编号：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.scode}</td>
                <td class="text-left"  style="width:18%">商品名称：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.sname}</td>
            </tr>
            <c:if test="${commodityInfo.istoreDevice == 10}">
            <tr>
                <td class="text-left" style="width:18%">视觉商品编号：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.svrCommodityCode}</td>
                <td class="text-left"  style="width:18%">视觉商品识别编号：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.svrCode}</td>
            </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%">品牌名称：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.sbrandName}</td>
                <td class="text-left"  style="width:18%">商品标签名称：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.slabelName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">大类名称：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.sbigCategoryName}</td>
                <td class="text-left" style="width:18%">小类名称：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.ssmallCategoryName}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">成本价：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.fcostPrice}</td>
                <td class="text-left" style="width:18%">市场价：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.fmarketPrice}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">销售价：</td>
                <td class="text-right" style="width:32%">${commodityInfo.fsalePrice}</td>
                <td class="text-left"  style="width:18%">最小销售包装单位：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.spackageUnit}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">规格单位：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.sspecUnit}</td>
                <td class="text-left"  style="width:18%">规格/重量：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.ispecWeight}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">保质期：</td>
                <td class="text-right" style="width:32%">${commodityInfo.ishelfLife}</td>
                <td class="text-left"  style="width:18%">保质期类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commodityInfo.ilifeType == 10 }">天</c:if>
                    <c:if test="${commodityInfo.ilifeType == 20 }">月</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">口味：</td>
                <td class="text-right" style="width:32%">${commodityInfo.staste}</td>
                <td class="text-left"  style="width:18%">包装材质：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.spackagingMaterial}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">产地：</td>
                <td class="text-right" style="width:32%">${commodityInfo.sorigin}</td>
                <td class="text-left"  style="width:18%">标识类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commodityInfo.iidentificationType == 10 }">特产</c:if>
                    <c:if test="${commodityInfo.iidentificationType == 20 }">进口</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">商品条形码：</td>
                <td class="text-right" style="width:32%">${commodityInfo.sproductBarcode}</td>
                <td class="text-left"  style="width:18%">商品简称：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.sshortName}</td>
            </tr>
            <%--<tr>--%>
                <%--<td class="text-left"  style="width:18%">是否可以积分抵扣：</td>--%>
                <%--<td class="text-right"  style="width:32%">--%>
                    <%--<c:if test="${commodityInfo.iisUseIntegral == 0 }">否</c:if>--%>
                    <%--<c:if test="${commodityInfo.iisUseIntegral == 1 }">是</c:if>--%>
                <%--</td>--%>
                <%--<td class="text-left" style="width:18%">最高抵扣积分：</td>--%>
                <%--<td class="text-right" style="width:32%">${commodityInfo.imaxIntegral}</td>--%>
            <%--</tr>--%>
            <tr>
                <td class="text-left" style="width:18%">商品状态：</td>
                <td class="text-right" style="width:32%">
                    <c:if test="${commodityInfo.istatus == 10 }">正常</c:if>
                    <c:if test="${commodityInfo.istatus == 20 }">失效</c:if>
                </td>
                <td class="text-left"  style="width:18%">总销售数量：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.isaleNum }</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">平均销售价：</td>
                <td class="text-right" style="width:32%">${commodityInfo.favgSalePrice }</td>
                <td class="text-left"  style="width:18%">平均成本价：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.favgCostPrice }</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">商品重量（g）：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.iweigth}</td>
                <td class="text-left" style="width:18%">商品重量浮动值（g）：</td>
                <td class="text-right"  style="width:32%">${commodityInfo.icommodityFloat}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">商品图片：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${!empty  commodityInfo.scommodityImg}">
                        <p style="text-align: left;">
                            <img src="${dynamicSource}${commodityInfo.scommodityImg}" style='width: 50px; height: 45px;'>
                        </p>
                    </c:if>
                </td>
                <td class="text-left" style="width:18%">备注：</td>
                <td class="text-right" style="width:32%">${commodityInfo.sremark}</td>
            </tr>
        </table>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

