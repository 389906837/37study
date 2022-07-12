<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备网络摄像头信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%">IP：</td>
                <td class="text-right"  style="width:32%">${cameraConfig.sip}</td>
                <td class="text-left"  style="width:18%">端口号：</td>
                <td class="text-right"  style="width:32%">${cameraConfig.sport}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%">序列号：</td>
                <td class="text-right"  style="width:32%">${cameraConfig.sserialNumber}</td>
                <td class="text-left" style="width:18%">备注：</td>
                <td class="text-right" style="width:32%">${cameraConfig.sremark}</td>
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

