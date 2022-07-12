<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备责任人信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sb.area.code' />：</td>
                <td class="text-right" style="width:32%">${deviceManage.sareaCode}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
            <tr>
                <td class="text-left"><spring:message code='sb.regional.head' />：</td>
                <td class="text-right">${deviceManage.sareaPrincipal}</td>
                <td class="text-left" style="width:19%"><spring:message code='sb.regional.contact.person' />：</td>
                <td class="text-right" style="width:32%">${deviceManage.sareaPrincipalContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='tpmanage.equipment.manager' />：</td>
                <td class="text-right" style="width:32%">${deviceManage.sdevicePrincipal}</td>
                <td class="text-left" style="width:18%"><spring:message code='sb.contact.person.in.charge.of.equipment' />：</td>
                <td class="text-right" style="width:32%">${deviceManage.sdevicePrincipalContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='rm.name.of.replenisher' />：</td>
                <td class="text-right" style="width:32%">${deviceManage.sreplenishment}</td>
                <td class="text-left" style="width:18%"><spring:message code='sb.replenisher.contact' />：</td>
                <td class="text-right" style="width:32%">${deviceManage.sreplenishmentContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sb.equipment.maintainer.name' />：</td>
                <td class="text-right" style="width:32%">${deviceManage.smaintain}</td>
                <td class="text-left" style="width:18%"><spring:message code='sb.maintainer.contact.information' />：</td>
                <td class="text-right" style="width:32%">${deviceManage.smaintainContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.remarks" />：</td>
                <td class="text-right" style="width:32%">${deviceManage.sremark}</td>
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

