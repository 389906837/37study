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
                <td class="text-left" style="width:18%">模板编号：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.scode}</td>
                <td class="text-left" style="width:18%">模板名称：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sname}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">生产厂商：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sareaPrincipal}</td>
                <td class="text-left" style="width:18%">联系方式：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sareaPrincipalContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">设备负责人：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sdevicePrincipal}</td>
                <td class="text-left" style="width:18%">联系方式：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sdevicePrincipalContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">补货员：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sreplenishment}</td>
                <td class="text-left" style="width:18%">联系方式：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sreplenishmentContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">设备维护人员：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.smaintain}</td>
                <td class="text-left" style="width:18%">联系方式：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.smaintainContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">备注：</td>
                <td class="text-right" style="width:32%">${deviceManageTemplate.sremark}</td>
                <td class="text-left" style="width:18%"></td>
                <td class="text-right" style="width:32%"></td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>


