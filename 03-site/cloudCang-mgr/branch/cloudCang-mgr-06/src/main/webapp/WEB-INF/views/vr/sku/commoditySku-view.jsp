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
                    <td class="text-left" style="width:18%">视觉识别编号：</td>
                    <td class="text-right" style="width:32%">${commoditySku.svrCode}</td>
                    <td class="text-left" style="width:18%">商品条形码：</td>
                    <td class="text-right" style="width:32%">${commoditySku.sproductBarcode}</td>
                </tr>
            <tr>
                <td class="text-left" style="width:18%">商品编号：</td>
                <td class="text-right"  style="width:32%">${commoditySku.scode}</td>
                <td class="text-left" style="width:18%">商品名称：</td>
                <td class="text-right"  style="width:32%">${commoditySku.scommodityName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">品牌名称：</td>
                <td class="text-right"  style="width:32%">${commoditySku.sbrandName}</td>
                <td class="text-left"  style="width:18%">主分类名称：</td>
                <td class="text-right"  style="width:32%">${commoditySku.scategoryName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">大类名称：</td>
                <td class="text-right"  style="width:32%">${commoditySku.sbigCategoryName}</td>
                <td class="text-left"  style="width:18%">小类名称：</td>
                <td class="text-right"  style="width:32%">${commoditySku.ssmallCategoryName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">规格单位：</td>
                <td class="text-right"  style="width:32%">${commoditySku.sspecUnit}</td>
                <td class="text-left"  style="width:18%">规格/重量：</td>
                <td class="text-right"  style="width:32%">${commoditySku.ispecWeight}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">最小包装单位：</td>
                <td class="text-right"  style="width:32%">${commoditySku.spackageUnit}</td>
                <td class="text-left"  style="width:18%">生产厂家：</td>
                <td class="text-right"  style="width:32%">${commoditySku.smanufacturer}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">保质期：</td>
                <td class="text-right"  style="width:32%">${commoditySku.ishelfLife}</td>
                <td class="text-left"  style="width:18%">保质期类型：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commoditySku.ilifeType == 10 }">天</c:if>
                    <c:if test="${commoditySku.ilifeType == 20 }">月</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">口味：</td>
                <td class="text-right"  style="width:32%">${commoditySku.staste}</td>
                <td class="text-left"  style="width:18%">包装材质：</td>
                <td class="text-right"  style="width:32%">${commoditySku.spackagingMaterial}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">商品重量（g）：</td>
                <td class="text-right"  style="width:32%">${commoditySku.iweigth}</td>
                <td class="text-left"  style="width:18%">商品重量浮动值：</td>
                <td class="text-right"  style="width:32%">${commoditySku.icommodityFloat}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">产地：</td>
                <td class="text-right"  style="width:32%">${commoditySku.sorigin}</td>
                <td class="text-left"  style="width:18%">标识类型：</td>
                <td class="text-right"  style="width:32%">${markType}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">商品状态：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commoditySku.istatus == 10 }">在售</c:if>
                    <c:if test="${commoditySku.istatus == 20 }">停用</c:if>
                </td>
                <td class="text-left"  style="width:18%">上线状态：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${commoditySku.ionlineStatus == 10 }">草稿</c:if>
                    <c:if test="${commoditySku.ionlineStatus == 20 }">已上架</c:if>
                    <c:if test="${commoditySku.ionlineStatus == 30 }">已下架</c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">版本号：</td>
                <td class="text-right"  style="width:32%">${commoditySku.iverson}</td>
                <td class="text-left"  style="width:18%">备注：</td>
                <td class="text-right"  style="width:32%">${commoditySku.sremark}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">商品图片：</td>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

