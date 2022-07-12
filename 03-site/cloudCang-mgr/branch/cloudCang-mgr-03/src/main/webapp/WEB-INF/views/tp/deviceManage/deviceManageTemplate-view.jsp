<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备管理信息模板查看</title>
<link href="${staticSource}/resources/layui/css/layui.css?4" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmanage.template.number" />：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.scode}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmanage.template.name" />：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sname}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmanage.manufacturer" />：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sareaPrincipal}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmanage.contact.information" />：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sareaPrincipalContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmanage.equipment.manager" />：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sdevicePrincipal}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmanage.contact.information" />：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sdevicePrincipalContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmanage.replenisher" />：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sreplenishment}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmanage.contact.information" />：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sreplenishmentContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="tpmanage.equipment.maintenance.personnel" />：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.smaintain}</td>
                <td class="text-left" style="width:18%"><spring:message code="tpmanage.contact.information" />：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.smaintainContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.remarks" />：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sremark}</td>
                <td class="text-left" style="width:18%"></td>
                <td class="text-right" style="width:32%"></td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>


