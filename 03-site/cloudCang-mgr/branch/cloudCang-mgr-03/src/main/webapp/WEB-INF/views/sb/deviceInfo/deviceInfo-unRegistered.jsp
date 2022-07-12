<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>未注册TCP列表</title>
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
                            <col width="100">
                        </colgroup>
                        <thead>
                        <tr>
                            <th><spring:message code='sb.ip.address' /></th>
                            <th><spring:message code='sb.port.number' /></th>
                            <th><spring:message code="main.operation" /></th>
                        </tr>
                        </thead>
                        <tbody id="deviceBody">
                        <c:forEach items="${tcpVoList}" var="vo">
                            <tr>
                                <td>${vo.ip}</td>
                                <td>${vo.port}</td>
                                <td><a href="javascript:void(0);" onclick="disconnect('${vo.channelId}');"><spring:message code='sb.remove' /></a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">


    layui.use(['form'], function () {
        var form = layui.form;
    });

    function disconnect(channelId) {
        confirmTip("<spring:message code='sb.are.you.sure.you.want.to.disconnect.this.connection' />",function () {
             $.ajax({
                type: 'post',
                url: '${ctx}/device/info/disconnectTcp',
                data: {channelId: channelId},
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



