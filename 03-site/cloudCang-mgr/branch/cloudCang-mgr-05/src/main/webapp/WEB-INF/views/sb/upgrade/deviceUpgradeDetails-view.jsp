<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备信息查看</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.device.id" />：</td>
                <td class="text-right" style="width:32%">${deviceUpgradeDetailsDomain.sdeviceCode}</td>
                <td class="text-left" style="width:18%"><spring:message code="main.device.name" />：</td>
                <td class="text-right" style="width:32%">${deviceUpgradeDetailsDomain.sname}</td>
            </tr>
            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.merchant.number" />：</td>
                <td class="text-right"  style="width:32%">${deviceUpgradeDetailsDomain.merchantCode}</td>
                <td class="text-left" style="width:18%"><spring:message code="main.merchant.name" />：</td>
                <td class="text-right"  style="width:32%">${deviceUpgradeDetailsDomain.merchantName}</td>
            </tr>
            </c:if>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='sb.upgrade.status' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${deviceUpgradeDetailsDomain.istatus == 10 }"><spring:message code="main.to.be.processed" /></c:if>
                    <c:if test="${deviceUpgradeDetailsDomain.istatus == 20 }"><spring:message code='sb.update.successed' /></c:if>
                    <c:if test="${deviceUpgradeDetailsDomain.istatus == 30 }"><spring:message code='sb.upgrade.unsuccessful' /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code='sb.upgrade.exception.reason.description' />：</td>
                <td class="text-right"  style="width:32%">${deviceUpgradeDetailsDomain.sexceptionDesc}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='sb.upgrade.start.time' />：</td>
                <td class="text-right"  style="width:32%">
                    <fmt:formatDate value="${deviceUpgradeDetailsDomain.tstartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code='sb.upgrade.end.time' />：</td>
                <td class="text-right"  style="width:32%">
                    <fmt:formatDate value="${deviceUpgradeDetailsDomain.tendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
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





