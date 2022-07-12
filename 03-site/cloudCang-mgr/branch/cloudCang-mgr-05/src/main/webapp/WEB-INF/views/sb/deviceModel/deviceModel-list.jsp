<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备详细信息列表</title>
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
                <div class="ibox-title">
                    <h5><spring:message code='menu.device.details' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.device.id" />...' class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="sname" id="sname" value="" placeholder='<spring:message code="main.device.name" />...' class="layui-input">
                            </div>
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantCode" id="merchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
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
                        <%--<div class="layui-form-item">
                            <button class="layui-btn layui-btn-sm " data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
                        </div>--%>
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
    var colModel = [
        {name: 'id', index: 'id', hidden: true},
        {name: 'scode', index: 'scode', label: '<spring:message code="main.device.id" />',width:150,sortable : false},
        {name: 'sname', index: 'SNAME', label: '<spring:message code="main.device.name" />',width:150,sortable : false},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'merchantCode', index: 'merchantCode', label: '<spring:message code="main.merchant.number" />',width:160,sortable : false},
        {name: 'merchantName', index: 'merchantName', label: '<spring:message code="main.merchant.name" />',width:320,sortable : false},
        </c:if>
        {name: 'sdeviceModel', index: 'sdeviceModel', label: '<spring:message code="sb.equipment.core.model" />',width:200},
        {name: 'smanufacturer', index: 'smanufacturer', label: "<spring:message code='tpmodel.core.manufacturer' />",width:200},
        {name: 'scoreDesc', index: 'scoreDesc', label: "<spring:message code='tpmodel.core.configuration.description' />",width:280},
//                {name: 'sweight', index: 'SREG_PORT', label: "<spring:message code='tpmodel.weight' />",width:100},
//                {name: 'sratedPower', index: 'SAUDIT_USER', label: "<spring:message code='tpmodel.rated.power' />",width:100},
//                {name: 'sproductCapacity', index: 'TAUDIT_TIME', label: "<spring:message code='tpmodel.commodity.capacity' />"},
//                {name: 'sproductTypes', index: 'SREMARK', label: "<spring:message code='tpmodel.commodity.types' />",width:100},
//                {name: 'sdailyPower', index: 'SREMARK', label: "<spring:message code='tpmodel.daily.power.consumption' />",width:100},
//                {name: 'slocksModel', index: 'SREMARK', label: "<spring:message code='tpmodel.electronic.lock.model' />",width:100},
//                {name: 'slocksManufacturer', index: 'SREMARK', label: "<spring:message code='tpmodel.manufacturer.of.electronic.locks' />",width:100},
//                {name: 'sadDimensions', index: 'SREMARK', label: "<spring:message code='tpmodel.screen.dimension.instructions.for.advertising.machines' />",width:100},
//                {name: 'sadConf', index: 'SREMARK', label: "<spring:message code='tpmodel.description.of.advertising.machine.configuration' />",width:100},
//                {
//                    name: 'spayWap',
//                    index: 'ISTATUS',
//                    label: "<spring:message code='tpmodel.support.payment.method' />",
//                    formatter: "select",
//                    editoptions: {value: "10:<spring:message code='main.audited' />;20:<spring:message code='main.approval' />;30:<spring:message code='main.rejection' />;40:<spring:message code='main.registered' />"}
//                    ,width:100
//                },
//                {
//                    name: 'ielectricShock',
//                    index: 'IDEL_FLAG',
//                    label: "<spring:message code='sb.delete.or.not' />",
//                    formatter: "select",
//                    editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}
//                    ,width:100
//                },
//                {name: 'saddUser', index: 'SADD_USER', label: "<spring:message code='hy.add.a.person' />",width:70},
//                {name: 'taddTime', index: 'TADD_TIME', label: '<spring:message code="sp.brand.add.date" />'},
//                {name: 'supdateUser', index: 'SUPDATE_USER', label: "<spring:message code='hy.modifier' />",width:70},
//                {name: 'tupdateTime', index: 'TUPDATE_TIME', label: "<spring:message code='sb.modified.date' />"},
        {name: "process", title:false, index: "process", label: '<spring:message code="main.operation" />', sortable: false, frozen: true, width: 200}
    ];
    //表格加载完成后回调
    function tableCallBack() {
        setTimeout(function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
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
        initTable(ctx+"/device/model/queryData",colModel,tableCallBack,config);
    });
    initBtnByForm2();

    function viewMethod(sid) {
        showLayerMedium("<spring:message code='sb.device.details.details' />", ctx + '/device/deviceModel/toView?sid=' + sid);
    }
    function editMethod(sid) {
        showLayerMedium("<spring:message code='sb.edit.device.details' />", ctx + '/device/deviceModel/toEdit?sid=' + sid);
    }

</script>
</body>
</html>


