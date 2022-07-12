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
                <td class="text-left" style="width:18%">所属区域编号：</td>
                <td class="text-right" style="width:32%">${deviceManage.sareaCode}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
            <tr>
                <td class="text-left">区域负责人：</td>
                <td class="text-right">${deviceManage.sareaPrincipal}</td>
                <td class="text-left" style="width:19%">区域负责人联系方式：</td>
                <td class="text-right" style="width:32%">${deviceManage.sareaPrincipalContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">设备负责人：</td>
                <td class="text-right" style="width:32%">${deviceManage.sdevicePrincipal}</td>
                <td class="text-left" style="width:18%">设备负责人联系方式：</td>
                <td class="text-right" style="width:32%">${deviceManage.sdevicePrincipalContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">补货员姓名：</td>
                <td class="text-right" style="width:32%">${deviceManage.sreplenishment}</td>
                <td class="text-left" style="width:18%">补货员联系方式：</td>
                <td class="text-right" style="width:32%">${deviceManage.sreplenishmentContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">设备维护人姓名：</td>
                <td class="text-right" style="width:32%">${deviceManage.smaintain}</td>
                <td class="text-left" style="width:18%">维护人联系方式：</td>
                <td class="text-right" style="width:32%">${deviceManage.smaintainContact}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%">备注：</td>
                <td class="text-right" style="width:32%">${deviceManage.sremark}</td>
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

