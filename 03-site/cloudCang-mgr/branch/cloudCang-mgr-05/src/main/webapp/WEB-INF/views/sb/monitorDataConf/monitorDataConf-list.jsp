<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备监控信息列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
    }
</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.device.id" />...'
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="main.device.name" />...'
                                       class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value=""
                                           placeholder='<spring:message code="main.merchant.number" />...'
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value=""
                                           placeholder='<spring:message code="main.merchant.name" />...'
                                           class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                                </button>
                            </div>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    var colModel =[
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'SCODE', label: '<spring:message code="main.device.id" />',width:120,sortable : false},
        {name: 'sname', index: 'SNAME', label: '<spring:message code="main.device.name" />',width:200,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', index: 'merchantCode', label: '<spring:message code="main.merchant.number" />',width:200,sortable : false},
        {name: 'merchantName', index: 'merchantName', label: '<spring:message code="main.merchant.name" />',width:400,sortable : false},
        </c:if>
        {name: 'tinventoryTime', index: 'TINVENTORY_TIME', label: "<spring:message code='sb.last.shipment.time' />",formatter: function (value) {
            if(isEmpty(value)){
                return '';
            }
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        },width:200},
        {name: 'tbootTime', index: 'TBOOT_TIME', label: "<spring:message code='sb.boot.time' />",width:160},
        {name: 'tshutDownTime', index: 'TSHUT_DOWN_TIME', label: "<spring:message code='sb.shutdown.time' />",width:160},
        {name: 'iactualVolume', index: 'IACTUAL_VOLUME', label: "<spring:message code='sb.real.time.volume' />",width:160},
        {name: 'iinventoryNum', index: 'IINVENTORY_NUM', label: "<spring:message code='sb.number.of.shipments.per.shipment' />",width:160},
        {name: 'tinventoryBeginTime', index: 'TINVENTORY_BEGIN_TIME', label: "<spring:message code='sb.automatic.stock.start.time' />",width:160},
        {name: 'tinventoryEndTime', index: 'TINVENTORY_END_TIME', label: "<spring:message code='sb.automatic.stock.end.time' />",width:160},
        {name: "process", title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 200}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            var ids = $("#gridTable").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var cl = ids[i];
                var str = '';
                <shiro:hasPermission name="DEVICE_MODEL_VIEW">
                str += "<img src='${staticSource}/resources/images/oper_icon/look.png' class=\"oper_icon\" onclick=\"viewMethod('"
                    + cl + "')\" title=\"<spring:message code='sh.view' />\" alt=\"<spring:message code='sh.view' />\" /> | ";
                </shiro:hasPermission>
                <shiro:hasPermission name="DEVICE_MODEL_EDIT">
                str += "<img src='${staticSource}/resources/images/oper_icon/edit.png' class=\"oper_icon\" onclick=\"editMethod('"
                    + cl + "')\" title=\"<spring:message code='main.edit' />\" alt=\"<spring:message code='main.edit' />\" /> | ";
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
            multiselect:false,
            sortorder: "desc"
        };
        initTable(ctx+"/device/monitorDataConf/queryData",colModel,tableCallBack,config);
    });
    initBtnByForm2();


    function viewMethod(sid) {
        showLayerMedium("<spring:message code='sb.device.real.time.monitoring.details' />", ctx + '/device/monitorDataConf/toView?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("<spring:message code='sb.edit.device.real.time.monitoring.information' />", ctx + '/device/monitorDataConf/toEdit?sid=' + sid);
    }
</script>
</body>
</html>


