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
                <td class="text-left" style="width:18%">商户编号：</td>
                <td class="text-right" style="width:32%">${commodityBatch.smerchantCode}</td>
                <td class="text-left" style="width:18%">商户名称：</td>
                <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
            </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%">商品编号：</td>
                <td class="text-right" style="width:32%">${commodityBatch.scommodityCode}</td>
                <td class="text-left" style="width:18%">商品名称：</td>
                <td class="text-right" style="width:32%">${commodityInfo.sname}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">批次号：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.sbatchNo}</td>
                <td class="text-left" style="width:18%">总数量：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.icommodityNum}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">已上架数量：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.ishelfNum}</td>
                <td class="text-left" style="width:18%">商品货损数量：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.ilossGoodsNum}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">商品成本价：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.fcostAmount }</td>
                <td class="text-left"  style="width:18%">商品进价税点：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.ftaxPoint}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">生产日期：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${commodityBatch.dproduceDate}" pattern="yyyy-MM-dd"/></td>
                <td class="text-left"  style="width:18%">过期日期：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value="${commodityBatch.dexpiredDate}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">销售状态：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commodityBatch.isaleStatus == 10 }">销售中</c:if>
                    <c:if test="${commodityBatch.isaleStatus == 20 }">售罄</c:if>
                    <c:if test="${commodityBatch.isaleStatus == 30 }">待销售</c:if>
                </td>
                <td class="text-left" style="width:18%">库存状态：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commodityBatch.istockStatus == 10 }">待上架</c:if>
                    <c:if test="${commodityBatch.istockStatus == 20 }">部分上架</c:if>
                    <c:if test="${commodityBatch.istockStatus == 30 }">全部上架</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">运营状态：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commodityBatch.istatus == 10 }">启用</c:if>
                    <c:if test="${commodityBatch.istatus == 20 }">禁用</c:if>
                </td>
                <td class="text-left"  style="width:18%">备注：</td>
                <td class="text-right"  style="width:32%">${commodityBatch.sremark}</td>
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

