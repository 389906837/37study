<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备网络摄像头列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code="sb.device.camera.management" /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="sip" id="sip" value="" placeholder="IP..." class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sport" id="sport" value="" placeholder='<spring:message code="sb.the.port.number" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sserialNumber" id="sserialNumber" value="" placeholder='<spring:message code="sb.serial.number" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sweightCode" id="sweightCode" value="" placeholder="称重编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="slaveAddress" id="slaveAddress" value="" placeholder="从机地址..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="DEVICE_NET_CAMERA_ADD">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DEVICE_NET_CAMERA_DELETE">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
                            </shiro:hasPermission>
                        </div>
                    </div>
                    <div class="jqGrid_wrapper">
                        <table id="gridTable"></table>
                        <div id="gridPager"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="sdeviceId" name="sdeviceId" value="${sdeviceId}"/>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var sdeviceId = $("#sdeviceId").val();
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {name: 'sip', index: 'sip', label: "IP",width:230},
        {name: 'sport', index: 'sport', label: '<spring:message code="sb.the.port.number" />',width:230},
        {name: 'sserialNumber', index: 'SSERIAL_NUMBER', label: '<spring:message code="sb.serial.number" />',width:230},
 		{name: 'slaveAddress', index: 'SLAVE_ADDRESS', label: "从机地址", width: 120},
        {name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sb.add.date" />',formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        },width:230},
        {name: "process", title:false,index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 310}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="DEVICE_NET_CAMERA_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_NET_CAMERA_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_NET_CAMERA_DELETE">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code="main.delete" />' alt='<spring:message code="main.delete" />'  /> | ";
                </shiro:hasPermission>
                $("#gridTable").jqGrid('setRowData',
                    ids[i], {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }

    $(function () {
        var config = {
            sortname: "tadd_Time",
            sortorder: "desc"
        };
        initTable(ctx + "/cameraConfig/queryData?sdeviceId=" + sdeviceId, colModel, tableCallBack, config);
    });
    var initBtnConfig = {
        addUrl: ctx + '/cameraConfig/toAdd?sdeviceId=' + sdeviceId,
        deleteUrl: ctx + "/cameraConfig/delete",
        addTitle: '<spring:message code="sb.add.webcam.configuration.information" />',
        addFn: showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function viewMethod(sid) {
        showLayerMin('<spring:message code="sb.webcam.configuration.details" />', ctx + '/cameraConfig/view?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMin('<spring:message code="sb.edit.webcam.configuration.information" />', ctx + '/cameraConfig/toEdit?sdeviceId=' + sdeviceId + "&sid=" + sid);
    }
    function delMethod(sid) {
        confirmDelTip('<spring:message code="sb.make.sure.you.want.to.delete.data" />?', ctx + "/cameraConfig/delete", {checkboxId: sid});
    }
</script>
</body>
</html>



