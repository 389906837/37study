<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>已注册TCP列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="layui-form-item">
                    <table class="layui-table" id="deviceTable">
                        <colgroup>
                            <col width="150">
                            <col width="150">
                            <col width="150">
                            <col width="100">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>设备编号</th>
                            <th>IP地址</th>
                            <th>端口号</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="deviceBody">
                        <c:forEach items="${tcpVoList}" var="vo">
                            <tr>
                                <td>${vo.deviceCode}</td>
                                <td>${vo.ip}</td>
                                <td>${vo.port}</td>
                                <td><a href="javascript:void(0);" onclick="disconnect('${vo.deviceId}');">移除</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">

    layui.use(['form'], function () {
        var form = layui.form;
    });

    function disconnect(deviceId) {
        confirmTip("确定要断开此连接吗?",function () {
            $.ajax({
                type: 'post',
                url: '${ctx}/device/info/disconnectRegisterTcp',
                data: {deviceId: deviceId},
                dataType: 'json',
                success: function () {
                    window.location.reload();
                }
            });
        });
    }
</script>
</body>
</html>



