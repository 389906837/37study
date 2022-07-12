<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>商品品牌信息</title>
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
                <td class="text-right" style="width:32%">${brandInfo.smerchantCode}</td>
                <td class="text-left" style="width:18%"><spring:message code="main.merchant.name" />：</td>
                <td class="text-right" style="width:32%">${merchantInfo.sname}</td>
            </tr>
        </c:if>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="sp.brand.brand.name" />：</td>
                <td class="text-right"  style="width:32%">${brandInfo.sname}</td>
                <td class="text-left"  style="width:18%"><spring:message code="main.sort" />：</td>
                <td class="text-right"  style="width:32%">${brandInfo.isort}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="sp.brand.brand.logo" />：</td>
                <td class="text-right"  style="width:32%">
                <c:if test="${!empty  brandInfo.slogo}">
                    <p style="text-align: left;">
                    <img src="${dynamicSource}${brandInfo.slogo}" style='width: 100px; height: 100px;'>
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

