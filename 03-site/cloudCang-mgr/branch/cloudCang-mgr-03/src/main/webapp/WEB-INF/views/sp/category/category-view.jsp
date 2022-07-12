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
                    <td class="text-left" style="width:18%"><spring:message code="main.merchant.number" />：</td>
                    <td class="text-right" style="width:32%">${category.smerchantCode}</td>
                    <td class="text-left" style="width:18%"><spring:message code="main.merchant.name" />：</td>
                    <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
                </tr>
            </c:if>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sp.category.category.name" />：</td>
                <td class="text-right"  style="width:32%">${category.sname}</td>
                <td class="text-left" style="width:18%"><spring:message code="sp.category.main.category.name" />：</td>
                <td class="text-right"  style="width:32%">${category.scategoryName}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="sp.category.whether.it.is.a.parent.class" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${category.iisParent == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${category.iisParent == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code="sp.category.parent.class.name" />：</td>
                <td class="text-right"  style="width:32%">${categoryParent.sname}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="sp.category.whether.to.display.externally" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${category.iisDisplay == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${category.iisDisplay == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code="main.sort" />：</td>
                <td class="text-right"  style="width:32%">${category.isort}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="sp.category.whether.it.is.popular" />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${category.iisHot == 0 }"><spring:message code="main.no" /></c:if>
                    <c:if test="${category.iisHot == 1 }"><spring:message code="main.yes" /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code="main.remarks" />：</td>
                <td class="text-right"  style="width:32%">${category.sremark}</td>
                <%--<td class="text-left"  style="width:18%"></td>--%>
                <%--<td class="text-right"  style="width:32%"></td>--%>
                <%--<td class="text-left"  style="width:18%"><spring:message code="sp.category.types.of" />：</td>--%>
                <%--<td class="text-right"  style="width:32%">--%>
                    <%--<c:if test="${category.itype == 10 }"><spring:message code="sp.category.general.purpose" /></c:if>--%>
                    <%--<c:if test="${category.itype == 20 }"><spring:message code="sp.category.merchant" /></c:if>--%>
                <%--</td>--%>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="sp.category.product.image" />：</td>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

