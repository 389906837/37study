<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品分类详情</title>
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
                    <td class="text-right" style="width:32%">${category.smerchantCode}</td>
                    <td class="text-left" style="width:18%">商户名称：</td>
                    <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
                </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%">分类名称：</td>
                <td class="text-right"  style="width:32%">${category.sname}</td>
                <td class="text-left" style="width:18%">主分类名称：</td>
                <td class="text-right"  style="width:32%">${category.scategoryName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">是否父类：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${category.iisParent == 0 }">否</c:if>
                    <c:if test="${category.iisParent == 1 }">是</c:if>
                </td>
                <td class="text-left"  style="width:18%">父类名称：</td>
                <td class="text-right"  style="width:32%">${categoryParent.sname}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">是否对外显示：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${category.iisDisplay == 0 }">否</c:if>
                    <c:if test="${category.iisDisplay == 1 }">是</c:if>
                </td>
                <td class="text-left"  style="width:18%">排序号：</td>
                <td class="text-right"  style="width:32%">${category.isort}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">是否热门分类：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${category.iisHot == 0 }">否</c:if>
                    <c:if test="${category.iisHot == 1 }">是</c:if>
                </td>
                <td class="text-left"  style="width:18%">备注：</td>
                <td class="text-right"  style="width:32%">${category.sremark}</td>
                <%--<td class="text-left"  style="width:18%"></td>--%>
                <%--<td class="text-right"  style="width:32%"></td>--%>
                <%--<td class="text-left"  style="width:18%">类型：</td>--%>
                <%--<td class="text-right"  style="width:32%">--%>
                    <%--<c:if test="${category.itype == 10 }">通用</c:if>--%>
                    <%--<c:if test="${category.itype == 20 }">商户</c:if>--%>
                <%--</td>--%>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">商品图片：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${!empty  category.sicon}">
                    <p style="text-align: left;">
                    <img src="${dynamicSource}${category.sicon}" style='width: 50px; height: 50px;'>
                    </p>
                    </c:if>
                </td>
                <td class="text-left"  style="width:18%"></td>
                <td class="text-right"  style="width:32%"></td>
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

